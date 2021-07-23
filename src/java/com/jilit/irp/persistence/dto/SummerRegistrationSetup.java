package com.jilit.irp.persistence.dto;
// Generated Dec 19, 2009 12:17:52 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet; 
import java.util.Set;

/**
 *  @author Malkeet Singh
 */
public class SummerRegistrationSetup implements java.io.Serializable {

    private SummerRegistrationSetupId id;
    private double maxcredit;
    private int maxsubjects;
    private int maxprojectsubjects;
    private int induscasemaxsubj;

    public SummerRegistrationSetup() {
    }

    public SummerRegistrationSetup(SummerRegistrationSetupId id, double maxcredit, int maxsubjects, int maxprojectsubjects, int induscasemaxsubj) {
        this.id = id;
        this.maxcredit = maxcredit;
        this.maxsubjects = maxsubjects;
        this.maxprojectsubjects = maxprojectsubjects;
        this.induscasemaxsubj = induscasemaxsubj;
    }

    public SummerRegistrationSetupId getId() {
        return id;
    }

    public void setId(SummerRegistrationSetupId id) {
        this.id = id;
    }

    public double getMaxcredit() {
        return maxcredit;
    }

    public void setMaxcredit(double maxcredit) {
        this.maxcredit = maxcredit;
    }

    public int getMaxsubjects() {
        return maxsubjects;
    }

    public void setMaxsubjects(int maxsubjects) {
        this.maxsubjects = maxsubjects;
    }

    public int getMaxprojectsubjects() {
        return maxprojectsubjects;
    }

    public void setMaxprojectsubjects(int maxprojectsubjects) {
        this.maxprojectsubjects = maxprojectsubjects;
    }

    public int getInduscasemaxsubj() {
        return induscasemaxsubj;
    }

    public void setInduscasemaxsubj(int induscasemaxsubj) {
        this.induscasemaxsubj = induscasemaxsubj;
    }

}
