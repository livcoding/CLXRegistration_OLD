/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.util.HashSet;
import java.util.Set;

/** 
 *
 * @author singh.jaswinder
 */
public class InstituteCampusMaster implements java.io.Serializable {

    private String campusid;
    private String campuscode;
    private String campusname;
    private String campusaddress;
    private String deactive;
   private Set<StudentMaster> studentMasters = new HashSet<StudentMaster>(0);



    public InstituteCampusMaster() {
    }

    public InstituteCampusMaster(InstituteCampusMaster dto) {
        this.campusid = dto.getCampusid();
        this.campuscode = dto.getCampuscode();
        this.campusname = dto.getCampusname();
        this.campusaddress = dto.getCampusaddress();
        this.deactive = dto.getDeactive();
    }

     public InstituteCampusMaster(String campusid,String campuscode,String campusname,String campusaddress,String deactive,  Set<StudentMaster> studentMasters ) {
        this.campusid =campusid;
        this.campuscode = campuscode;
        this.campusname = campusname;
        this.campusaddress = campusaddress;
        this.deactive = deactive;
        this.studentMasters=studentMasters;
    }

    public String getCampusaddress() {
        return campusaddress;
    }

    public void setCampusaddress(String campusaddress) {
        this.campusaddress = campusaddress;
    }

    public String getCampuscode() {
        return campuscode;
    }

    public void setCampuscode(String campuscode) {
        this.campuscode = campuscode;
    }

    public String getCampusid() {
        return campusid;
    }

    public void setCampusid(String campusid) {
        this.campusid = campusid;
    }

    public String getCampusname() {
        return campusname;
    }

    public void setCampusname(String campusname) {
        this.campusname = campusname;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    

     public Set<StudentMaster> getStudentMasters() {
        return studentMasters;
    }

    public void setStudentMasters(Set<StudentMaster> studentMasters) {
        this.studentMasters = studentMasters;
    }
}
