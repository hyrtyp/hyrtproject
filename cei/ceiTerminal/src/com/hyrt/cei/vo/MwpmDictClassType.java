package com.hyrt.cei.vo;



/**
 * AbstractMwpmSysOrginfo generated by MyEclipse - Hibernate Tools
 */

public class MwpmDictClassType  implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;
     private String parentid;
     private String isleaf;
     private String path;
     private Integer depth;
     private Integer seqnum;
     private String fld1;
 	private String fld2;

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
        return this.parentid;
    }
    
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getIsleaf() {
        return this.isleaf;
    }
    
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

   

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }


   

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}

	public String getFld1() {
		return fld1;
	}

	public void setFld1(String fld1) {
		this.fld1 = fld1;
	}

	public String getFld2() {
		return fld2;
	}

	public void setFld2(String fld2) {
		this.fld2 = fld2;
	}
   








}