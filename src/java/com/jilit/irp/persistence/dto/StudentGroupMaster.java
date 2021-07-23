/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ankit.kumar
 */
public class StudentGroupMaster implements java.io.Serializable {

    private StudentGroupMasterId id;
    private String groupcode;
    private String groupname;
    private String deactive;
    private Set<StudentGroupCrLimit> studentgroupcrlimit = new HashSet<StudentGroupCrLimit>(0);

    public StudentGroupMaster(StudentGroupMasterId id, String groupcode, String groupname, String deactive, Set<StudentGroupCrLimit> studentgroupcrlimit) {
        this.id = id;
        this.groupcode = groupcode;
        this.groupname = groupname;
        this.deactive = deactive;
        this.studentgroupcrlimit = studentgroupcrlimit;
    }

    public StudentGroupMaster() {
    }

    public Set<StudentGroupCrLimit> getStudentgroupcrlimit() {
        return studentgroupcrlimit;
    }

    public void setStudentgroupcrlimit(Set<StudentGroupCrLimit> studentgroupcrlimit) {
        this.studentgroupcrlimit = studentgroupcrlimit;
    }

    public String getGroupcode() {
        return this.groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public StudentGroupMasterId getId() {
        return id;
    }

    public void setId(StudentGroupMasterId id) {
        this.id = id;
    }

    public String getIdStr() {
        return id.getGroupid() + ":" + id.getClientid();
    }

}
