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
public class GamesAndSportId  implements java.io.Serializable {
    String clientid;
    String gamename;

    public GamesAndSportId() {
    }

    public GamesAndSportId(String clientid, String gamename) {
        this.clientid = clientid;
        this.gamename = gamename;
    }

    
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }
    
    
}
