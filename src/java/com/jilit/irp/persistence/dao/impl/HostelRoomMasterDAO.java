/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HostelRoomMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.HostelRoomMaster;
import com.jilit.irp.persistence.dto.HostelRoomMasterId;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Criteria;
import java.lang.Integer;
import org.hibernate.criterion.*;
import java.util.List;


/**
 *
 * @author chetna.pargai
 */
public class HostelRoomMasterDAO extends HibernateDAO implements HostelRoomMasterIDAO {

    private static final Log log = LogFactory.getLog(HostelRoomMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all HostelRoomMaster records via Hibernate from the database");
        return this.find("from HostelRoomMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(HostelRoomMaster.class, id);
    }

    public int checkIfChildExist(final HostelRoomMasterId id) {

       HibernateCallback callback = new HibernateCallback(){

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                HostelRoomMaster  hostelroommaster= (HostelRoomMaster) session.get(HostelRoomMaster.class, id);
                int i1 =((Integer) session.createFilter( hostelroommaster.getHostelroombedmasters(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
       };

        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
