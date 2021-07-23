/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.PrFacultyEventTaggingIDAO;

import com.jilit.irp.persistence.dto.PRFacultyEventTagging;
import com.jilit.irp.persistence.dto.PRFacultyEventTaggingId;
import com.jilit.irp.persistence.dto.V_Staff;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author singh.amarjeet
 */
public class PrFacultyEventTaggingDAO  extends HibernateDAO   implements PrFacultyEventTaggingIDAO
{
        private static final Log log = LogFactory.getLog(PRFacultyEventDAO.class);
        public Collection<?> findAll() {
             log.info("Retrieving all DepartmentMaster records via Hibernate from the database");
             return this.find("from SubjectMaster as tname");
    }
            public Object findByPrimaryKey(Serializable id) {
            return getHibernateTemplate().get(PRFacultyEventTagging.class, id);
    }

     public Object load(Serializable id) {
          return    getHibernateTemplate().load(PRFacultyEventTagging.class, id);
    }

     public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((PRFacultyEventTagging) record);
      
    }
//  public int checkIfChildExist(final PRFacultyEventTaggingId prfacultyeventid)
//   {
//           HibernateCallback callback = new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException{
//               PRFacultyEventTagging prfaculty = (PRFacultyEventTagging)session.get(PRFacultyEventTagging.class, prfacultyeventid);
//             /*  int i1= ( (Integer) session.createFilter( submaster.getPrfacultysubjectchoiceses(), "select count(*)" ).list().get(0) ).intValue();
//               return  i1; */
//               /* Here no child is exists in the PrFacultyEventTaggingDao
//                  So return value is 0
//                */
//               return 0;
//            }
//           };
//         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
//  }
//
//  public  List<String> doValidate(final PRFacultyEventTagging prfacultyevent, final String mode)
//  {
//          List<String> errors = new ArrayList<String>();
//          List<PRFacultyEventTagging> checkForPrimaryKey =new ArrayList<PRFacultyEventTagging>();
//     if(mode.equals("save"))
//     {
//        checkForPrimaryKey =(List)getHibernateTemplate().executeFind(new HibernateCallback()
//                             {
//                                 public Object doInHibernate(Session session)
//                                 {
//                                   String queryString = "from PRFacultyEvent as prf where prf.id.instituteid=:instituteid    and   prf.id.registrationid=:registrationid  and prf.id.eventno=:eventno";
//                                      Query qs=session.createQuery(queryString);
//                                      qs.setParameter("instituteid", prfacultyevent.getId().getInstituteid());
//                                      qs.setParameter("eventno", prfacultyevent.getId().getEventno());
//                                      qs.setParameter("registrationid", prfacultyevent.getId().getRegistrationid());
//                                      return qs.list();
//                                      /*
//                                      Criteria criteria = session.createCriteria(PRFacultyEvent.class);
//                                        criteria.add(Expression.eq("id.instituteid", prfacultyevent.getId().getInstituteid()));
//                                        criteria.add(Expression.eq("id.eventno", prfacultyevent.getId().getEventno()));
//                                        criteria.add(Expression.eq("id.registrationid", prfacultyevent.getId().getRegistrationid()));
//                                        return criteria.list();
//                                       */
//                                  }
//                              }
//                           );
//
//	               	if(!checkForPrimaryKey.isEmpty())
//                            {
//                              errors.add("Event SLNO already exist, please enter different Event SLNO");
//                            }
//      }
//       return errors;
//}
//
//  public  int getMaxEventSlno( final String registrationid, final  String instituteid)
//  {
//       HibernateCallback callback = new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException{
//               String queryString = "select max(prf.id.eventno)     from     PRFacultyEvent as prf where prf.id.instituteid=:instituteid    and   prf.id.registrationid=:registrationid ";
//                                      Query qs=session.createQuery(queryString);
//                                      qs.setParameter("instituteid", instituteid);
//                                      qs.setParameter("registrationid", registrationid);
//
//                 if(qs.list()!=null  &&  !qs.list().isEmpty() &&  qs.list().size()>0)
//                 {
//                       return ( qs.list().get(0)!=null?qs.list().get(0):0);
//                 }
//                  else
//                  {
//                         return 0;
//                  }
//
//
//            }
//           };
//         return ((Integer)getHibernateTemplate().execute(callback));
//  }
//  public List getGridData()
//  {
//          List l=null;
//
//
//       return l;
//  }
}
