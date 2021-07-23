/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

/**
 *
 * @author ashok.singh
 */
public class StudentSgpaCgpaId implements java.io.Serializable {

     private String instituteid;
     private String studentid;
     private byte stynumber;

    public StudentSgpaCgpaId() {
    }

    public StudentSgpaCgpaId(String instituteid, String studentid, byte stynumber) {
       this.instituteid = instituteid;
       this.studentid = studentid;
       this.stynumber = stynumber;
    }

    public String getInstituteid() {
        return this.instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getStudentid() {
        return this.studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
    public byte getStynumber() {
        return this.stynumber;
    }

    public void setStynumber(byte stynumber) {
        this.stynumber = stynumber;
    }





}



