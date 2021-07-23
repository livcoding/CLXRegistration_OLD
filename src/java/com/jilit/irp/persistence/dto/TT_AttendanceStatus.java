/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author sunita.devi
 */
public class TT_AttendanceStatus implements java.io.Serializable {

     private TT_AttendanceStatusId id;
     private String staffname;
     private String subjectcode;
     private String subjectname;
     private String subjectcomponentdesc;
     private String allocationday;
     private String fromtime;
     private String totime;
     private String status;
     private String ttreferenceid;
     private String tttransid;
     private String ttstafftype;
     private String ttstaffid;

        public TT_AttendanceStatus() {
    }

	
    public TT_AttendanceStatus(TT_AttendanceStatusId id) {
        this.id = id;
    }
    public TT_AttendanceStatus(TT_AttendanceStatusId id,String staffname, String subjectcode, String subjectname, String subjectcomponentdesc, String allocationday, String fromtime, String totime, String status) {
       this.id = id;
       this.staffname = staffname;
       this.subjectcode = subjectcode;
       this.subjectname = subjectname;
       this.subjectcomponentdesc = subjectcomponentdesc;
       this.allocationday = allocationday;
       this.fromtime = fromtime;
       this.totime = totime;
       this.status = status;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

   

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public TT_AttendanceStatusId getId() {
        return id;
    }

    public void setId(TT_AttendanceStatusId id) {
        this.id = id;
    }

    public String getSubjectcode() {
        return subjectcode;
    }

    public void setSubjectcode(String sbjectcode) {
        this.subjectcode = sbjectcode;
    }

    public String getAllocationday() {
        return allocationday;
    }

    public void setAllocationday(String allocationday) {
        this.allocationday = allocationday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectcomponentdesc() {
        return subjectcomponentdesc;
    }

    public void setSubjectcomponentdesc(String subjectcomponentdesc) {
        this.subjectcomponentdesc = subjectcomponentdesc;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }
     public String getTtreferenceid() {
        return ttreferenceid;
    }

    public void setTtreferenceid(String ttreferenceid) {
        this.ttreferenceid = ttreferenceid;
    }

    public String getTtstaffid() {
        return ttstaffid;
    }

    public void setTtstaffid(String ttstaffid) {
        this.ttstaffid = ttstaffid;
    }

    public String getTtstafftype() {
        return ttstafftype;
    }

    public void setTtstafftype(String ttstafftype) {
        this.ttstafftype = ttstafftype;
    }

    public String getTttransid() {
        return tttransid;
    }

    public void setTttransid(String tttransid) {
        this.tttransid = tttransid;
    }

}


