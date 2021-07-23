/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Login;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 *
 * @author deepak.gupta
 */
public interface LoginIservice {

    public List<Object> checkUserid_Password(String username, String password, String usertype, String instituteid, HttpServletRequest request);

    public void getRightsIdUserWise(String Sct_UserRolesCriteraBased, String qry_Urts, Model model, HttpServletRequest request);

    public void getInstCompData(Model model);

}
