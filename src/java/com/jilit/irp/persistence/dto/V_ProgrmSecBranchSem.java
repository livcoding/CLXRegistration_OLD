/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author singh.jaswinder
 */
public class V_ProgrmSecBranchSem implements Serializable {

    private String instituteid;
    private String programid;
    private String subsectionid;
    private String sectionid;
    private String branchid;
    private String programcode;
    private String programdesc;
    private String programdeactive;
    private String sectioncode;
    private String sectiondesc;
    private String academicyear;
    private String sectiontype;
    private Short stynumber;
    private String stypattern;
    private String stydesc;
    private Short startsty;
    private Short endsty;
    private String branchcode;
    private String branchdesc;
    private String subsectioncode;
    private String subsectiondesc;
    private Integer sectionmaxstudent;
    private Integer subsectionmaxstudent;

    public V_ProgrmSecBranchSem() {
    }

    public V_ProgrmSecBranchSem(V_ProgrmSecBranchSem dto) {
        this.instituteid = dto.getInstituteid();
        this.programid = dto.getProgramid();
        this.subsectionid = dto.getSubsectionid();
        this.sectionid = dto.getSectionid();
        this.branchid = dto.getBranchid();
        this.programcode = dto.getProgramcode();
        this.programdesc = dto.getProgramdesc();
        this.programdeactive = dto.getProgramdeactive();
        this.sectioncode = dto.getSectioncode();
        this.sectiondesc = dto.getSectiondesc();
        this.academicyear = dto.getAcademicyear();
        this.sectiontype = dto.getSectiontype();
        this.stynumber = dto.getStynumber();
        this.stypattern = dto.getStypattern();
        this.stydesc = dto.getStydesc();
        this.startsty = dto.getStartsty();
        this.endsty = dto.getEndsty();
        this.branchcode = dto.getBranchcode();
        this.branchdesc = dto.getBranchdesc();
        this.subsectioncode = dto.getSubsectioncode();
        this.subsectiondesc = dto.getSubsectiondesc();
        this.sectionmaxstudent=dto.getSectionmaxstudent();
        this.subsectionmaxstudent=dto.getSectionmaxstudent();
    }

    public Integer getSectionmaxstudent() {
        return sectionmaxstudent;
    }

    public void setSectionmaxstudent(Integer sectionmaxstudent) {
        this.sectionmaxstudent = sectionmaxstudent;
    }

    public Integer getSubsectionmaxstudent() {
        return subsectionmaxstudent;
    }

    public void setSubsectionmaxstudent(Integer subsectionmaxstudent) {
        this.subsectionmaxstudent = subsectionmaxstudent;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchdesc() {
        return branchdesc;
    }

    public void setBranchdesc(String branchdesc) {
        this.branchdesc = branchdesc;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public Short getEndsty() {
        return endsty;
    }

    public void setEndsty(Short endsty) {
        this.endsty = endsty;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public String getProgramcode() {
        return programcode;
    }

    public void setProgramcode(String programcode) {
        this.programcode = programcode;
    }

    public String getProgramdeactive() {
        return programdeactive;
    }

    public void setProgramdeactive(String programdeactive) {
        this.programdeactive = programdeactive;
    }

    public String getProgramdesc() {
        return programdesc;
    }

    public void setProgramdesc(String programdesc) {
        this.programdesc = programdesc;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getSectioncode() {
        return sectioncode;
    }

    public void setSectioncode(String sectioncode) {
        this.sectioncode = sectioncode;
    }

    public String getSectiondesc() {
        return sectiondesc;
    }

    public void setSectiondesc(String sectiondesc) {
        this.sectiondesc = sectiondesc;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getSectiontype() {
        return sectiontype;
    }

    public void setSectiontype(String sectiontype) {
        this.sectiontype = sectiontype;
    }

    public Short getStartsty() {
        return startsty;
    }

    public void setStartsty(Short startsty) {
        this.startsty = startsty;
    }

    public String getStydesc() {
        return stydesc;
    }

    public void setStydesc(String stydesc) {
        this.stydesc = stydesc;
    }

    public Short getStynumber() {
        return stynumber;
    }

    public void setStynumber(Short stynumber) {
        this.stynumber = stynumber;
    }

    public String getStypattern() {
        return stypattern;
    }

    public void setStypattern(String stypattern) {
        this.stypattern = stypattern;
    }

    public String getSubsectioncode() {
        return subsectioncode;
    }

    public void setSubsectioncode(String subsectioncode) {
        this.subsectioncode = subsectioncode;
    }

    public String getSubsectiondesc() {
        return subsectiondesc;
    }

    public void setSubsectiondesc(String subsectiondesc) {
        this.subsectiondesc = subsectiondesc;
    }

    public String getSubsectionid() {
        return subsectionid;
    }

    public void setSubsectionid(String subsectionid) {
        this.subsectionid = subsectionid;
    }
    
   
}
