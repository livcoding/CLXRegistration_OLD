/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentCertificateSetupIDAO;
import com.jilit.irp.persistence.dto.StudentCertificateSetup;
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author ashok.singh
 */
public class StudentCertificateSetupDAO extends HibernateDAO implements StudentCertificateSetupIDAO { 

    private static final Log log = LogFactory.getLog(StudentCertificateSetupDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentCertificateSetup records via Hibernate from the database");
        return this.find("from StudentCertificateSetup as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentCertificateSetup.class, id);
    }


    public List getAllFstForStudentCertificateSetup(String instituteid){
        List list=null;
        String query = "select fst.academicyear, pm.programcode, bm.branchcode, bm.id.branchid, pm.id.programid " +
                "from FacultySubjectTagging fst, ProgramMaster pm, BranchMaster bm, ProgramWiseSubsection pws " +
                "where fst.id.instituteid=pm.id.instituteid " +
                "and fst.programid=pm.id.programid " +
                "and pm.id.instituteid=bm.id.instituteid " +
                "and pm.id.programid=bm.id.programid " +
                "and fst.id.instituteid=pws.id.instituteid " +
                "and fst.programid=pws.id.programid " +
                "and fst.academicyear=pws.id.academicyear " +
                "and fst.sectionid=pws.id.sectionid " +
                "and bm.id.instituteid=pws.id.instituteid " +
                "and bm.id.programid=pws.id.programid " +
                "and bm.id.branchid=pws.branchid "+
                "and fst.id.instituteid='"+instituteid+"' and coalesce(fst.deactive,'N')='N'  group by fst.academicyear, pm.programcode, bm.branchcode, bm.id.branchid, pm.id.programid";
        try{
            list = getHibernateTemplate().find(query);
        }catch(Exception e){
         e.printStackTrace();
        }
        return list;
    }
    
    public List getAllStudentCertificateSetup(String instituteid, String certificateid){
        List list = null;
        String qryString = " select scs.id.instituteid,scs.id.certificateid,scs.id.slno,scs.include,scs.runningnowidth,scs.academicyearwise from StudentCertificateSetup scs "+
                       " where scs.id.instituteid='"+instituteid+"' "+
                       " and scs.id.certificateid='"+certificateid+"' "+
                       " order by scs.id.slno ";
        try{
                 list = getHibernateTemplate().find(qryString);
           }catch(Exception e){
               e.printStackTrace();
           }
        return list;
    }

    public List doValidate(final StudentCertificateSetup studentCertificateSetup, final String mode){
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
            count = (Integer) getHibernateTemplate().executeFind( new HibernateCallback() {

                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        Criteria criteria = session.createCriteria(StudentCertificateSetup.class);
                        criteria.add(Restrictions.eq("id.instituteid", studentCertificateSetup.getId().getInstituteid()));
                        criteria.add(Restrictions.eq("id.certificateid", studentCertificateSetup.getId().getCertificateid()));
                        criteria.add(Restrictions.eq("id.slno", studentCertificateSetup.getId().getSlno()));
                        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                        return criteria.list();
                    }
            }).get(0);

            if(mode.equals("save")){
                if(count.intValue() > 0){
                    errors.add("Student Certificate Setup record already exists");
                }
            }
            else if(mode.equals("edit")){
                if(count.intValue() == 0){
                    errors.add("Student Certificate Setup record not exist. Please refresh the grid!");
                }
            }

        return errors;
    }

    public List getStudentCertificateSetup(String certificateid){
        List list = null;
        String query = "select scs.include, scs.runningnowidth, scs.academicyearwise, scs.id.slno from StudentCertificateSetup scs where scs.id.certificateid = '"+certificateid+"'";
        list = getHibernateTemplate().find(query);
        return list;
    }

    public List<StudentCertificateSetup> fetchByCertificateId(String certificateid){
        List<StudentCertificateSetup> list = null;
        String query = "from StudentCertificateSetup scs where scs.id.certificateid = '"+certificateid+"'";
        list = getHibernateTemplate().find(query);
        return list;
    }
}
