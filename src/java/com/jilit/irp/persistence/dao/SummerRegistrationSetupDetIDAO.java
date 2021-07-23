package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author Mohit kumar
 */
public interface SummerRegistrationSetupDetIDAO extends IDAO {

    public List getSummerRegistrationSetupDetEdit(String instituteid, String programid, byte stynumber);
}
