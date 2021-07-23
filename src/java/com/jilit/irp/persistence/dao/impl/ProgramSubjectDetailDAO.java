/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ProgramSubjectDetailIDAO;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.FeeStructureIndividualId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 *
 * @author sunny.singhal
 */
public class ProgramSubjectDetailDAO extends HibernateDAO implements ProgramSubjectDetailIDAO {

    private static final Log log = LogFactory.getLog(ProgramSubjectDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramSubjectDetail records via Hibernate from the database");
        return this.find("from ProgramSubjectDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramSubjectDetail.class, id);
    }

    @Override
    public List getSubjectComponentIds(String instituteid, String registrationid, String subjectid, String acadyear, String programid, String sectionid, String subsectionid, String stytypeid, String basketid, String stynumber) {
        List list = null;
        String qry = "select distinct b.id.subjectcomponentid from ProgramSubjectDetail b where b.id.programsubjectid in (select a.id.programsubjectid from ProgramSubjectTagging a"
                + " where a.id.instituteid='" + instituteid + "' "
                + " and a.id.registrationid='" + registrationid + "' "
                + " and a.academicyear='" + acadyear + "' "
                + " and a.programid='" + programid + "' "
                + " and a.sectionid='" + sectionid + "' "
                + " and a.stynumber='" + stynumber + "' "
                + " and a.basketid='" + basketid + "' "
                + " and a.subjectid='" + subjectid + "' "
                + " and a.subjectrunning='Y' "
                + " and coalesce(a.deactive,'N')='N' "
                + " and a.id.instituteid=b.id.instituteid "
                + " and a.id.registrationid=b.id.registrationid ) "
                + " and coalesce(b.deactive,'N')='N'";
        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    /**
//     * Method Used To Get LOV Data For Registration LOV
//     * @param instituteid
//     * @return
//     */
//    @Override
    public List getAllRegistrationCodeForSupplementary(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid, rm.registrationcode, rm.registrationdesc,rm.parentregistrationid "
                + " from InstituteRegistrationEvents ire, RegistrationMaster rm "
                + " where rm.id.instituteid=ire.id.instituteid "
                + " and rm.id.registrationid=ire.id.registrationid "
                + " and ire.supplymentryrequestallowed='Y' "
                + " and rm.id.instituteid =:instituteid "
                + " and coalesce(rm.deactive,'N')='N' "
                + " and rm.parentregistrationid is not null"
                + " order by rm.registrationcode ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getRegConfermOrNot(String instituteid, String registrationid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select coalesce(rm.regconfirmation,'N'),coalesce(rm.regconfirmatiodate,'') "
                + " from StudentRegistration rm "
                + " where rm.id.instituteid=:instituteid "
                + " and rm.id.registrationid=:registrationid "
                + " and rm.id.studentid=:studentid  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getParametervalueFOrLateRegistration(String instituteid, String moduleid, String parameterid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.parametervalue "
                + " from Parameters rm "
                + " where rm.id.instituteid=:instituteid "
                + " and rm.id.moduleid=:moduleid "
                + " and rm.id.parameterid=:parameterid  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("moduleid", moduleid).setParameter("parameterid", parameterid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getParameterForAuditSubjectCredit(String instituteid, String moduleid, String parameterid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.parametervalue "
                + " from Parameters rm "
                + " where rm.id.instituteid=:instituteid "
                + " and rm.id.moduleid=:moduleid "
                + " and rm.id.parameterid=:parameterid  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("moduleid", moduleid).setParameter("parameterid", parameterid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getRegFromAndToDate(String instituteid, String registrationid, String programid, String academicYear, String branchid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rmd.regdatefrom ,coalesce(rmd.extendedtilldate,rmd.regdateto),rmd.lateregfeeamount "
                + " from RegistrationMasterDetail rmd "
                + " where rmd.id.instituteid=:instituteid and rmd.id.registrationid=:registrationid"
                + " and rmd.id.programid=:programid  and rmd.id.branchid=:branchid "
                + " and rmd.id.academicyear=:academicYear  ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).setParameter("programid", programid).setParameter("branchid", branchid).setParameter("academicYear", academicYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getFeeHeadIdFOrLateRegistration(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fhd.id.feeheadid from FeeHeads fhd where fhd.id.instituteid =:instituteid and fhd.feetype = 'R' ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public String getFeeHeadIdForSupplementrySubjEntry(String instituteid, String feehead) {
        List list = null;
        String value = "";
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fhd.id.feeheadid from FeeHeads fhd where fhd.id.instituteid =:instituteid and fhd.feehead=:feehead ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("feehead", feehead).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return value;

    }

    public List getElectiveSubjectForSwap(String instituteid, String registrationid, String subjecttypeid, byte credit) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid,sm.subjectcode,sm.subjectdesc,pst.departmentid from SubjectMaster sm,ProgramSubjectTagging pst where exists (select 1 from ProgramSubjectTagging pst where pst.id.instituteid=sm.id.instituteid and pst.subjectid=sm.id.subjectid "
                + " and exists (select 1 from BasketMaster bm where bm.id.instituteid = pst.id.instituteid and bm.id.basketid=pst.basketid and bm.subjecttypeid=:subjecttypeid) "
                + " and pst.id.instituteid=:instituteid and pst.id.registrationid=:registrationid  and pst.subjectrunning='Y' and pst.credits =:credit "
                + " )  and pst.id.instituteid=sm.id.instituteid and pst.subjectid=sm.id.subjectid and pst.id.registrationid =:registrationid order by subjectcode         ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("subjecttypeid", subjecttypeid).setParameter("credit", credit).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getCurrencyIdForAddDrop() {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select cm.currencyid from CurrencyMaster cm  where cm.accountingcurrency ='Y' ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List checkRecordInFeestructureindivisual(String instituteid, String registrationid, String feeheadid, String studentid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select fsti.feeamount, fsti.feefinalized, fsti.id.currencyid, fsti.id.stytypeid, fsti.id.quotaid, fsti.id.slno, fsti.id.stynumber from  FeeStructureIndividual fsti where  fsti.id.instituteid=:instituteid and fsti.id.registrationid =:registrationid and fsti.id.feeheadid =:feeheadid and fsti.id.studentid = :studentid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("feeheadid", feeheadid).setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    @Override
    public void updateFeeStructureIndividual(FeeStructureIndividualId id, BigDecimal newamount) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(" update FeeStructureIndividual fsi set fsi.feeamount=:feeamount"
                    + " where fsi.id.instituteid=:instituteid"
                    + " and fsi.id.feeheadid=:feeheadid"
                    + " and fsi.id.studentid=:studentid "
                    + " and fsi.id.registrationid=:registrationid"
                    + " and fsi.id.stytypeid=:stytypeid"
                    + " and fsi.id.stynumber=:stynumber"
                    + " and fsi.id.quotaid=:quotaid"
                    + " and fsi.id.currencyid=:currencyid"
                    + " and fsi.id.slno=:slno");
            Query query = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", id.getInstituteid()).setParameter("registrationid", id.getRegistrationid()).
                    setParameter("feeheadid", id.getFeeheadid()).setParameter("studentid", id.getStudentid()).setParameter("stytypeid", id.getStytypeid())
                    .setParameter("stynumber", id.getStynumber()).setParameter("quotaid", id.getQuotaid()).setParameter("currencyid", id.getCurrencyid()).setParameter("slno", id.getSlno())
                    .setParameter("feeamount", newamount);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
    }

}
