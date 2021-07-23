/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ashok.singh
 */
  

public class HobbiesMaster implements java.io.Serializable {

    private HobbiesMasterId id; 
    private String hobbydetail; 
    private String deactive;  

    public HobbiesMaster(HobbiesMaster dto) {
        this.id = dto.getId();
        this.hobbydetail = dto.getHobbydetail();
        this.deactive = dto.getDeactive();
    }

    public HobbiesMaster() {
    }

    public HobbiesMasterId getId() {
        return id;
    }

    public void setId(HobbiesMasterId id) {
        this.id = id;
    }

    public String getHobbydetail() {
        return hobbydetail;
    }

    public void setHobbydetail(String hobbydetail) {
        this.hobbydetail = hobbydetail;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

   
}


