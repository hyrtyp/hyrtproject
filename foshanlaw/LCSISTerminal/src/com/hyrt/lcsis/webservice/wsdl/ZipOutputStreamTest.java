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
//		 String[] fileName = { "D:/�ƶ�ƽ̨_95015.rar" };
//		   t.fun1(fileName);
//		   t.fun2("D:/test.zip");
		
		
		File file=new File("D:/Workspaces/ceiTerminal/WebContent/WEB-INF/claass/aaaa.xml");
		System.out.println(file.exists());
		
	}
	
	public static void test1() throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\testZip.zip"));
		//ʵ����һ������Ϊab.txt��ZipEntry����
		ZipEntry entry = new ZipEntry("����FeiQ.exe");
		//����ע��
		zos.setComment("zip����for�����ļ�");
		//�����ɵ�ZipEntry������뵽ѹ���ļ��У���֮����ѹ���ļ���д������ݶ���������ZipEntry��������
		zos.putNextEntry(entry);
		InputStream is = new FileInputStream("D:\\����FeiQ.exe");
		int len = 0;
		while ((len = is.read()) != -1)
			zos.write(len);
		is.close();
		zos.close();
	}
	
	public static void test2(String filename) throws IOException {
		String[] strfile = {"D:/�����ƶ�ѧϰ","D:/�ƶ�ƽ̨_95015"};
		for (int i = 0; i < strfile.length; i++) {
			String file = strfile[i];
			File inFile = new File(file);
			String[] file1 = file.split("/");
			String file2 = file1[file1.length-1];
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\"+filename+"\\"+file2+".zip"));
			zos.setComment("���ļ�����");
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
	    FileOutputStream f = new FileOutputStream("D:/test.zip");// �ļ������
	    CheckedOutputStream cumu = new CheckedOutputStream(f, new Adler32());// У�������
	    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	      cumu));// ��ѹ���ļ�����������
	    out.setComment("A test zipping!");// Ϊָ����ѹ���ļ�дע��
	    for (int i = 0; i < fileName.length; i++) {
	     BufferedReader in = new BufferedReader(new FileReader(
	       fileName[i]));
	     out.putNextEntry(new ZipEntry(fileName[i]));// ��ʼд���µ��ļ���Ŀ,��������λ���µ��ļ���Ŀλ��
	     int c;
	     while ((c = in.read()) != -1) {
	      out.write(c);
	     }
	     in.close();
	    }
	    out.close();
	    System.out.println("checksum:" + cumu.getChecksum().getValue());// ���ش���������У���
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