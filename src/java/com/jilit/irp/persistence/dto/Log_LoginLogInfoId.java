/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sumitk.singh
 */
public class Log_LoginLogInfoId implements Serializable{

     private String userid;
     private Date logindatetime;

    public Log_LoginLogInfoId() {
    }

    public Log_LoginLogInfoId(String userid, Date logindatetime) {
       this.userid = userid;
       this.logindatetime = logindatetime;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public Date getLogindatetime() {
        return this.logindatetime;
    }

    public void setLogindatetime(Date logindatetime) {
        this.logindatetime = logindatetime;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Log_LoginLogInfoId) ) return false;
		Log_LoginLogInfoId castOther = ( Log_LoginLogInfoId ) other;

		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getLogindatetime()==castOther.getLogindatetime()) || ( this.getLogindatetime()!=null && castOther.getLogindatetime()!=null && this.getLogindatetime().equals(castOther.getLogindatetime()) ) );
   }

   public int hashCode() {
         int result = 17;

         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getLogindatetime() == null ? 0 : this.getLogindatetime().hashCode() );
         return result;
   }

}
