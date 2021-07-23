/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentLeaveMasterIDAO;
import com.jilit.irp.persistence.dto.StudentLeaveMaster;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.StudentLeaveMasterId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author chetna.pargai
 */
public class StudentLeaveMasterDAO extends HibernateDAO implements StudentLeaveMasterIDAO {

    private static final Log log = LogFactory.getLog(StudentLeaveMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SectionMaster records via Hibernate from the database");
        return this.find("from StudentLeaveMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentLeaveMaster.class, id);
    }

    public int checkIfChildExist(final StudentLeaveMasterId id) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StudentLeaveMaster studentleavemaster = (StudentLeaveMaster) session.get(StudentLeaveMaster.class, id);
                int i1 =  Integer.parseInt(session.createFilter(studentleavemaster.getStudentleavedetails(), "select count(*)").list().get(0).toString());
                return i1;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List doValidate(final StudentLeaveMaster dto) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StudentLeaveMaster.class, "slm");
                criteria.add(Restrictions.eq("slm.id.instituteid", dto.getId().getInstituteid()));
                criteria.add(Restrictions.ne("slm.id.leaveid", dto.getId().getLeaveid()));
                criteria.add(Restrictions.disjunction().add(Restrictions.eq("slm.leavecode", dto.getLeavecode()).ignoreCase()).add(Restrictions.eq("slm.leavedescription", dto.getLeavedescription()).ignoreCase()));
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);
        if (count.intValue() > 0) {
            errors.add("Student Leave Code Already exist");
        }
        return errors;
    }
    
      public List getStudentLeaveMaster(String instituteid) {
        String qryString = "select stm.id.instituteid, stm.id.leaveid,ins.institutecode ,stm.leavecode,stm.leavedescription,stm.mindays,stm.maxdays,stm.halfday,stm.deactive"
                + "    from  StudentLeaveMaster stm,InstituteMaster ins    where stm.id.instituteid = '" + instituteid + "' and stm.id.instituteid = ins.id.instituteid ";
        return getHibernateTemplate().find(qryString);

    }

    public List editStudentLeaveMaster(String instituteid,String leave_id) {
        String qryString = "select stm.id.instituteid, stm.id.leaveid,ins.institutecode ,stm.leavecode,stm.leavedescription,stm.mindays,stm.maxdays,stm.halfday,stm.deactive"
                + "    from  StudentLeaveMaster stm,InstituteMaster ins    where stm.id.instituteid = '" + instituteid + "' and stm.id.instituteid = ins.id.instituteid  and stm.id.leaveid = '"+ leave_id +"'";
        return getHibernateTemplate().find(qryString);

    }
}
