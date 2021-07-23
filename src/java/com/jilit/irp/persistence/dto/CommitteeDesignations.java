/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author campus.trainee
 */
public class CommitteeDesignations implements Serializable{

     private CommitteeDesignationsId id;
     private InstituteMaster institutemaster;
     private String designationcode;
     private String designation;
     private Short designationweight;
     private Short seqid;
     private String deactive;
     private String reportingto;
     private Set<CommitteeDesignations> committeeDesignationses = new HashSet<CommitteeDesignations>(0);




    public CommitteeDesignations() {
    }

     public CommitteeDesignations(CommitteeDesignations dto) {
        this.id = dto.getId();
        this.designation=dto.getDesignation();
        this.designationcode=dto.getDesignationcode();
        this.deactive=dto.getDeactive();
        this.designationweight=dto.getDesignationweight();
        this.seqid=dto.getSeqid();
        this.reportingto=dto.getReportingto();
       

    }


    public CommitteeDesignations(CommitteeDesignationsId id, InstituteMaster institutemaster, String designationcode, String designation) {
        this.id = id;
        this.institutemaster = institutemaster;
        this.designationcode = designationcode;
        this.designation = designation;
    }
    public CommitteeDesignations(CommitteeDesignationsId id, InstituteMaster institutemaster, String designationcode, String designation, Short designationweight, Short seqid, String deactive, String reportingto, Set<CommitteeDesignations> committeeDesignationses) {
       this.id = id;
       this.institutemaster = institutemaster;
       this.designationcode = designationcode;
       this.designation = designation;
       this.designationweight = designationweight;
       this.seqid = seqid;
       this.deactive = deactive;
       this.reportingto = reportingto;
       this.committeeDesignationses = committeeDesignationses;
    }

    public CommitteeDesignationsId getId() {
        return this.id;
    }

    public void setId(CommitteeDesignationsId id) {
        this.id = id;
    }
    public InstituteMaster getInstitutemaster() {
        return this.institutemaster;
    }

    public void setInstitutemaster(InstituteMaster institutemaster) {
        this.institutemaster = institutemaster;
    }
    public String getDesignationcode() {
        return this.designationcode;
    }

    public void setDesignationcode(String designationcode) {
        this.designationcode = designationcode;
    }
    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public Short getDesignationweight() {
        return this.designationweight;
    }

    public void setDesignationweight(Short designationweight) {
        this.designationweight = designationweight;
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

    public String getIdStr() {
        return getId().getDesignationid()+ ":" + getId().getInstituteid();
    }

    public String getReportingto() {
        return reportingto;
    }

    public void setReportingto(String reportingto) {
        this.reportingto = reportingto;
    }

    public Set<CommitteeDesignations> getCommitteeDesignationses() {
        return committeeDesignationses;
    }

    public void setCommitteeDesignationses(Set<CommitteeDesignations> committeeDesignationses) {
        this.committeeDesignationses = committeeDesignationses;
    }

}
