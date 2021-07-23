/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


import com.jilit.irp.persistence.dao.PreRequisitAcademicYearwiseIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.PreRequisitAcademicYearwise;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;


import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;


import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;




/**
 *
 * @author singh.jaswinder
 */
public class PreRequisitAcademicYearwiseDAO extends HibernateDAO implements PreRequisitAcademicYearwiseIDAO  {

private static final Log log = LogFactory.getLog(PreRequisitAcademicYearwise.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PreRequisitAcademicYearwise records via Hibernate from the database");
        return this.find("from PreRequisitAcademicYearwise as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PreRequisitAcademicYearwise.class, id);
    }    
    
//
//    public void update(Object record) {
//        getHibernateTemplate().update((PreRequisitAcademicYearwise) record);
//    }
//    public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((PreRequisitAcademicYearwise) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((PreRequisitAcademicYearwise) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((PreRequisitAcademicYearwise) record);
//    }
//
//    public List getPrerequisiteData_hql(final int firstResult, final int maxResult, final boolean rowcount) {
//         final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                String qry = "select distinct prm.id.instituteid, prm.id.subjectsetid, prm.programid, v.programcode, v.sectioncode, prm.stynumber," +
//                        "smr.id.subjectid, smr.subjectcode, smr.subjectdesc, sms.id.subjectid, sms.subjectcode, sms.subjectdesc, prm.stypattern, prm.acadyear " +
//                        "from PreRequisitAcademicYearwise as prm, V_ProgrmSecBranchSem as v, PreRequisitAcadWiseRequired as prmr, " +
//                        "SubjectMaster as smr, PreRequisitAcademicSubjects as prms, SubjectMaster sms where " +
//                        "prm.id.instituteid=v.instituteid and " +
//                        "prm.programid=v.programid and " +
//                        "prm.stynumber = v.stynumber and " +
//                        "prm.sectionid = v.sectionid and " +
//                        "prm.id.instituteid=prmr.id.instituteid and " +
//                        "prm.id.subjectsetid=prmr.id.subjectsetid and " +
//                        "prmr.id.instituteid=smr.id.instituteid and " +
//                        "prmr.id.prereqsubjectid=smr.id.subjectid and " +
//                        "prm.id.instituteid=prms.id.instituteid " +
//                        "and prm.id.subjectsetid=prms.id.subjectsetid " +
//                        "and prms.id.instituteid=sms.id.instituteid and " +
//                        "prms.id.subjectid=sms.id.subjectid";
//                Query q1 = session.createQuery(qry);
//                if(firstResult>=0)
//                {
//                    q1.setFirstResult(firstResult);
//                    q1.setMaxResults(maxResult);
//                }
//                List result = null;
//                if(rowcount)
//                {
//                    result = new ArrayList();
//                    result.add(q1.list().size());
//                }
//                else
//                {
//                    result = q1.list();
//                }
//                System.err.println(result.size());
//                System.err.println("firstResult"+firstResult);
//                System.err.println("maxResult"+maxResult);
//                return result;
//            }
//        });
//        return list;
//    }


}
