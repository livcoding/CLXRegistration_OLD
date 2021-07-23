/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dto.EligibilityExamInfo;
import com.jilit.irp.persistence.dto.EligibilityExamInfoDetail;
import com.jilit.irp.persistence.dto.EligibilityExamInfoDetailId;
import com.jilit.irp.persistence.dto.EligibilityExamInfoId;
import com.jilit.irp.persistence.dao.EligibilityExamInfoIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deepak
 */
public class EligibilityExamInfoDAO extends HibernateDAO implements EligibilityExamInfoIDAO {

    private static final Log log = LogFactory.getLog(EligibilityExamInfoIDAO.class);
    Session session1 = null;
    Transaction tx1 = null;

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(EligibilityExamInfo.class, id);
    }

    public Object findByPrimaryKeyDetail(Serializable id) {
        return getHibernateTemplate().get(EligibilityExamInfoDetail.class, id);
    }

    public Collection<?> findAll() {
        log.info("Retrieving all EligibilityExamInfo records via Hibernate from the database");
        return this.find("from EligibilityExamInfo as tname");
    }
    
    public Collection<?> findAllInstituteWise(String instituteid) {
        log.info("Retrieving all EligibilityExamInfo records via Hibernate from the database");
        return this.find("from EligibilityExamInfo as tname where tname.id.instituteid='"+instituteid+"'");
    }
    
    public List doValidate(final EligibilityExamInfo master, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(EligibilityExamInfo.class);
                criteria.add(Restrictions.eq("eeexamcode", master.getEeexamcode()));
                criteria.add(Restrictions.eq("id.instituteid", master.getId().getInstituteid()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Same Values Of Exam Info Exist");
        }
        return errors;
    }

    public List doValidate1(final EligibilityExamInfoDetail master, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(EligibilityExamInfoDetail.class);
                criteria.add(Restrictions.eq("id.eeexamid", master.getId().getEeexamid()));
                criteria.add(Restrictions.eq("id.instituteid", master.getId().getInstituteid()));
                criteria.add(Restrictions.eq("eeinfoshortname", master.getEeinfoshortname()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Same Values Of Eligibility ExamInfo Detail Exist");
        }
        return errors;

    }

    public int checkIfChildExist(final EligibilityExamInfoId id) {
        HibernateCallback callback = new HibernateCallback() {
            int i = 0;

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EligibilityExamInfo master = (EligibilityExamInfo) session.get(EligibilityExamInfo.class, id);
                int i1 = Integer.parseInt(session.createFilter(master.getEligibilityexaminfodetails(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(master.getProgrameligibilityexamses(), "select count(*)").list().get(0).toString());
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public int checkIfChildExistdetail(final EligibilityExamInfoDetailId id) {
        HibernateCallback callback = new HibernateCallback() {
            int i = 0;

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                EligibilityExamInfoDetail master = (EligibilityExamInfoDetail) session.get(EligibilityExamInfoDetail.class, id);
                int i1 = Integer.parseInt(session.createFilter(master.getStudenteligibilityexaminfos(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<Object[]> getGridData(final String instituteid) {

        List list = null;
        String qryString = " select a.id.instituteid,a.id.eeexamid,a.eeexamcode, a.eeexamname ,a.seqid,a.entryby,a.entrydate from EligibilityExamInfo a where a.id.instituteid='" + instituteid + "' order by a.seqid ";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getAllEligibilityExamInfoDetailgridData(final String eeexamid) {

        List list = null;
        String qryString = "select a.id.instituteid,  a.id.eeexamid,    a.id.eeinfoid,  a.eeinfoshortname,   a.eeinfoname,  a.eeetypeofinfo, a.seqid  from  EligibilityExamInfoDetail a   where a.id.eeexamid='" + eeexamid + "'   order by   a.seqid";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
