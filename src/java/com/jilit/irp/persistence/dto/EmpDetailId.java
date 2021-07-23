/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

/**
 *
 * @author saroj.khuntia
 */
public class EmpDetailId implements java.io.Serializable{

     private String instituteid;
     private String empid;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
    public EmpDetailId() {
    }

    public EmpDetailId(String instituteid, String empid) {
       this.instituteid = instituteid;
       this.empid = empid;
    }

    public String getInstituteid() {
        return this.instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EmpDetailId) ) return false;
		 EmpDetailId castOther = ( EmpDetailId ) other;

		 return ( (this.getInstituteid()==castOther.getInstituteid()) || ( this.getInstituteid()!=null && castOther.getInstituteid()!=null && this.getInstituteid().equals(castOther.getInstituteid()) ) )
 && ( (this.getEmpid()==castOther.getEmpid()) || ( this.getEmpid()!=null && castOther.getEmpid()!=null && this.getEmpid().equals(castOther.getEmpid()) ) );
   }

   public int hashCode() {
         int result = 17;

         result = 37 * result + ( getInstituteid() == null ? 0 : this.getInstituteid().hashCode() );
         result = 37 * result + ( getEmpid() == null ? 0 : this.getEmpid().hashCode() );
         return result;
   }   

}
