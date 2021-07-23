/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.WorkingDaysIDAO;
import com.jilit.irp.persistence.dto.WorkingDays;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ashok.singh
 */
public class WorkingDaysDAO extends HibernateDAO implements WorkingDaysIDAO {

    private static final Log log = LogFactory.getLog(WorkingDaysDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all WorkingDays records via Hibernate from the database");
        return this.find("from WorkingDays as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(WorkingDays.class, id);
    }
//
//    public List getWorkingDayData(String instituteid) {
//        String hqlQry = " select new Map(a.monday as monday, a.tuesday as tuesday, a.wednesday as wednesday, a.thursday as thursday, a.friday as friday, a.saturday as saturday, a.sunday as sunday,a.deactive as deactive)" +
//                " from WorkingDays a" +
//                " where a.instituteid='"+ instituteid +"'";
//        List list = new ArrayList();
//        try {
//            list = getHibernateTemplate().find(hqlQry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    
}
