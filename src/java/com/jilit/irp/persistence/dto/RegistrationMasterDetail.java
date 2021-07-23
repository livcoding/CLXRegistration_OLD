/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ashutosh1.kumar
 */
public class RegistrationMasterDetail implements java.io.Serializable {
    
    private RegistrationMasterDetailId id;
    private RegistrationMaster registrationmaster;
    private BranchMaster branchmaster;
    private Date regdatefrom;
    private Date regdateto;    
    private Date extendedtilldate ;    
    private String remarks;
    private BigDecimal lateregfeeamount;

    public BigDecimal getLateregfeeamount() {
        return lateregfeeamount;
    }

    public void setLateregfeeamount(BigDecimal lateregfeeamount) {
        this.lateregfeeamount = lateregfeeamount;
    }

    public RegistrationMasterDetail() {
    }

    public RegistrationMasterDetail(RegistrationMasterDetailId id, RegistrationMaster registrationmaster, BranchMaster branchmaster, Date regdatefrom, Date regdateto, Date extendedtilldate, String remarks, BigDecimal lateregfeeamount) {
        this.id = id;
        this.registrationmaster = registrationmaster;
        this.branchmaster = branchmaster;
        this.regdatefrom = regdatefrom;
        this.regdateto = regdateto;
        this.extendedtilldate = extendedtilldate;
        this.remarks = remarks;
        this.lateregfeeamount = lateregfeeamount;
    }

    public RegistrationMasterDetailId getId() {
        return id;
    }

    public void setId(RegistrationMasterDetailId id) {
        this.id = id;
    }

    public RegistrationMaster getRegistrationmaster() {
        return registrationmaster;
    }

    public void setRegistrationmaster(RegistrationMaster registrationmaster) {
        this.registrationmaster = registrationmaster;
    }

    public BranchMaster getBranchmaster() {
        return branchmaster;
    }

    public void setBranchmaster(BranchMaster branchmaster) {
        this.branchmaster = branchmaster;
    }

    public Date getRegdatefrom() {
        return regdatefrom;
    }

    public void setRegdatefrom(Date regdatefrom) {
        this.regdatefrom = regdatefrom;
    }

    public Date getRegdateto() {
        return regdateto;
    }

    public void setRegdateto(Date regdateto) {
        this.regdateto = regdateto;
    }

    public Date getExtendedtilldate() {
        return extendedtilldate;
    }

    public void setExtendedtilldate(Date extendedtilldate) {
        this.extendedtilldate = extendedtilldate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}
