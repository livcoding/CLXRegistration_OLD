/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author bhopals.rawat
 */
public class TT_ModificationBean implements java.io.Serializable {

    private TT_ModificationBeanId id;
    private String days;
    private String fromsessiontime;
    private String tosessiontime;
    private String roomid;
    private String employeecode;
    private String employeename;
    private String staffid;
    private String stafftype;
    private String[] WeekDays;
    private boolean flage;
    private String roomcode;
    private String subjectcomponentid;
    private String subjectcomponent;
    private String subjectcode;

    public TT_ModificationBean() {
    }

    public TT_ModificationBean(TT_ModificationBean tt_ModificationBean) {
        this.id = tt_ModificationBean.getId();
        this.days = tt_ModificationBean.getDays();
        this.fromsessiontime = tt_ModificationBean.getFromsessiontime();
        this.tosessiontime = tt_ModificationBean.getTosessiontime();
        this.roomid = tt_ModificationBean.getRoomid();
        this.employeecode = tt_ModificationBean.getEmployeecode();
        this.employeename = tt_ModificationBean.getEmployeename();
        this.flage = tt_ModificationBean.isFlage();
        this.staffid = tt_ModificationBean.getStaffid();
        this.stafftype = tt_ModificationBean.getStafftype();
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
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

    public String getFromsessiontime() {
        return fromsessiontime;
    }

    public void setFromsessiontime(String fromsessiontime) {
        this.fromsessiontime = fromsessiontime;
    }

    public String getTosessiontime() {
        return tosessiontime;
    }

    public void setTosessiontime(String tosessiontime) {
        this.tosessiontime = tosessiontime;
    }

    public boolean isFlage() {
        return flage;
    }

    public void setFlage(boolean flage) {
        this.flage = flage;
    }

    public TT_ModificationBeanId getId() {
        return id;
    }

    public void setId(TT_ModificationBeanId id) {
        this.id = id;
    }

    public String getIdStr() {
        return id.getTtid() + ":" + id.getRegistrationid();
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    public String[] getWeekDays() {
        return WeekDays;
    }

    public void setWeekDays(String[] WeekDays) {
        this.WeekDays = WeekDays;
    }

    public String getSubjectcode() {
        return subjectcode;
    }

    public void setSubjectcode(String subjectcode) {
        this.subjectcode = subjectcode;
    }

    public String getSubjectcomponent() {
        return subjectcomponent;
    }

    public void setSubjectcomponent(String subjectcomponent) {
        this.subjectcomponent = subjectcomponent;
    }

    public String getSubjectcomponentid() {
        return subjectcomponentid;
    }

    public void setSubjectcomponentid(String subjectcomponentid) {
        this.subjectcomponentid = subjectcomponentid;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

}
