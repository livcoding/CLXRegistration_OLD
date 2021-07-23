package com.jilit.irp.persistence.dto;
// Generated Jan 7, 2017 10:17:58 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;

/**
 *
 * @author deepak.gupta
 */
public class ProgramEligibilityExams implements java.io.Serializable {

    private ProgramEligibilityExamsId id;
    private ProgramMaster programmaster;
    private EligibilityExamInfo eligibilityexaminfo;
    private Short seqid;
    private String entryby;
    private Date entrydate;

    public ProgramEligibilityExams() {
    }

    public ProgramEligibilityExams(ProgramEligibilityExams master) {
        this.id = id;
        this.entryby = master.entryby;
        this.entrydate = master.entrydate;
        this.seqid = master.seqid;
    }

    public ProgramEligibilityExams(ProgramEligibilityExamsId id, ProgramMaster programmaster, EligibilityExamInfo eligibilityexaminfo) {
        this.id = id;
        this.programmaster = programmaster;
        this.eligibilityexaminfo = eligibilityexaminfo;
    }

    public ProgramEligibilityExams(ProgramEligibilityExamsId id, ProgramMaster programmaster, EligibilityExamInfo eligibilityexaminfo, Short seqid, String entryby, Date entrydate) {
        this.id = id;
        this.programmaster = programmaster;
        this.eligibilityexaminfo = eligibilityexaminfo;
        this.seqid = seqid;
        this.entryby = entryby;
        this.entrydate = entrydate;
    }

    public ProgramEligibilityExamsId getId() {
        return this.id;
    }

    public void setId(ProgramEligibilityExamsId id) {
        this.id = id;
    }

    public ProgramMaster getProgrammaster() {
        return this.programmaster;
    }

    public void setProgrammaster(ProgramMaster programmaster) {
        this.programmaster = programmaster;
    }

    public EligibilityExamInfo getEligibilityexaminfo() {
        return this.eligibilityexaminfo;
    }

    public void setEligibilityexaminfo(EligibilityExamInfo eligibilityexaminfo) {
        this.eligibilityexaminfo = eligibilityexaminfo;
    }

    public Short getSeqid() {
        return this.seqid;
    }

    public void setSeqid(Short seqid) {
        this.seqid = seqid;
    }

    public String getEntryby() {
        return this.entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public Date getEntrydate() {
        return this.entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getIdStr() {
        return (id.getInstituteid() + "::" + id.getProgramid() + "::" + id.getEeexamid());
    }
}


