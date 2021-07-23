/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import java.util.Collection;
import java.util.List;


/**
 *
 * @author singh.jaswinder
 */
public interface RollNumberingSetupDetailIDAO extends IDAO
{
    public Collection<?> getEnrollmentFormatReport (final String[] programid ,final String[] branchid);
    
    public List getGroupidForEnrollNumber(String inid , String prid,String bmid , String acyr,String pmtyid);
//
//    public List getRollNumberingSetupDetail(final String instituteid, final String acadyear, final String programtypeid, final String programid, final String branchid);
}