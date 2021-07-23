/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;
import com.jilit.irp.iservice.InstituteRegistrationEventsIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteRegistrationEvents;
import com.jilit.irp.persistence.dto.InstituteRegistrationEventsId;
import com.jilit.irp.util.JIRPSession;
import java.util.*;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class InstituteRegistrationEventsService implements InstituteRegistrationEventsIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getInstituteRegistrationEventsList(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getInstituteRegistrationEventsDAO().getInstituteRegistrationEventGridData(instituteid);
        model.addAttribute("data", list);
    }

    @Override
    public void getSemesterCode(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationMaster(instituteid);
        model.addAttribute("data", list);
    }

    public List addInstituteRegistrationEvents(HttpServletRequest request) {

        InstituteRegistrationEvents instituteRegistrationEvents = new InstituteRegistrationEvents();
        List err = new ArrayList<String>();
        String registrationid = null;
        try {
            if (request.getParameter("attendentryAllowed") != null) {
                instituteRegistrationEvents.setAttendentryallowed(request.getParameter("attendentryAllowed"));
            } else {
                instituteRegistrationEvents.setAttendentryallowed("N");
            }
            if (request.getParameter("gradeentryAllowed") != null) {
                instituteRegistrationEvents.setGradeentryallowed(request.getParameter("gradeentryAllowed"));
            } else {
                instituteRegistrationEvents.setGradeentryallowed("N");
            }
            if (request.getParameter("marksentryAllowed") != null) {
                instituteRegistrationEvents.setMarksentryallowed(request.getParameter("marksentryAllowed"));
            } else {
                instituteRegistrationEvents.setMarksentryallowed("N");
            }
            if (request.getParameter("preregistrationAllowed") != null) {
                instituteRegistrationEvents.setPreregistrationallowed(request.getParameter("preregistrationAllowed"));
            } else {
                instituteRegistrationEvents.setPreregistrationallowed("N");
            }
            if (request.getParameter("srsAllowed") != null) {
                instituteRegistrationEvents.setSrsallowed(request.getParameter("srsAllowed"));
            } else {
                instituteRegistrationEvents.setSrsallowed("N");
            }
            if (request.getParameter("supplymentryrequestAllowed") != null) {
                instituteRegistrationEvents.setSupplymentryrequestallowed(request.getParameter("supplymentryrequestAllowed"));
            } else {
                instituteRegistrationEvents.setSupplymentryrequestallowed("N");
            }
            if (request.getParameter("hostelallocationallowed") != null) {
                instituteRegistrationEvents.setHostelallocationallowed(request.getParameter("hostelallocationallowed"));
            } else {
                instituteRegistrationEvents.setHostelallocationallowed("N");
            }
            InstituteRegistrationEventsId id = new InstituteRegistrationEventsId();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            registrationid = request.getParameter("semesterCode");
            id.setRegistrationid(registrationid.split("~@~")[0].toString());
            instituteRegistrationEvents.setId(id);
            err = daoFactory.getInstituteRegistrationEventsDAO().doValidate(instituteRegistrationEvents, "save");
            if (!err.isEmpty()) {
                return err;
            } else {
                daoFactory.getInstituteRegistrationEventsDAO().add(instituteRegistrationEvents);
                err = new ArrayList<String>();
                err.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
    }

    @Override
    public void editInstituteRegistrationEvents(Model mm, HttpServletRequest request) {
        InstituteRegistrationEvents dto = null;
        InstituteRegistrationEventsId id = null;
        try {
            dto = new InstituteRegistrationEvents();
            id = new InstituteRegistrationEventsId();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            List list = (List) daoFactory.getInstituteRegistrationEventsDAO().getInstituteRegistrationEventEditData(ids[0], ids[1]);
            mm.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List updateInstituteRegistrationEvents(HttpServletRequest request) {
        InstituteRegistrationEvents instituteRegistrationEvents = new InstituteRegistrationEvents();
        List err = new ArrayList<String>();
        String registrationid = null;
        try {
            if (request.getParameter("attendentryAllowed") != null) {
                instituteRegistrationEvents.setAttendentryallowed(request.getParameter("attendentryAllowed"));
            } else {
                instituteRegistrationEvents.setAttendentryallowed("N");
            }
            if (request.getParameter("gradeentryAllowed") != null) {
                instituteRegistrationEvents.setGradeentryallowed(request.getParameter("gradeentryAllowed"));
            } else {
                instituteRegistrationEvents.setGradeentryallowed("N");
            }
            if (request.getParameter("marksentryAllowed") != null) {
                instituteRegistrationEvents.setMarksentryallowed(request.getParameter("marksentryAllowed"));
            } else {
                instituteRegistrationEvents.setMarksentryallowed("N");
            }
            if (request.getParameter("preregistrationAllowed") != null) {
                instituteRegistrationEvents.setPreregistrationallowed(request.getParameter("preregistrationAllowed"));
            } else {
                instituteRegistrationEvents.setPreregistrationallowed("N");
            }
            if (request.getParameter("srsAllowed") != null) {
                instituteRegistrationEvents.setSrsallowed(request.getParameter("srsAllowed"));
            } else {
                instituteRegistrationEvents.setSrsallowed("N");
            }
            if (request.getParameter("supplymentryrequestAllowed") != null) {
                instituteRegistrationEvents.setSupplymentryrequestallowed(request.getParameter("supplymentryrequestAllowed"));
            } else {
                instituteRegistrationEvents.setSupplymentryrequestallowed("N");
            }
            if (request.getParameter("hostelallocationallowed") != null) {
                instituteRegistrationEvents.setHostelallocationallowed(request.getParameter("hostelallocationallowed"));
            } else {
                instituteRegistrationEvents.setHostelallocationallowed("N");
            }
            InstituteRegistrationEventsId id = new InstituteRegistrationEventsId();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            registrationid = request.getParameter("registrationid");
            id.setRegistrationid(registrationid);
            instituteRegistrationEvents.setId(id);
            if (!err.isEmpty()) {
                return err;
            } else {
                daoFactory.getInstituteRegistrationEventsDAO().update(instituteRegistrationEvents);
                err = new ArrayList<String>();
                err.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
    }

    @Override
    public List deleteInstituteRegistrationEvents(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String pks1[] = pks[i].toString().split(":");
                daoFactory.getInstituteRegistrationEventsDAO().delete(daoFactory.getInstituteRegistrationEventsDAO().findByPrimaryKey(new InstituteRegistrationEventsId(pks1[0].toString(), pks1[1].toString())));
            }
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

}
