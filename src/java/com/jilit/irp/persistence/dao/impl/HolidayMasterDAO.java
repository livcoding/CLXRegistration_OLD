/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.HolidayMasterIDAO;
import com.jilit.irp.persistence.dto.HolidayMaster;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ashok.singh
 */
public class HolidayMasterDAO extends HibernateDAO implements HolidayMasterIDAO {

    private static final Log log = LogFactory.getLog(HolidayMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all HolidayMaster records via Hibernate from the database");
        return this.find("from HolidayMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(HolidayMaster.class, id);
    }

}
