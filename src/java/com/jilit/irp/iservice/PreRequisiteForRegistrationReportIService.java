package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface PreRequisiteForRegistrationReportIService {

    public void getCombo(ModelMap model);

    public List getBranchCode(HttpServletRequest request);

    public List getAllPreRequisiteForRegistrationReportData(HttpServletRequest request);
}
