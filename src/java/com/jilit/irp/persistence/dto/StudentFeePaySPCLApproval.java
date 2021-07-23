package com.jilit.irp.persistence.dto;
// Generated Sep 12, 2009 11:04:12 AM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;




/**
 Malkeet.singh
 */
public class StudentFeePaySPCLApproval  implements java.io.Serializable {


     private StudentFeePaySPCLApprovalId id;
     private String currencyid;
     private String dateofapproval;
     private String approvalupto;
     private String approvalammount;
     private String approvalremarks;
     private String deactive;

    public StudentFeePaySPCLApproval() {
    }

    public StudentFeePaySPCLApproval(StudentFeePaySPCLApprovalId id, String currencyid, String dateofapproval, String approvalupto, String approvalammount, String approvalremarks, String deactive) {
        this.id = id;
        this.currencyid = currencyid;
        this.dateofapproval = dateofapproval;
        this.approvalupto = approvalupto;
        this.approvalammount = approvalammount;
        this.approvalremarks = approvalremarks;
        this.deactive = deactive;
    }

    public StudentFeePaySPCLApprovalId getId() {
        return id;
    }

    public void setId(StudentFeePaySPCLApprovalId id) {
        this.id = id;
    }

    public String getCurrencyid() {
        return currencyid;
    }

    public void setCurrencyid(String currencyid) {
        this.currencyid = currencyid;
    }

    public String getDateofapproval() {
        return dateofapproval;
    }

    public void setDateofapproval(String dateofapproval) {
        this.dateofapproval = dateofapproval;
    }

    public String getApprovalupto() {
        return approvalupto;
    }

    public void setApprovalupto(String approvalupto) {
        this.approvalupto = approvalupto;
    }

    public String getApprovalammount() {
        return approvalammount;
    }

    public void setApprovalammount(String approvalammount) {
        this.approvalammount = approvalammount;
    }

    public String getApprovalremarks() {
        return approvalremarks;
    }

    public void setApprovalremarks(String approvalremarks) {
        this.approvalremarks = approvalremarks;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

}


