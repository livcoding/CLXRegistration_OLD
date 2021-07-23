/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TeachingLoadDistributionIDAO;
import com.jilit.irp.persistence.dto.TeachingLoadDetailId;
import com.jilit.irp.persistence.dto.TeachingLoadDistribution;
import java.util.Collection;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Transaction;
/**
 *
 * @author chetan.kaushik
 */
public class TeachingLoadDistributionDAO extends HibernateDAO implements TeachingLoadDistributionIDAO {
   private static final Log log = LogFactory.getLog(TeachingLoadDistribution.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Teaching Load Distribution records via Hibernate from the database");
        return this.find("from TeachingLoadDistribution as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TeachingLoadDistribution.class, id);

    }


   public int checkIfChildExist(final String tlid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                TeachingLoadDistribution teachingLoadDistribution = (TeachingLoadDistribution) session.get(TeachingLoadDistribution.class, tlid);
                int i = ((Integer) session.createFilter(teachingLoadDistribution.getTeachingloaddetails(), "select count(*)").list().get(0)).intValue();
                return i;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
