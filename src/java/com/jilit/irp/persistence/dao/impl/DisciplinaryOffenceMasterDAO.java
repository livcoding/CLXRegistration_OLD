/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.DisciplinaryOffenceMasterIDAO;
import com.jilit.irp.persistence.dto.DisciplinaryOffenceMaster;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ashok.singh
 */
public class DisciplinaryOffenceMasterDAO  extends HibernateDAO implements DisciplinaryOffenceMasterIDAO {

    private static final Log log = LogFactory.getLog(DisciplinaryOffenceMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all DisciplinaryOffenceMaster records via Hibernate from the database");
        return this.find("from DisciplinaryOffenceMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(DisciplinaryOffenceMaster.class, id);
    }

    public int checkIfChildExist(final DisciplinaryOffenceMaster id) {
       HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                DisciplinaryOffenceMaster disciplinaryOffenceMaster = (DisciplinaryOffenceMaster) session.get(DisciplinaryOffenceMaster.class, id.getOffenceid());
                int i1 = Integer.parseInt(session.createFilter(disciplinaryOffenceMaster.getStudentdisciplinaryactions(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List doValidate(final DisciplinaryOffenceMaster disciplinaryOffence) {
        

        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(DisciplinaryOffenceMaster.class);
                criteria.add(Restrictions.eq("offencecode", disciplinaryOffence.getOffencecode()).ignoreCase());
                criteria.add(Restrictions.ne("offenceid", disciplinaryOffence.getOffenceid()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Offence Code Already exist");
        }
        return errors;
    }
//    public List getOffenceLevel()
//    {
//        String qryString = "select d.offenceid, d.offencecode, d.offencelevel from DisciplinaryOffenceMaster d group by " +
//                           " d.offenceid, d.offencecode, d.offencelevel";
//        List list = getHibernateTemplate().find(qryString);
//        return list;
//    }
//
}
