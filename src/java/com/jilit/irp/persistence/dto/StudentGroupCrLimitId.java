/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankit.kumar
 */
public class StudentGroupCrLimitId implements java.io.Serializable {
    
    private String groupid;    
    private String clientid;

    public StudentGroupCrLimitId() {
    }

    public StudentGroupCrLimitId(String groupid, String clientid) {
        this.groupid = groupid;
        this.clientid = clientid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
    
}
