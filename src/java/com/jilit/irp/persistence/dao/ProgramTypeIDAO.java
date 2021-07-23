/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramType;
import com.jilit.irp.persistence.dto.ProgramTypeId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface ProgramTypeIDAO extends IDAO {
    
    public Collection<?> findAllInstituteWise(String instituteid);

    public int checkIfChildExist(final ProgramTypeId programTypeId);

    public List<String> doValidate(final ProgramType programType, final String mode);
//
//    public List<ProgramType> getProgTypeInstWise(final String id);
//
//    public List getProgramType(String instituteid);
//
//    public List checkgetProgramType(String instituteid, String programtype);
//
//    public List getProgTypeData(final String instituteid);
//
//    public List getprogramtypeProgram(String instId, String progTypeId);
}
