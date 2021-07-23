/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author campus.trainee
 */
public interface RegistrationSubjectGroupIService {

    public void getProgramCode(ModelMap model);

    public List getGroupId(HttpServletRequest req);

    public List getRegistrationSubjectGroupGridData(HttpServletRequest req);

    public List getSubjects(HttpServletRequest req);

    public List getGroupedSubjects(HttpServletRequest req);

    public List saveRegistrationSubjectGroup(HttpServletRequest request);

    public List updateRegistrationSubjectGroup(HttpServletRequest request);
    
    public List deleteRegistrationGroupedSubject(HttpServletRequest request);
}
