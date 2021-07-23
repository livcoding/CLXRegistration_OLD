package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.ExamPatternMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Ex_PatternMaster;
import com.jilit.irp.persistence.dto.Ex_PatternMasterId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class ExamPatternMasterService implements ExamPatternMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getExamPatternMasterList(Model model) {
        List<Ex_PatternMaster> examPatternList = null;
        try {
            examPatternList = new ArrayList<Ex_PatternMaster>();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            examPatternList = (List<Ex_PatternMaster>) daoFactory.getEx_PatternMasterDAO().findAll(instituteid);
            model.addAttribute("examPatternList", examPatternList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void getComponentCode(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getSubjectComponentDAO().findAll(instituteid);
        model.addAttribute("component_code",list);
    }

    @Override
    public List saveExamPatternMaster(HttpServletRequest request) {
        Ex_PatternMaster dto = null;
        Ex_PatternMasterId id = null;
        List list = null;
        BusinessService businessService = null;
        try {
            list = new ArrayList<String>();
            dto = new Ex_PatternMaster();
            id = new Ex_PatternMasterId();
            businessService = new BusinessService(daoFactory, true);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String patternCode = request.getParameter("patternCode").trim();
            String patternName = request.getParameter("patternName").trim();
            String minAttendance = request.getParameter("minAttendance").trim();
            String deactive = request.getParameter("deactive");
            String activeDeactive = "";
            int sid = 0;
            if (deactive != null) {
                activeDeactive = deactive;
            } else {
                activeDeactive = "Y";
            }
            id.setInstituteid(instituteid);
            id.setPatternid(businessService.generateId("ExamPatternID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            dto.setId(id);
            dto.setPatterncode(patternCode);
            dto.setPatternname(patternName);
            dto.setDeactive(activeDeactive);
            dto.setminattendance(new BigDecimal(minAttendance));
            List seqidList = daoFactory.getEx_PatternMasterDAO().getSeqId(instituteid);
            if (seqidList.size() == 0) {
                sid = 0;
            } else {
                sid = Integer.parseInt(seqidList.get(0).toString());
            }
            short seqid = (short) ++sid;
            dto.setSeqid(seqid);
            list = daoFactory.getEx_PatternMasterDAO().doValidate(dto, "save");
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
        } finally {
            businessService.closeSession();
        }
        return list;
    }

    @Override
    public ModelMap getExamPatternMasterEdit(HttpServletRequest request, ModelMap mm) {
        Ex_PatternMaster dto = null;
        Ex_PatternMasterId id = null;
        try {
            dto = new Ex_PatternMaster();
            id = new Ex_PatternMasterId();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            id.setInstituteid(ids[0]);
            id.setPatternid(ids[1]);
            dto = (Ex_PatternMaster) daoFactory.getEx_PatternMasterDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;
    }

    @Override
    public List updateExamPatternMaster(HttpServletRequest request) {
        Ex_PatternMaster dto = null;
        Ex_PatternMasterId id = null;
        List list = null;
        try {
            list = new ArrayList<String>();
            dto = new Ex_PatternMaster();
            id = new Ex_PatternMasterId();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String patternId = request.getParameter("PatternId").trim();
            String patternCode = request.getParameter("patternCode").trim();
            String patternName = request.getParameter("patternName").trim();
            String minAttendance = request.getParameter("minAttendance").trim();
            String deactive = request.getParameter("deactive");
            String activeDeactive = "";
            int sid = 0;
            if (deactive != null) {
                activeDeactive = deactive;
            } else {
                activeDeactive = "Y";
            }
            id.setInstituteid(instituteid);
            id.setPatternid(patternId);
            dto.setId(id);
            dto.setPatterncode(patternCode);
            dto.setPatternname(patternName);
            dto.setDeactive(activeDeactive);
            dto.setminattendance(new BigDecimal(minAttendance));
            List seqidList = daoFactory.getEx_PatternMasterDAO().getSeqId(instituteid);
            if (seqidList.size() == 0) {
                sid = 0;
            } else {
                sid = Integer.parseInt(seqidList.get(0).toString());
            }
            short seqid = (short) ++sid;
            dto.setSeqid(seqid);
            list = daoFactory.getEx_PatternMasterDAO().doValidate(dto, "edit");
            if (list != null && !list.isEmpty()) {
                return list;
            }
            daoFactory.getEx_PatternMasterDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        } finally {
        }
        return list;
    }

    

}
