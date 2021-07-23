/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amit1.kumar
 */


package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.StudentHostelListIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentHostelDetail;
import java.beans.Expression;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;



public class StudentHostelListDAO extends HibernateDAO implements StudentHostelListIDAO {

 
     private static final Log log = LogFactory.getLog(StudentHostelListDAO.class);
//     public Collection<?> getStudentHostelList(final String sortedby ,final String orderby,final String registrationid){
//
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//           public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//
//                            Criteria criteria = session.createCriteria(StudentHostelDetail.class,"shd");
//                                     criteria.createAlias("shd.hostelmaster", "hm");
//                                     criteria.setFetchMode("hostelmaster", FetchMode.JOIN);
//                                     criteria.createAlias("shd.studentmaster", "sm");
//                                     criteria.setFetchMode("studentmaster", FetchMode.JOIN);
//
//                         //          criteria.createAlias("shd.branchmaster", "bm");
//                         //          criteria.setFetchMode("branchmaster", FetchMode.JOIN);
//
//
//                        criteria.setProjection(Projections.projectionList()
//                        .add(Projections.property("sm.enrollmentno").as("enrollmentno"))
//                        .add(Projections.property("sm.name").as("name"))
//                        .add(Projections.property("sm.acadyear").as("acadyear"))
//                        .add(Projections.property("sm.stynumber").as("stynumber"))
//                        .add(Projections.property("shd.dateofallotement").as("dateofallotement"))
//                        .add(Projections.property("shd.allotedtilldate").as("allotedtilldate"))
//                        .add(Projections.property("hm.hostelcode").as("hostelcode"))
//                        .add(Projections.property("shd.programcode").as("programcode"))
//                        .add(Projections.property("hm.branchcode").as("branchcode"))
//                        .add(Projections.property("shd.allotedroomno").as("allotedroomno"))
//                        .add(Projections.property("shd.allotedbedno").as("allotedbedno")));
//
//                        if(orderby.equals("E")&& sortedby.equals("A")){
//                           criteria.addOrder(Order.asc("sm.enrollmentno"));
//                        }
//                        else if(orderby.equals("E") && sortedby.equals("D")){
//                            criteria.addOrder(Order.desc("sm.enrollmentno"));
//                        }
//                        else if(orderby.equals("S") && sortedby.equals("A")){
//                            criteria.addOrder(Order.asc("sm.name"));
//                        }
//                        else if(orderby.equals("S") && sortedby.equals("D"))
//                        {
//                             criteria.addOrder(Order.desc("sm.name"));
//                        }
//                            else  if(orderby.equals("H") && sortedby.equals("A"))
//                        {
//                             criteria.addOrder(Order.asc("hm.hostelcode"));
//                        }
//                                 else if(orderby.equals("H") && sortedby.equals("D"))
//                        {
//                             criteria.addOrder(Order.desc("hm.hostelcode"));
//                        }
//
//
//                      return criteria.list();
//           }
//
//        });
//
//        return list;
//     }
//
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

