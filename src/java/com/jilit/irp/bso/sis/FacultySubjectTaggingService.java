package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.FacultySubjectTaggingIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.persistence.dto.TT_SlotMaster;
import com.jilit.irp.persistence.dto.TT_TimeTable;
import com.jilit.irp.persistence.dto.TT_TimeTableId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocation;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetailId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ankit.kumar
 */
@Service
public class FacultySubjectTaggingService implements FacultySubjectTaggingIService {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getRegistraionCode(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List list = (List) daoFactory.getUtilDAO().findSimpleData("findAllRegistrationMaster", new Object[]{instituteid});
            model.addAttribute("reg_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDepartmentCode(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List list1 = (List) daoFactory.getDepartmentMasterDAO().getAllDepartment();
            List list2 = (List) daoFactory.getStyTypeDAO().getStyTypeData(instituteid);
            model.addAttribute("depart_list", list1);
            model.addAttribute("stytype_list", list2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getSubject_FacultyCode(HttpServletRequest req
    ) {
        List subjectlist = new ArrayList();
        List facultylist = new ArrayList();
        List finalList = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = req.getParameter("registrationid").split(",")[0];
        String academicyear = req.getParameter("academicyear");
        String departmentid = req.getParameter("departmentid");
        try {
            subjectlist = (List) daoFactory.getSubjectMasterDAO().getSubjectCodeAcademicyearWise(instituteid, registrationid, academicyear, departmentid);
            facultylist = (List) daoFactory.getSubjectCoordinatorDAO().getCoordinator(instituteid, departmentid, "S");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finalList.add(subjectlist);
        finalList.add(facultylist);
        return finalList;
    }

    @Override
    public List getComponentCode(HttpServletRequest req
    ) {
        List list = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = req.getParameter("registrationid").split(",")[0];
        String academicyear = req.getParameter("academicyear");
        String subjectid = req.getParameter("subjectid");
        try {
            list = (List) daoFactory.getSubjectMasterDAO().getComponentCodeForFST(instituteid, registrationid, academicyear, subjectid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAcadmicyear(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid_arr[] = request.getParameter("regId").split(",");
        List list = null;
        try {
            list = daoFactory.getStudentRegistrationDAO().getAcadmicyearForFst(instituteid, regid_arr[0]);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSemesterCode(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid_arr[] = request.getParameter("regId").split(",");
        String acadYear = request.getParameter("acadYear");
        List list = null;
        try {
            list = daoFactory.getStyDescDAO().getAllStyNumber_Fst(instituteid, regid_arr[0], acadYear);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getProgramCode(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid_arr[] = request.getParameter("regId").split(",");
        String acadYear = request.getParameter("acadYear");
        List list = null;
        try {
            list = daoFactory.getProgramMasterDAO().getProgramForSubjectFst(instituteid, regid_arr[0], acadYear);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSectionCode(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid_arr[] = request.getParameter("regId").split(",");
        String acadYear = request.getParameter("acadYear");
        String programId = request.getParameter("programId");
        List list = null;
        try {
            list = daoFactory.getSectionMasterDAO().getAllSectionMaster_Fst(instituteid, regid_arr[0], programId, acadYear);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    public List getFstList(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String regid_arr[] = request.getParameter("regId").split(",");
        String acadYear = request.getParameter("acadYear");
        String subjectid = request.getParameter("sub_id");
        String subjectcomponentid = request.getParameter("subcom_id");
        String stytype_id = request.getParameter("stytype_id");
        String programId = request.getParameter("programId");
        String sec_id[] = request.getParameter("sec_id").split(",");
        ArrayList<String> aList = new ArrayList<String>(Arrays.asList(sec_id));
        String sty_id = request.getParameter("sty_num");
        String dep_id = request.getParameter("dep_id");
        String radioValue = request.getParameter("radioValue"); //value is undefined beacuse radiovalue radiobutton is commented in jsp
        List list = null;
        try {
            List l = daoFactory.getLoadDistributionGrantDAO().checkLoadDistributionStatus(instituteid, regid_arr[0], dep_id);
            if (l != null && l.size() > 0) {
                if (radioValue.equals("B")) {
                    list = daoFactory.getProgramSubjectTaggingDAO().getData(instituteid, acadYear, regid_arr[0], programId, aList, Short.parseShort(sty_id));
                } else {
                    list = daoFactory.getProgramSubjectTaggingDAO().getSubjectWiseFSTData(instituteid, acadYear, regid_arr[0], subjectid, subjectcomponentid, stytype_id);
                }
            } else {
                list = new ArrayList<String>();
                list.add("NotApprove");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    public List getSaveGridata(HttpServletRequest request) {
        List err = null;
        boolean msg = false;
        String fstid = "";
        String stytype = "";
        String staffid = jirpsession.getJsessionInfo().getMemberid();
        String stafftype = jirpsession.getJsessionInfo().getUsertype();
        String tttransid = "";
        String Instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String unique_id = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
        FacultySubjectTagging facultySubjectTagging = null;
        FacultySubjectTaggingId facultySubjectTaggingId = null;
        List facultySubjectTaggingList = new ArrayList();
        String registrationidMain = "";
        BusinessService businessService = new BusinessService(daoFactory);
        int count = Integer.parseInt(request.getParameter("checkedCount"));
        try {
            stytype = daoFactory.getStyTypeDAO().getStytypeId(Instituteid, "REG");
            for (int i = 0; i <= count; i++) {
                if (request.getParameter("chk" + i) != null) {
                    err = new ArrayList<String>();
                    String pks[] = request.getParameter("chk" + i).split("~@~");
                    String academicyear = pks[0].toString();
                    String programid = pks[1].toString();
                    String sectionid = pks[2].toString();
                    String stynumber = pks[3].toString();
                    String basketid = pks[4].toString();
                    String subsectionid = pks[5].toString();
                    String subjectid = pks[6].toString();
                    String subjectcomponentid = pks[7].toString();
                    String registrationid = pks[8].toString();
                    String subjecttype = pks[10].equals("null") ? "" : pks[10].toString();
                    String subjecttypeid = pks[11].equals("null") ? "" : pks[11].toString();
                    registrationidMain = registrationid;
                    try {
                        if (1 == 1) {
                            facultySubjectTagging = new FacultySubjectTagging();
                            facultySubjectTaggingId = new FacultySubjectTaggingId();
                            fstid = businessService.generateId("FSTID", unique_id, "I", false);
                            facultySubjectTaggingId.setFstid(fstid);
                            facultySubjectTaggingId.setInstituteid(Instituteid);
                            facultySubjectTagging.setId(facultySubjectTaggingId);
                            facultySubjectTagging.setStytypeid(stytype);
                            facultySubjectTagging.setAcademicyear(academicyear);
                            facultySubjectTagging.setBasketid(basketid);
                            facultySubjectTagging.setDeactive("N");
                            facultySubjectTagging.setProgramid(programid);
                            facultySubjectTagging.setRegistrationid(registrationid);
                            facultySubjectTagging.setSectionid(sectionid);
                            facultySubjectTagging.setStynumber(Byte.parseByte(stynumber));
                            facultySubjectTagging.setSubjectcomponentid(subjectcomponentid);
                            facultySubjectTagging.setSubjectid(subjectid);
                            facultySubjectTagging.setSubsectionid(subsectionid);
                            facultySubjectTagging.setTtreferenceid(tttransid);
                            facultySubjectTagging.setSubjecttype(subjecttype);
                            facultySubjectTagging.setSubjecttypeid(subjecttypeid);
                            facultySubjectTaggingList.add(facultySubjectTagging);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        businessService.rollback();
                    }
                }
            }
            businessService.insertInIdGenerationControl(facultySubjectTaggingList);

            if (request.getParameter("subjectFinalization") != null) {
                try {
                    if (!registrationidMain.equals("")) {
                        // Calling Time Table Allocation Package (Oracle-Package - SIS)
                        String mSlotID = "";
                        TT_TimeTable tt = (TT_TimeTable) daoFactory.getTt_TimeTableDAO().findByPrimaryKey(new TT_TimeTableId(Instituteid, registrationidMain));
                        TT_SlotMaster tt_sm = null;
                        if (tt == null) {
                            msg = saveTT_TimeTableRecord(Instituteid, registrationidMain);
                        }
                        List<Object> sml = (List<Object>) daoFactory.getTt_SlotMasterDAO().find(" from TT_SlotMaster where id.instituteid = '" + Instituteid + "' and registrationid = '" + registrationidMain + "' and rownum = 1 order by id.slotid desc  ");
                        if (sml != null && sml.size() > 0) {
                            tt_sm = (TT_SlotMaster) sml.get(0);
                            mSlotID = tt_sm.getId().getSlotid();
                        } else {
                            mSlotID = "NULL";
                        }
                        String st = daoFactory.getStoredProcedureDAO().saveTTAllocationData(Instituteid, registrationidMain, "MON", stafftype, staffid, "09:00 AM", "11:00 AM", mSlotID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            err = new ArrayList<String>();
            err.add("Error");
            err.add(e.getMessage());
        } finally {
            businessService.closeSession();
        }
        return err;
    }

    @Override
    public List saveFacultySubjectTagging(HttpServletRequest request) {
        List list = new ArrayList();
        FacultySubjectTagging fst = new FacultySubjectTagging();
        FacultySubjectTaggingId fstId = new FacultySubjectTaggingId();
        TT_TimeTableAllocation tta = new TT_TimeTableAllocation();
        TT_TimeTableAllocationId ttaId = new TT_TimeTableAllocationId();
        TT_TimeTableAllocationDetail ttad = new TT_TimeTableAllocationDetail();
        TT_TimeTableAllocationDetailId ttadId = new TT_TimeTableAllocationDetailId();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String unique_id = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
        String departmentid = request.getParameter("dep_id");
        String facultyid = request.getParameter("faculty_id").split("~@~")[0];
        String stafftype = request.getParameter("faculty_id").split("~@~")[1];
        BusinessService businessService = null;
        int count = Integer.parseInt(request.getParameter("checkedCount"));
        String stytype_id = request.getParameter("stytype_id");
        String newfstid = "";
        String ttreferenceid = "";
        String tttransid = "";
        List err = new ArrayList<String>();
        try {
            for (int i = 0; i <= count; i++) {
                if (request.getParameter("chk" + i) != null) {
                    err = new ArrayList<String>();
                    String pks[] = request.getParameter("chk" + i).split("~@~");
                    String academicyear = pks[0].toString();
                    String programid = pks[1].toString();
                    String sectionid = pks[2].toString();
                    String stynumber = pks[3].toString();
                    String basketid = pks[4].toString();
                    String subsectionid = pks[5].toString();
                    String subjectid = pks[6].toString();
                    String subjectcomponentid = pks[7].toString();
                    String registrationid = pks[8].toString();
                    String subjecttype = pks[10].equals("null") ? "" : pks[10].toString();
                    String subjecttypeid = pks[11].equals("null") ? "" : pks[11].toString();
                    try {
                        List l = daoFactory.getFacultySubjectTaggingDAO().checkFSTExistOrNot(instituteid, registrationid, subjectid, academicyear, programid, new Byte(stynumber), sectionid, subsectionid, stytype_id, subjectcomponentid, basketid);
                        if (l != null && l.size() > 0) {
                            newfstid = l.get(0).toString();
                        } else {
                            fst = new FacultySubjectTagging();
                            fstId = new FacultySubjectTaggingId();
                            businessService = new BusinessService(daoFactory);
                            newfstid = businessService.generateId("FSTID", unique_id, "I", false);
                            businessService.updateInIdGenerationControl();
                            fstId.setFstid(newfstid);
                            fstId.setInstituteid(instituteid);
                            fst.setId(fstId);
                            fst.setStytypeid(stytype_id);
                            fst.setAcademicyear(academicyear);
                            fst.setBasketid(basketid);
                            fst.setDeactive("N");
                            fst.setProgramid(programid);
                            fst.setRegistrationid(registrationid);
                            fst.setSectionid(sectionid);
                            fst.setStynumber(Byte.parseByte(stynumber));
                            fst.setSubjectcomponentid(subjectcomponentid);
                            fst.setSubjectid(subjectid);
                            fst.setSubsectionid(subsectionid);
                            fst.setSubjecttype(subjecttype);
                            fst.setSubjecttypeid(subjecttypeid);

                            List<Object[]> ttalist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDAO().checkTTAllocation(instituteid, registrationid, subjectid, subjectcomponentid, facultyid);
                            if (ttalist != null && ttalist.size() > 0) {
                                tttransid = ttalist.get(0)[0].toString();
                                ttreferenceid = ttalist.get(0)[1] != null ? ttalist.get(0)[1].toString() : "";
                            } else {
                                businessService = new BusinessService(daoFactory);
                                tttransid = businessService.generateId("TTTransactionId", unique_id, "I", false);
                                ttreferenceid = businessService.generateId("TTReferenceID", unique_id, "I", false);
                                businessService.updateInIdGenerationControl();
                                ttaId.setTttransid(tttransid);
                                ttaId.setRegistrationid(registrationid);
                                ttaId.setInstituteid(instituteid);
                                tta.setId(ttaId);
                                tta.setSubjectid(subjectid);
                                tta.setSubjectcomponentid(subjectcomponentid);
                                tta.setRunningdepartmentid(departmentid);
                                tta.setStaffid(facultyid);
                                tta.setStafftype(stafftype);
                                tta.setStatus("A");
                                tta.setTtreferenceid(ttreferenceid);
                                tta.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                tta.setEntrydate(new Date());
                                tta.setDeactive("N");
                                tta.setMultifaculty("N");
                                daoFactory.getTt_TimeTableAllocationDAO().saveOrUpdate(tta);
                            }
                            fst.setTtreferenceid(ttreferenceid);
                            daoFactory.getFacultySubjectTaggingDAO().saveOrUpdate(fst);

                            List<Object[]> ttadlist = (List<Object[]>) daoFactory.getTt_TimeTableAllocationDAO().checkTTAllocationDetail(instituteid, registrationid, subjectid, subjectcomponentid, academicyear, programid, sectionid, subsectionid, stynumber, stytype_id);
                            if (ttadlist != null && ttadlist.size() > 0) {
                            } else {
                                ttadId.setInstituteid(instituteid);
                                ttadId.setProgramid(programid);
                                ttadId.setRegistrationid(registrationid);
                                ttadId.setSectionid(sectionid);
                                ttadId.setStynumber(Integer.parseInt(stynumber));
                                ttadId.setStytypeid(stytype_id);
                                ttadId.setSubsectionid(subsectionid);
                                ttadId.setTttransid(tttransid);
                                ttadId.setAcademicyear(academicyear);
                                ttad.setEntrydate(new Date());
                                ttad.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                ttad.setId(ttadId);
                                ttad.setBasketid(basketid);
                                ttad.setDeactive("N");
                                daoFactory.getTt_TimeTableAllocationDetailDAO().saveOrUpdate(ttad);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        businessService.rollback();
                        list = new ArrayList<String>();
                        list.add("Error");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        list = new ArrayList<String>();
        list.add("Success");
        return list;
    }

    private boolean saveTT_TimeTableRecord(String Instituteid, String registrationid) {
        TT_TimeTable tt = new TT_TimeTable();
        TT_TimeTableId ttid = new TT_TimeTableId();
        boolean err = false;
        try {
            ttid.setInstituteid(Instituteid);
            ttid.setRegistrationid(registrationid);
            tt.setId(ttid);
            tt.setStartdate(new Date());
            tt.setEnddate(new Date());
            tt.setApprovedby(jirpsession.getJsessionInfo().getUserid());
            tt.setApproveddate(new Date());
            tt.setDeactive("N");
            tt.setEntrydate(new Date());
            daoFactory.getFacultySubjectTaggingDAO().saveOrUpdate(tt);
            err = true;
        } catch (Exception e) {
            e.printStackTrace();
            err = false;
        }
        return err;
    }
}
