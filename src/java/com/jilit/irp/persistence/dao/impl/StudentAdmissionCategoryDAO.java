/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentAdmissionCategoryIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import com.jilit.irp.persistence.dto.StudentAdmissionCategory;
import com.jilit.irp.persistence.dto.StudentAdmissionCategoryId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class StudentAdmissionCategoryDAO extends HibernateDAO implements StudentAdmissionCategoryIDAO {

    private static final Log log = (Log) LogFactory.getLog(StudentAdmissionCategoryDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAdmissionCategory records via Hibernate from the database");
        return this.find("from StudentAdmissionCategory as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAdmissionCategory.class, id);
    }


      public int checkIfChildExist(final StudentAdmissionCategoryId id) {
        HibernateCallback callback = new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentAdmissionCategory dto = (StudentAdmissionCategory) session.get(StudentAdmissionCategory.class, id);
                int i1 = Integer.parseInt(session.createFilter(dto.getStudentadmissionsubcategories(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }


    public List doValidate(final StudentAdmissionCategory dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (mode.equals("save")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentAdmissionCategory.class);
                   criteria.add(Restrictions.eq("admittedcategorycode", dto.getAdmittedcategorycode()).ignoreCase());
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Admission Category Code Already exist");
            }
        }
        if (mode.equals("edit")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentAdmissionCategory.class);
                    criteria.add(Restrictions.eq("admittedcategorycode", dto.getAdmittedcategorycode()).ignoreCase());
                    criteria.add(Restrictions.ne("id.admittedcategoryid", dto.getId().getAdmittedcategoryid()));
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Admission Category Code Already exist");
            }
        }
        return errors;
    }
//
//
//
    
    public List getadmittedCategory(String instId)
    {
        List list=new ArrayList();
        String qryStr=" select id.admittedcategoryid, admittedcategorycode, admittedcategorydesc" +
                " from StudentAdmissionCategory" +
                " where " +
                " id.instituteid='"+instId+"' " +
                " and coalesce(deactive,'N')='N' ";
        try{
            list=getHibernateTemplate().find(qryStr);

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
