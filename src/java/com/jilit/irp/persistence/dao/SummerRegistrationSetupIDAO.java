package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author Malkeet Singh
 */
public interface SummerRegistrationSetupIDAO extends IDAO {

    public List getGridData(String instituteid, String programid);

    public List getStyNumber(String instituteid, List programlist);

    public List getSummerRegistrationSetupEdit(String instituteid, String programid, byte stynumber);

}
