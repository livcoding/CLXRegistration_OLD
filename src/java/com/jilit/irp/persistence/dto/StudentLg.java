/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;


import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author singh.jaswinder
 */
public class StudentLg implements java.io.Serializable {

    private StudentLgId Id;
    private StudentMaster studentmaster;
    private String lgnname;
    private String lgno;
    private String lgaddress;

    private String guardianemail;
    private String guardianoccupation;
    private String gender;
    private String relation;
    private String cityname;
    private String district;
    private String statename;
    private String pin;
    

    private String deactive;

    public StudentLg() {
    }

    public StudentLg(StudentLg dto) {
        try {
            dto.studentmaster = null;

            BeanUtils.copyProperties(this, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentLgId getId() {
        return Id;
    }

    public void setId(StudentLgId Id) {
        this.Id = Id;
    }

    public StudentMaster getStudentmaster() {
        return studentmaster;
    }

    public void setStudentmaster(StudentMaster studentmaster) {
        this.studentmaster = studentmaster;
    }

    public String getLgaddress() {
        return lgaddress;
    }

    public void setLgaddress(String lgaddress) {
        this.lgaddress = lgaddress;
    }

    public String getLgnname() {
        return lgnname;
    }

    public void setLgnname(String lgnname) {
        this.lgnname = lgnname;
    }

    public String getLgno() {
        return lgno;
    }

    public void setLgno(String lgno) {
        this.lgno = lgno;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

//    public void setAsid(ASObject id) {
//        if (this.Id == null) {
//            this.Id = new StudentLgId();
//        }
//        try {
//            BeanUtils.copyProperties(this.Id, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

   

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardianemail() {
        return guardianemail;
    }

    public void setGuardianemail(String guardianemail) {
        this.guardianemail = guardianemail;
    }

    public String getGuardianoccupation() {
        return guardianoccupation;
    }

    public void setGuardianoccupation(String guardianoccupation) {
        this.guardianoccupation = guardianoccupation;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    

}
