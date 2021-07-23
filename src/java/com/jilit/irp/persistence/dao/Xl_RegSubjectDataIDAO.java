/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 *  @author vijeta.bopche
 */
public interface Xl_RegSubjectDataIDAO extends IDAO{

    public List getAllXlRegSubjectData(final String userid, final String xltaskid);

}
