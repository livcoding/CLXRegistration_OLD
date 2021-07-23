/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ProgramSubjectTaggingIDAO;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.SectionMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.util.JIRPStringUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sunny.singhal
 */
public class ProgramSubjectTaggingDAO extends HibernateDAO implements ProgramSubjectTaggingIDAO {

    private static final Log log = LogFactory.getLog(ProgramSubjectTaggingDAO.class);
    private Session session = null;

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramSubjectTagging records via Hibernate from the database");
        return this.find("from ProgramSubjectTagging as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramSubjectTagging.class, id);
    }

    public List getAcadYear(String instituteid, String regid) {
        StringBuilder qry = new StringBuilder();
        qry.append("  select distinct sr.academicyear ");
        qry.append(" from StudentRegistration_info sr where sr.id.instituteid=:instituteid");
        qry.append(" and sr.id.registrationid=:regid order by sr.academicyear desc ");
        try {
            return getHibernateSession().createQuery(qry.toString()).setParameter("regid", regid).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    @Override
    public List getProgramTaggingData(String instituteid, String regId, String academicyear, String programid, String branchId, Short stynumber, String sectionid, String basketid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pst from ProgramSubjectTagging pst ");
        qry.append(" where pst.id.instituteid= :instituteid ");
        qry.append(" and pst.id.registrationid= :registrationid ");
        qry.append(" and pst.academicyear= :academicyear ");
        qry.append(" and pst.basketid= :basketid ");
        qry.append(" and pst.programid= :programid ");
        qry.append(" and pst.sectionid= :sectionid ");
        qry.append(" and pst.stynumber= :stynumber");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("registrationid", regId)
                    .setParameter("instituteid", instituteid)
                    .setParameter("academicyear", academicyear)
                    .setParameter("sectionid", sectionid)
                    .setParameter("programid", programid)
                    .setParameter("stynumber", stynumber)
                    .setParameter("basketid", basketid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getProgramTaggingData(String instituteid, String regId, String programSubId) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pst from ProgramSubjectTagging pst ");
        qry.append(" where pst.id.instituteid= :instituteid ");
        qry.append(" and pst.id.registrationid= :registrationid ");
        qry.append(" and pst.id.programsubjectid= :programsubjectid ");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("registrationid", regId)
                .setParameter("instituteid", instituteid)
                .setParameter("programsubjectid", programSubId).list();

        return list;
    }

    @Override
    public List getTakenCredit(String instituteid, String registrationid, String studentid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select sum(pst.credits) from ProgramSubjectTagging pst ");
        qry.append(" where exists(select 1 from  FacultySubjectTagging fst, PRFacultyStudentTagging ");
        qry.append(" psft where fst.id.instituteid = pst.id.instituteid and pst.id.registrationid = fst.registrationid ");
        qry.append(" and fst.id.instituteid = psft.id.instituteid and psft.id.fstid = fst.id.fstid and coalesce(psft.auditsubject,'N')='N' and coalesce(psft.deactive,'N')='N' ");
        qry.append(" and fst.academicyear=pst.academicyear and fst.programid=pst.programid  and fst.sectionid = pst.sectionid ");
        qry.append(" and  fst.stynumber = pst.stynumber  and fst.basketid = pst.basketid and fst.subjectid = pst.subjectid ");
        qry.append(" and fst.id.instituteid = :instituteid and fst.registrationid=:registrationid and psft.id.studentid=:studentid )");
        list = getHibernateSession().createQuery(qry.toString())
                .setParameter("registrationid", registrationid)
                .setParameter("instituteid", instituteid)
                .setParameter("studentid", studentid).list();

        return list;
    }

    public List getData(String instituteid, String academicyear, String registrationid, String programid, List sectionid, Short stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("  select distinct ay.id.academicyear, ay.id.academicyear, pm.id.programid,pm.programcode, sem.id.sectionid,sem.sectioncode, pst.stynumber, "
                + "  bm.id.basketid,bm.basketcode, pws.subsectioncode, pws.id.subsectionid,  sm.id.subjectid, sm.subjectcode,sm.subjectdesc,  sc.id.subjectcomponentid,sc.subjectcomponentcode, "
                + "  sc.subjectcomponentdesc, 'D','99999999999999999999', pst.id.registrationid, (select 1 from  FacultySubjectTagging fst where  fst.academicyear = ay.id.academicyear and fst.id.instituteid = ay.id.instituteid  "
                + "  and fst.programid = pst.programid  and fst.id.instituteid= pst.id.instituteid  and fst.sectionid = sem.id.sectionid  and fst.id.instituteid = sem.id.instituteid  and fst.stynumber = pst.stynumber   "
                + "  and fst.id.instituteid = pst.id.instituteid  and fst.basketid = bm.id.basketid  and fst.id.instituteid = bm.id.instituteid  and fst.subsectionid = pws.id.subsectionid  and fst.id.instituteid = pws.id.instituteid   "
                + "  and fst.subjectid = sm.id.subjectid  and fst.id.instituteid = sm.id.instituteid  and fst.subjectcomponentid = sc.id.subjectcomponentid   and pst.id.registrationid = fst.registrationid and  fst.id.instituteid = sc.id.instituteid and rownum=1)as fstid,pst.subjecttype,pst.subjecttypeid  "
                + "  from  ProgramWiseSubsection pws, ProgramSubjectTagging pst,  ProgramSubjectDetail psd,  Academicyear ay,  ProgramMaster pm,  SectionMaster sem,  BasketMaster bm,  SubjectMaster sm,  SubjectComponent sc  "
                + " where  pst.id.instituteid =:instituteid and pst.id.registrationid=:registrationid  and pws.id.instituteid = pst.id.instituteid and pws.id.academicyear = pst.academicyear and  "
                + " pws.id.programid = pst.programid and pws.id.stynumber = pst.stynumber and pws.id.sectionid = pst.sectionid and psd.id.instituteid = pst.id.instituteid and psd.id.instituteid = pws.id.instituteid and psd.id.registrationid = pst.id.registrationid   "
                + " and psd.id.programsubjectid = pst.id.programsubjectid and ay.id.academicyear = pst.academicyear and ay.id.instituteid = pst.id.instituteid and pm.id.programid = pst.programid and pm.id.instituteid = pst.id.instituteid and sm.id.subjectid = pst.subjectid "
                + " and sm.id.instituteid = pst.id.instituteid and bm.id.basketid = pst.basketid and bm.id.instituteid = pst.id.instituteid and sem.id.sectionid = pst.sectionid and sc.id.instituteid = pst.id.instituteid and sc.id.subjectcomponentid = psd.id.subjectcomponentid and "
                + " sem.id.instituteid = pst.id.instituteid and coalesce(sc.deactive,'N')='N'  and coalesce(pst.deactive,'N')='N'  and coalesce(pws.deactive,'N')='N'  and coalesce(psd.deactive,'N')='N'  and coalesce(ay.deactive,'N')='N'  and coalesce(pm.deactive,'N')='N'  and coalesce(sm.deactive,'N')='N'  and "
                + " coalesce(sem.deactive,'N')='N'  and coalesce(bm.deactive,'N')='N'  and pst.academicyear=:academicyear and pst.programid=:programid and pst.sectionid in ( :sectionid ) and pst.stynumber=:stynumber order by  ay.id.academicyear, pm.id.programid,pm.programcode, sem.id.sectionid,sem.sectioncode, pst.stynumber, bm.id.basketid,bm.basketcode, pws.subsectioncode, pws.id.subsectionid, "
                + " sm.id.subjectid, sm.subjectcode,sm.subjectdesc,  sc.id.subjectcomponentid,sc.subjectcomponentcode,sc.subjectcomponentdesc, 'D','99999999999999999999' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).
                    setParameter("registrationid", registrationid).
                    setParameterList("sectionid", sectionid).
                    setParameter("programid", programid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubjectWiseFSTData(String instituteid, String academicyear, String registrationid, String subjectid, String subjectcomponentid,String stytype_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("  select distinct ay.id.academicyear, ay.id.academicyear, pm.id.programid,pm.programcode, sem.id.sectionid,sem.sectioncode, pst.stynumber, "
                + "  bm.id.basketid,bm.basketcode, pws.subsectioncode, pws.id.subsectionid,  sm.id.subjectid, sm.subjectcode,sm.subjectdesc,  sc.id.subjectcomponentid,sc.subjectcomponentcode, "
                + "  sc.subjectcomponentdesc, 'D','99999999999999999999', pst.id.registrationid, (select concat(concat(vs.employeecode,' / '),vs.employeename) from  FacultySubjectTagging fst,V_Staff vs,TT_TimeTableAllocation tta where  fst.academicyear = ay.id.academicyear and fst.id.instituteid = ay.id.instituteid  "
                + "  and fst.programid = pst.programid  and fst.id.instituteid= pst.id.instituteid  and fst.sectionid = sem.id.sectionid  and fst.id.instituteid = sem.id.instituteid  and fst.stynumber = pst.stynumber   "
                + "  and fst.id.instituteid = pst.id.instituteid  and fst.basketid = bm.id.basketid  and fst.id.instituteid = bm.id.instituteid  and fst.subsectionid = pws.id.subsectionid  and fst.id.instituteid = pws.id.instituteid   "
                + "  and fst.subjectid = sm.id.subjectid  and fst.id.instituteid = sm.id.instituteid  and fst.subjectcomponentid = sc.id.subjectcomponentid   and pst.id.registrationid = fst.registrationid and  fst.id.instituteid = sc.id.instituteid"
                + "  and vs.employeeid=tta.staffid and tta.id.instituteid=fst.id.instituteid and fst.stytypeid=:stytype_id and tta.ttreferenceid=fst.ttreferenceid and tta.id.registrationid=fst.registrationid and tta.subjectid=fst.subjectid and fst.subjectcomponentid=tta.subjectcomponentid"
                + "  and rownum=1)as fstid,pst.subjecttype,pst.subjecttypeid,pws.seqid  "
                + "  from  ProgramWiseSubsection pws, ProgramSubjectTagging pst,  ProgramSubjectDetail psd,  Academicyear ay,  ProgramMaster pm,  SectionMaster sem,  BasketMaster bm,  SubjectMaster sm,  SubjectComponent sc  "
                + " where  pst.id.instituteid =:instituteid and pst.id.registrationid=:registrationid  and pws.id.instituteid = pst.id.instituteid and pws.id.academicyear = pst.academicyear and  "
                + " pws.id.programid = pst.programid and pws.id.stynumber = pst.stynumber and pws.id.sectionid = pst.sectionid and psd.id.instituteid = pst.id.instituteid and psd.id.instituteid = pws.id.instituteid and psd.id.registrationid = pst.id.registrationid   "
                + " and psd.id.programsubjectid = pst.id.programsubjectid and ay.id.academicyear = pst.academicyear and ay.id.instituteid = pst.id.instituteid and pm.id.programid = pst.programid and pm.id.instituteid = pst.id.instituteid and sm.id.subjectid = pst.subjectid "
                + " and sm.id.instituteid = pst.id.instituteid and bm.id.basketid = pst.basketid and bm.id.instituteid = pst.id.instituteid and sem.id.sectionid = pst.sectionid and sc.id.instituteid = pst.id.instituteid and sc.id.subjectcomponentid = psd.id.subjectcomponentid and "
                + " sem.id.instituteid = pst.id.instituteid and coalesce(sc.deactive,'N')='N'  and coalesce(pst.deactive,'N')='N'  and coalesce(pws.deactive,'N')='N'  and coalesce(psd.deactive,'N')='N'  and coalesce(ay.deactive,'N')='N'  and coalesce(pm.deactive,'N')='N'  and coalesce(sm.deactive,'N')='N'  and "
                + " coalesce(sem.deactive,'N')='N'  and coalesce(bm.deactive,'N')='N'  and pst.academicyear=:academicyear and pst.subjectid=:subjectid  and sc.id.subjectcomponentid=:subjectcomponentid order by  ay.id.academicyear,pm.programcode, sem.sectioncode, pst.stynumber,bm.basketcode,pws.subsectioncode, "
                + "  sm.subjectcode,sm.subjectdesc, sc.subjectcomponentcode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("academicyear", academicyear).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("stytype_id", stytype_id).
                    setParameter("subjectcomponentid", subjectcomponentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public int checkIfChildExist1(final String instituteid, final String registrationid, final String programsubjectid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramSubjectTagging programSubjectTagging = (ProgramSubjectTagging) session.get(ProgramSubjectTagging.class, new ProgramSubjectTaggingId(instituteid, registrationid, programsubjectid));
                String qry = " select count(*) from StudentSubjectChoiceMaster a, StudentRegistration_info b "
                        + " where a.instituteid=b.instituteid and  a.registrationid=b.registrationid "
                        + " and a.studentid=b.studentid "
                        + " and a.instituteid='" + instituteid + "' "
                        + " and a.registrationid='" + registrationid + "' "
                        + " and b.stynumber='" + programSubjectTagging.getStynumber() + "' "
                        + " and b.academicyear='" + programSubjectTagging.getAcademicyear() + "' "
                        + " and b.programid='" + programSubjectTagging.getProgramid() + "' "
                        + " and b.sectionid='" + programSubjectTagging.getSectionid() + "' "
                        + " and a.subjectid='" + programSubjectTagging.getSubjectid() + "' ";//+
                int i1 = Integer.parseInt(session.createSQLQuery(qry).list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public int checkIfChildExist2(final String instituteid, final String registrationid, final String programsubjectid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramSubjectTagging programSubjectTagging = (ProgramSubjectTagging) session.get(ProgramSubjectTagging.class, new ProgramSubjectTaggingId(instituteid, registrationid, programsubjectid));
                String qry = "  select count(*) from FacultySubjectTagging fst"
                        + " where fst.instituteid='" + instituteid + "' "
                        + " and registrationid='" + registrationid + "' "
                        + " and subjectid='" + programSubjectTagging.getSubjectid() + "' "
                        + " and stynumber='" + programSubjectTagging.getStynumber() + "' "
                        + " and academicyear='" + programSubjectTagging.getAcademicyear() + "' "
                        + " and programid='" + programSubjectTagging.getProgramid() + "' "
                        + " and sectionid='" + programSubjectTagging.getSectionid() + "' ";
                int i1 = Integer.parseInt(session.createSQLQuery(qry).list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final ProgramSubjectTagging programSubjectTagging, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramSubjectTagging.class);
                criteria.add(Restrictions.eq("id.instituteid", programSubjectTagging.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.registrationid", programSubjectTagging.getId().getRegistrationid()).ignoreCase());
                criteria.add(Restrictions.eq("academicyear", programSubjectTagging.getAcademicyear()).ignoreCase());
                criteria.add(Restrictions.eq("programid", programSubjectTagging.getProgramid()).ignoreCase());
                criteria.add(Restrictions.eq("sectionid", programSubjectTagging.getSectionid()).ignoreCase());
                criteria.add(Restrictions.eq("stynumber", programSubjectTagging.getStynumber()));
                criteria.add(Restrictions.eq("basketid", programSubjectTagging.getBasketid()).ignoreCase());
                criteria.add(Restrictions.eq("subjectid", programSubjectTagging.getSubjectid()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.programsubjectid", programSubjectTagging.getId().getProgramsubjectid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Subject Code Found! ");
        }
        return errors;
    }

    public List getAll_TTSTYNumberData(final String instituteid, final String registrationid, final String programid, final String sectionid) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder qryString = new StringBuilder();
                qryString.append(" select  distinct pst.stynumber  from ProgramSubjectTagging pst ");
                qryString.append(" where  pst.instituteid='" + instituteid + "' and ");
                qryString.append(" pst.registrationid='" + registrationid + "' and ");
                if (!("ALL".equalsIgnoreCase(programid)) && !("".equalsIgnoreCase(programid))) {
                    qryString.append(" pst.programid in ('" + programid + "') and ");
                }
                if (!("ALL".equalsIgnoreCase(sectionid))) {
                    qryString.append(" pst.sectionid='" + sectionid + "' and ");
                }
                qryString.append(" coalesce(pst.deactive,'N')='N' order by pst.stynumber");
                return session.createSQLQuery(qryString.toString()).list();
            }
        });
        return list;
    }

    public List getAllRunningSubjectFromProgramSubjectTagging(final String instituteid, final String registrationid, final String programid, final String sectionid, final String academicyear, final String stynumber) {
        List list = null;
        String str = " select distinct pst.id.instituteid, pst.subjectmaster.subjectcode, pst.subjectmaster.subjectdesc, "
                + " pst.credits, pst.stynumber, pst.programid, pst.sectionid, pst.subjectid, pst.departmentid, "
                + " pst.programmaster.programdesc, pst.sectionmaster.sectioncode ,stm.subjecttypedesc, bm.basketcode,dm.department,pst.subjectrunning  "
                + " from "
                + " ProgramSubjectTagging pst, ProgramSubjectDetail psd, SubjectTypeMaster stm,  BasketMaster bm,DepartmentMaster dm"
                + "  where "
                + " pst.id.instituteid='" + instituteid + "' "
                + " and dm.departmentid=pst.departmentid"
                + " and pst.id.instituteid=stm.id.instituteid "
                + " and pst.id.instituteid=bm.id.instituteid "
                + " and bm.id.basketid=pst.basketid "
                + " and stm.id.subjecttypeid=bm.subjecttypeid"
                + " and pst.id.registrationid='" + registrationid + "' "
                + " and pst.sectionid='" + sectionid + "' "
                + " and pst.academicyear='" + academicyear + "' "
                + " and psd.id.instituteid = pst.id.instituteid "
                + " and psd.id.registrationid = pst.id.registrationid "
                + " and psd.id.programsubjectid = pst.id.programsubjectid ";
        if (!("All".equals(programid))) {
            str = str + " and pst.programid='" + programid + "' ";
        }
        if (!("All".equals(stynumber))) {
            str = str + " and pst.stynumber='" + stynumber + "' ";
        }
        str = str + " and coalesce(pst.subjectrunning,'N') = 'Y' ";
        str = str + " order by pst.programid, pst.sectionid, pst.stynumber, stm.subjecttypedesc";
        list = getHibernateTemplate().find(str);
        return list;
    }

    public List getAllRunningSubjectFromProgramScheme(final String instituteid, final String programid, final String stynumber, final String sectionid) {
        List list = null;

        String qryString = " select b "
                + " from ProgramScheme b "
                + " where  b.id.instituteid = '" + instituteid + "' "
                + " and b.sectionid = '" + sectionid + "' ";
        if (!("All".equals(programid))) {
            qryString = qryString + " and b.programid = '" + programid + "' ";
        }
        if (!("All".equals(stynumber))) {
            qryString = qryString + " and b.stynumber = '" + stynumber + "' ";
        }
        qryString = qryString + " order by b.electiveid desc ";

        return list;
    }

    public String getProgramCode(final String institiuteid, final String programid) {
        final String programcode = (String) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ProgramMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", institiuteid));
                criteria.add(Restrictions.eq("master.id.programid", programid));
                criteria.setProjection(Projections.property("master.programcode"));
                return criteria.uniqueResult();
            }
        });
        return programcode;
    }

//    /**
//     * Description: Section to get sectioncode.
//     * @param institiuteid
//     * @param sectionid
//     * @return
//     */
    public String getSectionCode(final String institiuteid, final String sectionid) {
        final String sectioncode = (String) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(SectionMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", institiuteid));
                criteria.add(Restrictions.eq("master.id.sectionid", sectionid));
                criteria.setProjection(Projections.property("master.sectioncode"));
                return criteria.uniqueResult();
            }
        });
        return sectioncode;
    }

    public List getAllRunningSubjectFromProgramSubjectTagging_LTP(final String instituteid, final String registrationid, final String programid, final String sectionid, final String academicyear, final String stynumber, final String subjectcode) {
        List list = null;
        String str = " select distinct pst.id.instituteid, pst.subjectmaster.subjectcode, pst.subjectmaster.subjectdesc, "
                + " pst.credits, pst.stynumber, pst.programid, pst.sectionid, pst.subjectid, pst.departmentid, "
                + " pst.programmaster.programdesc, pst.sectionmaster.sectioncode, sc.subjectcomponentcode, psd.totalccpmarks "
                + " from "
                + " ProgramSubjectTagging pst, ProgramSubjectDetail psd, SubjectComponent sc "
                + "  where "
                + " pst.id.instituteid='" + instituteid + "' "
                + " and pst.id.registrationid='" + registrationid + "' "
                + " and pst.sectionid='" + sectionid + "' "
                + " and pst.academicyear='" + academicyear + "' "
                + " and pst.subjectmaster.subjectcode='" + subjectcode + "' "
                + " and psd.id.instituteid = pst.id.instituteid "
                + " and psd.id.registrationid = pst.id.registrationid "
                + " and psd.id.programsubjectid = pst.id.programsubjectid "
                + " and sc.id.instituteid = psd.id.instituteid "
                + " and sc.id.subjectcomponentid = psd.id.subjectcomponentid ";
        if (!("All".equals(programid))) {
            str = str + " and pst.programid='" + programid + "' ";
        }
        if (!("All".equals(stynumber))) {
            str = str + " and pst.stynumber='" + stynumber + "' ";
        }
        str = str + " and coalesce(pst.subjectrunning,'N') = 'Y' ";
        str = str + " order by pst.programid, pst.sectionid, pst.stynumber ";
        list = getHibernateTemplate().find(str);
        return list;
    }

    public List getPSTList(String instituteid, String academicyear, String programid, String stynumber, String sectionid, String basketid, String reg) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.subjectid, a.departmentid, a.credits, a.totalmarks, a.passingmarks, a.id.programsubjectid,a.attendanceapproved,a.pstpopulated,a.subjectrunning,a.customfinalized,a.noofstudents,a.basketid,a.subjecttype,a.subjecttypeid "
                + " from ProgramSubjectTagging a where a.academicyear = :academicyear and a.stynumber = :stynumber "
                + " and a.programid = :programid "
                //+ " and a.sectionid = :sectionid"
                //+ " and a.basketid = :basketid "
                + " and a.id.registrationid = :registrationid and a.id.instituteid = :instituteid ");
        if (!("All".equals(sectionid))) {
            sb.append(" and a.sectionid = :sectionid ");
        }
        if (!("All".equals(basketid))) {
            sb.append(" and a.basketid = :basketid ");
        }

        try {
            if (!("All".equals(sectionid)) && !("All".equals(basketid))) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("academicyear", academicyear).
                        setParameter("stynumber", Short.valueOf(stynumber)).
                        setParameter("programid", programid).
                        setParameter("sectionid", sectionid).
                        setParameter("basketid", basketid).
                        setParameter("registrationid", reg).
                        setParameter("instituteid", instituteid).list();
            } else if (!("All".equals(sectionid)) && ("All".equals(basketid))) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("academicyear", academicyear).
                        setParameter("stynumber", Short.valueOf(stynumber)).
                        setParameter("programid", programid).
                        setParameter("sectionid", sectionid).
                        //setParameter("basketid", basketid).
                        setParameter("registrationid", reg).
                        setParameter("instituteid", instituteid).list();
            } else if (("All".equals(sectionid)) && !("All".equals(basketid))) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("academicyear", academicyear).
                        setParameter("stynumber", Short.valueOf(stynumber)).
                        setParameter("programid", programid).
                        //setParameter("sectionid", sectionid).
                        setParameter("basketid", basketid).
                        setParameter("registrationid", reg).
                        setParameter("instituteid", instituteid).list();
            } else {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("academicyear", academicyear).
                        setParameter("stynumber", Short.valueOf(stynumber)).
                        setParameter("programid", programid).
                        //setParameter("sectionid", sectionid).
                        //setParameter("basketid", basketid).
                        setParameter("registrationid", reg).
                        setParameter("instituteid", instituteid).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Boolean checkPSTExistence(String instituteid, String academicyear, String programid, String sectionid, String stynumber, String subjectid) {
        List list = null;
        boolean flag;
        StringBuilder sb = new StringBuilder();
        sb.append(" select 1 from ProgramSubjectTagging a where a.academicyear = :academicyear and a.stynumber = :stynumber"
                + " and a.programid = :programid and a.sectionid = :sectionid and a.id.instituteid = :instituteid "
                + " and a.subjectid = :subjectid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("academicyear", academicyear).
                    setParameter("stynumber", Short.valueOf(stynumber)).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public List getPSTDetail(String instituteid, String programsubjectid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.ltppassingmarks, a.totalccpmarks, a.noofhours, a.noofclassinaweek, a.totalclasses, "
                + " a.byltp, a.totalmarks, a.passingmarks, a.id.subjectcomponentid from ProgramSubjectDetail a "
                + " where a.id.instituteid = :instituteid and a.id.registrationid = :registrationid "
                + " and a.id.programsubjectid = :programsubjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("programsubjectid", programsubjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List dovalidate(String instituteid, String academicyear, String programid, Short stynumber, String sectionid, String basketid, String registrationid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select 1 from ProgramSubjectTagging pst where pst.id.instituteid=(:instituteid) and pst.id.registrationid=(:registrationid) and "
                + " pst.academicyear =(:academicyear) and pst.programid=(:programid) and pst.stynumber=(:stynumber)  and"
                + " pst.sectionid=(:sectionid)    and   pst.subjectid=(:subjectid) and pst.basketid = :basketid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("sectionid", sectionid).
                    setParameter("programid", programid).
                    setParameter("stynumber", stynumber).
                    setParameter("basketid", basketid).
                    setParameter("academicyear", academicyear).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public Boolean checkPSTExistence(String instituteid, String registrationid, String academicyear, String programid, String sectionid, String stynumber, String basketid, String subjectid) {
        List list = null;
        boolean flag;
        StringBuilder sb = new StringBuilder();
        sb.append(" select 1 from ProgramSubjectTagging a where a.academicyear = :academicyear and a.stynumber = :stynumber"
                + " and a.programid = :programid and a.sectionid = :sectionid and a.id.instituteid = :instituteid "// and a.basketid = :basketid"
                + " and a.id.registrationid =:registrationid and a.subjectid = :subjectid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("academicyear", academicyear).
                    setParameter("stynumber", Short.valueOf(stynumber)).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("registrationid", registrationid).list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public List getSubjectType(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct stm.id.subjecttypeid,stm.subjecttype,stm.subjecttypedesc "
                + " from ProgramSubjectTagging pst,SubjectTypeMaster stm "
                + " where pst.id.instituteid=:instituteid and pst.id.registrationid=:registrationid "
                + " and stm.id.instituteid=pst.id.instituteid and stm.id.subjecttypeid=pst.subjecttypeid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStyNumber(String instituteid, String registrationid, String subjecttypeid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pst.stynumber "
                + " from ProgramSubjectTagging pst "
                + " where pst.id.instituteid=:instituteid "
                + " and pst.id.registrationid=:registrationid  ");
        if (subjecttypeid.equals("All")) {
            sb.append(" and :subjecttypeid = :subjecttypeid");
        } else {
            sb.append(" and pst.subjecttypeid = :subjecttypeid");
        }
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjecttypeid", subjecttypeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStyNumberByInstituteid(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pst.stynumber "
                + " from ProgramSubjectTagging pst "
                + " where pst.id.instituteid=:instituteid order by pst.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getDepartment(String instituteid, String registrationid, String subjecttypeid, String stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct dm.id.departmentid,dm.departmentcode,dm.department"
                + " from ProgramSubjectTagging pst,DepartmentMaster dm "
                + " where pst.id.instituteid=:instituteid and pst.id.registrationid=:registrationid  "
                + " and pst.stynumber=:stynumber and dm.id.departmentid=pst.departmentid");
        if (subjecttypeid.equals("All")) {
            sb.append(" and :subjecttypeid = :subjecttypeid");
        } else {
            sb.append(" and pst.subjecttypeid = :subjecttypeid");
        }
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjecttypeid", subjecttypeid).
                    setParameter("stynumber", Short.valueOf(stynumber)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public String getPSTid(String instituteid, String regId, String academicyear, String programid, String branchId, Short stynumber, String sectionid, String basketid, String subjectid) {
        List list = null;
        String value = "";
        StringBuilder qry = new StringBuilder();
        qry.append(" select pst.id.programsubjectid from ProgramSubjectTagging pst ");
        qry.append(" where pst.id.instituteid= :instituteid ");
        qry.append(" and pst.id.registrationid= :registrationid ");
        qry.append(" and pst.academicyear= :academicyear ");
        qry.append(" and pst.basketid= :basketid ");
        qry.append(" and pst.programid= :programid ");
        qry.append(" and pst.sectionid= :sectionid ");
        qry.append(" and pst.stynumber= :stynumber");
        qry.append(" and pst.subjectid= :subjectid");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("registrationid", regId)
                    .setParameter("instituteid", instituteid)
                    .setParameter("academicyear", academicyear)
                    .setParameter("sectionid", sectionid)
                    .setParameter("programid", programid)
                    .setParameter("stynumber", stynumber)
                    .setParameter("basketid", basketid)
                    .setParameter("subjectid", subjectid).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
