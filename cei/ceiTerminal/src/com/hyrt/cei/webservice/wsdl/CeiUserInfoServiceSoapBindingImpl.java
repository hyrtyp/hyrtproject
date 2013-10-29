package com.hyrt.cei.webservice.wsdl;

import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import com.hyrt.cei.webservice.service.CeiUserInfoService;

@SuppressWarnings("serial")
public class CeiUserInfoServiceSoapBindingImpl extends ServletEndpointSupport
		implements CeiUserInfoService, java.io.Serializable {

	private CeiUserInfoService ceiUserInfoService;

	@Override
	protected void onInit() throws ServiceException {

		ceiUserInfoService = (CeiUserInfoService) getApplicationContext()
				.getBean("ceiUserInfoService");
	}

	@Override
	public String queryXfgwLists(String xml) {
		return null;
	}

	@Override
	public String loginUserInfo(String xml) {
		return ceiUserInfoService.loginUserInfo(xml);
	}

	@Override
	public String queryUserInfo(String xml) {
		return ceiUserInfoService.queryUserInfo(xml);
	}

	@Override
	public String queryBrightness(String xml) {
		return ceiUserInfoService.queryBrightness(xml);
	}

	@Override
	public String queryPhoneFunctionTree(String xml) {
		return ceiUserInfoService.queryPhoneFunctionTree(xml);
	}

	@Override
	public String saveUserClassTime(String xml) {
		return ceiUserInfoService.saveUserClassTime(xml);
	}

	@Override
	public String queryUserClassTime(String xml) {
		return ceiUserInfoService.queryUserClassTime(xml);
	}


	@Override
	public String queryCourse(String xml) {
		return ceiUserInfoService.queryCourse(xml);
	}

	@Override
	public String saveCourse(String xml) {
		return ceiUserInfoService.saveCourse(xml);
	}

	@Override
	public String queryBusiness(String xml) {
		return ceiUserInfoService.queryBusiness(xml);
	}

	@Override
	public String queryBBSMotifList(String xml) {
		return ceiUserInfoService.queryBBSMotifList(xml);
	}

	@Override
	public String delBusiness(String xml) {
		return ceiUserInfoService.delBusiness(xml);
	}

	@Override
	public String saveBBSFollow(String xml) {
		return ceiUserInfoService.saveBBSFollow(xml);
	}

	@Override
	public String quitUserInfo(String xml) {
		return ceiUserInfoService.quitUserInfo(xml);
	}

	@Override
	public String saveOperationlog(String xml) {
		return ceiUserInfoService.saveOperationlog(xml);
	}

	@Override
	public String queryBBSFollow(String xml) {
		return ceiUserInfoService.queryBBSFollow(xml);
	}

	@Override
	public String saveUserInfo(String xml) {
		return ceiUserInfoService.saveUserInfo(xml);
	}

	@Override
	public String updateUserInfoPassWord(String xml) {
		return ceiUserInfoService.updateUserInfoPassWord(xml);
	}

	@Override
	public String updatePassWord(String xml) {
		return ceiUserInfoService.updatePassWord(xml);
	}

	@Override
	public String queryClassByName(String xml) {
		return ceiUserInfoService.queryClassByName(xml);
	}

	@Override
	public String queryClassByType(String xml) {
		return ceiUserInfoService.queryClassByType(xml);
	}

	@Override
	public String queryClassTypeByClass(String xml) {
		return ceiUserInfoService.queryClassTypeByClass(xml);
	}

	@Override
	public String queryReportByType(String xml) {
		return ceiUserInfoService.queryReportByType(xml);
	}

	@Override
	public String queryReportTypeByReport(String xml) {
		return ceiUserInfoService.queryReportTypeByReport(xml);
	}

	@Override
	public String addClassTime(String xml) {
		return ceiUserInfoService.addClassTime(xml);
	}

	@Override
	public String queryClassTime(String xml) {
		return ceiUserInfoService.queryClassTime(xml);
	}

	@Override
	public String queryMoneyClass(String xml) {
		return ceiUserInfoService.queryMoneyClass(xml);
	}

	@Override
	public String queryNewsImage(String xml) {
		return ceiUserInfoService.queryNewsImage(xml);
	}

	@Override
	public String queryNewsList(String xml) {
		return ceiUserInfoService.queryNewsList(xml);
	}

	@Override
	public String queryNewsByFunctionId(String xml) {
		return ceiUserInfoService.queryNewsByFunctionId(xml);
	}

	@Override
	public String queryNewsByName(String xml) {
		return ceiUserInfoService.queryNewsByName(xml);
	}

	@Override
	public String saveCoolect(String xml) {
		return ceiUserInfoService.saveCoolect(xml);
	}

	@Override
	public String queryCollect(String xml) {
		return ceiUserInfoService.queryCollect(xml);
	}

	@Override
	public String delCollect(String xml) {
		return ceiUserInfoService.delCollect(xml);
	}

	@Override
	public String queryClassAndReport(String xml) {
		return ceiUserInfoService.queryClassAndReport(xml);
	}

	@Override
	public String queryReportByName(String xml) {
		return ceiUserInfoService.queryReportByName(xml);
	}
	/**
	 * 清空资讯收藏
	 * @author tanJie
	 */
	public String clearCollect(String xml){
		return ceiUserInfoService.clearCollect(xml);
	}
	/*
	 * 显示全部的免费报告
	 * */
	public String queryAllFreeReport(String xml){
		return ceiUserInfoService.queryAllFreeReport(xml);
	}
	/*
	 * 取得某一分类下的所有报告列表
	 * */
	public String queryAllClassTypeReport(String xml){
		return ceiUserInfoService.queryAllClassTypeReport(xml);
	}

	@Override
	public String addFunctionTree(String xml) {
		return ceiUserInfoService.addFunctionTree(xml);
	}

	@Override
	public String queryClassByTime(String xml) {
		return ceiUserInfoService.queryClassByTime(xml);
	}

	@Override
	public String queryBuyNews(String xml) {
		return ceiUserInfoService.queryBuyNews(xml);
	}

	@Override
	public String queryNotice(String xml) {
		return ceiUserInfoService.queryNotice(xml);
	}

	@Override
	public String queryPassKey(String xml) {
		return ceiUserInfoService.queryPassKey(xml);
	}

	@Override
	public String queryMoneyReport(String xml) {
		return ceiUserInfoService.queryMoneyReport(xml);
	}

	@Override
	public String queryPassKeyByReport(String xml) {
		return ceiUserInfoService.queryPassKeyByReport(xml);
	}

	@Override
	public String updatedownsum(String xml) {
		return ceiUserInfoService.updatedownsum(xml);
	}

	@Override
	public String queryReportName(String xml) {
		return ceiUserInfoService.queryReportName(xml);
	}

	@Override
	public String queryClassName(String xml) {
		return ceiUserInfoService.queryClassName(xml);
	}

	@Override
	public String queryApkList(String xml) {
		return ceiUserInfoService.queryApkList(xml);
	}

	@Override
	public String queryFunctionAddress(String xml) {
		return ceiUserInfoService.queryFunctionAddress(xml);
	}
}
