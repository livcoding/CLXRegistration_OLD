/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author soa university
 */
public interface Notify_NotificationAlertMasterIDAO extends IDAO {

    public List getSMSEmailPortalData(final String memberid, final String membertype);

}
