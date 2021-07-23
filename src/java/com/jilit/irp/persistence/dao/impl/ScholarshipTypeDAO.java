/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ScholarshipType;
import com.jilit.irp.persistence.dao.ScholarshipTypeIDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author ashok.singh
 */
public class ScholarshipTypeDAO  extends HibernateDAO implements ScholarshipTypeIDAO
{
    private static final Log log = LogFactory.getLog(ScholarshipTypeDAO.class);




    public Collection<?> findAll() {
         log.info("Retrieving all ScholarshipType records via Hibernate from the database");
         return this.find("from ScholarshipType as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ScholarshipType.class, id);
    }

     public int checkIfChildExist(final String scholarshipTypeId) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException{
                ScholarshipType ins = (ScholarshipType)session.get(ScholarshipType.class, scholarshipTypeId);
               int i1= ( (Integer) session.createFilter( ins.getScholarshipcriterias(), "select count(*)" ).list().get(0) ).intValue();
               //int i2= ( (Integer) session.createFilter( ins.getDesignationmasterdetails(), "select count(*)" ).list().get(0) ).intValue();
               int i2= ( (Integer) session.createFilter( ins.getScholarshipapplicantses(), "select count(*)" ).list().get(0) ).intValue();

            return i1+i2;
            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
     }



//      public List doValidate(final ScholarshipType slType,final String mode) {
//         List<String> errors = new ArrayList<String>();
//         Integer count =new Integer(0);
//         count =(Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(ScholarshipType.class);
//                 criteria.add(Restrictions.eq("scholarshiptypecode", slType.getScholarshiptypecode()).ignoreCase());
//                  if(mode.equals("edit")){
//                      criteria.add(Restrictions.ne("scholarshiptypeid", slType.getScholarshiptypeid()));   //Do not check for itself when updating record
//                  }
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
//		if(count.intValue()>0){
//            errors.add("Same Values Of Scholarship Type Code Code exist");
//        }
//        return errors;
//    }
//
//      public List getScholarshipType(String instituteid, String sessionid){
//           String qryString = "select distinct st.scholarshiptypeid, st.scholarshiptypecode, st.scholarshiptypedesc" +
//                              " from ScholarshipType st, ScholarshipCriteria sc  " +
//                              "  where  st.scholarshiptypeid = sc.id.scholarshiptypeid and sc.id.sessionid = ? and sc.id.instituteid = ? " ;
//            List list = getHibernateTemplate().find(qryString,  new Object[]{sessionid, instituteid});
//            return list;
//      }
//
//       public List checkScholarshipTypeCodeExist(final String sessionid,final String scholarshiptypeode) {
//        String query = "select st.scholarshiptypeid from ScholarshipType st, ScholarshipCriteria sc where st.scholarshiptypeid=sc.id.scholarshiptypeid"+
//                         " and sc.id.sessionid='"+sessionid+"' and st.scholarshiptypecode='"+scholarshiptypeode+"'";
//        return getHibernateTemplate().find(query);
//    }
}
