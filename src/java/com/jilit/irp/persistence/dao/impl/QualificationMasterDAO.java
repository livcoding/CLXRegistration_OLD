/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.QualificationMasterIDAO;
import com.jilit.irp.persistence.dto.QualificationMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class QualificationMasterDAO extends HibernateDAO implements QualificationMasterIDAO {

    private static final Log log = LogFactory.getLog(QualificationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all QualificationMaster records via Hibernate from the database");
        return this.find("from QualificationMaster as tname");
    }



    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(QualificationMaster.class, id);
    }
    public int checkIfChildExist(final String qualificationid) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                QualificationMaster qualificationMaster = (QualificationMaster) session.get(QualificationMaster.class, qualificationid);
                int i1 = Integer.parseInt(session.createFilter(qualificationMaster.getStudentqualifications(), "select count(*)").list().get(0).toString());
                   return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final QualificationMaster qualificationMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);

        /*Unique Key Constraint Qualification Category Code*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(QualificationMaster.class);
                criteria.add(Restrictions.eq("qualificationcode", qualificationMaster.getQualificationcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("qualificationid", qualificationMaster.getQualificationid()));   //Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Qualification Code Already exist");
        }
        return errors;
    }
//
//      public Collection<?> getQualificationMasterReportData(final String orderBy,final String includeDeactivce,final String ascDesc) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//             public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                List list1 = new ArrayList();
//                Criteria criteria = session.createCriteria(QualificationMaster.class, "master");
//                if(includeDeactivce.equalsIgnoreCase("N"))
//                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", new String("N"))));
//              criteria.setProjection(
//                            Projections.projectionList()
//                            .add(Projections.property("master.qualificationcode").as("qualificationcode"))
//                            .add(Projections.property("master.qualification").as("qualification"))
//                            .add(Projections.property("master.qualificationfor").as("qualificationfor"))
//                            .add(Projections.property("master.qualificationlevel").as("qualificationlevel"))
//                                                    );
//                if(ascDesc.equalsIgnoreCase("A"))
//                criteria.addOrder(Order.asc("master."+orderBy).ignoreCase());
//                else
//                criteria.addOrder(Order.desc("master."+orderBy).ignoreCase());
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//    public List getQualifcationMasterDataForEmployee(){
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(QualificationMaster.class, "master");
//                criteria.add(Restrictions.or(Restrictions.ne("master.qualificationfor", "S"), Restrictions.isNull("master.qualificationfor")));
//                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", "N")));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.qualificationcode").as("qualificationcode"))
//                                                                   .add(Projections.property("master.qualification").as("qualification"))
//                                                                   .add(Projections.property("master.qualificationid").as("qualificationid")));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//    public List checkQualifcationCodeExist(final String qualificationCode){
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(QualificationMaster.class, "master");
//                criteria.add(Restrictions.eq("master.qualificationcode", qualificationCode).ignoreCase());
//                criteria.add(Restrictions.or(Restrictions.ne("master.qualificationfor", "S"), Restrictions.isNull("master.qualificationfor")));
//                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"), Restrictions.eq("master.deactive", "N")));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.qualificationcode").as("qualificationcode"))
//                                                                   .add(Projections.property("master.qualification").as("qualification"))
//                                                                   .add(Projections.property("master.qualificationid").as("qualificationid")));
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//    }
//
//      public List getQualificationBoardCode(){
//        List list = null;
//        String qryString = "select cm.boardid, cm.boardcode, cm.boardname, cm.deactive from StudentQlyBoardMaster cm ";
//        try {
//            list = getHibernateTemplate().find(qryString);
//            System.out.println("Size is of LIst::getQualificationBoardCode():: " + list.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//       public List ValidateQualificationBoardCode(String code){
//        List list = null;
//        String qryString = "select cm.boardid, cm.boardcode, cm.boardname, cm.deactive from StudentQlyBoardMaster cm where cm.boardcode = '"+code+"'";
//        try {
//                list = getHibernateTemplate().find(qryString);
//                System.out.println("Size is of LIst::getQualificationBoardCode():: " + list.size());
//            } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//         public List checkIfQualification_Exist(String qcode) {
//        List list = null;
//        final String qry1 = "select sm.qualificationid, sm.qualificationcode, sm.qualification " +
//                "from QualificationMaster sm " +
//                "where sm.qualificationcode='" + qcode + "' ";
//
//        try {
//            list = getHibernateTemplate().find(qry1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//      public List getQualificationCode() {
//             final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//                 public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                     Criteria criteria = session.createCriteria(QualificationMaster.class,"master");
//                     criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),Restrictions.eq("master.deactive", "N")));
//                     criteria.setProjection(Projections.projectionList().add(Projections.property("master.qualificationcode").as("qualificationcode"))
//                                                                        .add(Projections.property("master.qualificationid").as("qualificationid"))
//                                                                        .add(Projections.property("master.qualification").as("qualificationdesc")));
//                     criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                     return criteria.list();
//                 }
//             });
//             return list;
//         }
//
//         public List checkIfQualificationCodeExist(final String qualificationcode) {
//             final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//                 public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                     Criteria criteria = session.createCriteria(QualificationMaster.class,"master");
//                     criteria.add(Restrictions.eq("master.qualificationcode",qualificationcode));
//                     criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),Restrictions.eq("master.deactive", "N")));
//                     criteria.setProjection(Projections.projectionList().add(Projections.property("master.qualificationcode").as("qualificationcode"))
//                                                                        .add(Projections.property("master.qualificationid").as("qualificationid"))
//                                                                        .add(Projections.property("master.qualification").as("qualificationdesc")));
//                     criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                     return criteria.list();
//                 }
//             });
//             return list;
//         }
//
//         public List getQualificationDropDown() {
//             final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//                 public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                     Criteria criteria = session.createCriteria(QualificationMaster.class,"master");
//                     criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),Restrictions.eq("master.deactive", "N")));
//                     criteria.setProjection(Projections.projectionList().add(Projections.property("master.qualificationcode").as("label"))
//                                                                        .add(Projections.property("master.qualificationid").as("data"))
//                                                                        .add(Projections.property("master.qualification").as("desc")));
//                      criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                    return criteria.list();
//                 }
//             });
//             return list;
//         }

}
