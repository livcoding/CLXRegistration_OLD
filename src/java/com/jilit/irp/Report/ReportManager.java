/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author singh.jaswinder
 */
public abstract class ReportManager {

    public static final String HTML = "HTML";
    public static final String PDF = "PDF";
    public static final String EXCEL = "XLS";
    public static final String RTF = "RTF";
    public static final String VIEWER = "VIEWER";
    public static final String PDFFILE = "PDFFILE";

    public abstract void getReport(HttpServletRequest request, HttpServletResponse response);

    public void setResponseType(HttpServletResponse response, String contentType, byte[] outBytes, String headerString) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", headerString);
        response.setContentLength(outBytes.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(outBytes);
        out.flush();
        out.close();
    }

    public void renderReport(String pathName, String filename, HttpServletResponse response) {
        File file = new File(pathName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            fis.read(b);
            setResponseType(response, "application/vnd.ms-excel", b, "attachment;filename=" + filename + ".xls");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            } finally {
//                    file.delete();
                }
        }
    }

    public void renderReport(String format, String jrxmlPath, List data, HttpServletResponse response, HashMap parameters, String filename) throws Exception {
        if (format.equals(HTML)) {
            FileInputStream fis = new FileInputStream(jrxmlPath);
            JasperDesign jd = JRXmlLoader.load(fis);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(data));
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
            // exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/web/common/JRImage!renderImage.action?image=");
            exporter.exportReport();
        } else if (format.equals(PDF)) {
            FileInputStream fis = new FileInputStream(jrxmlPath);
            byte[] outBytes = Report.exportToPdf(fis, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "attachment;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(PDFFILE)) {
            FileInputStream fis = new FileInputStream(jrxmlPath);
            byte[] outBytes = Report.exportToPdf(fis, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "attachment;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(EXCEL)) {
            FileInputStream fis = new FileInputStream(jrxmlPath);
            byte[] outBytes = Report.exportToExcel(fis, parameters, data);
            setResponseType(response, "application/vnd.ms-excel", outBytes, "attachment;filename=" + filename + ".xls");
        } else if (format.equals(RTF)) {
            FileInputStream fis = new FileInputStream(jrxmlPath);
            byte[] outBytes = Report.exportToRTF(fis, parameters, data);
            setResponseType(response, "application/rtf", outBytes, "attachment;filename=" + filename + ".rtf");
        } else if (format.equals(VIEWER)) {
            /*FileInputStream fis = new FileInputStream(jrxmlPath);
            JasperDesign jd = JRXmlLoader.load(fis);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(data));
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            out.println("<html><head></head><body>");
            out.println("<APPLET CODE='ReportViewer.class'  WIDTH=600 HEIGHT=600>");
            out.println("<PARAM NAME=JP VALUE=" + jp + ">");
            out.println("</APPLET>");
            out.println("</body></html>");*/
            //MyJasperViewer.viewReport(jp, false);
        }
        System.gc();
    }

    /**
     *
     * This method is used to create excel files.
     */
    public void renderXlsReport(HttpServletRequest request, HttpServletResponse response, String path, String zipFileName, String sessionid) {
        try {
            File f = new File(path);
            String files[] = f.list();
            if (files.length > 0) {
                if (files.length == 1) {
                    FileInputStream fis = new FileInputStream(path + "\\" + files[0]);
                    byte b[] = new byte[fis.available()];
                    while (fis.read(b) != -1) {
                    }
                    setResponseType(response, "application/vnd.ms-excel", b, "attachment;filename=" + files[0]);
                    fis.close();
                    new File(path + "\\" + files[0]).delete();
                    f.delete();
                } else {
                    ZipCreationMethod(path, zipFileName, sessionid);
                    FileInputStream fis2 = new FileInputStream("C:\\CampusLynxZipExcelFiles" + sessionid + "\\" + zipFileName);
                    byte b[] = new byte[fis2.available()];
                    while (fis2.read(b) != -1) {
                    }
                    setResponseType(response, "application/zip", b, "attachment;filename=" + zipFileName);
                    fis2.close();
                    for (int i = 0; i < files.length; i++) {
                        new File(path + "\\" + files[i]).delete();
                    }
                    f.delete();
                    new File("C:\\CampusLynxZipExcelFiles" + sessionid + "\\" + zipFileName).delete();
                    new File("C:\\CampusLynxZipExcelFiles" + sessionid).delete();
                }
            } else {
                f.delete();
            }
        } catch (IOException ex) {
            System.err.println("Exception in renderXlsReport " + ex);
        }
    }

    /**
     *
     * This method is used to create zip of excel files if there is more than one excel file created.
     */
    public void ZipCreationMethod(String path, String zipFileName, String sessionid) {
        try {
            File file = new File("C:\\CampusLynxZipExcelFiles" + sessionid + "\\" + zipFileName);
            if (!(file.exists())) {
                try {
                    String folderName = "C:\\CampusLynxZipExcelFiles" + sessionid + "\\";
                    new File(folderName).mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream dest = new FileOutputStream(file);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            File f = new File(path);
            String files[] = f.list();
            FileInputStream fi = null;
            for (int i = 0; i < files.length; i++) {
//                System.out.println("Adding: " + files[i]);
                fi = new FileInputStream(path + "\\" + files[i]);
                ZipEntry entry = new ZipEntry(files[i]);
                out.putNextEntry(entry);
                byte data[] = new byte[fi.available()];
                while (fi.read(data) != -1) {
                }
                fi.close();
                out.write(data);
            }
            out.close();
            dest.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * This method is used to create grouping in excel files.
     */
    public void renderXlsReportInGroup(HttpServletRequest request, HttpServletResponse response, String fileName, int noOfGroups, int maxColumns, Map instituteData, Map mainData) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            PrintWriter out = response.getWriter();
            StringBuffer sb = new StringBuffer();
            int columnSpan = 8;
            if (maxColumns > 8) {
                columnSpan = maxColumns;
            }
            String mainTitle = instituteData.get("institutename").toString() + "<br/>" + instituteData.get("address1") + "," + instituteData.get("address2") + "," + instituteData.get("state") + "," + instituteData.get("city") + "," + instituteData.get("pin");
            String lowerdetails = instituteData.get("exameventcode") + " of " + instituteData.get("registrationcodedesc") + ", Semester: " + instituteData.get("stynumberall");
            sb.append("<table width='70%' border='1' align='center' cellpadding='0' cellspacing='0'>");
            sb.append("<tr>");
            sb.append("<td width='100%' align='center' colspan='" + columnSpan + "'><font size='5'>" + mainTitle + "</font><br><font size = '3'>" + lowerdetails + "</font></br></td>");
            sb.append("</tr>");
            for (int i = 0; i <= noOfGroups; i++) {
                Map listMap = (HashMap) mainData.get("list" + i);
                List grpList = (List) listMap.get("groupListIndex" + i + 0);
                String[] arr = (String[]) grpList.get(0);
                sb.append("<tr'>");
                sb.append("<th width='20%' align='center'>" + arr[1] + "-" + arr[0] + "</th>");
                sb.append("<th width='40%' align='center' colspan=" + (columnSpan - 2) + "></th>");
                sb.append("<th width='40%' align='center'>" + arr[2] + "</th>");
                sb.append("</tr>");
                int groupsRecord = Integer.parseInt(mainData.get("groupsRecord" + i).toString());
                List columnList = (List) mainData.get("columnList" + i);
                sb.append("<tr>");
                sb.append("<th width='5%' align='center'>&nbsp;</th>");
                for (int j = 0; j < columnList.size(); j++) {
                    sb.append("<th width='5%' align='center'>" + columnList.get(j) + "</th>");
                }
                sb.append("</tr>");
                for (int k = 0; k <= groupsRecord; k++) {
                    List groupRecordsList = (List) listMap.get("groupListIndex" + i + k);
                    if (groupRecordsList == null) {
                        continue;
                    }
                    sb.append("<tr>");
                    outerloop:
                    for (int j = 0; j < columnList.size(); j++) {
                        boolean flag = false;
                        String[] temp = null;
                        innerloop:
                        for (int p = 0; p < groupRecordsList.size(); p++) {
                            String[] array = (String[]) groupRecordsList.get(p);
                            if (array[3].equals(columnList.get(j))) {
                                flag = true;
                                temp = array;
                                break innerloop;
                            } else {
                                if (j == 0) {
                                    temp = array;
                                }
                            }
                        }
                        if (flag) {
                            if (j == 0) {
                                sb.append("<td width='5%' align='center'>" + temp[4] + "</td>");
                                sb.append("<td width='5%' align='center'>" + temp[5] + "<br>" + temp[6] + "</td>");
                            } else {
                                sb.append("<td width='5%' align='center'>" + temp[5] + "<br>" + temp[6] + "</td>");
                            }
                        } else {
                            if (j == 0) {
                                sb.append("<td width='5%' align='center'>" + temp[4] + "</td>");
                                sb.append("<td width='5%' align='center'></td>");
                            } else {
                                sb.append("<td width='5%' align='center'></td>");
                            }
                        }
                    }

                    sb.append("</tr>");
                }

            }
            sb.append("</table>");
            out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createExcelFileForExamResult(HttpServletRequest request, HttpServletResponse response, String fileName, List examtypecodes, List records, Map instituteData) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            PrintWriter out = response.getWriter();
            StringBuffer sb = new StringBuffer();
            int columnSpan = 6 + (5 * (examtypecodes.size()));
            Date now = new Date();
            String date = "Report Run Date And Time: " + DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(now);
            String mainTitle = instituteData.get("institutename").toString() + "<br/>" + instituteData.get("address1") + "," + instituteData.get("address2") + "," + instituteData.get("state") + "," + instituteData.get("city") + "," + instituteData.get("pin") + "";
            String lowerdetails = "Event Code: " + request.getParameter("eventcode") + "<br>Exam Type: " + request.getParameter("examtype") + "<br>" + date;
            sb.append("<table width='100%' border='1' align='center' cellpadding='0' cellspacing='0'>");
            sb.append("<tr>");
            sb.append("<td width='100%' align='center' colspan='" + columnSpan + "'><font size='5'>" + mainTitle + "</font><br><font size = '3'>" + lowerdetails + "</font></br></td>");
            sb.append("</tr>");
            sb.append("<th>Sl. No</th>");
            sb.append("<th>Enrollemnt No</th>");
            sb.append("<th>Student Name</th>");
            sb.append("<th>Institute-Program-Branch</th>");
            sb.append("<th>STY Number</th>");
            sb.append("<th align='center' width='30%' colspan=" + examtypecodes.size() + ">Exam Type</th>");
            sb.append("<th align='center' width='30%' colspan=" + examtypecodes.size() + ">Obtained Marks/Full Marks</th>");
            sb.append("<th align='center' width='30%' colspan=" + examtypecodes.size() + ">Passing Marks</th>");
            sb.append("<th align='center' width='30%' colspan=" + examtypecodes.size() + ">Result Status</th>");
            sb.append("<th align='center' width='30%' colspan=" + examtypecodes.size() + ">Remarks</th>");
            sb.append("<th align='center'>Final</th>");
            sb.append("<tr>");
            sb.append("<th></th>");
            sb.append("<th></th>");
            sb.append("<th></th>");
            sb.append("<th></th>");
            sb.append("<th></th>");
            for (int i = 0; i < examtypecodes.size(); i++) {
                sb.append("<th>" + examtypecodes.get(i).toString() + "</th>");
            }
            for (int i = 0; i < examtypecodes.size(); i++) {
                sb.append("<th>" + examtypecodes.get(i).toString() + "</th>");
            }
            for (int i = 0; i < examtypecodes.size(); i++) {
                sb.append("<th>" + examtypecodes.get(i).toString() + "</th>");
            }
            for (int i = 0; i < examtypecodes.size(); i++) {
                sb.append("<th>" + examtypecodes.get(i).toString() + "</th>");
            }
            for (int i = 0; i < examtypecodes.size(); i++) {
                sb.append("<th>" + examtypecodes.get(i).toString() + "</th>");
            }
            sb.append("<th></th>");
            int slno = 1;
            for (int z = 0; z < records.size(); z++) {
                Map m1 = (Map) records.get(z);
                sb.append("<tr>");
                sb.append("<td align='center'>" + (slno++) + "</td>");
                sb.append("<td align='center'>" + m1.get("enrollmentno").toString() + "</td>");
                sb.append("<td align='center'>" + m1.get("name").toString() + "</td>");
                sb.append("<td align='center'>" + m1.get("IPB").toString() + "</td>");
                sb.append("<td align='center'>" + m1.get("stynumber").toString() + "</td>");
                for (int x = 0; x < examtypecodes.size(); x++) {
                    String extypecode = examtypecodes.get(x).toString();
                    if (m1.containsKey("examtype" + extypecode)) {
                        sb.append("<td align='center'>" + m1.get("examtype" + extypecode).toString() + "</td>");
                    } else {
                        sb.append("<td align='center'>Absent</td>");
                    }
                }
                for (int x = 0; x < examtypecodes.size(); x++) {
                    String extypecode = examtypecodes.get(x).toString();
                    if (m1.containsKey("o/fmarks" + extypecode)) {
                        sb.append("<td align='center'>" + m1.get("o/fmarks" + extypecode).toString() + "</td>");
                    } else {
                        sb.append("<td align='center'>N/A</td>");
                    }
                }
                for (int x = 0; x < examtypecodes.size(); x++) {
                    String extypecode = examtypecodes.get(x).toString();
                    if (m1.containsKey("passingmarks" + extypecode)) {
                        sb.append("<td align='center'>" + m1.get("passingmarks" + extypecode).toString() + "</td>");
                    } else {
                        sb.append("<td align='center'>N/A</td>");
                    }
                }
                for (int x = 0; x < examtypecodes.size(); x++) {
                    String extypecode = examtypecodes.get(x).toString();
                    if (m1.containsKey("resultstatus" + extypecode)) {
                        sb.append("<td align='center'>" + m1.get("resultstatus" + extypecode).toString() + "</td>");
                    } else {
                        sb.append("<td align='center'>N/A</td>");
                    }
                }
                for (int x = 0; x < examtypecodes.size(); x++) {
                    String extypecode = examtypecodes.get(x).toString();
                    if (m1.containsKey("remarks" + extypecode)) {
                        sb.append("<td align='center'>" + m1.get("remarks" + extypecode).toString() + "</td>");
                    } else {
                        sb.append("<td align='center'>N/A</td>");
                    }
                }
                sb.append("<td align='center'>" + m1.get("final").toString() + "</td>");
                sb.append("</tr>");
            }
            out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void renderReportJasper(String format, String jrxmlPath, List data, HttpServletResponse response, HashMap parameters, String filename) throws Exception {
        if (format.equals(HTML)) {
            JasperPrint jp = JasperFillManager.fillReport(jrxmlPath, parameters, new JRBeanCollectionDataSource(data));
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
            // exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/web/common/JRImage!renderImage.action?image=");
            exporter.exportReport();
        } else if (format.equals(PDF)) {
            byte[] outBytes = Report.exportToPdfJasper(jrxmlPath, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "attachment;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(PDFFILE)) {
            byte[] outBytes = Report.exportToPdf(jrxmlPath, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "attachment;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(EXCEL)) {
            byte[] outBytes = Report.exportToExcelJasper(jrxmlPath, parameters, data);
            setResponseType(response, "application/vnd.ms-excel", outBytes, "attachment;filename=" + filename + ".xls");
        } else if (format.equals(RTF)) {
            byte[] outBytes = Report.exportToRTFJasper(jrxmlPath, parameters, data);
            setResponseType(response, "application/rtf", outBytes, "attachment;filename=" + filename + ".rtf");
        } else if (format.equals(VIEWER)) {
        }
        System.gc();
    }
}
