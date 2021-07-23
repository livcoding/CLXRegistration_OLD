/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author chetna.pargai
 */
public interface StudentActivityDetailIDAO extends IDAO {

    public List getAllStyNumberWithAllOptionLOV(String instituteid);
}
