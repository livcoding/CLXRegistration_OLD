package com.jilit.irp.persistence.dto;
// Generated Sep 12, 2009 11:04:12 AM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Malkeet.singh
 */
public class StudentFeePaySPCLApprovalId implements java.io.Serializable {

    private String instituteid;
    private String registrationid;
        private String eventid;
    private byte stynumber;
    private String stytypeid;
    private String quotaid;
    private String studentid;
    private String feeheadid;

    public StudentFeePaySPCLApprovalId() {
    }

    public StudentFeePaySPCLApprovalId(String instituteid, String registrationid, String eventid, byte stynumber, String stytypeid, String quotaid, String studentid, String feeheadid) {
        this.instituteid = instituteid;
        this.registrationid = registrationid;
        this.eventid = eventid;
        this.stynumber = stynumber;
        this.stytypeid = stytypeid;
        this.quotaid = quotaid;
        this.studentid = studentid;
        this.feeheadid = feeheadid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public byte getStynumber() {
        return stynumber;
    }

    public void setStynumber(byte stynumber) {
        this.stynumber = stynumber;
    }

    public String getStytypeid() {
        return stytypeid;
    }

    public void setStytypeid(String stytypeid) {
        this.stytypeid = stytypeid;
    }

    public String getQuotaid() {
        return quotaid;
    }

    public void setQuotaid(String quotaid) {
        this.quotaid = quotaid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getFeeheadid() {
        return feeheadid;
    }

    public void setFeeheadid(String feeheadid) {
        this.feeheadid = feeheadid;
    }

}
