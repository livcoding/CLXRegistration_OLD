/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramMaxStyIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.BranchMaster;
import com.jilit.irp.persistence.dto.ProgramMaxSty;
import com.jilit.irp.persistence.dto.ProgramMaxStyId;
import com.jilit.irp.persistence.dto.StyDesc;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Shimona.Khandelwal
 */
public class ProgramMaxStyDAO extends HibernateDAO implements ProgramMaxStyIDAO {

    private static final Log log = LogFactory.getLog(ProgramMaxStyDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramMaxSty records via Hibernate from the database");
        return this.find("from ProgramMaxSty as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramMaxSty.class, id);
    }

    public int checkIfChildExist(final ProgramMaxStyId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramMaxSty programMaxSty = (ProgramMaxSty) session.get(ProgramMaxSty.class, id);

                int i1 = Integer.parseInt((session.createFilter(programMaxSty.getProgramminmaxlimits(), "select count(*)").list().get(0)).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getProgramMaxStyData(String instituteid) {

        String qryString = "Select pm.programcode, bm.branchcode,ins.institutecode,pgm.id.academicyear,pgm.startsty,pgm.endsty,pgm.startdate,pgm.enddate,pgm.exampattern,pgm.deactive,pgm.stypattern,pgm.id.instituteid,pgm.id.academicyear,pgm.id.programid,pgm.id.branchid "
                + " from  ProgramMaxSty pgm,ProgramMaster pm,BranchMaster bm,InstituteMaster ins  where pgm.id.instituteid='" + instituteid + "' and "
                + " pgm.id.programid = pm.id.programid and pgm.id.branchid = bm.id.branchid and pgm.id.instituteid = ins.id.instituteid  ";
        return getHibernateTemplate().find(qryString);

    }

    public List editProgramMaxStyData(String instituteid, String programid, String acad_year, String branchid) {

        String qryString = "Select pm.programcode, bm.branchcode,ins.institutecode,pgm.id.academicyear,pgm.startsty,pgm.endsty,pgm.startdate,pgm.enddate,pgm.exampattern,pgm.deactive,pgm.stypattern,pgm.id.instituteid,pgm.id.academicyear,pgm.id.programid,pgm.id.branchid "
                + " from  ProgramMaxSty pgm,ProgramMaster pm,BranchMaster bm,InstituteMaster ins  where pgm.id.instituteid='" + instituteid + "' and pgm.id.programid = '" + programid + "' and pgm.id.academicyear='" + acad_year + "'  and pgm.id.branchid='" + branchid + "' and "
                + " pgm.id.programid = pm.id.programid and pgm.id.branchid = bm.id.branchid and pgm.id.instituteid = ins.id.instituteid  ";
        return getHibernateTemplate().find(qryString);

    }

    public List<String> doValidate(final ProgramMaxSty programMaxSty) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramMaxSty.class);
                criteria.add(Restrictions.eq("id.instituteid", programMaxSty.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.branchid", programMaxSty.getId().getBranchid()));
                criteria.add(Restrictions.eq("id.programid", programMaxSty.getId().getProgramid()));
                criteria.add(Restrictions.eq("id.academicyear", programMaxSty.getId().getAcademicyear()));
                /*if (mode.equals("edit")) {
                criteria.add(Expression.ne("id.gradeid", gradePayScale.getId().getGradeid()));//Do not check for itself when updating record
                }*/
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Duplicate Program Max STY");
        }
        return errors;
    }

    public List getSemester(String program_id, String acad_year) {             /// for get the min and max semester for multiple programs..
        String qryString = "select min(prg.startsty) , max(prg.endsty) from ProgramMaxSty prg "
                + "  where prg.id.academicyear='" + acad_year + "'   and ( prg.id.programid in (" + program_id + ")) ";
        return getHibernateTemplate().find(qryString);

    }

    public List getSemesterCode(String ins_id, String prog_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select min(prg.startsty) , max(prg.endsty) from ProgramMaxSty prg"
                + " where  prg.id.instituteid= :ins_id "
                + " and prg.id.programid= :prog_id ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).
                    setParameter("prog_id", prog_id).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

   

    public String insertProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            System.err.println("*********** in transaction " + programMaxStysList.size());
            for (int i = 0; i < programMaxStysList.size(); i++) {
                System.err.println("************* value" + i);
                session.save((ProgramMaxSty) programMaxStysList.get(i));
            }
            for (int i = 0; i < styDescsList.size(); i++) {
                session.saveOrUpdate((StyDesc) styDescsList.get(i));
            }
            retList = "Data Save Successfully";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public String updateProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            System.err.println("*********** in transaction " + programMaxStysList.size());
            for (int i = 0; i < programMaxStysList.size(); i++) {
                System.err.println("************* value" + i);
                session.update((ProgramMaxSty) programMaxStysList.get(i));
            }
            for (int i = 0; i < styDescsList.size(); i++) {
                session.saveOrUpdate((StyDesc) styDescsList.get(i));
            }
            retList = "Data Updated Successfully !";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public List getBranchCode(final String instituteid, final String progid, final String acad_year) {
        List list = null;
        String strqry = "select bm.id.instituteid,bm.id.programid,bm.id.branchid, bm.registrationcardlabel "
                + " from BranchMaster bm where bm.id.instituteid='" + instituteid + "' and bm.id.programid in (" + progid + ") ";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
