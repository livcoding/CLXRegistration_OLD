/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OtherStaffMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.OtherStaffMaster;

import com.jilit.irp.persistence.dto.OtherStaffMasterDetail;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
/** 
 *
 * @author sunny.singhal
 */
public class OtherStaffMasterDAO extends HibernateDAO implements OtherStaffMasterIDAO{

    private static final Log log = LogFactory.getLog(OtherStaffMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OtherStaffMaster records via Hibernate from the database");
        return this.find("from OtherStaffMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OtherStaffMaster.class, id);
    }    


//    public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((OtherStaffMaster) record);
//    }
//
//     public List getOtherStaffName(final String otherStaffId) {
//        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                List l = null;
//                Criteria criteria = session.createCriteria(OtherStaffMaster.class, "master");
//                criteria.createAlias("master.otherstaffmasterdetails", "osmd").setFetchMode("otherstaffmasterdetails", FetchMode.JOIN);
//                criteria.add(Restrictions.eq("master.osid", otherStaffId));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("osmd.osname")).add(Projections.property("master.ostype")));
//                l = criteria.list();
//                return l;
//            }
//        });
//        return list;
//    }
//
//    @Override
//     public List getOther_StaffMaster(final String companyid, final String ostype) {
//         final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            @Override
//             public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                 Criteria criteria = session.createCriteria(OtherStaffMaster.class,"master");
//                 criteria.createAlias("master.otherstaffmasterdetails", "osmd");
//                 criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),Restrictions.eq("master.deactive", "N")));
//                 criteria.add(Restrictions.eq("master.companyid", companyid));
//                 criteria.add(Restrictions.eq("master.ostype", ostype));
//                 criteria.setProjection(Projections.projectionList().add(Projections.property("osmd.oscode").as("oscode"))
//                                                                    .add(Projections.property("osmd.osid").as("osid"))
//                                                                    .add(Projections.property("osmd.osname").as("osname")));
//                 criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                 return criteria.list();
//             }
//         });
//         return list;
//     }
//
//    @Override
//     public List checkOther_StaffMasterExist(final String companyid, final String ostype,final String oscode) {
//         final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            @Override
//             public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                 Criteria criteria = session.createCriteria(OtherStaffMaster.class,"master");
//                 criteria.createAlias("master.otherstaffmasterdetails", "osmd");
//                 criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),Restrictions.eq("master.deactive", "N")));
//                 criteria.add(Restrictions.eq("master.companyid", companyid));
//                 criteria.add(Restrictions.eq("osmd.oscode", oscode));
//                 criteria.add(Restrictions.eq("master.ostype", ostype));
//                 criteria.setProjection(Projections.projectionList().add(Projections.property("osmd.oscode").as("oscode"))
//                                                                    .add(Projections.property("osmd.osid").as("osid"))
//                                                                    .add(Projections.property("osmd.osname").as("osname")));
//                 criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                 return criteria.list();
//             }
//         });
//         return list;
//     }
//
//    @Override
//    public List<String> doValidate(final OtherStaffMasterDetail dto) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        try {
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//            @Override
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(OtherStaffMasterDetail.class);
//                criteria.add(Restrictions.eq("shortname", dto.getShortname()).ignoreCase());
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue() > 1) {
//            errors.add("Short Name Already Exist!!!");
//        }
//        } catch(Exception e) {
//            errors.add("Short Name Already Exist!!!");
//        }
//        return errors;
//    }
//
//    @Override
//    public List<String> doValidate_OSCode(final OtherStaffMasterDetail dto) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        try {
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            @Override
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(OtherStaffMaster.class, "master");
//                criteria.createAlias("master.otherstaffmasterdetails", "osmd");
//                criteria.add(Restrictions.eq("osmd.oscode", dto.getOscode()));
//                criteria.add(Restrictions.ne("master.osid", dto.getOsid()));
//                if(dto.getOtherstaffmaster()!=null && dto.getOtherstaffmaster().getCompanyid()!=null) {
//                    criteria.add(Restrictions.ne("master.companyid", dto.getOtherstaffmaster().getCompanyid()));
//                }
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue()> 0) {
//            errors.add("Name Already Exists!!!");
//        }
//        }catch(Exception e) {
//            errors.add("Name Already Exists!!!");
//        }
//        return errors;
//    }
}
