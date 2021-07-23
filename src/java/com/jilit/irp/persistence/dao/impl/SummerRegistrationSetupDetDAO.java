package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SummerRegistrationSetupDetIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupDet;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupDetId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *  @author Malkeet Singh
 */
public class SummerRegistrationSetupDetDAO extends HibernateDAO implements SummerRegistrationSetupDetIDAO {

    private static final Log log = LogFactory.getLog(SummerRegistrationSetupDet.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SummerRegistrationSetupDet records via Hibernate from the database");
        return this.find("from SummerRegistrationSetupDet as tname order by seqid asc");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all SummerRegistrationSetupDet records via Hibernate from the database");
        return this.find("from SummerRegistrationSetupDet as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SummerRegistrationSetupDet.class, id);
    }

    public List getSummerRegistrationSetupDetEdit(String instituteid, String programid, byte stynumber) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select srsd.id.instituteid,srsd.id.programid,pm.programcode,pm.programdesc,srsd.id.stynumber,srsd.id.majororminor,srsd.lpcriteriatype, "
                + " srsd.maxcredit,srsd.maxlsubjectscredit,srsd.maxpsubjectscredit,srsd.maxltheorysubjects,srsd.maxplabsubjects "
                + " from SummerRegistrationSetupDet srsd , ProgramMaster pm "
                + " where srsd.id.instituteid=:instituteid "
                + " and srsd.id.programid=:programid "
                + " and srsd.id.stynumber=:stynumber "
                + " and pm.id.instituteid=srsd.id.instituteid "
                + " and pm.id.programid=srsd.id.programid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

}
