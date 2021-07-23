package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PRFacultyEventIDAO;
import com.jilit.irp.persistence.dto.PRFacultyEvent;
import com.jilit.irp.persistence.dto.PRFacultyEventId;
import com.jilit.irp.util.JIRPDBUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;




/**
 *
 * @author singh.amarjeet
 */
public class PRFacultyEventDAO extends HibernateDAO implements PRFacultyEventIDAO {

    private static final Log log = LogFactory.getLog(PRFacultyEventDAO.class);

    public Collection<?> findAll() {

        log.info("Retrieving all DepartmentMaster records via Hibernate from the database");
        return this.find("from SubjectMaster as tname");

    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRFacultyEvent.class, id);

    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((PRFacultyEvent) record);


    }

    public int checkIfChildExist(final PRFacultyEventId prfacultyeventid) {


        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PRFacultyEvent prfaculty = (PRFacultyEvent) session.get(PRFacultyEvent.class, prfacultyeventid);


                int i1 = ((Integer) session.createFilter(prfaculty.getPrfacultydaytimepreferences(), "select count(*)").list().get(0)).intValue();
                int i2 = ((Integer) session.createFilter(prfaculty.getPrfacultyeventtaggings(), "select count(*)").list().get(0)).intValue();
                int i3 = ((Integer) session.createFilter(prfaculty.getPrsubjecttypewiseapprovals(), "select count(*)").list().get(0)).intValue();
                int i4 = ((Integer) session.createFilter(prfaculty.getPrfacultysubjectchoiceses(), "select count(*)").list().get(0)).intValue();



                return i1 + i2 + i3 + i4;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();

    }


}
