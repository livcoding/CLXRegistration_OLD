package com.jilit.irp.persistence.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberControl implements java.io.Serializable {

    private TempRollNumberControlId id;
    private TempRollNumberSetup temprollnumbersetup;
    private String lastno;
    private BigDecimal lastrunningno;
    private Date lastgenerationdate;

    public TempRollNumberControl() {

    }

    public TempRollNumberControlId getId() {
        return id;
    }

    public void setId(TempRollNumberControlId id) {
        this.id = id;
    }

    public TempRollNumberSetup getTemprollnumbersetup() {
        return temprollnumbersetup;
    }

    public void setTemprollnumbersetup(TempRollNumberSetup temprollnumbersetup) {
        this.temprollnumbersetup = temprollnumbersetup;
    }

    public String getLastno() {
        return lastno;
    }

    public void setLastno(String lastno) {
        this.lastno = lastno;
    }

    public BigDecimal getLastrunningno() {
        return lastrunningno;
    }

    public void setLastrunningno(BigDecimal lastrunningno) {
        this.lastrunningno = lastrunningno;
    }

    public Date getLastgenerationdate() {
        return lastgenerationdate;
    }

    public void setLastgenerationdate(Date lastgenerationdate) {
        this.lastgenerationdate = lastgenerationdate;
    }

}
