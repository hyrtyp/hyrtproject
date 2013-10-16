package com.hyrt.lcsis.webservice.service;

import java.util.List;

import com.hyrt.mwpm.vo.MwpmBussPatrolProof;


public interface LcsisUserInfoService {



	/**
	 * 新增
	 * 
	 * @author tanJie
	 */
	public String saveUser(String xml);
	
	/**
	 * 获取用户资料
	 */
	public String getUser(String xml);

	/**
	 * 修改经纬度
	 * 
	 * @author tanJie
	 */
	public String updateUser(String xml);

	/**
	 * 根据userid查询任务表
	 * @author tanJie
	 */
	public String queryTaskByUserid(String xml);
	/**
	 * 新增case表
	 * @author tanJie
	 */
	public String saveCase(String xml);
	
	/**
	 * 根据用户id和任务id改变task表的idreceive状态
	 * @author tanJie
	 */
	public String updateCaseByIsDreceive(String xml);

	
	/**
	 * 新增巡查记录
	 * @author tanJie
	 */
	public String savePatrol(String xml);
	
	/**
	 * 查询巡查记录
	 * @author tanJie
	 */
	public String queryPatrol(String xml);
	
	/**
	 * 分页查询企业列表
	 */
	public String queryEntInfos(String xml);
	
	/**
	 * 更新企业经纬度
	 */
	public String updateEntInfo(String xml);
	

	/**
	 * 查询法律法规
	 * @author Jh
	 */
	public String queryCodeFlag(String xml);
	/**
	 * 查询法律法规详细
	 * @author Jh
	 */
	public String queryCodeFlagByid(String xml);
	/**
	 * 用户信息反馈
	 * @author Jh
	 */
	public String saveUserFankui(String xml);
	/**
	 * 市场主体纠错信息录入
	 * @author Jh
	 */
	public String saveEntErrorinfo(String xml);
	/**
	 * 市场主体信息纠错查询
	 * @author Jh
	 */
	public String queryEntErrorList(String xml);
	/**
	 * 市场主体信息纠错详细
	 * @author Jh
	 */
	public String queryEntErrorInfo(String xml);
	
	/**
	 * 查询巡查任务列表
	 */
	public String queryPatrolTask(String xml);
	/**
	 * 查询巡查记录列表
	 */
	public String queryPatrolLOG(String xml);
	/**
	 * 插入巡查记录列表
	 */
	public String savePatrolLOG(String xml);
	/**
	 * 插入巡查事项
	 */
	public String savePatrolItem(String xml);
	/**
	 * 插入证据
	 */
	public String savePatrolProof(String xml);
	/**
	 * 查询以认领的企业列表
	 */
	public String queryPatrolENTINFO(String xml);
	/**
	 * 查询回查记录列表
	 */
	public String queryReturnLog(String xml);

	/**
	 * 通知公告发布权限判断
	 */
	/**
	 * 通知公告发布
	 */
	public String saveNotice(String xml);
	/**
	 * 通知公告查询
	 */
	public String queryNotice(String xml);
	/**
	 * 通知公告详细信息查询
	 */
	public String queryNoticeInfo(String xml);
	/**
	 * 无证无照登记
	 */
	public String saveNocard(String xml);
	/**
	 * 无证无照查询
	 */
	public String queryNocardList(String xml);
	/**
	 * 无证无照详细
	 */
	public String queryNocardInfo(String xml);

	/**
	 * 查询任务下对应的公司
	 * @param xml
	 * @return
	 */
	public String queryCompanyByPatrolId(String xml);
	
	/**
	 * 用户组列表
	 */
	public String queryUserGroup(String xml);
	
	/**
	 * 根据用户组id查询用户列表
	 */
	public String getUserListByGroupId(String xml);
	
	/**
	 * 根据用户id得到用户属性
	 */
	public String getUserListByuserId(String xml);
	/**
	 * 根据网格id得到用户属性
	 */
	public String getUserListByrId(String xml);

	/**
	 * 查询快到期的任务
	 * @param xml
	 * @return
	 */
	public String queryTimeTask(String xml);

	/**
	 * 查询公告的数量
	 */	
	public String queryNoticeNum(String xml);
	/**
	 * 巡查事项列表查询
	 */		
	public String queryMwpmBussPatrolItemList(String xml);
	/**
	 * 回查列表查询
	 */	
	public String queryReturnLogList(String xml);
	/**
	 * 回查录入
	 */	
	public String saveReturnLog(String xml);

	/**
	 * 录入证据
	 * @param proofs
	 * @throws Exception 
	 */
	public void saveMwpmBussPatrolProofs(List<MwpmBussPatrolProof> proofs) throws Exception;
	
	/**
	 * 判断当前企业是不是在本网格下
	 */
	public String getEntByReaId(String xml);
	/**
	 * 数据字典列表查询
	 */	
	public String queryDictionaryList(String xml);
	/**
	 * 专项任务查询
	 */	
	public String queryZxPatrolTask(String xml);
	/**
	 * 未巡查任务统计列表
	 */
	public String queryUncheckedPatrolReport(String xml);
	
	/**
	 * 查询范围内的企业
	 */
	public String queryEntByLatLong(String xml);
	
	/**
	 * 查询巡查轨迹
	 */
	public String queryPatrolLoca(String xml);
	/**
	 * 判断该用户是不是中队长以上级别
	 */
	public String getUserLevel(String xml);
	/**
	 * 企业邮箱补录
	 */
	public String updateEntEmail(String xml);
	/**
	 * 法律法规发送邮件企业列表
	 */
	public String queryEntListByemail(String xml);
	public String sendEmail(String xml);

	/**
	 * 查找普通用户
	 * @param xml
	 * @return
	 */
	public String getCommonUserList(String xml);
	/**
	 * 查找巡查类型
	 * @param xml
	 * @return
	 */
	public String queryPType(String xml);

	public String getPhoneIpadByUserId(String xml);

	public String savePhoneIpadByUserId(String xml);
	/**
	 * 巡查记录类型
	 * @param xml
	 * @return
	 */
	public String savePType(String xml);

	public String getRoleTypeByUserId(String xml);
	
	public String getReseaNameById(String reseaId);
	
}
