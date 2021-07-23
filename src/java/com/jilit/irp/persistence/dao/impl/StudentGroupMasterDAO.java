/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentGroupMasterIDAO;
import com.jilit.irp.persistence.dto.StudentGroupMaster;
import com.jilit.irp.persistence.dto.StudentGroupMasterId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ankit.kumar
 */
public class StudentGroupMasterDAO extends HibernateDAO implements StudentGroupMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentGroupMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentGroupMaster records via Hibernate from the database");
        return this.find("from StudentGroupMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentGroupMaster.class, id);
    }

    public List getStudentGroupMaster(String instituteid) {

        String qryString = "select st.groupcode,st.groupname,st.deactive,crlmt.maxcredit,crlmt.mincredit,crlmt.stynumber,"
                + " pr.id.programid,pr.programcode,br.id.branchid, br.branchcode,st.id.groupid,st.id.clientid "
                + "from  StudentGroupMaster st,StudentGroupCrLimit crlmt,ProgramMaster pr,BranchMaster br  where crlmt.programid = pr.id.programid  and crlmt.branchid = br.id.branchid and pr.id.instituteid = '" + instituteid + "' and st.id.groupid=crlmt.id.groupid  and  st.id.clientid=crlmt.id.clientid ";
        return getHibernateTemplate().find(qryString);

    }

    public List editGroupMaster(String groupid, String clientid, String programid, String branchid) {

        String qryString = "select st.groupcode,st.groupname,st.deactive,crlmt.maxcredit,crlmt.mincredit,crlmt.stynumber,"
                + " pr.id.programid,pr.programcode,br.id.branchid, br.branchcode,st.id.groupid,st.id.clientid "
                + "from  StudentGroupMaster st,StudentGroupCrLimit crlmt,ProgramMaster pr,BranchMaster br  where st.id.groupid = '" + groupid + "' and st.id.clientid = '" + clientid + "' and crlmt.programid = pr.id.programid  and crlmt.branchid = br.id.branchid   and st.id.groupid=crlmt.id.groupid and st.id.clientid=crlmt.id.clientid ";
        return getHibernateTemplate().find(qryString);

    }

    public int checkIfChildExist(final StudentGroupMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentGroupMaster studentGroupMaster = (StudentGroupMaster) session.get(StudentGroupMaster.class, id);

                int i1 = Integer.parseInt((session.createFilter(studentGroupMaster.getStudentgroupcrlimit(), "select count(*)").list().get(0)).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
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

//    public List editProgramMaxStyData(String instituteid,String programid,String acad_year,String branchid) {
//
//        String qryString = "Select pm.programcode, bm.branchcode,ins.institutecode,pgm.id.academicyear,pgm.startsty,pgm.endsty,pgm.startdate,pgm.enddate,pgm.exampattern,pgm.deactive,pgm.stypattern,pgm.id.instituteid,pgm.id.academicyear,pgm.id.programid,pgm.id.branchid "
//                + " from  ProgramMaxSty pgm,ProgramMaster pm,BranchMaster bm,InstituteMaster ins  where pgm.id.instituteid='" + instituteid + "' and pgm.id.programid = '" + programid + "' and pgm.id.academicyear='"+ acad_year +"'  and pgm.id.branchid='" + branchid +"' and "
//                + " pgm.id.programid = pm.id.programid and pgm.id.branchid = bm.id.branchid and pgm.id.instituteid = ins.id.instituteid  ";
//        return  getHibernateTemplate().find(qryString);
//
//    }
//    public List<String> doValidate(final ProgramMaxSty programMaxSty) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(ProgramMaxSty.class);
//                criteria.add(Restrictions.eq("id.instituteid", programMaxSty.getId().getInstituteid()));
//                criteria.add(Restrictions.eq("id.branchid", programMaxSty.getId().getBranchid()));
//                criteria.add(Restrictions.eq("id.programid", programMaxSty.getId().getProgramid()));
//                criteria.add(Restrictions.eq("id.academicyear", programMaxSty.getId().getAcademicyear()));
//                /*if (mode.equals("edit")) {
//                criteria.add(Expression.ne("id.gradeid", gradePayScale.getId().getGradeid()));//Do not check for itself when updating record
//                }*/
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue() > 0) {
//            errors.add("Duplicate Program Max STY");
//        }
//        return errors;
//    }
//
//    public String insertProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + programMaxStysList.size());
//            for (int i = 0; i < programMaxStysList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.save((ProgramMaxSty) programMaxStysList.get(i));
//            }
//            for (int i = 0; i < styDescsList.size(); i++) {
//                session.saveOrUpdate((StyDesc) styDescsList.get(i));
//            }
//            retList = "Data Save Successfully";
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            retList = "Error in tx update";
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return retList;
//    }
//
//    public String updateProgramMaxSTY(final List<ProgramMaxSty> programMaxStysList, final List<StyDesc> styDescsList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + programMaxStysList.size());
//            for (int i = 0; i < programMaxStysList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.update((ProgramMaxSty) programMaxStysList.get(i));
//            }
//            for (int i = 0; i < styDescsList.size(); i++) {
//                session.saveOrUpdate((StyDesc) styDescsList.get(i));
//            }
//            retList = "Data Updated Successfully !";
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) {
//                tx.rollback();
//           }
//            retList = "Error in tx update";
//            e.printStackTrace();
//        } finally {
//             session.close();
//         }
//         return retList;
//    }
}
