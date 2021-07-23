/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author Nazar.Mohammad
 */
public class Sct_UserRightsCriteraBased implements java.io.Serializable {

    private Sct_UserRightsCriteraBasedId id;
    private Character alwaysvalid;
    private Date validfrom;
    private Date validupto;
    private Character deactive;

    public Sct_UserRightsCriteraBasedId getId() {
        return id;
    }

    public void setId(Sct_UserRightsCriteraBasedId id) {
        this.id = id;
    }

    public Character getAlwaysvalid() {
        return alwaysvalid;
    }

    public void setAlwaysvalid(Character alwaysvalid) {
        this.alwaysvalid = alwaysvalid;
    }

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidupto() {
        return validupto;
    }

    public void setValidupto(Date validupto) {
        this.validupto = validupto;
    }

    public Character getDeactive() {
        return deactive;
    }

    public void setDeactive(Character deactive) {
        this.deactive = deactive;
    }

}
