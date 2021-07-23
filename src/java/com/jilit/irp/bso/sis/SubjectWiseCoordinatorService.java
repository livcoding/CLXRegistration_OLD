/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.SubjectWiseCoordinatorIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Pr_ElectiveSets;
import com.jilit.irp.persistence.dto.SubjectCoordinator;
import com.jilit.irp.persistence.dto.SubjectCoordinatorId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class SubjectWiseCoordinatorService implements SubjectWiseCoordinatorIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getSubjectWiseCoordinatorList(Model model) {
        List data = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            data = (List) daoFactory.getSubjectCoordinatorDAO().getGridData(instituteid);
            model.addAttribute("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllSubjectCode(ModelMap map) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getSubjectMasterDAO().getReqSubject(instituteid);
        map.put("subject_code", list);
    }

    @Override
    public List getCoordinator(HttpServletRequest request) {
        String status = request.getParameter("status");
        String subcode = request.getParameter("sub_code");
        String[] ids = subcode.split("~@~");
        String subid = "";
        String depid = "";
        if (ids.length > 1) {
            subid = ids[0];
            depid = ids[1];
        } else {
            subid = ids[0];
            depid = " ";
        }
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getSubjectCoordinatorDAO().getCoordinator(instituteid, depid, status);
        return list;
    }

    @Override
    public List saveSubjectCoordinator(HttpServletRequest request) {
        SubjectCoordinator dto = null;
        SubjectCoordinatorId id = new SubjectCoordinatorId();
        List list = new ArrayList();
        try {
            String subjectid = request.getParameter("sub_code");
            String subid = subjectid.split("~@~")[0];
            String coord_code = request.getParameter("coord_code");
            String cid = coord_code.split("~@~")[0];
            String ctype = coord_code.split("~@~")[1];
            String remarks = request.getParameter("remarks");
            String chiefcoord = request.getParameter("chiefCoor");
            String deactive = request.getParameter("deactive");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(instituteid);
            id.setSubjectid(subid);
            id.setStaffid(cid);
            id.setStafftype(ctype);
            id.setBreak_slno(BigDecimal.valueOf(1));
            dto = (SubjectCoordinator) daoFactory.getSubjectCoordinatorDAO().findByPrimaryKey(id);
            if (dto != null) {
                list.add("This Coordinator is already tagged with this subject....");
                return list;
            }
            dto = new SubjectCoordinator();
            dto.setId(id);
            dto.setDeactive(deactive);
            dto.setRemarks(remarks);
            if (request.getParameter("chiefCoor") == null) {
                dto.setChiefcoordinator("N");
            } else {
                dto.setChiefcoordinator("Y");
            }
            dto.setEntrydatetime(new Date());
            daoFactory.getSubjectCoordinatorDAO().add(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List updateStatus(HttpServletRequest request) {
        SubjectCoordinator dto = null;
        SubjectCoordinatorId id = new SubjectCoordinatorId();
        List list = null;
        list = new ArrayList<String>();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(instituteid);
            id.setSubjectid(request.getParameter("subid"));
            id.setStaffid(request.getParameter("stafid").trim());
            id.setStafftype(request.getParameter("stafftype").trim());
            id.setBreak_slno(BigDecimal.valueOf(1));
            String status = request.getParameter("deactive");
            if (status.equals("Y")) {
                List<String> list1 = (List<String>) daoFactory.getSubjectCoordinatorDAO().checkActiveSubject(request.getParameter("subid"), instituteid);
                boolean flag = true;
                for (int i = 0; i < list1.size(); i++) {
                    String str = list1.get(i);
                    if (str.equals("N")) {
                        flag = false;
                    }
                }
                if (!flag) {
                    list.add("This subject is already active with other coordinator!");
                    return list;
                }
            }
            dto = (SubjectCoordinator) daoFactory.getSubjectCoordinatorDAO().findByPrimaryKey(id);
            if (dto != null) {
                if (status.equals("N")) {
                    dto.setDeactive("Y");
                } else {
                    dto.setDeactive("N");
                }
                daoFactory.getSubjectCoordinatorDAO().update(dto);

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
