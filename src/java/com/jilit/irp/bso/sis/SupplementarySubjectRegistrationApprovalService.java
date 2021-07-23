
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.util.JIRPSession;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SupplementarySubjectRagistrationApprovalIservice;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.TT_TimeTable;
import com.jilit.irp.persistence.dto.TT_TimeTableId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocation;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetailId;
import com.jilit.irp.persistence.dto.TT_MultiFacultyTeachingLoad;
import com.jilit.irp.persistence.dto.TT_MultiFacultyTeachingLoadId;
import com.jilit.irp.persistence.dto.Tt_Timetableapproval;
import com.jilit.irp.persistence.dto.Tt_TimetableapprovalId;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.RegistrationMasterId;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.math.BigDecimal;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class SupplementarySubjectRegistrationApprovalService implements SupplementarySubjectRagistrationApprovalIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List list = (List) daoFactory.getProgramSubjectDetailDAO().getAllRegistrationCodeForSupplementary(instituteid.toUpperCase());
            String parametervalue = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "D1.1");
            model.addAttribute("parametervalue", parametervalue);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method Used To Get GridData For Pending For Approval Tab
     *
     * @param registrationid
     * @return
     */
    public Map getPendingForApprovalData(HttpServletRequest request) {
        String[] regids = request.getParameter("regCode").split("~@~");
        String registrationid = regids[0];
        Map map = new HashMap();
        String styType = "";
        List list = null;
        String stytype = null;
        String status = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List instidlist = new ArrayList();
        instidlist.add(instituteid);
        List regidlist = new ArrayList();
        regidlist.add(registrationid);
        try {
            if (styType != null && !"".equals(styType)) {
                stytype = styType;
            } else {
                String parameterid = "SUBREG2.7";
                List parameter = daoFactory.getStudentSubjectChoiceDetailDAO().getBackPaperAttemptParameters(instituteid, parameterid);
                Map val = new HashMap();
                for (int j = 0; j < parameter.size(); j++) {
                    Map m = (Map) parameter.get(j);
                    val.put(m.get("parameterid"), m.get("parametervalue"));
                }
                stytype = (val.get("SUBREG2.7") == null ? "SUP" : val.get("SUBREG2.7").toString());
                map.put("styTypevalue", stytype.toUpperCase());
            }
            list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instidlist, regidlist, status, stytype.toUpperCase(), "SUP");
            map.put("pendingforapprovaldata", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status = null;
        }
        return map;
    }

    /**
     * Method Used To Get GridData For Approval Tab
     *
     * @param registrationid
     * @return
     */
    public List getApprovedData(HttpServletRequest request) {
        String[] regids = request.getParameter("regCode").split("~@~");
        String registrationid = regids[0];
        String styType = request.getParameter("styType");
        List<Object[]> list = new ArrayList<Object[]>();
        String status = "A";
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List instidlist = new ArrayList();
        instidlist.add(instituteid);
        List regidlist = new ArrayList();
        regidlist.add(registrationid);
        try {
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instidlist, regidlist, status, styType, "SUP");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status = null;
        }
        return list;
    }

    /**
     * Method Used To Get GridData For Cancle Tab
     *
     * @param registrationid
     * @return
     */
    public List getCanclledData(HttpServletRequest request) {
        String[] regids = request.getParameter("regCode").split("~@~");
        String registrationid = regids[0];
        String styType = request.getParameter("styType");
        List<Object[]> list = new ArrayList<Object[]>();
        String status = "C";
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List instidlist = new ArrayList();
        instidlist.add(instituteid);
        List regidlist = new ArrayList();
        regidlist.add(registrationid);
        try {
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instidlist, regidlist, status, styType, "SUP");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status = null;
        }
        return list;
    }

    /**
     * Method Used To Approve Or Cancle Data
     *
     * @param asobj
     * @param dataList
     * @return
     */
    public List approveCancleData(String status, HttpServletRequest request) {
        List err = null;
        String stytype = request.getParameter("styType");
        String[] regids = request.getParameter("regCode").split("~@~");
        String registrationid = regids[0];
        String parentregistrationid = regids[2];
        String subjectid = "";
        String studentid = "";
        String fstid = "";
        String studentfstid = "";
        String acadyear = "";
        String programid = "";
        String sectionid = "";
        String subsectionid = "";
        String stytypeid = "";
        String basketid = "";
        String stynumber = "";
        List subComponentidList = null;
        FacultySubjectTagging facultySubjectTagging = null;
        FacultySubjectTaggingId facultySubjectTaggingId = null;
        FacultyStudentTagging f_StudentTagging = null;
        FacultyStudentTaggingId f_StudentTaggingId = null;
        byte styno = new Byte("0");
        Map saveListmap = new HashMap();
        List saveOrUpdateList = new ArrayList();
        List updateList = new ArrayList();
        List deletelist = new ArrayList();
        String qryString = null;
        int saverecordcount = 0;
        int errorrecordcount = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String entryby = jirpsession.getJsessionInfo().getMemberid();
        BusinessService businessService = new BusinessService(daoFactory, true);
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();

        try {
            if ("A".equalsIgnoreCase(status)) {
                int count = Integer.parseInt(request.getParameter("checkedCount1"));
                String regConfirmation = request.getParameter("regconf");
                Date regConfDate = null;
                if (regConfirmation != null) {
                    regConfDate = format.parse(request.getParameter("regConfirm_date"));
                }
                //if ("SUP".equalsIgnoreCase(stytype)) {
                for (int i = 0; i < count; i++) {
                    if (request.getParameter("chk" + i) != null) {
                        String[] ids = request.getParameter("chk" + i).split("~@~");
                        qryString = "";
                        studentid = ids[0];
                        subjectid = ids[2];
                        acadyear = ids[3];
                        programid = ids[4];
                        sectionid = ids[5];
                        subsectionid = ids[6];
                        stytypeid = ids[7];
                        basketid = ids[8];
                        stynumber = ids[1];
                        styno = new Byte(stynumber);
                        List dueAmount = (List) daoFactory.getdBDependentDAO().checkStudentDueAmount(instituteid, studentid, registrationid);
                        BigDecimal feedueamount = new BigDecimal(dueAmount.get(0).toString());
                        String parfeeheadcode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG2.4");
                        String feeheadid = daoFactory.getProgramSubjectDetailDAO().getFeeHeadIdForSupplementrySubjEntry(instituteid, parfeeheadcode);
                        List<Object[]> checkRecordInFeestructureindivisual = (List<Object[]>) daoFactory.getProgramSubjectDetailDAO().checkRecordInFeestructureindivisual(instituteid, registrationid, feeheadid, studentid);
                        String totalduefeeamount = "";
                        String feefinilized = "";
                        if (checkRecordInFeestructureindivisual.size() > 0 && checkRecordInFeestructureindivisual.get(0) != null) {
                            Object[] obj = checkRecordInFeestructureindivisual.get(0);
                            totalduefeeamount = obj[0].toString();
                            feefinilized = obj[1].toString();
                        }
                        if (feefinilized.equals("Y")) {
                            if (!feedueamount.equals(BigDecimal.ZERO)) {
                                err = new ArrayList<String>();
                                err.add("Please Deposit the remaining Fee Amount " + feedueamount + " Rs.!");
                                return err;
                            } else {
                                TT_TimeTable ttdto = new TT_TimeTable();
                                TT_TimeTableId ttid = new TT_TimeTableId();
                                ttid.setInstituteid(instituteid);
                                ttid.setRegistrationid(registrationid);
                                ttdto = (TT_TimeTable) daoFactory.getTt_TimeTableDAO().findByPrimaryKey(ttid);
                                if (ttdto == null) {
                                    ttdto = new TT_TimeTable();
                                    ttdto.setId(ttid);
                                    ttdto.setStartdate(new Date());
                                    ttdto.setEnddate(new Date());
                                    ttdto.setEntrydate(new Date());
                                    ttdto.setDeactive("N");
                                    daoFactory.getTt_TimeTableDAO().add(ttdto);
                                }

                                TT_TimeTableAllocation ttadto = null;
                                TT_TimeTableAllocation newttadto = new TT_TimeTableAllocation();
                                TT_TimeTableAllocationId ttaid = new TT_TimeTableAllocationId();
                                TT_TimeTableAllocationId newttaid = new TT_TimeTableAllocationId();
                                TT_TimeTableAllocationDetail ttddto = new TT_TimeTableAllocationDetail();
                                TT_TimeTableAllocationDetailId ttdid = new TT_TimeTableAllocationDetailId();;
                                TT_MultiFacultyTeachingLoad mfdto = new TT_MultiFacultyTeachingLoad();
                                TT_MultiFacultyTeachingLoadId mfid = new TT_MultiFacultyTeachingLoadId();
                                PRFacultyStudentTagging prfstdto = null;
                                PRFacultyStudentTaggingId prfstid = null;
                                String parentstytypeid = daoFactory.getStudentRegistrationDAO().getStudentStytypeid(instituteid, parentregistrationid, studentid);
                                String ttreferenceid = "";
                                String tttransid = "";
                                String subjectcomponentid = "";
                                List l = new ArrayList();
                                List<TT_TimeTableAllocation> ttalist = (List<TT_TimeTableAllocation>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkTT_TimeTableAllocationData(instituteid, registrationid, acadyear, programid, sectionid, subsectionid, stytypeid, Integer.parseInt(stynumber), subjectid);
                                if (ttalist.isEmpty()) {
                                    ttalist = (List<TT_TimeTableAllocation>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkTT_TimeTableAllocationData(instituteid, parentregistrationid, acadyear, programid, sectionid, subsectionid, parentstytypeid, Integer.parseInt(stynumber), subjectid);
                                    if (ttalist.size() > 0) {
                                        for (int ii = 0; ii < ttalist.size(); ii++) {
                                            ttadto = ttalist.get(ii);
                                            tttransid = ttadto.getId().getTttransid().toString();
                                            ttreferenceid = ttadto.getTtreferenceid().toString();
                                            subjectcomponentid = ttadto.getSubjectcomponentid().toString();
                                            l.add(subjectcomponentid);
                                            newttaid.setRegistrationid(registrationid);
                                            newttaid.setTttransid(tttransid);
                                            newttaid.setInstituteid(ttadto.getId().getInstituteid());
                                            newttadto.setId(newttaid);
                                            newttadto.setStafftype(ttadto.getStafftype());
                                            newttadto.setStaffid(ttadto.getStaffid());
                                            newttadto.setSubjectid(ttadto.getSubjectid());
                                            newttadto.setSubjectcomponentid(ttadto.getSubjectcomponentid());
                                            newttadto.setRunningdepartmentid(ttadto.getRunningdepartmentid());
                                            newttadto.setMultifaculty(ttadto.getMultifaculty());
                                            newttadto.setStatus(ttadto.getStatus());
                                            newttadto.setDeactive(ttadto.getDeactive());
                                            newttadto.setEntryby(ttadto.getEntryby());
                                            newttadto.setEntrydate(new Date());
                                            newttadto.setTtreferenceid(ttreferenceid);
                                            newttadto.setEntrydate(new Date());
//                                            newttadto.setSlotid(ttadto.getSlotid());
//                                            newttadto.setRoomid(ttadto.getRoomid());
//                                            newttadto.setExamcenterid(ttadto.getExamcenterid());
//                                            newttadto.setAllocationday(ttadto.getAllocationday());
                                            newttadto.setFromsessiontime(ttadto.getFromsessiontime());
                                            newttadto.setTosessiontime(ttadto.getTosessiontime());
                                            daoFactory.getTt_TimeTableAllocationDAO().add(newttadto);
                                            ttdid.setInstituteid(instituteid);
                                            ttdid.setAcademicyear(acadyear);
                                            ttdid.setProgramid(programid);
                                            ttdid.setRegistrationid(registrationid);
                                            ttdid.setSectionid(sectionid);
                                            ttdid.setStynumber(new Byte(stynumber));
                                            ttdid.setStytypeid(stytypeid);
                                            ttdid.setSubsectionid(subsectionid);
                                            ttdid.setTttransid(tttransid);
                                            ttddto = (TT_TimeTableAllocationDetail) daoFactory.getTt_TimeTableAllocationDetailDAO().findByPrimaryKey(ttdid);
                                            if (ttddto == null) {
                                                ttdid.setStytypeid(parentstytypeid);
                                                ttdid.setRegistrationid(parentregistrationid);
                                                ttddto = (TT_TimeTableAllocationDetail) daoFactory.getTt_TimeTableAllocationDetailDAO().findByPrimaryKey(ttdid);
                                                ttdid = ttddto.getId();
                                                ttdid.setRegistrationid(registrationid);
                                                ttddto.setId(ttdid);
                                                ttdid.setStytypeid(stytypeid);
                                                ttddto.setEntrydate(new Date());
                                                daoFactory.getTt_TimeTableAllocationDetailDAO().add(ttddto);
                                            }
                                            List<TT_MultiFacultyTeachingLoad> mflist = (List<TT_MultiFacultyTeachingLoad>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkMultifacultyData(instituteid, registrationid, tttransid);
                                            if (mflist.isEmpty()) {
                                                mflist = (List<TT_MultiFacultyTeachingLoad>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkMultifacultyData(instituteid, parentregistrationid, tttransid);
                                                for (int mf = 0; mf < mflist.size(); mf++) {
                                                    mfdto = mflist.get(mf);
                                                    mfid = mfdto.getId();
                                                    mfid.setRegistrationid(registrationid);
                                                    mfdto.setId(mfid);
                                                    daoFactory.getTt_MultiFacultyTeachingLoadDAO().add(ttddto);
                                                }
                                            }
                                        }
                                    }
                                }
//                                    }
//                                }
//                                subComponentidList = daoFactory.getProgramSubjectDetailDAO().getSubjectComponentIds(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, stytypeid, basketid, stynumber);
//
//                                if (subComponentidList != null && !subComponentidList.isEmpty()) {
//                                    for (int j = 0; j < subComponentidList.size(); j++) {
                                Set<String> set = new LinkedHashSet<String>(l);
                                for (String subjectcomid : set) {
                                    List fstidList = daoFactory.getFacultySubjectTaggingDAO().checkIfFSTIDExists(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, styno, subjectcomponentid, stytypeid);
                                    if (fstidList == null || fstidList.isEmpty()) {
                                        facultySubjectTagging = new FacultySubjectTagging();
                                        facultySubjectTaggingId = new FacultySubjectTaggingId();
                                        fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
                                        facultySubjectTagging.setSubjectcomponentid(subjectcomid);
                                        facultySubjectTagging.setSubjectid(subjectid);
                                        facultySubjectTagging.setSubsectionid(subsectionid);
                                        facultySubjectTagging.setTtreferenceid(ttreferenceid);

                                        daoFactory.getFacultySubjectTaggingDAO().add(facultySubjectTagging);
                                        ++saverecordcount;
                                        saveListmap.put("chk" + i, true);
                                    } else {
                                        fstid = fstidList.get(0).toString();
                                        ++saverecordcount;
                                        saveListmap.put("chk" + i, true);
                                    }
                                    List<Map> auditEquivalentSubid = (List<Map>) daoFactory.getStudentSubjectChoiceMasterDAO().getAutidOrEquivalentsubjectid(instituteid, registrationid, subjectid, styno, studentid);
                                    f_StudentTagging = new FacultyStudentTagging();
                                    f_StudentTaggingId = new FacultyStudentTaggingId();
                                    studentfstid = businessService.generateId("SudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                    f_StudentTaggingId.setInstituteid(instituteid);
                                    f_StudentTaggingId.setStudentfstid(studentfstid);
                                    f_StudentTaggingId.setStudentid(studentid);
                                    f_StudentTagging.setId(f_StudentTaggingId);
                                    f_StudentTagging.setRegistrationid(registrationid);
                                    f_StudentTagging.setSubjectid(subjectid);
                                    f_StudentTagging.setFstid(fstid);
                                    f_StudentTagging.setEntrydate(new Date());
                                    f_StudentTagging.setEntryby(entryby);
                                    f_StudentTagging.setRegconfirmationdate(regConfDate);
                                    f_StudentTagging.setEquivalentsubjectid(auditEquivalentSubid.get(0).get("equivalentsubjectid").toString());
                                    f_StudentTagging.setAuditsubject(auditEquivalentSubid.get(0).get("auditsubject").toString());
                                    f_StudentTagging.setDeactive("N");
                                    prfstid = new PRFacultyStudentTaggingId();
                                    prfstid.setInstituteid(instituteid);
                                    prfstid.setFstid(fstid);
                                    prfstid.setStudentid(studentid);
                                    prfstdto = (PRFacultyStudentTagging) daoFactory.getPrFacultyStudentTaggingDAO().findByPrimaryKey(prfstid);
                                    if (prfstdto == null) {
                                        prfstdto = new PRFacultyStudentTagging();
                                        prfstdto.setId(prfstid);
                                        prfstdto.setRegistrationid(registrationid);
                                        prfstdto.setDeactive("N");
                                        daoFactory.getPrFacultyStudentTaggingDAO().add(prfstdto);
                                    }

                                    saveOrUpdateList.add(f_StudentTagging);
                                }
//                                } else {
//                                    ++errorrecordcount;
//                                }
                            }
                        } else {
                            err = new ArrayList<String>();
                            err.add("Please Deposit the remaining Fee Amount " + totalduefeeamount + " Rs.!");
                            return err;
                        }
                    }
                }
                businessService.insertInIdGenerationControl(saveOrUpdateList);
                if ("SUP".equalsIgnoreCase(stytype) || "RWJ".equalsIgnoreCase(stytype) || "SAP".equalsIgnoreCase(stytype)) {
                    for (int i = 0; i < count; i++) {
                        if (saveListmap.containsKey("chk" + i)) {
                            if (request.getParameter("chk" + i) != null) {
                                String[] ids = request.getParameter("chk" + i).split("~@~");
                                studentid = ids[0];
                                subjectid = ids[2];
                                stynumber = ids[1];
                                styno = new Byte(stynumber);
                                qryString = " update StudentSubjectChoiceMaster sscm set sscm.subjectrunning ='Y', sscm.supplimentryregistered ='" + status + "',sscm.supplimentryregisteredby='" + entryby + "' ,sscm.supplimentryregistereddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'),sscm.reregtype='Supplementry Reg.' where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "' and sscm.id.stynumber ='" + styno + "' and sscm.id.subjectid ='" + subjectid + "'";
                                updateList.add(qryString);
                                if (regConfirmation != null) {
                                    qryString = " update StudentRegistration sr set sr.preventfreezed ='Y',sr.prfreezeddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'), sr.regconfirmatiodate =to_date('" + format.format(regConfDate) + "','DD/MM/YYYY'),sr.regconfirmation ='" + regConfirmation + "' where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' and sr.id.registrationid ='" + registrationid + "' ";
                                } else {
                                    qryString = " update StudentRegistration sr set sr.preventfreezed ='Y',sr.prfreezeddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' and sr.id.registrationid ='" + registrationid + "' ";
                                }
                                updateList.add(qryString);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < count; i++) {
                        if (saveListmap.containsKey("chk" + i)) {
                            if (request.getParameter("chk" + i) != null) {
                                String[] ids = request.getParameter("chk" + i).split("~@~");
                                qryString = "";
                                studentid = ids[0];
                                subjectid = ids[2];
                                stynumber = ids[1];
                                styno = new Byte(stynumber);
                                qryString = " update StudentSubjectChoiceMaster sr set sr.subjectrunning ='Y' , sr.supplimentryregistered ='" + status + "',sr.supplimentryregisteredby='" + entryby + "' ,sr.supplimentryregistereddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'),sscm.reregtype='Supplementry Reg.' where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' "
                                        + " and sr.id.registrationid ='" + registrationid + "' and sr.id.stynumber ='" + styno + "' and sr.id.subjectid ='" + subjectid + "' ";
                                updateList.add(qryString);
                                if (regConfirmation != null) {
                                    qryString = " update StudentRegistration sr set sr.preventfreezed ='Y',sr.prfreezeddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'), sr.regconfirmatiodate =to_date('" + format.format(regConfDate) + "','DD/MM/YYYY'),sr.regconfirmation ='" + regConfirmation + "' where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' and sr.id.registrationid ='" + registrationid + "' ";
                                } else {
                                    qryString = " update StudentRegistration sr set sr.preventfreezed ='Y',sr.prfreezeddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' and sr.id.registrationid ='" + registrationid + "' ";
                                }
                                updateList.add(qryString);
                            }
                        }
                    }
                }
                daoFactory.getStudentSubjectChoiceMasterDAO().update_STR(updateList);
                err = new ArrayList<String>();
                err.add("Success");
                err.add(saverecordcount + " Record(s) Approved Successfully. " + (errorrecordcount > 0 ? (errorrecordcount + " Record(s) Not Found In FacultyStudentTagging Or No Subject Component Defined.") : ""));
            } else {
                int count = Integer.parseInt(request.getParameter("checkedCount2"));
                for (int i = 0; i < count; i++) {
                    if (request.getParameter("approvedchk" + i) != null) {
                        String[] ids = request.getParameter("approvedchk" + i).split("~@~");
                        qryString = "";
                        studentid = ids[0];
                        subjectid = ids[2];
                        acadyear = ids[3];
                        programid = ids[4];
                        sectionid = ids[5];
                        subsectionid = ids[6];
                        stytypeid = ids[7];
                        basketid = ids[8];
                        stynumber = ids[1];
                        styno = new Byte(stynumber);
                        styno = new Byte(stynumber);
                        subComponentidList = daoFactory.getProgramSubjectDetailDAO().getSubjectComponentIds(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, stytypeid, basketid, stynumber);

                        if (subComponentidList != null && !subComponentidList.isEmpty()) {
                            for (int j = 0; j < subComponentidList.size(); j++) {
                                List fstidList = daoFactory.getFacultySubjectTaggingDAO().checkIfFSTIDExists(instituteid, registrationid, subjectid, acadyear, programid, sectionid, subsectionid, styno, subComponentidList.get(j).toString(), stytypeid);
                                if (fstidList != null && !fstidList.isEmpty()) {
                                    for (int k = 0; k < fstidList.size(); k++) {
                                        List fstudentList = (List) daoFactory.getFacultyStudentTaggingDAO().find("from FacultyStudentTagging fst where fst.fstid='" + fstidList.get(k).toString() + "' and fst.id.instituteid='" + instituteid + "' and fst.id.studentid='" + studentid + "' ");
                                        if (fstudentList != null && !fstudentList.isEmpty()) {
                                            for (int l = 0; l < fstudentList.size(); l++) {
                                                deletelist.add(fstudentList.get(l));
                                                ++saverecordcount;
                                            }
                                        } else {
                                            ++errorrecordcount;
                                        }
                                    }
                                }
                            }
                        } else {
                            ++errorrecordcount;
                        }
                    }
                }
                businessService.insertupdateInIdGenerationControl(new ArrayList(), deletelist, false);
                for (int i = 0; i < count; i++) {
                    if (request.getParameter("approvedchk" + i) != null) {
                        String[] ids = request.getParameter("approvedchk" + i).split("~@~");
                        studentid = ids[0];
                        subjectid = ids[2];
                        stynumber = ids[1];
                        styno = new Byte(stynumber);
                        qryString = " update StudentSubjectChoiceMaster sscm set sscm.subjectrunning ='N', sscm.supplimentryregistered ='" + status + "',sscm.supplimentryregisteredby='" + entryby + "' ,sscm.supplimentryregistereddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY') where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "' and sscm.id.stynumber ='" + styno + "' and sscm.id.subjectid ='" + subjectid + "'";
                        updateList.add(qryString);
                    }
                }
                daoFactory.getStudentSubjectChoiceMasterDAO().update_STR(updateList);
                err = new ArrayList<String>();
                err.add("Success");
                err.add(saverecordcount + " Record(s) Cancelled Successfully. " + (errorrecordcount > 0 ? (errorrecordcount + " Record(s) Not Found In FacultyStudentTagging Or No Subject Component Defined.") : ""));
            }
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
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
            saveListmap = null;
            saveOrUpdateList = null;
            facultySubjectTagging = null;
            facultySubjectTaggingId = null;
            f_StudentTagging = null;
            f_StudentTaggingId = null;
            businessService.closeSession();
        }
        return err;
    }

    public List getSubjectWiseReportData(HttpServletRequest request) {
        String[] regids = request.getParameter("regCode").split("~@~");
        String registrationid = regids[0];
        String styType = request.getParameter("styType");
        List<Object[]> list = new ArrayList<Object[]>();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectWiseReportData(instituteid, registrationid, styType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
