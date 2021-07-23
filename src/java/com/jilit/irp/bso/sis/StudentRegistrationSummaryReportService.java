/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.RegistrationSummaryReportIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.util.JIRPSession;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class StudentRegistrationSummaryReportService extends ReportManager implements RegistrationSummaryReportIservice, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    private ServletContext context;

    @Autowired
    JIRPSession jirpsession;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> reg = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.addAttribute("registration", reg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getAcademicYear(HttpServletRequest request) {
        String registrationid = request.getParameter("registrationCode").split("~@~")[0];
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getStudentRegistrationDAO().getAcadYear(instituteid, registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String exportto = request.getParameter("exportto");
            String level = request.getParameter("level");
            String registrationid = "";
            String registrationcode = "";
            String acadYear = "";
            String pendingStatus = "";
            String compStatus = "";
//            Date regCurrentDate = null;
            String reportfor = "";
            if (level.equals("one")) {
                String[] regid = request.getParameter("registrationid").split("~@~");
                registrationid = regid[0];
                registrationcode = regid[1];
            } else if (level.equals("two")) {
                registrationid = request.getParameter("registrationid");
                registrationcode = request.getParameter("registrationcode");
                reportfor = request.getParameter("parameter");
            } else if (level.equals("three")) {
                String[] regid = request.getParameter("registrationid").split("~@~");
                registrationid = regid[0];
                registrationcode = regid[1];
                acadYear = request.getParameter("acadYear");
                pendingStatus = request.getParameter("pendingStatus");
                compStatus = request.getParameter("compStatus");
            } else if (level.equals("four")) {
                String[] regid = request.getParameter("registrationid").split("~@~");
                registrationid = regid[0];
                registrationcode = regid[1];
                acadYear = request.getParameter("acadYear");
                pendingStatus = request.getParameter("pendingStatus");
                compStatus = request.getParameter("compStatus");
//                regCurrentDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("regCurrentDate"));
//                String dateason = request.getParameter("regCurrentDate");
            }
            String programid = request.getParameter("programid");
            String branchid = request.getParameter("branchid");
            String parameter = request.getParameter("parameter");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String url = request.getRequestURL().toString();
            String programcode = "";
            String branchcode = "";
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map map = null;
            if (level.equals("four")) {
                getStudentRegistrationDateNewBaseList(instituteid, registrationcode, acadYear, compStatus, pendingStatus, response);
            } else {
                map = getStudentRegistrationDateBaseList(instituteid, registrationid, acadYear, compStatus, pendingStatus);
                data = (List) map.get("list");
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                parameters.put("state", (ims.getState() == null) ? "" : ims.getState());
                parameters.put("city", (ims.getCity() == null) ? "" : ims.getCity());
                parameters.put("pin", (ims.getPin() == null) ? "" : ims.getPin());
                parameters.put("instituteid", (instituteid == null) ? "" : instituteid);
                parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                parameters.put("image", context.getRealPath(ims.getLogofilename()));
                parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                parameters.put("url", url);

                if (level.equals("one")) {
                    data = (List<StudentMaster>) daoFactory.getStudentMasterDAO().getStudentRegistrationSummaryList(registrationid);
                    path = request.getRealPath("/jrxml/StudentRegistrationSummaryFinalTotal.jrxml");
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("exportto", exportto);
                    parameters.put("registrationid", registrationid);
                }
                if (level.equals("two")) {
                    Byte stynumber = Byte.decode(request.getParameter("stynumber"));
                    data = (List) daoFactory.getStudentMasterDAO().getRegistrationAllowedList(programid, branchid, registrationid, stynumber, parameter);
                    path = request.getRealPath("/jrxml/StudentRegistrationAllowedStudent.jrxml");
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("programcode", programcode);
                    parameters.put("branchcode", branchcode);
                    if (reportfor.equals("totalstudent")) {
                        reportfor = "List of Total Students";
                    } else if (reportfor.equals("regallow")) {
                        reportfor = "List of Registration Allowed Students";
                    } else if (reportfor.equals("regnotallow")) {
                        reportfor = "List of Registration Not Allowed Students";
                    } else {
                        reportfor = "List of Registration Confirmed Students";
                    }
                    parameters.put("reportfor", reportfor);
                    if (!data.isEmpty()) {
                        for (int i = 0; i < 1; i++) {
                            HashMap object = (HashMap) data.get(i);
                            programcode = object.get("programcode").toString();
                            branchcode = object.get("branchcode").toString();
                        }
                    }
                }

                if (level.equals("three")) {
                    path = request.getRealPath("/jrxml/StudentRegistrationDateBaseReport.jrxml");
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("acadYear", acadYear);
                    pendingStatus = request.getParameter("pendingStatus");
                    compStatus = request.getParameter("compStatus");
                    parameters.put("exportto", exportto);
                    parameters.put("registrationid", registrationid);
                }
                if (exportto.contains("P")) {
                    if (level.equals("three")) {
                        renderReport(PDF, path, data, response, parameters, "StudentRegistrationDateBaseReport");
                    } else {
                        renderReport(PDF, path, data, response, parameters, "StudentRegistrationSummaryReport");
                    }
                } else if (exportto.contains("H")) {
                    renderReport(HTML, path, data, response, parameters, "StudentRegistrationSummaryReport");
                } else if (exportto.contains("W")) {
                    renderReport(RTF, path, data, response, parameters, "StudentRegistrationSummaryReport");
                } else if (exportto.contains("E")) {
                    renderReport(EXCEL, path, data, response, parameters, "StudentRegistrationSummaryReport");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map getStudentRegistrationDateBaseList(String instituteid, String registrationid, String acadYear, String compStatus, String pendingStatus) {
        List returnList = new ArrayList();
        try {
            List<Object[]> data = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentRegistrationDateBaseList(instituteid, registrationid, acadYear, compStatus, pendingStatus);
            for (int i = 0; i < data.size(); i++) {
                Map map = new HashMap();
                Object[] obj = (Object[]) data.get(i);
                map.put("acadYear", obj[0] == null ? "" : obj[0].toString());
                map.put("enrollmentno", obj[1] == null ? "" : obj[1].toString());
                map.put("name", obj[2] == null ? "" : obj[2].toString());
                map.put("program", obj[3] == null ? "" : obj[3].toString());
                map.put("branch", obj[4] == null ? "" : obj[4].toString());
                map.put("styNo", obj[5] == null ? "" : obj[5].toString());

                map.put("status", obj[6] == null ? "" : obj[6].toString());
                map.put("planRegDate", obj[7] == null ? null : obj[7].toString());
                map.put("activeRegDate", obj[8] == null ? null : obj[8].toString());

                returnList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map finalmap = new HashMap();
        finalmap.put("list", returnList);
        return finalmap;
    }

    public void getStudentRegistrationDateNewBaseList(String instituteid, String regcode, String acadYear, String compStatus, String pendingStatus, HttpServletResponse response) {
        try {
            List<Object[]> probranchlist = (List<Object[]>) daoFactory.getStudentMasterDAO().getProgramBranchCode(regcode);
            List<String> quotalist = (List<String>) daoFactory.getStudentMasterDAO().getStudentQuotaCode();
            List<Object[]> data = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentRegistrationDateNewBaseList(regcode, acadYear, compStatus, pendingStatus);
            List<String[]> finalList = new ArrayList<String[]>();
            String str[] = new String[quotalist.size() + 3];
            str[0] = "Institute Code";
            str[1] = "Program-Branch Code";
            for (int i = 0; i < quotalist.size(); i++) {
                str[(i + 2)] = quotalist.get(i).toString();
            }
            str[quotalist.size() + 2] = "Total";
            finalList.add(str);
            Map m = new HashMap();
            int clno = 0;
            for (Object[] obj1 : probranchlist) {
                clno = 0;
                int n = 1;
                String str1[] = new String[quotalist.size() + 3];
                str1[0] = obj1[6].toString();
                String pid = obj1[0].toString();
                String bid = obj1[1].toString();
                String iid = obj1[7].toString();
                str1[n] = obj1[2].toString() + "-" + obj1[3].toString();
                int total = 0;
                List<Object[]> l = new ArrayList();
                for (String obj : quotalist) {
                    clno++;
                    String qcode = obj.toString();
                    n++;
                    l = (List<Object[]>) data.stream().filter(v -> v[0].toString().equals(qcode) && v[1].toString().equals(pid) && v[2].toString().equals(bid) && v[4].toString().equals(iid)).collect(Collectors.toList());
                    if (l.size() > 0) {
                        str1[n] = l.get(0)[3].toString();
                        total += Integer.parseInt(l.get(0)[3].toString());
                        if (m.containsKey("key" + clno)) {
                            m.put("key" + clno, Integer.parseInt(m.get("key" + clno).toString()) + Integer.parseInt(l.get(0)[3].toString()));
                        } else {
                            m.put("key" + clno, l.get(0)[3].toString());
                        }
                    } else {
                        str1[n] = "0";
                        total += 0;
                    }
                }
                str1[quotalist.size() + 2] = total + "";
                finalList.add(str1);
            }
            int ttotal = 0;
            int gtotal = 0;
            String str3[] = new String[quotalist.size() + 2];
            str3[0] = "Total";
            for (int x = 1; x <= clno; x++) {
                str3[x] = m.get("key" + x) != null ? m.get("key" + x).toString() : "0";
                ttotal += m.get("key" + x) != null ? Integer.parseInt(m.get("key" + x).toString()) : 0;
            }
            str3[quotalist.size() + 1] = ttotal + "";
            finalList.add(str3);
            StringBuilder sb = new StringBuilder();
            ByteArrayOutputStream bstream = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=StudentWiseSubjectChoiceReport.xls");
            ServletOutputStream out = response.getOutputStream();
            Document document = null;
            PdfWriter pdfWriter = null;
            HTMLWorker htmlWorker = null;
            InstituteMaster im = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);

            sb.append("<html>");
            sb.append("<head><title> </title>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append("<table style='border:2px black solid;'>");
            sb.append("<tr><th colspan='" + (quotalist.size() + 3) + "'>" + im.getInstitutename() + "</th></tr>");
            sb.append("<tr><th colspan='" + (quotalist.size() + 3) + "' ><b>" + im.getAddress1() + "," + im.getAddress2() + "," + (im.getAddress3() == null ? "" : im.getAddress3()) + "</b></th></tr>");
            sb.append("<tr><th colspan='" + (quotalist.size() + 3) + "' ><b>" + im.getCity() + "," + im.getState() + "-" + im.getPin() + "</b></th></tr>");
            sb.append("<tr><th colspan='" + (quotalist.size() + 3) + "' ><b>Branch Wise Student Registred Count</b></th></tr>");
            sb.append("</table><table border cellpadding=5>");
            for (int ii = 0; ii < finalList.size(); ii++) {
                String[] row = finalList.get(ii);
                sb.append("<tr>");
                for (int colum = 0; colum < row.length; colum++) {
                    if (ii == (finalList.size() - 1)) {
                        if (colum == 0) {
                            sb.append("<th colspan='2'>" + row[colum] + "</th>");
                        } else {
                            sb.append("<td>" + row[colum] + "</td>");
                        }
                    } else {
                        if (ii == 0) {
                            sb.append("<th>" + row[colum] + "</th>");
                        } else {
                            sb.append("<td>" + row[colum] + "</td>");
                        }
                    }
                }
            }
            sb.append("</tr>");
            sb.append("</body>");
            sb.append("</html>");
            bstream = new ByteArrayOutputStream();
            document = new Document(PageSize.A4.rotate()) {
            };
            out.println(sb.toString());
            bstream.writeTo(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
