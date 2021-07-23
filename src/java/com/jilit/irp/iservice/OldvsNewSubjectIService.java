package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface OldvsNewSubjectIService {

    public void getAllOldvsNewSubject(ModelMap model);

    public void getAllCombo(ModelMap model);

    public ModelMap getEditOldvsNewSubject(HttpServletRequest request, ModelMap model);

    public List getSaveOldvsNewSubject(HttpServletRequest request);

    public List getUpdateOldvsNewSubject(HttpServletRequest request);
    
    public List getDeleteOldvsNewSubject(HttpServletRequest request);
}
