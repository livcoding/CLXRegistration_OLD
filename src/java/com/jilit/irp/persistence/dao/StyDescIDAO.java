/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.persistence.dto.StyDescId;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface StyDescIDAO extends IDAO {

//    public int checkIfChildExist(StyDescId Id);
    public List getSemesterNumber(String instituteid, String registrationid, String subjectid, String acad_year, String programid);

    public List getAllStyNumber_Fst(String instituteid, String reg_id, String acad_year);

    public List getSty_Pst(String instituteid, String reg_id, String acad_year, String prg_id);

    public List getStyNumber(String instituteid, String regid, String acadyear, String progid);
    
    public List getStyNumber1(String instituteid, String regid);

//
//    public List<String> doValidate(final StyDesc styDesc, final String mode);
//
//    public List<String> Academicyearvalidate(final StyDesc styDesc, final String mode);
}
