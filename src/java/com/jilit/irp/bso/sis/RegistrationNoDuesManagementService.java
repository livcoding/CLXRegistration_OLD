/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationNoDuesManagementIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Sis_StudentRegActivities;
import com.jilit.irp.persistence.dto.Sis_StudentRegActivitiesId;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class RegistrationNoDuesManagementService implements RegistrationNoDuesManagementIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.addAttribute("data", list);
            List<Object[]> activity = (List<Object[]>) daoFactory.getStudentRegistrationDAO().getActivityValues(instituteid);
            model.addAttribute("activity", activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getAcademicYear(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getAcademicYearDAO().getAcademicYearCheckPST(registrationid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllProgramCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = request.getParameter("regCode");
            String acadYear = request.getParameter("acadYear");
            list = (List<Object[]>) daoFactory.getProgramMasterDAO().getPST_Programcodes(instituteid, registrationid, acadYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getEnrollmentNo(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String programid = request.getParameter("programid");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getStudentRegistrationDAO().getEnrolmentNo(instituteid, registrationid, acadYear,programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStudentWiseNoDuesGridData(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String programid = request.getParameter("programid");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getStudentRegistrationDAO().getStudentWiseNoDuesGridData(instituteid, registrationid, acadYear,programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List saveStudentWise(HttpServletRequest request) {
        List list = new ArrayList();
        List retrnlist = null;
        Sis_StudentRegActivities dto = null;
        Sis_StudentRegActivitiesId id = null;
        String[] stuids = request.getParameter("regNo").split("~@~");
        String[] activityType = request.getParameterValues("activityType");
        String studentid = stuids[0];
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String remarks = request.getParameter("remarks");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String activity = "";
        String stuname = "";
        try {
            for (int i = 0; i < activityType.length; i++) {
                id = new Sis_StudentRegActivitiesId();
                dto = new Sis_StudentRegActivities();
                String activityid[] = activityType[i].split("~@~");
                list = daoFactory.getStudentRegistrationDAO().checkDuplicateEntry(instituteid, registrationid, studentid, activityid[0]);
                if (list != null && list.size() > 0) {
                    activity = activityid[1];
                    stuname = stuids[1];
                    continue;
                } else {
                    id.setInstituteid(instituteid);
                    id.setRegistrationid(registrationid);
                    id.setActivityid(activityid[0]);
                    id.setStudentid(studentid);
                    dto.setId(id);
                    dto.setRemarks(remarks);
                    dto.setProcessed("N");
                    dto.setAllowforregistration("N");
                    daoFactory.getStudentRegistrationDAO().saveStudentWiseNoDues(dto);
                    retrnlist = new ArrayList();
                    retrnlist.add("Success");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            retrnlist = new ArrayList();
            retrnlist.add("Error");
        }
        if (retrnlist == null) {
            retrnlist = new ArrayList();
            retrnlist.add(activity + " is already applied for Student (" + stuname + ")");
        }
        return retrnlist;
    }

    @Override
    public List deleteNoDuesActivity(HttpServletRequest request) {
        Sis_StudentRegActivitiesId id = null;
        List retList = new ArrayList();
        Sis_StudentRegActivities master = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String reg_act_stu = request.getParameter("ids");
        String[] arr = reg_act_stu.split(",");
        for (int i = 0; i < arr.length; i++) {
            String[] new_arr = arr[i].split("~@~");
            String registrationid = new_arr[0];
            String studentid = new_arr[1];
            String activityid = new_arr[2];
            id = new Sis_StudentRegActivitiesId();
            master = new Sis_StudentRegActivities();
            id.setInstituteid(instituteid);
            id.setRegistrationid(registrationid);
            id.setActivityid(activityid);
            id.setStudentid(studentid);
            master = (Sis_StudentRegActivities) daoFactory.getStudentRegistrationDAO().findByPrimaryKey2(id);
            if (master != null) {
                try {
                    daoFactory.getStudentRegistrationDAO().delete(master);
                    retList.add("Success");
                } catch (Exception e) {
                    e.printStackTrace();
                    retList.add("Error");
                }
            }

        }
        return retList;
    }

    @Override
    public List saveAcadWiseRecord(HttpServletRequest request) {
        List list = new ArrayList();
        List retrnlist = null;
        Sis_StudentRegActivities dto = null;
        Sis_StudentRegActivitiesId id = null;
        String activityid = request.getParameter("activityTypeAcd");
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        int count = Integer.parseInt(request.getParameter("countvalac"));
        try {
            for (int i = 0; i < count; i++) {
                id = new Sis_StudentRegActivitiesId();
                dto = new Sis_StudentRegActivities();
                String studentid = request.getParameter("chk2" + i);
                String remarks = request.getParameter("bulkremarks" + i);
                String allowreg = request.getParameter("regstatus" + i);
                id.setInstituteid(instituteid);
                id.setRegistrationid(registrationid);
                id.setActivityid(activityid);
                id.setStudentid(studentid);
                dto.setId(id);
                dto.setRemarks(remarks);
                dto.setProcessed("N");
                dto.setAllowforregistration(allowreg);
                list.add(dto);
            }
            daoFactory.getStudentRegistrationDAO().saveAcadWiseNoDues(list);
            retrnlist = new ArrayList();
            retrnlist.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            retrnlist = new ArrayList();
            retrnlist.add("Error");
        }
        return retrnlist;
    }
}
