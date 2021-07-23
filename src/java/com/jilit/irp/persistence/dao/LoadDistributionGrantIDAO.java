/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.LoadDistributionGrant;
import com.jilit.irp.persistence.dto.LoadDistributionGrantId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Akshya Gaur
 */
public interface LoadDistributionGrantIDAO extends IDAO {

    public List getLoadDistributionData(String instituteid, String regid);

    public List checkLoadDistributionStatus(String instituteid, String registrationnid, String departmentid);
}
