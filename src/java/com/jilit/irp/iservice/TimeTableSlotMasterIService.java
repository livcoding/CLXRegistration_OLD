/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.iservice;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Nazar.Mohammad
 */
public interface TimeTableSlotMasterIService {
    public void getAllRegsitrationCode(ModelMap model);
     public Map getGridData(HttpServletRequest request);
     public String checkIfChildExist(HttpServletRequest request);
      public List registrationCode(HttpServletRequest request);
      public List copyPreviousData(HttpServletRequest request);
    
}
