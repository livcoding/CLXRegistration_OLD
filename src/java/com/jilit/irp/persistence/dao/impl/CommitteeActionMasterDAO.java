/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CommitteeActionMasterIDAO;
import com.jilit.irp.persistence.dto.CommitteeMaster;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CommitteeActionType;
import com.jilit.irp.persistence.dto.CommitteeActionTypeId;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author campus.trainee
 */
public class CommitteeActionMasterDAO extends HibernateDAO implements CommitteeActionMasterIDAO {

    private static final Log log = LogFactory.getLog(CommitteeMaster.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CommitteeActionType  records via Hibernate from the database");
        return this.find("from CommitteeActionType as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeActionType.class, id);
    }

    public int checkIfChildExist(final String CommitteeActionTypeId) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                CommitteeActionType committeeActionType = (CommitteeActionType) session.get(CommitteeActionType.class, CommitteeActionTypeId);

                return 0;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();

    }

    public int checkIfChildExist(CommitteeActionTypeId uid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
