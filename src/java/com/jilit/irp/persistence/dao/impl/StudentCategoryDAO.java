/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentCategoryIDAO;
import com.jilit.irp.persistence.dto.StudentCategory;
import com.jilit.irp.persistence.dto.StudentCategoryId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author S.Saurabh
 */
public class StudentCategoryDAO extends HibernateDAO implements StudentCategoryIDAO {

    private static final Log log = LogFactory.getLog(StudentCategoryDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentCategory records via Hibernate from the database");
        return this.find("from StudentCategory as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all StudentCategory records via Hibernate from the database");
        return this.find("from StudentCategory as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentCategory.class, id);
    }

//    @Override
//    public ArrayList getDataForStudentCategory(final String instid) {
//        ArrayList l = (ArrayList) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            @Override
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                String qryString = "SELECT categorycode  ,categoryid" +
//                        " FROM studentcategory where " +
//                        " instituteid = '" + instid + "' ";
//                return session.createSQLQuery(qryString).list();
//            }
//        });
//        return l;
//    }
//
    @Override
    public List getStudentCategoryList(final String instituteid) {
        final List list = (List) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentCategory.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.or(Restrictions.eq("master.deactive", "N"), Restrictions.isNull("master.deactive")));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.categoryid")).add(Projections.property("master.category")));
                return criteria.list();
            }
        });
        return list;
    }
//
    @Override
    public List doValidate(final StudentCategory dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (mode.equals("save")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentCategory.class);
                    criteria.add(Restrictions.eq("categorycode", dto.getCategorycode()));
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Category Code Already exist");
            }
        }
        return errors;
    }

    public int checkIfChildExist(final StudentCategoryId id) {
        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentCategory dto = (StudentCategory) session.get(StudentCategory.class, id);
                int i1 = Integer.parseInt(session.createFilter(dto.getStudentmasters(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(dto.getCategoryprogramdocumenttaggings(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(dto.getStudentdocumentdetails(), "select count(*)").list().get(0).toString());
                int i4 = Integer.parseInt(session.createFilter(dto.getScholarshipcriterias(), "select count(*)").list().get(0).toString());
                int i5 = Integer.parseInt(session.createFilter(dto.getBranchchangerequests(), "select count(*)").list().get(0).toString());
                return i1 + i2 + i3 + i4 + i5;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
