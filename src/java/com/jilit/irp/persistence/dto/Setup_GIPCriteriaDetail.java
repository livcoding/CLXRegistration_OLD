package com.jilit.irp.persistence.dto;
// Generated 30 OCT, 2019 

import java.math.BigDecimal;
import java.util.Date;

/**
 * malkeet.singh
 */
public class Setup_GIPCriteriaDetail implements java.io.Serializable {

    private Setup_GIPCriteriaDetailId id;
    private Setup_GIPCriteria setup_gipCriteria;
    private ExamGradeMaster examGradeMaster;
    private Date entrydatetime;

    public Setup_GIPCriteriaDetail() {
    }

    public Setup_GIPCriteriaDetail(Setup_GIPCriteriaDetailId id, Setup_GIPCriteria setup_gipCriteria, ExamGradeMaster examGradeMaster, Date entrydatetime) {
        this.id = id;
        this.setup_gipCriteria = setup_gipCriteria;
        this.examGradeMaster = examGradeMaster;
        this.entrydatetime = entrydatetime;
    }

    public Setup_GIPCriteriaDetailId getId() {
        return id;
    }

    public void setId(Setup_GIPCriteriaDetailId id) {
        this.id = id;
    }

    public Setup_GIPCriteria getSetup_gipCriteria() {
        return setup_gipCriteria;
    }

    public void setSetup_gipCriteria(Setup_GIPCriteria setup_gipCriteria) {
        this.setup_gipCriteria = setup_gipCriteria;
    }

    public Date getEntrydatetime() {
        return entrydatetime;
    }

    public void setEntrydatetime(Date entrydatetime) {
        this.entrydatetime = entrydatetime;
    }

    public ExamGradeMaster getExamGradeMaster() {
        return examGradeMaster;
    }

    public void setExamGradeMaster(ExamGradeMaster examGradeMaster) {
        this.examGradeMaster = examGradeMaster;
    }

}
