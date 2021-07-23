package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author campus.trainee
 */
public interface GradeImprovementCriteriaMasterIServices {

    public void getListGradeImprovementCriteriaMaster(ModelMap model);

    public void getAllProgramMaster(ModelMap model);

    public List saveGradeImprovementCriteriaMaster(HttpServletRequest req);

    public ModelMap editGradeImprovementCriteriaMaster(HttpServletRequest request, ModelMap model);
    
    public List deleteGradeImprovementCriteriaMaster(HttpServletRequest request);
    
    public List updateGradeImprovementCriteriaMaster(HttpServletRequest req);

}
