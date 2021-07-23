/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.DepartmentBranchTaggingIDAO;
import com.jilit.irp.persistence.dto.DepartmentBranchTagging;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class DepartmentBranchTaggingDAO extends HibernateDAO implements DepartmentBranchTaggingIDAO {

    private static final Log log = LogFactory.getLog(DepartmentBranchTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all DepartmentBranchTagging records via Hibernate from the database");
        return this.find("from DepartmentBranchTagging as tname ");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(DepartmentBranchTagging.class, id);
    }

    public List getDepartmentBranchTaggingList(String instituteid) {
        List list = null;
        String strqry = " select distinct pm.programcode, pm.programcode, pm.programdesc, bm.branchcode,bm.branchdesc, "
                + "dm.departmentcode,dm.department, dbt.id.instituteid, dbt.id.departmentid, dbt.id.programid,"
                + "dbt.id.branchid from DepartmentBranchTagging dbt,ProgramMaster pm, "
                + "BranchMaster bm, DepartmentMaster dm where dbt.id.instituteid=pm.id.instituteid "
                + "and dbt.id.instituteid=bm.id.instituteid and pm.id.instituteid=bm.id.instituteid "
                + "and dbt.id.programid=pm.id.programid and dbt.id.branchid=bm.id.branchid and dbt.id.departmentid=dm.id.departmentid "
                + "and pm.id.programid=bm.id.programid and dbt.id.instituteid='" + instituteid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<String> doValidate(final DepartmentBranchTagging departmentBranchTagging, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(DepartmentBranchTagging.class);
                criteria.add(Restrictions.eq("id.instituteid", departmentBranchTagging.getId().getInstituteid()).ignoreCase());               
                criteria.add(Restrictions.eq("id.departmentid", departmentBranchTagging.getId().getDepartmentid()).ignoreCase());
                criteria.add(Restrictions.eq("id.programid", departmentBranchTagging.getId().getProgramid()).ignoreCase());
                criteria.add(Restrictions.eq("id.branchid", departmentBranchTagging.getId().getBranchid()).ignoreCase());
                if (mode.equals("edit")) {
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                System.err.println("In doValidate method");
                System.err.println("criteria.list() - " + criteria.list());
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Department-Branch Tagging Found! ");
        }
        return errors;
    }
        

    public List getPopUpData(String instituteid, String programid, String branchid) {
        List list = null;
        String pqry = "";
        if (programid != null && !programid.equals("")) {
            pqry = " and a.id.programid = '" + programid + "'";
        }
        String bqry = "";
        if (branchid != null && !branchid.equals("")) {
            bqry = " and a.id.branchid = '" + branchid + "'";
        }
        String qryString = " select a from DepartmentBranchTagging a"
                + " where a.id.instituteid='" + instituteid + "'"       
                + ""+ pqry +""
                + ""+ bqry +"";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
