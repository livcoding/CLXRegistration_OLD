/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.util.JIRPSession;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SummerSubjectRagistrationApprovalIservice;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import org.springframework.ui.ModelMap;
import java.util.Arrays;

/**
 *
 * @author malkeet.singh
 */
@Service
public class SummerSubjectRegistrationApprovalService implements SummerSubjectRagistrationApprovalIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void getInstituteCode(ModelMap model) {
        try {
            String maininstituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String userid = jirpsession.getJsessionInfo().getUserid();
            List list = daoFactory.getInstituteMasterDAO().getInstituteCodeForAddDrop(userid, "0000RTID1005A0Z0ZZZ9");
            String parametervalue = daoFactory.getRegistrationParametersDAO().getParametersValue(maininstituteid, "C1.1");
            model.put("parametervalue", parametervalue);
            model.put("institutelist", list);
            model.put("maininstituteid", maininstituteid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getSemesterCode(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        List<Object[]> list = null;
        List<Object[]> rtnlist = new ArrayList<Object[]>();
        try {
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForSummer(instituteids);
            if (list.size() > 0) {
                Map map = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    Object[] mainobj = new Object[5];
                    Object[] obj = list.get(i);
                    String registrationcode = obj[3].toString();
                    String key = obj[3].toString();
                    if (map.containsKey(key)) {
                        for (int j = 0; j < rtnlist.size(); j++) {
                            Object[] obj1 = rtnlist.get(j);
                            if (obj1[3].toString().equals(registrationcode)) {
                                mainobj[0] = obj[0].toString() + "," + obj1[0].toString(); // instituteid
                                mainobj[1] = obj[1].toString() + "/" + obj1[1].toString(); // institutecode
                                mainobj[2] = obj[2].toString() + "," + obj1[2].toString(); // registrationid
                                mainobj[3] = obj[3].toString();                            // registrationcode
                                if (!obj1[4].toString().equals(obj[4].toString())) {
                                    mainobj[4] = obj[4].toString() + "/" + obj1[4].toString(); // registrationdesc
                                } else {
                                    mainobj[4] = obj[4].toString(); // registrationdesc
                                }
                                rtnlist.remove(j);
                            }
                        }
                        rtnlist.add(mainobj);
                    } else {
                        map.put(registrationcode, registrationcode);
                        rtnlist.add(obj);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtnlist;
    }

    /**
     * Method Used To Get GridData For Pending For Approval Tab
     *
     */
    public Map getPendingForApprovalData(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteid = Arrays.asList(instids);
        String[] regids = request.getParameter("regCode").split("~@~")[0].split(",");
        List registrationid = Arrays.asList(regids);
        Map map = new HashMap();
        String styType = "";
        List list = null;
        String stytype = null;
        String status = null;
        try {
            String maininstituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            if (styType != null && !"".equals(styType)) {
                stytype = styType;
            } else {
                String parameterid = "SUBREG3.1";
                List parameter = daoFactory.getStudentSubjectChoiceDetailDAO().getBackPaperAttemptParameters(maininstituteid, parameterid);
                Map val = new HashMap();
                for (int j = 0; j < parameter.size(); j++) {
                    Map m = (Map) parameter.get(j);
                    val.put(m.get("parameterid"), m.get("parametervalue"));
                }
                stytype = (val.get("SUBREG3.1") == null ? "RWJ" : val.get("SUBREG3.1").toString());
                map.put("styTypevalue", stytype.toUpperCase());
            }
            list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instituteid, registrationid, status, stytype.toUpperCase(), "RWJ");
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
     */
    public List getApprovedData(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteid = Arrays.asList(instids);
        String[] regids = request.getParameter("regCode").split("~@~")[0].split(",");
        List registrationid = Arrays.asList(regids);
        String styType = request.getParameter("styType");
        List<Object[]> list = new ArrayList<Object[]>();
        String status = "A";
        try {
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instituteid, registrationid, status, styType, "RWJ");

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
     */
    public List getCanclledData(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteid = Arrays.asList(instids);
        String[] regids = request.getParameter("regCode").split("~@~")[0].split(",");
        List registrationid = Arrays.asList(regids);
        String styType = request.getParameter("styType");
        List<Object[]> list = new ArrayList<Object[]>();
        String status = "C";
        try {
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubjectRegistrationData(instituteid, registrationid, status, styType, "RWJ");

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
     * @return
     */
    public List approveCancleData(String status, HttpServletRequest request) {
        List err = null;
        String stytype = request.getParameter("styType");
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
        String departmentid = "";
        String mainmergewithsubsectioncode = "";
        List subComponentidList = null;
        FacultySubjectTagging facultySubjectTagging = null;
        FacultySubjectTaggingId facultySubjectTaggingId = null;
        FacultyStudentTagging f_StudentTagging = null;
        FacultyStudentTaggingId f_StudentTaggingId = null;
        byte styno = new Byte("0");
        Map saveListmap = new HashMap();
        List updateList = new ArrayList();
        List deletelist = new ArrayList();
        String qryString = null;
        int saverecordcount = 0;
        int errorrecordcount = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String entryby = jirpsession.getJsessionInfo().getMemberid();
        BusinessService businessService = null;
        String instituteid = "";
        String registrationid = "";
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
                        instituteid = ids[9];
                        registrationid = ids[10];
                        departmentid = ids[11];
                        mainmergewithsubsectioncode = ids[12];
                        styno = new Byte(stynumber);
                        String maininstituteid = "";
                        String mainregistrationid = "";
                        String mainsubjectid = "";
                        List<Map> list = (List<Map>) daoFactory.getStudentSubjectChoiceMasterDAO().getMainInstidRegidSubid(instituteid, registrationid, subjectid);
                        if (list.size() > 0) {
                            Map m = list.get(0);
                            maininstituteid = m.get("maininstituteid").toString();
                            mainregistrationid = m.get("mainregistrationid").toString();
                            mainsubjectid = m.get("mainsubjectid").toString();
                        }
                        List dueAmount = (List) daoFactory.getdBDependentDAO().checkStudentDueAmount(instituteid, studentid, registrationid);
                        BigDecimal feedueamount = new BigDecimal(dueAmount.get(0).toString());
                        String parfeeheadcode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.6");
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

                                TT_TimeTableAllocation newttadto = new TT_TimeTableAllocation();
                                TT_TimeTableAllocationId newttaid = new TT_TimeTableAllocationId();
                                TT_TimeTableAllocationDetail ttddto = new TT_TimeTableAllocationDetail();
                                TT_TimeTableAllocationDetailId ttdid = new TT_TimeTableAllocationDetailId();
                                PRFacultyStudentTagging prfstdto = null;
                                PRFacultyStudentTaggingId prfstid = null;

                                boolean updateChildBatches = false;
                                List<Object[]> ttalocationlist = new ArrayList<Object[]>();
                                //maininstituteid is "" means it belong to maininstitue
                                if (maininstituteid.equals("")) {
                                    updateChildBatches = true;
                                    // Means this student belongs to MainInstitute
                                    ttalocationlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkDataInTTAllocation(instituteid, registrationid, subjectid);
                                    // if main batch not found check its child subject batch..
                                    if (ttalocationlist.size() == 0) {
                                        String childinstituteid = "";
                                        String childregistrationid = "";
                                        String childsubjectid = "";
                                        List<Map> childlist = (List<Map>) daoFactory.getStudentSubjectChoiceMasterDAO().getChildInstidRegidSubid(instituteid, registrationid, subjectid);
                                        // Check All Child institute Batch by loop
                                        for (Map m : childlist) {
                                            childinstituteid = m.get("childinstituteid").toString();
                                            childregistrationid = m.get("childregistrationid").toString();
                                            childsubjectid = m.get("childsubjectid").toString();
                                            ttalocationlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkDataInTTAllocation(childinstituteid, childregistrationid, childsubjectid);
                                            if (ttalocationlist.size() > 0) {
                                                //if Batch Found.. Break..
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    // Means this student belongs to Child Institute merge with MainInstitute,MainSubject
                                    // Get Child InstituteBatch information
                                    ttalocationlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkDataInTTAllocation(instituteid, registrationid, subjectid);
                                    //  if ChildInstitute batch not found
                                    // Check MainInstitute Batch
                                    if (ttalocationlist.size() == 0) {
                                        ttalocationlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkDataInTTAllocation(maininstituteid, mainregistrationid, mainsubjectid);
                                    }
                                }
                                if (ttalocationlist.size() == 0) {
                                    // If Main,Child Batch not found 
                                    // Get Data from OfferedSubjectTagging for Batch
                                    ttalocationlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().checkDataInOfferSubTaggingDetail(instituteid, registrationid, subjectid);
                                    if (ttalocationlist.size() == 0) {
                                        err = new ArrayList<String>();
                                        err.add("Offered Subject Tagging not found...!");
                                        return err;
                                    }
                                }
                                for (int l = 0; l < ttalocationlist.size(); l++) {
                                    List saveOrUpdateList = new ArrayList();
                                    List saveOrUpdateTTA = new ArrayList();
                                    List saveOrUpdateFST = new ArrayList();
                                    businessService = new BusinessService(daoFactory, true);
                                    Object[] obj = ttalocationlist.get(l);
                                    String staffid = obj[0] != null ? obj[0].toString() : "";
                                    String stafftype = obj[1] != null ? obj[1].toString() : "";
                                    String subjectcomponentid = obj[2] != null ? obj[2].toString() : "";
                                    String depid = obj[3] != null ? obj[3].toString() : "";
                                    String tttransid = obj[4] != null ? obj[4].toString() : "";
                                    String ttreferenceid = obj[5] != null ? obj[5].toString() : "";
                                    String instid = obj[6] != null ? obj[6].toString() : "";
                                    String subjectcomponentcode = obj[7] != null ? obj[7].toString() : "";
                                    String subcomid = daoFactory.getSubjectComponentDAO().getComponentIdByCode(instituteid, subjectcomponentcode);
                                    boolean genid = false;
                                    if (tttransid.equals("empty")) {
                                        tttransid = businessService.generateId("TTTransactionId", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                        ttreferenceid = businessService.generateId("TTReferenceID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                        genid = true;
                                    }
                                    newttaid.setRegistrationid(registrationid);
                                    newttaid.setTttransid(tttransid);
                                    newttaid.setInstituteid(instituteid);
                                    newttadto.setId(newttaid);
                                    newttadto.setStafftype(stafftype);
                                    newttadto.setStaffid(staffid);
                                    newttadto.setSubjectid(subjectid);
                                    newttadto.setSubjectcomponentid(subcomid);
                                    newttadto.setRunningdepartmentid(departmentid);
                                    newttadto.setMultifaculty("N");
                                    newttadto.setStatus("A");
                                    newttadto.setDeactive("N");
                                    newttadto.setEntryby(entryby);
                                    newttadto.setEntrydate(new Date());
                                    newttadto.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                    newttadto.setTtreferenceid(ttreferenceid);
                                    if (genid) {
                                        saveOrUpdateTTA.add(newttadto);
                                        businessService.insertInIdGenerationControl(saveOrUpdateTTA);
                                        businessService = new BusinessService(daoFactory, true);
                                    } else {
                                        daoFactory.getTt_TimeTableAllocationDAO().add(newttadto);
                                    }
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
                                    String mergecode = "";
                                    if (ttddto == null) {
                                        List<Object[]> mergewithlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().getMergedWithCode(instituteid, registrationid, tttransid);
                                        if (mergewithlist.size() > 0) {
                                            for (Object[] o : mergewithlist) {
                                                if (mergewithlist.size() > 0) {
                                                    //if it belong with MainBatch get 0 index mergewith 
                                                    mergecode = o[0].toString();
                                                }
                                            }
                                        } else {
                                            List<Object[]> mergewithlist1 = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDetailDAO().getMergedWithCode(maininstituteid, mainregistrationid, tttransid);
                                            for (Object[] o : mergewithlist1) {
                                                if (mergewithlist1.size() > 0) {
                                                    //if it belong with ChildBatch Create a new MergeWith with 1,2,3,4,5 index By MainBatch
                                                    mergecode = o[1].toString() + "/" + o[2].toString() + "/" + o[3].toString() + "/" + o[4].toString() + "/" + o[5].toString();
                                                }
                                            }
                                        }
                                        ttddto = new TT_TimeTableAllocationDetail();
                                        ttddto.setId(ttdid);
                                        ttddto.setBasketid(basketid);
                                        ttddto.setEntrydate(new Date());
                                        ttddto.setDeactive("N");
                                        ttddto.setMergewithsubsectioncode(mergecode);
                                        ttddto.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                        daoFactory.getTt_TimeTableAllocationDetailDAO().add(ttddto);
                                    }
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
                                        facultySubjectTagging.setSubjectcomponentid(subcomid);
                                        facultySubjectTagging.setSubjectid(subjectid);
                                        facultySubjectTagging.setSubsectionid(subsectionid);
                                        facultySubjectTagging.setTtreferenceid(ttreferenceid);
                                        saveOrUpdateFST.add(facultySubjectTagging);
                                        businessService.insertInIdGenerationControl(saveOrUpdateFST);
                                        businessService = new BusinessService(daoFactory, true);
//                                        daoFactory.getFacultySubjectTaggingDAO().add(facultySubjectTagging);
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
                                    saveOrUpdateList.add(f_StudentTagging);
                                    businessService.insertInIdGenerationControl(saveOrUpdateList);
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
                                    if (updateChildBatches) {
                                        daoFactory.getTt_TimeTableAllocationDetailDAO().updateMergeWithSecSubsection(instituteid, tttransid, mainmergewithsubsectioncode);
                                    }
                                }
                            }
                        } else {
                            businessService = new BusinessService(daoFactory, true);
                            err = new ArrayList<String>();
                            err.add("Please Deposit the remaining Fee Amount " + totalduefeeamount + " Rs.!");
                            return err;
                        }
                    }
                }
                if ("SUP".equalsIgnoreCase(stytype) || "RWJ".equalsIgnoreCase(stytype) || "SAP".equalsIgnoreCase(stytype)) {
                    for (int i = 0; i < count; i++) {
                        if (saveListmap.containsKey("chk" + i)) {
                            if (request.getParameter("chk" + i) != null) {
                                String[] ids = request.getParameter("chk" + i).split("~@~");
                                studentid = ids[0];
                                subjectid = ids[2];
                                stynumber = ids[1];
                                styno = new Byte(stynumber);
                                qryString = " update StudentSubjectChoiceMaster sscm set sscm.subjectrunning ='Y', sscm.supplimentryregistered ='" + status + "',sscm.supplimentryregisteredby='" + entryby + "' ,sscm.supplimentryregistereddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'),sscm.reregtype='Summer Reg.' where sscm.id.instituteid='" + instituteid + "' and sscm.id.studentid ='" + studentid + "' and sscm.id.registrationid ='" + registrationid + "' and sscm.id.stynumber ='" + styno + "' and sscm.id.subjectid ='" + subjectid + "'";
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
                                qryString = " update StudentSubjectChoiceMaster sr set sr.subjectrunning ='Y' , sr.supplimentryregistered ='" + status + "',sr.supplimentryregisteredby='" + entryby + "' ,sr.supplimentryregistereddate=to_date('" + format.format(new Date()) + "','DD/MM/YYYY'),sscm.reregtype='Summer Reg.' where sr.id.instituteid='" + instituteid + "' and sr.id.studentid ='" + studentid + "' "
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
                        instituteid = ids[9];
                        registrationid = ids[10];
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
                businessService = new BusinessService(daoFactory, true);
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
            facultySubjectTagging = null;
            facultySubjectTaggingId = null;
            f_StudentTagging = null;
            f_StudentTaggingId = null;
            if (businessService != null) {
                businessService.closeSession();
            }
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
