package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationNoDuesStatusReportIservice;
import com.jilit.irp.util.JIRPSession;
import org.springframework.ui.Model;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.persistence.dao.DAOFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class RegistrationNoDuesStatusReportService extends ReportManager implements RegistrationNoDuesStatusReportIservice, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.addAttribute("data", list);
            List<Object[]> activity = (List<Object[]>) daoFactory.getStudentRegistrationDAO().getActivityValues(instituteid);
            model.addAttribute("activity", activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getAcademicYear(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getAcademicYearDAO().getAcademicYearCheckPST(registrationid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getProgram(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getProgramMasterDAO().getProgramForSubjectFst(instituteid, registrationid, acadYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyNo(HttpServletRequest request) {
        List list = new ArrayList();
        String registrationid = request.getParameter("regCode");
        String acadYear = request.getParameter("acadYear");
        String program = request.getParameter("program");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getStudentRegistration_infoDAO().getStyNoFOrNoDuesReport(instituteid, registrationid, acadYear, program);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        List activityid = new ArrayList();
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + request.getParameter("excelfilename") + ".xls");
            String[] activityids = request.getParameter("activityType").split(",");
            activityid = Arrays.asList(activityids);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            downloadLogMantananceReport(response.getOutputStream(), request.getParameter("regCode"), request.getParameter("acadYear"), request.getParameter("program"), new Byte(request.getParameter("semester")), activityid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void downloadLogMantananceReport(ServletOutputStream out, String regCode, String acadYear, String program, byte semester, List activityType, String instituteid) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("RegistrationNoDuesReport");
        HSSFCellStyle style = hwb.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFFont font = hwb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        style.setBorderRight((short) 1);
        HSSFFont headfont = hwb.createFont();
        headfont.setFontHeightInPoints((short) 10);
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle headstyle = hwb.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        List<Object[]> list = null;
        List<Object[]> list1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        HSSFRow instituteHeadRow = sheet.createRow((short) 0);
        HSSFCell instituteHeadCell = instituteHeadRow.createCell((short) 0);
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 15));
        instituteHeadCell.setCellValue("Registration No Dues Status Report");
        instituteHeadCell.setCellStyle(style);
        HSSFCell cell = null;
        String headername = "SLNO@Enrollment No@Name@Program Code@Branch Code@STY Number@ACTIVITY Name@Remarks@Processed@Approval Authorities@Allow for Registration@Approval By@Approval Date@Approval Remarks";
        String[] header = headername.split("@");
        HSSFRow header_row = sheet.createRow((short) 2);
        int index = 3;
        int snoindex = 1;
        HSSFRow row = null;
        try {
            for (int i = 0; i < header.length; i++) {
                cell = header_row.createCell((short) i);
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(header[i]));
                sheet.autoSizeColumn((short) i);
            }
            list = (List<Object[]>) daoFactory.getStudentRegistrationDAO().getRegistrationNoDuesStatusReportData(instituteid, regCode, acadYear, program, semester, activityType);
            list1 = (List<Object[]>) daoFactory.getStudentRegistrationDAO().getApprovalAuthorities(instituteid, activityType);

            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Object apdata[] = (Object[]) list.get(i);
                    row = sheet.createRow((short) index);
                    row.createCell((short) 0).setCellValue(snoindex);//S.No.
                    StringBuilder s = new StringBuilder();

                    for (int k = 0; k < apdata.length; k++) {

                        if (k == 7) {
                            if (apdata[k] != null) {
                                if (apdata[k].toString().equals("N")) {
                                    s.append("No" + "~,");
                                } else {
                                    s.append("Yes" + "~,");
                                }
                            } else {
                                s.append(" " + "~,");
                            }
                        } else if (k == 8) {
                            int count = 0;
                            String authorities = "";
                            if (list1 != null && !list1.isEmpty()) {
                                for (int j = 0; j < list1.size(); j++) {
                                    Object apdata1[] = (Object[]) list1.get(j);
                                    if (apdata1[0].toString().equals(apdata[k].toString())) {
                                        if (count == 0) {
                                            authorities += apdata1[1];
                                        } else {
                                            authorities += "," + apdata1[1];
                                        }
                                        count++;
                                    }
                                }
                                s.append(authorities + "~,");
                            } else {
                                s.append(" " + "~,");
                            }

                        } else if (k == 9) {
                            if (apdata[k] != null) {
                                if (apdata[k].toString().equals("N")) {
                                    s.append("No" + "~,");
                                } else {
                                    s.append("Yes" + "~,");
                                }
                            } else {
                                s.append(" " + "~,");
                            }
                        } else {
                            s.append((apdata[k] != null ? apdata[k].toString() : "") + "~,");
                        }
                    }
                    String finaldata[] = s.toString().split("~,");
                    for (int l = 0; l < finaldata.length; l++) {
                        row.createCell((short) (l + 1)).setCellValue(new HSSFRichTextString(finaldata[l] != null ? finaldata[l].toString() : ""));
                        if (i <= 20) {
                            sheet.autoSizeColumn((short) ((short) l + 1));
                        }
                    }
                    snoindex++;
                    index++;
                }
            }
            hwb.write(out);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        try {
            hwb.write(out);
        } catch (Exception e) {
            System.out.println("Error Occurred In : " + e);
        } finally {
            hwb = null;
            sheet = null;
            font = null;
            cell = null;
            style = null;
            index = 0;
            row = null;
        }
    }

}
