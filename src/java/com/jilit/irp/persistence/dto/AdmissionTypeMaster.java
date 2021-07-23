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
public class AdmissionTypeMaster  implements java.io.Serializable {
    AdmissionTypeMasterId id;
    String admissiontype;
    String enrollmentcode;
    short seqid;
    String deactive;

    public AdmissionTypeMaster() {
    }

    
    public AdmissionTypeMaster(AdmissionTypeMasterId id, String admissiontype, String enrollmentcode, short seqid, String deactive) {
        this.id = id;
        this.admissiontype = admissiontype;
        this.enrollmentcode = enrollmentcode;
        this.seqid = seqid;
        this.deactive = deactive;
    }

    
    
    public AdmissionTypeMasterId getId() {
        return id;
    }

    public void setId(AdmissionTypeMasterId id) {
        this.id = id;
    }

    public String getAdmissiontype() {
        return admissiontype;
    }

    public void setAdmissiontype(String admissiontype) {
        this.admissiontype = admissiontype;
    }

    public String getEnrollmentcode() {
        return enrollmentcode;
    }

    public void setEnrollmentcode(String enrollmentcode) {
        this.enrollmentcode = enrollmentcode;
    }

    public short getSeqid() {
        return seqid;
    }

    public void setSeqid(short seqid) {
        this.seqid = seqid;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }
    
    
    
}
