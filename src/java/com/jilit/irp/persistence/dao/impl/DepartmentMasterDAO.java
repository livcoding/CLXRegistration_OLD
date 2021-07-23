package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.DepartmentMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 *
 * @author subrata.lohar
 */
public class DepartmentMasterDAO extends HibernateDAO implements DepartmentMasterIDAO {

    private static final Log log = LogFactory.getLog(DepartmentMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all DepartmentMaster records via Hibernate from the database");
        return this.find("from DepartmentMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all DepartmentMaster records via Hibernate from the database");
        return this.find("from DepartmentMaster as tname where tname.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(DepartmentMaster.class, id);
    }

    public List getDepartmentNameOfReqSubject(String instituteid, String subjectid) {
        StringBuilder qryString = new StringBuilder();
        qryString.append("select d.departmentid, d.departmentcode, d.department from DepartmentMaster d where exists(select 1 from DepartmentSubjectTagging ds where ds.id.departmentid = d.departmentid and ds.id.subjectid='" + subjectid + "' and ds.id.instituteid='" + instituteid + "')");
        List list = null;

        try {
            list = getHibernateTemplate().find(qryString.toString());
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
        }
        return list;
    }

    public List getAllDepartmentCode() {
        List list = null;
        String qryString = "select dm.departmentid, dm.departmentcode, dm.department, coalesce(dm.shortname,' ') "
                + " from DepartmentMaster dm"
                + " where coalesce(dm.deactive,'N') = 'N'"
                + " group by dm.departmentid, dm.departmentcode, dm.department, dm.shortname"
                + " order by dm.departmentcode";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllFSTDepartmentCode(String instituteid, String registrationid) {
        List list = null;
        String qryString = "select dm.departmentid, dm.departmentcode, dm.department, coalesce(dm.shortname,' ') "
                + " from DepartmentMaster dm"
                + " where coalesce(dm.deactive,'N') = 'N'"
                + " and exists ("
                + " select 1 from  "
                + " FacultySubjectTagging fst ,"
                + " DepartmentSubjectTagging dst"
                + " where"
                + "  fst.registrationid=:registrationid "
                + " and fst.id.instituteid=:instituteid "
                + " and dst.id.instituteid=fst.id.instituteid"
                + " and dst.id.subjectid=fst.subjectid"
                + " and dst.id.departmentid=dm.id.departmentid )"
                + " group by dm.departmentid, dm.departmentcode, dm.department, dm.shortname"
                + " order by dm.departmentcode";
        try {
            list = getHibernateSession().createQuery(qryString).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllDepartment() {
        List list = null;
        String qryString = " select dm.departmentid, dm.departmentcode, dm.department,dm.departmenttype from DepartmentMaster dm"
                + " where coalesce(dm.departmenttype,'T')='T' and coalesce(dm.deactive,'N')='N' order by dm.seqid ";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Description: Function has been used to get Department Code id Data.
     *
     * @return
     */
    public List getAllRegWiseDepartmentCode(final String regid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(ProgramSubjectTagging.class, "pt");
                criteria.createAlias("pt.departmentmaster", "d");
                criteria.add(Restrictions.eq("pt.id.registrationid", regid));
                criteria.add(Restrictions.or(Restrictions.isNull("d.deactive"), Restrictions.eq("d.deactive", "N")));
                criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("d.departmentid").as("departmentid")).add(Projections.groupProperty("d.departmentcode").as("departmentcode")).add(Projections.groupProperty("d.department").as("department")));
                criteria.addOrder(Order.asc("d.department"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getDepartmentCode() {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select dm.departmentid,dm.departmentcode,dm.department from DepartmentMaster dm where coalesce(dm.deactive,'N')='N' order by dm.department asc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
