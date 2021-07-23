package com.jilit.irp.persistence.dto;
// Generated May 18, 2011 12:08:19 PM by Hibernate Tools 3.2.1.GA


/**
 * StudentResultDetailId malkeet.singh
 */
public class StudentResultDetailId  implements java.io.Serializable {


     private String instituteid;
     private String registrationid;
     private String studentfstid;
     private String studentid;

    public StudentResultDetailId() {
    }

    public StudentResultDetailId(String instituteid, String registrationid, String studentfstid, String studentid) {
        this.instituteid = instituteid;
        this.registrationid = registrationid;
        this.studentfstid = studentfstid;
        this.studentid = studentid;
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

    public String getStudentfstid() {
        return studentfstid;
    }

    public void setStudentfstid(String studentfstid) {
        this.studentfstid = studentfstid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

}


