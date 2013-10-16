/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
 

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Description:FTP操作类
 * @author 郑伟
 * @Date 2013-1-9
 * @Copyright:2013-1-9
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class FtpSocket {
	Context context;
	private Socket socket = null;

	private BufferedReader reader = null; // 输入

	private BufferedWriter writer = null; // 输出

	private static boolean DEBUG = false; // 是否输出错误信息

	InputStream is = null; // 输入流
	OutputStream os = null; // 输出流

 

	public synchronized Boolean connect(Context _context,String host, int port, String user,
			String pass) throws IOException {
		context=_context;
		if (socket != null) {
			throw new IOException("已经连接了FTP,如需要再连接，请先断开.");
		}
		socket = new Socket(host, port);
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));

		String response = readLine();
		if (DEBUG)
			//Log.i("FTP", "response:" + response);
		if (!response.startsWith("220 ")) {
			returnmsg("连接FTP时收到了来自于服务器的未知错误，错误编码为: " + response,0);
			throw new IOException("连接FTP时收到了来自于服务器的未知错误，错误编码为: " + response);
		}
		sendLine("USER " + user);

		response = readLine();
		if (!response.startsWith("331 ")) {
			returnmsg("输入用户名时收到了来自于服务器的未知错误，错误编码为: " + response,0);
			throw new IOException("输入用户名时收到了来自于服务器的未知错误，错误编码为: " + response);
		}

		sendLine("PASS " + pass);

		response = readLine();
		if (!response.startsWith("230 ")) {
			returnmsg("密码错误: " + response,0);
			throw new IOException("密码错误: " + response);
		}
		return true;

		// Now logged in.
	}

	/**
	 * Disconnects from the FTP server.
	 */
	public synchronized void disconnect() throws IOException {
		try {
			sendLine("QUIT");
		} finally {
			if (socket != null) {
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
			}
			socket = null;
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            文件名
	 * @return 是否创建成功
	 * @throws IOException
	 */
	public synchronized boolean mkdir(String dir) {
		try {
			sendLine("MKD " +dir);
			String response = readLine();
			//Log.i("ftp", response);
			// 目录创建成功或者目录已经存在，则返回true
			if (response.startsWith("257 ") || response.startsWith("550 ")) {
 
				return true;
			} else {
				return false;
			}
		} catch (IOException x) {
			x.printStackTrace();
			return false;
		}
	}
	
	/** 
	 * 创建多级文件夹   
	 * @param dir xxx/ddd/fff   前后不能有/
	 * @return
	 */
	public synchronized boolean mkdirs(String dir){
		String[] path=dir.split("/");
		int i=0;
		for(;i<path.length-1;i++){
			if(mkdir(path[i])){
				this.cwd(path[i]);
			}else{
				return false;
			}
		}
		//创建最后一级
		i=path.length-1;
		if(mkdir(path[i])){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 读取目录
	 */
	public synchronized String pwd() throws IOException {
		sendLine("PWD");
		String dir = null;
		String response = readLine();
		if (response.startsWith("257 ")) {
			int firstQuote = response.indexOf('\"');
			int secondQuote = response.indexOf('\"', firstQuote + 1);
			if (secondQuote > 0) {
				dir = response.substring(firstQuote + 1, secondQuote);
			}
		}
		return dir;
	}

	/**
	 * 改变当前目录，成功了返回true
	 */
	public synchronized boolean cwd(String dir)   {
		try{
		sendLine("CWD " + dir);
		String response = readLine();
		return (response.startsWith("250 "));
		}catch(Exception x){
			x.printStackTrace();
			return false;
		}
	}

	/**
	 * 上传文件，成功了返回true. The file is sent in passive mode to avoid NAT or firewall
	 * problems at the client end.
	 */
	public synchronized boolean stor(File file, String ServerFullPath,long stamp)
			throws IOException {
		if (file.isDirectory()) {
			returnmsg("不能上传一个文件夹." ,stamp );
			throw new IOException("不能上传一个文件夹.");
		}
		// String filename = file.getName();

		return stor(new FileInputStream(file), ServerFullPath,stamp);
	}

	/**
	 * 上传文件，成功了返回true. . The file is sent in passive mode to avoid NAT or
	 * firewall problems at the client end.
	 */
	public synchronized boolean stor(InputStream inputStream, String filename,long stamp)
			throws IOException {

		BufferedInputStream input = new BufferedInputStream(inputStream);

		sendLine("PASV");
		String response = readLine();
		if (!response.startsWith("227 ")) {
			returnmsg("不能以passive的模式上传: " + response,stamp);
			throw new IOException("不能以passive的模式上传: " + response);
		}

		String ip = null;
		int port = -1;
		int opening = response.indexOf('(');
		int closing = response.indexOf(')', opening + 1);
		if (closing > 0) {
			String dataLink = response.substring(opening + 1, closing);
			StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
			try {
				ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
						+ tokenizer.nextToken() + "." + tokenizer.nextToken();
				port = Integer.parseInt(tokenizer.nextToken()) * 256
						+ Integer.parseInt(tokenizer.nextToken());
			} catch (Exception e) {
				returnmsg("文件获取出错: " + response,stamp);
				throw new IOException("文件获取出错: " + response);
			}
		}

		sendLine("STOR " + filename);

		Socket dataSocket = new Socket(ip, port);

		response = readLine();
		// if (!response.startsWith("125 ")) {
		if (!response.startsWith("150 ")) {
			returnmsg("没有上传权限: " + response,stamp);
			throw new IOException("没有上传权限: " + response);
		}

		BufferedOutputStream output = new BufferedOutputStream(
				dataSocket.getOutputStream());
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		output.flush();
		output.close();
		input.close();

		response = readLine();
		return response.startsWith("226 ");
	}

	/**
	 * 以2进制的方式上传文件.
	 */
	public synchronized boolean bin() throws IOException {
		sendLine("TYPE I");
		String response = readLine();
		return (response.startsWith("200 "));
	}

	/**
	 * 
	 * Enter ASCII mode for sending text files. This is usually the default
	 * mode. Make sure you use binary mode if you are sending images or other
	 * binary data, as ASCII mode is likely to corrupt them.
	 */
	public synchronized boolean ascii() throws IOException {
		sendLine("TYPE A");
		String response = readLine();
		return (response.startsWith("200 "));
	}

	/**
	 * 发送命令，内部调用方法.
	 */
	private void sendLine(String line) throws IOException {
		if (socket == null) {
			throw new IOException("SimpleFTP is not connected.");
		}
		try {
			writer.write(line + "\r\n");
			writer.flush();
			if (DEBUG) {
				Log.i("FTP", "> " + line);
			}
		} catch (IOException e) {
			socket = null;
			throw e;
		}
	}

	private String readLine() throws IOException {
		String line = reader.readLine();
		if (DEBUG) {
			// System.out.println("< " + line);
			 Log.i("FTP", "<" + line);
		}
		return line;
	}

	/*
	 * 下载,savename带路径
	 */
	public synchronized boolean RETR(Context context, String filename,
			String savename) throws IOException {
		Socket dataSocket = getConnection();
		sendLine("RETR " + filename);
		String response = readLine();
		if (!response.startsWith("150 ")) {
			returnmsg("文件暂时不能下载或者文件不存在: " + response,0);
			throw new IOException("文件暂时不能下载或者文件不存在: " + response);
		}
		// 直接保存到SD卡上
		Log.i("savename", savename);
		File file = new File(savename);
		if (file.exists())
			file.delete();
		file.createNewFile();
		FileOutputStream outfile = new FileOutputStream(file);
		// 构造传输文件用的数据流
		BufferedInputStream dataInput = new BufferedInputStream(
				dataSocket.getInputStream());
		// 接收来自服务器的数据，写入本地文件
		int n;
		byte[] buff = new byte[1024];
		while ((n = dataInput.read(buff)) > 0) {
			outfile.write(buff, 0, n);
		}
		dataSocket.close();
		outfile.close();

		response = readLine();
		return response.startsWith("226 ");
	}

	public synchronized Vector<String> List() throws IOException {
		Socket dataSocket = getConnection();
		sendLine("LIST ");
		Vector<String> result = new Vector<String>();
		int n;
		byte[] buff = new byte[65530];
		// 准备读取数据用的流
		BufferedInputStream dataInput = new BufferedInputStream(
				dataSocket.getInputStream());
		// 读取目录信息

		while ((n = dataInput.read(buff)) > 0) {
			System.out.write(buff, 0, n);
			result.add(new String(buff, 0, n));
		}
		dataSocket.close();
		readLine();
		readLine();

		return result;
	}

	private Socket getConnection() throws IOException {
		sendLine("PASV");
		String response = readLine();
		if (!response.startsWith("227 ")) {
			returnmsg("文件不能以passive模式下载: " + response,0);
			throw new IOException("文件不能以passive模式下载: " + response);
		}

		String ip = null;
		int port = -1;
		int opening = response.indexOf('(');
		int closing = response.indexOf(')', opening + 1);
		if (closing > 0) {
			String dataLink = response.substring(opening + 1, closing);
			StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
			try {
				ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
						+ tokenizer.nextToken() + "." + tokenizer.nextToken();
				port = Integer.parseInt(tokenizer.nextToken()) * 256
						+ Integer.parseInt(tokenizer.nextToken());
			} catch (Exception e) {
				returnmsg("下载文件出错: " + response,0);
				throw new IOException("下载文件出错: " + response);
			}
		}
		Socket dataSocket = new Socket(ip, port);
		return dataSocket;

	}
	
	 void returnmsg(String msg,long stamp){
		 Intent iesession = new Intent();
			iesession.setAction(CommKey.ftp_msg);
			iesession.putExtra("msg",msg);
			iesession.putExtra("stamp", stamp);
			GlobalFunction.sendPendingBroadcast(context, iesession, 1);
	 }
}
