/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.EmployeePhoto;

/**
 *
 * @author ashok.singh
 */
public interface EmployeePhotoIDAO extends IDAO {

    public EmployeePhoto getPhoto(String employeeid);

}
