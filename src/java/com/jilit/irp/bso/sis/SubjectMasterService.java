package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Ex_PatternMaster;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentDetail;
import com.jilit.irp.persistence.dto.SubjectComponentDetailId;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import com.jilit.irp.persistence.dto.SubjectComponentMaster;
import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.persistence.dto.SubjectMasterId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Nazar.Mohammad
 */
@Service
public class SubjectMasterService implements SubjectMasterIService {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getList(Model model) {
        List<SubjectMaster> subjectList = new ArrayList<SubjectMaster>();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            subjectList = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().findAllForMaster(instituteid);
            model.addAttribute("subjectList", subjectList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void subjectMasterAdd(Model model) {
        List<Ex_PatternMaster> patternList = new ArrayList<Ex_PatternMaster>();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            patternList = (List<Ex_PatternMaster>) daoFactory.getEx_PatternMasterDAO().findAll(instituteid);
            List<SubjectComponent> componentList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
            model.addAttribute("patternList", patternList);
            model.addAttribute("componentList", componentList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List deleteSubjectMaster(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String pks1[] = pks[i].toString().split("::");
                daoFactory.getSubjectMasterDAO().delete(daoFactory.getSubjectMasterDAO().findByPrimaryKey(new SubjectMasterId(pks1[0].toString(), pks1[1].toString())));
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

    @Override
    public List addSubjectMaster(HttpServletRequest request) {
        SubjectMaster submas = new SubjectMaster();
        SubjectComponentDetail detail = null;
        SubjectComponentDetailId detailid = null;
        Set<SubjectComponentDetail> subjectcomponentdetails = new HashSet<SubjectComponentDetail>();
        List err = new ArrayList<String>();
        BusinessService businessService = new BusinessService(daoFactory);
        SubjectMasterId id = new SubjectMasterId();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            id.setInstituteid(instituteid);
            submas.setId(id);
            submas.setSubjectcode(request.getParameter("subjectcode").toString().trim());
            submas.setSubjectdesc(request.getParameter("subjectdesc").toString());
            submas.setPatternid(request.getParameter("sPattern").toString());
            submas.setEntrydatetime(new Date());
            err = daoFactory.getSubjectMasterDAO().doValidate(submas, "save");
            if (err.size() > 0) {
                businessService.rollback();
                return err;
            }
            id.setSubjectid(businessService.generateId("SubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            submas.setId(id);

            //  submas.setCredits(new Byte(request.getParameter("credits").toString()));
            submas.setCredits(Double.parseDouble(request.getParameter("credits") != null ? request.getParameter("credits").toString() : "0"));
            submas.setTotalmarks((request.getParameter("totalmarks") != null && !request.getParameter("totalmarks").toString().trim().isEmpty()) ? (new Double(request.getParameter("totalmarks").toString().replace(" ", ""))) : null);
            submas.setPassingmarks((request.getParameter("pMarks") != null && !request.getParameter("pMarks").toString().trim().isEmpty()) ? (new Double(request.getParameter("pMarks").toString().replace(" ", ""))) : null);
            if (request.getParameter("deactive") != null) {
                submas.setDeactive(request.getParameter("deactive").toString());
            } else {
                submas.setDeactive("N");
            }
            submas.setSubjectflag(request.getParameter("subjecttype") != null ? request.getParameter("subjecttype") : null);
            submas.setShortname(request.getParameter("shortname"));
            for (int i = 1; i <= 3; i++) {
                if (request.getParameter("chk" + i + "") != null) {
                    detail = new SubjectComponentDetail();
                    detailid = new SubjectComponentDetailId();
                    String pks[] = request.getParameter("chk" + i + "").split(":");
                    detailid.setInstituteid(pks[0].toString());
                    detailid.setSubjectcomponentid(pks[1].toString());
                    detailid.setSubjectid(submas.getId().getSubjectid());
                    detail.setId(detailid);
                    if (request.getParameter("deactivecheck" + i + "") != null) {
                        detail.setDeactive(request.getParameter("deactivecheck" + i + "").toString().equals("on") ? "N" : "Y");
                    } else {
                        detail.setDeactive("Y");
                    }
                    detail.setLtppassingmarks(new BigDecimal(request.getParameter("ltppassingmarks" + i + "").toString().replace(" ", "")));
                    detail.setNoofclassinaweek((request.getParameter("noofclassinaweek" + i + "") != null && !request.getParameter("noofclassinaweek" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("noofclassinaweek" + i + "").toString().replace(" ", ""))) : null);
                    detail.setNoofhours((request.getParameter("noofhours" + i + "") != null && !request.getParameter("noofhours" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("noofhours" + i + "").toString().replace(" ", ""))) : null);
                    detail.setPassingmarks((request.getParameter("passingmarks" + i + "") != null && !request.getParameter("passingmarks" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("passingmarks" + i + "").toString().replace(" ", ""))) : null);
                    detail.setTotalccpmarks(new BigDecimal(request.getParameter("totalccpmarks" + i + "").toString().replace(" ", "")));
                    detail.setTotalclasses((request.getParameter("totalclass" + i + "") != null && !request.getParameter("totalclass" + i + "").toString().trim().isEmpty()) ? Short.valueOf(request.getParameter("totalclass" + i + "").toString().replace(" ", "")) : null);
                    detail.setTotalmarks((request.getParameter("totalmarks" + i + "") != null && !request.getParameter("totalmarks" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("totalmarks" + i + "").toString().replace(" ", ""))) : null);
                    subjectcomponentdetails.add(detail);
                }
            }
            submas.setSubjectcomponentdetails(subjectcomponentdetails);
            businessService.insertInIdGenerationControl(submas);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        } finally {
            businessService.closeSession();
        }
        return err;
    }

    @Override
    public String checkIfChildExist(HttpServletRequest request) {
        String uid = request.getParameter("pk");
        SubjectMasterId id = new SubjectMasterId();
        id.setInstituteid(uid.split("::")[0]);
        id.setSubjectid(uid.split("::")[1]);
        String status = (daoFactory.getSubjectMasterDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        return status;
    }

    @Override
    public ModelMap subjectMasterEdit(ModelMap model, HttpServletRequest request) {
        List<Ex_PatternMaster> patternList = new ArrayList<Ex_PatternMaster>();
        SubjectMaster submas = new SubjectMaster();
        SubjectMasterId id = new SubjectMasterId();
        SubjectComponentMaster master = null;

        List dto_list = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String pk[] = request.getParameter("pk").split("::");
            id.setInstituteid(pk[0].toString());
            id.setSubjectid(pk[1].toString());
            submas.setId(id);
            List<SubjectComponent> componentList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
            submas = (SubjectMaster) daoFactory.getSubjectMasterDAO().findByPrimaryKey(submas.getId());
            patternList = (List<Ex_PatternMaster>) daoFactory.getEx_PatternMasterDAO().findAll(submas.getId().getInstituteid());
            List<Object[]> list = (List<Object[]>) daoFactory.getSubjectMasterDAO().getGridSubjectcomponent(submas.getId().getSubjectid(), submas.getId().getInstituteid());
            for (int i = 0; i < list.size(); i++) {
                master = new SubjectComponentMaster();
                master.setSubjectcomponentcode(list.get(i)[0] == null ? "" : list.get(i)[0].toString());
                master.setSubjectcomponentid(list.get(i)[1] == null ? "" : list.get(i)[1].toString());
                master.setLtppassingmarks(list.get(i)[2] == null ? "" : list.get(i)[2].toString());
                master.setTotalccpmarks(list.get(i)[3] == null ? "" : list.get(i)[3].toString());
                master.setNoofhours(list.get(i)[4] == null ? "" : list.get(i)[4].toString());
                master.setNoofclassinaweek(list.get(i)[5] == null ? "" : list.get(i)[5].toString());
                master.setByltp(list.get(i)[6] == null ? "" : list.get(i)[6].toString());
                master.setTotalmarks(list.get(i)[7] == null ? "" : list.get(i)[7].toString());
                master.setPassingmarks(list.get(i)[8] == null ? "" : list.get(i)[8].toString());
                master.setTotalclasses(list.get(i)[9] == null ? "" : list.get(i)[9].toString());
                master.setDeactive(list.get(i)[10] == null ? "" : list.get(i)[10].toString());
                dto_list.add(master);
            }
            model.addAttribute("data", submas);
            model.addAttribute("patternList", patternList);
            model.addAttribute("componentList", dto_list);
            model.addAttribute("componentAllList", componentList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

    @Override
    public List updateSubjectMaster(HttpServletRequest request) {
        SubjectMaster submas = new SubjectMaster();
        SubjectComponentDetail detail = null;
        SubjectComponentDetailId detailid = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        Set<SubjectComponentDetail> subjectcomponentdetails = new HashSet<SubjectComponentDetail>();
        List err = new ArrayList<String>();
        SubjectMasterId id = new SubjectMasterId();
        try {
            id.setInstituteid(instituteid);
            id.setSubjectid(request.getParameter("subjectid").toString());
            submas.setId(id);
            submas = (SubjectMaster) daoFactory.getSubjectMasterDAO().findByPrimaryKey(submas.getId());
            if (submas != null) {
                submas.setSubjectcode(request.getParameter("subjectcode").toString().trim());
                submas.setSubjectdesc(request.getParameter("subjectdesc").toString());
                submas.setPatternid(request.getParameter("sPattern").toString());
//                submas.setCredits(new Byte(request.getParameter("credits").toString().replace(" ", "")));
                submas.setCredits(Double.parseDouble(request.getParameter("credits") != null ? request.getParameter("credits").toString() : "0"));
                submas.setTotalmarks((request.getParameter("totalmarks") != null && !request.getParameter("totalmarks").toString().trim().isEmpty()) ? (new Double(request.getParameter("totalmarks").toString().replace(" ", ""))) : null);
                submas.setPassingmarks((request.getParameter("pMarks") != null && !request.getParameter("pMarks").toString().trim().isEmpty()) ? (new Double(request.getParameter("pMarks").toString().replace(" ", ""))) : null);
                submas.setSubjectflag(request.getParameter("subjecttype") != null ? request.getParameter("subjecttype") : null);
                submas.setShortname(request.getParameter("shortname"));
                submas.setEntrydatetime(new Date());
                if (request.getParameter("chkbox") != null) {
                    submas.setDeactive(request.getParameter("chkbox").toString());
                } else {
                    submas.setDeactive("Y");
                }
                daoFactory.getSubjectMasterDAO().saveOrUpdate(submas);
            }
            for (int i = 1; i <= 3; i++) {
                if (request.getParameter("chk" + i + "") != null) {
                    detail = new SubjectComponentDetail();
                    detailid = new SubjectComponentDetailId();
                    detailid.setInstituteid(request.getParameter("instituteid").toString());
                    detailid.setSubjectcomponentid(request.getParameter("chk" + i + "").toString());
                    detailid.setSubjectid(request.getParameter("subjectid").toString());
                    detail.setId(detailid);
                    //  daoFactory.getSubjectComponentDAO().
                    detail = (SubjectComponentDetail) daoFactory.getSubjectComponentDAO().findByPrimaryKey1(detail.getId());
                    if (detail != null) {
                        daoFactory.getSubjectComponentDAO().delete(detail);
                    } else {
                        detail = new SubjectComponentDetail();
                        detailid.setInstituteid(request.getParameter("instituteid").toString());
                        detailid.setSubjectcomponentid(request.getParameter("chk" + i).toString());
                        detailid.setSubjectid(request.getParameter("subjectid").toString());
                        detail.setId(detailid);
                    }
                    //  if (detail != null) {
                    if (request.getParameter("deactivecheck" + i + "") != null) {
                        detail.setDeactive(request.getParameter("deactivecheck" + i + "").toString().equals("on") ? "N" : "Y");
                    } else {
                        detail.setDeactive("Y");
                    }
                    detail.setLtppassingmarks(new BigDecimal(request.getParameter("ltppassingmarks" + i + "").toString().replace(" ", "")));
                    detail.setNoofclassinaweek((request.getParameter("noofclassinaweek" + i + "") != null && !request.getParameter("noofclassinaweek" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("noofclassinaweek" + i + "").toString().replace(" ", ""))) : null);
                    detail.setNoofhours((request.getParameter("noofhours" + i + "") != null && !request.getParameter("noofhours" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("noofhours" + i + "").toString().replace(" ", ""))) : null);
                    detail.setPassingmarks((request.getParameter("passingmarks" + i + "") != null && !request.getParameter("passingmarks" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("passingmarks" + i + "").toString().replace(" ", ""))) : null);
                    detail.setTotalccpmarks(new BigDecimal(request.getParameter("totalccpmarks" + i + "").toString().replace(" ", "")));
                    detail.setTotalclasses((request.getParameter("totalclass" + i + "") != null && !request.getParameter("totalclass" + i + "").toString().trim().isEmpty()) ? Short.valueOf(request.getParameter("totalclass" + i + "").toString().replace(" ", "")) : null);
                    detail.setTotalmarks((request.getParameter("totalmarks" + i + "") != null && !request.getParameter("totalmarks" + i + "").toString().trim().isEmpty()) ? (new BigDecimal(request.getParameter("totalmarks" + i + "").toString().replace(" ", ""))) : null);
                    daoFactory.getSubjectComponentDAO().add(detail);
                    //}
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
}
