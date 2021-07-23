/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OtherStaffSpecTaggingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.OtherStaffSpecTagging;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
/**
 *
 * @author chetna.pargai
 */
public class OtherStaffSpecTaggingDAO extends HibernateDAO implements OtherStaffSpecTaggingIDAO
{
    private static final Log log = LogFactory.getLog(OtherStaffSpecTagging.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OtherStaffSpecTagging records via Hibernate from the database");
        return this.find("from OtherStaffSpecTagging as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OtherStaffSpecTagging.class, id);
    }

//     public List getGridDataAccordingToFacultyCode(final String osid){
//        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(OtherStaffSpecTagging.class);
//                criteria.add(Restrictions.eq("id.osid", osid));
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//     public List doValidate(final OtherStaffSpecTagging dto) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        /* Check For Primary Key*/
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(OtherStaffSpecTagging.class, "mp");
//                criteria.add(Expression.eq("mp.id.osid", dto.getId().getOsid()));
//                criteria.add(Expression.eq("mp.id.subjectid", dto.getId().getSubjectid()));
//                criteria.add(Expression.eq("mp.instituteid", dto.getInstituteid()));
//                criteria.add(Expression.eq("mp.id.specializationcategoryid", dto.getId().getSpecializationcategoryid()));
//                criteria.add(Expression.eq("mp.id.specializationid", dto.getId().getSpecializationid()).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue() > 0) {
//            errors.add("Same Values already exist");
//        }
//        return errors;
//
//    }
//
}
