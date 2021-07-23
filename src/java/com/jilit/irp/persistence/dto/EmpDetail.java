/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author saroj.khuntia
 */
public class EmpDetail implements java.io.Serializable{

     private EmpDetailId id;
     private InstituteMaster institutemaster;
     private String empcode;
     private String empname;
     private String deactive;
     private double salary;
     private String deptid;

     
     
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
     
     private Set<ProgramSchemeAcadyearWise> programschemeacacmicyearwises = new HashSet<ProgramSchemeAcadyearWise>(0);
     private Set<ProgramScheme> programschemes = new HashSet<ProgramScheme>(0);

    public EmpDetail() {
    }

    public EmpDetail(EmpDetail dto){

        this.id = dto.id;
        this.empcode = dto.empcode;
        this.empname = dto.empname;
        this.deactive = dto.deactive;
        this.salary = dto.salary;
        this.deptid=dto.deptid;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public EmpDetail(EmpDetailId id, InstituteMaster institutemaster, String empcode, String empname) {
        this.id = id;
        this.institutemaster = institutemaster;
        this.empcode = empcode;
        this.empname = empname;
    }
    public EmpDetail(EmpDetailId id, InstituteMaster institutemaster, String empcode, String empname, String deactive, double salary, Set<ProgramScheme> programschemes) {
       this.id = id;
       this.institutemaster = institutemaster;
       this.empcode = empcode;
       this.empname = empname;
       this.deactive = deactive;
       this.salary = salary;
       this.programschemes = programschemes;
    }

    public EmpDetailId getId() {
        return this.id;
    }

    public void setId(EmpDetailId id) {
        this.id = id;
    }
    public InstituteMaster getInstitutemaster() {
        return this.institutemaster;
    }

    public void setInstitutemaster(InstituteMaster institutemaster) {
        this.institutemaster = institutemaster;
    }
    
    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }
    
    public Set<ProgramScheme> getProgramschemes() {
        return this.programschemes;
    }

    public void setProgramschemes(Set<ProgramScheme> programschemes) {
        this.programschemes = programschemes;
    }

    public String getIdStr() {
                return (id.getInstituteid() + "::" + id.getEmpid());
    }

    public Set<ProgramSchemeAcadyearWise> getProgramschemeacacmicyearwises() {
        return programschemeacacmicyearwises;
    }

    public void setProgramschemeacacmicyearwises(Set<ProgramSchemeAcadyearWise> programschemeacacmicyearwises) {
        this.programschemeacacmicyearwises = programschemeacacmicyearwises;
    }

}
