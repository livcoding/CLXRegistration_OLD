package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SectionMasterIDAO;
import com.jilit.irp.persistence.dto.SectionMaster;
import com.jilit.irp.persistence.dto.SectionMasterId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author ankur.goyal
 */
public class SectionMasterDAO extends HibernateDAO implements SectionMasterIDAO {

    private static final Log log = LogFactory.getLog(SectionMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SectionMaster records via Hibernate from the database");
        return this.find("from SectionMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all SectionMaster records via Hibernate from the database");
        return this.find("from SectionMaster as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SectionMaster.class, id);
    }

    public int checkIfChildExist(final SectionMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SectionMaster master = (SectionMaster) session.get(SectionMaster.class, id);
                int i1 = Integer.parseInt(session.createFilter(master.getProgramwisesubsections(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(master.getPrsubjecttypewiseapprovals(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(master.getProgramschemes(), "select count(*)").list().get(0).toString());
                return i1 + i2 + i3;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final SectionMaster sectionMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        /*Unique Key Constraint on instituteid and Shortname*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(SectionMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", sectionMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("sectioncode", sectionMaster.getSectioncode()));
                if (mode.equals("update")) {
                    criteria.add(Restrictions.ne("id.sectionid", sectionMaster.getId().getSectionid()));   //Do not check for itself when updating record

                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Section Code!");
        }
        return errors;
    }

    public Collection<?> getSection(final String instituteId, final String sectioncode) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(SectionMaster.class, "master")
                        .createAlias("master.programwisesubsections", "pws")
                        .setFetchMode("programwisesubsections", FetchMode.JOIN);
                criteria.add(Restrictions.eq("id.instituteid", instituteId));
                if (sectioncode != null) {
                    criteria.add(Restrictions.eq("master.sectioncode", sectioncode));
                }

                criteria.setProjection(Projections.projectionList().
                        add(Projections.distinct(Projections.property("master.id.sectionid").as("sectionid"))).
                        add(Projections.property("master.sectioncode").as("sectioncode")).
                        add(Projections.property("master.sectiondesc").as("sectiondesc")));

                //criteria.setProjection(Projections.distinct(Projections.property("programid")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                return criteria.list();
            }
        });
        return list;
    }

    @Override
    public List getSectionMaster(String instituteid, String registrationid, String subjectid, String acad_year, Byte stynumber, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select  sec.id.instituteid, sec.id.sectionid,sec.sectioncode, sec.sectiondesc ,sec.sectiondesc,sec.seqid , sec.deactive  from "
                + " SectionMaster sec  where sec.id.instituteid=:instituteid  and (exists ( select sm.sectionid  from StudentMaster sm,StudentSubjectChoiceMaster scm   "
                + " where sm.instituteid=sec.id.instituteid and sm.sectionid=sec.id.sectionid and sm.instituteid=scm.id.instituteid and sm.studentid=scm.id.studentid "
                + " and scm.id.registrationid=:registrationid ");
        if (!subjectid.equalsIgnoreCase("All")) {
            sb.append(" and scm.id.subjectid= :subjectid");
        }
        if (!acad_year.equalsIgnoreCase("All")) {
            sb.append(" and sm.acadyear= :acad_year");
        }
        if (!programid.equalsIgnoreCase("All")) {
            sb.append(" and sm.programid= :programid");
        }
        sb.append(" and  :subjectid = :subjectid and :acad_year = :acad_year and scm.id.stynumber = :stynumber and  :programid = :programid");
        sb.append(" and scm.id.instituteid=:instituteid  group by sm.sectionid)) order by sec.sectioncode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("stynumber", stynumber).setParameter("subjectid", subjectid).setParameter("programid", programid).setParameter("acad_year", acad_year).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAllSectionMaster(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sect.sectioncode,sect.sectiondesc,sect.id.sectionid,sect.id.instituteid from SectionMaster sect "
                + " where sect.id.instituteid = :instituteid and coalesce(sect.deactive,'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllSectionMaster_NotSaved(String instituteid, String bas_code, String programid, String styno) {
        String queryString = "select sect.sectioncode ,sect.sectiondesc, sect.id.sectionid, sect.id.instituteid from SectionMaster sect where  sect.id.instituteid='" + instituteid + "'  and coalesce(sect.deactive,'N')='N'"
                + " and exists(select 1 from ProgramWiseSubsection p where p.id.sectionid=sect.id.sectionid and p.id.instituteid=sect.id.instituteid and p.id.programid='" + programid + "' ) "
                + "and not exists (select 1 from BasketMaster bs where bs.stynumber='" + styno + "' and bs.id.instituteid=sect.id.instituteid and bs.sectionid=sect.id.sectionid and bs.basketcode='" + bas_code + "') order by sect.seqid";
        List list = getHibernateTemplate().find(queryString);
        return list;
    }

    public List getSectionCode(String ins_id, String branchid) {
        String queryString = "Select  sm.id.sectionid,sm.sectioncode from  SectionMaster sm where sm.id.instituteid='" + ins_id + "' and  sm.branchid in  (" + branchid + ") ";
        List list = getHibernateTemplate().find(queryString);
        return list;
    }

    public List getSectionCodeByProg(String instituteid, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select "
                + " sm.sectioncode,"
                + " sm.sectiondesc,"
                + " sm.id.sectionid,"
                + " sm.id.instituteid,"
                + " sm.seqid"
                + " from"
                + " SectionMaster sm"
                + " where "
                + " exists(select 1 from ProgramWiseSubsection p where p.id.sectionid=sm.id.sectionid and p.id.instituteid=sm.id.instituteid and p.id.programid=:programid ) "
                + " and sm.id.instituteid=:instituteid  "
                + " and coalesce(sm.deactive, 'N')='N' "
                + " order by  sm.sectioncode  ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    /**
     * Description: Function has been used to getSection code basis of following
     * parameter.
     *
     * @param stynumber
     * @param branchid
     * @param acadyear
     * @param programid
     * @param instid
     * @return
     */
    public List getTSRSection(final List stynumber, final List branchid, final List acadyear, final String programid, final String instid) {
        List list = null;
        Session session = null;
        StringBuilder qryString = new StringBuilder();
        qryString.append(" select sm.id.sectionid, sm.sectioncode, sm.sectiondesc ");
        qryString.append(" from SectionMaster sm ");
        qryString.append(" where ");
        qryString.append(" id.instituteid=:instid ");
        qryString.append(" and coalesce(deactive,'N')='N' and");
        qryString.append(" exists (select 1 from ProgramWiseSubsection ");
        qryString.append(" where");
        qryString.append(" id.sectionid=sm.id.sectionid ");
        qryString.append(" and id.instituteid=:instid ");
        qryString.append(" and  id.academicyear in(:acadyear) ");
        if (!("All".equals(programid))) {
            qryString.append("  and  id.programid = :programid ");
        }
        qryString.append("  and  id.stynumber in(:stynumber) ");
        qryString.append("  and  branchid in(:branchid)) order by  sm.sectioncode ");

        try {
            session = (Session) getSession();
            list = session.createQuery(qryString.toString()).
                    setParameter("instid", instid).setParameterList("branchid", branchid).
                    setParameter("programid", programid).
                    setParameterList("stynumber", stynumber).
                    setParameterList("acadyear", acadyear).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            qryString = null;
        }
        return list;
    }

    public Collection<?> getSection_SMF(final String instituteId, final String programid, final String academicyear, final byte stynumber, final String branchid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(SectionMaster.class,
                        "master")
                        .createAlias("master.programwisesubsections", "pws")
                        .setFetchMode("programwisesubsections", FetchMode.JOIN);
                criteria.add(Restrictions.eq("id.instituteid", instituteId));
                criteria.add(Restrictions.eq("pws.id.academicyear", academicyear));
                criteria.add(Restrictions.eq("pws.id.programid", programid));
                criteria.add(Restrictions.eq("pws.id.stynumber", stynumber));
                //criteria.add(Restrictions.eq("pws.id.sectionid", sectionid));
                criteria.add(Restrictions.eq("pws.branchid", branchid));
//                if (!sectioncode.equals("")) {
//                    criteria.add(Restrictions.eq("master.sectioncode", sectioncode));
//                }
                criteria.setProjection(Projections.projectionList().
                        add(Projections.distinct(Projections.property("master.id.sectionid").as("sectionid"))).
                        add(Projections.property("master.sectioncode").as("sectioncode")).
                        add(Projections.property("master.sectiondesc").as("sectiondesc")));
                criteria.addOrder(Order.asc("master.sectioncode"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getSectionDataWithBranch(final String instituteid, final String programid, final String branchid) {
        List list = null;
        StringBuilder qryString = new StringBuilder();
        qryString.append(" select sm.id.sectionid as sectionid, sm.sectioncode as sectioncode, sm.sectiondesc as sectiondesc ");
        qryString.append(" from SectionMaster sm ");
        qryString.append(" where ");
        qryString.append(" sm.id.instituteid= :instituteid ");
        qryString.append(" and coalesce(sm.deactive,'N')='N' and");
        qryString.append(" exists (select 1 from ProgramWiseSubsection ");
        qryString.append(" where id.sectionid=sm.id.sectionid ");
        qryString.append(" and id.instituteid= :instituteid ");
        qryString.append(" and id.programid= :programid ");
        qryString.append(" and branchid= :branchid ) order by sm.sectioncode");
        try {
            list = getHibernateSession().createQuery(qryString.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qryString = null;
        }
        return list;
    }

    public List getAll_SectionData_SCR(final String registrationid, final String programid, final String instituteid) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder qryString = new StringBuilder();
                qryString.append(" select sm.sectionid, sm.sectioncode, sm.sectiondesc from SectionMaster sm ");
                qryString.append(" where sm.instituteid='" + instituteid + "' and coalesce(sm.deactive,'N')='N' and ");
                qryString.append(" exists(select 1 from ProgramSubjectTagging pst  ");
                qryString.append(" where pst.instituteid=sm.instituteid and pst.sectionid=sm.sectionid and ");
                if (!("All").equals(programid)) {
                    qryString.append(" pst.programid in ('" + programid + "') and ");
                }
                qryString.append(" pst.registrationid='" + registrationid + "' ) order by sm.sectioncode ");

                return session.createSQLQuery(qryString.toString()).list();
            }
        });
        return list;
    }

    public List getSectionForSubjectFinalization(String instituteid, String regid, String acadyear, String programid, List branchid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct bm.id.branchid,bm.branchcode,sm.id.sectionid,sm.sectioncode from SectionMaster sm , BranchMaster bm,ProgramWiseSubsection pws where coalesce (sm.deactive, 'N') = 'N' and sm.id.instituteid=bm.id.instituteid and pws.id.sectionid = sm.id.sectionid "
                + " and pws.branchid = bm.id.branchid and sm.id.instituteid = :instituteid and bm.id.branchid in (:branchid) and bm.id.programid = :programid and exists (select 1 from StudentRegistration_info sri where sri.sectionid=sm.id.sectionid and  sri.sectionid=sm.id.sectionid  and sri.branchid = pws.branchid "
                + " and sri.programid=bm.id.programid and sri.id.instituteid=:instituteid and sri.academicyear=:acadyear and sri.programid=:programid and sri.id.registrationid=:regid and sri.branchid in (:branchid)) order by sm.sectioncode");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear).setParameter("programid", programid).setParameterList("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getAllSectionMaster_Fst(String instituteid, String reg_id, String programid, String acad_year) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sectionid, sectionmaster.sectioncode from ProgramSubjectTagging where academicyear=:acad_year and programid=:programid "
                + " and coalesce(deactive,'N')='N' and id.registrationid  =:reg_id and id.instituteid =:instituteid order by sectionmaster.sectioncode");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("reg_id", reg_id).setParameter("acad_year", acad_year).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    @Override
    public List getSectionCode_Pst(String ins_id, String prg_id, String acad_year, String reg_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select  sec.id.instituteid,sec.id.sectionid,sec.sectioncode,sec.sectiondesc,sec.seqid,sec.deactive from SectionMaster sec" //sec.branchid,
                + " where  sec.id.instituteid=:ins_id  and (sec.id.sectionid in ( select  prsubtag.sectionid   from  ProgramSubjectTagging prsubtag where  sec.id.sectionid=prsubtag.sectionid "
                + " and prsubtag.id.instituteid=:ins_id and prsubtag.academicyear=:acad_year and prsubtag.id.registrationid=:reg_id and prsubtag.programid=:prg_id and coalesce(prsubtag.deactive, 'N')='N' )) "
                + "  order by  sec.sectioncode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).
                    setParameter("reg_id", reg_id).
                    setParameter("prg_id", prg_id).
                    setParameter("acad_year", acad_year).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubSectionListForSectionCode(String instituteid, String registrationid, String sectionId) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct ps.id.subsectionid, ps.subsectioncode, ps.subsectiondesc from ProgramWiseSubsection ps ");
        qry.append(" where ps.id.instituteid= :instituteid ");
        qry.append(" and ps.id.sectionid= :sectionid ");
        qry.append(" and exists( select ttad.id.instituteid from TT_TimeTableAllocationDetail ttad where ttad.id.instituteid=ps.id.instituteid and ttad.id.registrationid=:registrationid and ttad.id.subsectionid=ps.id.subsectionid ");
        qry.append(" and ttad.id.sectionid=ps.id.sectionid and ttad.id.programid=ps.id.programid and ttad.id.academicyear=ps.id.academicyear and ttad.id.stynumber=ps.id.stynumber) ");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("instituteid", instituteid)
                .setParameter("registrationid", registrationid)
                .setParameter("sectionid", sectionId).list();

        return list;
    }
}
