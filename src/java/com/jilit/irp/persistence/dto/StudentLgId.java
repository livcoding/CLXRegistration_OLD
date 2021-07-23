/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author singh.jaswinder
 */
public class StudentLgId implements java.io.Serializable {

    private String studentid;
    private short slno;

    public StudentLgId() {
    }

    public StudentLgId(String studentid, short slno) {
       this.studentid = studentid;
       this.slno = slno;
    }

    public short getSlno() {
        return slno;
    }

    public void setSlno(short slno) {
        this.slno = slno;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}
