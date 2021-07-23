/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.SummerRegistrationEntryIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetail;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceDetailId;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCount;
import com.jilit.irp.persistence.dto.PRStudentSubjectChoiceCountId;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.ProgramSubjectTaggingId;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentRegistrationId;
import com.jilit.irp.persistence.dto.StudentRegistration_info;
import com.jilit.irp.persistence.dto.StudentRegistration_infoId;
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetailId;
import com.jilit.irp.persistence.dto.FeeStructureIndividual;
import com.jilit.irp.persistence.dto.FeeStructureIndividualId;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.ui.ModelMap;
import javax.servlet.http.*;
import java.util.Arrays;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class SummerRegistrationEntryService implements SummerRegistrationEntryIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void getInstituteCode(ModelMap model) {
        try {
            String maininstituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String userid = jirpsession.getJsessionInfo().getUserid();
            List list = daoFactory.getInstituteMasterDAO().getInstituteCodeForAddDrop(userid, "0000RTID1005A0Z0ZZZ9");
            model.put("institutelist", list);
            model.put("maininstituteid", maininstituteid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getSemesterCode(HttpServletRequest request) {
        //for multi selection institute Code
        String[] instids = request.getParameter("instituteids").split(",");
        List instituteids = Arrays.asList(instids);
        List<Object[]> list = null;
        try {
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForSummer(instituteids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStudetnInfo(HttpServletRequest request) {
        String instituteid = request.getParameter("instituteids");
        String enrollmentno = request.getParameter("enrollmentno").split("~@~")[0].trim();
        List list = null;
        try {
            list = (List) daoFactory.getStudentNRSubjectsDAO().getStudentInfo(instituteid, enrollmentno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getGridData(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = request.getParameter("instituteids");
            String registrationid = request.getParameter("registrationid");
            String studentid = request.getParameter("studentid").split("~@~")[0];
            list = (List<Object[]>) daoFactory.getStudentSubjectChoiceMasterDAO().getGridDateForSupplementary(instituteid, registrationid, studentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getStudents(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = request.getParameter("instituteids");
            String rid = request.getParameter("registrationid");
            list = (List) daoFactory.getStudentMasterDAO().getStudentsForSummer(instituteid, rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSubjectCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = request.getParameter("instituteids");
            String rid = request.getParameter("registrationid");
            String studentid = request.getParameter("studentid").split("~@~")[0];
            list = (List) daoFactory.getSubjectMasterDAO().getSubjectsForSummer(instituteid, rid, studentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List saveSummerSubjects(HttpServletRequest request) {
        List list = new ArrayList();
        List pstlist = new ArrayList();
        List<Object[]> dlist = new ArrayList();
        List<String> list2 = new ArrayList();
        BusinessService businessService = new BusinessService(daoFactory, true);
        Set set = new HashSet();
        List list1 = new ArrayList();
        try {
            List recordsToInsert = new ArrayList();
            StudentSubjectChoiceMaster mdto = null;
            StudentSubjectChoiceMasterId mid = new StudentSubjectChoiceMasterId();

            StudentSubjectChoiceDetail ddto = new StudentSubjectChoiceDetail();
            StudentSubjectChoiceDetailId did = new StudentSubjectChoiceDetailId();

            PRStudentSubjectChoiceCount prdto = new PRStudentSubjectChoiceCount();
            PRStudentSubjectChoiceCountId prid = new PRStudentSubjectChoiceCountId();

            StudentRegistration srdto = new StudentRegistration();
            StudentRegistrationId srid = new StudentRegistrationId();

            StudentRegistration_info sridto = new StudentRegistration_info();
            StudentRegistration_infoId sriid = new StudentRegistration_infoId();

            ProgramSubjectTagging pstdto = new ProgramSubjectTagging();
            ProgramSubjectTaggingId pstid = new ProgramSubjectTaggingId();
            ProgramSubjectDetail programSubjectDetail = null;
            ProgramSubjectDetailId programSubjectDetailId = null;

            String instituteid = request.getParameter("multiinstituteid");
            String registrationid = request.getParameter("semestercode");
            String offeredsubjectid = request.getParameter("offeredsubjectid");
            String studentid = request.getParameter("enrollmentno").split("~@~")[0];
            String stustynumber = request.getParameter("enrollmentno").split("~@~")[1];
            String stuquotaid = request.getParameter("enrollmentno").split("~@~")[2];
            String subjectid = "";
            boolean newsubject = false;
            if (!offeredsubjectid.equals("") && offeredsubjectid != null) {
                newsubject = true;
                subjectid = offeredsubjectid;
            } else {
                subjectid = request.getParameter("subjectcode").split("~@~")[0];
            }
            String credit_styno = request.getParameter("subjectcode").split("~@~")[1];
            String subjectcredit = credit_styno.split("@")[0];
            String failstyno = credit_styno.split("@")[1];
            String reasontype = request.getParameter("subjectcode").split("~@~")[2];
            String departmentid = request.getParameter("subjectcode").split("~@~")[5];
            // reasontype [1] Medical [0] Others..
            String parameterstytype = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.1");//--
            if (parameterstytype.length() > 3) {
                list.add("Please Check and Correct the value of Parameter SUBREG3.1 in Registration Parameters Form...!");
                return list;
            }
            String parameterfeecal = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.2");//--
            String parfeeheadcode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.6");//--
            boolean supflag = false;
            if (parameterstytype.equalsIgnoreCase("SUP")) {
                supflag = true;
            }
            String newstytypeid = daoFactory.getStyTypeDAO().getStytypeId(instituteid, parameterstytype);
            String feeamount = "";
            String feeamountfrom = "";
            if (parameterfeecal.equals("F")) {
                if (supflag) {
                    feeamount = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.4");//--
                    feeamountfrom = "fixed3.4";
                } else {
                    feeamount = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.5");//--
                    feeamountfrom = "fixed3.5";
                }
            } else if (parameterfeecal.equals("S")) {
                feeamountfrom = "subjectwise";
                feeamount = daoFactory.getSubjectWiseBackPaperFeeDAO().getSubjectWiseBackPaperFeeAmount(instituteid, registrationid, subjectid);
            } else if (parameterfeecal.equals("C")) {
                feeamountfrom = "creditwise";
                feeamount = daoFactory.getCourseCreditWiseFeeDAO().getCreditWiseBackPaperFeeAmount(instituteid, newstytypeid, new Double(subjectcredit), new Double(subjectcredit));
            } else {
                list.add("Please Check and Correct the value of Parameter SUBREG3.2 in Registration Parameters Form...!");//--
                return list;
            }
            if (feeamount.equals("")) {
                if (feeamountfrom.equals("subjectwise")) {
                    list.add("Subject wise back paper fee is not define...!");
                    return list;
                } else if (feeamountfrom.equals("creditwise")) {
                    list.add("Credit wise back paper fee is not define...!");
                    return list;
                } else if (feeamountfrom.equals("fixed3.4")) {
                    list.add("Back Paper fee is not define for Parameter SUBREG3.4 in Registration Parameters Form...");
                    return list;
                } else if (feeamountfrom.equals("fixed3.5")) {
                    list.add("Back Paper fee is not define for Parameter SUBREG3.5 in Registration Parameters Form...");
                    return list;
                }
            }

            String feeheadid = daoFactory.getProgramSubjectDetailDAO().getFeeHeadIdForSupplementrySubjEntry(instituteid, parfeeheadcode);

            String currencyid = "";
            List currency = (List) daoFactory.getProgramSubjectDetailDAO().getCurrencyIdForAddDrop();
            if (currency.size() > 0 && currency.get(0) != null) {
                currencyid = currency.get(0).toString();
            }

            List<Object[]> checkRecordInFeestructureindivisual = (List<Object[]>) daoFactory.getProgramSubjectDetailDAO().checkRecordInFeestructureindivisual(instituteid, registrationid, feeheadid, studentid);
            String totalduefeeamount = "";
            String feefinilized = "";
            boolean fineexistflag = false;
            if (checkRecordInFeestructureindivisual.size() > 0 && checkRecordInFeestructureindivisual.get(0) != null) {
                Object[] obj = checkRecordInFeestructureindivisual.get(0);
                totalduefeeamount = obj[0].toString();
                feefinilized = obj[1].toString();
                fineexistflag = true;
            }

            //StudentRegistrationId
            srid.setInstituteid(instituteid);
            srid.setRegistrationid(registrationid);
            srid.setStudentid(studentid);
            //StudentRegistration_InfoId
            sriid.setInstituteid(instituteid);
            sriid.setRegistrationid(registrationid);
            sriid.setStudentid(studentid);
            List<Object[]> data = (List<Object[]>) daoFactory.getStudentMasterDAO().getStudentData(instituteid, studentid);
            Object[] sobj = data.get(0);
            String proid = sobj[0].toString();
            String secid = sobj[5].toString();
            String subsectionid = sobj[6].toString();
            dlist = (List<Object[]>) daoFactory.getSubjectMasterDAO().getBasketidSubjecttypeid(instituteid, subjectid, proid, secid);
            if (dlist.size() > 0) {
                list2 = (List<String>) daoFactory.getSubjectMasterDAO().getSubjectComponentDetail(instituteid, subjectid);
                String basketid = "";
                String subjecttype = "";
                String subjecttypeid = "";
                for (int i = 0; i < dlist.size(); i++) {
                    Object[] obj = dlist.get(i);
                    basketid = obj[0] != null ? obj[0].toString() : "";
                    subjecttype = obj[1] != null ? obj[1].toString() : "";
                    subjecttypeid = obj[2] != null ? obj[2].toString() : "";
                }
                srdto = (StudentRegistration) daoFactory.getStudentRegistrationDAO().findByPrimaryKey(srid);
                if (srdto == null) {
                    srdto = new StudentRegistration();
                    srdto.setId(srid);
                    srdto.setStynumber(new Byte(stustynumber));
                    srdto.setStytypeid(newstytypeid);
                    srdto.setQuotaid(stuquotaid);
                    srdto.setRegallow("Y");
                    srdto.setAllowforfeepay("Y");
                    srdto.setRegallowdate(new Date());
                    daoFactory.getStudentRegistrationDAO().add(srdto);
                    sridto = (StudentRegistration_info) daoFactory.getStudentRegistration_infoDAO().findByPrimaryKey(sriid);
                    if (sridto == null) {
                        sridto = new StudentRegistration_info();
                        sridto.setId(sriid);
                        sridto.setStynumber(new Byte(stustynumber));
                        sridto.setStytypeid(newstytypeid);
                        sridto.setAcademicyear(sobj[2].toString());
                        sridto.setProgramid(sobj[0].toString());
                        sridto.setBranchid(sobj[1].toString());
                        sridto.setSectionid(sobj[5].toString());
                        sridto.setSubsectionid(sobj[6].toString());
                        daoFactory.getStudentRegistration_infoDAO().add(sridto);
                    }
                }
                //if feeStructureIndividual fee is not finialized
                if (!feefinilized.equals("Y")) {

//                    List<Object[]> departmentlist = (List<Object[]>) daoFactory.getDepartmentMasterDAO().getDepartmentNameOfReqSubject(instituteid, subjectid);
//                    Object[] depobj = departmentlist.get(0);
//                    String departmentid = depobj[0].toString();
                    //ProgramSubjectTaggingId
                    String pstagid = daoFactory.getProgramSubjectTaggingDAO().getPSTid(instituteid, registrationid, sobj[2].toString(), sobj[0].toString(), sobj[1].toString(), Short.parseShort(failstyno), sobj[5].toString(), basketid, subjectid);
                    pstid.setInstituteid(instituteid);
                    pstid.setRegistrationid(registrationid);
                    pstid.setProgramsubjectid(pstagid);
                    List pstdlist = daoFactory.getProgramSubjectTaggingDAO().getPSTDetail(instituteid, pstagid, registrationid);
                    if (pstdlist.size() == 0) {
                        String programsubjectid = businessService.generateId("ProgramSubjectID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                        pstid.setProgramsubjectid(programsubjectid);
                        pstdto = new ProgramSubjectTagging();
                        pstdto.setId(pstid);
                        pstdto.setAcademicyear(sobj[2] != null ? sobj[2].toString() : "");
                        pstdto.setStynumber(Short.parseShort(failstyno));//--------------------
                        pstdto.setBasketid(basketid);
                        pstdto.setDepartmentid(departmentid);
                        pstdto.setDeactive("N");
                        pstdto.setProgramid(sobj[0] != null ? sobj[0].toString() : "");
                        pstdto.setSectionid(sobj[5] != null ? sobj[5].toString() : "");
                        pstdto.setSubjectid(subjectid);
                        pstdto.setPstpopulated("Y");
                        pstdto.setSubjectrunning("Y");
                        pstdto.setCustomfinalized("Y");
                        pstdto.setCredits(Double.parseDouble("0"));
                        pstdto.setSubjecttype(subjecttype);
                        pstdto.setSubjecttypeid(subjecttypeid);
                        pstlist = daoFactory.getProgramSubjectTaggingDAO().doValidate(pstdto, "normal");
                        if (pstlist.size() > 0) {
                            businessService.rollback();
//                            return list;
                        } else {
                            set = new HashSet();
                            HashMap data1 = null;
                            list1 = (List) daoFactory.getProgramSchemeDAO().getSubjectTaggingList(instituteid, sobj[2].toString(), sobj[5].toString(), sobj[0].toString(), new Byte(failstyno), basketid, registrationid, subjectid);
                            if (list1.size() > 0) {
                                for (int j = 0; j < list1.size(); j++) {
                                    data1 = (HashMap) list1.get(j);
                                    SubjectComponent subjectComponent = (SubjectComponent) daoFactory.getSubjectComponentDAO().findByPrimaryKey(new SubjectComponentId(instituteid, data1.get("subjectcomponentid").toString()));
                                    String subjectcomponentcode = subjectComponent != null ? subjectComponent.getSubjectcomponentcode() : "";
                                    programSubjectDetail = new ProgramSubjectDetail();
                                    programSubjectDetailId = new ProgramSubjectDetailId();
                                    programSubjectDetailId.setInstituteid(instituteid);
                                    programSubjectDetailId.setProgramsubjectid(programsubjectid);
                                    programSubjectDetailId.setRegistrationid(registrationid);

                                    if (subjectcomponentcode.equals("P")) {
                                        programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                        programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                        programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                        programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                        programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                        programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                                    } else if (subjectcomponentcode.equals("T")) {
                                        programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                        programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                        programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                        programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                        programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                        programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                                    } else if (subjectcomponentcode.equals("L")) {
                                        programSubjectDetailId.setSubjectcomponentid(data1.get("subjectcomponentid").toString());
                                        programSubjectDetail.setLtppassingmarks(data1.get("ltppassingmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("ltppassingmarks").toString()));
                                        programSubjectDetail.setTotalccpmarks(data1.get("totalccpmarks") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalccpmarks").toString()));
                                        programSubjectDetail.setNoofhours(data1.get("noofhours") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofhours").toString()));
                                        programSubjectDetail.setNoofclassinaweek(data1.get("noofclassinaweek") == null ? new BigDecimal(0) : new BigDecimal(data1.get("noofclassinaweek").toString()));
                                        programSubjectDetail.setTotalclasses(data1.get("totalclasses") == null ? new BigDecimal(0) : new BigDecimal(data1.get("totalclasses").toString()));
                                    }
                                    programSubjectDetail.setDeactive("N");
                                    programSubjectDetail.setId(programSubjectDetailId);
                                    set.add(programSubjectDetail);
                                }
                            }
                            pstdto.setProgramsubjectdetails(set);
                            recordsToInsert.add(pstdto);
                            businessService.insertInIdGenerationControl(recordsToInsert);
                        }
                    }
                    mid.setInstituteid(instituteid);
                    mid.setRegistrationid(registrationid);
                    mid.setStudentid(studentid);
                    mid.setSubjectid(subjectid);
                    mid.setStynumber(Byte.decode(failstyno));//--------------------
                    mdto = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(mid);
                    if (mdto == null) {
                        mdto = new StudentSubjectChoiceMaster();
                        mdto.setId(mid);
                        mdto.setBasketid(basketid);
                        mdto.setChoice(new Byte("1"));
                        mdto.setSubjectrunning("N");
                        mdto.setRegfeeamount(new BigDecimal(feeamount));
                        mdto.setStytypeid(newstytypeid);
                        mdto.setSupplimentryregistered("D");//--Draft Save ((A)pproval and (C)ancle by Approval Form)
                        mdto.setSupplimentryregisteredby(jirpsession.getJsessionInfo().getMemberid());
                        mdto.setSubjecttype(subjecttype);
                        mdto.setSubjecttypeid(subjecttypeid);
                        mdto.setSupplimentryregistereddate(new Date());
                        if (!offeredsubjectid.equals("") && offeredsubjectid != null) {
                            mdto.setEquivalentsubjectid(request.getParameter("subjectcode").split("~@~")[0]);
                        }
                        daoFactory.getStudentSubjectChoiceMasterDAO().add(mdto);
                        for (int i = 0; i < list2.size(); i++) {
                            String subcomid = list2.get(i);
                            did.setInstituteid(instituteid);
                            did.setRegistrationid(registrationid);
                            did.setStudentid(studentid);
                            did.setSubjectid(subjectid);
                            did.setStynumber(Byte.decode(failstyno));//------------------------
                            did.setSubjectcomponentid(subcomid);
                            ddto.setId(did);
                            ddto.setSubsectionid(subsectionid);
                            daoFactory.getStudentSubjectChoiceDetailDAO().add(ddto);
                            if (i == 0) {
                                prid.setInstituteid(instituteid);
                                prid.setRegistrationid(registrationid);
                                prid.setStudentid(studentid);
                                prid.setStynumber(Byte.decode(failstyno));//-------------------------
                                prid.setBasketid(basketid);
                                prdto = (PRStudentSubjectChoiceCount) daoFactory.getPrStudentSubjectChoiceCountDAO().findByPrimaryKey(prid);
                                if (prdto == null) {
                                    prdto = new PRStudentSubjectChoiceCount();
                                    prdto.setId(prid);
                                    prdto.setDeactive("N");
                                    prdto.setSubjectschoicescount(Byte.decode("1"));
                                    daoFactory.getPrStudentSubjectChoiceCountDAO().add(prdto);
                                }
                            }
                        }
                        //reasontype ( 0 / 1 )[1] Medical reason [0] Other Reason
                        if (reasontype.equals("0")) { // [0] Other Reason
                            FeeStructureIndividual fsti = new FeeStructureIndividual();
                            FeeStructureIndividualId id = new FeeStructureIndividualId();
                            id.setCurrencyid(currencyid);
                            id.setStudentid(studentid);
                            id.setStytypeid(newstytypeid);
                            id.setQuotaid(stuquotaid);
                            id.setFeeheadid(feeheadid);
                            id.setInstituteid(instituteid);
                            id.setRegistrationid(registrationid);
                            id.setStynumber(new Byte(stustynumber));
                            id.setSlno(77);
                            fsti.setId(id);
                            if (fineexistflag) {
                                int finalfeeamount = Integer.parseInt(totalduefeeamount) + Integer.parseInt(feeamount);
                                fsti.setFeeamount(new BigDecimal(finalfeeamount));
                            } else {
                                fsti.setFeeamount(new BigDecimal(feeamount));
                            }
                            fsti.setSourcetransaction("Summer Subject Entry");
                            fsti.setAcceptablecurrency("A");
                            fsti.setPartiallyacceptable("N");
                            fsti.setCriteriabased("N");
                            fsti.setFeefinalized("N");
                            fsti.setDeactive("N");
                            fsti.setEntrydate(new Date());
                            daoFactory.getProgramSubjectDetailDAO().saveOrUpdate(fsti);
                        }
                        list.add("Success");
                    } else {
                        list.add("Subject is already exists");
                    }
                } else {
                    //if feeStructureIndividual fee is finialized
                    list.add("The fee has been finalized, So you can't add more Subjects...! ");
                    return list;
                }
            } else {
                if (newsubject) {
                    list.add("Teaching Scheme not found for this offered Subejct...!");
                } else {
                    list.add("Teaching Scheme not found for this Subject ...!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list.add("Error");
            return list;
        }
        return list;
    }

    @Override
    public List deleteSummerSubjects(HttpServletRequest request
    ) {
        StudentSubjectChoiceMaster mdto = null;
        StudentSubjectChoiceMasterId mid = new StudentSubjectChoiceMasterId();

        StudentSubjectChoiceDetail ddto = null;
        StudentSubjectChoiceDetailId did = new StudentSubjectChoiceDetailId();

        PRStudentSubjectChoiceCount prdto = null;
        PRStudentSubjectChoiceCountId prid = new PRStudentSubjectChoiceCountId();
        List list = null;
        List<String> list2 = new ArrayList();
        try {
            list = new ArrayList<String>();
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (int i = 0; i < pks.length; i++) {
                String[] idss = pks[i].toString().split("~@~");
                String instituteid = idss[0];
                String registrationid = idss[1];
                String studentid = idss[2].toString();
                String styno = idss[3].toString();
                String subjectid = idss[4].toString();
                String basketid = idss[5].toString();
                String parfeeheadcode = daoFactory.getRegistrationParametersDAO().getParametersValue(instituteid, "SUBREG3.6");//--
                String feeheadid = daoFactory.getProgramSubjectDetailDAO().getFeeHeadIdForSupplementrySubjEntry(instituteid, parfeeheadcode);
                List<Object[]> checkRecordInFeestructureindivisual = (List<Object[]>) daoFactory.getProgramSubjectDetailDAO().checkRecordInFeestructureindivisual(instituteid, registrationid, feeheadid, studentid);
                String feefinilized = "";
                String oldfeeamount = "";
                String currencyid = "";
                String stytypeid = "";
                String quotaid = "";
                String slno = "";
                String stynumber = "";
                if (checkRecordInFeestructureindivisual.size() > 0 && checkRecordInFeestructureindivisual.get(0) != null) {
                    Object[] obj = checkRecordInFeestructureindivisual.get(0);
                    oldfeeamount = obj[0].toString();
                    feefinilized = obj[1].toString();
                    currencyid = obj[2].toString();
                    stytypeid = obj[3].toString();
                    quotaid = obj[4].toString();
                    slno = obj[5].toString();
                    stynumber = obj[6].toString();
                }
                if (!feefinilized.equals("Y")) {
                    list2 = (List<String>) daoFactory.getSubjectMasterDAO().getSubjectComponentDetail(instituteid, subjectid);
                    for (int j = 0; j < list2.size(); j++) {
                        String subcomid = list2.get(j);
                        did.setInstituteid(instituteid);
                        did.setRegistrationid(registrationid);
                        did.setStudentid(studentid);
                        did.setSubjectid(subjectid);
                        did.setStynumber(Byte.decode(styno));
                        did.setSubjectcomponentid(subcomid);
                        ddto = (StudentSubjectChoiceDetail) daoFactory.getStudentSubjectChoiceDetailDAO().findByPrimaryKey(did);
                        if (ddto != null) {
                            daoFactory.getStudentSubjectChoiceDetailDAO().delete(ddto);
                        }
                    }
                    mid.setInstituteid(instituteid);
                    mid.setRegistrationid(registrationid);
                    mid.setStudentid(studentid);
                    mid.setSubjectid(subjectid);
                    mid.setStynumber(Byte.decode(styno));
                    mdto = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(mid);
                    if (mdto != null) {
                        daoFactory.getStudentSubjectChoiceMasterDAO().delete(mdto);
                        BigDecimal regamount = mdto.getRegfeeamount();
                        FeeStructureIndividual fsti = new FeeStructureIndividual();
                        FeeStructureIndividualId id = new FeeStructureIndividualId();
                        id.setCurrencyid(currencyid);
                        id.setStudentid(studentid);
                        id.setStytypeid(stytypeid);
                        id.setQuotaid(quotaid);
                        id.setFeeheadid(feeheadid);
                        id.setInstituteid(instituteid);
                        id.setRegistrationid(registrationid);
                        id.setStynumber(new Byte(stynumber));
                        id.setSlno(77);
                        BigDecimal newamount = new BigDecimal(Integer.parseInt(oldfeeamount) - regamount.intValue());
                        daoFactory.getProgramSubjectDetailDAO().updateFeeStructureIndividual(id, newamount);
                    }
                    prid.setInstituteid(instituteid);
                    prid.setRegistrationid(registrationid);
                    prid.setStudentid(studentid);
                    prid.setStynumber(Byte.decode(styno));
                    prid.setBasketid(basketid);
                    prdto = (PRStudentSubjectChoiceCount) daoFactory.getPrStudentSubjectChoiceCountDAO().findByPrimaryKey(prid);
                    if (prdto != null) {
                        daoFactory.getPrStudentSubjectChoiceCountDAO().delete(prdto);
                    }
                } else {
                    //if feeStructureIndividual fee is finialized
                    list.add("The fee has been finalized, So you can't Drop this Subjects...! ");
                    return list;
                }
            }
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }

        return list;
    }
}
