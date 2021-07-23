/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ScholarshipCriteriaId;
import com.jilit.irp.persistence.dto.ScholarshipCriteria;
import com.jilit.irp.persistence.dao.ScholarshipCriteriaIDAO;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author ashok.singh
 */
    public class ScholarshipCriteriaDAO extends HibernateDAO implements ScholarshipCriteriaIDAO
    {
        private static final Log log = LogFactory.getLog(ScholarshipCriteriaDAO.class);
//
//        public int checkIfChildExist(final String Id) {
//
//        }

//         public List doValidate(final ScholarshipCriteria slCriteria, final String mode) {
//          System.out.println("Inside do validate methods");
//            List<String> errors = new ArrayList<String>();
//
//       return errors;
//    }

       public Collection<?> findAll() {
         log.info("Retrieving all ScholarshipCriteria records via Hibernate from the database");
         return this.find("from ScholarshipCriteria as tname");
        }
        public Object findByPrimaryKey(Serializable id) {
             return getHibernateTemplate().get(ScholarshipCriteria.class, id);
        }
//     public List getAllSessionCode(String instituteid) {
//         String query=  "   select distinct sc.id.sessionid, sc.id.categoryid, sc.id.scholarshiptypeid, sc.id.instituteid, sc.id.academicyear, sc.id.programid, sc.id.branchid" +
//                        "   from ScholarshipCriteria as sc " +
//                        "   where sc.id.instituteid = ? " +
//                        "   and coalesce(sc.deactive, 'N') = 'N'";
//         return  getHibernateTemplate().find(query, instituteid);
//         }
//
//     public ArrayList getAllScholarshipCriteriaDeatails(String instituteid,String sessionid,String sessioncode){
//        String query=   " select sc.id.academicyear, pm.programcode, bm.branchcode " +
//                        " from ScholarshipCriteria  sc, ProgramMaster  pm, BranchMaster  bm " +
//                        " where sc.id.instituteid = ? " +
//                        " and sc.id.sessionid = ? " +
//                        " and sc.sessioncode = ?" +
//                        " and pm.id.instituteid = sc.id.instituteid " +
//                        " and pm.id.programid   = sc.id.programid " +
//                        " and bm.id.instituteid = sc.id.instituteid " +
//                        " and bm.id.programid = sc.id.programid " +
//                        " and bm.id.branchid = sc.id.branchid" +
//                        " and coalesce(sc.deactive,'N') = 'N'";
//         System.err.println("Scholarship Details:"+query);
//        return (ArrayList) getHibernateTemplate().find(query, new Object[]{instituteid, sessionid, sessioncode});
//     }
//
//    public ArrayList getScholarshipCriteriaTreeDeatails(String instituteid, String programid, String branchid, String sessionid, String sessioncode) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public int checkIfChildExist(ScholarshipCriteriaId Id) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
 
}
