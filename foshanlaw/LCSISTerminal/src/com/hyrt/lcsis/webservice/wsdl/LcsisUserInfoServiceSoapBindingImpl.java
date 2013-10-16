package com.hyrt.lcsis.webservice.wsdl;

import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import com.hyrt.lcsis.webservice.service.LcsisUserInfoService;
import com.hyrt.mwpm.vo.MwpmBussPatrolProof;

@SuppressWarnings("serial")
public class LcsisUserInfoServiceSoapBindingImpl extends ServletEndpointSupport
		implements LcsisUserInfoService, java.io.Serializable {

	private LcsisUserInfoService ceiUserInfoService;

	@Override
	protected void onInit() throws ServiceException {

		ceiUserInfoService = (LcsisUserInfoService) getApplicationContext()
				.getBean("ceiUserInfoService");
	}




	@Override
	public String saveUser(String xml) {
		return ceiUserInfoService.saveUser(xml);
	}




	@Override
	public String updateUser(String xml) {
		return ceiUserInfoService.updateUser(xml);
	}




	@Override
	public String queryTaskByUserid(String xml) {
		return ceiUserInfoService.queryTaskByUserid(xml);
	}




	@Override
	public String saveCase(String xml) {
		return ceiUserInfoService.saveCase(xml);
	}




	@Override
	public String updateCaseByIsDreceive(String xml) {
		return ceiUserInfoService.updateCaseByIsDreceive(xml);
	}




	@Override
	public String savePatrol(String xml) {
		return ceiUserInfoService.savePatrol(xml);
	}




	@Override
	public String queryPatrol(String xml) {
		return ceiUserInfoService.queryPatrol(xml);
	}


	/**
	 * 分页查询企业列表
	 */
	@Override
	public String queryEntInfos(String xml) {
		return ceiUserInfoService.queryEntInfos(xml);
	}

	@Override
	public String queryCodeFlag(String xml) {
		return ceiUserInfoService.queryCodeFlag(xml);
	}



	/**
	 * 更新企业经纬度
	 */
	@Override
	public String updateEntInfo(String xml) {
		return ceiUserInfoService.updateEntInfo(xml);
	}



	/**
	 * 查询巡查任务列表
	 */
	@Override
	public String queryPatrolTask(String xml) {
		// TODO Auto-generated method stub
		return ceiUserInfoService.queryPatrolTask(xml);
	}
	/**
	 * 查询巡查记录列表
	 */
	@Override
	public String queryPatrolLOG(String xml) {
		// TODO Auto-generated method stub
		return ceiUserInfoService.queryPatrolLOG(xml);
	}
	
	@Override
	public String queryCodeFlagByid(String xml) {
		return ceiUserInfoService.queryCodeFlagByid(xml);
	}




	@Override
	public String saveUserFankui(String xml) {
		return ceiUserInfoService.saveUserFankui(xml);
	}



	@Override
	public String saveEntErrorinfo(String xml) {
		return ceiUserInfoService.saveEntErrorinfo(xml);
	}


	@Override
	public String getUser(String xml) {
		return ceiUserInfoService.getUser(xml);
	}
	
	@Override
	public String queryEntErrorList(String xml) {
		return ceiUserInfoService.queryEntErrorList(xml);
	}

	@Override
	public String queryEntErrorInfo(String xml) {
		return ceiUserInfoService.queryEntErrorInfo(xml);
	}

	/**
	 * 查询以认领的企业列表
	 */


	@Override
	public String queryPatrolENTINFO(String xml) {
		return ceiUserInfoService.queryPatrolENTINFO(xml);
	}




	@Override
	public String saveNotice(String xml) {
		return ceiUserInfoService.saveNotice(xml);
	}




	@Override
	public String queryNotice(String xml) {
		return ceiUserInfoService.queryNotice(xml);
	}




	@Override
	public String queryNoticeInfo(String xml) {
		return ceiUserInfoService.queryNoticeInfo(xml);
	}


	/**
	 * 查询回查记录列表
	 */
	@Override
	public String queryReturnLog(String xml) {
		return ceiUserInfoService.queryReturnLog(xml);
	}


	/**
	 * 插入巡查记录列表
	 */

	@Override
	public String savePatrolLOG(String xml) {
		return ceiUserInfoService.savePatrolLOG(xml);
	}





	@Override
	public String saveNocard(String xml) {
		return ceiUserInfoService.saveNocard(xml);
	}




	@Override
	public String queryNocardList(String xml) {
		return ceiUserInfoService.queryNocardList(xml);
	}
	/**
	 * 插入巡查事项
	 */

	@Override
	public String savePatrolItem(String xml) {
		return ceiUserInfoService.savePatrolItem(xml);
	}

	/**
	 * 插入证据
	 */
	@Override
	public String savePatrolProof(String xml) {
		return ceiUserInfoService.savePatrolProof(xml);
	}

	@Override
	public String queryNocardInfo(String xml) {
		return ceiUserInfoService.queryNocardInfo(xml);
	}


	@Override
	public String queryCompanyByPatrolId(String xml) {
		// TODO Auto-generated method stub
		return ceiUserInfoService.queryCompanyByPatrolId(xml);
	}


	@Override
	public String queryUserGroup(String xml) {
		return ceiUserInfoService.queryUserGroup(xml);
	}




	@Override
	public String getUserListByGroupId(String xml) {
		return ceiUserInfoService.getUserListByGroupId(xml);
	}




	@Override
	public String getUserListByuserId(String xml) {
		return ceiUserInfoService.getUserListByuserId(xml);
	}




	@Override
	public String queryTimeTask(String xml) {
		return ceiUserInfoService.queryTimeTask(xml);
	}

	/**
	 * 查询公告的数量
	 */
	@Override
	public String queryNoticeNum(String xml) {
		// TODO Auto-generated method stub
		return ceiUserInfoService.queryNoticeNum(xml);
	}




	@Override
	public String queryReturnLogList(String xml) {
		return ceiUserInfoService.queryReturnLogList(xml);
	}




	@Override
	public String saveReturnLog(String xml) {
		return ceiUserInfoService.saveReturnLog(xml);
	}




	@Override
	public String queryMwpmBussPatrolItemList(String xml) {
		return ceiUserInfoService.queryMwpmBussPatrolItemList(xml);
	}




	@Override
	public void saveMwpmBussPatrolProofs(List<MwpmBussPatrolProof> proofs)
			throws Exception {
		
	}




	@Override
	public String getEntByReaId(String xml) {
		return ceiUserInfoService.getEntByReaId(xml);
	}




	@Override
	public String getUserListByrId(String xml) {
		// TODO Auto-generated method stub
		return ceiUserInfoService.getUserListByrId(xml);
	}




	@Override
	public String queryDictionaryList(String xml) {
		return ceiUserInfoService.queryDictionaryList(xml);
	}




	@Override
	public String queryZxPatrolTask(String xml) {
		return ceiUserInfoService.queryZxPatrolTask(xml);
	}




	@Override
	public String queryUncheckedPatrolReport(String xml) {
		return ceiUserInfoService.queryUncheckedPatrolReport(xml);
	}


	@Override
	public String queryEntByLatLong(String xml) {
		return ceiUserInfoService.queryEntByLatLong(xml);
	}




	@Override
	public String queryPatrolLoca(String xml) {
		return ceiUserInfoService.queryPatrolLoca(xml);
	}




	@Override
	public String getUserLevel(String xml) {
		return ceiUserInfoService.getUserLevel(xml);
	}




	@Override
	public String updateEntEmail(String xml) {
		return ceiUserInfoService.updateEntEmail(xml);
	}
	
	
	@Override
	public String queryEntListByemail(String xml) {
		return ceiUserInfoService.queryEntListByemail(xml);
	}


	@Override
	public String sendEmail(String xml) {
		return ceiUserInfoService.sendEmail(xml);
	}




	@Override
	public String getCommonUserList(String xml) {
		return ceiUserInfoService.getCommonUserList(xml);
	}




	@Override
	public String queryPType(String xml) {
		return ceiUserInfoService.queryPType(xml);
	}




	@Override
	public String getPhoneIpadByUserId(String xml) {
		return ceiUserInfoService.getPhoneIpadByUserId(xml);
	}




	@Override
	public String savePhoneIpadByUserId(String xml) {
		return ceiUserInfoService.savePhoneIpadByUserId(xml);
	}




	@Override
	public String savePType(String xml) {
		return ceiUserInfoService.savePType(xml);
	}




	@Override
	public String getRoleTypeByUserId(String xml) {
		return ceiUserInfoService.getRoleTypeByUserId(xml);
	}




	@Override
	public String getReseaNameById(String reseaId) {
		return ceiUserInfoService.getReseaNameById(reseaId);
	}
	
}
