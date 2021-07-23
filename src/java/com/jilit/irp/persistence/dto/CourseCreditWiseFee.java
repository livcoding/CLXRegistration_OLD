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
public class CourseCreditWiseFee implements java.io.Serializable {
    
    private CourseCreditWiseFeeId id;   
    private double creditfrom;   
    private double creditto;   
    private int feeamount;
    
    public CourseCreditWiseFee(){
        
    }

    public CourseCreditWiseFeeId getId() {
        return id;
    }

    public void setId(CourseCreditWiseFeeId id) {
        this.id = id;
    }

    public double getCreditfrom() {
        return creditfrom;
    }

    public void setCreditfrom(double creditfrom) {
        this.creditfrom = creditfrom;
    }

    public double getCreditto() {
        return creditto;
    }

    public void setCreditto(double creditto) {
        this.creditto = creditto;
    }

    public int getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(int feeamount) {
        this.feeamount = feeamount;
    }

  
    
    
}
