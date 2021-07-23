/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

/**
 *
 * @author campus.trainee
 */
import com.jilit.irp.persistence.dto.StreamMaster;
import java.util.Collection;
import java.util.List;

public interface StudentAddressIDAO extends IDAO {

    //   public Collection<?> getStudentContactInformation(final String studentid);
    List getStudentStatus(String ins_id);

    List getSemester(String br_id, String acad_year);

    List getStudentData(String ins_id, String prg_id, String br_id, String sty_no, String acad_year, String status);

}
