/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author Malkeet Singh
 */
public class SubjectWiseBackPaperFee implements java.io.Serializable {

    private SubjectWiseBackPaperFeeId id;
    private int feeamount;
    
    public SubjectWiseBackPaperFee() {

    }

    public SubjectWiseBackPaperFeeId getId() {
        return id;
    }

    public void setId(SubjectWiseBackPaperFeeId id) {
        this.id = id;
    }

    public int getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(int feeamount) {
        this.feeamount = feeamount;
    }

    


}
