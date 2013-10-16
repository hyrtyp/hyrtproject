package com.hyrt.mwpm.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hyrt.mwpm.vo.MwpmBussPatrolProof;

public class XmlUtil {

	public static void parseCoursewares(String xml, List<MwpmBussPatrolProof> coursewares) {
		try {
			String tag_name = "";
			int i = coursewares.size() - 1;
			ByteArrayInputStream bis = new ByteArrayInputStream(
					xml.getBytes("UTF-8"));
			KXmlParser parser = new KXmlParser();
			parser.setInput(bis, "UTF-8");
			while (parser.next() > 1) {
				int eventType = parser.getEventType();
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				case XmlPullParser.END_TAG:
					tag_name = "";
					break;
				case XmlPullParser.START_TAG:
					tag_name = parser.getName().toLowerCase().trim();
					if (tag_name.equals("item")) {
						MwpmBussPatrolProof courseware = new MwpmBussPatrolProof();
						coursewares.add(courseware);
						i++;
					}
					break;
				case XmlPullParser.TEXT:
					String someValue = parser.getText().trim();
					if (tag_name.equals("lid")) {
						coursewares.get(i).setLid(someValue);
					}
					if (tag_name.equals("path")) {
						coursewares.get(i).setPath(someValue);
					}
					if (tag_name.equals("userid")) {
						coursewares.get(i).setUserid(someValue);
					}
					if (tag_name.equals("clock")) {
						coursewares.get(i).setClock(new Date());
					}
					if (tag_name.equals("remark")) {
						coursewares.get(i).setRemark(someValue);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
