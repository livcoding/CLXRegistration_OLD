/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OfferedODSubjectTaggingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.*;

/**
 *
 * @author deepak.gupta
 */
public class OfferedODSubjectTaggingDAO extends HibernateDAO implements OfferedODSubjectTaggingIDAO {

    private static final Log log = LogFactory.getLog(OfferedODSubjectTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OfferedODSubjectTagging records via Hibernate from the database");
        return this.find("from OfferedODSubjectTagging as tname ");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all OfferedODSubjectTagging records via Hibernate from the database");
        return this.find("from OfferedODSubjectTagging as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OfferedODSubjectTagging.class, id);
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((OfferedODSubjectTagging) record);
    }

    public List<String> doValidate(final OfferedODSubjectTagging offeredODSubjectTagging, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(OfferedODSubjectTagging.class);
                criteria.add(Restrictions.eq("id.instituteid", offeredODSubjectTagging.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.registrationid", offeredODSubjectTagging.getId().getRegistrationid()).ignoreCase());
                criteria.add(Restrictions.eq("odsubjectid", offeredODSubjectTagging.getOdsubjectid()).ignoreCase());

                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.offersubjectid", offeredODSubjectTagging.getId().getOffersubjectid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Record Exist ! ");
        }
        return errors;
    }

    public List getOfferSubjectTaggingGridData(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.registrationcode,sm.subjectcode,ost.credits,(select sb.subjectcode from SubjectMaster sb where sb.id.instituteid = ost.id.instituteid "
                + " and sb.id.subjectid = ost.odsubjectid) as currentsubjectcode,dm.department,ost.deactive,ost.id.instituteid,ost.id.registrationid,ost.id.offersubjectid "
                + " from OfferedODSubjectTagging ost,RegistrationMaster rm,SubjectMaster sm,DepartmentMaster dm where ost.id.instituteid = rm.id.instituteid "
                + " and ost.id.registrationid = rm.id.registrationid and ost.id.instituteid = sm.id.instituteid and ost.currentsubjectid = sm.id.subjectid "
                + " and ost.departmentid = dm.departmentid and ost.id.instituteid=:instituteid and ost.id.registrationid=:registrationid order by sm.subjectcode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
