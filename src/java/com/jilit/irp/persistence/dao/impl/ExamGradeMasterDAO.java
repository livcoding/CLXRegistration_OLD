/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ExamGradeMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import com.jilit.irp.persistence.dto.ExamGradeMaster;
import com.jilit.irp.persistence.dto.ExamGradeMasterId;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class ExamGradeMasterDAO extends HibernateDAO implements ExamGradeMasterIDAO {

    private static final Log log = (Log) LogFactory.getLog(ExamGradeMasterDAO.class);

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all ExamGradeMaster records via Hibernate from the database");
        return this.find("from ExamGradeMaster as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ExamGradeMaster.class, id);
    }

    public List getGrades(String instituteid) {
        List list = null;
        String qryString = " select egm.id.instituteid,egm.id.gradeid, egm.grade, egm.gradedesc "
                + " from ExamGradeMaster egm "
                + " where egm.id.instituteid='" + instituteid + "'"
                + " and coalesce(egm.deactive,'N') = 'N' order by egm.grade";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
