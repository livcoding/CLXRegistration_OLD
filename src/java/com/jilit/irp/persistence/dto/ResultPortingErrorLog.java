/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author deepak.gupta
 */
public class ResultPortingErrorLog implements Serializable {

    private ResultPortingErrorLogId id;
    private String tablename;
    private String errortype;
    private String errormessage;
    private Date datetime;

    public ResultPortingErrorLog() {
    }

    public ResultPortingErrorLog(ResultPortingErrorLogId id, String tablename, String errortype, String errormessage, Date datetime) {
        this.id = id;
        this.tablename = tablename;
        this.errortype = errortype;
        this.errormessage = errortype;
        this.datetime = datetime;
    }

    public ResultPortingErrorLog(ResultPortingErrorLog dto) {
        this.id = dto.getId();
        this.tablename = dto.getTablename();
        this.errortype = dto.getErrormessage();
        this.errortype = dto.getErrortype();
        this.datetime = dto.getDatetime();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getErrortype() {
        return errortype;
    }

    public void setErrortype(String errortype) {
        this.errortype = errortype;
    }

    public ResultPortingErrorLogId getId() {
        return id;
    }

    public void setId(ResultPortingErrorLogId id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getIdStr() {
        return id.getInstitutecode() + "--" + id.getLogid();
    }
}
