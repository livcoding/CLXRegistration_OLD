/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.ExamGradeMasterDeatilIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ExamGradeMasterDetail;
import com.jilit.irp.persistence.dto.ExamGradeMasterDetailId;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author priyanka.tyagi
 */
public class ExamGradeMasterDetailDAO extends HibernateDAO implements ExamGradeMasterDeatilIDAO {

    @Override
    public Collection<?> findAll(String instituteid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
