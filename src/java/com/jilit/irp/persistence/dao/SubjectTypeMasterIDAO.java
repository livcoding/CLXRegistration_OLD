package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.SubjectTypeMaster;
import com.jilit.irp.persistence.dto.SubjectTypeMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author campus.trainee
 */
public interface SubjectTypeMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public int checkIfChildExist(SubjectTypeMasterId id);

    public List doValidate(SubjectTypeMaster subjectTypeMaster, String string);

    public List getSubjectType(String ins_id);

    public List getAllSubjectType(final String instituteid);

    public List getSubjectTypeCode(String instituteid);

}
