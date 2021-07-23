/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ComplainTicketPersonIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ComplainTicketPerson;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vipinkr.sharma
 */
public class ComplainTicketPersonDAO extends HibernateDAO implements ComplainTicketPersonIDAO {

     private static final Log log = LogFactory.getLog(ComplainTicketPersonDAO.class);


    public Collection<?> findAll() {
        log.info("Retrieving all ComplainTicketPerson records via Hibernate from the database");
        return this.find("from ComplainTicketPerson as tname");
    }


    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ComplainTicketPerson.class, id);
    }

        public int checkIfChildExist(final ComplainTicketPerson id) {
       HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ComplainTicketPerson complainTicketPerson = (ComplainTicketPerson) session.get(ComplainTicketPerson.class, id.getPersonid());
                int i1 = ((Integer) session.createFilter(complainTicketPerson.getComplainticketmasters(), "select count(*)").list().get(0)).intValue();

                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

}
