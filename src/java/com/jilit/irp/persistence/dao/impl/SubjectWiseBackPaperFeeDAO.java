/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SubjectWiseBackPaperFeeIDAO;
import com.jilit.irp.persistence.dto.SubjectWiseBackPaperFee;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Malkeet Singh
 */
public class SubjectWiseBackPaperFeeDAO extends HibernateDAO implements SubjectWiseBackPaperFeeIDAO {

    private static final Log log = LogFactory.getLog(SubjectWiseBackPaperFeeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectWiseBackPaperFee records via Hibernate from the database");
        return this.find("from SubjectWiseBackPaperFee as tname");
    }

    public List getGridData(String instituteid, String regid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select  swbpf.id.instituteid,swbpf.id.registrationid,swbpf.id.subjectid,rm.registrationcode,sm.subjectcode,sm.subjectdesc,swbpf.feeamount "
                + " from SubjectWiseBackPaperFee swbpf, "
                + " RegistrationMaster rm, "
                + " SubjectMaster sm "
                + " where swbpf.id.instituteid=:instituteid "
                + " and swbpf.id.registrationid=:regid "
                + " and rm.id.instituteid=swbpf.id.instituteid "
                + " and sm.id.instituteid=swbpf.id.instituteid "
                + " and rm.id.registrationid=swbpf.id.registrationid "
                + " and sm.id.subjectid=swbpf.id.subjectid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSubjectCode(String instituteid, String departmentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.id.subjectid,sm.subjectcode,sm.subjectdesc from SubjectMaster sm , DepartmentSubjectTagging dst "
                + "where dst.id.instituteid=:instituteid "
                + "and dst.id.departmentid=:departmentid "
                + "and sm.id.instituteid= dst.id.instituteid "
                + "and sm.id.subjectid=dst.id.subjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("departmentid", departmentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List doValidate(String instituteid, String registrationid, String subjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select swbpf.id.instituteid "
                + " from SubjectWiseBackPaperFee swbpf "
                + " where swbpf.id.instituteid=:instituteid "
                + " and swbpf.id.registrationid=:registrationid "
                + " and swbpf.id.subjectid=:subjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectWiseBackPaperFee.class, id);
    }

    public String getSubjectWiseBackPaperFeeAmount(String instituteid, String registrationid, String subjectid) {
        List list = null;
        String value = "";
        StringBuilder sb = new StringBuilder();
        sb.append(" select swbpf.feeamount "
                + " from SubjectWiseBackPaperFee swbpf "
                + " where swbpf.id.instituteid=:instituteid "
                + " and swbpf.id.registrationid=:registrationid "
                + " and swbpf.id.subjectid=:subjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("subjectid", subjectid).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return value;
    }
}
