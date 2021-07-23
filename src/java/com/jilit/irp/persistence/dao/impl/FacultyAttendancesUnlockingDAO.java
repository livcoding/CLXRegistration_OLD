/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.FacultyAttendancesUnlockingIDAO;
import com.jilit.irp.persistence.dto.FacultyAttendancesUnlocking;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.FacultyAttendancesUnlockingId;
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
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author vikash.payasi
 */
public class FacultyAttendancesUnlockingDAO extends HibernateDAO implements FacultyAttendancesUnlockingIDAO {

    private static final Log log = LogFactory.getLog(FacultyAttendancesUnlocking.class);
    private Session session = null;

    public Collection<?> findAll() {

        log.info("Retrieving all FacultyAttendancesUnlocking records via Hibernate from the database");
        return this.find("from FacultyAttendancesUnlocking as tname");

    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(FacultyAttendancesUnlocking.class, id);

    }

    public int checkIfChildExist(final FacultyAttendancesUnlockingId facultyAttendancesUnlockingId) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                FacultyAttendancesUnlocking fau = (FacultyAttendancesUnlocking) session.get(FacultyAttendancesUnlocking.class, facultyAttendancesUnlockingId);
                return 0;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();

    }
}
