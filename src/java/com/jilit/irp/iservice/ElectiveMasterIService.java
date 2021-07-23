package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
public interface ElectiveMasterIService {

    public void getElectiveMaster(ModelMap model);

    public List saveElectiveMaster(HttpServletRequest req);

    public void editElectiveMaster(ModelMap mm, HttpServletRequest request);

    public List updateElectiveMaster(HttpServletRequest req);

    public String checkIfChildExist(HttpServletRequest req);

    public List deleteElectiveMaster(HttpServletRequest req);

}
