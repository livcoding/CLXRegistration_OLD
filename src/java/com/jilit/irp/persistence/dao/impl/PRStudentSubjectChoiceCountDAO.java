/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PRStudentSubjectChoiceCountIDAO;

import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 *
 * @author akshya.gaur
 */
public class PRStudentSubjectChoiceCountDAO extends HibernateDAO implements PRStudentSubjectChoiceCountIDAO {

    private static final Log log = LogFactory.getLog(PRStudentSubjectChoiceCountDAO.class);

    public Collection<?> findAll() {

        log.info("Retrieving all PRStudentSubjectChoiceCount records via Hibernate from the database");
        return this.find("from PRStudentSubjectChoiceCount as tname");

    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRStudentSubjectChoiceCount.class, id);

    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((PRStudentSubjectChoiceCount) record);

    }

    public List getPRStudentSubjectChoiceCountData(String instituteid, String registrationid, String studentid) {
        List list = new ArrayList();
        try {
            String qry = "from PRStudentSubjectChoiceCount s"
                    + " where s.id.instituteid =:instituteid "
                    + " and s.id.registrationid =:registrationid "
                    + " and s.id.studentid =:studentid ";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid)
                    .setParameter("studentid", studentid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deletePRStudentSubjectChoiceCountData(String instituteid, String registrationid, String studentid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete PRStudentSubjectChoiceCount s where s.id.instituteid =:instituteid and s.id.registrationid =:registrationid "
                    + " and s.id.studentid =:studentid");
        try {
            Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid);
            qry.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
