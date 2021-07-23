/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author deepak.gupta
 */
public class StudentDataInformationId implements java.io.Serializable {

    private String clientid;
    private String informationid;

    public StudentDataInformationId() {
    }

    public StudentDataInformationId(String clientid, String informationid) {
        this.clientid = clientid;
        this.informationid = informationid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getInformationid() {
        return informationid;
    }

    public void setInformationid(String informationid) {
        this.informationid = informationid;
    }
    
    

}
