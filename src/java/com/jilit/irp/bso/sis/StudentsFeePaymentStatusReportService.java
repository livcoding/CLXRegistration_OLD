/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.StudentsFeePaymentStatusReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class StudentsFeePaymentStatusReportService implements StudentsFeePaymentStatusReportIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public List getSemesterCode(HttpServletRequest request) {
        String[] ids = request.getParameter("instituteids").split(",");
        List instid = Arrays.asList(ids);
        String reg_Type = request.getParameter("reg_Type");
        List<Object[]> list = null;
        List<Object[]> rtnlist = new ArrayList<Object[]>();
        try {
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegCodeForStudentsFeePaymentStatus(instid, reg_Type);
            if (list.size() > 0) {
                Map map = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    Object[] mainobj = new Object[5];
                    Object[] obj = list.get(i);
                    String registrationcode = obj[3].toString();
                    String key = obj[3].toString();
                    if (map.containsKey(key)) {
                        for (int j = 0; j < rtnlist.size(); j++) {
                            Object[] obj1 = rtnlist.get(j);
                            if (obj1[3].toString().equals(registrationcode)) {
                                mainobj[0] = obj[0].toString() + "," + obj1[0].toString(); // instituteid
                                mainobj[1] = obj[1].toString() + "/" + obj1[1].toString(); // institutecode
                                mainobj[2] = obj[2].toString() + "," + obj1[2].toString(); // registrationid
                                mainobj[3] = obj[3].toString();                            // registrationcode
                                mainobj[4] = obj[4].toString() + "/" + obj1[4].toString(); // registrationdesc vcb
                                rtnlist.remove(j);
                                break;
                            }
                        }
                        rtnlist.add(mainobj);
                    } else {
                        map.put(registrationcode, registrationcode);
                        rtnlist.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    @Override
    public List getGridData(HttpServletRequest request) {
        String instituteids[] = request.getParameter("institutelist").split(",");
        List instituteid=Arrays.asList(instituteids);
        String registrationids[] = request.getParameter("registrationid").split(",");
        List registrationid=Arrays.asList(registrationids);
        String reg_Type = request.getParameter("reg_Type");
        String Order_By = request.getParameter("Order_By");
        String duestudent = request.getParameter("duestudent");
        String lesssubject = request.getParameter("lesssubject");
        String exceedsubject = request.getParameter("exceedsubject");
        List feeheadCode = new ArrayList();
        if (reg_Type.equals("S")) {
            feeheadCode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.6");
        } else if (reg_Type.equals("P")) {
            feeheadCode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG2.4");
        } else {
            feeheadCode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG5.6");
        }
        List feeheadid = daoFactory.getRegistrationParametersDAO().getFeeheadids(instituteid, feeheadCode);
        List list1 = null;
        List list2 = null;
        List list3 = null;
        List returnlist = new ArrayList();
        try {
            list1 = (List) daoFactory.getStudentMasterDAO().getStudentFeePaymentData(instituteid, registrationid, reg_Type, Order_By, duestudent, lesssubject, exceedsubject, feeheadid);
            list2 = (List) daoFactory.getStudentMasterDAO().getStudentSubCode(instituteid, registrationid);
            list3 = (List) daoFactory.getStudentMasterDAO().getStudentRegisteredSubjects(instituteid, registrationid);
            returnlist.add(list1);
            returnlist.add(list2);
            returnlist.add(list3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnlist;
    }
}
