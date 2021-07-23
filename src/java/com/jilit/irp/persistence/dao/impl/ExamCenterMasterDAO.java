/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ExamCenterMasterIDAO;
import com.jilit.irp.persistence.dto.ExamCenterMaster;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ExamCenterMasterId;
import com.jilit.irp.util.JIRPSession;
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
 * @author ashok.singh
 */
public class ExamCenterMasterDAO extends HibernateDAO implements ExamCenterMasterIDAO {

    private static final Log log = LogFactory.getLog(ExamCenterMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ExamCenterMaster records via Hibernate from the database");
        return this.find("from ExamCenterMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ExamCenterMaster.class, id);
    }

    public int checkIfChildExist(final ExamCenterMasterId examcenterid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ExamCenterMaster examCenterMaster = (ExamCenterMaster) session.get(ExamCenterMaster.class, examcenterid);
                int i1 = ((Integer) session.createFilter(examCenterMaster.getExamcenterwiseroomses(), "select count(*)").list().get(0)).intValue();
                int i2 = 0;//((Integer) session.createFilter(glType.getParameterss(), "select count(*)").list().get(0)).intValue();
                return i1 + i2;
            // return 0;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

 
}


