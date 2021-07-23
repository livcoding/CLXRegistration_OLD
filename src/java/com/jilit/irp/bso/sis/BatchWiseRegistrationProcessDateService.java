package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.BatchWiseRegistrationProcessDateIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.RegistrationMasterDetail;
import com.jilit.irp.persistence.dto.RegistrationMasterDetailId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class BatchWiseRegistrationProcessDateService implements BatchWiseRegistrationProcessDateIservice {

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
    public List getProgram(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getProgramMasterDAO().getProgramForAcadWiseRegistration(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBranch(HttpServletRequest request) {
        List list = new ArrayList();
        List academicyear = new ArrayList();
        List programid = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String[] acadids = request.getParameter("acadYear").split(",");
        String[] prids = request.getParameter("program").split(",");
        academicyear = Arrays.asList(acadids);
        programid = Arrays.asList(prids);
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getBranchMasterDAO().getBranchForAcadWiseRegistration(instituteid, registrationid, academicyear, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getGridData(HttpServletRequest request) {
        List list = new ArrayList();
        String academicyear = "";
        String programid = "";
        String basedon = request.getParameter("basedon");
        String registrationid = request.getParameter("regCode");
        String[] acadids = request.getParameter("acadYear").split(",");
        String[] program = request.getParameter("program").split(",");
        List academicyearlist = Arrays.asList(acadids);
        List programlist = Arrays.asList(program);
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            if (basedon.equals("G")) {
                list = daoFactory.getdBDependentDAO().getGridDataRegistration_infoBased(instituteid, registrationid, academicyearlist, programlist);
            } else {
                list = daoFactory.getdBDependentDAO().getGridDataPSTBased(instituteid, registrationid, academicyearlist, programlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List saveAcadWiseRegistration(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List list = new ArrayList();
        List retrnlist = null;
        RegistrationMasterDetail dto = null;
        RegistrationMasterDetailId id = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        int count = Integer.parseInt(request.getParameter("countval"));
        BigDecimal lateRegistrationFee = BigDecimal.ZERO;
        try {
            for (int i = 0; i < count; i++) {
                id = new RegistrationMasterDetailId();
                dto = new RegistrationMasterDetail();
                String[] ids = request.getParameter("chk" + i).split("~@~");
                String remarks = request.getParameter("remarks" + i);
                if (!request.getParameter("reglatefee" + i).equals("")) {
                    String reglatefee = request.getParameter("reglatefee" + i);
                    lateRegistrationFee = new BigDecimal(reglatefee);
                } else {
                    lateRegistrationFee = null;
                }
                id.setInstituteid(instituteid);
                id.setRegistrationid(ids[2]);
                id.setProgramid(ids[0]);
                id.setBranchid(ids[1]);
                id.setAcademicyear(ids[3]);
                dto.setId(id);
                dto.setRemarks(remarks);
                dto.setLateregfeeamount(lateRegistrationFee);
                if (!request.getParameter("fromdate" + i).equals("") && request.getParameter("fromdate" + i) != null) {
                    dto.setRegdatefrom(df.parse(request.getParameter("fromdate" + i)));
                }
                if (!request.getParameter("todate" + i).equals("") && request.getParameter("todate" + i) != null) {
                    dto.setRegdateto(df.parse(request.getParameter("todate" + i)));
                }
                if (!request.getParameter("tilldate" + i).equals("") && request.getParameter("tilldate" + i) != null) {
                    dto.setExtendedtilldate(df.parse(request.getParameter("tilldate" + i)));
                }
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
