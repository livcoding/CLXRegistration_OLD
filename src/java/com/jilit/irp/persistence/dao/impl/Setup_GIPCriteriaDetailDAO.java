package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Setup_GIPCriteriaDetailIDAO;
import com.jilit.irp.persistence.dto.Setup_GIPCriteriaDetail;
import java.io.Serializable;
import java.util.Collection;
import org.hibernate.Query;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * malkeet.singh
 */
public class Setup_GIPCriteriaDetailDAO extends HibernateDAO implements Setup_GIPCriteriaDetailIDAO {

    private static final Log log = LogFactory.getLog(Setup_GIPCriteriaDetail.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Setup_GIPCriteriaDetail records via Hibernate from the database");
        return this.find("from Setup_GIPCriteriaDetail as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Setup_GIPCriteria records via Hibernate from the database");
        return this.find("from Setup_GIPCriteria as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Setup_GIPCriteriaDetail.class, id);
    }

    public void deleteGIPDetail(String instituteid, String programid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from Setup_GIPCriteriaDetail gipd where gipd.id.instituteid = :instituteid and gipd.id.programid =:programid ");
        Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("programid", programid);
        qry.executeUpdate();
    }
}
