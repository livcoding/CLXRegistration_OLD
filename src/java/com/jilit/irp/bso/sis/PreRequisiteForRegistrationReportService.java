package com.jilit.irp.bso.sis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.jilit.irp.iservice.PreRequisiteForRegistrationReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *
 * @author ankur.goyal
 */
@Service
public class PreRequisiteForRegistrationReportService implements PreRequisiteForRegistrationReportIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    /**
     * @description This method is used for get Combo of Academic Year or STY
     * Number.
     * @param model
     */
    @Override
    public void getCombo(ModelMap model) {
        List acadList = null;
        List progList = null;
        List styList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearReg(instituteid);
            progList = (List<Object[]>) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            styList = (List<StyDesc>) daoFactory.getUtilDAO().findSimpleData("getAllStyDesc", new String[]{instituteid});
            model.addAttribute("acadList", acadList);
            model.addAttribute("progList", progList);
            model.addAttribute("styList", styList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for get Branch Code combo from
     * BranchMaster table.
     * @param request
     * @return branchList
     */
    @Override
    public List getBranchCode(HttpServletRequest request) {
        List branchList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String prog[] = request.getParameter("programCode").split(",");
            String programid = "";
            for (int i = 0; i < prog.length; i++) {
                if (i == 0) {
                    programid = programid + "'" + prog[i] + "'";
                } else {
                    programid = programid + "," + "'" + prog[i] + "'";
                }
            }
            branchList = (List<Object[]>) daoFactory.getBranchMasterDAO().getBranchCodeWithProList(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }

    /**
     * @description This method is used for get Grid data from
     * PreRequisiteForRegistration table.
     * @param request
     * @return list
     */
    @Override
    public List getAllPreRequisiteForRegistrationReportData(HttpServletRequest request) {
        List list = new ArrayList();
        List progList = new ArrayList();
        List branchList = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String academic = request.getParameter("academicYear");
        String stynumber = request.getParameter("styNumber");
        String program[] = request.getParameter("programCode").split(",");
        String branch[] = request.getParameter("branchCode").split(",");
        progList = Arrays.asList(program);
        branchList = Arrays.asList(branch);
        list = daoFactory.getPreRequisitForRegistrationDAO().getPreRequisiteForRegistrationReportData(instituteid, academic, progList, branchList, stynumber);
        return list;
    }
}
