/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.AttendancefollowupmasterIDAO;
import com.jilit.irp.persistence.dto.Attendancefollowupmaster;
import com.jilit.irp.persistence.dto.AttendancefollowupmasterId;
import com.jilit.irp.util.JIRPSession;
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
 * @author Chandan kumar singh
 */
public class AttendancefollowupmasterDAO extends HibernateDAO implements AttendancefollowupmasterIDAO {
  private static final Log log = LogFactory.getLog(AttendancefollowupmasterDAO.class);


  
   public Collection<?> findAll() {
        log.info("Retrieving all Attendancefollowupmaster records via Hibernate from the database");
        return this.find("from Attendancefollowupmaster as tname");
    }



  public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all Attendancefollowupmaster records via Hibernate from the database");
        return this.find("from Attendancefollowupmaster as tname where tname.id.instituteid = ? ", new Object[]{instituteid});
    }
   public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Attendancefollowupmaster.class, id);
    }


//
//
//   public List doValidate(final Attendancefollowupmaster dto,final String mode) {
//         List<String> errors = new ArrayList<String>();
//        Integer count = new Integer(0);
//        if (mode.equals("save")) {
//            count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//                @Override
//                public Object doInHibernate(Session session) {
//                    Criteria criteria = session.createCriteria(Attendancefollowupmaster.class);
//                    criteria.add(Restrictions.eq("id.registrationid", dto.getId().getRegistrationid()));
//                    criteria.add(Restrictions.eq("id.followupno", dto.getId().getFollowupno()));
//                    criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                    return criteria.list();
//                }
//            }).get(0);
//            if (count.intValue() > 0) {
//                errors.add("Attendance Follow up Already exist");
//            }
//        }
//        return errors;
//    }
//
//
//
//    public int checkIfChildExist(final AttendancefollowupmasterId ids) {
//        HibernateCallback callback = new HibernateCallback() {
//                public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                    Attendancefollowupmaster dto = (Attendancefollowupmaster) session.get(Attendancefollowupmaster.class, ids);
//                    int i1 = ((Integer) session.createFilter(dto.getStudentattendancefollowup(), "select count(*)").list().get(0)).intValue();
//                    return i1;
//                }
//        };
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    }
//
//    public List getAttendancefollowupmasterData(String instId,String regid)
//    {
//        List rtnList = new ArrayList();
//        try {
//            String str = " Select  a.id.followupno, " +
//                    "  a.criteriatype, " +
//                    "  a.reportingdate ," +
//                    "  a.followupfromdate, " +
//                    "  a.followuptodate, " +
//                    "  a.remarks, " +
//                    "  a.deactive, " +
//                    "  a.entryby, " +
//                    "  a.entrydate, " +
//                    "  rm.registrationcode " +
//                    "  from " +
//                    " Attendancefollowupmaster a ,RegistrationMaster rm " +
//                    " where " +
//                    "  a.id.instituteid=rm.id.instituteid and " +
//                    "  a.id.instituteid='" + instId + "' and " +
//                    " a.id.registrationid='" + regid + "' and " +
//                    " a.id.registrationid=rm.id.registrationid " +
//                    " order by a.id.registrationid";
//            rtnList = getHibernateTemplate().find(str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rtnList;
//
//    }
    }