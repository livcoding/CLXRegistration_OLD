package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.ForcefullyRegistrationIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
@Service
public class ForcefullyRegistrationService implements ForcefullyRegistrationIService {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getProgramCode(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("prog_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getBranchCode(HttpServletRequest req, Model model) {
        String progcode = req.getParameter("prog_code");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getBranchMasterDAO().getBranchCode(instituteid, progcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public List getRegistrationNumber(HttpServletRequest req, Model model) {
        String progcode = req.getParameter("prog_code");
        String br_id = req.getParameter("br_code");
        String sty_code = req.getParameter("sty_code");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = null;
        try {
            list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationNumber(instituteid, progcode, br_id, sty_code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public List updateForcefullyRegistration(HttpServletRequest req) {
        StudentRegistration master = new StudentRegistration();
        StudentRegistrationId id = new StudentRegistrationId();
        List err = new ArrayList<String>();
        String ins_id = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            String reg_num[] = req.getParameter("reg_num").split("~@~");
            String stud_id = reg_num[0];
            String reg_id = reg_num[9];
            String allow_for_sub_choice = req.getParameter("allow_for_sub_choice");
            String fees_paid = req.getParameter("fees_paid");
            String hostel_allow = req.getParameter("hostel_allow");
            String pr_event_freezed = req.getParameter("pr_event_freezed");
            String document_verified = req.getParameter("document_verified");
            String transport_allow = req.getParameter("transport_allow");
            String allow_pay_fee = req.getParameter("allow_pay_fee");
            id.setInstituteid(ins_id);
            id.setRegistrationid(reg_id);
            id.setStudentid(stud_id);
            master = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(id);
            if (master != null) {
                if (allow_for_sub_choice != null && !allow_for_sub_choice.equals("")) {
                    allow_for_sub_choice = "Y";
                } else {
                    allow_for_sub_choice = "N";
                }
                master.setAllowforsubjectchoice(allow_for_sub_choice);
                if (fees_paid != null && !fees_paid.equals("")) {
                    fees_paid = "Y";
                } else {
                    fees_paid = "N";
                }
                master.setFeespaid(fees_paid);
                if (hostel_allow != null && !hostel_allow.equals("")) {
                    hostel_allow = "Y";
                } else {
                    hostel_allow = "N";
                }
                master.setHostelallow(hostel_allow);
                if (pr_event_freezed != null && !pr_event_freezed.equals("")) {
                    pr_event_freezed = "Y";
                } else {
                    pr_event_freezed = "N";
                }
                master.setPreventfreezed(pr_event_freezed);
                if (document_verified != null && !document_verified.equals("")) {
                    document_verified = "Y";
                } else {
                    document_verified = "N";
                }
                master.setDocverification(document_verified);
                if (transport_allow != null && !transport_allow.equals("")) {
                    transport_allow = "Y";
                } else {
                    transport_allow = "N";
                }
                master.setTransportallow(transport_allow);
                if (allow_pay_fee != null && !allow_pay_fee.equals("")) {
                    allow_pay_fee = "Y";
                } else {
                    allow_pay_fee = "N";
                }
                master.setAllowforfeepay(allow_pay_fee);
                daoFactory.getStudentRegistrationDAO().update(master);
            }
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
        }
        return err;
    }

}
