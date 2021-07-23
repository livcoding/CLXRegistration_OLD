/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankit.kumar
 */
public class SubjectsForBranchChangeId  implements java.io.Serializable {


     private String instituteid;
     private String programid;
     private String frombranchid;
     private String tobranchid;
     private String subjectid;

    public SubjectsForBranchChangeId() {
    }

    public SubjectsForBranchChangeId(String instituteid, String programid, String frombranchid,  String tobranchid, String subjectid) {
       this.instituteid = instituteid;
       this.programid = programid;
       this.frombranchid = frombranchid;
       this.tobranchid = tobranchid;
       this.subjectid = subjectid;
    }
   
    public String getInstituteid() {
        return this.instituteid;
    }
    
    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }
    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getFrombranchid() {
        return this.frombranchid;
    }

    public void setFrombranchid (String frombranchid) {
        this.frombranchid = frombranchid;
    }
    public String getTobranchid() {
        return this.tobranchid;
    }

    public void setTobranchid (String tobranchid) {
        this.tobranchid = tobranchid;
    }

    public String getSubjectid() {
        return this.subjectid;
    }
    
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SubjectsForBranchChangeId) ) return false;
		 SubjectsForBranchChangeId castOther = ( SubjectsForBranchChangeId ) other;
         
		 return ( (this.getInstituteid()==castOther.getInstituteid()) || ( this.getInstituteid()!=null && castOther.getInstituteid()!=null && this.getInstituteid().equals(castOther.getInstituteid()) ) )
 && ( (this.getProgramid()==castOther.getProgramid()) || ( this.getProgramid()!=null && castOther.getProgramid()!=null && this.getProgramid().equals(castOther.getProgramid()) ) )
 && ( (this.getFrombranchid()==castOther.getFrombranchid()) || ( this.getFrombranchid()!=null && castOther.getFrombranchid()!=null && this.getFrombranchid().equals(castOther.getFrombranchid()) ) )
 && ( (this.getTobranchid()==castOther.getTobranchid()) || ( this.getTobranchid()!=null && castOther.getTobranchid()!=null && this.getTobranchid().equals(castOther.getTobranchid()) ) )
 && ( (this.getSubjectid()==castOther.getSubjectid()) || ( this.getSubjectid()!=null && castOther.getSubjectid()!=null && this.getSubjectid().equals(castOther.getSubjectid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getInstituteid() == null ? 0 : this.getInstituteid().hashCode() );
         result = 37 * result + ( getProgramid() == null ? 0 : this.getProgramid().hashCode() );
         result = 37 * result + ( getFrombranchid() == null ? 0 : this.getFrombranchid().hashCode() );
         result = 37 * result + ( getTobranchid() == null ? 0 : this.getTobranchid().hashCode() );
         result = 37 * result + ( getSubjectid() == null ? 0 : this.getSubjectid().hashCode() );
         return result;
   }   


}


