/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author Nazar.Mohammad
 */
public class Sct_UserRightsCriteraBasedId implements java.io.Serializable {
    
    private String userid;
     private String rightsid;
     private String rightsbasedon;
     private String rightsbasedid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRightsid() {
        return rightsid;
    }

    public void setRightsid(String rightsid) {
        this.rightsid = rightsid;
    }

    public String getRightsbasedon() {
        return rightsbasedon;
    }

    public void setRightsbasedon(String rightsbasedon) {
        this.rightsbasedon = rightsbasedon;
    }

    public String getRightsbasedid() {
        return rightsbasedid;
    }

    public void setRightsbasedid(String rightsbasedid) {
        this.rightsbasedid = rightsbasedid;
    }

    public Sct_UserRightsCriteraBasedId()
    {
        
    }
    public Sct_UserRightsCriteraBasedId(String userid,String rightsid,String rightsbasedon,String rightsbasedid){
        this.userid=userid;
        this.rightsid=rightsid;
        this.rightsbasedon=rightsbasedon;
        this.rightsbasedid=rightsbasedid;
    }
    
}
