package com.hyrt.lcsis.webservice.wsdl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamTest {

	public static void main(String args[]) throws IOException {
//		test1();
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String filename = sdf.format(date);
//		File f = new File("D://"+filename);
//		f.mkdir();
//		test2(filename);
		
//		ZipOutputStreamTest t = new ZipOutputStreamTest();
//		 String[] fileName = { "D:/移动平台_95015.rar" };
//		   t.fun1(fileName);
//		   t.fun2("D:/test.zip");
		
		
		File file=new File("D:/Workspaces/ceiTerminal/WebContent/WEB-INF/claass/aaaa.xml");
		System.out.println(file.exists());
		
	}
	
	public static void test1() throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\testZip.zip"));
		//实例化一个名称为ab.txt的ZipEntry对象
		ZipEntry entry = new ZipEntry("飞秋FeiQ.exe");
		//设置注释
		zos.setComment("zip测试for单个文件");
		//把生成的ZipEntry对象加入到压缩文件中，而之后往压缩文件中写入的内容都会放在这个ZipEntry对象里面
		zos.putNextEntry(entry);
		InputStream is = new FileInputStream("D:\\飞秋FeiQ.exe");
		int len = 0;
		while ((len = is.read()) != -1)
			zos.write(len);
		is.close();
		zos.close();
	}
	
	public static void test2(String filename) throws IOException {
		String[] strfile = {"D:/最新移动学习","D:/移动平台_95015"};
		for (int i = 0; i < strfile.length; i++) {
			String file = strfile[i];
			File inFile = new File(file);
			String[] file1 = file.split("/");
			String file2 = file1[file1.length-1];
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\"+filename+"\\"+file2+".zip"));
			zos.setComment("多文件处理");
			zipFile(inFile, zos, "");
			zos.close();
		}
	}
	
	public static void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			for (File file:files)
				zipFile(file, zos, dir + "\\" + inFile.getName());
		} else {
			String entryName = null;
			if (!"".equals(dir))
				entryName = dir + "\\" + inFile.getName();
			else
				entryName = inFile.getName();
			ZipEntry entry = new ZipEntry(entryName);
			zos.putNextEntry(entry);
			InputStream is = new FileInputStream(inFile);
			int len = 0;
			while ((len = is.read()) != -1)
				zos.write(len);
			is.close();
		}

	}


	public void fun1(String[] fileName) {
	   try {
	    FileOutputStream f = new FileOutputStream("D:/test.zip");// 文件输出流
	    CheckedOutputStream cumu = new CheckedOutputStream(f, new Adler32());// 校验输出流
	    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	      cumu));// 向压缩文件中输入数据
	    out.setComment("A test zipping!");// 为指定的压缩文件写注释
	    for (int i = 0; i < fileName.length; i++) {
	     BufferedReader in = new BufferedReader(new FileReader(
	       fileName[i]));
	     out.putNextEntry(new ZipEntry(fileName[i]));// 开始写入新的文件条目,并将流定位在新的文件条目位置
	     int c;
	     while ((c = in.read()) != -1) {
	      out.write(c);
	     }
	     in.close();
	    }
	    out.close();
	    System.out.println("checksum:" + cumu.getChecksum().getValue());// 返回此输入流的校验和
	   } catch (FileNotFoundException e) {
	    e.printStackTrace();
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	}


	public void fun2(String file) {
	   try {
	    FileInputStream cin = new FileInputStream(file);
	    CheckedInputStream cumu = new CheckedInputStream(cin, new Adler32());
	    ZipInputStream in = new ZipInputStream(
	      new BufferedInputStream(cumu));
	    System.out.println("checksum:" + cumu.getChecksum().getValue());
	    ZipEntry ze;
	    while ((ze = in.getNextEntry()) != null) {
	     System.out.println("file:" + ze);
	     int c;
	     while ((c = in.read()) != -1) {
	      System.out.println(c);
	     }
	    }
	    in.close();
	    ZipFile zipFile = new ZipFile("G:/test.zip");
	    Enumeration e = zipFile.entries();
	    while (e.hasMoreElements()) {
	     ZipEntry ze2 = (ZipEntry) e.nextElement();
	     System.out.println("file:" + ze2);
	    }
	   } catch (FileNotFoundException e) {
	    e.printStackTrace();
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	}


	
	 
}