package com.jilit.irp.bso.sis;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.StudentSubjectFinalizationIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class StudentSubjectFinalizationService implements StudentSubjectFinalizationIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List list = (List) daoFactory.getUtilDAO().findSimpleData("findAllRegistrationMaster", new Object[]{instituteid});
            model.addAttribute("data", list);
            String parametervalue = "";
            String moduleid = "MOD08000002";
            String parameterid = "B1.1";
            List parameterval = (List) daoFactory.getStudentRegistrationDAO().getParametervalueFOrRegistrationConfirmation(instituteid, moduleid, parameterid);
            if (parameterval.size() > 0 && parameterval.get(0) != null) {
                parametervalue = parameterval.get(0).toString();
                model.addAttribute("parametervalue", parametervalue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getAcadmicyear(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid = request.getParameter("regId");
        List list = null;
        try {
            list = daoFactory.getStudentRegistrationDAO().getAcadmicyearForSubjectFinalization(instituteid, regid);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getProgram(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid = request.getParameter("regId");
        String acadYear = request.getParameter("acadYear");
        List list = null;
        try {
            list = daoFactory.getProgramMasterDAO().getProgramForSubjectFinalization(instituteid, regid, acadYear);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getBranch(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid = request.getParameter("regId");
        String acadYear = request.getParameter("acadYear");
        String programId = request.getParameter("programId");
        List list = null;
        try {
            list = daoFactory.getBranchMasterDAO().getBranchForSubjectFinalization(instituteid, regid, acadYear, programId);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSection(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid = request.getParameter("regId");
        String acadYear = request.getParameter("acadYear");
        String programId = request.getParameter("programId");
        String[] branchId = request.getParameter("branchId").split(",");
        List brlist = new ArrayList();
        brlist = Arrays.asList(branchId);
        List list = null;
        try {
            list = daoFactory.getSectionMasterDAO().getSectionForSubjectFinalization(instituteid, regid, acadYear, programId, brlist);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public Map getStudentSubjectFinalizationData(HttpServletRequest request) {
        boolean checkCrLimit = true;
        if (request.getParameter("allocatesubwithcrlimit").equals("N")) {
            checkCrLimit = false;
        }
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid = request.getParameter("regId");
        String acadYear = request.getParameter("acadYear");
        String programId = request.getParameter("programId");
        String[] sectionId = request.getParameter("sectionId").split(",");
        String sectionList = null;
        for (int sec = 0; sec < sectionId.length; sec++) {
            if (sec > 0) {
                sectionList = sectionList + "','" + sectionId[sec] + "";
            } else {
                sectionList = "" + sectionId[sec] + "";
            }
        }
        List criterialist = new ArrayList();
        String ids = acadYear + ":" + programId + ":" + sectionList;
        criterialist.add(ids);

        Map map = new HashMap();
        try {
            map = daoFactory.getStudentSubjectChoiceMasterDAO().getStudentDataForStudentSubjectFinalization(instituteid, regid, criterialist, checkCrLimit, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public String doSave(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        boolean autoRegistrationConfirmation = false;
        Date regConfirmationDate = null;
        if (request.getParameter("chkbox") != null) {
            autoRegistrationConfirmation = true;
        }
        PRFacultyStudentTagging prfst = new PRFacultyStudentTagging();
        FacultyStudentTagging fst = new FacultyStudentTagging();
        List<FacultyStudentTagging> saveListFS = new ArrayList<FacultyStudentTagging>();
        FacultyStudentTaggingId fstid = null;
        PRFacultyStudentTaggingId prfstid = null;
        StudentRegistration stureg = new StudentRegistration();
        BusinessService businessService = new BusinessService(daoFactory);
        String result = "success";
        String studentfstid = "";
        List saveList = new ArrayList();
        List<StudentRegistration> updateList = new ArrayList<StudentRegistration>();
        String userid = jirpsession.getJsessionInfo().getMemberid();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String studentids = request.getParameter("elegiblestucountval");
        int count = Integer.parseInt(studentids);
        String regid = request.getParameter("regCode");
        try {
            for (int i = 0; i < count; i++) {
                if (request.getParameter("chk" + i) != null) {
                    String studentid = request.getParameter("chk" + i);
                    List l = daoFactory.getStudentSubjectChoiceMasterDAO().getStudentFSTids(instituteid, regid, studentid);
                    for (int j = 0; j < l.size(); j++) {
                        Object[] object = (Object[]) l.get(j);
                        //0 =  instituteid ; 1 =  fstid ; 2 = studentid
                        if (object[6].toString().equals("0")) {
                            prfst = new PRFacultyStudentTagging();
                            prfstid = new PRFacultyStudentTaggingId(object[0].toString(), object[1].toString(), object[2].toString());
                            prfst.setId(prfstid);
                            prfst.setSstpopulated("N");
                            prfst.setDeactive("N");
                            prfst.setPopulationdate(new Date());
                            prfst.setPopulatedbyuser(userid);
                            prfst.setRegistrationid(regid);
                            prfst.setAuditsubject(object[4] == null ? "N" : object[4].toString());
                            saveList.add(prfst);

                            fst = new FacultyStudentTagging();
                            studentfstid = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
//                                
                            fstid = new FacultyStudentTaggingId(object[0].toString(), studentfstid, object[2].toString());
                            fst.setFstid(object[1].toString());
                            fst.setId(fstid);
                            fst.setEntryby(userid);
                            fst.setEntrydate(new Date());
                            fst.setDeactive("N");
                            if (autoRegistrationConfirmation) {
                                fst.setRegconfirmationdate(regConfirmationDate);
                                regConfirmationDate = df.parse(request.getParameter("regConfirm_date"));
                            }
                            fst.setRegistrationid(regid);
                            fst.setSubjectid(object[3] == null ? "" : object[3].toString());
                            fst.setAuditsubject(object[4] == null ? "N" : object[4].toString());
                            fst.setEquivalentsubjectid(object[5] == null ? "" : object[5].toString());
                            saveListFS.add(fst);

                        }
                    }
                    if (autoRegistrationConfirmation) {
                        regConfirmationDate = df.parse(request.getParameter("regConfirm_date"));
                        stureg = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(new StudentRegistrationId(instituteid, regid, studentid));
                        if (stureg != null) {
                            stureg.setRegconfirmation("Y");
                            stureg.setRegconfirmationuser(userid);
                            stureg.setRegconfirmatiodate(regConfirmationDate);
                            updateList.add(stureg);
                        }

                    }
                }
            }
            if (saveList.isEmpty()) {
                businessService.rollback();
                return "All Record Is Already Finalized";
            }
            if (saveListFS != null && !saveListFS.isEmpty()) {
                businessService.insertInIdGenerationControl(saveListFS);
            }
            result = daoFactory.getStudentSubjectChoiceMasterDAO().saveStudentSubjectFinalize(saveList, saveListFS, updateList);
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            result = "Error in tx update";
        } finally {
            businessService.closeSession();
        }
        return result;

    }

}
