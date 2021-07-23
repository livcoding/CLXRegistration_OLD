/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentPhotoIDAO;
import com.jilit.irp.persistence.dto.BulkStudentPhoto;
import com.jilit.irp.persistence.dto.StudentPhoto;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author akshya.gaur
 */
public class StudentPhotoDAO extends HibernateDAO implements StudentPhotoIDAO {

    private static final Log log = LogFactory.getLog(StudentPhotoDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentPhoto records via Hibernate from the database");
        return this.find("from StudentPhoto as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentPhoto.class, id);
    }

    public Object findByPrimaryKeyForBulkPhoto(Serializable id) {
        return getHibernateTemplate().get(BulkStudentPhoto.class, id);
    }

    public String getStudentId(String enroll) {
        String stid = null;
        List list = new ArrayList();
        try {
            String query = "select sm.studentid  from StudentMaster sm where sm.enrollmentno= '" + enroll + "' and sm.activestatus='A' ";
            list = getHibernateTemplate().find(query);
            if (list != null && list.size() > 0) {
                stid = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stid;
    }

    public String getStudentIdRank(String rank) {
        String stid = null;
        List list = new ArrayList();
        try {
            String query = "select sm.studentid  from StudentMaster sm where sm.rank= '" + rank + "' and sm.activestatus='A' ";
            list = getHibernateTemplate().find(query);
            if (list != null && list.size() > 0) {
                stid = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stid;
    }

    public List getStudentPhotoData(String instituteid, String studentids) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Select stph.photo,stph.signature   from StudentPhoto stph where  stph.studentid =:studentids ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("studentids", studentids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }
    
    public List getStudentPhotoForRegSlip(String studentids) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Select stph  from StudentPhoto stph where  stph.studentid =:studentids ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("studentids", studentids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getStudentPhotoSign(String studentId) {
//        Blob b = null;
        List list = new ArrayList();
        try {
//            String query = " select sp.photo,sp.signature  from StudentPhoto sp where sp.studentid =:studentId ";
            String query = " select sp.photo  from StudentPhotoFetch sp where sp.studentid =:studentId ";
            list = getHibernateSession().createQuery(query).setParameter("studentId", studentId).list();
            if (list != null && list.size() > 0) {
//                for(int i=0;i<list.size();i++){
//                     b[i] = (Blob) list.get(i);
// b = (Blob)((StudentPhotoFetch) list.get(0)).getPhoto();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return b;
        return list;
    }

//
//     public ArrayList getStudentPhotoData(final String instituteid, final List criteriaList) {
//        final ArrayList retObj = (ArrayList) getHibernateTemplate().execute(new HibernateCallback() {
//
//            public ArrayList doInHibernate(Session session) throws HibernateException, SQLException {
//                String whereClause = "";
//
//                for (int i = 0; i < criteriaList.size(); i++) {
//                    String str = (String)criteriaList.get(i);
//                    String [] arr = str.split(":");
//                    if(i>0)
//                        whereClause=whereClause+ "or";
//                    whereClause = whereClause+" ( std.acadyear = '"+arr[0]+"'" +
//                        " and std.programid = '"+arr[1]+"'" +
//                        " and std.branchid = '"+arr[2]+"'" +
//                        "  )";
//                }
//                String qry = "select std.studentid, " +
//                        " std.enrollmentno," +
//                        " std.name, " +
//                        " std.acadyear," +
//                        " pm.programcode, " +
//                        " bm.branchcode" +
//                        " from StudentMaster std," +
//                        " BranchMaster bm," +
//                        " ProgramMaster pm "+
//                        " where std.programid = pm.id.programid " +
//                        " and std.branchid = bm.id.branchid " +
//                        " and std.instituteid='"+instituteid+"' " +
//                        " and std.instituteid= pm.id.instituteid " +
//                        " and std.instituteid= bm.id.instituteid " +
//                        " and ("+  whereClause +")" +
//                        " and coalesce(std.activestatus,'A')='A' " +
//                        " and coalesce(pm.deactive,'N')='N' " +
//                        " and coalesce(bm.deactive,'N')='N' "
//                        ;/*," +
//                        " StudentPhoto sp"+
//                         " and coalesce(sp.deactive,'N')='N'"*/
//                ArrayList l = new ArrayList();
//                l = (ArrayList) session.createQuery(qry).list();
//                System.err.println("dddddddd" + l.size());
//                return l;
//            }
//        });
//        return retObj;
//    }
//
//
//     public String insertAndUpdateStudentPhoto(final List<StudentPhoto> insertSPList) {
//        String retList = null;
//        Session session = null;
//        Transaction tx = null;
//        try {
//            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            System.err.println("*********** in transaction " + insertSPList.size());
//            for (int i = 0; i < insertSPList.size(); i++) {
//                System.err.println("************* value" + i);
//                session.saveOrUpdate((StudentPhoto) insertSPList.get(i));
//            }
//
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
//public ArrayList getStudentPhotoEnrollmentWiseData(final String instituteid, final String from,final String to) {
//        final ArrayList retObj = (ArrayList) getHibernateTemplate().execute(new HibernateCallback() {
//
//            public ArrayList doInHibernate(Session session) throws HibernateException, SQLException {
//
//
//                String qry = "select distinct std.studentid, " +
//                        " std.enrollmentno," +
//                        " std.name, " +
//                        " std.acadyear," +
//                        " branchmaster.branchcode," +
//                        " branchmaster.programmaster.programcode, "+
//                        " sectionmaster.sectioncode" +
//                        " from StudentMaster std" +
//                        " where to_number(std.enrollmentno)   " +
//                        " between "+from+" and "+to+"  "+
//                         " and coalesce(std.activestatus,'A')='A' " +
//                        " and std.instituteid='"+instituteid+"' "
//
//
//                        ;/*," +
//                        " StudentPhoto sp"+
//                         " and coalesce(sp.deactive,'N')='N'"*/
//                ArrayList l = new ArrayList();
//                l = (ArrayList) session.createQuery(qry).list();
//                System.err.println("dddddddd" + l.size());
//                return l;
//            }
//        });
//        return retObj;
//    }
//
//    public StudentPhoto getPhoto(String studentid){
//        StudentPhoto studentPhoto = null;
//        List list =new ArrayList();
//        try {
//            String query= " select sp " +
//                      " from StudentPhoto sp" +
//                      " where sp.studentid = '"+studentid+"' ";
//
//        list = getHibernateTemplate().find(query);
//        if(list != null && list.size() > 0 ){
//            studentPhoto = (StudentPhoto) list.get(0);
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return studentPhoto;
//    }    
    
    
     public List getGuestStudentPhotoSign(String guestStId) {
//        Blob b = null;
        List list = new ArrayList();
        try {
            String query = " select sp from GuestStudentPhotoFetch sp where sp.gueststudentid =:guestStId ";
            list = getHibernateSession().createQuery(query).setParameter("guestStId", guestStId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
