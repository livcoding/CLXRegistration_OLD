/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.sql.Blob;
import org.hibernate.Hibernate;

public class EmployeePhoto implements Serializable {

    private String employeeid;
    private Blob photo;
    private Blob signature;

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public void setPhoto(byte[] employeephoto) {
        this.photo = Hibernate.createBlob(employeephoto);
    }
    
    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public Blob getSignature() {
        return signature;
    }

    public void setSignature(Blob signature) {
        this.signature = signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = Hibernate.createBlob(signature);
    }
    
}
