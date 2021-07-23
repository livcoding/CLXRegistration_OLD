package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import java.io.Serializable;
import java.util.Collection;

import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface SubjectComponentIDAO extends IDAO {

    public int checkIfChildExist(final SubjectComponentId uid);

    public List doValidate(final SubjectComponent subjectComponent, final String mode);

    public Collection<?> findAll(final String instituteid);

    public Collection<?> findAllWithDeactive(final String instituteid);

    public Collection<?> findAll(final String subjectid, final String instituteid);

    public Object findByPrimaryKey1(Serializable id);

    public List getSubjectComponentCode(String instituteid);

    public List getComponentIdOfSubject(String instituteid, String subjectid);

    public List getComponentIdOfSubjectForAddDrop(String instituteid, String subjectid, String registrationid);

    public String getComponentIdByCode(String instituteid, String subjectcomponentcode);

}
