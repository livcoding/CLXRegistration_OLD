package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.DepartmentSubjectTaggingIDAO;
import com.jilit.irp.persistence.dto.DepartmentSubjectTagging;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v.kumar
 */
public class DepartmentSubjectTaggingDAO extends HibernateDAO implements DepartmentSubjectTaggingIDAO {

    private static final Log log = LogFactory.getLog(DepartmentSubjectTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all DepartmentSubjectTagging records via Hibernate from the database");
        return this.find("from DepartmentSubjectTagging as tname order by academicyear asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(DepartmentSubjectTagging.class, id);
    }

    @Override
    public List getDepartmentSubjectTaggingData(final String instituteid) {
        List list = null;
        String qrystring = " select dst.id.instituteid as instituteid, sm.subjectcode as subjectcode, dst.id.subjectid as subjectid, sm.subjectdesc as subjectdesc, dst.id.departmentid as departmentid, dm.departmentcode as departmentcode, dm.department as department "
                + " from SubjectMaster sm, DepartmentMaster dm, DepartmentSubjectTagging dst where dst.id.instituteid = '" + instituteid + "' "
                + " and sm.id.subjectid = dst.id.subjectid and sm.id.instituteid = dst.id.instituteid "
                + " and dm.departmentid = dst.id.departmentid  ";
        try {
            list = getHibernateTemplate().find(qrystring);
        } catch (Exception e) {
        }
        return list;
    }

    public List<String> doValidate(final DepartmentSubjectTagging departmentSubjectTagging, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(DepartmentSubjectTagging.class);
                System.err.println("INSTITUTECODE - " + departmentSubjectTagging.getId().getInstituteid());
                criteria.add(Restrictions.eq("id.instituteid", departmentSubjectTagging.getId().getInstituteid()).ignoreCase());
                criteria.add(Restrictions.eq("id.subjectid", departmentSubjectTagging.getId().getSubjectid()).ignoreCase());
                criteria.add(Restrictions.eq("id.departmentid", departmentSubjectTagging.getId().getDepartmentid()).ignoreCase());
                if (mode.equals("edit")) {
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                System.err.println("In doValidate method");
                System.err.println("criteria.list() - " + criteria.list());
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Department-Subject Tagging Found! ");
        }
        return errors;
    }

    public List getSubjectCodeUsingDepartmentBased(String instituteid, String departmentid, String subjectid, String registrationid,String currentsubjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct ds.id.subjectid, sm.subjectcode, sm.subjectdesc ,sm.subjectflag from DepartmentSubjectTagging ds, SubjectMaster sm "
                + " where ds.id.instituteid = sm.id.instituteid and ds.id.subjectid = sm.id.subjectid and ds.id.instituteid = :instituteid and "
                + " sm.id.subjectid<>:subjectid  and coalesce(sm.deactive,'N') = 'N' ");
        if (!(departmentid.equalsIgnoreCase("ALL"))) {
            sb.append(" and ds.id.departmentid = :departmentid ");
        }
        sb.append(" and sm.id.subjectid not in( select ost.currentsubjectid from OfferedODSubjectTagging ost where ost.id.instituteid=:instituteid and ost.id.registrationid=:registrationid and ost.currentsubjectid<>:currentsubjectid ) order by sm.subjectcode ");
        try {
            if (!(departmentid.equalsIgnoreCase("ALL"))) {
                list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("departmentid", departmentid).setParameter("subjectid", subjectid).setParameter("registrationid", registrationid).setParameter("currentsubjectid", currentsubjectid).list();
            } else {
                list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("subjectid", subjectid).setParameter("registrationid", registrationid).setParameter("currentsubjectid", currentsubjectid).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getDepartmentWiseSubjectTaggingData(String instituteid, String deptId) {
        List list = null;
        String qrystring = " select dst.id.instituteid as instituteid, sm.subjectcode as subjectcode, dst.id.subjectid as subjectid, sm.subjectdesc as subjectdesc, dst.id.departmentid as departmentid, dm.departmentcode as departmentcode, dm.department as department "
                + " from SubjectMaster sm, DepartmentMaster dm, DepartmentSubjectTagging dst where dst.id.instituteid = '" + instituteid + "' and dst.id.departmentid='" + deptId + "' "
                + " and sm.id.subjectid = dst.id.subjectid and sm.id.instituteid = dst.id.instituteid "
                + " and dm.departmentid = dst.id.departmentid   ";
        try {
            list = getHibernateTemplate().find(qrystring);
        } catch (Exception e) {
        }
        return list;
    }
}
