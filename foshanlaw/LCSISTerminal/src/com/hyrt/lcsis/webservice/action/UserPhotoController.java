package com.hyrt.lcsis.webservice.action;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hyrt.lcsis.webservice.service.LcsisUserInfoService;
import com.hyrt.mwpm.util.XmlUtil;
import com.hyrt.mwpm.vo.MwpmBussPatrolProof;

@Controller
@RequestMapping("/photo")
public class UserPhotoController {

	@Autowired
	private LcsisUserInfoService ceiUserInfoService;

	public void setCeiUserInfoService(LcsisUserInfoService ceiUserInfoService) {
		this.ceiUserInfoService = ceiUserInfoService;
	}

	@RequestMapping("/uploadPicture")
	public String uploadPicture(HttpServletRequest request, ModelMap map) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> fileNameIterator = multipartRequest.getFileNames();
		List<MwpmBussPatrolProof> proofs = new ArrayList<MwpmBussPatrolProof>();
		String xmlParam = request.getParameter("xmlStr");
		try {
			String des = new String(xmlParam.getBytes("UTF-8"),"UTF-8");
			xmlParam = des;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XmlUtil.parseCoursewares(xmlParam, proofs);
		int i = 0;
		try {
			while (fileNameIterator.hasNext()) {
				String fileName = fileNameIterator.next();
				MultipartFile multipartFile = multipartRequest.getFile(fileName);
				String saveFileName = writeFile(multipartFile, request);
				proofs.get(i).setPath("/proofPic/"+saveFileName);
				i++;
			}
			ceiUserInfoService.saveMwpmBussPatrolProofs(proofs);
		} catch (Exception e) {
			e.printStackTrace();
			return "/status/fail_phone.html";
		}
		return "/status/success_phone.html";
	}

	public String writeFile(MultipartFile multipartFile,
			HttpServletRequest request) throws Exception {
		String fileName = System.currentTimeMillis() + "";
		//String filePath = request.getRealPath("/") + "proofPic/" + fileName;
		String filePath = "D:/apache-tomcat-7.0.41/webapps/LCSIS/proofPic/" + "proofPic/" + fileName;
		byte[] bytes = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(bytes);
		fos.close();
		return fileName;
	}
}
