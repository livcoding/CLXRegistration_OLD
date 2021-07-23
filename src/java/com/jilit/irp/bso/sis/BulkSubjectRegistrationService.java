/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.iservice.BulkSubjectRegistrationIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.Xl_RegSubjectData;
import com.jilit.irp.persistence.dto.Xl_RegSubjectDataId;
import com.jilit.irp.util.JIRPSession;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author deepak.gupta
 */
@Service
public class BulkSubjectRegistrationService extends ReportManager implements BulkSubjectRegistrationIService {

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

    List requiredColumns;
    Map columnPosition;

    @Override
    public void getRegistrationCode(Model model) {
        List<RegistrationMaster> regCodeList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            regCodeList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            model.addAttribute("regCodeList", regCodeList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BulkSubjectRegistrationService() {
        requiredColumns = new ArrayList();
        requiredColumns.add("ENROLLMENTNO");
        requiredColumns.add("SUBJECTCODE");
        requiredColumns.add("SUBJECTTYPE");
        requiredColumns.add("STYNUMBER");
        requiredColumns.add("STYTPE");
        requiredColumns.add("CREDIT");
    }

    public List checkExcelData(HttpServletRequest request, MultipartFile file) {
        List retList = new ArrayList();
        List saveObjectList = null;
        List errorObjectList = null;
        List alertList = new ArrayList();
        alertList.add("Validation_Error_List:");
        String regCode = request.getParameter("regId");
        String enroll = request.getParameter("enroll");
        try {
            byte barr[] = file.getBytes();
            List<Map> list = getExcelColumnData(barr);
            Map aso = null;
            String[] enrolBased = enroll.split(":");
            Map map1 = new HashMap();
            StringBuilder comments = null;
            map1.put("regiscode", regCode);
            map1.put("enroll", enrolBased[0].toString());
            map1.put("subBase", enrolBased[1].toString());
            if (list != null && !list.isEmpty()) {
                saveObjectList = new ArrayList();
                errorObjectList = new ArrayList();
                int n = 0;
                aso = new HashMap();
                aso.put("", "");
                aso.put("COLUMN" + "0", "");
                aso.put("COLUMN" + "1", "");
                aso.put("COLUMN" + "2", "");
                aso.put("COLUMN" + "3", "");
                aso.put("COLUMN" + "4", "");
                aso.put("COLUMN" + "5", "");
                aso.put("COMMENTS", "");
                errorObjectList.add(aso);
                for (int i = 0; i < list.size(); i++) {
                    if (i > 0) {
                        aso = new HashMap();
                        Map map = list.get(i);
                        boolean isValid = true;
                        String enrollmentno = "";
                        double temp2 = 0.0;
                        int temp = 0;
                        comments = new StringBuilder();
                        Short colPosition = null;
                        if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                            colPosition = 0;
                            if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                                aso.put("COLUMN" + colPosition + "", "");
                                comments.append("ENROLLMENTNO field is empty!");
                                if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                    aso.put("COLUMN0", enrollmentno);
                                } else {
                                    aso.put("COLUMN0", enrollmentno);
                                }
                                aso.put("COLUMN1", map.get(new Short("1")));
                                aso.put("COLUMN2", map.get(new Short("2")));
                            } else if (map.get(colPosition) instanceof Double) {
                                temp2 = (Double) map.get(colPosition);
                                temp = (int) temp2;
                                enrollmentno = String.valueOf(temp);
                                aso.put("enrollmentno", enrollmentno);
                            } else if (map.get(colPosition) instanceof String) {
                                enrollmentno = map.get(colPosition).toString();
                                aso.put("enrollmentno", enrollmentno);
                            }
                        }
                        String subjectCode = "";
                        colPosition = 1;
                        if (enrolBased[1].toString().equalsIgnoreCase("SUBJECTCODE")) {
                            if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                                aso.put("COLUMN" + colPosition + "", "");
                                comments.append("SubjectCode field for this student is empty!");
                                if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                    aso.put("COLUMN0", enrollmentno);
                                } else {
                                    aso.put("COLUMN0", enrollmentno);
                                }
                            } else if (map.get(colPosition) instanceof Double) {
                                temp2 = (Double) map.get(colPosition);
                                temp = (int) temp2;
                                subjectCode = String.valueOf(temp);
                                aso.put("subjectcode", subjectCode);
                            } else if (map.get(colPosition) instanceof String) {
                                subjectCode = map.get(colPosition).toString();
                                aso.put("subjectcode", subjectCode);
                            }
                        }

                        String subjecttypeTemp = "";
                        colPosition = 2;
                        if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                            aso.put("COLUMN" + colPosition + "", "");
                            comments.append("SubjectType field for this student is empty!");
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof Double) {
                            temp2 = (Double) map.get(colPosition);
                            temp = (int) temp2;
                            subjecttypeTemp = String.valueOf(temp);
                            aso.put("COLUMN" + "2", subjecttypeTemp);
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof String) {
                            subjecttypeTemp = (String) map.get(colPosition);
                            aso.put("subjecttype", subjecttypeTemp);
                        }

                        String styNumber = "";
                        colPosition = 3;
                        if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                            aso.put("COLUMN" + colPosition + "", "");
                            comments.append("StyNumber field for this student is empty!");
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof Double) {
                            temp2 = (Double) map.get(colPosition);
                            temp = (int) temp2;
                            styNumber = String.valueOf(temp);
                            aso.put("stynumber", styNumber);
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof String) {
                            styNumber = (String) map.get(colPosition);
                            aso.put("stynumber", styNumber);
                        }

                        String creditTemp = "";
                        colPosition = 4;
                        if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                            aso.put("COLUMN" + colPosition + "", "");
                            comments.append("Credit field for this student is empty!");
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof Double) {
                            temp2 = (Double) map.get(colPosition);
                            temp = (int) temp2;
                            creditTemp = String.valueOf(temp);
                            aso.put("credits", creditTemp);
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof String) {
                            creditTemp = (String) map.get(colPosition);
                            aso.put("credits", creditTemp);
                        }

                        String stytypeTemp = "";
                        colPosition = 5;
                        if (map.get(colPosition) == null || (map.get(colPosition).toString().equalsIgnoreCase("Type not supported."))) {
                            aso.put("COLUMN" + colPosition + "", "");
                            comments.append("StyType field for this student is empty!");
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof Double) {
                            temp2 = (Double) map.get(colPosition);
                            temp = (int) temp2;
                            stytypeTemp = String.valueOf(temp);
                            aso.put("COLUMN - " + "3", stytypeTemp);
                            if (enrolBased[0].toString().equalsIgnoreCase("ENROLLMENTNO")) {
                                aso.put("COLUMN0", enrollmentno);
                            } else {
                                aso.put("COLUMN0", enrollmentno);
                            }
                            aso.put("COLUMN1", subjectCode);
                        } else if (map.get(colPosition) instanceof String) {
                            stytypeTemp = (String) map.get(colPosition);
                            aso.put("stytype", stytypeTemp);
                        }
                        if (isValid) {
                            aso.put("registrationcode", regCode);

                            saveObjectList.add(aso);
                        } else {
                            n = n + 1;
                            aso.put("", n);
                            aso.put("COMMENTS", comments.toString());
                            errorObjectList.add(aso);
                        }
                    } else {
                    }
                }
                List saveList = new ArrayList();
                Xl_RegSubjectData xluploadDTO = null;
                Xl_RegSubjectDataId xluploadID = null;
                if (!saveObjectList.isEmpty()) {
                    String userID = jirpsession.getJsessionInfo().getUserid();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String excel_taskid = "";
                    excel_taskid = df.format(new Date()).replace(":", "").replace(" ", "").replace("/", "") + (int) (Math.random() * 900) + 100;
                    for (int a = 0; a < saveObjectList.size(); a++) {
                        xluploadDTO = new Xl_RegSubjectData();
                        xluploadID = new Xl_RegSubjectDataId();
                        xluploadID.setUserid(userID);
                        xluploadID.setXltaskid(excel_taskid);
                        xluploadID.setSlno(a + 1);
                        xluploadDTO.setId(xluploadID);
                        xluploadDTO.setDocmode("D");
                        String subjectcode = ((Map) saveObjectList.get(a)).get("subjectcode") == null ? "" : ((Map) saveObjectList.get(a)).get("subjectcode").toString().trim();
                        if (subjectcode.length() > 20) {
                            alertList.add("Subject Code length should not be greater than 20 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        }
                        String enrollmentno = ((Map) saveObjectList.get(a)).get("enrollmentno") == null ? "" : ((Map) saveObjectList.get(a)).get("enrollmentno").toString().trim();
                        if (enrollmentno.length() > 20) {
                            alertList.add("Enrollment no length should not be greater than 20 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        }
                        String regcode = ((Map) saveObjectList.get(a)).get("registrationcode") == null ? "" : ((Map) saveObjectList.get(a)).get("registrationcode").toString().trim();
                        if (regcode.length() > 20) {
                            alertList.add("Registration Code length should not be greater than 20 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        }
                        String credits = ((Map) saveObjectList.get(a)).get("credits") == null ? "0" : ((Map) saveObjectList.get(a)).get("credits").toString().trim();
                        if (credits.length() > 3) {
                            alertList.add("Credits length should not be greater than 3 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        } else {
                            if (!isNumeric(credits)) {
                                alertList.add("Please enter Numeric data only in Credits (<b>Excel Record no.: " + (a + 2) + "</b>)");
                            }
                        }
                        String styno = ((Map) saveObjectList.get(a)).get("stynumber") == null ? "" : ((Map) saveObjectList.get(a)).get("stynumber").toString().trim();
                        if (styno.length() > 3) {
                            alertList.add("Stynumber length should not be greater than 3 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        } else {
                            if (!isNumeric(styno)) {
                                alertList.add("Please enter Numeric data only in Stynumber (<b>Excel Record no.: " + (a + 2) + "</b>)");
                            }
                        }
                        String stytype = ((Map) saveObjectList.get(a)).get("stytype") == null ? "" : ((Map) saveObjectList.get(a)).get("stytype").toString().trim();
                        String subjecttype = ((Map) saveObjectList.get(a)).get("subjecttype") == null ? "" : ((Map) saveObjectList.get(a)).get("subjecttype").toString().trim();
                        if (subjecttype.length() > 1) {
                            alertList.add("Subject Type length should not be greater than 1 (<b>Excel Record no.: " + (a + 2) + "</b>)");
                        }
                        if (alertList.size() > 1) {
                            return alertList;
                        }
                        xluploadDTO.setSubjectcode(subjectcode);
                        xluploadDTO.setEnrollmentno(enrollmentno);
                        xluploadDTO.setRegistrationcode(regcode);
                        xluploadDTO.setCredits(Byte.parseByte(credits));
                        xluploadDTO.setStynumber(styno);
                        xluploadDTO.setSubjecttype(subjecttype);
                        xluploadDTO.setStytype(stytype);
                        saveList.add(xluploadDTO);
                    }
                    daoFactory.getRegistrationMasterDAO().saveOrUpdateObjectList(saveList);
                    retList.add(userID);
                    retList.add(excel_taskid);
                }
                retList.add(list.size() - 1);//list.size()-1 because first row is heading row
                retList.add(errorObjectList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retList.add("Error");
        }
        return retList;
    }

    public List analyzeAndSaveExcelData(HttpServletRequest request) {
        List retObj = new ArrayList();
        List retList = new ArrayList();
        String pErrorCode = "";
        String pReturnFlag = "";
        String pErrorMessage = "";
        String pSPCode = "ALL";
        List errorObjectList = null;
        List aso1 = null;
        String pUserID = request.getParameter("userid");
        String pTaskID = request.getParameter("taskid");
        String pProcessingType = "A";
        String pSPFlag = "S";
        try {
            String statusString = daoFactory.getStoredProcedureDAO().subjectRegDataUploadExcel(pUserID, pTaskID, pProcessingType, pErrorCode, pReturnFlag, pErrorMessage, pSPFlag, pSPCode);
            String[] errorDetail = statusString.split("@@");
            if (Integer.parseInt(errorDetail[0]) == -1) {
                retObj.add("No records found to save");
            } else if (Integer.parseInt(errorDetail[0]) == -2) {
                retObj.add("Wrong call of Procedure!");
            } else if (Integer.parseInt(errorDetail[0]) == -9) {
                errorObjectList = new ArrayList();
                List<Object[]> errorList = (List<Object[]>) daoFactory.getXl_RegSubjectDataDAO().find("select xlg.enrollmentno, xlg.subjectcode, xlg.subjecttype, xlg.stynumber, xlg.credits, xlg.stytype, "
                        + "  xlg.recstatusmessage from  Xl_RegSubjectData xlg where  xlg.recstatus = 'E' and xlg.id.userid='" + pUserID + "' and xlg.id.xltaskid='" + pTaskID + "' order by xlg.enrollmentno, xlg.subjectcode ");
                if (errorList != null && !errorList.isEmpty()) {
                    for (int i = 0; i < errorList.size(); i++) {
                        aso1 = new ArrayList();
                        aso1.add(errorList.get(i)[0] == null ? "" : errorList.get(i)[0].toString());
                        aso1.add(errorList.get(i)[1] == null ? "" : errorList.get(i)[1].toString());
                        aso1.add(errorList.get(i)[2] == null ? "" : errorList.get(i)[2].toString());
                        aso1.add(errorList.get(i)[3] == null ? "" : errorList.get(i)[3].toString());
                        aso1.add(errorList.get(i)[4] == null ? "" : errorList.get(i)[4].toString());
                        aso1.add(errorList.get(i)[5] == null ? "" : errorList.get(i)[5].toString());
                        aso1.add(errorList.get(i)[6] == null ? "" : errorList.get(i)[6].toString());
                        errorObjectList.add(aso1);
                    }
                }
                if (Integer.parseInt(errorDetail[1].split(",")[1]) > 0) {
                    retObj.add("Data is processed with some errors!");
                } else {
                    retObj.add("Data is processed with errors no correct records to save!");
                }
            } else if (Integer.parseInt(errorDetail[1].split(",")[2]) == 0) {
                retObj.add("Data is processed with No errors!");
            }
            String[] numberOfRecords = errorDetail[1].split(",");
            retObj.add(numberOfRecords[1]);//savedRecords
            retObj.add(numberOfRecords[2]);//errorpronRecords
            retObj.add(errorObjectList);//errorlist
            if (Integer.parseInt(numberOfRecords[1].toString()) == 0) {
                retObj.add("None of the records are elligible to be port by the system!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        retList.add(retObj);
        return retList;
    }

    public List saveAndDeleteExcelData(HttpServletRequest request) {
        List retObj = new ArrayList();
        List retList = new ArrayList();
        String pErrorCode = "";
        String pReturnFlag = "";
        String pErrorMessage = "";
        String statusString = "";
        String pUserID = request.getParameter("userid");
        String pTaskID = request.getParameter("taskid");
        String pProcessingType = "P";
        String pSPFlag = "S";
        String pSPCode = "ALL";
        try {
            statusString = daoFactory.getStoredProcedureDAO().subjectRegDataUploadExcel(pUserID, pTaskID, pProcessingType, pErrorCode, pReturnFlag, pErrorMessage, pSPFlag, pSPCode);
            if (statusString != null && !statusString.equals("null@@null@@null")) {
                String[] errorDetail = statusString.split("@@");
                if (Integer.parseInt(errorDetail[0]) == -1) {
                    retObj.add("No records found to save");
                } else if (Integer.parseInt(errorDetail[0]) == -2) {
                    retObj.add("Wrong call of Procedure!");
                } else if (Integer.parseInt(errorDetail[0]) == -9) {
                    if (Integer.parseInt(errorDetail[1].split(",")[1]) > 0) {
                        retObj.add("Records saved successfully!");
                    } else {
                        retObj.add("Data is processed with errors no correct records to save!");
                    }
                } else if (Integer.parseInt(errorDetail[0]) <= -25 && Integer.parseInt(errorDetail[0]) >= -54) {
                    retObj.add("Error Code (" + Integer.parseInt(errorDetail[0]) + ") ," + errorDetail[2] + "!");
                } else if (Integer.parseInt(errorDetail[0]) == 0 && Integer.parseInt(errorDetail[1].toString().split(",")[2]) == 0) {
                    retObj.add("Subject saved in respected tables successfully!");
                }
            } else {
                retObj.add("Subject Registration has done successfully!");
            }
            List l = saveAndDeleteExcelData1(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        retList.add(retObj);
        return retList;
    }

    public List saveAndDeleteExcelData1(HttpServletRequest request) {
        List retObj = new ArrayList();
        List retList = new ArrayList();
        String pErrorCode = "";
        String pReturnFlag = "";
        String pErrorMessage = "";
        String statusString = "";
        String pUserID = request.getParameter("userid");
        String pTaskID = request.getParameter("taskid");
        String pProcessingType = "D";
        String pSPFlag = "S";
        String pSPCode = "ALL";
        try {
            statusString = daoFactory.getStoredProcedureDAO().subjectRegDataUploadExcel(pUserID, pTaskID, pProcessingType, pErrorCode, pReturnFlag, pErrorMessage, pSPFlag, pSPCode);
            if (statusString != null && !statusString.equals("null@@null@@null")) {
                String[] errorDetail = statusString.split("@@");
                if (Integer.parseInt(errorDetail[0]) == -1) {
                    retObj.add("No records found to save");
                } else if (Integer.parseInt(errorDetail[0]) == -2) {
                    retObj.add("Wrong call of Procedure!");
                } else if (Integer.parseInt(errorDetail[0]) == -9) {
                    if (Integer.parseInt(errorDetail[1].split(",")[1]) > 0) {
                        retObj.add("" + errorDetail[1].split(",")[1] + " records saved successfully!");
                    } else {
                        retObj.add("Data is processed with errors no correct records to save!");
                    }
                } else if (Integer.parseInt(errorDetail[0]) <= -25 && Integer.parseInt(errorDetail[0]) >= -54) {
                    retObj.add("Error Code (" + Integer.parseInt(errorDetail[0]) + ") ," + errorDetail[2] + "!");
                } else if (Integer.parseInt(errorDetail[0]) == 0 && Integer.parseInt(errorDetail[1].toString().split(",")[2]) == 0) {
                    retObj.add("Subjects saved in respected tables successfully!");
                }
            } else {
                retObj.add("Data deleted from Temporary Table!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        retList.add(retObj);
        return retList;
    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename= Sample.xls");
            downloadSampleExcel(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadSampleExcel(ServletOutputStream out) throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Blank Sample XL");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setBorderRight((short) 1);
        HSSFCell cell = null;
        HSSFRow row = sheet.createRow(0);
        try {
            cell = row.createCell(new Short("0"));
            cell.setCellValue(new HSSFRichTextString("ENROLLMENT NO."));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("1"));
            cell.setCellValue(new HSSFRichTextString("SUBJECT CODE"));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("2"));
            cell.setCellValue(new HSSFRichTextString("SUBJECT TYPE"));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("3"));
            cell.setCellValue(new HSSFRichTextString("STY NUMBER"));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("4"));
            cell.setCellValue(new HSSFRichTextString("COURSE CREDIT POINT"));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("5"));
            cell.setCellValue(new HSSFRichTextString("SEMESTER TYPE"));
            cell.setCellStyle(style);
            cell = row.createCell(new Short("6"));
            cell.setCellValue(new HSSFRichTextString("SEMESTER CODE"));
            cell.setCellStyle(style);
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn((short) ((short) i));
            }
            try {
                workbook.write(out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workbook = null;
                sheet = null;
                font = null;
                cell = null;
                style = null;
                row = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Map> getExcelColumnData(byte barr[]) {
        List<Map> retList = new ArrayList<Map>();
        Map map;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(barr);
            HSSFWorkbook workBook = new HSSFWorkbook(bis);
            HSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<HSSFRow> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = rows.next();
                Iterator<HSSFCell> cells = row.cellIterator();
                map = new HashMap();
                while (cells.hasNext()) {
                    HSSFCell cell = cells.next();
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: {
                            map.put(cell.getCellNum(), cell.getNumericCellValue());
                            break;
                        }
                        case HSSFCell.CELL_TYPE_STRING: {
                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
                            map.put(cell.getCellNum(), richTextString.getString().trim());
                            break;
                        }
                        default: {
                            map.put(cell.getCellNum(), "Type not supported.");
                            break;
                        }
                    }
                }
                retList.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
