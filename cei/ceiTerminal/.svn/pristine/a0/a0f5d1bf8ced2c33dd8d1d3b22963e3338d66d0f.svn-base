package test;



import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FormatXMLStr{
   
    public static String format(String str) throws Exception {

        StringReader in=null;
        StringWriter out=null;
        try{
            SAXReader reader=new SAXReader();
            //创建一个串的字符输入流
            in=new StringReader(str);
            Document doc=reader.read(in);
            //创建输出格式
            OutputFormat formate=OutputFormat.createPrettyPrint();
            //创建输出
            out=new StringWriter();
            //创建输出流
            XMLWriter writer=new XMLWriter(out,formate);
            //输出格式化的串到目标中,格式化后的串保存在out中。
            writer.write(doc);
        } catch (IOException ioe){
            throw new Exception("对xml字符串进行格式化时产生IOException异常",ioe);    
        } catch (DocumentException de){
            throw new Exception("对xml字符串进行格式化时产生DocumentException异常",de);
        } finally{
            //关闭流
            quietClose(in);
            quietClose(out);
        }
        return out.toString();
      }    
    
   
    public static void quietClose(Reader reader){
        try{
            if(reader!=null){
                reader.close();
            }
        } catch(IOException e){
        	 e.printStackTrace();  
        }

    }

   
    public static void quietClose(Writer writer){
        try{
            if(writer!=null){
                writer.close();
            }
        } catch(IOException e){
           e.printStackTrace();
        }
    } 
}