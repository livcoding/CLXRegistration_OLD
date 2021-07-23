/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author sunny.singhal
 */
public class V_ProgramSubjectTagging implements Serializable{

    private String instituteid;
    private String registrationid;
    private String registrationcode;
    private String registrationdesc;
    private String programsubjectid;
    private String academicyear;
    private String programid;
    private String programcode;
    private String programdesc;
    private String sectionid;
    private String sectioncode;
    private String sectiondesc;
    private Short  stynumber;
    private String basketid;
    private String basketcode;
    private String basketdesc;
    private String subjecteid;
    private String subjectcode;
    private String subjectdesc;
    private String pstpopulated;
    private String subjectrunning;
    private String deactive;
    private String customfinalized;
    private String departmentid;
    private String departmentcode;
    private String department;
    private String subjectcomponentid;
    private BigDecimal ltppassingmarks;
    private BigDecimal totalccpmarks;
    private BigDecimal noofhours;
    private BigDecimal noofclassinaweek;
    private String subjectcomponentcode;
    private String subjectcomponentdesc;
    private Short classperweekformula;
    private BigDecimal calccpmarks;
    private String subjectid;
    private String subjecttypeid;
    private String subjecttype;
    private String subjecttypedesc;
  //  private String subjectareadesc;
    //private String subjectareacode;
    private Short  credits;
    private Short noofstudents;
    private Short alloted;






    public V_ProgramSubjectTagging() {
    }

    public V_ProgramSubjectTagging(V_ProgramSubjectTagging dto) {
        this.instituteid = dto.getInstituteid();
        this.registrationid = dto.getRegistrationid();
        this.registrationcode = dto.getRegistrationcode();
        this.registrationdesc = dto.getRegistrationdesc();
        this.programsubjectid = dto.getProgramsubjectid();
        this.academicyear = dto.getAcademicyear();
        this.programid = dto.getProgramid();
        this.programcode = dto.getProgramcode();
        this.programdesc = dto.getProgramdesc();
        this.sectionid = dto.getSectionid();
        this.sectioncode = dto.getSectioncode();
        this.sectiondesc = dto.getSectiondesc();
        this.stynumber = dto.getStynumber();
        this.basketid = dto.getBasketid();
        this.basketcode = dto.getBasketcode();
        this.basketdesc = dto.getBasketdesc();
        this.subjecteid = dto.getSubjecteid();
        this.subjectcode = dto.getSubjectcode();
        this.subjectdesc = dto.getSubjectdesc();
        this.pstpopulated = dto.getPstpopulated();
        this.subjectrunning = dto.getSubjectrunning();
        this.deactive = dto.getDeactive();
        this.customfinalized = dto.getCustomfinalized();
        this.departmentid = dto.getDepartmentid();
        this.departmentcode = dto.getDepartmentcode();
        this.department = dto.getDepartment();
        this.subjectcomponentid = dto.getSubjectcomponentid();
        this.ltppassingmarks = dto.getLtppassingmarks();
        this.totalccpmarks = dto.getTotalccpmarks();
        this.noofhours = dto.getNoofhours();
        this.noofclassinaweek = dto.getNoofclassinaweek();
        this.subjectcomponentcode = dto.getSubjectcomponentcode();
        this.subjectcomponentdesc = dto.getSubjectcomponentdesc();
        this.classperweekformula = dto.getClassperweekformula();
        this.calccpmarks = dto.getCalccpmarks();
        this.subjectid=dto.getSectionid();
        this.subjecttypeid=dto.getSubjecttypeid();
        this.subjecttype = dto.getSubjecttype();
       // this.subjectareacode = dto.getSubjectareacode();
       // this.subjectareadesc = dto.getSubjectareadesc();
        this.subjecttypedesc = dto.getSubjecttypedesc();
        this.credits = dto.getCredits();
        this.noofstudents = dto.getNoofstudents();
        this.alloted = dto.getAlloted();
    }

    

    public V_ProgramSubjectTagging(String subjecttype, String subjecttypedesc)
    {
       this.subjecttype = subjecttype;
       this.subjecttypedesc = subjecttypedesc;
    }

//    public V_ProgramSubjectTagging(String string, String string0) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    public V_ProgramSubjectTagging(String registrationcode, String registrationdesc ,String registrationid)
    {
      this.registrationcode = registrationcode;
      this.registrationdesc = registrationdesc;
      this.registrationid=registrationid;
    }



    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public String getBasketid() {
        return basketid;
    }

    public void setBasketid(String basketid) {
        this.basketid = basketid;
    }

     public String getBasketcode() {
        return basketcode;
    }

    public void setBasketcode(String basketcode) {
        this.basketcode = basketcode;
    }

    public String getBasketdesc() {
        return basketdesc;
    }

    public void setBasketdesc(String basketdesc) {
        this.basketdesc = basketdesc;
    }

    public BigDecimal getCalccpmarks() {
        return calccpmarks;
    }

    public void setCalccpmarks(BigDecimal calccpmarks) {
        this.calccpmarks = calccpmarks;
    }

    public Short getClassperweekformula() {
        return classperweekformula;
    }

    public void setClassperweekformula(Short classperweekformula) {
        this.classperweekformula = classperweekformula;
    }

    public String getCustomfinalized() {
        return customfinalized;
    }

    public void setCustomfinalized(String customfinalized) {
        this.customfinalized = customfinalized;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public BigDecimal getLtppassingmarks() {
        return ltppassingmarks;
    }

    public void setLtppassingmarks(BigDecimal ltppassingmarks) {
        this.ltppassingmarks = ltppassingmarks;
    }

    public BigDecimal getNoofclassinaweek() {
        return noofclassinaweek;
    }

    public void setNoofclassinaweek(BigDecimal noofclassinaweek) {
        this.noofclassinaweek = noofclassinaweek;
    }

    public BigDecimal getNoofhours() {
        return noofhours;
    }

    public void setNoofhours(BigDecimal noofhours) {
        this.noofhours = noofhours;
    }

    public String getProgramcode() {
        return programcode;
    }

    public void setProgramcode(String programcode) {
        this.programcode = programcode;
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

    public String getProgramsubjectid() {
        return programsubjectid;
    }

    public void setProgramsubjectid(String programsubjectid) {
        this.programsubjectid = programsubjectid;
    }

    public String getPstpopulated() {
        return pstpopulated;
    }

    public void setPstpopulated(String pstpopulated) {
        this.pstpopulated = pstpopulated;
    }

    public String getRegistrationcode() {
        return registrationcode;
    }

    public void setRegistrationcode(String registrationcode) {
        this.registrationcode = registrationcode;
    }

    public String getRegistrationdesc() {
        return registrationdesc;
    }

    public void setRegistrationdesc(String registrationdesc) {
        this.registrationdesc = registrationdesc;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
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

    public Short getStynumber() {
        return stynumber;
    }

    public void setStynumber(Short stynumber) {
        this.stynumber = stynumber;
    }

    public String getSubjectcode() {
        return subjectcode;
    }

    public void setSubjectcode(String subjectcode) {
        this.subjectcode = subjectcode;
    }

    public String getSubjectcomponentcode() {
        return subjectcomponentcode;
    }

    public void setSubjectcomponentcode(String subjectcomponentcode) {
        this.subjectcomponentcode = subjectcomponentcode;
    }

    public String getSubjectcomponentdesc() {
        return subjectcomponentdesc;
    }

    public void setSubjectcomponentdesc(String subjectcomponentdesc) {
        this.subjectcomponentdesc = subjectcomponentdesc;
    }

    public String getSubjectcomponentid() {
        return subjectcomponentid;
    }

    public void setSubjectcomponentid(String subjectcomponentid) {
        this.subjectcomponentid = subjectcomponentid;
    }

    public String getSubjectdesc() {
        return subjectdesc;
    }

    public void setSubjectdesc(String subjectdesc) {
        this.subjectdesc = subjectdesc;
    }

    public String getSubjecteid() {
        return subjecteid;
    }

    public void setSubjecteid(String subjecteid) {
        this.subjecteid = subjecteid;
    }

    public String getSubjectrunning() {
        return subjectrunning;
    }

    public void setSubjectrunning(String subjectrunning) {
        this.subjectrunning = subjectrunning;
    }

    public BigDecimal getTotalccpmarks() {
        return totalccpmarks;
    }

    public void setTotalccpmarks(BigDecimal totalccpmarks) {
        this.totalccpmarks = totalccpmarks;
    }
    public String getSubjecttypeid() {
        return subjecttypeid;
    }

    public void setSubjecttypeid(String subjecttypeid) {
        this.subjecttypeid = subjecttypeid;
    }

     public String getSubjecttype() {
        return subjecttype;
    }

    public void setSubjecttype(String subjecttype) {
        this.subjecttype = subjecttype;
    }
//     public String getSubjectareacode() {
//        return subjectareacode;
//    }
//
//    public void setSubjectareacode(String subjectareacode) {
//        this.subjectareacode = subjectareacode;
//    }
//
//    public String getSubjectareadesc() {
//        return subjectareadesc;
//    }
//
//    public void setSubjectareadesc(String subjectareadesc) {
//        this.subjectareadesc = subjectareadesc;
//    }

    public Short getCredits() {
        return credits;
    }

    public void setCredits(Short credits) {
        this.credits = credits;
    }

    public Short getAlloted() {
        return alloted;
    }

    public void setAlloted(Short alloted) {
        this.alloted = alloted;
    }

    public  String getSubjecttypedesc()
    {
       return subjecttypedesc ;
    }

    public void setSubjecttypedesc(String subjecttypedesc)
    {
        this.subjecttypedesc = subjecttypedesc;
    }

    public Short getNoofstudents() {
        return noofstudents;
    }

    public void setNoofstudents(Short noofstudents) {
        this.noofstudents = noofstudents;
    }
}
