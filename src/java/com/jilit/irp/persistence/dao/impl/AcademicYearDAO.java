package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.AcademicYearIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.AcademicyearId;
import com.jilit.irp.persistence.dto.StudentMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ankur.goyal
 */
public class AcademicYearDAO extends HibernateDAO implements AcademicYearIDAO {

    private static final Log log = LogFactory.getLog(AcademicYearDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Academicyear records via Hibernate from the database");
        return this.find("from Academicyear as tname order by seqid asc");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Academicyear records via Hibernate from the database");
        return this.find("from Academicyear as tname where tname.id.instituteid = ? order by tname.id.academicyear desc ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Academicyear.class, id);
    }

    public int checkIfChildExist(final AcademicyearId id) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Academicyear academicyear = (Academicyear) session.get(Academicyear.class, id);
                int i1 = ((Integer) session.createFilter(academicyear.getStudentmasters(), "select count(*)").list().get(0)).intValue();
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final Academicyear academicyear, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Academicyear.class);
                criteria.add(Restrictions.eq("id.instituteid", academicyear.getId().getInstituteid()));
                criteria.add(Restrictions.eq("id.academicyear", academicyear.getId().getAcademicyear()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Academic Year Date is Invalid Date Range Already Exists!");
        }
        return errors;
    }

    public List<String> Academicyearvalidate(final Academicyear academicyear, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Academicyear.class);
                criteria.add(Restrictions.between("yearfrom", academicyear.getYearto(), academicyear.getYearfrom()));
                criteria.add(Restrictions.between("yearto", academicyear.getYearto(), academicyear.getYearfrom()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Year Value exist");
        }
        return errors;
    }

    public List getAcademicYear() {
        String qrystring = "select id.academicyear from Academicyear";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List getAcademicYear(final String instituteid) {
        String qrystring = "select year.id.instituteid,year.id.academicyear from Academicyear year where(1=1)and  year.id.instituteid=:instId and coalesce(year.deactive,'N')='N'order by year.id.academicyear desc";
        List list = getHibernateSession().createQuery(qrystring).setParameter("instId", instituteid).list();
        return list;
    }

    @Override
    public List checkAcademicYear(String academicyear) {
        String qryString = "select id.academicyear"
                + " from Academicyear  "
                + " where coalesce(deactive,'N')='N' and id.academicyear='" + academicyear + "'";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAllAcademicYear(String instituteid) {
        String qryString = "select a.id.academicyear "
                + " from Academicyear a "
                + " where a.id.instituteid='" + instituteid + "'"
                + " and coalesce(a.deactive,'N')='N' order by cast(a.id.academicyear as long) desc";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAllDistinctAcademicYear(String instituteid) {
        String qryString = "select distinct ay.id.academicyear,deactive "
                + " from Academicyear ay "
                + " where id.instituteid='" + instituteid + "' "
                + " and coalesce(deactive,'N')='N' "
                + " order by cast(ay.id.academicyear as long) desc ";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAllAcademic_Year(String instituteid, String registrationid) {
        String qryString = "select ay.id.academicyear "
                + " from Academicyear ay "
                + " where exists (select 1 from StudentRegistration_info sri where ay.id.instituteid = sri.id.instituteid and ay.id.academicyear=sri.academicyear and sri.id.registrationid='" + registrationid + "') "
                + " and ay.id.instituteid = '" + instituteid + "' ";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAllCertificateCode(String instituteid, String academicyear) {
        List list = null;
        String qryString = "select ay.id.academicyear, ay.certificatecode "
                + " from Academicyear ay "
                + " where ay.id.instituteid = '" + instituteid + "' "
                + " and ay.id.academicyear = '" + academicyear + "' ";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllAcademic_YearExitInFST(String instituteid, String registrationid) {
        String qryString = " select ay.id.academicyear "
                + " from Academicyear ay "
                + " where exists (select 1 from FacultySubjectTagging fst where ay.id.instituteid = fst.id.instituteid and ay.id.academicyear=fst.academicyear and fst.registrationid='" + registrationid + "') "
                + " and ay.id.instituteid = '" + instituteid + "' ";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAllAcademicYearFst(String instituteid) {
        List l = null;
        String qryString = "select ay.id.academicyear from Academicyear ay where exists(select 1 from FacultySubjectTagging fst where ay.id.instituteid = fst.id.instituteid and ay.id.academicyear=fst.academicyear  and coalesce(fst.deactive,'N')='N') "
                + "and coalesce(ay.deactive,'N')='N' "
                + "and ay.id.instituteid = '" + instituteid + "' "
                + "group by ay.id.academicyear "
                + "order by ay.id.academicyear ";
        try {
            l = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public List getAcademicYearData_LOV(final String instituteid) {
        final List list = (List) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Academicyear.class);
                criteria.add(Restrictions.eq("id.instituteid", instituteid));
                criteria.setProjection(Projections.property("id.academicyear"));
                criteria.addOrder(Order.desc("id.academicyear"));
                return criteria.list();
            }
        });
        return list;
    }

    public List checkAcademicYear_LOV(final String instituteid, final String academicyear) {
        final List list = (List) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Academicyear.class);
                criteria.add(Restrictions.eq("id.instituteid", instituteid));
                criteria.add(Restrictions.eq("id.academicyear", academicyear).ignoreCase());
                criteria.setProjection(Projections.property("id.academicyear"));
                return criteria.list();
            }
        });
        return list;
    }

    public String getMaxAcadmicYear(final String instituteid) {
        final String maxacadyear = (String) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentMaster.class);
                criteria.add(Restrictions.eq("instituteid", instituteid));
                criteria.setProjection(Projections.max("acadyear"));
                return criteria.list();
            }
        }).get(0).toString();
        return maxacadyear;
    }

    public List getAcademicYearbyGroupby() {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + "group by ay.id.academicyear "
                + "order by ay.id.academicyear ";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List checkAcademicbyGroupby(String academicyear) {
        String qryString = "select id.academicyear "
                + " from Academicyear  "
                + " where id.academicyear='" + academicyear + "' ";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAcademicYearCheckPST(String regid, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ay.id.academicyear from Academicyear ay where exists(select 1 from ProgramSubjectTagging pst "
                + " where pst.id.instituteid = ay.id.instituteid and pst.academicyear = ay.id.academicyear and pst.id.registrationid = :regid) and "
                + " ay.id.instituteid = :instituteid order by ay.id.academicyear desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("regid", regid).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAll_TSRAcademicYear(String instid) {
        String qrystring = "select ay.id.academicyear,ay.currentyear from Academicyear ay "
                + " Where ay.id.instituteid='" + instid + "' and coalesce(ay.deactive,'N') = 'N'  "
                + "order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List checkTSRAcademicbyGroupby(String instid, String academicyear) {
        String qryString = "select id.academicyear "
                + " from Academicyear  "
                + " where id.academicyear='" + academicyear + "' "
                + " and id.instituteid='" + instid + "'";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    public List getAll_TSRSemester(String instid) {
        String qrystring = " select id.stypattern, id.stynumber, stydesc, styyear, styyeardesc "
                + " from StyDesc "
                + "  where"
                + "  id.instituteid='" + instid + "' ";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    @Override
    public List getStyNumber(String instituteid, String programid, String academicyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct psaw.stynumber from ProgramSchemeAcadyearWise psaw where psaw.id.instituteid=:instituteid and psaw.programid=:programid and psaw.academicyear=:academicyear  order by psaw.stynumber");

        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("programid", programid).setParameter("academicyear", academicyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStyNumberForTSReport(String instituteid, String programid, List academicyear) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct psaw.stynumber from ProgramSchemeAcadyearWise psaw where psaw.id.instituteid=:instituteid and psaw.programid=:programid and psaw.academicyear in(:academicyear ) order by psaw.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("programid", programid).setParameterList("academicyear", academicyear).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List checkTSRSemester(String stynumber) {
        String qryString = " select id.stypattern, id.stynumber, stydesc, styyear, styyeardesc "
                + " from StyDesc "
                + "  where"
                + "   id.stynumber='" + stynumber + "'";
        List list = getHibernateTemplate().find(qryString);
        return list;
    }

    @Override
    public List getacadyrForFilteration(String instid) {
        List list = new ArrayList();
        String qrystring = " select ay.id.academicyear, ay.currentyear from Academicyear ay "
                + " Where ay.id.instituteid='" + instid + "' "
                + " group by ay.id.academicyear , ay.currentyear"
                + " order by ay.id.academicyear desc ";
        try {
            list = getHibernateTemplate().find(qrystring);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAcademicYearData(String instid) {
        List list = new ArrayList();
        String qrystring = " select ay.id.academicyear,ay.yearfrom,ay.yearto,ay.closed, ay.currentyear from Academicyear ay "
                + " where ay.id.instituteid='" + instid + "' "
                + " and coalesce(ay.deactive,'N')='N' "
                + " group by ay.id.academicyear,ay.yearfrom,ay.yearto,ay.closed, ay.currentyear "
                + " order by ay.id.academicyear desc ";
        try {
            list = getHibernateTemplate().find(qrystring);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getProgramSchemeAcadYear(String instid) {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + " Where ay.id.instituteid='" + instid + "' "
                + " and exists( select 1 from ProgramSchemeAcadyearWise ps where ps.id.instituteid = ay.id.instituteid"
                + " and ay.id.academicyear = ps.academicyear ) "
                + " group by ay.id.academicyear "
                + " order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List checkIfProgramSchemeAcadYearExists(String acadyr, String instid) {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + " Where ay.id.instituteid = '" + instid + "' and ay.id.academicyear = '" + acadyr + "'"
                + " and exists( select 1 from ProgramSchemeAcadyearWise ps where ps.id.instituteid = ay.id.instituteid"
                + " and ay.id.academicyear = ps.academicyear ) "
                + " group by ay.id.academicyear "
                + " order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List getAcadYear_PST(String instid, String Regid) {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + " Where ay.id.instituteid='" + instid + "' "
                + " and exists( select 1 from ProgramSubjectTagging ps where ps.id.instituteid = ay.id.instituteid"
                + " and  ps.id.registrationid ='" + Regid + "'"
                + " and ay.id.academicyear = ps.academicyear ) "
                + " group by ay.id.academicyear "
                + " order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List checkIfAcadYearExists_PST(String acadyr, String instid, String Regid) {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + " Where ay.id.instituteid = '" + instid + "' and ay.id.academicyear = '" + acadyr + "'"
                + " and exists( select 1 from ProgramSubjectTagging ps where ps.id.instituteid = ay.id.instituteid"
                + " and  ps.id.registrationid ='" + Regid + "'"
                + " and ay.id.academicyear = ps.academicyear ) "
                + " group by ay.id.academicyear "
                + " order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List getAcadYear_PST1(String instid, String Regid) {
        String qrystring = "select ay.id.academicyear from Academicyear ay "
                + " Where ay.id.instituteid='" + instid + "' "
                + " and exists( select 1 from ProgramSubjectTagging ps where ps.id.instituteid = ay.id.instituteid";
        if (!Regid.equals("All")) {
            qrystring = qrystring + " and  ps.id.registrationid ='" + Regid + "'";
        }
        qrystring = qrystring + " and ay.id.academicyear = ps.academicyear ) "
                + " group by ay.id.academicyear "
                + " order by ay.id.academicyear desc";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    public List getAll_AcademicYearForTopperList(String instituteid) {
        List list = null;
        String qry = " select "
                + " id.instituteid, "
                + " id.academicyear "
                + " from Academicyear "
                + " where "
                + " id.instituteid = '" + instituteid + "' "
                + " and coalesce(deactive, 'N') = 'N'"
                + " and coalesce(closed, 'N') = 'N'"
                + " order by id.academicyear desc ";
        try {

            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAcademicYear(String instituteid, String subjectid, String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select academicye0_.id.instituteid,academicye0_.id.academicyear, academicye0_.yearfrom,academicye0_.yearto,academicye0_.closed,academicye0_.currentyear,academicye0_.enrollmentcode,academicye0_.certificatecode, "
                + " academicye0_.seqid, academicye0_.deactive from Academicyear academicye0_  where    academicye0_.id.instituteid=:instituteid  and coalesce(academicye0_.deactive, 'N')='N'  and (exists ( select studentmas1_.acadyear   "
                + " from StudentMaster studentmas1_,StudentSubjectChoiceMaster studentsub2_ where  studentmas1_.instituteid=academicye0_.id.instituteid and studentmas1_.acadyear=academicye0_.id.academicyear  "
                + " and studentmas1_.instituteid=studentsub2_.id.instituteid and studentmas1_.studentid=studentsub2_.id.studentid and studentsub2_.id.registrationid=:registrationid  ");
        if (!subjectid.equalsIgnoreCase("All")) {
            sb.append(" and studentsub2_.id.subjectid = :subjectid");
        }
        sb.append(" and :subjectid = :subjectid and studentsub2_.id.instituteid=:instituteid group by  studentmas1_.acadyear ))");

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
    public List getAcademicYearDeactiveWise(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select ac.id.academicyear from Academicyear ac where ac.id.instituteid = :instituteid and coalesce (ac.deactive, 'N') = 'N' order by ac.id.academicyear desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
