package com.hyrt.mwpm.util;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class PrincipalUtil {
	
	public static String ParseXMLOutputter(org.jdom.Document errorDocument) {
		String outString = "";
		Format format = Format.getCompactFormat();
		format.setEncoding("UTF-8");
		format.setIndent("    ");
		XMLOutputter xmlOut = new XMLOutputter(format);
		outString = xmlOut.outputString(errorDocument);

		return outString;
	}
}
