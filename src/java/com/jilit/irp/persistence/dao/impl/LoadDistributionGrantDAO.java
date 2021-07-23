/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.LoadDistributionGrantIDAO;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.LoadDistributionGrant;
import com.jilit.irp.persistence.dto.LoadDistributionGrantId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Akshya Gaur
 */
public class LoadDistributionGrantDAO extends HibernateDAO implements LoadDistributionGrantIDAO {

    private static final Log log = LogFactory.getLog(OtherStaffMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OtherStaffMaster records via Hibernate from the database");
        return this.find("from OtherStaffMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(LoadDistributionGrant.class, id);
    }

    public void update(Object record) {
        getHibernateTemplate().update((LoadDistributionGrant) record);
    }

    public void add(Object record) {
        getHibernateTemplate().save((LoadDistributionGrant) record);
    }

    public void delete(Object record) {
        getHibernateTemplate().delete((LoadDistributionGrant) record);
    }

    @Override
    public List checkLoadDistributionStatus(String instituteid, String registrationnid, String departmentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select idg.loaddistributionstatus from LoadDistributionGrant idg "
                + " where idg.id.instituteid=:instituteid and idg.id.registrationid=:registrationnid and idg.id.departmentid=:departmentid and coalesce(idg.loaddistributionstatus,'D')='A' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationnid", registrationnid).
                    setParameter("departmentid", departmentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getLoadDistributionData(String instituteid, String regid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select idg from LoadDistributionGrant idg "
                + " where idg.id.instituteid=:instituteid and idg.id.registrationid=:regid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid)
                    .setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
