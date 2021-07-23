/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

/**
 *
 * @author Administrator
 */
public class PhdSelfcourseRegistrationId  implements java.io.Serializable {


     private String instituteid;
     private String registrationid;
     private String studentid;
     private String subjectid;

    public PhdSelfcourseRegistrationId() {
    }

    public PhdSelfcourseRegistrationId(String instituteid, String registrationid, String studentid,String subjectid) {
       this.instituteid = instituteid;
       this.registrationid = registrationid;
       this.studentid = studentid;
       this.subjectid = subjectid;
    }

    public String getInstituteid() {
        return this.instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }
    public String getRegistrationid() {
        return this.registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }
    public String getStudentid() {
        return this.studentid;
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


