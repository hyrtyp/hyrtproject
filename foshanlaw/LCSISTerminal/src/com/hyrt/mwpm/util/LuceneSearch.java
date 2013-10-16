package com.hyrt.mwpm.util;

import java.util.Date;
import java.util.LinkedList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

 

public class LuceneSearch {
	private IndexSearcher searcher=null;
	private Query query=null;
 
	public LuceneSearch(){
 
		try{

			searcher=LuceneUtils.createIndexSearcher();        
			//searcher = LuceneUtils.createIndexSearcher();        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
 
	public final TopDocs search(String keyword){
  
		System.out.println("���ڼ����ؼ��֣�"+keyword);
		try{
//			��ѯ���ַ���:���벻���ڵ��ַ����ǲ�ѯ������,�磺�й�   
	        //��ѯ�ֶμ���   
		//	query = LuceneUtils.createQuery(queryFileds, queryString);
	        QueryParser queryParser= new QueryParser(Version.LUCENE_35,"id",new StandardAnalyzer(Version.LUCENE_35));
	        Query query=queryParser.parse(keyword);
		//	query=QueryParser.parse(keyword);  
			Date start=new Date();
			int queryCount = 100;
			TopDocs hits=searcher.search(query,queryCount);
			Date end=new Date(); 
			System.out.println("���������ʱ"+(end.getTime()-start.getTime())/1000+"����");
			return hits;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
  
	}
	public LinkedList   printResult(TopDocs h){
		LinkedList  result=new LinkedList();;
		if(h.totalHits==0){
			System.out.println("�Բ���û���ҵ���Ҫ�Ľ����");
			//result=
			result.add("�Բ���û���ҵ���Ҫ�Ľ����");
		}else{	
			for(int i=0;i<h.totalHits;i++){    
				try{
//					�ĵ����   
		            int docID = h.scoreDocs[i].doc;   
		            System.out.println(h.totalHits+"|"+docID);
		            //����������   
		            Document doc = searcher.doc(docID);   

					System.out.println("���ǵ�"+(i+1)+"���������Ľ�����ļ���Ϊ��");
					System.out.println(doc.get("name"));
					String contents=doc.get("id");
    
					if(contents.length()>240){
						contents=contents.substring(0,128)+"...............";
					}
					//result+="���ǵ�"+(i+1)+"���������Ľ�����ļ���Ϊ��"+doc.get("path")+"<br>";          
					// result+=doc.get("contents")+"<br>";
					// result+="-------------------------------------------<br>";
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
		System.out.println("-----------------------------");
		return result;
	}
 
	public static void main(String args[])throws Exception{
  
		LuceneSearch test =new LuceneSearch();
		TopDocs h=null;
		h=test.search("8");
		test.printResult(h);
		
  
	}
}

