/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SubjectsForBranchChangeIDAO;
import com.jilit.irp.persistence.dto.SubjectsForBranchChange;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

/**
 *
 * @author ankit.kumar
 */
public class SubjectsForBranchChangeDAO extends HibernateDAO implements SubjectsForBranchChangeIDAO {

    private static final Log log = LogFactory.getLog(SubjectsForBranchChangeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectsForBranchChange records via Hibernate from the database");
        return this.find("from ProgramMaxSty as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectsForBranchChange.class, id);
    }

    public List getSubjectsForBranchChangeList(String instituteid) {

        String qryString = " Select sbc.id.instituteid,sbc.id.programid,sbc.id.frombranchid,sbc.id.tobranchid,sbc.id.subjectid,sbc.credit,sbc.departmentid,  " + 
                           " pr.programcode,br.branchcode,sm.subjectcode,dm.departmentcode  from SubjectsForBranchChange sbc,ProgramMaster pr,BranchMaster br,SubjectMaster sm,DepartmentMaster dm " +
                           " where sbc.id.programid = pr.id.programid and sbc.id.frombranchid = br.id.branchid and sbc.id.subjectid = sm.id.subjectid and sbc.id.instituteid = '"+instituteid+"' ";
        return getHibernateTemplate().find(qryString);

    }
    
    
//     public List<String> doValidate(final SubjectsForBranchChange subjectsForBranchChange) {
//        List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(SubjectsForBranchChange.class);
//                criteria.add(Restrictions.eq("id.instituteid", SubjectsForBranchChange.getId().getInstituteid()));
//                criteria.add(Restrictions.eq("id.frombranchid", SubjectsForBranchChange.getId().getFrombranchid()));
//                criteria.add(Restrictions.eq("id.tobranchid", SubjectsForBranchChange.getId().getFrombranchid()));
//                criteria.add(Restrictions.eq("id.programid", SubjectsForBranchChange.getId().getProgramid()));
//                criteria.add(Restrictions.eq("id.subjectid", SubjectsForBranchChange.getId().getSubjectid()));
//                /*if (mode.equals("edit")) {
//                criteria.add(Expression.ne("id.gradeid", gradePayScale.getId().getGradeid()));//Do not check for itself when updating record
//                }*/
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//        if (count.intValue() > 0) {
//            errors.add("Duplicate Program Max STY");
//        }
//        return errors;
//    }

//    public List editProgramMaxStyData(String instituteid, String programid, String acad_year, String branchid) {
//
//        String qryString = "Select pm.programcode, bm.branchcode,ins.institutecode,pgm.id.academicyear,pgm.startsty,pgm.endsty,pgm.startdate,pgm.enddate,pgm.exampattern,pgm.deactive,pgm.stypattern,pgm.id.instituteid,pgm.id.academicyear,pgm.id.programid,pgm.id.branchid "
//                + " from  ProgramMaxSty pgm,ProgramMaster pm,BranchMaster bm,InstituteMaster ins  where pgm.id.instituteid='" + instituteid + "' and pgm.id.programid = '" + programid + "' and pgm.id.academicyear='" + acad_year + "'  and pgm.id.branchid='" + branchid + "' and "
//                + " pgm.id.programid = pm.id.programid and pgm.id.branchid = bm.id.branchid and pgm.id.instituteid = ins.id.instituteid  ";
//        return getHibernateTemplate().find(qryString);
//
//    }
}
