/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CommitteeMasterIDAO;
import com.jilit.irp.persistence.dto.CommitteeMaster;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CommitteeMasterId;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author campus.trainee
 */

    public class CommitteeMasterDAO extends HibernateDAO implements CommitteeMasterIDAO {
   
    private static final Log log = LogFactory.getLog(CommitteeMaster.class);
    public Collection<?> findAll() {
        log.info("Retrieving all CommitteeMaster  records via Hibernate from the database");
        return this.find("from CommitteeMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeMaster.class, id);
    }

public int checkIfChildExist(final String CommitteeMasterId ) {
       HibernateCallback callback = new HibernateCallback() {

             public Object doInHibernate(Session session) throws HibernateException, SQLException{
               CommitteeMaster committeeMaster= (CommitteeMaster)session.get(CommitteeMaster.class, CommitteeMasterId);
               return 0;

            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();

  }
}
