package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.BranchMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.BranchMaster;
import com.jilit.irp.persistence.dto.BranchMasterId;
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
 * @author singh.jaswinder
 */
public class BranchMasterDAO extends HibernateDAO implements BranchMasterIDAO {

    private static final Log log = LogFactory.getLog(BranchMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all BranchMaster records via Hibernate from the database");
        return this.find("from BranchMaster as tname order by seqid asc");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all BranchMaster records via Hibernate from the database");
        return this.find("from BranchMaster as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(BranchMaster.class, id);
    }

    public int checkIfChildExist(final BranchMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                BranchMaster branchMaster = (BranchMaster) session.get(BranchMaster.class, id);
                int i1 = Integer.parseInt((session.createFilter(branchMaster.getDepartmentbranchtaggings(), "select count(*)").list().get(0)).toString());
                int i2 = Integer.parseInt((session.createFilter(branchMaster.getMentorMasters(), "select count(*)").list().get(0)).toString());
                int i3 = Integer.parseInt((session.createFilter(branchMaster.getPrerequisitforregistrations(), "select count(*)").list().get(0)).toString());
                int i4 = Integer.parseInt((session.createFilter(branchMaster.getProgrammaxsties(), "select count(*)").list().get(0)).toString());
                int i5 = Integer.parseInt((session.createFilter(branchMaster.getProgramwisesubsections(), "select count(*)").list().get(0)).toString());
                int i6 = Integer.parseInt((session.createFilter(branchMaster.getRollnumberingsetupdetails(), "select count(*)").list().get(0)).toString());
                int i7 = Integer.parseInt((session.createFilter(branchMaster.getStudentcertificatesetupdetails(), "select count(*)").list().get(0)).toString());
                int i8 = Integer.parseInt((session.createFilter(branchMaster.getStudentcertificateslnos(), "select count(*)").list().get(0)).toString());
                int i9 = Integer.parseInt((session.createFilter(branchMaster.getStudentmasters(), "select count(*)").list().get(0)).toString());
                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getbranchMasterList(String instituteid) {
        List list = null;
        String strqry = " select bm.id.instituteid,bm.id.programid,bm.id.branchid,bm.branchcode,bm.branchdesc,bm.deactive,pm.programcode from ProgramMaster pm,BranchMaster bm "
                + " where pm.id.instituteid=bm.id.instituteid and pm.id.programid=bm.id.programid and bm.id.instituteid='" + instituteid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getBranchCodeWithProList(String insid, String programid) {
        List list = null;
        String strqry = "select id.branchid, branchcode, branchdesc from BranchMaster"
                + " where coalesce(deactive,'N')='N' "
                + " and id.instituteid='" + insid + "' and "
                + " id.programid in(" + programid + ")";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getToBranchCode(String insid, String programid, String branchid) {
        List list = null;
        String strqry = "select id.branchid, branchcode, branchdesc from BranchMaster"
                + " where coalesce(deactive,'N')='N' "
                + " and id.instituteid='" + insid + "' and "
                + " id.programid ='" + programid + "'"
                + " and id.branchid not in('" + branchid + "')";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getBranchMasterEdit(String instituteid, String programid, String branchid) {
        List list = null;
        String strqry = " select bm.id.instituteid,bm.id.programid,bm.id.branchid,bm.branchcode,bm.branchdesc,bm.deactive,pm.programcode,bm.certificatecode,bm.enrollmentcode,bm.seqid from ProgramMaster pm,BranchMaster bm "
                + " where pm.id.instituteid=bm.id.instituteid and pm.id.programid=bm.id.programid and bm.id.instituteid='" + instituteid + "' "
                + " and bm.id.programid='" + programid + "' and bm.id.branchid='" + branchid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> doValidate(final BranchMaster branchMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(BranchMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", branchMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.programid", branchMaster.getId().getProgramid()));
                criteria.add(Restrictions.eq("branchcode", branchMaster.getBranchcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.branchid", branchMaster.getId().getBranchid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Branch Code ");
        }
        return errors;
    }

    public List getBranchCode(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select bm.branchcode,bm.branchdesc,bm.id.instituteid,bm.id.programid,bm.id.branchid from BranchMaster bm"
                + " where bm.id.programid=:programid and bm.id.instituteid=:instituteid and coalesce(bm.deactive,'N')='N'  order by bm.seqid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("programid", programid).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBranchCode1(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select bm.branchcode from BranchMaster bm where bm.id.programid=:programid and bm.id.instituteid=:instituteid");
        sb.append(" and coalesce(bm.deactive,'N')='N'  order by bm.seqid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("programid", programid).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAll_TSRBranchCode(String programid, String instid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select id.branchid,id.instituteid,id.programid, branchcode, branchdesc from BranchMaster"
                + " where id.instituteid=:instituteid  and coalesce(deactive,'N')='N'");
        if (!programid.equalsIgnoreCase("All")) {
            sb.append(" and id.programid=:programid");
        } else {
            sb.append(" and :programid=:programid");
        }
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("programid", programid).
                    setParameter("instituteid", instid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getBranchForSubjectFinalization(String instituteid, String regid, String acadyear, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct bm.id.branchid,bm.branchcode,bm.branchdesc from BranchMaster bm where coalesce (bm.deactive, 'N') = 'N' and bm.id.instituteid = :instituteid"
                + " and bm.id.programid = :programid and exists (select 1 from StudentRegistration_info sri where sri.programid=bm.id.programid and sri.id.instituteid=:instituteid"
                + " and sri.academicyear=:acadyear and sri.programid=:programid and sri.id.registrationid=:regid )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getBranchForAcadWiseRegistration(String instituteid, String regid, List acadyear, List programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct bm.id.branchid,bm.id.programid,bm.branchcode from BranchMaster bm where coalesce (bm.deactive, 'N') = 'N' and bm.id.instituteid = :instituteid"
                + " and bm.id.programid in(:programid) and exists (select 1 from StudentRegistration_info sri where sri.programid=bm.id.programid and sri.id.instituteid=:instituteid"
                + " and sri.academicyear in(:acadyear) and sri.programid in(:programid) and sri.id.registrationid=:regid )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameterList("acadyear", acadyear).setParameterList("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }
}
