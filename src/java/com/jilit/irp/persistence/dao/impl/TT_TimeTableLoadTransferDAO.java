/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.TT_TimeTableLoadTransferIDAO;
import com.jilit.irp.persistence.dto.TT_TimeTableLoadTransfer;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author yatendra.singh
 */
public class TT_TimeTableLoadTransferDAO extends HibernateDAO implements  TT_TimeTableLoadTransferIDAO {

    private static final Log log = LogFactory.getLog(TT_TimeTableLoadTransferDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_TimeTableLoadTransfer records via Hibernate from the database");
        return this.find("from TT_TimeTableLoadTransfer as ttime");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_TimeTableLoadTransfer.class, id);
    }

}
