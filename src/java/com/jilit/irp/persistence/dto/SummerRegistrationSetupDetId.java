package com.jilit.irp.persistence.dto;

/**
 *  @author Malkeet Singh
 */
public class SummerRegistrationSetupDetId implements java.io.Serializable {

    private String instituteid;
    private String programid;
    private byte stynumber;
    private String majororminor;

    public SummerRegistrationSetupDetId() {
    }

    public SummerRegistrationSetupDetId(String instituteid, String programid, byte stynumber, String majororminor) {
        this.instituteid = instituteid;
        this.programid = programid;
        this.stynumber = stynumber;
        this.majororminor = majororminor;
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

    public String getMajororminor() {
        return majororminor;
    }

    public void setMajororminor(String majororminor) {
        this.majororminor = majororminor;
    }

  
}
