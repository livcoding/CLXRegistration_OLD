package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetupId implements java.io.Serializable {

    private String instituteid;
    private String groupid;

    public TempRollNumberSetupId() {

    }

    public TempRollNumberSetupId(String instituteid, String groupid) {
        this.instituteid = instituteid;
        this.groupid = groupid;
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

}
