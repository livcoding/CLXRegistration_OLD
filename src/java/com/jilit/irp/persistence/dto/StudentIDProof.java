/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author vineetk.raj
 */
public class StudentIDProof implements java.io.Serializable {

    private String studentid;
    private StudentMaster studentmaster;
    private String passportnumber;
    private Date passportdateofissue;
    private Date passportvalidupto;
    private String passportplaceofissue;
    private String adhaarnumber;
    private String pan;
    private String emergencycontactno;
    private String emergencycontactpersonname;
    private String emergencyrelationship;
    private String medicalhistory;

    public StudentIDProof(String studentid, StudentMaster studentmaster, String passportnumber, Date passportdateofissue, Date passportvalidupto, String passportplaceofissue, String adhaarnumber, String pan, String emergencycontactno, String emergencycontactpersonname, String emergencyrelationship, String medicalhistory) {
        this.studentid = studentid;
        this.studentmaster = studentmaster;
        this.passportnumber = passportnumber;
        this.passportdateofissue = passportdateofissue;
        this.passportvalidupto = passportvalidupto;
        this.passportplaceofissue = passportplaceofissue;
        this.adhaarnumber = adhaarnumber;
        this.pan = pan;
        this.emergencycontactno = emergencycontactno;
        this.emergencycontactpersonname = emergencycontactpersonname;
        this.emergencyrelationship = emergencyrelationship;
        this.medicalhistory = medicalhistory;
    }

    public StudentIDProof() {
    }

    public StudentIDProof(StudentIDProof dto) {
        this.studentid = dto.getStudentid();
        this.passportnumber = dto.getPassportnumber();
        this.passportdateofissue = dto.getPassportdateofissue();
        this.passportvalidupto = dto.getPassportvalidupto();
        this.passportplaceofissue = dto.getPassportplaceofissue();
        this.adhaarnumber = dto.getAdhaarnumber();
        this.pan = dto.getPan();
        this.emergencycontactno=dto.getEmergencycontactno();
        this.emergencycontactpersonname=dto.getEmergencycontactpersonname();
        this.emergencyrelationship=dto.getEmergencyrelationship();
        this.medicalhistory=dto.getMedicalhistory();
    }

    public String getAdhaarnumber() {
        return adhaarnumber;
    }

    public void setAdhaarnumber(String adhaarnumber) {
        this.adhaarnumber = adhaarnumber;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Date getPassportdateofissue() {
        return passportdateofissue;
    }

    public void setPassportdateofissue(Date passportdateofissue) {
        this.passportdateofissue = passportdateofissue;
    }

    public String getPassportnumber() {
        return passportnumber;
    }

    public void setPassportnumber(String passportnumber) {
        this.passportnumber = passportnumber;
    }

    public String getPassportplaceofissue() {
        return passportplaceofissue;
    }

    public void setPassportplaceofissue(String passportplaceofissue) {
        this.passportplaceofissue = passportplaceofissue;
    }

    public Date getPassportvalidupto() {
        return passportvalidupto;
    }

    public void setPassportvalidupto(Date passportvalidupto) {
        this.passportvalidupto = passportvalidupto;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public StudentMaster getStudentmaster() {
        return studentmaster;
    }

    public void setStudentmaster(StudentMaster studentmaster) {
        this.studentmaster = studentmaster;
    }

    public String getEmergencycontactno() {
        return emergencycontactno;
    }

    public void setEmergencycontactno(String emergencycontactno) {
        this.emergencycontactno = emergencycontactno;
    }

    public String getEmergencycontactpersonname() {
        return emergencycontactpersonname;
    }

    public void setEmergencycontactpersonname(String emergencycontactpersonname) {
        this.emergencycontactpersonname = emergencycontactpersonname;
    }

    public String getEmergencyrelationship() {
        return emergencyrelationship;
    }

    public void setEmergencyrelationship(String emergencyrelationship) {
        this.emergencyrelationship = emergencyrelationship;
    }

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }



}
