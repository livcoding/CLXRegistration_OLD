package com.jilit.irp.persistence.dao.impl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jilit.irp.persistence.dto.RegistrationMasterId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.LoadDistributionApprovalIDAO;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.LoadDistributionGrant;
import com.jilit.irp.persistence.dto.ProgramWiseSubsection;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

public class LoadDistributionApprovalDAO extends HibernateDAO implements LoadDistributionApprovalIDAO {

    private static final Log log = LogFactory.getLog(LoadDistributionApprovalDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all LoadDistributionApproval records via Hibernate from the database");
        return this.find("from LoadDistributionApproval as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RegistrationMaster.class, id);
    }

    public int checkIfChildExist(final String instituteid, final String registrationid) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                RegistrationMaster regmaster = (RegistrationMaster) session.get(RegistrationMaster.class, new RegistrationMasterId(instituteid, registrationid));
                int i1 = ((Integer) session.createFilter(regmaster.getOfferedodsubjecttaggings(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

//    public List<String> doValidate(final RegistrationMaster registrationMaster, final String mode) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//        public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(RegistrationMaster.class);
//                criteria.add(Expression.eq("id.instituteid", registrationMaster.getId().getInstituteid()));
//                criteria.add(Expression.eq("registrationcode", registrationMaster.getRegistrationcode()).ignoreCase());
//                if (mode.equals("edit")) {
//                    criteria.add(Expression.ne("id.registrationid", registrationMaster.getId().getRegistrationid()));//Do not check for itself when updating record
//                }
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
//        if (count.intValue() > 0) {
//            errors.add("Duplicate Registration Code ! ");
//        }
//        return errors;
//    }
//
//    public Collection<?> getAllRegistrationCode_FromPRFacultyEvent(final String instituteid, final String registrationcode) {
//
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//
//                Criteria criteria = session.createCriteria(RegistrationMaster.class, "master").createAlias("master.prfacultyevents", "pfe").setFetchMode("prfacultyevents", FetchMode.JOIN);
//
//                criteria.add(Restrictions.or(Restrictions.isNull("master.lockregistration"),Restrictions.eq("master.lockregistration", "N")))
//                            .add(Expression.eq("master.id.instituteid", instituteid))
//                            .add(Restrictions.or(Restrictions.isNull("master.finalized"), Restrictions.eq("master.finalized", "N")))
//                            .add(Restrictions.or(Restrictions.isNull("pfe.eventbroadcast"), Restrictions.eq("pfe.eventbroadcast", "Y")))
//                            .add(Restrictions.or(Restrictions.isNull("pfe.eventcompleted"), Restrictions.eq("pfe.eventcompleted", "N")))
//                            .add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", "N")));
//
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid"))
//                        .add(Projections.property("master.id.registrationid").as("registrationid"))
//                        .add(Projections.property("master.registrationcode").as("registrationcode"))
//                        .add(Projections.property("master.registrationdesc").as("registrationdesc"))
//                        .add(Projections.property("pfe.id.eventno").as("eventno"))
//                        .add(Projections.property("master.deactive").as("deactive")));
//
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//
//                return criteria.list();
//            }
//        });
//        return list;
//
//    }
//
//    public Collection<?> getAllSubjectType(final String department) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(DepartmentMaster.class, "master").createAlias("master.teachingloaddistributions", "tld").setFetchMode("teachingloaddistributions", FetchMode.JOIN);
//
//                criteria.add(Expression.eq("master.department", department));
//
//                criteria.setProjection(Projections.projectionList().add(Projections.property("tld.id.instituteid").as("instituteid")).add(Projections.property("tld.id.subjectid").as("subjectid")).add(Projections.property("tld.id.subjectcomponentid").as("subjectcomponentid")).add(Projections.property("tld.id.subsectionid").as("subsectionid")).add(Projections.property("tld.id.staffid").as("staffid")));
//
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//
//                return criteria.list();
//            }
//        });
//        return list;
//
//    }
//
//    public Collection<?> finddata(final String instituteid, final String subsectionid) {
//
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(ProgramWiseSubsection.class, "master");
//
//                criteria.add(Expression.eq("master.id.instituteid", instituteid));
//                criteria.add(Expression.eq("master.id.subsectionid", subsectionid));
//
//                criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("master.subsectioncode").as("subsectioncode"))));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//
//    }
//
//    public String insertLoadDistributionApproval(final List<LoadDistributionGrant> ldaList) {
//        String retList = null;
//        Session session = null;
//        Transaction tr = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tr = session.beginTransaction();
//            System.err.println("*********** in transaction " + ldaList.size());
//            for (int i = 0; i < ldaList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.saveOrUpdate((LoadDistributionGrant) ldaList.get(i));
//            }
//
//            retList = "Success";
//            tr.commit();
//        } catch (Exception e) {
//            if (tr != null) {
//                tr.rollback();
//            }
//            retList = "Error in tr update";
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return retList;
//    }
}