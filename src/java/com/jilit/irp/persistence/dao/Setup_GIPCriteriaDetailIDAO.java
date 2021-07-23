package com.jilit.irp.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * malkeet.singh
 */
public interface Setup_GIPCriteriaDetailIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public Object findByPrimaryKey(Serializable id);

    public void deleteGIPDetail(String instituteid, String programid);

}
