/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author pankaj.kumar
 */
public class NOCApplicants implements java.io.Serializable {

    private NOCApplicantsId id;
    private StudentRegistration studentregistration;
    private RegistrationMaster registrationmaster;
    private StudentMaster studentmaster;
    private String appliedinstitute;
    private Date appliedfromdate;
    private Date appliedtodate;
    private String appliedstatus;
    private String remarks;
    private String deactive;


    public NOCApplicants() {
    }

    public NOCApplicants(NOCApplicants dto) {
        this.id = dto.getId();
        this.appliedfromdate = dto.getAppliedfromdate();
        this.appliedtodate = dto.getAppliedtodate();
        this.appliedinstitute = dto.getAppliedinstitute();
        this.appliedstatus = dto.getAppliedstatus();
        this.deactive = dto.getDeactive();
        this.remarks = dto.getRemarks();

    }

    public NOCApplicants(NOCApplicantsId id, StudentRegistration studentregistration, RegistrationMaster registrationmaster, String appliedinstitute, Date appliedfromdate, Date appliedtodate) {
        this.id = id;
        this.studentregistration = studentregistration;
        this.registrationmaster = registrationmaster;
        this.appliedinstitute = appliedinstitute;
        this.appliedfromdate = appliedfromdate;
        this.appliedtodate = appliedtodate;
    }

    public NOCApplicants(NOCApplicantsId id, StudentRegistration studentregistration, RegistrationMaster registrationmaster, String appliedinstitute, Date appliedfromdate, Date appliedtodate, String appliedstatus, String remarks, String deactive) {
        this.id = id;
        this.studentregistration = studentregistration;
        this.registrationmaster = registrationmaster;
        this.appliedinstitute = appliedinstitute;
        this.appliedfromdate = appliedfromdate;
        this.appliedtodate = appliedtodate;
        this.appliedstatus = appliedstatus;
        this.remarks = remarks;
        this.deactive = deactive;
    }




    /**
     * @return the id
     */
    public NOCApplicantsId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(NOCApplicantsId id) {
        this.id = id;
    }

    /**
     * @return the studentregistration
     */
    public StudentRegistration getStudentregistration() {
        return studentregistration;
    }

    /**
     * @param studentregistration the studentregistration to set
     */
    public void setStudentregistration(StudentRegistration studentregistration) {
        this.studentregistration = studentregistration;
    }

    /**
     * @return the registrationmaster
     */
    public RegistrationMaster getRegistrationmaster() {
        return registrationmaster;
    }

    /**
     * @param registrationmaster the registrationmaster to set
     */
    public void setRegistrationmaster(RegistrationMaster registrationmaster) {
        this.registrationmaster = registrationmaster;
    }

    /**
     * @return the studentmaster
     */
    public StudentMaster getStudentmaster() {
        return studentmaster;
    }

    /**
     * @param studentmaster the studentmaster to set
     */
    public void setStudentmaster(StudentMaster studentmaster) {
        this.studentmaster = studentmaster;
    }

    /**
     * @return the appliedinstitute
     */
    public String getAppliedinstitute() {
        return appliedinstitute;
    }

    /**
     * @param appliedinstitute the appliedinstitute to set
     */
    public void setAppliedinstitute(String appliedinstitute) {
        this.appliedinstitute = appliedinstitute;
    }

    /**
     * @return the appliedfromdate
     */
    public Date getAppliedfromdate() {
        return appliedfromdate;
    }

    /**
     * @param appliedfromdate the appliedfromdate to set
     */
    public void setAppliedfromdate(Date appliedfromdate) {
        this.appliedfromdate = appliedfromdate;
    }

    /**
     * @return the appliedtodate
     */
    public Date getAppliedtodate() {
        return appliedtodate;
    }

    /**
     * @param appliedtodate the appliedtodate to set
     */
    public void setAppliedtodate(Date appliedtodate) {
        this.appliedtodate = appliedtodate;
    }

    /**
     * @return the appliedstatus
     */
    public String getAppliedstatus() {
        return appliedstatus;
    }

    /**
     * @param appliedstatus the appliedstatus to set
     */
    public void setAppliedstatus(String appliedstatus) {
        this.appliedstatus = appliedstatus;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the deactive
     */
    public String getDeactive() {
        return deactive;
    }

    /**
     * @param deactive the deactive to set
     */
    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getIdStr() {
        return (id.getInstituteid() + "::" + id.getRegistrationid() + "::" + id.getStudentid());
    }

}
