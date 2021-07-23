/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface TT_TimeTableAllocationIDAO extends IDAO {

    public List checkTTAllocation(String instituteid, String registrationid, String subjectid, String subjectcomponentid, String facultyid);

    public List checkTTAllocationDetail(String instituteid, String registrationid, String subjectid, String subjectcomponentid, String academicyear,
            String programid, String sectionid, String subsectionid, String stynumber, String stytypeid);
}
