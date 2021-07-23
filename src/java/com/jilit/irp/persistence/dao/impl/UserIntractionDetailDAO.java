/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.UserIntractionDetailIDAO;
import com.jilit.irp.persistence.dto.UserIntractionDetail;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class UserIntractionDetailDAO extends HibernateDAO implements UserIntractionDetailIDAO
{

    private static final Log log = LogFactory.getLog(UserIntractionDetailDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all UserIntractionDetail records via Hibernate from the database");
        return this.find("from UserIntractionDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(UserIntractionDetail.class, id);
    }

}
