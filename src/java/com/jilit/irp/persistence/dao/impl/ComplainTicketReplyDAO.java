/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ComplainTicketReplyIDAO;
import com.jilit.irp.persistence.dto.ComplainTicketReply;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author vipinkr.sharma
 */
public class ComplainTicketReplyDAO extends HibernateDAO implements ComplainTicketReplyIDAO {

     private static final Log log = LogFactory.getLog(ComplainTicketReplyDAO.class);


    public Collection<?> findAll() {
        log.info("Retrieving all ComplainTicketReply records via Hibernate from the database");
        return this.find("from ComplainTicketReply as tname");
    }


    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ComplainTicketReply.class, id);
    }
}
