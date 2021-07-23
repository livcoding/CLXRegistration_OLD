package com.jilit.irp.persistence.dto;
// Generated Dec 19, 2009 12:17:52 PM by Hibernate Tools 3.2.1.GA

/**
*  @author Malkeet Singh
 */
public class SummerRegistrationSetupDet implements java.io.Serializable {

    private SummerRegistrationSetupDetId id;
    private String  lpcriteriatype;
    private double maxcredit;
    private int maxlsubjectscredit;
    private int maxpsubjectscredit;
    private int maxltheorysubjects;
    private int maxplabsubjects;

    public SummerRegistrationSetupDet(SummerRegistrationSetupDetId id, String lpcriteriatype, double maxcredit, int maxlsubjectscredit, int maxpsubjectscredit, int maxltheorysubjects, int maxplabsubjects) {
        this.id = id;
        this.lpcriteriatype = lpcriteriatype;
        this.maxcredit = maxcredit;
        this.maxlsubjectscredit = maxlsubjectscredit;
        this.maxpsubjectscredit = maxpsubjectscredit;
        this.maxltheorysubjects = maxltheorysubjects;
        this.maxplabsubjects = maxplabsubjects;
    }

    public SummerRegistrationSetupDet() {
    }

    public SummerRegistrationSetupDetId getId() {
        return id;
    }

    public void setId(SummerRegistrationSetupDetId id) {
        this.id = id;
    }

    public String getLpcriteriatype() {
        return lpcriteriatype;
    }

    public void setLpcriteriatype(String lpcriteriatype) {
        this.lpcriteriatype = lpcriteriatype;
    }

    public double getMaxcredit() {
        return maxcredit;
    }

    public void setMaxcredit(double maxcredit) {
        this.maxcredit = maxcredit;
    }

    public int getMaxlsubjectscredit() {
        return maxlsubjectscredit;
    }

    public void setMaxlsubjectscredit(int maxlsubjectscredit) {
        this.maxlsubjectscredit = maxlsubjectscredit;
    }

    public int getMaxpsubjectscredit() {
        return maxpsubjectscredit;
    }

    public void setMaxpsubjectscredit(int maxpsubjectscredit) {
        this.maxpsubjectscredit = maxpsubjectscredit;
    }

    public int getMaxltheorysubjects() {
        return maxltheorysubjects;
    }

    public void setMaxltheorysubjects(int maxltheorysubjects) {
        this.maxltheorysubjects = maxltheorysubjects;
    }

    public int getMaxplabsubjects() {
        return maxplabsubjects;
    }

    public void setMaxplabsubjects(int maxplabsubjects) {
        this.maxplabsubjects = maxplabsubjects;
    }

   
}
