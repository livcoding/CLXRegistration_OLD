/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

//import com.jilit.irp.bso.biz.UIService;
import com.jilit.irp.persistence.dao.CountryMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.CountryMaster;
//import com.jilit.irp.util.JIRPCommonUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class CountryMasterDAO extends HibernateDAO implements CountryMasterIDAO {

    private static final Log log = LogFactory.getLog(CountryMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CountryMaster records via Hibernate from the database");
        return this.find("from CountryMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CountryMaster.class, id);
    }
     public List getAllCountryCode() {
        List list = null;
        try {
            String qryString = " select s.countryid, s.countrycode, s.countryname " +
                               " from CountryMaster s where coalesce(s.deactive,'N')='N' " +
                               " order by s.seqid" ;
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

