/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentAdmissionSubCategoryIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import com.jilit.irp.persistence.dto.StudentAdmissionSubCategory;
import com.jilit.irp.persistence.dto.StudentAdmissionSubCategoryId;
import java.sql.SQLException;
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
public class StudentAdmissionSubCategoryDAO extends HibernateDAO implements StudentAdmissionSubCategoryIDAO {

    private static final Log log = (Log) LogFactory.getLog(StudentAdmissionSubCategoryDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentAdmissionSubCategory records via Hibernate from the database");
        return this.find("from StudentAdmissionSubCategory as tname");
    }

    @Override
    public Collection<?> findAllStudentAdmissionCategoryCode(String instituteid) {
        log.info("Retrieving all StudentAdmissionCategory records via Hibernate from the database");
        return this.find("from StudentAdmissionCategory as tname where tname.id.instituteid='" + instituteid + "'");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAdmissionSubCategory.class, id);
    }

    @Override
    public List getsub_admittedCategory(String instId, String admittedcategoryid)
    {
        List list=new ArrayList();
        String qryStr=" select id.admittedsubcategoryid, admittedsubcategorycode, admittedsubcategorydesc " +
                " from StudentAdmissionSubCategory " +
                " where " +
                " id.instituteid='"+instId+"' " +
                " and id.admittedcategoryid='"+admittedcategoryid+"' " +
                " and coalesce(deactive,'N')='N' ";
        try{
            list=getHibernateTemplate().find(qryStr);

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<String> doValidate(final StudentAdmissionSubCategory dto, String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (mode.equals("add")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentAdmissionSubCategory.class);
                    criteria.add(Restrictions.eq("admittedsubcategorycode", dto.getAdmittedsubcategorycode()).ignoreCase());
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.add(Restrictions.eq("id.admittedsubcategoryid", dto.getId().getAdmittedsubcategoryid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Admission Sub Category Code Already exist");
            }
        } else if (mode.equals("edit")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentAdmissionSubCategory.class);
                    criteria.add(Restrictions.eq("admittedsubcategorycode", dto.getAdmittedsubcategorycode()).ignoreCase());
                    criteria.add(Restrictions.ne("id.instituteid", dto.getId().getInstituteid()));
                    criteria.add(Restrictions.ne("id.admittedcategoryid", dto.getId().getAdmittedcategoryid()));
                    criteria.add(Restrictions.ne("id.admittedsubcategoryid", dto.getId().getAdmittedsubcategoryid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Admission Sub Category Code Already exist");
            }
        }
        return errors;
    }

    @Override
    public int checkIfChildExist(final StudentAdmissionSubCategoryId id) {
        HibernateCallback callback = new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentAdmissionSubCategory studentadmissionsubcategory = (StudentAdmissionSubCategory) session.get(StudentAdmissionSubCategory.class, id);
                int i1 = Integer.parseInt(session.createFilter(studentadmissionsubcategory.getStudentmasters(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getAllStudentAdmissionSubCategory(String instituteid) {

        String qryString = "select st.id.instituteid,st.id.admittedcategoryid,st.id.admittedsubcategoryid,sc.admittedcategorycode,st.admittedsubcategorycode,st.admittedsubcategorydesc,st.seqid,st.deactive from "
                + " StudentAdmissionSubCategory st,StudentAdmissionCategory sc where st.id.admittedcategoryid = sc.id.admittedcategoryid and st.id.instituteid = '" + instituteid + "' ";

        return getHibernateTemplate().find(qryString);

    }

}
