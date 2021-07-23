/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.QualificationBoardMasterIDAO;
import com.jilit.irp.persistence.dto.StudentQlyBoardMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author narendars.shekhawat
 */
public class QualificationBoardMasterDAO extends HibernateDAO implements QualificationBoardMasterIDAO {


 private static final Log log = LogFactory.getLog(QualificationBoardMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentQlyBoardMaster records via Hibernate from the database");
        return this.find("from StudentQlyBoardMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentQlyBoardMaster.class, id);
    }


  public int checkIfChildExist(final String qualificationid) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException
            {
                StudentQlyBoardMaster lm = (StudentQlyBoardMaster)session.get(StudentQlyBoardMaster.class, qualificationid);
                int i1 = Integer.parseInt(session.createFilter(lm.getStudentqualifications(), "select count(*)").list().get(0).toString());
                return i1;
            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final StudentQlyBoardMaster master, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        /*Unique Key Constraint on CID and Shortname*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentQlyBoardMaster.class);
                criteria.add(Restrictions.ne("boardid", master.getBoardid()));
                criteria.add(Restrictions.eq("boardcode", master.getBoardcode()).ignoreCase());
                criteria.add(Restrictions.eq("boardname", master.getBoardname()).ignoreCase());
                if (mode.equals("update")) {
                     criteria.add(Restrictions.ne("boardid", master.getBoardid()));
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if(count>0){
            errors.add("Qualification Board Code Already exist.");
        }
        return errors;
    }
//
//
//    //*****************************************************************//
//
//  public List getQualificationData(String data) {
//        List list = null;
//        String qryString = "select  ct.boardid, ct.boardcode, ct.boardname from StudentQlyBoardMaster ct where ct.boardcode = '"+data+"'";
//        try {
//            list = getHibernateTemplate().find(qryString);
//            System.out.println("Size is ::getQualificationData():: " + list.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
///////////////////////////////////////////////////Use in online Application////////////////////////////////////////////////////////////////////
//public List<String> doValidateBoardUniversity(final StudentQlyBoardMaster master) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        /*Unique Key Constraint on CID and Shortname*/
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(StudentQlyBoardMaster.class);
//                criteria.add(Restrictions.eq("boardname", master.getBoardname()).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if(count>0){
//            errors.add("Qualification Board Code Already exist.");
//        }
//        return errors;
//    }
//
//
//    

}
