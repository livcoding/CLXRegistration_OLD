/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.TimeTableSlotMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.TT_SlotMaster;
import com.jilit.irp.persistence.dto.TT_SlotMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Nazar.Mohammad
 */
@Service
public class TimeTableSlotMasterService implements TimeTableSlotMasterIService{
     @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;
    
    public void getAllRegsitrationCode(ModelMap model) {
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<RegistrationMaster> list = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().getRegsitrationCode(instituteid);
            model.put("regisList", list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List registrationCode(HttpServletRequest request) {
         List<RegistrationMaster> list = null;
        try {
            
              String [] registrationid = request.getParameter("registrationid").split(",");  
              
            List retList = new ArrayList();           
             retList.add(request.getParameter("registrationid"));
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().getRegsitrationCode(instituteid,registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    @Override
    public Map getGridData(HttpServletRequest request) {
         Map map = new HashMap();
        String registrationid = request.getParameter("registrationid");  
         List retList = new ArrayList();
        List <TT_SlotMaster> list= null;
        String slotno = "";
        Map returnMap = new HashMap();
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List<TT_SlotMaster>) daoFactory.getRegistrationMasterDAO().timeSlotGridData(instituteid, registrationid);
            
            for (int i = 0; i < list.size(); i++) {  
                Map m = (Map)list.get(i);
                if (i == 0) {
                    slotno = m.get("slotno").toString();
                    map = new HashMap();
                    map.put(m.get("days").toString().toUpperCase(), m.get("starttime") + "-" + m.get("endtime"));
                    map.put("slotid", m.get("slotid"));
                    map.put("instituteid", m.get("instituteid"));
                    map.put("registrationid", m.get("registrationid"));                  
                   
                } else {
                    if (slotno.equals(m.get("slotno").toString())) {
                      map.put(m.get("days").toString().toUpperCase(), m.get("starttime") + "-" + m.get("endtime"));                      
                        if (i == list.size() - 1) {
                            slotno = m.get("slotno").toString();
                            map.put("slotno", slotno);
                            map.put("slotid", m.get("slotid"));
                            map.put("instituteid", m.get("instituteid"));
                            map.put("registrationid", m.get("registrationid"));                            
                            retList.add(map);
                        }
                    } else {
                        if (i != list.size() - 1) {
                            map.put("slotno", slotno);
                            retList.add(map);
                            map = new HashMap();
                            slotno = m.get("slotno").toString();
                            map.put("slotno", slotno);
                            map.put("slotid", m.get("slotid"));
                            map.put("instituteid", m.get("instituteid"));
                            map.put("registrationid", m.get("registrationid"));
                            map.put(m.get("days").toString().toUpperCase(), m.get("starttime") + "-" + m.get("endtime"));                          
                        }
                        if (i == list.size() - 1) {
                            map.put("slotno", slotno);
                            retList.add(map);
                             map = new HashMap();
                             slotno = m.get("slotno").toString();
                            map.put("slotno", slotno);
                            map.put("slotid", m.get("slotid"));
                            map.put("instituteid", m.get("instituteid"));
                            map.put("registrationid", m.get("registrationid"));
                            map.put(m.get("days").toString().toUpperCase(), m.get("starttime") + "-" + m.get("endtime"));                          
                            retList.add(map);
                        }
                    }
                }
                
            }
         returnMap.put("timeSloatList", retList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnMap;
    }
    
    @Override
    public String checkIfChildExist(HttpServletRequest request) {
        String uid[] = request.getParameter("pk").split("~@~");
         TT_SlotMasterId id = new TT_SlotMasterId();
         id.setInstituteid(uid[0]);
         id.setRegistrationid(uid[1]);        
         id.setSlotid(uid[2]);
         String status="";
         List<Object[]> days = (List<Object[]>)daoFactory.getRegistrationMasterDAO().getDaysBySlotId(uid[2],uid[1],uid[0]);
         for (int i = 0; i < days.size(); i++) {
            id.setDays(days.get(i)[0].toString());
             status = (daoFactory.getRegistrationMasterDAO().checkIfChildExist1(id) > 0) ? "true" : "false";
        }
        
        return status;
    }
    
    @Override
    public List copyPreviousData(HttpServletRequest request) {
         List retList = new ArrayList<String>();
        TT_SlotMaster tt_SlotMaster = null;
        TT_SlotMasterId tt_SlotMasterId = null;
         try{
             String selectedTimetable = request.getParameter("selectedTimetable");
             String[] previousTimeSloat = selectedTimetable.split(",");
              for (int i = 0; i < previousTimeSloat.length; i++) {   
                   tt_SlotMaster = new TT_SlotMaster();
                   tt_SlotMasterId = new TT_SlotMasterId();
                   String instituteid = previousTimeSloat[i].split("~@~")[0].toString();
                   String registration = previousTimeSloat[i].split("~@~")[1].toString();
                   String slotid = previousTimeSloat[i].split("~@~")[2].toString();
                   String slotno = previousTimeSloat[i].split("~@~")[3].toString();
                    List<Object[]> days = (List<Object[]>)daoFactory.getRegistrationMasterDAO().getDaysBySlotId(slotid,registration,instituteid);
                     for (int j = 0; j < days.size(); j++) {
                    tt_SlotMasterId.setInstituteid(instituteid);
                    tt_SlotMasterId.setRegistrationid(registration);
                    tt_SlotMasterId.setSlotid(slotid);
                    tt_SlotMasterId.setDays(days.get(i)[0].toString());
                    tt_SlotMaster.setId(tt_SlotMasterId);
                    tt_SlotMaster.setSlotno((new Byte(slotno)));
                    tt_SlotMaster.setSlotcode(days.get(i)[1].toString());
                    tt_SlotMaster.setStarttime(days.get(i)[2].toString());
                    tt_SlotMaster.setEndtime(days.get(i)[3].toString());                    
                    tt_SlotMaster.setDeactive("N");
                    tt_SlotMaster.setEntryby(jirpession.getJsessionInfo().getMemberid());
                    tt_SlotMaster.setEntrydate(new Date());
                    //daoFactory.getTt_SlotMasterDAO().saveOrUpdate(tt_SlotMaster); 
                     }
              }
         }catch(Exception e){
             
         }
         return retList;
    }
    
//    public List deleteTT_TimeTableSlotMasterData(HttpServletRequest request) {
//        List err = null;
//        int i;
//        String obj[] = request.getParameter("pk").split(",");
//        try {
//            for (i = 0; i < obj.length; i++) {
//               String id[] = request.getParameter("pk").split("~@~");                
//                    TT_SlotMaster dto = (TT_SlotMaster) daoFactory.getTt_SlotMasterDAO().findByPrimaryKey(new TT_SlotMasterId(id[0], id[1], id[2], id[3]));
//                    if(dto!=null)
//                    daoFactory.getTt_SlotMasterDAO().delete(dto);
//
//               
//            }
//            err = new ArrayList<String>();
//            err.add("Success");
//            err.add(String.valueOf(i) + " record(s) deleted.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            err.add(e.getMessage());
//        }
//        return err;
//
//    }
    
}
