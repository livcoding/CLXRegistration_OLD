package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author ankur.goyal
 */
public class OldvsNewSubject implements java.io.Serializable {

    private OldvsNewSubjectId id;
    private RegistrationMaster registrationmaster;
    private SubjectMaster subjectmaster;
    private String newsubjectid;
    private Date wef;
    private String remarks;
    private String deactive;
    private String entryby;
    private Date entrydatetime;

    public OldvsNewSubject() {

    }

    public OldvsNewSubjectId getId() {
        return id;
    }

    public void setId(OldvsNewSubjectId id) {
        this.id = id;
    }

    public SubjectMaster getSubjectmaster() {
        return subjectmaster;
    }

    public void setSubjectmaster(SubjectMaster subjectmaster) {
        this.subjectmaster = subjectmaster;
    }

    public RegistrationMaster getRegistrationmaster() {
        return registrationmaster;
    }

    public void setRegistrationmaster(RegistrationMaster registrationmaster) {
        this.registrationmaster = registrationmaster;
    }

    public String getNewsubjectid() {
        return newsubjectid;
    }

    public void setNewsubjectid(String newsubjectid) {
        this.newsubjectid = newsubjectid;
    }

    public Date getWef() {
        return wef;
    }

    public void setWef(Date wef) {
        this.wef = wef;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public Date getEntrydatetime() {
        return entrydatetime;
    }

    public void setEntrydatetime(Date entrydatetime) {
        this.entrydatetime = entrydatetime;
    }
}
