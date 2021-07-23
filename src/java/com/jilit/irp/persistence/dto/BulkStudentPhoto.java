/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author mohit1.kumar
 */
public class BulkStudentPhoto implements Serializable{
private String studentid;
private String deactive;
private byte[] photo;

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public byte[] getFatherphoto() {
        return fatherphoto;
    }

    public void setFatherphoto(byte[] fatherphoto) {
        this.fatherphoto = fatherphoto;
    }

    public byte[] getGuadianphoto() {
        return guadianphoto;
    }

    public void setGuadianphoto(byte[] guadianphoto) {
        this.guadianphoto = guadianphoto;
    }

    public byte[] getMotherphoto() {
        return motherphoto;
    }

    public void setMotherphoto(byte[] motherphoto) {
        this.motherphoto = motherphoto;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
private byte[] signature;
private byte[] fatherphoto;
private byte[] motherphoto;
private byte[] guadianphoto;
}
