/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ComplainTicketPerson;

/**
 *
 * @author vipinkr.sharma
 */
public interface ComplainTicketPersonIDAO extends IDAO{

    public int checkIfChildExist(final ComplainTicketPerson id);

}
