 /*014762
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentEventSubjectMarksIDAO;
import com.jilit.irp.persistence.dto.ExamEventSubjectTagging;
import com.jilit.irp.persistence.dto.StudentEventSubjectMarks;
import com.jilit.irp.util.JIRPStringUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.Map;

/**
 *
 * @author v.kumar
 */
public class StudentEventSubjectMarksDAO extends HibernateDAO implements StudentEventSubjectMarksIDAO {

    private static final Log log = LogFactory.getLog(StudentEventSubjectMarksDAO.class);
    private Session session = null;

    public Collection<?> findAll() {
        log.info("Retrieving all StudentEventSubjectMarks records via Hibernate from the database");
        return this.find("from StudentEventSubjectMarks as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentEventSubjectMarks.class, id);
    }

}
