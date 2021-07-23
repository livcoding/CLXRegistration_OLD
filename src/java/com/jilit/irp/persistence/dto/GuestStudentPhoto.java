/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.sql.Blob;
import org.hibernate.Hibernate;


/**
 *
 * @author Priya.sharma
 */
public class GuestStudentPhoto implements Serializable{
private String gueststudentid;
//private String deactive;
private byte[] sphoto;
private byte[] ssignature;

    public byte[] getSphoto() {
        return sphoto;
    }

    public void setSphoto(byte[] sphoto) {
        this.sphoto = sphoto;
    }

    public byte[] getSsignature() {
        return ssignature;
    }

    public void setSsignature(byte[] ssignature) {
        this.ssignature = ssignature;
    }

    public String getGueststudentid() {
        return gueststudentid;
    }

    public void setGueststudentid(String gueststudentid) {
        this.gueststudentid = gueststudentid;
    }

   
}
