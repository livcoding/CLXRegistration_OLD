package com.jilit.irp.persistence.dto;

/**
 *  @author Malkeet Singh
 */
public class SummerRegistrationSetupId implements java.io.Serializable {

    private String instituteid;
    private String programid;
    private byte stynumber;

    public SummerRegistrationSetupId() {
    }

    public SummerRegistrationSetupId(String instituteid, String programid, byte stynumber) {
        this.instituteid = instituteid;
        this.programid = programid;
        this.stynumber = stynumber;
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

    public byte getStynumber() {
        return stynumber;
    }

    public void setStynumber(byte stynumber) {
        this.stynumber = stynumber;
    }

  
}
