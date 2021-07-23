/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author ashok.singh
 */
public interface NumberingSetupOthersIDAO extends IDAO {

    public List getNumberingSetupGroupID(final String pCompanyOrInstituteID, final String pCompInstFlag, final String pPrefix);

    public List getAllNumberingSetupOthersData();

    public String getMaxGroupId();
//public int checkIfChildExist(final String groupid);
}
