package com.hyrt.mwpm.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
public class main2 {
private static String fileDir = "F:/mingsuo";
	private static String indexDir1 = "F:/mingsuo/index";
	static Analyzer luceneAnalyzer = new IKAnalyzer();
	static Directory   indexDir = null ;
	/***
	 * 建立索引
	 */
	
	public static void createIndex1() {
		File file = new File("F:/mingsuo/jiangsu.xls");
		String indexDir1 = "F:/mingsuo/index/jiangsu";
		
            try{
            	IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,luceneAnalyzer);
            	indexDir=new SimpleFSDirectory(new File(indexDir1));
            	config.setOpenMode(OpenMode.CREATE);
            	IndexWriter indexWriter = null;
            	indexWriter = new IndexWriter(indexDir,config);
            	Workbook workbook = null;
            	Sheet newsheet = null;
            	String tmp = null;
            	try {
            		workbook = Workbook.getWorkbook(file);// 创建对Excel工作簿文件的引用
            		for (int i=0;i<workbook.getNumberOfSheets();i++){
            			newsheet = workbook.getSheet(i);
            			for (int m=0;m<newsheet.getRows();m++){
            				Document doc = new Document();
            				tmp = newsheet.getCell(0,m)==null?"":((Cell)newsheet.getCell(0,m)).getContents();
            				doc.add(new Field("name",tmp,Field.Store.YES, Field.Index.ANALYZED));
            				tmp = newsheet.getCell(1,m)==null?"":((Cell)newsheet.getCell(1,m)).getContents();
            				doc.add(new Field("code",tmp,Field.Store.YES, Field.Index.ANALYZED));
            				tmp = newsheet.getCell(3,m)==null?"":((Cell)newsheet.getCell(3,m)).getContents();
            				doc.add(new Field("people",tmp,Field.Store.YES, Field.Index.NO));
            				tmp = newsheet.getCell(2,m)==null?"":((Cell)newsheet.getCell(2,m)).getContents();
            				doc.add(new Field("address",tmp,Field.Store.YES, Field.Index.NO));
            				indexWriter.addDocument(doc);
            			}
            		}
            	} catch (Exception e) {
            		if (workbook!=null){
					   workbook.close();
            		}
            		e.printStackTrace();
            	}
            	if (workbook!=null){
            		workbook.close();
            	}
            	indexWriter.close();
            	System.out.println("完毕");
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("全部处理完毕");
	}
	public static void main(String [] args){
		
		Date start = new Date();
		try{
			createIndex1();
		}catch(Exception e){
			e.printStackTrace();
		}
		Date end = new Date();
		System.out.println("建立索引用时" + (end.getTime() - start.getTime() + "毫秒"));
		
	}
	}