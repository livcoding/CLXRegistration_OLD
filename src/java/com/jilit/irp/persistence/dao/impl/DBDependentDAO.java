package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.DBDependentIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ankur.goyal
 */
public class DBDependentDAO extends HibernateDAO implements DBDependentIDAO {

    @Override
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List getSubjectWiseStudentCountDataPre(String instituteid, String companyid, String stynumber, String programid, String branchid, String registrationid, String stytypeid) {
        List list = null;
        final String query = "select distinct bm.basketcode, s.subjectcode, S.SUBJECTDESC, PST.STYNUMBER, pst.credits , listagg(B.BRANCHCODE,',')within group(order by B.BRANCHCODE) Branches, "
                + "(select count(distinct fst.studentid) from   StudentSubjectChoiceMaster fst,StudentMaster sft  where   FST.INSTITUTEID=SFT.INSTITUTEID "
                + " and FST.INSTITUTEID=S.INSTITUTEID and pst.programid=sft.programid "
                + " and fst.studentid=sft.studentid and FST.SUBJECTID=S.SUBJECTID and fst.stynumber = pst.stynumber "
                + " and FST.INSTITUTEID = pst.INSTITUTEID  and FST.REGISTRATIONID=PST.REGISTRATIONID  and FST.SUBJECTID=PST.SUBJECTID  "
                + " and fst.stytypeid  not in (select st.stytypeid from StyType st where ST.INSTITUTEID=FST.INSTITUTEID and st.stytype='SUP')) TotalRegStudents , "
                + " (select count(distinct fst.studentid)  from  STUDENTSUBJECTCHOICEMASTER fst, STUDENTMASTER sft  where   FST.INSTITUTEID=SFT.INSTITUTEID "
                + " and FST.INSTITUTEID=S.INSTITUTEID and pst.programid=sft.programid "
                + " and fst.studentid=sft.studentid  and fst.stynumber = pst.stynumber  and FST.SUBJECTID=S.SUBJECTID "
                + " and FST.INSTITUTEID = pst.INSTITUTEID "
                + " and FST.REGISTRATIONID=PST.REGISTRATIONID    and FST.SUBJECTID=PST.SUBJECTID   "
                + "  and fst.stytypeid in (select st.stytypeid from StyType st where ST.INSTITUTEID=FST.INSTITUTEID and st.stytype='SUP')) TotalExamStudents, "
                + " (select listagg(employeename,',')within group(order by employeename) from FacultySubjectTagging fst, TT_TimeTableAllocation sft, V_Staff v where FST.INSTITUTEID=SFT.INSTITUTEID and FST.INSTITUTEID=S.INSTITUTEID "
                + " and pst.sectionid=fst.sectionid and pst.programid=fst.programid and V.EMPLOYEEID=SFT.STAFFID and FST.TTREFERENCEID=SFT.TTREFERENCEID and FST.SUBJECTID=S.SUBJECTID and FST.INSTITUTEID=PST.INSTITUTEID and FST.REGISTRATIONID=PST.REGISTRATIONID "
                + " and FST.SUBJECTID=PST.SUBJECTID and FST.REGISTRATIONID=SFT.REGISTRATIONID and FST.SUBJECTCOMPONENTID=SFT.SUBJECTCOMPONENTID and  FST.SUBJECTID=SFT.SUBJECTID "
                + " and FST.STYNUMBER=PST.STYNUMBER) Staff "
                + " from SubjectMaster s, ProgramSubjectTagging pst, BranchMaster b, ProgramWiseSubSection pws, BasketMaster bm "
                + " where bm.instituteid=pst.instituteid and bm.basketid=pst.basketid and S.INSTITUTEID = PST.INSTITUTEID and S.SUBJECTID = PST.SUBJECTID "
                + " and PST.INSTITUTEID = PWS.INSTITUTEID and PST.ACADEMICYEAR = PWS.ACADEMICYEAR and PST.PROGRAMID=PWS.PROGRAMID and PWS.SECTIONID=PST.SECTIONID "
                + " and PWS.STYNUMBER = PST.STYNUMBER and PWS.INSTITUTEID = B.INSTITUTEID and PWS.BRANCHID=b.branchid "
                + "  and PST.INSTITUTEID='" + instituteid + "'"
                + "  and pst.PROGRAMID in (" + programid + ") "
                + " and B.BRANCHID in (" + branchid + ") "
                + "  and pst.registrationid='" + registrationid + "' "
                + "  and pst.stynumber in(" + stynumber + ") "
                + " and (select count(distinct fst.studentid) from   StudentSubjectChoiceMaster fst,StudentMaster sft  where   FST.INSTITUTEID=SFT.INSTITUTEID "
                + " and FST.INSTITUTEID=S.INSTITUTEID and pst.programid=sft.programid "
                + " and fst.studentid=sft.studentid and FST.SUBJECTID=S.SUBJECTID and fst.stynumber = pst.stynumber "
                + " and FST.INSTITUTEID = pst.INSTITUTEID  and FST.REGISTRATIONID=PST.REGISTRATIONID  and FST.SUBJECTID=PST.SUBJECTID  "
                + " and fst.stytypeid  not in (select st.stytypeid from StyType st where ST.INSTITUTEID=FST.INSTITUTEID and st.stytype='SUP')) > 0"
                + "group by s.subjectcode,bm.basketcode, S.SUBJECTDESC, PST.STYNUMBER, pst.credits ,S.INSTITUTEID,S.SUBJECTID,PST.REGISTRATIONID ,PST.SUBJECTID,PST.INSTITUTEID, pst.sectionid,pst.programid";

        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    return session.createSQLQuery(query).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSubjectWiseStudentCountData(String instituteid, String companyid, String stynumber, String programid, String branchid, String registrationid, String stytypeid) {
        List list = null;
        // and FST.STYNUMBER = PST.STYNUMBER in feature it is needed than added on line 5302
        final String query = "select distinct bm.basketcode,s.subjectcode, S.SUBJECTDESC, pst.credits, PST.STYNUMBER , listagg(B.BRANCHCODE,',')within group(order by B.BRANCHCODE) Branches, "
                + "(select count(distinct studentid) from FacultySubjectTagging fst, FacultyStudentTagging sft where FST.INSTITUTEID = SFT.INSTITUTEID and FST.INSTITUTEID = S.INSTITUTEID "
                + " and pst.programid=fst.programid and fst.fstid = sft.fstid and FST.SUBJECTID = S.SUBJECTID and FST.INSTITUTEID = PST.INSTITUTEID and FST.REGISTRATIONID = PST.REGISTRATIONID and FST.SUBJECTID = PST.SUBJECTID "
                + " and FST.STYNUMBER = PST.STYNUMBER  and fst.stytypeid  not in (select st.stytypeid from StyType st where ST.INSTITUTEID=FST.INSTITUTEID and st.stytype='SUP')) TotalRegStudents , "
                + " (select count(distinct studentid) from FacultySubjectTagging fst, FacultyStudentTagging sft where FST.INSTITUTEID=SFT.INSTITUTEID and FST.INSTITUTEID=S.INSTITUTEID "
                + " and pst.programid=fst.programid and fst.fstid=sft.fstid and FST.SUBJECTID=S.SUBJECTID and FST.INSTITUTEID = PST.INSTITUTEID and FST.REGISTRATIONID=PST.REGISTRATIONID and FST.SUBJECTID=PST.SUBJECTID "
                + "  and fst.stytypeid in (select st.stytypeid from StyType st where ST.INSTITUTEID=FST.INSTITUTEID and st.stytype='SUP')) TotalExamStudents, "
                + " (select listagg(employeename,',')within group(order by employeename) from FacultySubjectTagging fst, TT_TimeTableAllocation sft, V_Staff v where FST.INSTITUTEID=SFT.INSTITUTEID and FST.INSTITUTEID=S.INSTITUTEID "
                + " and pst.sectionid=fst.sectionid and pst.programid=fst.programid and V.EMPLOYEEID=SFT.STAFFID and FST.TTREFERENCEID=SFT.TTREFERENCEID and FST.SUBJECTID=S.SUBJECTID and FST.INSTITUTEID=PST.INSTITUTEID and FST.REGISTRATIONID=PST.REGISTRATIONID "
                + " and FST.SUBJECTID=PST.SUBJECTID and FST.REGISTRATIONID=SFT.REGISTRATIONID and FST.SUBJECTCOMPONENTID=SFT.SUBJECTCOMPONENTID and  FST.SUBJECTID=SFT.SUBJECTID "
                + " and FST.STYNUMBER=PST.STYNUMBER) Staff "
                + " from SubjectMaster s, ProgramSubjectTagging pst, BranchMaster b, ProgramWiseSubSection pws,BasketMaster bm "
                + " where bm.instituteid=pst.instituteid and bm.basketid=pst.basketid and S.INSTITUTEID = PST.INSTITUTEID and S.SUBJECTID = PST.SUBJECTID "
                + " and PST.INSTITUTEID = PWS.INSTITUTEID and PST.ACADEMICYEAR = PWS.ACADEMICYEAR and PST.PROGRAMID=PWS.PROGRAMID and PWS.SECTIONID=PST.SECTIONID "
                + " and PWS.STYNUMBER = PST.STYNUMBER and PWS.INSTITUTEID = B.INSTITUTEID and PWS.BRANCHID=b.branchid "
                + "  and PST.INSTITUTEID='" + instituteid + "'"
                + "  and pst.PROGRAMID in (" + programid + ") "
                + " and B.BRANCHID in (" + branchid + ") "
                + "  and pst.registrationid='" + registrationid + "' "
                + "  and pst.stynumber in(" + stynumber + ") "
                + "group by s.subjectcode,bm.basketcode, S.SUBJECTDESC, PST.STYNUMBER, pst.credits ,S.INSTITUTEID,S.SUBJECTID,PST.REGISTRATIONID ,PST.SUBJECTID,PST.INSTITUTEID, pst.sectionid,pst.programid";

        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    return session.createSQLQuery(query).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGridDataRegistration_infoBased(String instituteid, String registrationid, List academicyearlist, List programlist) {
        List list = null;
        final String query = " select distinct sri.programid, sri.branchid,sri.id.registrationid, sri.academicyear, pm.programcode, bm.branchcode,"
                + "( select  concat(concat(concat(concat(rmd.regdatefrom,','),concat(rmd.regdateto,',')),concat(concat(rmd.extendedtilldate,','),concat(rmd.remarks,','))),rmd.lateregfeeamount)"
                + " from  RegistrationMasterDetail rmd where sri.id.instituteid = rmd.id.instituteid  and sri.programid = rmd.id.programid  and sri.branchid = rmd.id.branchid  and sri.id.registrationid = rmd.id.registrationid "
                + " and sri.academicyear=rmd.id.academicyear and  pm.id.instituteid = rmd.id.instituteid and pm.id.programid = rmd.id.programid and rmd.id.instituteid = bm.id.instituteid) as rmd "
                + " from StudentRegistration_info sri,ProgramMaster pm,BranchMaster bm where"
                + " sri.id.instituteid = pm.id.instituteid  and sri.programid = pm.id.programid "
                + " and sri.id.instituteid = bm.id.instituteid  and sri.branchid = bm.id.branchid "
                + " and  pm.id.instituteid = bm.id.instituteid and pm.id.programid = bm.id.programid "
                + " and sri.id.instituteid =:instituteid and sri.programid in(:programlist) and sri.id.registrationid=:registrationid"
                + " and sri.academicyear in(:academicyearlist) order by sri.academicyear,pm.programcode,bm.branchcode";
        try {
            list = getHibernateSession().createQuery(query).
                    setParameter("instituteid", instituteid).
                    setParameterList("academicyearlist", academicyearlist).
                    setParameterList("programlist", programlist).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGridDataPSTBased(String instituteid, String registrationid, List academicyearlist, List programlist) {
        List list = null;
        final String query = " select distinct pst.programid, bm.id.branchid,pst.id.registrationid, pst.academicyear, pm.programcode, bm.branchcode,"
                + " ( select  concat(concat(concat(concat(rmd.regdatefrom,','),concat(rmd.regdateto,',')),concat(concat(rmd.extendedtilldate,','),concat(rmd.remarks,','))),rmd.lateregfeeamount)"
                + " from  RegistrationMasterDetail rmd where pst.id.instituteid = rmd.id.instituteid  and pst.programid = rmd.id.programid  and bm.id.branchid = rmd.id.branchid  and pst.id.registrationid = rmd.id.registrationid "
                + "  and pst.academicyear=rmd.id.academicyear and  pm.id.instituteid = rmd.id.instituteid and pm.id.programid = rmd.id.programid and rmd.id.instituteid = bm.id.instituteid) as rmd "
                + " from ProgramSubjectTagging pst,ProgramMaster pm,BranchMaster bm where "
                + " pst.id.instituteid = pm.id.instituteid  and pst.programid = pm.id.programid "
                + " and pst.id.instituteid = bm.id.instituteid and coalesce(bm.deactive,'N')='N'"
                + " and  pm.id.instituteid = bm.id.instituteid and pm.id.programid = bm.id.programid "
                + " and pst.id.instituteid =:instituteid and pst.programid in(:programlist) and pst.id.registrationid=:registrationid"
                + " and pst.academicyear in(:academicyearlist) order by  pst.academicyear,pm.programcode,bm.branchcode";

        try {
            list = getHibernateSession().createQuery(query).
                    setParameter("instituteid", instituteid).
                    setParameterList("academicyearlist", academicyearlist).
                    setParameterList("programlist", programlist).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkStudentDueAmount(String instituteid, String studentid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select coalesce(sum (ff.dueamount),0) as dueamount  from FeeFinalization ff where ff.id.instituteid=:instituteid and ff.id.studentid=:studentid and ff.id.registrationid=:registrationid "
                + " and not exists( select 1 from StudentFeePaySPCLApproval sa where sa.id.instituteid=:instituteid and sa.id.studentid=:studentid and sa.id.registrationid=:registrationid "
                + " and sysdate<=sa.approvalupto and sa.id.feeheadid=ff.id.feeheadid)"
                + " and ff.dueamount > (select coalesce(max(p.parametervalue),0) from Parameters p where p.id.parameterid='N4.1' and p.id.instituteid=:instituteid and p.id.moduleid='MOD08000011')");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("studentid", studentid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List checkStudentDueAmountFeeHeadWise(String instituteid, String studentid, String registrationid, String feeheadid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select coalesce(sum (ff.feeamount),'0'),coalesce(sum (ff.dueamount),'0'), (select count(sa.id.instituteid) from StudentFeePaySPCLApproval sa where sa.id.instituteid=:instituteid and sa.id.studentid=:studentid and sa.id.registrationid=:registrationid "
                + " and sysdate<=approvalupto and sa.id.feeheadid=ff.id.feeheadid) as spclapproval"
                + " from FeeFinalization ff where ff.id.instituteid=:instituteid and ff.id.studentid=:studentid and ff.id.registrationid=:registrationid "
                + " and  ff.id.feeheadid =:feeheadid group by ff.feeamount, ff.dueamount,ff.id.feeheadid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("studentid", studentid).
                    setParameter("feeheadid", feeheadid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
