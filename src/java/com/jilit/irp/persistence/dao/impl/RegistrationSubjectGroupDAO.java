/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RegistrationSubjectGroupIDAO;
import com.jilit.irp.persistence.dto.Pr_ElectiveSets;
import com.jilit.irp.persistence.dto.Pr_ElectiveSetsId;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import com.jilit.irp.persistence.dto.ScholarshipCriteriaId;
import com.jilit.irp.persistence.dto.ScholarshipCriteria;
import com.jilit.irp.persistence.dao.ScholarshipCriteriaIDAO;

/**
 *
 * @author Malkeet Singh
 */
public class RegistrationSubjectGroupDAO extends HibernateDAO implements RegistrationSubjectGroupIDAO {

    private static final Log log = LogFactory.getLog(RegistrationSubjectGroupDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CourseCreditWiseFee records via Hibernate from the database");
        return this.find("from CourseCreditWiseFee as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Pr_ElectiveSets.class, id);
    }

    public List getGridData(String instituteid, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select pes.id.programid,pes.id.subjectid,pm.programcode,pm.programdesc,pes.id.groupid,sm.subjectcode,sm.subjectdesc,coalesce(pes.deactive,'N') as deactive "
                + " from Pr_ElectiveSets pes,ProgramMaster pm ,SubjectMaster sm "
                + " where pes.id.programid=:programid "
                + " and pes.id.instituteid=:instituteid "
                + " and pm.id.instituteid=pes.id.instituteid "
                + " and pm.id.programid=pes.id.programid "
                + " and sm.id.instituteid=pes.id.instituteid "
                + " and sm.id.subjectid=pes.id.subjectid "
                + " order by pes.entrydate desc");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGroupId(String instituteid, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct pes.id.groupid from Pr_ElectiveSets pes "
                + " where pes.id.instituteid=:instituteid "
                + " and pes.id.programid=:programid "
                + " order by pes.id.groupid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSubjects(String instituteid, String programid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sm.id.subjectid,sm.subjectcode,sm.subjectdesc  "
                + " from SubjectMaster sm "
                + " where sm.id.instituteid=:instituteid "
                + " and exists (select ps.subjectid from ProgramSchemeAcadyearWise ps "
                + " where ps.programid=:programid "
                + " and ps.id.instituteid=sm.id.instituteid "
                + " and sm.id.subjectid=ps.subjectid )"
                + " and not exists ( select pes.id.subjectid from Pr_ElectiveSets pes "
                + " where pes.id.subjectid=sm.id.subjectid "
                + " and pes.id.instituteid=sm.id.instituteid"
                + " and pes.id.programid=:programid )");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGroupedSubjects(String instituteid, String programid, String groupid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct sm.id.subjectid,sm.subjectcode,sm.subjectdesc from Pr_ElectiveSets pes,SubjectMaster sm "
                + " where pes.id.instituteid=:instituteid "
                + " and pes.id.groupid=:groupid "
                + " and pes.id.programid=:programid "
                + " and sm.id.subjectid=pes.id.subjectid "
                + " and sm.id.instituteid=pes.id.instituteid"
                + " and coalesce(pes.deactive,'N') = 'N' "
                + " order by sm.subjectdesc ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("groupid", groupid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List validateGroupId(String instituteid, String programid, String groupid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct pes.id.groupid from Pr_ElectiveSets pes  "
                + " where pes.id.instituteid=:instituteid "
                + " and pes.id.programid=:programid "
                + " and pes.id.groupid=:groupid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("groupid", groupid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
