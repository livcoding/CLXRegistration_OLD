/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationSubjectGroupIService;
import com.jilit.irp.persistence.dto.Pr_ElectiveSets;
import com.jilit.irp.persistence.dto.Pr_ElectiveSetsId;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.util.JIRPSession;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet Singh
 */
@Service
public class RegistrationSubjectGroupService implements RegistrationSubjectGroupIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void getProgramCode(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("prog_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List getRegistrationSubjectGroupGridData(HttpServletRequest req) {
        List gridData = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programid");
            gridData = (List) daoFactory.getRegistrationSubjectGroupDAO().getGridData(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridData;
    }

    public List getGroupId(HttpServletRequest req) {
        List grouplist = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programid");
            grouplist = (List) daoFactory.getRegistrationSubjectGroupDAO().getGroupId(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grouplist;

    }

    public List getSubjects(HttpServletRequest req) {
        List subjectData = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programid");
            subjectData = (List) daoFactory.getRegistrationSubjectGroupDAO().getSubjects(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectData;

    }

    public List getGroupedSubjects(HttpServletRequest req) {
        List groupedSubject = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programid");
            String groupid = req.getParameter("groupid");
            groupedSubject = (List) daoFactory.getRegistrationSubjectGroupDAO().getGroupedSubjects(instituteid, programid, groupid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupedSubject;

    }

    public List saveRegistrationSubjectGroup(HttpServletRequest request) {
        Pr_ElectiveSets dto = new Pr_ElectiveSets();
        Pr_ElectiveSetsId id = new Pr_ElectiveSetsId();
        List err = new ArrayList<String>();
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = request.getParameter("programid");
            String group = request.getParameter("group").trim();
            String groupid = request.getParameter("groupid").trim();
            boolean flag = true;
            if (group.equalsIgnoreCase("new")) {
                List grouplist = (List) daoFactory.getRegistrationSubjectGroupDAO().validateGroupId(instituteId, programid, groupid);
                if (grouplist.size() > 0) {
                    flag = false;
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
            if (flag) {
                String[] subjects = request.getParameterValues("subjectid");
                for (int i = 0; i < subjects.length; i++) {
                    id.setInstituteid(instituteId);
                    id.setProgramid(programid);
                    id.setGroupid(groupid);
                    id.setSubjectid(subjects[i]);
                    dto.setId(id);
                    dto.setDeactive("N");
                    dto.setEntrydate(new Date());
                    dto.setUpdatedate(new Date());
                    daoFactory.getRegistrationSubjectGroupDAO().add(dto);
                }
                err.add("Success");
            } else {
                err.add("( " + groupid + " ) Group Name is already exists for this Program Code");
            }
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }

    @Override
    public List updateRegistrationSubjectGroup(HttpServletRequest request) {
        Pr_ElectiveSets dto = null;
        Pr_ElectiveSetsId id = new Pr_ElectiveSetsId();
        List list = null;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(instituteId);
            id.setProgramid(request.getParameter("programid"));
            id.setGroupid(request.getParameter("groupid").trim());
            id.setSubjectid(request.getParameter("subjectid").trim());
            String sts = request.getParameter("deactive");
            String status = "";
            if (sts.equalsIgnoreCase("N")) {
                status = "Y";
            } else {
                status = "N";
            }
            dto = (Pr_ElectiveSets) daoFactory.getRegistrationSubjectGroupDAO().findByPrimaryKey(id);
            if (dto != null) {
                dto.setDeactive(status);
                daoFactory.getRegistrationSubjectGroupDAO().update(dto);
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
 
    public List deleteRegistrationGroupedSubject(HttpServletRequest request) {
        List err = null;
        Pr_ElectiveSets dto = null;
        Pr_ElectiveSetsId id = new Pr_ElectiveSetsId();
        int i;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] pkid = pks[i].toString().split("~@~");
                id.setInstituteid(instituteId);
                id.setProgramid(pkid[0]);
                id.setSubjectid(pkid[1]);
                id.setGroupid(pkid[2]);
                dto = (Pr_ElectiveSets) daoFactory.getRegistrationSubjectGroupDAO().findByPrimaryKey(id);
                daoFactory.getRegistrationSubjectGroupDAO().delete(dto);

            }
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
    }
}
