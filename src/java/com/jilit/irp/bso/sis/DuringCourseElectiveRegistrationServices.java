/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.SubjectRegistrationCriteria;
import com.jilit.irp.persistence.dto.SubjectRegistrationCriteriaId;
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
import com.jilit.irp.iservice.DuringCourseElectiveRegistrationIServices;

/**
 *
 * @author Malkeet Singh
 */
@Service
public class DuringCourseElectiveRegistrationServices implements DuringCourseElectiveRegistrationIServices {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void getProgramCode(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("prog_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getGridData(ModelMap model, HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programId");
            list = (List<Object[]>) daoFactory.getSubjectRegistrationCriteriaDAO().getGridData(instituteid, programid);
            model.addAttribute("gridData", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void creditSubjectType(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list = (List<Object[]>) daoFactory.getSubjectRegistrationCriteriaDAO().creditSubjectType(instituteid);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List saveDuringCourseElectiveRegistration(HttpServletRequest req) {
        SubjectRegistrationCriteria dto=null;
        SubjectRegistrationCriteriaId id = new SubjectRegistrationCriteriaId();
        List err = new ArrayList<String>();
        try { 
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String clientid = jirpsession.getJsessionInfo().getSelectedclientid();
            String programId = req.getParameter("programId");
            String creditTo[] = req.getParameterValues("creditTo");
            String elective[] = req.getParameterValues("elective");
            String elecSub[] = req.getParameterValues("elecSub");
            String showBack = req.getParameter("showBack");
            String editBack = req.getParameter("editBack");
            String allowNot = req.getParameter("allowNot");
            String defaultSel = req.getParameter("defaultSel");
            String backPaperSel = req.getParameter("backPaperSel");
            String printCourse = req.getParameter("printCourse");
            String studentFee = req.getParameter("studentFee");
            String backPaperFee = req.getParameter("backPaperFee");
            String creditToElec[] = req.getParameterValues("creditToElec");
            String elecType[] = req.getParameterValues("elecType");
            String typeElec = req.getParameter("typeElec");
            String duringElec = req.getParameter("duringElec");
            String printElec = req.getParameter("printElec");
            id.setInstituteid(instituteid);
            id.setClientid(clientid);
            id.setProgramid(programId);
            dto = (SubjectRegistrationCriteria) daoFactory.getSubjectRegistrationCriteriaDAO().findByPrimaryKey(id);
            if (dto != null) {
                err.add("Duplicate");
                return err;
            } else {
               dto = new SubjectRegistrationCriteria();
                dto.setId(id);
                //During Course Registration
                dto.setSubjtypeforcreditsummation_c(Arrays.toString(creditTo).replace("[", "").replace("]", "").replace(" ", "").trim());
                dto.setElectivetypetobeshown_c(Arrays.toString(elective).replace("[", "").replace("]", "").replace(" ", "").trim());
                dto.setElectivetypetobedisabled_c(Arrays.toString(elecSub).replace("[", "").replace("]", "").replace(" ", "").trim());
                dto.setShowbackpaperincoursereg(showBack);
                dto.setEnablebackpaperincoursereg(editBack);
                dto.setAllownotrunningbackpaper(allowNot);
                dto.setBackpaperdefaultselected(defaultSel);
                dto.setBackpaperselectionmandaoty(backPaperSel);
                dto.setPrintingofcourseregistration(printCourse);
                dto.setFeeduecheckisrequierdfor(studentFee);
                dto.setBackpaperfeetobe(backPaperFee);
                //During Elective Registration
                dto.setSubjtypeforcreditsummation_e(Arrays.toString(creditToElec).replace("[", "").replace("]", "").replace(" ", "").trim());
                dto.setElectivetypetobeshown_e(Arrays.toString(elecType).replace("[", "").replace("]", "").replace(" ", "").trim());
                dto.setElectivetypetobedisabled_e(typeElec);
                dto.setModificationofpereferences(duringElec);
                dto.setAllowprintingofelectivechoices(printElec);
                daoFactory.getSubjectRegistrationCriteriaDAO().add(dto);
            }
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;
    }

    public ModelMap editDuringCourseElectiveRegistration(HttpServletRequest request, ModelMap model) {
        SubjectRegistrationCriteria dto = null;
        SubjectRegistrationCriteriaId id = null;
        try {
            dto = new SubjectRegistrationCriteria();
            id = new SubjectRegistrationCriteriaId();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String clientid = jirpsession.getJsessionInfo().getSelectedclientid();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            model.addAttribute("instituteid", instituteid);
            model.addAttribute("clientid", clientid);
            model.addAttribute("programId", ids[0]);
            id.setProgramid(ids[0]);
            id.setInstituteid(instituteid);
            id.setClientid(clientid);
            dto = (SubjectRegistrationCriteria) daoFactory.getSubjectRegistrationCriteriaDAO().findByPrimaryKey(id);
            String s1[] = dto.getSubjtypeforcreditsummation_c().toString().split(",");
            List creditTo = new ArrayList<String>();
            for (int i = 0; i < s1.length; i++) {
                creditTo.add(s1[i]);
            }
            String s2[] = dto.getSubjtypeforcreditsummation_e().toString().split(",");
            List creditToElec = new ArrayList<String>();
            for (int i = 0; i < s2.length; i++) {
                creditToElec.add(s2[i]);
            }
            String s3[] = dto.getElectivetypetobeshown_e().toString().split(",");
            List elecType = new ArrayList<String>();
            for (int i = 0; i < s3.length; i++) {
                elecType.add(s3[i]);
            }
            String s4[] = dto.getElectivetypetobeshown_c().toString().split(",");
            List elective = new ArrayList<String>();
            for (int i = 0; i < s4.length; i++) {
                elective.add(s4[i]);
            }
            String s5[] = dto.getElectivetypetobedisabled_c().toString().split(",");
            List elecSub = new ArrayList<String>();
            for (int i = 0; i < s5.length; i++) {
                elecSub.add(s5[i]);
            }
            model.addAttribute("creditTo", creditTo);
            model.addAttribute("creditToElec", creditToElec);
            model.addAttribute("elecType", elecType);
            model.addAttribute("elective", elective);
            model.addAttribute("elecSub", elecSub);
            model.addAttribute("getData", dto);
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        return model;
    }

    public List updateDuringCourseElectiveRegistration(HttpServletRequest req) {
        SubjectRegistrationCriteria dto = new SubjectRegistrationCriteria();
        SubjectRegistrationCriteriaId id = new SubjectRegistrationCriteriaId();
        List err = new ArrayList<String>();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String clientid = jirpsession.getJsessionInfo().getSelectedclientid();
            String programId = req.getParameter("programId");
            String creditTo[] = req.getParameterValues("creditTo");
            String elective[] = req.getParameterValues("elective");
            String elecSub[] = req.getParameterValues("elecSub");
            String showBack = req.getParameter("showBack");
            String editBack = req.getParameter("editBack");
            String allowNot = req.getParameter("allowNot");
            String defaultSel = req.getParameter("defaultSel");
            String backPaperSel = req.getParameter("backPaperSel");
            String printCourse = req.getParameter("printCourse");
            String studentFee = req.getParameter("studentFee");
            String backPaperFee = req.getParameter("backPaperFee");
            String creditToElec[] = req.getParameterValues("creditToElec");
            String elecType[] = req.getParameterValues("elecType");
            String typeElec = req.getParameter("typeElec");
            String duringElec = req.getParameter("duringElec");
            String printElec = req.getParameter("printElec");
            id.setInstituteid(instituteid);
            id.setClientid(clientid);
            id.setProgramid(programId);
            dto.setId(id);
            //During Course Registration
            dto.setSubjtypeforcreditsummation_c(Arrays.toString(creditTo).replace("[", "").replace("]", "").replace(" ", "").trim());
            dto.setElectivetypetobeshown_c(Arrays.toString(elective).replace("[", "").replace("]", "").replace(" ", "").trim());
            dto.setElectivetypetobedisabled_c(Arrays.toString(elecSub).replace("[", "").replace("]", "").replace(" ", "").trim());
            dto.setShowbackpaperincoursereg(showBack);
            dto.setEnablebackpaperincoursereg(editBack);
            dto.setAllownotrunningbackpaper(allowNot);
            dto.setBackpaperdefaultselected(defaultSel);
            dto.setBackpaperselectionmandaoty(backPaperSel);
            dto.setPrintingofcourseregistration(printCourse);
            dto.setFeeduecheckisrequierdfor(studentFee);
            dto.setBackpaperfeetobe(backPaperFee);
            //During Elective Registration
            dto.setSubjtypeforcreditsummation_e(Arrays.toString(creditToElec).replace("[", "").replace("]", "").replace(" ", "").trim());
            dto.setElectivetypetobeshown_e(Arrays.toString(elecType).replace("[", "").replace("]", "").replace(" ", "").trim());
            dto.setElectivetypetobedisabled_e(typeElec);
            dto.setModificationofpereferences(duringElec);
            dto.setAllowprintingofelectivechoices(printElec);
            daoFactory.getSubjectRegistrationCriteriaDAO().saveOrUpdate(dto);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;
    }
}
