package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface Sis_RegistrationActivityMasterIService {

    void getListSis_RegistrationActivityMaster(ModelMap model);

    ModelMap getEditSis_RegistrationActivityMaster(HttpServletRequest request, ModelMap model);

    List getSaveSis_RegistrationActivityMaster(HttpServletRequest request);

    List getUpdateSis_RegistrationActivityMaster(HttpServletRequest request);

    void getFeeHeadList(HttpServletRequest request, ModelMap model);

    List getDeleteSis_RegistrationActivityMaster(HttpServletRequest request);

    String checkIfChildExist(HttpServletRequest request);
}
