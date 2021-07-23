package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.SubjectWiseStudentListReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jilit.irp.context.AppContext;
import javax.servlet.ServletOutputStream;
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
 * @author ankit.kumar
 */
@Service
public class SubjectWiseStudentListReportService implements SubjectWiseStudentListReportIService {

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

    public DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = (DAOFactory) AppContext.getApplicationContext().getBean("DAOFactory");
        }
        return daoFactory;
    }

    @Override
    public void getRegistraionCode(Model model, HttpServletRequest req) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<RegistrationMaster> list = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().getRegsitrationCode(instituteid);
            model.addAttribute("reg_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getDepartmentCode(HttpServletRequest req) {
        List<DepartmentMaster> list = new ArrayList<DepartmentMaster>();
        String reg_arr[] = req.getParameter("reg_id").split(",");
        try {
            list = (List<DepartmentMaster>) daoFactory.getDepartmentMasterDAO().getAllRegWiseDepartmentCode(reg_arr[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getEmployeeCode(HttpServletRequest req) {
        List list = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String dep_arr[] = req.getParameter("dept_id").split(",");
        try {
            list = (List) daoFactory.getV_StaffDAO().gettingAllDepartmentWiseEmployeeCode(dep_arr[0], instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllSubjectWiseStudentList(HttpServletRequest req) {
        List list = new ArrayList();
        String reg_arr[] = req.getParameter("reg_id").split(",");
        String emp_desc[] = req.getParameter("emp_id").split(",");
        String inst = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getAllSubjectWiseStudentList(inst, reg_arr[0], emp_desc[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String inst_id = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> list = new ArrayList<Object[]>();
            ArrayList al = new ArrayList();
            list = daoFactory.getInstituteMasterDAO().getAllInstituteCode(inst_id);
            String ins_name = list.get(0)[1].toString();
            String reg_desc[] = request.getParameter("reg_id").split(",");
            String dep_desc[] = request.getParameter("dept_id").split(",");
            String emp_desc[] = request.getParameter("emp_id").split(",");
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + request.getParameter("excelfilename").toString() + ".xls");
            ArrayList excelHeaderDetail = new ArrayList();
            al.add(inst_id);//0
            al.add(reg_desc[0]);//1
            al.add(emp_desc[0]);//2
            if (request.getParameter("sub_id") != null && !request.getParameter("sub_id").equals("undefined") && !request.getParameter("sub_id").equals("")) {
                String sub_arr[] = request.getParameter("sub_id").split("~@~");
                al.add(sub_arr[0]);//3
                al.add(sub_arr[1]);//4
            } else {
                al.add("All");
                al.add("All");
            }
            excelHeaderDetail.add(reg_desc[1]);
            excelHeaderDetail.add(dep_desc[1]);
            excelHeaderDetail.add(emp_desc[1]+ "/" +emp_desc[2]);
            excelHeaderDetail.add(ins_name);
            downloadApplicationMasterDataExcel(response.getOutputStream(), al, excelHeaderDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadApplicationMasterDataExcel(ServletOutputStream out, ArrayList searchData, ArrayList excelHeaderDetail) {
        getDaoFactory();
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet(searchData.get(2).toString());
        HSSFCellStyle style = hwb.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFFont font = hwb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setBorderRight((short) 1);
        HSSFFont headfont = hwb.createFont();
        headfont.setFontHeightInPoints((short) 15);
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle headstyle = hwb.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        HSSFRow instituteHeadRow = sheet.createRow((short) 0);
        HSSFCell instituteHeadCell = instituteHeadRow.createCell((short) 0);
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 9));
        instituteHeadCell.setCellValue(excelHeaderDetail.get(3).toString());
        instituteHeadCell.setCellStyle(headstyle);

        HSSFRow firstSubHeading = sheet.createRow((short) 1);

        HSSFCell regCodeHeadingCell = firstSubHeading.createCell((short) 3);
        regCodeHeadingCell.setCellValue("Registration Desc");
        HSSFCell regCodeValueCell = firstSubHeading.createCell((short) 4);
        regCodeValueCell.setCellValue(excelHeaderDetail.get(0).toString());

        HSSFCell depCodeHeadingCell = firstSubHeading.createCell((short) 6);
        depCodeHeadingCell.setCellValue("Department Name");
        HSSFCell depCodeValueCell = firstSubHeading.createCell((short) 7);
        depCodeValueCell.setCellValue(excelHeaderDetail.get(1).toString());

        HSSFRow secondSubHeading = sheet.createRow((short) 2);
        HSSFCell empCodeHeadingCell = secondSubHeading.createCell((short) 3);
        empCodeHeadingCell.setCellValue("Employee Name");
        HSSFCell empCodeValueCell = secondSubHeading.createCell((short) 4);
        empCodeValueCell.setCellValue(excelHeaderDetail.get(2).toString());

        HSSFCell cell = null;
        String[] header = {"S.No.",
            " Registration.No. ",//1
            " Name ",//2
            " STY No ",//3
            " Program Code ",//4
            " Section Code ",//5
            " Subsection Code ",//6
            " Subject Code ",//7
            " Subject Description ",//8
            " Subject Component Description "//9
        };//10
        HSSFRow header_row = sheet.createRow((short) 4);
        int index = 5;
        int snoindex = 1;
        HSSFRow row = null;
        try {
            for (int i = 0; i < header.length; i++) {
                cell = header_row.createCell((short) i);
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(header[i]));
                sheet.autoSizeColumn((short) i);
            }
            boolean flag = true;
            List list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getAllSubjectWiseStudentExcelList(searchData);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Object apdata[] = (Object[]) list.get(i);
                    if (flag) {
                        row = sheet.createRow((short) index);
                        row.createCell((short) 0).setCellValue(snoindex);//S.No.
                        for (int k = 0; k < apdata.length; k++) {
                            row.createCell((short) (k + 1)).setCellValue(new HSSFRichTextString(apdata[k] != null ? apdata[k].toString() : ""));
                            if (i <= 20) {
                                sheet.autoSizeColumn((short) ((short) k + 1));//
                            }
                        }
                        snoindex++;
                        index++;
                    }
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
