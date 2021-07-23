/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package com.jilit.irp.persistence.dao;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author ibrahimb.shaik
 */

public interface Sct_KioskMarqueeTextIDAO extends IDAO{

    public List getMaxSnoQuery();
    public List getIndividualUserIds();
    public List getUserCodeData(final String usercode, final String membertype);



}
