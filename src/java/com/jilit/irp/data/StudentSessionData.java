/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.data;

import java.io.Serializable;

/**
 *
 * @author sunny.singhal
 */
public class StudentSessionData implements Serializable{

    //private String loginid;
    //private String instituteid;
    //private String selectedinstituteid;
    //private String userid;
    //private String usertype;
    //private String username;
    //private String membercode;
    private String programid;
    private String branchid;
    //private String tittlehead;

    
    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    
//    public String getLoginid() {
//        return loginid;
//    }
//
//    public void setLoginid(String loginid) {
//        this.loginid = loginid;
//    }
//
//    public String getMembercode() {
//        return membercode;
//    }
//
//    public void setMembercode(String membercode) {
//        this.membercode = membercode;
//    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

//    public String getTittlehead() {
//        return tittlehead;
//    }
//
//    public void setTittlehead(String tittlehead) {
//        this.tittlehead = tittlehead;
//    }
//
//    public String getUserid() {
//        return userid;
//    }
//
//    public void setUserid(String userid) {
//        this.userid = userid;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getUsertype() {
//        return usertype;
//    }
//
//    public void setUsertype(String usertype) {
//        this.usertype = usertype;
//    }
//
//    public String getInstituteid() {
//        return instituteid;
//    }
//
//    public void setInstituteid(String instituteid) {
//        this.instituteid = instituteid;
//    }
//
//    public String getSelectedinstituteid() {
//        return selectedinstituteid;
//    }
//
//    public void setSelectedinstituteid(String selectedinstituteid) {
//        this.selectedinstituteid = selectedinstituteid;
//    }


}
