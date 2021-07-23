/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_TimeTableAllocationIDAO;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocation;
import com.jilit.irp.persistence.dto.TT_AttendanceStatus;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ashok.singh
 */
public class TT_TimeTableAllocationDAO extends HibernateDAO implements TT_TimeTableAllocationIDAO {

    private static final Log log = LogFactory.getLog(TT_TimeTableAllocation.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_TimeTableAllocation records via Hibernate from the database");
        return this.find("from TT_TimeTableAllocation as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_TimeTableAllocation.class, id);
    }

    public Object findByPrimaryKey1(Serializable id) {
        return getHibernateTemplate().get(TT_AttendanceStatus.class, id);
    }
    CallableStatement cstmt = null;
    static Connection con = null;
    Session session = null;

    @Override
    public List checkTTAllocation(String instituteid, String registrationid, String subjectid, String subjectcomponentid, String facultyid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select tta.id.tttransid,tta.ttreferenceid from TT_TimeTableAllocation tta "
                + " where tta.id.instituteid=:instituteid and tta.id.registrationid=:registrationnid and tta.subjectid=:subjectid and tta.subjectcomponentid=:subjectcomponentid and tta.staffid=:facultyid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationnid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("facultyid", facultyid).
                    setParameter("subjectcomponentid", subjectcomponentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List checkTTAllocationDetail(String instituteid, String registrationid, String subjectid, String subjectcomponentid, String academicyear,
            String programid, String sectionid, String subsectionid, String stynumber, String stytypeid) {
        List list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select tta.id.instituteid from TT_TimeTableAllocation tta,TT_TimeTableAllocationDetail ttad  "
                + " where  tta.id.instituteid=ttad.id.instituteid  and tta.id.registrationid=ttad.id.registrationid "
                + " and tta.id.tttransid=ttad.id.tttransid and tta.id.instituteid=:instituteid "
                + " and tta.id.registrationid=:registrationnid and tta.subjectid=:subjectid and tta.subjectcomponentid=:subjectcomponentid "
                + " and ttad.id.academicyear=:academicyear and ttad.id.programid=:programid and ttad.id.sectionid=:sectionid "
                + " and ttad.id.subsectionid=:subsectionid and ttad.id.stynumber=:stynumber and ttad.id.stytypeid=:stytypeid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationnid", registrationid).
                    setParameter("subjectid", subjectid).
                    setParameter("academicyear", academicyear).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("subsectionid", subsectionid).
                    setParameter("stynumber", new Byte(stynumber)).
                    setParameter("stytypeid", stytypeid).
                    setParameter("subjectcomponentid", subjectcomponentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
