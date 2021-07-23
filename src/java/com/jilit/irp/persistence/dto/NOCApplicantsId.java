/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

/**
 *
 * @author pankaj.kumar
 */
public class NOCApplicantsId implements java.io.Serializable {

     private String instituteid;
     private String registrationid;
     private String studentid;



     public NOCApplicantsId() {
    }

    public NOCApplicantsId(String instituteid, String registrationid, String studentid) {
       this.instituteid = instituteid;
       this.registrationid = registrationid;
       this.studentid = studentid;
    }


     /**
     * @return the instituteid
     */
    public String getInstituteid() {
        return instituteid;
    }

    /**
     * @param instituteid the instituteid to set
     */
    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    /**
     * @return the registrationid
     */
    public String getRegistrationid() {
        return registrationid;
    }

    /**
     * @param registrationid the registrationid to set
     */
    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    /**
     * @return the studentid
     */
    public String getStudentid() {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

     

}
