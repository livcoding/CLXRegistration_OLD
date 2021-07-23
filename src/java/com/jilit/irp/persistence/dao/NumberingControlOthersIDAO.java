/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.NumberingControlOthers;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ashok.singh
 */
public interface NumberingControlOthersIDAO  extends IDAO
{
   public NumberingControlOthers getNumberingControlData(  final String pymd,   final String pGroupID,   Session session) ;
}
