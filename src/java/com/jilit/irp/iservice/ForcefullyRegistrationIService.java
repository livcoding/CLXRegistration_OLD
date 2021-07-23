package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface ForcefullyRegistrationIService {

    public void getProgramCode(ModelMap model);

    public List getBranchCode(HttpServletRequest req, Model model);

    public List getRegistrationNumber(HttpServletRequest req, Model model);

    public List updateForcefullyRegistration(HttpServletRequest req);

}
