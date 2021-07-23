/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author campus.trainee
 */
public interface RegistrationSubjectGroupIDAO extends IDAO {

    public List getGridData(String instituteid, String programid);

    public List getGroupId(String instituteid, String programid);

    public List getSubjects(String instituteid, String programid);

    public List getGroupedSubjects(String instituteid,String programid, String groupid);

    public List validateGroupId(String instituteid, String programid, String groupid);
}
