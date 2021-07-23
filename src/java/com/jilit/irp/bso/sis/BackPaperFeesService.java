/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.BackPaperFeesIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.CourseCreditWiseFee;
import com.jilit.irp.persistence.dto.CourseCreditWiseFeeId;
import com.jilit.irp.persistence.dto.SubjectWiseBackPaperFee;
import com.jilit.irp.persistence.dto.SubjectWiseBackPaperFeeId;
import com.jilit.irp.util.JIRPSession;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet Singh
 */
@Service
public class BackPaperFeesService implements BackPaperFeesIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public List getSubjectWiseGridData(HttpServletRequest req) {
        List gridData = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regid = req.getParameter("regid");
            gridData = (List) daoFactory.getSubjectWiseBackPaperFeeDAO().getGridData(instituteid, regid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridData;
    }

    public void getSemesterCode(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.addAttribute("semCode", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDepartmentData(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            String departmentId = req.getParameter("department");
            List<Object[]> list = (List<Object[]>) daoFactory.getSubjectRegistrationCriteriaDAO().getDepartmentData();
            model.addAttribute("dapartmentData", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getSubjectCode(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = null;
        try {
            String departmentid = req.getParameter("departmentid");
            list = (List) daoFactory.getSubjectWiseBackPaperFeeDAO().getSubjectCode(instituteid, departmentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void styType(ModelMap model, HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getStyTypeDAO().getStyTypeData(instituteid);
            model.addAttribute("styType", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List saveOrUpdateSubjectWise(HttpServletRequest request) {
        SubjectWiseBackPaperFee dto = new SubjectWiseBackPaperFee();
        SubjectWiseBackPaperFeeId id = new SubjectWiseBackPaperFeeId();
        List err = new ArrayList<String>();
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationId = request.getParameter("semesterCode");
            String subjectId = request.getParameter("subjectCode");
            int backPaperFee = Integer.parseInt(request.getParameter("backPaperFee"));
            id.setInstituteid(instituteId);
            id.setRegistrationid(registrationId);
            id.setSubjectid(subjectId);
            dto.setId(id);
            dto.setFeeamount(backPaperFee);
            List list = (List) daoFactory.getSubjectWiseBackPaperFeeDAO().doValidate(instituteId, registrationId, subjectId);
            daoFactory.getSubjectWiseBackPaperFeeDAO().saveOrUpdate(dto);
            if (list.size() > 0) {
                err.add("update");
            } else {
                err.add("save");
            }
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }

    public List getCreditWiseGridData(HttpServletRequest req) {
        List gridData = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String styTypeId = req.getParameter("styType");
            gridData = (List) daoFactory.getCourseCreditWiseFeeDAO().getGridData(instituteid, styTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridData;
    }

    public List saveCreditWiseFee(HttpServletRequest request) {
        CourseCreditWiseFee dto = new CourseCreditWiseFee();
        CourseCreditWiseFeeId id = new CourseCreditWiseFeeId();
        List err = new ArrayList<String>();
        int slno;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String styType = request.getParameter("styType");
            int feeAmount = Integer.parseInt(request.getParameter("feeAmount"));
            double creditfrom = Double.parseDouble(request.getParameter("creditFrom"));
            double creditUpTo = Double.parseDouble(request.getParameter("creditUpTo"));
            id.setInstituteid(instituteId);
            id.setStytypeid(styType);
            dto.setFeeamount(feeAmount);
            dto.setCreditfrom(creditfrom);
            dto.setCreditto(creditUpTo);
            List list = daoFactory.getCourseCreditWiseFeeDAO().creditSRno(instituteId);
            if (list.size() == 0) {
                slno = 0;
            } else {
                slno = Integer.parseInt(list.get(0).toString());
            }
            short sl = (short) ++slno;
            id.setSlno(sl);
            dto.setId(id);
            List validList = daoFactory.getCourseCreditWiseFeeDAO().validData(instituteId, styType, creditfrom, creditUpTo,sl,"save");
            if (validList.size() > 0) {
                err.add("Plese check the 'Credit Range'. The range is overlapping on previous data");
                return err;
            }
            daoFactory.getCourseCreditWiseFeeDAO().add(dto);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }

    @Override
    public void getCreditWiseEditData(ModelMap mm, HttpServletRequest request) {
        CourseCreditWiseFee dto = new CourseCreditWiseFee();
        CourseCreditWiseFeeId id = new CourseCreditWiseFeeId();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String pk = request.getParameter("pk");
            String ids[] = pk.split("~@~");
            id.setInstituteid(ids[0]);
            id.setStytypeid(ids[1]);
            int slno = Integer.parseInt(ids[2]);
            short sl = (short) slno;
            id.setSlno(sl);
            dto = (CourseCreditWiseFee) daoFactory.getCourseCreditWiseFeeDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List updateCreditWiseFee(HttpServletRequest request) {
        CourseCreditWiseFee dto = new CourseCreditWiseFee();
        CourseCreditWiseFeeId id = new CourseCreditWiseFeeId();
        List err = new ArrayList<String>();
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String styType = request.getParameter("stytypeid");
            int sno = Integer.parseInt(request.getParameter("slno"));
            int feeAmount = Integer.parseInt(request.getParameter("feeAmount"));
            double creditfrom = Double.parseDouble(request.getParameter("creditFrom"));
            double creditUpTo = Double.parseDouble(request.getParameter("creditUpTo"));
            short slno = (short) sno;
            id.setSlno(slno);
            id.setInstituteid(instituteId);
            id.setStytypeid(styType);
            dto.setFeeamount(feeAmount);
            dto.setCreditfrom(creditfrom);
            dto.setCreditto(creditUpTo);
            dto.setId(id);
            List validList = daoFactory.getCourseCreditWiseFeeDAO().validData(instituteId, styType, creditfrom, creditUpTo,slno,"update");
            if (validList.size() > 0) {
                err.add("Plese check the 'Credit Range'. The range is overlapping on previous data");
                return err;
            }
            daoFactory.getCourseCreditWiseFeeDAO().update(dto);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }

}
