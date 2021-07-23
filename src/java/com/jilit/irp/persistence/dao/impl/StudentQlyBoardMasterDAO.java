/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentQlyBoardMasterIDAO;
import com.jilit.irp.persistence.dto.StudentQlyBoardMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Pankaj.Kumar
 */
public class StudentQlyBoardMasterDAO extends HibernateDAO implements StudentQlyBoardMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentQlyBoardMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentQlyBoardMasterDAO records via Hibernate from the database");
        return this.find("from StudentQlyBoardMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentQlyBoardMaster.class, id);
    }

    public List getAllStudentQLYMasterData() {
        List retList = null;

        retList = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentQlyBoardMaster.class, "master").add(Restrictions.or(Restrictions.eq("master.deactive", "N"), Restrictions.isNull("master.deactive")));
                return criteria.list();

            }
        });
        return retList;
    }

//    public StudentQlyBoardMaster checkifQLYBoardmasterExist(final String boardcode) {
//        StudentQlyBoardMaster dto = null;
//        dto=(StudentQlyBoardMaster)getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(StudentQlyBoardMaster.class, "master")
//                                            /**.add(Restrictions.eq("master.boardcode", boardcode).ignoreCase())**/
//                                           .add(Restrictions.eq("master.boardcode", boardcode))
//                                           .add(Restrictions.or(Restrictions.eq("deactive", "N"), Restrictions.isNull("deactive")));
//
//                return criteria.list();
//            }
//        }).get(0);
//        return dto;
//    }
//    public List checkIfQLYBoardmasterExist_SM(final String boardcode)
//    {
//        List list=new ArrayList();
//        String strQry=" select boardid, boardcode, boardname, boarduniv " +
//                " from StudentQlyBoardMaster " +
//                " where boardcode='"+boardcode+"'";
//        try{
//            list=getHibernateTemplate().find(strQry);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
}
