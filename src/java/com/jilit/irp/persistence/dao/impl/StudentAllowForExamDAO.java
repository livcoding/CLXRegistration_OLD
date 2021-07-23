/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentAllowForExamIDAO;
import com.jilit.irp.persistence.dto.StudentAllowForExam;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 *
 * @author shivrajs.umbarkar
 */
public class StudentAllowForExamDAO extends HibernateDAO implements StudentAllowForExamIDAO{

    private static final Log log = (Log) LogFactory.getLog(StudentAllowForExam.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAllowForExam records via Hibernate from the database");
        return this.find("from StudentAllowForExam as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAllowForExam.class, id);
    }

}
