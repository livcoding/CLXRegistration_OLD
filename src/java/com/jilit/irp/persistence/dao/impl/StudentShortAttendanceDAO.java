  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentShortAttendanceIDAO;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentShortAttendance;

import com.jilit.irp.persistence.dto.SubjectMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Jaswinder
 */
public class StudentShortAttendanceDAO extends HibernateDAO implements StudentShortAttendanceIDAO {

    private static final Log log = LogFactory.getLog(StudentShortAttendanceDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentShortAttendance records via Hibernate from the database");
        return this.find("from StudentShortAttendance as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentShortAttendance.class, id);

    }

    public Collection<?> findByPrimaryKeys(Serializable id) {
        return this.find("from StudentShortAttendance as tname");
    }

//    public List<String> doValidate(final StudentShortAttendance studentShortAttendance, final String mode) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        //Unique Key Constraint
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(StudentShortAttendance.class);
//                criteria.add(Restrictions.eq("id.instituteid", studentShortAttendance.getId().getInstituteid()));
//                criteria.add(Restrictions.eq("id.registrationid", studentShortAttendance.getId().getRegistrationid()).ignoreCase());
//                criteria.add(Restrictions.eq("id.subjectid", studentShortAttendance.getId().getSubjectid()).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
//        if (count.intValue() > 0) {
//            errors.add("Duplicate Record Exist ! ");
//        }
//        return errors;
//    }
//
//    public Collection<?> getShortAttendanceData(final String instituteid, final String registrationid, final String studentid, final String subjectid) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(StudentShortAttendance.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteid)).add(Restrictions.eq("master.id.registrationid", registrationid)).add(Restrictions.eq("master.id.studentid", studentid)).add(Restrictions.eq("master.id.subjectid", subjectid));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.attendancepercent").as("attendancepercent")));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//    public Collection<?> getAllStudentShortAttendance(final String registrationid, final String instituteid) {
//        // System.err.println ("asdcStudentShortAttendnce");
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(StudentShortAttendance.class, "master").createAlias("master.studentmaster", "stud").setFetchMode("studentmaster", FetchMode.JOIN);
//                criteria.add(Restrictions.eq("master.id.registrationid", registrationid)).add(Restrictions.eq("master.id.instituteid", instituteid));
//                criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("stud.studentid").as("studentid")).add(Projections.property("stud.enrollmentno").as("enrollmentno")).add(Projections.property("stud.name").as("name"))));
//                criteria.addOrder(Order.asc("stud.name"));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//
//            }
//        });
//        return list;
//    }
//
//    public Collection<?> getShortAttendanceDetail(final String instituteid, final String registrationid, final String[] studentid) {
//        List listsend = new ArrayList();
//
//        try {
//            listsend = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//                public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                    Criteria criteria =
//                            session.createCriteria(StudentMaster.class, "master").createAlias("master.studentadddresses", "sa").setFetchMode("studentaddress", FetchMode.JOIN).createAlias("master.branchmaster", "bm").setFetchMode("branchmaster", FetchMode.JOIN).createAlias("bm.programmaster", "pm").setFetchMode("programmaster", FetchMode.JOIN).createAlias("master.studentshortattendances", "shortmaster").setFetchMode("studentshortattendance", FetchMode.JOIN);
//
//                    criteria.add(Restrictions.eq("shortmaster.id.instituteid", instituteid)).add(Restrictions.eq("shortmaster.id.registrationid", registrationid)).add(Restrictions.in("shortmaster.id.studentid", studentid));
//
//                    criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("master.studentid").as("studentid")).add(Projections.property("master.name").as("name")).add(Projections.property("master.enrollmentno").as("enrollmentno")).add(Projections.property("master.stynumber").as("stynumber")).add(Projections.property("master.fathersname").as("fathername")).add(Projections.property("sa.caddress1").as("caddress1")).add(Projections.property("sa.caddress2").as("caddress2")).add(Projections.property("sa.caddress3").as("caddress3")).add(Projections.property("sa.cdistrict").as("cdistrict")).add(Projections.property("sa.cpostoffice").as("cpostoffice")).add(Projections.property("sa.ccityname").as("ccityname")).add(Projections.property("sa.cstatename").as("cstatename")).add(Projections.property("sa.cpin").as("cpin")).add(Projections.property("pm.programdesc").as("programdesc")).add(Projections.property("bm.branchdesc").as("branchdesc"))));
//
//
//
//                    criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                    return criteria.list();
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return listsend;
//        }
//        return listsend;
//    }
//
//    public Collection<?> getShortAttendanceSubject(final String instituteid, final String registrationid, final String studentid) {
//
//        final List listsend = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria =
//                        session.createCriteria(SubjectMaster.class, "master").createAlias("master.studentshortattendances", "shortmaster").setFetchMode("studentshortattendance", FetchMode.JOIN);
//
//                criteria.add(Restrictions.eq("shortmaster.id.instituteid", instituteid)).add(Restrictions.eq("shortmaster.id.registrationid", registrationid)).add(Restrictions.eq("shortmaster.id.studentid", studentid));
//
//                criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("master.subjectcode").as("subjectcode")).add(Projections.property("master.subjectdesc").as("subjectdesc"))));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//
//        return listsend;
//    }
//
//    public int getMinShortAttendanceAllowed(final String instid) {
//
//        //--get the values from the parameter table
//        String pModuleID = "MOD08000007";
//        int NOD = 0;
//        List list2 = null;
//        String qry = "select parametervalue as amount from Parameters a where a.id.instituteid='" + instid + "' and a.id.moduleid= '" + pModuleID + "'" +
//                " and a.id.parameterid ='B1.2'";
//        try {
//            list2 = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (list2.size() > 0) {
//            Object str = list2.get(0);
//            NOD = Integer.parseInt(str.toString());
//        }
//        return NOD;
//
//    }
//    public int getMinShortAttendanceForLagging(final String instituteid, final String companyid, final String moduleid, final String parameterid) {
//
//        //--get the values from the parameter table
//        int NOD = 0;
//        List list2 = null;
//        String qry = "select parametervalue as amount from Parameters a where a.id.instituteid='" + instituteid + "' and a.id.companyid = '"+companyid+"' and a.id.moduleid= '" + moduleid + "'" +
//                " and a.id.parameterid ='"+parameterid+"'";
//        try {
//            list2 = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (list2.size() > 0) {
//            Object str = list2.get(0);
//            NOD = Integer.parseInt(str.toString());
//        }
//        return NOD;
//
//    }
}
