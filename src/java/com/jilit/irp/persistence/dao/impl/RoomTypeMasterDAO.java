/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RoomTypeMasterIDAO;
import com.jilit.irp.persistence.dto.RoomTypeMaster;
import java.util.Collection;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 *
 * @author suman.saurabh
 */
public class RoomTypeMasterDAO extends HibernateDAO implements RoomTypeMasterIDAO {
    private static final Log log = LogFactory.getLog(RoomTypeMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RoomTypeMaster records via Hibernate from the database");
        return this.find("from RoomTypeMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RoomTypeMaster.class, id);
    }


    public List<String> doValidate(final RoomTypeMaster roomTypeMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(RoomTypeMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", roomTypeMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("roomtypecode", roomTypeMaster.getRoomtypecode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.roomtypeid", roomTypeMaster.getId().getRoomtypeid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Room Type Code ! ");
        }
        return errors;
    }

    public List getAllRoomType(final String instituteid) {
       final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(RoomTypeMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.roomtypecode").as("roomtypecode")).add(Projections.property("master.roomtypedesc").as("roomtypedesc")).add(Projections.property("master.id.roomtypeid").as("roomtypeid")));
                criteria.addOrder(Order.asc("master.roomtypecode"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List checkIfRoomTypeCode_PSTExists(final String instituteid, final String rtcode)
      {
         String qry = "select rtm.id.roomtypeid, rtm.roomtypecode, rtm.roomtypedesc" +
                 " from RoomTypeMaster rtm" +
                 " where rtm.id.instituteid = '"+instituteid+"'" +
                 " and coalesce(rtm.deactive,'N')='N'" +
                 " and rtm.roomtypecode = '"+rtcode+"'";
          return getHibernateTemplate().find(qry);
      }
}
