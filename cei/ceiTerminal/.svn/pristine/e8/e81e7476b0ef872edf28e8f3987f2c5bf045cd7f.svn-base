package com.hyrt.mwpm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页逻辑
 * @author 
 *
 */
public class PageBean  implements Serializable{
	
	private List list=null;
	
	private int pageNo = 1;     //页码
	private int pageSize = 10;   //页面记录数
	private int pageSum;   //总页数
	private int rowCount;   //记录总条数

	private int pagePrev;   //上一页
	private int pageNext;   //下一页
	  
	private int hasFirst;
	private int hasPrev;   //是否有上一页
	private int hasNext;   //是否有下一页
	private int hasEnd;
	
	public PageBean(){
		list=new ArrayList();
	}

	public int getHasEnd() {
	    if(pageSum>1){
	        if(pageNo<pageSum)
	          return 1;
	        else
	          return 0;
	    }else{
	        return 0;
	    }
	}

	public void setHasEnd(int hasEnd) {
		this.hasEnd = hasEnd;
	}

	public int getHasFirst() {
	    if(pageSum>1){
	    	if(pageNo>1)
	          return 1;
	        else
	          return 0;
	    }else{
	        return 0;
	    }
	}

	public void setHasFirst(int hasFirst) {
		this.hasFirst = hasFirst;
	}

	public int getHasNext() {
		if(pageSum>1){
			if(!(pageNo<pageSum))
		    	return 0;
		    else
		        return 1;
		}else{
			return 0;
		}
	}

	public void setHasNext(int hasNext) {
		this.hasNext = hasNext;
	}

	public int getHasPrev() {
		if(pageSum>1){
			if(pageNo<2)
		    	return 0;
		    else
		        return 1;
		}else{
			return 0;
		}
	}

	public void setHasPrev(int hasPrev) {
		this.hasPrev = hasPrev;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageNext() {
		return (this.pageNo+1);
	}

	public void setPageNext(int pageNext) {
		this.pageNext = pageNext;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo<=0){
			this.pageNo = 1;
		}else{
			this.pageNo = pageNo;
		}
	}

	public int getPagePrev() {
		return (this.pageNo-1);
	}

	public void setPagePrev(int pagePrev) {
		this.pagePrev = pagePrev;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSum() {
		return pageSum;
	}

	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	/* @author Zhengys
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sf = new StringBuffer("PageBean[");
		sf.append("pageNo=");
		sf.append(pageNo);
		sf.append(";pageSize=");
		sf.append(pageSize);
		sf.append(";pageSum=");
		sf.append(pageSum);
		sf.append(";rowCount=");
		sf.append(rowCount);
		sf.append(";pagePrev=");
		sf.append(pagePrev);
		sf.append(";pageNext=");
		sf.append(pageNext);
		sf.append(";hasFirst=");
		sf.append(hasFirst);
		sf.append(";hasPrev=");
		sf.append(hasPrev);
		sf.append(";hasNext=");
		sf.append(hasNext);
		sf.append(";hasEnd=");
		sf.append(hasEnd);
		sf.append("]");
		return sf.toString();
	}
	

	
}
