/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ProgramSchemeAcadYearDetail;
import java.util.List;

/**
 *
 * @author subrata.lohar
 */
public interface ProgramSchemeAcadYearDetailIDAO extends IDAO {

    public boolean deleteChildRecord(List<ProgramSchemeAcadYearDetail> objectList);
}
