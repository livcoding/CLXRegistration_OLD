package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.SubjectMasterIDAO;
import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.persistence.dto.SubjectMasterId;
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

/**
 *
 * @author singh.amarjeet
 */
public class SubjectMasterDAO extends HibernateDAO implements SubjectMasterIDAO {

    private static final Log log = LogFactory.getLog(SubjectMasterDAO.class);
    private Session session = null;

    public Collection<?> findAll() {

        log.info("Retrieving all SubjectMaster records via Hibernate from the database");
        return this.find("from SubjectMaster as tname");

    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all SubjectMaster records via Hibernate from the database");
        return this.find("from SubjectMaster as tname where tname.id.instituteid = ? order by tname.subjectcode", new Object[]{instituteid});
    }

    public Collection<?> findAllWithNVL(String instituteid) {
        log.info("Retrieving all SubjectMaster records via Hibernate from the database");
        return this.find("from SubjectMaster as tname where tname.id.instituteid = ? and coalesce(tname.deactive, 'N')='N' order  by tname.subjectcode", new Object[]{instituteid});
    }

    public Collection<?> findAllForMaster(String instituteid) {
        log.info("Retrieving all SubjectMaster records via Hibernate from the database");
        return this.find("from SubjectMaster as tname where tname.id.instituteid = ? order by tname.entrydatetime desc", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectMaster.class, id);

    }

    public List getSubjectForDST(String instituteid) {
        List list = null;
        String qry = "select sm.id.subjectid, sm.subjectcode, sm.subjectdesc from SubjectMaster sm  where sm.id.instituteid = '" + instituteid + "'"
                + " and not exists (select dst.id.subjectid from DepartmentSubjectTagging dst where dst.id.instituteid=sm.id.instituteid and dst.id.subjectid=sm.id.subjectid)"
                + " and coalesce(sm.deactive,'N')='N'";
        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public List getReqSubjectForBranchChange(String instituteid) {
        List list = null;
        String qry = "select sm.id.subjectid, sm.subjectcode, sm.subjectdesc from SubjectMaster sm  where sm.id.instituteid = '" + instituteid + "'";

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubjectCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid, sm.subjectcode, sm.subjectdesc from SubjectMaster sm where sm.id.instituteid = :instituteid ");
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

    @Override
    public List getMarks(String instituteid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
//        sb.append("select sm.credits,sm.totalmarks,sm.passingmarks from SubjectMaster sm where sm.id.instituteid=:instituteid and sm.id.subjectid=:subjectid ");
        sb.append(" select"
                + " sm.credits ,"
                + " sm.totalmarks ,"
                + " sm.passingmarks ,"
                + " sc.id.subjectcomponentid,"
                + " sc.subjectcomponentcode,"
                + " scd.totalccpmarks,"
                + " scd.noofhours,"
                + " scd.noofclassinaweek,"
                + " scd.totalclasses ,"
                + " scd.deactive   "
                + " from"
                + " SubjectMaster sm ,"
                + " SubjectComponentDetail scd,"
                + " SubjectComponent sc"
                + " where"
                + " sm.id.instituteid=:instituteid"
                + " and sm.id.subjectid=:subjectid"
                + " and scd.id.instituteid = sm.id.instituteid"
                + " and scd.id.subjectid = sm.id.subjectid"
                + " and sc.id.instituteid = sm.id.instituteid"
                + " and sc.id.subjectcomponentid = scd.id.subjectcomponentid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSub_Code_Fst(String instituteid, String registrationid, String departmentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select  sm.id.subjectid,sm.subjectcode,sm.subjectdesc from SubjectMaster sm where sm.id.instituteid =:instituteid  "
                + " and exists (select 1 from FacultySubjectTagging fst where sm.id.instituteid = fst.id.instituteid and sm.id.subjectid = fst.subjectid and fst.registrationid =:registrationid and sm.id.instituteid =:instituteid ) "
                + " and exists (select 1 from DepartmentSubjectTagging dst where dst.id.instituteid=:instituteid and dst.id.subjectid=sm.id.subjectid and dst.id.departmentid=:departmentid )");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("departmentid", departmentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubPerProgSty(String instituteid, String programid, String styno) {
        List list = null;
        short stynumber = Short.valueOf(styno);
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid,sm.subjectcode,sm.subjectdesc "
                + " from SubjectMaster sm  "
                + " where sm.id.instituteid=:instituteid "
                + " and exists( "
                + " select 1 from ProgramSubjectTagging pst "
                + " where pst.id.instituteid=sm.id.instituteid and pst.subjectid= sm.id.subjectid   "
                + " and pst.programid=:programid and pst.stynumber=:styno ) "
                + " order by sm.subjectcode "
                + " ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("styno", stynumber).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSub_Wise_SubSec_Report(String instituteid, String registrationid, String departid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("  select distinct sm.enrollmentno ,sm.name, fst.stynumber,prg.programcode,sec.sectioncode,pws.subsectioncode,sub.subjectcode,sub.subjectdesc   "
                + "  from FacultySubjectTagging fst,StudentMaster sm,FacultyStudentTagging sft,SubjectMaster sub,ProgramMaster prg, SectionMaster sec, ProgramWiseSubsection pws  "
                + "  where fst.id.instituteid=sub.id.instituteid and fst.subjectid = sub.id.subjectid  and fst.id.instituteid=prg.id.instituteid  and fst.programid=prg.id.programid   "
                + " and pws.id.instituteid=fst.id.instituteid and pws.id.academicyear=fst.academicyear and pws.id.programid=fst.programid and pws.id.stynumber=fst.stynumber  "
                + " and  pws.id.sectionid=fst.sectionid and pws.id.subsectionid=fst.subsectionid  "
                + " and fst.id.instituteid=sec.id.instituteid and fst.sectionid = sec.id.sectionid and fst.id.instituteid=sft.id.instituteid and fst.id.fstid=sft.fstid and sft.id.studentid=sm.studentid "
                + "  and fst.registrationid =:registrationid and sm.instituteid =:instituteid and fst.subjectid =:subjectid  ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("subjectid", subjectid).setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getSubjectCode_SubjectWise(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.instituteid,sm.id.subjectid,sm.deactive,sm.patternid,sm.subjectcode,sm.subjectdesc,sm.totalmarks, "
                + "  sm.passingmarks,sm.credits, sm.deactive,sm.shortname from SubjectMaster sm  where   sm.id.instituteid=:instituteid "
                + "  and exists(select 1 from StudentSubjectChoiceMaster sc where sc.id.instituteid=sm.id.instituteid and sc.id.subjectid =sm.id.subjectid and sc.id.registrationid=:registrationid) ");

        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public int checkIfChildExist(final SubjectMasterId subjectmasterid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SubjectMaster submaster = (SubjectMaster) session.get(SubjectMaster.class, subjectmasterid);
                int i1 = Integer.parseInt(session.createFilter(submaster.getStudentsubjectchoicemastersForStudentsubjectchoicemasterFk3(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(submaster.getStudentsubjectchoicemastersForStudentsubjectmasterFk4(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(submaster.getStudentshortattendances(), "select count(*)").list().get(0).toString());
                int i4 = Integer.parseInt(session.createFilter(submaster.getPrerequisitacadwiserequireds(), "select count(*)").list().get(0).toString());
                int i5 = Integer.parseInt(session.createFilter(submaster.getPrfacultysubjectchoiceses(), "select count(*)").list().get(0).toString());
                int i6 = Integer.parseInt(session.createFilter(submaster.getPrerequisitmasterrequireds(), "select count(*)").list().get(0).toString());
                int i7 = Integer.parseInt(session.createFilter(submaster.getProgramschemeacadyearwises(), "select count(*)").list().get(0).toString());
                int i8 = Integer.parseInt(session.createFilter(submaster.getPrerequisitacademicsubjectses(), "select count(*)").list().get(0).toString());
                int i9 = Integer.parseInt(session.createFilter(submaster.getProgramsubjecttaggings(), "select count(*)").list().get(0).toString());
                int i10 = Integer.parseInt(session.createFilter(submaster.getOfferedodsubjecttaggingsForOfferedodsubjecttaggingFk4(), "select count(*)").list().get(0).toString());
                int i11 = Integer.parseInt(session.createFilter(submaster.getOfferedodsubjecttaggingsForOfferedodsubjecttaggingFk3(), "select count(*)").list().get(0).toString());
                int i12 = Integer.parseInt(session.createFilter(submaster.getStudentnrsubjectses(), "select count(*)").list().get(0).toString());
                int i13 = Integer.parseInt(session.createFilter(submaster.getProgramschemes(), "select count(*)").list().get(0).toString());
                int i14 = Integer.parseInt(session.createFilter(submaster.getDepartmentsubjecttaggings(), "select count(*)").list().get(0).toString());
                int i15 = Integer.parseInt(session.createFilter(submaster.getSubjectcomponentdetails(), "select count(*)").list().get(0).toString());
                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15;//+ i16 + i17 + i18 + i19 + i20 + i21 + i22 + i23 + i24 + i25 + i26 + i27 + i28 + i29 + i30 + i31 + i32 + i33 + i34 + i35 + i36 + i37 + i38;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();

    }

    public List<String> doValidate(final SubjectMaster subjectmaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        /*Unique Key Constraint on instituteid and Shortname*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(SubjectMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", subjectmaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("subjectcode", subjectmaster.getSubjectcode()));
                if (mode.equals("update")) {
                    criteria.add(Restrictions.ne("id.subjectid", subjectmaster.getId().getSubjectid()));   //Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("SubjectCode already exist, please enter different SubjectCode!");
        }
        return errors;
    }

//      public Collection<?> findAll(String instituteid) {
//        log.info("Retrieving all SubjectMaster records via Hibernate from the database");
//        return this.find("from SubjectMaster as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
//    }
    public List getGridSubjectcomponent(String subjectid, String instituteid) {
        StringBuilder qry = new StringBuilder();
        qry.append(" select b.subjectcomponentcode as subjectcomponentcode,a.id.subjectcomponentid as subjectcomponentid, a.ltppassingmarks as ltppassingmarks, a.totalccpmarks as totalccpmarks , ");
        qry.append(" a.noofhours as noofhours, a.noofclassinaweek as noofclassinaweek, a.byltp as byltp, a.totalmarks as totalmarks, a.passingmarks as passingmarks, a.totalclasses as totalclasses , a.deactive as deactive ");
        qry.append(" from SubjectComponentDetail a,SubjectComponent b");
        qry.append(" where a.id.subjectid = '" + subjectid + "'");
        qry.append(" and a.id.instituteid = '" + instituteid + "'");
        qry.append(" and a.id.instituteid = b.id.instituteid ");
        qry.append(" and a.id.subjectcomponentid = b.id.subjectcomponentid");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getSubjectCodeForAudit(String instituteid, String registrationid, String programid, String sectionid, String academicyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sm.id.subjectid ,sm.subjectcode,sm.subjectdesc ,sm.credits,dm.department,dm.id.departmentid ");
        sb.append(" from SubjectMaster sm, ProgramSubjectTagging pst,DepartmentMaster dm ");
        sb.append(" where sm.id.instituteid = pst.id.instituteid and sm.id.subjectid = pst.subjectid "
                + " and pst.departmentid = dm.id.departmentid and sm.id.instituteid=:instituteid"
                + " and pst.id.registrationid=:registrationid and coalesce(pst.subjectrunning,'N')='Y'"
                + " and not exists( select pst1.id.instituteid from ProgramSubjectTagging pst1 where pst1.id.instituteid=pst.id.instituteid"
                + " and pst1.id.registrationid=pst.id.registrationid"
                + " and pst1.subjectid=pst.subjectid"
                + " and pst1.programid=:programid"
                + " and pst1.sectionid=:sectionid"
                + " and pst1.academicyear=:academicyear and coalesce(pst1.customauditflag,'N')='N' )"
                + " and exists(select fst.id.fstid from FacultySubjectTagging fst where fst.id.instituteid=pst.id.instituteid and fst.registrationid=pst.id.registrationid and fst.subjectid=pst.subjectid)"
                + " order by sm.subjectcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("academicyear", academicyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getDataForLTPPassingMarks(String registrationId, String instituteId, String subjectTypeId, String departmentId, Short styNum) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pst.subjectid, pst.basketid, sm.subjectcode, sm.subjectdesc, "
                + "sc.subjectcomponentcode, sc.subjectcomponentdesc, psd.ltppassingmarks "
                + "from ProgramSubjectTagging pst, SubjectMaster sm, SubjectComponent sc, "
                + "SubjectComponentDetail scd, ProgramSubjectDetail psd "
                + "where psd.id.instituteid=pst.id.instituteid and psd.id.programsubjectid=pst.id.programsubjectid "
                + "and psd.id.instituteid=sc.id.instituteid and psd.id.subjectcomponentid=sc.id.subjectcomponentid "
                + "and pst.id.registrationid = :registrationid and pst.id.instituteid = :instituteid and pst.id.instituteid=sm.id.instituteid "
                + "and pst.id.instituteid=sc.id.instituteid and pst.id.instituteid=scd.id.instituteid "
                + "and pst.subjectid=sm.id.subjectid and sm.id.subjectid=scd.id.subjectid and pst.subjectid=scd.id.subjectid "
                + "and sc.id.instituteid=scd.id.instituteid and sc.id.subjectcomponentid=scd.id.subjectcomponentid "
                + "and (pst.basketid in (select bm.id.basketid from BasketMaster bm where ");
        if (!subjectTypeId.equals("All")) {
            sb.append(" bm.subjecttypeid = :subjecttypeid ) ");
        } else {
            sb.append(" :subjecttypeid = :subjecttypeid ) ");
        }
        sb.append(" or pst.basketid in (select bmd.id.basketid from BasketMasterDetail bmd where");
        if (!subjectTypeId.equals("All")) {
            sb.append(" bmd.subjecttypeid = :subjecttypeid )) ");
        } else {
            sb.append(" :subjecttypeid = :subjecttypeid )) ");
        }
        sb.append(" and pst.departmentid = :departmentid and pst.stynumber= :stynumber and coalesce(pst.subjectrunning, 'N')='Y' "
                + " group by pst.subjectid, pst.basketid, sm.subjectcode, sm.subjectdesc, sc.subjectcomponentcode, "
                + " sc.subjectcomponentdesc, psd.ltppassingmarks order by sm.subjectcode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("registrationid", registrationId).
                    setParameter("instituteid", instituteId).
                    setParameter("subjecttypeid", subjectTypeId).
                    setParameter("departmentid", departmentId).
                    setParameter("stynumber", styNum).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getCoordinate(String ins_id, String reg_id, String staff_type) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        if (staff_type.equals("C")) {
            sb.append(" Select subjectmas0_.id.instituteid,subjectmas0_.id.subjectid,subjectmas0_.patternid,subjectmas0_.subjectcode,subjectmas0_.subjectdesc,subjectmas0_.totalmarks,subjectmas0_.passingmarks, "
                    + " subjectmas0_.credits,subjectmas0_.deactive,subjectmas0_.shortname from SubjectMaster subjectmas0_ where coalesce(subjectmas0_.deactive, 'N')='N' and (subjectmas0_.id.subjectid in (select sc.id.subjectid "
                    + " from SubjectCoordinator sc  where sc.id.instituteid=:ins_id and sc.id.subjectid=subjectmas0_.id.subjectid and coalesce(sc.deactive, 'N')='N' group by "
                    + " sc.id.subjectid)) order by  subjectmas0_.subjectcode ");//subjectmas0_.subjecttypeid, 
        } else {
            sb.append(" Select subjectmas0_.id.instituteid,subjectmas0_.id.subjectid,subjectmas0_.patternid,subjectmas0_.subjectcode,subjectmas0_.subjectdesc,subjectmas0_.totalmarks,subjectmas0_.passingmarks, "
                    + " subjectmas0_.credits,subjectmas0_.deactive,subjectmas0_.shortname from SubjectMaster subjectmas0_ where coalesce(subjectmas0_.deactive, 'N')='N' and (subjectmas0_.id.subjectid in (select facultysub1_.subjectid "
                    + " from FacultySubjectTagging facultysub1_  where facultysub1_.id.instituteid=:ins_id and facultysub1_.registrationid=:reg_id and coalesce(facultysub1_.deactive, 'N')='N' group by "
                    + " facultysub1_.subjectid)) order by  subjectmas0_.subjectcode ");//subjectmas0_.subjecttypeid, 
        }
        try {
            if (staff_type.equals("C")) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("ins_id", ins_id).list();
            } else {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("ins_id", ins_id).setParameter("reg_id", reg_id).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubjectCodeUsingFacultySubjectTagging(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid, sm.subjectcode, sm.subjectdesc from SubjectMaster sm where coalesce(sm.deactive,'N') = 'N' ");
        sb.append(" and sm.id.subjectid in (select b.subjectid from FacultySubjectTagging b where b.id.instituteid = :instituteid ");
        sb.append(" and b.registrationid = :registrationid and coalesce(b.deactive,'N') = 'N' group by b.subjectid) order by sm.subjectcode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllSubjectCode_SCR(String instituteid, String registrationid, String Academicyear, String Programid, String secId, String basketcode) {
        String qryString = "select sm.id.subjectid , sm.id.instituteid , sm.subjectcode, sm.subjectdesc  from SubjectMaster  sm , ProgramSubjectTagging pst "
                + " where coalesce(sm.deactive,'N') = 'N'"
                + " and sm.id.subjectid = pst.subjectid "
                + " and sm.id.instituteid = pst.id.instituteid "
                + " and pst.id.instituteid = '" + instituteid + "'";
        if (!registrationid.equals("All")) {
            qryString = qryString + " and pst.id.registrationid = '" + registrationid + "'";
        }
        if (!"".equals(Academicyear) && null != Academicyear) {
            if (!Academicyear.equals("All")) {
                qryString = qryString + " and pst.academicyear = '" + Academicyear + "'";
            }
        }
        if (!"".equals(Programid) && null != Programid) {
            if (!Programid.equals("All")) {
                qryString = qryString + " and pst.programid='" + Programid + "'";
            }
        }
        if (!secId.equals("All")) {
            qryString = qryString + " and pst.sectionid='" + secId + "'";
        }
        if (!basketcode.equals("ALL")) {
            qryString = qryString + " and pst.basketid=(select a.id.basketid from BasketMaster a where a.id.instituteid=sm.id.instituteid and a.basketcode in(" + basketcode + ")and a.id.basketid=pst.basketid )";
        }
        qryString = qryString + " group by sm.id.subjectid , sm.id.instituteid , sm.subjectcode, sm.subjectdesc order by sm.subjectcode";
        List list = null;
        try {
            list = getHibernateTemplate().find(qryString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getSubjectChoicesReportDatas(String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, String sem, String deptid, String subjectrunning) {
        List rtnList = null;
        final StringBuilder str = new StringBuilder();
        try {
            str.append(" SELECT distinct Enrollmentno, ");
            str.append("Name, ");
            str.append("j.AcademicYear, ");
            str.append("f.ProgramCode, ");
            str.append("h.BranchCode, ");
            str.append("g.SectionCode, ");
            str.append("b.STYNumber, ");
            str.append("SUBJECTTYPEDESC, ");
            str.append("BasketCode, ");
            str.append("SubjectCode, ");
            str.append("SubjectDesc, ");
            str.append("CHOICE, ");
            str.append("NVL (SUBJECTRUNNING, 'N') ");
            str.append("FROM ");
            str.append("StudentSubjectChoiceMaster a, ");
            str.append("StudentSubjectChoiceDetail b, ");
            str.append("StudentMaster c, ");
            str.append("SubjectMaster d, ");
            str.append(" BasketMaster e, ");
            str.append("ProgramMaster f, ");
            str.append("SectionMaster g, ");
            str.append("BranchMaster h, ");
            str.append("SubjectTypeMaster I, ");
            str.append("ProgramWiseSubSection j, ");
            str.append("SubjectComponent K ");
            if (deptid != null && !"".equals(deptid)) {
                str.append(",DepartmentBranchTagging dbt ");
            }
            str.append(" WHERE  ");
            if (deptid != null && !"".equals(deptid)) {
                str.append("   dbt.INSTITUTEID=a.INSTITUTEID  ");
                str.append(" and  dbt.DEPARTMENTID='" + deptid + "'    ");
                str.append(" and dbt.PROGRAMID=f.ProgramID   ");
                str.append(" and  dbt.BRANCHID=c.BranchID and  ");
            }
            str.append(" a.INSTITUTEID = b.INSTITUTEID ");
            str.append(" and a.INSTITUTEID = '" + instituteid + "' ");
            str.append("AND a.REGISTRATIONID = b.REGISTRATIONID ");
            str.append("AND a.REGISTRATIONID = '" + registrationid + "' ");
            str.append("AND a.STYNUMBER = b.STYNUMBER ");
            str.append(" AND a.STUDENTID = b.STUDENTID ");
            str.append(" AND a.SUBJECTID = b.SUBJECTID ");
            if (!subjectid.equals("All") && !subjectid.equals("")) {
                str.append(" AND a.SUBJECTID = '" + subjectid + "' ");
            }
            str.append(" AND a.STUDENTID = c.STUDENTID ");
            str.append("AND a.InstituteId = d.InstituteID ");
            str.append("AND a.SubjectID = d.SubjectID ");
            str.append("AND a.InstituteId = e.InstituteID ");
            str.append("AND e.BasketID = a.BasketID ");
            str.append("AND c.InstituteId = f.InstituteID ");
            str.append("AND c.ProgramID = f.ProgramID ");
            str.append("AND h.InstituteId = c.InstituteID ");
            str.append("AND h.ProgramID = c.ProgramID ");
            if (!programid.equals("All") && !programid.equals("") && !programid.equals("null")) {
                str.append("AND h.ProgramID = '" + programid + "' ");
            }
            str.append("AND h.BranchID = c.BranchID ");
            str.append("AND c.InstituteId = g.InstituteID ");
            str.append("AND c.SectionID = g.SectionID ");
            str.append("AND e.InstituteID = I.InstituteID ");
            str.append("AND e.SubjectTypeID = I.SubjectTypeID ");
            if (!"".equals(subjecttypeid)) {
                str.append(" and e.basketid in (select distinct bkm.basketid from BasketMaster bkm where bkm.instituteid=c.instituteid and  bkm.basketid=a.basketid and  bkm.programid=c.programid and  bkm.sectionid=g.sectionid and  bkm.stynumber=b.stynumber and bkm.subjecttypeid=i.subjecttypeid and bkm.basketcode in (" + subjecttypeid + "))");
            } else {
                str.append(" and e.basketid in (select distinct bkm.basketid from BasketMaster bkm where bkm.instituteid=c.instituteid and  bkm.basketid=a.basketid and  bkm.programid=c.programid and  bkm.sectionid=g.sectionid and  bkm.stynumber=b.stynumber and bkm.subjecttypeid=i.subjecttypeid ) ");
            }
            str.append("AND c.InstituteId = j.InstituteID ");
            str.append("AND c.ProgramID = j.ProgramID ");
            str.append("AND c.Academicyear = j.Academicyear ");
            if (!academicyear.equals("All") && !academicyear.equals("")) {
                str.append("AND c.Academicyear = '" + academicyear + "' ");
            }
            str.append("AND c.SectionID = j.SectionID ");
            if (!sectiondata.equals("All") && !"".equals(sectiondata)) {
                str.append("AND c.subSectionID in(" + sectiondata + ")");
            }
            str.append("AND b.STYNumber = j.STYNumber ");
            str.append("AND subjectrunning = 'Y' ");
            str.append("AND b.SubSectionID = j.SubSectionID ");
            str.append("AND b.InstituteId = k.InstituteID ");
            str.append("AND b.SUBJECTCOMPONENTId = k.SUBJECTCOMPONENTID");
            str.append(" and exists(select 1 from STUDENTREGISTRATION_INFO sri ");
            str.append(" where sri.INSTITUTEID = a.INSTITUTEID   ");
            str.append("AND sri.REGISTRATIONID = a.REGISTRATIONID  ");
            str.append("AND a.STYNUMBER = sri.STYNUMBER ");
            str.append("AND a.STUDENTID = sri.STUDENTID ");
            str.append("and SRI.PROGRAMID=C.PROGRAMID ");
            str.append("and SRI.SECTIONID=C.SECTIONID) ");
            str.append(" order by Enrollmentno, CHOICE");

            rtnList = (List) getHibernateTemplate().execute(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                    return sn.createSQLQuery(str.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnList;
    }

    public List getSubjectChoiceNotFilledByStudentReport(String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, String sem, String deptid) {
        List rtnList = null;
        final StringBuilder str = new StringBuilder();
        try {

            str.append(" select (select concat(coalesce(enrollmentno,''),concat(' / ',name)) from studentmaster where studentid=a.studentid) Rollno,  ");
            str.append(" (select branchcode from branchmaster where branchid=b.branchid) BranchCode, ");
            str.append(" (select sectioncode from sectionmaster where sectionid=b.sectionid)SectionCode, ");
            str.append(" b.Stynumber, b.academicyear ");
            str.append(" from studentregistration a, studentregistration_info b ");
            str.append(" where a.registrationid='" + registrationid + "'  ");
            str.append(" and a.regallow='Y' and a.instituteid=b.instituteid  ");
            str.append(" and b.STYNUMBER=a.STYNUMBER ");
            str.append(" and a.instituteid='" + instituteid + "' and a.registrationid=B.REGISTRATIONID and a.studentid=b.studentid ");
            str.append(" and B.SECTIONID in(select sectionid from programsubjecttagging p where p.registrationid='" + registrationid + "' and p.subjectrunning='Y' ");
            str.append(" and basketid in(select basketid from basketmaster where sectionid=b.sectionid and stynumber=b.stynumber )) ");
            str.append(" and not exists (select * from studentsubjectchoicemaster where registrationid=a.registrationid and studentid=a.studentid) order by BranchCode,Rollno");
            rtnList = (List) getHibernateTemplate().execute(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                    return sn.createSQLQuery(str.toString()).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnList;
    }

    public List getSubjectCodeDeactive(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid, sm.subjectcode, sm.subjectdesc from SubjectMaster sm where sm.id.instituteid = :instituteid and coalesce(sm.deactive,'N')='N' order by sm.subjectdesc asc ");
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

    public List checkSubjectComponent(String instituteid, String subjectid, String subcomid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select 1"
                + " from SubjectComponentDetail scd"
                + " where scd.id.instituteid = :instituteid"
                + " and scd.id.subjectid = :subjectid "
                + " and scd.id.subjectcomponentid = :subcomid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("subcomid", subcomid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSubjectsForSupplementary(String instituteid, String registrationid, String parentregistrationid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sm.id.instituteid,sm.id.subjectid,sm.subjectcode,sm.subjectdesc,(select concat(srd.coursecreditpoint,concat('@',srd.stynumber))  "
                + " from StudentResultDetail srd  "
                + " where srd.id.instituteid =sm.id.instituteid  "
                + " and srd.id.registrationid = :parentregistrationid  "
                + " and srd.id.studentid=:studentid "
                + " and sm.id.subjectid=srd.subjectid)as credit,"
                + " ( select count(drm.reasontype) from Ex_GradingDebarStudentDetail gdst,DebarreasonMaster drm"
                + " where gdst.id.instituteid=:instituteid "
                + " and gdst.id.registrationid=:parentregistrationid "
                + " and gdst.id.studentid=:studentid "
                + " and gdst.id.subjectid=sm.id.subjectid"
                + " and gdst.debarreasonid=drm.id.debarreasonid"
                + " and drm.id.instituteid=gdst.id.instituteid"
                + " and coalesce(drm.supplimentoryregfeerequired,'N')='N' and gdst.eventsubevent is null) as reasontype,  "
                + " (select dm.department from DepartmentSubjectTagging dst,DepartmentMaster dm where dst.id.instituteid=:instituteid and dst.id.subjectid=sm.id.subjectid"
                + " and dm.departmentid=dst.id.departmentid and rownum = 1) as department"
                + " from SubjectMaster sm where sm.id.instituteid= :instituteid    "
                + " and exists(   "
                + " select srd.subjectid  "
                + " from StudentResultDetail srd  "
                + " where srd.id.instituteid =sm.id.instituteid  "
                + " and srd.id.registrationid = :parentregistrationid  "
                + " and srd.id.studentid=:studentid "
                + " and sm.id.subjectid=srd.subjectid and srd.fail='Y' )"
                + " and not exists( select sscm.id.subjectid from StudentSubjectChoiceMaster sscm where sscm.id.instituteid=:instituteid"
                + " and sscm.id.registrationid=:registrationid and sscm.id.subjectid=sm.id.subjectid and sscm.id.studentid=:studentid)  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("parentregistrationid", parentregistrationid).
                    setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getBasketStyTypeStyno(String instituteid, String registrationid, String studentid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct fst.basketid,fst.stytypeid,fst.stynumber,fst.subsectionid,fst.subjecttype,fst.subjecttypeid from FacultyStudentTagging sft,FacultySubjectTagging fst    "
                + " where sft.id.instituteid = :instituteid "
                + " and sft.id.studentid = :studentid  and fst.registrationid = :registrationid "
                + " and fst.id.instituteid = sft.id.instituteid "
                + " and fst.registrationid = sft.registrationid "
                + " and sft.facultysubjecttagging.subjectid = fst.subjectid  "
                + " and fst.id.fstid = sft.fstid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).list();
//                    setParameter("subjectid", subjectid)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getBasketidSubjecttypeid(String instituteid, String subjectid, String programid, String sectionid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct psaw.basketid,psaw.subjecttype, psaw.subjecttypeid  from ProgramSchemeAcadyearWise psaw "
                + "where psaw.id.instituteid=:instituteid and psaw.subjectid=:subjectid"
                + " and psaw.programid=:programid and psaw.sectionid=:sectionid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getSubjectComponentDetail(String instituteid, String subjectid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select scd.id.subjectcomponentid from SubjectComponentDetail scd where scd.id.instituteid=:instituteid and scd.id.subjectid = :subjectid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getReqSubject(String instituteid) {
        List list = null;
        String qry = "select sm.id.subjectid, sm.subjectcode, sm.subjectdesc "
                + " ,( select dst.id.departmentid from DepartmentSubjectTagging dst where dst.id.instituteid=sm.id.instituteid and dst.id.subjectid = sm.id.subjectid and rownum = 1) as departmentid"
                + " from SubjectMaster sm  where sm.id.instituteid = '" + instituteid + "'"
                + " and not exists( select sc.id.subjectid from SubjectCoordinator sc where sc.id.instituteid=sm.id.instituteid and sc.id.subjectid=sm.id.subjectid and coalesce(sc.deactive,'N')='N')";

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public List getSubjectsForSummer(String instituteid, String registrationid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sm.id.instituteid,sm.id.subjectid,sm.subjectcode,sm.subjectdesc,(select concat(srd.coursecreditpoint,concat('@',srd.id.stynumber))  "
                + " from StudentResult srd  "
                + " where srd.id.instituteid =sm.id.instituteid  "
                + " and srd.id.studentid=:studentid "
                + " and sm.id.subjectid=srd.id.subjectid)as credit,"
                + " ( select count(drm.reasontype) from Ex_GradingDebarStudentDetail gdst,DebarreasonMaster drm"
                + " where gdst.id.instituteid=:instituteid "
                + " and gdst.id.registrationid=:registrationid "
                + " and gdst.id.studentid=:studentid "
                + " and gdst.id.subjectid=sm.id.subjectid"
                + " and gdst.debarreasonid=drm.id.debarreasonid"
                + " and drm.id.instituteid=gdst.id.instituteid"
                + " and drm.reasontype='M' and gdst.eventsubevent is null) as reasontype,  "
                + " (select min(dm.department) from OfferedODSubjectTagging ost1,DepartmentMaster dm where ost1.id.instituteid=:instituteid and coalesce(ost1.odsubjectid,ost1.currentsubjectid)=sm.id.subjectid"
                + " and dm.departmentid=ost1.departmentid) as department,"
                + " (select concat(concat(ost.currentsubjectid,concat('@',sub.subjectcode)),concat('@',sub.subjectdesc)) from SubjectMaster sub,OfferedODSubjectTagging ost where ost.id.instituteid =:instituteid "
                + " and coalesce(ost.odsubjectid,ost.currentsubjectid) = sm.id.subjectid "
                + " and ost.id.registrationid =:registrationid "
                + " and coalesce(ost.deactive,'N') ='N'"
                + " and sub.id.subjectid=ost.currentsubjectid"
                + " and sub.id.instituteid=ost.id.instituteid ) as newsubjectid,"
                + " (select ost1.departmentid from OfferedODSubjectTagging ost1 where ost1.id.instituteid=:instituteid and coalesce(ost1.odsubjectid,ost1.currentsubjectid)=sm.id.subjectid and ost1.id.registrationid=:registrationid) as departmentid"
                + " from SubjectMaster sm where sm.id.instituteid= :instituteid "
                + " and exists(   "
                + " select srd.subjectid  "
                + " from StudentResultDetail srd  "
                + " where srd.id.instituteid =sm.id.instituteid"
                + " and srd.id.studentid=:studentid "
                + " and sm.id.subjectid=srd.subjectid and srd.fail='Y' )"
                + " and not exists( select sscm.id.subjectid from OfferedODSubjectTagging ost1, StudentSubjectChoiceMaster sscm where sscm.id.instituteid=:instituteid"
                + " and sscm.id.registrationid=:registrationid and sscm.id.studentid=:studentid"
                + " and coalesce(ost1.odsubjectid,ost1.currentsubjectid)=sm.id.subjectid"
                + " and sscm.id.subjectid=ost1.currentsubjectid and  ost1.id.registrationid =:registrationid )"
                + " and exists( select ost.currentsubjectid from OfferedODSubjectTagging ost where ost.id.instituteid =:instituteid "
                + " and coalesce(ost.odsubjectid,ost.currentsubjectid)= sm.id.subjectid "
                + " and ost.id.registrationid =:registrationid "
                + " and coalesce(ost.deactive,'N') ='N')");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    @Override
    public List getSubjectCodeAcademicyearWise(String instituteid, String registrationid, String academicyear, String departmentid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct pst.subjectid,sm.subjectcode,sm.subjectdesc from ProgramSubjectTagging pst,SubjectMaster sm "
                + "where coalesce(pst.subjectrunning,'N')='Y' "
                + "and pst.id.instituteid=:instituteid and pst.id.registrationid=:registrationid "
                + "and pst.academicyear=:academicyear and sm.id.instituteid=pst.id.instituteid "
                + "and sm.id.subjectid=pst.subjectid "
                + "and pst.departmentid=:departmentid "
//                + "and not exists( select 1 from FacultySubjectTagging fst  "
//                + "where fst.id.instituteid=pst.id.instituteid and fst.registrationid=pst.id.registrationid "
//                + "and fst.subjectid=pst.subjectid) "
                + "order by sm.subjectcode");
        list = getHibernateSession().createQuery(sb.toString()).
                setParameter("instituteid", instituteid).
                setParameter("registrationid", registrationid).
                setParameter("departmentid", departmentid).
                setParameter("academicyear", academicyear).list();
        return list;
    }

    @Override
    public List getComponentCodeForFST(String instituteid, String registrationid, String academicyear, String subjectid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct psd.id.subjectcomponentid,sc.subjectcomponentcode,sc.subjectcomponentdesc "
                + " from ProgramSubjectDetail psd, SubjectComponent sc "
                + " where psd.id.instituteid=sc.id.instituteid and sc.id.subjectcomponentid=psd.id.subjectcomponentid and psd.id.instituteid=:instituteid  "
                + " and exists(select pst.id.instituteid from ProgramSubjectTagging pst where pst.id.instituteid=psd.id.instituteid and pst.id.registrationid=:registrationid "
                + " and pst.subjectid=:subjectid and pst.academicyear=:academicyear and coalesce(pst.subjectrunning,'N')='Y' and  pst.id.programsubjectid=psd.id.programsubjectid) "
                + " order by psd.id.subjectcomponentid");
        list = getHibernateSession().createQuery(sb.toString()).
                setParameter("instituteid", instituteid).
                setParameter("registrationid", registrationid).
                setParameter("subjectid", subjectid).
                setParameter("academicyear", academicyear).list();
        return list;
    }

}
