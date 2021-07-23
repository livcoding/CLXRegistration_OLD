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
public class StudentSpecializationMasterId  implements java.io.Serializable {


     private String instituteid;
     private String programid;
     private String specid;

    public StudentSpecializationMasterId() {
    }

    public StudentSpecializationMasterId(String instituteid, String programid, String specid) {
       this.instituteid = instituteid;
       this.programid = programid;
       this.specid = specid;
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
    public String getSpecid() {
        return this.specid;
    }
    
    public void setSpecid(String specid) {
        this.specid = specid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof StudentSpecializationMasterId) ) return false;
		 StudentSpecializationMasterId castOther = ( StudentSpecializationMasterId ) other;
         
		 return ( (this.getInstituteid()==castOther.getInstituteid()) || ( this.getInstituteid()!=null && castOther.getInstituteid()!=null && this.getInstituteid().equals(castOther.getInstituteid()) ) )
 && ( (this.getProgramid()==castOther.getProgramid()) || ( this.getProgramid()!=null && castOther.getProgramid()!=null && this.getProgramid().equals(castOther.getProgramid()) ) )
 && ( (this.getSpecid()==castOther.getSpecid()) || ( this.getSpecid()!=null && castOther.getSpecid()!=null && this.getSpecid().equals(castOther.getSpecid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getInstituteid() == null ? 0 : this.getInstituteid().hashCode() );
         result = 37 * result + ( getProgramid() == null ? 0 : this.getProgramid().hashCode() );
         result = 37 * result + ( getSpecid() == null ? 0 : this.getSpecid().hashCode() );
         return result;
   }   


}


