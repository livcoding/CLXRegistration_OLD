package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.InstituteRegistrationEvents;
import java.util.Collection;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author singh.jaswinder
 */
public interface InstituteRegistrationEventsIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

//    public List getAllRegistrationCode(String queryName, String instituteid);
//
    public List<String> doValidate(final InstituteRegistrationEvents instituteRegistrationEvents, final String mode);

    public List getInstituteRegistrationEventGridData(String instituteid);

    public List getInstituteRegistrationEventEditData(String instituteid, String registrationid);
//    public List checkRegistrationCode(String queryName, String instituteid, String regcode);
//
//    public List getAllInstituteRegistrationEventsData(String instituteid) ;
}
