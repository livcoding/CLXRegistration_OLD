package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectComponentMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
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
 * @author deepak.gupta
 */
@Service
public class SubjectComponentMasterService implements SubjectComponentMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    /**
     * @description This Method is used for get Subject Component Code for
     * institute basis.
     * @param model
     * @return subjectComponentList
     */
    @Override
    public Model getSubjectComponentMasterList(Model model) {
        List<SubjectComponent> subjectComponentList = new ArrayList<SubjectComponent>();
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            subjectComponentList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAllWithDeactive(instituteid);
            model.addAttribute("subjectComponentList", subjectComponentList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

    /**
     * @description This method is used for add new Subject Component code.
     * @param request
     * @return Success or error message.
     */
    @Override
    public List addSubjectComponentMaster(HttpServletRequest request) {
        SubjectComponent dto = null;
        SubjectComponentId id = null;
        List list = new ArrayList<String>();
        String instituteuid = jirpession.getJsessionInfo().getSelectedinstituteid();
        BusinessService businessService = null;
        try {
            dto = new SubjectComponent();
            id = new SubjectComponentId();
            businessService = new BusinessService(daoFactory);
            id.setInstituteid(instituteuid);
            id.setSubjectcomponentid(businessService.generateId("SubjectComponentID", jirpession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            dto.setId(id);
            dto.setSubjectcomponentcode(request.getParameter("subLodeCode"));
            dto.setSubjectcomponentdesc(request.getParameter("subLoadDesc"));
            dto.setClassperweekformula(Short.decode(request.getParameter("classFormula")));
            dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectComponentDAO().getMaxSequenceIdInstPK(dto, jirpession.getJsessionInfo().getSelectedinstituteid()) + 1)));
          //  dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectComponentDAO().getMaxSeqId(dto) + 1)));
            dto.setDeactive(request.getParameter("deactive"));
            list = daoFactory.getSubjectComponentDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                 businessService.rollback();
                return list;
            } else {
                businessService.insertInIdGenerationControl(dto);
                list.add("Success");
            }
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            list.add("Error");
        } finally {
            businessService.closeSession();
        }
        return list;
    }

    /**
     * @description This method is used for get selected record behalf of id
     * @param mm
     * @param request
     * @return selected row data
     */
    @Override
    public ModelMap getAllSubjectComponentMasterData(ModelMap mm, HttpServletRequest request) {
        try {
            SubjectComponent dto = new SubjectComponent();
            SubjectComponentId id = new SubjectComponentId();
            String pk = request.getParameter("pk");
            String sr[] = pk.split(":");
            mm.addAttribute("instituteid", sr[0]);
            mm.addAttribute("subjectcomponentid", sr[1]);
            id.setInstituteid(sr[0]);
            id.setSubjectcomponentid(sr[1]);
            dto = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mm;
    }

    /**
     * @description This method is used for update subject component master data
     * @param request
     * @return Success or error message
     */
    @Override
    public List updateSubjectComponentMaster(HttpServletRequest request) {
        SubjectComponent dto = new SubjectComponent();
        SubjectComponentId id = new SubjectComponentId();
        List list = null;
        try {
            id.setInstituteid(request.getParameter("instituteId"));
            id.setSubjectcomponentid(request.getParameter("subjectComponentId"));
            dto.setId(id);
            dto.setSubjectcomponentcode(request.getParameter("subLodeCode"));
            dto.setSubjectcomponentdesc(request.getParameter("subLoadDesc"));
            dto.setClassperweekformula(Short.decode(request.getParameter("classFormula")));
            if (!request.getParameter("seqId").equals("")) {
                dto.setSeqid(Short.decode(request.getParameter("seqId")));
            } else {
                dto.setSeqid(null);
            }
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            list = new ArrayList<String>();
            daoFactory.getStudentCategoryDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    /**
     * @description This methos is used for check child record exist or not
     * @param request
     * @return status
     */
    @Override
    public String checkIfChildExist(HttpServletRequest request) {
        String uid = request.getParameter("pk");
        SubjectComponentId id = new SubjectComponentId();
        id.setInstituteid(uid.split(":")[0]);
        id.setSubjectcomponentid(uid.split(":")[1]);
        String status = (daoFactory.getSubjectComponentDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        return status;
    }

    /**
     * @description This method is used for delete selected record.
     * @param request
     * @return success or error message
     */
    @Override
    public List deleteSubjectComponentMaster(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split(":");
                daoFactory.getSubjectComponentDAO().delete(daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(id[0], id[1])));
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
