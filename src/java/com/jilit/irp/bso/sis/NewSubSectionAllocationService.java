/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.NewSubSectionAllocationIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.StudentRegistration_info;
import com.jilit.irp.persistence.dto.StudentRegistration_infoId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetailId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.util.JIRPDateUtil;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
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
public class NewSubSectionAllocationService implements NewSubSectionAllocationIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public void getStudentMasterTabData(Model model) {
        List<Academicyear> acadList = null;
        List regList = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Academicyear>) daoFactory.getAcademicYearDAO().findAll(instituteid);
            regList = daoFactory.getStudentSubjectChoiceDetailDAO().getRegistraionCodeForStuReg(instituteid);
            model.addAttribute("acadList", acadList);
            model.addAttribute("stuRegList", regList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getBranchAndStyNumber(HttpServletRequest req, ModelMap mm) {
        List list = new ArrayList();
        List branchList = null;
        List stuNumberList = null;
        try {
            String acadmicYear = req.getParameter("acadYearMas");
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            stuNumberList = daoFactory.getStudentSubjectChoiceDetailDAO().StyNumberList(instituteid, acadmicYear);
            branchList = daoFactory.getStudentSubjectChoiceDetailDAO().branchList(instituteid, acadmicYear);
            mm.addAttribute("stuNumberList", stuNumberList);
            mm.addAttribute("branchList", branchList);
            list.add(mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSection(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = req.getParameter("acadYear");
            String styNum = req.getParameter("styNum");
            String branchId[] = req.getParameter("branchId").split("~@~");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSectionList(instituteid, acadYear, styNum, branchId[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubSectionStudentMas(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = req.getParameter("acadYear");
            String styNum = req.getParameter("styNum");
            String branchId[] = req.getParameter("branchId").split("~@~");
            String sectionId = req.getParameter("sectionId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubSectionListForStuMas(instituteid, acadYear, styNum, branchId[0], sectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubsectionForCombo2(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = req.getParameter("acadYear");
            String styNum = req.getParameter("styNum");
            String branchId[] = req.getParameter("branchId").split("~@~");
            String nextSemValue = req.getParameter("nextSemValue");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubsectionForCombo2(instituteid, styNum, acadYear, branchId[0], nextSemValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List loadStudentMasterList(HttpServletRequest req) {
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String acadYear = req.getParameter("acadYear");
        String styNum = req.getParameter("styNum");
        String branchId[] = req.getParameter("branchId").split("~@~");
        String sectionId = req.getParameter("sectionId");
        List retList = new ArrayList();
        List localList = null;
        try {
            int slno = 1;
            List<Object[]> list = (List<Object[]>) daoFactory.getStudentRegistration_infoDAO().getStudentMasterData(instituteid, acadYear, styNum, branchId[0], sectionId);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    localList = new ArrayList();
                    localList.add(slno);
                    localList.add(list.get(i)[0]);
                    localList.add(list.get(i)[1]);
                    localList.add(list.get(i)[2]);
                    localList.add(acadYear);
                    localList.add(branchId[1]);
                    localList.add(styNum);
                    localList.add((Integer.parseInt(styNum) + 1));
                    localList.add(list.get(i)[3].toString());
                    localList.add(list.get(i)[4] != null ? list.get(i)[4].toString() : "NA");
                    localList.add(list.get(i)[3].toString());
                    localList.add(list.get(i)[5] != null ? list.get(i)[5].toString() : "NA");
                    localList.add(list.get(i)[6].toString());
                    localList.add(false);
                    retList.add(localList);
                    slno++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List saveSubSectionAllocationStudentMaster(HttpServletRequest request) {
        List list = new ArrayList();
        StudentMaster sm = null;
        List smList = new ArrayList();
        int count = Integer.parseInt(request.getParameter("checkedCountStudentMas"));
        try {
            for (int i = 0; i < count; i++) {
                if (request.getParameter("chk" + i + "") != null) {
                    String studentId = request.getParameter("chk" + i + "");
                    if (request.getParameter("subSectionMas") != null && !"".equals(request.getParameter("subSectionMas"))) {
                        sm = new StudentMaster();
                        sm = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentId);
                        sm.setSectionid(request.getParameter("sectionCodeMas"));
                        sm.setSubsectionid(request.getParameter("subSectionMas"));
                        sm.setNextsectionid(request.getParameter("sectionCodeMas"));
                        sm.setNextsubsectionid(request.getParameter("subSectionMas"));
                        sm.setActivestatus(request.getParameter("activeStatusMas"));
                        smList.add(sm);
                    } else {
                        sm = new StudentMaster();
                        sm = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentId);
                        sm.setActivestatus(request.getParameter("activeStatusMas"));
                        smList.add(sm);
                    }
                }
            }
            if (daoFactory.getStudentAttendanceDAO().saveObjlist(smList)) {
                list = new ArrayList();
                list.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getAcademicYear(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getAcademicYear(instituteid, regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyNumber(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String academicYear = req.getParameter("academicYear");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getStyNumber(instituteid, academicYear, regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBranchForStuReg(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String academicYear = req.getParameter("academicYear");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getBranchForStuReg(instituteid, academicYear, regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSectionForStuReg(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String academicYear = req.getParameter("academicYear");
            String stynumber = req.getParameter("styNum");
            String branchid[] = req.getParameter("branchId").split("~@~");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSectionForStuReg(instituteid, academicYear, regId, stynumber, branchid[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSectionListForReg(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = req.getParameter("academicYear");
            String styNum = req.getParameter("styNum");
            String branchId[] = req.getParameter("branchId").split("~@~");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubsectionForCombo(instituteid, styNum, acadYear, branchId[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubSectionListForReg(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = req.getParameter("academicYear");
            String styNum = req.getParameter("styNum");
            String branchId[] = req.getParameter("branchId").split("~@~");
            String sectionId = req.getParameter("sectionId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubSectionListForStuMas(instituteid, acadYear, styNum, branchId[0], sectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List loadStudentRegData(HttpServletRequest req) {
        List retList = new ArrayList();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String regId = req.getParameter("regId");
        String acadYear = req.getParameter("acadYear");
        String styNum = req.getParameter("styNum");
        String branchId[] = req.getParameter("branchId").split("~@~");
        String sectionId = req.getParameter("sectionId");
        List localList = null;
        try {
            List<Object[]> list1 = (List<Object[]>) daoFactory.getStudentRegistration_infoDAO().getStudentRegData(instituteid, regId, acadYear, styNum, branchId[0], sectionId);
            List indexlist = new ArrayList();
            indexlist.add(7);    
            indexlist.add(9);    
            indexlist.add(10);    
            indexlist.add(11);    
            List<Object[]> list = daoFactory.getUtilDAO().dateConverterList(list1, indexlist,"dd/MM/yyyy");
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    localList = new ArrayList();
                    localList.add(list.get(i)[0]);
                    localList.add(list.get(i)[1]);
                    localList.add(list.get(i)[2]);
                    localList.add(acadYear);
                    localList.add(branchId[1]);
                    localList.add(styNum);
                    localList.add(list.get(i)[3]);
                    localList.add(list.get(i)[4]);
                    localList.add(list.get(i)[5]);
                    localList.add(list.get(i)[6]);
                    localList.add(list.get(i)[7]);
                    localList.add(list.get(i)[8]);
                    localList.add(list.get(i)[9]);
                    localList.add(list.get(i)[10]);
                    localList.add(list.get(i)[11]);
                    localList.add(false);
                    retList.add(localList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List saveSubSectionAllocationStudentReg(HttpServletRequest req) {
        List list = new ArrayList();
        StudentRegistration_info studeReg = null;
        StudentRegistration_infoId studeRegId = null;
        StudentRegistration reg = null;
        StudentRegistrationId regId = null;
        StudentMaster sm = null;
        List smList = new ArrayList();
        List srList = new ArrayList();
        List regList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String preventfromdate = "";
        String preventenddate = "";
        int count = Integer.parseInt(req.getParameter("checkedCountStudentReg"));
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = req.getParameter("regCodeReg");
            String updateStudentMaster = req.getParameter("updateStuReg");
            String regallow = req.getParameter("regAllow");// if Y then update StudentRegistration for regallow  = 'Y'
            if (!"".equals(req.getParameter("fromdate")) && req.getParameter("fromdate") != null && !"".equals(req.getParameter("todate")) && req.getParameter("todate") != null) {
                preventfromdate = req.getParameter("fromdate");
                preventenddate = req.getParameter("todate");
            }
            for (int i = 0; i < count; i++) {
                if (req.getParameter("chk" + i + "") != null) {
                    String studentId = req.getParameter("chk" + i + "");
                    studeReg = new StudentRegistration_info();
                    studeRegId = new StudentRegistration_infoId();
                    studeRegId.setInstituteid(instituteid);
                    studeRegId.setRegistrationid(registrationid);
                    studeRegId.setStudentid(studentId);
                    studeReg = (StudentRegistration_info) daoFactory.getStudentRegistration_infoDAO().findByPrimaryKey(studeRegId);
                    if (req.getParameter("subSectionReg") != null && !"".equals(req.getParameter("subSectionReg"))) {
                        studeReg.setSubsectionid(req.getParameter("subSectionReg") != null ? req.getParameter("subSectionReg") : "");
                        studeReg.setSectionid(req.getParameter("sectionCodeReg") != null ? req.getParameter("sectionCodeReg") : "");
                        srList.add(studeReg);
                        if ("Y".equals(updateStudentMaster)) {
                            sm = new StudentMaster();
                            sm = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentId);
                            sm.setSectionid(req.getParameter("sectionCodeReg"));
                            sm.setSubsectionid(req.getParameter("subSectionReg"));
                            sm.setNextsectionid(req.getParameter("sectionCodeReg"));
                            sm.setNextsubsectionid(req.getParameter("subSectionReg"));
                            smList.add(sm);
                        }
                    }
                    if (!"".equals(regallow)) {
                        reg = new StudentRegistration();
                        regId = new StudentRegistrationId();
                        regId.setInstituteid(instituteid);
                        regId.setRegistrationid(registrationid);
                        regId.setStudentid(studentId);
                        reg = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(regId);
                        reg.setRegallow(regallow);
                        if (!"".equals(preventfromdate) && preventfromdate != null && !"".equals(preventenddate) && preventenddate != null) {
                            reg.setPreventfrom(sdf.parse(preventfromdate.toString()));
                            reg.setPreventto(JIRPDateUtil.convertToDateFormat(preventenddate, "dd/MM/yyyy"));
                        }
                        regList.add(reg);
                    }
                }
            }
            if (!regList.isEmpty()) {
                srList.addAll(regList);
            }
            if (daoFactory.getStudentMasterDAO().saveObjList(srList, smList)) {
                list = new ArrayList();
                list.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSubjectCode(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubjectCode(instituteid, regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getDepartmentCode(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getDepartmentCode(instituteid, regId, subjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubjectCompCode(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String subjectId = req.getParameter("subjectId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubjectCompCode(instituteid, subjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List loadStudentChoiceData(HttpServletRequest req) {
        List retList = new ArrayList();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String regId = req.getParameter("regIdCho");
        String subjectId = req.getParameter("subCodeCho");
        String departmentId = req.getParameter("deptCodeCho");
        List localList = null;
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getStudentSubjectChoiceDetailDAO().getPSTSection(instituteid, regId, subjectId, departmentId);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    localList = new ArrayList();
                    localList.add(regId);//stytype
                    localList.add(subjectId);//stytype
                    localList.add(list.get(i)[0]); // studentid
                    localList.add(list.get(i)[1]); //sectionid
                    localList.add(list.get(i)[2]); //enrollmentno
                    localList.add(list.get(i)[3]); //name
                    localList.add(list.get(i)[4]);// programcode
                    localList.add(list.get(i)[5]);//academixcyear
                    localList.add(list.get(i)[6]);//stynumber
                    localList.add(list.get(i)[7]);//sectioncode
                    localList.add(list.get(i)[8]);//subsectioncode
                    localList.add(list.get(i)[9]);//subjectrunning
                    localList.add(list.get(i)[10]);//basketcode
                    localList.add(list.get(i)[11]);//stytype
                    retList.add(localList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List getSectionForChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String departmentId = req.getParameter("deptId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getPSTSectionChoice(instituteid, regId, subjectId, departmentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubSectionChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String sectionId = req.getParameter("sectionId");
            String acadYear = req.getParameter("acadYear");
            String styNum = req.getParameter("styNum");
            String SubComp[] = req.getParameter("SubCompId").split("~@~");
            String departmentId = req.getParameter("departmentId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getSubsection(instituteid, regId, subjectId, sectionId, acadYear, styNum, SubComp[1], departmentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAcademicYearForChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String subCompId[] = req.getParameter("subCompId").split("~@~");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getAcademicYearForChoice(instituteid, regId, subjectId, subCompId[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getProgramForChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String subCompId[] = req.getParameter("subCompId").split("~@~");
            String acadYear = req.getParameter("acadYear");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getProgramForChoice(instituteid, regId, subjectId, subCompId[0], acadYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBranchForChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String subCompId[] = req.getParameter("subCompId").split("~@~");
            String acadYear = req.getParameter("acadYear");
            String programId = req.getParameter("programId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getBranchForChoice(instituteid, regId, subjectId, subCompId[0], acadYear, programId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyTypeChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String acadYear = req.getParameter("acadYear");
            String programId = req.getParameter("programId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getStyTypeChoice(instituteid, regId, subjectId, acadYear, programId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyNumberChoice(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String regId = req.getParameter("regId");
            String subjectId = req.getParameter("subjectId");
            String acadYear = req.getParameter("acadYear");
            String programId = req.getParameter("programId");
            list = daoFactory.getStudentSubjectChoiceDetailDAO().getStyNumberChoice(instituteid, regId, subjectId, acadYear, programId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List loadStudentChoiceFilterData(HttpServletRequest req) {
        List retList = new ArrayList();
        List localList = null;
        String instituteId = jirpession.getJsessionInfo().getSelectedinstituteid();
        String regId = req.getParameter("regIdCho");
        String departmentId = req.getParameter("deptCodeCho");
        String subjectId = req.getParameter("subCodeCho");
        String subCompId[] = req.getParameter("subCompCho").split("~@~");
        String acadYear = req.getParameter("acadYear");
        String programId = req.getParameter("programId");
        String branchId = req.getParameter("branchId");
        String styNum = req.getParameter("styNum");
        String styType = req.getParameter("styType");
        try {
            List<Object[]> offencelist = (List<Object[]>) daoFactory.getStudentSubjectChoiceDetailDAO().getSubSectionAllocation(instituteId, regId, departmentId, subjectId, subCompId[0], acadYear, programId, branchId, styNum, styType);
            if (offencelist != null && offencelist.size() > 0) {
                for (int i = 0; i < offencelist.size(); i++) {
                    localList = new ArrayList();
                    localList.add(offencelist.get(i)[0] == null ? "" : offencelist.get(i)[0].toString());
                    localList.add(offencelist.get(i)[1] == null ? "" : offencelist.get(i)[1].toString());
                    localList.add(offencelist.get(i)[2] == null ? "" : offencelist.get(i)[2].toString());
                    localList.add(offencelist.get(i)[3] == null ? "" : offencelist.get(i)[3].toString());
                    localList.add(offencelist.get(i)[4] == null ? "" : offencelist.get(i)[4].toString());
                    localList.add(offencelist.get(i)[5] == null ? "" : offencelist.get(i)[5].toString());
                    localList.add(offencelist.get(i)[6] == null ? "" : offencelist.get(i)[6].toString());
                    localList.add(offencelist.get(i)[7] == null ? "0" : offencelist.get(i)[7].toString());
                    localList.add(offencelist.get(i)[8] == null ? "" : offencelist.get(i)[8].toString());
                    localList.add(offencelist.get(i)[9] == null ? "" : offencelist.get(i)[9].toString());
                    localList.add(offencelist.get(i)[10] == null ? "" : offencelist.get(i)[10].toString());
                    localList.add(offencelist.get(i)[11] == null ? "" : offencelist.get(i)[11].toString());
                    retList.add(localList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List saveSubSectionAllocationStudentChoiceDetail(HttpServletRequest req) {
        List list = new ArrayList();
        List updateList1 = new ArrayList();
        List updateList2 = new ArrayList();
        StudentSubjectChoiceDetail choiceDetail = null;
        StudentSubjectChoiceDetailId choiceDetailid = null;
        StudentSubjectChoiceMaster choiceMaster = null;
        StudentSubjectChoiceMasterId choiceMasterId = null;
        int count = Integer.parseInt(req.getParameter("checkedCountStudentChoice"));
        String subjectRunning = req.getParameter("subjectRunning");
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            for (int i = 0; i < count; i++) {
                if (req.getParameter("chk" + i + "") != null) {
                    String ids[] = req.getParameter("chk" + i + "").split("~@~");
                    choiceMaster = new StudentSubjectChoiceMaster();
                    choiceMasterId = new StudentSubjectChoiceMasterId();
                    choiceMasterId.setInstituteid(instituteid);
                    choiceMasterId.setRegistrationid(req.getParameter("regCodeChoice"));
                    choiceMasterId.setStynumber(Byte.parseByte(ids[2]));
                    choiceMasterId.setStudentid(ids[0]);
                    choiceMasterId.setSubjectid(req.getParameter("subCode"));
                    choiceMaster = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(choiceMasterId);
                    if (choiceMaster != null) {
                        choiceMaster.setSubjectrunning(subjectRunning);
                        updateList1.add(choiceMaster);
                    } else {
                    }
                    if (req.getParameter("subSectionChoice") != null && !"".equals(req.getParameter("subSectionChoice"))) {
                        choiceDetail = new StudentSubjectChoiceDetail();
                        choiceDetailid = new StudentSubjectChoiceDetailId();
                        choiceDetailid.setInstituteid(instituteid);
                        choiceDetailid.setRegistrationid(req.getParameter("regCodeChoice"));
                        choiceDetailid.setSubjectid(req.getParameter("subCode"));
                        choiceDetailid.setSubjectcomponentid(req.getParameter("subComp").split("~@~")[0]);
                        choiceDetailid.setStudentid(ids[0]);
                        choiceDetailid.setStynumber(Byte.parseByte(ids[2]));
                        choiceDetail = (StudentSubjectChoiceDetail) daoFactory.getStudentSubjectChoiceDetailDAO().findByPrimaryKey(choiceDetailid);
                        if (choiceDetail != null) {
                            choiceDetail.setSubsectionid(req.getParameter("subSectionChoice") != null ? req.getParameter("subSectionChoice") : "");
                            updateList2.add(choiceDetail);
                        } else {
                        }
                    }
                }
            }
            if (daoFactory.getStudentMasterDAO().saveObjList(updateList1, updateList2)) {
                list = new ArrayList();
                list.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

}
