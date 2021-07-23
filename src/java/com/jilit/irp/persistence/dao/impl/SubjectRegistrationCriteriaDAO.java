/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.SubjectRegistrationCriteriaIDAO;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.SubjectRegistrationCriteria;
import java.io.Serializable;
import static java.lang.Math.log;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author campus.trainee
 */
public class SubjectRegistrationCriteriaDAO extends HibernateDAO implements SubjectRegistrationCriteriaIDAO {

    private static final Log log = LogFactory.getLog(SubjectRegistrationCriteriaDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SubjectRegistrationCriteria records via Hibernate from the database");
        return this.find("from SubjectRegistrationCriteria as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(SubjectRegistrationCriteria.class, id);
    }

    public List creditSubjectType(String instituteid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select st.id.subjecttypeid, st.subjecttype, st.subjecttypedesc from SubjectTypeMaster st "
                + " where st.id.instituteid =:instituteid and coalesce(st.deactive,'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGridData(String instituteid, String programid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select sr.subjtypeforcreditsummation_c, sr.electivetypetobeshown_c, sr.electivetypetobedisabled_c, "
                + " sr.showbackpaperincoursereg, sr.enablebackpaperincoursereg, sr.allownotrunningbackpaper, sr.backpaperdefaultselected, "
                + " sr.backpaperselectionmandaoty, sr.printingofcourseregistration, "
                + " sr.feeduecheckisrequierdfor, sr.backpaperfeetobe, sr.subjtypeforcreditsummation_e, sr.electivetypetobeshown_e, "
                + " sr.electivetypetobedisabled_e, sr.modificationofpereferences, "
                + " sr.allowprintingofelectivechoices,sr.id.programid,pm.programcode,sr.id.instituteid from SubjectRegistrationCriteria sr,ProgramMaster pm "
                + " where sr.id.instituteid =:instituteid and sr.id.programid=:programid and sr.id.instituteid=pm.id.instituteid and sr.id.programid=pm.id.programid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).setParameter("programid", programid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getDepartmentData() {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append("select dm.departmentid,dm.department from DepartmentMaster dm where coalesce(dm.deactive, 'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
