package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.OldvsNewSubject;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface OldvsNewSubjectIDAO extends IDAO {

    public List getSubjectcode(String instituteid);

    public List getSemesterCode(String instituteid);

    public List getAllDataOldVsNewSubject(String instituteid);

    public List getEditOldvsNewSubject(String instituteid, String registrationid, String subjectid);

    public List<String> doValidate(final OldvsNewSubject dto, final String mode);

}
