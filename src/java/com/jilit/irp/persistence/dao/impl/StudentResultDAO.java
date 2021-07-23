/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dto.StudentResult;
import java.util.Collection;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentResultIDAO;
import com.jilit.irp.persistence.dto.SubjectMaster;
import java.sql.SQLException;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ibrahimb.shaik
 */
public class StudentResultDAO extends HibernateDAO implements StudentResultIDAO {

    private static final Log log = LogFactory.getLog(StudentResultDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentResult records via Hibernate from the database");
        return this.find("from StudentResult as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentResult.class, id);
    }

//    public List<String> doValidate(StudentResult studentResultDetail, String mode) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    public List getAllProgram(String instituteid) {
        String query = "select pm.id.programid, pm.programcode from ProgramMaster pm where pm.id.instituteid='" + instituteid + "' order by pm.seqid";
        return getHibernateTemplate().find(query);
    }

    public List getAllBranch(String instituteid) {
        String query = "select bm.id.branchid, bm.branchcode from BranchMaster bm where bm.id.instituteid='" + instituteid + "' order by bm.seqid";
        return getHibernateTemplate().find(query);
    }

    public List getAllStyNumber(String instituteid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select max(sm.stynumber) from StudentMaster sm where sm.instituteid= :instituteid");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }

        return list;
    }

    public List getBranchForProgram(String instituteid, String programid) {
        String query = "";
        if (programid.equals("ALL")) {
            query = "select bm.id.branchid, bm.branchcode from BranchMaster bm where bm.id.instituteid='" + instituteid + "' order by bm.seqid";
        } else {
            query = "select bm.id.branchid, bm.branchcode from BranchMaster bm where bm.id.instituteid='" + instituteid + "' and bm.id.programid='" + programid + "' order by bm.seqid";
        }
        return getHibernateTemplate().find(query);
    }

    public List<SubjectMaster> getAllSubjectCode_BPR(String instituteid) {
        String query = "select sm from SubjectMaster sm where sm.id.instituteid='" + instituteid + "'";
        return getHibernateTemplate().find(query);
    }
}
