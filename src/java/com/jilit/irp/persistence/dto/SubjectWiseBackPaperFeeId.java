/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author Malkeet Singh
 */
public class SubjectWiseBackPaperFeeId implements java.io.Serializable {

    private String instituteid;
    private String registrationid;
    private String subjectid;

    public SubjectWiseBackPaperFeeId() {

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

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof SubjectWiseBackPaperFeeId)) {
            return false;
        }
        SubjectWiseBackPaperFeeId castOther = (SubjectWiseBackPaperFeeId) other;

        return ((this.getInstituteid() == castOther.getInstituteid()) || (this.getInstituteid() != null && castOther.getInstituteid() != null && this.getInstituteid().equals(castOther.getInstituteid())))
                && ((this.getRegistrationid() == castOther.getRegistrationid()) || (this.getRegistrationid() != null && castOther.getRegistrationid() != null && this.getRegistrationid().equals(castOther.getRegistrationid())))
                && ((this.getSubjectid() == castOther.getSubjectid()) || (this.getSubjectid() != null && castOther.getSubjectid() != null && this.getSubjectid().equals(castOther.getSubjectid())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getInstituteid() == null ? 0 : this.getInstituteid().hashCode());
        result = 37 * result + (getRegistrationid() == null ? 0 : this.getRegistrationid().hashCode());
        result = 37 * result + (getSubjectid() == null ? 0 : this.getSubjectid().hashCode());
        return result;
    }

}
