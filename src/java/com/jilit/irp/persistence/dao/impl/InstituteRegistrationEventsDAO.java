package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.InstituteRegistrationEventsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.InstituteRegistrationEvents;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author singh.jaswinder
 */
public class InstituteRegistrationEventsDAO extends HibernateDAO implements InstituteRegistrationEventsIDAO {

    private static final Log log = LogFactory.getLog(InstituteRegistrationEventsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all InstituteRegistrationEvents records via Hibernate from the database");
        return this.find("from InstituteRegistrationEvents as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from InstituteRegistrationEvents as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(InstituteRegistrationEvents.class, id);
    }

//    public void update(Object record) {
//        getHibernateTemplate().update((InstituteRegistrationEvents) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((InstituteRegistrationEvents) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((InstituteRegistrationEvents) record);
//    }
//
//    public List getAllRegistrationCode(String queryName, String instituteid) {
//        return getHibernateTemplate().findByNamedQuery(queryName, instituteid);
//    }
//
//    public List checkRegistrationCode(String queryName, String instituteid, String regcode) {
//
//        return getHibernateTemplate().findByNamedQuery(queryName, new String[]{instituteid, regcode});
////        return getHibernateTemplate().findByNamedQuery(queryName, instituteid, regcode);
//    }
//
//
//
    public List<String> doValidate(final InstituteRegistrationEvents instituteRegistrationEvents, final String mode) {

        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(InstituteRegistrationEvents.class);
                criteria.add(Restrictions.eq("id.instituteid", instituteRegistrationEvents.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.registrationid", instituteRegistrationEvents.getId().getRegistrationid()));

                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.registrationid", instituteRegistrationEvents.getId().getRegistrationid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Semester Code ");
        }
        return errors;

    }
//    @Override
//     public List getAllInstituteRegistrationEventsData(String instituteid) {
//        String qryString = " select smt  from InstituteRegistrationEvents smt " +
//                " where smt.id.instituteid='" + instituteid + "'" +
//                " and smt.preregistrationallowed='Y' " ;
//        List   list = getHibernateTemplate().find(qryString);
//        return list;
//    }

    @Override
    public List getInstituteRegistrationEventGridData(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ire.id.instituteid,"
                + " ire.id.registrationid,"
                + " rm.registrationcode,"
                + " rm.registrationdesc,"
                + " coalesce (ire.preregistrationallowed, 'N') as preregistrationallowed,"
                + " coalesce (ire.attendentryallowed, 'N') as attendentryallowed,"
                + " coalesce (ire.marksentryallowed, 'N') as marksentryallowed,"
                + " coalesce (ire.gradeentryallowed, 'N') as gradeentryallowed,"
                + " coalesce (ire.srsallowed, 'N') as srsallowed,"
                + " coalesce (ire.supplymentryrequestallowed, 'N') as supplymentryrequestallowed,"
                + " coalesce (ire.hostelallocationallowed, 'N') as hostelallocationallowed"
                + " from InstituteRegistrationEvents ire, RegistrationMaster rm"
                + " where ire.id.instituteid = :instituteid "
                + " and ire.id.instituteid = rm.id.instituteid"
                + " and ire.id.registrationid = rm.id.registrationid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    } 

    @Override
    public List getInstituteRegistrationEventEditData(String instituteid,String registrationid) { 
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ire.id.instituteid,"
                + " ire.id.registrationid,"
                + " rm.registrationcode,"
                + " rm.registrationdesc,"
                + " rm.preventfromdate,rm.preventenddate,"
                + " rm.courseregistrationdatefrom,rm.courseregistrationdateto,"
                + " rm.attendancefromdate,rm.attendancetodate,    "
                + " coalesce (ire.preregistrationallowed, 'N') as preregistrationallowed,"
                + " coalesce (ire.attendentryallowed, 'N') as attendentryallowed,"
                + " coalesce (ire.marksentryallowed, 'N') as marksentryallowed,"
                + " coalesce (ire.gradeentryallowed, 'N') as gradeentryallowed,"
                + " coalesce (ire.srsallowed, 'N') as srsallowed,"
                + " coalesce (ire.supplymentryrequestallowed, 'N') as supplymentryrequestallowed,"
                + " coalesce (ire.hostelallocationallowed, 'N') as hostelallocationallowed"
                + " from InstituteRegistrationEvents ire, RegistrationMaster rm"
                + " where ire.id.instituteid = :instituteid "
                + " and ire.id.registrationid = :registrationid "
                + " and ire.id.instituteid = rm.id.instituteid"
                + " and ire.id.registrationid = rm.id.registrationid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
}
