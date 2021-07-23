/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

//import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.ProgramSchemeIDAO;
import java.util.Collection;
import org.apache.commons.logging.Log;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ProgramScheme;

import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWise;
import java.io.Serializable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.*;

/**
 *
 * @author subrata.lohar/Mohit1.kumar
 */
public class ProgramSchemeDAO extends HibernateDAO implements ProgramSchemeIDAO {

    private static final Log log = LogFactory.getLog(ProgramSchemeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramScheme records via Hibernate from the database");
        return this.find("from ProgramScheme as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramScheme.class, id);
    }

    @Override
    public void update(Object record) {
        getHibernateTemplate().update((ProgramScheme) record);
    }

//    @Override
//    public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((ProgramScheme) record);
//    }
//
//    @Override
//    public void add(Object record) {
//        getHibernateTemplate().save((ProgramScheme) record);
//    }
//
//    @Override
//    public void delete(Object record) {
//        getHibernateTemplate().delete((ProgramScheme) record);
//
//    }
//
//    public Collection<?> getPopUpProgramData(final String instituteid, final String[] programid, final String[] sectionid, final Byte[] stynumber) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(ProgramScheme.class, "master");
//                criteria.createAlias("master.programschemedetails", "psd");
//                criteria.setFetchMode("programschemedetails", FetchMode.JOIN);
//
//                criteria.add(Expression.eq("master.id.instituteid", instituteid)).add(Expression.in("master.programid", programid)).add(Expression.in("master.sectionid", sectionid)).add(Expression.in("master.stynumber", stynumber));
//
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid")).add(Projections.property("master.id.programschemeid").as("programschemeid")).add(Projections.property("master.stynumber").as("stynumber")).add(Projections.property("master.subjectid").as("subjectid")).add(Projections.property("master.sectionid").as("sectionid")).add(Projections.property("master.departmentid").as("departmentid")).add(Projections.property("master.programid").as("programid")).add(Projections.property("master.basketid").as("basketid")).add(Projections.property("psd.totalccpmarks").as("totalccpmarks")).add(Projections.property("psd.id.subjectcomponentid").as("subjectcomponentid")).add(Projections.property("psd.ltppassingmarks").as("ltppassingmarks")).add(Projections.property("psd.noofclassinaweek").as("noofclassinaweek")).add(Projections.property("psd.noofhours").as("noofhours")) //.add(Projections.property("sc.subjectcomponentid").as("subjectcomponentid"))
//                        .add(Projections.property("psd.byltp").as("byltp")).add(Projections.property("psd.deactive").as("deactive")));
//
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                System.err.println(criteria.list().size());
//
//                // criteria.addOrder(Order.asc("master.grade"));
//                return criteria.list();
//            }
//        });
//        return list;
//
//    }
//
//    public Collection<?> findPSDuplicateData(final String instituteid, final String Dprogramid, final String Dbasketid, final String Dsubjectid, final String Dsectionid, final Byte Dstynumber) {
//
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(ProgramScheme.class, "master");
//
//
//                criteria.add(Expression.eq("master.id.instituteid", instituteid)).add(Expression.eq("master.programid", Dprogramid)).add(Expression.eq("master.basketid", Dbasketid)).add(Expression.eq("master.subjectid", Dsubjectid)).add(Expression.eq("master.subjectid", Dsectionid)).add(Expression.eq("master.stynumber", Dstynumber));
//
//
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid")).add(Projections.property("master.id.programschemeid").as("programschemeid")) //                        .add(Projections.property("master.stynumber").as("stynumber"))
//                        //                        .add(Projections.property("master.subjectid").as("subjectid"))
//                        //                        .add(Projections.property("master.sectionid").as("sectionid"))
//                        //                        .add(Projections.property("master.departmentid").as("departmentid"))
//                        //                        .add(Projections.property("master.programid").as("programid"))
//                        //                        .add(Projections.property("master.basketid").as("basketid"))
//                        //                        .add(Projections.property("psd.totalccpmarks").as("totalccpmarks"))
//                        //                        .add(Projections.property("psd.id.subjectcomponentid").as("subjectcomponentid"))
//                        //                        .add(Projections.property("psd.ltppassingmarks").as("ltppassingmarks"))
//                        //                        .add(Projections.property("psd.noofclassinaweek").as("noofclassinaweek"))
//                        //                        .add(Projections.property("psd.noofhours").as("noofhours"))
//                        //                        //.add(Projections.property("sc.subjectcomponentid").as("subjectcomponentid"))
//                        //                        .add(Projections.property("psd.byltp").as("byltp"))
//                        .add(Projections.property("master.deactive").as("deactive")));
////
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                System.err.println("sssssssssssssssssssssssss-------------------" + criteria.list().size());
//
//                // criteria.addOrder(Order.asc("master.grade"));
//                return criteria.list();
//            }
//        });
//        return list;
//
//    }
//
//    public List<String> doValidate(final ProgramScheme programScheme, final String mode) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        //Unique Key Constraint
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(ProgramScheme.class);
//                criteria.add(Expression.eq("id.instituteid", programScheme.getId().getInstituteid()));
//                criteria.add(Expression.eq("programid", programScheme.getProgramid()).ignoreCase());
//                criteria.add(Expression.eq("basketid", programScheme.getBasketid()).ignoreCase());
//                criteria.add(Expression.eq("sectionid", programScheme.getSectionid()).ignoreCase());
//                criteria.add(Expression.eq("stynumber", programScheme.getStynumber()));
//                criteria.add(Expression.eq("subjectid", programScheme.getSubjectid()).ignoreCase());
//                if (mode.equals("edit")) {
//                    criteria.add(Expression.ne("id.programschemeid", programScheme.getId().getProgramschemeid()));//Do not check for itself when updating record
//                }
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
//        if (count.intValue() > 0) {
//            errors.add("Duplicate Record Exist !");
//        }
//        return errors;
//    }
//
//
    public List getSubjectTaggingList(final String instituteid, final String academicyear, final String sectionid, final String programid, final byte semister, final String basketid, String registrationid, String subjectid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select");
        qry.append(" new map(sm.subjectcode as subjectcode,");
        qry.append(" psad.subjectid as subjectid,");
        qry.append(" psad.id.instituteid as instituteid,");
        qry.append(" psd.id.subjectcomponentid as subjectcomponentid,");
        qry.append(" sm.subjectdesc as subjectdesc,");
        qry.append(" psd.ltppassingmarks as ltppassingmarks,");
        qry.append(" psd.noofhours as noofhours,");
        qry.append(" psd.noofclassinaweek as noofclassinaweek,");
        qry.append(" psd.totalccpmarks as totalccpmarks,");
        qry.append(" psad.credits as credits,");
        qry.append(" psad.departmentid as departmentid,");
        qry.append(" psd.byltp as byltp,");
        qry.append(" psad.totalmarks as totalmarks,");
        qry.append(" psad.passingmarks as passingmarks,");
        qry.append(" psd.totalclasses as totalclasses, ");
        qry.append(" (select stm.subjecttype from SubjectTypeMaster stm, BasketMaster bm  where  stm.id.instituteid = bm.id.instituteid ");
        qry.append(" and stm.id.subjecttypeid = bm.subjecttypeid and bm.id.instituteid = psad.id.instituteid and psad.basketid = bm.id.basketid) as subjecttype,psad.subjecttype as subjecttype1,psad.subjecttypeid as subjecttypeid ,coalesce(psad.auditsubject,'N') as auditsubject ) ");
        qry.append(" from ProgramSchemeAcadyearWise psad, SubjectMaster sm, ProgramSchemeAcadYearDetail psd");
        qry.append(" where");
        qry.append(" psad.id.instituteid=sm.id.instituteid");
        qry.append(" and psad.subjectid=sm.id.subjectid ");
        if (!subjectid.equals(" ")) {
            qry.append(" and sm.id.subjectid = :subjectid");
        } else {
            qry.append(" and :subjectid=:subjectid ");
        }
        qry.append(" and psad.id.instituteid=psd.id.instituteid ");
        qry.append(" and psad.id.programschemeacadwiseid=psd.id.programschemeacadwiseid");
        qry.append(" and psad.id.instituteid= :instituteid ");
        qry.append(" and psad.academicyear= :academicyear ");
        qry.append(" and psad.sectionid= :sectionid");
        qry.append(" and psad.programid= :programid ");
        qry.append(" and psad.stynumber= :semister");
        qry.append(" and psad.basketid= :basketid "
                + " and not exists ( "
                + " select 1 from ProgramSubjectTagging pst  "
                + " where pst.id.instituteid=psad.id.instituteid "
                + " and  pst.academicyear=psad.academicyear "
                + " and pst.sectionid=psad.sectionid "
                + " and pst.programid=psad.programid "
                + " and pst.stynumber= psad.stynumber "
                + " and pst.basketid= psad.basketid "
                + " and pst.subjectid=psad.subjectid "
                + " and pst.id.registrationid=:registrationid "
                + " )");
        qry.append(" order by psad.subjectid asc ");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("academicyear", academicyear)
                .setParameter("sectionid", sectionid)
                .setParameter("programid", programid)
                .setParameter("semister", semister)
                .setParameter("basketid", basketid)
                .setParameter("subjectid", subjectid)
                .setParameter("registrationid", registrationid).list();

        return list;
    }

    public Collection<?> getSubjectTaggingListFromProgramScheme(final String instituteid, final String sectionid, final String programid, final byte semister, final String basketid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ProgramScheme.class, "master").createAlias("master.programschemedetails", "psd").setFetchMode("programschemedetails", FetchMode.JOIN).createAlias("subjectmaster", "sm").setFetchMode("subjectmaster", FetchMode.JOIN);
                criteria.add(Expression.eq("id.instituteid", instituteid));
                criteria.add(Expression.eq("sectionid", sectionid));
                criteria.add(Expression.eq("programid", programid));
                criteria.add(Expression.eq("stynumber", semister));
                criteria.add(Expression.eq("basketid", basketid));
                criteria.setProjection(Projections.projectionList().
                        add(Projections.distinct(Projections.property("sm.subjectcode").as("subjectcode"))).
                        add(Projections.property("master.subjectid").as("subjectid")).
                        add(Projections.property("master.id.instituteid").as("instituteid")).
                        add(Projections.property("psd.id.subjectcomponentid").as("subjectcomponentid")).
                        add(Projections.property("sm.subjectdesc").as("subjectdesc")).
                        add(Projections.property("psd.ltppassingmarks").as("ltppassingmarks")).
                        add(Projections.property("psd.noofhours").as("noofhours")).
                        add(Projections.property("psd.noofclassinaweek").as("noofclassinaweek")).
                        add(Projections.property("psd.totalccpmarks").as("totalccpmarks")).
                        add(Projections.property("master.credits").as("credits")).
                        add(Projections.property("master.departmentid").as("departmentid")).
                        add(Projections.property("psd.byltp").as("byltp")).
                        add(Projections.property("psd.totalclasses").as("totalclasses")));
                criteria.addOrder(Order.asc("sm.subjectcode"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

//    public String insertProgramScheme(BusinessService businessService, final List<ProgramScheme> prList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();//getHibernateTemplate().getSessionFactory().getCurrentSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + prList.size());
//            for (int i = 0; i < prList.size(); i++) {
//                System.err.println("************* value" + i);
//                // session.save((PRFacultySubjectTimePreference) prList.get(i));
//                businessService.insertInIdGenerationControl((ProgramScheme) prList.get(i));
//            }
//
//            retList = "Success";
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            retList = "Error in tx update";
//            e.printStackTrace();
//        } finally {
//            session.close();
//            businessService.closeSession();
//        }
//        return retList;
//    }
//
//    public List getDataForAutomaticSelection(String instituteid, String programid, String sectionid, byte stynumber, String subjecttypeSTR) {
//        List list = null;
//        String qry = " select p.subjectid, p.departmentid,s.subjectcode,d.departmentcode, p.credits, s.subjectdesc, p.id.programschemeid , p.basketid " +
//                " from ProgramScheme p ,SubjectMaster s,DepartmentMaster d " +
//                " where p.departmentid=d.id.departmentid " +
//                " and p.id.instituteid=s.id.instituteid" +
//                " and p.subjectid=s.id.subjectid " +
//                " and p.id.instituteid='" + instituteid + "' " +
//                " and p.programid='" + programid + "' " +
//                " and p.sectionid='" + sectionid + "' " +
//                " and p.stynumber='" + stynumber + "' " +
//                " and p.basketid in (select b.id.basketid from BasketMaster b " +
//                " where b.id.instituteid='" + instituteid + "' " +
//                " and b.programid='" + programid + "' " +
//                " and b.sectionid='" + sectionid + "' " +
//                " and b.stynumber='" + stynumber + "' " +
//                " and b.subjecttypeid in (" + subjecttypeSTR + ")) ";
//        try {
//            System.out.println("query" + qry);
//            list = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
    public List getProgramSchemeList(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String basketid) {
        List list = new ArrayList();
        StringBuilder qry = new StringBuilder();
        qry.append(" select a.subjectid, a.departmentid, a.credits, a.electiveid, a.totalmarks, a.passingmarks, a.id.programschemeacadwiseid, sb.subjectcode, sb.subjectdesc,a.subjecttype,a.subjecttypeid");
        qry.append("  from ProgramSchemeAcadyearWise a, SubjectMaster sb");
        qry.append(" where a.academicyear =:academicyear");
        qry.append(" and a.stynumber = :stynumber");
        qry.append(" and a.programid = :programid");
        qry.append(" and a.sectionid = :sectionid");
        qry.append(" and a.basketid = :basketid");
        qry.append(" and a.id.instituteid = :instituteid");
        qry.append(" and sb.id.instituteid = a.id.instituteid");
        qry.append(" and sb.id.subjectid = a.subjectid");

        try {
            byte stno = Byte.valueOf(stynumber);
            list = getHibernateSession().createQuery(qry.toString()).
                    setParameter("instituteid", instituteid).setParameter("academicyear", academicyear)
                    .setParameter("stynumber", stno)
                    .setParameter("programid", programid).setParameter("sectionid", sectionid)
                    .setParameter("basketid", basketid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getTeachingSchemeData(String instituteid, String programid, String academicyear, String sectionid, String styno,String registrationid) {
        List list = new ArrayList();
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct psaw.id.programschemeacadwiseid,psaw.basketid,psaw.subjectid,psaw.departmentid,"
                + " psaw.credits,psaw.totalmarks,psaw.passingmarks,psaw.subjecttypeid,psaw.subjecttype,psaw.auditsubject from ProgramSchemeAcadyearWise psaw"
                + " where psaw.id.instituteid = :instituteid "
                + " and psaw.academicyear = :academicyear "
                + " and psaw.programid = :programid "
                + " and psaw.sectionid = :sectionid "
                + " and psaw.stynumber = :styno"
                + " and not exists ( "
                + " select 1 from ProgramSubjectTagging pst  "
                + " where pst.id.instituteid=psaw.id.instituteid "
                + " and  pst.academicyear=psaw.academicyear "
                + " and pst.sectionid=psaw.sectionid "
                + " and pst.programid=psaw.programid "
                + " and pst.stynumber= psaw.stynumber "
                + " and pst.basketid= psaw.basketid "
                + " and pst.subjectid=psaw.subjectid "
                + " and pst.id.registrationid=:registrationid "
                + " )");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("academicyear", academicyear)
                    .setParameter("sectionid", sectionid)
                    .setParameter("registrationid", registrationid)
                    .setParameter("programid", programid)
                    .setParameter("styno", new Byte(styno)).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getProgramSchemeDetail(String instituteid, String programschemeacadwiseid) {
        List list = new ArrayList();
        StringBuilder qry = new StringBuilder();
        qry.append(" select a.ltppassingmarks, a.totalccpmarks, a.noofhours, a.noofclassinaweek, a.totalclasses, a.byltp, a.totalmarks, a.passingmarks, a.id.subjectcomponentid");
        qry.append(" from ProgramSchemeAcadYearDetail a");
        qry.append(" where a.id.instituteid = :instituteid");
        qry.append(" and a.id.programschemeacadwiseid = :programschemeacadwiseid");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("programschemeacadwiseid", programschemeacadwiseid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean checkProgramSchemeExistence(String instituteid, String academicyear, String programid, String sectionid, String stynumber, String subjectid) {
        List list = null;
        boolean flag;
        StringBuilder qry = new StringBuilder();
        qry.append(" select 1");
        qry.append("  from ProgramSchemeAcadyearWise a");
        qry.append(" where a.academicyear = :academicyear");
        qry.append(" and a.stynumber = :stynumber");
        qry.append(" and a.programid = :programid");
        qry.append(" and a.sectionid = :sectionid");
        qry.append(" and a.id.instituteid = :instituteid");
        qry.append(" and a.subjectid = :subjectid");
        try {
            byte stno = Byte.valueOf(stynumber);

            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("academicyear", academicyear)
                    .setParameter("stynumber", stno)
                    .setParameter("programid", programid)
                    .setParameter("sectionid", sectionid)
                    .setParameter("instituteid", instituteid)
                    .setParameter("subjectid", subjectid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public List dovalidate(String instituteid, String academicyear, String programid, byte stynumber, String sectionid, String basketid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select 1 from ProgramSchemeAcadyearWise pst where pst.id.instituteid=(:instituteid) and "
                + " pst.academicyear =(:academicyear) and pst.programid=(:programid) and pst.stynumber=(:stynumber)  and"
                + " pst.sectionid=(:sectionid)    and   pst.subjectid=(:subjectid) ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("sectionid", sectionid).
                    setParameter("programid", programid).
                    setParameter("stynumber", stynumber).
                    setParameter("academicyear", academicyear).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

}
