package com.jilit.irp.bso.sis;

import java.util.Map;
import com.lowagie.text.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.iservice.SubjectChoiceNotFilledByStudentReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.util.JIRPSession;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.ServletContextAware;
import com.lowagie.text.PageSize;

/**
 *
 * @author ankur.goyal
 */
@Service
public class SubjectChoiceNotFilledByStudentReportService extends ReportManager implements SubjectChoiceNotFilledByStudentReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    int studentcount = 0;

    @Override
    public void getRegistrationCombo(ModelMap model) {
        List regList = null;
        List depList = null;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteId);
//            depList = (List<Object[]>) daoFactory.getDepartmentMasterDAO().getAllDepartment();
            depList = (List<Object[]>) daoFactory.getDepartmentMasterDAO().getAllDepartmentCode();
            model.addAttribute("regCode", regList);
            model.addAttribute("depList", depList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAllAcademicYear(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("regId").toString().split("~@~");
            String regid = reg[0];
            list = (List<Object[]>) daoFactory.getAcademicYearDAO().getAcadYear_PST1(instituteid, regid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllProgramCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("regId").toString().split("~@~");
            String regid = reg[0];
            String academicyear = request.getParameter("acadYear");
            list = (List<Object[]>) daoFactory.getProgramMasterDAO().getPST_Programcodes(instituteid, regid, academicyear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllSectionCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("regId").toString().split("~@~");
            String registrationid = reg[0];
            String programid = request.getParameter("programCode");
            list = (List<Object[]>) daoFactory.getSectionMasterDAO().getAll_SectionData_SCR(registrationid, programid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllBasketCode(HttpServletRequest request) {
        List list = null;
        try {
            String instId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("registrationCode").toString().split("~@~");
            String regId = reg[0];
            String progId = request.getParameter("programCode");
            String acdyr = request.getParameter("academicYear");
            String secId = request.getParameter("sectionCode");
            list = (List<Object[]>) daoFactory.getBasketMasterDAO().getAllBasketForReport(instId, regId, progId, acdyr, secId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllSubjectCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("registrationCode").toString().split("~@~");
            String registrationid = reg[0];
            String Academicyear = request.getParameter("academicYear");
            String Programid = request.getParameter("programCode");
            String secId = request.getParameter("sectionCode");
            String basket[] = request.getParameter("basketCode").toString().split(",");
            String basketcode = "";
            for (int i = 0; i < basket.length; i++) {
                basketcode = basketcode + "'" + basket[i] + "'" + ",";
            }
            if (basketcode.endsWith(",")) {
                basketcode = basketcode.substring(0, basketcode.length() - 1);
            }
            list = (List<Object[]>) daoFactory.getSubjectMasterDAO().getAllSubjectCode_SCR(instituteid, registrationid, Academicyear, Programid, secId, basketcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllSubSectionCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("registrationCode").toString().split("~@~");
            String registrationid = reg[0];
            String academicyear = request.getParameter("academicYear");
            String subjectid = request.getParameter("subjectCode");
            String section = request.getParameter("sectionCode");
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getSubSectionNumber(instituteid, registrationid, academicyear, subjectid, section);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllSemester(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("registrationCode").toString().split("~@~");
            String registrationid = reg[0];
            String Programid = request.getParameter("programCode");
            String section = request.getParameter("sectionCode");
            list = (List<Object[]>) daoFactory.getProgramSubjectTaggingDAO().getAll_TTSTYNumberData(instituteid, registrationid, Programid, section);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String level = "one";
            String basketids = "";
            String sectiondata = "";
            String basket = request.getParameter("basketCode");
            if (!"".equals(basket)) {
                String basket1[] = basket.split(",");
                for (int i = 0; i < basket1.length; i++) {
                    if (i == 0) {
                        basketids += "'" + basket1[i] + "'";
                    } else {
                        basketids += "," + "'" + basket1[i] + "'";
                    }
                }
            }
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String reg[] = request.getParameter("registration").toString().split("~@~");
            String registrationid = reg[0];
            String registrationcode = reg[1];
            String subjectid = request.getParameter("subjectCode") == null ? "" : request.getParameter("subjectCode").toString();
            String branchid = request.getParameter("branchid") == null ? "" : request.getParameter("branchid").toString(); // Not Used 
            String branchcode = request.getParameter("branchcode") == null ? "" : request.getParameter("branchcode").toString(); //---
            String programid = request.getParameter("programCode") == null ? "" : request.getParameter("programCode").toString();
            String academicyear = request.getParameter("academicYear") == null ? "" : request.getParameter("academicYear").toString();
            String section = request.getParameter("subSectionCode") == null ? "" : request.getParameter("subSectionCode").toString();// For Sub-Section Combo
            if (!"".equals(section)) {
                String section1[] = section.split(",");
                for (int i = 0; i < section1.length; i++) {
                    if (i == 0) {
                        sectiondata += "'" + section1[i] + "'";
                    } else {
                        sectiondata += "," + "'" + section1[i] + "'";
                    }
                }
            } else {
                sectiondata = "All";
            }
            int reporttype = Integer.parseInt(request.getParameter("reporttype"));
            String reportName = request.getParameter("reportName") == null ? "" : request.getParameter("reportName").toString(); //---
            String dep = request.getParameter("department") == null ? "" : request.getParameter("department");
            String deptid = "";
            String deptcode = "";
            if (!dep.equals("")) {
                deptid = dep.split("~@~")[0];
                deptcode = dep.split("~@~")[1];
            }
            String deptname = request.getParameter("deptname"); //----
            String sem = request.getParameter("semester") == null ? "" : request.getParameter("semester").toString();
            String subjectrunning = "N";
            String exportto = request.getParameter("exportto");

            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map aSObject = null;
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);

            List<Object[]> reportDataList = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSubjectChoicesReportDatas(instituteid, registrationid, subjectid, basketids, branchid, programid, academicyear, sectiondata, sem, deptid, subjectrunning);
            if (reporttype != 1) {
                aSObject = getSubjectChoiceReportsData(instituteid, registrationid, subjectid, basketids, branchid, programid, academicyear, sectiondata, reporttype, reportDataList, sem, deptid);
                data = (List) aSObject.get("list");

                parameters = new HashMap();
                if (ims != null || data != null) {
                    parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState() == null ? "" : ims.getState());
                    parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                    parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));

                } else {
                    parameters.put("institutename", "");
                    parameters.put("sessioncode", "");
                    parameters.put("academicyear", "");
                    parameters.put("address1", "");
                    parameters.put("address2", "");
                    parameters.put("address3", "");
                    parameters.put("state", "");
                    parameters.put("city", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                }

                if (level.equals("one")) {
                    parameters.put("totalstudent", "Total Student: " + studentcount);
                    if (reporttype == 2) {
                        parameters.put("sem", "Semester: " + sem);
                        parameters.put("dept", "Department: " + deptcode);
                        path = request.getRealPath("/jrxml/InstituteElectiveReportDepartmentWise.jrxml");
                    } else if (reporttype == 3) {
                        path = request.getRealPath("/jrxml/SubjectWiseStudentReport.jrxml");//StudentWiseSubject
                    } else if (reporttype == 4) {
                        path = request.getRealPath("/jrxml/SubjectAllotedChoiceListReport.jrxml");
                    } else if (reporttype == 5) {
                        path = request.getRealPath("/jrxml/SubjectWiseStudentNewReport.jrxml");
                    } else {
                        path = request.getRealPath("/jrxml/SubjectChoiceNotFilledByStudentReport.jrxml");
                    }
                    if (exportto.contains("P")) {
                        renderReport(PDF, path, data, response, parameters, "Report");
                    } else if (exportto.contains("H")) {
                        renderReport(HTML, path, data, response, parameters, "Report");
                    } else if (exportto.contains("W")) {
                        renderReport(RTF, path, data, response, parameters, "Report");
                    } else if (exportto.contains("E")) {
                        renderReport(EXCEL, path, data, response, parameters, "Report");
                    }
                }

            } else if (exportto.contains("P")) {
                String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=StudentWiseSubjectChoiceReport.pdf");
                getStudentWiseSubjectChoiceReport(response.getOutputStream(), instituteid, registrationid, subjectid, basketids, branchid, programid, academicyear, sectiondata, reporttype, reportDataList, realPath, reportName, registrationcode, branchcode, exportto);
            } else {
                String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=StudentWiseSubjectChoiceReport.xls");
                getStudentWiseSubjectChoiceReport(response.getOutputStream(), instituteid, registrationid, subjectid, basketids, branchid, programid, academicyear, sectiondata, reporttype, reportDataList, realPath, reportName, registrationcode, branchcode, exportto);
            }
        } catch (Exception e) {
            System.err.println("Exception in getReport() in Student Master Report " + e);
            e.printStackTrace();
        }
    }

    private Map getSubjectChoiceReportsData(String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, int reporttype, List reportDataList, String sem, String deptid) {
        List rtnList = new ArrayList();
        Map aos = null;
        Set ckSet = new LinkedHashSet();
        int slno = 1;
        studentcount = 0;

        List l = new ArrayList();
        List ll = new ArrayList();
        for (int t1 = 0; t1 < reportDataList.size(); t1++) {
            if (!l.contains(String.valueOf(((Object[]) reportDataList.get(t1))[9] + "-" + ((Object[]) reportDataList.get(t1))[10]))) {
                l.add(String.valueOf(((Object[]) reportDataList.get(t1))[9] + "-" + ((Object[]) reportDataList.get(t1))[10]));
            }
            if (!ll.contains(String.valueOf(((Object[]) reportDataList.get(t1))[0]))) {
                ll.add(String.valueOf(((Object[]) reportDataList.get(t1))[0]));
            }
        }

        try {
            if (reporttype == 2 || reporttype == 4 || reporttype == 3) {
                if (ll != null && ll.size() > 0 && !ll.isEmpty()) {
                    for (int ii = 0; ii < ll.size(); ii++) {
                        if (reportDataList != null && reportDataList.size() > 0 && !reportDataList.isEmpty()) {
                            slno = 1;
                            for (int i = 0; i < reportDataList.size(); i++) {
                                if (ll.get(ii).equals(String.valueOf(((Object[]) reportDataList.get(i))[0]))) {
                                    aos = new HashMap();
                                    aos.put("slno", reporttype == 2 ? (ii + 1) : reporttype == 4 ? (slno++) : reporttype == 3 ? (ii + 1) : 0);
                                    aos.put("dept", String.valueOf(((Object[]) reportDataList.get(i))[4]));
                                    aos.put("rollno", String.valueOf(((Object[]) reportDataList.get(i))[0]));
                                    aos.put("name", String.valueOf(((Object[]) reportDataList.get(i))[1]));
                                    aos.put("sem", String.valueOf(((Object[]) reportDataList.get(i))[6]));
                                    ckSet.add(String.valueOf(((Object[]) reportDataList.get(i))[0]));
                                    aos.put("courseoffered", String.valueOf(((Object[]) reportDataList.get(i))[9] + "-" + ((Object[]) reportDataList.get(i))[10]));
                                    aos.put("subject", String.valueOf(((Object[]) reportDataList.get(i))[9] + "-" + ((Object[]) reportDataList.get(i))[10]));

                                    rtnList.add(aos);
                                }
                            }
                        }
                    }
                }

                studentcount = ckSet.size();
            }
            if ((reporttype == 0)) {
                List<Object[]> listtt = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSubjectChoiceNotFilledByStudentReport(instituteid, registrationid, subjectid, subjecttypeid, branchid, programid, academicyear, sectiondata, sem, deptid);
                if (listtt != null && listtt.size() > 0 && !listtt.isEmpty()) {
                    for (int ii = 0; ii < listtt.size(); ii++) {
                        aos = new HashMap();
                        if (!ckSet.contains(String.valueOf(((Object[]) listtt.get(ii))[0]))) {
                            aos.put("slno", slno++);
                            aos.put("dept", String.valueOf(((Object[]) listtt.get(ii))[1]));
                            aos.put("rollno", String.valueOf(((Object[]) listtt.get(ii))[0]));
                            // aos.put("name", String.valueOf(((Object[]) listtt.get(ii))[1]));
                            // aos.put("sem", String.valueOf(((Object[]) listtt.get(ii))[6]));
                            aos.put("filled", "No");
                            ckSet.add(String.valueOf(((Object[]) listtt.get(ii))[0]));
                            // aos.put("courseoffered", String.valueOf(((Object[]) listtt.get(ii))[9] + "-" + ((Object[]) listtt.get(ii))[10]));
                            // aos.put("subject", String.valueOf(((Object[]) listtt.get(ii))[9] + "-" + ((Object[]) listtt.get(ii))[10]));
                            rtnList.add(aos);
                        }
                    }
                    studentcount = ckSet.size();
                }
            }

            if (reporttype == 5) {
                if (l != null && l.size() > 0 && !l.isEmpty()) {
                    for (int p = 0; p < l.size(); p++) {
                        slno = 1;
                        for (int pi = 0; pi < reportDataList.size(); pi++) {
                            if (l.get(p).equals(String.valueOf(((Object[]) reportDataList.get(pi))[9] + "-" + ((Object[]) reportDataList.get(pi))[10]))) {
                                aos = new HashMap();
                                aos.put("slno", slno++);
                                aos.put("dept", String.valueOf(((Object[]) reportDataList.get(pi))[4]));
                                aos.put("rollno", String.valueOf(((Object[]) reportDataList.get(pi))[0]));
                                aos.put("name", String.valueOf(((Object[]) reportDataList.get(pi))[1]));
                                aos.put("sem", String.valueOf(((Object[]) reportDataList.get(pi))[6]));
                                ckSet.add(String.valueOf(((Object[]) reportDataList.get(pi))[0]));
                                //  aos.put("courseoffered", String.valueOf(((Object[]) reportDataList.get(i))[9] + "-" + ((Object[]) reportDataList.get(i))[10]));
                                aos.put("subject", String.valueOf(((Object[]) reportDataList.get(pi))[9] + "-" + ((Object[]) reportDataList.get(pi))[10]));
                                rtnList.add(aos);
                            }

                        }

                    }
                    studentcount = ckSet.size();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map aSObject = new HashMap();
        aSObject.put("list", rtnList);
        return aSObject;
    }

    private void getStudentWiseSubjectChoiceReport(ServletOutputStream out, String instituteid, String registrationid, String subjectid, String subjecttypeid, String branchid, String programid, String academicyear, String sectiondata, int reporttype, List reportDataList, String realPath, String reportName, String registrationcode, String branchcode, String exportto) {
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream bstream = null;
        Document document = null;
        PdfWriter pdfWriter = null;
        HTMLWorker htmlWorker = null;
        InstituteMaster im = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        try {
            sb.append("<html>");
            sb.append("<head><title> </title>");
            sb.append("</head>");
            sb.append("<body>");
//=======================================================Student Wise Subject Choice Report+++++++++++++++++++++++++++++++++++++//
            List distinctRollnoList = new ArrayList();
            List distinctbasketList = new ArrayList();
            List distinctsubjectList = new ArrayList();
            for (int t1 = 0; t1 < reportDataList.size(); t1++) {
                if (!distinctRollnoList.contains(((Object[]) reportDataList.get(t1))[0])) {
                    distinctRollnoList.add(((Object[]) reportDataList.get(t1))[0]);
                } else if (!distinctbasketList.contains(((Object[]) reportDataList.get(t1))[8])) {
                    distinctbasketList.add(((Object[]) reportDataList.get(t1))[8]);
                } else if (!distinctsubjectList.contains(((Object[]) reportDataList.get(t1))[9])) {
                    distinctsubjectList.add(((Object[]) reportDataList.get(t1))[9]);
                }
            }
            int maxchoice = distinctsubjectList.size() + 1;
//             For logo file name---- 
//            sb.append("<td><table style='text-align:left;border-collapse:collapse;border:1px solid black'><tr><th><img src='" + realPath + "" + im.getLogofilename() + "' width='150px' height='80px'/></th></tr></table></td>");
            sb.append("<table style='text-align:center;border-collapse:collapse;border:1px solid black;font-size:9px' border='1' colspan='" + ((2 * maxchoice) + 5) + "' >");
            sb.append("<tr><th colspan='" + ((2 * maxchoice) + 5) + "' ><b>" + im.getInstitutename() + "</b></th></tr>");
            sb.append("<tr><th colspan='" + ((2 * maxchoice) + 5) + "' ><b>" + im.getAddress1() + "," + im.getAddress2() + "," + (im.getAddress3() == null ? "" : im.getAddress3()) + "</b></th></tr>");
            sb.append("<tr><th colspan='" + ((2 * maxchoice) + 5) + "' ><b>" + im.getCity() + "," + im.getState() + "-" + im.getPin() + "</b></th></tr>");
            sb.append("<tr><th colspan='" + ((2 * maxchoice) + 5) + "' ><b>" + reportName + "</b></th></tr>");
            sb.append("<tr><th colspan='" + ((2 * maxchoice) + 5) + "' ><b>Registration Code:" + registrationcode + "</b></th></tr>");
            sb.append("<tr><th  colspan='" + ((2 * maxchoice) + 5) + "' <b>Academic Year:" + academicyear + "</b></th></tr>");
            sb.append("</table>");

            sb.append("<table style='text-align:center;border-collapse:collapse;border:1px solid black;font-size:9px' border='1' colspan='" + ((2 * maxchoice) + 5) + "'>");
            sb.append("<tr>");
            sb.append("<th font-size:9px width='100' colspan='3'style='text-align:center'><b>Roll No</b></th>");
            sb.append("<th font-size:9px colspan='2'><b>Basket</b></th>");
            for (int i = 0; i < maxchoice; i++) {
                sb.append("<th font-size:9px colspan='2'><b>Choice " + (i + 1) + "</b></th>");
            }
            sb.append("</tr>");

            for (int r = 0; r < distinctRollnoList.size(); r++) {
                for (int t = 0; t < distinctbasketList.size(); t++) {
                    int ii = 0;
                    sb.append("<tr>");
                    sb.append("<th  colspan='3' style='text-align:center'><b>" + (distinctRollnoList.get(r)) + "</b></th>");
                    sb.append("<th colspan='2'><b>" + (distinctbasketList.get(t)) + "</b></th>");
                    for (int tt = 0; tt < reportDataList.size(); tt++) {
                        if (((distinctbasketList.get(t)).equals(((Object[]) reportDataList.get(tt))[8])) && ((((Object[]) reportDataList.get(tt))[0]).equals((distinctRollnoList.get(r))))) {
                            for (int i = 0; i < maxchoice; i++) {
                                if (((BigDecimal) (((Object[]) reportDataList.get(tt))[11] == null ? new BigDecimal("0") : ((Object[]) reportDataList.get(tt))[11])).compareTo(new BigDecimal(i + 1)) == 0) {
                                    sb.append("<td colspan='2'>" + ((Object[]) reportDataList.get(tt))[9] + "</td>");
                                    ii++;
                                }
                            }
                        }
                    }
                    int ck = (maxchoice - ii) == 0 ? 0 : (maxchoice - ii);
                    for (int t2 = 0; t2 < ck; t2++) {
                        sb.append("<td colspan='2'>--</td>");
                    }
                    sb.append("</tr>");
                }
            }
            sb.append("</table>");

            //=======================================================End+++++++++++++++++++++++++++++++++++++//
            sb.append("</body>");
            sb.append("</html>");

            bstream = new ByteArrayOutputStream();
            document = new Document(PageSize.A4.rotate()) {
            };

            pdfWriter = PdfWriter.getInstance(document, bstream);

            document.open();
            document.addAuthor("Campus Lynx");
            document.addCreator("JILIT");
            document.addSubject("Report");
            document.addCreationDate();
            document.addTitle("JILIT");

            htmlWorker = new HTMLWorker(document);
            out.println(sb.toString());

            htmlWorker.parse(new StringReader(sb.toString()));
            document.close();
            pdfWriter.close();
            bstream.writeTo(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
