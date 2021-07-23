/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ashok.singh
 */
public class AdmissionTypeMasterId  implements java.io.Serializable {
    String instituteid;
    String programid;
    String admissiontypeid;

    public AdmissionTypeMasterId() {
    }

    
    
    public AdmissionTypeMasterId(String instituteid, String programid) {
        this.instituteid = instituteid;
        this.programid = programid;
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

    public String getAdmissiontypeid() {
        return admissiontypeid;
    }

    public void setAdmissiontypeid(String admissiontypeid) {
        this.admissiontypeid = admissiontypeid;
    }
    
    
    
}
