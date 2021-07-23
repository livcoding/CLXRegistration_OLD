/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dto;

/**
 *
 * @author Malkeet Singh
 */
public class CourseCreditWiseFeeId implements java.io.Serializable {

    private String instituteid;
        private short slno;
    private String stytypeid;

    public CourseCreditWiseFeeId() {
    }

    public String getInstituteid() {
        return instituteid;
    }

    public void setInstituteid(String instituteid) {
        this.instituteid = instituteid;
    }

    public short getSlno() {
        return slno;
    }

    public void setSlno(short slno) {
        this.slno = slno;
    }

     

    public String getStytypeid() {
        return stytypeid;
    }

    public void setStytypeid(String stytypeid) {
        this.stytypeid = stytypeid;
    }

//    public boolean equals(Object other) {
//        if ((this == other)) {
//            return true;
//        }
//        if ((other == null)) {
//            return false;
//        }
//        if (!(other instanceof CourseCreditWiseFeeId)) {
//            return false;
//        }
//        CourseCreditWiseFeeId castOther = (CourseCreditWiseFeeId) other;
//
//        return ((this.getInstituteid() == castOther.getInstituteid()) || (this.getInstituteid() != null && castOther.getInstituteid() != null && this.getInstituteid().equals(castOther.getInstituteid())))
//                && ((this.getSlno() == castOther.getSlno()) || (this.getSlno() != null && castOther.getSlno() != null && this.getSlno().equals(castOther.getSlno())));
//    }
//
//    public int hashCode() {
//        int result = 17;
//
//        result = 37 * result + (getInstituteid() == null ? 0 : this.getInstituteid().hashCode());
//        result = 37 * result + (getSlno() == null ? 0 : this.getSlno().hashCode());
//        return result;
//    }

}
