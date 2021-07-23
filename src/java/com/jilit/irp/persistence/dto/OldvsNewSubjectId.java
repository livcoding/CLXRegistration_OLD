package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class OldvsNewSubjectId implements java.io.Serializable {

    private String instituteid;
    private String registrationid;
    private String subjectid;

    public OldvsNewSubjectId() {

    }

    public OldvsNewSubjectId(String instituteid, String registrationid, String subjectid) {
        this.instituteid = instituteid;
        this.registrationid = registrationid;
        this.subjectid = subjectid;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

}
