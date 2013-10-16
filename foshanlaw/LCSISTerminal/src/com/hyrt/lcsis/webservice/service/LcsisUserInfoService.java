package com.hyrt.lcsis.webservice.service;

import java.util.List;

import com.hyrt.mwpm.vo.MwpmBussPatrolProof;


public interface LcsisUserInfoService {



	/**
	 * ����
	 * 
	 * @author tanJie
	 */
	public String saveUser(String xml);
	
	/**
	 * ��ȡ�û�����
	 */
	public String getUser(String xml);

	/**
	 * �޸ľ�γ��
	 * 
	 * @author tanJie
	 */
	public String updateUser(String xml);

	/**
	 * ����userid��ѯ�����
	 * @author tanJie
	 */
	public String queryTaskByUserid(String xml);
	/**
	 * ����case��
	 * @author tanJie
	 */
	public String saveCase(String xml);
	
	/**
	 * �����û�id������id�ı�task���idreceive״̬
	 * @author tanJie
	 */
	public String updateCaseByIsDreceive(String xml);

	
	/**
	 * ����Ѳ���¼
	 * @author tanJie
	 */
	public String savePatrol(String xml);
	
	/**
	 * ��ѯѲ���¼
	 * @author tanJie
	 */
	public String queryPatrol(String xml);
	
	/**
	 * ��ҳ��ѯ��ҵ�б�
	 */
	public String queryEntInfos(String xml);
	
	/**
	 * ������ҵ��γ��
	 */
	public String updateEntInfo(String xml);
	

	/**
	 * ��ѯ���ɷ���
	 * @author Jh
	 */
	public String queryCodeFlag(String xml);
	/**
	 * ��ѯ���ɷ�����ϸ
	 * @author Jh
	 */
	public String queryCodeFlagByid(String xml);
	/**
	 * �û���Ϣ����
	 * @author Jh
	 */
	public String saveUserFankui(String xml);
	/**
	 * �г����������Ϣ¼��
	 * @author Jh
	 */
	public String saveEntErrorinfo(String xml);
	/**
	 * �г�������Ϣ�����ѯ
	 * @author Jh
	 */
	public String queryEntErrorList(String xml);
	/**
	 * �г�������Ϣ������ϸ
	 * @author Jh
	 */
	public String queryEntErrorInfo(String xml);
	
	/**
	 * ��ѯѲ�������б�
	 */
	public String queryPatrolTask(String xml);
	/**
	 * ��ѯѲ���¼�б�
	 */
	public String queryPatrolLOG(String xml);
	/**
	 * ����Ѳ���¼�б�
	 */
	public String savePatrolLOG(String xml);
	/**
	 * ����Ѳ������
	 */
	public String savePatrolItem(String xml);
	/**
	 * ����֤��
	 */
	public String savePatrolProof(String xml);
	/**
	 * ��ѯ���������ҵ�б�
	 */
	public String queryPatrolENTINFO(String xml);
	/**
	 * ��ѯ�ز��¼�б�
	 */
	public String queryReturnLog(String xml);

	/**
	 * ֪ͨ���淢��Ȩ���ж�
	 */
	/**
	 * ֪ͨ���淢��
	 */
	public String saveNotice(String xml);
	/**
	 * ֪ͨ�����ѯ
	 */
	public String queryNotice(String xml);
	/**
	 * ֪ͨ������ϸ��Ϣ��ѯ
	 */
	public String queryNoticeInfo(String xml);
	/**
	 * ��֤���յǼ�
	 */
	public String saveNocard(String xml);
	/**
	 * ��֤���ղ�ѯ
	 */
	public String queryNocardList(String xml);
	/**
	 * ��֤������ϸ
	 */
	public String queryNocardInfo(String xml);

	/**
	 * ��ѯ�����¶�Ӧ�Ĺ�˾
	 * @param xml
	 * @return
	 */
	public String queryCompanyByPatrolId(String xml);
	
	/**
	 * �û����б�
	 */
	public String queryUserGroup(String xml);
	
	/**
	 * �����û���id��ѯ�û��б�
	 */
	public String getUserListByGroupId(String xml);
	
	/**
	 * �����û�id�õ��û�����
	 */
	public String getUserListByuserId(String xml);
	/**
	 * ��������id�õ��û�����
	 */
	public String getUserListByrId(String xml);

	/**
	 * ��ѯ�쵽�ڵ�����
	 * @param xml
	 * @return
	 */
	public String queryTimeTask(String xml);

	/**
	 * ��ѯ���������
	 */	
	public String queryNoticeNum(String xml);
	/**
	 * Ѳ�������б��ѯ
	 */		
	public String queryMwpmBussPatrolItemList(String xml);
	/**
	 * �ز��б��ѯ
	 */	
	public String queryReturnLogList(String xml);
	/**
	 * �ز�¼��
	 */	
	public String saveReturnLog(String xml);

	/**
	 * ¼��֤��
	 * @param proofs
	 * @throws Exception 
	 */
	public void saveMwpmBussPatrolProofs(List<MwpmBussPatrolProof> proofs) throws Exception;
	
	/**
	 * �жϵ�ǰ��ҵ�ǲ����ڱ�������
	 */
	public String getEntByReaId(String xml);
	/**
	 * �����ֵ��б��ѯ
	 */	
	public String queryDictionaryList(String xml);
	/**
	 * ר�������ѯ
	 */	
	public String queryZxPatrolTask(String xml);
	/**
	 * δѲ������ͳ���б�
	 */
	public String queryUncheckedPatrolReport(String xml);
	
	/**
	 * ��ѯ��Χ�ڵ���ҵ
	 */
	public String queryEntByLatLong(String xml);
	
	/**
	 * ��ѯѲ��켣
	 */
	public String queryPatrolLoca(String xml);
	/**
	 * �жϸ��û��ǲ����жӳ����ϼ���
	 */
	public String getUserLevel(String xml);
	/**
	 * ��ҵ���䲹¼
	 */
	public String updateEntEmail(String xml);
	/**
	 * ���ɷ��淢���ʼ���ҵ�б�
	 */
	public String queryEntListByemail(String xml);
	public String sendEmail(String xml);

	/**
	 * ������ͨ�û�
	 * @param xml
	 * @return
	 */
	public String getCommonUserList(String xml);
	/**
	 * ����Ѳ������
	 * @param xml
	 * @return
	 */
	public String queryPType(String xml);

	public String getPhoneIpadByUserId(String xml);

	public String savePhoneIpadByUserId(String xml);
	/**
	 * Ѳ���¼����
	 * @param xml
	 * @return
	 */
	public String savePType(String xml);

	public String getRoleTypeByUserId(String xml);
	
	public String getReseaNameById(String reseaId);
	
}
