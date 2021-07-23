/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Login;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.DAOFactory;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author deepak.gupta
 */
@Controller
@RequestMapping("/")
public class LoginController implements ServletContextAware {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    LoginIservice loginIservice;
    ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @RequestMapping("/authTokenValidate")
    @ResponseBody
    public String authTokenValidate(ModelMap model, HttpServletRequest request) {
        BusinessService bservice = new BusinessService(context, daoFactory);
        String tokenName = request.getParameter("tokenName");
        String tokenValue = request.getParameter("tokenValue");
        String userType = "DirectLogin";
        String oauth = bservice.getPropertyValue("oauth_logincontroller_validator", "campuslynx.properties").trim();
        List list = loginIservice.checkUserid_Password(tokenName, tokenValue, userType, oauth, request);
        return list.get(0).toString() + "~@~" + list.get(1).toString();
    }

    @RequestMapping("/loadDashboard")
    public String getPersonByIdAndName(Model model, HttpServletRequest request) {
        String tokenValue = request.getParameter("tokenValue");
        loginIservice.getRightsIdUserWise("getRightsIdsFormSct_UserRolesTr", "getRightsIdsFormSct_Urts", model, request);
        return "Dashboard";
    }

    @RequestMapping("/loadPopup")
    public String loadPopup(Model model) {
        loginIservice.getInstCompData(model);
        return "instituteCompanyPopup";
    }

    @RequestMapping("/logOut")
    @ResponseBody
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return "success";
        } else {
            return "logout not yet.";
        }
    }
}
