/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;
import java.util.Date;
/**
 *
 * @author sunita.devi
 */
public class TT_AttendanceStatusId implements java.io.Serializable {


     private String instituteid;
     private String registrationid;
     private String staffid;
     private String subjectid;
     private Date attendancedate;
     private String slotid;
     private String subjectcomponentid;
     private String stafftype;

    public TT_AttendanceStatusId() {
    }

    public TT_AttendanceStatusId(String lognsessionid, String instituteid, String registrationid, String staffid, String subjectid, Date attendancedate, String slotid, String subjectcomponentid,String stafftype) {
       this.instituteid = instituteid;
       this.registrationid = registrationid;
       this.staffid = staffid;
       this.subjectid = subjectid;
       this.attendancedate = attendancedate;
       this.slotid = slotid;
       this.subjectcomponentid = subjectcomponentid;
       this.stafftype=stafftype;
    }
    public String getInstituteid() {
        return this.instituteid;
    }
    
    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }
    public String getRegistrationid() {
        return this.registrationid;
    }
    
    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }
    
    public String getSubjectid() {
        return this.subjectid;
    }
    
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public Date getAttendancedate() {
        return this.attendancedate;
    }
    
    public void setAttendancedate(Date attendancedate) {
        this.attendancedate = attendancedate;
    }
    public String getSlotid() {
        return this.slotid;
    }
    
    public void setSlotid(String slotid) {
        this.slotid = slotid;
    }
    public String getSubjectcomponentid() {
        return this.subjectcomponentid;
    }
    
    public void setSubjectcomponentid(String subjectcomponentid) {
        this.subjectcomponentid = subjectcomponentid;
    }
        public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

}
