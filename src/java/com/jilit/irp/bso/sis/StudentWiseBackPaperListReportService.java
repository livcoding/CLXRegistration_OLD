package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.StudentWiseBackPaperListReportIService;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.StyDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.util.JIRPSession;
import java.util.HashMap;
import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 *
 * @author ankur.goyal
 */
@Service
public class StudentWiseBackPaperListReportService extends ReportManager implements StudentWiseBackPaperListReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getComboStudentWiseBackPaperList(Model model) {
        List acadList = null;
        List regList = null;
        List<ProgramMaster> progList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearReg(instituteid);
            regList = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getAllRegistrationCodeDesc(instituteid);
            progList = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().findAll(instituteid);
            model.addAttribute("acadYear", acadList);
            model.addAttribute("semCode", regList);
            model.addAttribute("program", progList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getBranch(HttpServletRequest request) {
        List list = null;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String program = request.getParameter("programCode");
            String program_1[] = program.split("~@~");
            String programId = program_1[0];
            list = (List<Object[]>) daoFactory.getBranchMasterDAO().getBranchCode(instituteId, programId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyNo(HttpServletRequest request) {
        List list = new ArrayList();
        String regid = request.getParameter("semesterCode").split("~@~")[0];
        String programid = request.getParameter("programCode").split("~@~")[0];
        String branchid = request.getParameter("branchCode").equalsIgnoreCase("All") ? "All" : request.getParameter("branchCode").split("~@~")[2];
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getStudentRegistration_infoDAO().getStyNoFOrStudentBackPaperReport(instituteid, regid, programid, branchid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        String branchid = "";
        String branchcode = "";
        String branchdesc = "";
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String exportto = request.getParameter("exportto");
            String reportfor = request.getParameter("reportfor");
            String branch = request.getParameter("branchid");
            if ("All".equals(branch)) {
                branchid = "All";
            } else {
                String[] branch_1 = branch.split("~@~");
                branchcode = branch_1[0];
                branchdesc = branch_1[1];
                branchid = branch_1[2];
            }
            String stynumber = request.getParameter("stynumber");
            String program = request.getParameter("programid");
            String[] program_1 = program.split("~@~");
            String programid = program_1[0];
            String programcode = program_1[1];
            String acadYr = request.getParameter("academicyr");
            String[] acadYr_1 = acadYr.split(",");
            String academicyr = "";
            for (int i = 0; i < acadYr_1.length; i++) {
                academicyr = academicyr + "'" + acadYr_1[i] + "'" + ",";
            }
            if (academicyr.endsWith(",")) {
                academicyr = academicyr.substring(0, academicyr.length() - 1);
            }
            String registrationid = "";
            String date1 = "";
            if (!"failgrade".equals(reportfor)) {
                String reg = request.getParameter("registrationid");
                String reg_1[] = reg.split("~@~");
                registrationid = reg_1[0];
                date1 = reg_1[1];
            }
            String order = request.getParameter("order");
            String path = null;
            HashMap parameters = null;
            List<Object[]> list = null;
            List<Object[]> list1 = null;
            List<Object[]> list2 = null;
            List<Object[]> list3 = null;
            List<Object[]> list4 = null;
            List<Object[]> sublist = null;
            List<Object[]> sublistCompulsory = null;

            //******************************* for Branch Wise Fail Student Subject With their fail grade**************************************//
            if ("E".equals(request.getParameter("exportto")) && reportfor.equals("failgrade")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Fail_Grade_Student_Subject.xls");
                sublist = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentBackPaperSubjectList(instituteid, programid, branchid, stynumber, order, academicyr);
                list = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentBackPaperListWithFailGrade(instituteid, programid, branchid, stynumber, order, academicyr);
                List l = new ArrayList();
                l.add(1);
                l.add(2);
                l.add(3);
                l.add(4);
                List<Object[]> newlist = daoFactory.getUtilDAO().listAgg(list, l, "@");
                downloadStudentBackPaperListWithFailGradePdfCumExcelReport(response.getOutputStream(), request.getRealPath(""), request, newlist, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, sublist);
            } else if ("P".equals(request.getParameter("exportto")) && reportfor.equals("failgrade")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Branch_Wise_fail_Student_Subject.pdf");
                list = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentBackPaperListWithFailGrade(instituteid, programid, branchid, stynumber, order, academicyr);
                List l = new ArrayList();
                l.add(1);
                l.add(2);
                l.add(3);
                l.add(4);
                List<Object[]> newlist = daoFactory.getUtilDAO().listAgg(list, l, "@");
                sublist = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentBackPaperSubjectList(instituteid, programid, branchid, stynumber, order, academicyr);
                downloadStudentBackPaperListWithFailGradePdfReport(response.getOutputStream(), request.getRealPath(""), request, newlist, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, sublist);
            } //******************************* for fail grade student subject registration deatil**************************************//
            else if ("P".equals(request.getParameter("exportto")) && reportfor.equals("regwise")) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
//                Date date = format1.parse(date1);
                String event_from = format2.format(new Date()).toString();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Fail_Grade_Student_Subject_Registration_detail.pdf");

                list1 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getFailGradeStudentSubjectRegistrationDeatil(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadFailGradeStudentSubjectRegistrationDetailPdfReport(response.getOutputStream(), request.getRealPath(""), request, list1, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, event_from);
            } else if ("E".equals(request.getParameter("exportto")) && reportfor.equals("regwise")) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
//                Date date = format1.parse(new Date());
                String event_from = format2.format(new Date()).toString();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Fail_Grade_Student_Subject_Registration.xls");
                list1 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getFailGradeStudentSubjectRegistrationDeatil(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadFailGradeStudentSubjectRegistrationDetailPdfReport(response.getOutputStream(), request.getRealPath(""), request, list1, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, event_from);

            } //******************************* for Regular Student Compulsory Subject Who have done Registration deatil**************************************//
            else if ("P".equals(request.getParameter("exportto")) && reportfor.equals("compulsorysubject")) {

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Regular_Student_Compulsory_Subject.pdf");
                sublistCompulsory = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentCompulsorySubjectList(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                list2 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getRegularStudentCompulsorySubjectData(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadRegularStudentCompulsorySubjectPdfReport(response.getOutputStream(), request.getRealPath(""), request, list2, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, academicyr, sublistCompulsory);
            } else if ("E".equals(request.getParameter("exportto")) && reportfor.equals("compulsorysubject")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Regular_student_compulsory_subject.xls");
                sublistCompulsory = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getStudentCompulsorySubjectList(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                list2 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getRegularStudentCompulsorySubjectData(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadRegularStudentCompulsorySubjectPdfReport(response.getOutputStream(), request.getRealPath(""), request, list2, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, academicyr, sublistCompulsory);

            } //******************************* for Regular Student Elective Subject List Who have done Registration **************************************//
            else if ("P".equals(request.getParameter("exportto")) && reportfor.equals("electivesubject")) {

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Elective_Subject_Choice Not_Filled_By_Student.pdf");

                list3 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getRegularStudentElectiveSubjectData(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                List l = new ArrayList();
                l.add(3);
                List<Object[]> newlist = daoFactory.getUtilDAO().listAgg(list3, l, ",");
                downloadRegularStudentElectiveSubjectReport(response.getOutputStream(), request.getRealPath(""), request, newlist, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, academicyr, sublistCompulsory);
            } else if ("E".equals(request.getParameter("exportto")) && reportfor.equals("electivesubject")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Elective_Subject_Choice_Not_Filled_By_Student.xls");

                list3 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getRegularStudentElectiveSubjectData(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                List l = new ArrayList();
                l.add(3);
                List<Object[]> newlist = daoFactory.getUtilDAO().listAgg(list3, l, ",");
                downloadRegularStudentElectiveSubjectReport(response.getOutputStream(), request.getRealPath(""), request, newlist, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc, academicyr, sublistCompulsory);

            } //******************************* for fail grade student subject not registered deatil**************************************//
            else if ("P".equals(request.getParameter("exportto")) && reportfor.equals("notregister")) {

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Fail_Grade_Student_Subject_Not_Registered.pdf");

                list4 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getFailGradeStudentSubjectNotRegisteredDeatil(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadFailGradeStudentSubjectNotRegisteredDetailPdfReport(response.getOutputStream(), request.getRealPath(""), request, list4, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc);

            } else if ("E".equals(request.getParameter("exportto")) && reportfor.equals("notregister")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Fail_Grade_Student_Subject_Not_Registered.xls");

                list4 = (List<Object[]>) daoFactory.getStudentAttendanceDAO().getFailGradeStudentSubjectNotRegisteredDeatil(instituteid, programid, branchid, stynumber, order, academicyr, registrationid);
                downloadFailGradeStudentSubjectNotRegisteredDetailPdfReport(response.getOutputStream(), request.getRealPath(""), request, list4, instituteid, branchid, branchcode, stynumber, programid, exportto, programcode, branchdesc);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //*******************************Report for Branch Wise Fail Student Subject With their fail grade**************************************//
    public void downloadStudentBackPaperListWithFailGradePdfReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc, List<Object[]> sublist) {
        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        StringBuilder html = new StringBuilder();
        String enrollno = null;

        String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);

            if (list != null && !list.isEmpty()) {
                int pageindex = 1;
                int j = 0;

                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");

                for (int index = 0; index < list.size(); index++) {
                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td style='text-align:centre' colspan='1'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr >");
                    html.append("<td style='text-align:centre'>");
                    html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                    html.append("</td>");
                    html.append("</tr>");
                    html.append("</table>");
                    html.append("</td>");

                    html.append("<td colspan='5'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("</td>");

                    html.append("</tr>");

                    html.append("</table>");

                    html.append("<hr width=100%, size='1px'");
                    html.append("<br/>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td style='font-weight:bold'>" + programcode + " in " + branchdesc + " </td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td style='font-weight:bold'>Student Wise Back Paper List With Fail Grade </td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' border='.5' colspan='14' align='center'>");
                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    html.append("<td colspan='1' style='font-weight:bold;font-size:7px'> SL.No</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Enrollment No</td> ");

                    html.append("<td colspan='11'style='font-weight:bold;font-size:7px'> ");

                    html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> " + request.getParameter("stynumber") + "</td> ");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;'   align='center'>");
                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    for (j = 0; j < sublist.size(); j++) {
                        html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> " + sublist.get(j)[0] + "</td> ");
                    }
                    html.append("</tr>");

                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    for (j = 0; j < sublist.size(); j++) {
                        html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> " + sublist.get(j)[1] + "</td> ");
                    }
                    html.append("</tr>");
                    html.append("</table>");
                    html.append("</td>");
                    html.append("</tr>");

                    if (list != null && !list.isEmpty()) {
                        for (int i = index; i < (index + 28); i++) {
                            String[] RegStr = null;
                            if (i < list.size()) {

                                html.append("<tr style='font-size:8px'>");
                                html.append("<td colspan='1' style='font-size:7px align='center'>" + (i + 1) + "" + " </td>");
                                html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[0] != null ? list.get(i)[0].toString() : " ") + " </td>");

                                html.append("<td colspan='11'style='font-weight:bold;font-size:7px'> ");
                                html.append("<table style='font-size:7px;font-family:Calibri;'   align='center'>");
                                html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");

                                String[] str = list.get(i)[1].toString().split("@");//0,2,3,6

                                if (list.get(i)[3] != null) {
                                    RegStr = list.get(i)[3].toString().split("@");
                                }

                                int kk = 0;
                                int m = 0;
                                for (int k = 0; k < str.length; k++) {
                                    for (j = kk; j < sublist.size(); j++) {
                                        if (str[k].toString().equals(sublist.get(j)[0].toString())) {
                                            if (RegStr != null && RegStr.length > 0) {
                                                if (k + 1 <= RegStr.length) {
                                                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px align='center'> " + (RegStr[k].toString().equalsIgnoreCase("-") ? "-" : RegStr[k].toString()) + "</td> ");
                                                } else {
                                                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px align='center'> </td> ");
                                                }
                                            } else {
                                                html.append("<td colspan='2'style='font-weight:bold;font-size:7px align='center'> </td> ");
                                            }

                                            kk = j + 1;
                                            if (str.length > 1) {
                                                break;
                                            }

                                        } else {
                                            html.append("<td colspan='2'style='font-weight:bold;font-size:7px align='center'> </td> ");
                                        }
                                    }

                                    m = sublist.size() - kk;
                                }
                                if (m > 0 && str.length > 1) {
                                    for (int nn = 0; nn < m; nn++) {
                                        html.append("<td colspan='2'style='font-weight:bold;font-size:7px align='center'> </td> ");
                                    }
                                }

                                html.append("</tr>");
                                html.append("</table>");
                                html.append("</td>");
                                html.append("</tr>");

                            }
                        }
                    }

                    html.append("</table>");
                    index += 27;
                }

                html.append("</body></html>");
            } else { //no data
                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='1'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");

                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:8px;font-family:Calibri;' align='center'>");
                html.append("<tr><td>" + programcode + " in " + branchdesc + " </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:8px;font-family:Calibri;' align='center'>");
                html.append("<tr><td>Student Wise Back Paper List With Fail Grade  </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:16px;font-weight:bold;font-family:Calibri;' colspan='4' >");
                html.append("<tr><td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='4' align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There is no data Found.</td></tr>");
                html.append("<tr><td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");
                html.append("</body></html>");
            }

            if (!exportto.contains("P")) {
                out.print(html.toString());
            } else {
                htmlWorker.parse(new StringReader(html.toString()));
                document.close();
                int n = pdfWriter.getPageNumber();
                pdfWriter.close();

                // Create a stamper that will copy the document to a new file
                PdfReader reader = new PdfReader(bstream.toByteArray());
                bstream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bstream);
                int i = 1;
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte under;
                Image img = Image.getInstance(path + ims.getWatermarkfilename());
                img.setAbsolutePosition(170, 300);
                img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    under.addImage(img);
                    under.beginText();
                    under.setFontAndSize(bf, 10);
                    under.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page " + i + " of " + (n - 1), 15, 5, 0);
                    under.endText();
                    i++;
                }
                stamp.close();

                bstream.writeTo(out);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******************************* Method for fail grade student subject registration deatil**************************************//
    public void downloadFailGradeStudentSubjectRegistrationDetailPdfReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc, String event_from) {
        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        StringBuilder html = new StringBuilder();
        String enrollno = "";
        String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);

            if (list != null && !list.isEmpty()) {
                int pageindex = 1;

                int j = 0;

                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");

                for (int index = 0; index < list.size(); index++) {
                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td style='text-align:centre' colspan='1'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr >");
                    html.append("<td style='text-align:centre'>");
                    html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                    html.append("</td>");
                    html.append("</tr>");
                    html.append("</table>");
                    html.append("</td>");

                    html.append("<td colspan='5'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("</td>");

                    html.append("</tr>");

                    html.append("</table>");

                    html.append("<hr width=100%, size='1px'");
                    html.append("<br/>");

                    html.append("<table style='font-size:9px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td><u><b>Proforma - C :</b> REGISTRATION DETAILS OF STUDENTS ( UNDER RPR CATEGORY ) </u></td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' align='left'>");
                    html.append("<tr><td style='font-weight:bold colspan='10'>Name Of Institute : - <b><u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</u></b> </td></tr>");
                    html.append("<tr><td style='font-weight:bold colspan='10'>Programm Name: - <b><u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + programcode + " in " + branchdesc + " </u></b></td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;'>");
                    html.append("<tr><td style='font-weight:bold'><b>Examination Month & Year :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + event_from + "</td></tr>");
                    html.append("<tr><td style='font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' border='.5' colspan='13' align='center'>");
                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Enrollment No</td> ");
                    html.append("<td colspan='1'style='font-weight:bold;font-size:7px'> Semester</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Subject code</td> ");
                    html.append("<td colspan='6'style='font-weight:bold;font-size:7px'> Subject Name</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Registration Type</td> ");
                    html.append("</tr>");

                    if (list != null && !list.isEmpty()) {
                        for (int i = index; i < (index + 29); i++) {
                            if (i < list.size()) {
                                if (i > 0) {
                                    enrollno = list.get(i - 1)[0].toString();
                                }
                                html.append("<tr style='font-size:8px'>");
                                //              html.append("<td colspan='1' style='font-size:7px align='center'>" + (i + 1) + "" + " </td>");
                                if (enrollno.equals(list.get(i)[0])) {
                                    html.append("<td colspan='2'style='font-size:7px align='center'></td>");
                                } else {
                                    html.append("<td colspan='2'style='font-size:7px align='center'><b>" + (list.get(i)[0] != null ? list.get(i)[0].toString() : " ") + " </b></td>");
                                }
                                html.append("<td colspan='1'style='font-size:7px align='center'>" + (list.get(i)[4] != null ? list.get(i)[4].toString() : " ") + " </td>");
                                html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[1] != null ? list.get(i)[1].toString() : " ") + " </td>");
                                html.append("<td colspan='6'style='font-size:7px'>" + (list.get(i)[2] != null ? list.get(i)[2].toString() : " ") + " </td>");
                                html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[5] != null ? list.get(i)[5].toString() : " ") + " </td>");

                                html.append("</tr>");

                            }
                        }
                    }
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                    html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                    html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;' colspan='11' align='center'   align='left'>");
                    html.append("<tr>");
                    html.append("<td colspan='1'style='font-size:8px;font-weight:bold' align='left'>________<br/>Date - :</td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Verified by head student section:</td>");
                    html.append("<td colspan='4'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Signature of head of the department :</td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Signature of head of the institute :</td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<hr width=10%, size='1px'");
                    html.append("</br>");

                    index += 28;
                }

                html.append("</body></html>");
            } else { //no data
                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='1'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");

                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>" + programcode + " in " + branchdesc + " </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>Month And Year Of Examination: " + event_from + "</td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>Registration Detail of Student(Under RPR category) </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' border='.5' colspan='13' align='center'>");
                html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                //                html.append("<td colspan='1' style='font-weight:bold;font-size:7px'> SL.No</td> ");
                html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Enrollment No</td> ");
                html.append("<td colspan='1'style='font-weight:bold;font-size:7px'> Semester</td> ");
                html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Subject code</td> ");
                html.append("<td colspan='6'style='font-weight:bold;font-size:7px'> Subject Name</td> ");
                html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Registration Type</td> ");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:16px;font-weight:bold;font-family:Calibri;' colspan='4' >");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There is no data Found.</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");
                html.append("</body></html>");
            }

            if (!exportto.contains("P")) {
                out.print(html.toString());
            } else {
                htmlWorker.parse(new StringReader(html.toString()));
                document.close();
                int n = pdfWriter.getPageNumber();
                pdfWriter.close();

                // Create a stamper that will copy the document to a new file
                PdfReader reader = new PdfReader(bstream.toByteArray());
                bstream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bstream);
                int i = 1;
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte under;
                Image img = Image.getInstance(path + ims.getWatermarkfilename());
                img.setAbsolutePosition(170, 300);
                img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    under.addImage(img);
                    under.beginText();
                    under.setFontAndSize(bf, 10);
                    under.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page " + i + " of " + (n - 1), 15, 5, 0);
                    under.endText();
                    i++;
                }
                stamp.close();

                bstream.writeTo(out);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }

    //******************************* for Regular Student Compulsory Subject List Who have done Registration **************************************//
    public void downloadRegularStudentCompulsorySubjectPdfReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc, String academicyr, List<Object[]> sublistCompulsory) {

        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        StringBuilder html = new StringBuilder();
        String enrollno = "";
        String styno = "";
        String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);

            if (list != null && !list.isEmpty()) {
                int pageindex = 1;

                int j = 0;
                int i = 0;

                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");

                //             for (int index = 0; index < list.size(); index++) {
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='1'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");

                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");

                html.append("</td>");

                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:9px;font-family:Calibri; 'colspan='4' align='center'>");
                html.append("<tr><th 'colspan='3' style='font-weight:bold' align='center'>Proforma-A of Circular no.: NUST/Exam/Circular/2008-09/2363 dated 02-02-2009</th>");
                html.append("</tr>");
                html.append("</table>");
                html.append("<table style='font-size:9px;font-family:Calibri; 'colspan='4' align='center'>");
                html.append("<tr><td 'colspan='3' style='font-weight:bold' align='center'><u>Part - I</u></td>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='left'>");
                html.append("<tr><td style='font-weight:bold colspan='10'>Name of Institute : - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</u></td></tr>");
                html.append("<tr><td style='font-weight:bold colspan='10'>Programm Name: - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>" + programcode + " in " + branchdesc + " </u></td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri; 'colspan='4' align='left'>");
                html.append("<tr><td 'colspan='3' style='font-weight:bold'>Details of schedule course notified for :" + stynumber + "</td><td 'colspan='3' style='font-weight:bold' align='right'>Academic Year :" + request.getParameter("registrationcode") + "</td>");

                html.append("</tr>");
                html.append("</table><br/>");

                html.append("<table style='font-size:7px;font-family:Calibri;'  align='center'>");
                html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                html.append("<td colspan='1' style='font-weight:bold;font-size:7px' border='.5'><b> Course Code and Name</b></td> ");
                html.append("</tr>");
                html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");

                html.append("<td>");
                html.append("<table style='text-align:left' cellpadding='0' colspan='2'  border='.5' >");

                if (sublistCompulsory != null && !sublistCompulsory.isEmpty()) {
                    for (int k = 0; k < sublistCompulsory.size(); k++) {

                        html.append("<tr>");

                        html.append(((sublistCompulsory.size() > k) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + sublistCompulsory.get(k)[0] + "</td> " : "<td colspan='1'></td>"));
                        html.append(((sublistCompulsory.size() > k) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + sublistCompulsory.get(k)[1] + "</td> " : "<td colspan='1'></td>"));
                        k++;
                        html.append(((sublistCompulsory.size() > k) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + sublistCompulsory.get(k)[0] + "</td> " : "<td colspan='1'></td>"));
                        html.append(((sublistCompulsory.size() > k) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + sublistCompulsory.get(k)[1] + "</td> " : "<td colspan='1'></td>"));
                        html.append("</tr>");

                    }
                }
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                html.append("<tr><td  style='font-size:7px' >Following students of regular batch who have registered for Initial Registration (IR) in the abovecourses are eligible for Semester End Examination if they fulfill the academic requirements as per academic regulations. </td></tr>");
                html.append("</table>");

                html.append("<table style='text-align:left' cellpadding='0'    >");
                html.append("<tr><td  style='font-weight:bold;font-size:7px'  border='.5' align='center'><u>Roll No/Exam No </u></td></tr>");
                html.append("</table>");

                html.append("<table style='text-align:left' cellpadding='0' colspan='6'   >");
                if (list != null && !list.isEmpty()) {
                    for (i = 0; i < list.size(); i++) {
                        html.append("<tr>");

                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));
                        i++;
                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));
                        i++;
                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));
                        i++;
                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));
                        i++;
                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));
                        i++;
                        html.append(((list.size() > i) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='center'>" + list.get(i)[0] + "</td> " : "<td colspan='1'></td>"));

                        html.append("</tr>");

                    }
                }

                html.append("</table>");

                html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");

                html.append("<table style='text-align:left' cellpadding='0' colspan='1' border='.5'  >");
                html.append("<tr>");
                html.append(((list.size() > 0) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  >Total No of Students :" + list.size() + "</td> " : "<td colspan='1'></td>"));
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");

                html.append("<table style='font-size:8px;font-family:Calibri;' colspan='11' align='center'   align='left'>");
                html.append("<tr>");
                html.append("<td colspan='1'style='font-size:8px;font-weight:bold' align='left'>_________<br/>Date - :</td>");
                html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>___________________<br/>Verified by head student section:</td>");
                html.append("<td colspan='4'style='font-size:8px;font-weight:bold' align='center'>___________________<br/>Signature of head of the department :</td>");
                html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>___________________<br/>Signature of head of the institute :</td>");
                html.append("</table>");

                //       index += 31;
                html.append("</body></html>");
            } else { //no data
                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='1'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "' width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:7px;font-family:Calibri; 'colspan='4' align='center'>");
                html.append("<tr><td 'colspan='3' style='font-weight:bold' align='left'>Details of schedule course notified for :" + stynumber + "</td>");
                html.append("<td 'colspan='1' style='font-weight:bold'>Academic year :" + academicyr + "</td>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>Registration Deatil of Student(Under RPR category) </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:16px;font-weight:bold;font-family:Calibri;' colspan='4' >");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There is no data Found.</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");
                html.append("</body></html>");
            }

            if (!exportto.contains("P")) {
                out.print(html.toString());
            } else {
                htmlWorker.parse(new StringReader(html.toString()));
                document.close();
                int n = pdfWriter.getPageNumber();
                pdfWriter.close();

                // Create a stamper that will copy the document to a new file
                PdfReader reader = new PdfReader(bstream.toByteArray());
                bstream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bstream);
                int i = 1;
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte under;
                Image img = Image.getInstance(path + ims.getWatermarkfilename());
                img.setAbsolutePosition(170, 300);
                img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    under.addImage(img);
                    under.beginText();
                    under.setFontAndSize(bf, 10);
                    under.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page " + i + " of " + (n - 1), 15, 5, 0);
                    under.endText();
                    i++;
                }
                stamp.close();

                bstream.writeTo(out);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******************************* for Regular Student Elective Subject List Who have done Registration **************************************//
    public void downloadRegularStudentElectiveSubjectReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc, String academicyr, List<Object[]> sublistCompulsory) {

        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        StringBuilder html = new StringBuilder();
        String enrollno = "";
        String[] str = null;
        Map Basketcodemap = new HashMap();
        Map<String, String> Headingmap = new TreeMap<String, String>();
        Headingmap.put("B", "Department Elective Courses (IR Category) Elective-I");
        Headingmap.put("B1", "Department Elective Courses (IR Category) Elective-II");
        Headingmap.put("B2", "Department Elective Courses (IR Category) Elective-III");
        String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);

            if (list != null && !list.isEmpty()) {
                int pageindex = 1;
                int totalcount = 0;

                int j = 0;
                int i = 0;

                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");

                for (int index = 0; index < list.size(); index++) {

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td style='text-align:centre' colspan='2'>");

                    html.append("</td>");

                    html.append("<td colspan='5'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("</td>");

                    html.append("</tr>");

                    html.append("</table>");

                    html.append("<hr width=100%, size='1px'");
                    html.append("<br/>");

                    html.append("<table style='font-size:9px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td style='font-weight:bold'><u>Proforma - A</u><br/><u>Part - II </u></td></tr>");
                    html.append("</tr>");
                    html.append("</table>");
                    html.append("<table style='font-size:9px;font-family:Calibri;' align='left'>");
                    html.append("<tr><td style='font-weight:bold colspan='10'>Name of Institute : - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</u></td></tr>");
                    html.append("<tr><td style='font-weight:bold colspan='10'>Programm Name: - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>" + programcode + " in " + branchdesc + " </u></td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    if (list != null && !list.isEmpty()) {
                        for (Map.Entry<String, String> em : Headingmap.entrySet()) {
                            boolean hradingprint = false;
                            for (i = 0; i < list.size(); i++) {
                                if (em.getKey().equalsIgnoreCase(list.get(i)[0].toString())) {
                                    str = list.get(i)[3].toString().split(",");

                                    if (!hradingprint) {
                                        html.append("<table style='font-size:9px;font-family:Calibri;' align='center'>");
                                        html.append("<tr><td style='font-weight:bold' align='left'>" + em.getValue() + "</td>");
                                        hradingprint = true;
                                        html.append("<td style='font-weight:bold' align='right'>Semester " + stynumber + " </td>");
                                        html.append("</tr>");
                                        html.append("</table>");
                                    }
                                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                                    html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                                    html.append("</table>");

                                    html.append("<table style='text-align:left' cellpadding='0' colspan='1'  border='.5' >");
                                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                                    html.append("<td colspan='1' style='font-weight:bold;font-size:7px' align='center'>Cousre Code and Name :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + list.get(i)[1] + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + list.get(i)[2] + "</td> ");
                                    html.append("</tr>");
                                    html.append("</table>");

                                    html.append("<table style='text-align:left' cellpadding='0' colspan='6'   >");

                                    for (j = 0; j < str.length; j++) {
                                        html.append("<tr>");
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));
                                        j++;
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));
                                        j++;
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));
                                        j++;
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));
                                        j++;
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));
                                        j++;
                                        html.append(((str.length > j) ? "<td colspan='1' style='font-size:7px'  align='center'>" + str[j].toString() + "</td> " : "<td colspan='1'></td>"));

                                        html.append("</tr>");

                                    }
                                    html.append("</table>");

                                    html.append("<table style='text-align:left' cellpadding='0' colspan='1' border='.5'  >");
                                    html.append("<tr>");
                                    html.append(((str.length > 0) ? "<td colspan='1' style='font-weight:bold;font-size:7px'  align='right'>Total No of Students :" + str.length + "</td> " : "<td colspan='1'></td>"));
                                    html.append("</tr>");
                                    html.append("</table>");

                                    totalcount = totalcount + str.length;
                                }
                            }
                        }
                    }

                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left' border='.5' >");
                    html.append("<tr><td colspan='3'><b>Total  Students :" + Integer.toString(totalcount) + "</b></td></tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;' colspan='11' align='center'   align='left'>");
                    html.append("<tr>");
                    html.append("<td colspan='1'style='font-size:8px;font-weight:bold' align='left'>_______<br/>Date - :</td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Verified by head student section:</td>");
                    html.append("<td colspan='4'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Signature of head of the department</td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>________________________<br/>Signature of head of the institute </td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<hr width=10%, size='1px'");
                    html.append("</br>");

                    index += 31;
                }

                html.append("</body></html>");
            } else { //no data
                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='1'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");

                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:7px;font-family:Calibri; 'colspan='4' align='center'>");
                html.append("<tr><td 'colspan='3' style='font-weight:bold' align='left'>Details of schedule course notified for :" + stynumber + "</td>");
                html.append("<td 'colspan='1' style='font-weight:bold'>Academic year :" + academicyr + "</td>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'><u>Proforma - A</u><br/><u>Part - II </u> </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:16px;font-weight:bold;font-family:Calibri;' colspan='4' >");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There is no data Found.</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");
                html.append("</body></html>");
            }
            if (!exportto.contains("P")) {
                out.print(html.toString());
            } else {
                htmlWorker.parse(new StringReader(html.toString()));
                document.close();
                int n = pdfWriter.getPageNumber();
                pdfWriter.close();

                // Create a stamper that will copy the document to a new file
                PdfReader reader = new PdfReader(bstream.toByteArray());
                bstream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bstream);
                int i = 1;
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte under;
                Image img = Image.getInstance(path + ims.getWatermarkfilename());
                img.setAbsolutePosition(170, 300);
                img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    under.addImage(img);
                    under.beginText();
                    under.setFontAndSize(bf, 10);
                    under.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page " + i + " of " + (n - 1), 15, 5, 0);
                    under.endText();
                    i++;
                }
                stamp.close();

                bstream.writeTo(out);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******************************* Method for fail grade student subject not registered deatil**************************************//
    public void downloadFailGradeStudentSubjectNotRegisteredDetailPdfReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc) {
        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        StringBuilder html = new StringBuilder();
        String enrollno = "";
        String realPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);

            if (list != null && !list.isEmpty()) {
                int pageindex = 1;

                int j = 0;

                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");

                for (int index = 0; index < list.size(); index++) {
                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td style='text-align:centre' colspan='1'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr >");
                    html.append("<td style='text-align:centre'>");
                    html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                    html.append("</td>");
                    html.append("</tr>");
                    html.append("</table>");
                    html.append("</td>");

                    html.append("<td colspan='5'>");

                    html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                    html.append("<tr>");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                    html.append("</tr>");
                    html.append("<tr  >");
                    html.append("<td colspan='5' style='text-align:center;font-size:11px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("</td>");

                    html.append("</tr>");

                    html.append("</table>");

                    html.append("<hr width=100%, size='1px'");
                    html.append("<br/>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td style='font-weight:bold'>" + programcode + " in " + branchdesc + " </td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:9px;font-family:Calibri;' align='center'>");
                    html.append("<tr><td style='font-weight:bold'>NOT REGISTERED STUDENTS ( UNDER RPR CATEGORY )</td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:9px;font-family:Calibri;' align='left'>");
                    html.append("<tr><td style='font-weight:bold colspan='10'>Name of Programme : - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>" + programcode + " in " + branchdesc + " </u></td></tr>");
                    html.append("</tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:7px;font-family:Calibri;' border='.5' colspan='11' align='center'>");
                    html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                    //                html.append("<td colspan='1' style='font-weight:bold;font-size:7px'> SL.No</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Enrollment No</td> ");
                    html.append("<td colspan='1'style='font-weight:bold;font-size:7px'> Semester</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Subject code</td> ");
                    html.append("<td colspan='6'style='font-weight:bold;font-size:7px'> Subject Name</td> ");
                    html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> RegiCode</td> ");

                    html.append("</tr>");

                    if (list != null && !list.isEmpty()) {
                        for (int i = index; i < (index + 32); i++) {
                            if (i < list.size()) {
                                if (i > 0) {
                                    enrollno = list.get(i - 1)[0].toString();
                                }
                                html.append("<tr style='font-size:8px'>");
                                //              html.append("<td colspan='1' style='font-size:7px align='center'>" + (i + 1) + "" + " </td>");
                                if (enrollno.equals(list.get(i)[0])) {
                                    html.append("<td colspan='2'style='font-size:7px align='center'></td>");
                                } else {
                                    html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[0] != null ? list.get(i)[0].toString() : "N/A") + " </td>");
                                }
                                html.append("<td colspan='1'style='font-size:7px align='center'>" + (list.get(i)[1] != null ? list.get(i)[1].toString() : "N/A") + " </td>");
                                html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[2] != null ? list.get(i)[2].toString() : "N/A") + " </td>");
                                html.append("<td colspan='6'style='font-size:7px align='left'>" + (list.get(i)[3] != null ? list.get(i)[3].toString() : "N/A") + " </td>");
                                html.append("<td colspan='2'style='font-size:7px align='left'>" + (list.get(i)[4] != null ? list.get(i)[4].toString() : "N/A") + " </td>");
                                //      html.append("<td colspan='2'style='font-size:7px align='center'>" + (list.get(i)[5] != null ? list.get(i)[5].toString() : "N/A") + " </td>");

                                html.append("</tr>");

                            }
                        }
                    }
                    html.append("</table>");
                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                    html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;'   align='left'>");
                    html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                    html.append("</table>");

                    html.append("<table style='font-size:8px;font-family:Calibri;' colspan='11' align='center'   align='left'>");
                    html.append("<tr>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='left'>Date - :_________________</td>");
                    html.append("<td colspan='2'style='font-size:8px;font-weight:bold' align='center'></td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'> </td>");
                    html.append("<td colspan='3'style='font-size:8px;font-weight:bold' align='center'>Signature of head of the institute :</td></tr>");
                    html.append("</table>");

                    html.append("<hr width=10%, size='1px'");
                    html.append("</br>");

                    index += 31;
                }

                html.append("</body></html>");
            } else { //no data
                html.append("<html>");
                html.append("<head>");
                html.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td style='text-align:centre' colspan='2'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr >");
                html.append("<td style='text-align:centre'>");
                html.append("<img src='" + path + ims.getLogofilename() + "'  width='70' height='70' />");
                html.append("</td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");

                html.append("<td colspan='5'>");

                html.append("<table style='text-align:left' cellpadding='0'  border='0' >");
                html.append("<tr>");
                html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getInstitutename() == null ? "" : ims.getInstitutename().toUpperCase()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getAddress1() == null ? "" : ims.getAddress1()) + " " + (ims.getAddress2() == null ? "" : ims.getAddress2()) + " " + (ims.getAddress3() == null ? "" : ims.getAddress3()) + "</b></td>");
                html.append("</tr>");
                html.append("<tr  >");
                html.append("<td colspan='5' style='text-align:center;font-size:12px'><b>&nbsp;&nbsp;" + (ims.getCity() == null ? "" : ims.getCity()) + " " + (ims.getState() == null ? "" : ims.getState()) + " " + (ims.getPin() == null ? "" : ims.getPin()) + "</b></td>");
                html.append("</tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("</tr>");

                html.append("</table>");

                html.append("<hr width=100%, size='1px'");
                html.append("<br/>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>" + programcode + " in " + branchdesc + " </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' align='center'>");
                html.append("<tr><td style='font-weight:bold'>Registration Deatil of Student(Under RPR category) </td></tr>");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:7px;font-family:Calibri;' border='.5' colspan='11' align='center'>");
                html.append("<tr style='font-size:7px;font-family:Calibri;font-weight:bold;'> ");
                //                html.append("<td colspan='1' style='font-weight:bold;font-size:7px'> SL.No</td> ");
                html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Enrollment No</td> ");
                html.append("<td colspan='1'style='font-weight:bold;font-size:7px'> Semester</td> ");
                html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Subject code</td> ");
                html.append("<td colspan='6'style='font-weight:bold;font-size:7px'> Subject Name</td> ");
                //                 html.append("<td colspan='2'style='font-weight:bold;font-size:7px'> Registration Type</td> ");
                html.append("</tr>");
                html.append("</table>");

                html.append("<table style='font-size:16px;font-weight:bold;font-family:Calibri;' colspan='4' >");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There is no data Found.</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("<tr><td colspan='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
                html.append("</table>");
                html.append("</body></html>");
            }

            if (!exportto.contains("P")) {
                out.print(html.toString());
            } else {
                htmlWorker.parse(new StringReader(html.toString()));
                document.close();
                int n = pdfWriter.getPageNumber();
                pdfWriter.close();

                // Create a stamper that will copy the document to a new file
                PdfReader reader = new PdfReader(bstream.toByteArray());
                bstream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bstream);
                int i = 1;
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte under;
                Image img = Image.getInstance(path + ims.getWatermarkfilename());
                img.setAbsolutePosition(170, 300);
                img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    under.addImage(img);
                    under.beginText();
                    under.setFontAndSize(bf, 10);
                    under.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page " + i + " of " + (n - 1), 15, 5, 0);
                    under.endText();
                    i++;
                }
                stamp.close();

                bstream.writeTo(out);
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
    }

    /**
     * Method Use To Generate The Excel File
     *
     * @param out
     * @param data
     * @param datelist
     * @param timelist
     * @param deptlist
     * @param shortnamelist
     * @param excelHeaderDetail
     */
    public void downloadStudentBackPaperListWithFailGradePdfCumExcelReport(ServletOutputStream out, String path, HttpServletRequest request, List<Object[]> list, String instituteid, String branchid, String branchcode, String stynumber, String programid, String exportto, String programcode, String branchdesc, List<Object[]> sublist) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        int lastindex = 0;
        try {
            if (sublist != null && !sublist.isEmpty()) {
                HSSFSheet sheet = hwb.createSheet("praveen");

                HSSFFont headfont = hwb.createFont();
                headfont.setFontHeightInPoints((short) 15);
                headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

                HSSFFont headerFont = hwb.createFont();
                headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                headerFont.setFontHeightInPoints((short) 12);

                HSSFFont dataFont = hwb.createFont();
                dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                headerFont.setFontHeightInPoints((short) 10);

                HSSFCellStyle headerStyle = hwb.createCellStyle();
                headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
                headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                headerStyle.setFont(headerFont);

                HSSFCellStyle dataStyle = hwb.createCellStyle();
                dataStyle.setFont(dataFont);
                dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
                dataStyle.setBorderRight((short) 1);
                dataStyle.setBorderLeft((short) 1);

                HSSFCellStyle dataStyleCenter = hwb.createCellStyle();
                dataStyleCenter.setFont(dataFont);
                dataStyleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
                dataStyleCenter.setBorderRight((short) 1);
                dataStyleCenter.setBorderLeft((short) 1);

                HSSFCellStyle headstyle = hwb.createCellStyle();
                headstyle.setFont(headfont);
                headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);

                HSSFRow instituteHeadRow = sheet.createRow((short) 0);
                HSSFCell instituteHeadCell = instituteHeadRow.createCell((short) 0);
                sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 9));
                instituteHeadCell.setCellValue(ims.getInstitutename().toString());
                instituteHeadCell.setCellStyle(headerStyle);

                HSSFRow subHeading = sheet.createRow((short) 1);
                HSSFCell subHeadingCell = subHeading.createCell((short) 0);
                sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 9));
                String headersubstring = "";
                headersubstring += (ims.getAddress1() == null) ? "" : ims.getAddress1();
                headersubstring += ",";
                headersubstring += (ims.getAddress2() == null) ? "" : ims.getAddress2();
                headersubstring += ",";
                headersubstring += (ims.getAddress3() == null) ? "" : ims.getAddress3();
                subHeadingCell.setCellValue(headersubstring);
                subHeadingCell.setCellStyle(headerStyle);

                HSSFRow addressHeading = sheet.createRow((short) 2);
                HSSFCell addressHeadingCell = addressHeading.createCell((short) 0);
                sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 9));
                String addresssubstring = "";
                addresssubstring += (ims.getCity() == null) ? "" : ims.getCity();
                addresssubstring += ",";
                addresssubstring += (ims.getState() == null) ? "" : ims.getState();
                addresssubstring += ",";
                addresssubstring += (ims.getPin() == null) ? "" : ims.getPin();
                addressHeadingCell.setCellValue(addresssubstring);
                addressHeadingCell.setCellStyle(headerStyle);

                HSSFRow reporttitle = sheet.createRow((short) 4);
                HSSFCell reporttitleHeadingCell = reporttitle.createCell((short) 0);
                sheet.addMergedRegion(new Region(4, (short) 0, 4, (short) 9));
                String reportsubstring = "Branch Wise Fail Student Subject With Fail Grade ";
                reporttitleHeadingCell.setCellValue(reportsubstring);
                reporttitleHeadingCell.setCellStyle(headerStyle);

                HSSFCell cell = null;
                HSSFCell headercell = null;
                StringBuffer headerList = new StringBuffer();
                StringBuffer headerSubjectList = new StringBuffer();
                headerList.append("SL.NO");
                headerList.append(",Enrollment no");
                for (int i = 0; i < sublist.size(); i++) {
                    headerList.append("," + (sublist.get(i)[0].toString()));
                }
                for (int t = 0; t < sublist.size(); t++) {
                    headerSubjectList.append("," + (sublist.get(t)[0].toString()));
                }
                String headerSubject[] = headerSubjectList.toString().split(",");
                String header[] = headerList.toString().split(",");
                StringBuffer styheaderList = new StringBuffer();
                styheaderList.append(" ");
                styheaderList.append(",");
                StringBuffer subheaderList = new StringBuffer();
                subheaderList.append(" ");
                subheaderList.append(",");
                for (int i = 0; i < sublist.size(); i++) {
                    styheaderList.append("," + (sublist.get(i)[2].toString()));
                }
                String styheader[] = styheaderList.toString().split(",");

                for (int i = 0; i < sublist.size(); i++) {
                    subheaderList.append("," + (sublist.get(i)[1].toString()));
                }
                String Subheader[] = subheaderList.toString().split(",");
                int index = 10;
                int index1 = 9;
                int snoindex = 1;

                HSSFRow row = null;

                int cellindexvalue[] = null;
                HSSFRichTextString cellvalue = null;

                HSSFRow header_rowsty = sheet.createRow((short) 7);
                for (int m = 0; m < styheader.length; m++) {
                    headercell = header_rowsty.createCell((short) m);
                    headercell.setCellValue(new HSSFRichTextString(styheader[m]));
                    headercell.setCellStyle(headerStyle);

                }

                HSSFRow header_row = sheet.createRow((short) 8);

                for (int s = 0; s < header.length; s++) {
                    headercell = header_row.createCell((short) s);
                    headercell.setCellValue(new HSSFRichTextString(header[s].toString().trim()));
                    headercell.setCellStyle(headerStyle);
                }
                HSSFRow header_row1 = sheet.createRow((short) 9);
                for (int ii = 0; ii < Subheader.length; ii++) {
                    headercell = header_row1.createCell((short) ii);
                    headercell.setCellValue(new HSSFRichTextString(Subheader[ii]));
                    headercell.setCellStyle(headerStyle);
                }

                String subjectcode = "";
                if (list != null && !list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        int col = 0;
                        Object apdata[] = (Object[]) list.get(i);
                        row = sheet.createRow((short) index);
                        row.createCell((short) 0).setCellValue(snoindex + "".trim());
                        for (int k = 0; k < apdata.length; k++) {
                            if (k < 1) {
                                row.createCell((short) (++col)).setCellValue(new HSSFRichTextString(apdata[k] != null ? apdata[k].toString() : ""));

                            } else if (k == 3) {
                                String[] subjectrow = apdata[k].toString().split("@");
                                String[] subcoderow = apdata[1].toString().split("@");
                                for (int w = 0; w < sublist.size(); w++) {
                                    for (int y = 0; y < subcoderow.length; y++) {
                                        if (subcoderow[y].equals(sublist.get(w)[0].toString())) {
                                            if (w < subjectrow.length) {
                                                row.createCell((short) (++col)).setCellValue(new HSSFRichTextString(subjectrow[w] != null ? subjectrow[w].toString() : "-"));
                                            } else {
                                                row.createCell((short) (++col)).setCellValue(new HSSFRichTextString("-"));
                                            }
                                        } else {
                                            row.createCell((short) (++col)).setCellValue(new HSSFRichTextString("-"));

                                        }
                                    }
                                }
                            }

                        }
                        snoindex++;
                        index++;
                    }
                }
                hwb.write(out);
            }
            // hwb.write(out);
        } catch (Exception e) {
            System.out.println("Error : " + e);
            e.printStackTrace();
        }
        try {
            hwb.write(out);
        } catch (Exception e) {
            System.out.println("Error Occurred In : " + e);
        } finally {
            hwb = null;
        }
    }
}
