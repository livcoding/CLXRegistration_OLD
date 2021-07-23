package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class Sis_RegistrationActivityRightsId implements java.io.Serializable {

    private String instituteid;
    private String activityid;
    private String staffid;
    private String stafftype;

    public Sis_RegistrationActivityRightsId() {
    }

    public Sis_RegistrationActivityRightsId(String instituteid, String activityid, String staffid, String stafftype) {
        this.instituteid = instituteid;
        this.activityid = activityid;
        this.staffid = staffid;
        this.stafftype = stafftype;
    }

    public String getInstituteid() {
        return this.instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getActivityid() {
        return this.activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getStaffid() {
        return this.staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStafftype() {
        return this.stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }
}
