/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Sct_IrpUsersIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Sct_IrpUsers;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author sunny.singhal
 */
public class Sct_IrpUsersDAO extends HibernateDAO implements Sct_IrpUsersIDAO {

    private static final Log log = LogFactory.getLog(Sct_IrpUsersDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_IrpUsers records via Hibernate from the database");
        return this.find("from Sct_IrpUsers as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_IrpUsers.class, id);
    }

    

    public List<String> doValidate(Sct_IrpUsers sct_IrpUsers, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int checkIfChildExist(String userid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List getLoginStatus(String userid) {
        String status = null;
        String qrystring = "select mo.loginstatus,mo.lastvisiteddate from Sct_IrpUsers mo where mo.userid = '"+userid+"'";
        List<Object[]> list = getHibernateTemplate().find(qrystring);
        
       return list;
}
}
