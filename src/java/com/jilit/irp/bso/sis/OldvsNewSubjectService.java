package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.OldvsNewSubjectIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.OldvsNewSubject;
import com.jilit.irp.persistence.dto.OldvsNewSubjectId;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class OldvsNewSubjectService implements OldvsNewSubjectIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getAllOldvsNewSubject(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getAllDataOldVsNewSubject(instituteid);
            model.addAttribute("oldVsNewSubject", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllCombo(ModelMap model) {
        List subList = null;
        List semList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            subList = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getSubjectcode(instituteid);
            semList = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getSemesterCode(instituteid);
            model.addAttribute("subjectCode", subList);
            model.addAttribute("semesterCode", semList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModelMap getEditOldvsNewSubject(HttpServletRequest request, ModelMap model) {
        List list = null;
        List subList = null;
        List semList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String ids[] = request.getParameter("pk").split("~@~");
            list = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getEditOldvsNewSubject(instituteid, ids[0], ids[1]);
            subList = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getSubjectcode(instituteid);
            semList = (List<Object[]>) daoFactory.getOldvsNewSubjectDAO().getSemesterCode(instituteid);
            model.addAttribute("subjectCode", subList);
            model.addAttribute("semesterCode", semList);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public List getSaveOldvsNewSubject(HttpServletRequest request) {
        OldvsNewSubject dto = null;
        OldvsNewSubjectId id = null;
        List list = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dto = new OldvsNewSubject();
            id = new OldvsNewSubjectId();
            list = new ArrayList<String>();
            String oldSubjectCode = request.getParameter("subjectCode");
            String newSubjectCode = request.getParameter("newSubjectCode");
            id.setRegistrationid(request.getParameter("semesterCode"));
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setSubjectid(oldSubjectCode);
            dto.setId(id);
            dto.setWef(sdf.parse(request.getParameter("wef")));
            dto.setRemarks(request.getParameter("remarks") == null ? "" : request.getParameter("remarks").toString());
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            dto.setEntryby(jirpsession.getJsessionInfo().getUsername());
            dto.setEntrydatetime(new Date());
            dto.setNewsubjectid(newSubjectCode);
            list = daoFactory.getOldvsNewSubjectDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getOldvsNewSubjectDAO().add(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getUpdateOldvsNewSubject(HttpServletRequest request) {
        List list = null;
        OldvsNewSubject dto = null;
        OldvsNewSubjectId id = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dto = new OldvsNewSubject();
            id = new OldvsNewSubjectId();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setRegistrationid(request.getParameter("semesterCode"));
            id.setSubjectid(request.getParameter("subjectCode"));
            dto = (OldvsNewSubject) daoFactory.getOldvsNewSubjectDAO().findByPrimaryKey(id);
            if (dto != null) {
                dto.setWef(sdf.parse(request.getParameter("wef")));
                dto.setRemarks(request.getParameter("remarks") == null ? "" : request.getParameter("remarks").toString());
                dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
                dto.setNewsubjectid(request.getParameter("newSubjectCode"));
                daoFactory.getOldvsNewSubjectDAO().update(dto);
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

    @Override
    public List getDeleteOldvsNewSubject(HttpServletRequest request) {
        List list = null;
        try {
            String pk[] = request.getParameter("ids").split(",");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            for (int i = 0; i < pk.length; i++) {
                String ids[] = pk[i].toString().split("~@~");
                daoFactory.getOldvsNewSubjectDAO().delete(daoFactory.getOldvsNewSubjectDAO().findByPrimaryKey(new OldvsNewSubjectId(instituteid, ids[0], ids[1])));
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
