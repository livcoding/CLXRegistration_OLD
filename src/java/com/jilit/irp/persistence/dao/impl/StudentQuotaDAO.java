/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dto.StudentQuota;
import com.jilit.irp.persistence.dao.StudentQuotaIDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentQuotaId;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ashok.singh
 */
public class StudentQuotaDAO extends HibernateDAO implements StudentQuotaIDAO {

    private static final Log log = LogFactory.getLog(StudentQuotaDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all StudentQuota records via Hibernate from the database");
        return this.find("from StudentQuota as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentQuota.class, id);
    }

    @Override
    public List getAllQuota(String instituteid) {
        String query = "select sq.id.quotaid,sq.quotacode,sq.quota from StudentQuota sq where sq.id.instituteid='" + instituteid + "' order by sq.seqid";
        return getHibernateTemplate().find(query);
    }
//
//    @Override
//    public List checkIfQuotaExist(String instituteid, String quota) {
//        String query = "select sq.id.quotaid, sq.seqid from StudentQuota sq " +
//                "where sq.id.instituteid='" + instituteid + "'  " +
//                "and sq.quota ='" + quota + "' " +
//                "order by sq.seqid ";
//        return getHibernateTemplate().find(query);
//    }
//
    @Override
    public List doValidate(final StudentQuota dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);

        if ("save".equals(mode)) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentQuota.class, "sq");
                    criteria.add(Restrictions.eq("sq.id.instituteid", dto.getId().getInstituteid()));
                    criteria.add(Restrictions.ne("sq.id.quotaid", dto.getId().getQuotaid()));
                    criteria.add(Restrictions.eq("sq.quotacode", dto.getQuotacode()).ignoreCase());
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Quota Code Already exist");
            }
        }

        //************************For Updating the record************************//
        if ("edit".equals(mode)) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentQuota.class, "sq");
                    criteria.add(Restrictions.ne("sq.id.instituteid", dto.getId().getInstituteid()));
                    criteria.add(Restrictions.ne("sq.id.quotaid", dto.getId().getQuotaid()));
                    criteria.add(Restrictions.eq("sq.quotacode", dto.getQuotacode()).ignoreCase());
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Quota Code Already exist");
            }
        }
        return errors;  // error size 0 means no record match
    }

    @Override
    public int checkIfChildExist(final StudentQuotaId id) {
        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                StudentQuotaId ids = new StudentQuotaId();
//                ids.setInstituteid(uid.split("::")[0]);
//                ids.setQuotaid(uid.split("::")[1]);
                StudentQuota sq = (StudentQuota) session.get(StudentQuota.class, id);
                int i1 = Integer.parseInt(session.createFilter(sq.getStudentmasters(), "select count(*)").list().get(0).toString());
//                int i2 = ((Integer) session.createFilter(sq.getTemp_studentmasters(), "select count(*)").list().get(0)).intValue();
//                int i3 = ((Integer) session.createFilter(sq.getStudentfeediscounts(), "select count(*)").list().get(0)).intValue();
//                int i4 = ((Integer) session.createFilter(sq.getStudentspecialapprovals(), "select count(*)").list().get(0)).intValue();
//                int i5 = ((Integer) session.createFilter(sq.getEventwiseheadwisefine_grps(), "select count(*)").list().get(0)).intValue();
//                int i6 = ((Integer) session.createFilter(sq.getEventwisefine_grps(), "select count(*)").list().get(0)).intValue();
                int i2 = Integer.parseInt(session.createFilter(sq.getStudentregistration(), "select count(*)").list().get(0).toString());
//                return i1 + i2 + i3 + i4 + i5 + i6 + i7;
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
//
//    @Override
//    public List getAllQuotaData(String instituteid) {
//        List list = null;
//        String qryString = " select distinct c.id.quotaid, c.id.instituteid, c.quota, c.quotacode " +
//                " from StudentQuota  c " +
//                " where c.id.instituteid='" + instituteid + "'";
//        try {
//            list = getHibernateTemplate().find(qryString);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//
//    }
//
//    public List getStudentQuotaReportData(String instituteid, String quotaid, String ordertype, String sortedtype) {
//        List list = null;
//        String qryString = " select distinct c.quotacode, c.quota,c.seqid " +
//                " from StudentQuota  c " +
//                " where c.id.instituteid='" + instituteid + "' ";
//        if (!"ALL".equals(quotaid)) {
//            qryString = qryString + " and c.id.quotaid='" + quotaid + "' ";
//        }
//        if ("Ascending".equals(sortedtype)) {
//            if ("quotacode".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " asc";
//            } else if ("quota".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " asc";
//            } else if ("seqid".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " asc";
//            }
//        } else if ("Descending".equals(sortedtype)) {
//            if ("quotacode".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " desc";
//            } else if ("quota".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " desc";
//            } else if ("seqid".equals(ordertype)) {
//                qryString = qryString + " order by c." + ordertype + " desc";
//            }
//        }
//        try {
//            list = getHibernateTemplate().find(qryString.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    @Override
//    public String getQuotaId(final String instituteid, final String quotacode) {
//        List list = null;
//        String quotaid = null;
//        String query = " select sq.id.quotaid  from StudentQuota sq " +
//                " where sq.id.instituteid='" + instituteid + "'  " +
//                " and sq.quotacode ='" + quotacode + "' ";
//        try {
//            list = (List) getHibernateTemplate().find(query);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        if (list != null && !list.isEmpty()) {
//            quotaid = String.valueOf(list.get(0));
//        }
//        return quotaid;
//    }
//    public List getAllQuota1(String instituteid) {
//        String query = "select sq.id.quotaid,sq.quota,sq.quotacode from StudentQuota sq where sq.id.instituteid='" + instituteid + "'";
//        return getHibernateTemplate().find(query);
//    }
//    public List checkIfQuotaCodeExist(String instituteid, String quotacode) {
//        String query = "select sq.id.quotaid,sq.quota,sq.quotacode from StudentQuota sq where sq.id.instituteid='" + instituteid + "'"
//                        +" and sq.quota ='" + quotacode + "' ";
//        return getHibernateTemplate().find(query);
//    }
    }
