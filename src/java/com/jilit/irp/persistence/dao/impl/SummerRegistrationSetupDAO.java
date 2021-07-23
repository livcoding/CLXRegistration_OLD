package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SummerRegistrationSetupIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.SummerRegistrationSetup;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupId;
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
 * @author Malkeet Singh 
 */
public class SummerRegistrationSetupDAO extends HibernateDAO implements SummerRegistrationSetupIDAO {

    private static final Log log = LogFactory.getLog(SummerRegistrationSetup.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SummerRegistrationSetup records via Hibernate from the database");
        return this.find("from SummerRegistrationSetup as tname order by seqid asc");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all SummerRegistrationSetup records via Hibernate from the database");
        return this.find("from SummerRegistrationSetup as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SummerRegistrationSetup.class, id);
    }

    public List getGridData(String instituteid, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select srs.id.instituteid,srs.id.programid,pm.programcode,pm.programdesc,srs.id.stynumber, "
                + " srs.maxcredit,srs.maxsubjects,srs.maxprojectsubjects, "
                + " srs.induscasemaxsubj  "
                + " from SummerRegistrationSetup srs,ProgramMaster pm "
                + " where srs.id.instituteid = :instituteid  "
                + " and srs.id.programid = :programid "
                + " and pm.id.instituteid = srs.id.instituteid "
                + " and pm.id.programid = srs.id.programid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getStyNumber(String instituteid, List programlist) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct "
                + " min(pmsty.startsty ), "
                + " max(pmsty.endsty )  "
                + " from "
                + " ProgramMaxSty pmsty "
                + " where pmsty.id.programid in(:programid) "
                + " and pmsty.id.instituteid=:instituteid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameterList("programid", programlist).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getSummerRegistrationSetupEdit(String instituteid, String programid, byte stynumber) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select srs.id.instituteid,srs.id.programid,pm.programcode,pm.programdesc,srs.id.stynumber, "
                + " srs.maxcredit,srs.maxsubjects,srs.maxprojectsubjects,srs.induscasemaxsubj "
                + " from SummerRegistrationSetup srs , ProgramMaster pm "
                + " where srs.id.instituteid=:instituteid "
                + " and srs.id.programid=:programid "
                + " and srs.id.stynumber=:stynumber "
                + " and pm.id.instituteid=srs.id.instituteid "
                + " and pm.id.programid=srs.id.programid");
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
