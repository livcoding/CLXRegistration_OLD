/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


import com.jilit.irp.persistence.dao.PreRequisitAcademicSubjectsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PreRequisitAcademicSubjects;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;



/**
 *
 * @author singh.jaswinder
 */
public class PreRequisitAcademicSubjectsDAO extends HibernateDAO implements PreRequisitAcademicSubjectsIDAO  {

private static final Log log = LogFactory.getLog(PreRequisitAcademicSubjects.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitAcademicSubjects records via Hibernate from the database");
        return this.find("from PreRequisitAcademicSubjects as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitAcademicSubjects.class, id);
    }

//     public void update(Object record) {
//        getHibernateTemplate().update((PreRequisitAcademicSubjects) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((PreRequisitAcademicSubjects) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((PreRequisitAcademicSubjects) record);
//    }
//
//    public int delete(String instituteId, String subjetSetId) {
//        String hql = "delete from PreRequisitAcademicSubjects pms where pms.id.instituteid = ? and pms.id.subjectsetid = ?";
//        Query query = this.getSession().createQuery(hql);
//        query.setString(0, instituteId);
//        query.setString(1, subjetSetId);
//        int rowCount = query.executeUpdate();
//        return rowCount;
//    }
//
//    public Collection<?> findSubjects_InstituteSubjectSet(String instituteId, String subjetSetId) {
//        String qry = "from PreRequisitAcademicSubjects as prm where prm.id.instituteid = ? and prm.id.subjectsetid = ?";
//        return this.find(qry, new String[]{instituteId, subjetSetId});
//    }
}
