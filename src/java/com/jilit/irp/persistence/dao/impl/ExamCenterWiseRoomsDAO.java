/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ExamCenterWiseRoomsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ExamCenterWiseRooms;
import com.jilit.irp.persistence.dto.ExamCenterWiseRoomsId;
import com.jilit.irp.persistence.dto.RoomMaster;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Projections;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

;

/**
 *
 * @author ashok.singh
 */
public class ExamCenterWiseRoomsDAO extends HibernateDAO implements ExamCenterWiseRoomsIDAO {

    private static final Log log = LogFactory.getLog(ExamCenterWiseRoomsDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all ExamCenterWiseRooms records via Hibernate from the database");
        return this.find("from ExamCenterWiseRooms as tname order by seqid asc");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ExamCenterWiseRooms.class, id);
    }
}

