/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author ashok.singh
 */

package com.jilit.irp.persistence.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CommitteeFormation  implements java.io.Serializable
{
     private CommitteeFormationId id;
     private CommitteeMaster committeemaster;
     private Date formationdate;
     private Date activetilldate;
     private String status;
     private String remarks;
     private String deactive;
     private Set <CommitteeMembers> committeemembers = new HashSet <CommitteeMembers>(0);

    public CommitteeFormation()
    {
    }

    public CommitteeFormation(CommitteeFormationId id, CommitteeMaster committeemaster, Date formationdate) {
        this.id = id;
        this.committeemaster = committeemaster;
        this.formationdate = formationdate;
    }
    public CommitteeFormation(CommitteeFormationId id, CommitteeMaster committeemaster, Date formationdate, Date activetilldate, String status, String remarks, String deactive) {
       this.id = id;
       this.committeemaster = committeemaster;
       this.formationdate = formationdate;
       this.activetilldate = activetilldate;
       this.status = status;
       this.remarks = remarks;
       this.deactive = deactive;
    }

    public CommitteeFormationId getId() {
        return this.id;
    }

    public void setId(CommitteeFormationId id) {
        this.id = id;
    }
    public CommitteeMaster getCommitteemaster() {
        return this.committeemaster;
    }

    public void setCommitteemaster(CommitteeMaster committeemaster) {
        this.committeemaster = committeemaster;
    }
    public Date getFormationdate() {
        return this.formationdate;
    }

    public void setFormationdate(Date formationdate) {
        this.formationdate = formationdate;
    }
    public Date getActivetilldate() {
        return this.activetilldate;
    }

    public void setActivetilldate(Date activetilldate) {
        this.activetilldate = activetilldate;
    }
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public Set<CommitteeMembers> getCommitteemembers() {
        return committeemembers;
    }

    public void setCommitteemembers(Set<CommitteeMembers> committeemembers) {
        this.committeemembers = committeemembers;
    }
 

}
