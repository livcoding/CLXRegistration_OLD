package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TempRollNumberSetupIDAO;
import com.jilit.irp.persistence.dto.TempRollNumberSetup;
import com.jilit.irp.persistence.dto.TempRollNumberSetupId;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.TempRollNumberControl;
import org.apache.commons.logging.Log;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetupDAO extends HibernateDAO implements TempRollNumberSetupIDAO {

    private static final Log log = LogFactory.getLog(TempRollNumberSetup.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TempRollNumberSetup records via Hibernate from the database");
        return this.find("from TempRollNumberSetup as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TempRollNumberSetup.class, id);
    }

    public Object findByPrimaryKey1(Serializable id) {
        return getHibernateTemplate().get(TempRollNumberControl.class, id);
    }

    public int checkIfChildExist(final TempRollNumberSetupId id) {
        HibernateCallback callback = new HibernateCallback() {
            int i = 0;

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                TempRollNumberSetup temmpRollSetup = (TempRollNumberSetup) session.get(TempRollNumberSetup.class, id);
                int i1 = Integer.parseInt(session.createFilter(temmpRollSetup.getTemprollnumbercontrol(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(temmpRollSetup.getTemprollnumbersetupdetail(), "select count(*)").list().get(0).toString());
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getAllTempRollNumberSetup(String instituteid, String academicyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ts.id.instituteid,ts.id.groupid,ts.codelength,ts.seqid from TempRollNumberSetup ts where exists (select 1 from TempRollNumberSetupDetail td "
                + " where td.id.instituteid = ts.id.instituteid and td.id.groupid = ts.id.groupid and td.id.instituteid = :instituteid and td.id.academicyear = :academicyear) ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllEditTempRollNumberSetup(String instituteid, String groupid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rd.id.academicyear,pm.programdesc,bm.branchdesc,rd.id.branchid,rd.id.programid,rd.id.programtypeid,rd.id.instituteid, "
                + " rd.id.groupid from TempRollNumberSetupDetail rd, ProgramMaster pm, BranchMaster bm where rd.id.instituteid=pm.id.instituteid and "
                + " rd.id.programid=pm.id.programid and rd.id.branchid=bm.id.branchid and pm.id.programid=bm.id.programid and rd.id.instituteid = :instituteid "
                + " and rd.id.groupid = :groupid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("groupid", groupid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
