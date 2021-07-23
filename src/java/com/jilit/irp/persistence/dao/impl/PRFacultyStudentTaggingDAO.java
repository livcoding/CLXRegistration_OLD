/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.PRFacultyStudentTaggingIDAO;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.math.BigDecimal;

/**
 *
 * @author sunny.singhal
 */
public class PRFacultyStudentTaggingDAO extends HibernateDAO implements PRFacultyStudentTaggingIDAO {

    private static final Log log = LogFactory.getLog(AcademicYearDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PRFacultyStudentTagging records via Hibernate from the database");
        return this.find("from PRFacultyStudentTagging as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRFacultyStudentTagging.class, id);
    }

//
//    public List<PRFacultyStudentTagging> getPRFacStudTaggingData(final List criteriaList,
//            final String regid,
//            final String instid) {
//        String whereClause = "";
//
//
//        for (int i = 0; i < criteriaList.size(); i++) {
//            String str = (String) criteriaList.get(i);
//            String[] arr = str.split(":");
//            if (i > 0) {
//                whereClause = whereClause + " or ";
//            }
//            whereClause = whereClause + " ( fst.academicyear = '" + arr[0] + "'" +
//                    " and fst.programid = '" + arr[1] + "'" +
//                    " and fst.sectionid = '" + arr[2] + "'" +
//                    "  )";
//        }
//        String qry="select prfst.studentmaster.enrollmentno,prfst.studentmaster.name " +
//                " from PRFacultyStudentTagging prfst,FacultySubjectTagging fst where " +
//                " fst.id.instituteid=prfst.id.instituteid and " +
//                " fst.id.fstid=prfst.id.fstid and " +
//                " fst.registrationid='"+regid+"' and " +
//                " ("+whereClause+") and  " +
//                " fst.id.instituteid='"+instid+"' ";
//        System.err.println("Query :- " + qry);
//        ArrayList l = new ArrayList();
//        l = (ArrayList) getHibernateTemplate().find(qry);
//        System.err.println("wwwwwwww" + l.size());
//        return l;
//    }
//
//   public List checkIfChoiceExist(final String instituteid, final String registrationid, final byte stynumber, final String studentid, final String subjectid, final String academicyear, final String programid, final String sectionid, final String subsectionid) {
//
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(FacultyStudentTagging.class, "prfst");
//                criteria.createAlias("prfst.facultysubjecttagging", "fst");
//                criteria.setFetchMode("facultysubjecttagging", FetchMode.JOIN);
//                criteria.add(Expression.eq("prfst.id.instituteid", instituteid));
//                criteria.add(Expression.eq("prfst.id.studentid", studentid));
//                criteria.add(Expression.eq("fst.id.instituteid", instituteid));
//                criteria.add(Expression.eq("fst.registrationid", registrationid));
//                criteria.add(Expression.eq("fst.subjectid", subjectid));
//                criteria.add(Expression.eq("fst.academicyear", academicyear));
//                criteria.add(Expression.eq("fst.programid", programid));
//                criteria.add(Expression.eq("fst.sectionid", sectionid));
//                criteria.add(Expression.eq("fst.subsectionid", subsectionid));
//                criteria.add(Expression.eq("fst.stynumber", stynumber));
//                criteria.setProjection(Projections.distinct(Projections.projectionList()
//                .add(Projections.property("prfst.fstid").as("fstid"))
//                .add(Projections.property("prfst.deactive").as("deactive"))));
//
//                return criteria.list();
//            }
//        });
//
//        return list;
//    }
}
