/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RollNumberingSetupIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.RollNumberingSetup;
import com.jilit.irp.persistence.dto.RollNumberingSetupId;
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
 * @author suman.saurabh
 */
public class RollNumberingSetupDAO extends HibernateDAO implements RollNumberingSetupIDAO {

    private static final Log log = LogFactory.getLog(RollNumberingSetupDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RollNumberingSetup records via Hibernate from the database");
        return this.find("from RollNumberingSetup as tname");
    }

    public Collection<?> findAllInstituteWise(String instituteid) {
        log.info("Retrieving all RollNumberingSetup records via Hibernate from the database");
        return this.find("from RollNumberingSetup as tname where tname.id.instituteid='" + instituteid + "'");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RollNumberingSetup.class, id);
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((RollNumberingSetup) record);
    }

    public int checkIfChildExist(final RollNumberingSetupId id) {
        HibernateCallback callback = new HibernateCallback() {
            int i = 0;

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                RollNumberingSetup rollSetup = (RollNumberingSetup) session.get(RollNumberingSetup.class, id);
                int i1 = Integer.parseInt(session.createFilter(rollSetup.getRollnumberingcontrols(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(rollSetup.getRollnumberingsetupdetails(), "select count(*)").list().get(0).toString());
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    @Override
    public List getRollNumberDetail(String instituteid, String groupid) {
        List list = null;
        String strqry = "select rd.id.academicyear,pm.programdesc,bm.branchdesc,rd.id.branchid,rd.id.programid,rd.id.programtypeid,rd.id.instituteid,rd.id.groupid "
                + "from RollNumberingSetupDetail rd, ProgramMaster pm, BranchMaster bm where "
                + "rd.id.instituteid=pm.id.instituteid and rd.id.programid=pm.id.programid and rd.id.branchid=bm.id.branchid "
                + "and pm.id.programid=bm.id.programid and rd.id.instituteid='" + instituteid + "' "
                + "and rd.id.groupid='" + groupid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
