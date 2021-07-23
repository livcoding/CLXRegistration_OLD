/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentSpecializationMasterIDAO;
import com.jilit.irp.persistence.dto.StudentSpecializationMaster;
import com.jilit.irp.persistence.dto.StudentSpecializationMasterId;
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
 * @author ankit.kumar
 */
public class StudentSpecializationMasterDAO extends HibernateDAO implements StudentSpecializationMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentSpecializationMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentSpecializationMaster records via Hibernate from the database");
        return this.find("from StudentSpecializationMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentSpecializationMaster.class, id);
    }

    public String getenrollmentCode(String instituteid, String programid, String specid) {
        String enrollcode = null;

        String qry = "select sp.enrollmentcode from StudentSpecializationMaster sp where sp.id.instituteid='" + instituteid + "' and sp.id.programid='" + programid + "' and sp.id.specid='" + specid + "'";
        try {
            List list = getHibernateTemplate().find(qry);
            if (list != null && !list.isEmpty()) {
                enrollcode = list.get(0).toString();
            }
        } catch (Exception s) {
            s.printStackTrace();
        }
        return enrollcode;
    }

    public List getStudentSpecializationMaster(String instituteid) {

        String qryString = "Select spm.id.instituteid,spm.id.programid,spm.id.specid,spm.speccode,spm.specdesc,spm.enrollmentcode,spm.deactive,ins.institutecode,prg.programcode,spm.seqid   "
                + "  from  StudentSpecializationMaster  spm,InstituteMaster ins,ProgramMaster prg where spm.id.instituteid = ins.id.instituteid  and spm.id.instituteid = '" + instituteid + "'  and prg.id.programid = spm.id.programid ";

        return getHibernateTemplate().find(qryString);

    }

    public List editStudentSpecializationMaster(String ins_id, String prog_id, String spec_id) {

        String qryString = "Select spm.id.instituteid,spm.id.programid,spm.id.specid,spm.speccode,spm.specdesc,spm.enrollmentcode,spm.deactive,ins.institutecode,prg.programcode,spm.seqid   "
                + "  from  StudentSpecializationMaster  spm,InstituteMaster ins,ProgramMaster prg where spm.id.instituteid = ins.id.instituteid  and spm.id.instituteid = '" + ins_id + "'  and prg.id.programid = spm.id.programid "
                + " and spm.id.programid = '" + prog_id + "'  and spm.id.specid = '" + spec_id + "' ";

        return getHibernateTemplate().find(qryString);

    }

    public List doValidate(final StudentSpecializationMaster dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        if (mode.equals("save")) {
            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

                @Override
                public Object doInHibernate(Session session) {
                    Criteria criteria = session.createCriteria(StudentSpecializationMaster.class);
                    criteria.add(Restrictions.eq("speccode", dto.getSpeccode()));
                    criteria.add(Restrictions.eq("id.instituteid", dto.getId().getInstituteid()));
                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                    return criteria.list();
                }
            }).get(0);
            if (count.intValue() > 0) {
                errors.add("Student Category Code Already exist");
            }
        }

        return errors;
    }

    public int checkIfChildExist(final StudentSpecializationMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentSpecializationMaster dto = (StudentSpecializationMaster) session.get(StudentSpecializationMaster.class, id);
                int i1 = Integer.parseInt(session.createFilter(dto.getStudentmasters(), "select count(*)").list().get(0).toString());

                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }
}
