/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

//import com.jilit.irp.bso.biz.BusinessService;
////import com.jilit.irp.data.FilterInfoData;
//
//
import com.jilit.irp.data.Option;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentMasterIDAO;
import com.jilit.irp.persistence.dto.BranchChangeRequest;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.V_Staff;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.Type;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Jaswinder Singh
 */
public class StudentMasterDAO extends HibernateDAO implements StudentMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentMasterDAO.class);
    Session session1 = null;
    Transaction tx1 = null;

    public Collection<?> findAll() {
        log.info("Retrieving all StudentMaster records via Hibernate from the database");
        return this.find("from StudentMaster as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all StudentMaster records via Hibernate from the database");
        return this.find("from StudentMaster as tname where tname.instituteid = ? ", new Object[]{instituteid});
    }

    public Collection<?> findAll(String instituteid, String acdYr, String program, String branch/*,String filert,String value */) {
        log.info("Retrieving all StudentMaster records via Hibernate from the database");
        String str = "";
        str += "from StudentMaster as tname where tname.instituteid = '" + instituteid + "'";
        if (!acdYr.equalsIgnoreCase("All")) {
            str += " and tname.acadyear='" + acdYr + "'";
        }
        if (!program.equalsIgnoreCase("All")) {
            str += " and tname.programid='" + program + "'";
        }
        if (!branch.equalsIgnoreCase("All")) {
            str += " and tname.branchid='" + branch + "'";
        }
        return this.find(str);
    }

    public List studentListData(String instituteid, String acdYr, String program, String branch/*,String filert,String value */) {
        List list = new ArrayList();
        String str = "";
        str += "select distinct s.studentid,s.instituteid,s.acadyear,s.admissionyear,s.enrollmentno,s.name,s.rank,s.stynumber,s.dateofbirth,s.gender,s.fathersname,s.mothersname,"
                + " s.bloodgroup,pm.programdesc,br.branchdesc,sc.category "
                + " from StudentMaster s,ProgramMaster pm,BranchMaster br,StudentCategory sc,SectionMaster sec "
                + " where s.programid=pm.id.programid and s.branchid=br.id.branchid and s.studentcategory.id.categoryid=sc.id.categoryid "
                + " and s.instituteid='" + instituteid + "'";
        if (!acdYr.equalsIgnoreCase("All")) {
            str += " and s.acadyear='" + acdYr + "'";
        }
        if (!program.equalsIgnoreCase("All")) {
            str += " and s.programid='" + program + "'";
        }
        if (!branch.equalsIgnoreCase("All")) {
            str += " and s.branchid='" + branch + "'";
        }
        try {
            list = getHibernateTemplate().find(str);
//            list = getHibernateSession(false).createQuery(str).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
//           if(!filert.equalsIgnoreCase("All")){
//               sb.append(" and tname.branchid='"+branch+"'");
//         }
//        return this.find("from StudentMaster as tname where tname.instituteid = ? ", new Object[]{instituteid});
        return list;
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentMaster.class, id);
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((StudentMaster) record);
    }

    public List<String> doValidate(final StudentMaster master, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        /*Unique Key Constraint on instituteid and Shortname*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentMaster.class);
                criteria.add(Restrictions.eq("instituteid", master.getInstituteid()));
                criteria.add(Restrictions.eq("enrollmentno", master.getEnrollmentno().trim()));
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("studentid", master.getStudentid()));   //Do not check for itself when updating record

                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Enrollment No. Found");
        }
        return errors;
    }

    public Collection<?> getActiveDeactiveStudent(final String memberid) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                List l = null;
                Criteria criteria = session.createCriteria(StudentMaster.class, "master");
                criteria.add(Restrictions.eq("master.studentid", memberid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.studentid")).add(Projections.property("master.activestatus")));
                l = criteria.list();
                return l;
            }
        });
        return list;
    }

    public Collection<?> getActiveDeactiveEmployee(final String memberid) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                List l = null;
                Criteria criteria = session.createCriteria(V_Staff.class, "master");
                criteria.add(Restrictions.eq("master.employeeid", memberid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.employeeid")).add(Projections.property("master.deactive")));
                l = criteria.list();
                return l;
            }
        });
        return list;
    }

    public ArrayList totalStudentCount(final String instid, final String acadmicyear, final String programid,
            final String branchid, final String orderby) {
        String qry = "select sm.id.studentid "
                + " from StudentMaster sm "
                + " where sm.instituteid = '" + instid + "' "
                + " and sm.acadyear = '" + acadmicyear + "'"
                + " and sm.programid= '" + programid + "'"
                + " and sm.branchid= '" + branchid + "'"
                + " and coalesce(sm.activestatus,'A')='A' "
                + " order by sm." + orderby.toLowerCase() + "";

        System.err.println("Query :- " + qry);
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        System.err.println("dddddddd" + l.size());
        return l;
    }

    public List coustomizeTotalStudentCount(final String instid, final String acadmicyear, final String programid, final String branchid, final String rwp, final String qrp, final String xrp) {
        String qry = "select sm.id.studentid "
                + " from StudentMaster sm "
                + " where sm.instituteid = '" + instid + "' "
                + " and sm.acadyear = '" + acadmicyear + "'"
                + " and sm.programid= '" + programid + "'"
                + " and sm.branchid= '" + branchid + "'"
                + " and coalesce(sm.activestatus,'A')='A' "
                + " order by (((sm.finalentrancetestscore * " + rwp + " ) /100) + ((sm.qlyexammarkspercentage * " + qrp + " ) / 100) + ((sm.xthmarkspercentage * " + xrp + ") / 100)), sm.enrollmentno ";
        return getHibernateTemplate().find(qry);
    }

    public String updateSectionSubSection(final List<StudentMaster> studentMasters) {
        String retStr = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();
            for (int i = 0; i < studentMasters.size(); i++) {
                System.err.println("************* value" + i);
                session.update((StudentMaster) studentMasters.get(i));
            }
            retStr = "Success";
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retStr = "Error in tx update";
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retStr;
    }

    public List getGridData(String instituteid, String acadYear, String programid, String branchid) {
        List list = null;
        String qry = " select distinct count(sm.studentid ) as noofstu,sec.sectioncode,subsec.subsectioncode,subsec.id.sectionid,subsec.id.subsectionid "
                + " from StudentMaster sm ,SectionMaster sec,ProgramWiseSubsection subsec "
                + " where  sm.instituteid =sec.id.instituteid and sm.instituteid =subsec.id.instituteid and sm.acadyear=subsec.id.academicyear "
                + " and sm.programid=subsec.id.programid and sm.subsectionid =subsec.id.subsectionid and sm.stynumber=subsec.id.stynumber "
                + " and sm.sectionid =sec.id.sectionid   and sm.sectionid =subsec.id.sectionid "
                + " and sm.instituteid='" + instituteid + "' "
                + " and sm.acadyear='" + acadYear + "'  "
                + " and sm.programid='" + programid + "' "
                + " and sm.branchid in ('" + branchid + "') "
                + " and coalesce(sm.activestatus, 'A')='A' "
                + " group by sec.sectioncode,subsec.subsectioncode,subsec.id.sectionid,subsec.id.subsectionid ";

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStudentData(String instituteid, String studentid) {
        List list = null;
        List retlist = new ArrayList();
        try {
            String qry = " select sm.programid,sm.branchid,sm.acadyear,sm.stynumber,sm.enrollmentno,sm.sectionid,sm.subsectionid,sm.quotaid,sm.instituteid "
                    + " from StudentMaster sm where sm.studentid='" + studentid + "' ";
            list = getHibernateTemplate().find(qry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getStudentDetailSubsectionWise(String instituteid, String acadYear, String programid, String branchid, String secid, String subsecid, String stynumber) {
        List list = null;
        String qry = " select sm.enrollmentno ,sm.name "
                + " from StudentMaster sm "
                + " where sm.instituteid='" + instituteid + "' "
                + " and sm.acadyear='" + acadYear + "'  "
                + " and sm.programid='" + programid + "' "
                + " and sm.stynumber='" + stynumber + "' "
                + " and sm.branchid in ('" + branchid + "') "
                + " and sm.sectionid='" + secid + "' "
                + " and sm.subsectionid='" + subsecid + "' "
                + " and coalesce(sm.activestatus, 'A')='A' ";

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllSubjectChangeData(String instituteid, String programid) {
        List list = null;
        String qry = " select sb.credit, sb.id.subjectid, sb.id.programid, sb.id.frombranchid,"
                + " sb.id.tobranchid, bfrom.branchdesc,  bfrom.branchcode, bto.branchdesc, bto.branchcode, dm.department, "
                + "dm.departmentcode, dm.id.departmentid, s.subjectdesc, s.subjectcode from SubjectsForBranchChange sb, "
                + "SubjectMaster s, BranchMaster bfrom, BranchMaster bto, DepartmentMaster dm "
                + " where sb.id.instituteid=s.id.instituteid and sb.id.subjectid=s.id.subjectid "
                + " and sb.id.instituteid=bfrom.id.instituteid and sb.id.programid=bfrom.id.programid and sb.id.frombranchid=bfrom.id.branchid "
                + " and sb.id.instituteid=bto.id.instituteid and sb.id.programid=bto.id.programid and sb.id.tobranchid=bto.id.branchid and sb.departmentid=dm.id.departmentid and  sb.id.programid='" + programid + "' and   sb.id.instituteid='" + instituteid + "'  ";

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Collection<?> getStudentListforLabel(final String[] studentid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentMaster.class, "master").createAlias("master.studentadddresses", "sa").setFetchMode("studentaddress", FetchMode.JOIN);

                criteria.add(Restrictions.in("master.studentid", studentid));
                //  add(Restrictions.in("master.acadyear", academicyear)).
                //  add(Restrictions.in("master.programid", programid)).
                //add(Restrictions.in("master.branchid", branchid)).
                //add(Restrictions.in("master.stynumber", stynumber));

                criteria.setProjection(Projections.projectionList().add(Projections.property("master.studentid").as("studentid")).add(Projections.property("master.name").as("name")).add(Projections.property("sa.caddress1").as("caddress1")).add(Projections.property("sa.caddress2").as("caddress2")).add(Projections.property("sa.caddress3").as("caddress3")).add(Projections.property("sa.cdistrict").as("cdistrict")).add(Projections.property("sa.cpostoffice").as("cpostoffice")).add(Projections.property("sa.ccityname").as("ccityname")).add(Projections.property("sa.cstatename").as("cstatename")).add(Projections.property("sa.cpin").as("cpin")));

                criteria.addOrder(Order.asc("master.name"));

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

            }
        });
        return list;

    }

    public ArrayList getProgramSectionBranchStudentData(final String instituteid) {

        System.err.println("SUBRATA V#");
        final ArrayList retObj = (ArrayList) getHibernateTemplate().execute(new HibernateCallback() {

            public ArrayList doInHibernate(Session session) throws HibernateException, SQLException {
                String whereclause = null;

                whereclause = " coalesce(activestatus,'A')='A' and instituteid='" + instituteid + "'";
                String qry1 = "select distinct acadyear ,acadyear from StudentMaster  where " + whereclause + "";

                String qry2 = "select distinct programid, programid from StudentMaster where " + whereclause + " and acadyear =";

                String qry3 = "select distinct sectionid, sectionid from StudentMaster where  " + whereclause + " and acadyear =";

                String qry4 = "select distinct stynumber, stynumber from StudentMaster where  " + whereclause + " and acadyear =";

                String qry5 = "select distinct name, studentid from StudentMaster where  " + whereclause + " and acadyear =";

                String qry6 = "select distinct sectioncode, sectioncode from SectionMaster where  instituteid='" + instituteid + "' and sectionid =";

                String qry7 = "select distinct programcode, programcode from ProgramMaster where  instituteid='" + instituteid + "' and programid =";

                ArrayList acadPrgSectStyList = null;
                List<Object[]> acdlist = session.createQuery(qry1 + "order by acadyear").list();
                acadPrgSectStyList = new ArrayList();
                for (int j = 0; j < acdlist.size(); j++) {
                    List<Object[]> prglist = session.createQuery(qry2 + "'" + acdlist.get(j)[0] + "'" + "order by programid").list();
                    List programList = new ArrayList();
                    String acadkey = acdlist.get(j)[1].toString();
                    for (int i = 0; i < prglist.size(); i++) {
                        List<Object[]> seclist = session.createQuery(qry3 + "'" + acdlist.get(j)[0] + "'" + " and programid='" + prglist.get(i)[0] + "'" + "order by sectionid").list();
                        List sectionList = new ArrayList();

                        List<Object[]> programcodelist = session.createQuery(qry7 + "'" + prglist.get(i)[0] + "'").list();
                        System.err.println("programlist-----++222222222++++++++++++++++++:" + programcodelist.get(0)[0]);
                        String prykey = acadkey + ":" + prglist.get(i)[1].toString() + "/" + prglist.get(i)[0].toString();
                        // String prykey =  prglist.get(i)[1].toString();
                        for (int k = 0; k < seclist.size(); k++) {
                            List<Object[]> stylist = session.createQuery(qry4 + "'" + acdlist.get(j)[0] + "'" + " and programid='" + prglist.get(i)[0] + "'" + " and sectionid='" + seclist.get(k)[0] + "'" + "order by stynumber").list();
                            List styList = new ArrayList();
                            System.err.println("seclist-----++++++++++++++++++++:" + seclist.get(k)[0]);
                            List<Object[]> sectioncodelist = session.createQuery(qry6 + "'" + seclist.get(k)[0] + "'").list();
                            System.err.println("seclist-----++111111111111++++++++++++++++++:" + sectioncodelist.get(0)[0]);
                            sectioncodelist.get(0);
                            String seckey = prykey + ":" + seclist.get(k)[1].toString() + "/" + seclist.get(k)[0].toString();

                            for (int l = 0; l < stylist.size(); l++) {
                                List<Object[]> namelist = session.createQuery(qry5 + "'" + acdlist.get(j)[0] + "'" + " and programid='" + prglist.get(i)[0] + "'" + " and sectionid='" + seclist.get(k)[0] + "'" + " and stynumber='" + stylist.get(l)[0] + "'" + "order by name").list();
                                String stykey = seckey + ":" + stylist.get(l)[1].toString();
                                //styList.add(new Option(stylist.get(l)[0].toString(), stykey));
                                List nameList = new ArrayList();
                                System.err.println("++++++++++++:" + stykey);

                                for (int m = 0; m < namelist.size(); m++) {
                                    String namekey = stykey + ":" + namelist.get(m)[1].toString();
                                    nameList.add(new Option(namelist.get(m)[0].toString(), namekey));
                                    System.err.println("++++++++++++Name Key:" + namekey);
                                }
                                styList.add(new Option(stylist.get(l)[0].toString(), stykey, nameList));
                            }

                            //sectionList.add(new Option(seclist.get(k)[0].toString(), seckey, styList));
                            sectionList.add(new Option(sectioncodelist.get(0)[0].toString(), seckey, styList));
                        }
                        // programList.add(new Option(prglist.get(i)[0].toString(), prykey, sectionList));
                        programList.add(new Option(programcodelist.get(0)[0].toString(), prykey, sectionList));
                    }
                    acadPrgSectStyList.add(new Option(acdlist.get(j)[0].toString(), acadkey, programList));
                }
                System.err.println("++++++++++++" + acadPrgSectStyList.size());
                return acadPrgSectStyList;

            }
        });
        System.err.println("********" + retObj.size());
        return retObj;
    }

    public List getStudentInfoUpdateSummary(final String instituteid, final String academicyear) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qry = "select program, branch, upd_cnt, tot_cnt-upd_cnt,  tot_cnt, stynumber from  "
                        + "(select b.programid, c.branchid, b.programcode program, c.branchcode branch, a.stynumber,"
                        + " (select count (*) c from StudentMaster s where s.regstatus = 'Y' and s.activestatus = 'A'"
                        + " and s.programid = b.programid and s.branchid = c.branchid ) upd_cnt, count (*) tot_cnt"
                        + " from StudentMaster a, ProgramMaster b, BranchMaster c,"
                        + " Academicyear d  where a.instituteid = b.instituteid and a.instituteid = c.instituteid "
                        + " and b.instituteid = c.instituteid and a.branchid = c.branchid  "
                        + " and a.programid = b.programid and a.branchid = c.branchid  and a.instituteid = d.instituteid "
                        + " and b.instituteid=d.instituteid  and c.instituteid=d.instituteid "
                        + " and A.ACADEMICYEAR = d.academicyear  and a.activestatus = 'A' and a.instituteid = '" + instituteid + "' "
                        + " and A.ACADEMICYEAR='" + academicyear + "' "
                        + " group by b.programid, c.branchid, b.programcode, c.branchcode, "
                        + " a.stynumber order by b.programcode, c.branchcode) ";

                return session.createSQLQuery(qry).list();
            }
        });
        return list;
    }

    public List getNotSavedDataInformation() {
        List list = null;
        String qryString = " select id.informationid, informationdetail, informationdesc from StudentDataInformation where coalesce(deactive,'N')='N' ";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int checkIfChildExist(final String studentid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentMaster master = (StudentMaster) session.get(StudentMaster.class, studentid);
                int i1 = ((Integer) session.createFilter(master.getFacultystudenttaggings(), "select count(*)").list().get(0)).intValue();
                int i2 = ((Integer) session.createFilter(master.getPrfacultystudenttaggings(), "select count(*)").list().get(0)).intValue();
                int i3 = ((Integer) session.createFilter(master.getStudentdisciplinaryactions(), "select count(*)").list().get(0)).intValue();
                int i4 = ((Integer) session.createFilter(master.getStudenthosteldetails(), "select count(*)").list().get(0)).intValue();
                int i5 = ((Integer) session.createFilter(master.getStudentnrsubjectses(), "select count(*)").list().get(0)).intValue();
                int i6 = ((Integer) session.createFilter(master.getStudentregistrations(), "select count(*)").list().get(0)).intValue();
                int i7 = ((Integer) session.createFilter(master.getStudentleavedetails(), "select count(*)").list().get(0)).intValue();
                int i8 = ((Integer) session.createFilter(master.getStudentactivitydetails(), "select count(*)").list().get(0)).intValue();
                int i9 = ((Integer) session.createFilter(master.getUpdatestynumbers(), "select count(*)").list().get(0)).intValue();

                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public Collection<?> getStudentRegistrationSummaryList(final String registrationid) {

        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentRegistration.class, "master").createAlias("master.studentmaster", "sr").setFetchMode("studentmaster", FetchMode.JOIN).createAlias("sr.branchmaster", "bm").setFetchMode("branchmaster", FetchMode.JOIN);
                //.setFetchMode("bm.programmaster", FetchMode.JOIN)
                criteria.createAlias("bm.programmaster", "pm").setFetchMode("programmaster", FetchMode.JOIN);
                //criteria.createAlias("sr.studentsubjectchoicemasters", "sscm");
                //.setFetchMode("studentsubjectchoicemasters", FetchMode.JOIN);
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));

                criteria.setProjection(Projections.projectionList().add(Projections.property("sr.programid").as("PROGRAMID")).add(Projections.property("sr.branchid").as("BRANCHID")).add(Projections.property("pm.programcode").as("PROGRAMCODE")).add(Projections.property("bm.branchcode").as("BRANCHCODE")).add(Projections.property("master.stynumber").as("STYNUMBER")) //.add(Projections.property("master.allotedbranch").as("allotedbranch"))
                        //.add(Projections.property("master.allotedinstitute").as("allotedinstitute"))
                        .add(Projections.groupProperty("sr.programid")).add(Projections.groupProperty("sr.branchid")).add(Projections.groupProperty("pm.programcode")).add(Projections.groupProperty("bm.branchcode")).add(Projections.groupProperty("master.stynumber")) //.add(Projections.groupProperty("master.allotedbranch"))
                        //.add(Projections.groupProperty("master.allotedinstitute"))
                        .add(Projections.count("master.id.studentid").as("TOTALSTUDENT")) // .add(Projections.count("master.id.studentid").as("registered"))
                        // .add(Projections.count("master.id.studentid").as("feepaid"))
                        .add(Projections.sqlProjection("count({alias}.regallow) as REGISTERED ", new String[]{"REGISTERED"}, new Type[]{Hibernate.INTEGER})).add(Projections.sqlProjection("count({alias}.regconfirmation) as RegConfirmed ", new String[]{"RegConfirmed"}, new Type[]{Hibernate.INTEGER})) //Count(decode (b.Regallow,'Y',b.studentid,null)),
                //.add(Projections.groupProperty("master.hostelcodealloted"))
                );
                criteria.addOrder(Order.asc("pm.programcode"));
                criteria.addOrder(Order.asc("bm.branchcode"));
                criteria.addOrder(Order.asc("master.stynumber"));

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();

                // criteria.addOrder(Order.asc("master.name"));
                // return criteria.list();
            }
        });
        return list;
    }

    public Collection<?> getRegistrationAllowedList(final String programid, final String branchid, final String registrationid, final byte stynumber, final String parameter) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StudentRegistration.class, "master").createAlias("master.studentmaster", "sr").setFetchMode("studentmaster", FetchMode.JOIN).createAlias("sr.branchmaster", "bm").setFetchMode("branchmaster", FetchMode.JOIN);
                criteria.createAlias("bm.programmaster", "pm").setFetchMode("programmaster", FetchMode.JOIN);
                criteria.add(Restrictions.eq("master.id.registrationid", registrationid));
                criteria.add(Restrictions.eq("sr.programid", programid));
                criteria.add(Restrictions.eq("sr.branchid", branchid));
                criteria.add(Restrictions.eq("master.stynumber", stynumber));
                if (parameter.contains("notregistered")) {
                    criteria.add(Restrictions.or(Restrictions.isNull("master.regallow"), Restrictions.eq("master.regallow", "N")));
                } else if (parameter.contains("registered")) {
                    criteria.add(Restrictions.eq("master.regallow", "Y"));
                } else if (parameter.contains("feespaid")) {
                    criteria.add(Restrictions.eq("master.feespaid", "Y"));
                }

                criteria.setProjection(Projections.projectionList().add(Projections.property("sr.enrollmentno").as("enrollmentno")).add(Projections.property("sr.name").as("name")).add(Projections.property("bm.branchcode").as("branchcode")).add(Projections.property("pm.programcode").as("programcode")));
                criteria.addOrder(Order.asc("sr.enrollmentno"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getBatchWiseStudentList(final String[] progamid, final String academicyear) {

        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(StudentMaster.class, "sm");

                criteria.createAlias("sm.branchmaster", "bm");
                criteria.setFetchMode("branchmaster", FetchMode.JOIN);
                criteria.createAlias("bm.programmaster", "pm");
                criteria.setFetchMode("programmaster", FetchMode.JOIN);
                criteria.createAlias("sm.sectionmaster", "secm");
                criteria.setFetchMode("sectionmaster", FetchMode.JOIN);

                criteria.createAlias("pm.programwisesubsections", "pws");
                criteria.setFetchMode("programwisesubsection", FetchMode.JOIN);

                criteria.add(Restrictions.eqProperty("pws.id.academicyear", "sm.acadyear"));
                criteria.add(Restrictions.eqProperty("pws.id.stynumber", "sm.stynumber"));
                criteria.add(Restrictions.eqProperty("pws.id.sectionid", "sm.sectionid"));
                criteria.add(Restrictions.eqProperty("pws.id.subsectionid", "sm.subsectionid"));
                criteria.add(Restrictions.eqProperty("pws.id.instituteid", "sm.instituteid"));
                criteria.add(Restrictions.eqProperty("pws.id.programid", "sm.programid"));
                criteria.add(Restrictions.eqProperty("pws.id.instituteid", "secm.id.instituteid"));
                criteria.add(Restrictions.eqProperty("pws.id.sectionid", "secm.id.sectionid"));

                criteria.add(Restrictions.eq("sm.acadyear", academicyear));
                criteria.add(Restrictions.or(Restrictions.isNull("sm.activestatus"),
                        (Restrictions.eq("sm.activestatus", new String("A")))));
                criteria.add(Restrictions.in("pm.id.programid", progamid));

                criteria.setProjection(Projections.projectionList().add(Projections.property("sm.enrollmentno").as("enrollmentno")).add(Projections.property("sm.studentid").as("studentid")).add(Projections.property("sm.name").as("name")).add(Projections.property("sm.enrollmentno").as("registrationnumber")).add(Projections.property("pm.programcode").as("programcode")).add(Projections.property("bm.branchcode").as("branchcode")).add(Projections.property("secm.sectioncode").as("sectioncode")).add(Projections.property("sm.acadyear").as("acadyear")).add(Projections.property("sm.subsectionid").as("subsectionid")).add(Projections.property("pws.subsectioncode").as("subsectioncode")).add(Projections.groupProperty("sm.enrollmentno")).add(Projections.groupProperty("sm.name")).add(Projections.groupProperty("sm.enrollmentno")).add(Projections.groupProperty("pm.programcode")).add(Projections.groupProperty("bm.branchcode")).add(Projections.groupProperty("secm.sectioncode")).add(Projections.groupProperty("sm.acadyear")).add(Projections.groupProperty("sm.studentid")).add(Projections.groupProperty("sm.subsectionid")).add(Projections.groupProperty("pws.subsectioncode")));

                criteria.addOrder(Order.asc("pm.programcode"));
                criteria.addOrder(Order.asc("secm.sectioncode"));
                criteria.addOrder(Order.asc("sm.subsectionid"));
                criteria.addOrder(Order.asc("sm.enrollmentno"));

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                System.err.println("HHHH-------------------" + criteria.list().size());
                return criteria.list();
            }
        });

        return list;
    }

    public List getAllStudentsName1(String instituteid, String academicyear, String programid, String secId) {
        List list = null;
        String qryString = "select sm.studentid, sm.enrollmentno, sm.name from StudentMaster sm"
                + " where sm.instituteid=:instituteid and  coalesce(sm.activestatus,'A')='A'"
                + " and sm.acadyear=:academicyear"
                + " and sm.programid=:programid and sm.sectionid=:secId ";
        try {
            list = getHibernateSession().createQuery(qryString).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("instituteid", instituteid).
                    setParameter("secId", secId).
                    list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getEnrollmentNo_BranchChangeChoiceReport(final String instituteid, final String acadyear) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = null;
                DetachedCriteria suqry = DetachedCriteria.forClass(BranchChangeRequest.class, "bcr");
                suqry.add(Restrictions.eqProperty("bcr.id.instituteid", "master.instituteid"));
                suqry.add(Restrictions.eqProperty("bcr.id.studentid", "master.studentid"));
                suqry.setProjection(Projections.property("bcr.id.studentid"));

                criteria = session.createCriteria(StudentMaster.class, "master");
                criteria.add(Restrictions.eq("master.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.acadyear", acadyear));
                criteria.add(Subqueries.exists(suqry));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.enrollmentno").as("enrollmentno")).add(Projections.property("master.name").as("name")).add(Projections.property("master.studentid").as("studentid")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getStudentInformations(final String instituteid, final String studentid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = null;
                criteria = session.createCriteria(StudentMaster.class, "master");
                criteria.add(Restrictions.eq("master.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.studentid", studentid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.quotaid").as("quotaid")).add(Projections.property("master.acadyear").as("academicyear")).add(Projections.property("master.programid").as("programid")).add(Projections.property("master.sectionid").as("sectionid")).add(Projections.property("master.subsectionid").as("subsectionid")).add(Projections.property("master.branchid").as("branchid")).add(Projections.property("master.nextsectionid").as("nextsectionid")).add(Projections.property("master.nextsubsectionid").as("nextsubsectionid")).add(Projections.property("master.stynumber").as("stynumber")).add(Projections.property("master.name").as("name")).add(Projections.property("master.enrollmentno").as("enrollmentno")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List getsectionWiseStudentReport(String instituteid, String programid, String year, String branchid, String sectionid, String orderby, String gender) {
        List list = null;
        String qry;
        if (orderby.equals("enroll")) {

            qry = " select s.enrollmentno, s.name, sc.categorycode, p.programcode,s.stynumber, (select pws.subsectioncode from ProgramWiseSubsection pws "
                    + " where pws.id.instituteid= s.instituteid and pws.id.academicyear = '" + year + "' and s.programid =pws.id.programid and s.sectionid = pws.id.sectionid "
                    + " and pws.branchid = s.branchid and pws.id.stynumber = s.stynumber and pws.id.subsectionid = s.subsectionid and rownum=1) as batchs, s.gender "
                    + " from StudentMaster s, StudentCategory sc, ProgramMaster p, BranchMaster b "
                    + " where s.instituteid = sc.id.instituteid and s.studcategory = sc.id.categoryid and p.id.instituteid = s.instituteid and p.id.programid = s.programid "
                    + " and s.instituteid = b.id.instituteid and s.programid = b.id.programid and s.branchid = b.id.branchid "
                    + " and s.programid in (" + programid + ") and s.branchid in (" + branchid + ") and s.sectionid in (" + sectionid + ") ";
            if (gender.equals("male")) {
                qry = qry + "  and s.gender = 'M' ";
            } else if (gender.equals("female")) {
                qry = qry + "  and s.gender = 'F' ";
            }
            qry = qry + "  order by s.enrollmentno ";
        } else {
            qry = " select s.enrollmentno, s.name, sc.categorycode, p.programcode,s.stynumber, (select pws.subsectioncode from ProgramWiseSubsection pws "
                    + " where pws.id.instituteid= s.instituteid and pws.id.academicyear = '" + year + "' and s.programid =pws.id.programid and s.sectionid = pws.id.sectionid "
                    + " and pws.branchid = s.branchid and pws.id.stynumber = s.stynumber and pws.id.subsectionid = s.subsectionid and rownum=1) as batchs, s.gender "
                    + " from StudentMaster s, StudentCategory sc, ProgramMaster p, BranchMaster b "
                    + " where s.instituteid = sc.id.instituteid and s.studcategory = sc.id.categoryid and p.id.instituteid = s.instituteid and p.id.programid = s.programid "
                    + " and s.instituteid = b.id.instituteid and s.programid = b.id.programid and s.branchid = b.id.branchid "
                    + " and s.programid in (" + programid + ") and s.branchid in (" + branchid + ") and s.sectionid in (" + sectionid + ") ";

            if (gender.equals("male")) {
                qry = qry + "  and s.gender = 'M' ";
            } else if (gender.equals("female")) {
                qry = qry + "  and s.gender = 'F' ";
            }
            qry = qry + "  order by s.name ";

        }

        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List StudentDocumentStatusReport(final String instituteid, final String programid, final String branchid, final String acadyear, final int styno) {
        List list = null;
        String qry = "  select c.enrollmentno,c.name,c.acadyear,a.documentverified,a.documentkept,b.documentdesc,  pm.programcode,c.stynumber,bm.branchcode "
                + "  from  StudentDocumentDetail  a, StudentDocumentMaster b,  StudentMaster c  ,   ProgramMaster pm, BranchMaster bm where "
                + "  c.studentid=a.id.studentid "
                + "  and a.id.documentid=b.documentid "
                + "  and a.id.programid=c.programid "
                + "  and a.id.studentid=c.studentid "
                + "  and pm.id.programid=c.programid "
                + " and  bm.id.instituteid=c.instituteid"
                + "   and bm.id.programid= c.programid"
                + "  and bm.id.branchid=c.branchid"
                + " and c.programid=a.id.programid    "
                + " and c.instituteid = pm.id.instituteid  "
                + " and  c.branchid=bm.id.branchid  "
                + " and   a.id.programid=  pm.id.programid  "
                + "  and a.id.instituteid='" + instituteid + "' ";
        if (!acadyear.equalsIgnoreCase("All")) {
            qry = qry + " and c.acadyear='" + acadyear + "' ";
        }
        if (!branchid.equalsIgnoreCase("All")) {
            qry = qry + " and c.branchid='" + branchid + "' ";
        }
        if (styno != 0) {
            qry = qry + " and c.stynumber=" + styno + " ";
        }
        if (!programid.equalsIgnoreCase("All")) {
            qry = qry + " and c.programid='" + programid + "' ";
        }

        qry = qry + " order by c.acadyear,c.enrollmentno ";
        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveObjList(List saveList1, List saveList2) {
        boolean flag = false;
        Session session = null;
        Transaction tx = null;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }
            for (int b = 0; b < saveList1.size(); b++) {
                session.saveOrUpdate(saveList1.get(b));
            }

            for (int b = 0; b < saveList2.size(); b++) {
                session.saveOrUpdate(saveList2.get(b));
            }
            tx.commit();
            if (tx.wasCommitted()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            flag = false;
            e.printStackTrace();
        } finally {
            session.clear();
            session.close();
        }
        return flag;
    }

    public List getStudentDataEligibleForUpcomingSemester(String instituteid, String registrationid, String programid, String branchid, String check, String academicyearList) {
        List list = null;
        long nooffailsubject = Long.parseLong(check);
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.instituteid, b.enrollmentno, d.branchcode, ");
        sb.append(" (select max(e.id.stynumber) from StudentResult e where a.id.studentid = e.id.studentid and a.id.instituteid = e.id.instituteid ),");
        sb.append(" d.seqid, count (*) from StudentResult a, StudentMaster b, BranchMaster d ");
        sb.append(" where a.id.instituteid = b.instituteid and a.id.studentid = b.id.studentid ");
        sb.append(" and b.instituteid = d.id.instituteid and b.programid = d.id.programid ");
        sb.append(" and b.branchid = d.id.branchid and b.programid = :programid ");
        sb.append(" and b.instituteid = :instituteid ");
        sb.append(" and a.fail = 'Y' and b.activestatus not in ('C', 'X') ");
        sb.append(" and b.branchid = :branchid ");
        sb.append(" and b.acadyear = :acadyear ");
        sb.append(" and not exists (select 1 from StudentSubjectChoiceMaster ssc where ");
        sb.append(" ssc.id.registrationid = :registrationid ");
        sb.append(" and ssc.basketid in (select bm.id.basketid from BasketMaster bm where bm.id.instituteid=a.id.instituteid and  bm.subjecttypeid in(select st.id.subjecttypeid from SubjectTypeMaster st where st.id.instituteid=bm.id.instituteid and st.subjecttype in('C'))) ");
        sb.append(" and ssc.id.studentid = a.id.studentid and ssc.id.instituteid = a.id.instituteid) ");
        sb.append(" group by a.id.instituteid, b.enrollmentno, d.branchcode, d.seqid, a.id.studentid,b.stynumber ");
        sb.append(" having count(*)  <=:nooffailsubject order by d.seqid,b.stynumber, b.enrollmentno");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).
                    setParameter("nooffailsubject", nooffailsubject).
                    setParameter("acadyear", academicyearList).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAcademicYearReg(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct academicyear.id.academicyear from StudentMaster where coalesce(activestatus,'A')='A' and instituteid =:instituteid order by acadyear desc ");
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

    public List getAcademicYearRegList(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct ay.id.academicyear from Academicyear ay where  coalesce(deactive, 'N')='N'  and instituteid =:instituteid order by academicyear desc ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getProgramCodeReg(String instituteid, String acadYear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct programcode,id.programid from ProgramMaster where id.programid in (select distinct programid from StudentMaster where coalesce(activestatus,'A')='A' and acadyear=:acadyear) and instituteid =:instituteid  ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("acadyear", acadYear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getSecCodeReg(String instituteid, String programId) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sectionmaster.sectioncode,sectionmaster.id.sectionid from StudentMaster where coalesce(activestatus,'A')='A' and instituteid =:instituteid and programid =:programId order by sectionmaster.sectioncode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("programId", programId).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getProgramReportData(final String programid, final String branchid, final String categoryid, final String instituteid, final String reporttype, final String ordertype) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuilder qry = new StringBuilder();

                if ("PCW".equals(reporttype)) {
                    qry.append(" select im.institutecode, pm.programcode , pm.programdesc, cm.categorycode,cm.category,  count(distinct am.studentid) as cmm,"
                            + " (select sum(count(sm.studentid)) from StudentMaster sm where   sm.instituteid= '" + instituteid + "' and sm.programid in (" + programid + ") group by studentid)as total"
                            + " from ProgramMaster pm, StudentMaster am, StudentCategory cm, InstituteMaster im  "
                            + " where pm.instituteid= '" + instituteid + "'  "
                            + " and pm.programid in (" + programid + ")   "
                            + " and am.instituteid = pm.instituteid "
                            + " and pm.programid=am.programid "
                            + " and am.studentcategory = cm.categoryid "
                            + " and am.instituteid = cm.instituteid  ");
                    if (!categoryid.equalsIgnoreCase("All")) {
                        qry.append(" and  am.studentcategory = '" + categoryid + "'");
                    }
                    qry.append("group by im.institutecode,pm.programcode ,pm.programdesc,cm.categorycode, cm.category "
                            + " order by  programcode,categorycode ");
                }
                if ("PBW".equals(reporttype)) {
                    qry.append(" select im.institutecode, pm.programcode , pm.programdesc,bm.branchcode, bm.branchdesc,  cm.categorycode, cm.category,  count(distinct am.studentid) as caProgWiseStudent,"
                            + " (select sum(count(sm.studentid)) from StudentMaster sm where  sm.instituteid= '" + instituteid + "' and sm.programid in (" + programid + ") and sm.branchid in (" + branchid + ") group by studentid)as progTotalStudent "
                            + " from ProgramMaster pm, StudentMaster am, StudentCategory cm, InstituteMaster im , BranchMaster bm "
                            + " where pm.instituteid= '" + instituteid + "'  "
                            + " and pm.programid in (" + programid + ")   "
                            + " and bm.branchid in (" + branchid + ")"
                            + " and am.instituteid=pm.instituteid "
                            + " and am.branchid=bm.branchid  and pm.programid=bm.programid"
                            + " and pm.programid=am.programid "
                            + " and am.studentcategory=cm.categoryid "
                            + " and am.instituteid=cm.instituteid ");
                    if (!categoryid.equalsIgnoreCase("All")) {
                        qry.append(" and  am.studentcategory = '" + categoryid + "'");
                    }
                    qry.append(" group by im.institutecode,pm.programcode ,pm.programdesc,bm.branchcode, bm.branchdesc,cm.categorycode, cm.category "
                            + " order by  programcode,branchcode,categorycode  ");
                }
                return session.createSQLQuery(qry.toString()).list();
            }
        });
        return list;

    }

    public List getEnrollmentNumberOrName(String instituteid, String programid, String acad_year) {
        String qryString = "select sm.studentid, sm.enrollmentno, sm.name from StudentMaster sm  where sm.instituteid = '" + instituteid + "' "
                + "and sm.acadyear='" + acad_year + "' and sm.programid in (" + programid + ") ";
        return getHibernateTemplate().find(qryString);
    }

    @Override
    public List getRegistrationCodeWiseStudentMasterData(List instituteid, List regid, String programid, String academicyear) {
        List list = null;
        Session session = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select sm.id.studentid, sm.name, coalesce(sm.enrollmentno,'- -'), sri.academicyear as acadyear, sri.programid, pm.programcode, sr.stynumber, sri.branchid, bm.branchcode, sri.sectionid, secm.sectioncode,  ");
        qry.append(" sri.subsectionid , sm.groupid,sr.allowforfeepay,sr.feespaid,sr.docverification,sr.quotaid,sr.stytypeid,sm.fathersname, coalesce(sm.rank,'- -'), ");
        qry.append(" (select distinct pwss.subsectioncode from ProgramWiseSubsection pwss "
                + "  where pwss.id.instituteid=sm.instituteid  and pwss.id.academicyear=sri.academicyear and branchid=sri.branchid and pwss.id.sectionid=sri.sectionid and pwss.id.subsectionid=sri.subsectionid and pwss.id.programid=sri.programid"
                + "  and pwss.id.stynumber=sr.stynumber)as subsectioncode,sr.id.instituteid,sr.id.registrationid "
                + " from StudentMaster sm,  StudentRegistration sr,StudentRegistration_info sri, ProgramMaster pm, BranchMaster bm, SectionMaster secm");
        qry.append(" where  sm.instituteid = sr.id.instituteid  and  sm.id.studentid = sr.id.studentid and sri.id.instituteid = pm.id.instituteid  and sri.programid = pm.id.programid  and sri.id.instituteid = bm.id.instituteid  and sri.programid = bm.id.programid  and sri.branchid = bm.id.branchid  and sm.instituteid = secm.id.instituteid ");
        qry.append(" and sm.sectionid=secm.id.sectionid  and sm.instituteid in(:instituteid) ");
        qry.append(" and sri.programid=:programid  and sri.academicyear=:academicyear ");
        qry.append(" and sr.id.instituteid=sri.id.instituteid  and sr.id.registrationid=sri.id.registrationid and sr.id.studentid=sri.id.studentid");
        qry.append(" and sr.id.registrationid in(:regid)  and coalesce(sr.regallow, 'N')='Y' and sm.activestatus='A' ");
        qry.append(" group by sm.instituteid, sri.academicyear, sm.id.studentid, sm.name,  sm.enrollmentno,  sri.programid, pm.programcode,sr.id.instituteid,sr.id.registrationid, sr.stynumber,");
        qry.append(" sri.branchid, bm.branchcode, sri.sectionid, secm.sectioncode, sri.subsectionid,sm.groupid,sr.allowforfeepay,sr.feespaid,sr.docverification,sr.quotaid,sr.stytypeid,sm.fathersname,sm.rank,sr.id.instituteid,sr.stynumber order by sr.stynumber desc,sm.enrollmentno ");
        try {
            session = (Session) getSession();
            list = session.createQuery(qry.toString()).setParameterList("regid", regid).
                    setParameterList("instituteid", instituteid).setParameter("academicyear", academicyear).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            qry = null;
        }
        return list;

    }

    @Override
    public List getStudentLovForAddDropBasic(List instituteid, List regid, String programid, String academicyear) {
        List list = null;
        Session session = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct sm.studentid,  "
                + " sm.name,coalesce (sm.enrollmentno, '- -'),  "
                + " sm.acadyear ,  sm.programid,  "
                + " pm.programcode,sm.stynumber,sm.branchid,bm.branchcode,  "
                + " sm.sectionid,(select secm.sectioncode from SectionMaster secm where  sm.instituteid = secm.id.instituteid and sm.sectionid = secm.id.sectionid) as sectioncode,  "
                + " sm.subsectionid,sm.groupid,'x','x','x',sm.quotaid,  "
                + " (select sty.id.stytypeid from StyType sty where sty.stytype='REG' and sty.id.instituteid=sm.instituteid) as stytypeid,sm.fathersname,  "
                + " coalesce (sm.rank, '- -'),  "
                + " (select distinct pwss.subsectioncode  "
                + " from ProgramWiseSubsection pwss  "
                + " where pwss.id.instituteid = sm.instituteid  "
                + " and pwss.id.academicyear = sm.acadyear  "
                + " and pwss.branchid = sm.branchid  "
                + " and pwss.id.sectionid = sm.sectionid  "
                + " and pwss.id.subsectionid = sm.subsectionid  "
                + " and pwss.id.programid = sm.programid  "
                + " and pwss.id.stynumber = sm.stynumber)as subsectioncode,  "
                + " sm.instituteid,rm.id.registrationid,sm.enrollmentno  "
                + " from StudentMaster sm,RegistrationMaster rm,ProgramMaster pm,  "
                + " BranchMaster bm  "
                + " where sm.instituteid = pm.id.instituteid and sm.programid = pm.id.programid  "
                + " and sm.instituteid = bm.id.instituteid and sm.programid = bm.id.programid and sm.branchid = bm.id.branchid  "
                + " and sm.instituteid in (:instituteid)  "
                + " and sm.programid = :programid  "
                + " and sm.acadyear = :academicyear "
                + " and sm.instituteid = rm.id.instituteid  "
                + " and rm.id.registrationid in (:regid)  "
                + " and sm.activestatus = 'A'  "
                + " and (sm.stynumber='1' or (sm.stynumber='3' and sm.lateralentry='Y') )"
                + " order by sm.stynumber desc, sm.enrollmentno");
        try {
            session = (Session) getSession();
            list = session.createQuery(qry.toString()).setParameterList("regid", regid).
                    setParameterList("instituteid", instituteid).setParameter("academicyear", academicyear).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            qry = null;
        }
        return list;

    }

    public Collection<?> getRegistrationActivity(String instituteid) {
        log.info("Retrieving all Sis_RegistrationActivityMaster records via Hibernate from the database");
        return this.find("from Sis_RegistrationActivityMaster as tname where  coalesce(tname.deactive, 'N')='N'  and tname.id.instituteid = ? ", new Object[]{instituteid});
    }

    public List getStudentActivity(final String instituteid, final String registrationid, final String studentid) {
        List list = null;
        StringBuilder qry = new StringBuilder();
        qry.append(" select b.id.activityid,b.processed,b.allowforregistration,  b.remarks,b.approvaldate, ");
        qry.append(" (select v.employeename from V_Staff v where b.approvalby=v.employeeid) ");
        qry.append(" from  Sis_StudentRegActivities b");
        qry.append(" where b.id.instituteid =:instituteid and b.id.registrationid=:registrationid and b.id.studentid=:studentid ");
        try {
            list = getHibernateSession().createQuery(qry.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("registrationid", registrationid)
                    .setParameter("studentid", studentid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qry = null;
        }

        return list;
    }

    public List getSemesterBranchData(final String instituteid, final String acadyear, final String programid, final String br_1[], final int stynumber) {
        String whereClause = "";
        String branchid = "";
        for (int i = 0; i < br_1.length; i++) {
            branchid = br_1[i];
            if (i > 0) {
                whereClause = whereClause + " or ";
            }
            whereClause = whereClause + " ( sm.programid= '" + programid + "'"
                    + " and sm.branchid  = '" + branchid + "'"
                    + "  )";
        }
        String qry = "select sm  "
                + " from StudentMaster sm "
                + " where sm.instituteid = '" + instituteid + "' "
                + " and sm.acadyear = '" + acadyear + "'"
                + " and sm.stynumber = '" + stynumber + "'"
                + " and coalesce(sm.activestatus,'A')='A'"
                + " and (" + whereClause + ")  order by sm.enrollmentno";
        System.err.println("Query :- " + qry);
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;
    }

    @Override
    public List getStudentUpgradeDegradeReportData(String programid, String branchid, String academicyear, String instituteid, String stynumber, String status, String fromdate, String todate) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.enrollmentno,b.name,a.id.prestynumber,a.updatestynumber,a.id.docmode ");
        sb.append(" from UpdateStyNumber a,StudentMaster b");
        sb.append(" where a.id.studentid= b.studentid ");
        sb.append(" and b.acadyear =:academicyear ");
        sb.append(" and b.programid = :programid ");
        sb.append(" and b.instituteid = :instituteid ");
        sb.append(" and b.stynumber = :stynumber ");
        sb.append(" and b.branchid = :branchid ");
        sb.append(" and entrydate between :fromdate and :todate  ");
        if ("U".equals(status)) {
            sb.append("and a.updatestynumber>a.id.prestynumber ");
        } else if ("N".equals(status)) {
            sb.append(" and a.id.prestynumber=a.updatestynumber ");
        }
        sb.append("order by b.enrollmentno");
        try {
            Date fromdatenew = new SimpleDateFormat("dd/MM/yyyy").parse(fromdate);
            Date todatenew = new SimpleDateFormat("dd/MM/yyyy").parse(todate);
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("instituteid", instituteid).
                    setParameter("fromdate", fromdatenew).
                    setParameter("todate", todatenew).
                    setParameter("stynumber", Byte.parseByte(stynumber)).
                    setParameter("branchid", branchid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAcademicYearForAddDrop(List instituteid, List regid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sri.academicyear from  StudentRegistration_info sri "
                + " where  sri.id.registrationid in (:regid) and  sri.id.instituteid in (:instituteid) "
                + " order by sri.academicyear desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameterList("regid", regid).
                    setParameterList("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getProgramForAddDrop(List instituteid, List regid, String acadyear) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pm.id.programid,pm.programcode,pm.programdesc from  StudentRegistration_info sri , ProgramMaster pm "
                + " where  sri.id.instituteid = pm.id.instituteid  and sri.programid = pm.id.programid "
                + " and sri.id.registrationid in(:regid) and sri.id.instituteid in(:instituteid) "
                + " and sri.academicyear =:acadyear order by pm.programcode");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameterList("regid", regid).
                    setParameterList("instituteid", instituteid).setParameter("acadyear", acadyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentInfo(List instituteid, String enrollmentno, List registrationid, String qryFor, String specialcase) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.programid,sm.acadyear,sm.studentid,sm.enrollmentno,sm.sectionid,sm.subsectionid,(select p.parametervalue from Parameters p where p.id.instituteid in(:instituteid) and p.id.moduleid='MOD08000002' and p.id.parameterid='SUBREG5.3') as modeparameters "
                + " ,(select coalesce(sr.regallow,'N') from StudentRegistration sr where sr.id.instituteid=sm.instituteid and sr.id.studentid=sm.studentid and sr.id.registrationid in(:registrationid)) as regallow"
                + " from StudentMaster sm"
                + " where sm.instituteid in (:instituteid) ");
        if (qryFor.equals("basic") && specialcase.equals("N")) {
            sb.append("and( sm.enrollmentno=:enrollmentno or sm.rank=:enrollmentno)"
                    + " and (sm.stynumber='1' or (sm.stynumber='3' and sm.lateralentry='Y') ) ");
        } else {
            sb.append(" and( sm.enrollmentno=:enrollmentno)");
        }
        try {
            session = (Session) getSession();

            list = session.createQuery(sb.toString()).
                    setParameterList("instituteid", instituteid).
                    setParameter("enrollmentno", enrollmentno).
                    setParameterList("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentInfoForRegPermission(String instituteid, String enrollmentno) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.programid,sm.acadyear,sm.studentid,sm.sectionid"
                + " from StudentMaster sm"
                + " where sm.instituteid=:instituteid and sm.enrollmentno=:enrollmentno");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("enrollmentno", enrollmentno).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentsForSupplementary(String instituteid, String registrationid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.instituteid,sm.studentid,sm.enrollmentno,sm.name,sm.programid,"
                + " sm.acadyear,sm.stynumber,sm.quotaid from StudentMaster sm where sm.instituteid= :instituteid and sm.activestatus='A'  "
                + " and exists(  "
                + " select srd.id.studentid  "
                + " from StudentResultDetail srd  "
                + " where srd.id.instituteid =sm.instituteid  "
                + " and srd.id.registrationid = :registrationid  "
                + " and srd.id.studentid=sm.studentid  "
                + " and srd.fail='Y'"
                + " )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentFeePaymentData(List instituteid, List registrationid, String reg_Type, String Order_By, String duestudent, String lesssubject, String exceedsubject, List feeheadid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.enrollmentno,sm.name,(select sp.semailid from StudentPhone sp where sp.id.studentid=sm.id.studentid) as email, coalesce(sr.regconfirmation,'N'), "
                + " (select sp.scellno from StudentPhone sp where sp.id.studentid=sm.id.studentid) as phone, "
                + " (select count(sc.id.subjectid) from  StudentSubjectChoiceMaster sc where sc.id.studentid=sm.id.studentid  and sc.id.instituteid=sm.instituteid and sc.id.registrationid=sr.id.registrationid and sc.subjectrunning='Y') as noofsub ,"
                + " (select sum(ff.feeamount)  from FeeFinalization ff where ff.id.studentid=sm.id.studentid  and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) as feeamount ,"
                + " (select sum(ff.receiveamount)  from FeeFinalization ff where ff.id.studentid=sm.id.studentid  and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) as recvfeeamount,"
                + " (select sum(ff.dueamount)  from FeeFinalization ff where ff.id.studentid=sm.id.studentid  and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid) ) as duefeeamount, "
                + " (select count(*) from StudentFeePaySPCLApproval ff where ff.id.studentid=sm.id.studentid  and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.approvalupto >= trunc(sysdate) and ff.id.feeheadid in (:feeheadid)) as spclapproval,sm.studentid "
                + " from StudentMaster sm, StudentRegistration sr "
                + " where exists (select sc.id.studentid from StudentSubjectChoiceMaster sc where sc.id.studentid=sm.id.studentid and sc.id.instituteid=sc.id.instituteid and sc.id.registrationid=sr.id.registrationid) "
                + " and sr.id.instituteid=sm.id.instituteid and sr.id.studentid=sm.id.studentid and sr.id.instituteid in(:instituteid) "
                + " and sr.id.registrationid in(:registrationid) and sr.regallow='Y' ");
        if (duestudent.equals("Y")) {
            sb.append(" and (select sum(ff.dueamount) from FeeFinalization ff where ff.id.studentid=sm.id.studentid  and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid) ) > 0 ");
        }
        if (lesssubject.equals("Y")) {
            sb.append(" and (select sum(coalesce(ff.receiveamount,0)) from FeeFinalization ff where ff.id.studentid=sm.id.studentid and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) > (select sum(coalesce(ff.feeamount,0)) from FeeFinalization ff where ff.id.studentid=sm.id.studentid and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) ");
        } else if (exceedsubject.equals("Y")) {
            sb.append(" and (select sum(coalesce(ff.receiveamount,0)) from FeeFinalization ff where ff.id.studentid=sm.id.studentid and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) < (select sum(coalesce(ff.feeamount,0)) from FeeFinalization ff where ff.id.studentid=sm.id.studentid and ff.id.instituteid=sm.instituteid and ff.id.registrationid=sr.id.registrationid and ff.id.feeheadid in (:feeheadid)) ");
        }
        if (Order_By.equals("En")) {
            sb.append("  order by sm.enrollmentno ");
        } else {
            sb.append("  order by sm.name ");
        }

        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameterList("instituteid", instituteid).
                    setParameterList("feeheadid", feeheadid).
                    setParameterList("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentSubCode(List instituteid, List registrationid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sc.id.studentid, sm.subjectcode from SubjectMaster sm,  StudentSubjectChoiceMaster sc"
                + "  where sc.id.instituteid=sc.id.instituteid and  sc.id.subjectid=sm.id.subjectid and sc.id.instituteid in(:instituteid) "
                + "  and sc.id.registrationid in(:registrationid)");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameterList("instituteid", instituteid).
                    setParameterList("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentRegisteredSubjects(List instituteid, List registrationid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sft.id.studentid, sm.subjectcode from FacultyStudentTagging sft, FacultySubjectTagging fst, SubjectMaster sm "
                + "  where fst.id.instituteid=fst.id.instituteid and fst.subjectid=sft.subjectid and sft.fstid=fst.id.fstid "
                + "  and sft.id.instituteid=fst.id.instituteid and sm.id.instituteid=fst.id.instituteid "
                + "  and fst.subjectid=sm.id.subjectid and sft.subjectid=sm.id.subjectid "
                + "  and fst.id.instituteid in(:instituteid) "
                + "  and fst.registrationid in(:registrationid)");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameterList("instituteid", instituteid).
                    setParameterList("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getStudentsForSummer(String instituteid, String registrationid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.instituteid,sm.studentid,sm.enrollmentno,sm.name,sm.programid,"
                + " sm.acadyear,sm.stynumber,sm.quotaid from StudentMaster sm where sm.instituteid= :instituteid and sm.activestatus='A' "
                + " and exists(  "
                + " select srd.id.studentid  "
                + " from StudentResultDetail srd,OfferedODSubjectTagging ost  "
                + " where srd.id.instituteid =sm.instituteid  "
                + " and srd.id.studentid=sm.studentid  "
                + " and srd.fail='Y'"
                + " and coalesce(ost.odsubjectid,ost.currentsubjectid)=srd.subjectid"
                + " and ost.id.registrationid=:registrationid)");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List<Object[]> getStudentRegistrationDateBaseList(String instituteid, String registrationid, String academicyear, String compStatus, String pendingStatus) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sri.academicyear , sm.enrollmentno , sm.name , pm.programcode , bm.branchcode , sr.stynumber , coalesce(sr.regconfirmation,'Pending') as regconfirmation, sr.regconfirmatiodate ,(select coalesce(rmd.extendedtilldate, rmd.regdateto)  "
                + " from RegistrationMasterDetail rmd where rmd.id.instituteid=sri.id.instituteid and rmd.id.registrationid=sri.id.registrationid and rmd.id.academicyear=sri.academicyear and rmd.id.programid=sri.programid and rmd.id.branchid=sri.branchid)as planRegDate "
                + " from StudentMaster sm , StudentRegistration sr, StudentRegistration_info sri, ProgramMaster pm, BranchMaster bm "
                + " where sr.id.instituteid = sri.id.instituteid and sr.id.studentid=sri.id.studentid and sr.id.registrationid = sri.id.registrationid "
                + " and sri.id.instituteid = bm.id.instituteid and sri.branchid = bm.id.branchid "
                + " and sri.id.instituteid = pm.id.instituteid and sri.programid = pm.id.programid "
                + " and sr.id.instituteid = sm.instituteid and sr.id.studentid = sm.studentid and sm.activestatus = 'A' "
                + " and sr.id.instituteid = :instituteid "
                + " and sr.id.registrationid = :registrationid "
                + " and sri.academicyear = :academicyear ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("academicyear", academicyear).list();
//                    setParameter("dateason", dateason)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List<Object[]> getBackPaperReportData_BPR(String instituteid, String programid, String branchid, String stynumber, String subjectid, String orderby) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select"
                + " sm.enrollmentno, sm.name,pm.programcode,bm.branchcode,sr.id.stynumber, sub.subjectcode,sub.subjectdesc"
                + " from"
                + " StudentMaster sm,"
                + " StudentResult sr,"
                + " ProgramMaster pm,"
                + " BranchMaster bm,"
                + " SubjectMaster sub"
                + " where"
                + " sr.id.instituteid=sm.instituteid"
                + " and sr.id.studentid=sm.studentid"
                + " and pm.id.instituteid=sm.instituteid"
                + " and pm.id.programid=sm.programid"
                + " and bm.id.instituteid=sm.instituteid"
                + " and bm.id.programid=sm.programid"
                + " and bm.id.branchid=sm.branchid"
                + " and sub.id.instituteid=sr.id.instituteid"
                + " and sub.id.subjectid=sr.id.subjectid"
                + " and coalesce(sm.activestatus,'A')='A'"
                + " and coalesce(sr.fail,'N')='Y'");
        if (!programid.equalsIgnoreCase("All")) {
            sb.append("  and sm.programid=:programid");
        }
        if (!branchid.equalsIgnoreCase("All")) {
            sb.append("  and sm.branchid=:branchid");
        }
        if (!stynumber.equalsIgnoreCase("All")) {
            sb.append("  and sr.id.stynumber=:stynumber");
        }
        if (!subjectid.equalsIgnoreCase("All")) {
            sb.append("  and sr.id.subjectid=:subjectid");
        }
        sb.append(" and :programid=:programid and :branchid=:branchid and :stynumber=:stynumber and :subjectid=:subjectid order by :orderby ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("branchid", branchid).
                    setParameter("stynumber", stynumber).
                    setParameter("subjectid", subjectid).
                    setParameter("orderby", orderby).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }

    public List getProgramBranchCode(String regcode) {
        List list = new ArrayList();
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct pm.id.programid,bm.id.branchid,pm.programcode,bm.branchcode , pm.seqid,bm.seqid ,im.institutecode,im.instituteid"
                + " from BranchMaster bm, ProgramMaster pm,InstituteMaster im "
                + " where im.instituteid = pm.id.instituteid"
                + " and pm.id.instituteid=bm.id.instituteid "
                + " and pm.id.programid=bm.id.programid "
                + " and exists (select 'Y' from StudentRegistration_info sr  "
                + " where sr.id.instituteid=bm.id.instituteid "
                + " and sr.branchid=bm.id.branchid "
                + " and sr.programid=bm.id.programid "
                + " and sr.id.registrationid in (select rm.id.registrationid from RegistrationMaster rm where rm.id.instituteid=sr.id.instituteid and rm.registrationcode=:regcode) ) "
                + " order by im.institutecode,pm.seqid,bm.seqid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("regcode", regcode).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStudentQuotaCode() {
        List list = new ArrayList();
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct sq.quota from StudentQuota sq where coalesce(sq.deactive,'N')='N'");
        try {
            list = getHibernateSession().createQuery(sb.toString()).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getStudentRegistrationDateNewBaseList(String regcode, String academicyear, String compStatus, String pendingStatus) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sq.quota, srinfo.programid,srinfo.branchid,count(distinct sr.id.studentid),sr.id.instituteid "
                + " from StudentRegistration sr,StudentRegistration_info srinfo,StudentQuota sq  "
                + " where   sq.id.instituteid=sr.id.instituteid and sq.id.quotaid=sr.quotaid "
               + " and sr.id.registrationid in (select rm.id.registrationid from RegistrationMaster rm where rm.id.instituteid=sr.id.instituteid and rm.registrationcode=:regcode ) "
                + " and sr.id.instituteid=srinfo.id.instituteid  "
                + " and sr.id.registrationid=srinfo.id.registrationid  "
                + " and sr.id.studentid=srinfo.id.studentid  "
                + " and srinfo.academicyear in (:academicyear)  "
                //+ " and sr.regconfirmation=:regcon "
                //+ " and sr.regconfirmatiodate <= to_date(sysdate)  "
                + " group by sr.quotaid, srinfo.programid,srinfo.branchid,sr.id.instituteid,sq.quota ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("regcode", regcode).
                    setParameter("academicyear", academicyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;

    }
}
