package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface SemesterTypeMasterIService {

    public void getSemesterTypeMasterList(Model model);

    public ModelMap getSemesterTypeMasterEdit(HttpServletRequest request, ModelMap mm);

    public List getSemesterTypeMasterSave(HttpServletRequest request);

    public List getSemesterTypeMasterUpdate(HttpServletRequest request);

    public List getSemesterTypeMasterDelete(HttpServletRequest request);

    public String checkIfChildExist(HttpServletRequest request);
}
