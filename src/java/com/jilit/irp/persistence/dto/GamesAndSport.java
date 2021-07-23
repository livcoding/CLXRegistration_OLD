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
public class GamesAndSport  implements java.io.Serializable {
    GamesAndSportId id;
    String gamedetail;
    String deactive;

     public GamesAndSport() {
    }

    public GamesAndSport(GamesAndSportId id, String gamedetail, String deactive) {
        this.id = id;
        this.gamedetail = gamedetail;
        this.deactive = deactive;
    }
    
     
     
     
    public GamesAndSportId getId() {
        return id;
    }

    public void setId(GamesAndSportId id) {
        this.id = id;
    }

    public String getGamedetail() {
        return gamedetail;
    }

    public void setGamedetail(String gamedetail) {
        this.gamedetail = gamedetail;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

   
    
    
    
}
