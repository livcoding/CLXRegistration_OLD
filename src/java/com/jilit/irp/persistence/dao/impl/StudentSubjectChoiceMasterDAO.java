package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentSubjectChoiceMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingDetail;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.ProgramMinMaxLimit;
import com.jilit.irp.persistence.dto.ProgramMinMaxLimitId;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.StudentNRSubjects;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Transaction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ashutosh1.kumar
 */
public class StudentSubjectChoiceMasterDAO extends HibernateDAO implements StudentSubjectChoiceMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentSubjectChoiceMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentSubjectChoiceMaster records via Hibernate from the database");
        return this.find("from StudentSubjectChoiceMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentSubjectChoiceMaster.class, id);
    }
    CallableStatement cstmt = null;
    static Connection con = null;

    public String saveStudentSubjectChoiceData(final List<StudentSubjectChoiceMaster> choiceMasterList, final List<StudentNRSubjects> nrSubjectsList, final List<PRStudentSubjectChoiceCount> subjectChoiceCountList, boolean studentlogin) {
        String returnMsg = "Success";
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionF-actory().openSession();
            tx = session.beginTransaction();
            System.err.println("choiceMasterList " + choiceMasterList.size());
            for (int m = 0; m < choiceMasterList.size(); m++) {
                StudentSubjectChoiceMaster choiceMaster = choiceMasterList.get(m);
                System.err.println("Subjectid : - " + choiceMaster.getId().getSubjectid());
                if (m == 0) {
                    List l1 = (List) session.createQuery("from StudentSubjectChoiceDetail where id.instituteid = '" + choiceMaster.getId().getInstituteid() + "' and id.registrationid = '" + choiceMaster.getId().getRegistrationid() + "' and id.studentid = '" + choiceMaster.getId().getStudentid() + "'").list();
                    for (int i = 0; i < l1.size(); i++) {
                        session.delete(l1.get(i));
                    }
                    List l = (List) session.createQuery("from StudentSubjectChoiceMaster where id.instituteid = '" + choiceMaster.getId().getInstituteid() + "' and id.registrationid = '" + choiceMaster.getId().getRegistrationid() + "' and id.studentid = '" + choiceMaster.getId().getStudentid() + "'").list();
                    for (int i = 0; i < l.size(); i++) {
                        session.delete(l.get(i));
                    }
                    session.flush();
                    if (!studentlogin && !l.isEmpty()) {
                        StudentRegistration registration = ((StudentSubjectChoiceMaster) l.get(0)).getStudentregistration();
                        registration.setPreventfreezed("Y");
                        registration.setPrfreezeddate(new Date());
                        session.update(registration);
                    }
                }
                session.saveOrUpdate(choiceMaster);
            }
            if (subjectChoiceCountList != null) {
//                System.err.println("subjectChoiceCountList " + subjectChoiceCountList.size());
                for (int m = 0; m < subjectChoiceCountList.size(); m++) {
                    session.saveOrUpdate(subjectChoiceCountList.get(m));
                }
            }
            if (nrSubjectsList != null) {
                //              System.err.println("nrSubjectsList " + nrSubjectsList.size());
                for (int m = 0; m < nrSubjectsList.size(); m++) {
                    StudentNRSubjects nRSubjects = nrSubjectsList.get(m);
                    if (m == 0) {
                        List l = (List) session.createQuery("from StudentNRSubjects where id.instituteid = '" + nRSubjects.getId().getInstituteid() + "' and id.academicyear = '" + nRSubjects.getId().getAcademicyear() + "' and id.programid = '" + nRSubjects.getId().getProgramid() + "' and id.studentid = '" + nRSubjects.getId().getStudentid() + "'  and id.sectionid = '" + nRSubjects.getId().getSectionid() + "' and id.stynumber = " + nRSubjects.getId().getStynumber() + "").list();
                        for (int i = 0; i < l.size(); i++) {
                            session.delete(l.get(i));
                        }
                    }
                    session.save(nRSubjects);
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            returnMsg = "error";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return returnMsg;
    }

    public List getSubjectData(final String instid, final String regid, final String acadYear, final String programid, final String semester, final String section, final String subjectType) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append("select distinct p.id.registrationid,p.subjectid,p.academicyear,p.programid,p.sectionid,p.basketid,p.stynumber,p.credits,");
        qry.append(" bm.basketcode,sm.sectioncode,sub.subjectcode,sub.subjectdesc,st.subjecttype,st.id.subjecttypeid,sm.id.sectionid ");
        qry.append(" from ProgramSubjectTagging p , ProgramSubjectDetail d,BasketMaster bm ,SectionMaster sm,SubjectMaster sub,SubjectTypeMaster st ");
        qry.append(" where p.id.instituteid = '" + instid + "' ");
        qry.append(" and p.id.registrationid='" + regid + "' ");
        qry.append(" and p.academicyear='" + acadYear + "' ");
        qry.append(" and p.programid='" + programid + "' ");
        qry.append(" and p.stynumber in(" + semester + ") ");
        qry.append(" and p.sectionid in(" + section + ") ");
        qry.append(" and p.id.instituteid=d.id.instituteid ");
        qry.append(" and p.id.registrationid=d.id.registrationid ");
        qry.append(" and p.id.programsubjectid=d.id.programsubjectid ");
        qry.append(" and p.id.instituteid =bm.id.instituteid ");
        qry.append(" and p.programid =bm.programid ");
        qry.append(" and p.stynumber = bm.stynumber ");
        qry.append(" and p.basketid = bm.id.basketid ");
        qry.append(" and p.sectionid = bm.sectionid ");
        qry.append(" and p.sectionid = sm.id.sectionid ");
        qry.append(" and p.subjectid = sub.id.subjectid and p.id.instituteid = sub.id.instituteid");
        qry.append(" and bm.subjecttypeid = st.id.subjecttypeid ");
        qry.append(" and bm.id.instituteid = st.id.instituteid ");
        qry.append(" and coalesce(p.deactive,'N')='N'  ");
        qry.append(" and coalesce(d.deactive,'N')='N' ");
        if (subjectType.equals("E")) {
            qry.append(" and st.subjecttype in ('E','I','O')");
        }
        if (subjectType.equals("C")) {
            qry.append(" and st.subjecttype in ('C','P','S')");
        }
        qry.append(" order by p.basketid ");
        try {
            list = getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map getAllRegdStudentNotInSubjectChoice(final String instituteid, final String registrationid, final String acad_year, final String programid, final String sectionids, float totalcredit, String styno) {
        final Map asobject = (Map) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Map retObject = new HashMap();
                try {
                    String whereClause = " concat(p.studentmaster.acadyear,p.studentmaster.programid,p.studentmaster.sectionid) in ( ";
                    String[] sectionid = sectionids.split(",");
                    for (int i = 0; i < sectionid.length; i++) {
                        if (i > 0) {
                            whereClause = whereClause + " , ";
                        }
                        whereClause = whereClause + " '" + acad_year + "" + programid + "" + sectionid[i] + "' ";
                    }
                    if (!whereClause.equals("")) {

                        whereClause = "  and " + whereClause + " )";
                    }
                    List studentWithoutErrorList = new ArrayList();
                    List studentWithErrorList = new ArrayList();
                    Map listObject = new HashMap();
                    StudentRegistration studentRegistration = null;
                    //checkif it exist in ProgramSubjectTagging
                    Query qry = session.createQuery("select  p from StudentRegistration p  "
                            + " where size(p.studentsubjectchoicemasters)=0 "
                            + " and p.id.instituteid = '" + instituteid + "'"
                            + " and p.id.registrationid='" + registrationid + "'"
                            + " " + whereClause
                            + " and coalesce(p.studentmaster.activestatus,'A')='A' "
                            + " and p.regallow ='Y' and p.stynumber in(" + styno + ")").setReadOnly(true);
                    List studentlist = qry.list();
                    ProgramMinMaxLimit programMinMaxLimit = null;
                    float totalCredits = totalcredit;
                    Map subjectObj = null;
                    for (int i = 0; i < studentlist.size(); i++) {
                        subjectObj = new HashMap();
                        studentRegistration = (StudentRegistration) studentlist.get(i);
                        listObject = new HashMap();
                        listObject.put("studentid", studentRegistration.getId().getStudentid());
                        listObject.put("registrationid", studentRegistration.getId().getRegistrationid());
                        listObject.put("instituteid", studentRegistration.getId().getInstituteid());
                        listObject.put("studentname", studentRegistration.getStudentmaster().getName());
                        listObject.put("enrollmentno", studentRegistration.getStudentmaster().getEnrollmentno());
                        programMinMaxLimit = (ProgramMinMaxLimit) session.get(ProgramMinMaxLimit.class, new ProgramMinMaxLimitId(studentRegistration.getId().getInstituteid(), studentRegistration.getStudentmaster().getAcadyear(), studentRegistration.getStudentmaster().getProgramid(), studentRegistration.getStudentmaster().getBranchid(), studentRegistration.getStynumber()));
                        if (programMinMaxLimit == null) {
                            System.err.println("ProgramMinMaxLimit null for " + studentRegistration.getId().getInstituteid() + "" + studentRegistration.getStudentmaster().getProgramid() + "" + studentRegistration.getStudentmaster().getBranchid() + "" + studentRegistration.getStudentmaster().getAcadyear() + "" + studentRegistration.getStynumber());
                            listObject.put("error", "ProgramMinMaxLimit is not defined");
                            studentWithErrorList.add(listObject);
                        } else {
                            listObject.put("maxlimit", programMinMaxLimit.getMaxlimit().floatValue());
                            //totalCredits = getStudentCredit(studentRegistration,  subjectObj);
                            if (programMinMaxLimit.getMaxlimit().floatValue() >= totalCredits) {
                                if (totalCredits >= programMinMaxLimit.getMinlimit().floatValue()) {
                                    listObject.put("subject", subjectObj);
                                    studentWithoutErrorList.add(listObject);
                                } else {
                                    listObject.put("error", "Credit points should be less than " + programMinMaxLimit.getMinlimit().floatValue());
                                    studentWithErrorList.add(listObject);
                                }
                            } else {
                                listObject.put("error", "Credit points should not be greater than " + programMinMaxLimit.getMaxlimit().floatValue());//programMinMaxLimit.getMaxlimit().floatValue());
                                studentWithErrorList.add(listObject);
                            }
                        }
                    }
                    retObject.put("studentwithouterror", studentWithoutErrorList);
                    retObject.put("studentwitherror", studentWithErrorList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return retObject;
            }
        });
        return asobject;
    }

    public List getAllSubjectWiseStudentExcelList(List searchData) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.enrollmentno,sm.name,sm.stynumber,pm.programcode,scm.sectioncode,pws.subsectioncode,sub.subjectcode,sub.subjectdesc,sc.subjectcomponentdesc "
                + " from FacultySubjectTagging fst, FacultyStudentTagging sft, SubjectMaster stm, SubjectComponent sc, StudentMaster sm, ProgramMaster pm, SectionMaster scm, ProgramWiseSubsection pws, SubjectMaster sub "
                + " where fst.id.instituteid=sft.id.instituteid and fst.id.fstid=sft.fstid and sm.id.instituteid= sft.id.instituteid "
                + " and sm.id.studentid=sft.id.studentid and fst.id.instituteid=sm.id.instituteid "
                + " and fst.subjectid=stm.id.subjectid and fst.id.instituteid=sc.id.instituteid and fst.subjectcomponentid=sc.id.subjectcomponentid "
                + " and pm.id.instituteid=sft.id.instituteid and pm.id.programid=fst.programid and scm.id.instituteid=fst.id.instituteid "
                + " and scm.id.sectionid=fst.sectionid and pws.id.instituteid=fst.id.instituteid and pws.id.programid=fst.programid "
                + " and pws.id.sectionid=fst.sectionid and pws.id.subsectionid=fst.subsectionid "
                + " and pws.id.academicyear=fst.academicyear and pws.id.stynumber=fst.stynumber "
                + " and sub.id.instituteid=fst.id.instituteid  and sub.id.subjectid=fst.subjectid "
                + " and ((fst.id.instituteid=:instituteid and fst.registrationid=:registrationid ");
        if (!searchData.get(3).toString().equalsIgnoreCase("All")) {
            sb.append(" and fst.subjectid=:subjectid )");
        } else {
            sb.append(" and :subjectid=:subjectid )");
        }
        sb.append(" or exists( select cl.id.instituteid from SubjectForCommonLoad cl where cl.id.maininstituteid=:instituteid"
                + " and cl.id.mainregistrationid=:registrationid "
                + " and cl.id.instituteid=fst.id.instituteid and cl.id.registrationid=fst.registrationid and cl.id.subjectid=fst.subjectid)) ");
        if (!searchData.get(4).toString().equalsIgnoreCase("All")) {
            sb.append(" and fst.subjectcomponentid=:subjectcomponentid");
        } else {
            sb.append(" and :subjectcomponentid=:subjectcomponentid");
        }
        sb.append(" and exists(select 1 from TT_TimeTableAllocation tt "
                + " where tt.id.instituteid=fst.id.instituteid "
                + " and tt.id.registrationid=fst.registrationid "
                + " and tt.subjectid=fst.subjectid "
                + " and tt.subjectcomponentid=fst.subjectcomponentid "
                + " and fst.ttreferenceid=tt.ttreferenceid"
                + " and tt.staffid=:staffid and coalesce(tt.status, 'A')='A') ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", searchData.get(0)).
                    setParameter("registrationid", searchData.get(1)).
                    setParameter("subjectid", searchData.get(3)).
                    setParameter("subjectcomponentid", searchData.get(4)).
                    setParameter("staffid", searchData.get(2)).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Boolean getPrerequisteSubjects(Session session, String subjectid, List nrSubjects) {
        List l = session.createQuery("select d.id.prereqsubjectid from PreRequisitMaster p "
                + "left join p.prerequisitmasterrequireds d left join p.prerequisitmastersubjectses s "
                + "where d.id.subjectsetid = s.id.subjectsetid and s.id.subjectid = '" + subjectid + "'").setReadOnly(true).list();
        Boolean flag = true;
        for (int i = 0; i < l.size(); i++) {
            if (l.contains(l.get(i).toString())) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * Method Used To Get GridData For Pending For Approval/Approved/Canclled
     * Tab
     *
     * @param instituteid
     * @param registrationid
     * @param status
     * @return
     */
    public List getSubjectRegistrationData(List instituteid, List registrationid, String status, String stytype, String requestfrom) {
        List list = null;
        Session session = null;
        StringBuilder query = new StringBuilder();
        query.append("select sm.enrollmentno,sm.studentid, sm.name, concat(concat(pm.programcode,'('),concat(bm.branchcode,')')),sscm.id.stynumber, concat(sbm.subjectcode,concat(':',sbm.subjectdesc)),sscm.regfeeamount ");
        query.append(" ,sscm.id.subjectid, sm.acadyear, sm.programid, sm.sectionid, sm.subsectionid, sscm.stytypeid, sscm.basketid,sscm.subjectrunning,str.id.instituteid,str.id.registrationid,im.institutecode, ");
        query.append(" pm.programcode,sec.sectioncode,(select pws.subsectioncode from ProgramWiseSubsection pws"
                + " where pws.id.instituteid=sec.id.instituteid and pws.id.sectionid=sec.id.sectionid "
                + " and pws.id.programid=pm.id.programid and pws.branchid=bm.id.branchid "
                + " and pws.id.stynumber=str.stynumber and pws.id.subsectionid=sm.subsectionid"
                + " and pws.id.academicyear=sm.acadyear) as subsectioncode,(select min(dst.id.departmentid) "
                + " from DepartmentSubjectTagging dst where"
                + " dst.id.instituteid=sscm.id.instituteid"
                + " and dst.id.subjectid=sscm.id.subjectid) as departmentid ");
        query.append(" from StudentSubjectChoiceMaster sscm, StudentMaster sm, SubjectMaster sbm, ProgramMaster pm, BranchMaster bm, StudentRegistration str,InstituteMaster im,SectionMaster sec ");
        query.append(" where sscm.id.instituteid in(:instituteid) ");
        query.append(" and sscm.id.registrationid in(:registrationid) ");
        query.append(" and im.instituteid=sm.instituteid ");
        query.append(" and sec.id.instituteid=sm.instituteid ");
        query.append(" and sec.id.sectionid=sm.sectionid ");
        query.append(" and sscm.id.instituteid=sm.instituteid ");
        query.append(" and sscm.id.instituteid=sbm.id.instituteid ");
        query.append(" and sscm.id.instituteid=pm.id.instituteid ");
        query.append(" and sscm.id.instituteid=bm.id.instituteid ");
        query.append(" and sscm.id.instituteid=str.id.instituteid ");
        query.append(" and sscm.id.registrationid=str.id.registrationid ");
        query.append(" and sscm.id.subjectid=sbm.id.subjectid ");
        query.append(" and sm.studentid=sscm.id.studentid ");
        query.append(" and sm.studentid=str.id.studentid ");
        query.append(" and sm.programid=pm.id.programid ");
        query.append(" and pm.id.programid=bm.id.programid ");
        query.append(" and sm.branchid=bm.id.branchid ");
        query.append(" and sscm.stytypeid in (select st.id.stytypeid from StyType st where st.stytype=:stytype and st.id.instituteid=sscm.id.instituteid) ");
//query.append(" and sscm.stytypeid not in (select st.id.stytypeid from StyType st where st.stytype='REG' and st.id.instituteid=sscm.id.instituteid) ");
        if (requestfrom.equalsIgnoreCase(stytype)) {
            if (status == null) {
                query.append(" and (sscm.supplimentryregistered is null or sscm.supplimentryregistered='D') ");
            } else if ("A".equalsIgnoreCase(status)) {
                query.append(" and sscm.supplimentryregistered='A' ");
            } else if ("C".equalsIgnoreCase(status)) {
                query.append(" and sscm.supplimentryregistered='C' ");
            }
        } else if (status == null) {
            query.append(" and (sscm.subjectrunning is null or sscm.subjectrunning='N') ");
        } else if ("A".equalsIgnoreCase(status)) {
            query.append(" and sscm.subjectrunning='Y' ");
        } else if ("C".equalsIgnoreCase(status)) {
            query.append(" and sscm.subjectrunning='N' ");
        }
        query.append(" order by sm.enrollmentno,sm.name, concat(concat(pm.programcode,'('),concat(bm.branchcode,')')),sscm.id.stynumber, concat(sbm.subjectcode,concat(':',sbm.subjectdesc)) ");
        try {
            session = (Session) getSession();
            list = session.createQuery(query.toString()).
                    setParameterList("instituteid", instituteid).setParameterList("registrationid", registrationid).setParameter("stytype", stytype).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            query = null;
        }
        return list;
    }

    public List getSubjectWiseReportData(String instituteid, String registrationid, String stytype) {
        List list = null;
        Session session = null;
        StringBuilder query = new StringBuilder();
        query.append("select sscm.id.subjectid, concat(sbm.subjectcode,concat(':',sbm.subjectdesc)), count(*) ");
        if ("SUP".equalsIgnoreCase(stytype)) {
            query.append(",(select count(*) from StudentSubjectChoiceMaster sscm1 where sscm1.id.instituteid=:instituteid ");
            query.append(" and sscm1.id.registrationid=:registrationid and sscm1.id.subjectid=sscm.id.subjectid and coalesce(sscm1.supplimentryregistered,'C')='A' ");
            query.append(" and sscm1.stytypeid in (select st.id.stytypeid from StyType st where st.stytype=:stytype and st.id.instituteid=sscm1.id.instituteid)) ");
        } else {
            query.append(",(select count(*) from StudentSubjectChoiceMaster sscm1 where sscm1.id.instituteid=:instituteid ");
            query.append(" and sscm1.id.registrationid=:registrationid and sscm1.id.subjectid=sscm.id.subjectid and coalesce(sscm1.subjectrunning,'N')='Y' ");
            query.append(" and sscm1.stytypeid in (select st.id.stytypeid from StyType st where st.stytype=:stytype and st.id.instituteid=sscm1.id.instituteid)) ");
        }
        query.append(" from StudentSubjectChoiceMaster sscm, SubjectMaster sbm ");
        query.append(" where sscm.id.instituteid=:instituteid ");
        query.append(" and sscm.id.registrationid=:registrationid ");
        query.append(" and sscm.id.instituteid=sbm.id.instituteid ");
        query.append(" and sscm.id.subjectid=sbm.id.subjectid ");
        query.append(" and sscm.stytypeid in (select st.id.stytypeid from StyType st where st.stytype=:stytype and st.id.instituteid=sscm.id.instituteid) ");
        query.append(" group by sscm.id.subjectid, concat(sbm.subjectcode,concat(':',sbm.subjectdesc)) order by sscm.id.subjectid, concat(sbm.subjectcode,concat(':',sbm.subjectdesc)) ");
        try {
            session = (Session) getSession();
            list = session.createQuery(query.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("stytype", stytype).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            query = null;
        }
        return list;
    }

    public boolean update_STR(List updateObj) {
        Session session = null;
        Transaction tx = null;
        String query = null;
        boolean flag = false;
        if (session == null) {
            session = getHibernateSession();
            tx = session.beginTransaction();
        }
        try {
            if (updateObj != null) {
                for (int i = 0; i < updateObj.size(); i++) {
                    query = updateObj.get(i).toString();
                    org.hibernate.Query quy = session.createQuery(query);
                    quy.executeUpdate();
                }
                tx.commit();
                if (tx.wasCommitted()) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            flag = false;
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return flag;
    }

    public List getprogSubDetail(String regId, String instId, String subId, String basketId, int styNo) {
        List list = null;
        String str = " select distinct psd.id.subjectcomponentid,coalesce(psd.deactive,'N') from ProgramSubjectDetail psd"
                + " where psd.id.instituteid='" + instId + "' and psd.id.registrationid='" + regId + "' and  psd.id.programsubjectid in(select b.id.programsubjectid from ProgramSubjectTagging b"
                + " where b.id.instituteid='" + instId + "' and b.id.registrationid='" + regId + "' and b.stynumber='" + styNo + "' and b.basketid='" + basketId + "' and b.subjectid='" + subId + "')";
        list = getHibernateTemplate().find(str);
        return list;
    }

    public List getStudentSubjectRegistrationDetailsReportData(final String instituteid, final String programid, final String branchid, final String registrationid, final String orderby, String sortedby) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        String qry1 = registrationid.equals("ALL") ? " " : "and sscm.id.registrationid =:registrationid ";
        String qry2 = programid.equals("ALL") ? " " : "and sm.id.programid=:programid and sm.id.branchid=:branchid ";
        String qry3 = orderby.equals("E") ? "order by sm.enrollmentno" : "order by sub.subjectcode";
        String qry4 = sortedby.equals("asc") ? "asc" : "desc";
        sb.append(" select sm.enrollmentno, sm.name, pm.programcode,bm.branchcode,sub.subjectcode,rm.registrationdatefrom,rm.registrationdateto,' ' as subjectareacode "
                + " ,sscm.id.stynumber from StudentMaster sm,ProgramMaster pm, BranchMaster bm,StudentSubjectChoiceMaster sscm, SubjectMaster sub,RegistrationMaster rm  "
                + " Where sm.id.instituteid =  pm.id.instituteid and sm.id.programid = pm.id.programid and sm.id.instituteid =bm.id.instituteid and sm.id.programid=bm.id.programid "
                + " and sm.id.branchid=bm.id.branchid and sm.id.instituteid =sscm.id.instituteid and sub.id.instituteid=sscm.id.instituteid and sub.id.subjectid=sscm.id.subjectid "
                + " and rm.id.registrationid=sscm.id.registrationid and rm.id.instituteid=sscm.id.instituteid "
                + " " + qry1 + " "
                + " " + qry2 + " "
                + " and sm.instituteid=:instituteid "
                + " and sm.id.studentid = sscm.id.studentid and sscm.subjectrunning='Y' and coalesce(sscm.deactive,'N')='N' "
                + " " + qry3 + " " + qry4 + "");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStudentSubjectRegistrationDetailsReportData1(final String instituteid, final String programid, final String branchid, final String registrationid, final String orderby, String sortedby) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.studentid,bm.id.branchid,pm.id.programid,sm.name,sm.enrollmentno,sm.stynumber,bm.branchcode,bm.branchdesc, "
                + " pm.programcode,pm.programdesc from StudentMaster sm, BranchMaster bm, ProgramMaster pm where sm.instituteid = :instituteid "
                + " and bm.id.instituteid = sm.instituteid and pm.id.instituteid = sm.instituteid and bm.id.branchid = sm.branchid and pm.id.programid = sm.programid "
                + " and (exists (select smc.id.studentid from StudentSubjectChoiceMaster smc where smc.id.studentid = sm.studentid  and smc.id.instituteid=sm.instituteid "
                + " and smc.id.registrationid= :registrationid)) order by sm.enrollmentno ");
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

    public List<Object[]> getStudentSubjectChoice(final String studentid, final String instituteid, final String registrationid) {

        String hql = "select bm.basketdesc, stytype3_.stytypedesc, sm.subjectcode, sm.subjectdesc, studentsub1_.choice, v_programs0_.credits"
                + " from ProgramSubjectTagging v_programs0_, BasketMaster bm, SubjectMaster sm, StudentSubjectChoiceMaster studentsub1_, StudentMaster studentmas2_, StyType stytype3_, StudentRegistration_info studentreg4_"
                + " where studentsub1_.id.studentid = ?"
                + " and studentsub1_.id.instituteid = ?"
                + " and studentsub1_.id.registrationid = ?"
                + //" and coalesce(studentsub1_.subjectrunning,'N') = 'Y'" +
                " and studentsub1_.id.subjectid = v_programs0_.subjectid"
                + " and studentsub1_.id.instituteid = v_programs0_.id.instituteid"
                + " and studentsub1_.id.registrationid = v_programs0_.id.registrationid"
                + " and studentsub1_.choice is not null"
                + " and studentsub1_.id.studentid = studentmas2_.studentid"
                + " and v_programs0_.id.instituteid = studentmas2_.instituteid"
                + " and v_programs0_.academicyear = studentmas2_.acadyear"
                + " and v_programs0_.programid = studentmas2_.programid"
                + " and stytype3_.id.stytypeid = studentsub1_.stytypeid"
                + " and stytype3_.id.instituteid = studentsub1_.id.instituteid"
                + " and studentreg4_.id.registrationid = v_programs0_.id.registrationid"
                + " and studentreg4_.id.instituteid = v_programs0_.id.instituteid"
                + " and studentreg4_.sectionid = v_programs0_.sectionid"
                + " and v_programs0_.academicyear = studentreg4_.academicyear"
                + " and v_programs0_.programid = studentreg4_.programid"
                + " and studentreg4_.id.studentid = studentsub1_.id.studentid"
                + " and v_programs0_.id.instituteid = bm.id.instituteid"
                + " and v_programs0_.basketid = bm.id.basketid"
                + " and v_programs0_.id.instituteid = sm.id.instituteid"
                + " and v_programs0_.subjectid = sm.id.subjectid"
                + " group by bm.basketdesc, stytype3_.stytypedesc, sm.subjectcode, sm.subjectdesc, studentsub1_.choice, v_programs0_.credits"
                + " order by bm.basketdesc, stytype3_.stytypedesc, studentsub1_.choice";

        List list = getHibernateTemplate().find(hql, new Object[]{studentid, instituteid, registrationid});
        return list;
    }

    @Override
    public List getEnrollmentNumber(String instituteid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.studentid,bm.id.branchid,pm.id.programid,sm.name,sm.enrollmentno,sm.stynumber,bm.branchcode,bm.branchdesc, "
                + " pm.programcode,pm.programdesc from StudentMaster sm, BranchMaster bm, ProgramMaster pm where sm.instituteid = :instituteid "
                + " and bm.id.instituteid = sm.instituteid and pm.id.instituteid = sm.instituteid and bm.id.branchid = sm.branchid and pm.id.programid = sm.programid "
                + " and (exists (select smc.id.studentid from StudentSubjectChoiceMaster smc where smc.id.studentid = sm.studentid  and smc.id.instituteid=sm.instituteid "
                + " and smc.id.registrationid= :registrationid)) order by sm.enrollmentno ");
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

    @Override
    public List getAllSubjectWiseStudentList(String instituteid, String registrationid, String employeeid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select  subjectmas2_.subjectcode, subjectmas2_.subjectdesc,subjectcom3_.subjectcomponentdesc,count(distinct facultystu1_.id.studentid),subjectmas2_.id.subjectid, "
                + " subjectcom3_.id.subjectcomponentid, facultysub0_.id.instituteid, facultysub0_.registrationid from FacultySubjectTagging facultysub0_,FacultyStudentTagging facultystu1_,SubjectMaster subjectmas2_,SubjectComponent subjectcom3_ "
                + " where facultysub0_.id.instituteid=facultystu1_.id.instituteid and facultysub0_.id.fstid=facultystu1_.fstid and facultysub0_.id.instituteid=subjectmas2_.id.instituteid  and facultysub0_.subjectid=subjectmas2_.id.subjectid  "
                + " and facultysub0_.id.instituteid=subjectcom3_.id.instituteid and facultysub0_.subjectcomponentid=subjectcom3_.id.subjectcomponentid "
                + " and ((facultysub0_.id.instituteid=:instituteid and facultysub0_.registrationid=:registrationid)"
                + " or exists (select cl.id.instituteid from SubjectForCommonLoad cl where cl.id.maininstituteid=:instituteid and cl.id.mainregistrationid=:registrationid "
                + " and cl.id.instituteid= facultysub0_.id.instituteid and cl.id.registrationid= facultysub0_.registrationid and cl.id.subjectid= facultysub0_.subjectid ))"
                + " and (exists (select  1 from TT_TimeTableAllocation tt_timetab4_ where tt_timetab4_.id.instituteid=:instituteid  "
                + " and tt_timetab4_.id.registrationid=:registrationid "
                + " and facultysub0_.ttreferenceid=tt_timetab4_.ttreferenceid "
                + " and tt_timetab4_.staffid=:employeeid and coalesce(tt_timetab4_.status, 'A')='A' "
                + "  )) group by subjectmas2_.subjectcode , subjectmas2_.subjectdesc ,  subjectcom3_.subjectcomponentdesc ,subjectmas2_.id.subjectid , subjectcom3_.id.subjectcomponentid, facultysub0_.id.instituteid,facultysub0_.registrationid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("employeeid", employeeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Map getStudentDataForStudentSubjectFinalization(final String instid, final String regid, final List criteriaList, final boolean checkCrLimit, HttpServletRequest request) {
        final Map aSObject = (Map) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                String whereClause = "";
                if (criteriaList.isEmpty()) {
                    whereClause = " (1=1) ";
                }
                String[] arr = new String[3];
                for (int i = 0; i < criteriaList.size(); i++) {
                    String str = (String) criteriaList.get(i);
                    arr = str.split(":");
                    if (i > 0) {
                        whereClause = whereClause + " or ";
                    }
                    whereClause = whereClause + "("
                            + "  a.id.instituteid = b.id.instituteid"
                            + " and a.id.registrationid = b.id.registrationid"
                            + " and a.id.studentid = b.id.studentid"
                            + " and b.academicyear = '" + arr[0] + "'"
                            + " and b.programid = '" + arr[1] + "'"
                            + " and b.sectionid in ( '" + arr[2] + "'))";
                }
                List<Map> studentWithoutErrorList = new ArrayList<Map>();
                List<Map> studentWithErrorList = new ArrayList<Map>();
                List noSubFoundSSCM = new ArrayList();
                List fstNotFound = new ArrayList();
                List minMaxCrPt = new ArrayList();
                List noSbInPST = new ArrayList();
                List noSubSec = new ArrayList();
                List zeroCrPT = new ArrayList();
                List noCrLimit = new ArrayList();
                String pid = request.getParameter("programId");

                Query qry1 = session.createQuery("select distinct fst.id.studentid from FacultyStudentTagging fst where fst.id.instituteid='" + instid + "' and fst.registrationid='" + regid + "'");
                Query qry = session.createQuery("select b.id.instituteid,b.id.registrationid,b.id.studentid,b.academicyear,b.programid,b.branchid,b.stynumber,sm.enrollmentno,sm.name,"
                        + " b.sectionid,"
                        + " (select count(sscm.id.studentid) from"
                        + " StudentSubjectChoiceMaster sscm,"
                        + " StudentSubjectChoiceDetail sscd "
                        + " where sscm.id.instituteid = sscd.id.instituteid "
                        + " and sscm.id.registrationid = sscd.id.registrationid "
                        + " and sscm.id.stynumber = sscd.id.stynumber "
                        + " and sscm.id.studentid = a.id.studentid"
                        + " and sscm.id.studentid = sscd.id.studentid"
                        + " and sscm.id.subjectid = sscd.id.subjectid"
                        + " and sscm.id.instituteid = a.id.instituteid "
                        + " and sscm.id.registrationid = a.id.registrationid "
                        + " and sscm.subjectrunning = 'Y'"
                        + " and ( sscd.subsectionid is null )) as subsectionnotfound,coalesce(sm.lateralentry,'N') as lateralentry,"
                        + " (select scm.sectioncode from SectionMaster scm"
                        + " where b.id.instituteid=scm.id.instituteid"
                        + " and b.sectionid=scm.id.sectionid ) as section,"
                        + " (select  pws.subsectioncode from ProgramWiseSubsection pws "
                        + " where pws.id.instituteid=b.id.instituteid and pws.id.academicyear=b.academicyear and pws.id.programid=b.programid"
                        + " and pws.id.sectionid=b.sectionid and pws.id.subsectionid=b.subsectionid and pws.id.stynumber=b.stynumber and pws.branchid=b.branchid"
                        + " ) as sectionsubsection"
                        + " from StudentRegistration a,StudentMaster sm,StudentRegistration_info b"
                        + " where "
                        + " a.id.instituteid = '" + instid + "'"
                        + " and a.id.registrationid='" + regid + "'"
                        + " and a.id.instituteid=sm.instituteid"
                        + " and a.id.studentid=sm.studentid"
                        + " and sm.programid = '" + pid + "'"
                        + " and coalesce(a.regallow,'N') ='Y'"
                        + " and coalesce(a.regconfirmation,'N') ='N'"
                        + " and coalesce(sm.activestatus, 'A')='A'"
                        + " and (" + whereClause + ")");

                List<Object[]> studentlist = null;
                List<Object[]> sftstudentlist = null;
                List<Map> studentzeroccp = null;
                List<Map> studentnullccp = null;
                List<Map> studentMinMaxLimit = null;
                List<Map> checkNoFSTSubjects = null;
                List<Map> checkNoPSTSubjects = null;

                try {
                    studentlist = (List<Object[]>) qry.list();
                    sftstudentlist = (List<Object[]>) qry1.list();
                    studentzeroccp = (List<Map>) checkCCPmarks(instid, regid, arr[0], pid, arr[2], "0");
                    studentnullccp = (List<Map>) checkCCPmarks(instid, regid, arr[0], pid, arr[2], "-1");
                    checkNoFSTSubjects = (List<Map>) fstNotPopulateData(instid, regid, arr[0], pid, arr[2]);
                    checkNoPSTSubjects = (List<Map>) subjectIdNotFoundinPST(instid, regid, arr[0], pid, arr[2]);
                    if (checkCrLimit) {
                        studentMinMaxLimit = (List<Map>) getMinMaxCreditPointsError(instid, regid, arr[0], pid, arr[2]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < studentlist.size(); i++) {
                    Object[] obj1 = studentlist.get(i);

                    String sriInstituteid = obj1[0] != null ? obj1[0].toString() : "";
                    String sriRegistrationid = obj1[1] != null ? obj1[1].toString() : "";
                    String sriAcadyear = obj1[3] != null ? obj1[3].toString() : "";
                    String sriBranchid = obj1[5] != null ? obj1[5].toString() : "";
                    String sriProgramid = obj1[4] != null ? obj1[4].toString() : "";
                    String sriSectionid = obj1[9] != null ? obj1[9].toString() : "";
                    String sriSubsectionid = Integer.parseInt(obj1[10].toString()) > 0 ? "NotFound" : "Found";
                    String lateralentry = obj1[11].toString();
                    String alreadyexists = "";
                    if (sftstudentlist.contains(obj1[2] != null ? obj1[2].toString() : "")) {
                        alreadyexists = "1";
                    } else {
                        alreadyexists = "0";
                    }
                    String sriStudentid = obj1[2] != null ? obj1[2].toString() : "";
                    String sriStyno = obj1[6] != null ? obj1[6].toString() : "";

                    Map aso = new HashMap();
                    aso.put("studentid", obj1[2]);
                    aso.put("enrollmentno", obj1[7]);
                    aso.put("name", obj1[8]);
                    aso.put("sectionsubsection", (obj1[12] != null ? obj1[12] : "") + (obj1[13] != null ? "( " + obj1[13] + " )" : ""));
                    aso.put("alreadyexists", alreadyexists);
                    List l = new ArrayList();
                    if (!(sriStyno.equals("1") || (sriStyno.equals("3") && lateralentry.equals("Y")))) {
                        l = getNOSubFoundInStudSubChoiceMaster(instid, regid, sriStyno, sriStudentid);
                    }
                    if (l.isEmpty()) {
                        //System.err.println("1. No Subject Found in Student Subject Choice");
                        noSubFoundSSCM.add(aso);
                        studentWithErrorList.add(aso);
                    } else {
                        boolean flag = true;
                        List temp = new ArrayList();
                        //minmax credit point
                        //System.err.println("2. Min Max CR LIMIT"); 
                        if (studentMinMaxLimit != null) {
                            if (studentMinMaxLimit.size() > 0) {
                                for (int x = 0; x < studentMinMaxLimit.size(); x++) {
                                    Map map = studentMinMaxLimit.get(x);
                                    if (map.containsValue(sriStudentid)) {
                                        aso.put("studcredit", map.get("studcredit"));
                                        aso.put("minlimit", map.get("minlimit"));
                                        aso.put("maxlimit", map.get("maxlimit"));
                                        minMaxCrPt.add(aso);
                                        if (flag) {
                                            studentWithErrorList.add(aso);
                                            flag = false;
                                        }
                                        studentMinMaxLimit.remove(map);
                                        x--;
                                    }
                                }

                            }
                        }
                        //fst not found in pst
                        //System.err.println("3. FST not found...");
                        if (checkNoFSTSubjects != null) {
                            if (checkNoFSTSubjects.size() > 0) {
                                StringBuilder sb = new StringBuilder();
                                Map m = new HashMap();
                                List<String> sublist = new ArrayList<String>();
                                for (int x = 0; x < checkNoFSTSubjects.size(); x++) {
                                    Map map = checkNoFSTSubjects.get(x);
                                    if (map.containsValue(sriStudentid)) {
                                        if (m.containsKey(map.get("subjectcode"))) {
                                            String str = "";
                                            for (int j = 0; j < sublist.size(); j++) {
                                                String s = sublist.get(j);
                                                String[] st = s.split(" / ");
                                                if (st[0].toString().equals(map.get("subjectcode"))) {
                                                    str = map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + st[2].split("<br>")[0] + " , " + map.get("subjectcomponentcode") + "<br>";
                                                    sublist.remove(j);
                                                }
                                            }
                                            sublist.add(str);
                                        } else {
                                            sublist.add(map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + map.get("subjectcomponentcode") + "<br>");
                                        }
                                        m.put(map.get("subjectcode"), map.get("subjectcode"));
                                        checkNoFSTSubjects.remove(map);
                                        x--;
                                    }
                                }
                                for (String ss : sublist) {
                                    sb.append(ss);
                                }
                                aso.put("nofstsubject", sb);
                                if (flag && sb.length() != 0) {
                                    fstNotFound.add(aso);
                                    studentWithErrorList.add(aso);
                                    flag = false;
                                }
                            }
                        }
                        // subject not found in pst
                        //System.err.println("4. Subject not found in pst");
                        if (checkNoPSTSubjects != null) {
                            if (checkNoPSTSubjects.size() > 0) {
                                StringBuilder sb = new StringBuilder();
                                Map m = new HashMap();
                                List<String> sublist = new ArrayList<String>();
                                for (int x = 0; x < checkNoPSTSubjects.size(); x++) {
                                    Map map = checkNoPSTSubjects.get(x);
                                    if (map.containsValue(sriStudentid)) {
                                        if (m.containsKey(map.get("subjectcode"))) {
                                            String str = "";
                                            for (int j = 0; j < sublist.size(); j++) {
                                                String s = sublist.get(j);
                                                String[] st = s.split(" / ");
                                                if (st[0].toString().equals(map.get("subjectcode"))) {
                                                    str = map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + st[2].split("<br>")[0] + " , " + map.get("subjectcomponentcode") + "<br>";
                                                    sublist.remove(j);
                                                }
                                            }
                                            sublist.add(str);
                                        } else {
                                            sublist.add(map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + map.get("subjectcomponentcode") + "<br>");
                                        }
                                        m.put(map.get("subjectcode"), map.get("subjectcode"));
                                        checkNoPSTSubjects.remove(map);
                                        x--;
                                    }
                                }
                                for (String ss : sublist) {
                                    sb.append(ss);
                                }
                                aso.put("nopstsubject", sb);
                                if (flag && sb.length() != 0) {
                                    noSbInPST.add(aso);
                                    studentWithErrorList.add(aso);
                                    flag = false;
                                }
                            }
                        }

                        if (sriSubsectionid.equals("NotFound")) {
                            noSubSec.add(aso);
                            if (flag) {
                                studentWithErrorList.add(aso);
                                flag = false;
                            }
                        }
                        if (studentnullccp != null) {
                            if (studentnullccp.size() > 0) {
                                String str = "";
                                boolean allow = false;
                                for (int x = 0; x < studentnullccp.size(); x++) {
                                    Map map = studentnullccp.get(x);
                                    if (map.containsValue(sriStudentid)) {
                                        str += map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + map.get("subjectcomponentcode") + "<br>";
                                        allow = true;
                                        studentnullccp.remove(map);
                                        x--;
                                    }
                                }
                                if (allow) {
                                    noCrLimit.add(aso);
                                    aso.put("nullccpsubject", str);
                                }
                                if (flag && !str.equals("")) {
                                    studentWithErrorList.add(aso);
                                    flag = false;
                                }
                            }
                        }
                        if (studentzeroccp != null) {
                            if (studentzeroccp.size() > 0) {
                                String str = "";
                                boolean allow = false;
                                for (int x = 0; x < studentzeroccp.size(); x++) {
                                    Map map = studentzeroccp.get(x);
                                    if (map.containsValue(sriStudentid)) {
                                        str += map.get("subjectcode") + " / " + map.get("subjectdesc") + " / " + map.get("subjectcomponentcode") + "<br>";
                                        allow = true;
                                        studentzeroccp.remove(map);
                                        x--;
                                    }
                                }
                                if (allow) {
                                    zeroCrPT.add(aso);
                                    aso.put("zeroccpsubject", str);
                                }
                                if (flag && !str.equals("")) {
                                    studentWithErrorList.add(aso);
                                    flag = false;
                                }
                            }
                        }
                        if (flag) {
                            studentWithoutErrorList.add(aso);
                        }
                    }
                }
                List thirdList = new ArrayList();
                Map temp = new HashMap();
                temp.put("title", "No Subject found (0) in Subject Choice Master");
                temp.put("value", noSubFoundSSCM.size());
                temp.put("list", noSubFoundSSCM);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Faculty Subject Tagging not found");
                temp.put("value", fstNotFound.size());
                temp.put("list", fstNotFound);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Min Max Credit Limit");
                temp.put("value", minMaxCrPt.size());
                temp.put("list", minMaxCrPt);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Subject Not Found in PST");
                temp.put("value", noSbInPST.size());
                temp.put("list", noSbInPST);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Sub Section not entered");
                temp.put("value", noSubSec.size());
                temp.put("list", noSubSec);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Zero Cr Points");
                temp.put("value", zeroCrPT.size());
                temp.put("list", zeroCrPT);
                thirdList.add(temp);
                temp = new HashMap();
                temp.put("title", "Credit Limit not Entered");
                temp.put("value", noCrLimit.size());
                temp.put("list", noCrLimit);
                thirdList.add(temp);

                String reqRegid = request.getParameter("regId");
                String reqAcadYear = request.getParameter("acadYear");
                String reqProgramId = request.getParameter("programId");
                String reqBranchId = request.getParameter("branchId");
                String reqBranchIds[] = reqBranchId.split(",");
                String branchList = null;
                for (int br = 0; br < reqBranchIds.length; br++) {
                    if (br > 0) {
                        branchList = branchList + "','" + reqBranchIds[br] + "";
                    } else {
                        branchList = "" + reqBranchIds[br] + "";
                    }
                }
                String reqSectionId = request.getParameter("sectionId");
                String reqSectionIds[] = reqSectionId.split(",");
                String sectionList = null;
                for (int sec = 0; sec < reqSectionIds.length; sec++) {
                    if (sec > 0) {
                        sectionList = sectionList + "','" + reqSectionIds[sec] + "";
                    } else {
                        sectionList = "" + reqSectionIds[sec] + "";
                    }
                }
                List loadDist = (List) checkLoadDistribution(instid, reqProgramId, reqAcadYear, sectionList, reqRegid, branchList);
                temp = new HashMap();
                temp.put("title", "Load Distribution not provided");
                temp.put("value", loadDist.size());
                temp.put("list", loadDist);
                thirdList.add(temp);

                Map retObject = new HashMap();
                retObject.put("first", studentWithoutErrorList);
                retObject.put("second", studentWithErrorList);
                retObject.put("third", thirdList);
                return retObject;
            }
        }
        );
        return aSObject;
    }

    public List getNOSubFoundInStudSubChoiceMaster(final String instid, final String redgid, final String styno, final String studentid) {
        String qry = "select sscm.id.studentid from StudentSubjectChoiceMaster sscm where "
                + " sscm.id.instituteid ='" + instid + "'"
                + " and sscm.id.registrationid='" + redgid + "'"
                + " and sscm.id.stynumber = '" + styno + "'"
                + " and sscm.id.studentid = '" + studentid + "'"
                + " and sscm.subjectrunning='Y'";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public List checkCCPmarks(final String instid, final String regid, final String acadyear, final String pid, final String sectionid, final String val) {
        String qry = "select distinct new map ( sr.id.studentid as studentid,pst.subjectcode as subjectcode,pst.subjectdesc as subjectdesc,pst.subjectcomponentcode as subjectcomponentcode )"
                + "  from V_ProgramSubjectTagging pst,StudentRegistration sr,"
                + "  StudentRegistration_info info"
                + " where  pst.instituteid= '" + instid + "'"
                + " and info.id.instituteid = sr.id.instituteid"
                + " and sr.id.registrationid = info.id.registrationid"
                + " and sr.id.studentid = info.id.studentid"
                + " and coalesce (sr.regallow, 'N') = 'Y'"
                + " and info.id.instituteid = pst.instituteid"
                + " and pst.academicyear = info.academicyear"
                + " and pst.programid = info.programid"
                + " and pst.stynumber = info.stynumber"
                + " and info.sectionid = pst.sectionid "
                + " and coalesce (pst.calccpmarks, -1) = '" + val + "'"
                + " and pst.registrationid ='" + regid + "'"
                + " and pst.academicyear = '" + acadyear + "'"
                + " and pst.programid= '" + pid + "'"
                + " and pst.sectionid  in ( '" + sectionid + "')"
                + " and (pst.subjectid in"
                + " (select sscm.id.subjectid"
                + " from StudentSubjectChoiceMaster sscm"
                + " where  sscm.id.instituteid = '" + instid + "'"
                + " and sscm.id.registrationid ='" + regid + "'"
                + " and sscm.id.stynumber = pst.stynumber"
                + " and sscm.id.studentid = sr.id.studentid"
                + " and sscm.subjectrunning = 'Y'))";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public List getMinMaxCreditPointsError(final String instid, final String regid, final String acadyear, final String programid, final String sectionid) {
        String qry = " select new map(info.id.studentid as studentid,sum (pst.credits) as studcredit,min (pm.minlimit) as minlimit,max (pm.maxlimit) as maxlimit)"
                + " from ProgramSubjectTagging pst,StudentRegistration sr,StudentRegistration_info info,ProgramMinMaxLimit pm"
                + "   where  info.id.instituteid = pm.id.instituteid and pm.id.academicyear = info.academicyear"
                + " and pm.id.programid = info.programid and info.branchid = pm.id.branchid"
                + " and pm.id.stynumber = info.stynumber and info.id.instituteid = pst.id.instituteid"
                + " and info.id.registrationid = pst.id.registrationid and info.academicyear = pst.academicyear"
                + " and info.programid = pst.programid and info.sectionid = pst.sectionid"
                + " and pst.id.instituteid = '" + instid + "'"
                + " and pst.id.registrationid = '" + regid + "'"
                // + " and s.branchid in('" + branchid + "') and s.sectionid in('" + sectionid + "')"
                + " and info.sectionid in('" + sectionid + "')"
                + " and pst.academicyear = '" + acadyear + "' and pst.programid = '" + programid + "'"
                + " and sr.id.instituteid = info.id.instituteid and sr.id.registrationid = info.id.registrationid"
                + " and sr.id.studentid = info.id.studentid and coalesce (sr.regconfirmation, 'N') = 'N'"
                + " and coalesce (sr.regallow, 'N') = 'Y'"
                + " and exists ( select sscm.id.studentid from StudentSubjectChoiceMaster sscm where sscm.id.studentid = sr.id.studentid "
                + " and sscm.id.instituteid = sr.id.instituteid and sscm.id.registrationid = sr.id.registrationid "
                + " and sscm.id.stynumber = pst.stynumber  and sscm.id.subjectid=pst.subjectid "
                + " and sscm.basketid = pst.basketid and coalesce(sscm.subjectrunning,'N')='Y' and coalesce(sscm.auditsubject,'N')='N')"
                + " group by info.id.studentid"
                + " having sum (pst.credits) not between min (pm.minlimit) and max (pm.maxlimit)";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public ArrayList subjectIdNotFoundinPST(final String instid, final String regid, final String acadyear, final String programid, final String sectionid) {
        String qry = "select distinct new map (info.id.studentid as studentid ,sub.subjectcode as subjectcode,sub.subjectdesc as subjectdesc,sc.subjectcomponentcode as  subjectcomponentcode)"
                + " from"
                + " StudentSubjectChoiceMaster sscm,StudentSubjectChoiceDetail sscd,"
                + " SubjectComponent sc,SubjectMaster sub,"
                + " StudentRegistration_info info,StudentRegistration sr"
                + " where"
                + " sscm.id.registrationid='" + regid + "' and sscm.id.instituteid='" + instid + "'"
                + " and sscm.subjectrunning='Y'  and info.programid='" + programid + "'"
                + " and sr.id.instituteid=info.id.instituteid and sr.id.registrationid=info.id.registrationid"
                + " and sr.id.studentid=info.id.studentid and sr.regallow='Y'"
                + " and coalesce(sr.regconfirmation,'N')='N' and info.id.instituteid=sscd.id.instituteid"
                + " and info.id.studentid=sscd.id.studentid and info.academicyear='" + acadyear + "'"
                + " and info.sectionid in ('" + sectionid + "') and sscd.id.instituteid=sscm.id.instituteid "
                + " and sc.id.instituteid=sscm.id.instituteid  and sscd.id.registrationid=sscm.id.registrationid "
                + " and sscd.id.studentid=sscm.id.studentid  and sscd.id.stynumber=sscm.id.stynumber "
                + " and sscd.id.subjectid=sscm.id.subjectid and sscd.id.subjectcomponentid=sc.id.subjectcomponentid "
                + " and sub.id.instituteid=sscm.id.instituteid and sub.id.subjectid=sscm.id.subjectid "
                + " and exists (select studentid from StudentMaster sm"
                + " where sm.instituteid=info.id.instituteid and sm.studentid=info.id.studentid and coalesce(sm.activestatus, 'A')='A' )"
                + " and ( sscm.basketid in ( select bm.id.basketid from BasketMaster bm  where bm.subjecttypeid in ( select subtm.id.subjecttypeid from SubjectTypeMaster subtm where subtm.subjecttype='C' and subtm.id.instituteid='" + instid + "' ) ) )"
                + " and ( concat(sscm.id.subjectid,sscd.id.subjectcomponentid) not in  ("
                + " select distinct concat(pst.subjectid,pstd.id.subjectcomponentid) "
                + " from ProgramSubjectTagging pst,ProgramSubjectDetail pstd "
                + " where pst.id.registrationid=sscm.id.registrationid and pst.id.instituteid=sscm.id.instituteid"
                + " and coalesce(pst.deactive, 'N')='N' and pst.academicyear= info.academicyear"
                + " and pst.programid= info.programid and pst.sectionid=info.sectionid"
                + " and pstd.id.registrationid=pst.id.registrationid and pstd.id.instituteid=pst.id.instituteid"
                + " and pstd.id.instituteid=sc.id.instituteid and pstd.id.programsubjectid=pst.id.programsubjectid "
                + " and pst.subjectrunning='Y'  and sscm.basketid=pst.basketid and pst.stynumber = sscd.id.stynumber))"
                + " order by  info.id.studentid";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public List checkLoadDistribution(final String instid, final String programid, final String acadyear, final String sectionid, final String regid, final String branchid) {
        String qry = "select fst.academicyear,"
                + " pm.programcode,"
                + " sec.sectioncode,"
                + " (select pws.subsectioncode"
                + " from ProgramWiseSubsection pws"
                + " where fst.id.instituteid = pws.id.instituteid and fst.academicyear=pws.id.academicyear and fst.programid= pws.id.programid and"
                + " fst.stynumber=pws.id.stynumber and fst.sectionid=pws.id.sectionid and fst.subsectionid=pws.id.subsectionid"
                + " and pws.branchid in ('" + branchid + "')) as subsectioncode,"
                + " sb.subjectcode,"
                + " sc.subjectcomponentcode"
                + " from FacultySubjectTagging fst,"
                + " ProgramMaster pm,"
                + " SubjectMaster sb,"
                + " SubjectComponent sc,"
                + " SectionMaster sec"
                + " where "
                + " fst.id.instituteid='" + instid + "'"
                + " and fst.programid='" + programid + "'"
                + " and fst.academicyear='" + acadyear + "'"
                + " and fst.sectionid in ('" + sectionid + "')"
                + " and fst.registrationid='" + regid + "'"
                + " and pm.id.instituteid=fst.id.instituteid"
                + " and sb.id.instituteid=fst.id.instituteid"
                + " and sc.id.instituteid=fst.id.instituteid"
                + " and sec.id.instituteid=fst.id.instituteid"
                + " and pm.id.programid=fst.programid"
                + " and sb.id.subjectid=fst.subjectid"
                + " and sc.id.subjectcomponentid=fst.subjectcomponentid"
                + " and sec.id.sectionid=fst.sectionid"
                + " and fst.ttreferenceid is null";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public List fstNotPopulateData(final String instid, final String regid, final String academicyear, final String programid, final String sectionid) {

        String qry = "  select distinct"
                + "  new map( sscm.id.studentid as studentid,sub.subjectcode as subjectcode, sub.subjectdesc as subjectdesc, sc.subjectcomponentcode as subjectcomponentcode )"
                + " from"
                + " StudentSubjectChoiceDetail sscd,StudentSubjectChoiceMaster sscm,"
                + " StudentRegistration_info info,StudentRegistration sr,"
                + " SubjectMaster sub,SubjectComponent sc "
                + " where"
                + " sr.id.instituteid=info.id.instituteid and sr.id.registrationid=info.id.registrationid"
                + " and sr.id.studentid=info.id.studentid and sscm.id.instituteid='" + instid + "' "
                + " and info.programid='" + programid + "' and sscm.subjectrunning='Y' "
                + " and sscm.id.registrationid='" + regid + "' "
                + " and info.sectionid in ('" + sectionid + "')"
                + " and info.academicyear='" + academicyear + "'  and sr.regallow='Y'"
                + " and coalesce(sr.regconfirmation,'N')='N'"
                + " and exists (select studentid from   StudentMaster sm"
                + " where sm.instituteid=info.id.instituteid"
                + " and sm.studentid=info.id.studentid and coalesce(sm.activestatus, 'A')='A' )"
                + " and sub.id.instituteid=sscm.id.instituteid  and sub.id.subjectid=sscm.id.subjectid "
                + " and sc.id.instituteid=sscm.id.instituteid and sc.id.subjectcomponentid=sscd.id.subjectcomponentid "
                + " and sscm.id.studentid=info.id.studentid and sscm.id.instituteid=sscd.id.instituteid "
                + " and sscd.id.studentid=sscm.id.studentid  and sscd.id.registrationid=sscm.id.registrationid "
                + " and sscd.id.stynumber=sscm.id.stynumber and sscd.id.subjectid=sscm.id.subjectid "
                + " and sscd.id.instituteid=sscm.id.instituteid and sscm.id.instituteid=info.id.instituteid "
                + " and ( sscm.id.subjectid not in  "
                + " (select  fst.subjectid from FacultySubjectTagging fst where"
                + " fst.id.instituteid=sscm.id.instituteid and fst.registrationid=sscm.id.registrationid "
                + " and fst.stynumber=sscm.id.stynumber and fst.subjectid=sscm.id.subjectid "
                + " and fst.stytypeid=sscm.stytypeid and fst.subsectionid=sscd.subsectionid "
                + " and fst.basketid=sscm.basketid and fst.subjectcomponentid=sscd.id.subjectcomponentid "
                + " and fst.academicyear=info.academicyear and fst.programid=info.programid "
                + " and coalesce(fst.deactive, 'N')='N' ) ) "
                + " order by sscm.id.studentid";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public List getStudentFSTids(final String inst, final String regid, final String studentid) {
        String qry = "select  distinct sscm.id.instituteid,"
                + " fst.id.fstid ,"
                + " sm.id.studentid,fst.subjectid,sscm.auditsubject as subjectflag "
                + " ,sscm.equivalentsubjectid,(select count(id.instituteid) from PRFacultyStudentTagging"
                + " where id.instituteid=fst.id.instituteid "
                + " and id.fstid=fst.id.fstid "
                + " and id.studentid=sm.id.studentid "
                + " and coalesce(deactive,'N')='N' ) as prfst"
                + " from StudentSubjectChoiceDetail sscd,"
                + " StudentSubjectChoiceMaster sscm,"
                + " StudentRegistration_info sm, "
                + " FacultySubjectTagging fst "
                + " where   sscm.id.instituteid = '" + inst + "'"
                + " and sscm.id.studentid = sm.id.studentid"
                + " and sscm.id.instituteid = sm.id.instituteid"
                + " and sscm.id.registrationid = sm.id.registrationid"
                + " and sscm.id.instituteid = sscd.id.instituteid"
                + " and sscd.id.studentid = sscm.id.studentid"
                + " and sscd.id.registrationid = sscm.id.registrationid"
                + " and sscd.id.stynumber = sscm.id.stynumber"
                + " and sscd.id.subjectid = sscm.id.subjectid"
                + " and sscd.id.instituteid = sscm.id.instituteid"
                + " and sscm.id.instituteid = sm.id.instituteid"
                + " and sscm.id.studentid = '" + studentid + "'"
                + " and sscm.subjectrunning = 'Y'"
                + " and sscm.id.registrationid = '" + regid + "'"
                + " and fst.id.instituteid = sscm.id.instituteid"
                + " and fst.registrationid  = sscm.id.registrationid"
                + " and fst.stynumber  = sscm.id.stynumber"
                + " and fst.subjectid  = sscm.id.subjectid"
                + " and fst.stytypeid = sscm.stytypeid"
                + " and fst.subsectionid = sscd.subsectionid"
                + " and fst.basketid  = sscm.basketid"
                + " and fst.subjectcomponentid = sscd.id.subjectcomponentid"
                + " and fst.id.instituteid = sm.id.instituteid"
                + " and fst.academicyear  = sm.academicyear"
                + " and fst.programid  = sm.programid"
                + " and fst.sectionid = sm.sectionid"
                + " and coalesce(fst.deactive, 'N') = 'N' ";
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    public String saveStudentSubjectFinalize(final List<PRFacultyStudentTagging> prfstList, final List<FacultyStudentTagging> saveListFS, final List<StudentRegistration> updateList) {

        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();
            tx = session.beginTransaction();
            for (int i = 0; i < prfstList.size(); i++) {
                session.save((PRFacultyStudentTagging) prfstList.get(i));
            }
            for (int i = 0; i < updateList.size(); i++) {
                session.update((StudentRegistration) updateList.get(i));
            }
            retList = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retList;
    }

    public List getSubSectionNumber(String instituteid, String registrationid, String academicyear, String subjectid, String section) {
        StringBuilder query = new StringBuilder("select  distinct sd.subsectionid, p.subsectioncode  from  StudentSubjectChoiceMaster sscm, StudentMaster sm,SectionMaster s,StudentSubjectChoiceDetail sd,ProgramWiseSubsection p");
        query.append(" where  sscm.id.instituteid='" + instituteid + "'");
        query.append(" and sscm.id.registrationid='" + registrationid + "'");
        query.append(" and sscm.id.registrationid=sd.id.registrationid ");
        query.append(" and sscm.id.instituteid=sm.instituteid ");
        query.append(" and sscm.id.studentid=sm.studentid ");
        query.append(" and sscm.id.instituteid=sd.id.instituteid ");
        query.append(" and sm.sectionid=s.id.sectionid ");
        if (!section.equalsIgnoreCase("All")) {
            query.append(" and sm.sectionid= '" + section + "'");
        }
        if (!academicyear.equalsIgnoreCase("All")) {
            query.append(" and sm.acadyear= '" + academicyear + "'");
        }
        if (!subjectid.equalsIgnoreCase("All")) {
            query.append(" and sscm.id.subjectid= '" + subjectid + "'");
        }
        query.append(" and sd.subsectionid=sm.subsectionid ");
        query.append(" and sd.subsectionid=p.id.subsectionid ");
        return getHibernateTemplate().find(query.toString());
    }

    public List checkIfStudentExists(final String instituteid, final String registrationid, final String studentid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentSubjectChoiceMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));
                criteria.add(Restrictions.eq("master.id.studentid", studentid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.studentid").as("studentid")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getStudentSubjectChoiceMasterData(String instituteid, String registrationid, String studentid) {
        List list = new ArrayList();
        try {
            String qry = "from StudentSubjectChoiceMaster s"
                    + " where s.id.instituteid =:instituteid"
                    + " and s.id.registrationid =:registrationid"
                    + " and s.id.studentid =:studentid";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid)
                    .setParameter("studentid", studentid)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteStudentSubjectChoiceMasterData(String instituteid, String registrationid, String studentid) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from StudentSubjectChoiceMaster s where s.id.instituteid =:instituteid and s.id.registrationid =:registrationid and s.id.studentid =:studentid");
        try {
            Query qry = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid);
            qry.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getGridDateForSupplementary(String instituteid, String registrationid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sscm.id.instituteid,sscm.id.registrationid,sscm.id.studentid,stu.enrollmentno,stu.name,sscm.id.stynumber,sscm.id.subjectid,sm.subjectcode,sm.subjectdesc,sscm.basketid,bm.basketdesc "
                + " from StudentSubjectChoiceMaster sscm,SubjectMaster sm,BasketMaster bm,StudentMaster stu "
                + " where sscm.id.instituteid = :instituteid "
                + " and sscm.id.registrationid = :registrationid "
                + " and sscm.id.studentid = :studentid"
                + " and sm.id.instituteid = sscm.id.instituteid"
                + " and sm.id.subjectid= sscm.id.subjectid"
                + " and bm.id.instituteid = sscm.id.instituteid"
                + " and bm.id.basketid = sscm.basketid"
                + " and stu.instituteid = sscm.id.instituteid"
                + " and stu.studentid= sscm.id.studentid");
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

    public List checkDataInPRFST(String instituteid, String fstid, String studentid) {
        List list = new ArrayList();
        try {
            String qry = " select count(*) from PRFacultyStudentTagging"
                    + " where id.instituteid=:instituteid "
                    + " and id.fstid=:fstid "
                    + " and id.studentid=:studentid "
                    + " and coalesce(deactive,'N')='N'";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).setParameter("fstid", fstid)
                    .setParameter("studentid", studentid).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getMainInstidRegidSubid(String instituteid, String registrationid, String subjectid) {
        List list = new ArrayList();
        try {
            String qry = " select new map( cl.id.maininstituteid as maininstituteid,cl.id.mainregistrationid as mainregistrationid,cl.id.mainsubjectid as mainsubjectid)"
                    + " from SubjectForCommonLoad cl where cl.id.instituteid=:instituteid and cl.id.registrationid=:registrationid and cl.id.subjectid=:subjectid and coalesce(cl.status,'A')='A'";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid)
                    .setParameter("subjectid", subjectid).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getChildInstidRegidSubid(String maininstituteid, String mainregistrationid, String mainsubjectid) {
        List list = new ArrayList();
        try {
            String qry = " select new map( cl.id.instituteid as childinstituteid,cl.id.registrationid as childregistrationid,cl.id.subjectid as childsubjectid)"
                    + " from SubjectForCommonLoad cl where cl.id.maininstituteid=:maininstituteid and cl.id.mainregistrationid=:mainregistrationid and cl.id.mainsubjectid=:mainsubjectid and coalesce(cl.status,'A')='A'";
            list = getHibernateSession().createQuery(qry).
                    setParameter("maininstituteid", maininstituteid).
                    setParameter("mainregistrationid", mainregistrationid)
                    .setParameter("mainsubjectid", mainsubjectid).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getAutidOrEquivalentsubjectid(String instituteid, String registrationid, String subjectid, byte stynumber, String studentid) {
        List list = new ArrayList();
        try {
            String qry = "  select new map(sscm.auditsubject as auditsubject,sscm.equivalentsubjectid as equivalentsubjectid) from StudentSubjectChoiceMaster sscm"
                    + "  where sscm.id.instituteid=:instituteid and sscm.id.registrationid=:registrationid"
                    + "  and sscm.id.studentid=:studentid and sscm.id.stynumber=:stynumber and sscm.id.subjectid=:subjectid";
            list = getHibernateSession().createQuery(qry).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("studentid", studentid).
                    setParameter("stynumber", stynumber).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List findAllStudentsFromStudentSubjectChoiceMaster(String instituteid, String academicyear, String programid, String sectionid, String registrationid, String subjectid, String subjecttypeid, String stynumber, String subsectionid) {
        List list = new ArrayList();
        try {
            StringBuilder query = new StringBuilder("select a.acadyear, a.programid, a.branchid, b.id.stynumber, b.id.subjectid, a.enrollmentno, a.name, c.subsectionid, a.sectionid, pm.programcode, bm.branchcode, sm.subjectcode, sm.subjectdesc"
                    + " from StudentMaster a, StudentSubjectChoiceMaster b, StudentSubjectChoiceDetail c, ProgramMaster pm, BranchMaster bm, SubjectMaster sm"
                    + " where a.instituteid = b.id.instituteid"
                    + " and a.studentid = b.id.studentid"
                    + " and a.instituteid = c.id.instituteid"
                    + " and a.studentid = c.id.studentid"
                    + " and b.id.instituteid = c.id.instituteid"
                    + " and b.id.registrationid = c.id.registrationid"
                    + " and b.id.stynumber = c.id.stynumber"
                    + " and b.id.studentid = c.id.studentid"
                    + " and b.id.subjectid = c.id.subjectid"
                    + " and a.programid = pm.id.programid"
                    + " and a.instituteid = pm.id.instituteid"
                    + " and a.branchid = bm.id.branchid"
                    + " and a.instituteid = bm.id.instituteid"
                    + " and a.programid = bm.id.programid"
                    + " and b.id.subjectid = sm.id.subjectid"
                    + " and b.id.instituteid = sm.id.instituteid"
                    + " and a.instituteid = :instituteid"
                    + " and b.id.registrationid = :registrationid");
            if (!academicyear.equalsIgnoreCase("All")) {
                query.append(" and a.acadyear = :acadyear");
            }
            if (!programid.equalsIgnoreCase("All")) {
                query.append(" and a.programid = :programid");
            }
            if (!sectionid.equalsIgnoreCase("All")) {
                query.append(" and a.sectionid = :sectionid");
            }
            if (!subjectid.equalsIgnoreCase("All")) {
                query.append(" and b.id.subjectid = :subjectid");
            }
            query.append(" and :acadyear = :acadyear and :programid = :programid and :sectionid = :sectionid");
            query.append("  and ( :subjectid <> 'all' or (  :subjectid = 'all' and b.basketid in ( select bm.id.basketid from BasketMaster bm where bm.id.instituteid = :instituteid");
            query.append(")))");//
            if (!stynumber.equalsIgnoreCase("All")) {
                query.append(" and b.id.stynumber = :stynumber");
            }
            if (!subsectionid.equalsIgnoreCase("All")) {
                query.append(" and c.subsectionid = :subsectionid");
            }
            query.append(" and :subsectionid = :subsectionid and :stynumber = :stynumber ");
            query.append(" and coalesce(b.subjectrunning,'N')='Y'"
                    + " and coalesce(b.deactive,'N')='N'"
                    + " group by a.acadyear, a.programid, a.branchid, b.id.stynumber, b.id.subjectid, a.enrollmentno, a.name, a.sectionid, c.subsectionid, pm.programcode, bm.branchcode, sm.subjectcode, sm.subjectdesc"
                    + " order by a.acadyear, a.programid, a.branchid, b.id.stynumber, b.id.subjectid, a.enrollmentno, a.name, a.sectionid, c.subsectionid, pm.programcode, bm.branchcode, sm.subjectcode, sm.subjectdesc");
            list = getHibernateSession().createQuery(query.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("acadyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("stynumber", new Byte(stynumber)).
                    setParameter("subsectionid", subsectionid).list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
