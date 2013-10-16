package com.hyrt.cei.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hyrt.cei.vo.Patrol;
import com.hyrt.cei.vo.Task;
import com.hyrt.foshanLaw.e.Law;

public class XmlUtil {

	public static String parseReturnCode(String xml) {
		String returnCode = "";
		try {
			String tag_name = "";
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
					break;
				case XmlPullParser.TEXT:
					String someValue = parser.getText().trim();
					if (tag_name.equals("returncode")) {
						returnCode = someValue;
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnCode;
	}

	public static void parseCoursewares(String xml, List<Task> coursewares) {
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
					if (tag_name.equals("task")) {
						Task courseware = new Task();
						coursewares.add(courseware);
						i++;
					}
					break;
				case XmlPullParser.TEXT:
					String someValue = parser.getText().trim();
					if (tag_name.equals("id")) {
						coursewares.get(i).setId(someValue);
					}
					if (tag_name.equals("content")) {
						coursewares.get(i).setContent(someValue);
					}
					if (tag_name.equals("taskname")) {
						coursewares.get(i).setTaskname(someValue);
					}
					if (tag_name.equals("company")) {
						coursewares.get(i).setCompany(someValue);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void parsePatrols(String xml, List<Patrol> coursewares) {
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
					if (tag_name.equals("patrol")) {
						Patrol courseware = new Patrol();
						coursewares.add(courseware);
						i++;
					}
					break;
				case XmlPullParser.TEXT:
					String someValue = parser.getText().trim();
					if (tag_name.equals("registid")) {
						coursewares.get(i).setRegistId(someValue);
					}
					if (tag_name.equals("company")) {
						coursewares.get(i).setCompany(someValue);
					}
					if (tag_name.equals("contact")) {
						coursewares.get(i).setContact(someValue);
					}
					if (tag_name.equals("phone")) {
						coursewares.get(i).setPhone(someValue);
					}
					if (tag_name.equals("majorproject")) {
						coursewares.get(i).setMajorproject(someValue);
					}
					if (tag_name.equals("monitoringarea")) {
						coursewares.get(i).setMonitoringarea(someValue);
					}
					if (tag_name.equals("checktype")) {
						coursewares.get(i).setChecktype(someValue);
					}
					if (tag_name.equals("checkproject1")) {
						coursewares.get(i).setCheckproject1(someValue);
					}
					if (tag_name.equals("checkproject2")) {
						coursewares.get(i).setCheckproject2(someValue);
					}
					if (tag_name.equals("checkproject3")) {
						coursewares.get(i).setCheckproject3(someValue);
					}
					if (tag_name.equals("checkproject4")) {
						coursewares.get(i).setCheckproject4(someValue);
					}
					if (tag_name.equals("checkproject5")) {
						coursewares.get(i).setCheckproject5(someValue);
					}
					if (tag_name.equals("checkproject6")) {
						coursewares.get(i).setCheckproject6(someValue);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 解析法律
	public static List<Law> parseLaws(String str, List<Law> laws) {
		try {
			String tag_name = "";
			int i = laws.size() - 1;
			ByteArrayInputStream bis = new ByteArrayInputStream(
					str.getBytes("UTF-8"));
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
					if (tag_name.equals("law")) {
						Law law = new Law();
						laws.add(law);
						i++;
					}
					break;
				case XmlPullParser.TEXT:
					String someValue = parser.getText().trim();
					if (tag_name.equals("id")) {
						laws.get(i).setId(someValue);
					}
					if (tag_name.equals("dm")) {
						laws.get(i).setDm(someValue);
					}
					if (tag_name.equals("ms")) {
						laws.get(i).setMs(someValue);
					}
					if (tag_name.equals("bz")) {
						laws.get(i).setBz(someValue);
					}
					if (tag_name.equals("yxq")) {
						laws.get(i).setYxq(someValue);

					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return laws;

	}

}
