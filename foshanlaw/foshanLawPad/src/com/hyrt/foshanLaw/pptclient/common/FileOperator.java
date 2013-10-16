/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

import java.io.File;
import java.io.IOException;
 

import android.content.Context;

/**
 * Description:文件上传下载类
 * 
 * @author 郑伟
 * @Date 2013-1-9
 * @Copyright:2013-1-9
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class FileOperator {
	Context context;
	FtpSocket ftp;

	public FileOperator(Context _context) {
		context = _context;
		ftp = new FtpSocket();
	}

	/**
	 * 录音上传
	 * 
	 * @param userid
	 *            用户id
	 * @param filename
	 *            文件名称
	 * @param filepath
	 *            本地文件路径（含名称 ）
	 * @return
	 */
	public synchronized String upload(String sessionid,String userid, String filename,
			String filepath,long _id) {
		// Log.i("xxx", "文件路径=" + filepath);
		// 开始上传

		try {
			ftp.connect(context,CommKey.ftp_default_ip, CommKey.ftp_default_port,
					CommKey.ftpuser, CommKey.ftppwd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ftp服务器连接失败";
		}
		// 创建目录
		String did = sessionid+"/"+userid ;
		String re = "";
		// 创建目录
		if (ftp.mkdirs(did)) {
			// 有目录了,上传文件
			String serverpath = "/" + did + "/" + filename;
			// Log.i("ftp", serverpath);
			try {
				// 第二个参数是服务器路径
				boolean flag = ftp.stor(new File(filepath), serverpath,_id);
				// 事后断开
				ftp.disconnect();
				if (!flag) {
					re = "文件上传失败";
				} else {
					re = "ok";
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				re = "文件上传失败,IO异常";
			}
		}
	
		return re;
	}

	/**
	 * 下载函数
	 * 
	 * @param filepath
	 *            本地文件路径（含名称）
	 * @param filename
	 *            文件名称
	 * @return
	 */
	public synchronized String download(String ftppath, String filepath) {
		// 开始连接
		try {

			ftp.connect(context,CommKey.ftp_default_ip, CommKey.ftp_default_port,
					CommKey.ftpuser, CommKey.ftppwd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "服务器连接失败";
		}
 
		try {
			boolean flag = ftp.RETR(context, ftppath, filepath);
			
			if (flag){
				return "ok";
			}
			else{
				return "下载失败";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  "下载失败";
		}
	}
	
	public void close(){
		if(ftp!=null){
			try {
				ftp.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
}
