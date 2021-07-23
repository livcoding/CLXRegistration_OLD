/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.EmployeePhotoIDAO;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.EmployeePhoto;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ashok.singh
 */
public class EmployeePhotoDAO extends HibernateDAO implements EmployeePhotoIDAO {

    private static final Log log = LogFactory.getLog(EmployeePhotoDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all EmployeePhoto records via Hibernate from the database");
        return this.find("from EmployeePhoto as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(EmployeePhoto.class, id);
    }
 public EmployeePhoto getPhoto(String employeeid){
        EmployeePhoto employeePhoto = null;
        List list =new ArrayList();
        try {
            String query= " select ep " +
                      " from EmployeePhoto ep" +
                      " where ep.employeeid = '"+employeeid+"' ";

        list = getHibernateTemplate().find(query);
        if(list != null && list.size() > 0 ){
            employeePhoto = (EmployeePhoto) list.get(0);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeePhoto;
    }    

}
