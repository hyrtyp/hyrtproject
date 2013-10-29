package com.hyrt.mwpm.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.cyberneko.html.parsers.DOMFragmentParser;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.html.dom.HTMLDocumentImpl;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class NekoHtmlUtil {
	
	public static void main(String[] args) {
		String content = "<p><style type='text/css'>"
			+ "body{ font-size:12px; }"
			+ "li{ list-style:none;}"
			+ ".title{ margin:10px auto; color: #FF0000; text-align:center; font-size:18px; font-    weight:bold;}"
			+ ".paper1  { width:95%;color:#000000;background:#FF0000; margin-bottom:5px; }"
			+ ".w1{ width:200px; float:left;}"
			+ ".w2{float:right; margin-right:10px; margin-bottom:5px; }"
			+ ".paper1  td{ padding:5px 5px 5px 15px;  background:#FFFFFF; font-size:12px;}"
			+ ".paper1 label{color:#ff0000; clear:both; text-align:left; }"
			+ ".paper input{ clear:both; width:95%; background-color: #f0f0f0;}"
			+ ".paper1 textarea{  clear:both; margin:2px 0 2px 0px; padding:4px;"
			+ "height:52px;background-color: #f0f0f0;}  .paper1 select{ clear:both; width:90%; }"
			+ ".paper1 ul { float:left; width:95%; margin-top:5px;margin-left:5px;}"
			+ ".paper1 ul li { float:left; width:95%; line-height:18px;word-break:break-all;word-    warp:warp;}</style>"
			+ "</p>  <div class='title'>文 件 处 理 单</div>  <table class='paper1' cellspacing='1' cellpadding='8' align='center'>"
			+ "<tbody>"
			+ "<tr>              <td width='34%'><label for='Intervolve'>&nbsp;卷&nbsp;&nbsp;号 ：</label> 十二</td>"
			+ " <td width='33%'>&nbsp;</td>              <td width='33%'><label for='SerialNumber'>编&nbsp;&nbsp;号 ：</label> 965</td>"
			+ "</tr>          <tr>"
			+ "<td><label for='Date'>收文日期：</label>2011-8-10</td>"
			+ "<td rowspan='2'><label for='OriginGov'>来文机关：</label><br />"
			+ "市委保密委员会办公室、市保密局</td>"
			+ "<td rowspan='2'><label for='Number'>来文文号：</label><br />"
			+ "淄密办（局）发〔2011〕2号</td>          </tr>          <tr>"
			+ "<td><label for='Number'>&nbsp;份&nbsp;&nbsp; 数 ： </label>1</td>"
			+ "</tr>          <tr>              <td colspan='2'><label for='Title'>文件名称：</label><br />"
			+ "关于印发《淄博市保密工作目标管理及考核办法（试行）》的通知</td>              <td><label for='Annex'>附件：</label><br />  <a href='http://www.baidu.com'>www.baidu.com</a>   </td>"
			+ "</tr>          <tr>            fdgrsd  <td colspan='2'><label for='Overmarginalia'>拟办意见：</label><br />"
			+ "<ul><li>请刘局长、栾局长阅。请办公室（政务）、信息中心阅处。<br/><b>时间：</b>2011-8-10 15:08:33<b>拟办人：</b>王爱东</li></ul></td>"
			+ "<td><label for='Speed'>紧急程度：</label><br />              普通</td>          </tr>          <tr>"
			+ "<td colspan='3'><label for='Offices'>科室意见：</label><br />              <ul></ul></td>          </tr>          <tr>"
			+ "<td colspan='3'><label for='Boss'>领导批示：</label><br />              <ul></ul></td>          </tr>          <tr>"
			+ "<td colspan='3'><label for='Signature'>阅后签名：</label><br />"
			+ "<ul><li><b>姓名：</b>王爱东<b>   时间：</b>2011-8-10 15:08:33  </li><li><b>姓名：</b>刘波<b>   时间：</b>2011-8-11 8:48:30  </li>"
			+ "<li><b>姓名：</b>栾召金<b>   时间：</b>2011-8-11 16:34:52  </li></ul></td>          </tr>"
			+ "<tr>              <td colspan='4'><label>反馈类型：</label><br />              #ReplyExpect#</td>          </tr>"
			+ "<tr>              <td colspan='4'><label for='Reply'>办理结果：</label><br />              #Reply#</td>          </tr>"
			+ "</tbody>" + "</table>";
		try {
			extractTextFromHTML(content,new LinkedMap());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String extractTextFromHTML(String content,Map kvMap)
			throws UnsupportedEncodingException {
		DOMFragmentParser parser = new DOMFragmentParser();
		DocumentFragment node = new HTMLDocumentImpl().createDocumentFragment();
		InputStream is = new ByteArrayInputStream(content.getBytes("gbk"));
		try {
			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					"gbk");
			parser.parse(new InputSource(is), node);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		}

		StringBuffer newContent = new StringBuffer();
		getText(newContent, node,kvMap);
		return (new String(newContent.toString().getBytes("gbk"), "gbk")
				.toString());
	}

	private static void getText(StringBuffer sb, Node node,Map kvMap) {
		if (node.getNodeName().equals("STYLE")
				|| node.getNodeName().equals("SCRIPT"))
			sb.append("");
		else {
			if (node.getNodeName().equals("TBODY")) {
				NodeList children = node.getChildNodes();
				int len = children.getLength();
				for (int i = 0; i < len; i++) {
					if (children.item(i).getNodeName().equals("TR")) {
						String key = "";
						String value = "";
						NodeList trChildren = children.item(i).getChildNodes();
						for (int j = 0; j < trChildren.getLength(); j++) {
							if (trChildren.item(j).getNodeName().equals("TD")
									&& trChildren.item(j).getChildNodes()
											.getLength() != 0) {
								NodeList tdChildren = trChildren.item(j)
										.getChildNodes();
								for (int g = 0; g < tdChildren.getLength(); g++) {
									if (tdChildren.item(g).getNodeName()
											.equals("LABEL")) {
									
										NodeList lbChildren = tdChildren
												.item(g).getChildNodes();
										for (int x = 0; x < lbChildren
												.getLength(); x++) {
											key = lbChildren.item(x)
													.getNodeValue();
										}
									} else if (tdChildren.item(g).getNodeType() == Node.TEXT_NODE) {
									
										value = tdChildren.item(g)
												.getNodeValue();
									} else if (tdChildren.item(g).getNodeName()
											.equals("UL")) {
									
										NodeList ulChildren = tdChildren
												.item(g).getChildNodes();
										for (int x = 0; x < ulChildren
												.getLength(); x++) {
											NodeList liChildren = ulChildren
													.item(x).getChildNodes();

											for (int y = 0; y < liChildren
													.getLength(); y++) {
												if (liChildren.item(y)
														.getNodeType() == Node.TEXT_NODE
														|| liChildren.item(y)
																.getNodeName()
																.equals("B")) {
													if (liChildren.item(y)
															.getNodeName()
															.equals("B")) {
														if (liChildren
																.item(y)
																.getChildNodes()
																.item(0)
																.getNodeValue() != null)
															value += liChildren
																	.item(y)
																	.getChildNodes()
																	.item(0)
																	.getNodeValue();
													}
													if (liChildren.item(y)
															.getNodeValue() != null)
														value += liChildren
																.item(y)
																.getNodeValue();
												}
											}
										}
									}
								}		
							}
						}
						if(!key.equals(""))
							kvMap.put(key, value);
					}
				}
			}
			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++) {
					getText(sb, children.item(i),kvMap);
				}
			}
		}
	}

}
