package com.jilit.irp.persistence.dto;
// Generated 30 OCT, 2019 

/**
 * malkeet.singh
 */
public class Setup_GIPCriteriaDetailId implements java.io.Serializable {

    private String instituteid;
    private String programid;
    private String applicablegradeid;

    public Setup_GIPCriteriaDetailId() {
    }

    public Setup_GIPCriteriaDetailId(String instituteid, String programid, String applicablegradeid) {
        this.instituteid = instituteid;
        this.programid = programid;
        this.applicablegradeid = applicablegradeid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getApplicablegradeid() {
        return applicablegradeid;
    }

    public void setApplicablegradeid(String applicablegradeid) {
        this.applicablegradeid = applicablegradeid;
    }

}
