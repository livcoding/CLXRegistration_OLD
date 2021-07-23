/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.util;

import java.io.Serializable;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author ashutosh1.kumar
 */
@Component
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class JIRPSessionInfo implements Serializable {

    String selectedinstituteid;
    String selectedinstituteuniqueid;
    String selectedcompanyid;
    String selectedlocationid;
    String selectedlibraryid;
    String selectedtransporterid;
    String selectedclientid;
    String username;
    String usertype;
    String userid;
    String membercode;
    String memberid;
    String ipaddress;
    HttpSession session;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
        
    public JIRPSessionInfo() {
    }
 
    public void removeSession() {
        getSession().invalidate();
    }
    
  
    public String getSelectedinstituteid() {
        return selectedinstituteid;
    }

    public void setSelectedinstituteid(String selectedinstituteid) {
        this.selectedinstituteid = selectedinstituteid;
    }

    public String getSelectedinstituteuniqueid() {
        return selectedinstituteuniqueid;
    }

    public void setSelectedinstituteuniqueid(String selectedinstituteuniqueid) {
        this.selectedinstituteuniqueid = selectedinstituteuniqueid;
    }

    public String getSelectedcompanyid() {
        return selectedcompanyid;
    }

    public void setSelectedcompanyid(String selectedcompanyid) {
        this.selectedcompanyid = selectedcompanyid;
    }

    public String getSelectedlocationid() {
        return selectedlocationid;
    }

    public void setSelectedlocationid(String selectedlocationid) {
        this.selectedlocationid = selectedlocationid;
    }

    public String getSelectedlibraryid() {
        return selectedlibraryid;
    }

    public void setSelectedlibraryid(String selectedlibraryid) {
        this.selectedlibraryid = selectedlibraryid;
    }

    public String getSelectedtransporterid() {
        return selectedtransporterid;
    }

    public void setSelectedtransporterid(String selectedtransporterid) {
        this.selectedtransporterid = selectedtransporterid;
    }

    public String getSelectedclientid() {
        return selectedclientid;
    }

    public void setSelectedclientid(String selectedclientid) {
        this.selectedclientid = selectedclientid;
    }
    
    
    
}
