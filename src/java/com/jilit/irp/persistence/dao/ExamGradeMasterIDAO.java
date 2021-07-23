/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ExamGradeMaster;
import com.jilit.irp.persistence.dto.ExamGradeMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public interface ExamGradeMasterIDAO extends IDAO {

    public List getGrades(String instituteid);
    
    public Collection<?> findAll(String instituteid);

}
