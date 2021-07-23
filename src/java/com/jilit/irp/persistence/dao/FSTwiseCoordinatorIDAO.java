/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;
//import com.jilit.irp.bso.biz.BusinessService;
//import com.jilit.irp.data.FilterInfoData;

import java.util.ArrayList;

import java.util.List;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ashok.singh
 */
public interface FSTwiseCoordinatorIDAO extends IDAO {

    public List getGridData(String instituteid, String subjectid,String registartionid);

    public List getCoordinatorType(String instituteid);

    public List getStaffCode1(String instituteid, String regId, String subjectId);

    public List getStaffCode2(String companyid, String staffType);

    public List getAddGridData(String instituteid, String registrationid, String subjectid, String subjectcomponentid);

}
