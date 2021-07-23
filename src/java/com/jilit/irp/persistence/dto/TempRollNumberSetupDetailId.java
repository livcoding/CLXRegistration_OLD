package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetupDetailId implements java.io.Serializable {

    private String instituteid;
    private String groupid;
    private String academicyear;
    private String programtypeid;
    private String programid;
    private String branchid;

    public TempRollNumberSetupDetailId() {

    }

    public TempRollNumberSetupDetailId(String instituteid, String groupid, String academicyear, String programtypeid, String programid, String branchid) {
        this.instituteid = instituteid;
        this.groupid = groupid;
        this.academicyear = academicyear;
        this.programtypeid = programtypeid;
        this.programid = programid;
        this.branchid = branchid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public String getProgramtypeid() {
        return programtypeid;
    }

    public void setProgramtypeid(String programtypeid) {
        this.programtypeid = programtypeid;
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

}
