/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author campus.trainee
 */
public class SubjectRegistrationCriteriaId implements java.io.Serializable {

    private String clientid;
    private String instituteid;
    private String programid;

    public SubjectRegistrationCriteriaId() {

    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
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

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof AcademicyearId)) {
            return false;
        }
        SubjectRegistrationCriteriaId castOther = (SubjectRegistrationCriteriaId) other;

        return ((this.getInstituteid() == castOther.getInstituteid()) || (this.getInstituteid() != null && castOther.getInstituteid() != null && this.getInstituteid().equals(castOther.getInstituteid())))
                && ((this.getClientid() == castOther.getClientid()) || (this.getClientid() != null && castOther.getClientid() != null && this.getClientid().equals(castOther.getClientid())))
                && ((this.getProgramid() == castOther.getProgramid()) || (this.getProgramid() != null && castOther.getProgramid() != null && this.getProgramid().equals(castOther.getProgramid())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getInstituteid() == null ? 0 : this.getInstituteid().hashCode());
        result = 37 * result + (getClientid() == null ? 0 : this.getClientid().hashCode());
        result = 37 * result + (getProgramid() == null ? 0 : this.getProgramid().hashCode());
        return result;
    }

}
