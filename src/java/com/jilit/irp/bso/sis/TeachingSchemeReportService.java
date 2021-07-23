/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.TeachingSchemeReportIservice;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.util.JIRPSession;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import java.util.List;
import java.util.Map;
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
public class TeachingSchemeReportService extends ReportManager implements TeachingSchemeReportIservice, ServletContextAware {

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
            List acadyear = (List) daoFactory.getAcademicYearDAO().getAll_TSRAcademicYear(instituteid);
            model.addAttribute("acadyear", acadyear);
            List program = (List) daoFactory.getProgramMasterDAO().findAll(instituteid);
            model.addAttribute("program", program);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List getBranch(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String[] prid = request.getParameter("program").split("~@~");
        String programid = prid[0];
        List list = null;
        try {
            list = daoFactory.getBranchMasterDAO().getAll_TSRBranchCode(programid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    public List getStyNumber(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String[] acadYear = request.getParameter("acadYear").split(",");
        List acadlist = Arrays.asList(acadYear);
        String[] prid = request.getParameter("program").split(",");
        String programid = prid[0];
        List list = null;
        try {
            list = daoFactory.getAcademicYearDAO().getStyNumberForTSReport(instituteid, programid, acadlist);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    public List getSection(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String[] strs = request.getParameter("semester").split(",");
        Byte[] bytes = new Byte[strs.length];
        for (int i = 0; i < strs.length; i++) {
            bytes[i] = Byte.parseByte(strs[i]);
        }
        List stynumberlist = Arrays.asList(bytes);
        String[] branchid = request.getParameter("branch").split(",");
        List branchlist = Arrays.asList(branchid);
        String[] acadyear = request.getParameter("acadYear").split(",");
        List acadyearlist = Arrays.asList(acadyear);
        String[] prid = request.getParameter("program").split("~@~");
        String programid = prid[0];
        List list = null;
        try {
            list = daoFactory.getSectionMasterDAO().getTSRSection(stynumberlist, branchlist, acadyearlist, programid, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String username = jirpsession.getJsessionInfo().getUsername();
            String reporttype = request.getParameter("reportType");
            String exportto = request.getParameter("export_To");
            String[] strs = request.getParameter("semester").split(",");
            Byte[] bytes = new Byte[strs.length];
            for (int i = 0; i < strs.length; i++) {
                bytes[i] = Byte.parseByte(strs[i]);
            }
            List stynumberlist = Arrays.asList(bytes);
            String[] acadyear = request.getParameter("acadYear").split(",");
            List acadyearlist = Arrays.asList(acadyear);
            String[] prid = request.getParameter("program").split("~@~");
            String programid = prid[0];
            String programname = prid[1];
            String stynumber = request.getParameter("semester");
            String[] secid112 = request.getParameter("section").split("~@~");
            String[] secid = request.getParameter("section").split(",");
            String[] secids = new String[secid.length];
            String sectioncode = "";
            for (int i = 0; i < secid.length; i++) {
                secids[i] = secid[i].split("~@~")[0].toString();
                if (i == 0) {
                    sectioncode = sectioncode;
                } else {
                    sectioncode += "," + sectioncode;
                }
            }
            List secidlist = Arrays.asList(secids);
            String level = "one";
            String path = null;
            String subjectcode = "";
            String l = "", t = "", p = "";
            HashMap parameters = null;
            List data = new ArrayList();
            Map map = null;
            Map map1 = null;
            List<Object[]> list_ltp = null;
            List<Object[]> list1 = null;
            int k = 0;
            if (level.equals("one")) {
                if (reporttype.equals("D")) {
                    String stynumberparam = "";
                    list1 = (List<Object[]>) daoFactory.getProgramMasterDAO().getAllRunningSubjectFromProgramSchemeAcad11(instituteid, programid, stynumberlist, secidlist, acadyearlist);
                    // System.err.println("Running Subject List::" + list1.size());
                    if (!list1.isEmpty()) {

                        for (int i = 0; i < list1.size(); i++) {
                            Object[] os = list1.get(i);
                            String academicyear = os[14] == null ? "0" : os[14].toString();
                            String sectionid = os[13] == null ? "0" : os[13].toString();
                            String styno = os[2] == null ? "0" : os[2].toString();
                            map = new HashMap();
                            map.put("SubjectCode", os[3] == null ? "" : os[3].toString());
                            subjectcode = os[3].toString();
                            map.put("SubjectName", os[4] == null ? "" : os[4].toString());
                            list_ltp = (List<Object[]>) daoFactory.getProgramMasterDAO().getAllRunningSubjectFromProgramSchemeAcad11_LTP(instituteid, programid, styno, sectionid, academicyear, subjectcode);
                            map1 = new HashMap();

                            for (int j = 0; j < list_ltp.size(); j++) {
                                if (os[3].toString().equals(list_ltp.get(j)[3].toString())) {
                                    if (list_ltp.get(j)[12].toString().equals("L")) {
                                        l = list_ltp.get(j)[11].toString();
                                        map1.put("l", l);
                                    } else if (list_ltp.get(j)[12].toString().equals("T")) {
                                        t = list_ltp.get(j)[11].toString();
                                        map1.put("t", t);
                                    } else if (list_ltp.get(j)[12].toString().equals("P")) {
                                        p = list_ltp.get(j)[11].toString();
                                        map1.put("p", p);
                                    }
                                }
                            }
                            map.put("l", map1.get("l") == null ? "0" : map1.get("l"));
                            map.put("t", map1.get("t") == null ? "0" : map1.get("t"));
                            map.put("p", map1.get("p") == null ? "0" : map1.get("p"));
                            map.put("Credit", os[5] == null ? Double.parseDouble("0") : Double.parseDouble(os[5].toString()));
                            String electiveid = os[10] == null ? "" : os[10].toString();
                            ElectiveMaster em = null;
                            String electivecode = "";

                            if (!electiveid.equals("")) {
                                System.out.println("gotelectiveid");
                            }
                            em = (ElectiveMaster) daoFactory.getElectiveMasterDAO().findByPrimaryKey(new ElectiveMasterId(instituteid, electiveid));

                            if (em != null) {
                                electivecode = em.getElectivecode();
                            }

                            map.put("Electivecode", electivecode.equals("") ? "--" : electivecode);
                            map.put("acadyear", os[14] == null ? "" : os[14].toString());
                            map.put("programcode", os[0] == null ? "" : os[0].toString());
                            map.put("sectioncode", os[1] == null ? "" : os[1].toString());
                            map.put("stynumber", os[2] == null ? "" : os[2].toString());
                            map.put("basketcode", os[11] == null ? "" : os[11].toString());
                            map.put("deparment", os[12] == null ? "" : os[12].toString());
                            /**
                             * *********************************************************
                             */
                            if (!stynumberparam.equals(os[2].toString())) {
                                k = 0;
                            }
                            k++;
                            map.put("slno", k + "");
                            stynumberparam = os[2] == null ? "" : os[2].toString();
                            /**
                             * *********************************************************
                             */
                            map.put("subjecttype", os[9] == null ? "" : os[9].toString());
                            data.add(map);
                        }
                    }
                    path = request.getRealPath("/jrxml/TeachingSchemeWithoutRegReport.jrxml");
                }

                parameters = new HashMap();
                parameters.put("ProgramName", programname);
                parameters.put("StyNumber", stynumber);
                parameters.put("SectionCode", sectioncode);
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
                } else {
                    parameters.put("institutename", "");
                    parameters.put("address1", "");
                    parameters.put("address2", "");
                    parameters.put("address3", "");
                    parameters.put("state", "");
                    parameters.put("city", "");
                    parameters.put("pin", "");
                    parameters.put("image", "");
                }
                path = request.getRealPath("/jrxml/TeachingSchemeWithoutRegReport.jrxml");
            }
            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "TeachingSchemeReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "TeachingSchemeReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
