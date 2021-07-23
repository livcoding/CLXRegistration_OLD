/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentDisciplinaryActionIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentDisciplinaryAction;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author singh.jaswinder
 */
public class StudentDisciplinaryActionDAO extends HibernateDAO implements StudentDisciplinaryActionIDAO {

    private static final Log log = LogFactory.getLog(StudentDisciplinaryActionDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentDisciplinaryAction records via Hibernate from the database");
        return this.find("from StudentDisciplinaryAction as tname");
    }

    public Collection<?> findAllInstituteWise(String instituteid) {
        log.info("Retrieving all StudentDisciplinaryAction records via Hibernate from the database");
        return this.find("from StudentDisciplinaryAction as tname where tname.id.instituteid='" + instituteid + "'");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentDisciplinaryAction.class, id);
    }

    public Byte getMaxSLNO(final Object exampleObject) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(exampleObject.getClass());
                criteria.setProjection(Projections.projectionList().add(Projections.max("id.slno")));
                return criteria.list();
            }
        });
        Byte slno = list.get(0) == null ? new Byte("0") : (Byte) list.get(0);
        return slno;
    }

    public List getProgramMaxStyData(String instituteid) {
        String qryString = "Select pm.programcode, bm.branchcode,ins.institutecode,pgm.id.academicyear,pgm.startsty,pgm.endsty,pgm.startdate,pgm.enddate,pgm.exampattern,pgm.deactive,pgm.stypattern,pgm.id.instituteid,pgm.id.academicyear,pgm.id.programid,pgm.id.branchid "
                + " from  ProgramMaxSty pgm,ProgramMaster pm,BranchMaster bm,InstituteMaster ins  where pgm.id.instituteid='" + instituteid + "' and "
                + " pgm.id.programid = pm.id.programid and pgm.id.branchid = bm.id.branchid and pgm.id.instituteid = ins.id.instituteid  ";
        return getHibernateTemplate().find(qryString);

    }
//

    public List<String> doValidate(final StudentDisciplinaryAction studentDisciplinaryAction, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);

        /*Unique Key Constraint on CountryId and CountryCode*/
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentDisciplinaryAction.class);
                criteria.add(Restrictions.eq("daid", studentDisciplinaryAction.getDaid()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (mode.equals("edit")) {
            if (count.intValue() == 0) {
                errors.add("daid does not exists");
            }

        } else {
            if (count.intValue() > 0) {
                errors.add("Same Values Of daid exists");
            }
        }

        return errors;
    }

    public List getAllDataStudentDisciplinaryAction(String instituteid) {
        String qryString = "select sda.daid,sm.studentid, sm.enrollmentno, dom.offenceid, dom.offencelevel, sda.misconductdate, sda.status,sda.stoppromotion, "
                + "sda.actioninitiated,sda.actiontaken, dtm.disciplinarytypeid, dtm.disciplinarycategory, dom.offencelevel from StudentDisciplinaryAction sda, StudentMaster sm, "
                + "DisciplinaryOffenceMaster dom, DisciplinaryTypeMaster dtm where sda.studentid = sm.studentid and sda.disciplinarytypeid = dtm.disciplinarytypeid "
                + "and sda.offenceid = dom.offenceid and sda.instituteid = '" + instituteid + "' ";

        return getHibernateTemplate().find(qryString);
    }

    public List getEditUsingPrimaryKey(String instituteid, String daid) {
        String qryString = "select sda.daid,sm.studentid, sm.enrollmentno,sm.name, dom.offenceid, dom.offencelevel, sda.misconductdate, sda.misconduct, sda.status,sda.stoppromotion, "
                + "sda.actioninitiated, sda.actioninitiatedby, sda.actioninitiateddate, sda.actiontaken, sda.actiontakenby, sda.actiondate, sda.remarks, dtm.disciplinarytypeid, dtm.disciplinarycategory, dom.offencelevel from StudentDisciplinaryAction sda, StudentMaster sm, "
                + "DisciplinaryOffenceMaster dom, DisciplinaryTypeMaster dtm where sda.studentid = sm.studentid and sda.disciplinarytypeid = dtm.disciplinarytypeid "
                + "and sda.offenceid = dom.offenceid and sda.instituteid = '" + instituteid + "' and sda.daid = '" + daid + "' ";

        return getHibernateTemplate().find(qryString);
    }

//
//    public int checkIfChildExist(final StudentDisciplinaryAction id) {
//        HibernateCallback callback = new HibernateCallback() {
//
//            int i = 0;
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                StudentDisciplinaryAction studentDisciplinaryAction = (StudentDisciplinaryAction) session.get(StudentDisciplinaryAction.class, id);
//                return i;
//            }
//        };
//        return 0;
//    }
}
