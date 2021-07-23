/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentActivityMasterIDAO;
import com.jilit.irp.persistence.dto.StudentActivityMaster;
import com.jilit.irp.persistence.dto.StudentActivityMasterId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author chetna.pargai
 */
public class StudentActivityMasterDAO extends HibernateDAO implements StudentActivityMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentActivityMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentActivityMaster records via Hibernate from the database");
        return this.find("from StudentActivityMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentActivityMaster.class, id);
    }

    public int checkIfChildExist(final StudentActivityMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentActivityMaster StudentActivityMaster = (StudentActivityMaster) session.get(StudentActivityMaster.class, id);
                int i1 = ((Integer) session.createFilter(StudentActivityMaster.getStudentactivitydetails(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List doValidate(final StudentActivityMaster dto) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentActivityMaster.class, "slm");
                criteria.add(Restrictions.eq("slm.id.instituteid", dto.getId().getInstituteid()));
                criteria.add(Restrictions.ne("slm.id.activityid", dto.getId().getActivityid()));
                criteria.add(Restrictions.disjunction().add(Restrictions.eq("slm.activitycode", dto.getActivitycode()).ignoreCase()).add(Restrictions.eq("slm.activitydescription", dto.getActivitydescription()).ignoreCase()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Student Activity Code Already exist");
        }
        return errors;
    }
     public List getAll_ActivityCode(String instituteid) {
         List list= null;
        String qryString = " select distinct c.id.activityid, c.id.instituteid, c.activitycode, c.activitydescription " +
                " from StudentActivityMaster  c " +
                " where coalesce(c.deactive,'N')='N' and c.id.instituteid='" + instituteid + "'";
        try
        {
        list = getHibernateTemplate().find(qryString);
        
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        return list;
      
    }

    public List getStudentActivityReportData(String instituteid, String activityid,String ordertype,String sortedtype) {
        List list = null;
        String qryString = " select distinct c.activitycode, c.activitydescription,c.mindays,c.maxdays,c.halfday,c.deactive " +
                " from StudentActivityMaster  c " +
                " where c.id.instituteid='" + instituteid + "' ";
        if(! "ALL".equals(activityid)){
            qryString = qryString+" and c.id.activityid='" + activityid + "' ";
        }
                if ("Ascending".equals(sortedtype)) {
            if ("activitycode".equals(ordertype)) {
                qryString = qryString + " order by c." + ordertype + " asc";
            } else if ("activitydescription".equals(ordertype)) {
                qryString = qryString +" order by c." + ordertype + " asc";
            }
        } else if ("Descending".equals(sortedtype)) {
            if ("activitycode".equals(ordertype)) {
                qryString = qryString + " order by c." + ordertype + " desc";
            } else if ("activitydescription".equals(ordertype)) {
                qryString = qryString + " order by c." + ordertype + " desc";
            }
        }
         try {
            list = getHibernateTemplate().find(qryString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

    