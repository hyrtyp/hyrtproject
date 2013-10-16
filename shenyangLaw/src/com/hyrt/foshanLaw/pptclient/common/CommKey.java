/**
 * 
 */
package com.hyrt.foshanLaw.pptclient.common;

/**
 * Description:
 * 
 * @author 郑伟
 * @Date 2013-1-7
 * @Copyright:2013-1-7
 * @Company:华源润通（北京）科技有限公司重庆分公司
 */
public class CommKey {
	public static final String pkgname = "com.hyrt.foshanLaw.pptclient";
	//ftp默认值
	public static final String ftp_default_ip = "218.25.36.5";
	public static final int ftp_default_port = 21;
	public static final String ftpuser="ftpuser";
	public static final String ftppwd="password";

	//服务器连接值
	public static final String server_default_ip = "218.25.36.5";
	public static final int server_default_port = 5211;
	
	
	//广播类型
	public static final String netstop_receiver = "netstop_receiver";
	public static final String netstart_receiver = "netstart_receiver";
	public static final String svr_receiver="svr_receiver";//service用广播
	public static final String ui_receiver="ui_receiver";//ui用广播
	
	//广播Type类型
	public static final String type_info="type_info";      //查询分组信息
	public static final String type_update="type_update";   //提交录音
	public static final String type_sessioninfo="type_sessioninfo";   //UI用，更新会话人员列表
	public static final String type_new="type_new";  //新对讲信息(列表)
	public static final String type_newitem="type_newitem";  //新对讲信息（会话项）
	public static final String type_send="type_send";   //告之Service发送指令 
	public static final String type_returnmsg="type_returnmsg";  //发送信息成功的通知
	public static final String ftp_msg="ftp_msg";  //发送信息成功的通知
}
