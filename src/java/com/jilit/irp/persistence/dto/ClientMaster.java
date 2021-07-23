/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.sql.Blob;
import java.util.Date;

/**
 *
 * @author ankur.goyal
 */
public class ClientMaster implements java.io.Serializable{
    
private String clientid;
private String clientname;
private String otprequired;
private String passwordrequierd;
private String passwordvalidationmode;
private String clientdetail;
private byte[] logofile;
private byte[] watermarkfile;
private String fromemailid;
private String fromemailpwd;
private String remarks;
private String entrybyuserid;
private Date entrybydatetime;
private String lastupdatedbyuserid;
private Date lastupdateddatetime;
private String captcharequired;
private String passwordrecoveymode;
private String complainticketreqired;
private String staffbdaynotication;
private String staffannvnotification;
private String navigationtobecaptured;
private String deactive;
private String fromemailhost;
private String fromemailport;
private String clientsmsurl;
private String resetpasswordmode;
private String defaultresetpassword;

public ClientMaster(){
    
}

public ClientMaster(ClientMaster dto){

this.clientname=dto.clientname;
this.otprequired=dto.otprequired;
this. passwordrequierd=dto.passwordrequierd;
this.passwordvalidationmode=dto.passwordvalidationmode;
this.clientdetail=dto.clientdetail;
this.logofile=dto.logofile;
this.watermarkfile=dto.watermarkfile;
this.fromemailid=dto.fromemailid;
this.fromemailpwd=dto.fromemailpwd;
this.remarks=dto.remarks;
this.entrybyuserid=dto.entrybyuserid;
this.entrybydatetime=dto.entrybydatetime;
this.lastupdatedbyuserid=dto.lastupdatedbyuserid;
this.lastupdateddatetime=dto.lastupdateddatetime;
this.captcharequired=dto.captcharequired;
this.passwordrecoveymode=dto.passwordrecoveymode;
this.complainticketreqired=dto.complainticketreqired;
this.staffbdaynotication=dto.staffbdaynotication;
this.staffannvnotification=dto.staffannvnotification;
this.navigationtobecaptured=dto.navigationtobecaptured;
this.deactive=dto.deactive;
this.fromemailhost=dto.fromemailhost;
this.fromemailport=dto.fromemailport;
this.clientsmsurl=dto.clientsmsurl;
this.resetpasswordmode=dto.resetpasswordmode;
this.defaultresetpassword=dto.defaultresetpassword;

}

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getOtprequired() {
        return otprequired;
    }

    public void setOtprequired(String otprequired) {
        this.otprequired = otprequired;
    }

    public String getPasswordrequierd() {
        return passwordrequierd;
    }

    public void setPasswordrequierd(String passwordrequierd) {
        this.passwordrequierd = passwordrequierd;
    }

    public String getPasswordvalidationmode() {
        return passwordvalidationmode;
    }

    public void setPasswordvalidationmode(String passwordvalidationmode) {
        this.passwordvalidationmode = passwordvalidationmode;
    }

    public String getClientdetail() {
        return clientdetail;
    }

    public void setClientdetail(String clientdetail) {
        this.clientdetail = clientdetail;
    }

    public byte[] getLogofile() {
        return logofile;
    }

    public void setLogofile(byte[] logofile) {
        this.logofile = logofile;
    }

    public byte[] getWatermarkfile() {
        return watermarkfile;
    }

    public void setWatermarkfile(byte[] watermarkfile) {
        this.watermarkfile = watermarkfile;
    }

    public String getFromemailid() {
        return fromemailid;
    }

    public void setFromemailid(String fromemailid) {
        this.fromemailid = fromemailid;
    }

    public String getFromemailpwd() {
        return fromemailpwd;
    }

    public void setFromemailpwd(String fromemailpwd) {
        this.fromemailpwd = fromemailpwd;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntrybyuserid() {
        return entrybyuserid;
    }

    public void setEntrybyuserid(String entrybyuserid) {
        this.entrybyuserid = entrybyuserid;
    }

    public Date getEntrybydatetime() {
        return entrybydatetime;
    }

    public void setEntrybydatetime(Date entrybydatetime) {
        this.entrybydatetime = entrybydatetime;
    }

    public String getLastupdatedbyuserid() {
        return lastupdatedbyuserid;
    }

    public void setLastupdatedbyuserid(String lastupdatedbyuserid) {
        this.lastupdatedbyuserid = lastupdatedbyuserid;
    }

    public Date getLastupdateddatetime() {
        return lastupdateddatetime;
    }

    public void setLastupdateddatetime(Date lastupdateddatetime) {
        this.lastupdateddatetime = lastupdateddatetime;
    }

    public String getCaptcharequired() {
        return captcharequired;
    }

    public void setCaptcharequired(String captcharequired) {
        this.captcharequired = captcharequired;
    }

    public String getPasswordrecoveymode() {
        return passwordrecoveymode;
    }

    public void setPasswordrecoveymode(String passwordrecoveymode) {
        this.passwordrecoveymode = passwordrecoveymode;
    }

    public String getComplainticketreqired() {
        return complainticketreqired;
    }

    public void setComplainticketreqired(String complainticketreqired) {
        this.complainticketreqired = complainticketreqired;
    }

    public String getStaffbdaynotication() {
        return staffbdaynotication;
    }

    public void setStaffbdaynotication(String staffbdaynotication) {
        this.staffbdaynotication = staffbdaynotication;
    }

    public String getStaffannvnotification() {
        return staffannvnotification;
    }

    public void setStaffannvnotification(String staffannvnotification) {
        this.staffannvnotification = staffannvnotification;
    }

    public String getNavigationtobecaptured() {
        return navigationtobecaptured;
    }

    public void setNavigationtobecaptured(String navigationtobecaptured) {
        this.navigationtobecaptured = navigationtobecaptured;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getFromemailhost() {
        return fromemailhost;
    }

    public void setFromemailhost(String fromemailhost) {
        this.fromemailhost = fromemailhost;
    }

    public String getFromemailport() {
        return fromemailport;
    }

    public void setFromemailport(String fromemailport) {
        this.fromemailport = fromemailport;
    }

    public String getClientsmsurl() {
        return clientsmsurl;
    }

    public void setClientsmsurl(String clientsmsurl) {
        this.clientsmsurl = clientsmsurl;
    }

    public String getResetpasswordmode() {
        return resetpasswordmode;
    }

    public void setResetpasswordmode(String resetpasswordmode) {
        this.resetpasswordmode = resetpasswordmode;
    }

    public String getDefaultresetpassword() {
        return defaultresetpassword;
    }

    public void setDefaultresetpassword(String defaultresetpassword) {
        this.defaultresetpassword = defaultresetpassword;
    }
}
