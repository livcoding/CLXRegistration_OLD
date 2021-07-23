package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.ReportManager;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SummerRegistrationReportIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.PhdSelfcourseRegistration;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author ankur.goyal
 */
@Service
public class SummerRegistrationReportService extends ReportManager implements SummerRegistrationReportIService, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    /**
     * @description This method is used for get Enrollment LOV from
     * StudentMaster table.
     * @param request
     * @return list
     */
    @Override
    public List getEnrollmentNumber(HttpServletRequest request) {
        List list = null;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regId = request.getParameter("semesterCode");
            String redId_1[] = regId.split("~@~");
            String registrationid = redId_1[0];
            list = (List) daoFactory.getStudentSubjectChoiceMasterDAO().getEnrollmentNumber(instituteId, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @description This method is used for report generate.
     * @param request
     * @param response
     */
    @Override
    public void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            BusinessService bs = new BusinessService(context, daoFactory);
            String username = jirpsession.getJsessionInfo().getUsername();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regId = request.getParameter("semesterCode");
            String redId_1[] = regId.split("~@~");
            String registrationid = redId_1[0];
            String registrationcode = redId_1[1];
            String registrationdesc = redId_1[2];
            String enroll = request.getParameter("enrollmentNo");
            String[] enroll_1 = enroll.split("~@~");
            String studentid = enroll_1[0];
            String name = enroll_1[1];
            String enrollmentno = enroll_1[2];
            String sty = enroll_1[3];
            Byte stynumber = Byte.decode(sty);
            String branchcode = enroll_1[4];
            String branchdesc = enroll_1[5];
            String programcode = enroll_1[6];
            String programdesc = enroll_1[7];
            List data = null;
            String subjectareacode1 = "";
            String subjectareacode2 = "";
            double creditsum = 0.0;

            if (studentid != null) {
                List<Object[]> subjectdetail = daoFactory.getStudentSubjectChoiceMasterDAO().getStudentSubjectChoice(studentid, instituteid, registrationid);
                data = new ArrayList();
                for (int i = 0; i < subjectdetail.size(); i++) {
                    Object[] obj = (Object[]) subjectdetail.get(i);
                    Map generalInfo1 = new HashMap();
                    generalInfo1.put("group", "1");
                    generalInfo1.put("basketdesc", obj[0]);
                    generalInfo1.put("stytypedesc", obj[1]);
                    generalInfo1.put("subjectcode", obj[2]);
                    generalInfo1.put("subjectdesc", obj[3]);
                    generalInfo1.put("choice", obj[4]);
                    generalInfo1.put("credits", obj[5]);
                    creditsum = creditsum + (double) (Double.parseDouble(obj[5].toString()));
                    data.add(generalInfo1);
                }
                List<PhdSelfcourseRegistration> phdSubjectdetail = (List<PhdSelfcourseRegistration>) daoFactory.getUtilDAO().findSimpleData("findSubjectsFromPhdSelfCourseRegistration", new Object[]{instituteid, registrationid, studentid});
                if (!phdSubjectdetail.isEmpty()) {
                    for (int i = 0; i < phdSubjectdetail.size(); i++) {
                        PhdSelfcourseRegistration phdSelfcourseRegistration = phdSubjectdetail.get(i);
                        Map generalInfo1 = new HashMap();
                        generalInfo1.put("group", "1");
                        generalInfo1.put("basketdesc", "OPEN ELECTIVE SUBJECT");
                        generalInfo1.put("stytypedesc", "Regular");
                        generalInfo1.put("subjectcode", phdSelfcourseRegistration.getSubjectcode());
                        generalInfo1.put("subjectdesc", phdSelfcourseRegistration.getSubjectdesc());
                        generalInfo1.put("choice", "1");
                        generalInfo1.put("credits", Byte.valueOf(phdSelfcourseRegistration.getCredits().toString()));
                        creditsum = creditsum + Double.parseDouble(phdSelfcourseRegistration.getCredits().toString());
                        data.add(generalInfo1);
                    }
                }
            }
            String exportto = request.getParameter("exportto");
            String level = request.getParameter("level");
            String path = null;
            HashMap parameters = null;

            if (level.equals("one")) {
                path = request.getRealPath("/jrxml/StudentSubjectChoiceReport1.jrxml");
                InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
                System.out.println("InstituteMAster::" + ims);
                parameters = new HashMap();
                if (ims != null) {
                    parameters.put("institutename", ims.getInstitutename());
                    parameters.put("enrollmentno", enrollmentno);
                    parameters.put("branchcode", branchcode);
                    parameters.put("programcode", programcode);
                    parameters.put("branchdesc", branchdesc);
                    parameters.put("programdesc", programdesc);
                    parameters.put("stynumber", stynumber);
                    parameters.put("name", name);
                    parameters.put("registrationcode", registrationcode);
                    parameters.put("address1", (ims.getAddress1() == null) ? "" : ims.getAddress1());
                    parameters.put("address2", (ims.getAddress2() == null) ? "" : ims.getAddress2());
                    parameters.put("address3", (ims.getAddress3() == null) ? "" : ims.getAddress3());
                    parameters.put("state", ims.getState());
                    parameters.put("city", ims.getCity());
                    parameters.put("pin", ims.getPin());
                    parameters.put("creditsum", new BigDecimal(creditsum));
                    parameters.put("username", bs.getPropertyValue("reportby", "campuslynx.properties") + ": " + username);
                    parameters.put("image", context.getRealPath(ims.getLogofilename()));
                    parameters.put("imageback", context.getRealPath(ims.getWatermarkfilename()));
                    parameters.put("registrationdesc", registrationdesc);
                } else {
                    parameters.put("institutename", " ");
                    parameters.put("enrollmentno", " ");
                    parameters.put("branchcode", " ");
                    parameters.put("programcode", " ");
                    parameters.put("branchdesc", " ");
                    parameters.put("programdesc", " ");
                    parameters.put("stynumber", " ");
                    parameters.put("name", " ");
                    parameters.put("registrationcode", " ");
                    parameters.put("address1", " ");
                    parameters.put("address2", " ");
                    parameters.put("address3", " ");
                    parameters.put("state", " ");
                    parameters.put("city", " ");
                    parameters.put("pin", " ");
                    parameters.put("creditsum", " ");
                    parameters.put("image", " ");
                }
            }

            if (exportto.contains("P")) {
                renderReport(PDF, path, data, response, parameters, "StudentSubjectChoiceReport");
            } else if (exportto.contains("H")) {
                renderReport(HTML, path, data, response, parameters, "StudentSubjectChoiceReport");
            } else if (exportto.contains("W")) {
                renderReport(RTF, path, data, response, parameters, "StudentSubjectChoiceReport");
            } else if (exportto.contains("E")) {
                renderReport(EXCEL, path, data, response, parameters, "StudentSubjectChoiceReport");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jirpsession.removeAttributeFromSession(request.getSession(), "data");
        }
    }
}
