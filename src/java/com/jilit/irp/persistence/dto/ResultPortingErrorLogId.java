/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author deepak.gupta
 */
public class ResultPortingErrorLogId implements Serializable {

    private String institutecode;
    private String logid;

     public ResultPortingErrorLogId() {
    }

    public ResultPortingErrorLogId(String institutecode, String logid) {
        this.institutecode = institutecode;
        this.logid = logid;
    }

    public String getInstitutecode() {
        return institutecode;
    }

    public void setInstitutecode(String institutecode) {
        this.institutecode = institutecode;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }
}
