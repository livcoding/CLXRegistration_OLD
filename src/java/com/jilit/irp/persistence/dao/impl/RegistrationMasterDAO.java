package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RegistrationMasterIDAO;
import com.jilit.irp.persistence.dto.RegistrationMasterId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.TT_SlotMaster;
import com.jilit.irp.persistence.dto.TT_SlotMasterId;
import java.io.Serializable;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

public class RegistrationMasterDAO extends HibernateDAO implements RegistrationMasterIDAO {

    private static final Log log = LogFactory.getLog(RegistrationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from RegistrationMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from RegistrationMaster as tname where tname.id.instituteid = ? order by tname.registrationdatefrom desc", new Object[]{instituteid});
    }

    public Collection<?> getRegistrationMaster(String instituteid) {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from RegistrationMaster as tname where coalesce (tname.deactive, 'N') = 'N' and coalesce (tname.lockregistration, 'N') = 'N' and tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public Collection<?> getRegistrationMasterList(String instituteid) {
        log.info("Retrieving all RegistrationMaster records via Hibernate from the database");
        return this.find("from RegistrationMaster as tname where  tname.id.instituteid = ? order by tname.registrationdatefrom desc", new Object[]{instituteid});
    }

    public List getRegistrationCodeGridData(final String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select rm.id.instituteid,rm.id.registrationid, "
                + " rm.registrationcode,rm.registrationdesc, "
                + " rm.registrationdatefrom,rm.registrationdateto, "
                + " rm.preventfromdate,rm.preventenddate, "
                + " rm.attendancefromdate,rm.attendancetodate, "
                + " coalesce(rm.deactive,'N') as deactive ,rm.lockregistration "
                + " from RegistrationMaster rm "
                + " where rm.id.instituteid=:instituteid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getRegistrationEditData(final String instituteid, final String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select rm.id.instituteid,rm.id.registrationid, "
                + " rm.registrationcode,rm.registrationdesc, "
                + " rm.registrationdatefrom,rm.registrationdateto, "
                + " rm.registrationcaption,rm.placeofregistration, "
                + " rm.registrationtype,rm.parentregistrationid, "
                + " rm.evenoddflag,rm.academicyear,rm.gradesheetcaption,coalesce(rm.excludeinattendance,'N') as excludeinattendance, "
                + " rm.attendancefromdate,rm.attendancetodate, "
                + " rm.examperiodfrom,coalesce(rm.allowbackpaperreg,'N') as allowbackpaperreg, "
                + " rm.courseregistrationdatefrom,rm.courseregistrationdateto,coalesce(rm.finalized,'N') as finalized,coalesce(rm.excludeinattendance,'N') as excludeinattendance, "
                + " rm.preventfromdate,rm.preventenddate,coalesce(rm.preventcompleted,'N') as preventcompleted,coalesce(rm.preventbroadcast,'N') as preventbroadcast, "
                + " rm.supplregeventfrom,rm.supplregeventupto, coalesce(rm.excludeinsrs,'N') as excludeinsrs, coalesce(rm.excludeindatesheet,'N') as excludeindatesheet,  "
                + " coalesce(rm.excludeininvigdutyseatplan,'N') as excludeininvigdutyseatplan, "
                + " coalesce(rm.excludeinclasstimetable,'N') as excludeinclasstimetable,coalesce(rm.registrationcomplete,'N') as registrationcomplete , "
                + " coalesce(rm.lockregistration,'N') as lockregistration,coalesce(rm.deactive,'N') as deactive,rm.courseregcompleted,rm.courseregcompleted,rm.courseregcompleted, "
                + " rm.courseregcompleted,rm.courseregbroadcast, "
                + " rm.gipregfrom,rm.gipregistrationupto,rm.gipregbroadcast "
                + " from RegistrationMaster rm "
                + " where rm.id.instituteid=:instituteid "
                + " and rm.id.registrationid=:registrationid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllRegistrationCodeDesc(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid,rm.registrationcode,rm.registrationdesc from RegistrationMaster rm where rm.id.instituteid = :instituteid order by rm.registrationcode desc ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RegistrationMaster.class, id);
    }

    public int checkIfChildExist(final String instituteid, final String registrationid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                RegistrationMaster regmaster = (RegistrationMaster) session.get(RegistrationMaster.class, new RegistrationMasterId(instituteid, registrationid));
                int i1 = Integer.parseInt(session.createFilter(regmaster.getOfferedodsubjecttaggings(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(regmaster.getStudentnrsubjectses(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(regmaster.getProgramsubjecttaggings(), "select count(*)").list().get(0).toString());
                int i4 = Integer.parseInt(session.createFilter(regmaster.getPrfacultyevents(), "select count(*)").list().get(0).toString());
                int i5 = Integer.parseInt(session.createFilter(regmaster.getStudentregistrations(), "select count(*)").list().get(0).toString());
                int i6 = Integer.parseInt(session.createFilter(regmaster.getPrsubjectfinalizationcriterias(), "select count(*)").list().get(0).toString());
                int i7 = Integer.parseInt(session.createFilter(regmaster.getLoaddistributiongrants(), "select count(*)").list().get(0).toString());
                int i8 = Integer.parseInt(session.createFilter(regmaster.getInstituteregistrationeventss(), "select count(*)").list().get(0).toString());
                int i9 = Integer.parseInt(session.createFilter(regmaster.getStudenthosteldetails(), "select count(*)").list().get(0).toString());
                int i10 = Integer.parseInt(session.createFilter(regmaster.getRegistrationmasterdetail(), "select count(*)").list().get(0).toString());

                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public Collection<?> getRegsitrationCode(final String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(RegistrationMaster.class, "master").createAlias("master.tt_timetables", "tt").setFetchMode("tt_timetables", FetchMode.JOIN);
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.deactive", "N"));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid")).add(Projections.property("master.id.registrationid").as("registrationid")).add(Projections.property("master.registrationcode").as("registrationcode")).add(Projections.property("master.registrationdesc").as("registrationdesc")).add(Projections.property("tt.startdate").as("startdate")).add(Projections.property("tt.enddate").as("enddate")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public Collection<?> getRegsitrationCode(final String instituteid, final String[] registrationid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(RegistrationMaster.class, "master").createAlias("master.tt_timetables", "tt").setFetchMode("tt_timetables", FetchMode.JOIN);
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.deactive", "N"));
                criteria.add(Restrictions.not(Restrictions.in("master.id.registrationid", registrationid)));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid")).add(Projections.property("master.id.registrationid").as("registrationid")).add(Projections.property("master.registrationcode").as("registrationcode")).add(Projections.property("master.registrationdesc").as("registrationdesc")).add(Projections.property("tt.startdate").as("startdate")).add(Projections.property("tt.enddate").as("enddate")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public Collection<?> timeSlotGridData(final String instituteid, final String registrationid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(TT_SlotMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));
                criteria.add(Restrictions.eq("master.deactive", "N"));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid").as("instituteid")).add(Projections.property("master.id.registrationid").as("registrationid")).add(Projections.property("master.id.days").as("days")).add(Projections.property("master.id.slotid").as("slotid")).add(Projections.property("master.slotno").as("slotno")).add(Projections.property("master.slotcode").as("slotcode")).add(Projections.property("master.starttime").as("starttime")).add(Projections.property("master.endtime").as("endtime")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            }
        });
        return list;
    }

    public int checkIfChildExist1(final TT_SlotMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                TT_SlotMaster tt_SlotMaster = (TT_SlotMaster) session.get(TT_SlotMaster.class, id);
                int i1 = ((Integer) session.createFilter(tt_SlotMaster.getTt_programwiseslots(), "select count(*)").list().get(0)).intValue();
                int i2 = ((Integer) session.createFilter(tt_SlotMaster.getTt_timetableallocations(), "select count(*)").list().get(0)).intValue();
                return i1 + i2;

            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getDaysBySlotId(final String slotid, final String registration_no, final String instituteid) {
        String qry = "select a.id.days,a.slotcode,a.starttime,a.endtime from TT_SlotMaster a"
                + " where a.id.slotid='" + slotid + "' and a.id.registrationid='" + registration_no + "' and a.id.instituteid='" + instituteid + "'";
        return getHibernateTemplate().find(qry);
    }

    public List<String> doValidate(final RegistrationMaster registrationMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(RegistrationMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", registrationMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("registrationcode", registrationMaster.getRegistrationcode()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.registrationid", registrationMaster.getId().getRegistrationid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Semester Code ! ");
        }
        return errors;
    }

    public List<Object[]> getRegistrationCodeForAcademicDataReset(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.registrationid,a.registrationcode,a.registrationdesc,a.registrationdatefrom,a.registrationdateto,a.registrationtype from RegistrationMaster a "
                + " where a.id.instituteid=:instituteid and coalesce(a.lockregistration, 'N')='N' "
                + " and coalesce(a.deactive, 'N')='N' and coalesce(a.finalized, 'N')='N' and coalesce(a.registrationcomplete, 'N')='N' "
                + " and exists(select 1 from InstituteRegistrationEvents ir "
                + " where ir.id.instituteid = a.id.instituteid and a.id.registrationid = ir.id.registrationid and ir.preregistrationallowed = 'Y') order by a.registrationdatefrom desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    public List<Object[]> getRegCodeForOST(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.registrationid,a.registrationcode,a.registrationdesc,a.registrationdatefrom,a.registrationdateto,a.registrationtype from RegistrationMaster a "
                + " where a.id.instituteid=:instituteid and coalesce(a.lockregistration, 'N')='N' and a.registrationtype<>'F' "
                + " and coalesce(a.deactive, 'N')='N' and coalesce(a.finalized, 'N')='N' and coalesce(a.registrationcomplete, 'N')='N' "
                + " and exists(select 1 from InstituteRegistrationEvents ir "
                + " where ir.id.instituteid = a.id.instituteid and a.id.registrationid = ir.id.registrationid and ir.preregistrationallowed = 'Y') order by a.registrationdatefrom desc");
        try {
            return getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getFromToDate(String instituteid, String registrationid) {

        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.registrationdatefrom,rm.registrationdateto  "
                + " from RegistrationMaster rm  "
                + " where rm.id.instituteid=:instituteid and rm.id.registrationid=:registrationid");
        try {
            return getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public List<Object[]> getRegistrationCodeForAcademicDataReset(String instituteid) {
//        List list = null;
//        StringBuilder sb = new StringBuilder();
//        sb.append(" select a.id.registrationid,a.registrationcode,a.registrationdesc,a.registrationdatefrom,a.registrationdateto from RegistrationMaster a "
//                + " where a.id.instituteid=:instituteid ");
//        try {
//            return getHibernateSession().createQuery(sb.toString()).
//                    setParameter("instituteid", instituteid).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    public List getRegistrationCode_1(final String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid, rm.registrationcode, rm.registrationdesc from RegistrationMaster rm "
                + " where exists (select 1 from StudentRegistration sr where rm.id.instituteid = sr.id.instituteid and "
                + " rm.id.registrationid = sr.id.registrationid ) and rm.id.instituteid = :instituteid and coalesce (rm.deactive, 'N') = 'N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getregcaption(String instituteid, String registrationid) {
        List list = null;
        String query = " select  coalesce (rm.registrationcaption,rm.registrationcode) as caption,rm.academicyear from RegistrationMaster rm where rm.id.instituteid='" + instituteid + "' and rm.id.registrationid='" + registrationid + "' ";
        list = getHibernateTemplate().find(query);
        return list;
    }

    public List getprogduration(final String instituteid, final String acadyear, final String programid, final String branchid) {
        List list = null;
        try {
            list = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {

                    String qryString = " select round((MX.ENDSTY-MX.STARTSTY)/decode(MX.STYPATTERN,'S',2,'T',3,1)) as DURATION from ProgramMaxSty mx "
                            + " where MX.INSTITUTEID= '" + instituteid + "' and MX.ACADEMICYEAR='" + acadyear + "' and MX.PROGRAMID='" + programid + "' "
                            + " and  MX.BRANCHID='" + branchid + "' ";

                    return session.createSQLQuery(qryString).list();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getRegistrationNumber(String institute, String programid, String branchid, String reg_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Select  sm.studentid,sm.enrollmentno,sm.name,sm.stynumber,sr.allowforsubjectchoice,sr.feespaid,sr.hostelallow,sr.preventfreezed,sr.docverification,sr.transportallow,"
                + " sr.allowforfeepay,sr.id.registrationid,sr.stynumber  from StudentMaster sm ,StudentRegistration sr where  sr.id.studentid = sm.studentid and sr.id.instituteid =sm.instituteid and sr.id.instituteid =:institute "
                + " and sr.id.registrationid =:reg_id and sr.regallow = 'Y' and  sm.programid =:programid and sm.branchid =:branchid  and sm.instituteid=:institute "
                + " and exists (select 1 from  StudentRegistration_info sa where  sa.programid =:programid and sa.branchid =:branchid  and sa.id.instituteid=:institute "
                + " and sm.studentid = sa.id.studentid and sa.id.registrationid =:reg_id and sa.id.instituteid = sm.instituteid and sa.programid = sm.programid and sa.branchid = sm.branchid "
                + " and  sr.id.registrationid =  sa.id.registrationid ) ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("institute", institute).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).
                    setParameter("reg_id", reg_id).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAllRegistrationCodeForAddDropSubject(List instituteids) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select im.instituteid,im.institutecode,rm.id.registrationid, rm.registrationcode, rm.registrationdesc "
                + " from InstituteRegistrationEvents ire, RegistrationMaster rm,InstituteMaster im"
                + " where rm.id.instituteid=ire.id.instituteid "
                + " and rm.id.registrationid=ire.id.registrationid "
                + " and ire.preregistrationallowed='Y' "
                + " and rm.id.instituteid in(:instituteids) "
                + " and coalesce(rm.deactive,'N')='N' "
                + " and im.instituteid=ire.id.instituteid"
                + " and coalesce(im.deactive,'N')='N' "
                + " order by im.institutecode,rm.registrationcode desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameterList("instituteids", instituteids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getAllRegistrationCode(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.id.registrationid, rm.registrationcode, rm.registrationdesc "
                + " from InstituteRegistrationEvents ire, RegistrationMaster rm "
                + " where rm.id.instituteid=ire.id.instituteid "
                + " and rm.id.registrationid=ire.id.registrationid "
                + " and ire.preregistrationallowed='Y' "
                + " and rm.id.instituteid =:instituteid "
                + " and coalesce(rm.deactive,'N')='N' "
                + " order by rm.registrationcode desc");
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

    public List getRegistrationCodeForSupplementary(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select rm.id.registrationid, rm.registrationcode,rm.parentregistrationid "
                + " from RegistrationMaster rm, InstituteRegistrationEvents ire "
                + " where rm.id.instituteid = :instituteid "
                + " and ire.id.instituteid = rm.id.instituteid "
                + " and ire.id.registrationid = rm.id.registrationid "
                + " and ire.supplymentryrequestallowed = 'Y'"
                + " and coalesce(rm.deactive,'N')='N' "
                + " and rm.parentregistrationid is not null");
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

    @Override
    public boolean saveOrUpdateObjectList(List list) {
        boolean flag = false;
        getHibernateTemplate().flush();
        Session session = null;
        Transaction tx = null;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }
            for (int i = 0; i < list.size(); i++) {
                session.merge(list.get(i));
            }
            tx.commit();
            if (tx.wasCommitted()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.clear();
                session.close();
                getHibernateTemplate().flush();
            }
        }
        return flag;
    }

    public List getRegCodeForStudentsFeePaymentStatus(List instituteid, String reg_Type) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select im.instituteid,im.institutecode,rm.id.registrationid,rm.registrationcode,rm.registrationdesc from RegistrationMaster rm,InstituteMaster im where rm.id.instituteid in(:instituteid) ");
        if (reg_Type.equals("S") || reg_Type.equals("P")) {
            sb.append(" and rm.registrationtype =:reg_Type ");
        } else {
            sb.append(" and :reg_Type = :reg_Type ");
        }
        sb.append("  and exists(select ire.id.registrationid from InstituteRegistrationEvents ire where ire.id.instituteid=rm.id.instituteid "
                + " and ire.id.registrationid = rm.id.registrationid "
                + " and ire.preregistrationallowed='Y' and ire.attendentryallowed='Y' )  and im.instituteid=rm.id.instituteid"
        );
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameterList("instituteid", instituteid).
                    setParameter("reg_Type", reg_Type).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getRegistrationCodeForSummer(List instituteids) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select im.instituteid,im.institutecode,rm.id.registrationid, rm.registrationcode,rm.registrationdesc "
                + " from RegistrationMaster rm, InstituteRegistrationEvents ire,InstituteMaster im "
                + " where rm.id.instituteid in(:instituteids) "
                + " and im.instituteid =rm.id.instituteid"
                + " and ire.id.instituteid = rm.id.instituteid "
                + " and ire.id.registrationid = rm.id.registrationid "
                + " and coalesce(rm.deactive,'N')='N' "
                + " and rm.registrationtype = 'S' order by rm.registrationcode desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameterList("instituteids", instituteids).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    @Override
    public String getRegistrationIdByCode(String instituteid, String registrationcode) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select rm.id.registrationid "
                + " from RegistrationMaster rm where rm.id.instituteid=:instituteid and rm.registrationcode=:registrationcode ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationcode", registrationcode).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        if (list != null && list.size() > 0) {
            return list.get(0).toString();
        } else {
            return "";
        }

    }
}
