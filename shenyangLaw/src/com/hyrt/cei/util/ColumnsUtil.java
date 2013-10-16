package com.hyrt.cei.util;



import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hyrt.cei.vo.Column;
import com.hyrt.cei.vo.Table;

import android.content.Context;

public class ColumnsUtil {
	
	public Table[] parsePDM_VO(InputStream ips) {
		Table[] tabs = new Table[] {};
		List<Table> voS = new ArrayList<Table>();
		Table vo = null;
		Column[] cols = null;
		SAXReader sr = new SAXReader();
		Document doc = null;
		try {
			doc = sr.read(ips);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Iterator itr = doc.selectNodes("//c:Tables//o:Table").iterator();
		while (itr.hasNext()) {
			vo = new Table();
			cols = new Column[] {};
			List<Column> list = new ArrayList<Column>();
			Column col = null;
			Element e_table = (Element) itr.next();
			vo.setTableName(e_table.elementTextTrim("Name"));
			vo.setTableCode(e_table.elementTextTrim("Code"));
			Iterator itr1 = e_table.element("Columns").elements("Column").iterator();
			while (itr1.hasNext()) {
				try {
					col = new Column();
					Element e_col = (Element) itr1.next();
					String pkID = e_col.attributeValue("Id");
					col.setDefaultValue(e_col.elementTextTrim("DefaultValue"));
					col.setName(e_col.elementTextTrim("Name"));
					if(e_col.elementTextTrim("DataType").indexOf("(") >0){
						col.setType(e_col.elementTextTrim("DataType").substring(0, e_col.elementTextTrim("DataType").indexOf("(")));
					}else {
						col.setType(e_col.elementTextTrim("DataType"));
					}
					col.setCode(e_col.elementTextTrim("Code"));
					col.setLength(e_col.elementTextTrim("Length") == null ? 0 : Integer.parseInt(e_col.elementTextTrim("Length")));
					if(e_table.element("Keys")!=null){
						String keys_key_id = e_table.element("Keys").element("Key").attributeValue("Id");
						String keys_column_ref = e_table.element("Keys").element("Key").element("Key.Columns")
								.element("Column").attributeValue("Ref");
						String keys_primarykey_ref_id = e_table.element("PrimaryKey").element("Key").attributeValue("Ref");
						
							if (keys_primarykey_ref_id.equals(keys_key_id) && keys_column_ref.equals(pkID)) {
								col.setPkFlag(true);
								vo.setPkField(col.getCode());
							}
					
					}
					list.add(col);
					//System.out.println(col);
				} catch (Exception ex) {
					// col.setType(e_col.elementTextTrim("DataType"));
					ex.printStackTrace();
				}
			}
			vo.setCols(list.toArray(cols));
			voS.add(vo);
			//System.out.println(vo);
			//System.out.println("======================");
			//System.out.println();
		}
		return voS.toArray(tabs);
	}

	
    private static final String LL = "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself.";
    public String getSerial(String userId, String licenseNum) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(1, 3);
        cal.add(6, -1);
        java.text.NumberFormat nf = new java.text.DecimalFormat("000");
        licenseNum = nf.format(Integer.valueOf(licenseNum));
        String verTime = new StringBuilder("-").append(new java.text.
                SimpleDateFormat("yyMMdd").format(cal.getTime())).append("0").
                         toString();
        String type = "YE3MP-";
        String need = new StringBuilder(userId.substring(0, 1)).append(type).
                      append("300").append(licenseNum).append(verTime).toString();
        String dx = new StringBuilder(need).append(LL).append(userId).toString();
        int suf = this.decode(dx);
        String code = new StringBuilder(need).append(String.valueOf(suf)).
                      toString();
        return this.change(code);
    }

    private int decode(String s) {
        int i;
        char[] ac;
        int j;
        int k;
        i = 0;
        ac = s.toCharArray();
        j = 0;
        k = ac.length;
        while (j < k){
            i = (31 * i) + ac[j];
            j++;
        }
        return Math.abs(i);
    }

    private String change(String s){
        byte[] abyte0;
        char[] ac;
        int i;
        int k;
        int j;
        abyte0 = s.getBytes();
        ac = new char[s.length()];
        i = 0;
        k = abyte0.length;
        while (i < k) {
            j = abyte0[i];
            if ((j >= 48) && (j <= 57)) {
                j = (((j - 48) + 5) % 10) + 48;
            } else if ((j >= 65) && (j <= 90)) {
                j = (((j - 65) + 13) % 26) + 65;
            } else if ((j >= 97) && (j <= 122)) {
                j = (((j - 97) + 13) % 26) + 97;
            }
            ac[i] = (char) j;
            i++;
        }
        return String.valueOf(ac);
    }

    public ColumnsUtil() {
        super();
    }

    public static Column[] getColumnsByTableCode(Context context,String tableCode){
    	try {
	    	Table[] tables = new ColumnsUtil().parsePDM_VO(context.getAssets().open("table_column.pdm"));
	    	for(int i=0;i<tables.length;i++){
	    		if((tableCode).equals(tables[i].getTableCode())){
	    			 Column[] columns =tables[i].getCols();
	    			 return columns;
	    		}
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return null;
    }
}