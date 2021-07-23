package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.GradeImprovementCriteriaMasterIServices;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SemesterTypeMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.Setup_GIPCriteria;
import com.jilit.irp.persistence.dto.Setup_GIPCriteriaId;
import com.jilit.irp.persistence.dto.Setup_GIPCriteriaDetail;
import com.jilit.irp.persistence.dto.Setup_GIPCriteriaDetailId;
import com.jilit.irp.persistence.dto.ExamGradeMaster;
import com.jilit.irp.persistence.dto.ExamGradeMasterId;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author campus.trainee
 */
@Service
public class GradeImprovementCriteriaMasterServices implements GradeImprovementCriteriaMasterIServices {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void getListGradeImprovementCriteriaMaster(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getSetup_GIPCriteriaDAO().getAllSetup_GIPCriteria(instituteid);
            model.addAttribute("setUPGIP", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllProgramMaster(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            List list1 = (List) daoFactory.getExamGradeMasterDAO().getGrades(instituteid);
            model.addAttribute("programList", list);
            model.addAttribute("grades", list1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ModelMap editGradeImprovementCriteriaMaster(HttpServletRequest request, ModelMap model) {
        List list = null;
        List list1 = null;
        List list2 = null;
        List<ProgramMaster> programList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String pk = request.getParameter("pk");
            String ids[] = pk.split("~@~");
            list = (List<Object[]>) daoFactory.getSetup_GIPCriteriaDAO().getEditSetip_Criteria(ids[0], ids[1]);
            list1 = (List<Object[]>) daoFactory.getSetup_GIPCriteriaDAO().getApplicableGrade(ids[0], ids[1]);
            list2 = (List) daoFactory.getExamGradeMasterDAO().getGrades(instituteid);
            programList = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().findAll(instituteid);
            model.addAttribute("data", list);
            model.addAttribute("datadetail", list1);
            model.addAttribute("grades", list2);
            model.addAttribute("progList", programList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public List saveGradeImprovementCriteriaMaster(HttpServletRequest req) {
        Setup_GIPCriteria master = new Setup_GIPCriteria();
        Setup_GIPCriteriaId id = new Setup_GIPCriteriaId();
        Setup_GIPCriteriaDetail ddto = new Setup_GIPCriteriaDetail();
        Setup_GIPCriteriaDetailId did = new Setup_GIPCriteriaDetailId();
        List err = new ArrayList<String>();
        try {
            String programid = req.getParameter("programid");
            String cgpaFrom = req.getParameter("cgpaFrom");
            Double dbFrom = Double.parseDouble(cgpaFrom);
            String cgpaTo = req.getParameter("cgpaTo");
            Double dbTO = Double.parseDouble(cgpaTo);
            String applicablegrades = req.getParameter("applicablegrades");
            String assignGrade = req.getParameter("assignGrade");
            String ins_id = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(ins_id);
            id.setProgramid(programid);
            master.setId(id);
            master.setCgparangefrom(BigDecimal.valueOf(dbFrom));
            master.setCgparangeupto(BigDecimal.valueOf(dbTO));
            master.setAppicablegrades(applicablegrades);
            master.setFailgradeflag(assignGrade);
            master.setEntrydatetime(new Date());
            daoFactory.getSetup_GIPCriteriaDAO().saveOrUpdate(master);
            String c = req.getParameter("count");
            int count = Integer.parseInt(c);
            for (int i = 0; i < count; i++) {
                String gradeid = req.getParameter("chk" + i);
                if (gradeid != null) {
                    did.setInstituteid(ins_id);
                    did.setProgramid(programid);
                    did.setApplicablegradeid(gradeid);
                    ddto.setId(did);
                    ddto.setEntrydatetime(new Date());
                    daoFactory.getSetup_GIPCriteriaDetailDAO().add(ddto);
                }
            }
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;
    }

    public List deleteGradeImprovementCriteriaMaster(HttpServletRequest request) {
        List list = null;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (int i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split("~@~");
                daoFactory.getSetup_GIPCriteriaDAO().delete(daoFactory.getSetup_GIPCriteriaDAO().findByPrimaryKey(new Setup_GIPCriteriaId(id[0], id[1])));
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

    public List updateGradeImprovementCriteriaMaster(HttpServletRequest req) {
        Setup_GIPCriteria dto = null;
        Setup_GIPCriteriaId id = null;
        Setup_GIPCriteriaDetail ddto = new Setup_GIPCriteriaDetail();
        Setup_GIPCriteriaDetailId did = new Setup_GIPCriteriaDetailId();
        List list = null;
        String cgpaFrom = req.getParameter("cgpaFrom");
        Double dbFrom = Double.parseDouble(cgpaFrom);
        String cgpaTo = req.getParameter("cgpaTo");
        Double dbTO = Double.parseDouble(cgpaTo);
        try {
            dto = new Setup_GIPCriteria();
            id = new Setup_GIPCriteriaId();
            id.setProgramid(req.getParameter("programid"));
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            dto.setId(id);
            list = new ArrayList<String>();
            dto = (Setup_GIPCriteria) daoFactory.getSetup_GIPCriteriaDAO().findByPrimaryKey(id);
            String assignGrade = req.getParameter("assignGrade");
            if (dto != null) {
                dto.setFailgradeflag(assignGrade);
                dto.setEntrydatetime(new Date());
                dto.setCgparangefrom(BigDecimal.valueOf(dbFrom));
                dto.setCgparangeupto(BigDecimal.valueOf(dbTO));
                dto.setAppicablegrades(req.getParameter("applicablegrades"));
                daoFactory.getSetup_GIPCriteriaDAO().update(dto);
            }
            String c = req.getParameter("count");
            int count = Integer.parseInt(c);
            daoFactory.getSetup_GIPCriteriaDetailDAO().deleteGIPDetail(jirpsession.getJsessionInfo().getSelectedinstituteid(), req.getParameter("programid"));
            for (int i = 0; i < count; i++) {
                String gradeid = req.getParameter("chk" + i);
                if (gradeid != null) {
                    did.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
                    did.setProgramid(req.getParameter("programid"));
                    did.setApplicablegradeid(gradeid);
                    ddto.setId(did);
                    ddto.setEntrydatetime(new Date());
                    daoFactory.getSetup_GIPCriteriaDetailDAO().saveOrUpdate(ddto);
                }
            }
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }
}
