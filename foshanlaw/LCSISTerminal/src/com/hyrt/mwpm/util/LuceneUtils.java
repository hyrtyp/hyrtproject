package com.hyrt.mwpm.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
  
public class LuceneUtils {   
  
    //��ǰĿ¼λ��   
    public static final String USERDIR = System.getProperty("user.dir");   
    //���������Ŀ¼   
    private static final String INDEXPATH = USERDIR + File.separator + "index";   
    //����Դ   
    private static final String INDEXSOURCE = USERDIR + File.separator   
            + "source" + File.separator + "lucene.txt";   
    //ʹ�ð汾   
    public static final Version version = Version.LUCENE_35;   
       
    /**  
     * ��ȡ�ִ���  
     * */  
    public static Analyzer getAnalyzer(){   
        // �ִ���   
        Analyzer analyzer = new StandardAnalyzer(version);   
        return analyzer;   
    }   
  
    /**  
     * ����һ���������Ĳ�����  
     *   
     * @param openMode  
     * @return  
     * @throws Exception  
     */  
    public static IndexWriter createIndexWriter(OpenMode openMode)   
            {
    	IndexWriter writer = null;
    	try{
        // �������λ������   
        Directory dir = FSDirectory.open(new File(INDEXPATH));         
        // ��������������   
        IndexWriterConfig iwc = new IndexWriterConfig(version,   
                getAnalyzer());   
        iwc.setOpenMode(openMode);   
        //writer= new IndexWriter(dir, iwc);
        writer = new IndexWriter(dir,getAnalyzer(),true,(IndexWriter.MaxFieldLength.LIMITED));
            }catch(Exception e){
            	e.printStackTrace();
            }
        return writer;   
    }   
  
    /***  
     * ����һ��������������  
     * @throws IOException   
     * @throws CorruptIndexException   
     * */  
    public static IndexSearcher createIndexSearcher() throws CorruptIndexException, IOException {   
        IndexReader reader = IndexReader.open(FSDirectory.open(new File("F:/text")));   
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;   
    }   
  
    /**  
     * ����һ����ѯ��  
     * @param queryFileds  ����Щ�ֶ��Ͻ��в�ѯ  
     * @param queryString  ��ѯ����  
     * @return  
     * @throws ParseException  
     */  
    public static Query createQuery(String [] queryFileds,String queryString) throws ParseException{   
         QueryParser parser = new MultiFieldQueryParser(version, queryFileds, getAnalyzer());   
         Query query = parser.parse(queryString);   
         return query;   
    }   
       
    /***  
     * ��ȡ�ļ�����  
     * */  
    public static String readFileContext(File file){   
        try {   
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));   
            StringBuilder content = new StringBuilder();   
            for(String line = null; (line = br.readLine())!= null;){   
                content.append(line).append("\n");   
            }   
            return content.toString();   
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }   
       
    }   
       
       
    public static void main(String[] args) {   
  
        System.out.println(Thread.currentThread().getContextClassLoader()   
                .getResource(""));   
        System.out.println(LuceneUtils.class.getClassLoader().getResource(""));   
        System.out.println(ClassLoader.getSystemResource(""));   
        System.out.println(LuceneUtils.class.getResource(""));   
        System.out.println(LuceneUtils.class.getResource("/")); // Class�ļ�����·��   
        System.out.println(new File("/").getAbsolutePath());   
        System.out.println(System.getProperty("user.dir"));   
    }   
  
    /**  
     * ��������������Դ  
     *   
     * @return  
     */  
    public static File createSourceFile() {   
        File file = new File(INDEXSOURCE);   
        return file;   
    }   
}  
