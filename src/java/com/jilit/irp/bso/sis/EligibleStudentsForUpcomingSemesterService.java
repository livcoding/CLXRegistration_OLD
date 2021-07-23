package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.iservice.EligibleStudentsForUpcomingSemesterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.util.JIRPSession;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author deepak.gupta
 */
@Service
public class EligibleStudentsForUpcomingSemesterService extends ReportManager implements EligibleStudentsForUpcomingSemesterIService {

    @Autowired
    JIRPSession jirpsession;

    @Autowired
    DAOFactory daoFactory;

    private ServletContext context;
    private HttpServletRequest request;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void getRegAndProgramList(Model model) {
        List<RegistrationMaster> regList = null;
        List<ProgramMaster> progList = null;
        List acadYearList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            progList = (List<ProgramMaster>) daoFactory.getProgramMasterDAO().findAll(instituteid);
            acadYearList = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearReg(instituteid);
            model.addAttribute("regList", regList);
            model.addAttribute("progList", progList);
            model.addAttribute("acadYearList", acadYearList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getBranch(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String programId[] = req.getParameter("programId").split("~@~");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> ccList = daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programId[0]);
            if (ccList != null && ccList.size() > 0) {
                rtnlist.add(ccList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] branchStr = {};
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid[] = request.getParameter("regId").split("~@~");
            String programid[] = request.getParameter("programId").split("~@~");
            String branchid[] = request.getParameter("branch").split("~@~");
            String check = request.getParameter("nooffailsubject");
            String docType = request.getParameter("radioValue");
            String academicyearList = request.getParameter("acadYear");
            List<String> branchcode = (List<String>) daoFactory.getBranchMasterDAO().getBranchCode1(instituteid, programid[0]);
            branchStr = branchcode.toArray(new String[branchcode.size()]);
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            response.reset();
            if (request.getParameter("radioValue").toString().contains("P")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=Eligible_Students_Upcoming_Semester.pdf");
            } else {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=Eligible_Students_Upcoming_Semester.xls");
            }
            getEligibleStudentsForUpcomingSemesterReport(response.getOutputStream(), instituteid, request.getRealPath(""), branchid[0], docType, registrationid[0], programid[0], programid[1], branchStr, ims.getWatermarkfilename(), check, academicyearList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEligibleStudentsForUpcomingSemesterReport(ServletOutputStream out, String instituteid, String path, String branchid, String docType, String registrationid, String programid, String programcode, String[] branchStr, String watermarkfilename, String check, String academicyearList) {

        StringBuilder html = new StringBuilder();

        try {
            out.print(html.toString());
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4.rotate(), 15, 20, 7, 15);
            InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
            List<Object[]> list = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentDataEligibleForUpcomingSemester(instituteid, registrationid, programid, branchid, check, academicyearList);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, bstream);
            document.open();
            document.addAuthor("Campus Lynx");
            document.addCreator("JILIT");
            document.addSubject("Eligible Students For Upcoming Semester Report");
            document.addCreationDate();
            document.addTitle("CLX Report");
            document.addKeywords("");
            HTMLWorker htmlWorker = new HTMLWorker(document);
            Map map = null;
            List<String> enrollList = null;
            List branchEnrollList = new ArrayList();
            List<Map> semBranchList = new ArrayList<Map>();
            String styNum = "";
            String branchCode = "";
            int count = 1;
            int maxCount = 0;
            int totStud = 0;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (!styNum.equals(list.get(i)[3].toString())) {
                        if (i != 0) {
                            branchEnrollList.add(enrollList);
                            maxCount = count > maxCount ? count : maxCount;
                            map = new HashMap();
                            map.put("semNum", styNum);
                            map.put("semData", branchEnrollList);
                            map.put("maxCount", maxCount);
                            semBranchList.add(map);
                        }
                        branchEnrollList = new ArrayList();
                        enrollList = new ArrayList<String>();
                        branchCode = "";
                        count = 1;
                        maxCount = 0;
                        styNum = list.get(i)[3].toString();
                        enrollList.add(list.get(i)[2].toString());
                        enrollList.add(list.get(i)[1].toString());
                        count++;
                        branchCode = list.get(i)[2].toString();
                    } else {
                        if (!branchCode.equals(list.get(i)[2].toString())) {
                            if (i != 0) {
                                branchEnrollList.add(enrollList);
                            }
                            maxCount = count > maxCount ? count : maxCount;
                            branchCode = list.get(i)[2].toString();
                            enrollList = new ArrayList<String>();
                            count = 1;
                            enrollList.add(list.get(i)[2].toString());
                            enrollList.add(list.get(i)[1].toString());
                            count++;
                        } else {
                            if (list.get(i)[1].toString() != "") {
                                enrollList.add(list.get(i)[1].toString());
                                count++;
                            }
                        }
                    }
                    if (i == list.size() - 1) {
                        branchEnrollList.add(enrollList);
                        maxCount = count > maxCount ? count : maxCount;
                        map = new HashMap();
                        map.put("semNum", styNum);
                        map.put("semData", branchEnrollList);
                        map.put("maxCount", maxCount);
                        semBranchList.add(map);
                    }
                }
            }
            if (semBranchList != null && semBranchList.size() > 0) {
                html.append("<html>");
                html.append("<head><title></title></head>");
                html.append("<body style='font-family:Calibri;font-weight:normal;font-size:8px'>");
                html.append("<table frame='box' style='text-align:right;padding-top:10;'>");
                html.append("<tr><td colspan='" + (branchStr.length - 4) + "' >");
                html.append("<table>");
                html.append("<tr><td style='font-size:9px;text-align:left'><b>" + ims.getInstitutename() + "</b></td></tr>");
                html.append("<tr><td style='font-size:9px;text-align:left'><b>Eligible Students For Upcoming Semester Report</b></td></tr>");
                html.append("</table>");
                html.append("</td>");
                html.append("<td colspan='5' style='text-align:right'><img src='" + path + ims.getLogofilename() + "'/>");
                html.append("</td></tr>");
                Map obj = null;
                if (semBranchList != null && semBranchList.size() > 0) {
                    for (int j = 0; j < semBranchList.size(); j++) {
                        obj = semBranchList.get(j);
                        branchEnrollList = (List) obj.get("semData");
                        html.append("<tr style='font-size:9px;text-align:center'><td colspan='" + (branchStr.length + 1) + "'><b><h2 style='text-align:center' >" + programcode + " Semester - " + obj.get("semNum") + "</h2></b></td></tr>");
                        html.append("<tr><td colspan='" + (branchStr.length + 1) + "'>");
                        html.append("<table cellpadding='0' frame='box' border='1' style='text-align:center;padding-top:10'>");
                        html.append("<tr><td colspan='1' >");
                        html.append("<table style='text-align:center'>");
                        html.append("<tr><td colspan='1' >SR.No.</td></tr>");
                        for (int k = 0; k < Integer.parseInt(obj.get("maxCount").toString()) - 1; k++) {
                            html.append("<tr><td colspan='1' >" + (k + 1) + "</td></tr>");
                        }
                        html.append("<tr><td colspan='1' >&nbsp;</td></tr>");
                        html.append("</table>");
                        html.append("</td>");
                        int l = 0;
                        int k = 0;
                        for (l = 0; l < branchStr.length; l++) {
                            if (k < branchEnrollList.size()) {
                                enrollList = (List<String>) branchEnrollList.get(k);
                            }
                            if (!enrollList.get(0).equals(branchStr[l])) {
                                html.append("<td colspan='1'>");
                                html.append("<table style='text-align:center'>");
                                html.append("<tr><td colspan='1' ><b>" + branchStr[l] + "</b></td></tr>");
                                for (int kk = 0; kk < (Integer.parseInt(obj.get("maxCount").toString()) - 1); kk++){
                                    html.append("<tr><td colspan='1' >&nbsp;</td></tr>");
                                }
                                html.append("<tr><td colspan='1' >0</td></tr>");
                                html.append("</table>");
                                html.append("</td>");
                            } else {
                                html.append("<td colspan='1'>");
                                html.append("<table style='text-align:center' border=1>");
                                for (int kk = 0; kk < enrollList.size(); kk++) {
                                    if (kk == 0) {
                                        html.append("<tr><td colspan='1' ><b>" + enrollList.get(kk) + "</b></td></tr>");
                                    } else {
                                        html.append("<tr><td colspan='1' >" + enrollList.get(kk) + "</td></tr>");
                                    }
                                }
                                if (Integer.parseInt(obj.get("maxCount").toString()) > enrollList.size()) {
                                    for (int kk = 0; kk < (Integer.parseInt(obj.get("maxCount").toString()) - enrollList.size()); kk++) {
                                        html.append("<tr><td colspan='1' >&nbsp;</td></tr>");
                                    }
                                }
                                html.append("<tr><td colspan='1' >" + (enrollList.size() - 1) + "</td></tr>");
                                totStud += enrollList.size() - 1;
                                html.append("</table>");
                                html.append("</td>");
                                k++;
                            }
                        }
                        html.append("</tr>");
                        html.append("<tr><td colspan='" + (branchStr.length + 1) + "' >" + totStud + "</td></tr>");
                        html.append("</table>");
                        totStud = 0;
                        html.append("</td></tr>");
                    }
                    html.append("</table>");
                } else {
                    html.append("</table>");
                }
                html.append("</body>");
                html.append("</html>");
            } else {
                html.append("<h1 style='text-align:center'>No Data Found</h1>");
                html.append("</body>");
                html.append("</html>");
            }
            if (!docType.contains("P")) {
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
                //Image img = Image.getInstance(context.getRealPath(watermarkfilename));
                //img.setAbsolutePosition(300, 150);
                //img.scaleAbsolute(250, 250);
                while (i < n) {
                    // Watermark under the existing page
                    under = stamp.getUnderContent(i);
                    //under.addImage(img);
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
            html = null;
            System.gc();
        }
    }

}
