/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationInstructionUploadIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.RegistrationInstruction;
import com.jilit.irp.persistence.dto.RegistrationInstructionId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.servlet.ServletOutputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import com.jilit.irp.Report.RequestManager;
import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class RegistrationInstructionUpload extends RequestManager implements ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;
    private ServletContext context;

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void getRequestReponse(HttpServletRequest request, HttpServletResponse response, String data) {
        try {
            ServletOutputStream out = response.getOutputStream();
            saveRegistrationInstructionUpload(request, response, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveRegistrationInstructionUpload(HttpServletRequest request, HttpServletResponse response, String data) throws IOException {
        RegistrationInstruction dto = new RegistrationInstruction();
        RegistrationInstructionId id = new RegistrationInstructionId();
        String[] d = data.split("~@~");
        String registrationid = d[0];
        String instructionType = d[1];
        String instituteid = d[2];
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        int flag = 0;
        StringBuilder sb = new StringBuilder();
        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                // Parse the request
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    String fileName1 = "";
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        fileName1 = item.getFieldName();
                    }
                    if (!item.isFormField()) {
                        File file = new File(item.getName());
                        String fileName = item.getName();
                        if (fileName != "") {
                            byte[] byteArr = item.get();
                            id.setInstituteid(instituteid);
                            id.setRegistrationid(registrationid);
                            dto = (RegistrationInstruction) daoFactory.getRegistrationInstructionUploadDAO().findByPrimaryKey(id);
                            if (dto == null) {
                                dto = new RegistrationInstruction();
                                dto.setId(id);
                            }
                            if (instructionType.equals("C")) {
                                dto.setCoursereginstruction(byteArr);
                            } else if (instructionType.equals("E")) {
                                dto.setElectiveinstruction(byteArr);
                            } else if (instructionType.equals("G")) {
                                dto.setGipinstruction(byteArr);
                            } else if (instructionType.equals("S")) {
                                dto.setSupplinstruction(byteArr);
                            }
                            daoFactory.getRegistrationInstructionUploadDAO().saveOrUpdate(dto);
                            sb.append(fileName + ",");
                            sb.append("Registration Instruction Successfull Upload");
                        }
                    }
                }
            } catch (FileUploadException e) {
                flag = 1;
                sb.append("not uploaded.");
                e.printStackTrace();
            }
        }
        getReport(request, response, sb.toString(), "Reg Instruction");
        return "Success";

    }

    public void getReport(HttpServletRequest request, HttpServletResponse response, String sb, String repfor) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + "uploadStatus.xls");
            downloadInstructionUploadReport(response.getOutputStream(), sb, "instruction");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void downloadInstructionUploadReport(ServletOutputStream out, String sb, String repfor) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Registration Instruction");
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
        List list = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        HSSFRow instituteHeadRow = sheet.createRow((short) 0);
        HSSFCell instituteHeadCell = instituteHeadRow.createCell((short) 0);
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 15));
        instituteHeadCell.setCellValue("Registration Instruction Upload Status");
        instituteHeadCell.setCellStyle(style);
        HSSFRow firstSubHeading = sheet.createRow((short) 2);
//        HSSFCell messCodeHeadingCell = firstSubHeading.createCell((short) 1);
//        messCodeHeadingCell.setCellValue("Date From :-");
//        messCodeHeadingCell.setCellStyle(style);
//        sheet.addMergedRegion(new Region(2, (short) 1, 2, (short) 2));
//        HSSFCell messCodeValueCell = firstSubHeading.createCell((short) 3);
//        messCodeValueCell.setCellValue(sdf.format(new Date()));
//        // String reporttypename = "";
//        HSSFCell reportTypeHeadingCell = firstSubHeading.createCell((short) 5);
//        reportTypeHeadingCell.setCellValue("Date To :-");
//        reportTypeHeadingCell.setCellStyle(style);
//        sheet.addMergedRegion(new Region(2, (short) 5, 2, (short) 6));
//        HSSFCell reportTypeValueCell = firstSubHeading.createCell((short) 7);
//        reportTypeValueCell.setCellValue(sdf.format(new Date()));
        HSSFCell dateHeadingCell = firstSubHeading.createCell((short) 9);
        dateHeadingCell.setCellValue("Report as on");
        dateHeadingCell.setCellStyle(style);
        sheet.addMergedRegion(new Region(2, (short) 9, 2, (short) 10));
        HSSFCell dateValueCell = firstSubHeading.createCell((short) 11);
        dateValueCell.setCellValue(sdf.format(new Date()));
        HSSFCell cell = null;
        String headername = "";
        headername = "Sl.No.@Registration Instruction Name@Status";
        String[] header = headername.split("@");
        HSSFRow header_row = sheet.createRow((short) 4);
        int index = 5;
        int snoindex = 1;
        HSSFRow row = null;
        try {
            for (int ii = 0; ii < header.length; ii++) {
                cell = header_row.createCell((short) ii);
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(header[ii]));
                sheet.autoSizeColumn((short) ii);
            }
            String str[] = sb.toString().split("@@");
            for (int i = 0; i < str.length; i++) {
                String finaldata[] = str[i].toString().split(",");
                for (int l = 0; l < finaldata.length; l++) {
                    row = sheet.createRow((short) index);
                    row.createCell((short) 0).setCellValue(snoindex);//S.No.
                    row.createCell((short) (l + 1)).setCellValue(new HSSFRichTextString(finaldata[l] != null ? finaldata[l].toString() : ""));
                }
                if (i <= 20) {
                    sheet.autoSizeColumn((short) ((short) i + 1));
                }
                snoindex++;
                index++;
            }
            hwb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e);
        }
        try {
            hwb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
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
