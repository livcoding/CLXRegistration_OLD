/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.BulkStudentSubjectChoiceIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCountId;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetailId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nazar.Mohammad
 */
@Service
public class BulkStudentSubjectChoiceService implements BulkStudentSubjectChoiceIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public List getQueryCriteriaData(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentRegistrationDAO().getAcadYear(instituteid, registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getProgramCode(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        String acad_year = request.getParameter("acad_year");
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid, registrationid, acad_year);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBranchCode(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        String acad_year = request.getParameter("acad_year");
        String programid = request.getParameter("program_code");
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramMasterDAO().getBranchCode(instituteid, registrationid, acad_year, programid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStynumber(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        String acad_year = request.getParameter("acad_year");
        String programid = request.getParameter("program_code");
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramMasterDAO().getStynumber(instituteid, registrationid, acad_year, programid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSectionCode(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        String acad_year = request.getParameter("acad_year");
        String programid = request.getParameter("program_code");
        String[] branchids = request.getParameter("branch_code").split(",");
        String[] stynumbers = request.getParameter("stynumber").split(",");
        String branchid = "";
        for (int i = 0; i < branchids.length; i++) {
            branchid = branchid + "'" + branchids[i] + "'" + ",";
        }
        if (branchid.endsWith(",")) {
            branchid = branchid.substring(0, branchid.length() - 1);
        }
        String stynumber = "";
        for (int i = 0; i < stynumbers.length; i++) {
            stynumber = stynumber + "'" + stynumbers[i] + "'" + ",";
        }
        if (stynumber.endsWith(",")) {
            stynumber = stynumber.substring(0, stynumber.length() - 1);
        }
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentRegistrationDAO().getSectionCode(instituteid, registrationid, acad_year, programid, branchid, stynumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubjectData(HttpServletRequest request) {
        String subjectType = request.getParameter("subjectType");
        String registrationid = request.getParameter("semCode");
        String acad_year = request.getParameter("acad_year");
        String programid = request.getParameter("program_code");
        String[] branchids = request.getParameter("branch_code").split(",");
        String[] stynumbers = request.getParameter("stynumber").split(",");
        String[] sectionids = request.getParameter("section").split(",");
        String branchid = "";
        for (int i = 0; i < branchids.length; i++) {
            branchid = branchid + "'" + branchids[i] + "'" + ",";
        }
        if (branchid.endsWith(",")) {
            branchid = branchid.substring(0, branchid.length() - 1);
        }
        String stynumber = "";
        for (int i = 0; i < stynumbers.length; i++) {
            stynumber = stynumber + "'" + stynumbers[i] + "'" + ",";
        }
        if (stynumber.endsWith(",")) {
            stynumber = stynumber.substring(0, stynumber.length() - 1);
        }

        String sectionid = "";
        for (int i = 0; i < sectionids.length; i++) {
            sectionid = sectionid + "'" + sectionids[i] + "'" + ",";
        }
        if (sectionid.endsWith(",")) {
            sectionid = sectionid.substring(0, sectionid.length() - 1);
        }
        List list = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectData(instituteid, registrationid, acad_year, programid, stynumber, sectionid, subjectType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Map getStudentsList(HttpServletRequest request) {
        Map m = new HashMap();
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = request.getParameter("semCode");
            String acad_year = request.getParameter("acad_year");
            String programid = request.getParameter("program_code");
            String[] branchids = request.getParameter("branch_code").split(",");
            String[] stynumbers = request.getParameter("stynumber").split(",");
            String sectionids = request.getParameter("section");
            float totalcredit = Float.parseFloat(request.getParameter("totalcredit"));
            String branchid = "";
            for (int i = 0; i < branchids.length; i++) {
                branchid = branchid + "'" + branchids[i] + "'" + ",";
            }
            if (branchid.endsWith(",")) {
                branchid = branchid.substring(0, branchid.length() - 1);
            }
            String stynumber = "";
            for (int i = 0; i < stynumbers.length; i++) {
                stynumber = stynumber + "'" + stynumbers[i] + "'" + ",";
            }
            if (stynumber.endsWith(",")) {
                stynumber = stynumber.substring(0, stynumber.length() - 1);
            }
            m = daoFactory.getStudentSubjectChoiceMasterDAO().getAllRegdStudentNotInSubjectChoice(instituteid, registrationid, acad_year, programid, sectionids, totalcredit, stynumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return m;
    }

    public Set getsubjectCompList(String regId, String instId, String subId, String basketId, int styNo, String studentid, String nextSubSectionID) {
        StudentSubjectChoiceDetail stuSubChoiceDetail = null;
        StudentSubjectChoiceDetailId stuSubChoiceDetailId = null;
        Set studentsubjectchoicedetail = new HashSet();
        try {
            List<Object[]> progSubDetailList = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getprogSubDetail(regId, instId, subId, basketId, styNo);
            for (int i = 0; i < progSubDetailList.size(); i++) {
                String str1 = progSubDetailList.get(i)[0].toString();
                stuSubChoiceDetail = new StudentSubjectChoiceDetail();
                stuSubChoiceDetailId = new StudentSubjectChoiceDetailId();
                stuSubChoiceDetailId.setSubjectcomponentid(str1);
                stuSubChoiceDetailId.setRegistrationid(regId);
                stuSubChoiceDetailId.setStudentid(studentid);
                stuSubChoiceDetailId.setInstituteid(instId);
                stuSubChoiceDetailId.setStynumber((byte) styNo);
                stuSubChoiceDetailId.setSubjectid(subId);
                stuSubChoiceDetail.setId(stuSubChoiceDetailId);
                stuSubChoiceDetail.setSubsectionid(nextSubSectionID);
                studentsubjectchoicedetail.add(stuSubChoiceDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentsubjectchoicedetail;
    }

    @Override
    public List doSaveBulkStudents(HttpServletRequest request) {
        List retList = new ArrayList<String>();
        try {
            List<StudentSubjectChoiceMaster> choiceMasterList = new ArrayList<StudentSubjectChoiceMaster>();
            List<PRStudentSubjectChoiceCount> prStudentSubjChoiceCountList = new ArrayList<PRStudentSubjectChoiceCount>();
            String selectedCoreSubject = request.getParameter("selectedCoreSubject");//Core Subject
            String[] coreids = selectedCoreSubject.split(",");
            String selectedElecSubject = request.getParameter("selectedElecSubject");//Elective Subject
            String[] electiveids = selectedElecSubject.split(",");
            String studentsList = request.getParameter("studentsList"); // Student List
            String[] studentList = studentsList.split(",");
            for (int i = 0; i < studentList.length; i++) {
                String registrationid = studentList[i].split("~@~")[1].toString();
                String instituteid = studentList[i].split("~@~")[2].toString();
                String studentid = studentList[i].split("~@~")[0].toString();
                StudentRegistration registration = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(new StudentRegistrationId(instituteid, registrationid, studentid));
                if (registration != null) {
                    StudentMaster sm = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentid);

                    // Getting Subsection code for the Next Semester Master table may have prev semester
                    String nextSubSectionID = sm.getSubsectionid();
                    StudentSubjectChoiceMaster subjectChoiceMaster = new StudentSubjectChoiceMaster();
                    //----------------------------------This block for Core Subject -------------------------------------------//  
                    if (coreids.length > 0) {
                        for (int j = 0; j < coreids.length; j++) {
                            if (!coreids[j].equals("")) {
                                if (coreids[j].split("~@~")[8].toString().equals(sm.getSectionid()) && coreids[j].split("~@~")[2].toString().equals(sm.getProgramid()) && coreids[j].split("~@~")[4].toString().equals(registration.getStynumber()+"")) {
                                    subjectChoiceMaster = new StudentSubjectChoiceMaster();
                                    String regid = coreids[j].split("~@~")[0].toString();
                                    String subjectid = coreids[j].split("~@~")[1].toString();
                                    String programid = coreids[j].split("~@~")[2].toString();
                                    String basketid = coreids[j].split("~@~")[3].toString();
                                    String stynumber = coreids[j].split("~@~")[4].toString();
                                    String subjectType = coreids[j].split("~@~")[5].toString();
                                    String subjectTypeId = coreids[j].split("~@~")[7].toString();

                                    subjectChoiceMaster.setId(new StudentSubjectChoiceMasterId(instituteid, registrationid, registration.getStynumber(), studentid, subjectid));
                                    subjectChoiceMaster.setBasketid(basketid);
                                    subjectChoiceMaster.setChoice(new Byte("1"));
                                    subjectChoiceMaster.setSubjectrunning("Y");
                                    subjectChoiceMaster.setStytypeid(registration.getStytypeid());
                                    subjectChoiceMaster.setSubjecttype(subjectType);
                                    subjectChoiceMaster.setSubjecttypeid(subjectTypeId);
                                    Set studentsubjectchoicedetail = getsubjectCompList(registrationid, instituteid, subjectid, basketid, registration.getStynumber(), studentid, nextSubSectionID);
                                    subjectChoiceMaster.setStudentsubjectchoicedetails(studentsubjectchoicedetail);
                                    choiceMasterList.add(subjectChoiceMaster);
                                }
                            }

                        }
                    }
                    //----------------------------------------------------End Core----------------------------------------------------------------------------//

                    //---------------------------------------------------Elective Subject --------------------------------------------------------------------//
                    if (electiveids.length > 0) {
                        for (int j = 0; j < electiveids.length; j++) {
                            if (!electiveids[j].equals("")) {
                                if (electiveids[j].split("~@~")[8].toString().equals(sm.getSectionid()) && electiveids[j].split("~@~")[2].toString().equals(sm.getProgramid()) && electiveids[j].split("~@~")[4].toString().equals(sm.getStynumber().toString())) {
                                    subjectChoiceMaster = new StudentSubjectChoiceMaster();
                                    String regid = electiveids[j].split("~@~")[0].toString();
                                    String subjectid = electiveids[j].split("~@~")[1].toString();
                                    String programid = electiveids[j].split("~@~")[2].toString();
                                    String basketid = electiveids[j].split("~@~")[3].toString();
                                    String stynumber = electiveids[j].split("~@~")[4].toString();
                                    String subjectType = electiveids[j].split("~@~")[5].toString();
                                    String subjectTypeId = electiveids[j].split("~@~")[7].toString();

                                    subjectChoiceMaster.setId(new StudentSubjectChoiceMasterId(instituteid, registrationid, registration.getStynumber(), studentid, subjectid));
                                    subjectChoiceMaster.setBasketid(basketid);
                                    subjectChoiceMaster.setChoice(new Byte("1"));
                                    subjectChoiceMaster.setSubjectrunning("Y");
                                    subjectChoiceMaster.setStytypeid(registration.getStytypeid());
                                    subjectChoiceMaster.setSubjecttype(subjectType);
                                    subjectChoiceMaster.setSubjecttypeid(subjectTypeId);
                                    Set studentsubjectchoicedetail = getsubjectCompList(registrationid, instituteid, subjectid, basketid, registration.getStynumber(), studentid, nextSubSectionID);
                                    subjectChoiceMaster.setStudentsubjectchoicedetails(studentsubjectchoicedetail);
                                    choiceMasterList.add(subjectChoiceMaster);

                                    if (j == 0) {
                                        PRStudentSubjectChoiceCount prss = new PRStudentSubjectChoiceCount();
                                        PRStudentSubjectChoiceCountId prssid = new PRStudentSubjectChoiceCountId();
                                        prssid.setBasketid(basketid);
                                        prssid.setStudentid(studentid);
                                        prssid.setInstituteid(instituteid);
                                        prssid.setRegistrationid(registrationid);
                                        prssid.setStynumber(registration.getStynumber());
                                        prss.setId(prssid);
                                        prss.setSubjectschoicescount(new Byte("1"));
                                        prStudentSubjChoiceCountList.add(prss);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            retList.add(daoFactory.getStudentSubjectChoiceMasterDAO().saveStudentSubjectChoiceData(choiceMasterList, null, prStudentSubjChoiceCountList, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retList;

    }
}
