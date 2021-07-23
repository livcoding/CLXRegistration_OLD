package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Nazar.Mohammad
 */
public interface SubjectMasterIService {

    public void getList(Model model);

    public List deleteSubjectMaster(HttpServletRequest request);

    public void subjectMasterAdd(Model model);

    public List addSubjectMaster(HttpServletRequest request);

    public String checkIfChildExist(HttpServletRequest request);

    public ModelMap subjectMasterEdit(ModelMap model, HttpServletRequest request);

    public List updateSubjectMaster(HttpServletRequest request);
}
