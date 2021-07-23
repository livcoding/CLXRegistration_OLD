/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.lowagie.text.Image;
import com.jilit.irp.Report.ReportManager;
import static com.jilit.irp.Report.ReportManager.EXCEL;
import static com.jilit.irp.Report.ReportManager.HTML;
import static com.jilit.irp.Report.ReportManager.PDF;
import static com.jilit.irp.Report.ReportManager.RTF;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.AddDropSubjectIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import com.jilit.irp.persistence.dto.FacultyStudentTagging;
import com.jilit.irp.persistence.dto.FacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.FacultySubjectTagging;
import com.jilit.irp.persistence.dto.FacultySubjectTaggingId;
import com.jilit.irp.persistence.dto.FeeStructureIndividual;
import com.jilit.irp.persistence.dto.FeeStructureIndividualId;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.PRFacultyStudentTagging;
import com.jilit.irp.persistence.dto.PRFacultyStudentTaggingId;
import com.jilit.irp.persistence.dto.ProgramMinMaxLimit;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.Sis_RegistrationActivityMaster;
import com.jilit.irp.persistence.dto.StudentAttendance;
import com.jilit.irp.persistence.dto.StudentGroupCrLimit;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.StudentPhoto;
import com.jilit.irp.persistence.dto.StudentPhotoFetch;
import com.jilit.irp.persistence.dto.StudentPreviousAttendence;
import com.jilit.irp.persistence.dto.StudentPreviousAttendenceId;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetailId;

import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.SubjectMaster;
import com.jilit.irp.util.JIRPDateUtil;
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
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.time.Month;
import org.springframework.ui.ModelMap;
import java.util.Arrays;

/**
 *
 * @author Ashutosh1.kumar
 */
@Service
public class AddDropSubjectService extends ReportManager implements AddDropSubjectIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

//    @Autowired
//    HttpServletResponse response;
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
            List list = daoFactory.getInstituteMasterDAO().getInstituteCodeForAddDrop(userid, "0000RTID1006A0000032");
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
        List list = null;
        try {
            list = (List) daoFactory.getStudentMasterDAO().getStudentInfo(instituteids, enrollmentno, registrationidlist, "advance", "");
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
        List list = null;
        try {
            list = (List) daoFactory.getStudentMasterDAO().getRegistrationCodeWiseStudentMasterData(instituteids, registrationidlist, program, acadyear);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
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
            list = (List) daoFactory.getStudentMasterDAO().getProgramForAddDrop(instituteids, registrationidlist, acadyear);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getElectiveSubjectForSwap(HttpServletRequest request) {
        String registrationid = request.getParameter("semCode");
        String subjecttypeid = request.getParameter("subjecttypeid");
        String credit = request.getParameter("credit");

        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramSubjectDetailDAO().getElectiveSubjectForSwap(instituteid, registrationid, subjecttypeid, new Byte(credit));
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
            List<Sis_RegistrationActivityMaster> list = (List<Sis_RegistrationActivityMaster>) daoFactory.getStudentMasterDAO().getRegistrationActivity(instituteid);
            List<Object[]> slist = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentActivity(instituteid, registrationid, studentid);
            Map registrationActivity = new HashMap();
            list.forEach(n -> registrationActivity.put(n.getId().getActivityid(), n.getActivityname()));
            Iterator itr = registrationActivity.entrySet().iterator();
            List studentActivityList = new ArrayList();
            List creditList = new ArrayList();
            Map creditMap = new HashMap();
            while (itr.hasNext()) {
                Map studentActivity = new HashMap();
                Entry entry = (Entry) itr.next();
                String activity = entry.getKey().toString();
                String activityDesc = entry.getValue().toString();
                boolean flag = true;

                if (slist.size() > 0) {
                    for (int i = 0; i < slist.size(); i++) {
                        if (activity.equals(slist.get(i)[0].toString())) {
                            studentActivity.put("activity", activityDesc);
                            studentActivity.put("activityStatus", slist.get(i)[1] == null ? "" : slist.get(i)[1].toString());
                            studentActivity.put("remark", slist.get(i)[3] == null ? "" : slist.get(i)[3].toString());
                            studentActivity.put("approvrdBy", slist.get(i)[5] == null ? "" : slist.get(i)[5].toString());
                            studentActivity.put("approvedDate", slist.get(i)[4] == null ? "" : slist.get(i)[4].toString());
                            studentActivity.put("regAllow", slist.get(i)[2] == null ? "" : slist.get(i)[2].toString());
                            studentActivityList.add(studentActivity);
                            flag = false;
                        } else {
                            if (i == slist.size() - 1 && flag) {
                                studentActivity.put("activity", activityDesc);
                                studentActivity.put("activityStatus", "");
                                studentActivity.put("remark", "");
                                studentActivity.put("approvrdBy", "");
                                studentActivity.put("approvedDate", "");
                                studentActivity.put("regAllow", "");
                                studentActivityList.add(studentActivity);
                            }
                        }
                    }
                } else {
                    studentActivity.put("activity", activityDesc);
                    studentActivity.put("activityStatus", "");
                    studentActivity.put("remark", "");
                    studentActivity.put("approvrdBy", "");
                    studentActivity.put("approvedDate", "");
                    studentActivity.put("regAllow", "");
                    studentActivityList.add(studentActivity);
                }
            }
            String clientid = jirpsession.getJsessionInfo().getSelectedclientid();
            String userType = jirpsession.getJsessionInfo().getUsertype();

            //-------- min max credits------------------------------------------        
            String minCredit = "";
            String maxCredit = "";
            String takenCredit = "0";
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

            creditMap.put("userType", userType);
            creditMap.put("maxCredit", maxCredit);
            creditMap.put("minCredit", minCredit);
            creditMap.put("takenCredit", takenCredit);
            creditMap.put("remaningCredit", remaningCredit);
            creditList.add(creditMap);
            studentActivityMap.put("studentActivity", studentActivityList);
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
                                daoFactory.getProgramSubjectDetailDAO().add(fsti);
                                studentActivityMap.put("alert", "Please Pay Fee Rs" + lateRegistrationFeeAmount + " As Late Registration Fee");
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
//            } else {
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
            List<Object[]> listBck = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findFailSubject_AddDrop_BM", params);
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
                    String batchid = "";
                    String batchcode = "";
                    Map compareBatchMap = new HashMap();
                    if (addDropList.isEmpty()) {
                        List<Object[]> subjectList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getSubjectDetail(instituteid, registrationid, listBck.get(ii)[0].toString(), listBck.get(ii)[4].toString(), programid);
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
                                String mergefstid = subjectList.get(i)[19] == null ? "*" : subjectList.get(i)[19].toString();
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
                                        batchObj.put("mergefstid", mergefstid);
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
                                        batchObj.put("mergefstid", mergefstid);
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
                                        batchObj.put("mergefstid", mergefstid);
                                        PracList.add(batchObj);
                                        setSubj.add(batchid);
                                    }
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
                            object.put("departmentid", listBck.get(ii)[9]);
                        }
                        addList.add(object);
                    } else {
                        List<Object[]> componentsList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getDropSubjectDetail(instituteid, registrationid, listBck.get(ii)[0].toString(), listBck.get(ii)[4].toString(), programid, studentid);
                        List batchList = new ArrayList();
                        String subjComponent = "";
                        Map batchObj = null;
                        if (componentsList != null && !componentsList.isEmpty()) {
                            for (int i = 0; i < componentsList.size(); i++) {
                                String ttreferenceid = "";
                                if (componentsList.get(i)[9] != null) {
                                    ttreferenceid = componentsList.get(i)[9].toString();
                                }
                                String batchid1 = componentsList.get(i)[2].toString() + "@" + componentsList.get(i)[3].toString() + "@"
                                        + componentsList.get(i)[4].toString() + "@" + componentsList.get(i)[5].toString() + "@"
                                        + componentsList.get(i)[6].toString() + "@" + componentsList.get(i)[7].toString() + "@"
                                        + componentsList.get(i)[8].toString() + "@" + ttreferenceid;
                                String batchcode1 = componentsList.get(i)[10].toString() + "/" + componentsList.get(i)[11].toString() + "/"
                                        + componentsList.get(i)[12].toString() + "/" + componentsList.get(i)[13].toString() + "/"
                                        + componentsList.get(i)[14].toString();
                                if (i == 0) {
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
                            object.put("departmentid", listBck.get(ii)[9]);
                        }
                        dropList.add(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//--------------------------Current Subjects------------------------------------
            List<Object[]> currentSubjectList = (List<Object[]>) daoFactory.getUtilDAO().findNamedQuery("findStudentSubjectData_AddDrop_BM", params);

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
                                    String mergefstid = subjectList.get(i)[19] == null ? "*" : subjectList.get(i)[19].toString();
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
                                            batchObj.put("mergefstid", mergefstid);
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
                                            batchObj.put("mergefstid", mergefstid);
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
                                            batchObj.put("mergefstid", mergefstid);
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
                    } else if (addDropList.get(0).toString().equals("N")) {
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
     * Description : This Method is used to get Gip Subject
     *
     * @param request
     * @return
     */
    public Map getGIPSubject(HttpServletRequest request) {
        Map studentActivityMap = new HashMap();
        String studentid = request.getParameter("studentid");
//        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//        String registrationid = request.getParameter("semCode");
        String academicYear = request.getParameter("academic");
        String programid = request.getParameter("programid");
        String styNumber = request.getParameter("styNumber");
        String branchid = request.getParameter("branchid");
        String sectionid = request.getParameter("sectionid");
        String subsectionid = request.getParameter("subsectionid");
        String groupid = request.getParameter("groupid");
        String instituteid = request.getParameter("studentinstituteid");
        String registrationid = request.getParameter("studentregistrationid");
        String grade = "";
        try {
            List GIPList = new ArrayList();
            List<Object[]> gipCriteria = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getGipCriteria(instituteid, programid);
            if (gipCriteria.size() != 0) {
                String gradeid = "";
                if (gipCriteria.size() > 0) {
                    grade = "between " + gipCriteria.get(0)[0].toString() + " and " + gipCriteria.get(0)[1].toString();
                    String[] criteria = gipCriteria.get(0)[2].toString().split(",");
                    List<Object[]> gipCriteriaGrade = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getGradeCriteriaBased(instituteid, criteria);
                    if (gipCriteriaGrade.size() > 0) {
                        for (int i = 0; i < gipCriteriaGrade.size(); i++) {
                            gradeid = gradeid + "'" + gipCriteriaGrade.get(i) + "'" + ",";
                        }
                        if (gradeid.endsWith(",")) {
                            gradeid = gradeid.substring(0, gradeid.length() - 1);
                        }
                    }
                }
                Map object = null;
//--------------------------GIP Subjects------------------------------------
                List<Object[]> gipSubjectList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getGipSubject(instituteid, registrationid, studentid, programid, gradeid, grade);

                for (int ii = 0; ii < gipSubjectList.size(); ii++) {
                    try {
                        object = new HashMap();
                        object.put("subjtype", "GIP");
                        object.put("subjectid", gipSubjectList.get(ii)[0]);
                        object.put("subjectcode", gipSubjectList.get(ii)[1]);
                        object.put("subjectdesc", gipSubjectList.get(ii)[2]);
                        object.put("credits", gipSubjectList.get(ii)[3]);
                        object.put("stynumber", gipSubjectList.get(ii)[4]);
                        object.put("subjectgroup", gipSubjectList.get(ii)[5] == null ? "" : gipSubjectList.get(ii)[5].toString());

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
                        object.put("instituteid", instituteid);
                        object.put("registrationid", registrationid);
                        object.put("studentid", studentid);
                        object.put("academicyear", academicYear);
                        object.put("programid", programid);
                        object.put("sectionid", sectionid);
                        object.put("subsectionid", subsectionid);
                        List<Object[]> componentsList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getSubjectDetail(instituteid, registrationid, gipSubjectList.get(ii)[0].toString(), "", programid);
                        List LecList = new ArrayList();
                        List PracList = new ArrayList();
                        List TutList = new ArrayList();
                        Map batchObj = null;
                        if (componentsList != null && !componentsList.isEmpty()) {
                            for (int i = 0; i < componentsList.size(); i++) {
                                String mergefstid = componentsList.get(i)[19] == null ? "*" : componentsList.get(i)[19].toString();
                                if ("L".equals(componentsList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", componentsList.get(i)[3].toString());
                                    batchObj.put("batchid", componentsList.get(i)[2].toString());
                                    batchObj.put("componentid", componentsList.get(i)[0].toString());
                                    batchObj.put("componentcode", componentsList.get(i)[1].equals("L") ? "Lecture" : componentsList.get(i)[1]);
                                    batchObj.put("subj", componentsList.get(i)[18].toString());
                                    batchObj.put("mergefstid", mergefstid);
                                    LecList.add(batchObj);
                                }
                                if ("T".equals(componentsList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", componentsList.get(i)[3].toString());
                                    batchObj.put("batchid", componentsList.get(i)[2].toString());
                                    batchObj.put("componentid", componentsList.get(i)[0].toString());
                                    batchObj.put("componentcode", componentsList.get(i)[1].equals("T") ? "Tutorial" : componentsList.get(i)[1]);
                                    batchObj.put("subj", componentsList.get(i)[18].toString());
                                    batchObj.put("mergefstid", mergefstid);
                                    TutList.add(batchObj);
                                }
                                if ("P".equals(componentsList.get(i)[1].toString())) {
                                    batchObj = new HashMap();
                                    batchObj.put("batchcode", componentsList.get(i)[3].toString());
                                    batchObj.put("batchid", componentsList.get(i)[2].toString());
                                    batchObj.put("componentid", componentsList.get(i)[0].toString());
                                    batchObj.put("componentcode", componentsList.get(i)[1].equals("P") ? "Practical" : componentsList.get(i)[1]);
                                    batchObj.put("subj", componentsList.get(i)[18].toString());
                                    batchObj.put("mergefstid", mergefstid);
                                    PracList.add(batchObj);

                                }
                            }
                            object.put("LecList", LecList);
                            object.put("TutList", TutList);
                            object.put("PracList", PracList);

                            GIPList.add(object);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                studentActivityMap.put("Warning", "");
                studentActivityMap.put("GIPList", GIPList);
            } else {
                studentActivityMap.put("Warning", "Grade Improvement Criteria is not defined...!");
            }
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
    public String datacheckfordelete(HttpServletRequest req) {
        String registrationid = req.getParameter("registrationid");
        String studentid = req.getParameter("studentid");
        String subjectid = req.getParameter("subjectid");
        String exists = "NODATA";
        try {
            int size = daoFactory.getStudentAttendanceDAO().checkStudentEventSubjectMarks(studentid, registrationid, studentid, subjectid);
            if (size > 0) {
                exists = "SESM";
            } else {
                size = daoFactory.getStudentAttendanceDAO().checkStudentAttendance(studentid, registrationid, studentid, subjectid);
                if (size > 0) {
                    exists = "SA";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    @Override
    public List delete(HttpServletRequest request) {
        StudentRegistration studentRegistration = null;
        StudentRegistrationId studentRegistrationId = null;
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
            err.add("Error");
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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
                String ddmmyyyy = df.format(d);
                Date regConfirmationDate = null;
                if (!regDate.equals("")) {
                    regConfirmationDate = df.parse(ddmmyyyy);
                }
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
//                    businessService = new BusinessService(daoFactory);
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
                        String selectedfstid = batchid[1].toString();
                        String selectedmergedwithfstid = batchids[3].toString();
                        String ttrefernceid = batchid[7].toString();
                        List checkFstIdExistOrNot = daoFactory.getFacultySubjectTaggingDAO().checkFstIdExistOrNot(instituteid, registrationid, subjectid, acadyear, programid, styNo, sectionid, stytypeid, componentid, subsectionid);
                        if (checkFstIdExistOrNot == null || checkFstIdExistOrNot.isEmpty() || checkFstIdExistOrNot.size() == 0) {
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
                            if (selectedmergedwithfstid.equals("*")) {
                                facultysubjecttagging.setMergewithfstid(selectedfstid);
                            } else {
                                facultysubjecttagging.setMergewithfstid(selectedmergedwithfstid);
                            }
                            FSTList.add(facultysubjecttagging);
//                            businessService.insertInIdGenerationControl(FSTList);
//                            businessService.closeSession();

                        }
                        List<FacultyStudentTagging> fstList = daoFactory.getFacultyStudentTaggingDAO().getFSTID(instituteid, "('" + fstid + "')", studentid);
                        if (fstList != null && fstList.size() == 0) {
//                            businessService = new BusinessService(daoFactory);
//                            recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
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
                            facultyStudentTagging.setRegconfirmationdate(regConfirmationDate);
                            facultyStudentTagging.setSubjectid(subjectid);
                            recordsToInsertFST.add(facultyStudentTagging);
//                            businessService.insertInIdGenerationControl(recordsToInsertFST);
//                            businessService.closeSession();
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
                            recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);
                        }
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
                                facultyStudentTagging.setSubjectid(subjectid);
                                facultyStudentTagging.setEntrydate(new Date());
                                facultyStudentTagging.setEntryby(Memberid);
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
                                prFacultyStudentTagging.setDeactive("N");
                                prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                                prFacultyStudentTagging.setPopulationdate(new Date());
                                prFacultyStudentTagging.setRegistrationid(registrationid);
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
                                prFacultyStudentTagging.setDeactive("N");
                                prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                                prFacultyStudentTagging.setPopulationdate(new Date());
                                prFacultyStudentTagging.setRegistrationid(registrationid);
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

    /**
     * Description: This method is used to add Gip Subject
     *
     * @param request
     * @return
     */
    public List addGipSubject(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        FacultyStudentTagging facultyStudentTagging = null;
        FacultySubjectTagging facultysubjecttagging = null;
        FacultySubjectTaggingId facultysubjecttaggingId = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        PRFacultyStudentTagging prFacultyStudentTagging = null;
        StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = null;
        StudentSubjectChoiceMaster studentSubjectChoiceMaster = null;
        StudentSubjectChoiceDetail studentSubjectChoiceDetail = null;
        StudentSubjectChoiceDetailId studentSubjectChoiceDetailId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        List err = null;
        String Memberid = jirpsession.getJsessionInfo().getMemberid();
        List<FacultyStudentTagging> recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
        List<PRFacultyStudentTagging> recordsToInsOrUpdPRFST = new ArrayList<PRFacultyStudentTagging>();
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
            String basketid = "";
            String departmentid = "";
            for (int j = 0; j < selectedcheckboxIds.length; j++) {
                String[] selectedBaskete_comid = selectedcheckboxIds[j].split("~~");
                String[] selectedcheckboxvalue = selectedBaskete_comid[0].split("~@~");
                String[] selectedBaskete_Mode = selectedBaskete_comid[1].split("~@~");
                String[] selectedBaskete = selectedBaskete_Mode[0].split("#~#");
                String mode = selectedBaskete_Mode[1].toString();
                String regDate = selectedBaskete_Mode[2].toString();
                Date regConfirmationDate = null;
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
                String ddmmyyyy = df.format(d);
                if (!regDate.equals("")) {
                    regConfirmationDate = df.parse(ddmmyyyy);
                }
                instituteid = selectedcheckboxvalue[0];
                registrationid = selectedcheckboxvalue[1];
                studentid = selectedcheckboxvalue[2];
                String acadyear = selectedcheckboxvalue[3];
                String programid = selectedcheckboxvalue[4];
                String sectionid = selectedcheckboxvalue[5];
                String subjectid = selectedcheckboxvalue[7];
                String stynumber = selectedcheckboxvalue[8];
                String credit = selectedcheckboxvalue[9];
                String subsectionid = selectedcheckboxvalue[6];
                byte styNo = Byte.decode(stynumber);
                List<StyType> list = daoFactory.getStyTypeDAO().getStyType(instituteid, "GIP");
                String stytypeid = ((StyType) list.get(0)).getId().getStytypeid();
                List<Object[]> basketidList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getBasketForGip(instituteid, registrationid, studentid, subjectid);
                if (basketidList.size() > 0) {
                    basketid = basketidList.get(0)[0].toString();
                    departmentid = basketidList.get(0)[1].toString();

                    //-------------------ProgramSubjectTagging and  ProgramSubjectDetail--------------------------------    
                    List checkPst = (List) daoFactory.getFacultySubjectTaggingDAO().checkInPST(instituteid, registrationid, subjectid, acadyear, programid, Short.parseShort(stynumber), sectionid, basketid);

                    if (checkPst == null || checkPst.size() == 0) {
                        Set set = new HashSet();
//                        businessService = new BusinessService(daoFactory);
//                        PSTList = new ArrayList<ProgramSubjectTagging>();
                        programSubjectTagging = new ProgramSubjectTagging();
                        programSubjectTaggingId = new ProgramSubjectTaggingId();
                        String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
//                        businessService.insertInIdGenerationControl(PSTList);
//                        businessService.closeSession();

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
                        studentSubjectChoiceMaster.setAuditsubject("N");
                        recordsToInsOrUpdChoiceMaster.add(studentSubjectChoiceMaster);
                    }
                    for (int i = 0; i < selectedBaskete.length; i++) {
                        String[] batchids = selectedBaskete[i].split("~@@~");
                        String[] batchid = batchids[0].split("@");//staffid @fstid @academicyear @programid @sectionid @stynumber @subsectionid
                        componentid = batchids[1];
                        fstid = batchid[1].toString();
                        String ttrefernceid = batchid[7].toString();

                        List checkFstIdExistOrNot = daoFactory.getFacultySubjectTaggingDAO().checkFstIdExistOrNot(instituteid, registrationid, subjectid, acadyear, programid, styNo, sectionid, stytypeid, componentid, subsectionid);
                        if (checkFstIdExistOrNot == null || checkFstIdExistOrNot.isEmpty()) {
//                            businessService = new BusinessService(daoFactory);
//                            FSTList = new ArrayList<FacultySubjectTagging>();
                            fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
//                            businessService.insertInIdGenerationControl(FSTList);
//                            businessService.closeSession();

                        }
                        List<FacultyStudentTagging> fstList = daoFactory.getFacultyStudentTaggingDAO().getFSTID(instituteid, "('" + fstid + "')", studentid);
                        if (fstList != null && fstList.size() == 0) {
//                            businessService = new BusinessService(daoFactory);
//                            recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
                            facultyStudentTagging = new FacultyStudentTagging();
                            FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                            id.setInstituteid(instituteid);
                            String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                            id.setStudentfstid(StudentFstID);
                            id.setStudentid(studentid);
                            facultyStudentTagging.setId(id);
                            facultyStudentTagging.setFstid(fstid);
                            facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                            facultyStudentTagging.setEntrydate(new Date());
                            facultyStudentTagging.setAuditsubject("N");
                            facultyStudentTagging.setDeactive("N");
                            facultyStudentTagging.setRegconfirmationdate(regConfirmationDate);
                            recordsToInsertFST.add(facultyStudentTagging);
//                            businessService.insertInIdGenerationControl(recordsToInsertFST);
//                            businessService.closeSession();
                            //-------------------StudentSubjectChoiceDetail --------------------------------
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
                            prFacultyStudentTagging.setDeactive("N");
                            prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                            prFacultyStudentTagging.setPopulationdate(new Date());
                            recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);
                        }
                    }

                }

            }
            err = new ArrayList<String>();
            err.add(daoFactory.getFacultyStudentTaggingDAO().insertFacultyStudentTagging2(recordsToInsertFST, recordsToInsOrUpdPRFST, registrationid, instituteid, studentid, FSTList, PSTList, recordsToInsOrUpdChoiceMaster, recordsToInsOrUpdChoicedetail, businessService));

        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("fail");
        }
        return err;
    }

    /**
     * Description: This method is used to get Audit Gip Subject
     *
     * @param request
     * @return
     */
    public List auditSubject(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = request.getParameter("studentinstituteid");
            String registrationid = request.getParameter("studentregistrationid");
//            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//            String registrationid = request.getParameter("semCode");
            String programid = request.getParameter("programid");
            String sectionid = request.getParameter("sectionid");
            String academicyear = request.getParameter("academicyear");
            list = (List) daoFactory.getSubjectMasterDAO().getSubjectCodeForAudit(instituteid, registrationid, programid, sectionid, academicyear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Description: This method is used to save Audit Subject
     *
     * @param request
     * @return
     */
    public List saveAuditSubject(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        FacultyStudentTagging facultyStudentTagging = null;
        FacultySubjectTagging facultysubjecttagging = null;
        FacultySubjectTaggingId facultysubjecttaggingId = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        PRFacultyStudentTagging prFacultyStudentTagging = null;
        StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = null;
        StudentSubjectChoiceMaster studentSubjectChoiceMaster = null;
        StudentSubjectChoiceDetail studentSubjectChoiceDetail = null;
        StudentSubjectChoiceDetailId studentSubjectChoiceDetailId = null;
        List err = null;
        String Memberid = jirpsession.getJsessionInfo().getMemberid();
        List<FacultyStudentTagging> recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
        List<PRFacultyStudentTagging> recordsToInsOrUpdPRFST = new ArrayList<PRFacultyStudentTagging>();
        List<FacultySubjectTagging> FSTList = new ArrayList<FacultySubjectTagging>();
        List<ProgramSubjectTagging> PSTList = new ArrayList<ProgramSubjectTagging>();
        List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail = new ArrayList<StudentSubjectChoiceDetail>();
        List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster = new ArrayList<StudentSubjectChoiceMaster>();
        BusinessService businessService = null;
        //Set set = new HashSet();
        try {
            String fstid = "";
            String componentid = "";
            String instituteid = request.getParameter("studentinstituteid");
            String registrationid = request.getParameter("studentregistrationid");
//            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//            String registrationid = request.getParameter("semCode");
            String studentid = request.getParameter("studentid");
            String acadyear = request.getParameter("academic");
            String programid = request.getParameter("programid");
            String sectionid = request.getParameter("sectionid");
            String subjectid = request.getParameter("subjectid");
            String stynumber = request.getParameter("styNumber");
            String credit = request.getParameter("credit");
            String regDate = request.getParameter("classstartdate");
            String departmentid = request.getParameter("runningDeparid");
            Date regConfirmationDate = null;
            if (!regDate.equals("")) {
                regConfirmationDate = df.parse(regDate);
            }
            String subsectionid = request.getParameter("subsectionid");
            String batchdetail[] = request.getParameter("batchdetail").split("@@");  // L@@T@@P batches
            byte styNo = Byte.decode(stynumber);
            List<StyType> list = daoFactory.getStyTypeDAO().getStyType(instituteid, "REG");
            String stytypeid = ((StyType) list.get(0)).getId().getStytypeid();
            List<Object[]> componentids = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getComponentIds(instituteid, subjectid, registrationid, programid, acadyear);
            String basketid = daoFactory.getFacultySubjectTaggingDAO().getBasketForAudit(instituteid, programid, sectionid, stynumber);

            //-------------------ProgramSubjectTagging and  ProgramSubjectDetail--------------------------------    
            List checkPst = (List) daoFactory.getFacultySubjectTaggingDAO().checkInPST(instituteid, registrationid, subjectid, acadyear, programid, Short.parseShort(stynumber), sectionid, basketid);
            if (checkPst == null || checkPst.size() == 0) {
                Set set = new HashSet();
                businessService = new BusinessService(daoFactory);
                PSTList = new ArrayList<ProgramSubjectTagging>();
                programSubjectTagging = new ProgramSubjectTagging();
                programSubjectTaggingId = new ProgramSubjectTaggingId();
                String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
                programSubjectTagging.setCustomauditflag("Y");
                programSubjectTagging.setId(programSubjectTaggingId);
                if (batchdetail.length > 0) { // 0-Componentid 3-fstid 9-ttreferenceid 19-mergewithfstid
                    for (int i = 0; i < batchdetail.length; i++) {
                        componentid = batchdetail[i].split("~@~")[0];
                        programSubjectDetail = new ProgramSubjectDetail();
                        programSubjectDetailId = new ProgramSubjectDetailId();
                        programSubjectDetailId.setInstituteid(instituteid);
                        programSubjectDetailId.setProgramsubjectid(programsubjectid);
                        programSubjectDetailId.setRegistrationid(registrationid);
                        programSubjectDetailId.setSubjectcomponentid(componentid);
                        programSubjectDetail.setId(programSubjectDetailId);
                        programSubjectDetail.setLtppassingmarks(BigDecimal.ZERO);
                        programSubjectDetail.setTotalccpmarks(BigDecimal.ZERO);
                        programSubjectDetail.setDeactive("N");
                        set.add(programSubjectDetail);
                    }
                }
                programSubjectTagging.setProgramsubjectdetails(set);
                PSTList.add(programSubjectTagging);
                businessService.insertInIdGenerationControl(PSTList);
                businessService.closeSession();

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
                studentSubjectChoiceMaster.setDeactive("N");
                studentSubjectChoiceMaster.setChoice(new Byte("1"));
                studentSubjectChoiceMaster.setAuditsubject("Y");
                recordsToInsOrUpdChoiceMaster.add(studentSubjectChoiceMaster);
            }
            for (int i = 0; i < batchdetail.length; i++) {
                componentid = batchdetail[i].split("~@~")[0];
                String selectedfstid = batchdetail[i].split("~@~")[1];
                String ttrefernceid = batchdetail[i].split("~@~")[2];
                String selectedmergedwithfstid = batchdetail[i].split("~@~")[3];

                List checkFstIdExistOrNot = daoFactory.getFacultySubjectTaggingDAO().checkFstIdExistOrNotForAudit(instituteid, registrationid, subjectid, acadyear, programid, styNo, sectionid, stytypeid, componentid, basketid);
                if (checkFstIdExistOrNot == null || checkFstIdExistOrNot.isEmpty()) {
                    businessService = new BusinessService(daoFactory);
                    FSTList = new ArrayList<FacultySubjectTagging>();
                    fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                    facultysubjecttagging = new FacultySubjectTagging();
                    facultysubjecttaggingId = new FacultySubjectTaggingId();
                    facultysubjecttaggingId.setFstid(fstid);
                    facultysubjecttaggingId.setInstituteid(instituteid);
                    facultysubjecttagging.setId(facultysubjecttaggingId);
                    facultysubjecttagging.setAcademicyear(acadyear);
                    facultysubjecttagging.setSubjectcomponentid(componentid);
                    facultysubjecttagging.setBasketid(basketid);
                    facultysubjecttagging.setProgramid(programid);
                    facultysubjecttagging.setRegistrationid(registrationid);
                    facultysubjecttagging.setSectionid(sectionid);
                    facultysubjecttagging.setStynumber(styNo);
                    facultysubjecttagging.setStytypeid(stytypeid);
                    facultysubjecttagging.setSubjectid(subjectid);
                    facultysubjecttagging.setTtreferenceid(ttrefernceid);
                    facultysubjecttagging.setSubsectionid(subsectionid);
                    if (selectedmergedwithfstid.equals("null")) {
                        facultysubjecttagging.setMergewithfstid(selectedfstid);
                    } else {
                        facultysubjecttagging.setMergewithfstid(selectedmergedwithfstid);
                    }
                    FSTList.add(facultysubjecttagging);
                    businessService.insertInIdGenerationControl(FSTList);
                    businessService.closeSession();

                }
                fstid = selectedfstid;
                List<FacultyStudentTagging> fstList = daoFactory.getFacultyStudentTaggingDAO().getFSTID(instituteid, "('" + fstid + "')", studentid);
                if (fstList != null && fstList.size() == 0) {
                    businessService = new BusinessService(daoFactory);
                    recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
                    facultyStudentTagging = new FacultyStudentTagging();
                    FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                    id.setInstituteid(instituteid);
                    String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                    id.setStudentfstid(StudentFstID);
                    id.setStudentid(studentid);
                    facultyStudentTagging.setId(id);
                    facultyStudentTagging.setFstid(fstid);//selected fstid on lov
                    facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                    facultyStudentTagging.setEntrydate(new Date());
                    facultyStudentTagging.setAuditsubject("Y");
                    facultyStudentTagging.setDeactive("N");
                    facultyStudentTagging.setSubjectid(subjectid);
                    facultyStudentTagging.setRegistrationid(registrationid);
                    facultyStudentTagging.setRegconfirmationdate(regConfirmationDate);
                    recordsToInsertFST.add(facultyStudentTagging);
                    businessService.insertInIdGenerationControl(recordsToInsertFST);
                    businessService.closeSession();
                }
                //-------------------StudentSubjectChoiceDetail --------------------------------
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
                prFacultyStudentTagging.setAuditsubject("Y");
                prFacultyStudentTagging.setRegistrationid(registrationid);
                prFacultyStudentTagging.setDeactive("N");
                recordsToInsOrUpdPRFST.add(prFacultyStudentTagging);
            }
            err = new ArrayList<String>();
            err.add(daoFactory.getFacultyStudentTaggingDAO().insertFacultyStudentTagging3(recordsToInsOrUpdPRFST, registrationid, instituteid, studentid, recordsToInsOrUpdChoiceMaster, recordsToInsOrUpdChoicedetail));

        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
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
        try {
            regConfirmationDate = df.parse(request.getParameter("regConfirm_date"));
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
            String[] fstids = request.getParameter("fstid").toString().split("~@~");
            String Memberid = jirpsession.getJsessionInfo().getMemberid();
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
                studentMaster = (StudentMaster) daoFactory.getStudentMasterDAO().findByPrimaryKey(studentid);
                studentMaster.setStynumber(studentRegistration.getStynumber());
                daoFactory.getStudentMasterDAO().update(studentMaster);
                for (int i = 0; i < fstids.length; i++) {
                    String[] fstidltp = fstids[i].toString().split(",");
                    for (int j = 0; j < fstidltp.length; j++) {
                        String studentsftid = daoFactory.getFacultyStudentTaggingDAO().getStudentFstId(instituteid, studentid, fstidltp[j].toString());
                        id = new FacultyStudentTaggingId(instituteid, studentsftid, studentid);
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
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
//            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
//            String regid = request.getParameter("registrationid");
            String client_id = jirpsession.getJsessionInfo().getSelectedclientid();
            String instituteid = request.getParameter("studentinstituteid");
            String regid = request.getParameter("studentregistrationid");
            String studentid = request.getParameter("studentid");
            String acadyear = request.getParameter("acadyear");
            String subsectionid = request.getParameter("subsectionid");
            String forModule = "ADDDROP";//request.getParameter("forModule");
            String stynumber = request.getParameter("stynumber");
            String bc = request.getParameter("branchcode");
            String[] bcode = bc.split("/");
            String branchcode = bcode[1];
            String exportto = request.getParameter("export_To");
            String styno = request.getParameter("stynumber");
            String programid = request.getParameter("programid");
            String branchid = request.getParameter("branchid");
            response.reset();
            response.setContentType("application/pdf");
            if (client_id.equals("JAYPEE") || client_id.equals("JIIT")) {
                response.setHeader("Content-Disposition", "attachment;filename=SubjectRegistrationJIIT.pdf");
                getIncompleteInformationJIIT(response.getOutputStream(), request.getRealPath(""), instituteid, studentid, styno, programid, branchid, acadyear, subsectionid, regid, forModule, branchcode, stynumber);
            } else {
                response.setHeader("Content-Disposition", "attachment;filename=SubjectRegistration.pdf");
                getIncompleteInformation(response.getOutputStream(), request.getRealPath(""), instituteid, studentid, styno, programid, branchid, acadyear, subsectionid, regid, forModule, branchcode, stynumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIncompleteInformationJIIT(ServletOutputStream outputStream, String path, String instituteid, String studentid, String styno, String programid, String branchid, String acadyear, String subsectionid, String regid, String forModule, String branchcode, String stynumber) {
        int a = 1;
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 22, 22);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);

            HashSet<String> subjectcomponent = new HashSet<String>();
            document.open();
            PdfPTable masterTable;
            PdfPCell cell;

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            Font font1 = new Font(bf, 9, Font.BOLD);
            Font headfont = new Font(bf, 9, Font.NORMAL);
            Font font2 = new Font(bf, 11, Font.NORMAL | Font.UNDERLINE);
            Font font22 = new Font(bf, 20, Font.UNDERLINE);
            Font font3 = new Font(bf, 15, Font.NORMAL);
            Font font23 = new Font(bf, 15, Font.NORMAL | Font.UNDERLINE);
            Font font4 = new Font(bf, 11, Font.NORMAL);
            Font font5 = new Font(bf, 11, Font.ITALIC);
            Font font11 = new Font(bf, 9, Font.NORMAL);
            StudentPhoto studentphoto = null;
            String photo = "";
            byte[] bytes = null;
            Image img = null;
            List stuphotosignature = (List) daoFactory.getStudentPhotoDAO().getStudentPhotoForRegSlip(studentid);
            if (stuphotosignature != null && stuphotosignature.size() > 0) {
                Blob b = (Blob) ((StudentPhoto) stuphotosignature.get(0)).getPhoto();
                if (b != null) {
                    bytes = b.getBytes(1, (int) b.length());
                    img = Image.getInstance(bytes);
                }

            }

            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            List<Object[]> personalAcademicinfo = daoFactory.getSct_UserHierarchyDAO().studentPersonalAcademicInfo(instituteid, studentid, regid, programid, branchid, acadyear, subsectionid);
            List<Object[]> regcaption = (List) daoFactory.getRegistrationMasterDAO().getregcaption(instituteid, regid);
            List progduration = (List) daoFactory.getRegistrationMasterDAO().getprogduration(instituteid, acadyear, programid, branchid);
            ArrayList<Object[]> subjectInfo = new ArrayList<Object[]>();

            if (forModule.equals("ADDDROP")) {
                subjectInfo = (ArrayList<Object[]>) daoFactory.getSct_UserHierarchyDAO().studentSubjectInfo(instituteid, studentid, regid);
            } else if (forModule.equals("SSCP")) {
                subjectInfo = (ArrayList<Object[]>) daoFactory.getSct_UserHierarchyDAO().studentSubjectInfoChoice(instituteid, studentid, regid);

            }
            masterTable = new PdfPTable(31);
            cell = new PdfPCell(new Phrase(" "));
            cell.setColspan(31);
            cell.setRowspan(1);
            cell.setBorder(0);
            masterTable.addCell(cell);
            String addr1 = "";
            String addr2 = "";
            String addr3 = "";
            String ins_name = "";
            String state = "";
            String pin = "";
            String city = "";

            if (personalAcademicinfo != null && !personalAcademicinfo.isEmpty()) {

                if (ims.getAddress1() != null) {
                    addr1 = ims.getAddress1().toString();
                } else {
                    addr1 = " ";
                }
                if (ims.getAddress2() != null) {
                    addr2 = ims.getAddress2().toString();
                } else {
                    addr2 = " ";
                }
                if (ims.getAddress3() != null) {
                    addr3 = ims.getAddress3().toString();
                } else {
                    addr3 = " ";
                }
                if (ims.getInstitutename() != null) {
                    ins_name = ims.getInstitutename().toString();
                } else {
                    ins_name = " ";
                }
                if (ims.getState() != null) {
                    state = ims.getState().toString();
                } else {
                    state = " ";
                }
                if (ims.getPin() != null) {
                    pin = ims.getCity().toString();
                } else {
                    pin = " ";
                }
                if (ims.getCity() != null) {
                    city = ims.getCity().toString();
                } else {
                    city = " ";
                }

                cell = new PdfPCell(new Phrase(" " + ins_name, font23));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                if (ims.getHeading1() != null) {
                    cell = new PdfPCell(new Phrase("(" + ims.getHeading1() + ")", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthBottom(2);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase(" " + addr1 + " " + addr2 + " " + addr3, font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderWidthBottom(2);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" " + state + " " + city + " " + pin, font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderWidthBottom(2);
                cell.setBorder(0);
                masterTable.addCell(cell);

                String Academicyear = acadyear;
                String str1 = Academicyear.substring(0, 2);
                String str2 = "20" + str1;

                cell = new PdfPCell(new Phrase("  "));
                cell.setColspan(2);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Student Personal Identification / Registration Details ", font23));
                cell.setColspan(27);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                if (img != null) {
                    img.scaleAbsoluteHeight(50);
                    img.scaleAbsoluteWidth(50);
                    cell.setColspan(2);
                    cell = new PdfPCell(img, false);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setRowspan(1);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("  "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Note for Students:", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("1.Retain this form with you till finalisation of results", font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("2.Changes in part-II, must be resolved before leaving registration hall", font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                LocalDate currentdate = LocalDate.now();
                System.out.println("Current date: " + currentdate);
                int currentDay = currentdate.plusDays(15).getDayOfMonth();
                Month currentMonth = currentdate.plusDays(15).getMonth();
                int currentYear = currentdate.plusDays(15).getYear();

                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String stringDate = sdf.format(now);

                cell = new PdfPCell(new Phrase("3.Changes in part-I, must be sent to registrar by " + currentMonth.toString().toLowerCase() + " " + currentDay + ", " + currentYear + "", font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("  "));
                cell.setColspan(6);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Report as on: " + stringDate, headfont));
                cell.setColspan(25);
                cell.setRowspan(1);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Part-I", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell.setBorder(0);
                masterTable.addCell(cell);
                String str = personalAcademicinfo.get(0)[2] == null ? "" : personalAcademicinfo.get(0)[2].toString();

                cell = new PdfPCell(new Phrase("Enrollment No : " + new Phrase(str).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                //  cell.setBorder(Element.RECTANGLE);
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                //   String regcode = personalAcademicinfo.get(0)[19].toString();
                String regcode = personalAcademicinfo.get(0)[19] == null ? "" : personalAcademicinfo.get(0)[19].toString();
                cell = new PdfPCell(new Phrase("Registration Code : " + new Phrase(regcode).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                // String stname = personalAcademicinfo.get(0)[0].toString();
                String stname = personalAcademicinfo.get(0)[0] == null ? "" : personalAcademicinfo.get(0)[0].toString();
                cell = new PdfPCell(new Phrase("Student Name : " + new Phrase(stname).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String regdate = personalAcademicinfo.get(0)[10] == null ? "" : personalAcademicinfo.get(0)[10].toString();
                SimpleDateFormat oldf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                SimpleDateFormat newf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date d1 = oldf1.parse(regdate);
                String nregdate = newf1.format(d1);
                cell = new PdfPCell(new Phrase("Registration Date : " + new Phrase(nregdate).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String acad = personalAcademicinfo.get(0)[3] == null ? "" : personalAcademicinfo.get(0)[3].toString();
                cell = new PdfPCell(new Phrase("Academic Year  : " + new Phrase(acad).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String dob = personalAcademicinfo.get(0)[11] == null ? "" : personalAcademicinfo.get(0)[11].toString();
                SimpleDateFormat oldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                SimpleDateFormat newf = new SimpleDateFormat("dd/MM/yyyy");
                Date d = oldf.parse(dob);
                String ndob = newf.format(d);
                cell = new PdfPCell(new Phrase("Date of Birth : " + new Phrase(ndob).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String prg = personalAcademicinfo.get(0)[8] == null ? "" : personalAcademicinfo.get(0)[8].toString();
                cell = new PdfPCell(new Phrase("Program : " + new Phrase(prg).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String mob = personalAcademicinfo.get(0)[23] == null ? "" : personalAcademicinfo.get(0)[23].toString();
                cell = new PdfPCell(new Phrase("Mobile No : " + new Phrase(mob).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String branch = personalAcademicinfo.get(0)[20] == null ? "" : personalAcademicinfo.get(0)[20].toString();
                cell = new PdfPCell(new Phrase("Branch : " + new Phrase(branch).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                //   String branch = personalAcademicinfo.get(0)[20] == null ? "" : personalAcademicinfo.get(0)[20].toString();
                String sem = progduration.get(0) == null ? "" : progduration.get(0).toString();
                cell = new PdfPCell(new Phrase("STY Number : " + new Phrase(sem).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String batch = personalAcademicinfo.get(0)[7] == null ? "" : personalAcademicinfo.get(0)[7].toString();
                cell = new PdfPCell(new Phrase("Batch Code  : " + new Phrase(batch).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String emailid = personalAcademicinfo.get(0)[21] == null ? "" : personalAcademicinfo.get(0)[21].toString();
                cell = new PdfPCell(new Phrase("Email ID : " + new Phrase(emailid).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String fathername = personalAcademicinfo.get(0)[1] == null ? "" : personalAcademicinfo.get(0)[1].toString();
                cell = new PdfPCell(new Phrase("Father Name  : " + new Phrase(fathername).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String parincome = personalAcademicinfo.get(0)[14] == null ? "" : personalAcademicinfo.get(0)[14].toString();
                cell = new PdfPCell(new Phrase("Parent Annual Income : " + new Phrase(parincome).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String mothername = personalAcademicinfo.get(0)[12] == null ? "" : personalAcademicinfo.get(0)[12].toString();
                cell = new PdfPCell(new Phrase("Mother Name  : " + new Phrase(mothername).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String parmob = personalAcademicinfo.get(0)[24] == null ? "" : personalAcademicinfo.get(0)[24].toString();
                cell = new PdfPCell(new Phrase("Parent Mobile : " + new Phrase(parmob).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String occupation = personalAcademicinfo.get(0)[13] == null ? "" : personalAcademicinfo.get(0)[13].toString();
                cell = new PdfPCell(new Phrase("Parent Occupation  : " + new Phrase(occupation).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String paremailid = personalAcademicinfo.get(0)[22] == null ? "" : personalAcademicinfo.get(0)[22].toString();
                cell = new PdfPCell(new Phrase("Parent Email ID : " + new Phrase(paremailid).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String parquali = personalAcademicinfo.get(0)[15] == null ? "" : personalAcademicinfo.get(0)[15].toString();
                cell = new PdfPCell(new Phrase("Parent Qualification  : " + new Phrase(parquali).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                masterTable.addCell(cell);

                String localadd = personalAcademicinfo.get(0)[17] == null ? "" : personalAcademicinfo.get(0)[17].toString();
                cell = new PdfPCell(new Phrase("Local Address : " + new Phrase(localadd).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(15);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthLeft(1);
                cell.setBorderWidthRight(1);
                cell.setBorderWidthBottom(1);
                masterTable.addCell(cell);

                String peradd = personalAcademicinfo.get(0)[18] == null ? "" : personalAcademicinfo.get(0)[18].toString();
                cell = new PdfPCell(new Phrase("Permanent Address : " + new Phrase(peradd).toString().replace("[", "").replace("]", ""), font11));
                cell.setColspan(16);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                cell.setBorderWidthRight(1);
                cell.setBorderWidthBottom(1);
                masterTable.addCell(cell);

//This belock use for get SubjectComponent
                if (subjectInfo != null && !subjectInfo.isEmpty()) {

//------------------------------------------------------------------------------------------------
                    cell = new PdfPCell(new Phrase("Student Qualification Details", font1));
                    cell.setColspan(8);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" ", font1));
                    cell.setColspan(23);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Name of Board", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Exam Passed", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Year of Passing", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Division", headfont));
                    cell.setColspan(3);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Max Marks", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Marks Obtained", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Percentage of Marks", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Grade", headfont));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    List<Object[]> studentquafication = (List<Object[]>) daoFactory.getStudentQualificationDAO().getStudentQualification(studentid);
                    if (studentquafication != null && !studentquafication.isEmpty()) {
                        for (int i = 0; i < studentquafication.size(); i++) {

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[7] == null ? "" : studentquafication.get(i)[7].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[0] == null ? "" : studentquafication.get(i)[0].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[1] == null ? "" : studentquafication.get(i)[1].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[2] == null ? "" : studentquafication.get(i)[2].toString(), font11));
                            cell.setColspan(3);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[3] == null ? "" : studentquafication.get(i)[3].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[4] == null ? "" : studentquafication.get(i)[4].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[5] == null ? "" : studentquafication.get(i)[5].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(studentquafication.get(i)[6] == null ? "" : studentquafication.get(i)[6].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                        }

                    }

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Subject Registration Details", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Part-II", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Regular Subject", font1));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" ", font1));
                    cell.setColspan(26);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Slno.", headfont));
                    cell.setColspan(2);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    cell.setBorderWidthLeft(1);
                    cell.setBorderWidthRight(1);
                    cell.setBorderWidthTop(1);
                    cell.setBorderWidthBottom(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Subject Code", headfont));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    cell.setBorderWidthRight(1);
                    cell.setBorderWidthBottom(1);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Subject", headfont));
                    cell.setColspan(15);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    cell.setBorderWidthRight(1);
                    cell.setBorderWidthBottom(1);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Program Credit Point", headfont));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    cell.setBorderWidthRight(1);
                    cell.setBorderWidthTop(1);
                    cell.setBorderWidthBottom(1);
                    masterTable.addCell(cell);
                    Double totalcredit = 0.0;
                    for (int i = 0; i < subjectInfo.size(); i++) {
                        if (subjectInfo.get(i)[5].toString().equals("N")) {
                            int j = i + 1;
                            String var = Integer.toString(j);
                            cell = new PdfPCell(new Phrase(var, font11));
                            cell.setColspan(2);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(0);
                            cell.setBorderWidthLeft(1);
                            cell.setBorderWidthRight(1);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[1] == null ? "" : subjectInfo.get(i)[1].toString(), font11));
                            cell.setColspan(5);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(0);
                            cell.setBorderWidthRight(1);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[0] == null ? "" : subjectInfo.get(i)[0].toString(), font11));
                            cell.setColspan(15);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(0);
                            cell.setBorderWidthRight(1);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[2] == null ? "" : subjectInfo.get(i)[2].toString(), font11));
                            cell.setColspan(9);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(0);
                            cell.setBorderWidthRight(1);
                            masterTable.addCell(cell);
                            totalcredit += subjectInfo.get(i)[2] == null ? 0 : new Double(subjectInfo.get(i)[2].toString());
                        }

                    }

                    cell = new PdfPCell(new Phrase(" ", font1));
                    cell.setColspan(13);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Total Course Credit Points", headfont));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(totalcredit + "", headfont));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Audit Subject", font1));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" ", font1));
                    cell.setColspan(26);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Slno.", headfont));
                    cell.setColspan(2);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Subject Code", headfont));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Subject", headfont));
                    cell.setColspan(15);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Program Credit Point", headfont));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    for (int i = 0; i < subjectInfo.size(); i++) {
                        if (subjectInfo.get(i)[5].toString().equals("Y")) {
                            int j = i + 1;
                            String var = Integer.toString(j);
                            cell = new PdfPCell(new Phrase(var, font11));
                            cell.setColspan(2);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[1] == null ? "" : subjectInfo.get(i)[1].toString(), font11));
                            cell.setColspan(5);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[0] == null ? "" : subjectInfo.get(i)[0].toString(), font11));
                            cell.setColspan(15);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(i)[2] == null ? "" : subjectInfo.get(i)[2].toString(), font11));
                            cell.setColspan(9);
                            cell.setRowspan(1);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);
                        }

                    }

                    //  This belock use for only Workshop Coures
//-----------------------------------------------------------------------------------------------------------
                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Signature of Registration Superintendent", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                    cell = new PdfPCell(new Phrase("Verification of Core Details by Student ( Write 'YES' or Enter the Correct Details' )", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                    cell = new PdfPCell(new Phrase("(i) Telephone Number. :", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                    cell = new PdfPCell(new Phrase("(ii) email id. :", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("(iii) Correctness of Spelling of Name as per Xth Certificate. :", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" ", font11));
                    cell.setColspan(25);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Signature of Student :", font1));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    document.add(masterTable);
                    document.newPage();
                } else {
                    for (int o = 0; o < 3; o++) {
                        cell = new PdfPCell(new Phrase(" "));
                        cell.setColspan(31);
                        cell.setRowspan(1);
                        cell.setBorder(0);
                        masterTable.addCell(cell);
                    }

                    masterTable = new PdfPTable(31);
                    cell = new PdfPCell(new Phrase("No data  found"));
                    cell.setColspan(31);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(1);
                    cell.setBorder(Rectangle.BOX);
                    masterTable.addCell(cell);
                    document.add(masterTable);
                }
            } else {
                for (int o = 0; o < 3; o++) {
                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                }

                masterTable = new PdfPTable(31);
                cell = new PdfPCell(new Phrase("No data  found"));
                cell.setColspan(31);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(1);
                cell.setBorder(Rectangle.BOX);
                masterTable.addCell(cell);
                document.add(masterTable);

            }

            document.close();
            pdfWriter.close();
            bstream.writeTo(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream = null;
        }
    }

    public void getIncompleteInformation(ServletOutputStream outputStream, String path, String instituteid, String studentid, String styno, String programid, String branchid, String acadyear, String subsectionid, String regid, String forModule, String branchcode, String stynumber) {
        int a = 1;
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 0, 0, 20, 20);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);

            HashSet<String> subjectcomponent = new HashSet<String>();
            document.open();
            PdfPTable masterTable;
            PdfPCell cell;

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            Font font1 = new Font(bf, 9, Font.BOLD);
            Font font2 = new Font(bf, 11, Font.NORMAL | Font.UNDERLINE);
            Font font22 = new Font(bf, 20, Font.UNDERLINE);
            Font font23 = new Font(bf, 15, Font.BOLD | Font.UNDERLINE);
            Font font3 = new Font(bf, 15, Font.BOLD);
            Font font4 = new Font(bf, 11, Font.NORMAL);
            Font font5 = new Font(bf, 11, Font.ITALIC);
            Font font11 = new Font(bf, 9, Font.NORMAL);

            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            List<String[]> studentphonelist = daoFactory.getSct_UserHierarchyDAO().getStudentPhoneInfo(studentid);
            List<Object[]> PAInfo = daoFactory.getSct_UserHierarchyDAO().studentPersonalAcademicInfo(instituteid, studentid, regid, programid, branchid, acadyear, subsectionid);
            List indexlist = new ArrayList();
            indexlist.add(10);
            List<Object[]> personalAcademicinfo = daoFactory.getUtilDAO().dateConverterList(PAInfo, indexlist, "dd/MM/yyyy");
            List<Object[]> regcaption = (List) daoFactory.getRegistrationMasterDAO().getregcaption(instituteid, regid);
            List progduration = (List) daoFactory.getRegistrationMasterDAO().getprogduration(instituteid, acadyear, programid, branchid);
            ArrayList<Object[]> subjectInfo = new ArrayList<Object[]>();

            if (forModule.equals("ADDDROP")) {
                subjectInfo = (ArrayList<Object[]>) daoFactory.getSct_UserHierarchyDAO().studentSubjectInfo(instituteid, studentid, regid);
            } else if (forModule.equals("SSCP")) {
                subjectInfo = (ArrayList<Object[]>) daoFactory.getSct_UserHierarchyDAO().studentSubjectInfoChoice(instituteid, studentid, regid);

            }
            masterTable = new PdfPTable(31);
            cell = new PdfPCell(new Phrase(" "));
            cell.setColspan(31);
            cell.setRowspan(1);
            cell.setBorder(0);
            masterTable.addCell(cell);
            String addr1 = "";
            String addr2 = "";
            String addr3 = "";
            String ins_name = "";
            String state = "";
            String pin = "";
            String city = "";
            Image img = null;

            if (personalAcademicinfo != null && !personalAcademicinfo.isEmpty()) {

                if (ims.getAddress1() != null) {
                    addr1 = ims.getAddress1().toString();
                } else {
                    addr1 = " ";
                }
                if (ims.getAddress2() != null) {
                    addr2 = ims.getAddress2().toString();
                } else {
                    addr2 = " ";
                }
                if (ims.getAddress3() != null) {
                    addr3 = ims.getAddress3().toString();
                } else {
                    addr3 = " ";
                }
                if (ims.getInstitutename() != null) {
                    ins_name = ims.getInstitutename().toString();
                } else {
                    ins_name = " ";
                }
                if (ims.getState() != null) {
                    state = ims.getState().toString();
                } else {
                    state = " ";
                }
                if (ims.getPin() != null) {
                    pin = ims.getCity().toString();
                } else {
                    pin = " ";
                }
                if (ims.getCity() != null) {
                    city = ims.getCity().toString();
                } else {
                    city = " ";
                }
                cell = new PdfPCell(new Phrase(" " + ins_name, font23));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" " + addr1 + " " + addr2 + " " + addr3, font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderWidthBottom(2);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" " + state + " " + city + " " + pin, font11));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderWidthBottom(2);
                cell.setBorder(0);
                masterTable.addCell(cell);
                String Academicyear = acadyear;
                String str1 = Academicyear.substring(0, 2);
                String str2 = "20" + str1;
                int yr = Integer.parseInt(str1) + Integer.parseInt(progduration.get(0).toString());
                String str3 = str2 + "-" + "20" + yr;

                cell = new PdfPCell(new Phrase("SUBJECT  REGISTRATION : " + personalAcademicinfo.get(0)[8].toString() + " PROGRAM : BATCH " + str3, font2));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                String sem = (stynumber.equals("1")) ? "I" : (stynumber.equals("2")) ? "II" : (stynumber.equals("3")) ? "III" : (stynumber.equals("4")) ? "IV" : (stynumber.equals("5")) ? "V" : (stynumber.equals("6")) ? "VI" : "";
                String regitrationcaption = regcaption.get(0)[0] == null ? "" : regcaption.get(0)[0].toString();

                if ((personalAcademicinfo.get(0)[6].toString().equals("MBA"))) {
                    cell = new PdfPCell(new Phrase("TERM -" + sem + " (" + regitrationcaption + ") ", font2));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase("SEMESTER -" + sem + " (" + regitrationcaption + ") ", font2));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("  "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("Note for Students:", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("1.Retain this form till finalisation of results.", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("2.Changes in any part must be resolved before completion of registration process.", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("3.The registration for odd semester 2018,is provisional Subject to clearance of Dues,if any.", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("(a) Student maintains a minimum GPA of 2.00 on 4 point scale.", font4));
//                cell.setColspan(31);
//                cell.setRowspan(1);
//                cell.setVerticalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(0);
//                masterTable.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("(b) Student should not have more than 15 DCP till the end of Term -03.", font4));
//                cell.setColspan(31);
//                cell.setRowspan(1);
//                cell.setVerticalAlignment(Element.ALIGN_CENTER);
//                cell.setBorder(0);
//                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("  "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("(PERSONAL INFORMATION)", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("NAME OF STUDENT :", font11));
                cell.setColspan(9);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop(1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[0] == null ? "" : personalAcademicinfo.get(0)[0].toString(), font1));
                cell.setColspan(22);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop(1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("FATHER'S NAME :", font11));
                cell.setColspan(9);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[1] == null ? "" : personalAcademicinfo.get(0)[1].toString().toUpperCase(), font1));
                cell.setColspan(22);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("ENROLMENT NO. :", font11));
                cell.setColspan(9);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[2] == null ? "" : personalAcademicinfo.get(0)[2].toString(), font1));
                cell.setColspan(22);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);
                String[] studentphone = {"0", "0", "0", "0"};
                if (studentphonelist.size() > 0) {
                    Object[] objArr = studentphonelist.get(0);
                    int i = 0;
                    for (Object obj : objArr) {
                        studentphone[i++] = (String) obj;
                    }
                }
                if (studentphone != null && studentphone.length > 0) {

                    cell = new PdfPCell(new Phrase("STUDENT'S TELE NO. : ", font11));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(studentphone[0].equals("0") ? "" : studentphone[0].toString(), font11));
                    cell.setColspan(8);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("PARENT'S TELE NO. : ", font11));
                    cell.setColspan(8);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(studentphone[1].equals("0") ? "" : studentphone[1].toString(), font11));
                    cell.setColspan(6);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("STUDENT'S EMAIL ID : ", font11));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    // cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(studentphone[2].equals("0") ? "" : studentphone[2].toString(), font11));
                    cell.setColspan(22);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    // cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("PARENT'S EMAIL ID : ", font11));
                    cell.setColspan(9);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    if (studentphone.length > 3) {
                        cell = new PdfPCell(new Phrase(studentphone[3].equals("0") ? "" : studentphone[3].toString(), font11));
                        cell.setColspan(22);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        masterTable.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase(""));
                        cell.setColspan(22);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        masterTable.addCell(cell);
                    }

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                }

                cell = new PdfPCell(new Phrase("(ACADEMIC INFORMATION)", font1));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("SCHOOL : ", font11));
                cell.setColspan(8);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop(1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[5] == null ? "" : personalAcademicinfo.get(0)[5].toString().toUpperCase(), font1));
                cell.setColspan(10);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop(1);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("PROGRAM :", font11));
                cell.setColspan(5);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                cell.setBorderWidthTop(1);
                masterTable.addCell(cell);

                if ((!personalAcademicinfo.get(0)[6].toString().equals("MBA"))) {

                    String progBran = personalAcademicinfo.get(0)[6] == null ? "" : personalAcademicinfo.get(0)[6].toString() + "-" + branchcode;

                    cell = new PdfPCell(new Phrase(progBran, font1));
                    cell.setColspan(10);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[6] == null ? "" : personalAcademicinfo.get(0)[6].toString(), font1));
                    cell.setColspan(10);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("ACADEMIC YEAR :", font11));
                cell.setColspan(8);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(regcaption.get(0)[1] == null ? "" : regcaption.get(0)[1].toString(), font1));
                cell.setColspan(10);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                if ((personalAcademicinfo.get(0)[6].toString().equals("MBA"))) {
                    cell = new PdfPCell(new Phrase("TERM:", font11));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase("STY NUMBER:", font11));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);
                }

                cell = new PdfPCell(new Phrase(stynumber == null ? "" : stynumber.toString(), font1));
                cell.setColspan(10);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("REGISTRATION DATE :", font11));
                cell.setColspan(8);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[10] == null ? "" : personalAcademicinfo.get(0)[10].toString(), font1));
                cell.setColspan(10);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase("BATCH :", font11));
                cell.setColspan(5);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

                cell = new PdfPCell(new Phrase(personalAcademicinfo.get(0)[7] == null ? "" : personalAcademicinfo.get(0)[7].toString(), font1));
                cell.setColspan(10);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Element.RECTANGLE);
                masterTable.addCell(cell);

//                        3 6  10 3 3 3 3
                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(31);
                cell.setRowspan(1);
                cell.setBorder(0);
                masterTable.addCell(cell);
//This belock use for get SubjectComponent
                if (subjectInfo != null && !subjectInfo.isEmpty()) {

                    for (int j = 0; j < subjectInfo.size(); j++) {
//                            String data = "5,L-5,T-4,P-3";
                        String data1 = subjectInfo.get(j)[2] == null ? "0,L-0,T-0,P-0" : subjectInfo.get(j)[2].toString();
                        String lec1 = null;
                        String[] splitintofour1 = data1.split(",");

                        for (int i = 0; i < splitintofour1.length; i++) {
                            String[] ltp1 = splitintofour1[i].split("-");

                            if (ltp1[0].equalsIgnoreCase("L")) {
                                subjectcomponent.add(ltp1[0].toString());
                            } else if (ltp1[0].equalsIgnoreCase("T")) {
                                subjectcomponent.add(ltp1[0].toString());
                            } else if (ltp1[0].equalsIgnoreCase("P")) {
                                subjectcomponent.add(ltp1[0].toString());

                            }
                        }
                    }
//------------------------------------------------------------------------------------------------
                    cell = new PdfPCell(new Phrase("(SUBJECT INFORMATION)", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("S.NO.", font1));
                    cell.setColspan(2);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("COURSE CODE", font1));
                    cell.setColspan(5);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("COURSE TITLE", font1));
                    cell.setColspan(13);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("CATEGORY", font1));
                    cell.setColspan(4);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);
                    // this Condition use MBA (SubjectComponent = L)
                    if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {

                        cell = new PdfPCell(new Phrase("COURSE CREDITS", font1));
                        cell.setColspan(3);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        cell.setBorderWidthTop(1);
                        masterTable.addCell(cell);

                    }
                    // this Condition use B.Tech (SubjectComponent = L,T,p)

//                    if (subjectcomponent.size() > 1) {
                    cell = new PdfPCell(new Phrase("L", font1));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("T", font1));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("P", font1));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    cell.setBorderWidthTop(1);
                    masterTable.addCell(cell);
//                    }
                    if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {
                        cell = new PdfPCell(new Phrase("CONTACT HOURS", font1));
                        cell.setColspan(5);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        cell.setBorderWidthTop(1);
                        masterTable.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("COURSE CREDITS", font1));
                        cell.setColspan(4);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        cell.setBorderWidthTop(1);
                        masterTable.addCell(cell);

                    }
                    int lsum = 0, tsum = 0, psum = 0, crsum = 0, inc = 1, hsum = 0;
                    //HashMap subjectcomponent = new HashMap();

//            Data is not in db just doinf testing.....................................................
                    for (int j = 0; j < subjectInfo.size(); j++) {
//                            String data = "5,L-5,T-4,P-3";
                        String data = subjectInfo.get(j)[2] == null ? "0,L-0,T-0,P-0" : subjectInfo.get(j)[2].toString();
                        String lec = null, theu = null, prac = null, noofhours = null;
                        String[] splitintofour = data.split(",");

                        for (int i = 0; i < splitintofour.length; i++) {
                            String[] ltp = splitintofour[i].split("-");

                            if (ltp[0].equalsIgnoreCase("L")) {
                                lec = ltp[1] == null ? "0" : ltp[1].toString();
                            } else if (ltp[0].equalsIgnoreCase("T")) {
                                theu = ltp[1] == null ? "0" : ltp[1].toString();
                            } else if (ltp[0].equalsIgnoreCase("P")) {
                                prac = ltp[1] == null ? "0" : ltp[1].toString();
                            }

                        }

                        lsum = lsum + Integer.valueOf(lec == null ? "0" : lec.toString());
                        tsum = tsum + Integer.valueOf(theu == null ? "0" : theu.toString());
                        psum = psum + Integer.valueOf(prac == null ? "0" : prac.toString());
                        hsum = hsum + Integer.valueOf(splitintofour[0] == null ? "0" : splitintofour[0].toString());
                        crsum = crsum + Integer.valueOf(splitintofour[0] == null ? "0" : splitintofour[0].toString());

//                      This belock use for only Core  and Elective Coures
                        if (!subjectInfo.get(j)[3].toString().equals("W")) {

                            cell = new PdfPCell(new Phrase(" " + a++, font1));
                            cell.setColspan(2);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(j)[1] == null ? "" : subjectInfo.get(j)[1].toString(), font11));
                            cell.setColspan(5);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(j)[0] == null ? "" : subjectInfo.get(j)[0].toString(), font11));
                            cell.setColspan(13);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(j)[4] == null ? "" : subjectInfo.get(j)[4].toString(), font11));
                            cell.setColspan(4);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {

                                cell = new PdfPCell(new Phrase(splitintofour[0] == null ? "" : splitintofour[0].toString(), font11));
                                cell.setColspan(3);
                                cell.setRowspan(1);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBorder(Element.RECTANGLE);
                                masterTable.addCell(cell);

                            }
//                            if (subjectcomponent.size() > 1) {

                            cell = new PdfPCell(new Phrase(lec == null ? "-" : lec.toString(), font11));
                            cell.setColspan(1);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(theu == null ? "-" : theu.toString(), font11));
                            cell.setColspan(1);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(prac == null ? "-" : prac.toString(), font11));
                            cell.setColspan(1);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);
//                            }
                            if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {
                                cell = new PdfPCell(new Phrase(splitintofour[0] == null ? "" : splitintofour[0].toString(), font11));
                                cell.setColspan(5);
                                cell.setRowspan(1);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBorder(Element.RECTANGLE);
                                masterTable.addCell(cell);
                            } else {
                                cell = new PdfPCell(new Phrase(splitintofour[0] == null ? "" : splitintofour[0].toString(), font11));
                                cell.setColspan(4);
                                cell.setRowspan(1);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBorder(Element.RECTANGLE);
                                masterTable.addCell(cell);

                            }
                        }
                    }

//            *****************************************************************************************  int lsum = 0,tsum = 0,psum = 0,crsum = 0;
                    cell = new PdfPCell(new Phrase(" TOTAL ", font1));
                    cell.setColspan(24);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);
                    if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {
                        cell = new PdfPCell(new Phrase("" + crsum, font11));
                        cell.setColspan(3);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        masterTable.addCell(cell);
                    }
//                    if (subjectcomponent.size() > 1) {

                    cell = new PdfPCell(new Phrase("" + lsum, font11));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("" + tsum, font11));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("" + psum, font11));
                    cell.setColspan(1);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Element.RECTANGLE);
                    masterTable.addCell(cell);
//                    }
                    if (subjectcomponent.size() == 1 && subjectcomponent.contains("L")) {
                        cell = new PdfPCell(new Phrase("" + hsum, font11));
                        cell.setColspan(4);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        masterTable.addCell(cell);

                    } else {
                        cell = new PdfPCell(new Phrase("" + crsum, font11));
                        cell.setColspan(4);
                        cell.setRowspan(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Element.RECTANGLE);
                        masterTable.addCell(cell);
                    }
                    cell = new PdfPCell(new Phrase(""));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    //  This belock use for only Workshop Coures
                    int count = 1;
                    int b = 1;
                    for (int jj = 0; jj < subjectInfo.size(); jj++) {
                        if (subjectcomponent.size() > 1 && (subjectInfo.get(jj)[3].toString().equals("W"))) {

                            if (subjectInfo.get(jj)[3].toString().equals("W") && count == 1) {
                                cell = new PdfPCell(new Phrase("Non Credit Compulsory Courses and Workshops", font1));
                                cell.setColspan(31);
                                cell.setRowspan(1);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBorder(Element.RECTANGLE);
                                cell.setBorderWidthTop(1);
                                masterTable.addCell(cell);
                                count++;

                            }

                            cell = new PdfPCell(new Phrase(" " + b++, font1));
                            cell.setColspan(3);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(jj)[1] == null ? "" : subjectInfo.get(jj)[1].toString(), font11));
                            cell.setColspan(10);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(jj)[1] == null ? "" : subjectInfo.get(jj)[1].toString(), font11));
                            cell.setColspan(10);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(jj)[1] == null ? "" : subjectInfo.get(jj)[1].toString(), font11));
                            cell.setColspan(10);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);
                        }

                        if ((subjectcomponent.size() == 1 && subjectcomponent.contains("L")) && (subjectInfo.get(jj)[3].toString().equals("W"))) {

                            if (subjectInfo.get(jj)[3].toString().equals("W") && count == 1) {
                                cell = new PdfPCell(new Phrase("Non Credit Compulsory Courses and Workshops", font1));
                                cell.setColspan(31);
                                cell.setRowspan(1);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBorder(Element.RECTANGLE);
                                cell.setBorderWidthTop(1);
                                masterTable.addCell(cell);
                                count++;

                            }

                            cell = new PdfPCell(new Phrase(" " + b++, font1));
                            cell.setColspan(3);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase(subjectInfo.get(jj)[0] == null ? "" : subjectInfo.get(jj)[0].toString(), font11));
                            cell.setColspan(12);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase("Full Day Workshop", font11));
                            cell.setColspan(6);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                            cell = new PdfPCell(new Phrase("Compulsory for All Students", font11));
                            cell.setColspan(10);
                            cell.setRowspan(1);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setBorder(Element.RECTANGLE);
                            masterTable.addCell(cell);

                        }
                    }
//-----------------------------------------------------------------------------------------------------------
                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("C - Core Course ", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("E - Elective Course ", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Declaration by students : ", font1));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" I declare that all the information given under 'personal information' is accurate (please fill where blank)"
                            + " and complete, and I intend to register for the courses as listed in the table above. ", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

//                    cell = new PdfPCell(new Phrase(" "));
//                    cell.setColspan(31);
//                    cell.setRowspan(1);
//                    cell.setBorder(0);
//                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    masterTable.addCell(cell);
                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Signature of Student:", font11));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setVerticalAlignment(Element.ALIGN_LEFT);
                    cell.setBorder(0);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("    ", font4));
                    cell.setColspan(20);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Approved By :", font11));
                    cell.setColspan(11);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("    ", font4));
                    cell.setColspan(20);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    masterTable.addCell(cell);

                    cell = new PdfPCell(new Phrase("Signature of Registrar :", font11));
                    cell.setColspan(11);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    masterTable.addCell(cell);

//                    cell = new PdfPCell(new Phrase(" "));
//                    cell.setColspan(25);
//                    cell.setRowspan(1);
//                    cell.setBorder(0);
//                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    masterTable.addCell(cell);
//
//                    cell = new PdfPCell(new Phrase("Signature of Registrar :", font4));
//                    cell.setColspan(6);
//                    cell.setRowspan(1);
//                    cell.setBorder(0);
//                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    masterTable.addCell(cell);
                    document.add(masterTable);
                    document.newPage();
                } else {
                    for (int o = 0; o < 3; o++) {
                        cell = new PdfPCell(new Phrase(" "));
                        cell.setColspan(31);
                        cell.setRowspan(1);
                        cell.setBorder(0);
                        masterTable.addCell(cell);
                    }

                    masterTable = new PdfPTable(31);
                    cell = new PdfPCell(new Phrase("No data  found"));
                    cell.setColspan(31);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(1);
                    cell.setBorder(Rectangle.BOX);
                    masterTable.addCell(cell);
                    document.add(masterTable);
                }
            } else {
                for (int o = 0; o < 3; o++) {
                    cell = new PdfPCell(new Phrase(" "));
                    cell.setColspan(31);
                    cell.setRowspan(1);
                    cell.setBorder(0);
                    masterTable.addCell(cell);
                }

                masterTable = new PdfPTable(31);
                cell = new PdfPCell(new Phrase("No data  found"));
                cell.setColspan(31);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(1);
                cell.setBorder(Rectangle.BOX);
                masterTable.addCell(cell);
                document.add(masterTable);

            }

            document.close();
            pdfWriter.close();
            bstream.writeTo(outputStream);
            byte[] b = bstream.toByteArray();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream = null;
        }
    }

    @Override
    public List getParameterForAuditSubjectCredit(HttpServletRequest req) {
        List finallist = new ArrayList();
        String parametervalue = "";
        try {
            String instituteid = req.getParameter("instituteid");
            String registrationid = req.getParameter("registrationid");
            String subjectid = req.getParameter("subjectid");

            String moduleid = "MOD08000002";
            String parameterid = "A1.2";
            List parameterval = (List) daoFactory.getProgramSubjectDetailDAO().getParameterForAuditSubjectCredit(instituteid, moduleid, parameterid);
            List<Object[]> subjectList = (List<Object[]>) daoFactory.getFacultySubjectTaggingDAO().getSubjectDetail(instituteid, registrationid, subjectid, " ", " ");
            finallist.add(parameterval);
            finallist.add(subjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finallist;
    }

    /**
     * Description: This method is used to Change Elective Subject In Case Of
     * Backlogs
     *
     * @param request
     * @return
     */
    public List changeElectiveSubject(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        FacultyStudentTagging facultyStudentTagging = null;
        FacultySubjectTagging facultysubjecttagging = null;
        FacultySubjectTaggingId facultysubjecttaggingId = null;
        PRFacultyStudentTagging prFacultyStudentTagging = null;
        StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = null;
        StudentSubjectChoiceMaster studentSubjectChoiceMaster = null;
        StudentSubjectChoiceDetail studentSubjectChoiceDetail = null;
        StudentSubjectChoiceDetailId studentSubjectChoiceDetailId = null;
        ProgramSubjectTagging programSubjectTagging = null;
        ProgramSubjectTaggingId programSubjectTaggingId = null;
        ProgramSubjectDetail programSubjectDetail = null;
        ProgramSubjectDetailId programSubjectDetailId = null;
        List err = null;
        String Memberid = jirpsession.getJsessionInfo().getMemberid();
        List<FacultyStudentTagging> recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
        List<PRFacultyStudentTagging> recordsToInsOrUpdPRFST = new ArrayList<PRFacultyStudentTagging>();
        List<ProgramSubjectTagging> PSTList = new ArrayList<ProgramSubjectTagging>();
        List<FacultySubjectTagging> FSTList = new ArrayList<FacultySubjectTagging>();
        List<StudentSubjectChoiceDetail> recordsToInsOrUpdChoicedetail = new ArrayList<StudentSubjectChoiceDetail>();
        List<StudentSubjectChoiceMaster> recordsToInsOrUpdChoiceMaster = new ArrayList<StudentSubjectChoiceMaster>();
        BusinessService businessService = new BusinessService(daoFactory);
        try {
            String[] selectedBaskete = request.getParameter("selectedBaskete").toString().split("#~#");
            String registrationid = "";
            String studentid = "";
            String instituteid = "";
            String fstid = "";
            String componentid = "";
            String mode = request.getParameter("electiveSubjectMode").toString();
            instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            registrationid = request.getParameter("semCode");
            studentid = request.getParameter("studentid");
            String acadyear = request.getParameter("academic");
            String programid = request.getParameter("programid");
            String sectionid = request.getParameter("sectionid");
            String[] subjectid_departmentid = request.getParameter("subjectid").split("~@~");
            String subjectid = subjectid_departmentid[0].toString();
            String departmentid = subjectid_departmentid[1].toString();
            String credit = request.getParameter("credit");
            String stynumber = request.getParameter("styNumber");
            String basketid = request.getParameter("basketid");
            String oldelectivesubjectid = request.getParameter("oldelectivesubject");
            String regDate = request.getParameter("classstartdate");
            Date regConfirmationDate = null;
            if (!regDate.equals("")) {
                regConfirmationDate = df.parse(regDate);
            }
            String subsectionid = request.getParameter("subsectionid");
            byte styNo = Byte.decode(stynumber);
            List<StyType> list = daoFactory.getStyTypeDAO().getStyType(instituteid, "REG");
            String stytypeid = ((StyType) list.get(0)).getId().getStytypeid();

            //-------------------ProgramSubjectTagging and  ProgramSubjectDetail--------------------------------    
            List checkPst = (List) daoFactory.getFacultySubjectTaggingDAO().checkInPST(instituteid, registrationid, subjectid, acadyear, programid, Short.parseShort(stynumber), sectionid, basketid);

            if (checkPst == null || checkPst.size() == 0) {
                Set set = new HashSet();
                //                businessService = new BusinessService(daoFactory);
                //                PSTList = new ArrayList<ProgramSubjectTagging>();
                programSubjectTagging = new ProgramSubjectTagging();
                programSubjectTaggingId = new ProgramSubjectTaggingId();
                String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
                programSubjectTagging.setSubjectrunning("N");
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
//                businessService.insertInIdGenerationControl(PSTList);
//                businessService.closeSession();

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
                studentSubjectChoiceMaster.setAuditsubject("N");
                studentSubjectChoiceMaster.setEquivalentsubjectid(oldelectivesubjectid);
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
//                        businessService = new BusinessService(daoFactory);
//                        FSTList = new ArrayList<FacultySubjectTagging>();
                        fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
//                        businessService.insertInIdGenerationControl(FSTList);
//                        businessService.closeSession();

                    }
                    List<FacultyStudentTagging> fstList = daoFactory.getFacultyStudentTaggingDAO().getFSTID(instituteid, "('" + fstid + "')", studentid);
                    if (fstList != null && fstList.size() == 0) {
//                        businessService = new BusinessService(daoFactory);
//                        recordsToInsertFST = new ArrayList<FacultyStudentTagging>();
                        facultyStudentTagging = new FacultyStudentTagging();
                        FacultyStudentTaggingId id = new FacultyStudentTaggingId();
                        id.setInstituteid(instituteid);
                        String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                        id.setStudentfstid(StudentFstID);
                        id.setStudentid(studentid);
                        facultyStudentTagging.setId(id);
                        facultyStudentTagging.setFstid(fstid);
                        facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                        facultyStudentTagging.setEntrydate(new Date());
                        facultyStudentTagging.setAuditsubject("N");
                        facultyStudentTagging.setDeactive("N");
                        facultyStudentTagging.setRegistrationid(registrationid);
                        facultyStudentTagging.setEquivalentsubjectid(oldelectivesubjectid);
                        facultyStudentTagging.setRegconfirmationdate(regConfirmationDate);
                        recordsToInsertFST.add(facultyStudentTagging);
//                        businessService.insertInIdGenerationControl(recordsToInsertFST);
//                        businessService.closeSession();
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
                    prFacultyStudentTagging.setDeactive("N");
                    prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                    prFacultyStudentTagging.setEquivalentsubjectid(oldelectivesubjectid);
                    prFacultyStudentTagging.setPopulationdate(new Date());
                    prFacultyStudentTagging.setRegistrationid(registrationid);
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
                            fstid = businessService.generateId("FSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
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
                            String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                            id.setStudentfstid(StudentFstID);
                            id.setStudentid(studentid);
                            facultyStudentTagging.setId(id);
                            facultyStudentTagging.setFstid(fstid);
                            facultyStudentTagging.setDeactive("N");
                            facultyStudentTagging.setEntrydate(new Date());
                            facultyStudentTagging.setEntryby(Memberid);
                            facultyStudentTagging.setRegistrationid(registrationid);
                            recordsToInsertFST.add(facultyStudentTagging);

                            prFacultyStudentTagging = new PRFacultyStudentTagging();
                            PRFacultyStudentTaggingId prId = new PRFacultyStudentTaggingId();
                            prId.setInstituteid(instituteid);
                            prId.setFstid(fstid);
                            prId.setStudentid(studentid);
                            prFacultyStudentTagging.setId(prId);
                            prFacultyStudentTagging.setSstpopulated("Y");
                            prFacultyStudentTagging.setDeactive("N");
                            prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                            prFacultyStudentTagging.setPopulationdate(new Date());
                            prFacultyStudentTagging.setRegistrationid(registrationid);
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
                            String StudentFstID = businessService.generateId("StudentFSTID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                            id.setStudentfstid(StudentFstID);
                            id.setStudentid(studentid);
                            facultyStudentTagging.setId(id);
                            facultyStudentTagging.setFstid(fstid);
                            facultyStudentTagging.setEntryby(jirpsession.getJsessionInfo().getMemberid());
                            facultyStudentTagging.setEntrydate(new Date());
                            facultyStudentTagging.setEntryby(Memberid);
                            facultyStudentTagging.setAuditsubject("N");
                            facultyStudentTagging.setDeactive("N");
                            facultyStudentTagging.setRegistrationid(registrationid);
                            recordsToInsertFST.add(facultyStudentTagging);

                            prFacultyStudentTagging = new PRFacultyStudentTagging();
                            PRFacultyStudentTaggingId prId = new PRFacultyStudentTaggingId();
                            prId.setInstituteid(instituteid);
                            prId.setFstid(fstid);
                            prId.setStudentid(studentid);
                            prFacultyStudentTagging.setId(prId);
                            prFacultyStudentTagging.setSstpopulated("Y");
                            prFacultyStudentTagging.setDeactive("N");
                            prFacultyStudentTagging.setPopulatedbyuser(Memberid);
                            prFacultyStudentTagging.setPopulationdate(new Date());
                            prFacultyStudentTagging.setRegistrationid(registrationid);
                            prFacultyStudentTagging.setRegistrationid(registrationid);
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
//
//            }
            err = new ArrayList<String>();
            err.add(daoFactory.getFacultyStudentTaggingDAO().insertFacultyStudentTagging1(recordsToInsertFST, recordsToInsOrUpdPRFST, registrationid, instituteid, studentid, FSTList, PSTList, recordsToInsOrUpdChoiceMaster, recordsToInsOrUpdChoicedetail, businessService));

        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;

    }
}
