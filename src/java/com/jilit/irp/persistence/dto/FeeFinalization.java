package com.jilit.irp.persistence.dto;
// Generated Apr 21, 2011 3:02:39 PM by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * FeeFinalization malkeet.singh
 */
public class FeeFinalization implements java.io.Serializable {

    private FeeStructureIndividualId id;
    private BigDecimal feeamount;
    private String acceptablecurrency;
    private String partiallyacceptable;
    private String feerefundable;
    private String sourcetransaction;
    private String conversionfactor;
    private String finalizationstatus;
    private String glpostingstatus;
    private String processid;
    private String postingamount;
    private Date processdatetime;
    private String processby;
    private Date finalizationdatetime;
    private String finalizationby;
    private BigDecimal concessionamount;
    private BigDecimal receiveamount;
    private BigDecimal dueamount;
    private BigDecimal waiveramount;
    private BigDecimal refundamount;
    private BigDecimal transferinamount;
    private BigDecimal transferoutamount;
    private String deactive;
    private Date feecollectionfromdate;
    private Date feecollectiontodate;
    private String programid, branchid;
    private String academicyear;
    private Date feepostingdate;
    private String adjustmentdramonut;
    private String adjustmentcredit;
    private String remarks;
    private String feerefunded;

    public FeeFinalization() {
    }

    public FeeFinalization(FeeStructureIndividualId id, BigDecimal feeamount, String acceptablecurrency, String partiallyacceptable, String feerefundable, String sourcetransaction, String conversionfactor, String finalizationstatus, String glpostingstatus, String processid, String postingamount, Date processdatetime, String processby, Date finalizationdatetime, String finalizationby, BigDecimal concessionamount, BigDecimal receiveamount, BigDecimal dueamount, BigDecimal waiveramount, BigDecimal refundamount, BigDecimal transferinamount, BigDecimal transferoutamount, String deactive, Date feecollectionfromdate, Date feecollectiontodate, String programid, String branchid, String academicyear, Date feepostingdate, String adjustmentdramonut, String adjustmentcredit, String remarks, String feerefunded) {
        this.id = id;
        this.feeamount = feeamount;
        this.acceptablecurrency = acceptablecurrency;
        this.partiallyacceptable = partiallyacceptable;
        this.feerefundable = feerefundable;
        this.sourcetransaction = sourcetransaction;
        this.conversionfactor = conversionfactor;
        this.finalizationstatus = finalizationstatus;
        this.glpostingstatus = glpostingstatus;
        this.processid = processid;
        this.postingamount = postingamount;
        this.processdatetime = processdatetime;
        this.processby = processby;
        this.finalizationdatetime = finalizationdatetime;
        this.finalizationby = finalizationby;
        this.concessionamount = concessionamount;
        this.receiveamount = receiveamount;
        this.dueamount = dueamount;
        this.waiveramount = waiveramount;
        this.refundamount = refundamount;
        this.transferinamount = transferinamount;
        this.transferoutamount = transferoutamount;
        this.deactive = deactive;
        this.feecollectionfromdate = feecollectionfromdate;
        this.feecollectiontodate = feecollectiontodate;
        this.programid = programid;
        this.branchid = branchid;
        this.academicyear = academicyear;
        this.feepostingdate = feepostingdate;
        this.adjustmentdramonut = adjustmentdramonut;
        this.adjustmentcredit = adjustmentcredit;
        this.remarks = remarks;
        this.feerefunded = feerefunded;
    }

    public FeeStructureIndividualId getId() {
        return id;
    }

    public void setId(FeeStructureIndividualId id) {
        this.id = id;
    }

    public BigDecimal getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(BigDecimal feeamount) {
        this.feeamount = feeamount;
    }

    public String getAcceptablecurrency() {
        return acceptablecurrency;
    }

    public void setAcceptablecurrency(String acceptablecurrency) {
        this.acceptablecurrency = acceptablecurrency;
    }

    public String getPartiallyacceptable() {
        return partiallyacceptable;
    }

    public void setPartiallyacceptable(String partiallyacceptable) {
        this.partiallyacceptable = partiallyacceptable;
    }

    public String getFeerefundable() {
        return feerefundable;
    }

    public void setFeerefundable(String feerefundable) {
        this.feerefundable = feerefundable;
    }

    public String getSourcetransaction() {
        return sourcetransaction;
    }

    public void setSourcetransaction(String sourcetransaction) {
        this.sourcetransaction = sourcetransaction;
    }

    public String getConversionfactor() {
        return conversionfactor;
    }

    public void setConversionfactor(String conversionfactor) {
        this.conversionfactor = conversionfactor;
    }

    public String getFinalizationstatus() {
        return finalizationstatus;
    }

    public void setFinalizationstatus(String finalizationstatus) {
        this.finalizationstatus = finalizationstatus;
    }

    public String getGlpostingstatus() {
        return glpostingstatus;
    }

    public void setGlpostingstatus(String glpostingstatus) {
        this.glpostingstatus = glpostingstatus;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public String getPostingamount() {
        return postingamount;
    }

    public void setPostingamount(String postingamount) {
        this.postingamount = postingamount;
    }

    public Date getProcessdatetime() {
        return processdatetime;
    }

    public void setProcessdatetime(Date processdatetime) {
        this.processdatetime = processdatetime;
    }

    public String getProcessby() {
        return processby;
    }

    public void setProcessby(String processby) {
        this.processby = processby;
    }

    public Date getFinalizationdatetime() {
        return finalizationdatetime;
    }

    public void setFinalizationdatetime(Date finalizationdatetime) {
        this.finalizationdatetime = finalizationdatetime;
    }

    public String getFinalizationby() {
        return finalizationby;
    }

    public void setFinalizationby(String finalizationby) {
        this.finalizationby = finalizationby;
    }

    public BigDecimal getConcessionamount() {
        return concessionamount;
    }

    public void setConcessionamount(BigDecimal concessionamount) {
        this.concessionamount = concessionamount;
    }

    public BigDecimal getReceiveamount() {
        return receiveamount;
    }

    public void setReceiveamount(BigDecimal receiveamount) {
        this.receiveamount = receiveamount;
    }

    public BigDecimal getDueamount() {
        return dueamount;
    }

    public void setDueamount(BigDecimal dueamount) {
        this.dueamount = dueamount;
    }

    public BigDecimal getWaiveramount() {
        return waiveramount;
    }

    public void setWaiveramount(BigDecimal waiveramount) {
        this.waiveramount = waiveramount;
    }

    public BigDecimal getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(BigDecimal refundamount) {
        this.refundamount = refundamount;
    }

    public BigDecimal getTransferinamount() {
        return transferinamount;
    }

    public void setTransferinamount(BigDecimal transferinamount) {
        this.transferinamount = transferinamount;
    }

    public BigDecimal getTransferoutamount() {
        return transferoutamount;
    }

    public void setTransferoutamount(BigDecimal transferoutamount) {
        this.transferoutamount = transferoutamount;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public Date getFeecollectionfromdate() {
        return feecollectionfromdate;
    }

    public void setFeecollectionfromdate(Date feecollectionfromdate) {
        this.feecollectionfromdate = feecollectionfromdate;
    }

    public Date getFeecollectiontodate() {
        return feecollectiontodate;
    }

    public void setFeecollectiontodate(Date feecollectiontodate) {
        this.feecollectiontodate = feecollectiontodate;
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

    public Date getFeepostingdate() {
        return feepostingdate;
    }

    public void setFeepostingdate(Date feepostingdate) {
        this.feepostingdate = feepostingdate;
    }

    public String getAdjustmentdramonut() {
        return adjustmentdramonut;
    }

    public void setAdjustmentdramonut(String adjustmentdramonut) {
        this.adjustmentdramonut = adjustmentdramonut;
    }

    public String getAdjustmentcredit() {
        return adjustmentcredit;
    }

    public void setAdjustmentcredit(String adjustmentcredit) {
        this.adjustmentcredit = adjustmentcredit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFeerefunded() {
        return feerefunded;
    }

    public void setFeerefunded(String feerefunded) {
        this.feerefunded = feerefunded;
    }


}
