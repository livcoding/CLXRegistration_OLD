 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.MeetingAgendaMaster;
import com.jilit.irp.persistence.dto.MeetingAgendaMasterId;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author campus.trainee
 */
public interface MeetingAgendaMasterIDAO extends IDAO{

    public int checkIfChildExist(final String MeetingAgendaMasterId);

    public List doValidate(MeetingAgendaMaster meetingAgendaMaster, String string);

   // public List doValidate();

    public void saveOrUpdate(Object record);

    public Collection<?>getAgenda(final String instituteId, final String committeeId);

    public Collection<?> agendaExits(final String instituteId, final String committeeId,  final String agendadesc) ;
}

