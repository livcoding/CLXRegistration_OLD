/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.io.Serializable;
/**
 *
 * @author Shimona.Khandelwal
 */
public class V_Staff implements Serializable {

    private String companyid;
    private String employeeid;
    private String employeecode;
    private String salutationid;
    private String employeename;
    private String departmentid;
    private String departmentcode;
    private String department;
    private String designationid;
    private String designationcode;
    private String designation;
    private String stafftype;
    private String shortname;
    private String employeetypeid;
    private String employeetype;
    private String emailid;
    private String mobileno;
    private String gender;
    private String payrollareaid;
    private String deactive;
    private String placeofpostingid;


     public V_Staff()
     {

     }



   public V_Staff(String employeecode,String employeename,String designationcode,String designation, String departmentcode,String department,String placeofpostingid) {
        this.employeecode = employeecode;
        this.employeename = employeename;
        this.designationcode = designationcode;
        this.designation = designation;
        this.departmentcode = departmentcode;
        this.department = department;
        this.placeofpostingid = placeofpostingid;
      
     }
   public V_Staff(String employeeid,String employeecode,String employeename,String shortname, String stafftype) {
        this.employeecode = employeecode;
        this.employeename = employeename;
        this.employeeid = employeeid;
        this.shortname = shortname;
        this.stafftype = stafftype;
     }

    public V_Staff(V_Staff  dto) throws Exception {
        //BeanUtils.copyProperties(this, dto);
        //this.companycode=dto.getCompanycode();
        this.employeeid = dto.getEmployeeid();
        this.employeecode = dto.getEmployeecode();
        this.salutationid = dto.getSalutationid();
        this.employeename = dto.getEmployeename();
        this.departmentid=dto.getDepartmentid();
        this.departmentcode = dto.getDesignationcode();
        this.department = dto.getDepartment();
        this.designationid = dto.getDesignationid();
        this.designationcode = dto.getDesignationcode();
        this.designation=dto.getDesignation();
        this.stafftype=dto.getStafftype();
        this.shortname=dto.getShortname();
        this.employeetypeid = dto.getEmployeetypeid();
        this.employeetype = dto.getEmployeetype();
        this.deactive=dto.getDeactive();
        this.placeofpostingid=dto.getPlaceofpostingid();

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignationcode() {
        return designationcode;
    }

    public void setDesignationcode(String designationcode) {
        this.designationcode = designationcode;
    }

    public String getDesignationid() {
        return designationid;
    }

    public void setDesignationid(String designationid) {
        this.designationid = designationid;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getSalutationid() {
        return salutationid;
    }

    public void setSalutationid(String salutationid) {
        this.salutationid = salutationid;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public String getEmployeetypeid() {
        return employeetypeid;
    }

    public void setEmployeetypeid(String employeetypeid) {
        this.employeetypeid = employeetypeid;
    }



    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPayrollareaid() {
        return payrollareaid;
    }

    public void setPayrollareaid(String payrollareaid) {
        this.payrollareaid = payrollareaid;
    }

    public String getPlaceofpostingid() {
        return placeofpostingid;
    }

    public void setPlaceofpostingid(String placeofpostingid) {
        this.placeofpostingid = placeofpostingid;
    }

    public String getIdStr(){
        return getEmployeeid()+":"+getSalutationid()+":"+getDepartmentid()+":"+getDesignationid();
    }
}
