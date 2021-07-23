package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PreRequisitForRegistrationIDAO;
import com.jilit.irp.persistence.dto.PreRequisitForRegistration;
import com.jilit.irp.persistence.dto.PreRequisitForRegistrationId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ankur.goyal
 */
public class PreRequisitForRegistrationDAO extends HibernateDAO implements PreRequisitForRegistrationIDAO {

    private static final Log log = LogFactory.getLog(PreRequisitForRegistrationDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitForRegistration records via Hibernate from the database");
        return this.find("from PreRequisitForRegistration as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitForRegistration.class, id);
    }

    public int checkIfChildExist(final PreRequisitForRegistrationId id) {
        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                PreRequisitForRegistration master = (PreRequisitForRegistration) session.get(PreRequisitForRegistration.class, id);
                int i1 = Integer.parseInt(session.createFilter(master.getPrerequisitforregsubjdetail(), "select count(*)").list().get(0).toString());
                return (i1);
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final PreRequisitForRegistration preRequisitForRegistration, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(PreRequisitForRegistration.class);
                criteria.add(Restrictions.eq("id.instituteid", preRequisitForRegistration.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.academicyear", preRequisitForRegistration.getId().getAcademicyear()));
                criteria.add(Restrictions.eq("id.programid", preRequisitForRegistration.getId().getProgramid()));
                criteria.add(Restrictions.eq("id.branchid", preRequisitForRegistration.getId().getBranchid()));
                criteria.add(Restrictions.eq("id.stynumber", preRequisitForRegistration.getId().getStynumber()));
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.instituteid", preRequisitForRegistration.getId().getInstituteid()));//Do not check for itself when updating record
                    criteria.add(Restrictions.ne("id.academicyear", preRequisitForRegistration.getId().getAcademicyear()));//Do not check for itself when updating record
                    criteria.add(Restrictions.ne("id.programid", preRequisitForRegistration.getId().getProgramid()));
                    criteria.add(Restrictions.ne("id.branchid", preRequisitForRegistration.getId().getBranchid()));
                    criteria.add(Restrictions.ne("id.stynumber", preRequisitForRegistration.getId().getStynumber()));
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Entry For This Program, Branch, Semester/Trimester/Year! ");
        }
        return errors;
    }

    public List getAllListPreRequisitForRegistration(String instituteid, String academicYear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pr.id.academicyear,pm.programcode,bm.branchcode,pr.id.stynumber,pr.maxfailsubject,pr.minearnedpoints,pr.maxnoattempts,");
        sb.append(" pr.mincredits,pr.lateralentrymincredit,pr.mincgpa,pr.minsgpa,pr.minattendancepercent,pr.maxdeciplinary,pr.id.instituteid,pr.id.programid,pr.id.branchid from PreRequisitForRegistration pr,");
        sb.append(" Academicyear ac, BranchMaster bm, ProgramMaster pm where bm.id.branchid = pr.id.branchid and pr.id.programid = pm.id.programid ");
        sb.append(" and ac.id.academicyear = pr.id.academicyear and pr.id.instituteid = :instituteid and pr.id.academicyear = :academicYear order by pr.id.stynumber asc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicYear", academicYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getEditPreRequisitForRegistration(String instituteid, String academicyear, String programid, String branchid, String stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pr.id.instituteid, pr.id.academicyear,pm.programcode, bm.branchcode, pr.id.stynumber, pr.maxfailsubject, pr.minearnedpoints, "
                + " pr.maxnoattempts, pr.mincredits, pr.lateralentrymincredit,pr.mincgpa, pr.minsgpa, pr.minattendancepercent, pr.maxdeciplinary, pr.id.programid, pr.id.branchid, pr.deactive "
                + " from PreRequisitForRegistration pr, Academicyear ac, ProgramMaster pm, BranchMaster bm, StyDesc st where pr.id.academicyear = ac.id.academicyear "
                + " and pr.id.programid = pm.id.programid and pr.id.branchid = bm.id.branchid and pr.id.stynumber = st.id.stynumber and pr.id.instituteid = ac.id.instituteid "
                + " and pr.id.instituteid = :instituteid and pr.id.academicyear = :academicyear and pr.id.programid = :programid "
                + " and pr.id.branchid= :branchid and pr.id.stynumber =:stynumber ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).
                    setParameter("stynumber", Byte.parseByte(stynumber)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getPreRequisiteForRegistrationReportData(String instituteid, String academicyear, List programid, List branchid, String stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pm.programcode, bm.branchcode, pr.id.stynumber, pr.maxfailsubject, pr.minsgpa, pr.mincgpa, pr.minearnedpoints, "
                + " pr.minattendancepercent, pr.maxdeciplinary, pr.mincredits, pr.lateralentrymincredit, pr.maxnoattempts, pr.deactive from PreRequisitForRegistration pr, "
                + " ProgramMaster pm, BranchMaster bm, Academicyear ac where pr.id.academicyear = ac.id.academicyear and pr.id.programid = pm.id.programid and pr.id.branchid = bm.id.branchid "
                + " and pr.id.instituteid = :instituteid and pr.id.academicyear = :academicyear and pr.id.programid in (:programid) and pr.id.branchid in (:branchid) ");
        if (!"All".equals(stynumber)) {
            sb.append(" and pr.id.stynumber =:stynumber ");
        }
        try {
            if (!stynumber.equals("All")) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("instituteid", instituteid).
                        setParameter("academicyear", academicyear).
                        setParameterList("programid", programid).
                        setParameterList("branchid", branchid).
                        setParameter("stynumber", Byte.parseByte(stynumber)).list();
            } else {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("instituteid", instituteid).
                        setParameter("academicyear", academicyear).
                        setParameterList("programid", programid).
                        setParameterList("branchid", branchid).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
