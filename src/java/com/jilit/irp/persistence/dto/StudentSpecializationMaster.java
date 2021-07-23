/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ankit.kumar
 */
 
public class StudentSpecializationMaster  implements java.io.Serializable {


     private StudentSpecializationMasterId id;
     private String speccode;
     private String specdesc;
     private String enrollmentcode;
     private Short seqid;
     private String deactive;
     private Set<StudentMaster> studentmasters = new HashSet<StudentMaster>(0);

    public StudentSpecializationMaster() {
    }

	
    public StudentSpecializationMaster(StudentSpecializationMaster dto) {
        this.id = dto.getId();
        this.speccode = dto.getSpeccode();
        this.specdesc = dto.getSpecdesc();
        this.enrollmentcode = dto.getEnrollmentcode();
        this.deactive = dto.getDeactive();
        this.seqid = dto.getSeqid();
    }
    public StudentSpecializationMaster(StudentSpecializationMasterId id, String speccode, String specdesc, String enrollmentcode, Short seqid, String deactive,Set<StudentMaster> studentmasters) {
       this.id = id;
       this.speccode = speccode;
       this.specdesc = specdesc;
       this.enrollmentcode = enrollmentcode;
       this.seqid = seqid;
       this.deactive = deactive;
       this.studentmasters = studentmasters;
    }
   
    public StudentSpecializationMasterId getId() {
        return this.id;
    }
    
    public void setId(StudentSpecializationMasterId id) {
        this.id = id;
    }
    public String getSpeccode() {
        return this.speccode;
    }
    
    public void setSpeccode(String speccode) {
        this.speccode = speccode;
    }
    public String getSpecdesc() {
        return this.specdesc;
    }
    
    public void setSpecdesc(String specdesc) {
        this.specdesc = specdesc;
    }
    public String getEnrollmentcode() {
        return this.enrollmentcode;
    }
    
    public void setEnrollmentcode(String enrollmentcode) {
        this.enrollmentcode = enrollmentcode;
    }
    public Short getSeqid() {
        return this.seqid;
    }
    
    public void setSeqid(Short seqid) {
        this.seqid = seqid;
    }
    public String getDeactive() {
        return this.deactive;
    }
    
    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public Set<StudentMaster> getStudentmasters() {
        return studentmasters;
    }

    public void setStudentmasters(Set<StudentMaster> studentmasters) {
        this.studentmasters = studentmasters;
    }
    public String getIdStr(){
        return id.getInstituteid()+":"+id.getProgramid()+":"+id.getSpecid();
    }

}


