package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberControlId implements java.io.Serializable {

    private String instituteid;
    private String groupid;
    private String ym;

    public TempRollNumberControlId() {

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

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

}
