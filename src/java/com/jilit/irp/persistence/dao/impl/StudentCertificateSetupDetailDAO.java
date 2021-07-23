/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentCertificateSetupDetailIDAO;
import com.jilit.irp.persistence.dto.StudentCertificateSetupDetail;
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
public class StudentCertificateSetupDetailDAO extends HibernateDAO implements StudentCertificateSetupDetailIDAO {

    private static final Log log = LogFactory.getLog(StudentCertificateSetupDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentCertificateSetupDetail records via Hibernate from the database");
        return this.find("from StudentCertificateSetupDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentCertificateSetupDetail.class, id);
    }

     public List doValidate(final StudentCertificateSetupDetail studentCertificateSetupDetail, final String mode){
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
            count = (Integer) getHibernateTemplate().executeFind( new HibernateCallback() {

                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        Criteria criteria = session.createCriteria(StudentCertificateSetupDetail.class);
                        criteria.add(Restrictions.eq("id.instituteid", studentCertificateSetupDetail.getId().getInstituteid()));
                        criteria.add(Restrictions.eq("id.certificateid", studentCertificateSetupDetail.getId().getCertificateid()));
                        criteria.add(Restrictions.eq("id.academicyear", studentCertificateSetupDetail.getId().getAcademicyear()));
                        criteria.add(Restrictions.eq("id.programid", studentCertificateSetupDetail.getId().getProgramid()));
                        criteria.add(Restrictions.eq("id.branchid", studentCertificateSetupDetail.getId().getBranchid()));
                        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                        return criteria.list();
                    }
            }).get(0);

            if(mode.equals("save")){
                if(count.intValue() > 0){
                    errors.add("Student Certificate Setup Details record already exists");
                }
            }
            else if(mode.equals("edit")){
                if(count.intValue() == 0){
                    errors.add("Student Certificate Setup Details record not exist. Please refresh the grid!");
                }
            }

        return errors;
    }

//    public List getStudentCertificateDetailSetup(String certificateid){
//        List list = null;
//        String query = "select scsd.id.academicyear, scsd.id.programid, scsd.id.branchid from StudentCertificateSetupDetail scsd where scsd.id.certificateid = '"+certificateid+"'";
//        list = getHibernateTemplate().find(query);
//        return list;
//    }
//
//    public List<StudentCertificateSetupDetail> fetchByCertificateId(String certificateid){
//        List<StudentCertificateSetupDetail> list = null;
//        String query = "from StudentCertificateSetupDetail scsd where scsd.id.certificateid = '"+certificateid+"'";
//        list = getHibernateTemplate().find(query);
//        return list;
//    }
}
