package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface Sis_RegistrationActivityRightsIService {

    public void getListSis_RegistrationActivityRights(ModelMap model);

    public void getAllActivityCombo(ModelMap model);

    public void getEditis_RegistrationActivityRights(HttpServletRequest request, ModelMap model);

    public List getSaveSis_RegistrationActivityRights(HttpServletRequest request);

    public List getUpdateSis_RegistrationActivityRights(HttpServletRequest request);

    public List getDeleteSis_RegistrationActivityRights(HttpServletRequest request);
}
