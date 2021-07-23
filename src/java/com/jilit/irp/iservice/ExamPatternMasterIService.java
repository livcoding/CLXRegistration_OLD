package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet Singh
 */
public interface ExamPatternMasterIService {

    public void getExamPatternMasterList(Model model);

    public List saveExamPatternMaster(HttpServletRequest request);

    public ModelMap getExamPatternMasterEdit(HttpServletRequest request, ModelMap mm);

    public List updateExamPatternMaster(HttpServletRequest request);

    public void getComponentCode(ModelMap model, HttpServletRequest req);
}
