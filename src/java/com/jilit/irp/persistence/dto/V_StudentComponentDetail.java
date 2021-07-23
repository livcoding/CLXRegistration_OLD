/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author v.kumar
 */
public class V_StudentComponentDetail implements Serializable
{
    private String instituteid;
    private String fstid;
    private String studentid;
    private String stafftype;
    private String staffid;
    private String employeename;
    private String employeecode;
    private String registrationid;
    private String academicyear;
    private String programid;
    private String programcode;
    private String programdesc;
    private String branchid;
    private String sectionid;
    private String sectioncode;
    private String subsectionid;
    private String subsectioncode;
    private Short stynumber;
    private String stytypeid;
    private String stytype;
    private String basketid;
    private String basketcode;
    private String subjectid;
    private String subjectcode;
    private String subjectdesc;
    private String subjectcomponentid;
    private String mergewithfstid;
    private Short durationofclass;
    private Short noofclassinaweek;
    private String enrollmentno;
    private String name;
    private String tempenrollmentno;
    private String studentactivestatus;
    private String subjectcomponentcode;
    private String subjectcomponentdesc;
    private Short classperweekformula;  
    private String studentfstid;
   // private String branchdesc;






    public V_StudentComponentDetail() {
    }

    public V_StudentComponentDetail(V_StudentComponentDetail dto) {
        this.instituteid = dto.getInstituteid();
        this.fstid = dto.getFstid();
        this.studentid = dto.getStudentid();
        this.stafftype = dto.getStafftype();
        this.staffid = dto.getStaffid();
        this.employeename = dto.getEmployeename();
        this.employeecode = dto.getEmployeecode();
        this.registrationid = dto.getRegistrationid();
        this.academicyear = dto.getAcademicyear();
        this.programid = dto.getProgramid();
        this.programcode = dto.getProgramcode();
        this.sectionid = dto.getSectionid();
        this.sectioncode = dto.getSectioncode();
        this.subsectionid = dto.getSubsectionid();
        this.subsectioncode = dto.getSubsectioncode();
        this.stynumber = dto.getStynumber();
        this.stytypeid = dto.getStytypeid();
        this.stytype = dto.getStytype();
        this.basketid = dto.getBasketid();
        this.basketcode = dto.getBasketcode();
        this.subjectid = dto.getSubjectid();
        this.subjectcode = dto.getSubjectcode();
        this.subjectdesc = dto.getSubjectdesc();
        this.subjectcomponentid = dto.getSubjectcomponentid();
        this.subjectcomponentcode = dto.getSubjectcomponentcode();
        this.subjectcomponentdesc = dto.getSubjectcomponentdesc();
        this.classperweekformula = dto.getClassperweekformula();
        this.mergewithfstid = dto.getMergewithfstid();
        this.durationofclass = dto.getDurationofclass();
        this.noofclassinaweek = dto.getNoofclassinaweek();
        this.enrollmentno = dto.getEnrollmentno();
        this.studentfstid=dto.getStudentfstid();
        this.name = dto.getName();
        this.tempenrollmentno = dto.getTempenrollmentno();
        this.studentactivestatus = dto.getStudentactivestatus();
      

    }

     public V_StudentComponentDetail(String subjectid,String subjectcode,String subjectdesc) {
        this.subjectid = subjectid;
        this.subjectcode = subjectcode;
        this.subjectdesc = subjectdesc;
     }

      public V_StudentComponentDetail(String subjectcomponentid,String subjectcomponentcode,String subjectcomponentdesc, String instituteid) {
        this.subjectcomponentid = subjectcomponentid;
        this.subjectcomponentcode = subjectcomponentcode;
        this.subjectcomponentdesc = subjectcomponentdesc;
        this.instituteid = instituteid;
     }

      public V_StudentComponentDetail(String sectionid,String sectioncode) {
        this.sectionid = sectionid;
        this.sectioncode = sectioncode;

     }

      public V_StudentComponentDetail(String subsectionid,String subsectioncode,String sectionid,String sectioncode,String instituteid) {
        this.subsectionid = subsectionid;
        this.subsectioncode = subsectioncode;
        this.sectionid = sectionid;
        this.sectioncode = sectioncode;
        this.instituteid = instituteid;

     }


    /**
     * @return the instituteid
     */
    public String getInstituteid() {
        return instituteid;
    }

    /**
     * @param instituteid the instituteid to set
     */
    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    /**
     * @return the fstid
     */
    public String getFstid() {
        return fstid;
    }

    /**
     * @param fstid the fstid to set
     */
    public void setFstid(String fstid) {
        this.fstid = fstid;
    }

    /**
     * @return the studentid
     */
    public String getStudentid() {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    /**
     * @return the stafftype
     */
    public String getStafftype() {
        return stafftype;
    }

    /**
     * @param stafftype the stafftype to set
     */
    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    /**
     * @return the staffid
     */
    public String getStaffid() {
        return staffid;
    }

    /**
     * @param staffid the staffid to set
     */
    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    /**
     * @return the employeename
     */
    public String getEmployeename() {
        return employeename;
    }

    /**
     * @param employeename the employeename to set
     */
    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    /**
     * @return the employeecode
     */
    public String getEmployeecode() {
        return employeecode;
    }

    /**
     * @param employeecode the employeecode to set
     */
    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    /**
     * @return the registrationid
     */
    public String getRegistrationid() {
        return registrationid;
    }

    /**
     * @param registrationid the registrationid to set
     */
    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    /**
     * @return the academicyear
     */
    public String getAcademicyear() {
        return academicyear;
    }

    /**
     * @param academicyear the academicyear to set
     */
    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    /**
     * @return the programid
     */
    public String getProgramid() {
        return programid;
    }

    /**
     * @param programid the programid to set
     */
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    /**
     * @return the programcode
     */
    public String getProgramcode() {
        return programcode;
    }

    /**
     * @param programcode the programcode to set
     */
    public void setProgramcode(String programcode) {
        this.programcode = programcode;
    }

    /**
     * @return the sectionid
     */
    public String getSectionid() {
        return sectionid;
    }

    /**
     * @param sectionid the sectionid to set
     */
    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    /**
     * @return the sectioncode
     */
    public String getSectioncode() {
        return sectioncode;
    }

    /**
     * @param sectioncode the sectioncode to set
     */
    public void setSectioncode(String sectioncode) {
        this.sectioncode = sectioncode;
    }

    /**
     * @return the subsectionid
     */
    public String getSubsectionid() {
        return subsectionid;
    }

    /**
     * @param subsectionid the subsectionid to set
     */
    public void setSubsectionid(String subsectionid) {
        this.subsectionid = subsectionid;
    }

    /**
     * @return the subsectioncode
     */
    public String getSubsectioncode() {
        return subsectioncode;
    }

    /**
     * @param subsectioncode the subsectioncode to set
     */
    public void setSubsectioncode(String subsectioncode) {
        this.subsectioncode = subsectioncode;
    }

    /**
     * @return the stynumber
     */
    public Short getStynumber() {
        return stynumber;
    }

    /**
     * @param stynumber the stynumber to set
     */
    public void setStynumber(Short stynumber) {
        this.stynumber = stynumber;
    }

    /**
     * @return the stytypeid
     */
    public String getStytypeid() {
        return stytypeid;
    }

    /**
     * @param stytypeid the stytypeid to set
     */
    public void setStytypeid(String stytypeid) {
        this.stytypeid = stytypeid;
    }

    public String getStytype() {
        return stytype;
    }

    public void setStytype(String stytype) {
        this.stytype = stytype;
    }

    /**
     * @return the basketid
     */
    public String getBasketid() {
        return basketid;
    }

    /**
     * @param basketid the basketid to set
     */
    public void setBasketid(String basketid) {
        this.basketid = basketid;
    }

    /**
     * @return the basketcode
     */
    public String getBasketcode() {
        return basketcode;
    }

    /**
     * @param basketcode the basketcode to set
     */
    public void setBasketcode(String basketcode) {
        this.basketcode = basketcode;
    }

    /**
     * @return the subjectid
     */
    public String getSubjectid() {
        return subjectid;
    }

    /**
     * @param subjectid the subjectid to set
     */
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * @return the subjectcode
     */
    public String getSubjectcode() {
        return subjectcode;
    }

    /**
     * @param subjectcode the subjectcode to set
     */
    public void setSubjectcode(String subjectcode) {
        this.subjectcode = subjectcode;
    }

    /**
     * @return the subjectdesc
     */
    public String getSubjectdesc() {
        return subjectdesc;
    }

    /**
     * @param subjectdesc the subjectdesc to set
     */
    public void setSubjectdesc(String subjectdesc) {
        this.subjectdesc = subjectdesc;
    }

    /**
     * @return the subjectcomponentid
     */
    public String getSubjectcomponentid() {
        return subjectcomponentid;
    }

    /**
     * @param subjectcomponentid the subjectcomponentid to set
     */
    public void setSubjectcomponentid(String subjectcomponentid) {
        this.subjectcomponentid = subjectcomponentid;
    }

    /**
     * @return the mergewithfstid
     */
    public String getMergewithfstid() {
        return mergewithfstid;
    }

    /**
     * @param mergewithfstid the mergewithfstid to set
     */
    public void setMergewithfstid(String mergewithfstid) {
        this.mergewithfstid = mergewithfstid;
    }

    /**
     * @return the durationofclass
     */
    public Short getDurationofclass() {
        return durationofclass;
    }

    /**
     * @param durationofclass the durationofclass to set
     */
    public void setDurationofclass(Short durationofclass) {
        this.durationofclass = durationofclass;
    }

    /**
     * @return the noofclassinaweek
     */
    public Short getNoofclassinaweek() {
        return noofclassinaweek;
    }

    /**
     * @param noofclassinaweek the noofclassinaweek to set
     */
    public void setNoofclassinaweek(Short noofclassinaweek) {
        this.noofclassinaweek = noofclassinaweek;
    }

    /**
     * @return the enrollmentno
     */
    public String getEnrollmentno() {
        return enrollmentno;
    }

    /**
     * @param enrollmentno the enrollmentno to set
     */
    public void setEnrollmentno(String enrollmentno) {
        this.enrollmentno = enrollmentno;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tempenrollmentno
     */
    public String getTempenrollmentno() {
        return tempenrollmentno;
    }

    /**
     * @param tempenrollmentno the tempenrollmentno to set
     */
    public void setTempenrollmentno(String tempenrollmentno) {
        this.tempenrollmentno = tempenrollmentno;
    }

    /**
     * @return the studentactivestatus
     */
    public String getStudentactivestatus() {
        return studentactivestatus;
    }

    /**
     * @param studentactivestatus the studentactivestatus to set
     */
    public void setStudentactivestatus(String studentactivestatus) {
        this.studentactivestatus = studentactivestatus;
    }

     public String getSubjectcomponentcode() {
        return subjectcomponentcode;
    }

    public void setSubjectcomponentcode(String subjectcomponentcode) {
        this.subjectcomponentcode = subjectcomponentcode;
    }
    public Short getClassperweekformula() {
        return classperweekformula;
    }

    public void setClassperweekformula(Short classperweekformula) {
        this.classperweekformula = classperweekformula;
    }

    public String getSubjectcomponentdesc() {
        return subjectcomponentdesc;
    }

    public void setSubjectcomponentdesc(String subjectcomponentdesc) {
        this.subjectcomponentdesc = subjectcomponentdesc;
    }
    public String getIdStr()
    {
        return getEnrollmentno()+":"+getName()+":"+getStudentid();
    }

    /**
     * @return the branchid
     */
    public String getBranchid() {
        return branchid;
    }

    /**
     * @param branchid the branchid to set
     */
    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    /**
     * @return the branchcode
     */
//    public String getBranchcode() {
//        return branchcode;
//    }

    /**
     * @param branchcode the branchcode to set
     */
//    public void setBranchcode(String branchcode) {
//        this.branchcode = branchcode;
//    }

    /**
     * @return the branchdesc
     */
//    public String getBranchdesc() {
//        return branchdesc;
//    }

    /**
     * @param branchdesc the branchdesc to set
     */
//    public void setBranchdesc(String branchdesc) {
//        this.branchdesc = branchdesc;
//    }

    /**
     * @return the registrationdesc
     */
      public String getProgramdesc() {
        return programdesc;
    }

    public void setProgramdesc(String programdesc) {
        this.programdesc = programdesc;
    }

    public String getStudentfstid() {
        return studentfstid;
    }

    public void setStudentfstid(String studentfstid) {
        this.studentfstid = studentfstid;
    }
    
}
