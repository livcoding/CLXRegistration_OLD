/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentDisciplinaryAction;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public interface StudentDisciplinaryActionIDAO extends IDAO {

    public Collection<?> findAllInstituteWise(String instituteid);

//    public int checkIfChildExist(final StudentDisciplinaryAction id);
//
    public List<String> doValidate(final StudentDisciplinaryAction studentDisciplinaryAction, final String mode);

    public List getAllDataStudentDisciplinaryAction(String instituteid);

    public List getEditUsingPrimaryKey(String instituteid, String daid);

//    public Byte getMaxSLNO(final Object exampleObject);
}
