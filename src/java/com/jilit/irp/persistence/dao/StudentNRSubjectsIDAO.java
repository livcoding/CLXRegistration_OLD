/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;
import java.util.List;
import com.jilit.irp.persistence.dto.StudentNRSubjects;

/**
 *
 * @author shimona.khandelwal
 */
public interface StudentNRSubjectsIDAO extends IDAO {

    public List getStudentInfo(String instituteid, String enrollmentno);

    public List getStudentNRSubjects(String instituteid, String studentid);

    public List getPreviousRegCode(String instituteid, String studentid);

    public List getPreviousSubject(String instituteid, String studentid, String subjecttype, String regid);

    public List doValidate(final StudentNRSubjects dto, final String mode);

}
