/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.sql.Blob;

/**
 *
 * @author priya.sharma
 */
public class GuestStudentPhotoFetch  implements Serializable{
    private String gueststudentid;
//private String deactive;
private Blob sphoto;
private Blob ssignature;

    public String getGueststudentid() {
        return gueststudentid;
    }

    public void setGueststudentid(String gueststudentid) {
        this.gueststudentid = gueststudentid;
    }

    public Blob getSphoto() {
        return sphoto;
    }

    public void setSphoto(Blob sphoto) {
        this.sphoto = sphoto;
    }

    public Blob getSsignature() {
        return ssignature;
    }

    public void setSsignature(Blob ssignature) {
        this.ssignature = ssignature;
    }
}
