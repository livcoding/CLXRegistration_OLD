/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.lowagie.text.Image;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.AddDropSubjectForBasicIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.persistence.dto.FeeStructureIndividual;
import com.jilit.irp.persistence.dto.FeeStructureIndividualId;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.StudentAttendance;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentPhoto;
import com.jilit.irp.persistence.dto.StudentPreviousAttendence;
import com.jilit.irp.persistence.dto.StudentPreviousAttendenceId;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.StudentRegistration_info;
import com.jilit.irp.persistence.dto.StudentRegistration_infoId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocation;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationId;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetail;
import com.jilit.irp.persistence.dto.TT_TimeTableAllocationDetailId;
import com.jilit.irp.persistence.dto.TT_TimeTable;
import com.jilit.irp.persistence.dto.TT_TimeTableId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetailId;

import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.TempRollNumberControl;
import com.jilit.irp.persistence.dto.TempRollNumberControlId;
import com.jilit.irp.persistence.dto.TempRollNumberSetup;
import com.jilit.irp.persistence.dto.TempRollNumberSetupId;
import com.jilit.irp.persistence.dto.Tt_Timetableapproval;
import com.jilit.irp.persistence.dto.Tt_TimetableapprovalId;
import com.jilit.irp.util.JIRPSession;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.ui.ModelMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Ashutosh1.kumar
 */
@Service
public class AddDropSubjectForBasicService extends ReportManager implements AddDropSubjectForBasicIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public void getInstituteCodeForAddDrop(ModelMap model) {
        try {
            String maininstituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String userid = jirpsession.getJsessionInfo().getUserid();
            String memberid = jirpsession.getJsessionInfo().getMemberid();
            List list = daoFactory.getInstituteMasterDAO().getInstituteCodeForAddDrop(userid, "0000RTID1006A0001033");
            String classConfirmationDateflag = daoFactory.getRegistrationParametersDAO().getParametersValue(maininstituteid, "SUBREG5.5");
            model.put("institutelist", list);
            model.put("classDate", classConfirmationDateflag);
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
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getAllRegistrationCodeForAddDropSubject(instituteids);
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
                                mainobj[4] = obj[4].toString() + "/" + obj1[4].toString(); // registrationdesc
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

    @Override
    public List getStudetnInfo(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        String[] registrationid = request.getParameter("semCode2").split(",");
        List registrationidlist = Arrays.asList(registrationid);
        String enrollmentno = request.getParameter("enrollmentno").trim();
        String specialcase = request.getParameter("specialcase");
        List list = null;
        try {
            list = (List) daoFactory.getStudentMasterDAO().getStudentInfo(instituteids, enrollmentno, registrationidlist, "basic", specialcase);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStudentDetail(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        String[] registrationid = request.getParameter("semCode").split(",");
        List registrationidlist = Arrays.asList(registrationid);
        String acadyear = request.getParameter("acadyear");
        String program = request.getParameter("programCode");
        String specialcase = request.getParameter("specialcase");
        List list = null;
        try {
            if (specialcase.equals("Y")) {
                list = (List) daoFactory.getStudentMasterDAO().getRegistrationCodeWiseStudentMasterData(instituteids, registrationidlist, program, acadyear);
            } else {
                list = (List) daoFactory.getStudentMasterDAO().getStudentLovForAddDropBasic(instituteids, registrationidlist, program, acadyear);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List assignData(HttpServletRequest request) {
        List returnList = new ArrayList();
//        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String studentid = request.getParameter("studentid");
        String assign = request.getParameter("assign");
        String instituteid = request.getParameter("studentinstituteid");
        String regid = request.getParameter("studentregistrationid");
        String regcode = request.getParameter("regcode");
        Random rand = new Random();
        List<Object[]> studatalist = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentData(instituteid, studentid);
        String programid = "";
        String branchid = "";
        String academicyear = "";
        byte stynumber = 0;
        String enrollmentno = "";
        String sectionid = "";
        String SectionCode = "";
        String subsectionid = "";
        String SubSectionCode = "";
        String quotaid = "";
        int studentmaxlimit = 0;
        if (studatalist != null && studatalist.size() > 0) {
            for (int i = 0; i < studatalist.size(); i++) {
                Object[] obj = studatalist.get(i);
                programid = obj[0].toString();
                branchid = obj[1].toString();
                academicyear = obj[2].toString();
                stynumber = new Byte(obj[3].toString());
                enrollmentno = obj[4] == null ? "" : obj[4].toString();
                sectionid = obj[5] == null ? "" : obj[5].toString();
                subsectionid = obj[6] == null ? "" : obj[6].toString();
                quotaid = obj[7] == null ? "" : obj[7].toString();
                if (instituteid.equals("")) {
                    instituteid = obj[8] == null ? "" : obj[8].toString();
                }
            }
        }
        boolean save = false;
        boolean regPermission = false;
        boolean flag = true;
        String registrationid = daoFactory.getRegistrationMasterDAO().getRegistrationIdByCode(instituteid, regcode);
        if (regid.equals("")) {
            regid = registrationid;
        }
        while (flag) {
            if (sectionid == "") {
                int minstudentcount = 0;
                List<Object[]> sectionlist = (List<Object[]>) daoFactory.getProgramWiseSubsectionDAO().getSectionListMinStudentWise(instituteid, programid, branchid, academicyear, stynumber);
                for (Object[] obj : sectionlist) {
                    minstudentcount = Integer.parseInt(obj[2].toString());
                    if (Integer.parseInt(obj[2].toString()) <= minstudentcount) {
                        minstudentcount = Integer.parseInt(obj[2].toString());
                        sectionid = obj[0].toString();
                        SectionCode = obj[1].toString();
                        save = true;
                    }
                }
            }
            if (subsectionid == "") {
                int minstudentcount = 0;
                List<Object[]> subsectionlist = (List<Object[]>) daoFactory.getProgramWiseSubsectionDAO().getSubSectionListMinStudentWise(instituteid, programid, branchid, academicyear, stynumber, sectionid);
                for (Object[] obj : subsectionlist) {
                    minstudentcount = Integer.parseInt(obj[4].toString());
                    if (Integer.parseInt(obj[4].toString()) <= minstudentcount) {
                        minstudentcount = Integer.parseInt(obj[4].toString());
                        subsectionid = obj[0].toString();
                        SubSectionCode = obj[1].toString();
                        SectionCode = obj[3].toString();
                        studentmaxlimit = Integer.parseInt(obj[2].toString());
                        save = true;
                    }
                }
            }
            String groupid = "";
            String number = "";
            boolean updaterollnotemp = false;
            if (enrollmentno == "") {
                List enrollmentno1 = generateAndSaveEnrollmentNo("Y", studentid, instituteid);
                if (enrollmentno1.get(0).toString().equals("TempRollNumberSetupNotDefine")) {
                    returnList.add("TempRollNumberSetupNotDefine~@~" + enrollmentno1.get(1).toString() + "~@~" + SubSectionCode);
                    return returnList;
                }
                enrollmentno = enrollmentno1.get(0).toString();
                number = enrollmentno1.get(1).toString();
                groupid = enrollmentno1.get(2).toString();
                updaterollnotemp = true;
                save = true;
            }
            if (save == true) {
//                List subsectioncount = (List) daoFactory.getProgramWiseSubsectionDAO().getSubSectionCount(instituteid, programid, branchid, academicyear, stynumber, sectionid, subsectionid);
//                int count = Integer.parseInt(subsectioncount.get(0).toString());
//                if (count < studentmaxlimit || studentmaxlimit == 0) {
                flag = false;
                StudentMaster dto = new StudentMaster();
                dto = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentid);
                dto.setSectionid(sectionid);
                dto.setSubsectionid(subsectionid);
                dto.setEnrollmentno(enrollmentno);
                dto.setGroupid(groupid);
                daoFactory.getStudentMasterDAO().update(dto);

                if (updaterollnotemp) {
                    //Save Temp EnrollmentNo. running no in temprollnumbercontrol table
                    TempRollNumberControl tdto = new TempRollNumberControl();
                    TempRollNumberControlId id = new TempRollNumberControlId();
                    id.setInstituteid(instituteid);
                    id.setGroupid(groupid);
                    id.setYm(academicyear);
                    tdto.setId(id);
                    tdto.setLastrunningno(new BigDecimal(number));
                    tdto.setLastno(number);
                    tdto.setLastgenerationdate(new Date());
                    TempRollNumberControl numberingControl = (TempRollNumberControl) daoFactory.getTempRollNumberControlDAO().findByPrimaryKey(id);
                    if (numberingControl != null) {
                        daoFactory.getTempRollNumberControlDAO().update(tdto);
                    } else {
                        daoFactory.getTempRollNumberControlDAO().add(tdto);
                    }
                    try {
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        returnList.add("Error");
                        return returnList;
                    }
                }
                regPermission = true;
            }
        }
        if (regPermission == true) {
            StudentRegistrationId srId = new StudentRegistrationId();
            StudentRegistration srDto = new StudentRegistration();
            StudentRegistration_infoId sriId = new StudentRegistration_infoId();
            StudentRegistration_info sriDto = new StudentRegistration_info();
            Set<StudentRegistration_info> set = new HashSet<StudentRegistration_info>();
            String stytypeid = daoFactory.getStyTypeDAO().getStytypeId(instituteid, "REG");

            srId.setInstituteid(instituteid);
            srId.setRegistrationid(regid);
            srId.setStudentid(studentid);
            srDto.setId(srId);
            srDto.setStynumber(stynumber);
            srDto.setStytypeid(stytypeid);
            srDto.setRegallow("Y");
            srDto.setQuotaid(quotaid);
            srDto.setRegallowdate(new Date());

            sriId.setInstituteid(instituteid);
            sriId.setRegistrationid(regid);
            sriId.setStudentid(studentid);
            sriDto.setId(sriId);
            sriDto.setAcademicyear(academicyear);
            sriDto.setProgramid(programid);
            sriDto.setBranchid(branchid);
            sriDto.setSectionid(sectionid);
            sriDto.setSubsectionid(subsectionid);
            sriDto.setStynumber(stynumber);
            sriDto.setStytypeid(stytypeid);
            // sriDto.setProgramtypeid();

            set.add(sriDto);
            srDto.setStudentregistration_infos(set);
            daoFactory.getStudentRegistrationDAO().saveOrUpdate(srDto);

        }
        returnList.add(enrollmentno + "~@~" + SectionCode + "~@~" + SubSectionCode);
        return returnList;
    }

    @Override
    public List getAcademicYear(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        String[] registrationid = request.getParameter("semCode").split(",");
        List registrationidlist = Arrays.asList(registrationid);
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentMasterDAO().getAcademicYearForAddDrop(instituteids, registrationidlist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getProgram(HttpServletRequest request) {
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        String[] registrationid = request.getParameter("semCode").split(",");
        List registrationidlist = Arrays.asList(registrationid);
        String acadyear = request.getParameter("acadyear");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentMasterDAO().getProgramForAddDrop(instituteids, registrationidlist, acadyear);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * Description : This Method is used to Due and Approvel
     *
     * @param request
     * @return
     */
    public Map getActivity(HttpServletRequest request) {
        Map studentActivityMap = new HashMap();
//        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//        String registrationid = request.getParameter("semCode");
        String studentid = request.getParameter("studentid");
        String academicYear = request.getParameter("academic");
        String programid = request.getParameter("programid");
        String styNumber = request.getParameter("styNumber");
        String branchid = request.getParameter("branchid");
        String sectionid = request.getParameter("sectionid");
        String subsectionid = request.getParameter("subsectionid");
        String groupid = request.getParameter("groupid");
        String instituteid = request.getParameter("studentinstituteid");
        String registrationid = request.getParameter("studentregistrationid");
        try {
            List creditList = new ArrayList();
            Map creditMap = new HashMap();
            String clientid = jirpsession.getJsessionInfo().getSelectedclientid();
            String userType = jirpsession.getJsessionInfo().getUsertype();

            //-------- min max credits------------------------------------------        
            String minCredit = "";
            String maxCredit = "";
            String takenCredit = "";
            if (!groupid.equals("null")) {
                List<Object[]> crList = (List<Object[]>) daoFactory.getProgramMinMaxLimitDAO().getStudentMinMaxCradits(clientid, groupid, branchid, programid, styNumber);
                if (crList.size() > 0) {
                    minCredit = crList.get(0)[0].toString();
                    maxCredit = crList.get(0)[1].toString();
                } else {
                    List<Object[]> crList1 = (List<Object[]>) daoFactory.getProgramMinMaxLimitDAO().getMinMaxLimt(instituteid, branchid, programid, Byte.parseByte(styNumber), academicYear);
                    if (crList1.size() > 0) {
                        minCredit = crList1.get(0)[0].toString();
                        maxCredit = crList1.get(0)[1].toString();
                    }
                }
            } else {
                List<Object[]> crList1 = (List<Object[]>) daoFactory.getProgramMinMaxLimitDAO().getMinMaxLimt(instituteid, branchid, programid, Byte.parseByte(styNumber), academicYear);
                if (crList1.size() > 0) {
                    minCredit = crList1.get(0)[0].toString();
                    maxCredit = crList1.get(0)[1].toString();
                }
            }

            //-----------------------taken credits----------------------
            String remaningCredit = "";
            List takencreditList = (List) daoFactory.getProgramSubjectTaggingDAO().getTakenCredit(instituteid, registrationid, studentid);
            if (takencreditList.size() > 0 && takencreditList.get(0) != null) {
                takenCredit = takencreditList.get(0).toString();
                if (!maxCredit.equals("")) {
                    remaningCredit = String.valueOf(Integer.parseInt(maxCredit) - new Double(takenCredit));
                }
            }
            if (minCredit.equals("") || maxCredit.equals("")) {
                String msg = "warning";
                creditList.add(msg);
            } else {
                creditMap.put("userType", userType);
                creditMap.put("maxCredit", maxCredit);
                creditMap.put("minCredit", minCredit);
                creditMap.put("takenCredit", takenCredit);
                creditMap.put("remaningCredit", remaningCredit);
                creditList.add(creditMap);
            }
//            studentActivityMap.put("studentActivity", studentActivityList);
            studentActivityMap.put("creditList", creditList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentActivityMap;

    }

    /**
     * Description : this Method is used to get Drop , Regular and Back Subject
     * With The Help Of Bussiness Logic
     *
     * @param request
     * @return
     */
    public Map getAddDropSubject(HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map studentActivityMap = new HashMap();
        String studentid = request.getParameter("studentid");
        String registrationid = request.getParameter("semCode");
        String academicYear = request.getParameter("academic");
        String programid = request.getParameter("programid");
        String styNumber = request.getParameter("styNumber");
        String branchid = request.getParameter("branchid");
        String sectionid = request.getParameter("sectionid");
        String subsectionid = request.getParameter("subsectionid");
        String groupid = request.getParameter("groupid");
        String quotaid = request.getParameter("quotaid");
        String stytypeid = request.getParameter("stytypeid");
        String stuinstituteid = request.getParameter("studentinstituteid");
        String sturegistrationid = request.getParameter("studentregistrationid");
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regconfirmflag = "";
            String regconfirmdate = "";
            List<Object[]> regconformation = (List<Object[]>) daoFactory.getProgramSubjectDetailDAO().getRegConfermOrNot(stuinstituteid, sturegistrationid, studentid);
            if (regconformation.size() > 0 && regconformation.get(0) != null) {
                Object[] obj = regconformation.get(0);
                if (obj[0] != null) {
                    regconfirmflag = obj[0].toString();
                    if (obj[0].equals("Y")) {
                        regconfirmflag = obj[0].toString();
                        regconfirmdate = newFormat.format(oldFormat.parse(obj[1].toString()));
                    }
                }
            }
            if (regconfirmflag.equals("N")) {
                List dueAmount = (List) daoFactory.getdBDependentDAO().checkStudentDueAmount(stuinstituteid, studentid, sturegistrationid);
                BigDecimal feedueamount = new BigDecimal(dueAmount.get(0).toString());
                //Parameter For LateRegistration Fee Calculated Or Not
                String parametervalue = "";
                String moduleid = "MOD08000011";
                String parameterid = "B1.3";
                List parameterval = (List) daoFactory.getProgramSubjectDetailDAO().getParametervalueFOrLateRegistration(instituteid, moduleid, parameterid);
                if (parameterval.size() > 0 && parameterval.get(0) != null) {
                    parametervalue = parameterval.get(0).toString();
                }
                //Calculate From And To Date For Registration 
                List<Object[]> regDates = (List<Object[]>) daoFactory.getProgramSubjectDetailDAO().getRegFromAndToDate(stuinstituteid, sturegistrationid, programid, academicYear, branchid);
                String currentDate = new Date().toString();
                d1 = format.parse(format.format(new Date()));//alert
                if (regDates.size() > 0 && regDates.get(0) != null && regDates.get(0)[1] != null) {
                    Object[] obj = regDates.get(0);
                    d2 = format.parse(format.format(obj[0]));
                    d3 = format.parse(format.format(obj[1]));
                    String lateRegistrationFeeAmount = obj[2] == null ? "0" : obj[2].toString();
                    if (d1.compareTo(d2) < 0) {
                        if (!feedueamount.equals(BigDecimal.ZERO)) {
                            studentActivityMap.put("alert", "Please Deposit the remaining Fee Amount " + feedueamount + " Rs.!");
                        } else {
                            studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
                            studentActivityMap.put("alert", "Your Registration Date Will Begin On " + obj[0].toString());
                        }
                    } else if (d1.compareTo(d3) > 0) {
                        String feeheadid = "";
                        String currencyid = "";
                        if (parametervalue.equals("Y")) {
                            List feehead = (List) daoFactory.getProgramSubjectDetailDAO().getFeeHeadIdFOrLateRegistration(stuinstituteid);
                            if (feehead.size() > 0 && feehead.get(0) != null) {
                                feeheadid = feehead.get(0).toString();
                            }
                            List currency = (List) daoFactory.getProgramSubjectDetailDAO().getCurrencyIdForAddDrop();
                            if (currency.size() > 0 && currency.get(0) != null) {
                                currencyid = currency.get(0).toString();
                            }
                            List checkRecordInFeestructureindivisual = (List) daoFactory.getProgramSubjectDetailDAO().checkRecordInFeestructureindivisual(stuinstituteid, sturegistrationid, feeheadid, studentid);
                            if (checkRecordInFeestructureindivisual.size() > 0 && checkRecordInFeestructureindivisual.get(0) != null) {
                                boolean checkadddrop = false;
                                if (feedueamount.equals(BigDecimal.ZERO)) {
                                    List<Object[]> feeAmount = (List<Object[]>) daoFactory.getdBDependentDAO().checkStudentDueAmountFeeHeadWise(stuinstituteid, studentid, sturegistrationid, feeheadid);
                                    if (feeAmount.size() > 0) {
                                        Object[] object = feeAmount.get(0);
                                        BigDecimal dueamount1 = new BigDecimal(object[1].toString());
                                        int spclapproval = Integer.parseInt(object[2].toString());  // 0 means no approval 1 means having spcl approval
                                        if (!dueamount1.equals(BigDecimal.ZERO)) {
                                            if (spclapproval == 0) {
                                                studentActivityMap.put("alert", "Late Registration Fine is not Paid!");
                                            } else {
                                                checkadddrop = true;
                                            }
                                        } else {
                                            checkadddrop = true;
                                        }
                                    } else {
                                        studentActivityMap.put("alert", "Late Registration Fine is not Paid!");
                                    }
                                } else {
                                    studentActivityMap.put("alert", "Please Deposit the remaining Fee Amount " + feedueamount + " Rs.!");
                                }
                                if (checkadddrop) {
                                    studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
                                } else {
//                                    studentActivityMap.put("alert", "Please Pay Fine " + lateRegistrationFeeAmount + " Rupess for  Late Registration!");
                                }
                            } else {
                                FeeStructureIndividual fsti = new FeeStructureIndividual();
                                FeeStructureIndividualId id = new FeeStructureIndividualId();
                                id.setCurrencyid(currencyid);
                                id.setStudentid(studentid);
                                id.setStytypeid(stytypeid);
                                id.setQuotaid(quotaid);
                                id.setFeeheadid(feeheadid);
                                id.setInstituteid(stuinstituteid);
                                id.setRegistrationid(sturegistrationid);
                                id.setStynumber(new Byte(styNumber));
                                id.setSlno(1);
                                fsti.setId(id);
                                fsti.setFeeamount(new BigDecimal(lateRegistrationFeeAmount));
                                fsti.setSourcetransaction("Add Drop Form");
                                fsti.setAcceptablecurrency("A");
                                fsti.setPartiallyacceptable("N");
                                fsti.setCriteriabased("N");
                                fsti.setFeefinalized("N");
                                fsti.setEntrydate(new Date());
                                daoFactory.getProgramSubjectDetailDAO().add(fsti);
                                studentActivityMap.put("alert", "Please Pay Fine " + lateRegistrationFeeAmount + " Rupess for  Late Registration!");
                            }
                        } else {
                            studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
                        }
                    } else {
                        if (!feedueamount.equals(BigDecimal.ZERO)) {
                            studentActivityMap.put("alert", "Please Deposit the remaining Fee Amount " + feedueamount + " Rs.!");
                        } else {
                            studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
                        }
                    }
                } else {
                    if (parametervalue.equals("Y")) {
                        studentActivityMap.put("alert", "Batch Wise Registration Process Dates is not define for this student Program(Branch) and Academic year..");
                    } else {
                        studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
                    }
                }
            } else {
                studentActivityMap = commonForAllCase(studentid, sturegistrationid, academicYear, programid, styNumber, branchid, sectionid, subsectionid, groupid, regconfirmflag, regconfirmdate, stuinstituteid);
            }
//        }else {
//                studentActivityMap.put("RegComf", "Registration Confirmation is not done for this Student...!");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentActivityMap;
    }

    public Map commonForAllCase(String studentid, String sturegistrationid, String academicYear, String programid, String styNumber, String branchid, String sectionid, String subsectionid, String groupid, String regconfirmflag, String regconfirmdate, String stuinstituteid) {
        Map studentActivityMap = new HashMap();
        try {
//            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String instituteid = stuinstituteid;
            String registrationid = sturegistrationid;

            //--------------------------------------------add Drop Subject List ------------------------------
            Map params = new HashMap();
            List addList = new ArrayList();
            List dropList = new ArrayList();
            Map object = null;
            params.put("instid", "'" + instituteid + "'");
            params.put("regid", "'" + registrationid + "'");
            params.put("acadyear", "'" + academicYear + "'");
            params.put("studid", "'" + studentid + "'");
            params.put("prid", "'" + programid + "'");
            params.put("styno", "'" + styNumber + "'");
            params.put("secid", "'" + sectionid + "'");
            params.put("subsecid", "'" + subsectionid + "'");
            params.put("studid", "'" + studentid + "'");
            //List modeLIst = null;

            //--------------------------------------------Back Papers-----------------------
            List<Object[]> listBck = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findFailSubject_AddDrop_BM_HavingChoice", params);
            if (listBck.isEmpty() || listBck.size() == 0) {
                listBck = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findFailSubject_AddDrop_BM", params);
            }
            for (int ii = 0; ii < listBck.size(); ii++) {
                try {
                    object = new HashMap();
                    object.put("subjtype", "BACKLOG<br>" + listBck.get(ii)[9].toString().toUpperCase() + "<br>" + listBck.get(ii)[12].toString().toUpperCase());
                    object.put("subjectid", listBck.get(ii)[0]);
                    object.put("subjectcode", listBck.get(ii)[1]);
                    object.put("subjectdesc", listBck.get(ii)[2]);
                    object.put("totalccpmarks", listBck.get(ii)[3]);
                    object.put("basketid", listBck.get(ii)[4]);
                    object.put("basketcode", listBck.get(ii)[5] + " (" + listBck.get(ii)[7] + ")");
                    object.put("stynumber", listBck.get(ii)[6]);
                    object.put("modeselected", "SAP".equals(listBck.get(ii)[8]) ? "1" : "0");
                    object.put("subjectgroup", listBck.get(ii)[8] == null ? "" : listBck.get(ii)[8].toString());
                    List modeLIst = null;
                    Map modeObj = null;
                    modeLIst = new ArrayList();
                    modeObj = new HashMap();
                    modeObj.put("mode", "S");
                    modeObj.put("modedesc", "Study");
                    modeLIst.add(modeObj);
                    modeObj = new HashMap();
                    modeObj.put("mode", "E");
                    modeObj.put("modedesc", "Exam");
                    modeLIst.add(modeObj);
                    object.put("modeList", modeLIst);
                    byte styNo = Byte.decode(String.valueOf(listBck.get(ii)[6]));
                    List addDropList = daoFactory.getFacultySubjectTaggingDAO().getAddDropList(instituteid, registrationid, styNo, studentid, (String) listBck.get(ii)[0], academicYear, programid, sectionid, subsectionid);
                    object.put("instituteid", instituteid);
                    object.put("registrationid", registrationid);
                    object.put("studentid", studentid);
                    object.put("academicyear", academicYear);
                    object.put("programid", programid);
                    object.put("sectionid", sectionid);
                    object.put("subsectionid", subsectionid);
                    if (addDropList.isEmpty()) {
                        List<Object[]> subjectList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getSubjectDetail(instituteid, registrationid, listBck.get(ii)[0].toString(), listBck.get(ii)[4].toString(), programid);
                        List LecList = new ArrayList();
                        List PracList = new ArrayList();
                        List TutList = new ArrayList();
                        Map batchObj = null;
                        if (subjectList != null && !subjectList.isEmpty()) {
                            for (int i = 0; i < subjectList.size(); i++) {
                                String batchid = subjectList.get(i)[2].toString() + "@" + subjectList.get(i)[3].toString() + "@" + subjectList.get(i)[4].toString() + "@" + subjectList.get(i)[5].toString() + "@" + subjectList.get(i)[6].toString() + "@" + subjectList.get(i)[7].toString() + "@" + subjectList.get(i)[8].toString() + "@" + subjectList.get(i)[9].toString();
                                String batchcode = subjectList.get(i)[10].toString() + "/" + subjectList.get(i)[11].toString() + "/" + subjectList.get(i)[12].toString() + "/" + subjectList.get(i)[13].toString() + "/" + subjectList.get(i)[14].toString() + "/" + subjectList.get(i)[15].toString();
                                if ("L".equals(subjectList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", batchcode);
                                    batchObj.put("batchid", batchid);
                                    batchObj.put("componentid", subjectList.get(i)[0].toString());
                                    batchObj.put("componentcode", subjectList.get(i)[1].equals("L") ? "Lecture" : subjectList.get(i)[1]);
                                    batchObj.put("subj", subjectList.get(i)[18].toString());
                                    LecList.add(batchObj);
                                }
                                if ("T".equals(subjectList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", batchcode);
                                    batchObj.put("batchid", batchid);
                                    batchObj.put("componentid", subjectList.get(i)[0].toString());
                                    batchObj.put("componentcode", subjectList.get(i)[1].equals("T") ? "Tutorial" : subjectList.get(i)[1]);
                                    batchObj.put("subj", subjectList.get(i)[18].toString());
                                    TutList.add(batchObj);
                                }
                                if ("P".equals(subjectList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", batchcode);
                                    batchObj.put("batchid", batchid);
                                    batchObj.put("componentid", subjectList.get(i)[0].toString());
                                    batchObj.put("componentcode", subjectList.get(i)[1].equals("P") ? "Practical" : subjectList.get(i)[1]);
                                    batchObj.put("subj", subjectList.get(i)[18].toString());
                                    PracList.add(batchObj);
                                }
                            }
                            object.put("LecList", LecList);
                            object.put("TutList", TutList);
                            object.put("PracList", PracList);
                            object.put("ElectiveType", listBck.get(ii)[9]);
                            object.put("SubjectTypeid", listBck.get(ii)[10]);
                            object.put("departmentid", listBck.get(ii)[11]);
                        } else {
                            object.put("LecList", "");
                            object.put("TutList", "");
                            object.put("PracList", "");
                            object.put("departmentid", listBck.get(ii)[11]);
                        }
                        addList.add(object);
                    } else {
                        List<Object[]> componentsList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getDropSubjectDetail(instituteid, registrationid, listBck.get(ii)[0].toString(), listBck.get(ii)[4].toString(), programid, studentid);
                        List batchList = null;
                        String subjComponent = "";
                        Map batchObj = null;
                        if (componentsList != null && !componentsList.isEmpty()) {
                            for (int i = 0; i < componentsList.size(); i++) {
                                String batchid1 = componentsList.get(i)[2].toString() + "@" + componentsList.get(i)[3].toString() + "@"
                                        + componentsList.get(i)[4].toString() + "@" + componentsList.get(i)[5].toString() + "@"
                                        + componentsList.get(i)[6].toString() + "@" + componentsList.get(i)[7].toString() + "@"
                                        + componentsList.get(i)[8].toString() + "@" + componentsList.get(i)[9].toString();
                                String batchcode1 = componentsList.get(i)[10].toString() + "/" + componentsList.get(i)[11].toString() + "/"
                                        + componentsList.get(i)[12].toString() + "/" + componentsList.get(i)[13].toString() + "/"
                                        + componentsList.get(i)[14].toString();
                                if (i == 0) {
                                    batchList = new ArrayList();
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", batchcode1);
                                    batchObj.put("batchid", batchid1);
                                    batchObj.put("fstid", componentsList.get(i)[15].toString());
                                    batchObj.put("componentid", componentsList.get(i)[0].toString());
                                    batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                    batchList.add(batchObj);

                                    subjComponent = componentsList.get(i)[1].toString();
                                    if (i == componentsList.size() - 1) {
                                        object.put("batchList", batchList);
                                    }
                                } else {
                                    if (subjComponent.equals(componentsList.get(i)[1].toString())) {
                                        batchObj = new HashMap();
                                        batchObj.put("batchcode", batchcode1);
                                        batchObj.put("batchid", batchid1);
                                        batchObj.put("fstid", componentsList.get(i)[15].toString());
                                        batchObj.put("componentid", componentsList.get(i)[0].toString());
                                        batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                        batchList.add(batchObj);
                                        subjComponent = componentsList.get(i)[1].toString();
                                        if (i == componentsList.size() - 1) {
                                            object.put("batchList", batchList);
                                        }
                                    } else {
                                        object.put("batchList", batchList);
                                        batchList = new ArrayList();
                                        batchObj = new HashMap();
                                        batchObj.put("batchcode", batchcode1);
                                        batchObj.put("batchid", batchid1);
                                        batchObj.put("fstid", componentsList.get(i)[15].toString());
                                        batchObj.put("componentid", componentsList.get(i)[0].toString());
                                        batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                        batchList.add(batchObj);
                                        object.put("batchList", batchList);
                                        subjComponent = componentsList.get(i)[1].toString();
                                        if (i == componentsList.size() - 1) {
                                            object.put("batchList", batchList);
                                        }
                                    }

                                }
                            }
                        }
                        dropList.add(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//--------------------------Current Subjects------------------------------------
            List<Object[]> currentSubjectList = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findStudentSubjectData_AddDrop_BM_HavingChoice", params);
            if (currentSubjectList.isEmpty() || currentSubjectList.size() == 0) {
                currentSubjectList = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findStudentSubjectData_AddDrop_BM", params);
            }

            for (int ii = 0; ii < currentSubjectList.size(); ii++) {
                try {
                    object = new HashMap();
                    String subjectmasteraudit = currentSubjectList.get(ii)[13].toString();
                    String pstaudit = currentSubjectList.get(ii)[12].toString();
                    if (subjectmasteraudit.equals("A") || pstaudit.equals("Y")) {
                        object.put("subjtype", "CURRENT<br>A<br>" + currentSubjectList.get(ii)[5] + " ( AUDIT ) ");
                    } else {
                        object.put("subjtype", "CURRENT<br>" + currentSubjectList.get(ii)[10].toString().toUpperCase() + "<br>" + currentSubjectList.get(ii)[5]);
                    }
                    object.put("subjectid", currentSubjectList.get(ii)[0]);
                    object.put("subjectcode", currentSubjectList.get(ii)[1]);
                    object.put("subjectdesc", currentSubjectList.get(ii)[2]);
                    object.put("totalccpmarks", currentSubjectList.get(ii)[3]);
                    object.put("basketid", currentSubjectList.get(ii)[4]);
                    object.put("basketcode", currentSubjectList.get(ii)[5] + " (" + currentSubjectList.get(ii)[7] + ")");
                    object.put("stynumber", currentSubjectList.get(ii)[6]);
                    object.put("subjectgroup", currentSubjectList.get(ii)[8] == null ? "" : currentSubjectList.get(ii)[8].toString());
                    List modeLIst = null;
                    Map modeObj = null;
                    modeLIst = new ArrayList();
                    modeObj = new HashMap();
                    modeObj.put("mode", "S");
                    modeObj.put("modedesc", "Study");
                    modeLIst.add(modeObj);
                    modeObj = new HashMap();
                    modeObj.put("mode", "E");
                    modeObj.put("modedesc", "Exam");
                    modeLIst.add(modeObj);
                    object.put("modeList", modeLIst);
                    byte styNo = Byte.decode(String.valueOf(currentSubjectList.get(ii)[6]));
                    List addDropList = daoFactory.getFacultySubjectTaggingDAO().getAddDropList(instituteid, registrationid, styNo, studentid, (String) currentSubjectList.get(ii)[0], academicYear, programid, sectionid, subsectionid);
                    object.put("instituteid", instituteid);
                    object.put("registrationid", registrationid);
                    object.put("studentid", studentid);
                    object.put("academicyear", academicYear);
                    object.put("programid", programid);
                    object.put("sectionid", sectionid);
                    object.put("subsectionid", subsectionid);
                    String batchid = "";
                    String batchcode = "";
                    Map compareBatchMap = new HashMap();
                    if (addDropList.isEmpty()) {
                        //index no. 12 = coalesce(a.customauditflag,'N') Means This Subject is a Audit Subject not show in Add List
                        if (currentSubjectList.get(ii)[12].toString().equals("N")) {
                            List<Object[]> subjectList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getSubjectDetail(instituteid, registrationid, currentSubjectList.get(ii)[0].toString(), currentSubjectList.get(ii)[4].toString(), programid);
                            List LecList = new ArrayList();
                            List PracList = new ArrayList();
                            List TutList = new ArrayList();
                            Map batchObj = null;

                            if (subjectList != null && !subjectList.isEmpty()) {
                                for (int i = 0; i < subjectList.size(); i++) {
                                    batchid = subjectList.get(i)[2].toString() + "@" + subjectList.get(i)[3].toString() + "@" + subjectList.get(i)[4].toString() + "@" + subjectList.get(i)[5].toString() + "@" + subjectList.get(i)[6].toString() + "@" + subjectList.get(i)[7].toString() + "@" + subjectList.get(i)[8].toString() + "@" + subjectList.get(i)[9].toString() + "@" + subjectList.get(i)[1].toString();
                                    batchcode = subjectList.get(i)[11].toString() + "/" + subjectList.get(i)[12].toString() + "/" + subjectList.get(i)[13].toString() + "/" + subjectList.get(i)[14].toString() + "/" + subjectList.get(i)[15].toString();
                                    if (compareBatchMap.containsKey(batchid)) {
                                        String oldbatchcode = compareBatchMap.get(batchid).toString();
                                        String[] oldstaffarray = oldbatchcode.split("=>/");
                                        String oldstaff = oldstaffarray[0].toString();
                                        oldstaff = oldstaff + "," + subjectList.get(i)[10].toString();
                                        compareBatchMap.put(batchid, oldstaff + "=>/" + batchcode);
                                    } else {
                                        compareBatchMap.put(batchid, subjectList.get(i)[17].toString() + "-" + subjectList.get(i)[16].toString() + "-" + subjectList.get(i)[10].toString() + "=>/" + batchcode);
                                    }
                                }
                            }

                            Set setSubj = new HashSet();

                            if (subjectList != null && !subjectList.isEmpty()) {
                                for (int i = 0; i < subjectList.size(); i++) {
                                    if ("L".equals(subjectList.get(i)[1].toString())) {
                                        batchid = subjectList.get(i)[2].toString() + "@" + subjectList.get(i)[3].toString() + "@" + subjectList.get(i)[4].toString() + "@" + subjectList.get(i)[5].toString() + "@" + subjectList.get(i)[6].toString() + "@" + subjectList.get(i)[7].toString() + "@" + subjectList.get(i)[8].toString() + "@" + subjectList.get(i)[9].toString() + "@" + subjectList.get(i)[1].toString();
                                        if (!setSubj.contains(batchid)) {
                                            batchcode = compareBatchMap.get(batchid).toString();
                                            batchObj = new HashMap();
                                            batchObj.put("batchcode", batchcode);
                                            batchObj.put("batchid", batchid);
                                            batchObj.put("componentid", subjectList.get(i)[0].toString());
                                            batchObj.put("componentcode", subjectList.get(i)[1].equals("L") ? "Lecture" : subjectList.get(i)[1]);
                                            batchObj.put("subj", subjectList.get(i)[18].toString());
                                            LecList.add(batchObj);
                                            setSubj.add(batchid);
                                        }
                                    }
                                    if ("T".equals(subjectList.get(i)[1].toString())) {
                                        batchid = subjectList.get(i)[2].toString() + "@" + subjectList.get(i)[3].toString() + "@" + subjectList.get(i)[4].toString() + "@" + subjectList.get(i)[5].toString() + "@" + subjectList.get(i)[6].toString() + "@" + subjectList.get(i)[7].toString() + "@" + subjectList.get(i)[8].toString() + "@" + subjectList.get(i)[9].toString() + "@" + subjectList.get(i)[1].toString();
                                        if (!setSubj.contains(batchid)) {
                                            batchcode = compareBatchMap.get(batchid).toString();
                                            batchObj = new HashMap();
                                            batchObj.put("batchcode", batchcode);
                                            batchObj.put("batchid", batchid);
                                            batchObj.put("componentid", subjectList.get(i)[0].toString());
                                            batchObj.put("componentcode", subjectList.get(i)[1].equals("T") ? "Tutorial" : subjectList.get(i)[1]);
                                            batchObj.put("subj", subjectList.get(i)[18].toString());
                                            TutList.add(batchObj);
                                            setSubj.add(batchid);
                                        }
                                    }
                                    if ("P".equals(subjectList.get(i)[1].toString())) {
                                        batchid = subjectList.get(i)[2].toString() + "@" + subjectList.get(i)[3].toString() + "@" + subjectList.get(i)[4].toString() + "@" + subjectList.get(i)[5].toString() + "@" + subjectList.get(i)[6].toString() + "@" + subjectList.get(i)[7].toString() + "@" + subjectList.get(i)[8].toString() + "@" + subjectList.get(i)[9].toString() + "@" + subjectList.get(i)[1].toString();
                                        if (!setSubj.contains(batchid)) {
                                            batchcode = compareBatchMap.get(batchid).toString();
                                            batchObj = new HashMap();
                                            batchObj.put("batchcode", batchcode);
                                            batchObj.put("batchid", batchid);
                                            batchObj.put("componentid", subjectList.get(i)[0].toString());
                                            batchObj.put("componentcode", subjectList.get(i)[1].equals("P") ? "Practical" : subjectList.get(i)[1]);
                                            batchObj.put("subj", subjectList.get(i)[18].toString());
                                            PracList.add(batchObj);
                                            setSubj.add(batchid);
                                        }
                                    }
                                }
                                object.put("LecList", LecList);
                                object.put("TutList", TutList);
                                object.put("PracList", PracList);
                                object.put("departmentid", currentSubjectList.get(ii)[9]);

                            } else {
                                object.put("LecList", "");
                                object.put("TutList", "");
                                object.put("PracList", "");
                                object.put("departmentid", currentSubjectList.get(ii)[9]);
                            }

                            addList.add(object);
                        }
                    } else {
                        List<Object[]> componentsList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getDropSubjectDetail(instituteid, registrationid, currentSubjectList.get(ii)[0].toString(), currentSubjectList.get(ii)[4].toString(), programid, studentid);
                        List batchList = null;
                        String subjComponent = "";
                        Map batchObj = null;
                        if (componentsList != null && !componentsList.isEmpty()) {
                            for (int i = 0; i < componentsList.size(); i++) {
                                String _ttreferenceid = "";
                                if (componentsList.get(i)[9] != null) {
                                    _ttreferenceid = componentsList.get(i)[9].toString();
                                }
                                String batchid1 = componentsList.get(i)[2].toString() + "@" + componentsList.get(i)[3].toString() + "@"
                                        + componentsList.get(i)[4].toString() + "@" + componentsList.get(i)[5].toString() + "@"
                                        + componentsList.get(i)[6].toString() + "@" + componentsList.get(i)[7].toString() + "@"
                                        + componentsList.get(i)[8].toString() + "@" + _ttreferenceid;
                                String batchcode1 = componentsList.get(i)[10].toString() + "/" + componentsList.get(i)[11].toString() + "/"
                                        + componentsList.get(i)[12].toString() + "/" + componentsList.get(i)[13].toString() + "/"
                                        + componentsList.get(i)[14].toString();
                                if (i == 0) {
                                    batchList = new ArrayList();
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", batchcode1);
                                    batchObj.put("batchid", batchid1);
                                    batchObj.put("fstid", componentsList.get(i)[15].toString());
                                    batchObj.put("componentid", componentsList.get(i)[0].toString());
                                    batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                    batchList.add(batchObj);

                                    subjComponent = componentsList.get(i)[1].toString();
                                    if (i == componentsList.size() - 1) {
                                        object.put("batchList", batchList);
                                    }
                                } else {
                                    if (subjComponent.equals(componentsList.get(i)[1].toString())) {
                                        batchObj = new HashMap();
                                        batchObj.put("batchcode", batchcode1);
                                        batchObj.put("batchid", batchid1);
                                        batchObj.put("fstid", componentsList.get(i)[15].toString());
                                        batchObj.put("componentid", componentsList.get(i)[0].toString());
                                        batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                        batchList.add(batchObj);
                                        subjComponent = componentsList.get(i)[1].toString();
                                        if (i == componentsList.size() - 1) {
                                            object.put("batchList", batchList);
                                        }
                                    } else {
                                        object.put("batchList", batchList);
                                        batchObj = new HashMap();
                                        batchObj.put("batchcode", batchcode1);
                                        batchObj.put("batchid", batchid1);
                                        batchObj.put("fstid", componentsList.get(i)[15].toString());
                                        batchObj.put("componentid", componentsList.get(i)[0].toString());
                                        batchObj.put("componentcode", componentsList.get(i)[1].toString());
                                        batchList.add(batchObj);
                                        object.put("batchList", batchList);
                                        subjComponent = componentsList.get(i)[1].toString();
                                        if (i == componentsList.size() - 1) {
                                            object.put("batchList", batchList);
                                        }
                                    }

                                }
                            }
                        } else {
                            object.put("LecList", "");
                            object.put("TutList", "");
                            object.put("PracList", "");
                            object.put("batchList", "");
                            object.put("departmentid", currentSubjectList.get(ii)[9]);
                        }
                        dropList.add(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            studentActivityMap.put("alert", "");
            studentActivityMap.put("RegComf", regconfirmflag + "~@~" + regconfirmdate);
            Collections.sort(addList, new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    return (o1.get("basketcode").toString() + o1.get("subjectcode").toString()).compareTo(o2.get("basketcode").toString() + o2.get("subjectcode").toString());
                }
            });
            studentActivityMap.put("addList", addList);
            Collections.sort(dropList, new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    return (o1.get("basketcode").toString() + o1.get("subjectcode").toString()).compareTo(o2.get("basketcode").toString() + o2.get("subjectcode").toString());
                }
            });
            studentActivityMap.put("dropList", dropList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentActivityMap;
    }

    /**
     * Description : This Method is used to delete Add Subject.
     *
     * @param request
     * @return
     */
    @Override
    public List delete(HttpServletRequest request) {
        StudentRegistration studentRegistration = null;
        StudentRegistrationId studentRegistrationId = null;
        StudentRegistration sr = null;
        StudentRegistrationId srId = null;
        PRFacultyStudentTagging prfstdao = null;
        PRFacultyStudentTaggingId prfstid = null;
        StudentPreviousAttendence studentPreviousAttendence = null;
        StudentPreviousAttendenceId studentPreviousAttendenceId = null;

        StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = null;
        StudentSubjectChoiceMaster studentSubjectChoiceMaster = null;
        StudentSubjectChoiceDetail studentSubjectChoiceDetail = null;
        StudentSubjectChoiceDetailId studentSubjectChoiceDetailId = null;
        List err = null;
        String Memberid = jirpsession.getJsessionInfo().getMemberid();
        List<PRFacultyStudentTagging> recordsToDeletePRFST = new ArrayList<PRFacultyStudentTagging>();
        List<FacultyStudentTaggingId> recordsToDeleteFST = new ArrayList<FacultyStudentTaggingId>();
        List<StudentRegistration> recordsToUpdateSR = new ArrayList<StudentRegistration>();
        List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail = new ArrayList<StudentSubjectChoiceDetail>();
        List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster = new ArrayList<StudentSubjectChoiceMaster>();
        List<StudentPreviousAttendence> attendanceHistory = new ArrayList<StudentPreviousAttendence>();
        try {
            String[] selectedcheckboxvalue = request.getParameter("selectedcheckboxvalue").toString().split("~@~");
            String instituteid = selectedcheckboxvalue[0];
            String registrationid = selectedcheckboxvalue[1];
            String studentid = selectedcheckboxvalue[2];
            String subjectid = selectedcheckboxvalue[7];
            String stynumber = selectedcheckboxvalue[8];
            String basketid = selectedcheckboxvalue[9];
            String styType = selectedcheckboxvalue[10];
            String subsectionid = selectedcheckboxvalue[6];
            String sectionid = selectedcheckboxvalue[5];
            String programid = selectedcheckboxvalue[4];
            String acadmicYear = selectedcheckboxvalue[3];
            byte styNo = Byte.decode(stynumber);
            List<StyType> list = daoFactory.getStyTypeDAO().getStyType(instituteid, "REG");
            String stytypeid = ((StyType) list.get(0)).getId().getStytypeid();
            String dropListLength = request.getParameter("dropListLength");
            String[] dropLength = dropListLength.split(",");
            String selectedDropvalue = request.getParameter("selectedDropvalue");
            String[] dropvalues = selectedDropvalue.split(",");

            //-------------------StudentSubjectChoiceMaster --------------------------------
//            List marksentrydata = (List) daoFactory.getFacultyStudentTaggingDAO().getStudentMarksAwarded(studentid, subjectid, instituteid, registrationid);
//            if (marksentrydata.size() <= 0) {
            studentSubjectChoiceMasterId = new StudentSubjectChoiceMasterId();
            studentSubjectChoiceMaster = new StudentSubjectChoiceMaster();
            studentSubjectChoiceMasterId.setInstituteid(instituteid);
            studentSubjectChoiceMasterId.setRegistrationid(registrationid);
            studentSubjectChoiceMasterId.setStudentid(studentid);
            studentSubjectChoiceMasterId.setStynumber(styNo);
            studentSubjectChoiceMasterId.setSubjectid(subjectid);
            StudentSubjectChoiceMaster dto = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(studentSubjectChoiceMasterId);
            if (dto != null) {
//                    dto.setId(studentSubjectChoiceMasterId);
                dto.setBasketid(basketid);
                dto.setStytypeid(stytypeid);
                dto.setSubjectrunning("Y");
                dto.setChoice(new Byte("1"));
                if (styType.equals("BACKLOG")) {
                    dto.setPart("PART C");
                }
                recordsToInsOrUpdChoiceMaster.add(dto);
            }
//-------------------StudentSubjectChoiceDetail --------------------------------
            String componentid = "";
            for (int j = 0; j < dropvalues.length; j++) {
                String[] batch = dropvalues[j].split("~@@~");

                String[] batchid = batch[0].split("@");//staffid @fstid @academicyear @programid @sectionid @stynumber @subsectionid
                componentid = batch[1];
                String fstid = batchid[1].toString();
                studentSubjectChoiceDetail = new StudentSubjectChoiceDetail();
                studentSubjectChoiceDetailId = new StudentSubjectChoiceDetailId();
                studentSubjectChoiceDetailId.setInstituteid(instituteid);
                studentSubjectChoiceDetailId.setRegistrationid(registrationid);
                studentSubjectChoiceDetailId.setStudentid(studentid);
                studentSubjectChoiceDetailId.setStynumber(styNo);
                studentSubjectChoiceDetailId.setSubjectcomponentid(componentid);
                studentSubjectChoiceDetailId.setSubjectid(subjectid);
                StudentSubjectChoiceDetail ide = (StudentSubjectChoiceDetail) daoFactory.getStudentSubjectChoiceDetailDAO().findByPrimaryKey(studentSubjectChoiceDetailId);
                if (ide == null) {
                    studentSubjectChoiceDetail.setId(studentSubjectChoiceDetailId);
                    studentSubjectChoiceDetail.setFstid(fstid);
                    studentSubjectChoiceDetail.setSubsectionid(subsectionid);
                    recordsToInsOrUpdChoicedetail.add(studentSubjectChoiceDetail);
                } else {
//                        studentSubjectChoiceDetail.setId(studentSubjectChoiceDetailId);
                    ide.setFstid("");
                    ide.setSubsectionid(subsectionid);
                    recordsToInsOrUpdChoicedetail.add(ide);
                }
            }

            /**
             * ************************************************
             */
            if (dropLength.length == 1) {
                studentRegistration = new StudentRegistration();
                studentRegistrationId = new StudentRegistrationId();
                studentRegistrationId.setInstituteid(instituteid);
                studentRegistrationId.setRegistrationid(registrationid);
                studentRegistrationId.setStudentid(studentid);
                studentRegistration = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(studentRegistrationId);
                studentRegistration.setSubjecttagged("N");
                studentRegistration.setRegconfirmation("N");
                studentRegistration.setRegconfirmationuser(Memberid);
                studentRegistration.setRegconfirmatiodate(null);
                studentRegistration.setSstpopulated("N");
                recordsToUpdateSR.add(studentRegistration);

            }

            /**
             * ************************************************
             */
            for (int i = 0; i < dropvalues.length; i++) {
                // String[] dropvalue = dropvalues.split("@");
                String[] batchid = dropvalues[i].split("@");//staffid @fstid @academicyear @programid @sectionid @stynumber @subsectionid
                //String componentid = gridObj.get("componentid").toString();
                String fstid = batchid[1].toString();
                Map fstParams = new HashMap();
                fstParams.put("instituteid", "'" + instituteid + "'");
                fstParams.put("fstid", "'" + fstid + "'");
                fstParams.put("studentid", "'" + studentid + "'");
                List<Object[]> facStudTagFST = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findFSTFromFacultyStudent", fstParams);

                String studentFstId = "";
                int FSTFlag = 0;
                if (facStudTagFST.size() == 1) {
                    FSTFlag = 1;
                    studentFstId = facStudTagFST.get(0)[0].toString();
                }
                if (FSTFlag > 0) {
                    if (!studentFstId.equals(null)) {
                        List attendanceList = (List) daoFactory.getStudentAttendanceDAO().getStudentAttendance(instituteid, studentid, studentFstId);
                        if (attendanceList.size() > 0) {
                            for (int k = 0; k < attendanceList.size(); k++) {
                                StudentAttendance alist = (StudentAttendance) attendanceList.get(k);
                                studentPreviousAttendence = new StudentPreviousAttendence();
                                studentPreviousAttendenceId = new StudentPreviousAttendenceId();
                                studentPreviousAttendenceId.setInstituteid(alist.getId().getInstituteid());
                                studentPreviousAttendenceId.setStudentid(alist.getId().getStudentid());
                                studentPreviousAttendenceId.setAttendancedate(alist.getId().getAttendancedate());
                                studentPreviousAttendenceId.setAttendancetype(alist.getId().getAttendancetype());
                                studentPreviousAttendenceId.setClasstimefrom(alist.getId().getClasstimefrom());
                                studentPreviousAttendenceId.setClasstimeupto(alist.getId().getClasstimeupto());
                                studentPreviousAttendenceId.setRegistrationid(registrationid);
                                studentPreviousAttendenceId.setProgramid(programid);
                                studentPreviousAttendenceId.setAcademicyear(acadmicYear);
                                studentPreviousAttendenceId.setStynumber(styNo);
                                studentPreviousAttendenceId.setSectionid(sectionid);
                                studentPreviousAttendenceId.setStytypeid("REG");
                                studentPreviousAttendenceId.setSubjectcomponentid(componentid);
                                studentPreviousAttendenceId.setSubjectid(subjectid);
                                studentPreviousAttendenceId.setBasketid(basketid);
                                studentPreviousAttendenceId.setStytypeid(stytypeid);
                                studentPreviousAttendenceId.setStaffid(alist.getEntrybyfacultyid());
                                studentPreviousAttendenceId.setStafftype(alist.getEntrybyfacultytype());
                                studentPreviousAttendence.setId(studentPreviousAttendenceId);
                                studentPreviousAttendence.setPresent(alist.getPresent());
                                studentPreviousAttendence.setStudentfstid(alist.getId().getStudentfstid());
                                studentPreviousAttendence.setEntrydate(alist.getEntrydate());
                                attendanceHistory.add(studentPreviousAttendence);

                            }
                        }
                        try {

                            String qry = "delete from StudentAttendance a where a.id.instituteid = '" + instituteid + "' and a.id.studentid = '" + studentid + "' and a.id.studentfstid = '" + studentFstId + "'";
                            daoFactory.getFacultyStudentTaggingDAO().commomDeleteForApplicationMaster(qry);
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                        FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                        id.setInstituteid(instituteid);
                        id.setStudentid(studentid);
                        id.setStudentfstid(studentFstId);
                        if (daoFactory.getFacultyStudentTaggingDAO().checkIfChildExist(id) == 0) {
                            recordsToDeleteFST.add(id);
                        }
                    }
                }
                prfstid = new PRFacultyStudentTaggingId();
                prfstid.setInstituteid(instituteid);
                prfstid.setFstid(fstid);
                prfstid.setStudentid(studentid);
                prfstdao = (PRFacultyStudentTagging) daoFactory.getPrFacultyStudentTaggingDAO().findByPrimaryKey(prfstid);
                if (prfstid != null) {
                    recordsToDeletePRFST.add(prfstdao);
                }
            }
            err = new ArrayList<String>();
            err.add(daoFactory.getFacultyStudentTaggingDAO().insertFacultyStudentTagging(recordsToDeleteFST, recordsToUpdateSR, recordsToDeletePRFST, registrationid, instituteid, studentid, recordsToInsOrUpdChoiceMaster, recordsToInsOrUpdChoicedetail, attendanceHistory));
//            } else {
//                err = new ArrayList<String>();
//                err.add("This Subject Can't be droped, Since further process's already done..!, Marks Entry already has been done.");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("fail");
        }
        return err;
    }

    /**
     * Description: This method is used to Add current & back Subject
     *
     * @param request
     * @return
     */
    public List AddCurrent_BackSubject(HttpServletRequest request) {
        String enrollmentNo = "";
        StudentMaster sm = null;
        TempRollNumberControl temrolcon = null;
        TempRollNumberControlId temrolconid = null;
        FacultyStudentTagging facultyStudentTagging = null;
        FacultySubjectTagging facultysubjecttagging = null;
        FacultySubjectTaggingId facultysubjecttaggingId = null;
        PRFacultyStudentTagging prFacultyStudentTagging = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        StudentRegistration studentRegistration = null;
        StudentRegistrationId srId = null;
        StudentMaster smDto = null;
        StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = null;
        StudentSubjectChoiceMaster studentSubjectChoiceMaster = null;
        StudentSubjectChoiceDetail studentSubjectChoiceDetail = null;
        StudentSubjectChoiceDetailId studentSubjectChoiceDetailId = null;
        List err = null;
        String Memberid = jirpsession.getJsessionInfo().getMemberid();
        List<FacultyStudentTagging> recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
        List<FacultyStudentTaggingId> recordsToDeleteFST = new ArrayList<FacultyStudentTaggingId>();
        List<PRFacultyStudentTagging> recordsToInsOrUpdPRFST = new ArrayList<PRFacultyStudentTagging>();
        List<StudentRegistration> recordsToUpdateSR = new ArrayList<StudentRegistration>();
        List<FacultySubjectTagging> FSTList = new ArrayList<FacultySubjectTagging>();
        List<ProgramSubjectTagging> PSTList = new ArrayList<ProgramSubjectTagging>();
        List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail = new ArrayList<StudentSubjectChoiceDetail>();
        List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster = new ArrayList<StudentSubjectChoiceMaster>();
        BusinessService businessService = null;
        try {
            String[] selectedcheckboxIds = request.getParameter("selectedcheckboxvalue").toString().split(",");
            String registrationid = "";
            String studentid = "";
            String instituteid = "";
            String fstid = "";
            String componentid = "";
            for (int j = 0; j < selectedcheckboxIds.length; j++) {
                String[] selectedBaskete_comid = selectedcheckboxIds[j].split("~#@~");
                String[] selectedcheckboxvalue = selectedBaskete_comid[0].split("~@~");
                String[] selectedBaskete_Mode = selectedBaskete_comid[1].split("~@~");
                String[] selectedBaskete = selectedBaskete_Mode[0].split("#~#");
                String mode = selectedBaskete_Mode[1].toString();
                String regDate = selectedBaskete_Mode[2].toString();
                Date regConfirmationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
//                if (!regDate.equals("")) {
//                    regConfirmationDate = df.parse(regDate);
//                }
                instituteid = selectedcheckboxvalue[0];
                registrationid = selectedcheckboxvalue[1];
                studentid = selectedcheckboxvalue[2];
                String acadyear = selectedcheckboxvalue[3];
                String programid = selectedcheckboxvalue[4];
                String sectionid = selectedcheckboxvalue[5];
                String subjectid = selectedcheckboxvalue[7];
                String stynumber = selectedcheckboxvalue[8];
                String basketid = selectedcheckboxvalue[9];
                String auditflag = selectedcheckboxvalue[10].split("br")[1];
                String credit = selectedcheckboxvalue[11];
                String departmentid = selectedcheckboxvalue[13];
                String subsectionid = selectedcheckboxvalue[6];
                byte styNo = Byte.decode(stynumber);
                List<StyType> list = daoFactory.getStyTypeDAO().getStyType(instituteid, "REG");
                String stytypeid = ((StyType) list.get(0)).getId().getStytypeid();

                //-------------------ProgramSubjectTagging and  ProgramSubjectDetail--------------------------------    
                List checkPst = (List) daoFactory.getFacultySubjectTaggingDAO().checkInPST(instituteid, registrationid, subjectid, acadyear, programid, Short.parseShort(stynumber), sectionid, basketid);

                if (checkPst == null || checkPst.size() == 0) {
                    Set set = new HashSet();
//                    PSTList = new ArrayList<ProgramSubjectTagging>();
                    programSubjectTagging = new ProgramSubjectTagging();
                    programSubjectTaggingId = new ProgramSubjectTaggingId();
                    businessService = new BusinessService(daoFactory);
                    String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                    businessService.updateInIdGenerationControl();
                    programSubjectTaggingId.setInstituteid(instituteid);
                    programSubjectTaggingId.setRegistrationid(registrationid);
                    programSubjectTaggingId.setProgramsubjectid(programsubjectid);
                    programSubjectTagging.setAcademicyear(acadyear);
                    programSubjectTagging.setProgramid(programid);
                    programSubjectTagging.setSectionid(sectionid);
                    programSubjectTagging.setStynumber(Short.parseShort(stynumber));
                    programSubjectTagging.setBasketid(basketid);
                    programSubjectTagging.setSubjectid(subjectid);
                    programSubjectTagging.setPstpopulated("N");
                    programSubjectTagging.setSubjectrunning("Y");
                    programSubjectTagging.setCustomfinalized("N");
                    programSubjectTagging.setDepartmentid(departmentid);
                    programSubjectTagging.setCredits(Double.parseDouble(credit));
                    programSubjectTagging.setDeactive("N");
                    programSubjectTagging.setId(programSubjectTaggingId);
                    List<Object[]> componentids = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getComponentIds(instituteid, subjectid, registrationid, programid, acadyear);
                    if (componentids.size() > 0) {
                        for (int i = 0; i < componentids.size(); i++) {
                            Object[] obj = componentids.get(i);
                            programSubjectDetail = new ProgramSubjectDetail();
                            programSubjectDetailId = new ProgramSubjectDetailId();
                            programSubjectDetailId.setInstituteid(instituteid);
                            programSubjectDetailId.setProgramsubjectid(programsubjectid);
                            programSubjectDetailId.setRegistrationid(registrationid);
                            programSubjectDetailId.setSubjectcomponentid(obj[0].toString());
                            set.add(programSubjectDetailId);
                        }
                    }
                    programSubjectTagging.setProgramsubjectdetails(set);
                    PSTList.add(programSubjectTagging);
//                    businessService.insertInIdGenerationControl(PSTList);
//                    businessService.closeSession();

                }

                //-------------------StudentSubjectChoiceMaster --------------------------------            
                studentSubjectChoiceMasterId = new StudentSubjectChoiceMasterId();
                studentSubjectChoiceMaster = new StudentSubjectChoiceMaster();
                studentSubjectChoiceMasterId.setInstituteid(instituteid);
                studentSubjectChoiceMasterId.setRegistrationid(registrationid);
                studentSubjectChoiceMasterId.setStudentid(studentid);
                studentSubjectChoiceMasterId.setStynumber(styNo);
                studentSubjectChoiceMasterId.setSubjectid(subjectid);
                StudentSubjectChoiceMaster dto = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(studentSubjectChoiceMasterId);
                if (dto == null) {
                    studentSubjectChoiceMaster.setId(studentSubjectChoiceMasterId);
                    studentSubjectChoiceMaster.setBasketid(basketid);
                    studentSubjectChoiceMaster.setStytypeid(stytypeid);
                    studentSubjectChoiceMaster.setSubjectrunning("Y");
                    studentSubjectChoiceMaster.setChoice(new Byte("1"));
                    if (auditflag.equals("A")) {
                        studentSubjectChoiceMaster.setAuditsubject("Y");
                    } else {
                        studentSubjectChoiceMaster.setAuditsubject("N");
                    }
                    recordsToInsOrUpdChoiceMaster.add(studentSubjectChoiceMaster);
                }
//-------------------StudentSubjectChoiceDetail --------------------------------
                if ("S".equals(mode)) {
                    for (int i = 0; i < selectedBaskete.length; i++) {
                        String[] batchids = selectedBaskete[i].split("~@@~");//staffid @fstid @academicyear @programid @sectionid @stynumber @subsectionid
                        String[] batchid = batchids[0].split("@");//staffid @fstid @academicyear @programid @sectionid @stynumber @subsectionid
                        componentid = batchids[1];
                        fstid = batchid[1].toString();
                        String ttrefernceid = batchid[7].toString();
                        List checkFstIdExistOrNot = daoFactory.getFacultySubjectTaggingDAO().checkFstIdExistOrNot(instituteid, registrationid, subjectid, acadyear, programid, styNo, sectionid, stytypeid, componentid, subsectionid);
                        if (checkFstIdExistOrNot == null || checkFstIdExistOrNot.isEmpty()) {
//                            FSTList = new ArrayList<FacultySubjectTagging>();
                            businessService = new BusinessService(daoFactory);
                            fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                            businessService.updateInIdGenerationControl();
                            facultysubjecttagging = new FacultySubjectTagging();
                            facultysubjecttaggingId = new FacultySubjectTaggingId();
                            facultysubjecttaggingId.setFstid(fstid);
                            facultysubjecttaggingId.setInstituteid(instituteid);
                            facultysubjecttagging.setId(facultysubjecttaggingId);
                            facultysubjecttagging.setAcademicyear(acadyear);
                            facultysubjecttagging.setBasketid(basketid);
                            facultysubjecttagging.setProgramid(programid);
                            facultysubjecttagging.setRegistrationid(registrationid);
                            facultysubjecttagging.setSectionid(sectionid);
                            facultysubjecttagging.setStynumber(styNo);
                            facultysubjecttagging.setStytypeid(stytypeid);
                            facultysubjecttagging.setSubjectid(subjectid);
                            facultysubjecttagging.setTtreferenceid(ttrefernceid);
                            facultysubjecttagging.setSubsectionid(subsectionid);
                            facultysubjecttagging.setSubjectcomponentid(componentid);
                            FSTList.add(facultysubjecttagging);
//                           businessService.updateInIdGenerationControl();
//                            businessService.closeSession();

                        }
                        List<FacultyStudentTagging> fstList = daoFactory.getFacultyStudentTaggingDAO().getFSTID(instituteid, "('" + fstid + "')", studentid);
                        if (fstList != null && fstList.size() == 0) {
                            facultyStudentTagging = new FacultyStudentTagging();
                            FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                            id.setInstituteid(instituteid);
                            businessService = new BusinessService(daoFactory);
//                            recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
                            String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                            businessService.updateInIdGenerationControl();
                            id.setStudentfstid(StudentFstID);
                            id.setStudentid(studentid);
                            facultyStudentTagging.setId(id);
                            facultyStudentTagging.setFstid(fstid);
                            facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                            facultyStudentTagging.setEntrydate(new Date());
                            if (auditflag.equals("A")) {
                                facultyStudentTagging.setAuditsubject("Y");
                            } else {
                                facultyStudentTagging.setAuditsubject("N");
                            }
                            facultyStudentTagging.setDeactive("N");
                            facultyStudentTagging.setRegistrationid(registrationid);
                            facultyStudentTagging.setSubjectid(subjectid);
                            facultyStudentTagging.setRegconfirmationdate(regConfirmationDate);
                            recordsToInsertFST.add(facultyStudentTagging);
//                            businessService.updateInIdGenerationControl();
//                            businessService.closeSession();
                        }
                        studentSubjectChoiceDetail = new StudentSubjectChoiceDetail();
                        studentSubjectChoiceDetailId = new StudentSubjectChoiceDetailId();
                        studentSubjectChoiceDetailId.setInstituteid(instituteid);
                        studentSubjectChoiceDetailId.setRegistrationid(registrationid);
                        studentSubjectChoiceDetailId.setStudentid(studentid);
                        studentSubjectChoiceDetailId.setStynumber(styNo);
                        studentSubjectChoiceDetailId.setSubjectcomponentid(componentid);
                        studentSubjectChoiceDetailId.setSubjectid(subjectid);
                        studentSubjectChoiceDetail.setId(studentSubjectChoiceDetailId);
                        studentSubjectChoiceDetail.setFstid(fstid);
                        studentSubjectChoiceDetail.setSubsectionid(subsectionid);
                        recordsToInsOrUpdChoicedetail.add(studentSubjectChoiceDetail);
//--------------------UPDATE in PRFacultyStudentTagging---------------------------------------------------
                        prFacultyStudentTagging = new PRFacultyStudentTagging();
                        PRFacultyStudentTaggingId prId = new PRFacultyStudentTaggingId();
                        prId.setInstituteid(instituteid);
                        prId.setFstid(fstid);
                        prId.setStudentid(studentid);
                        prFacultyStudentTagging.setId(prId);
                        prFacultyStudentTagging.setSstpopulated("Y");
                        prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                        prFacultyStudentTagging.setPopulationdate(new Date());
                        prFacultyStudentTagging.setRegistrationid(registrationid);
                        if (auditflag.equals("A")) {
                            prFacultyStudentTagging.setAuditsubject("Y");
                        } else {
                            prFacultyStudentTagging.setAuditsubject("N");
                        }
                        prFacultyStudentTagging.setDeactive("N");
                        recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);
                    }
                } else {
                    // Adding Exam Mode back paper                     
                    Map saveParams = new HashMap();
                    saveParams.put("instid", "'" + instituteid + "'");
                    saveParams.put("regid", "'" + registrationid + "'");
                    saveParams.put("acadyear", "'" + acadyear + "'");
                    saveParams.put("studid", "'" + studentid + "'");
                    saveParams.put("prid", "'" + programid + "'");
                    saveParams.put("styno", "'" + styNo + "'");
                    saveParams.put("secid", "'" + sectionid + "'");
                    saveParams.put("subsecid", "'" + subsectionid + "'");
                    saveParams.put("subjectid", "'" + subjectid + "'");
                    saveParams.put("basketid", "'" + basketid + "'");
                    List<Object[]> componentLst = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findStudentSubjectComponentHeader_AddDrop", saveParams);
                    if (componentLst != null && !componentLst.isEmpty()) {
                        for (int i = 0; i < componentLst.size(); i++) {
                            componentid = componentLst.get(i)[0].toString();
                            List checkFstIdExistOrNot = daoFactory.getFacultySubjectTaggingDAO().checkFstIdExistOrNot(instituteid, registrationid, subjectid, acadyear, programid, styNo, sectionid, stytypeid, componentid, subsectionid);
                            if (checkFstIdExistOrNot == null || checkFstIdExistOrNot.isEmpty()) {

                                businessService = new BusinessService(daoFactory);
                                fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                businessService.updateInIdGenerationControl();
                                facultysubjecttagging = new FacultySubjectTagging();
                                facultysubjecttaggingId = new FacultySubjectTaggingId();
                                facultysubjecttaggingId.setFstid(fstid);
                                facultysubjecttaggingId.setInstituteid(instituteid);
                                facultysubjecttagging.setId(facultysubjecttaggingId);
                                facultysubjecttagging.setAcademicyear(acadyear);
                                facultysubjecttagging.setBasketid(basketid);
                                facultysubjecttagging.setProgramid(programid);
                                facultysubjecttagging.setRegistrationid(registrationid);
                                facultysubjecttagging.setSectionid(sectionid);
                                facultysubjecttagging.setStynumber(styNo);
                                facultysubjecttagging.setStytypeid(stytypeid);
                                facultysubjecttagging.setSubjectid(subjectid);
                                facultysubjecttagging.setSubsectionid(subsectionid);
                                //                               facultysubjecttagging.setTtreferenceid(ttrefernceid);
                                facultysubjecttagging.setSubjectcomponentid(componentid);
                                FSTList.add(facultysubjecttagging);

                                facultyStudentTagging = new FacultyStudentTagging();
                                FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                                id.setInstituteid(instituteid);
                                businessService = new BusinessService(daoFactory);
                                String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                businessService.updateInIdGenerationControl();
                                id.setStudentfstid(StudentFstID);
                                id.setStudentid(studentid);
                                facultyStudentTagging.setId(id);
                                facultyStudentTagging.setFstid(fstid);
                                facultyStudentTagging.setDeactive("N");
                                facultyStudentTagging.setRegistrationid(registrationid);
                                facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                facultyStudentTagging.setEntrydate(new Date());
                                facultyStudentTagging.setSubjectid(subjectid);
                                if (auditflag.equals("A")) {
                                    facultyStudentTagging.setAuditsubject("Y");
                                } else {
                                    facultyStudentTagging.setAuditsubject("N");
                                }
                                recordsToInsertFST.add(facultyStudentTagging);

                                prFacultyStudentTagging = new PRFacultyStudentTagging();
                                PRFacultyStudentTaggingId prId = new PRFacultyStudentTaggingId();
                                prId.setInstituteid(instituteid);
                                prId.setFstid(fstid);
                                prId.setStudentid(studentid);
                                prFacultyStudentTagging.setId(prId);
                                prFacultyStudentTagging.setSstpopulated("Y");
                                prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                                prFacultyStudentTagging.setPopulationdate(new Date());
                                prFacultyStudentTagging.setRegistrationid(registrationid);
                                prFacultyStudentTagging.setDeactive("N");
                                if (auditflag.equals("A")) {
                                    prFacultyStudentTagging.setAuditsubject("Y");
                                } else {
                                    prFacultyStudentTagging.setAuditsubject("N");
                                }
                                recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);

                                studentSubjectChoiceDetail = new StudentSubjectChoiceDetail();
                                studentSubjectChoiceDetailId = new StudentSubjectChoiceDetailId();
                                studentSubjectChoiceDetailId.setInstituteid(instituteid);
                                studentSubjectChoiceDetailId.setRegistrationid(registrationid);
                                studentSubjectChoiceDetailId.setStudentid(studentid);
                                studentSubjectChoiceDetailId.setStynumber(styNo);
                                studentSubjectChoiceDetailId.setSubjectcomponentid(componentid);
                                studentSubjectChoiceDetailId.setSubjectid(subjectid);
                                studentSubjectChoiceDetail.setId(studentSubjectChoiceDetailId);
                                studentSubjectChoiceDetail.setFstid(fstid);
                                studentSubjectChoiceDetail.setSubsectionid(subsectionid);
                                recordsToInsOrUpdChoicedetail.add(studentSubjectChoiceDetail);

                            } else {
                                fstid = checkFstIdExistOrNot.get(0).toString();
                                facultyStudentTagging = new FacultyStudentTagging();
                                FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                                id.setInstituteid(instituteid);
                                businessService = new BusinessService(daoFactory);
                                String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                                businessService.updateInIdGenerationControl();
                                id.setStudentfstid(StudentFstID);
                                id.setStudentid(studentid);
                                facultyStudentTagging.setId(id);
                                facultyStudentTagging.setFstid(fstid);
                                facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                                facultyStudentTagging.setEntrydate(new Date());
                                if (auditflag.equals("A")) {
                                    facultyStudentTagging.setAuditsubject("Y");
                                } else {
                                    facultyStudentTagging.setAuditsubject("N");
                                }
                                facultyStudentTagging.setDeactive("N");
                                facultyStudentTagging.setRegistrationid(registrationid);
                                facultyStudentTagging.setSubjectid(subjectid);
                                recordsToInsertFST.add(facultyStudentTagging);

                                prFacultyStudentTagging = new PRFacultyStudentTagging();
                                PRFacultyStudentTaggingId prId = new PRFacultyStudentTaggingId();
                                prId.setInstituteid(instituteid);
                                prId.setFstid(fstid);
                                prId.setStudentid(studentid);
                                prFacultyStudentTagging.setId(prId);
                                prFacultyStudentTagging.setSstpopulated("Y");
                                prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                                prFacultyStudentTagging.setPopulationdate(new Date());
                                prFacultyStudentTagging.setRegistrationid(registrationid);
                                prFacultyStudentTagging.setDeactive("N");
                                if (auditflag.equals("A")) {
                                    prFacultyStudentTagging.setAuditsubject("Y");
                                } else {
                                    prFacultyStudentTagging.setAuditsubject("N");
                                }
                                recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);

                                studentSubjectChoiceDetail = new StudentSubjectChoiceDetail();
                                studentSubjectChoiceDetailId = new StudentSubjectChoiceDetailId();
                                studentSubjectChoiceDetailId.setInstituteid(instituteid);
                                studentSubjectChoiceDetailId.setRegistrationid(registrationid);
                                studentSubjectChoiceDetailId.setStudentid(studentid);
                                studentSubjectChoiceDetailId.setStynumber(styNo);
                                studentSubjectChoiceDetailId.setSubjectcomponentid(componentid);
                                studentSubjectChoiceDetailId.setSubjectid(subjectid);
                                studentSubjectChoiceDetail.setId(studentSubjectChoiceDetailId);
                                studentSubjectChoiceDetail.setFstid(fstid);
                                studentSubjectChoiceDetail.setSubsectionid(subsectionid);
                                recordsToInsOrUpdChoicedetail.add(studentSubjectChoiceDetail);
                            }
                        }
                    }
                }

            }
            //List enrollmentNolist = generateAndSaveEnrollmentNo(genEnrollOrNot, studentid, instituteid);
            err = new ArrayList<String>();
            err.add(daoFactory.getFacultyStudentTaggingDAO().insertFacultyStudentTagging1(recordsToInsertFST, recordsToInsOrUpdPRFST, registrationid, instituteid, studentid, FSTList, PSTList, recordsToInsOrUpdChoiceMaster, recordsToInsOrUpdChoicedetail, businessService));

        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
    }

    public List generateAndSaveEnrollmentNo(String genEnrollOrNot, String studentid, String instituteid) {
        StudentMaster sm = null;
        TempRollNumberControl temrolcon = null;
        TempRollNumberControlId temrolconid = null;
        String enrollmentNo = "";
        int number = 0;
        List retlist = null;
        try {
            if (genEnrollOrNot.equals("Y")) {
                TempRollNumberSetup numberingSetup = null;
                TempRollNumberSetupId id = null;
                String prid = "", prencode = "", bmid = "", bmencode = "", acyr = "", acencode = "", inid = "", inencode = "", pmtyid = "", pmtyencode = "";
                List<Object[]> studata = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getStudentDetailData(studentid, instituteid);
                if (studata.size() > 0) {
                    Object[] obj = studata.get(0);
                    prid = obj[0].toString();
                    prencode = (obj[1] != null ? obj[1].toString() : "");
                    bmid = obj[2].toString();
                    bmencode = (obj[3] != null ? obj[3].toString() : "");
                    acyr = obj[4].toString();
                    acencode = (obj[5] != null ? obj[5].toString() : "");
                    inid = obj[6].toString();
                    inencode = (obj[7] != null ? obj[7].toString() : "");
                    pmtyid = obj[8].toString();
                    pmtyencode = (obj[9] != null ? obj[9].toString() : "");
                    List groupid = (List) daoFactory.getRollNumberingSetupDetailDAO().getGroupidForEnrollNumber(inid, prid, bmid, acyr, pmtyid);
                    if (groupid.size() > 0) {
                        id = new TempRollNumberSetupId();
                        id.setInstituteid(inid);
                        id.setGroupid(groupid.get(0).toString());
                        numberingSetup = (TempRollNumberSetup) daoFactory.getTempRollNumberSetupDAO().findByPrimaryKey(id);
                        List rncList = ((List) daoFactory.getUtilDAO().findSimpleData("maxLastNo_TempRollNumberingControl", new String[]{inid, groupid.get(0).toString()}));
                        if (rncList.size() > 0) {
                            number = Integer.parseInt(rncList.get(0).toString());
                        }
                        number++;
                        //  public Map enrollmentGenerationSetup(RollNumberingSetup numberingSetup, String instituteId, String academicYear, String programId, String branchId, String programTypeId, int number) {
                        String seqId = numberingSetup.getSeqid();
                        Calendar cal = Calendar.getInstance();
                        String temp = "";
                        Byte runningNo = 0;
                        int num = 0;
                        Map returnMap = new HashMap();
                        String retMsg = "For " + numberingSetup.getSeqid();
                        for (int i = 0; i < seqId.length(); i++) {
                            char a = seqId.charAt(i);
                            switch (a) {
                                case 'X':
                                    temp = numberingSetup.getPrefix();
                                    if (temp == null) {
                                        temp = "";
                                        retMsg += " Prefix, ";
                                    }
                                    enrollmentNo = enrollmentNo + temp;
                                    break;
                                case 'S':
                                    temp = numberingSetup.getSuffix();
                                    if (temp == null) {
                                        temp = "";
                                        retMsg += " Suffix, ";
                                    }
                                    enrollmentNo = enrollmentNo + temp;
                                    break;
                                case 'A':
                                    if (numberingSetup.getAcademicyear().equals("Y")) {
                                        temp = acencode;
                                        if (temp == null) {
                                            temp = "";
                                            retMsg += " Enrollment Code[Academic Year], ";
                                        }
                                        enrollmentNo = enrollmentNo + temp;
                                    }
                                    break;
                                case 'I':
                                    if (numberingSetup.getInstitutecode().equals("Y")) {
                                        temp = inencode;
                                        if (temp == null) {
                                            temp = "";
                                            retMsg += " Enrollment Code[InstituteMaster], ";
                                        }
                                        enrollmentNo = enrollmentNo + temp;
                                    }
                                    break;
                                case 'T':
                                    if (numberingSetup.getProgramtype().equals("Y")) {
                                        temp = pmtyencode;
                                        if (temp != null) {
                                            enrollmentNo = enrollmentNo + temp;
                                        } else {
                                            retMsg += " Enrollment Code[ProgramType], ";
                                        }
                                    }
                                    break;
                                case 'P':
                                    if (numberingSetup.getProgramcode().equals("Y")) {
                                        temp = prencode;
                                        if (temp == null) {
                                            temp = "";
                                            retMsg += " Enrollment Code[ProgramMaster], ";
                                        }
                                        enrollmentNo = enrollmentNo + temp;

                                    }
                                    break;
                                case 'B':
                                    if (numberingSetup.getBranchcode().equals("Y")) {
                                        temp = bmencode;
                                        if (temp == null) {
                                            temp = "";
                                            retMsg += " Enrollment Code[BranchMaster], ";
                                        }
                                        enrollmentNo = enrollmentNo + temp;
                                    }
                                    break;
                                case 'M':
                                    if (numberingSetup.getMm().equals("Y")) {
                                        int month = cal.get(Calendar.MONTH) + 1;
                                        enrollmentNo = enrollmentNo + month;
                                    }
                                    break;
                                case 'Y':
                                    if (numberingSetup.getYy().length() == 2) {
                                        enrollmentNo = enrollmentNo + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
                                    } else if (numberingSetup.getYy().length() == 4) {
                                        enrollmentNo = enrollmentNo + cal.get(Calendar.YEAR);
                                    }
                                    break;
                                case 'N':
                                    String str = "";
                                    if (numberingSetup.getRunningno() != null) {
                                        str = StringUtils.leftPad(String.valueOf(number), numberingSetup.getRunningno(), '0');
                                        runningNo = numberingSetup.getRunningno();
                                        num = number;
                                    }
                                    enrollmentNo = enrollmentNo + str;
                                    break;
                            }
                        }
                        retlist = new ArrayList();
                        retlist.add(enrollmentNo);
                        retlist.add(number);
                        retlist.add(groupid.get(0).toString());

//                        temrolcon = new TempRollNumberControl();
//                        temrolconid = new TempRollNumberControlId();
//                        temrolconid.setInstituteid(inid);
//                        temrolconid.setGroupid(groupid.get(0).toString());
//                        temrolconid.setYm(acyr);
//                        temrolcon = (TempRollNumberControl) daoFactory.getTempRollNumberSetupDAO().findByPrimaryKey1(temrolconid);
//                        sm = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentid);
//                        if (sm != null) {
//                            sm.setEnrollmentno(enrollmentNo);
//                            daoFactory.getStudentMasterDAO().update(sm);
//
//                            if (temrolcon == null) {
//                                TempRollNumberControl newrollcon = new TempRollNumberControl();
//                                TempRollNumberControlId newrolconid = new TempRollNumberControlId();
//                                newrolconid.setInstituteid(inid);
//                                newrolconid.setGroupid(groupid.get(0).toString());
//                                newrolconid.setYm(acyr);
//                                newrollcon.setId(newrolconid);
//                                newrollcon.setLastno(enrollmentNo);
//                                newrollcon.setLastrunningno(new BigDecimal(number));
//                                newrollcon.setLastgenerationdate(new Date());
//                            } else {
//                                temrolcon.setLastrunningno(new BigDecimal(number));
//                                daoFactory.getTempRollNumberSetupDAO().update(temrolcon);
//                            }
//                        }
                    } else {
                        retlist = new ArrayList();
                        retlist.add("TempRollNumberSetupNotDefine");
                        retlist.add(acencode + "/" + prencode + "/" + bmencode);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retlist;
    }

    /**
     * Description: This method is used to Update RegAllow
     *
     * @param request
     * @return
     */
    public List updateRegAllow(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date regConfirmationDate = null;
        List retList = null;
        String enrollmentNo = "";
        int number = 0;
        String groupid = "";
        TempRollNumberControl temrolcon = null;
        TempRollNumberControlId temrolconid = null;
        try {
            regConfirmationDate = df.parse(request.getParameter("regConfirm_date"));
            String genEnrollOrNot = request.getParameter("genenrollornot").toString();
            StudentRegistration studentRegistration = null;
            StudentMaster studentMaster = null;
            StudentRegistrationId srId = null;
            FacultyStudentTaggingId id = null;
            FacultyStudentTagging facultystudenttagging = null;
            String instituteid = request.getParameter("studentinstituteid");
            String registrationid = request.getParameter("studentregistrationid");
//            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//            String registrationid = request.getParameter("semCode");
            String studentid = request.getParameter("studentid");
            String acadyear = request.getParameter("acadyear");
            String[] fstids = request.getParameter("fstid").toString().split("~@~");
            String Memberid = jirpsession.getJsessionInfo().getMemberid();
            List list = (List) generateAndSaveEnrollmentNo(genEnrollOrNot, studentid, instituteid);
            if (list != null) {
                if (list.size() > 0) {
                    enrollmentNo = list.get(0).toString();
                    number = Integer.parseInt(list.get(1).toString());
                    groupid = list.get(2).toString();
                }
            }
            studentRegistration = new StudentRegistration();
            srId = new StudentRegistrationId();
            srId.setInstituteid(instituteid);
            srId.setRegistrationid(registrationid);
            srId.setStudentid(studentid);
            studentRegistration = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(srId);
            if (studentRegistration != null) {
                if (studentRegistration.getRegconfirmation() != null) {
                    if (studentRegistration.getRegconfirmation().equals("Y")) {
                        retList = new ArrayList<String>();
                        retList.add("Registration Already Confirmed");
                        return retList;
                    }
                }
                studentRegistration.setSubjecttagged("Y");
                studentRegistration.setRegconfirmation("Y");
                studentRegistration.setRegconfirmatiodate(new Date());
                studentRegistration.setRegconfirmationuser(Memberid);
                studentRegistration.setSstpopulated("Y");
                daoFactory.getStudentRegistrationDAO().update(studentRegistration);
                temrolcon = new TempRollNumberControl();
                temrolconid = new TempRollNumberControlId();
                temrolconid.setInstituteid(instituteid);
                temrolconid.setGroupid(groupid);
                temrolconid.setYm(acadyear);
                temrolcon = (TempRollNumberControl) daoFactory.getTempRollNumberSetupDAO().findByPrimaryKey1(temrolconid);

                if (temrolcon == null) {
                    TempRollNumberControl newrollcon = new TempRollNumberControl();
                    TempRollNumberControlId newrolconid = new TempRollNumberControlId();
                    newrolconid.setInstituteid(instituteid);
                    newrolconid.setGroupid(groupid);
                    newrolconid.setYm(acadyear);
                    newrollcon.setId(newrolconid);
                    newrollcon.setLastno(enrollmentNo);
                    newrollcon.setLastrunningno(new BigDecimal(number));
                    newrollcon.setLastgenerationdate(new Date());
                } else {
                    temrolcon.setLastrunningno(new BigDecimal(number));
                    daoFactory.getTempRollNumberSetupDAO().update(temrolcon);
                }
                studentMaster = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentid);
                studentMaster.setStynumber(studentRegistration.getStynumber());
                daoFactory.getStudentMasterDAO().update(studentMaster);
                for (int i = 0; i < fstids.length; i++) {
                    String[] fstidltp = fstids[i].toString().split(",");
                    for (int j = 0; j < fstidltp.length; j++) {
                        String studentfstlist = (String) daoFactory.getFacultyStudentTaggingDAO().getStudentFstId(instituteid, studentid, fstidltp[j].toString());
                        id = new FacultyStudentTaggingId(instituteid, studentfstlist, studentid);
                        facultystudenttagging = new FacultyStudentTagging();
                        facultystudenttagging = (FacultyStudentTagging) daoFactory.getFacultyStudentTaggingDAO().findByPrimaryKey(id);
                        if (facultystudenttagging != null) {
                            facultystudenttagging.setRegconfirmationdate(regConfirmationDate);
                            daoFactory.getFacultyStudentTaggingDAO().update(facultystudenttagging);
                        }
                    }
                }

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
    public List getFacultyList(HttpServletRequest request) {
//      String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String departmentid = request.getParameter("departmentid");
        String facultyFrom = request.getParameter("facultyFrom");
        String subjectid = request.getParameter("subjectid");
        String instituteid = request.getParameter("studentinstituteid");
        String sturegistrationid = request.getParameter("studentregistrationid");
        List list0 = null;
        List list1 = null;
        List returnList = new ArrayList();
        try {
            list0 = (List) daoFactory.getV_StaffDAO().getStaff(departmentid, instituteid, facultyFrom);
            list1 = (List) daoFactory.getSubjectComponentDAO().getComponentIdOfSubjectForAddDrop(instituteid, subjectid, sturegistrationid);
            if (list1.isEmpty() || list1.size() < 1) {
                list1 = (List) daoFactory.getSubjectComponentDAO().getComponentIdOfSubject(instituteid, subjectid);
            }
            returnList.add(list0);
            returnList.add(list1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    @Override
    public List saveLoadDistribution(HttpServletRequest request) {
        List list = new ArrayList();
        BusinessService businessService = null;
        Tt_Timetableapproval dto = null;
        Tt_TimetableapprovalId id = null;
        try {
//            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String instunqid = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
            String data[] = request.getParameter("allData").trim().split(",");
            String regId = data[28];
            String instituteId = data[27];
            String semTypeId = data[23];
            String depId = data[1];
            TT_TimeTable ttdao = null;
            TT_TimeTableId ttid = new TT_TimeTableId();
            ttid.setInstituteid(instituteId);
            ttid.setRegistrationid(regId);
            ttdao = (TT_TimeTable) daoFactory.getTt_TimeTableDAO().findByPrimaryKey(ttid);
            if (ttdao == null) {
                ttdao = new TT_TimeTable();
                ttdao.setId(ttid);
                ttdao.setStartdate(new Date());
                ttdao.setEnddate(new Date());
                ttdao.setDeactive("N");
                daoFactory.getTt_TimeTableDAO().add(ttdao);
            }
            businessService = new BusinessService(daoFactory, true);
            String acadyear = data[9];//acadyear 
            String prgid = data[10];//prgid
            String styno = data[11];//styno
            String secid = data[14];//secid
            String subsecid = data[16];//subsecid
            String staffid = data[4];//staffid
            String stafftype = data[5];//stafftype
            String basketid = data[3];//basketid
            String subId = data[2];//subId
            String subCompId = data[6];//subCompId
            String tttransid = businessService.generateId("TTTransactionId", instunqid, "I", false);
            TT_TimeTableAllocation ttAllocation = new TT_TimeTableAllocation();
            TT_TimeTableAllocationId ttAlloctaionId = new TT_TimeTableAllocationId();
            ttAlloctaionId.setTttransid(tttransid);
            ttAlloctaionId.setRegistrationid(regId);
            ttAlloctaionId.setInstituteid(instituteId);
            ttAllocation = (TT_TimeTableAllocation) daoFactory.getTt_TimeTableAllocationDAO().findByPrimaryKey(ttAlloctaionId);
            if (ttAllocation != null) {
                ttAllocation.setStaffid(staffid);
                ttAllocation.setStafftype(stafftype);
                ttAllocation.setStatus("D");
                ttAllocation.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                ttAllocation.setEntrydate(new Date());
                ttAllocation.setDeactive("N");
                daoFactory.getTt_TimeTableAllocationDetailDAO().deleteTtDetailOnTTransREg(tttransid, regId, instituteId);
                ttAllocation.setMultifaculty("N");
                daoFactory.getTt_TimeTableAllocationDAO().saveOrUpdate(ttAllocation);
            } else {
                TT_TimeTableAllocation ttAllocation1 = new TT_TimeTableAllocation();
                ttAllocation1.setId(ttAlloctaionId);
                ttAllocation1.setSubjectid(subId);
                ttAllocation1.setSubjectcomponentid(subCompId);
                ttAllocation1.setRunningdepartmentid(depId);
                ttAllocation1.setStaffid(staffid);
                ttAllocation1.setStafftype(stafftype);
                ttAllocation1.setStatus("D");
                ttAllocation1.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                ttAllocation1.setEntrydate(new Date());
                ttAllocation1.setDeactive("N");
                ttAllocation1.setMultifaculty("N");
                businessService.insertInIdGenerationControl(ttAllocation1);
            }
            //entering parent data in ttTimetableallocationdetail table
            TT_TimeTableAllocationDetail ttd = new TT_TimeTableAllocationDetail();
            TT_TimeTableAllocationDetailId ttId = new TT_TimeTableAllocationDetailId();
            ttId.setInstituteid(instituteId);
            ttId.setProgramid(prgid);
            ttId.setRegistrationid(regId);
            ttId.setSectionid(secid);
            ttId.setStynumber(Integer.parseInt(styno));
            ttId.setStytypeid(semTypeId);
            ttId.setSubsectionid(subsecid);
            ttId.setTttransid(tttransid);
            ttId.setAcademicyear(acadyear);
            ttd.setId(ttId);
            ttd.setBasketid(basketid);
            ttd.setDeactive("N");
            daoFactory.getTt_TimeTableAllocationDetailDAO().saveOrUpdate(ttd);

            id = new Tt_TimetableapprovalId();
            dto = new Tt_Timetableapproval();

            id.setAcademicyear(acadyear);
            id.setProgramid(prgid);
            id.setStynumber(new Byte(styno));
            id.setDepartmentid(depId);
            id.setRegistrationid(regId);
            id.setInstituteid(instituteId);
            dto.setApprovalstatus("A");
            dto.setId(id);
            dto.setApprovaldate(new Date());
            String ttAppCanlData = daoFactory.getStoredProcedureDAO().getTTApprovalAppCanlData(instituteId, regId, depId, "A", prgid, acadyear, styno);
            if ("S".equals(ttAppCanlData)) {
                daoFactory.getAcademicYearDAO().saveOrUpdate(dto);
            }

            businessService.closeSession();
            businessService = null;
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list.add("Error");
            return list;
        }
        return list;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
