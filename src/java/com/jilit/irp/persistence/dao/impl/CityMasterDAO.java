/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CityMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.CityMaster;
import com.jilit.irp.persistence.dto.StateMaster;
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
public class CityMasterDAO extends HibernateDAO implements CityMasterIDAO {

    private static final Log log = LogFactory.getLog(CityMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CityMaster records via Hibernate from the database");
        return this.find("from CityMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CityMaster.class, id);
    }

    public List getAllCityCode(String countryid, String stateid) {
        List list = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select s.id.cityid,s.id.countryid, s.id.stateid, s.citycode, s.cityname "
                    + " from CityMaster s where :stateid=:stateid and :countryid=:countryid");
            if (!countryid.equalsIgnoreCase("All")) {
                sb.append(" and s.id.countryid=:countryid");
            }
            if (!stateid.equalsIgnoreCase("All")) {
                sb.append(" and s.id.stateid=:stateid ");
            }
            sb.append(" order by s.seqid ");
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("countryid", countryid).
                    setParameter("stateid", stateid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
