/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface UpdateStyNumberIDAO extends IDAO {

    public String saveData(final List updateStyNumberList, final List updateStudentMasterList);

    public List getStudentMaster(final String studentid);
//   public List getacedmicyear(final String instituteid ) ;
}
