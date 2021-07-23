/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Ex_PatternMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Ex_PatternMaster;
import com.jilit.irp.persistence.dto.Ex_PatternMasterId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;

/**
 *
 * @author Malkeet Singh
 */
public class Ex_PatternMasterDAO extends HibernateDAO implements Ex_PatternMasterIDAO {

    private static final Log log = LogFactory.getLog(Ex_PatternMasterDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all Ex_PatternMaster records via Hibernate from the database");
        return this.find("from Ex_PatternMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Ex_PatternMaster records via Hibernate from the database");
        return this.find("from Ex_PatternMaster as tname where tname.id.instituteid = ? order by tname.seqid desc", new Object[]{instituteid});
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Ex_PatternMaster.class, id);

    }

    @Override
    public List doValidate(final Ex_PatternMaster master, String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);

        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Ex_PatternMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", master.getId().getInstituteid()).ignoreCase());
                criteria.add(Restrictions.eq("patterncode", master.getPatterncode()).ignoreCase());
                if ("edit".equals(mode)) {
                    criteria.add(Restrictions.ne("id.patternid", master.getId().getPatternid()).ignoreCase());
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Pattern Code already exists!");
        }
        return errors;  // error size 0 means no record match
    }

    public List getAllPatternMaster(String instituteid) {
        List list = null;
        String qry1 = " select a from Ex_PatternMaster a" + " where a.id.instituteid='" + instituteid + "'" + " and coalesce(a.deactive,'N')='N'"
                + " order by a.patterncode";
        try {
            list = getHibernateTemplate().find(qry1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSeqId(String instituteid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select exp.seqid from Ex_PatternMaster exp where exp.id.instituteid=:instituteid order by exp.seqid desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//
//    @Override
//    public int checkIfChildExist(final Ex_PatternMasterId patternid) {
//        HibernateCallback callback = new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Ex_PatternMaster master = (Ex_PatternMaster) session.get(Ex_PatternMaster.class, patternid);
//                int i1 =0;// ((Integer) session.createFilter(master.getExameventmasters(), "select count(*)").list().get(0)).intValue();
//                int i2 = ((Integer) session.createFilter(master.getSubjectmasters(), "select count(*)").list().get(0)).intValue();
//                return i1 + i2;
//            }
//        };
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    }
//
// public List getPatternCode(String instituteid,String patternid){
//        List list = null;
//        String qry1 = " select a.patterncode from Ex_PatternMaster a" + " where a.id.instituteid='" + instituteid + "' and a.id.patternid='"+ patternid +"' and coalesce(a.deactive,'N')='N'";
//        try {
//            list = getHibernateTemplate().find(qry1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public List checkIfExamPatternExists(String instituteid, String pattrencode) {
//        List list = null;
//        String qry1 = " select a from Ex_PatternMaster a" +
//                " where a.id.instituteid='" + instituteid + "'"  +
//                " and a.patterncode='" + pattrencode + "'";
//        try {
//            list = getHibernateTemplate().find(qry1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public List getExamPattern_Attendance(final String instituteid, final String registrationid) {
//        List retList = null;
//        String qry = "select distinct sm.patternid," +
//                " pm.patterncode, pm.patternname" +
//                " from Ex_PatternMaster pm, SubjectMaster sm, Ex_AttendanceMarksCriteria amc" +
//                " where pm.id.instituteid = sm.id.instituteid" +
//                " and pm.id.patternid = sm.patternid" +
//                " and pm.id.instituteid = amc.id.instituteid" +
//                " and pm.id.patternid = amc.id.patternid" +
//                " and sm.id.instituteid = '" + instituteid + "'" +
//                " and sm.id.subjectid in" +
//                " (select fst.subjectid" +
//                " from FacultySubjectTagging fst" +
//                " where fst.id.instituteid = '" + instituteid + "'" +
//                " and fst.registrationid = '" + registrationid + "')" +
//                " and coalesce(sm.deactive,'N')='N'" +
//                " and coalesce(pm.deactive,'N')='N'" +
//                " order by sm.patternid";
//        try {
//            retList = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return retList;
//    }
}
