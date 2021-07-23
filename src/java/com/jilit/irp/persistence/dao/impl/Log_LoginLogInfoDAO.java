/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.util.OLTEncryption.EncryptionException;
import org.apache.commons.logging.Log;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Log_LoginLogInfoIDAO;

import com.jilit.irp.persistence.dto.Log_LoginLogInfo;
import com.jilit.irp.persistence.dto.Log_LoginLogInfoId;
import com.jilit.irp.util.OLTEncryption;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Date;
import javax.mail.internet.MailDateFormat;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author akshya.gaur
 */
public class Log_LoginLogInfoDAO extends HibernateDAO implements Log_LoginLogInfoIDAO {

    private static final Log log = LogFactory.getLog(Log_LoginLogInfoDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Log_LoginLogInfo records via Hibernate from the database");
        return this.find("from Log_LoginLogInfo as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return null;
    }

    public String getSlno(String userid, Date date) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select mo.slno from Log_LoginLogInfo mo where mo.id.userid = :userid and mo.id.logindatetime= :date ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("userid", userid).
                    setParameter("date", date).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            return list.get(0) == null ? "0" : list.get(0).toString();
        } else {
            return "0";
        }
    }
}
