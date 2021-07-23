package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class PreRequisitForRegSubjDetailId implements java.io.Serializable {

    private String instituteid;
    private String academicyear;
    private String programid;
    private String branchid;
    private String stynumber;
    private String subjectid;

    public PreRequisitForRegSubjDetailId() {
    }

    public PreRequisitForRegSubjDetailId(String instituteid, String academicyear, String programid, String branchid, String stynumber, String subjectid) {
        this.instituteid = instituteid;
        this.academicyear = academicyear;
        this.programid = programid;
        this.branchid = branchid;
        this.stynumber = stynumber;
        this.subjectid = subjectid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getStynumber() {
        return stynumber;
    }

    public void setStynumber(String stynumber) {
        this.stynumber = stynumber;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }
}
