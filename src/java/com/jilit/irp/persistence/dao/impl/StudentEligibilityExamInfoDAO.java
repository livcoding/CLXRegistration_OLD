/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentEligibilityExamInfoIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentEligibilityExamInfo;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author deepak.gupta
 */
public class StudentEligibilityExamInfoDAO extends HibernateDAO implements StudentEligibilityExamInfoIDAO {

    private static final Log log = LogFactory.getLog(StudentEligibilityExamInfoDAO.class);
    Session session1 = null;
    Transaction tx1 = null;

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentEligibilityExamInfo.class, id);
    }

    public Collection<?> findAll() {
        log.info("Retrieving all StudentEligibilityExamInfo records via Hibernate from the database");
        return this.find("from StudentEligibilityExamInfo as tname");
    }
    
    public int deleteStudentEligibilityExamInfo(final String instituteid, final String eeexamid, final String eeinfoid, final String studentid) {

        Object noofrecords = (Object) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                int count = 0;
                try {
                    String qry = "delete from StudentEligibilityExamInfo a " +
                            " where a.id.instituteid = ? " +
                            " and a.id.eeexamid=? " +
                            " and a.id.eeinfoid=? " +
                            " and a.id.studentid=? ";

                    Query query = session.createQuery(qry);
                    query.setString(0, instituteid);
                    query.setString(1, eeexamid);
                    query.setString(2, eeinfoid);
                    query.setString(3, studentid);
                    count = query.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return count;
            }
        });
        if (noofrecords != null) {
            return Integer.parseInt(noofrecords.toString());
        }
        return 0;
    }
}
