/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramTypeIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import com.jilit.irp.persistence.dto.ProgramType;
import com.jilit.irp.persistence.dto.ProgramTypeId;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sunny.singhal
 */
public class ProgramTypeDAO extends HibernateDAO implements ProgramTypeIDAO {

    private static final Log log = LogFactory.getLog(ProgramTypeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramType records via Hibernate from the database");
        return this.find("from ProgramType as tname");
    }
    
    public Collection<?> findAllInstituteWise(String instituteid){
        log.info("Retrieving all ProgramType records via Hibernate from the database");
        return this.find("from ProgramType as tname where tname.id.instituteid='" + instituteid + "'");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramType.class, id);
    }

    
    public int checkIfChildExist(final ProgramTypeId id) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramType programType = (ProgramType) session.get(ProgramType.class, id);
                int i1 = Integer.parseInt((session.createFilter(programType.getProgramtypeprogramtaggings(), "select count(*)").list().get(0)).toString());
                int i2 = Integer.parseInt((session.createFilter(programType.getRollnumberingsetupdetails(), "select count(*)").list().get(0)).toString());
                int i3 = Integer.parseInt((session.createFilter(programType.getStudentregistration_info(), "select count(*)").list().get(0)).toString());
                return i1 + i2 + i3 ;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final ProgramType programType, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramType.class);
                criteria.add(Restrictions.eq("id.instituteid", programType.getId().getInstituteid()));
                criteria.add(Restrictions.eq("programtype", programType.getProgramtype()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.programtypeid", programType.getId().getProgramtypeid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Duplicate Program Code ");
        }
        return errors;
    }

}
