/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.StudentRegistrationPermissionIService;
import com.jilit.irp.util.JIRPSession;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.RegistrationMasterId;
import com.jilit.irp.util.JIRPDateUtil;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import com.jilit.irp.bso.biz.BusinessService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.jilit.irp.persistence.dto.StudentRegistration_info;
import com.jilit.irp.persistence.dto.StudentRegistration_infoId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mohit1.kumar
 */
@Service
public class StudentRegistrationPermissionService implements StudentRegistrationPermissionIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getSemesterCode(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.put("semCode", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getStudetnInfo(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String enrollmentno = request.getParameter("enrollmentno").trim();
        List list = null;
        try {
            list = (List) daoFactory.getStudentMasterDAO().getStudentInfoForRegPermission(instituteid, enrollmentno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void getStyType(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getStyTypeDAO().getStyTypeData(instituteid);
            model.put("styType", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAcademicYearReg(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearRegList(instituteid);
            model.put("acad", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getFromToDate(HttpServletRequest request) {
        String registrationid = request.getParameter("registrationid");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getRegistrationMasterDAO().getFromToDate(instituteid, registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getProgramCodeReg(HttpServletRequest request) {
        String acad_year = request.getParameter("acad_year");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentMasterDAO().getProgramCodeReg(instituteid, acad_year);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List getSecCodeReg(HttpServletRequest request) {
        String programId = request.getParameter("prgCode");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentMasterDAO().getSecCodeReg(instituteid, programId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List doSubmit(Map criteriaDto, boolean fetchStudentsWithNullPrgBrn, boolean fetchOnlyRegStudents) {
        List<Object[]> studentList = new ArrayList();
        List<Object[]> regList = new ArrayList();
        List newretList = new ArrayList();
        List err = null;
        try {
            String studReg = "";
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = criteriaDto.get("acadYear").toString();
            String progId = criteriaDto.get("prgCode").toString();
            String secId = criteriaDto.get("branchCode").toString();
            String regId = criteriaDto.get("registrationid").toString();
            String indBulkFlag = criteriaDto.get("indBulk").toString();
            String checkboxval = criteriaDto.get("newReg").toString();
            String studentId = "";
            if (indBulkFlag.equals("I")) {
                studentId = criteriaDto.get("studentid").toString().trim();
                List list1 = daoFactory.getV_StudentComponentDetailDAO().checkIfStudentExists(instituteId, regId, studentId);
                if (list1 != null && list1.size() > 0) {
                    err = new ArrayList();
                    err.add("A");
                    return err;
                }
                List list2 = daoFactory.getStudentSubjectChoiceMasterDAO().checkIfStudentExists(instituteId, regId, studentId);
                if (list2 != null && list2.size() > 0) {
                    err = new ArrayList();
                    err.add("B");
                    return err;
                }
                List list3 = daoFactory.getStudentRegistrationDAO().checkIfStudentExists(instituteId, regId, studentId);
                if (list3 != null && list3.size() > 0) {
                    err = new ArrayList();
                    err.add("C");
                    return err;
                }
            }

            if (checkboxval.equals("Y")) {
                studentList = daoFactory.getStudentRegistrationDAO().fetchStudentDataForNewReg(instituteId, acadYear, progId, secId, indBulkFlag, studentId);
                regList = daoFactory.getStudentRegistrationDAO().getRegAllDataForNewReg(instituteId, regId, acadYear, progId, secId);

            } else {
                studentList = daoFactory.getStudentRegistrationDAO().fetchStudentData(instituteId, acadYear, progId, secId, indBulkFlag, studentId);
                regList = daoFactory.getStudentRegistrationDAO().getRegAllData(instituteId, regId, acadYear, progId, secId);
            }
            String str = "";
            if (studentList.size() > 0) {
                for (int i = 0; i < studentList.size(); i++) {
                    List retList = new ArrayList();
                    if (regList.size() > 0) {
                        int count = 0;
                        boolean nodfound = true;
                        for (int j = 0; j < regList.size(); j++) {
                            if (studentList.get(i)[1].toString().equals(regList.get(j)[2].toString())) {   //equating studentid of both the table
                                retList.add(studentList.get(i)[2] == null ? "" : studentList.get(i)[2].toString());//enrollment
                                retList.add(studentList.get(i)[4]);//rank
                                retList.add(studentList.get(i)[3]);//name
                                retList.add(studentList.get(i)[11]);//prog
                                retList.add(studentList.get(i)[12] == null ? "--" : studentList.get(i)[12]);//sec
                                retList.add(regList.get(j)[7]);//reg allow date
                                retList.add(regList.get(j)[3]);//semester
                                retList.add(regList.get(j)[5] == null ? "--" : regList.get(j)[5]);//pre event from date
                                retList.add(regList.get(j)[6] == null ? "--" : regList.get(j)[6]);//pre event to date
                                retList.add(regList.get(j)[9]);//styType
                                retList.add(regList.get(j)[4]);//regAllow
                                retList.add(regList.get(j)[8]);//remarks
                                retList.add(studentList.get(i)[1]);//studentid
                                retList.add(regList.get(j)[1]);//regID
                                retList.add(regList.get(j)[4].equals("Y") ? "ALLOW" : "NOT ALLOW");//);//status
                                retList.add("E");//If Existing Record found
                                retList.add(studentList.get(i)[13]);//student quota
                                retList.add(studentList.get(i)[6]);//academic year
                                retList.add(studentList.get(i)[5]);//programid
                                retList.add(studentList.get(i)[7]);//branchid
                                retList.add(studentList.get(i)[8]);//sectionid
                                retList.add(studentList.get(i)[9]);//sub-sectionid
                                retList.add(studentList.get(i)[14]);//student Lateralentry
                                nodfound = false;
                                break;
                            }
                        }
                        if (nodfound) {
                            count++;

                            try {
                                studReg = criteriaDto.get("regSem").toString();
                                if ("Y".equals(studReg)) {
                                    Byte sty = new Byte(studentList.get(i)[10].toString());
                                    int styno = new Integer(sty + 1);
                                    String flag = "Y";
                                    str = daoFactory.getStoredProcedureDAO().RegistrationPermited(instituteId, regId, studentList.get(i)[1].toString(), styno, flag);
                                } else {
                                    str = "Y";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (count == 1) {
                                retList.add(studentList.get(i)[2] == null ? "" : studentList.get(i)[2].toString());//enrollment  
                                retList.add(studentList.get(i)[4]);//rank
                                retList.add(studentList.get(i)[3]);//name 
                                retList.add(studentList.get(i)[11]);//prog
                                retList.add(studentList.get(i)[12] == null ? "--" : studentList.get(i)[12]);//sec
                                int styno = 0;
                                if ("Y".equals(studReg)) {
                                    Byte sty = new Byte(studentList.get(i)[10].toString());
                                    styno = new Integer(sty + 1);
                                    String flag = "Y";
                                    str = daoFactory.getStoredProcedureDAO().RegistrationPermited(instituteId, regId, studentList.get(i)[1].toString(), styno, flag);
                                } else {
                                    str = "Y";
                                    Byte sty = new Byte(studentList.get(i)[10].toString());
                                    styno = new Integer(sty);
                                }
                                retList.add("--");//reg allow date
                                retList.add(styno);//semester
                                retList.add("--");//pre event from date
                                retList.add("--");//pre event to date
                                retList.add("--");//styType
                                if ("Y".equals(str)) {
                                    retList.add(str);//regAllow
                                    retList.add("");//remarks
                                } else {
                                    String arr = str.substring(2);
                                    retList.add("N");//regAllow
                                    retList.add(arr);//remarks
                                }
                                retList.add(studentList.get(i)[1]);//studentid
                                retList.add(regId);//
                                studReg = criteriaDto.get("regSem").toString();

                                retList.add(str.equals("Y") ? "ALLOW" : "NOT ALLOW");//status
                                retList.add("N");//If Not Existing Record found
                                retList.add(studentList.get(i)[13]);//student quota
                                retList.add(studentList.get(i)[6]);//academic year
                                retList.add(studentList.get(i)[5]);//programid
                                retList.add(studentList.get(i)[7]);//branchid
                                retList.add(studentList.get(i)[8]);//sectionid
                                retList.add(studentList.get(i)[9]);//sub-sectionid
                                retList.add(studentList.get(i)[14]);//student Lateralentry
                            }
                        }
//                        }
                    } // new added on 1/3/2019 by mohit for the case when reglist is 0
                    else {
                        studReg = criteriaDto.get("regSem").toString();
                        retList.add(studentList.get(i)[2] == null ? "--" : studentList.get(i)[2].toString());//enrollment
                        retList.add(studentList.get(i)[4]);//rank
                        retList.add(studentList.get(i)[3]);//name
                        retList.add(studentList.get(i)[11]);//prog
                        retList.add(studentList.get(i)[12] == null ? "--" : studentList.get(i)[12].toString());//sec
                        int styno = 0;
                        if ("Y".equals(studReg)) {
                            Byte sty = new Byte(studentList.get(i)[10].toString());
                            styno = new Integer(sty + 1);
                            String flag = "Y";
                            str = daoFactory.getStoredProcedureDAO().RegistrationPermited(instituteId, regId, studentList.get(i)[1].toString(), styno, flag);
                        } else {
                            str = "Y";
                            Byte sty = new Byte(studentList.get(i)[10].toString());
                            styno = new Integer(sty);
                        }
                        retList.add("--");//reg allow date
                        retList.add(styno);//semester
                        retList.add("--");//pre event from date
                        retList.add("--");//pre event to date
                        retList.add("--");//styType
                        if ("Y".equals(str)) {
                            retList.add(str);//regAllow
                            retList.add("");//remarks
                        } else {
                            String arr = str.substring(2);
                            retList.add("N");//regAllow
                            retList.add(arr);//remarks
                        }
                        retList.add(studentList.get(i)[1]);//studentid
                        retList.add(regId);//regID
                        studReg = criteriaDto.get("regSem").toString();

                        retList.add(str.equals("Y") ? "ALLOW" : "NOT ALLOW");//status
                        retList.add("N");//If Not Existing Record found
                        retList.add(studentList.get(i)[13]);//student quota
                        retList.add(studentList.get(i)[6]);//academic year
                        retList.add(studentList.get(i)[5]);//programid
                        retList.add(studentList.get(i)[7]);//branchid
                        retList.add(studentList.get(i)[8]);//sectionid
                        retList.add(studentList.get(i)[9]);//sub-sectionids
                        retList.add(studentList.get(i)[14]);//student Lateralentry

                    }
                    newretList.add(retList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newretList;
    }

    @Override
    public List saveAndUpdate(HttpServletRequest request) {
        List err = new ArrayList();
        List saveList = new ArrayList();
        StudentRegistration srObj = null;
        StudentRegistrationId srId = null;
        StudentRegistration_info sriObj = null;
        Set<StudentRegistration_info> set = null;
        StudentRegistration_infoId sriId = null;
        try {
            String styexceedstudentlist = "";
            String nopwsstudentlist = "";
            int count = Integer.parseInt(request.getParameter("count"));
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            int no = 0;
            String instituteid = "";
            String registrationid = "";
            String programid = "";
            String branchid = "";
            String sectionid = "";
            String subsectionid = "";
            String academicyear = "";
            String programtypeid = "";
            String stytype = request.getParameter("styType");
            List stynolist = new ArrayList();
            List stynumberlist = null;
            for (int i = 0; i < count; i++) {
                set = new HashSet<StudentRegistration_info>();
                if (request.getParameter("chk" + i) != null) {
                    String checkVal[] = request.getParameter("chk" + i).split("~@~");
                    String studentId = checkVal[0].trim();
                    String regId = checkVal[1].trim();
                    instituteid = instituteId;
                    registrationid = regId;
                    programid = checkVal[4].trim();
                    branchid = checkVal[5].trim();
                    sectionid = checkVal[7].trim();
                    subsectionid = checkVal[8].trim();
                    academicyear = checkVal[3].trim();
                    srObj = new StudentRegistration();
                    srId = new StudentRegistrationId();
                    srId.setInstituteid(instituteId);
                    srId.setRegistrationid(regId);
                    srId.setStudentid(studentId);
                    srObj.setId(srId);
                    srObj = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(srId);
                    boolean flag = true;
                    if (programid != null) {
                        programtypeid = daoFactory.getStudentRegistrationDAO().getProgramType(instituteId, programid);
                    }
                    if (srObj != null) {
                        srObj.setStytypeid(request.getParameter("styType"));
                        if (request.getParameter("regAllowChk" + i) != null) {
                            srObj.setRegallow("Y");
                            stynolist.add(checkVal[6].trim());
                        } else {
                            srObj.setRegallow("N");
                        }
                        srObj.setRegallowdate(JIRPDateUtil.convertToDateFormat(request.getParameter("regAllowDate"), "dd/MM/yyyy"));
                        srObj.setPreventfrom(JIRPDateUtil.convertToDateFormat(request.getParameter("fromDate"), "dd/MM/yyyy"));
                        srObj.setPreventto(JIRPDateUtil.convertToDateFormat(request.getParameter("toDate"), "dd/MM/yyyy"));
                        String remarks = request.getParameter("remarks");
                        String gridremarks = request.getParameter("gridremarks" + i);
                        srObj.setRemarks(remarks);
                        sriObj = new StudentRegistration_info();
                        sriId = new StudentRegistration_infoId();
                        sriId.setInstituteid(instituteId);
                        sriId.setRegistrationid(regId);
                        sriId.setStudentid(studentId);
                        sriObj.setId(sriId);
                        int maxsty = 0;
                        List<Object[]> list1 = (List<Object[]>) daoFactory.getProgramMaxStyDAO().editProgramMaxStyData(instituteId, programid, checkVal[3].trim(), branchid);
                        Object[] obj = list1.get(0);
                        maxsty = Integer.parseInt(obj[5].toString());
                        Byte ssty = new Byte(checkVal[6].trim());
                        int stynumber = new Integer(ssty);
                        if (stynumberlist == null) {
                            stynumberlist = daoFactory.getProgramWiseSubsectionDAO().getSubSectionList(instituteid, programid, branchid, academicyear, sectionid);
                        }
                        boolean pwssexists = stynumberlist.contains(ssty);
                        if ("N".equals(request.getParameter("regSem")) || ("Y".equals(request.getParameter("regSem")) && pwssexists)) {
                            if (maxsty >= stynumber) {
                                srObj.setQuotaid(checkVal[2].trim());
                                sriObj.setAcademicyear(checkVal[3].trim());
                                sriObj.setProgramid(programid);
                                sriObj.setBranchid(branchid);
                                sriObj.setStynumber((byte) (Integer.parseInt(checkVal[6].trim())));
                                srObj.setStynumber((byte) (Integer.parseInt(checkVal[6].trim())));
                                sriObj.setSectionid(sectionid);
                                sriObj.setSubsectionid(subsectionid);
                                sriObj.setStytypeid(request.getParameter("styType"));
                                sriObj.setProgramtypeid(programtypeid);
                                set.add(sriObj);
                                srObj.setStudentregistration_infos(set);
                                saveList.add(srObj);
                            } else {
                                flag = false;
                                no++;
                                styexceedstudentlist += no + ". " + checkVal[9].trim() + " / " + checkVal[10].trim() + "<br>";
                            }
                        } else {
                            flag = false;
                            no++;
                            nopwsstudentlist += no + ". " + checkVal[9].trim() + " / " + checkVal[10].trim() + "<br>";
                        }
                        if (flag) {
                            daoFactory.getStudentRegistrationDAO().saveOrUpdate(srObj);
                        }
                    } else {
                        srObj = new StudentRegistration();
                        srId = new StudentRegistrationId();
                        srId.setInstituteid(instituteId);
                        srId.setRegistrationid(regId);
                        srId.setStudentid(studentId);
                        srObj.setId(srId);
                        srObj.setStytypeid(request.getParameter("styType"));
                        if (request.getParameter("regAllowChk" + i) != null) {
                            srObj.setRegallow("Y");
                            stynolist.add(checkVal[6].trim());
                        } else {
                            srObj.setRegallow("N");
                        }
                        srObj.setRegallowdate(JIRPDateUtil.convertToDateFormat(request.getParameter("regAllowDate"), "dd/MM/yyyy"));
                        srObj.setPreventfrom(JIRPDateUtil.convertToDateFormat(request.getParameter("fromDate"), "dd/MM/yyyy"));
                        srObj.setPreventto(JIRPDateUtil.convertToDateFormat(request.getParameter("toDate"), "dd/MM/yyyy"));
                        srObj.setRemarks(request.getParameter("remarks"));
                        sriObj = new StudentRegistration_info();
                        sriId = new StudentRegistration_infoId();
                        sriId.setInstituteid(instituteId);
                        sriId.setRegistrationid(regId);
                        sriId.setStudentid(studentId);
                        sriObj.setId(sriId);
                        int maxsty = 0;
                        List<Object[]> list1 = (List<Object[]>) daoFactory.getProgramMaxStyDAO().editProgramMaxStyData(instituteId, programid, checkVal[3].trim(), branchid);
                        Object[] obj = list1.get(0);
                        maxsty = Integer.parseInt(obj[5].toString());
                        Byte ssty = new Byte(checkVal[6].trim());
                        int stynumber = new Integer(ssty);
                        if (stynumberlist == null) {
                            stynumberlist = daoFactory.getProgramWiseSubsectionDAO().getSubSectionList(instituteid, programid, branchid, academicyear, sectionid);
                        }
                        boolean pwssexists = stynumberlist.contains(ssty);
                        if ("N".equals(request.getParameter("regSem")) || ("Y".equals(request.getParameter("regSem")) && pwssexists)) {
                            if (maxsty >= stynumber) {
                                srObj.setQuotaid(checkVal[2].trim());
                                sriObj.setAcademicyear(checkVal[3].trim());
                                sriObj.setProgramid(programid);
                                sriObj.setBranchid(branchid);
                                sriObj.setStynumber((byte) (Integer.parseInt(checkVal[6].trim())));
                                srObj.setStynumber((byte) (Integer.parseInt(checkVal[6].trim())));
                                sriObj.setSectionid(sectionid);
                                sriObj.setSubsectionid(subsectionid);
                                sriObj.setStytypeid(request.getParameter("styType"));
                                sriObj.setProgramtypeid(programtypeid);
                                set.add(sriObj);
                                srObj.setStudentregistration_infos(set);
                                saveList.add(srObj);
                            } else {
                                flag = false;
                                no++;
                                styexceedstudentlist += no + ". " + checkVal[9].trim() + " / " + checkVal[10].trim() + "<br>";
                            }
                        } else {
                            flag = false;
                            no++;
                            nopwsstudentlist += no + ". " + checkVal[9].trim() + " / " + checkVal[10].trim() + "<br>";
                        }
                        if (flag) {
                            daoFactory.getStudentRegistrationDAO().saveOrUpdate(srObj);
                        }
                    }
                }
            }
            //Populate PST automatically checkbox
            if (request.getParameter("ppst") != null) {
                List l = saveProgramSubjectTagging(instituteid, registrationid, programid, sectionid, stytype, academicyear, stynolist);
            }
            err.add("success~@~" + styexceedstudentlist + "~@~" + nopwsstudentlist);
        } catch (Exception e) {
            err.add("error");
            e.printStackTrace();
        }
        return err;
    }

    public List saveProgramSubjectTagging(String instituteid, String registrationid, String programid, String sectionid, String stytype, String academicyear, List stynolist) {

        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        BusinessService businessService = new BusinessService(daoFactory, true);
        Set set = new HashSet();
        List list = null;
        List list1 = new ArrayList();
        List<Object[]> tslist = new ArrayList<Object[]>();
        try {
            Set<String> s = new LinkedHashSet<String>(stynolist);
            List recordsToInsert = new ArrayList();
            for (String sty : s) {
                tslist = (List<Object[]>) daoFactory.getProgramSchemeDAO().getTeachingSchemeData(instituteid, programid, academicyear, sectionid, sty, registrationid);
                for (int ts = 0; ts < tslist.size(); ts++) {
                    Object[] obj = tslist.get(ts);
                    programSubjectTagging = new ProgramSubjectTagging();
                    programSubjectTaggingId = new ProgramSubjectTaggingId();
                    programSubjectTaggingId.setInstituteid(instituteid);
                    programSubjectTaggingId.setRegistrationid(registrationid);
                    String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                    programSubjectTaggingId.setProgramsubjectid(programsubjectid);
                    programSubjectTagging.setAcademicyear(academicyear);
                    programSubjectTagging.setProgramid(programid);
                    programSubjectTagging.setSectionid(sectionid);
                    programSubjectTagging.setStynumber(Short.parseShort(sty));
                    programSubjectTagging.setBasketid(obj[1] != null ? obj[1].toString() : "");
                    programSubjectTagging.setSubjectid(obj[2] != null ? obj[2].toString() : "");
                    programSubjectTagging.setTthide("N");
                    programSubjectTagging.setSubjecttype(obj[8] != null ? obj[8].toString() : "");
                    programSubjectTagging.setSubjecttypeid(obj[7] != null ? obj[7].toString() : "");
                    programSubjectTagging.setPstpopulated("Y");
                    String subtype = obj[8] != null ? obj[8].toString() : "C";
                    if (subtype.equals("C") || subtype.equals("P")) {
                        programSubjectTagging.setCustomfinalized("N");
                        programSubjectTagging.setSubjectrunning("Y");
                    } else {
                        programSubjectTagging.setSubjectrunning("N");
                        programSubjectTagging.setCustomfinalized("Y");
                    }
                    programSubjectTagging.setDepartmentid(obj[3] != null ? obj[3].toString() : "");
                    programSubjectTagging.setCredits(Double.parseDouble(obj[4] != null ? obj[4].toString() : "0"));
                    programSubjectTagging.setDeactive("N");
                    programSubjectTagging.setTotalmarks(Double.parseDouble(obj[5] != null ? obj[5].toString() : "0"));
                    programSubjectTagging.setPassingmarks(Double.parseDouble(obj[6] != null ? obj[6].toString() : "0"));
                    programSubjectTagging.setAuditsubject(obj[9] != null ? obj[9].toString() : "N");
                    programSubjectTagging.setId(programSubjectTaggingId);
                    list = daoFactory.getProgramSubjectTaggingDAO().doValidate(programSubjectTagging, "normal");
                    if (list.size() > 0) {
                        break;
                    }
                    set = new HashSet();
                    HashMap data1 = null;
                    list1 = (List) daoFactory.getProgramSchemeDAO().getSubjectTaggingList(instituteid, academicyear, sectionid, programid, Byte.parseByte(sty), obj[1] != null ? obj[1].toString() : "", registrationid, obj[2].toString());
                    if (list1.size() > 0) {
                        for (int j = 0; j < list1.size(); j++) {
                            data1 = (HashMap) list1.get(j);
                            SubjectComponent subjectComponent = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(instituteid, data1.get("subjectcomponentid").toString()));
                            String subjectcomponentcode = subjectComponent != null ? subjectComponent.getSubjectcomponentcode() : "";
                            programSubjectDetail = new ProgramSubjectDetail();
                            programSubjectDetailId = new ProgramSubjectDetailId();
                            programSubjectDetailId.setInstituteid(instituteid);
                            programSubjectDetailId.setProgramsubjectid(programsubjectid);
                            programSubjectDetailId.setRegistrationid(registrationid);

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
                            programSubjectDetail.setDeactive("N");
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

    public List getAllEnrollmentNo(HttpServletRequest req) {
        List<Object[]> list = null;
        try {
            String programid = req.getParameter("prgCode");
            String academicyear = req.getParameter("acadYear");
            String secId = req.getParameter("branchCode");
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = daoFactory.getStudentMasterDAO().getAllStudentsName1(instituteId, academicyear, programid, secId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List deleteStudentSubjectChoiceMasterWithChild(Map criteriaDto, boolean fetchStudentsWithNullPrgBrn, boolean fetchOnlyRegStudents) {

        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = criteriaDto.get("registrationid").toString();
        String studentid = criteriaDto.get("studentid").toString();
        List retList = new ArrayList();
        Object obj = null;
        Session session = null;
        Transaction tx = null;
        try {
//            List<StudentSubjectChoiceDetail> sscdlist = (List<StudentSubjectChoiceDetail>) daoFactory.getStudentSubjectChoiceDetailDAO().getStudentSubjectChoiceDetailData(instituteid, registrationid, studentid);
//            List<StudentSubjectChoiceMaster> sscmlist = (List<StudentSubjectChoiceMaster>) daoFactory.getStudentSubjectChoiceMasterDAO().getStudentSubjectChoiceMasterData(instituteid, registrationid, studentid);
//            List<PRStudentSubjectChoiceCount> prsscclist = (List<PRStudentSubjectChoiceCount>) daoFactory.getPrStudentSubjectChoiceCountDAO().getPRStudentSubjectChoiceCountData(instituteid, registrationid, studentid);
//            List<StudentRegistration_info> srilist = (List<StudentRegistration_info>) daoFactory.getStudentRegistration_infoDAO().getStudentRegistration_InfoData(instituteid, registrationid, studentid);
//            List<StudentRegistration> srlist = (List<StudentRegistration>) daoFactory.getStudentRegistrationDAO().getStudentRegistrationData(instituteid, registrationid, studentid);

            daoFactory.getStudentSubjectChoiceDetailDAO().deleteStudentSubjectChoiceDetailData(instituteid, registrationid, studentid);
            daoFactory.getStudentSubjectChoiceMasterDAO().deleteStudentSubjectChoiceMasterData(instituteid, registrationid, studentid);
            daoFactory.getPrStudentSubjectChoiceCountDAO().deletePRStudentSubjectChoiceCountData(instituteid, registrationid, studentid);
            daoFactory.getStudentRegistration_infoDAO().deleteStudentRegistration_InfoData(instituteid, registrationid, studentid);
            daoFactory.getStudentRegistrationDAO().delteteStudentRegistrationData(instituteid, registrationid, studentid);


            /*session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();
            if (sscdlist != null && sscdlist.size() > 0) {
                for (int i = 0; i < sscdlist.size(); i++) {
                    obj = sscdlist.get(i);
                    session.delete(obj);
                }
            }
            if (sscmlist != null && sscmlist.size() > 0) {
                for (int i = 0; i < sscmlist.size(); i++) {
                    obj = sscmlist.get(i);
                    session.delete(obj);
                }
            }
            if (prsscclist != null && prsscclist.size() > 0) {
                for (int i = 0; i < prsscclist.size(); i++) {
                    obj = prsscclist.get(i);
                    session.delete(obj);
                }
            }
            if (srilist != null && srilist.size() > 0) {
                for (int i = 0; i < srilist.size(); i++) {
                    obj = srilist.get(i);
                    session.delete(obj);
                }
            }
            if (srlist != null && srlist.size() > 0) {
                for (int i = 0; i < srlist.size(); i++) {
                    obj = srlist.get(i);
                    session.delete(obj);
                }
            }
            tx.commit();*/
//            BusinessService bs = new BusinessService(daoFactory, fetchOnlyRegStudents);
//            bs.deleteLits(sscdlist, sscmlist, prsscclist, srilist, srlist);
            retList = doSubmit(criteriaDto, fetchStudentsWithNullPrgBrn, fetchOnlyRegStudents);

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session = null;
            tx = null;
            return retList;

        }

    }

    public List deleteStudentRegistrationWithChild(Map criteriaDto, boolean fetchStudentsWithNullPrgBrn, boolean fetchOnlyRegStudents) {

        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = criteriaDto.get("registrationid").toString();
        String studentid = criteriaDto.get("studentid").toString();
        List retList = new ArrayList();
        Object obj = null;
        Session session = null;
        Transaction tx = null;
        try {
            List<StudentRegistration_info> srilist = (List<StudentRegistration_info>) daoFactory.getStudentRegistration_infoDAO().getStudentRegistration_InfoData(instituteid, registrationid, studentid);
            List<StudentRegistration> srlist = (List<StudentRegistration>) daoFactory.getStudentRegistrationDAO().getStudentRegistrationData(instituteid, registrationid, studentid);

            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();

            if (srilist != null && srilist.size() > 0) {
                for (int i = 0; i < srilist.size(); i++) {
                    obj = srilist.get(i);
                    session.delete(obj);
                }
            }
            if (srlist != null && srlist.size() > 0) {
                for (int i = 0; i < srlist.size(); i++) {
                    obj = srlist.get(i);
                    session.delete(obj);
                }
            }
            tx.commit();
            retList = doSubmit(criteriaDto, fetchStudentsWithNullPrgBrn, fetchOnlyRegStudents);

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session = null;
            tx = null;
            return retList;

        }

    }

}
