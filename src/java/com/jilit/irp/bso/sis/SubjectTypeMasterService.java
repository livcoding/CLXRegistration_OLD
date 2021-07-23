package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectTypeMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.SubjectTypeMaster;
import com.jilit.irp.persistence.dto.SubjectTypeMasterId;
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
 * @author ankur.goyal
 */
@Service
public class SubjectTypeMasterService implements SubjectTypeMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    /**
     * @description This method is used for get Grid data from
     * SubjectTypeMaster.
     * @param model
     */
    @Override
    public void getSubjectTypeMasterList(Model model) {
        List<SubjectTypeMaster> subjectList = null;
        try {
            subjectList = new ArrayList<SubjectTypeMaster>();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            subjectList = (List<SubjectTypeMaster>) daoFactory.getSubjectTypeMasterDAO().findAll(instituteid);
            model.addAttribute("subjectList", subjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for edit record by using primary key
     * basis.
     * @param request
     * @param mm
     * @return mm
     */
    @Override
    public ModelMap getSubjectTypeMasterEdit(HttpServletRequest request, ModelMap mm) {
        SubjectTypeMaster dto = null;
        SubjectTypeMasterId id = null;
        try {
            dto = new SubjectTypeMaster();
            id = new SubjectTypeMasterId();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            mm.addAttribute("institute_id", ids[0]);
            mm.addAttribute("subject_type_id", ids[1]);
            id.setInstituteid(ids[0]);
            id.setSubjecttypeid(ids[1]);
            dto = (SubjectTypeMaster) daoFactory.getSubjectTypeMasterDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;
    }

    /**
     * @description This method is used for add record into SubjectTypeMaster.
     * @param request
     * @return Success or Error message
     */
    @Override
    public List getSubjectTypeMasterSave(HttpServletRequest request) {
        SubjectTypeMaster dto = null;
        SubjectTypeMasterId id = null;
        BusinessService businessService = null;
        List list = null;
        try {
            list = new ArrayList<String>();
            dto = new SubjectTypeMaster();
            id = new SubjectTypeMasterId();
            businessService = new BusinessService(daoFactory);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(instituteid);
            id.setSubjecttypeid(businessService.generateId("SubjectTypeID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            dto.setId(id);
            dto.setSubjecttype(request.getParameter("subject_Type").trim());
            dto.setSubjecttypedesc(request.getParameter("subject_Type_Desc").trim());
            dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectTypeMasterDAO().getMaxSequenceIdInstPK(dto, jirpsession.getJsessionInfo().getSelectedinstituteid()) + 1)));
          //  dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectTypeMasterDAO().getMaxSeqId(dto) + 1)));
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = daoFactory.getSubjectTypeMasterDAO().doValidate(dto, "save");
            if (list != null && !list.isEmpty()) {
                businessService.rollback();
                return list;
            }
            businessService.insertOrUpdateInIdGenerationControl(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            list = new ArrayList<String>();
            list.add("Error");
        }
        finally
        {
            businessService.closeSession();
        }
        return list;
    }

    /**
     * @description This method is used for record update SubjectTypeMaster.
     * @param request
     * @return Success or Error Message
     */
    @Override
    public List getSubjectTypeMasterUpdate(HttpServletRequest request) {
        SubjectTypeMaster dto = null;
        SubjectTypeMasterId id = null;
        List list = null;
        String seqId = request.getParameter("Seq_Id");
        try {
            dto = new SubjectTypeMaster();
            id = new SubjectTypeMasterId();
            id.setInstituteid(request.getParameter("institute_id"));
            id.setSubjecttypeid(request.getParameter("subject_type_id"));
            dto.setId(id);
            list = new ArrayList<String>();
            dto.setSubjecttype(request.getParameter("subject_Type").trim());
            dto.setSubjecttypedesc(request.getParameter("subject_Type_Desc"));
            if (!"".equals(seqId)) {
                dto.setSeqid(Short.parseShort(request.getParameter("Seq_Id")));
            } else {
                dto.setSeqid(null);
            }
            dto.setDeactive(request.getParameter("deactive") == null ? "Y" : request.getParameter("deactive").toString());
            list = daoFactory.getSubjectTypeMasterDAO().doValidate(dto, "edit");
            if (list != null && !list.isEmpty()) {
                return list;
            }
            daoFactory.getSubjectTypeMasterDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This method is used for delete record from
     * SubjectTypeMaster.
     * @param request
     * @return Success or Error message.
     */
    @Override
    public List getSubjectTypeMasterDelete(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split(":");
                daoFactory.getSubjectTypeMasterDAO().delete(daoFactory.getSubjectTypeMasterDAO().findByPrimaryKey(new SubjectTypeMasterId(id[0], id[1])));
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

    /**
     * @description This method is used for check child exist in
     * SubjecTypeMaster table.
     * @param request
     * @return status
     */
    @Override
    public String checkIfChildExist(HttpServletRequest request) {
        String status = "";
        try {
            String pk = request.getParameter("pk");
            String[] ids = pk.split(":");
            SubjectTypeMasterId id = new SubjectTypeMasterId();
            id.setInstituteid(ids[0]);
            id.setSubjecttypeid(ids[1]);
            status = (daoFactory.getSubjectTypeMasterDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
