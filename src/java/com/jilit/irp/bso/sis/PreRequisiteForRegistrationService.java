package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.PreRequisiteForRegistrationIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.PreRequisitForRegistration;
import com.jilit.irp.persistence.dto.PreRequisitForRegistrationId;
import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Service
public class PreRequisiteForRegistrationService implements PreRequisiteForRegistrationIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    /**
     * @description This method is used for the get Grid data from
     * PREREQUISITFORREGISTRATION table.
     * @param request
     * @return list
     */
    public List getAllPreRequisiteForRegistration(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String academicYear = request.getParameter("acadYear");
            list = daoFactory.getPreRequisitForRegistrationDAO().getAllListPreRequisitForRegistration(instituteid, academicYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @description This method is used for the get Branch combo from
     * BranchMaster table.
     * @param request
     * @return branchList
     */
    public List getAllBranchCode(HttpServletRequest request) {
        List branchList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = request.getParameter("programCode");
            branchList = (List<Object[]>) daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }

    /**
     * @description This method is used for the get STY Code from STYTYPE table.
     * @param model
     */
    public void getAllStyCode(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List<StyDesc>) daoFactory.getUtilDAO().findSimpleData("getAllStyDesc", new String[]{instituteid});
            model.put("styList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for the edit record from
     * PREREQUISITFORREGISTRATION table.
     * @param request
     * @param model
     * @return model
     */
    public ModelMap getEditPreRequisiteForRegistration(HttpServletRequest request, ModelMap model) {
        List list = null;
        try {
            String pk = request.getParameter("pk");
            String ids[] = pk.split("~@~");
            String academicyear = ids[0];
            String stynumber = ids[1];
            String instituteid = ids[2];
            String programid = ids[3];
            String branchid = ids[4];
            list = (List<Object[]>) daoFactory.getPreRequisitForRegistrationDAO().getEditPreRequisitForRegistration(instituteid, academicyear, programid, branchid, stynumber);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * @description This method is used for the save data into
     * PREREQUISITFORREGISTRATION table.
     * @param request
     * @return Success or Error message
     */
    public List getSavePreRequisiteForRegistration(HttpServletRequest request) {
        List list = null;
        PreRequisitForRegistration dto = null;
        PreRequisitForRegistrationId id = null;
        try {
            dto = new PreRequisitForRegistration();
            id = new PreRequisitForRegistrationId();
            list = new ArrayList<String>();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setAcademicyear(request.getParameter("academicYear"));
            id.setProgramid(request.getParameter("programCode"));
            id.setBranchid(request.getParameter("branchCode"));
            id.setStynumber(Byte.parseByte(request.getParameter("styNumber")));
            dto.setId(id);
            if (!"".equals(request.getParameter("maxFailSubject"))) {
                dto.setMaxfailsubject(Long.parseLong(request.getParameter("maxFailSubject")));
            } else {
                dto.setMaxfailsubject(null);
            }
            if (!"".equals(request.getParameter("minEarnedPoints"))) {
                dto.setMinearnedpoints(new BigDecimal(request.getParameter("minEarnedPoints")));
            } else {
                dto.setMinearnedpoints(null);
            }
            if (!"".equals(request.getParameter("maxNoAttempts"))) {
                dto.setMaxnoattempts(Long.parseLong(request.getParameter("maxNoAttempts")));
            } else {
                dto.setMaxnoattempts(null);
            }
            if (!"".equals(request.getParameter("minCredit"))) {
                dto.setMincredits(new BigDecimal(request.getParameter("minCredit")));
            } else {
                dto.setMincredits(null);
            }
            if (!"".equals(request.getParameter("lateralEntryCredit"))) {
                dto.setLateralentrymincredit(new BigDecimal(request.getParameter("lateralEntryCredit")));
            } else {
                dto.setLateralentrymincredit(null);
            }
            if (!"".equals(request.getParameter("minSGPA"))) {
                dto.setMinsgpa(new BigDecimal(request.getParameter("minSGPA")));
            } else {
                dto.setMinsgpa(null);
            }
            if (!"".equals(request.getParameter("minCGPA"))) {
                dto.setMincgpa(new BigDecimal(request.getParameter("minCGPA")));
            } else {
                dto.setMincgpa(null);
            }
            if (!"".equals(request.getParameter("minPercentAttendance"))) {
                dto.setMinattendancepercent(new BigDecimal(request.getParameter("minPercentAttendance")));
            } else {
                dto.setMinattendancepercent(null);
            }
            if (!"".equals(request.getParameter("maxDiscplinary"))) {
                dto.setMaxdeciplinary(new BigDecimal(request.getParameter("maxDiscplinary")));
            } else {
                dto.setMaxdeciplinary(null);
            }
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = daoFactory.getPreRequisitForRegistrationDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getPreRequisitForRegistrationDAO().saveOrUpdate(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This method is used for the update grid record from
     * PREREQUISITFORREGISTRATION table.
     * @param request
     * @return Success or Error message.
     */
    public List getUpdatePreRequisiteForRegistration(HttpServletRequest request) {
        List list = null;
        PreRequisitForRegistration dto = null;
        PreRequisitForRegistrationId id = null;
        try {
            dto = new PreRequisitForRegistration();
            id = new PreRequisitForRegistrationId();
            list = new ArrayList<String>();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setAcademicyear(request.getParameter("acadYear"));
            id.setProgramid(request.getParameter("proogramId"));
            id.setBranchid(request.getParameter("branchId"));
            id.setStynumber(Byte.parseByte(request.getParameter("styNumber")));
            dto.setId(id);
            if (!"".equals(request.getParameter("maxFailSubject"))) {
                dto.setMaxfailsubject(Long.parseLong(request.getParameter("maxFailSubject")));
            } else {
                dto.setMaxfailsubject(null);
            }
            if (!"".equals(request.getParameter("minEarnedPoints"))) {
                dto.setMinearnedpoints(new BigDecimal(request.getParameter("minEarnedPoints")));
            } else {
                dto.setMinearnedpoints(null);
            }
            if (!"".equals(request.getParameter("maxNoAttempts"))) {
                dto.setMaxnoattempts(Long.parseLong(request.getParameter("maxNoAttempts")));
            } else {
                dto.setMaxnoattempts(null);
            }
            if (!"".equals(request.getParameter("minCredit"))) {
                dto.setMincredits(new BigDecimal(request.getParameter("minCredit")));
            } else {
                dto.setMincredits(null);
            }
            if (!"".equals(request.getParameter("lateralEntryCredit"))) {
                dto.setLateralentrymincredit(new BigDecimal(request.getParameter("lateralEntryCredit")));
            } else {
                dto.setLateralentrymincredit(null);
            }
            if (!"".equals(request.getParameter("minSGPA"))) {
                dto.setMinsgpa(new BigDecimal(request.getParameter("minSGPA")));
            } else {
                dto.setMinsgpa(null);
            }
            if (!"".equals(request.getParameter("minCGPA"))) {
                dto.setMincgpa(new BigDecimal(request.getParameter("minCGPA")));
            } else {
                dto.setMincgpa(null);
            }
            if (!"".equals(request.getParameter("minPercentAttendance"))) {
                dto.setMinattendancepercent(new BigDecimal(request.getParameter("minPercentAttendance")));
            } else {
                dto.setMinattendancepercent(null);
            }
            if (!"".equals(request.getParameter("maxDiscplinary"))) {
                dto.setMaxdeciplinary(new BigDecimal(request.getParameter("maxDiscplinary")));
            } else {
                dto.setMaxdeciplinary(null);
            }
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = daoFactory.getPreRequisitForRegistrationDAO().doValidate(dto, "edit");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getPreRequisitForRegistrationDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This method is used for the check child exist data in table
     * PREREQUISITFORREGISTRATION.
     * @param request
     * @return status
     */
    public String checkIfChildExist(HttpServletRequest request) {
        PreRequisitForRegistrationId id = new PreRequisitForRegistrationId();
        String pk = request.getParameter("pk");
        String ids[] = pk.split("~@~");
        id.setInstituteid(ids[2]);
        id.setAcademicyear(ids[0]);
        id.setProgramid(ids[3]);
        id.setBranchid(ids[4]);
        id.setStynumber(Byte.parseByte(ids[1]));
        String status = (daoFactory.getPreRequisitForRegistrationDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        return status;
    }

    /**
     * @description This method is used for the delete record from
     * PREREQUISITFORREGISTRATION table.
     * @param request
     * @return Success or Error message.
     */
    public List getDeletePreRequisiteForRegistration(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String pks[] = request.getParameter("ids").toString().split(",");
            for (i = 0; i < pks.length; i++) {
                String[] ids = pks[i].split("~@~");
                String instituteid = ids[2];
                String acadYear = ids[0];
                String program = ids[3];
                String branch = ids[4];
                Byte styNo = Byte.parseByte(ids[1]);
                daoFactory.getPreRequisitForRegistrationDAO().delete(daoFactory.getPreRequisitForRegistrationDAO().findByPrimaryKey(new PreRequisitForRegistrationId(instituteid, acadYear, program, branch, styNo)));
                list = new ArrayList<String>();
                list.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }
}
