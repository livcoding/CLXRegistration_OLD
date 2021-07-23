/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import java.util.Collection;
import org.apache.commons.logging.Log;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.ProgramSchemeDetailIDAO;
import com.jilit.irp.persistence.dto.ProgramSchemeDetail;
import java.io.Serializable;

import java.util.List;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author subrata.lohar
 */

public class ProgramSchemeDetailDAO extends HibernateDAO implements ProgramSchemeDetailIDAO {
    private static final Log log = LogFactory.getLog(ProgramSchemeDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ProgramSchemeDetail records via Hibernate from the database");
        return this.find("from ProgramSchemeDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ProgramSchemeDetail.class, id);
    }
//
//    public void update(Object record) {
//        getHibernateTemplate().update((ProgramSchemeDetail) record);
//    }
//
//    public void add(Object record) {
//        getHibernateTemplate().save((ProgramSchemeDetail) record);
//    }
//
//    public void delete(Object record) {
//        getHibernateTemplate().delete((ProgramSchemeDetail) record);
//    }
//
//     public List getLTPData(String instituteid, String programschemeid) {
//        List list = null;
//            String qry =    " select b.id.subjectcomponentid, b.ltppassingmarks, b.totalccpmarks, b.noofhours, b.noofclassinaweek, b.byltp, b.deactive " +
//                            " from ProgramSchemeDetail b " +
//                            " where b.id.instituteid = '"+instituteid+"' " +
//                            " and b.id.programschemeid = '"+programschemeid+"' " ;
// try {
//            list = getHibernateTemplate().find(qry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
