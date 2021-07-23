/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.InstituteElectiveTempDataIDAO;
import com.jilit.irp.persistence.dto.InstituteElectiveTempData;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author ashok.singh
 */
public class InstituteElectiveTempDataDAO extends HibernateDAO implements InstituteElectiveTempDataIDAO {

    private static final Log log = LogFactory.getLog(InstituteElectiveTempDataDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all InstituteElectiveTempData records via Hibernate from the database");
        return this.find("from InstituteElectiveTempData as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(InstituteElectiveTempData.class, id);
    }

//     public List getAllSubjectArea(final String instituteid, final String registrationid)
//    {
//         String qryString = " select  a.id.subjectareaid, b.subjectareacode, b.subjectareadescription , count(a.id.subjectid)" +
//                            " from InstituteElectiveTempData a, SubjectAreaMaster b " +
//                            " where a.id.subjectareaid = b.id.subjectareaid " +
//                            " and a.id.registrationid = '"+registrationid+"' " +
//                            " and a.id.instituteid = '"+instituteid+"' " +
//                            " group by  a.id.subjectareaid, b.subjectareacode, b.subjectareadescription" +
//                            " order by b.subjectareacode " ;
//
//            return getHibernateTemplate().find(qryString);
//    }
//      public List getAllSubject(final String instituteid, final String registrationid, final String subjectareaSrt)
//    {
//        List list=null;
//        String qry =    " select p.id.subjectid, p.departmentid,sb.subjectcode,d.departmentcode, p.credit, sb.subjectdesc, p.l, p.t, p.p " +
//                            " from InstituteElectiveTempData p, DepartmentMaster d, SubjectMaster sb" +
//                            " where p.departmentid = d.id.departmentid " +
//                            " and p.id.instituteid = sb.id.instituteid" +
//                            " and p.id.subjectid = sb.id.subjectid" +
//                            " and p.id.instituteid = '"+instituteid+"' " +
//                            " and p.id.registrationid = '"+registrationid+"' " +
//                            " and p.id.subjectareaid in ("+subjectareaSrt+")" ;
// try {
//            System.out.println("query" + qry);
//            list = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//       public List getSubject(final String instituteid, final String subjectid)
//    {
//        List list=null;
//        String qry =        " select p.id.subjectid " +
//                            " from InstituteElectiveTempData p " +
//                            " where p.id.instituteid = '"+instituteid+"' " +
//                            " and p.id.subjectid = '"+subjectid+"' " ;
// try {
//            System.out.println("query" + qry);
//            list = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
