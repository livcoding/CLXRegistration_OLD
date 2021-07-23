package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author ankur.goyal
 */
public class Sis_RegistrationActivityRights implements java.io.Serializable {

    private Sis_RegistrationActivityRightsId id;
    private Sis_RegistrationActivityMaster sis_registrationactivitymaster;
    private Date fromdate;
    private Date tilldate;
    private String deactive;

    public Sis_RegistrationActivityRights() {
    }

    public Sis_RegistrationActivityRights(Sis_RegistrationActivityRights dto) {
        this.id = dto.getId();
        this.sis_registrationactivitymaster = dto.getSis_registrationactivitymaster();
        this.deactive = dto.getDeactive();
        this.fromdate = dto.getFromdate();
        this.tilldate = dto.getTilldate();
    }

    public Sis_RegistrationActivityRights(Sis_RegistrationActivityRightsId id, Sis_RegistrationActivityMaster sis_registrationactivitymaster, Date fromdate) {
        this.id = id;
        this.sis_registrationactivitymaster = sis_registrationactivitymaster;
        this.fromdate = fromdate;
    }

    public Sis_RegistrationActivityRights(Sis_RegistrationActivityRightsId id, Sis_RegistrationActivityMaster sis_registrationactivitymaster, Date fromdate, Date tilldate, String deactive) {
        this.id = id;
        this.sis_registrationactivitymaster = sis_registrationactivitymaster;
        this.fromdate = fromdate;
        this.tilldate = tilldate;
        this.deactive = deactive;
    }

    public Date getFromdate() {
        return this.fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTilldate() {
        return this.tilldate;
    }

    public void setTilldate(Date tilldate) {
        this.tilldate = tilldate;
    }

    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public Sis_RegistrationActivityRightsId getId() {
        return id;
    }

    public void setId(Sis_RegistrationActivityRightsId id) {
        this.id = id;
    }

    public Sis_RegistrationActivityMaster getSis_registrationactivitymaster() {
        return sis_registrationactivitymaster;
    }

    public void setSis_registrationactivitymaster(Sis_RegistrationActivityMaster sis_registrationactivitymaster) {
        this.sis_registrationactivitymaster = sis_registrationactivitymaster;
    }

    public String getIdStr() {
        return id.getInstituteid() + "~@~" + id.getActivityid() + "~@~" + id.getStaffid() + "~@~" + id.getStafftype();
    }
}
