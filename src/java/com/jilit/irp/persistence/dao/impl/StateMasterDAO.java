/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.StateMasterIDAO;
import com.jilit.irp.persistence.dto.StateMaster;
import com.jilit.irp.persistence.dto.StateMasterId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class StateMasterDAO extends HibernateDAO implements StateMasterIDAO{

    private static final Log log = LogFactory.getLog(StateMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StateMaster records via Hibernate from the database");
        return this.find("from StateMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StateMaster.class, id);
    }


      public List getAllStateCode(String countryid) {
        List list = null;
        try {
            String qryString = " select s.id.stateid, s.id.countryid, s.statecode, s.statename " +
                               " from StateMaster s " +
                               " where coalesce(s.deactive,'N')='N' " ;
            if(!countryid.equalsIgnoreCase("All")){
                  qryString+=  " and s.id.countryid='"+countryid+"'";
            }
                  qryString+=  " order by s.seqid" ;
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

