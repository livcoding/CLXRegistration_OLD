package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface SubjectTypeMasterIService {

    public void getSubjectTypeMasterList(Model model);

//    public ModelMap getSubjectTypelov(ModelMap mm);

    public ModelMap getSubjectTypeMasterEdit(HttpServletRequest request, ModelMap mm);
    
    public List getSubjectTypeMasterSave(HttpServletRequest request);
    
    public List getSubjectTypeMasterUpdate(HttpServletRequest request);
    
    public List getSubjectTypeMasterDelete(HttpServletRequest request);
    
    public String checkIfChildExist(HttpServletRequest request);
}
