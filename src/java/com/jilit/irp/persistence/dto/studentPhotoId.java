/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author akshya.gaur
 */
public class studentPhotoId implements Serializable{

     private String studentid;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}
