package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Sis_RegistrationActivityMasterIDAO;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMasterId;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ankur.goyal
 */
public class Sis_RegistrationActivityMasterDAO extends HibernateDAO implements Sis_RegistrationActivityMasterIDAO {

    private static final Log log = LogFactory.getLog(Sis_RegistrationActivityMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sis_StudentRegActivities records via Hibernate from the database");
        return this.find("from Sis_StudentRegActivities as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Sis_StudentRegActivities records via Hibernate from the database");
        return this.find("from Sis_StudentRegActivities as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public List getAllDataSis_RegistrationActivityMaster(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select r.id.activityid,r.id.instituteid,r.activityname,r.feeheadid,r.deactive,r.activityentryby,r.activityentrydate,"
                + " r.activityupdateby,r.activityupdatedate,fh.feehead, fh.feeheaddesc from Sis_RegistrationActivityMaster r,FeeHeads fh where fh.id.feeheadid=r.feeheadid and r.id.instituteid=fh.id.instituteid and r.id.instituteid= :instituteid order by r.activityentrydate desc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getFeeHeadList(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select "
                + " distinct fh.id.feeheadid, fh.feehead, fh.feeheaddesc "
                + " from FeeHeads fh "
                + " where fh.id.instituteid=:instituteid and fh.feetype not in ('A') "
                + " and  coalesce(fh.deactive,'N' ) =  'N' order by feehead");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sis_RegistrationActivityMaster.class, id);
    }

    public List doValidate(final Sis_RegistrationActivityMaster dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (mode.equals("save")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(Sis_RegistrationActivityMaster.class);
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    //criteria.add(Restrictions.ne("id.activityid", dto.getId().getActivityid()));
                    criteria.add(Restrictions.eq("activityname", dto.getActivityname()).ignoreCase());
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Activity Name already exist..!!");
            }
        } else if (mode.equals("update")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(Sis_RegistrationActivityMaster.class);
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.add(Restrictions.ne("id.activityid", dto.getId().getActivityid()));
                    criteria.add(Restrictions.eq("activityname", dto.getActivityname()).ignoreCase());
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Activity Name already ..Record can't Update!!");
            }
        }
        return errors;
    }

    public int checkIfChildExist(final Sis_RegistrationActivityMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Sis_RegistrationActivityMaster dto = (Sis_RegistrationActivityMaster) session.get(Sis_RegistrationActivityMaster.class, id);
//                int i1 = Integer.parseInt(session.createFilter(dto.getSis_registrationactivityrightses(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(dto.getSis_studentregactivitieses(), "select count(*)").list().get(0).toString());
                return (i2);
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
