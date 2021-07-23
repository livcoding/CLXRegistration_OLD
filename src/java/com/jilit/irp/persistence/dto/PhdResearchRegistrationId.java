/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

/**
 *
 * @author Administrator
 */
public class PhdResearchRegistrationId  implements java.io.Serializable {


     private String instituteid;
     private String registrationid;
     private String studentid;

    public PhdResearchRegistrationId() {
    }

    public PhdResearchRegistrationId(String instituteid, String registrationid, String studentid) {
       this.instituteid = instituteid;
       this.registrationid = registrationid;
       this.studentid = studentid;
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

}



