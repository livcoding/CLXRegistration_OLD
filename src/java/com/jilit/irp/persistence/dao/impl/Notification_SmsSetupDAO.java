/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Notification_SmsSetupIDAO;
import com.jilit.irp.persistence.dto.Notification_SmsSetup;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
/**
 *
 * @author soa university
 */
public class Notification_SmsSetupDAO extends HibernateDAO implements Notification_SmsSetupIDAO {

    private static final Log log = LogFactory.getLog(Notification_SmsSetupDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Notification_SmsSetup records via Hibernate from the database");
        return this.find("from Notification_SmsSetup as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Notification_SmsSetup.class, id);
    }

}
