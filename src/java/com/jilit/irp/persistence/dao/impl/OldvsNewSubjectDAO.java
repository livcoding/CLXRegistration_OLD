package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OldvsNewSubjectIDAO;
import com.jilit.irp.persistence.dto.OldvsNewSubject;
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
public class OldvsNewSubjectDAO extends HibernateDAO implements OldvsNewSubjectIDAO {

    private static final Log log = LogFactory.getLog(OldvsNewSubjectDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all OldvsNewSubject records via Hibernate from the database");
        return this.find("from OldvsNewSubject as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OldvsNewSubject.class, id);
    }

    @Override
    public List getSubjectcode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid, sm.subjectcode,sm.subjectdesc from SubjectMaster sm where sm.id.instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSemesterCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid, rm.registrationcode, rm.registrationdesc from RegistrationMaster rm where rm.id.instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllDataOldVsNewSubject(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select os.id.registrationid,os.id.subjectid,os.newsubjectid,os.wef, os.remarks, os.deactive, os.entryby, os.entrydatetime,"
                + " sm.subjectcode,sm.subjectdesc,(select sb.subjectcode from SubjectMaster sb where sb.id.instituteid = os.id.instituteid and "
                + " sb.id.subjectid = os.newsubjectid) as newsubcode from OldvsNewSubject os, RegistrationMaster rm, SubjectMaster sm where "
                + " os.id.registrationid = rm.id.registrationid and os.id.subjectid = sm.id.subjectid and os.id.instituteid = sm.id.instituteid "
                + " and os.id.instituteid = rm.id.instituteid and os.id.instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getEditOldvsNewSubject(String instituteid, String registrationid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select os.id.registrationid,os.id.subjectid,os.newsubjectid,os.wef,os.remarks,os.deactive,sm.subjectcode,sm.subjectdesc,"
                + " rm.id.registrationid,rm.registrationcode,rm.registrationdesc from OldvsNewSubject os, RegistrationMaster rm, SubjectMaster sm "
                + " where os.id.registrationid = rm.id.registrationid and os.id.subjectid = sm.id.subjectid and os.id.instituteid = sm.id.instituteid "
                + " and os.id.instituteid = rm.id.instituteid and os.id.instituteid = :instituteid and os.id.registrationid = :registrationid and os.id.subjectid =:subjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> doValidate(final OldvsNewSubject dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(OldvsNewSubject.class);
                criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.subjectid", dto.getId().getSubjectid()));
                criteria.add(Restrictions.eq("newsubjectid", dto.getNewsubjectid()));
//                if (mode.equals("edit")) {
//                    criteria.add(Restrictions.eq("id.subjectid", dto.getId().getSubjectid()));
//                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Same Values Of Subject Code exist");
        }
        return errors;
    }
}
