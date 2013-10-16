package test;

import java.io.File;

public class ThreadPool {
	
	
	public static void main(String[] args) {
		String filePath = "http://mob.cei.gov.cn:80/ftpServer/ftpRoot/imagePic//kj/2013/01/25/7caed8e4b1ca4ab5881beefa8eec6558/small.png";
		//filePath =  filePath.substring(0,filePath.lastIndexOf( File.separator )+1).substring(filePath.lastIndexOf( File.separator )+1)+filePath.substring(filePath.lastIndexOf( File.separator )+1);
		System.out.println(filePath.substring(0,filePath.lastIndexOf("/")).substring(filePath.substring(0,filePath.lastIndexOf("/")).lastIndexOf("/")+1));
	}

}
