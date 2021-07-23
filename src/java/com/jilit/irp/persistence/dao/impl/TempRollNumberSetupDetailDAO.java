package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.TempRollNumberSetupDetailIDAO;
import com.jilit.irp.persistence.dto.TempRollNumberSetupDetail;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetupDetailDAO extends HibernateDAO implements TempRollNumberSetupDetailIDAO {

    @Override
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TempRollNumberSetupDetail.class, id);
    }

}
