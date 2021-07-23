/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.sql.Blob;
import org.hibernate.Hibernate;
import java.io.Serializable;


/**
 *
 * @author Mohit1.kumar
 */
public class StudentPhotoFetch implements Serializable{
private String studentid;
private String deactive;
private Blob photo;
private Blob signature;


    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public void setStudentphoto(byte[] studentphoto) {
        this.photo = Hibernate.createBlob(studentphoto);
    }
    public void setStudentsign(byte[] studentphoto) {
        this.signature = Hibernate.createBlob(studentphoto);
    }

    public Blob getSignature() {
        return signature;
    }

    public void setSignature(Blob signature) {
        this.signature = signature;
    }



}
