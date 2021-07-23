/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Sct_KioskMarqueeTextIDAO;
import com.jilit.irp.persistence.dto.Sct_KioskMarqueeText;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 *
 * @author ibrahimb.shaik
 */
public class Sct_KioskMarqueeTextDAO extends HibernateDAO implements Sct_KioskMarqueeTextIDAO {

    private static final Log log = LogFactory.getLog(Sct_KioskMarqueeTextDAO.class);

    public List getMaxSnoQuery() {

            List l = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String qryString = "SELECT   max(sno) from sct_kioskmarqueetext";
                    return session.createSQLQuery(qryString).list();
                }
            });
            // System.err.println("SSSSSSSSSSS"+list.size());
            return (ArrayList) l;
        }
        public List getIndividualUserIds() {

            List l = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String qryString = "SELECT   * from sct_irpusers";
                    return session.createSQLQuery(qryString).list();
                }
            });
            // System.err.println("SSSSSSSSSSS"+list.size());
            return (ArrayList) l;
        }

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_KioskMarqueeText records via Hibernate from the database");
        return this.find("from Sct_KioskMarqueeText as tname");
    }

   public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_KioskMarqueeText.class, id);
    }
   public List  getUserCodeData(final String usercode ,final String membertype){

        List l = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qryString="";
                if(membertype.equals("S"))
                    qryString=" select studentid, enrollmentno, name from StudentMaster where enrollmentno='"+usercode+"' ";
                else
                    qryString = "SELECT  EMPLOYEEID, EMPLOYEECODE, EMPLOYEENAME " +
                        " from v_Staff  " +
                        " WHERE  stafftype='"+membertype+"'" +
                        " and employeecode='"+usercode+"'";
                return session.createSQLQuery(qryString).list();
            }
        });
        return (ArrayList) l;
    }
}

