/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Notify_ParametersIDAO;
import com.jilit.irp.persistence.dto.Notify_Parameters;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author chetna.pargai
 */
public class Notify_ParametersDAO extends HibernateDAO implements Notify_ParametersIDAO {

    private static final Log log = LogFactory.getLog(Notify_ParametersDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Notify_Parameters records via Hibernate from the database");
        return this.find("from Notify_Parameters as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Notify_Parameters.class, id);
    }
}