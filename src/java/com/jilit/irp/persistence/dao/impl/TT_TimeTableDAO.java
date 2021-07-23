/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dto.TT_TimeTableId;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TT_TimeTableIDAO;
import com.jilit.irp.persistence.dto.TT_TimeTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author ashok.singh
 */
public class TT_TimeTableDAO  extends HibernateDAO implements TT_TimeTableIDAO{
     private static final Log log = LogFactory.getLog(TT_TimeTableDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_TimeTable records via Hibernate from the database");
        return this.find("from TT_TimeTable as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_TimeTable.class, id);
    }
}



