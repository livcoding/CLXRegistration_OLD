/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.math.BigDecimal;

/**
 *
 * @author ankit.kumar
 */
public class SubjectsForBranchChange implements java.io.Serializable {

    private SubjectsForBranchChangeId id;
    private SubjectMaster subjectmaster;
    //  private BranchMaster branchmaster;
    private BigDecimal credit;
    private String departmentid;
    private String deactive;
    private String entryby;
    private String endtrydate;

    public SubjectsForBranchChange() {
    }

    public SubjectsForBranchChange(SubjectsForBranchChangeId id, SubjectMaster subjectmaster, BranchMaster branchmaster, BigDecimal credit, String departmentid) {
        this.id = id;
        this.subjectmaster = subjectmaster;
        // this.branchmaster = branchmaster;
        this.credit = credit;
        this.departmentid = departmentid;
    }

    public SubjectsForBranchChange(SubjectsForBranchChangeId id, SubjectMaster subjectmaster, BranchMaster branchmaster, BigDecimal credit, String departmentid, String deactive, String entryby, String endtrydate) {
        this.id = id;
        this.subjectmaster = subjectmaster;
        //this.branchmaster = branchmaster;
        this.credit = credit;
        this.departmentid = departmentid;
        this.deactive = deactive;
        this.entryby = entryby;
        this.endtrydate = endtrydate;
    }

    public SubjectsForBranchChangeId getId() {
        return this.id;
    }

    public void setId(SubjectsForBranchChangeId id) {
        this.id = id;
    }

    public SubjectMaster getSubjectmaster() {
        return this.subjectmaster;
    }

    public void setSubjectmaster(SubjectMaster subjectmaster) {
        this.subjectmaster = subjectmaster;
    }
//    public BranchMaster getBranchmaster() {
//        return this.branchmaster;
//    }
//
//    public void setBranchmaster(BranchMaster branchmaster) {
//        this.branchmaster = branchmaster;
//    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getDepartmentid() {
        return this.departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getEntryby() {
        return this.entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public String getEndtrydate() {
        return this.endtrydate;
    }

    public void setEndtrydate(String endtrydate) {
        this.endtrydate = endtrydate;
    }

}
