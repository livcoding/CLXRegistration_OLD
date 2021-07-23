/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RoomUsedForMasterIDAO;
import com.jilit.irp.persistence.dto.RoomUsedForMaster;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.RoomUsedForMasterId;
import java.io.Serializable;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 *
 * @author shimona.khandelwal
 */

public class RoomUsedForMasterDAO extends HibernateDAO implements RoomUsedForMasterIDAO {
    private static final Log log = LogFactory.getLog(RoomUsedForMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RoomMasterUsedForMaster records via Hibernate from the database");
        return this.find("from RoomUsedForMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RoomUsedForMaster.class, id);
    }

   
    public int checkIfChildExist(final String instituteid, final String roomusedforid) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {        
                RoomUsedForMaster roomUsedForMaster = (RoomUsedForMaster)session.get(RoomUsedForMaster.class, new RoomUsedForMasterId(instituteid, roomusedforid));
                int i1 = ((Integer) session.createFilter(roomUsedForMaster.getRoomusedfortaggings(), "select count(*)").list().get(0)).intValue();
                return i1 ;
               }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
    public List<String> doValidate(final RoomUsedForMaster roomUsedForMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(RoomUsedForMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", roomUsedForMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("roomusedforcode", roomUsedForMaster.getRoomusedforcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.roomusedforid", roomUsedForMaster.getId().getRoomusedforid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Semester Code ! ");
        }
        return errors;
    }

}