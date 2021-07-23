package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet.singh
 */
public interface StudentNRSubjectsIService {

    public List getStudetnInfo(HttpServletRequest request);

    public List getGridData(HttpServletRequest request);

    public void getSemesterCode(ModelMap model, String studentid);

    public List getSubjectCode(HttpServletRequest request);

    public List saveNRSubjects(HttpServletRequest request);

    public List deleteNRSubjects(HttpServletRequest request);
}
