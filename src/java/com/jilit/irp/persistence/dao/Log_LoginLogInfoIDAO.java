/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface Log_LoginLogInfoIDAO extends IDAO {

    public String getSlno(String userid,Date date);

}
