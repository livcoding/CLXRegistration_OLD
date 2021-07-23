/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ProgramTypeProgramTaggingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.ProgramType;
import com.jilit.irp.persistence.dto.ProgramType;
import com.jilit.irp.persistence.dto.ProgramTypeProgramTagging;
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
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author sunny.singhal
 */
public class ProgramTypeProgramTaggingDAO extends HibernateDAO implements ProgramTypeProgramTaggingIDAO{

    private static final Log log = LogFactory.getLog(ProgramTypeProgramTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramTypeProgramTagging records via Hibernate from the database");
        return this.find("from ProgramTypeProgramTagging as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramTypeProgramTagging.class, id);
    }


//     public String  getProgramTypeLevel(String instituteid,String studentid){
//        String enrollmentno = null;
//        String qry="select a from ProgramType a where a.id.instituteid='"+instituteid+"' " +
//                " and a.id.programtypeid= in (select b.id.programtypeid from ProgramTypeProgramTagging b " +
//                "  where b.id.instituteid='"+instituteid+"' and b.id.programid in (select c.programid from StudentMaster c where c.studentid = '"+studentid+"' ))" ;
//        ProgramType pt=new ProgramType();
//        List l = (ArrayList) getHibernateTemplate().find(qry);
//        if (!l.isEmpty()) {
//            pt = (ProgramType) l.get(0);
//            enrollmentno = pt.getEnrollmentcode();
//        }
//
//            return enrollmentno;
//    }

}
