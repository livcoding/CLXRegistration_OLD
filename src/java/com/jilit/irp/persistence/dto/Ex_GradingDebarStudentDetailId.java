package com.jilit.irp.persistence.dto;
// Generated Sep 25, 2019 


/**
 * malkeet.singh
 */
public class Ex_GradingDebarStudentDetailId implements java.io.Serializable {

    private String instituteid;
    private String registrationid;
    private String studentid;
    private String subjectid;

    public Ex_GradingDebarStudentDetailId(String instituteid, String registrationid, String studentid, String subjectid) {
        this.instituteid = instituteid;
        this.registrationid = registrationid;
        this.studentid = studentid;
        this.subjectid = subjectid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    
}


