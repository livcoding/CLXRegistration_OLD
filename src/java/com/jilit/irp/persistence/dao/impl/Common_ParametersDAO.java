/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Common_ParametersIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Parameters;
import com.jilit.irp.persistence.dto.Sct_IrpModules;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;

/**
 *
 * @author bhopals.rawat
 */
public class Common_ParametersDAO extends HibernateDAO implements Common_ParametersIDAO {

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
