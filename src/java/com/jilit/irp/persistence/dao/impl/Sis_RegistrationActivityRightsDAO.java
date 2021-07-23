package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Sis_RegistrationActivityRightsIDAO;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityRights;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ankur.goyal
 */
public class Sis_RegistrationActivityRightsDAO extends HibernateDAO implements Sis_RegistrationActivityRightsIDAO {

    private static final Log log = LogFactory.getLog(Sis_RegistrationActivityRightsDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sis_RegistrationActivityRights records via Hibernate from the database");
        return this.find("from Sis_RegistrationActivityRights as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sis_RegistrationActivityRights.class, id);
    }

    public List doValidate(final Sis_RegistrationActivityRights dto, final String mode) {
        List<String> retList = new ArrayList<String>();
        Integer count = new Integer(0);

        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override 
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Sis_RegistrationActivityRights.class);
                criteria.add(Restrictions.eq("id.activityid", dto.getId().getActivityid()));
                criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.staffid", dto.getId().getStaffid()));
                criteria.add(Restrictions.eq("id.stafftype", dto.getId().getStafftype()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            retList.add("Activity Code already exist");
        }
        return retList;
    }

    public List getAllSis_RegistrationActivityRights(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rar.id.instituteid,rar.id.activityid,rar.id.staffid,rar.id.stafftype,rar.fromdate,rar.tilldate,rar.deactive, ram.activityname, "
                + " ram.activityentryby,vs.employeecode,vs.employeename from Sis_RegistrationActivityRights rar, Sis_RegistrationActivityMaster ram,V_Staff vs where vs.employeeid = rar.id.staffid  and rar.id.instituteid = ram.id.instituteid "
                + " and rar.id.activityid = ram.id.activityid and rar.id.instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getRegistrationActivityData(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ram.id.activityid as actid, ram.activityname as actname from Sis_RegistrationActivityMaster ram "
                + " where ram.id.instituteid = :instituteid and coalesce(ram.deactive,'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public List getAllStaffCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select v.employeeid, v.employeecode, v.employeename, v.stafftype from V_Staff v where v.placeofpostingid in (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N'))");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getEditDataSis_RegistrationActivityRights(String instituteid, String activityid, String staffid, String stafftype) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rar.id.instituteid, rar.id.activityid, rar.id.staffid, rar.id.stafftype, rar.fromdate, rar.tilldate, rar.deactive, ram.activityname, ram.activityentryby,"
                + " v.employeecode,v.employeename  from Sis_RegistrationActivityRights rar, Sis_RegistrationActivityMaster ram, V_Staff v where rar.id.instituteid = ram.id.instituteid "
                + " and rar.id.activityid = ram.id.activityid and  v.employeeid = rar.id.staffid and rar.id.instituteid = :instituteid and rar.id.activityid = :activityid "
                + " and rar.id.staffid = :staffid and rar.id.stafftype = :stafftype ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("activityid", activityid).setParameter("staffid", staffid).setParameter("stafftype", stafftype).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
