/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.util.Date;

/**
 *
 * @author Administrator
 */

public class PhdResearchRegistration  implements java.io.Serializable {


     private PhdResearchRegistrationId id;
     private StudentRegistration studentregistration;
     private String internalguide1;
     private String internalguide1type;
     private String internalguide2;
     private String internalguide2type;
     private String externalguide;
     private String thesistopic;
     private String thesisabstract;
     private String deactive;
     private Date entrydate;

    public PhdResearchRegistration() {
    }


    public PhdResearchRegistration(PhdResearchRegistrationId id, StudentRegistration studentregistration) {
        this.id = id;
        this.studentregistration = studentregistration;
    }
    public PhdResearchRegistration(PhdResearchRegistrationId id, StudentRegistration studentregistration, String internalguide1, String internalguide2, String externalguide, String thesistopic, String thesisabstract, String deactive) {
       this.id = id;
       this.studentregistration = studentregistration;
       this.internalguide1 = internalguide1;
       this.internalguide2 = internalguide2;
       this.externalguide = externalguide;
       this.thesistopic = thesistopic;
       this.thesisabstract = thesisabstract;
       this.deactive = deactive;
    }

   public PhdResearchRegistrationId getId() {
        return id;
    }

    public void setId(PhdResearchRegistrationId id) {
        this.id = id;
    }

    public StudentRegistration getStudentregistration() {
        return studentregistration;
    }

    public void setStudentregistration(StudentRegistration studentregistration) {
        this.studentregistration = studentregistration;
    }

    public String getInternalguide1() {
        return this.internalguide1;
    }

    public void setInternalguide1(String internalguide1) {
        this.internalguide1 = internalguide1;
    }
    public String getInternalguide2() {
        return this.internalguide2;
    }

    public void setInternalguide2(String internalguide2) {
        this.internalguide2 = internalguide2;
    }
    public String getExternalguide() {
        return this.externalguide;
    }

    public void setExternalguide(String externalguide) {
        this.externalguide = externalguide;
    }
    public String getThesistopic() {
        return this.thesistopic;
    }

    public void setThesistopic(String thesistopic) {
        this.thesistopic = thesistopic;
    }
    public String getThesisabstract() {
        return this.thesisabstract;
    }

    public void setThesisabstract(String thesisabstract) {
        this.thesisabstract = thesisabstract;
    }
    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getInternalguide1type() {
        return internalguide1type;
    }

    public void setInternalguide1type(String internalguide1type) {
        this.internalguide1type = internalguide1type;
    }

    public String getInternalguide2type() {
        return internalguide2type;
    }

    public void setInternalguide2type(String internalguide2type) {
        this.internalguide2type = internalguide2type;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

}
