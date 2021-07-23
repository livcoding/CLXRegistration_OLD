/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StyDescIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.persistence.dto.StyDescId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author sunny.singhal
 */
public class StyDescDAO extends HibernateDAO implements StyDescIDAO {

    private static final Log log = LogFactory.getLog(StyDescDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StyDesc records via Hibernate from the database");
        return this.find("from StyDesc as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StyDesc.class, id);
    }

    public int checkIfChildExist(StyDescId Id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> doValidate(StyDesc styDesc, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> Academicyearvalidate(StyDesc styDesc, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getSemesterNumber(String instituteid, String registrationid, String subjectid, String acad_year, String programid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct"
                + " sscm.id.stynumber "
                + " from"
                + " StudentMaster sm,"
                + " StudentSubjectChoiceMaster sscm "
                + " where"
                + " sscm.id.stynumber=sm.stynumber "
                + " and sm.instituteid=sscm.id.instituteid "
                + " and sm.studentid=sscm.id.studentid "
                + " and sscm.id.registrationid=:registrationid"
                + " and sm.acadyear=:acad_year"
                + " and ( sm.programid=:programid or :programid='all')"
                + " and sscm.id.instituteid=:instituteid "
                + " order by"
                + " sscm.id.stynumber ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("registrationid", registrationid).
                    setParameter("acad_year", acad_year).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getAllStyNumber_Fst(String instituteid, String reg_id, String acad_year) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct stynumber,stynumber from ProgramSubjectTagging where academicyear=:acad_year  "
                + " and coalesce(deactive,'N')='N' and id.registrationid  =:reg_id and id.instituteid =:instituteid order by stynumber");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).setParameter("reg_id", reg_id).setParameter("acad_year", acad_year).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    @Override
    public List getSty_Pst(String instituteid, String reg_id, String acad_year, String prg_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("  select   sty.id.instituteid,sty.id.stypattern, sty.id.stynumber, sty.stydesc,sty.styyear,sty.styyeardesc  from StyDesc sty  where sty.id.instituteid=:instituteid and ( "
                + "  sty.id.stynumber in (select prgsuntag.stynumber   from ProgramSubjectTagging prgsuntag   where prgsuntag.id.instituteid=:instituteid and prgsuntag.academicyear=:acad_year  and prgsuntag.id.registrationid=:reg_id "
                + "  and prgsuntag.programid=:prg_id and coalesce(prgsuntag.deactive, 'N')='N'  ))  order by  sty.id.stynumber ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("reg_id", reg_id).
                    setParameter("prg_id", prg_id).
                    setParameter("acad_year", acad_year).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStyNumber(String instituteid, String regid, String acadyear, String progid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct pst.stynumber from ProgramSubjectTagging pst where pst.id.instituteid=:instituteid and pst.id.registrationid=:regid and pst.academicyear=:acadyear and pst.programid=:progid order by pst.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("regid", regid).
                    setParameter("acadyear", acadyear).
                    setParameter("progid", progid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
    
    public List getStyNumber1(String instituteid, String regid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct pst.stynumber from ProgramSubjectTagging pst where pst.id.instituteid=:instituteid and pst.id.registrationid=:regid order by pst.stynumber");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("regid", regid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }


}
