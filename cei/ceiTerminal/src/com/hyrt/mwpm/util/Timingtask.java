package com.hyrt.mwpm.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hyrt.cei.vo.MwpmDictFunction;
import com.hyrt.cei.vo.MwpmSysClass;
import com.hyrt.cei.webservice.dao.CeiDictDao;
import com.hyrt.cei.webservice.dao.ClassExportXmlDao;

public class Timingtask{
	
	@Autowired
	private CeiDictDao ceiDictDao;
	@Autowired
	private ClassExportXmlDao classExportXmlDao;
	
	
	public CeiDictDao getCeiDictDao() {
		return ceiDictDao;
	}


	public void setCeiDictDao(CeiDictDao ceiDictDao) {
		this.ceiDictDao = ceiDictDao;
	}


	public ClassExportXmlDao getClassExportXmlDao() {
		return classExportXmlDao;
	}


	public void setClassExportXmlDao(ClassExportXmlDao classExportXmlDao) {
		this.classExportXmlDao = classExportXmlDao;
	}

	/**
	 * 查询有角色集合
	 * @author tanJie
	 */
	public void queryRoleList(){
		List list = new ArrayList();
		list = ceiDictDao.getALLData("select new map(r.roleid as roleid) from MwpmSysRoleinfo r ");	
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap)list.get(i);
				String roleid = map.get("roleid")==null?"":map.get("roleid").toString();
				this.queryRoleIdByFunction(roleid);
			}
		}
	}
	
	
	/**
	 * 通过角色id查询业务信息
	 * @author tanJie
	 */
	public void queryRoleIdByFunction(String roleId) {
		String xmlPath = CommonParm.getString("XML_PATH");
		String rolepath = CommonParm.getString("ROLE");
		List list = new ArrayList();
			try {
				list = ceiDictDao.getALLData("select new map(s.functionid as functionid,f.path as path) from MwpmSysSecurityacl s,MwpmDictFunction f " +
						"where s.functionid=f.id and s.roleid='"+roleId+"'");	
				Map map1 = new HashMap();	
				String ids = "";
				if(list!=null && list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						HashMap map = (HashMap)list.get(i);
						String functionid =  map.get("functionid")==null?"":map.get("functionid").toString(); 
						String path =  map.get("path")==null?"":map.get("path").toString(); 
						String[] fids = path.split("//");
						for (int k = 0; k < fids.length; k++) {
							if(map1.get(fids[k]) == null){
								if(!fids[k].equals("")){
									map1.put(fids[k], fids[k]);
									ids = ids+"'"+fids[k]+"',";
								}
							}
							if(map1.get(functionid) == null){
								if(!functionid.equals("")){
									map1.put(functionid, functionid);
									ids = ids+"'"+functionid+"',";
								}
							}
						}	
					}
				List phoneFunctionList = ceiDictDao.getALLData("select new map(f.id as id,f.name as name,f.parentid as parentid,f.path as path," +
						"f.type as type,f.operationimage as operationimage,f.issuetime as issuetime,f.ipadimage as ipadimage," +
						"f.ipadImageTime as ipadImageTime)" +
						" from MwpmDictFunction f where 1=1 and f.id in ("+ids.substring(0, ids.length()-1)+")");
				
				String xmlMessage="";
				if(phoneFunctionList!=null && phoneFunctionList.size()>0){
					for (int j = 0; j < phoneFunctionList.size(); j++) {
						HashMap phoneFunctionMap = (HashMap)phoneFunctionList.get(j);
						String id = phoneFunctionMap.get("id")==null?"":(String)phoneFunctionMap.get("id");
						String name = phoneFunctionMap.get("name")==null?"":(String)phoneFunctionMap.get("name");
						String parentid = phoneFunctionMap.get("parentid")==null?"":(String)phoneFunctionMap.get("parentid");
						String path = phoneFunctionMap.get("path")==null?"":(String)phoneFunctionMap.get("path");
						String type = phoneFunctionMap.get("type")==null?"":(String)phoneFunctionMap.get("type");
						String operationimage = phoneFunctionMap.get("operationimage")==null?"":(String)phoneFunctionMap.get("operationimage");
						String issuetime = phoneFunctionMap.get("issuetime")==null?"":(String)phoneFunctionMap.get("issuetime");
						String ipadimage = phoneFunctionMap.get("ipadimage")==null?"":(String)phoneFunctionMap.get("ipadimage");
						String ipadImageTime = phoneFunctionMap.get("ipadImageTime")==null?"":(String)phoneFunctionMap.get("ipadImageTime");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						MwpmDictFunction f = new MwpmDictFunction();
						f.setId(id);
						f.setName(name);
						f.setParentid(parentid);
						f.setPath(path);
						f.setType(type);
						f.setIpadimage(ipadimage);
						f.setIpadImageTime(ipadImageTime);
						f.setOperationimage(operationimage);
						f.setIssuetime(issuetime);
						classExportXmlDao.addClass(f, xmlPath+rolepath+roleId+".xml", "MwpmDictFunction", j);
					}
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}



	/**
	 * 查询版本集合
	 * @author tanJie
	 */
	public void queryFunctionList(){
		List list = new ArrayList();
		list = ceiDictDao.getALLData("select new map(r.id as id) from MwpmDictFunction r where r.path is null");	
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap)list.get(i);
				String id = map.get("id")==null?"":map.get("id").toString();
				this.queryFunctionByTree(0, id, id);
				this.queryFunctionIdByClass(id, 20, 1);
			}
		}
		
	}
	

	/**
	 * 按functionid查询子菜单
	 * 传入参数:a 
	 * @author tanJie
	 */
	private void queryFunctionByTree(int a,String functionid,String fid) {
		String xmlPath = CommonParm.getString("XML_PATH");
		String functionpath = CommonParm.getString("FUNCTION");
		List functionList = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
				"from MwpmDictFunction f where f.parentid='"+functionid+"'");
		if(functionList!=null && functionList.size()>0){
			for (int j = 0; j < functionList.size(); j++) {
				HashMap functionMap = (HashMap)functionList.get(j);
				String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
				String parentid = functionMap.get("parentid")==null?"":functionMap.get("parentid").toString();
				String type = functionMap.get("type")==null?"":functionMap.get("type").toString();
				String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
				if(!type.equals("")){
					MwpmDictFunction f = new MwpmDictFunction();
					f.setId(id);
					f.setParentid(parentid);
					f.setType(type);
					f.setName(name);
					classExportXmlDao.addClass(f, xmlPath+functionpath+fid+".xml", "function", a);
					a++;
				}else{
					MwpmDictFunction f = new MwpmDictFunction();
					f.setId(id);
					f.setParentid(parentid);
					f.setType(type);
					f.setName(name);
					classExportXmlDao.addClass(f, xmlPath+functionpath+fid+".xml", "function", a);
					a++;
					this.queryFunctionByTree(a,id,fid);
				}
			}
		}else{
			List list = ceiDictDao.getALLData("select new map(f.id as id,f.parentid as parentid,f.name as name,f.type as type) " +
					"from MwpmDictFunction f where f.id='"+functionid+"'");
			if(list!=null && list.size()>0){
				HashMap functionMap = (HashMap)list.get(0);
				String id = functionMap.get("id")==null?"":functionMap.get("id").toString();
				String parentid = functionMap.get("parentid")==null?"":functionMap.get("parentid").toString();
				String type = functionMap.get("type")==null?"":functionMap.get("type").toString();
				String name = functionMap.get("name")==null?"":functionMap.get("name").toString();
				if(!type.equals("")){
					MwpmDictFunction f = new MwpmDictFunction();
					f.setId(id);
					f.setParentid(parentid);
					f.setType(type);
					f.setName(name);
					classExportXmlDao.addClass(f, xmlPath+functionpath+fid+".xml", "function", a);
					a++;
				}
			}
		}
	}
	
	
	/**
	 * 按functionid集合查询所属课件列表
	 * @author tanJie
	 */
	private void queryFunctionIdByClass(String funid,int num,int index) {
		String kjid="";
		List operationList =new ArrayList();
		List operatiList =new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String xmlPath = CommonParm.getString("XML_PATH");
		String newclass = CommonParm.getString("NEWCLASS");
		String functionpath = CommonParm.getString("FUNCTION");
		//判断xml文件是否存在，如果存在则直接读取xml文件中的数据，不存在则生成xml文件再读取
		File file=new File(xmlPath+functionpath+funid+".xml");
		if(!file.exists()){
			//生成版本文件
			this.queryFunctionByTree(0, funid, funid);
		}
		List functionList = classExportXmlDao.findFunction(xmlPath+functionpath+funid+".xml", "function");
		String functionids = "";
		if(functionList!=null && functionList.size()>0){
			for (int i = 0; i < functionList.size(); i++) {
				MwpmDictFunction f = (MwpmDictFunction)functionList.get(i);
				String functionid = f.getId();
				String type = f.getType()==null?"":f.getType();
				if(type.equals("kj")){
					functionids = functionids+" oc.functionid='"+functionid+"' or ";
				}
			}
		}
		
		
		if(functionids.length()>0){
			//通过functionid集合查询业务下课件分类集合
			operationList = ceiDictDao.getALLData("select new map(c.resourceid as resourceid,c.type as type) " +
					"from MwpmSysClassification c ,MwpmSysOperationClass oc where c.typeid=oc.classid " +
					" and c.type='kj' and ("+functionids.substring(0, functionids.length()-3)+") group by c.resourceid,c.type");

			//通过functionid集合查询业务下直接对应的课件集合
			operatiList = ceiDictDao.getALLData("select new map(oc.classid as classid,oc.type as type) " +
					"from MwpmSysOperatiResource oc " +
					"where 0=0 and oc.type='kj' and ("+functionids.substring(0, functionids.length()-3)+")");
		}
		if(operationList!=null && operationList.size()>0){
			for (int i = 0; i < operationList.size(); i++) {
				HashMap resourceMap = (HashMap)operationList.get(i);
				String resourceid = resourceMap.get("resourceid")==null?"":resourceMap.get("resourceid").toString();
				String type = resourceMap.get("type")==null?"":resourceMap.get("type").toString();
				if(type.equals("kj")){
					kjid = kjid+" c.id='"+resourceid+"' or";
				}
			}
			
		}
		if(operatiList!=null && operatiList.size()>0){
			for (int i = 0; i < operatiList.size(); i++) {
				HashMap classMap = (HashMap)operatiList.get(i);
				String classid = classMap.get("classid")==null?"":classMap.get("classid").toString();
				String type = classMap.get("type")==null?"":classMap.get("type").toString();
				if(type.equals("kj")){
					kjid = kjid+" c.id='"+classid+"' or";
				}
			}
			
		}
		List classList = new ArrayList();
		
		//通过获得的课件id查询出对应的课件列表信息
		if(!kjid.equals("")){ 
			if(num!=0 && index!=0){
				String sql = " SELECT TOP "+num+" *  FROM (" +
				 "SELECT ROW_NUMBER() OVER (ORDER BY c.time desc) AS RowNumber," +
				 "c.id as classid,c.name as name,c.teachername as author,c.classlength as classlength,c.isfree as isfree," +
				 "c.intro as intro,c.setnum as setnum,c.TIME as creattime,re.path as path,re.passkey as passkey " +
				 "from MWPM_SYS_CLASS c left join MWPM_SYS_RESOURCEPATH re on c.id=re.RESOURCEID " +
				 "where c.status='1' and re.type='kj' "+
				 " and ("+kjid.substring(0,kjid.length()-3)+")"+
				 ") A WHERE RowNumber > "+num+"*("+index+"-1)";
				classList = ceiDictDao.getInfo(sql);
			}else{
				String classsql = "select c.id as classid,c.name as name,c.teachername as author,c.classlength as classlength,c.isfree as isfree," +
				  "c.intro as intro,c.setnum as setnum,c.TIME as creattime,re.path as path,re.passkey as passkey " +
				  "from MWPM_SYS_CLASS c left join MWPM_SYS_RESOURCEPATH re on c.id=re.RESOURCEID " +
				  "where c.status='1' and re.type='kj' and ("+kjid.substring(0,kjid.length()-3)+") order by c.time desc";
				classList = ceiDictDao.getInfo(classsql);
			}
		}
		
		if(classList!=null && classList.size()>0){
			for (int i = 0; i < classList.size(); i++) {
				HashMap map = (HashMap)classList.get(i);
				String classid = map.get("classid")==null?"":(String)map.get("classid");
				String name = map.get("name")==null?"":(String)map.get("name");
				String author = map.get("author")==null?"":(String)map.get("author");
				String classlength = map.get("classlength")==null?"":(String)map.get("classlength");
				String path = map.get("path")==null?"":(String)map.get("path");
				String intro = map.get("intro")==null?"":(String)map.get("intro");
				String isfree = map.get("isfree")==null?"":((Character)map.get("isfree")).toString();
				Date creattime = (Date) map.get("creattime");
				Integer setnum= map.get("setnum")==null?0:(Integer) map.get("setnum");
				String passkey = map.get("passkey")==null?"":(String)map.get("passkey");
				MwpmSysClass sc = new MwpmSysClass();
				sc.setId(classid);
				sc.setName(name);
				sc.setTeachername(author);
				sc.setClasslength(classlength);
				sc.setPath(path);
				sc.setIntro(intro);
				sc.setTime(creattime);
				sc.setSetnum(setnum);
				sc.setIsfree(isfree);
				sc.setPasskey(passkey);
				classExportXmlDao.addClass(sc, xmlPath+newclass+funid+".xml", "MwpmSysClass", i);
			}
		}
		
	}

}
