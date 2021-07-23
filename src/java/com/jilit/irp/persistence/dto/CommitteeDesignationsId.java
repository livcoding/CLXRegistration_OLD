/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author campus.trainee
 */
public class CommitteeDesignationsId implements java.io.Serializable {

    private String instituteid;
    private String designationid;


    public CommitteeDesignationsId() {
    }

    public CommitteeDesignationsId(String instituteid, String designationid) {
        this.instituteid = instituteid;
        this.designationid = designationid;
    }

    public String getInstituteid() {
        return this.instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getDesignationid() {
        return this.designationid;
    }

    public void setDesignationid(String designationid) {
        this.designationid = designationid;
    }
}
