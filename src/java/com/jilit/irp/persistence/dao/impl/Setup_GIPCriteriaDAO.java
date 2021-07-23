package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Setup_GIPCriteriaIDAO;
import com.jilit.irp.persistence.dto.Setup_GIPCriteria;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author campus.trainee
 */
public class Setup_GIPCriteriaDAO extends HibernateDAO implements Setup_GIPCriteriaIDAO {

    private static final Log log = LogFactory.getLog(Setup_GIPCriteria.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Setup_GIPCriteria records via Hibernate from the database");
        return this.find("from Setup_GIPCriteria as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Setup_GIPCriteria records via Hibernate from the database");
        return this.find("from Setup_GIPCriteria as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Setup_GIPCriteria.class, id);
    }

    public List getAllSetup_GIPCriteria(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pm.id.programid,pm.programcode,pm.programdesc,s.cgparangefrom,s.cgparangeupto,s.appicablegrades,s.id.instituteid "
                + " from Setup_GIPCriteria s, ProgramMaster pm where s.id.programid = pm.id.programid and s.id.instituteid = :instituteid order by s.entrydatetime desc");
        list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        return list;
    }

    public List getEditSetip_Criteria(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pm.id.programid,pm.programcode,pm.programdesc,s.cgparangefrom,s.cgparangeupto,s.appicablegrades,s.id.instituteid,s.failgradeflag from Setup_GIPCriteria s, "
                + " ProgramMaster pm where s.id.programid = pm.id.programid and s.id.instituteid = :instituteid and s.id.programid = :programid ");
        list = getHibernateSession().createQuery(sb.toString()).
                setParameter("instituteid", instituteid).
                setParameter("programid", programid).list();
        return list;
    }

    public List getApplicableGrade(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select gipd.id.applicablegradeid from Setup_GIPCriteriaDetail gipd where gipd.id.instituteid = :instituteid and gipd.id.programid =:programid ");
        list = getHibernateSession().createQuery(sb.toString()).
                setParameter("instituteid", instituteid).
                setParameter("programid", programid).list();
        return list;
    }
}
