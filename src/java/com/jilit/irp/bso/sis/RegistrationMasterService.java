/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.RegistrationMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.ExamGradeMaster;
import com.jilit.irp.persistence.dto.LoadDistributionGrant;
import com.jilit.irp.persistence.dto.LoadDistributionGrantId;
import com.jilit.irp.persistence.dto.ExamGradeMasterDetail;
import com.jilit.irp.persistence.dto.ExamGradeMasterDetailId;
import com.jilit.irp.persistence.dto.RegistrationMasterId;
import org.springframework.stereotype.Service;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class RegistrationMasterService implements RegistrationMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getRegistrationMaster(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationMaster(instituteid);
        model.addAttribute("data", list);
    }

    @Override
    public void getRegistrationMasterList(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationCodeGridData(instituteid);
        model.addAttribute("data", list);
    }

    public void getAcademicyearProgramList(Model model) {
        List regList = null;
        List<Academicyear> acadList = null;
        List progList = null;
        List semCode = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        acadList = (List<Academicyear>) daoFactory.getAcademicYearDAO().findAll(instituteid);
        progList = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
        semCode = (List) daoFactory.getProgramMasterDAO().getSemesterCode(instituteid);
        model.addAttribute("acadList", acadList);
        model.addAttribute("progList", progList);
        model.addAttribute("semCode", semCode);
    }

    @Override
    public List addRegistrationMaster(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        BusinessService businessService = new BusinessService(daoFactory);
        String generatedid = "";
        String instiuteuniqueid = null;
        RegistrationMaster registrationMaster = new RegistrationMaster();
        List err = null;
        try {
            instiuteuniqueid = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
            registrationMaster.setRegistrationdatefrom(df.parse(request.getParameter("fromdate")));
            registrationMaster.setRegistrationdateto(df.parse(request.getParameter("todate")));
            if (request.getParameter("suppFromDate") != null && !request.getParameter("suppFromDate").equals("")) {
                registrationMaster.setSupplregeventfrom(df.parse(request.getParameter("suppFromDate")));
            }
            if (request.getParameter("suppToDate") != null && !request.getParameter("suppToDate").equals("")) {
                registrationMaster.setSupplregeventupto(df.parse(request.getParameter("suppToDate")));
            }
            if (request.getParameter("regEventFrom") != null && !request.getParameter("regEventFrom").equals("")) {
                registrationMaster.setPreventfromdate(df.parse(request.getParameter("regEventFrom")));
            }
            if (request.getParameter("regEventTo") != null && !request.getParameter("regEventTo").equals("")) {
                registrationMaster.setPreventenddate(df.parse(request.getParameter("regEventTo")));
            }
            registrationMaster.setExamperiodfrom(df.parse(request.getParameter("examPerioddate")));
            if (request.getParameter("attPerFromdate") != null) {
                registrationMaster.setAttendancefromdate(df.parse(request.getParameter("attPerFromdate")));
            }
            if (request.getParameter("attPerTodate") != null) {
                registrationMaster.setAttendancetodate(df.parse(request.getParameter("attPerTodate")));
            } else {
                registrationMaster.setAttendancetodate(null);
            }
            if (request.getParameter("courseRegFromdate") != null && !request.getParameter("courseRegFromdate").equals("")) {
                registrationMaster.setCourseregistrationdatefrom(df.parse(request.getParameter("courseRegFromdate")));
            }
            if (request.getParameter("courseRegTodate") != null && !request.getParameter("courseRegTodate").equals("")) {
                registrationMaster.setCourseregistrationdateto(df.parse(request.getParameter("courseRegTodate")));
            }
            if (request.getParameter("gipRegFrom") != null && !request.getParameter("gipRegFrom").equals("")) {
                registrationMaster.setGipregfrom(df.parse(request.getParameter("gipRegFrom")));
            }
            if (request.getParameter("gipRegTo") != null && !request.getParameter("gipRegTo").equals("")) {
                registrationMaster.setGipregistrationupto(df.parse(request.getParameter("gipRegTo")));
            }
            registrationMaster.setGipregbroadcast(request.getParameter("gipRegBroadcast"));
            registrationMaster.setRegistrationcode(request.getParameter("registrationCode").trim());
            registrationMaster.setRegistrationdesc(request.getParameter("registrationDesc"));
            registrationMaster.setRegistrationcaption(request.getParameter("registrationCaption"));
            registrationMaster.setPlaceofregistration(request.getParameter("placeOfRegistration"));
            registrationMaster.setEvenoddflag(new Byte(request.getParameter("oddEven")));
            registrationMaster.setRegistrationtype(request.getParameter("registrationType"));
            registrationMaster.setAcademicyear(request.getParameter("academicYearTitle"));
            registrationMaster.setGradesheetcaption(request.getParameter("gradeSheetTitle"));
            registrationMaster.setFinalized(request.getParameter("finStatus"));
            registrationMaster.setFinalizedby(jirpsession.getJsessionInfo().getMemberid());
            registrationMaster.setFinalizeddate(new Date());
            registrationMaster.setExcludeinprereg(request.getParameter("excludePreReg"));
            registrationMaster.setPreventcompleted(request.getParameter("eventComplet"));
            registrationMaster.setPreventbroadcast(request.getParameter("eventBroadcast"));
            registrationMaster.setCourseregcompleted(request.getParameter("courseRegComplet"));
            registrationMaster.setCourseregbroadcast(request.getParameter("courseRegBroadcast"));
            registrationMaster.setExcludeinsrs(request.getParameter("excludeSrs"));
            registrationMaster.setExcludeinattendance(request.getParameter("excludeAttandance"));
            registrationMaster.setExcludeinclasstimetable(request.getParameter("excludeTimeTable"));
            registrationMaster.setExcludeindatesheet(request.getParameter("excludeDateSheet"));
            registrationMaster.setExcludeininvigdutyseatplan(request.getParameter("excludeInvgDuty"));
            registrationMaster.setRegistrationcomplete(request.getParameter("regComplete"));
            registrationMaster.setLockregistration(request.getParameter("lockReg"));
            registrationMaster.setParentregistrationid(request.getParameter("registrationid"));
            if (request.getParameter("allowBackPaper") != null) {
                registrationMaster.setAllowbackpaperreg(request.getParameter("allowBackPaper"));
            } else {
                registrationMaster.setAllowbackpaperreg("N");
            }
            if (request.getParameter("deactive") != null) {
                registrationMaster.setDeactive(request.getParameter("deactive"));
            } else {
                registrationMaster.setDeactive("Y");
            }

            RegistrationMasterId id = new RegistrationMasterId();
            generatedid = businessService.generateId("RegID", instiuteuniqueid, "I", false);
            id.setRegistrationid(generatedid);
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            registrationMaster.setId(id);
            err = daoFactory.getRegistrationMasterDAO().doValidate(registrationMaster, "normal");
            if (err.size() > 0) {
                businessService.rollback();
                return err;
            }
            businessService.insertInIdGenerationControl(registrationMaster);

            try {
                if (request.getParameter("examGrade") != null) {
                    List<ExamGradeMaster> examlist = new ArrayList<ExamGradeMaster>();
                    examlist = (List<ExamGradeMaster>) daoFactory.getExamGradeMasterDAO().findAll(jirpsession.getJsessionInfo().getSelectedinstituteid());
                    ExamGradeMasterDetail ddto = new ExamGradeMasterDetail();
                    ExamGradeMasterDetailId did = new ExamGradeMasterDetailId();
                    String pgid = request.getParameter("programid");
                    String[] programid = pgid.split(",");
                    for (int i = 0; i < examlist.size(); i++) {
                        ExamGradeMaster dto = null;
                        dto = (ExamGradeMaster) examlist.get(i);
                        did.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
                        did.setRegistrationid(generatedid);
                        did.setGradeid(dto.getId().getGradeid());
                        did.setProgramid(request.getParameter("programid"));
                        did.setAcademicyear(request.getParameter("academicyear"));

                        ddto.setId(did);
                        ddto.setCgpacalculationbasedon("C");
                        ddto.setSgpacalculationbasedon("C");
                        ddto.setDeactive(dto.getDeactive());
                        ddto.setEntrydate(new Date());
                        daoFactory.getExamGradeMasterDetailDAO().add(ddto);
                    }
                }

                if (request.getParameter("loadDistribution") != null) {
                    LoadDistributionGrant ldgdto = new LoadDistributionGrant();
                    LoadDistributionGrant newldgdto = new LoadDistributionGrant();
                    LoadDistributionGrantId ldgid = new LoadDistributionGrantId();
                    String regid = request.getParameter("semesterCode");
                    List<LoadDistributionGrant> l = new ArrayList<LoadDistributionGrant>();
                    l = (List<LoadDistributionGrant>) daoFactory.getLoadDistributionGrantDAO().getLoadDistributionData(jirpsession.getJsessionInfo().getSelectedinstituteid(), regid);
                    for (int i = 0; i < l.size(); i++) {
                        ldgdto = (LoadDistributionGrant) l.get(i);
                        ldgid.setInstituteid(ldgdto.getId().getInstituteid());
                        ldgid.setRegistrationid(generatedid);
                        ldgid.setStaffid(ldgdto.getId().getStaffid());
                        ldgid.setStafftype(ldgdto.getId().getStafftype());
                        ldgid.setDepartmentid(ldgdto.getId().getDepartmentid());
                        newldgdto.setId(ldgid);
                        newldgdto.setDeactive("N");
                        newldgdto.setEntrydate(new Date());
                        daoFactory.getLoadDistributionGrantDAO().add(newldgdto);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                err = new ArrayList<String>();
                err.add("Error");
            }
            err = new ArrayList<String>();
            err.add("Success");

        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        } finally {
            businessService.closeSession();
        }
        return err;
    }

    @Override
    public void editRegistrationMaster(Model mm, HttpServletRequest request) {
        RegistrationMaster dto = null;
        RegistrationMasterId id = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            dto = new RegistrationMaster();
            id = new RegistrationMasterId();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            id.setInstituteid(ids[0]);
            id.setRegistrationid(ids[1]);
            //            dto = (RegistrationMaster) daoFactory.getRegistrationMasterDAO().findByPrimaryKey(id);
            List l = (List) daoFactory.getRegistrationMasterDAO().getRegistrationEditData(ids[0], ids[1]);
            List list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationCode_1(instituteid);
            mm.addAttribute("sec_list", list);
            mm.addAttribute("data", l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List updatRegistrationMaster(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List retList = null;
        List err = null;
        RegistrationMaster registrationMaster = new RegistrationMaster();
        RegistrationMasterId id = new RegistrationMasterId();
        try {
            id.setRegistrationid(request.getParameter("registrationId"));
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            registrationMaster.setId(id);
            registrationMaster = (RegistrationMaster) daoFactory.getRegistrationMasterDAO().findByPrimaryKey(id);
            if (registrationMaster != null) {
                registrationMaster.setRegistrationdatefrom(df.parse(request.getParameter("fromdate")));
                registrationMaster.setRegistrationdateto(df.parse(request.getParameter("todate")));
                if (request.getParameter("suppFromDate") != null && !request.getParameter("suppFromDate").equals("")) {
                    registrationMaster.setSupplregeventfrom(df.parse(request.getParameter("suppFromDate")));
                }
                if (request.getParameter("suppToDate") != null && !request.getParameter("suppToDate").equals("")) {
                    registrationMaster.setSupplregeventupto(df.parse(request.getParameter("suppToDate")));
                }
                if (request.getParameter("regEventFrom") != null && !request.getParameter("regEventFrom").equals("")) {
                    registrationMaster.setPreventfromdate(df.parse(request.getParameter("regEventFrom")));
                }
                if (request.getParameter("regEventTo") != null && !request.getParameter("regEventTo").equals("")) {
                    registrationMaster.setPreventenddate(df.parse(request.getParameter("regEventTo")));
                }
                registrationMaster.setExamperiodfrom(df.parse(request.getParameter("examPerioddate")));

                if (request.getParameter("attPerFromdate") != null) {
                    registrationMaster.setAttendancefromdate(df.parse(request.getParameter("attPerFromdate")));
                } else {
                    registrationMaster.setAttendancefromdate(null);
                }
                if (request.getParameter("attPerTodate") != null) {
                    registrationMaster.setAttendancetodate(df.parse(request.getParameter("attPerTodate")));
                } else {
                    registrationMaster.setAttendancetodate(null);
                }
                if (request.getParameter("courseRegFromdate") != null && !request.getParameter("courseRegFromdate").equals("")) {
                    registrationMaster.setCourseregistrationdatefrom(df.parse(request.getParameter("courseRegFromdate")));
                }
                if (request.getParameter("courseRegTodate") != null && !request.getParameter("courseRegTodate").equals("")) {
                    registrationMaster.setCourseregistrationdateto(df.parse(request.getParameter("courseRegTodate")));
                }
                if (request.getParameter("gipRegFrom") != null && !request.getParameter("gipRegFrom").equals("")) {
                    registrationMaster.setGipregfrom(df.parse(request.getParameter("gipRegFrom")));
                }
                if (request.getParameter("gipRegTo") != null && !request.getParameter("gipRegTo").equals("")) {
                    registrationMaster.setGipregistrationupto(df.parse(request.getParameter("gipRegTo")));
                }
                registrationMaster.setCourseregcompleted(request.getParameter("courseRegComplet"));
                registrationMaster.setCourseregbroadcast(request.getParameter("courseRegBroadcast"));
                registrationMaster.setGipregbroadcast(request.getParameter("gipRegBroadcast"));
                registrationMaster.setRegistrationcode(request.getParameter("registrationCode").trim());
                registrationMaster.setRegistrationdesc(request.getParameter("registrationDesc"));
                registrationMaster.setRegistrationcaption(request.getParameter("registrationCaption"));
                registrationMaster.setPlaceofregistration(request.getParameter("placeOfRegistration"));
                registrationMaster.setEvenoddflag(new Byte(request.getParameter("oddEven")));
                registrationMaster.setRegistrationtype(request.getParameter("registrationType"));
                registrationMaster.setAcademicyear(request.getParameter("academicYearTitle"));
                registrationMaster.setGradesheetcaption(request.getParameter("gradeSheetTitle"));
                registrationMaster.setFinalized(request.getParameter("finStatus"));
                registrationMaster.setFinalizedby(jirpsession.getJsessionInfo().getMemberid());
                registrationMaster.setFinalizeddate(new Date());
                registrationMaster.setExcludeinprereg(request.getParameter("excludePreReg"));
                registrationMaster.setPreventcompleted(request.getParameter("eventComplet"));
                registrationMaster.setPreventbroadcast(request.getParameter("eventBroadcast"));
                registrationMaster.setExcludeinsrs(request.getParameter("excludeSrs"));
                registrationMaster.setExcludeinattendance(request.getParameter("excludeAttandance"));
                registrationMaster.setExcludeinclasstimetable(request.getParameter("excludeTimeTable"));
                registrationMaster.setExcludeindatesheet(request.getParameter("excludeDateSheet"));
                registrationMaster.setExcludeininvigdutyseatplan(request.getParameter("excludeInvgDuty"));
                registrationMaster.setRegistrationcomplete(request.getParameter("regComplete"));
                registrationMaster.setLockregistration(request.getParameter("lockReg"));
                registrationMaster.setParentregistrationid(request.getParameter("parentsregistrationid"));
                if (request.getParameter("allowBackPaper") != null) {
                    registrationMaster.setAllowbackpaperreg(request.getParameter("allowBackPaper"));
                } else {
                    registrationMaster.setAllowbackpaperreg("N");
                }
                if (request.getParameter("deactive") != null) {
                    registrationMaster.setDeactive(request.getParameter("deactive"));
                } else {
                    registrationMaster.setDeactive("Y");
                }

                err = daoFactory.getRegistrationMasterDAO().doValidate(registrationMaster, "edit");
                if (err.size() > 0) {
                    return err;
                }
                daoFactory.getRegistrationMasterDAO().update(registrationMaster);
                retList = new ArrayList<String>();
                retList.add("Success");
            }

        } catch (Exception e) {
            e.printStackTrace();
            retList = new ArrayList<String>();
            retList.add("Error");
        }
        return retList;
    }

    @Override
    public List deleteRegistrationMaster(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String pks1[] = pks[i].toString().split(":");
                daoFactory.getRegistrationMasterDAO().delete(daoFactory.getRegistrationMasterDAO().findByPrimaryKey(new RegistrationMasterId(pks1[0].toString(), pks1[1].toString())));
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
    public String checkIfChildExist(HttpServletRequest request) {
        String uid = request.getParameter("pk");
        String status = (daoFactory.getRegistrationMasterDAO().checkIfChildExist(uid.split(":")[0].toString(), uid.split(":")[1].toString()) > 0) ? "true" : "false";
        return status;
    }

    @Override
    public List getgetRegistrationID(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getRegistrationMasterDAO().getRegistrationCode_1(instituteid);
        return list;
    }
}
