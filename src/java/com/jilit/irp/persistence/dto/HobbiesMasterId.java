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
 
public class HobbiesMasterId  implements java.io.Serializable {


     private String clientid;
     private String hobbyname;

    public HobbiesMasterId() {
    }

    public HobbiesMasterId(String clientid, String hobbyname) {
       this.clientid = clientid;
       this.hobbyname = hobbyname;
    }
    
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getHobbyname() {
        return hobbyname;
    }

    public void setHobbyname(String hobbyname) {
        this.hobbyname = hobbyname;
    }
    
     

}


