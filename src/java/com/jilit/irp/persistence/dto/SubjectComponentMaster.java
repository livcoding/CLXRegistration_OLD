/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankit.kumar
 */
public class SubjectComponentMaster implements java.io.Serializable  {

    private String subjectcomponentcode;
    private String subjectcomponentid;
    private String ltppassingmarks;
    private String totalccpmarks;
    private String noofhours;
    private String noofclassinaweek;
    private String byltp;
    private String totalmarks;
    private String passingmarks;
    private String totalclasses;
    private String deactive;

    public SubjectComponentMaster() {

    }

    public String getSubjectcomponentcode() {
        return subjectcomponentcode;
    }

    public void setSubjectcomponentcode(String subjectcomponentcode) {
        this.subjectcomponentcode = subjectcomponentcode;
    }

    public String getSubjectcomponentid() {
        return subjectcomponentid;
    }

    public void setSubjectcomponentid(String subjectcomponentid) {
        this.subjectcomponentid = subjectcomponentid;
    }

    public String getLtppassingmarks() {
        return ltppassingmarks;
    }

    public void setLtppassingmarks(String ltppassingmarks) {
        this.ltppassingmarks = ltppassingmarks;
    }

    public String getTotalccpmarks() {
        return totalccpmarks;
    }

    public void setTotalccpmarks(String totalccpmarks) {
        this.totalccpmarks = totalccpmarks;
    }

    public String getNoofhours() {
        return noofhours;
    }

    public void setNoofhours(String noofhours) {
        this.noofhours = noofhours;
    }

    public String getNoofclassinaweek() {
        return noofclassinaweek;
    }

    public void setNoofclassinaweek(String noofclassinaweek) {
        this.noofclassinaweek = noofclassinaweek;
    }

    public String getByltp() {
        return byltp;
    }

    public void setByltp(String byltp) {
        this.byltp = byltp;
    }

    public String getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(String totalmarks) {
        this.totalmarks = totalmarks;
    }

    public String getPassingmarks() {
        return passingmarks;
    }

    public void setPassingmarks(String passingmarks) {
        this.passingmarks = passingmarks;
    }

    public String getTotalclasses() {
        return totalclasses;
    }

    public void setTotalclasses(String totalclasses) {
        this.totalclasses = totalclasses;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

}
