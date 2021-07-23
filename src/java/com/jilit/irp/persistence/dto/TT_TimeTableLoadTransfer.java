/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author yatendra.singh
 */
public class TT_TimeTableLoadTransfer implements java.io.Serializable {

  private TT_TimeTableAllocation tt_timetableallocation;
  private String loadtransferid;
  private String tttransid;
  private String registrationid;
  private String instituteid;
  private String allocationday;
  private String slotid;
  private String stafftype;
  private String staffid;
  private String loadtransfertostaffid;
  private String loadtransfertostafftype;
  private Date loadtransferfrom;
  private Date loadtransferupto;
  private String loadtransferremarks;
  private String loadtransferapproved;
  private String approvedby;
  private Date approveddate;
  private String approveremarks;
  private String deactive;
  private String entryby;
  private Date entrydate;

    public TT_TimeTableLoadTransfer(){
    }

    public TT_TimeTableLoadTransfer(String loadtransferid, String tttransid, String registrationid, String instituteid, String allocationday, String slotid, String stafftype, String staffid, String loadtransfertostaffid, String loadtransfertostafftype, Date loadtransferfrom, Date loadtransferupto, String loadtransferremarks, String loadtransferapproved, String approvedby, Date approveddate, String approveremarks, String deactive, String entryby, Date entrydate) {
        this.loadtransferid = loadtransferid;
        this.tttransid = tttransid;
        this.registrationid = registrationid;
        this.instituteid = instituteid;
        this.allocationday = allocationday;
        this.slotid = slotid;
        this.stafftype = stafftype;
        this.staffid = staffid;
        this.loadtransfertostaffid = loadtransfertostaffid;
        this.loadtransfertostafftype = loadtransfertostafftype;
        this.loadtransferfrom = loadtransferfrom;
        this.loadtransferupto = loadtransferupto;
        this.loadtransferremarks = loadtransferremarks;
        this.loadtransferapproved = loadtransferapproved;
        this.approvedby = approvedby;
        this.approveddate = approveddate;
        this.approveremarks = approveremarks;
        this.deactive = deactive;
        this.entryby = entryby;
        this.entrydate = entrydate;
    }

    public String getAllocationday() {
        return allocationday;
    }

    public void setAllocationday(String allocationday) {
        this.allocationday = allocationday;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Date getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(Date approveddate) {
        this.approveddate = approveddate;
    }

    public String getApproveremarks() {
        return approveremarks;
    }

    public void setApproveremarks(String approveremarks) {
        this.approveremarks = approveremarks;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getLoadtransferapproved() {
        return loadtransferapproved;
    }

    public void setLoadtransferapproved(String loadtransferapproved) {
        this.loadtransferapproved = loadtransferapproved;
    }

    public Date getLoadtransferfrom() {
        return loadtransferfrom;
    }

    public void setLoadtransferfrom(Date loadtransferfrom) {
        this.loadtransferfrom = loadtransferfrom;
    }

    public String getLoadtransferid() {
        return loadtransferid;
    }

    public void setLoadtransferid(String loadtransferid) {
        this.loadtransferid = loadtransferid;
    }

    public String getLoadtransferremarks() {
        return loadtransferremarks;
    }

    public void setLoadtransferremarks(String loadtransferremarks) {
        this.loadtransferremarks = loadtransferremarks;
    }

    public String getLoadtransfertostaffid() {
        return loadtransfertostaffid;
    }

    public void setLoadtransfertostaffid(String loadtransfertostaffid) {
        this.loadtransfertostaffid = loadtransfertostaffid;
    }

    public String getLoadtransfertostafftype() {
        return loadtransfertostafftype;
    }

    public void setLoadtransfertostafftype(String loadtransfertostafftype) {
        this.loadtransfertostafftype = loadtransfertostafftype;
    }

    public Date getLoadtransferupto() {
        return loadtransferupto;
    }

    public void setLoadtransferupto(Date loadtransferupto) {
        this.loadtransferupto = loadtransferupto;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    public String getSlotid() {
        return slotid;
    }

    public void setSlotid(String slotid) {
        this.slotid = slotid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    public TT_TimeTableAllocation getTt_timetableallocation() {
        return tt_timetableallocation;
    }

    public void setTt_timetableallocation(TT_TimeTableAllocation tt_timetableallocation) {
        this.tt_timetableallocation = tt_timetableallocation;
    }

    public String getTttransid() {
        return tttransid;
    }

    public void setTttransid(String tttransid) {
        this.tttransid = tttransid;
    }






}
