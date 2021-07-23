package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectWiseStudentCountReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.StyDesc;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.util.JIRPSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ankur.goyal
 */
@Service
public class SubjectWiseStudentCountReportService extends ReportManager implements SubjectWiseStudentCountReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * @description This method is used for getCombo of semester from STYDESC
     * table.
     * @param model
     */
    @Override
    public void getComboSubjectWiseStudentCount(ModelMap model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List styList = (List<StyDesc>) daoFactory.getUtilDAO().findSimpleData("getAllStyDesc", new String[]{instituteid});
            model.put("styType", styList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description This method is used for getCombo of Program data from
     * Program master table.
     * @param request
     * @return list
     */
    @Override
    public List getProgramWithBranchCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programtypeid = request.getParameter("programType");
            list = (List<Object[]>) daoFactory.getProgramMasterDAO().getAllProgramWithBranchCode(instituteid, programtypeid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStyNumber(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regid = request.getParameter("regid");
            list = (List<Object[]>) daoFactory.getStyDescDAO().getStyNumber1(instituteid, regid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @description This method is used for generate report.
     * @param request
     * @param response
     */
    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        String programid = "";
        String programcode = "";
        String branchid = "";
        String branchcode = "";
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String companyid = jirpsession.getJsessionInfo().getSelectedcompanyid();
            String username = jirpsession.getJsessionInfo().getUsername();
            String registrationid = request.getParameter("registrationid");
            String proid = request.getParameter("program");
            String[] pgid = proid.split(",");
            for (int x = 0; x < pgid.length; x++) {
                if (x == 0) {
                    programid += "'" + pgid[x] + "'";
                } else {
                    programid += ",'" + pgid[x] + "'";
                }
            }
            String branid = request.getParameter("branches");
            String[] brid = branid.split(",");
            for (int x = 0; x < brid.length; x++) {
                if (x == 0) {
                    branchid += "'" + brid[x] + "'";
                } else {
                    branchid += ",'" + brid[x] + "'";
                }
            }
            String stynumber = request.getParameter("stynumber");
            String orderby = request.getParameter("orderby");
            String showReport = request.getParameter("showReport");
            String exportto = request.getParameter("exportTo");
            String path = null;
            HashMap parameters = null;
            List data = null;
            parameters = new HashMap();
            Map aSObject = null;
            aSObject = getSubjectWiseStudentCountData(instituteid, companyid, stynumber, programid, branchid, registrationid, "", showReport);
            data = (List) aSObject.get("list");
            if (exportto.equals("P")) {
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                parameters = new HashMap();
                if (ims != null || data != null) {
                    parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState() == null ? "" : ims.getState());
                    parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                    parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                    if (showReport.equals("pre")) {
                        parameters.put("reportfor", "Based On Pre-Registration");
                    } else {
                        parameters.put("reportfor", "After Time Table/Teaching Load");
                    }
                    parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                    parameters.put("orderby", "Order By: " + (orderby == null ? "" : orderby));
                    parameters.put("programcode", "Program Code: " + (programcode == null ? "" : programcode));
                } else {
                    parameters.put("institutename", "");
                    parameters.put("sessioncode", "");
                    parameters.put("address1", "");
                    parameters.put("address2", "");
                    parameters.put("address3", "");
                    parameters.put("state", "");
                    parameters.put("city", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                    parameters.put("programcode", "");
                    if (showReport.equals("pre")) {
                        parameters.put("reportfor", "Based On Pre-Registration");
                    } else {
                        parameters.put("reportfor", "After Time Table/Teaching Load");
                    }
                }
                path = request.getRealPath("/jrxml/SubjectWiseStudentCountReport.jrxml");
                if (exportto.contains("P")) {
                    renderReport(PDF, path, data, response, parameters, "SubjectWiseStudentCount Report");
                } else if (exportto.contains("H")) {
                    renderReport(HTML, path, data, response, parameters, "SubjectWiseStudentCount Report");
                } else if (exportto.contains("W")) {
                    renderReport(RTF, path, data, response, parameters, " SubjectWiseStudentCountReport");
                } else if (exportto.contains("E")) {
                    renderReport(EXCEL, path, data, response, parameters, "SubjectWiseStudentCount Report");
                }
            } else {

                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("SubjectWiseStudentCountReport");
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
                String headername = "";
                if (showReport.equals("pre")) {
                    instituteHeadCell.setCellValue("Subject Wise Student Count Report / Based On Pre-Registration");
                    headername = "SLNO.@Basket Code@Course Code@Course Name@Semeste@Course Creditsr@Name of Barnch(es)in which course is offered@Number of Students Registered(for Study)@Number Of Students Registered(for Examination)@Total number of Students registered in the course ";

                } else {
                    instituteHeadCell.setCellValue("Subject Wise Student Count Report / After Time Table/Teaching Load");
                    headername = "SLNO.@Basket Code@Course Code@Course Name@Semestes@Course Credit@Name of Barnch(es)in which course is offered@Number of Students Registered(for Study)@Number Of Students Registered(for Examination)@Total number of Students registered in the course@Faculty Instructor/Coordinator ";
                }

                instituteHeadCell.setCellStyle(style);
                HSSFCell cell = null;
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=SubjectWiseStudentCountReport.xls");
                ServletOutputStream out = response.getOutputStream();
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
                        sheet.setColumnWidth((short) 0, (short) 1600);

                    }
                    List exdata = (List) aSObject.get("excellist");
                    if (exdata != null && !exdata.isEmpty()) {
                        for (int i = 0; i < exdata.size(); i++) {
                            Object apdata[] = (Object[]) exdata.get(i);
                            row = sheet.createRow((short) index);
                            row.createCell((short) 0).setCellValue(snoindex);//S.No.
                            StringBuilder s = new StringBuilder();
                            for (int k = 0; k < apdata.length; k++) {
                                if (k == 8) {
                                    s.append(Integer.parseInt(apdata[k - 2].toString()) + Integer.parseInt(apdata[k - 1].toString()) + "~,");
                                    if (apdata[k] != null) {
                                        String[] faculty = apdata[k].toString().split(",");
                                        faculty = Arrays.stream(faculty).distinct().toArray(String[]::new);
                                        StringBuilder f = new StringBuilder();
                                        for (int outter = 0; outter < faculty.length; outter++) {
                                            f.append(faculty[outter].toString());
                                        }
                                        s.append(f + "~,");
                                    } else {
                                        s.append(" ~,");
                                    }
                                } else if (k == 5) {
                                    String[] branches = apdata[k].toString().split(",");
                                    branches = Arrays.stream(branches).distinct().toArray(String[]::new);
                                    StringBuilder f = new StringBuilder();
                                    for (int outter = 0; outter < branches.length; outter++) {
                                        f.append(branches[outter].toString());
                                    }
                                    s.append(f + "~,");
                                } else {
                                    s.append((apdata[k] != null ? apdata[k].toString() : "") + "~,");
                                }
                            }
                            String finaldata[] = s.toString().split("~,");
                            int length = 0;
                            if (showReport.equals("pre")) {
                                length = finaldata.length - 1;
                            } else {
                                length = finaldata.length;
                            }
                            for (int l = 0; l < length; l++) {
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
                    e.printStackTrace();
                }
                try {
                    hwb.write(out);
                } catch (Exception e) {
                    System.out.println("Error Occurred In : " + e);
                    e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map getSubjectWiseStudentCountData(String instituteid, String companyid, String styno, String programid, String branchid, String registrationid, String programcode, String showReport) {
        List retList = new ArrayList();
        List<Object[]> objList = new ArrayList();
        Map object = null;
        int count = 0;
        try {
            String stynumber = "";
            String[] sty = styno.split(",");
            for (int xx = 0; xx < sty.length; xx++) {
                if (xx == 0) {
                    stynumber += "'" + sty[xx] + "'";
                } else {
                    stynumber += ",'" + sty[xx] + "'";
                }
            }
            List<StyType> stytype = daoFactory.getStyTypeDAO().getStyType(instituteid, "REG");
            if (showReport.equals("pre")) {
                objList = (List<Object[]>) daoFactory.getdBDependentDAO().getSubjectWiseStudentCountDataPre(instituteid, companyid, stynumber, programid, branchid, registrationid, stytype.get(0).getId().getStytypeid());
            } else {
                objList = (List<Object[]>) daoFactory.getdBDependentDAO().getSubjectWiseStudentCountData(instituteid, companyid, stynumber, programid, branchid, registrationid, stytype.get(0).getId().getStytypeid());
            }
            if (objList != null && objList.size() > 0) {
                for (int i = 0; i < objList.size(); i++) {
                    Object[] o = (Object[]) objList.get(i);
                    object = new HashMap();
                    if (Integer.parseInt(o[6].toString()) != 0) {
                        object.put("slno", "" + (++count));
                        object.put("subjectcode", o[1] == null ? "" : o[1].toString());
                        object.put("subjectdesc", o[2] == null ? "" : o[2].toString());
                        object.put("credits", o[4] == null ? "" : o[4].toString());
                        object.put("stynumber", o[3] == null ? "" : o[3].toString());
                        object.put("studymode", o[6] == null ? "" : o[6].toString());
                        object.put("exammode", o[7] == null ? "" : o[7].toString());
                        object.put("TotalRegStudents", String.valueOf(Integer.parseInt(object.get("studymode").toString()) + Integer.parseInt(object.get("exammode").toString())));
                        retList.add(object);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map aSObject = new HashMap();
        aSObject.put("list", retList);
        aSObject.put("excellist", objList);
        return aSObject;
    }

}
