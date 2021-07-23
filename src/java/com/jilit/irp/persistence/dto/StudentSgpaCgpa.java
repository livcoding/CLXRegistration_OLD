/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author ashok.singh
 */
public class StudentSgpaCgpa  implements java.io.Serializable {


     private StudentSgpaCgpaId id;
     private StudentMaster studentmaster;
     private InstituteMaster institutemaster;
     private BigDecimal totalgradepoints;
     private BigDecimal totalcoursecredit;
     private BigDecimal totalearnedcredit;
     private BigDecimal totalpointsecuredsgpa;
     private BigDecimal totalpointsecuredcgpa;
     private BigDecimal sgpa;
     private BigDecimal cgpa;
     private String fail;
     private BigDecimal sgpa_r;
     private BigDecimal cgpa_r;
     private String deactive;
//     private Set<StudentSgpaCgpa_Pub> studentsgpacgpa_pubs = new HashSet<StudentSgpaCgpa_Pub>(0);
    public StudentSgpaCgpa() {
    }

    
    public StudentSgpaCgpa(StudentSgpaCgpaId id, StudentMaster studentmaster, InstituteMaster institutemaster, BigDecimal sgpa) {
        this.id = id;
        this.studentmaster = studentmaster;
        this.institutemaster = institutemaster;
        this.sgpa = sgpa;
    }
    public StudentSgpaCgpa(StudentSgpaCgpaId id, StudentMaster studentmaster, InstituteMaster institutemaster, BigDecimal totalgradepoints, BigDecimal totalcoursecredit, BigDecimal totalearnedcredit, BigDecimal totalpointsecuredsgpa, BigDecimal totalpointsecuredcgpa, BigDecimal sgpa, BigDecimal cgpa, String fail, BigDecimal sgpa_r, BigDecimal cgpa_r, String deactive) {
       this.id = id;
       this.studentmaster = studentmaster;
       this.institutemaster = institutemaster;
       this.totalgradepoints = totalgradepoints;
       this.totalcoursecredit = totalcoursecredit;
       this.totalearnedcredit = totalearnedcredit;
       this.totalpointsecuredsgpa = totalpointsecuredsgpa;
       this.totalpointsecuredcgpa = totalpointsecuredcgpa;
       this.sgpa = sgpa;
       this.cgpa = cgpa;
       this.fail = fail;
       this.sgpa_r = sgpa_r;
       this.cgpa_r = cgpa_r;
       this.deactive = deactive;
    }

    public StudentSgpaCgpaId getId() {
        return this.id;
    }

    public void setId(StudentSgpaCgpaId id) {
        this.id = id;
    }

    public InstituteMaster getInstitutemaster() {
        return this.institutemaster;
    }

    public void setInstitutemaster(InstituteMaster institutemaster) {
        this.institutemaster = institutemaster;
    }
    public BigDecimal getTotalgradepoints() {
        return this.totalgradepoints;
    }

    public void setTotalgradepoints(BigDecimal totalgradepoints) {
        this.totalgradepoints = totalgradepoints;
    }
    public BigDecimal getTotalcoursecredit() {
        return this.totalcoursecredit;
    }

    public void setTotalcoursecredit(BigDecimal totalcoursecredit) {
        this.totalcoursecredit = totalcoursecredit;
    }
    public BigDecimal getTotalearnedcredit() {
        return this.totalearnedcredit;
    }

    public void setTotalearnedcredit(BigDecimal totalearnedcredit) {
        this.totalearnedcredit = totalearnedcredit;
    }
    public BigDecimal getTotalpointsecuredsgpa() {
        return this.totalpointsecuredsgpa;
    }

    public void setTotalpointsecuredsgpa(BigDecimal totalpointsecuredsgpa) {
        this.totalpointsecuredsgpa = totalpointsecuredsgpa;
    }
    public BigDecimal getTotalpointsecuredcgpa() {
        return this.totalpointsecuredcgpa;
    }

    public void setTotalpointsecuredcgpa(BigDecimal totalpointsecuredcgpa) {
        this.totalpointsecuredcgpa = totalpointsecuredcgpa;
    }
    public BigDecimal getSgpa() {
        return this.sgpa;
    }

    public void setSgpa(BigDecimal sgpa) {
        this.sgpa = sgpa;
    }
    public BigDecimal getCgpa() {
        return this.cgpa;
    }

    public void setCgpa(BigDecimal cgpa) {
        this.cgpa = cgpa;
    }
    public String getFail() {
        return this.fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }


    public BigDecimal getSgpa_r() {
        return sgpa_r;
    }


    public void setSgpa_r(BigDecimal sgpa_r) {
        this.sgpa_r = sgpa_r;
    }


    public BigDecimal getCgpa_r() {
        return cgpa_r;
    }


    public void setCgpa_r(BigDecimal cgpa_r) {
        this.cgpa_r = cgpa_r;
    }

    public StudentMaster getStudentmaster() {
        return studentmaster;
    }

    public void setStudentmaster(StudentMaster studentmaster) {
        this.studentmaster = studentmaster;
    }

//    public Set getStudentsgpacgpa_pubs() {
//        return studentsgpacgpa_pubs;
//    }
//
//    public void setStudentsgpacgpa_pubs(Set studentsgpacgpa_pubs) {
//        this.studentsgpacgpa_pubs = studentsgpacgpa_pubs;
//    }



    
}


