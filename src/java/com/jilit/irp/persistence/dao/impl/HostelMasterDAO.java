/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.HostelMasterIDAO;
import com.jilit.irp.persistence.dto.HostelMaster;
import com.jilit.irp.persistence.dto.HostelOutgoingRequest;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class HostelMasterDAO extends HibernateDAO implements HostelMasterIDAO {

    private static final Log log = LogFactory.getLog(HostelMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all HostelMaster records via Hibernate from the database");
        return this.find("from HostelMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(HostelMaster.class, id);
    }
    public int checkIfChildExist(final String hostelid) {
        HibernateCallback callback = new HibernateCallback() {
          
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                HostelMaster hostelMaster = (HostelMaster) session.get(HostelMaster.class, hostelid);
                int i1 = ((Integer) session.createFilter(hostelMaster.getStudentdisciplinaryactions(), "select count(*)").list().get(0)).intValue();
                int i2 = ((Integer) session.createFilter(hostelMaster.getStudenthosteldetails(), "select count(*)").list().get(0)).intValue();
                int i3 =0;// ((Integer) session.createFilter(hostelMaster.getTemp_studenthosteldetails(), "select count(*)").list().get(0)).intValue();
                return i1 + i2 + i3;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

}