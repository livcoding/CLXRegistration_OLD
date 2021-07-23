/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.PreRequisitMasterSubjectsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PreRequisitMasterSubjects;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author singh.jaswinder
 */
public class PreRequisitMasterSubjectsDAO extends HibernateDAO implements PreRequisitMasterSubjectsIDAO {

    private static final Log log = LogFactory.getLog(PreRequisitMasterSubjects.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitMasterSubjects records via Hibernate from the database");
        return this.find("from PreRequisitMasterSubjects as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitMasterSubjects.class, id);
    }

//    public void update(Object record) {
//        getHibernateTemplate().update((PreRequisitMasterSubjects) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((PreRequisitMasterSubjects) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((PreRequisitMasterSubjects) record);
//    }
//
//    public int delete(String instituteId, String subjetSetId) {
//
//        String hql = "delete from PreRequisitMasterSubjects pms where pms.id.instituteid = ? and pms.id.subjectsetid = ?";
//        Query query = this.getSession().createQuery(hql);
//        query.setString(0, instituteId);
//        query.setString(1, subjetSetId);
//        int rowCount = query.executeUpdate();
//        return rowCount;
//    }
//
//    public Collection<?> findSubjects(String instituteId, String subjetSetId) {
//        String qry = "from PreRequisitMasterSubjects as prm where prm.id.instituteid = ? and prm.id.subjectsetid = ?";
//        return this.find(qry, new String[]{instituteId, subjetSetId});
//
//    }
}
