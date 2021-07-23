/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ashutosh1.kumar
 */
public class RegistrationMasterDetailId implements java.io.Serializable {

    private String instituteid;
    private String registrationid;
    private String programid;
    private String branchid;
    private String academicyear;

    public RegistrationMasterDetailId(String instituteid, String registrationid, String programid, String branchid, String academicyear) {
        this.instituteid = instituteid;
        this.registrationid = registrationid;
        this.programid = programid;
        this.branchid = branchid;
        this.academicyear = academicyear;
    }

    public RegistrationMasterDetailId() {
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

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

}
