package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramMasterId;
import java.io.Serializable;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ankur.goyal
 */
public class ProgramMasterDAO extends HibernateDAO implements ProgramMasterIDAO {

    private static final Log log = LogFactory.getLog(ProgramMasterDAO.class);
    private Session session = null;

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramMaster records via Hibernate from the database");
        return this.find("from ProgramMaster as tname order by seqid asc");
    }

    public Collection<?> findAllInstituteWise(String instituteid) {
        log.info("Retrieving all ProgramMaster records via Hibernate from the database");
        return this.find("from ProgramMaster as tname where tname.id.instituteid='" + instituteid + "'");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramMaster.class, id);
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all ProgramMaster records via Hibernate from the database");
        return this.find("from ProgramMaster as tname where tname.id.instituteid = ? order by tname.programcode", new Object[]{instituteid});
    }

    public Collection<?> findAllProgramMasterData(final String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramMaster.class, "master").add(Expression.eq("master.id.instituteid", instituteid));

                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.programid").as("programid")).add(Projections.property("master.programcode").as("programcode")).add(Projections.property("master.programdesc").as("programdesc")));
                criteria.add(Restrictions.sqlRestriction("1=1 order by to_number({alias}.seqid) asc"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getBranchCode(final String instituteid, final String progid, final String acad_year) {
        List list = null;
        String strqry = "select bm.id.instituteid,bm.id.programid,bm.id.branchid, bm.registrationcardlabel "
                + " from BranchMaster bm where bm.id.instituteid='" + instituteid + "' and bm.id.programid in (" + progid + ") ";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getBranchCodeForRegNoSetup(final String instituteid, final String progid, final String acad_year) {
        List list = null;
        String strqry = "select bm.id.instituteid,bm.id.programid,bm.id.branchid, bm.registrationcardlabel,p.id.programtypeid  "
                + " from BranchMaster bm, ProgramTypeProgramTagging p where  bm.id.instituteid=p.id.instituteid and bm.id.programid=p.id.programid "
                + "and bm.id.instituteid='" + instituteid + "' and bm.id.programid in (" + progid + ") ";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getBranchCodeForDepartment(final String instituteid, final String progid, final String acad_year) {
        List list = null;
        String strqry = "select bm.id.instituteid,bm.id.programid,bm.id.branchid, bm.registrationcardlabel,p.id.programtypeid  "
                + " from BranchMaster bm, ProgramTypeProgramTagging p where  bm.id.instituteid=p.id.instituteid and bm.id.programid=p.id.programid "
                + "and bm.id.instituteid='" + instituteid + "' and bm.id.programid='" + progid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getprogramMasterList(String instituteid) {
        List list = null;
        String strqry = " select pm.id.instituteid,pm.id.programid, pm.programcode,pm.programdesc,pm.deactive,pt.programtype,pt.id.programtypeid  from ProgramMaster pm,ProgramTypeProgramTagging ptpt,ProgramType pt "
                + " where pm.id.instituteid=ptpt.id.instituteid and pt.id.instituteid=ptpt.id.instituteid and pm.id.programid=ptpt.id.programid and pt.id.programtypeid=ptpt.id.programtypeid  and pm.id.instituteid='" + instituteid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getprogramMasterEdit(String instituteid, String programid, String programtypeid) {
        List list = null;
        String strqry = " select pm.id.instituteid,pm.id.programid, pm.programcode,pm.programdesc,pm.deactive,pt.programtype,pt.id.programtypeid,pm.seqid,pm.certificatecode,pm.enrollmentcode  from ProgramMaster pm,ProgramTypeProgramTagging ptpt,ProgramType pt "
                + " where pm.id.instituteid=ptpt.id.instituteid and pt.id.instituteid=ptpt.id.instituteid and pm.id.programid=ptpt.id.programid and pt.id.programtypeid=ptpt.id.programtypeid "
                + " and pm.id.instituteid='" + instituteid + "' and pm.id.programid='" + programid + "' and pt.id.programtypeid='" + programtypeid + "'";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int checkIfChildExist(final ProgramMasterId id) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProgramMaster programMaster = (ProgramMaster) session.get(ProgramMaster.class, id);
                int i1 = Integer.parseInt((session.createFilter(programMaster.getBasketmasters(), "select count(*)").list().get(0)).toString());
                int i2 = Integer.parseInt((session.createFilter(programMaster.getBasketmasterdetails(), "select count(*)").list().get(0)).toString());
                int i3 = Integer.parseInt((session.createFilter(programMaster.getCategoryprogramdocumenttaggings(), "select count(*)").list().get(0)).toString());
                int i4 = Integer.parseInt((session.createFilter(programMaster.getCommitteeactiondetails(), "select count(*)").list().get(0)).toString());
                int i15 = Integer.parseInt((session.createFilter(programMaster.getExamgrademasterdetails(), "select count(*)").list().get(0)).toString());
                int i21 = Integer.parseInt((session.createFilter(programMaster.getMentorMasters(), "select count(*)").list().get(0)).toString());
                int i22 = Integer.parseInt((session.createFilter(programMaster.getPrerequisitacademicyearwises(), "select count(*)").list().get(0)).toString());
                int i23 = Integer.parseInt((session.createFilter(programMaster.getPrerequisitforregistrations(), "select count(*)").list().get(0)).toString());
                int i24 = Integer.parseInt((session.createFilter(programMaster.getPrerequisitmasters(), "select count(*)").list().get(0)).toString());
                int i25 = Integer.parseInt((session.createFilter(programMaster.getProgramschemes(), "select count(*)").list().get(0)).toString());
                int i26 = Integer.parseInt((session.createFilter(programMaster.getProgramschemeacadyearwises(), "select count(*)").list().get(0)).toString());
                int i27 = Integer.parseInt((session.createFilter(programMaster.getProgramsubjecttaggings(), "select count(*)").list().get(0)).toString());
                int i28 = Integer.parseInt((session.createFilter(programMaster.getProgramwisesubsections(), "select count(*)").list().get(0)).toString());
                int i29 = Integer.parseInt((session.createFilter(programMaster.getPrsubjecttypewiseapprovals(), "select count(*)").list().get(0)).toString());
                int i31 = Integer.parseInt((session.createFilter(programMaster.getStudentcertificatesetupdetails(), "select count(*)").list().get(0)).toString());
                int i32 = Integer.parseInt((session.createFilter(programMaster.getStudentcertificateslnos(), "select count(*)").list().get(0)).toString());
                int i33 = Integer.parseInt((session.createFilter(programMaster.getStudentnrsubjectses(), "select count(*)").list().get(0)).toString());
                int i34 = Integer.parseInt((session.createFilter(programMaster.getStudentpreviousattendences(), "select count(*)").list().get(0)).toString());
                int i35 = Integer.parseInt((session.createFilter(programMaster.getBranchmasters(), "select count(*)").list().get(0)).toString());
                return i1 + i2 + i3 + i4 + i15 + i21 + i22 + i23 + i24 + i25 + i26 + i27 + i28 + i29 + i31 + i32 + i33 + i34 + i35;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final ProgramMaster programMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ProgramMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", programMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("programcode", programMaster.getProgramcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.programid", programMaster.getId().getProgramid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Program Code ");
        }

        return errors;
    }

    public List getProgramDataForEnrollGen(String instituteid, String academicyear) {
        List list = null;
        String strqry = " select distinct id.programid, programcode, programdesc from ProgramMaster "
                + " where id.programid in (select distinct programid from StudentMaster where "
                + " ( activestatus is null or activestatus='A') and instituteid = '" + instituteid + "' "
                + " and acadyear='" + academicyear + "')  and instituteid ='" + instituteid + "' order by programcode ";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getProgramCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pm.programcode,pm.programdesc,pm.id.programid,pm.id.instituteid from ProgramMaster pm "
                + " where pm.id.instituteid = :instituteid and coalesce(pm.deactive,'N')='N' order by pm.seqid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSemesterCode(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct ldg.id.registrationid, rm.registrationcode from LoadDistributionGrant ldg, RegistrationMaster rm where rm.id.registrationid = ldg.id.registrationid "
                + " and rm.id.instituteid = ldg.id.instituteid "
                + " and rm.id.instituteid=:instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllRunningSubjectFromProgramSchemeAcad11(final String instituteid, final String programid, final List stynumber, final List sectionid, final List academicyear) {
        List list = null;
        Session session = null;
        StringBuilder qryString = new StringBuilder();
        qryString.append(" select pm.programcode,sma.sectioncode,ps.stynumber,sm.subjectcode,sm.subjectdesc,ps.credits,ps.electiveid,ps.id.programschemeacadwiseid, stm.subjecttype, stm.subjecttypedesc,ps.electiveid,bm.basketcode,dm.department,sma.id.sectionid,ps.academicyear "
                + " from ProgramSchemeAcadyearWise ps,SubjectMaster sm, ");
        qryString.append(" DepartmentSubjectTagging dst,ProgramMaster pm,SectionMaster sma, BasketMaster bm, SubjectTypeMaster stm,DepartmentMaster dm ");
        qryString.append(" where ps.id.instituteid=sm.id.instituteid ");
        qryString.append(" and ps.subjectid=sm.id.subjectid ");
        qryString.append(" and ps.id.instituteid=dst.id.instituteid ");
        qryString.append(" and ps.subjectid=dst.id.subjectid ");
        qryString.append("and ps.departmentid=dst.id.departmentid ");
        qryString.append("and dm.departmentid=dst.id.departmentid ");
        qryString.append("and  ps.id.instituteid=pm.id.instituteid ");
        qryString.append("and ps.programid=pm.id.programid ");
        qryString.append("and  ps.id.instituteid=sma.id.instituteid ");
        qryString.append("and ps.sectionid=sma.id.sectionid ");
        qryString.append("and ps.id.instituteid = :instituteid ");
        qryString.append("and ps.programid = :programid ");
        qryString.append("and ps.stynumber in(:stynumber) ");
        qryString.append("and ps.sectionid  in(:sectionid) ");
        qryString.append("and ps.academicyear in(:academicyear) ");
        qryString.append("and bm.id.instituteid= ps.id.instituteid ");
        qryString.append("and ps.basketid=bm.id.basketid ");
        qryString.append("and bm.subjecttypeid=stm.id.subjecttypeid ");
        qryString.append("and stm.id.instituteid=ps.id.instituteid ");
        qryString.append("order by ps.academicyear desc,pm.programcode,sma.sectioncode,ps.stynumber,sm.subjectcode,stm.subjecttype");
        try {
            session = (Session) getSession();
            list = session.createQuery(qryString.toString()).
                    setParameter("instituteid", instituteid).setParameter("programid", programid).setParameterList("stynumber", stynumber).
                    setParameterList("sectionid", sectionid).setParameterList("academicyear", academicyear).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            qryString = null;
        }
        return list;
    }

    public List getAllProgramCodes(final String instituteid, final String programtypeid) {
        String qrystring = "select pm.id.programid, pm.programcode, pm.programdesc"
                + " from ProgramMaster pm"
                + " where pm.id.instituteid='" + instituteid + "'"
                + " and coalesce(pm.deactive,'N')='N'"
                + " and exists"
                + "(select 1"
                + " from ProgramTypeProgramTagging ptpt"
                + " where ptpt.id.instituteid = pm.id.instituteid"
                + " and ptpt.id.programid= pm.id.programid"
                + " and ptpt.id.programtypeid = '" + programtypeid + "'"
                + " and coalesce(ptpt.deactive,'N')='N' )"
                + " order by pm.seqid ";

        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List getAllRunningSubjectFromProgramSchemeAcad11_LTP(final String instituteid, final String programid, final String stynumber, final String sectionid, final String academicyear, final String subjectcode) {
        List list = null;
        Session session = null;
        StringBuilder qryString = new StringBuilder();

        qryString.append(" select distinct pm.programcode,sma.sectioncode,ps.stynumber,sm.subjectcode,sm.subjectdesc,ps.credits,ps.electiveid,ps.id.programschemeacadwiseid, stm.subjecttype, stm.subjecttypedesc,ps.electiveid, psayd.totalccpmarks, "
                + "sc.subjectcomponentcode, bm.basketcode,dm.departmentcode from ProgramSchemeAcadyearWise ps,SubjectMaster sm, ProgramSchemeAcadYearDetail psayd, SubjectComponent sc, ");
        qryString.append(" DepartmentSubjectTagging dst,ProgramMaster pm,SectionMaster sma, BasketMaster bm, SubjectTypeMaster stm ,DepartmentMaster dm");
        qryString.append(" where ps.id.instituteid=sm.id.instituteid ");
        qryString.append(" and ps.subjectid=sm.id.subjectid ");
        qryString.append(" and dm.departmentid=dst.id.departmentid ");

        //  qryString.append(" and dm.instituteid=dst.id.instituteid ");
        qryString.append(" and ps.id.instituteid=dst.id.instituteid ");
        qryString.append(" and sc.id.instituteid=dst.id.instituteid ");
        qryString.append(" and sc.id.instituteid=psayd.id.instituteid ");
        qryString.append(" and sc.id.subjectcomponentid=psayd.id.subjectcomponentid ");
        qryString.append(" and ps.id.programschemeacadwiseid=psayd.id.programschemeacadwiseid ");
        qryString.append(" and ps.id.instituteid=psayd.id.instituteid ");
        qryString.append(" and ps.subjectid=dst.id.subjectid ");
        qryString.append("and ps.departmentid=dst.id.departmentid ");
        qryString.append("and  ps.id.instituteid=pm.id.instituteid ");
        qryString.append("and ps.programid=pm.id.programid ");
        qryString.append("and  ps.id.instituteid=sma.id.instituteid ");
        qryString.append("and ps.sectionid=sma.id.sectionid ");
        qryString.append("and ps.id.instituteid = :instituteid ");
        if (!("All".equals(programid))) {
            qryString.append("and ps.programid =:programid ");
        }
        if (!("All".equals(stynumber))) {
            qryString.append("and ps.stynumber = :stynumber ");
        }
        qryString.append("and ps.sectionid =:sectionid  and sm.subjectcode=:subjectcode ");
        qryString.append("and ps.academicyear=:academicyear ");
        //qryString.append("and ps.electiveid is not null ");
        qryString.append("and bm.id.instituteid= ps.id.instituteid ");
        qryString.append("and ps.basketid=bm.id.basketid ");
        qryString.append("and bm.subjecttypeid=stm.id.subjecttypeid ");
        qryString.append("and stm.id.instituteid=ps.id.instituteid ");
        qryString.append("order by pm.programcode,sma.sectioncode,ps.stynumber,stm.subjecttype");

        try {
            session = (Session) getSession();
            if (!("All".equals(stynumber))) {
                byte styno = Byte.valueOf(stynumber);
                list = session.createQuery(qryString.toString()).
                        setParameter("instituteid", instituteid).setParameter("programid", programid).setParameter("stynumber", styno).
                        setParameter("sectionid", sectionid).setParameter("academicyear", academicyear).setParameter("subjectcode", subjectcode).list();
            } else {
                list = session.createQuery(qryString.toString()).
                        setParameter("instituteid", instituteid).setParameter("programid", programid).
                        setParameter("sectionid", sectionid).setParameter("academicyear", academicyear).setParameter("subjectcode", subjectcode).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            qryString = null;
        }
        return list;
    }

    public List getPST_Programcodes(String instituteid, String Regid, String academicyear) {
        List list = null;
        String strqry = " select pm.id.programid, pm.programcode, pm.programdesc  "
                + "from ProgramMaster pm "
                + "where  pm.id.instituteid='" + instituteid + "' and coalesce(pm.deactive,'N')='N' "
                + " and exists (select 1 from  ProgramSubjectTagging ps where pm.id.instituteid = ps.id.instituteid";
        if (!Regid.equals("All")) {
            strqry = strqry + " and ps.id.registrationid ='" + Regid + "'";
        }
        if (!academicyear.equals("All")) {
            strqry = strqry + " and ps.academicyear = '" + academicyear + "' ";
        }
        strqry = strqry + " and pm.id.programid=ps.programid ) group by pm.id.programid, pm.programcode, pm.programdesc "
                + " order by pm.programcode";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getProgramCode(String instituteid, String regid, String acad_year) {
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct pm.programcode ,pm.id.programid ");
        qry.append(" from ProgramMaster pm ");
        qry.append(" where  pm.id.programid in (select distinct sr.programid ");
        qry.append(" from StudentRegistration_info sr where sr.id.instituteid='" + instituteid + "'");
        qry.append(" and sr.id.registrationid= '" + regid + "' and sr.academicyear='" + acad_year + "' ) order by pm.programcode ");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    public List getBranchCode(String instituteid, String regid, String acad_year, String programid) {
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct bm.branchcode ,bm.id.branchid ");
        qry.append(" from BranchMaster bm");
        qry.append(" where bm.id.branchid in (select distinct sr.branchid ");
        qry.append(" from StudentRegistration_info sr where sr.id.instituteid='" + instituteid + "'");
        qry.append(" and sr.id.registrationid= '" + regid + "' and sr.academicyear='" + acad_year + "' and sr.programid='" + programid + "')  order by bm.branchcode ");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    public List getStynumber(String instituteid, String regid, String acad_year, String programid) {
        StringBuilder qry = new StringBuilder();
        qry.append("select distinct sr.stynumber  ");
        qry.append(" from StudentRegistration_info sr where sr.id.instituteid='" + instituteid + "'");
        qry.append(" and sr.id.registrationid= '" + regid + "' and sr.academicyear='" + acad_year + "' and sr.programid='" + programid + "' order by sr.stynumber ");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    public List getProgramForSubjectFinalization(String instituteid, String regid, String acadyear) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct pm.id.programid,pm.programcode,pm.programdesc from ProgramMaster pm where coalesce (pm.deactive, 'N') = 'N' and pm.id.instituteid = :instituteid"
                + " and exists (select 1 from StudentRegistration_info sri where sri.programid=pm.id.programid and sri.id.instituteid=:instituteid"
                + " and sri.academicyear=:acadyear and sri.id.registrationid=:regid )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getProgramForAcadWiseRegistration(String instituteid, String regid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select "
                + " distinct pm.id.programid,pm.programcode from ProgramMaster pm where coalesce (pm.deactive, 'N') = 'N' and pm.id.instituteid = :instituteid"
                + " and exists (select 1 from StudentRegistration_info sri where sri.programid=pm.id.programid and sri.id.instituteid=:instituteid"
                + " and sri.id.registrationid=:regid )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getProgramForSubjectFst(String instituteid, String regid, String acadyear) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct programid,programmaster.programcode,programmaster.programdesc from ProgramSubjectTagging where academicyear=:acadyear "
                + " and coalesce(deactive,'N')='N' and id.registrationid =:regid  and id.instituteid=:instituteid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("regid", regid).setParameter("acadyear", acadyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    @Override
    public List getProgramCode(String instituteid, String registrationid, String subjectid, String acad_year) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select programmas0_.id.instituteid,programmas0_.id.programid,programmas0_.programcode,programmas0_.programdesc,programmas0_.seqid,programmas0_.enrollmentcode,programmas0_.certificatecode, "
                + " programmas0_.deactive,programmas0_.nooftimesmercy,programmas0_.fromrange,programmas0_.torange   from  ProgramMaster programmas0_ where programmas0_.id.instituteid=:instituteid "
                + " and coalesce(programmas0_.deactive, 'N')='N'    and ( exists (select studentmas1_.programid  from StudentMaster studentmas1_,StudentSubjectChoiceMaster studentsub2_  where studentmas1_.instituteid=programmas0_.id.instituteid "
                + " and studentmas1_.programid=programmas0_.id.programid and studentmas1_.instituteid=studentsub2_.id.instituteid and studentmas1_.studentid=studentsub2_.id.studentid and studentsub2_.id.registrationid=:registrationid");
        if (!subjectid.equalsIgnoreCase("All")) {
            sb.append(" and studentsub2_.id.subjectid= :subjectid");
        }
        if (!acad_year.equalsIgnoreCase("All")) {
            sb.append(" and studentmas1_.acadyear= :acad_year");
        }
        sb.append("  and :subjectid = :subjectid and :acad_year = :acad_year and studentsub2_.id.instituteid=:instituteid group by studentmas1_.programid)) order by  programmas0_.programcode ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("subjectid", subjectid).setParameter("acad_year", acad_year).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getProgramCode_Pst(String ins_id, String acar_year, String reg_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select  prg.id.instituteid,prg.id.programid,prg.programcode,prg.programdesc ,prg.seqid,prg.enrollmentcode,prg.certificatecode,"
                + " prg.deactive,prg.nooftimesmercy,prg.fromrange,prg.torange  from ProgramMaster prg  where   prg.id.instituteid=:ins_id "
                + " and (prg.id.programid in (select prgsubtag.programid  from ProgramSubjectTagging prgsubtag  where  prgsubtag.id.instituteid=:ins_id "
                + " and prgsubtag.academicyear=:acar_year and prgsubtag.id.registrationid=:reg_id and coalesce(prgsubtag.deactive, 'N')='N' ))  order by  prg.programcode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("ins_id", ins_id).
                    setParameter("reg_id", reg_id).
                    setParameter("acar_year", acar_year).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAllProgramWithBranchCode(final String instituteid, final String programtypeid) {
        String qrystring = " select pm.id.programid,bm.id.branchid , pm.programcode,bm.branchdesc  from ProgramMaster pm, BranchMaster bm, ProgramTypeProgramTagging ptpt where ptpt.id.programtypeid='" + programtypeid + "'"
                + " and ptpt.id.programid=pm.id.programid and ptpt.id.instituteid='" + instituteid + "'"
                + " and pm.id.instituteid=ptpt.id.instituteid and bm.id.instituteid=ptpt.id.instituteid "
                + " and bm.id.programid=pm.id.programid "
                + " order by pm.programcode,bm.branchcode ";

        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

}
