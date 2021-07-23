package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.PhdSubjectFinalizationIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.BasketMaster;
import com.jilit.irp.persistence.dto.BasketMasterId;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ankit.kumar
 */
@Service
public class PhdSubjectFinalizationService implements PhdSubjectFinalizationIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List getPendingForApprovalData(HttpServletRequest request) {
        String arr[] = request.getParameter("registrationid").split("~@~");
        String registrationid = arr[0];
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        List retList = new ArrayList();
        try {
            retList = (List<Object[]>) daoFactory.getPhdSelfcourseRegistrationDAO().getPHDSubjectRegistrationDataPending(instituteid, registrationid, "P");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List getApprovedData(HttpServletRequest request) {
        String arr[] = request.getParameter("registrationid").split("~@~");
        String registrationid = arr[0];
        List retList = new ArrayList();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        try {
            retList = (List<Object[]>) daoFactory.getPhdSelfcourseRegistrationDAO().getPHDSubjectRegistrationDataApprove(instituteid, registrationid, "A");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List getCanclledData(HttpServletRequest request) {
        String arr[] = request.getParameter("registrationid").split("~@~");
        String registrationid = arr[0];
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        List retList = new ArrayList();
        try {
            retList = (List<Object[]>) daoFactory.getPhdSelfcourseRegistrationDAO().getPHDSubjectRegistrationDataCancelled(instituteid, registrationid, "C");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return retList;
    }

    public List getSubjectReportData(HttpServletRequest request) {
        String arr[] = request.getParameter("registrationid").split("~@~");
        String registrationid = arr[0];
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        List list = new ArrayList();
        try {
            list = (List<Object[]>) daoFactory.getPhdSelfcourseRegistrationDAO().getSubjectWiseReportData(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public List savePendingForApprove_Approve(HttpServletRequest request) {
        List err = null;
        String reg_arr[] = request.getParameter("regCode").split("~@~");
        String registrationid = reg_arr[0];
        String status = request.getParameter("status");
        String subjectid = "";
        String subCode_descid = "";
        String studentid = "";
        String fstid = "";
        String studentfstid = "";
        String acadyear = "";
        String programid = "";
        String sectionid = "";
        String subsectionid = "";
        String basketid = "";
        String stynumber = "";
        String credits = "";
        String subtypeid = "";
        String departmentid = "";
        List<Object[]> subComponentidList = null;
        List pstList = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        FacultySubjectTagging facultySubjectTagging = null;
        FacultySubjectTaggingId facultySubjectTaggingId = null;
        FacultyStudentTagging f_StudentTagging = null;
        FacultyStudentTaggingId f_StudentTaggingId = null;
        byte styno = new Byte("0");
        Map subjectComponentMap = new HashMap();
        Map basketMap = new HashMap();
        Map pstMap = new HashMap();
        Map fstMap = new HashMap();
        List saveList = new ArrayList();
        List deletelist = new ArrayList();
        List updateList = new ArrayList();
        List<Object[]> basketList = new ArrayList();
        String qryString = null;
        int count_A = 0;
        int count_C = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (status.equals("A")) {
            count_A = Integer.parseInt(request.getParameter("checkedCount1"));
        } else {
            count_C = Integer.parseInt(request.getParameter("checkedCount2"));
        }
        String entryby = jirpession.getJsessionInfo().getMemberid();
        BusinessService businessService = new BusinessService(daoFactory,true);
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String stytypeid = daoFactory.getStyTypeDAO().getStytypeId(instituteid, "REG");
        String unique_id = jirpession.getJsessionInfo().getSelectedinstituteuniqueid();
        try {
            if ("A".equalsIgnoreCase(status)) {
                String depterrormsg = "";
                String comperrormsg = "";
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk1" + i + "") != null) {
                        String pks[] = request.getParameter("chk1" + i + "").split("~@~");
                        departmentid = pks[8];
                        subCode_descid = pks[2];
                        subjectid = pks[3];
                        if (departmentid == null || departmentid.equals("null")) {
                            depterrormsg = depterrormsg + subCode_descid.toString().split(":")[0] + ", ";
                        }
                        subComponentidList = (List<Object[]>) daoFactory.getSubjectComponentDAO().getComponentIdOfSubject(instituteid, (subjectid == null ? null : subjectid.toString()));
                        if (subComponentidList == null || subComponentidList.isEmpty()) {
                            comperrormsg = comperrormsg + subCode_descid.toString().split(":")[0] + ", ";
                        } else {
                            subjectComponentMap.put(subjectid.toString(), subComponentidList);
                        }
                    }
                }
                if (!"".equalsIgnoreCase(depterrormsg) || !"".equalsIgnoreCase(comperrormsg)) {
                    err = new ArrayList();
                    err.add("Fail To Approve");
                    err.add((!"".equalsIgnoreCase(depterrormsg) ? (depterrormsg + " Subjects Department Tagging Not Found ") : "") + (!"".equalsIgnoreCase(comperrormsg) ? (comperrormsg + " Subject Componemt Details Not Found.") : ""));
                    return err;
                }
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk1" + i + "") != null) {
                        String pks[] = request.getParameter("chk1" + i + "").split("~@~");
                        qryString = "";
                        studentid = (pks[0] == null ? null : pks[0].toString());
                        subjectid = (pks[3] == null ? null : pks[3].toString());
                        acadyear = (pks[4] == null ? null : pks[4].toString());
                        programid = (pks[5] == null ? null : pks[5].toString());
                        sectionid = (pks[6] == null ? null : pks[6].toString());
                        subsectionid = (pks[7] == null ? null : pks[7].toString());
                        basketid = (pks[9] == null ? null : pks[9].toString());
                        departmentid = (pks[8] == null ? null : pks[8].toString());
                        stynumber = (pks[1] == null ? null : pks[1].toString());
                        subtypeid = (pks[10] == null ? null : pks[10].toString());
                        credits = (pks[11] == null ? null : pks[11].toString());
                        styno = new Byte(stynumber);
                        if (!basketMap.containsKey(instituteid + programid + sectionid + Integer.parseInt(stynumber)) && basketid == null) {
                            basketList = (List<Object[]>) daoFactory.getBasketMasterDAO().getBasketCode(instituteid, programid, sectionid, Byte.decode(stynumber));
                            if (basketList == null || basketList.isEmpty()) {
                                BasketMaster basketMaster = new BasketMaster();
                                BasketMasterId basketMasterId = new BasketMasterId();
                                basketMasterId.setInstituteid(instituteid);
                                basketid = businessService.generateId("BasketID", unique_id, "I", false);
                                basketMasterId.setBasketid(basketid);
                                basketMaster.setId(basketMasterId);
                                basketMaster.setBasketcode("A");
                                basketMaster.setBasketdesc("For Core Subjecs");
                                basketMaster.setDeactive("N");
                                basketMaster.setMaxsubject((short) 0);
                                basketMaster.setMinsubject((short) 0);
                                basketMaster.setOptional("N");
                                basketMaster.setPreferencetype("C");
                                basketMaster.setProgramid(programid);
                                basketMaster.setSectionid(sectionid);
                                basketMaster.setStynumber(styno);
                                basketMaster.setSubjecttypeid(subtypeid == null ? null : subtypeid.toString());
                                basketMaster.setTotalsubject((short) 0);
                                saveList.add(basketMaster);
                                basketMap.put(instituteid + programid + sectionid + Integer.parseInt(stynumber), basketid);
                            } else {
                                basketid = basketList.get(0)[0].toString();
                                basketMap.put(instituteid + programid + sectionid + Integer.parseInt(stynumber), basketid);
                            }
                        } else {
                            if (basketid == null || basketid.equals("null")) {
                                if (basketMap.containsKey(instituteid + programid + sectionid + Integer.parseInt(stynumber))) {
                                    basketid = basketMap.get(instituteid + programid + sectionid + Integer.parseInt(stynumber)).toString();
                                }
                            }
                        }

                        subComponentidList = (List<Object[]>) subjectComponentMap.get(subjectid);

                        if (!pstMap.containsKey(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber)) {
                            pstList = daoFactory.getProgramSubjectDetailDAO().getSubjectComponentIds(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, null, basketid, stynumber);
                            if (pstList == null || pstList.isEmpty()) {
                                programSubjectTagging = new ProgramSubjectTagging();
                                programSubjectTaggingId = new ProgramSubjectTaggingId();
                                programSubjectDetail = new ProgramSubjectDetail();
                                programSubjectDetailId = new ProgramSubjectDetailId();
                                programSubjectTaggingId.setInstituteid(instituteid);
                                programSubjectTaggingId.setRegistrationid(registrationid);
                                String programsubjectid = businessService.generateId("ProgramSubjectID", unique_id, "I", false);
                                programSubjectTaggingId.setProgramsubjectid(programsubjectid);
                                programSubjectTagging.setId(programSubjectTaggingId);
                                programSubjectTagging.setAcademicyear(acadyear);
                                programSubjectTagging.setProgramid(programid);
                                programSubjectTagging.setSectionid(sectionid);
                                programSubjectTagging.setStynumber(new Short(stynumber));
                                programSubjectTagging.setBasketid(basketid);
                                programSubjectTagging.setSubjectid(subjectid);
                                programSubjectTagging.setPstpopulated("N");
                                programSubjectTagging.setSubjectrunning("Y");
                                programSubjectTagging.setCustomfinalized("N");
                                programSubjectTagging.setDepartmentid(departmentid);
                                programSubjectTagging.setCredits(Double.parseDouble(credits));
                                programSubjectTagging.setDeactive("N");

                                err = daoFactory.getProgramSubjectTaggingDAO().doValidate(programSubjectTagging, "normal");
                                if (err.size() > 0) {
                                    businessService.rollback();
                                    return err;
                                }
                                saveList.add(programSubjectTagging);
                                if (subComponentidList != null) {
                                    for (int l = 0; l < subComponentidList.size(); l++) {
                                        programSubjectDetail = new ProgramSubjectDetail();
                                        programSubjectDetailId = new ProgramSubjectDetailId();
                                        programSubjectDetailId.setInstituteid(instituteid);
                                        programSubjectDetailId.setProgramsubjectid(programsubjectid);
                                        programSubjectDetailId.setRegistrationid(registrationid);
                                        programSubjectDetailId.setSubjectcomponentid(subComponentidList.get(l)[0].toString());
                                        programSubjectDetail.setId(programSubjectDetailId);

                                        programSubjectDetail.setLtppassingmarks(subComponentidList.get(l)[3] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[3]).toString()));
                                        programSubjectDetail.setTotalccpmarks(subComponentidList.get(l)[4] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[4]).toString()));
                                        programSubjectDetail.setNoofhours(subComponentidList.get(l)[5] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[5]).toString()));
                                        programSubjectDetail.setNoofclassinaweek(subComponentidList.get(l)[6] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[6]).toString()));
                                        programSubjectDetail.setTotalclasses(subComponentidList.get(l)[7] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[7]).toString()));
                                        programSubjectDetail.setDeactive("N");
                                        saveList.add(programSubjectDetail);
                                    }
                                }
                                pstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber, true);
                            } else {
                                pstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber, true);
                            }
                        }
                        if (subComponentidList != null) {
                            for (int j = 0; j < subComponentidList.size(); j++) {
                                if (fstMap.containsKey(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid)) {
                                    fstid = fstMap.get(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid).toString();

                                    f_StudentTagging = new FacultyStudentTagging();
                                    f_StudentTaggingId = new FacultyStudentTaggingId();
                                    studentfstid = businessService.generateId("SudentFSTID", unique_id, "I", false);
                                    f_StudentTaggingId.setInstituteid(instituteid);
                                    f_StudentTaggingId.setStudentfstid(studentfstid);
                                    f_StudentTaggingId.setStudentid(studentid);
                                    f_StudentTagging.setId(f_StudentTaggingId);
                                    f_StudentTagging.setFstid(fstid);
                                    f_StudentTagging.setEntrydate(new Date());
                                    f_StudentTagging.setEntryby(entryby);
                                    f_StudentTagging.setDeactive("N");
                                    saveList.add(f_StudentTagging);
                                    break;
                                }
                                List fstidList = daoFactory.getFacultySubjectTaggingDAO().checkIfFSTIDExists(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, styno, subComponentidList.get(j)[0].toString(), stytypeid);
                                if (fstidList == null || fstidList.isEmpty()) {
                                    facultySubjectTagging = new FacultySubjectTagging();
                                    facultySubjectTaggingId = new FacultySubjectTaggingId();
                                    fstid = businessService.generateId("FSTID", unique_id, "I", false);
                                    facultySubjectTaggingId.setFstid(fstid);
                                    facultySubjectTaggingId.setInstituteid(instituteid);
                                    facultySubjectTagging.setId(facultySubjectTaggingId);
                                    facultySubjectTagging.setStytypeid(stytypeid);
                                    facultySubjectTagging.setAcademicyear(acadyear);
                                    facultySubjectTagging.setBasketid(basketid);
                                    facultySubjectTagging.setDeactive("N");
                                    facultySubjectTagging.setProgramid(programid);
                                    facultySubjectTagging.setRegistrationid(registrationid);
                                    facultySubjectTagging.setSectionid(sectionid);
                                    facultySubjectTagging.setStynumber(styno);
                                    facultySubjectTagging.setSubjectcomponentid(subComponentidList.get(j)[0].toString());
                                    facultySubjectTagging.setSubjectid(subjectid);
                                    facultySubjectTagging.setSubsectionid(subsectionid);
                                    facultySubjectTagging.setTtreferenceid("");
                                    fstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid, fstid);
                                    saveList.add(facultySubjectTagging);
                                } else {
                                    fstid = fstidList.get(0).toString();
                                    fstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid, fstid);
                                }
                                f_StudentTagging = new FacultyStudentTagging();
                                f_StudentTaggingId = new FacultyStudentTaggingId();
                                studentfstid = businessService.generateId("SudentFSTID", unique_id, "I", false);
                                f_StudentTaggingId.setInstituteid(instituteid);
                                f_StudentTaggingId.setStudentfstid(studentfstid);
                                f_StudentTaggingId.setStudentid(studentid);
                                f_StudentTagging.setId(f_StudentTaggingId);
                                f_StudentTagging.setFstid(fstid);
                                f_StudentTagging.setEntrydate(new Date());
                                f_StudentTagging.setEntryby(entryby);
                                f_StudentTagging.setDeactive("N");
                                saveList.add(f_StudentTagging);
                            }
                        }
                    }
                }
                businessService.insertInIdGenerationControl(saveList);
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk1" + i + "") != null) {
                        String pks[] = request.getParameter("chk1" + i + "").split("~@~");
                        studentid = (pks[0] == null ? null : pks[0].toString());
                        subjectid = (pks[3] == null ? null : pks[3].toString());
                        acadyear = (pks[4] == null ? null : pks[4].toString());
                        programid = (pks[5] == null ? null : pks[5].toString());
                        sectionid = (pks[6] == null ? null : pks[6].toString());
                        subsectionid = (pks[7] == null ? null : pks[7].toString());
                        basketid = (pks[9] == null ? null : pks[9].toString());
                        departmentid = (pks[8] == null ? null : pks[8].toString());
                        stynumber = (pks[1] == null ? null : pks[1].toString());
                        subtypeid = (pks[10] == null ? null : pks[10].toString());
                        credits = (pks[11] == null ? null : pks[11].toString());
                        styno = new Byte(stynumber);
                        qryString = "Update PhdSelfcourseRegistration sscm set sscm.status ='A', sscm.approvalby='" + entryby + "' ,sscm.approvaldate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "'  and sscm.id.subjectid ='" + subjectid + "'";
                        updateList.add(qryString);
                    }
                }
                daoFactory.getStudentSubjectChoiceMasterDAO().update_STR(updateList);
                err = new ArrayList<String>();
                err.add("Success");
            } else {
                for (int i = 0; i <= count_C; i++) {
                    if (request.getParameter("chk2" + i + "") != null) {
                        String pks[] = request.getParameter("chk2" + i + "").split("~@~");
                        qryString = "";
                        studentid = (pks[0] == null ? null : pks[0].toString());
                        subjectid = (pks[3] == null ? null : pks[3].toString());
                        acadyear = (pks[4] == null ? null : pks[4].toString());
                        programid = (pks[5] == null ? null : pks[5].toString());
                        sectionid = (pks[6] == null ? null : pks[6].toString());
                        subsectionid = (pks[7] == null ? null : pks[7].toString());
                        basketid = (pks[9] == null ? null : pks[9].toString());
                        departmentid = (pks[8] == null ? null : pks[8].toString());
                        stynumber = (pks[1] == null ? null : pks[1].toString());
                        subtypeid = (pks[10] == null ? null : pks[10].toString());
                        credits = (pks[11] == null ? null : pks[11].toString());
                        styno = new Byte(stynumber);
                        List list1 = new ArrayList();
                        list1 = daoFactory.getProgramSubjectDetailDAO().getSubjectComponentIds(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, stytypeid, basketid, stynumber);

                        if (list1 != null && !list1.isEmpty()) {
                            for (int j = 0; j < list1.size(); j++) {
                                String componentid = list1.get(0).toString();
                                List fstidList = daoFactory.getFacultySubjectTaggingDAO().checkIfFSTIDExists(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, styno, componentid, stytypeid);
                                if (fstidList != null && !fstidList.isEmpty()) {
                                    for (int k = 0; k < fstidList.size(); k++) {
                                        List fstudentList = (List) daoFactory.getFacultyStudentTaggingDAO().find("from FacultyStudentTagging fst where fst.fstid='" + fstidList.get(k).toString() + "' and fst.id.instituteid='" + instituteid + "' and fst.id.studentid='" + studentid + "' ");
                                        if (fstudentList != null && !fstudentList.isEmpty()) {
                                            for (int l = 0; l < fstudentList.size(); l++) {
                                                deletelist.add(fstudentList.get(l));
                                            }
                                        } else {
                                        }
                                    }
                                }
                            }
                        } else {
                        }
                    }
                    businessService.insertInIdGenerationControl_STRNew(new ArrayList(), new ArrayList(), deletelist);
                    for (i = 0; i <= count_C; i++) {
                        if (request.getParameter("chk2" + i + "") != null) {
                            String pks[] = request.getParameter("chk2" + i + "").split("~@~");
                            qryString = "";
                            studentid = (pks[0] == null ? null : pks[0].toString());
                            subjectid = (pks[3] == null ? null : pks[3].toString());
                            acadyear = (pks[4] == null ? null : pks[4].toString());
                            programid = (pks[5] == null ? null : pks[5].toString());
                            sectionid = (pks[6] == null ? null : pks[6].toString());
                            subsectionid = (pks[7] == null ? null : pks[7].toString());
                            basketid = (pks[9] == null ? null : pks[9].toString());
                            departmentid = (pks[8] == null ? null : pks[8].toString());
                            stynumber = (pks[1] == null ? null : pks[1].toString());
                            subtypeid = (pks[10] == null ? null : pks[10].toString());
                            credits = (pks[11] == null ? null : pks[11].toString());
                            styno = new Byte(stynumber);
                            qryString = "Update PhdSelfcourseRegistration sscm set sscm.status ='C', sscm.approvalby='" + entryby + "' ,sscm.approvaldate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "' and sscm.id.subjectid ='" + subjectid + "'";
                            updateList.add(qryString);

                        }
                    }
                    daoFactory.getStudentSubjectChoiceMasterDAO().update_STR(updateList);
                }
                err = new ArrayList<String>();
                err.add("Success");
            }
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
//            err.add("Error In Processing !!!");
            return err;
        } finally {
            status = null;
            registrationid = null;
            subjectid = null;
            fstid = null;
            studentfstid = null;
            acadyear = null;
            programid = null;
            sectionid = null;
            subsectionid = null;
            stytypeid = null;
            basketid = null;
            stynumber = null;
            subComponentidList = null;
            instituteid = null;
            saveList = null;
            facultySubjectTagging = null;
            facultySubjectTaggingId = null;
            f_StudentTagging = null;
            f_StudentTaggingId = null;
            programSubjectTagging = null;
            programSubjectTaggingId = null;
            programSubjectDetail = null;
            programSubjectDetailId = null;
            businessService.closeSession();
        }
        return err;

    }

    public List saveCancelledData(HttpServletRequest request) {
        List err = null;
        String reg_arr[] = request.getParameter("regCode").split("~@~");
        String registrationid = reg_arr[0];
        String status = request.getParameter("status");
        String subjectid = "";
        String subCode_descid = "";
        String studentid = "";
        String fstid = "";
        String studentfstid = "";
        String acadyear = "";
        String programid = "";
        String sectionid = "";
        String subsectionid = "";
        String basketid = "";
        String stynumber = "";
        String credits = "";
        String subtypeid = "";
        String departmentid = "";
        List<Object[]> subComponentidList = null;
        List pstList = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        FacultySubjectTagging facultySubjectTagging = null;
        FacultySubjectTaggingId facultySubjectTaggingId = null;
        FacultyStudentTagging f_StudentTagging = null;
        FacultyStudentTaggingId f_StudentTaggingId = null;
        byte styno = new Byte("0");
        Map subjectComponentMap = new HashMap();
        Map basketMap = new HashMap();
        Map pstMap = new HashMap();
        Map fstMap = new HashMap();
        List saveList = new ArrayList();
        List deletelist = new ArrayList();
        List updateList = new ArrayList();
        List<Object[]> basketList = new ArrayList();
        String qryString = null;
        int count_A = 0;
        int count_C = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if (status.equals("A")) {
            count_A = Integer.parseInt(request.getParameter("checkedCount3"));
        }
        String entryby = jirpession.getJsessionInfo().getMemberid();
        BusinessService businessService = new BusinessService(daoFactory);
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String stytypeid = daoFactory.getStyTypeDAO().getStytypeId(instituteid, "REG");
        String unique_id = jirpession.getJsessionInfo().getSelectedinstituteuniqueid();
        try {
            if ("A".equalsIgnoreCase(status)) {
                String depterrormsg = "";
                String comperrormsg = "";
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk3" + i + "") != null) {
                        String pks[] = request.getParameter("chk3" + i + "").split("~@~");
                        departmentid = pks[8];
                        subCode_descid = pks[2];
                        subjectid = pks[3];
                        if (departmentid == null || departmentid.equals("null")) {
                            depterrormsg = depterrormsg + subCode_descid.toString().split(":")[0] + ", ";
                        }
                        subComponentidList = (List<Object[]>) daoFactory.getSubjectComponentDAO().getComponentIdOfSubject(instituteid, (subjectid == null ? null : subjectid.toString()));
                        if (subComponentidList == null || subComponentidList.isEmpty()) {
                            comperrormsg = comperrormsg + subCode_descid.toString().split(":")[0] + ", ";
                        } else {
                            subjectComponentMap.put(subjectid.toString(), subComponentidList);
                        }
                    }
                }
                if (!"".equalsIgnoreCase(depterrormsg) || !"".equalsIgnoreCase(comperrormsg)) {
                    err = new ArrayList();
                    err.add("fail");
                    err.add((!"".equalsIgnoreCase(depterrormsg) ? (depterrormsg + " Subjects DepartMent Tagging Not Found ") : "") + (!"".equalsIgnoreCase(comperrormsg) ? (comperrormsg + " Subject Componemt Details Not Found.") : ""));
                    return err;
                }
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk3" + i + "") != null) {
                        String pks[] = request.getParameter("chk3" + i + "").split("~@~");
                        qryString = "";
                        studentid = (pks[0] == null ? null : pks[0].toString());
                        subjectid = (pks[3] == null ? null : pks[3].toString());
                        acadyear = (pks[4] == null ? null : pks[4].toString());
                        programid = (pks[5] == null ? null : pks[5].toString());
                        sectionid = (pks[6] == null ? null : pks[6].toString());
                        subsectionid = (pks[7] == null ? null : pks[7].toString());
                        basketid = (pks[9] == null ? null : pks[9].toString());
                        departmentid = (pks[8] == null ? null : pks[8].toString());
                        stynumber = (pks[1] == null ? null : pks[1].toString());
                        subtypeid = (pks[10] == null ? null : pks[10].toString());
                        credits = (pks[11] == null ? null : pks[11].toString());
                        styno = new Byte(stynumber);
                        if (!basketMap.containsKey(instituteid + programid + sectionid + Integer.parseInt(stynumber)) && basketid == null) {
                            basketList = (List<Object[]>) daoFactory.getBasketMasterDAO().getBasketCode(instituteid, programid, sectionid, Byte.decode(stynumber));
                            if (basketList == null || basketList.isEmpty()) {
                                BasketMaster basketMaster = new BasketMaster();
                                BasketMasterId basketMasterId = new BasketMasterId();
                                basketMasterId.setInstituteid(instituteid);
                                basketid = businessService.generateId("BasketID", unique_id, "I", false);
                                basketMasterId.setBasketid(basketid);
                                basketMaster.setId(basketMasterId);
                                basketMaster.setBasketcode("A");
                                basketMaster.setBasketdesc("For Core Subjecs");
                                basketMaster.setDeactive("N");
                                basketMaster.setMaxsubject((short) 0);
                                basketMaster.setMinsubject((short) 0);
                                basketMaster.setOptional("N");
                                basketMaster.setPreferencetype("C");
                                basketMaster.setProgramid(programid);
                                basketMaster.setSectionid(sectionid);
                                basketMaster.setStynumber(styno);
                                basketMaster.setSubjecttypeid(subtypeid == null ? null : subtypeid.toString());
                                basketMaster.setTotalsubject((short) 0);
                                saveList.add(basketMaster);
                                basketMap.put(instituteid + programid + sectionid + Integer.parseInt(stynumber), basketid);
                            } else {
                                basketid = basketList.get(0)[0].toString();
                                basketMap.put(instituteid + programid + sectionid + Integer.parseInt(stynumber), basketid);
                            }
                        } else {
                            if (basketid == null) {
                                if (basketMap.containsKey(instituteid + programid + sectionid + Integer.parseInt(stynumber))) {
                                    basketid = basketMap.get(instituteid + programid + sectionid + Integer.parseInt(stynumber)).toString();
                                }
                            }
                        }

                        subComponentidList = (List<Object[]>) subjectComponentMap.get(subjectid);

                        if (!pstMap.containsKey(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber)) {
                            pstList = daoFactory.getProgramSubjectDetailDAO().getSubjectComponentIds(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, null, basketid, stynumber);
                            if (pstList == null || pstList.isEmpty()) {
                                programSubjectTagging = new ProgramSubjectTagging();
                                programSubjectTaggingId = new ProgramSubjectTaggingId();
                                programSubjectDetail = new ProgramSubjectDetail();
                                programSubjectDetailId = new ProgramSubjectDetailId();
                                programSubjectTaggingId.setInstituteid(instituteid);
                                programSubjectTaggingId.setRegistrationid(registrationid);
                                String programsubjectid = businessService.generateId("ProgramSubjectID", unique_id, "I", false);
                                programSubjectTaggingId.setProgramsubjectid(programsubjectid);
                                programSubjectTagging.setId(programSubjectTaggingId);
                                programSubjectTagging.setAcademicyear(acadyear);
                                programSubjectTagging.setProgramid(programid);
                                programSubjectTagging.setSectionid(sectionid);
                                programSubjectTagging.setStynumber(new Short(stynumber));
                                programSubjectTagging.setBasketid(basketid);
                                programSubjectTagging.setSubjectid(subjectid);
                                programSubjectTagging.setPstpopulated("N");
                                programSubjectTagging.setSubjectrunning("Y");
                                programSubjectTagging.setCustomfinalized("N");
                                programSubjectTagging.setDepartmentid(departmentid);
                                programSubjectTagging.setCredits(Double.parseDouble(credits));
                                programSubjectTagging.setDeactive("N");

                                err = daoFactory.getProgramSubjectTaggingDAO().doValidate(programSubjectTagging, "normal");
                                if (err.size() > 0) {
                                    businessService.rollback();
                                    return err;
                                }
                                saveList.add(programSubjectTagging);
                                if (subComponentidList != null) {
                                    for (int l = 0; l < subComponentidList.size(); l++) {
                                        programSubjectDetail = new ProgramSubjectDetail();
                                        programSubjectDetailId = new ProgramSubjectDetailId();
                                        programSubjectDetailId.setInstituteid(instituteid);
                                        programSubjectDetailId.setProgramsubjectid(programsubjectid);
                                        programSubjectDetailId.setRegistrationid(registrationid);
                                        programSubjectDetailId.setSubjectcomponentid(subComponentidList.get(l)[0].toString());
                                        programSubjectDetail.setId(programSubjectDetailId);

                                        programSubjectDetail.setLtppassingmarks(subComponentidList.get(l)[3] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[3]).toString()));
                                        programSubjectDetail.setTotalccpmarks(subComponentidList.get(l)[4] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[4]).toString()));
                                        programSubjectDetail.setNoofhours(subComponentidList.get(l)[5] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[5]).toString()));
                                        programSubjectDetail.setNoofclassinaweek(subComponentidList.get(l)[6] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[6]).toString()));
                                        programSubjectDetail.setTotalclasses(subComponentidList.get(l)[7] == null ? new BigDecimal("0") : new BigDecimal((subComponentidList.get(l)[7]).toString()));
                                        programSubjectDetail.setDeactive("N");
                                        saveList.add(programSubjectDetail);
                                    }
                                }
                                pstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber, true);
                            } else {
                                pstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + basketid + stynumber, true);
                            }
                        }
                        if (subComponentidList != null) {
                            for (int j = 0; j < subComponentidList.size(); j++) {
                                if (fstMap.containsKey(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid)) {
                                    fstid = fstMap.get(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid).toString();

                                    f_StudentTagging = new FacultyStudentTagging();
                                    f_StudentTaggingId = new FacultyStudentTaggingId();
                                    studentfstid = businessService.generateId("SudentFSTID", unique_id, "I", false);
                                    f_StudentTaggingId.setInstituteid(instituteid);
                                    f_StudentTaggingId.setStudentfstid(studentfstid);
                                    f_StudentTaggingId.setStudentid(studentid);
                                    f_StudentTagging.setId(f_StudentTaggingId);
                                    f_StudentTagging.setFstid(fstid);
                                    f_StudentTagging.setEntrydate(new Date());
                                    f_StudentTagging.setEntryby(entryby);
                                    f_StudentTagging.setDeactive("N");
                                    saveList.add(f_StudentTagging);
                                    break;
                                }
                                List fstidList = daoFactory.getFacultySubjectTaggingDAO().checkIfFSTIDExists(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, styno, subComponentidList.get(j)[0].toString(), stytypeid);
                                if (fstidList == null || fstidList.isEmpty()) {
                                    facultySubjectTagging = new FacultySubjectTagging();
                                    facultySubjectTaggingId = new FacultySubjectTaggingId();
                                    fstid = businessService.generateId("FSTID", unique_id, "I", false);
                                    facultySubjectTaggingId.setFstid(fstid);
                                    facultySubjectTaggingId.setInstituteid(instituteid);
                                    facultySubjectTagging.setId(facultySubjectTaggingId);
                                    facultySubjectTagging.setStytypeid(stytypeid);
                                    facultySubjectTagging.setAcademicyear(acadyear);
                                    facultySubjectTagging.setBasketid(basketid);
                                    facultySubjectTagging.setDeactive("N");
                                    facultySubjectTagging.setProgramid(programid);
                                    facultySubjectTagging.setRegistrationid(registrationid);
                                    facultySubjectTagging.setSectionid(sectionid);
                                    facultySubjectTagging.setStynumber(styno);
                                    facultySubjectTagging.setSubjectcomponentid(subComponentidList.get(j)[0].toString());
                                    facultySubjectTagging.setSubjectid(subjectid);
                                    facultySubjectTagging.setSubsectionid(subsectionid);
                                    facultySubjectTagging.setTtreferenceid("");
                                    fstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid, fstid);
                                    saveList.add(facultySubjectTagging);
                                } else {
                                    fstid = fstidList.get(0).toString();
                                    fstMap.put(instituteid + registrationid + subjectid + acadyear + programid + sectionid + subsectionid + styno + subComponentidList.get(j)[0].toString() + stytypeid, fstid);
                                }
                                f_StudentTagging = new FacultyStudentTagging();
                                f_StudentTaggingId = new FacultyStudentTaggingId();
                                studentfstid = businessService.generateId("SudentFSTID", unique_id, "I", false);
                                f_StudentTaggingId.setInstituteid(instituteid);
                                f_StudentTaggingId.setStudentfstid(studentfstid);
                                f_StudentTaggingId.setStudentid(studentid);
                                f_StudentTagging.setId(f_StudentTaggingId);
                                f_StudentTagging.setFstid(fstid);
                                f_StudentTagging.setEntrydate(new Date());
                                f_StudentTagging.setEntryby(entryby);
                                f_StudentTagging.setDeactive("N");

                                saveList.add(f_StudentTagging);
                            }
                        }
                    }
                }
                businessService.insertInIdGenerationControl(saveList);
                for (int i = 0; i <= count_A; i++) {
                    if (request.getParameter("chk3" + i + "") != null) {
                        String pks[] = request.getParameter("chk3" + i + "").split("~@~"); studentid = (pks[0] == null ? null : pks[0].toString());
                        studentid = (pks[0] == null ? null : pks[0].toString());
                        subjectid = (pks[3] == null ? null : pks[3].toString());
                        acadyear = (pks[4] == null ? null : pks[4].toString());
                        programid = (pks[5] == null ? null : pks[5].toString());
                        sectionid = (pks[6] == null ? null : pks[6].toString());
                        subsectionid = (pks[7] == null ? null : pks[7].toString());
                        basketid = (pks[9] == null ? null : pks[9].toString());
                        departmentid = (pks[8] == null ? null : pks[8].toString());
                        stynumber = (pks[1] == null ? null : pks[1].toString());
                        subtypeid = (pks[10] == null ? null : pks[10].toString());
                        credits = (pks[11] == null ? null : pks[11].toString());
                        qryString = "Update PhdSelfcourseRegistration sscm set sscm.status ='A', sscm.approvalby='" + entryby + "' ,sscm.approvaldate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "'  and sscm.id.subjectid ='" + subjectid + "'";
                        updateList.add(qryString);
                    }
                }
                daoFactory.getStudentSubjectChoiceMasterDAO().update_STR(updateList);
                err = new ArrayList<String>();
                err.add("Success");
            }
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace(); 
            err = new ArrayList<String>();
            err.add("Error");
//            err.add("Error In Processing !!!");
            return err;
        } finally {
            status = null;
            registrationid = null;
            subjectid = null;
            fstid = null;
            studentfstid = null;
            acadyear = null;
            programid = null;
            sectionid = null;
            subsectionid = null;
            stytypeid = null;
            basketid = null;
            stynumber = null;
            subComponentidList = null;
            instituteid = null;
            saveList = null;
            facultySubjectTagging = null;
            facultySubjectTaggingId = null;
            f_StudentTagging = null;
            f_StudentTaggingId = null;
            programSubjectTagging = null;
            programSubjectTaggingId = null;
            programSubjectDetail = null;
            programSubjectDetailId = null;
            businessService.closeSession();
        }
        return err;

    }

}
