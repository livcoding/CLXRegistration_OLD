/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankit.kumar
 */
public class StudentGroupCrLimit implements java.io.Serializable {

    private StudentGroupCrLimitId id;
    private String programid;
    private String branchid;
    private String stynumber;
    private String maxcredit;
    private String mincredit;
    private StudentGroupMaster studentgroupmaster;

    public StudentGroupCrLimit(StudentGroupCrLimitId id, String programid, String branchid, String stynumber, String maxcredit, String mincredit, StudentGroupMaster studentgroupmaster) {
        this.id = id;
        this.programid = programid;
        this.branchid = branchid;
        this.stynumber = stynumber;
        this.maxcredit = maxcredit;
        this.mincredit = mincredit;
        this.studentgroupmaster = studentgroupmaster;
    }

    public StudentGroupMaster getStudentgroupmaster() {
        return studentgroupmaster;
    }

    public void setStudentgroupmaster(StudentGroupMaster studentgroupmaster) {
        this.studentgroupmaster = studentgroupmaster;
    }

    public StudentGroupCrLimit() {
    }

    public StudentGroupCrLimitId getId() {
        return id;
    }

    public void setId(StudentGroupCrLimitId id) {
        this.id = id;
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

    public String getStynumber() {
        return stynumber;
    }

    public void setStynumber(String stynumber) {
        this.stynumber = stynumber;
    }

    public String getMaxcredit() {
        return maxcredit;
    }

    public void setMaxcredit(String maxcredit) {
        this.maxcredit = maxcredit;
    }

    public String getMincredit() {
        return mincredit;
    }

    public void setMincredit(String mincredit) {
        this.mincredit = mincredit;
    }

    public String getIdStr() {
        return id.getGroupid() + ":" + id.getClientid();
    }

}
