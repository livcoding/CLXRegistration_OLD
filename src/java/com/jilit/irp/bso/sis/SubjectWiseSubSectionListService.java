/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.bso.xlsexport.ExcelExport;
import com.jilit.irp.Report.ReportManager;
import static com.jilit.irp.Report.ReportManager.HTML;
import static com.jilit.irp.Report.ReportManager.PDF;
import static com.jilit.irp.Report.ReportManager.RTF;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SubjectWiseSubSectionListIService;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import org.springframework.stereotype.Service;
import com.jilit.irp.util.JIRPSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ankit.kumar
 */
@Service
public class SubjectWiseSubSectionListService extends ReportManager implements SubjectWiseSubSectionListIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void getSemesterCode(Model model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<RegistrationMaster> list = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().getRegistrationCode_1(instituteid);
            model.addAttribute("reg_code", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getDepCode(HttpServletRequest req) {
        String reg_id = req.getParameter("reg_id");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = null;
        try {
            list = (List) daoFactory.getDepartmentMasterDAO().getAllFSTDepartmentCode(instituteid, reg_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public List getSubCode(HttpServletRequest req) {
        String reg_id = req.getParameter("reg_id");
        String dep_id = req.getParameter("dep_id");
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = null;
        try {
            list = (List) daoFactory.getSubjectMasterDAO().getSub_Code_Fst(instituteid, reg_id, dep_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String exportto = request.getParameter("exportto") == null ? "" : request.getParameter("exportto").toString();
            String registrationid = request.getParameter("registrationid") == null ? "" : request.getParameter("registrationid").toString();
            String registrationcode = request.getParameter("registrationcode") == null ? "" : request.getParameter("registrationcode").toString();
            String subjectid = request.getParameter("subjectid") == null ? "" : request.getParameter("subjectid").toString();
            //   String subjectid = "SOAUSUBJ1312A0000151";
            String departmentid = request.getParameter("departmentid") == null ? "" : request.getParameter("departmentid").toString();
            String subjectcode = request.getParameter("subjectcode") == null ? "" : request.getParameter("subjectcode").toString();
            //   String subjectcode = "EC439T";
            String subjectdesc = request.getParameter("subjectdesc") == null ? "" : request.getParameter("subjectdesc").toString();
            //  String subjectdesc = "Microwave Communication System";
            String orderby = request.getParameter("orderby") == null ? "" : request.getParameter("orderby").toString();
            String sortedby = request.getParameter("sortedby") == null ? "" : request.getParameter("sortedby").toString();
            String level = "one";
            String departmentcode1 = request.getParameter("departmentcode") == null ? "" : request.getParameter("departmentcode").toString();

            String departmentdesc = request.getParameter("departmentdesc") == null ? "" : request.getParameter("departmentdesc").toString();
            String path = null;
            String str = "";
            HashMap parameters = null;
            List data = new ArrayList();
            List<Object[]> list = null;

            if (level.equals("one")) {
                list = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSub_Wise_SubSec_Report(instituteid, registrationid, departmentid, subjectid);

                for (int i = 0; i < list.size(); i++) {
                    Map addldata = new HashMap();
                    addldata.put("enrollmentno", list.get(i)[0]);
                    addldata.put("name", list.get(i)[1]);
                    byte styNumberObj = (byte) list.get(i)[2];
                    int styNumber = (int) styNumberObj;
                    byte StyNumber = (byte) ((styNumber % 2) == 0 ? (styNumber / 2) : ((styNumber / 2) + 1));
                    str = (String) ((int) StyNumber == 1 ? "I Year" : (int) StyNumber == 2 ? "II Year" : (int) StyNumber == 3 ? "III Year" : (int) StyNumber == 4 ? "IV Year" : (int) StyNumber == 5 ? "V Year" : "Not Registered Yet");
                    addldata.put("academicyear", str);
                    addldata.put("programcode", list.get(i)[3]);
                    addldata.put("sectioncode", list.get(i)[4] == null ? "" : list.get(i)[4]);
                    addldata.put("subsectioncode", list.get(i)[5] == null ? "" : list.get(i)[5]);
                    addldata.put("subjectcode", list.get(i)[6] == null ? "" : list.get(i)[6]);
                    addldata.put("subjectdesc", list.get(i)[7] == null ? "" : list.get(i)[7]);
                    addldata.put("departmentcode", departmentcode1);
                    addldata.put("department", departmentdesc);
                    data.add(addldata);
                }
                path = request.getRealPath("/jrxml/SubjectWiseStudentListScript12.jrxml");
                parameters = new HashMap();
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                if (ims != null) {
                    parameters.put("institutename", (ims.getInstitutename() == null) ? "" : ims.getInstitutename());
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState() == null ? "" : ims.getState());
                    parameters.put("city", ims.getCity() == null ? "" : ims.getCity());
                    parameters.put("pin", ims.getPin() == null ? "" : ims.getPin());
                    parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                    parameters.put("subjectcode", subjectcode == null ? "" : subjectcode);
                    parameters.put("subjectdesc", subjectdesc == null ? "" : subjectdesc);
                    parameters.put("registrationcode", registrationcode == null ? "" : registrationcode);
                    parameters.put("departmentcode", departmentcode1 == null ? "" : departmentcode1);
                } else {
                    parameters.put("institutename", "");
                    parameters.put("address1", "");
                    parameters.put("address2", "");
                    parameters.put("address3", "");
                    parameters.put("state", "");
                    parameters.put("city", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                    parameters.put("subjectcode", "");
                    parameters.put("subjectdesc", "");
                    parameters.put("registrationcode", "");
                    parameters.put("departmentcode", "");
                }
            }
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "SubjectWiseStudentListService");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "SubjectWiseStudentListService");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "SubjectWiseStudentListService");
            } else if (exportto.contains("E")) {
                File file = new File("C:/CampusLynxExcelFiles");
                boolean exists = file.exists();
                if (!exists) {
                    try {
                        String folderName = "C:/CampusLynxExcelFiles";
                        boolean success = (new File(folderName)).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String fileName2 = null;
                ExcelExport test = null;
                List columnHeader = null;
                String departmentsubject = "";
                Map asObj = null;
                List<Object[]> columnData = null;
                //columnHeader = new ArrayList(Arrays.asList("S.No.", "Enrollment No.", "Name","Program","Year","Batch(Sub-Batch)"));
                columnHeader = new ArrayList(Arrays.asList("S.No.", "Enrollment No.", "Name", "Year", "Batch"));
                columnData = new ArrayList<Object[]>();
                if (data != null) {
                    int srNo = 1;
                    for (int i = 0; i < data.size(); i++) {
                        asObj = (Map) data.get(i);
                        String academicyear = asObj.get("academicyear") != null ? asObj.get("academicyear").toString() : "";
                        String subsectioncode = asObj.get("subsectioncode") != null ? asObj.get("subsectioncode").toString() : "";
                        String programcode = asObj.get("programcode") != null ? asObj.get("programcode").toString() : "";
                        String enrollmentno = asObj.get("enrollmentno") != null ? asObj.get("enrollmentno").toString() : "";
                        String sectioncode = asObj.get("sectioncode") != null ? asObj.get("sectioncode").toString() : "";
                        String name = asObj.get("name") != null ? asObj.get("name").toString() : "";
                        String departmentcode = asObj.get("departmentcode") != null ? asObj.get("departmentcode").toString() : "";
                        String department = asObj.get("department") != null ? asObj.get("department").toString() : "";
                        String subjectcodenew = asObj.get("subjectcode") != null ? asObj.get("subjectcode").toString() : "";
                        String subjectdescnew = asObj.get("subjectdesc") != null ? asObj.get("subjectdesc").toString() : "";
                        if (departmentsubject.equals("")) {
                            test = new ExcelExport("SubjectWiseStudentListReport");
                            int mtc = 13;
                            test.setMainTitle("", mtc, 20);
                            test.setAddress(parameters.get("address1").toString() + "," + parameters.get("address2").toString() + "," + parameters.get("address3").toString() + "\n" + parameters.get("city").toString() + "," + parameters.get("state").toString() + "-" + parameters.get("pin").toString(), mtc, 30);
                            Date d = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                            // test.setSubTitle("Subject Wise Student List  For - "+registrationcode+"                                        Run Date: "+sdf.format(d)+"\n"+"Department Code: "+departmentcode+"                                                  Department: "+department+"\n"+"Subject Code: "+subjectcodenew+"                                                  Subject Name: "+subjectdescnew , mtc, 50);
                            test.setSubTitle("Subject Wise Student List  For - " + registrationcode + "                                        \n" + "Department Code: " + departmentcode + "                                                  Department: " + department + "\n" + "Subject Code: " + subjectcodenew + "                                                  Subject Name: " + subjectdescnew, mtc, 50);
                            //String id=departmentcode+subjectcodenew;
                            String id = subjectcodenew;
                            //fileName2="C:/CampusLynxExcelFiles/SubjectwiseStudentList"+id+".xls";
                            fileName2 = "C:/CampusLynxExcelFiles/" + id + ".xls";
                            test.setOutputFile(fileName2);
                        }
                        departmentsubject = departmentcode + subjectcodenew;
                        if (i > 0) {
                            Map asObj2 = (Map) data.get(i - 1);
                            String departmentcode2 = asObj2.get("departmentcode") != null ? asObj2.get("departmentcode").toString() : "";
                            String subjectcode2 = asObj2.get("subjectcode") != null ? asObj2.get("subjectcode").toString() : "";
                            if (departmentsubject.equals(departmentcode2 + subjectcode2)) {
                                //columnData.add(new Object[]{srNo++,enrollmentno, name, programcode, academicyear,sectioncode+"("+subsectioncode+")"});
                                columnData.add(new Object[]{srNo++, enrollmentno, name, academicyear, sectioncode});
                            } else {
                                test.setData(columnHeader, columnData);
                                test.doWrite();
                                columnData = new ArrayList<Object[]>();
                                test = new ExcelExport("SubjectWiseStudentListReport");
                                int mtc = 13;
                                test.setMainTitle("", mtc, 20);
                                test.setAddress(parameters.get("address1").toString() + parameters.get("address2").toString() + parameters.get("address3").toString() + "\n" + parameters.get("city").toString() + parameters.get("state").toString() + "-" + parameters.get("pin").toString(), mtc, 30);
                                Date d = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                                test.setSubTitle("Subject Wise Student List  For - " + registrationcode + "                                              Run Date: " + sdf.format(d) + "\n" + "Department Code: " + departmentcode + "                                                        Department: " + department + "\n" + "Subject Code: " + subjectcodenew + "                                                        Subject Name: " + subjectdescnew, mtc, 50);
                                //String id=departmentcode+subjectcodenew;
                                String id = subjectcodenew;
                                //fileName2="C:/CampusLynxExcelFiles/SubjectwiseStudentList"+id+".xls";
                                fileName2 = "C:/CampusLynxExcelFiles/" + id + ".xls";
                                test.setOutputFile(fileName2);
                                srNo = 1;
                                columnData.add(new Object[]{srNo++, enrollmentno, name, academicyear, sectioncode});
                            }
                        } else {
                            columnData.add(new Object[]{srNo++, enrollmentno, name, academicyear, sectioncode});
                        }
                    }
                    test.setData(columnHeader, columnData);
                    test.doWrite();
                }

                data = null;
                path = request.getRealPath("/jrxml/SubjectWiseStudentListScript123.jrxml");
                renderReport(PDF, path, data, response, parameters, "SubjectWiseStudentListService");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
