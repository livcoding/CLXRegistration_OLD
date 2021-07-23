/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


import com.jilit.irp.persistence.dao.PreRequisitAcadWiseRequiredIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PreRequisitAcadWiseRequired;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;



/**
 *
 * @author singh.jaswinder
 */
public class PreRequisitAcadWiseRequiredDAO extends HibernateDAO implements PreRequisitAcadWiseRequiredIDAO  {

private static final Log log = LogFactory.getLog(PreRequisitAcadWiseRequired.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitAcadWiseRequired records via Hibernate from the database");
        return this.find("from PreRequisitAcadWiseRequired as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitAcadWiseRequired.class, id);
    }


//     public void update(Object record) {
//        getHibernateTemplate().update((PreRequisitAcadWiseRequired) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((PreRequisitAcadWiseRequired) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((PreRequisitAcadWiseRequired) record);
//    }
//
//    public int delete(final String instituteId, final String subjetSetId) {
//           final Object object = getHibernateTemplate().execute(new HibernateCallback() {
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//            String hql = "delete from PreRequisitAcadWiseRequired prm where prm.id.instituteid = ? and prm.id.subjectsetid = ?";
//            Query query = session.createQuery(hql);
//            query.setString(0, instituteId);
//            query.setString(1, subjetSetId);
//            return (Object)query.executeUpdate();
//            }
//        });
//        return Integer.parseInt(object.toString());
//    }
//
//    public Collection<?> findSubjects_InstituteSubjectSet(String instituteId, String subjetSetId) {
//        String qry = "from PreRequisitAcadWiseRequired as prm where prm.id.instituteid = ? and prm.id.subjectsetid = ?";
//        return this.find(qry, new String[]{instituteId, subjetSetId});
//
//    }
}
