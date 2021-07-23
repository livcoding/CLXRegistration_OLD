package com.jilit.irp.persistence.dto;
// Generated Apr 21, 2011 3:02:39 PM by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * StudentResultDetail malkeet.singh
 */
public class StudentResultDetail implements java.io.Serializable {

    private StudentResultDetailId id;
    private String gradeid;
    private BigDecimal gradepoint;
    private BigDecimal coursecreditpoint;
    private BigDecimal earnedcredit;
    private BigDecimal sgpapoints;
    private BigDecimal cgpapoints;
    private String fail;
    private String deactive;
    private String break_slno;
    private int stynumber;
    private Date resultdate;
    private String subjectid;
    private String reregtype;
    private BigDecimal deficitcredit;
    private String auditsubject;
    private String improvement;
    private String fstid;

    public StudentResultDetail() {
    }

    public StudentResultDetail(StudentResultDetailId id, String gradeid, BigDecimal gradepoint, BigDecimal coursecreditpoint, BigDecimal earnedcredit, BigDecimal sgpapoints, BigDecimal cgpapoints, String fail, String deactive, String break_slno, int stynumber, Date resultdate, String subjectid, String reregtype, BigDecimal deficitcredit, String auditsubject, String improvement, String fstid) {
        this.id = id;
        this.gradeid = gradeid;
        this.gradepoint = gradepoint;
        this.coursecreditpoint = coursecreditpoint;
        this.earnedcredit = earnedcredit;
        this.sgpapoints = sgpapoints;
        this.cgpapoints = cgpapoints;
        this.fail = fail;
        this.deactive = deactive;
        this.break_slno = break_slno;
        this.stynumber = stynumber;
        this.resultdate = resultdate;
        this.subjectid = subjectid;
        this.reregtype = reregtype;
        this.deficitcredit = deficitcredit;
        this.auditsubject = auditsubject;
        this.improvement = improvement;
        this.fstid = fstid;
    }

    public StudentResultDetailId getId() {
        return id;
    }

    public void setId(StudentResultDetailId id) {
        this.id = id;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public BigDecimal getGradepoint() {
        return gradepoint;
    }

    public void setGradepoint(BigDecimal gradepoint) {
        this.gradepoint = gradepoint;
    }

    public BigDecimal getCoursecreditpoint() {
        return coursecreditpoint;
    }

    public void setCoursecreditpoint(BigDecimal coursecreditpoint) {
        this.coursecreditpoint = coursecreditpoint;
    }

    public BigDecimal getEarnedcredit() {
        return earnedcredit;
    }

    public void setEarnedcredit(BigDecimal earnedcredit) {
        this.earnedcredit = earnedcredit;
    }

    public BigDecimal getSgpapoints() {
        return sgpapoints;
    }

    public void setSgpapoints(BigDecimal sgpapoints) {
        this.sgpapoints = sgpapoints;
    }

    public BigDecimal getCgpapoints() {
        return cgpapoints;
    }

    public void setCgpapoints(BigDecimal cgpapoints) {
        this.cgpapoints = cgpapoints;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getBreak_slno() {
        return break_slno;
    }

    public void setBreak_slno(String break_slno) {
        this.break_slno = break_slno;
    }

    public int getStynumber() {
        return stynumber;
    }

    public void setStynumber(int stynumber) {
        this.stynumber = stynumber;
    }

    public Date getResultdate() {
        return resultdate;
    }

    public void setResultdate(Date resultdate) {
        this.resultdate = resultdate;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getReregtype() {
        return reregtype;
    }

    public void setReregtype(String reregtype) {
        this.reregtype = reregtype;
    }

    public BigDecimal getDeficitcredit() {
        return deficitcredit;
    }

    public void setDeficitcredit(BigDecimal deficitcredit) {
        this.deficitcredit = deficitcredit;
    }

    public String getAuditsubject() {
        return auditsubject;
    }

    public void setAuditsubject(String auditsubject) {
        this.auditsubject = auditsubject;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public String getFstid() {
        return fstid;
    }

    public void setFstid(String fstid) {
        this.fstid = fstid;
    }

}
