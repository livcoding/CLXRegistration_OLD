package com.jilit.irp.persistence.dto;
// Generated Apr 21, 2011 3:02:39 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;

public class DebarreasonMaster implements java.io.Serializable {

    private DebarreasonMasterId id;
    private String gradeid;
    private String reasoncode;
    private String reasondesc;
    private String deactive;
    private String remarks;
    private String reasontype;
    private String summerregfeerequierd;
    private String supplimentoryregfeerequired;
    private String debarinsupplimentoryreg;
    private Date entrydate;

    public DebarreasonMaster() {
    }

    public DebarreasonMaster(DebarreasonMasterId id, String gradeid, String reasoncode, String reasondesc, String deactive, String remarks, String reasontype, String summerregfeerequierd, String supplimentoryregfeerequired, String debarinsupplimentoryreg, Date entrydate) {
        this.id = id;
        this.gradeid = gradeid;
        this.reasoncode = reasoncode;
        this.reasondesc = reasondesc;
        this.deactive = deactive;
        this.remarks = remarks;
        this.reasontype = reasontype;
        this.summerregfeerequierd = summerregfeerequierd;
        this.supplimentoryregfeerequired = supplimentoryregfeerequired;
        this.debarinsupplimentoryreg = debarinsupplimentoryreg;
        this.entrydate = entrydate;
    }

    public DebarreasonMasterId getId() {
        return id;
    }

    public void setId(DebarreasonMasterId id) {
        this.id = id;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getReasoncode() {
        return reasoncode;
    }

    public void setReasoncode(String reasoncode) {
        this.reasoncode = reasoncode;
    }

    public String getReasondesc() {
        return reasondesc;
    }

    public void setReasondesc(String reasondesc) {
        this.reasondesc = reasondesc;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReasontype() {
        return reasontype;
    }

    public void setReasontype(String reasontype) {
        this.reasontype = reasontype;
    }

    public String getSummerregfeerequierd() {
        return summerregfeerequierd;
    }

    public void setSummerregfeerequierd(String summerregfeerequierd) {
        this.summerregfeerequierd = summerregfeerequierd;
    }

    public String getSupplimentoryregfeerequired() {
        return supplimentoryregfeerequired;
    }

    public void setSupplimentoryregfeerequired(String supplimentoryregfeerequired) {
        this.supplimentoryregfeerequired = supplimentoryregfeerequired;
    }

    public String getDebarinsupplimentoryreg() {
        return debarinsupplimentoryreg;
    }

    public void setDebarinsupplimentoryreg(String debarinsupplimentoryreg) {
        this.debarinsupplimentoryreg = debarinsupplimentoryreg;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

}
