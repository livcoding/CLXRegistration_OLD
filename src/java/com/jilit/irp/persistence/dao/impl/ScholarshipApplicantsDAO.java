/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.util.JIRPDBUtil;
import com.jilit.irp.util.JIRPSession;
import java.sql.SQLException;
//import com.jilit.irp.data.FilterInfoData;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Restrictions;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ScholarshipApplicantsId;
import com.jilit.irp.persistence.dto.ScholarshipApplicants;
import com.jilit.irp.persistence.dao.ScholarshipApplicantsIDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author ashok.singh
 */

public class ScholarshipApplicantsDAO extends HibernateDAO implements ScholarshipApplicantsIDAO
{
    private static final Log log = LogFactory.getLog(ScholarshipApplicantsDAO.class);

    public List doValidate(final ScholarshipApplicants slApp, final String mode) {
          System.out.println("Inside do validate methods");
            List<String> errors = new ArrayList<String>();
            Integer count =new Integer(0);
             count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
                            public Object doInHibernate(Session session) {
                              Criteria criteria = session.createCriteria(ScholarshipApplicants.class);
                              criteria.add(Restrictions.eq("id.sessionid", slApp.getId().getSessionid()));
                              criteria.add(Restrictions.eq("id.studentid", slApp.getId().getStudentid()));
                              criteria.add(Restrictions.eq("id.scholarshiptypeid", slApp.getId().getScholarshiptypeid()));
                              criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                            return criteria.list();
                        }
                        }).get(0);
                    if (mode.equals("edit")){
                            if (count.intValue() == 0) {
                                errors.add("Applicants not found for the selected Session Code and Scholarship Type!");
                            }
                        }
                    else{
                        if (count.intValue() > 0) {
                            errors.add("Applicants already exist for the selected Session Code and Scholarship Type!");
                        }
                    }
       return errors;
    }

    public Collection<?> findAll()
    {
      log.info("Retrieving all ScholarshipApplicants records via Hibernate from the database");
     return this.find("from ScholarshipApplicants as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
            return getHibernateTemplate().get(ScholarshipApplicants.class, id);
    }

      public int checkIfChildExist(final ScholarshipApplicantsId scholarshipapplicantsid)  {

        return 0;
    }

//     public List getScholarshipTypeAppliedStudents(String sessionid, String scholarshiptypeid, String instituteid){
//           String qryString = "Select sm.studentid,sm.enrollmentno, sm.name"  +
//                              " from StudentMaster sm, ScholarshipApplicants sa  " +
//                              "  where sm.studentid=sa.id.studentid and ( sm.activestatus is null or sm.activestatus='A') and sa.scholarshipapplied='Y' and coalesce(sa.approvedscholarship,'N')='N' and sa.id.sessionid=? and sa.id.scholarshiptypeid=? and sm.instituteid=? " ;
//            List list = getHibernateTemplate().find(qryString,  new Object[]{sessionid, scholarshiptypeid, instituteid});
//            return list;
//      }
//
//    public List checkScholarshipAppliedStudentExist(String sessionId, String scholarshipTypeid, String enrollmentno, String instituteid){
//           String qryString = "Select sm.studentid, sm.name"  +
//                              " from StudentMaster sm, ScholarshipApplicants sa  " +
//                              "  where sm.studentid=sa.id.studentid and ( sm.activestatus is null or sm.activestatus='A') and sa.scholarshipapplied='Y' and coalesce(sa.approvedscholarship,'N')='N' and sa.id.sessionid=? and sa.id.scholarshiptypeid=? and sm.enrollmentno=? and sm.instituteid=? " ;
//            List list = getHibernateTemplate().find(qryString,  new Object[]{sessionId, scholarshipTypeid, enrollmentno,instituteid});
//            return list;
//      }
}


