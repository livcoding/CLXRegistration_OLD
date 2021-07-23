package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface PreRequisiteForRegistrationIService {

    List getAllPreRequisiteForRegistration(HttpServletRequest request);

    List getAllBranchCode(HttpServletRequest request);

    void getAllStyCode(ModelMap model);

    ModelMap getEditPreRequisiteForRegistration(HttpServletRequest request, ModelMap model);

    List getSavePreRequisiteForRegistration(HttpServletRequest request);

    List getUpdatePreRequisiteForRegistration(HttpServletRequest request);

    String checkIfChildExist(HttpServletRequest request);

    List getDeletePreRequisiteForRegistration(HttpServletRequest request);

}
