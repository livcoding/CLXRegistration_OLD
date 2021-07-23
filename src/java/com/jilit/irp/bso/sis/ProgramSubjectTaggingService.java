/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.ProgramSubjectTaggingIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.BasketMaster;
import com.jilit.irp.persistence.dto.BasketMasterDetail;
import com.jilit.irp.persistence.dto.BasketMasterDetailId;
import com.jilit.irp.persistence.dto.BasketMasterId;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ProgramSubjectTaggingService implements ProgramSubjectTaggingIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public void getProgramSubjectTaggingList(Model model) {
        List regList = null;
        List<Academicyear> acadList = null;
        List progList = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            regList = (List) daoFactory.getRegistrationMasterDAO().getAllRegistrationCodeDesc(instituteid);
            acadList = (List<Academicyear>) daoFactory.getAcademicYearDAO().findAll(instituteid);
            progList = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("regList", regList);
            model.addAttribute("acadList", acadList);
            model.addAttribute("progList", progList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getAcademicYear(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentRegistrationDAO().getAcadYearStudentMaster(instituteid, registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBranch(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String programId = req.getParameter("programId");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> ccList = daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programId);
            if (ccList != null && ccList.size() > 0) {
                rtnlist.add(ccList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    @Override
    public List getSemester(HttpServletRequest req) {
        List semester = new ArrayList();
        try {
            String registrationid = req.getParameter("regid");
            String academicyear = req.getParameter("acadYear");
            String programid = req.getParameter("program");
            String branchid = req.getParameter("branch");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            semester = daoFactory.getProgramMinMaxLimitDAO().getStynoForPST_TeachingSchem(instituteid, academicyear, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semester;
    }

    @Override
    public List getSection(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("program");
            String branchid = req.getParameter("branch");
            list = daoFactory.getSectionMasterDAO().getSectionDataWithBranch(instituteid, programid, branchid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBasket(HttpServletRequest req) {
        List basketList = new ArrayList();
        try {
            String programid = req.getParameter("program");
            byte semester = Byte.parseByte(req.getParameter("semester"));
            String section = req.getParameter("section");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            basketList = daoFactory.getBasketMasterDAO().getBasketCodeExistTeachingScheme(instituteid, programid, section, semester);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basketList;
    }

    @Override
    public List loadProgramTaggingList(HttpServletRequest req) {
        List retList = new ArrayList();
        List localList = null;
        String regId = req.getParameter("regId");
        String academicyear = req.getParameter("acadYear");
        String programid = req.getParameter("programId");
        String branchId = req.getParameter("branchId");
        Short semester = Short.parseShort(req.getParameter("semester"));
        String sectionid = req.getParameter("sectionId");
        String basketid = req.getParameter("basketId");
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        List<ProgramSubjectTagging> list = (List<ProgramSubjectTagging>) daoFactory.getProgramSubjectTaggingDAO().getProgramTaggingData(instituteid, regId, academicyear, programid, branchId, semester, sectionid, basketid);
        if (list != null) {
            try {
                for (int i = 0; i < list.size(); i++) {
                    localList = new ArrayList();
                    localList.add(((ProgramSubjectTagging) list.get(i)).getProgrammaster().getProgramcode()); // ProgramId 0
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSectionmaster().getId().getSectionid()); // SectionID 1
                    localList.add(((ProgramSubjectTagging) list.get(i)).getRegistrationmaster().getRegistrationcode());//Registyration Code 2
                    localList.add(list.get(i).getAcademicyear()); //Academic Year 3
                    localList.add(list.get(i).getStynumber()); //Semester 4
                    localList.add(((ProgramSubjectTagging) list.get(i)).getProgrammaster().getProgramcode()); // Program Code 5
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSectionmaster().getSectioncode()); // Section Code 6
                    if (list.get(i).getBasketid() != null) {
                        BasketMasterDetail basketMasterDetail = (BasketMasterDetail) daoFactory.getBasketMasterDetailDAO().findByPrimaryKey(new BasketMasterDetailId(((ProgramSubjectTagging) list.get(i)).getId().getInstituteid(), ((ProgramSubjectTagging) list.get(i)).getBasketid(), ((ProgramSubjectTagging) list.get(i)).getAcademicyear(), ((ProgramSubjectTagging) list.get(i)).getProgramid(), ((ProgramSubjectTagging) list.get(i)).getSectionid(), ((ProgramSubjectTagging) list.get(i)).getStynumber().byteValue()));
                        if (basketMasterDetail != null) {
                            localList.add((basketMasterDetail != null ? basketMasterDetail.getId().getBasketid() : "")); // BasketID 7
                            localList.add((basketMasterDetail != null ? basketMasterDetail.getBasketcode() : "")); // BAsket Code 8
                        } else {
                            BasketMaster basketMaster = (BasketMaster) daoFactory.getBasketMasterDAO().findByPrimaryKey(new BasketMasterId(((ProgramSubjectTagging) list.get(i)).getId().getInstituteid(), ((ProgramSubjectTagging) list.get(i)).getBasketid()));
                            localList.add((basketMaster != null ? basketMaster.getId().getBasketid() : "")); // BasketID 7
                            localList.add((basketMaster != null ? basketMaster.getBasketcode() : "")); // BAsket Code 8
                        }
                    }
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectmaster().getSubjectcode()); //SubjectCode 9
                    if (list.get(i).getSubjectrunning().equals("N")) {
                        localList.add("NO"); //Subject Running 10 
                    } else {
                        localList.add("YES"); //Subject Running 10
                    }
                    localList.add(list.get(i).getCredits()); //Credits 11
                    localList.add(((ProgramSubjectTagging) list.get(i)).getDepartmentmaster().getDepartmentcode());  // Department Code 12
                    if (list.get(i).getPstpopulated().equals("N")) {
                        localList.add("NO"); //PST Populated 13
                    } else {
                        localList.add("YES"); //PST Populated 13 
                    }
                    if (list.get(i).getCustomfinalized().equals("N")) {
                        localList.add("NO"); //Custom Finalize 14
                    } else {
                        localList.add("YES"); //Custom Finalize 14
                    }
                    if (list.get(i).getDeactive() != null) {
                        if (list.get(i).getDeactive().equals("N")) {
                            localList.add("NO"); //Deactive 15
                        } else {
                            localList.add("YES"); //Deactive 15
                        }
                    } else {
                        localList.add("NO"); //Deactive 15
                    }
                    if (list.get(i).getTthide() != null) {
                        localList.add(list.get(i).getTthide());  //TT Hide 16
                    } else {
                        localList.add("N/A");  //TT Hide 16
                    }
                    localList.add(list.get(i).getId().getInstituteid()); // 17
                    localList.add(list.get(i).getId().getRegistrationid()); // 18
                    localList.add(list.get(i).getId().getProgramsubjectid()); // 19
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectmaster().getSubjectdesc()); //SubjectDesc 20
                    localList.add(((ProgramSubjectTagging) list.get(i)).getDepartmentmaster().getDepartment());  // Department 21
                    localList.add(list.get(i).getId().getProgramsubjectid()); // 19
                    localList.add(list.get(i).getId().getProgramsubjectid()); // 19
                    retList.add(localList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retList;
    }

    @Override
    public List addProgramSubjectTagging(HttpServletRequest req) {
        List list = new ArrayList();
        List finalList = new ArrayList();
        String academicyear = req.getParameter("acadYear");
        String programid = req.getParameter("programId");
        byte semester = Byte.parseByte(req.getParameter("semester"));
        String sectionid = req.getParameter("sectionId");
        String basketid = req.getParameter("basketId");
        String registrationid = req.getParameter("regId");
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        list = (List) daoFactory.getProgramSchemeDAO().getSubjectTaggingList(instituteid, academicyear, sectionid, programid, semester, basketid, registrationid, " ");//last parameter is subjectid
        if (list.size() == 0) {
            list = (List) daoFactory.getProgramSchemeDAO().getSubjectTaggingListFromProgramScheme(instituteid, sectionid, programid, semester, basketid);
        }
        String subjectcode = "";
        List data = null;
        if (list != null) {
            try {
                HashMap data1 = null;
                for (int i = 0; i < list.size(); i++) {
                    data1 = (HashMap) list.get(i);
                    String subjectcode1 = (String) data1.get("subjectcode");
                    if (!subjectcode.equals(subjectcode1)) {
                        data = new ArrayList();
                    }
                    data.add((String) data1.get("subjectcomponentid"));//0
                    data.add((String) data1.get("subjectcode"));//1
                    data.add((String) data1.get("subjectid"));//2
                    data.add((String) data1.get("subjectdesc"));//3
                    data.add((BigDecimal) data1.get("ltppassingmarks"));//4
                    data.add((BigDecimal) data1.get("noofhours"));//5
                    data.add((BigDecimal) data1.get("noofclassinaweek"));//6
                    data.add((BigDecimal) data1.get("totalccpmarks"));//7
                    data.add((String) data1.get("subjectcomponentid"));//8
                    data.add((String) data1.get("departmentid"));//9
                    data.add((Double) data1.get("credits"));//10
                    data.add((BigDecimal) data1.get("byltp"));//11
                    data.add((String) data1.get("subjecttype"));//12 subjecttype
                    data.add(data1.get("passingmarks") + "/" + data1.get("totalmarks")); // 13 passingtotalmarks
                    String subjectcomponentid = (String) data1.get("subjectcomponentid");
                    SubjectComponent subjectComponent = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(instituteid, subjectcomponentid));
                    data.add((subjectComponent != null ? subjectComponent.getSubjectcomponentcode() : "")); //14
                    data.add((subjectComponent != null ? subjectComponent.getSubjectcomponentdesc() : "")); //15
                    String subjectcomponentcode = subjectComponent != null ? subjectComponent.getSubjectcomponentcode() : "";
                    if (subjectcomponentcode.contains("L")) {
                        data.add(subjectcomponentcode); //16 Lecture
                        data.add((BigDecimal) data1.get("totalccpmarks"));// 17
                        data.add((BigDecimal) data1.get("ltppassingmarks"));// 18
                        data.add(data1.get("subjectcomponentid"));// 19
                        data.add(data1.get("totalclasses"));// 20
                        data.add((BigDecimal) data1.get("noofhours"));// 21
                        data.add((BigDecimal) data1.get("noofclassinaweek"));// 22
                    } else {
                        data.add(subjectcomponentcode);//16 Lecture
                        data.add("0");//17 Lecturefullmarks
                        data.add("0");//18 Lecturepassingmarks
                        data.add("-");//19
                        data.add("-");//20
                        data.add("-");//21
                        data.add("-");//22
                    }

                    if (subjectcomponentcode.contains("T")) {
                        data.add(subjectcomponentcode);//23 theory
                        data.add((BigDecimal) data1.get("totalccpmarks"));//24
                        data.add((BigDecimal) data1.get("ltppassingmarks"));// 25
                        data.add(data1.get("subjectcomponentid"));// 26
                        data.add(data1.get("totalclasses"));// 27
                        data.add((BigDecimal) data1.get("noofhours"));//28
                        data.add((BigDecimal) data1.get("noofclassinaweek"));//29
                    } else {
                        data.add(subjectcomponentcode);//23 theory
                        data.add("0");//24 theoryfullmarks
                        data.add("0");//25 theorypassingmarks
                        data.add("-");//26
                        data.add("-");//27
                        data.add("-");//28
                        data.add("-");//29
                    }
                    if (subjectcomponentcode.contains("P")) {
                        data.add(subjectcomponentcode);//30 practicale
                        data.add((BigDecimal) data1.get("totalccpmarks"));//31
                        data.add((BigDecimal) data1.get("ltppassingmarks"));//32
                        data.add(data1.get("subjectcomponentid"));//33
                        data.add(data1.get("totalclasses"));//34
                        data.add((BigDecimal) data1.get("noofhours"));//35
                        data.add((BigDecimal) data1.get("noofclassinaweek"));//36
                    } else {
                        data.add(subjectcomponentcode);//30 practicale
                        data.add("0");//31 practicalfullmarks
                        data.add("0");//32 practicalpassingmarks
                        data.add("-");//33
                        data.add("-");//34
                        data.add("-");//35
                        data.add("-");//36
                    }
                    data.add(data1.get("subjecttype"));// 37
                    data.add(data1.get("subjecttypeid"));// 38
                    data.add(data1.get("auditsubject"));//39
                    if (!subjectcode.equals(subjectcode1)) {
                        finalList.add(data);
                    }
                    subjectcode = (String) data1.get("subjectcode");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return finalList;
    }

    @Override
    public List addNewProgramSubjectTagging(HttpServletRequest request) {

        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        BusinessService businessService = new BusinessService(daoFactory, true);
        Set set = new HashSet();
        List list = null;
        List list1 = new ArrayList();
        String registrationid = request.getParameter("regId");
        int count = Integer.parseInt(request.getParameter("totlaCountValue"));

        try {
            List recordsToInsert = new ArrayList();
            for (int i = 0; i <= count; i++) {
                programSubjectTagging = new ProgramSubjectTagging();
                programSubjectTaggingId = new ProgramSubjectTaggingId();
                if (request.getParameter("chk" + i + "") != null) {
                    String pks[] = request.getParameter("chk" + i + "").split("~@~");
                    String subjectComponentId = pks[8].toString();
                    String tthide = "";
                    if (pks[0].toString() != null) {
                        tthide = "Y";
                    } else {
                        tthide = "N";
                    }
                    programSubjectTaggingId.setInstituteid(jirpession.getJsessionInfo().getSelectedinstituteid());
                    programSubjectTaggingId.setRegistrationid(pks[5].toString());
                    String programsubjectid = businessService.generateId("ProgramSubjectID", jirpession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                    programSubjectTaggingId.setProgramsubjectid(programsubjectid);
                    programSubjectTagging.setAcademicyear(pks[1].toString());
                    programSubjectTagging.setProgramid(pks[0].toString());
                    programSubjectTagging.setSectionid(pks[3].toString());
                    programSubjectTagging.setStynumber(Short.parseShort(pks[2].toString()));
                    programSubjectTagging.setBasketid(pks[4].toString());
                    programSubjectTagging.setSubjectid(pks[7].toString());
                    programSubjectTagging.setTthide(tthide);
                    programSubjectTagging.setSubjecttype(pks[11].toString());
                    programSubjectTagging.setSubjecttypeid(pks[12].toString());
                    programSubjectTagging.setAuditsubject(pks[13].toString());

                    if (request.getParameter("pstPopulated" + i + "") != null) {
                        programSubjectTagging.setPstpopulated("Y");
                    } else {
                        programSubjectTagging.setPstpopulated("N");
                    }
                    if (request.getParameter("subjectRunning" + i + "") != null) {
                        programSubjectTagging.setSubjectrunning("Y");
                    } else {
                        programSubjectTagging.setSubjectrunning("N");
                    }
                    if (request.getParameter("customFinalized" + i + "") != null) {
                        programSubjectTagging.setCustomfinalized("Y");
                    } else {
                        programSubjectTagging.setCustomfinalized("N");
                    }
                    programSubjectTagging.setDepartmentid(pks[9].toString());
                    programSubjectTagging.setCredits(Double.parseDouble(pks[10].toString()));
                    if (request.getParameter("deactive" + i + "") != null) {
                        programSubjectTagging.setDeactive("N");
                    } else {
                        programSubjectTagging.setDeactive("Y");
                    }
                    programSubjectTagging.setId(programSubjectTaggingId);
                    list = daoFactory.getProgramSubjectTaggingDAO().doValidate(programSubjectTagging, "normal");
                    if (list.size() > 0) {
                        businessService.rollback();
                        return list;
                    }
                    set = new HashSet();
                    HashMap data1 = null;
                    list1 = (List) daoFactory.getProgramSchemeDAO().getSubjectTaggingList(jirpession.getJsessionInfo().getSelectedinstituteid(), pks[1].toString(), pks[3].toString(), pks[0].toString(), Byte.parseByte(pks[2].toString()), pks[4].toString(), registrationid, pks[7].toString());
                    if (list1.size() > 0) {
                        for (int j = 0; j < list1.size(); j++) {
                            data1 = (HashMap) list1.get(j);
                            SubjectComponent subjectComponent = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(jirpession.getJsessionInfo().getSelectedinstituteid(), subjectComponentId));
                            String subjectcomponentcode = subjectComponent != null ? subjectComponent.getSubjectcomponentcode() : "";
                            programSubjectDetail = new ProgramSubjectDetail();
                            programSubjectDetailId = new ProgramSubjectDetailId();
                            programSubjectDetailId.setInstituteid(jirpession.getJsessionInfo().getSelectedinstituteid());
                            programSubjectDetailId.setProgramsubjectid(programsubjectid);
                            programSubjectDetailId.setRegistrationid(pks[5].toString());

                            if (subjectcomponentcode.equals("P")) {
                                programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                            } else if (subjectcomponentcode.equals("T")) {
                                programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                            } else if (subjectcomponentcode.equals("L")) {
                                programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                            }
                            if (request.getParameter("deactive" + i + "") != null) {
                                programSubjectDetail.setDeactive("N");
                            } else {
                                programSubjectDetail.setDeactive("Y");
                            }
                            programSubjectDetail.setId(programSubjectDetailId);
                            set.add(programSubjectDetail);
                        }
                    }
                    programSubjectTagging.setProgramsubjectdetails(set);
                    recordsToInsert.add(programSubjectTagging);
                }
            }
            businessService.insertInIdGenerationControl(recordsToInsert);
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        } finally {
            businessService.closeSession();
        }
        return list;
    }

    @Override
    public ModelMap getAllProgramSubjectTaggingData(ModelMap mm, HttpServletRequest request) {
        String uid[] = request.getParameter("pk").split("~@~");
        List localList = null;
        List<ProgramSubjectTagging> list = (List<ProgramSubjectTagging>) daoFactory.getProgramSubjectTaggingDAO().getProgramTaggingData(uid[0], uid[1], uid[2]);
        if (list != null) {
            try {
                for (int i = 0; i < list.size(); i++) {
                    localList = new ArrayList();
                    localList.add(((ProgramSubjectTagging) list.get(i)).getId().getProgramsubjectid()); //Programsubjectid
                    localList.add(((ProgramSubjectTagging) list.get(i)).getId().getRegistrationid()); //Registrationid
                    localList.add(((ProgramSubjectTagging) list.get(i)).getAcademicyear()); //Academic Year
                    localList.add(((ProgramSubjectTagging) list.get(i)).getProgramid()); //ProgramId
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSectionid()); //SectionId
                    localList.add(((ProgramSubjectTagging) list.get(i)).getStynumber()); //StyNumber
                    localList.add(((ProgramSubjectTagging) list.get(i)).getBasketid()); //BasketId
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectid()); //SubjectId
                    localList.add(((ProgramSubjectTagging) list.get(i)).getDepartmentid()); //DepartmentId
                    localList.add(((ProgramSubjectTagging) list.get(i)).getCredits()); //credit
                    localList.add(((ProgramSubjectTagging) list.get(i)).getPstpopulated()); //PST populated
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectrunning()); //SubjectRunning
                    localList.add(((ProgramSubjectTagging) list.get(i)).getCustomfinalized()); //customFinalized
                    localList.add(((ProgramSubjectTagging) list.get(i)).getDeactive()); //deactive
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectmaster().getSubjectcode()); //SubjectCode
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjectmaster().getSubjectdesc()); //SubjectDesc
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjecttype()); //getSubjecttype
                    localList.add(((ProgramSubjectTagging) list.get(i)).getSubjecttypeid());//getSubjecttypeid
                    localList.add(((ProgramSubjectTagging) list.get(i)).getAuditsubject());//getSubjecttypeid

                    mm.addAttribute("editData", localList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return mm;
    }

    @Override
    public List updateProgramSubjectTagging(HttpServletRequest request) {
        List list = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        try {
            programSubjectTagging = new ProgramSubjectTagging();
            programSubjectTaggingId = new ProgramSubjectTaggingId();
            programSubjectTaggingId.setInstituteid(jirpession.getJsessionInfo().getSelectedinstituteid());
            programSubjectTaggingId.setRegistrationid(request.getParameter("regId"));
            programSubjectTaggingId.setProgramsubjectid(request.getParameter("programSubId"));
            programSubjectTagging.setId(programSubjectTaggingId);
            programSubjectTagging.setAcademicyear(request.getParameter("academicYear"));
            programSubjectTagging.setProgramid(request.getParameter("programId"));
            programSubjectTagging.setSectionid(request.getParameter("sectionId"));
            programSubjectTagging.setStynumber(Short.parseShort(request.getParameter("styNumber")));
            programSubjectTagging.setBasketid(request.getParameter("basketId"));
            programSubjectTagging.setSubjectid(request.getParameter("subjectId"));
            programSubjectTagging.setDepartmentid(request.getParameter("deptId"));
            programSubjectTagging.setCredits(Double.parseDouble(request.getParameter("credit")));
            programSubjectTagging.setSubjecttype(request.getParameter("subjecttype"));
            programSubjectTagging.setSubjecttypeid(request.getParameter("subjecttypeid"));
            programSubjectTagging.setCredits(Double.parseDouble(request.getParameter("credit")));
            programSubjectTagging.setAuditsubject(request.getParameter("auditsubject") == null ? "N" : "Y");
            if (request.getParameter("pstPopulated") != null) {
                programSubjectTagging.setPstpopulated("Y");
            } else {
                programSubjectTagging.setPstpopulated("N");
            }
            if (request.getParameter("subRunning") != null) {
                programSubjectTagging.setSubjectrunning("Y");
            } else {
                programSubjectTagging.setSubjectrunning("N");
            }
            if (request.getParameter("customFin") != null) {
                programSubjectTagging.setCustomfinalized("Y");
            } else {
                programSubjectTagging.setCustomfinalized("N");
            }

            if (request.getParameter("deactive") != null) {
                programSubjectTagging.setDeactive("N");
            } else {
                programSubjectTagging.setDeactive("Y");
            }
            programSubjectTagging.setId(programSubjectTaggingId);
            list = daoFactory.getProgramSubjectTaggingDAO().doValidate(programSubjectTagging, "edit");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getProgramSubjectTaggingDAO().saveOrUpdate(programSubjectTagging);
            list = new ArrayList();
            list.add("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    @Override
    public String checkIfChildExist(HttpServletRequest request) {
        String uid[] = request.getParameter("pk").split("~@~");
        String instituteId = uid[0];
        String registrationId = uid[1];
        String programsubjectId = uid[2];
        String status = null;
        status = (daoFactory.getProgramSubjectTaggingDAO().checkIfChildExist1(instituteId, registrationId, programsubjectId) > 0) ? "true" : "false";
        if (status.equals("false")) {
            status = (daoFactory.getProgramSubjectTaggingDAO().checkIfChildExist2(instituteId, registrationId, programsubjectId) > 0) ? "true" : "false";
        }
        return status;
    }

    @Override
    public List deleteProgramSubjectTagging(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids").replace("Y,", "");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split("~@~");
                daoFactory.getProgramSubjectTaggingDAO().delete(daoFactory.getProgramSubjectTaggingDAO().findByPrimaryKey(new ProgramSubjectTaggingId(id[0], id[1], id[2])));
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
    public List copyProgramSubjectTagging(HttpServletRequest request) {
        BusinessService businessService = new BusinessService(daoFactory);
        List retList = new ArrayList();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        ProgramSubjectTagging dto = null;
        ProgramSubjectTaggingId id = null;
        ProgramSubjectDetail detaildto = null;
        ProgramSubjectDetailId detailid = null;
        List programSubjectTaggingFinalList = new ArrayList();
        Set<ProgramSubjectDetail> programsubjectdetail = new HashSet<ProgramSubjectDetail>();
        try {

            List<Object[]> fromList = (List<Object[]>) daoFactory.getProgramSubjectTaggingDAO().getPSTList(instituteid, request.getParameter("acadYear1"), request.getParameter("program1"), request.getParameter("semester1"), request.getParameter("section1"), request.getParameter("basket1"), request.getParameter("regCode1"));
            List<Object[]> detailList = null;
            if (fromList != null && !fromList.isEmpty()) {
                for (int i = 0; i < fromList.size(); i++) {

                    String basketid = fromList.get(i)[11].toString();
                    if (daoFactory.getProgramSubjectTaggingDAO().checkPSTExistence(instituteid, request.getParameter("regCode2"), request.getParameter("acadYear2"), request.getParameter("program2"), request.getParameter("section2"), request.getParameter("semester2"), basketid, fromList.get(i)[0].toString())) {
                        continue;
                    }
                    dto = new ProgramSubjectTagging();
                    id = new ProgramSubjectTaggingId();
                    programsubjectdetail = new HashSet<ProgramSubjectDetail>();

                    id.setInstituteid(instituteid);
                    id.setRegistrationid(request.getParameter("regCode2"));
                    String ProgramSubjectID = businessService.generateIds("ProgramSubjectID", jirpession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", daoFactory);
                    id.setProgramsubjectid(ProgramSubjectID);
                    dto.setId(id);
                    dto.setAcademicyear(request.getParameter("acadYear2"));
                    dto.setBasketid(basketid);
                    dto.setSubjecttype(fromList.get(i)[12].toString());
                    dto.setSubjecttypeid(fromList.get(i)[13].toString());
                    dto.setBasketid(basketid);
                    dto.setCredits(Double.parseDouble(fromList.get(i)[2].toString()));
                    dto.setDeactive("N");
                    dto.setDepartmentid(fromList.get(i)[1] == null ? "" : fromList.get(i)[1].toString());
                    dto.setPassingmarks(fromList.get(i)[4] == null ? null : Double.parseDouble(fromList.get(i)[4].toString()));
                    dto.setProgramid(request.getParameter("program2"));
                    dto.setSectionid(request.getParameter("section2"));
                    dto.setStynumber(Short.valueOf(request.getParameter("semester2")));
                    dto.setSubjectid(fromList.get(i)[0].toString());
                    dto.setTotalmarks(fromList.get(i)[3] == null ? null : Double.parseDouble(fromList.get(i)[3].toString()));
                    dto.setCustomfinalized(fromList.get(i)[9] == null ? "" : fromList.get(i)[9].toString());
                    dto.setNoofstudents(fromList.get(i)[10] == null ? 0 : Integer.parseInt(fromList.get(i)[10].toString()));
                    dto.setSubjectrunning(fromList.get(i)[8] == null ? "" : fromList.get(i)[8].toString());
                    dto.setPstpopulated(fromList.get(i)[7] == null ? "" : fromList.get(i)[7].toString());
                    dto.setAttendanceapproved(fromList.get(i)[6] == null ? "" : fromList.get(i)[6].toString());
//                    Short sty =  Short.parseShort(request.getParameter("semester2").toString());
//                    List dulicatelist = daoFactory.getProgramSubjectTaggingDAO().dovalidate(instituteid, request.getParameter("acadYear2"), request.getParameter("program2"),sty, request.getParameter("section2"), basketid, request.getParameter("regCode2"),fromList.get(i)[0].toString());
//                    if (dulicatelist.size() > 0 && dulicatelist != null) {
//                        continue;
//                    }

                    detailList = (List<Object[]>) daoFactory.getProgramSubjectTaggingDAO().getPSTDetail(instituteid, fromList.get(i)[5].toString(), request.getParameter("regCode1"));
                    if (detailList != null && !detailList.isEmpty()) {
                        for (int j = 0; j < detailList.size(); j++) {
                            detaildto = new ProgramSubjectDetail();
                            detailid = new ProgramSubjectDetailId();
                            detailid.setInstituteid(instituteid);
                            detailid.setProgramsubjectid(ProgramSubjectID);
                            detailid.setSubjectcomponentid(detailList.get(j)[8].toString());
                            detailid.setRegistrationid(request.getParameter("regCode2"));
                            detaildto.setId(detailid);
                            detaildto.setByltp(detailList.get(j)[5] == null ? null : (BigDecimal) detailList.get(j)[5]);
                            detaildto.setDeactive("N");
                            detaildto.setLtppassingmarks((BigDecimal) (detailList.get(j)[0]));
                            detaildto.setNoofclassinaweek(detailList.get(j)[3] == null ? BigDecimal.valueOf(0) : (BigDecimal) detailList.get(j)[3]);
                            detaildto.setNoofhours(detailList.get(j)[2] == null ? BigDecimal.valueOf(0) : (BigDecimal) detailList.get(j)[2]);
                            detaildto.setPassingmarks(detailList.get(j)[7] == null ? 0 : (Double.parseDouble(detailList.get(j)[7].toString())));
                            detaildto.setTotalccpmarks((BigDecimal) detailList.get(j)[1]);
                            detaildto.setTotalclasses(detailList.get(j)[4] == null ? BigDecimal.valueOf(0) : (BigDecimal) detailList.get(j)[4]);
                            detaildto.setTotalmarks(detailList.get(j)[6] == null ? 0 : (Double.parseDouble(detailList.get(j)[6].toString())));
                            programsubjectdetail.add(detaildto);
                        }
                    }
                    dto.setProgramsubjectdetails(programsubjectdetail);

                    programSubjectTaggingFinalList.add(dto);
                }
                if (programSubjectTaggingFinalList != null && programSubjectTaggingFinalList.size() > 0) {
                    businessService.insertInIdGenerationControl(programSubjectTaggingFinalList);
                    retList.add("Success");
                } else {
                    retList.add("Pst Already Exists...");
                }
            } else {
                retList.add("PST Data not found for selected Filter...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            retList = new ArrayList();
            retList.add("Error");

        } finally {
            businessService.closeSession();
        }
        return retList;
    }

}
