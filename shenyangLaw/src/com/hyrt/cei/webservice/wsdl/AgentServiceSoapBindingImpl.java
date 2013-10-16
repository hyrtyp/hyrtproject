package com.hyrt.cei.webservice.wsdl;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public final class AgentServiceSoapBindingImpl {

	public String updateUser(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "updateUser");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryTaskByUserid(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryTaskByUserid");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String updateCaseByIsDreceive(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "updateCaseByIsDreceive");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String saveCase(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveCase");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String savePatrol(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "savePatrol");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryPatrol(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryPatrol");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryEntInfos(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryEntInfos");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String updateEntInfo(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "updateEntInfo");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String getUser(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "getUser");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 分页查看认领企业列表
	 */
	public String queryPatrolENTINFO(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryPatrolENTINFO");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	// 法律法规，办案工作指引，巡查工作指引
	public String queryCodeFlag(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryCodeFlag");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryPatrolLOG(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryPatrolLOG");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	// 法律法规详细,办案工作指引详细，巡查工作指引详细
	public String queryCodeFlagByid(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryCodeFlagByid");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();

	}

	/**
	 * 用户信息反馈
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public String saveUserFankui(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveUserFankui");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();

	}

	/**
	 * 市场主体信息纠错
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public String saveEntErrorinfo(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveEntErrorinfo");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 巡查任务
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public String queryPatrolTask(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryPatrolTask");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String savePatrolLOG(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "savePatrolLOG");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	// 巡查任务对应公司
	public String queryCompanyByPatrolId(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryCompanyByPatrolId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	// 查询是否有快到期的任务
	public String queryTimeTask(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryTimeTask");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 查询公告的数量
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public String queryNoticeCount(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryNoticeNum");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 查询公告列表
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public String queryNotice(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryNotice");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 公告列表查询,详细查询
	 */
	public String queryNoticeInfo(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryNoticeInfo");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();

	}

	/**
	 * 公告发布
	 */
	public String saveNotice(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveNotice");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();

	}

	public String savePatrolItem(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "savePatrolItem");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryNoCards(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryNocardList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String getEntByReaId(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "getEntByReaId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryMwpmBussPatrolItemList(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryMwpmBussPatrolItemList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 查询回查记录列表
	 */
	public String queryReturnLogList(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryReturnLogList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String saveReturnLog(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveReturnLog");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String getUserListByrId(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "getUserListByrId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryEntErrorList(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryEntErrorList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String saveNocard(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "saveNocard");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryZxPatrolTask(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryZxPatrolTask");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 获取用户权限级别
	 */
	public String getUserLevel(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "getUserLevel");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}
	
	public String getCommonUserList(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "getCommonUserList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String updateEntEmail(String rs) throws Exception{
		SoapObject _client = new SoapObject("", "updateEntEmail");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 获取企业列表进行邮件发送 queryEntListByemail
	 */
	public String queryEntListByemail(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryEntListByemail");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	/**
	 * 发送邮件 sendEmail
	 */
	public String sendEmail(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "sendEmail");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}


	public String queryPatrolLoca(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryPatrolLoca");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryDictionaryList(String rs) throws Exception {
		SoapObject _client = new SoapObject("", "queryDictionaryList");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String queryPType(String rs) throws Exception{
		SoapObject _client = new SoapObject("", "queryPType");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}
	public String savePhoneIpadByUserId(String rs)  throws Exception{
		SoapObject _client = new SoapObject("", "savePhoneIpadByUserId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}
	public String getPhoneIpadByUserId(String rs)  throws Exception{
		SoapObject _client = new SoapObject("", "getPhoneIpadByUserId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}
	
	public String getRoleTypeByUserId(String rs)  throws Exception{
		SoapObject _client = new SoapObject("", "getRoleTypeByUserId");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String savePType(String rs) throws Exception{
		SoapObject _client = new SoapObject("", "savePType");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	
	public String queryEntByLatLong(String rs) throws Exception{
		SoapObject _client = new SoapObject("", "queryEntByLatLong");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

	public String getReseaNameById(String rs) throws Exception{
		SoapObject _client = new SoapObject("", "getReseaNameById");
		SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		_envelope.bodyOut = _client;
		_client.addProperty("xmlStr", rs);
		MyAndroidHttpTransport _ht = new MyAndroidHttpTransport(
				Configuration.getWsUrl());
		_ht.call("", _envelope);
		return (java.lang.String) _envelope.getResponse();
	}

}