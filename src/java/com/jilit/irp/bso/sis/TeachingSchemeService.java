/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.TeachingSchemeIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.jilit.irp.util.JIRPSession;
import java.util.List;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.BasketMaster;
import com.jilit.irp.persistence.dto.DepartmentMaster;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadYearDetail;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadYearDetailId;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWise;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWiseId;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.persistence.dto.SubjectComponentDetail;
import com.jilit.irp.persistence.dto.SubjectComponentDetailId;
import com.jilit.irp.persistence.dto.SubjectMaster;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mohit1.kumar
 */
@Service
public class TeachingSchemeService implements TeachingSchemeIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getTeachingSchemeData(ModelMap model) {
        List<Academicyear> acadList = null;
        List progList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Academicyear>) daoFactory.getAcademicYearDAO().findAll(instituteid);
            progList = daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("acadList", acadList);
            model.addAttribute("progList", progList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getBranch(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String programId = req.getParameter("programId");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> ccList = daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programId);
            if (ccList != null && ccList.size() > 0) {
                rtnlist.add(ccList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    @Override
    public List getSemester(HttpServletRequest req) {
        List semester = new ArrayList();
        try {
            String acadYear = req.getParameter("academicYear");
            String branch = req.getParameter("branchId");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            semester = daoFactory.getProgramMinMaxLimitDAO().getSemester(branch, acadYear, instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semester;
    }

    @Override
    public List getSection(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("program");
            String branchid = req.getParameter("branch");
            list = daoFactory.getSectionMasterDAO().getSectionDataWithBranch(instituteid, programid, branchid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getBasket(HttpServletRequest req) {
        List basketList = new ArrayList();
        try {
            String programid = req.getParameter("program");
            byte semester = Byte.parseByte(req.getParameter("semester"));
            String section = req.getParameter("section");
            String reqfor  =  req.getParameter("reqfor");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            basketList = daoFactory.getBasketMasterDAO().getBasketCodeForCopy(instituteid, programid, section, semester,reqfor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basketList;
    }

    @Override
    public List getBasketData(HttpServletRequest req) {
        List basketList = new ArrayList();
        try {
            String programid = req.getParameter("program");
            byte semester = Byte.parseByte(req.getParameter("semester"));
            String section = req.getParameter("section");
            String subType = req.getParameter("subType").split("~@~")[0];
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            basketList = daoFactory.getBasketMasterDAO().getBasketCodeData(instituteid, programid, section, semester, subType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basketList;
    }

    public List getMarks(HttpServletRequest req) {
        List rtnlist = new ArrayList();
        try {
            String subjectid = req.getParameter("subjectid");
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> marks = daoFactory.getSubjectMasterDAO().getMarks(instituteid, subjectid);
            if (marks != null && marks.size() > 0) {
                rtnlist.add(marks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnlist;
    }

    public List saveCopiedData(HttpServletRequest request) {
        BusinessService businessService = new BusinessService(daoFactory, true);
        List retList = new ArrayList();
        ProgramSchemeAcadyearWise dto = null;
        ProgramSchemeAcadyearWiseId id = null;
        ProgramSchemeAcadYearDetail detaildto = null;
        ProgramSchemeAcadYearDetailId detailid = null;
        Set<ProgramSchemeAcadYearDetail> programschemeacadyeardetails = null;
        List finalList = new ArrayList();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List<Object[]> detailList = null;
            List<Object[]> objFromList = (List<Object[]>) daoFactory.getProgramSchemeDAO().getProgramSchemeList(instituteid, request.getParameter("acadYear1"), request.getParameter("program1"), request.getParameter("semester1"), request.getParameter("section1"), request.getParameter("basket1"));
            if (objFromList != null && !objFromList.isEmpty()) {
                try {
                    for (int i = 0; i < objFromList.size(); i++) {
                        if (daoFactory.getProgramSchemeDAO().checkProgramSchemeExistence(instituteid, request.getParameter("acadYear2"), request.getParameter("program2"), request.getParameter("section2"), request.getParameter("semester2"), objFromList.get(i)[0].toString())) { //objFromList.get(i)[0].toString()=subjectid
                            continue;
                        }
                        dto = new ProgramSchemeAcadyearWise();
                        id = new ProgramSchemeAcadyearWiseId();
                        programschemeacadyeardetails = new HashSet<ProgramSchemeAcadYearDetail>();
                        id.setInstituteid(instituteid);
                        String programschemeacadwiseid = businessService.generateId("ProgramSchemeAcadWiseID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
                        id.setProgramschemeacadwiseid(programschemeacadwiseid);
                        dto.setId(id);
                        dto.setAcademicyear(request.getParameter("acadYear2"));
                        dto.setBasketid(request.getParameter("basket2"));
                        dto.setCredits(Double.parseDouble(objFromList.get(i)[2].toString()));
                        dto.setDeactive("N");
                        dto.setDepartmentid(objFromList.get(i)[1] == null ? "" : objFromList.get(i)[1].toString());
                        dto.setElectiveid(objFromList.get(i)[3] == null ? "" : objFromList.get(i)[3].toString());
                        dto.setPassingmarks(objFromList.get(i)[5] == null ? null : Double.parseDouble(objFromList.get(i)[5].toString()));
                        dto.setProgramid(request.getParameter("program2").toString());
                        dto.setSectionid(request.getParameter("section2").toString());
                        dto.setStynumber(Byte.valueOf(request.getParameter("semester2").toString()));
                        dto.setSubjectid(objFromList.get(i)[0].toString());
                        dto.setTotalmarks(objFromList.get(i)[4] == null ? null : Double.parseDouble(objFromList.get(i)[4].toString()));
                        dto.setSubjecttype(objFromList.get(i)[9] == null ? "" : objFromList.get(i)[9].toString());
                        dto.setSubjecttypeid(objFromList.get(i)[10] == null ? "" : objFromList.get(i)[10].toString());
                        //   businessService.insertOrUpdateInIdGen();
                        detailList = (List<Object[]>) daoFactory.getProgramSchemeDAO().getProgramSchemeDetail(instituteid, objFromList.get(i)[6].toString());
                        if (detailList != null && !detailList.isEmpty()) {
                            for (int j = 0; j < detailList.size(); j++) {
                                detaildto = new ProgramSchemeAcadYearDetail();
                                detailid = new ProgramSchemeAcadYearDetailId();
                                detailid.setInstituteid(instituteid);
                                detailid.setProgramschemeacadwiseid(dto.getId().getProgramschemeacadwiseid());
                                detailid.setSubjectcomponentid(detailList.get(j)[8].toString());
                                detaildto.setId(detailid);
                                detaildto.setByltp(detailList.get(j)[5] == null ? null : (BigDecimal) detailList.get(j)[5]);
                                detaildto.setDeactive("N");
                                detaildto.setLtppassingmarks((BigDecimal) (detailList.get(j)[0]));
                                detaildto.setNoofclassinaweek(detailList.get(j)[3] == null ? null : (BigDecimal) detailList.get(j)[3]);
                                detaildto.setNoofhours(detailList.get(j)[2] == null ? null : (BigDecimal) detailList.get(j)[2]);
                                detaildto.setPassingmarks(detailList.get(j)[7] == null ? null : (Double.parseDouble(detailList.get(j)[7].toString())));
                                detaildto.setTotalccpmarks((BigDecimal) detailList.get(j)[1]);
                                detaildto.setTotalclasses(detailList.get(j)[4] == null ? null : (BigDecimal) detailList.get(j)[4]);
                                detaildto.setTotalmarks(detailList.get(j)[6] == null ? null : (Double.parseDouble(detailList.get(j)[6].toString())));
                                programschemeacadyeardetails.add(detaildto);

                            }
                        }
                        dto.setProgramschemeacadyeardetails(programschemeacadyeardetails);
                        finalList.add(dto);
                    }
                    if (finalList != null && finalList.size() > 0) {
                        businessService.insertInIdGenerationControl(finalList);
                        retList = new ArrayList();
                        // retList.add("Data saved successfully !");
                        retList.add("Success");
                    } else if (retList.isEmpty() || retList.size() == 0) {
                        retList = new ArrayList();
                        retList.add("Same record already exists for the fields choosed !");
                        // retList.add("Warning");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    businessService.rollback();
                } finally {
                    businessService.closeSession();
                    dto = null;
                }
            } else {
                retList.add("Teaching Scheme Not Found For This Cretira..!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            retList = new ArrayList();
//            retList.add("Problem in saving record !");
            retList.add("Error");
        }
        return retList;
    }

    public List getGridData(HttpServletRequest req) {
        List<ProgramSchemeAcadyearWise> l = new ArrayList<ProgramSchemeAcadyearWise>();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            Map map = new HashMap();
            map.put("instituteid", "'" + instituteid + "'");
            map.put("programid", "'" + req.getParameter("programId") + "'");
            map.put("sectionid", "'" + req.getParameter("sectionId") + "'");
            map.put("stynumber", req.getParameter("semester"));
            map.put("academicyear", "'" + req.getParameter("acadYear") + "'");
            l = (List<ProgramSchemeAcadyearWise>) daoFactory.getUtilDAO().findNamedQuery("getSelectedProgramSchemeAcadyearWise", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return l;
    }

    public List getAllSubjectType() {
        List list = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = daoFactory.getSubjectTypeMasterDAO().getAllSubjectType(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllSubjectMaster() {
        List<SubjectMaster> list = new ArrayList<SubjectMaster>();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            list = (List<SubjectMaster>) daoFactory.getSubjectMasterDAO().findAllWithNVL(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllBasketMaster(HttpServletRequest req) {
        List<BasketMaster> list = new ArrayList<BasketMaster>();
        try {
            String subjecttypeid = req.getParameter("subType").split("~@~")[0];
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<BasketMaster>) daoFactory.getBasketMasterDAO().getBasketCode(instituteid, subjecttypeid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getDepartmentMasterData(HttpServletRequest req) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List<DepartmentMaster> list = new ArrayList<DepartmentMaster>();
        try {
            String subjectid = req.getParameter("subCode");
            list = (List<DepartmentMaster>) daoFactory.getDepartmentMasterDAO().getDepartmentNameOfReqSubject(instituteid, subjectid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllElecticeCode() {
        List<ElectiveMaster> retList = new ArrayList<ElectiveMaster>();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            retList = (List<ElectiveMaster>) daoFactory.getElectiveMasterDAO().getElectiveMaster(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List getAllSubjectComponent() {
        List<SubjectComponent> retList = new ArrayList<SubjectComponent>();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            retList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List addProgramSchemeAcademicyearWise(HttpServletRequest req) {
        ProgramSchemeAcadyearWise programSchemeAcadyearWise = null;
        ProgramSchemeAcadYearDetail programSchemeAcadYearDetail = null;
        List<ProgramSchemeAcadyearWise> programSchemeAcadyearWisesList = new ArrayList<ProgramSchemeAcadyearWise>();
        List<ProgramSchemeAcadYearDetail> programSchemeAcadYearDetailsList = new ArrayList<ProgramSchemeAcadYearDetail>();
        ProgramSchemeAcadyearWiseId programSchemeAcadyearWiseId = null;
        ProgramSchemeAcadYearDetailId programSchemeAcadYearDetailId = null;
        SubjectComponentDetail detail = null;
        SubjectComponentDetailId detailid = null;
        List<SubjectComponentDetail> subjectcomponentdetails = new ArrayList<SubjectComponentDetail>();
        BusinessService businessService = null;
        String instituteuniqueid = "";
        String instituteid = "";
        Set set = new HashSet();
        List err = new ArrayList();
        try {
            businessService = new BusinessService(daoFactory);
            programSchemeAcadyearWise = new ProgramSchemeAcadyearWise();
            programSchemeAcadyearWiseId = new ProgramSchemeAcadyearWiseId();
            instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            instituteuniqueid = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
            programSchemeAcadyearWiseId.setInstituteid(instituteid);
            String programschemeacademicYearwiseid = businessService.generateId("ProgramSchemeAcadWiseID", instituteuniqueid, "I", false);
            programSchemeAcadyearWiseId.setProgramschemeacadwiseid(programschemeacademicYearwiseid);
            programSchemeAcadyearWise.setBasketid(req.getParameter("basketCode") == null ? "" : req.getParameter("basketCode"));
            programSchemeAcadyearWise.setSubjectid(req.getParameter("subCode") == null ? "" : req.getParameter("subCode").split("~@~")[0]);
            programSchemeAcadyearWise.setDepartmentid(req.getParameter("depCode") == null ? "" : req.getParameter("depCode"));
            programSchemeAcadyearWise.setAcademicyear(req.getParameter("acYear") == null ? "" : req.getParameter("acYear"));
            programSchemeAcadyearWise.setProgramid(req.getParameter("prg") == null ? "" : req.getParameter("prg"));
            programSchemeAcadyearWise.setSectionid(req.getParameter("sect") == null ? "" : req.getParameter("sect"));
            programSchemeAcadyearWise.setStynumber(req.getParameter("sty") == null ? Byte.decode("0") : Byte.decode(req.getParameter("sty")));
            programSchemeAcadyearWise.setCredits(req.getParameter("credit") == null ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("credit")));
            programSchemeAcadyearWise.setTotalmarks(req.getParameter("total").equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("total")));
            programSchemeAcadyearWise.setPassingmarks(req.getParameter("passMarks").equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("passMarks")));
            programSchemeAcadyearWise.setDeactive(req.getParameter("deactive") == null ? "Y" : req.getParameter("deactive"));
            programSchemeAcadyearWise.setRegfeeamount(new BigDecimal(req.getParameter("regFeeAmt").equals("") ? "0" : (req.getParameter("regFeeAmt"))));
            programSchemeAcadyearWise.setSubjecttype(req.getParameter("subType").split("~@~")[1]);
            programSchemeAcadyearWise.setSubjecttypeid(req.getParameter("subType").split("~@~")[0]);
            programSchemeAcadyearWise.setAuditsubject(req.getParameter("auditsubject") == null ? "N" : "Y");
            programSchemeAcadyearWise.setId(programSchemeAcadyearWiseId);
            err = daoFactory.getProgramSchemeAcadyearWiseDAO().doValidate(programSchemeAcadyearWise, "normal");
            if (err.size() > 0) {
                businessService.rollback();
                return err;
            }
            set = new HashSet();
            for (int j = 0; j < Integer.parseInt(req.getParameter("count")); j++) {
                if (req.getParameter("chk" + j) != null) {
                    programSchemeAcadYearDetail = new ProgramSchemeAcadYearDetail();
                    programSchemeAcadYearDetailId = new ProgramSchemeAcadYearDetailId();
                    programSchemeAcadYearDetailId.setInstituteid(instituteid);
                    programSchemeAcadYearDetailId.setProgramschemeacadwiseid(programschemeacademicYearwiseid);
                    programSchemeAcadYearDetailId.setSubjectcomponentid(req.getParameter("subCompCode" + j).equals("") ? "" : req.getParameter("subCompCode" + j));
                    programSchemeAcadYearDetail.setTotalccpmarks(req.getParameter("ccp" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setNoofclassinaweek(req.getParameter("noClass" + j).equals("") ? null : new BigDecimal(req.getParameter("noClass" + j)));
                    programSchemeAcadYearDetail.setNoofhours(req.getParameter("noHrs" + j).equals("") ? null : new BigDecimal(req.getParameter("noHrs" + j)));
                    programSchemeAcadYearDetail.setTotalclasses(req.getParameter("totalClass" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("totalClass" + j)));
                    programSchemeAcadYearDetail.setLtppassingmarks(req.getParameter("ccp" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setTotalmarks(req.getParameter("ccp" + j).equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setPassingmarks(req.getParameter("ccp" + j).equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("ccp" + j)));
//                            programSchemeAcadYearDetail.setByltp(asobject1.containsKey("applicable") ? asobject1.get("applicable") == null ? null : new BigDecimal(asobject1.get("applicable").toString()) : null);
                    programSchemeAcadYearDetail.setDeactive(req.getParameter("active" + j) == null ? "Y" : "N");
                    programSchemeAcadYearDetail.setId(programSchemeAcadYearDetailId);
                    programSchemeAcadYearDetailsList.add(programSchemeAcadYearDetail);
//                    if (req.getParameter("noHrs" + j) != null && req.getParameter("noClass" + j) != null) {
//                        if (!req.getParameter("noHrs" + j).trim().equals("0") && !req.getParameter("noClass" + j).trim().equals("0")) {
                            set.add(programSchemeAcadYearDetail);
//                        }
//                    }

                    if (req.getParameter("subComFlag" + j).equalsIgnoreCase("Y")) {
                    } else {
                        detail = new SubjectComponentDetail();
                        detailid = new SubjectComponentDetailId();
                        detailid.setInstituteid(instituteid);
                        detailid.setSubjectcomponentid(req.getParameter("subCompCode" + j).equals("") ? "" : req.getParameter("subCompCode" + j));
                        detailid.setSubjectid(req.getParameter("subCode") == null ? "" : req.getParameter("subCode").split("~@~")[0]);
                        detail.setId(detailid);
                        detail.setDeactive(req.getParameter("active" + j) == null ? "Y" : "N");
                        detail.setLtppassingmarks(new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", "")));
                        detail.setNoofclassinaweek((req.getParameter("noClass" + j + "") != null && !req.getParameter("noClass" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("noClass" + j + "").toString().replace(" ", ""))) : null);
                        detail.setNoofhours((req.getParameter("noHrs" + j + "") != null && !req.getParameter("noHrs" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("noHrs" + j + "").toString().replace(" ", ""))) : null);
                        detail.setPassingmarks((req.getParameter("ccp" + j + "") != null && !req.getParameter("ccp" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", ""))) : null);
                        detail.setTotalccpmarks(new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", "")));
                        detail.setTotalclasses((req.getParameter("totalClass" + j + "") != null && !req.getParameter("totalClass" + j + "").toString().trim().isEmpty()) ? Short.valueOf(req.getParameter("totalClass" + j + "").toString().replace(" ", "")) : null);
                        detail.setTotalmarks((req.getParameter("ccp" + j + "") != null && !req.getParameter("ccp" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", ""))) : null);
                        daoFactory.getSubjectMasterDAO().add(detail);
                    }
                    programSchemeAcadyearWise.setProgramschemeacadyeardetails(set);
                }
            }
            programSchemeAcadyearWisesList.add(programSchemeAcadyearWise);
            businessService.insertInIdGenerationControl(programSchemeAcadyearWisesList);
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        } finally {
            businessService.closeSession();
        }
        return err;
    }

    @Override
    public void getTeachingSchemeEditData(ModelMap model, HttpServletRequest req) {
        try {
            String pk[] = req.getParameter("pk").split("~@~");
            String instituteId = pk[0];
            String programSchemeAcadWiseId = pk[1];
            String subTypeId = pk[2];
            String subjectid = pk[3];
            model.put("subTypeId", subTypeId);
            model.put("programSchemeAcadWiseId", programSchemeAcadWiseId);
            ProgramSchemeAcadyearWiseId programSchemeAcadyearWiseId = new ProgramSchemeAcadyearWiseId();
            programSchemeAcadyearWiseId.setInstituteid(instituteId);
            programSchemeAcadyearWiseId.setProgramschemeacadwiseid(programSchemeAcadWiseId);
            ProgramSchemeAcadyearWise programSchemeAcadyearWise = new ProgramSchemeAcadyearWise();
            model.put("teachSchmData", daoFactory.getProgramSchemeAcadyearWiseDAO().getTeachingSchemeEditData(instituteId, programSchemeAcadWiseId));
            model.put("teachSchmCompoData", daoFactory.getProgramSchemeAcadyearWiseDAO().getTeachingSubjectComponentEditData(instituteId, programSchemeAcadWiseId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List updateProgramSchemeAcademicyearWise(HttpServletRequest req) {
        ProgramSchemeAcadyearWise programSchemeAcadyearWise = null;
        ProgramSchemeAcadYearDetail programSchemeAcadYearDetail = null;
        List<ProgramSchemeAcadyearWise> programSchemeAcadyearWisesList = new ArrayList<ProgramSchemeAcadyearWise>();
        List<ProgramSchemeAcadYearDetail> programSchemeAcadYearDetailsList = new ArrayList<ProgramSchemeAcadYearDetail>();
        SubjectComponentDetail detail = null;
        SubjectComponentDetailId detailid = null;
        List<SubjectComponentDetail> subjectcomponentdetails = new ArrayList<SubjectComponentDetail>();
        ProgramSchemeAcadyearWiseId programSchemeAcadyearWiseId = null;
        ProgramSchemeAcadYearDetailId programSchemeAcadYearDetailId = null;
        List<ProgramSchemeAcadYearDetail> programSchemeAcadYearDetailListDelete = new ArrayList<ProgramSchemeAcadYearDetail>();
        List<ProgramSchemeAcadYearDetail> programSchemeAcadYearDetailListTemp = new ArrayList<ProgramSchemeAcadYearDetail>();
        BusinessService businessService = null;
        String instituteuniqueid = "";
        String instituteid = "";
        Set set = new HashSet();
        List err = new ArrayList();
        try {
            programSchemeAcadyearWise = new ProgramSchemeAcadyearWise();
            programSchemeAcadyearWiseId = new ProgramSchemeAcadyearWiseId();
            instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            instituteuniqueid = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
            programSchemeAcadyearWiseId.setInstituteid(instituteid);
            String programschemeacademicYearwiseid = req.getParameter("programSchemeAcadWiseId");
            programSchemeAcadyearWiseId.setProgramschemeacadwiseid(programschemeacademicYearwiseid);
            programSchemeAcadyearWise = (ProgramSchemeAcadyearWise) daoFactory.getProgramSchemeAcadyearWiseDAO().findByPrimaryKey(programSchemeAcadyearWiseId);
            programSchemeAcadyearWise.setBasketid(req.getParameter("basketCode") == null ? "" : req.getParameter("basketCode"));
            programSchemeAcadyearWise.setSubjectid(req.getParameter("subId") == null ? "" : req.getParameter("subId"));
            programSchemeAcadyearWise.setDepartmentid(req.getParameter("depCode") == null ? "" : req.getParameter("depCode"));
            programSchemeAcadyearWise.setCredits(req.getParameter("credit") == null ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("credit")));
            programSchemeAcadyearWise.setTotalmarks(req.getParameter("total").equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("total")));
            programSchemeAcadyearWise.setPassingmarks(req.getParameter("passMarks").equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("passMarks")));
            programSchemeAcadyearWise.setDeactive(req.getParameter("deactive") == null ? "Y" : req.getParameter("deactive"));
            programSchemeAcadyearWise.setRegfeeamount(new BigDecimal(req.getParameter("regFeeAmt").equals("") ? "0" : (req.getParameter("regFeeAmt"))));
            programSchemeAcadyearWise.setAuditsubject(req.getParameter("auditsubject") == null ? "N" : "Y");
            programSchemeAcadyearWise.setId(programSchemeAcadyearWiseId);
            err = daoFactory.getProgramSchemeAcadyearWiseDAO().doValidate(programSchemeAcadyearWise, "edit");
            if (err.size() > 0) {
                return err;
            }
            set = new HashSet();
            try {
                programSchemeAcadYearDetailListTemp = (List<ProgramSchemeAcadYearDetail>) daoFactory.getProgramSchemeAcadYearDetailDAO().find("select a from ProgramSchemeAcadYearDetail a where a.id.instituteid='" + instituteid + "' and a.id.programschemeacadwiseid='" + programschemeacademicYearwiseid + "'");
                programSchemeAcadYearDetailListDelete = programSchemeAcadYearDetailListTemp;
                daoFactory.getProgramSchemeAcadYearDetailDAO().deleteChildRecord(programSchemeAcadYearDetailListDelete);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int j = 0; j < Integer.parseInt(req.getParameter("count")); j++) {
                if (req.getParameter("chk" + j) != null) {
                    programSchemeAcadYearDetail = new ProgramSchemeAcadYearDetail();
                    programSchemeAcadYearDetailId = new ProgramSchemeAcadYearDetailId();
                    programSchemeAcadYearDetailId.setInstituteid(instituteid);
                    programSchemeAcadYearDetailId.setProgramschemeacadwiseid(programschemeacademicYearwiseid);
                    programSchemeAcadYearDetailId.setSubjectcomponentid(req.getParameter("subCompCode" + j).equals("") ? "" : req.getParameter("subCompCode" + j));
                    programSchemeAcadYearDetail.setTotalccpmarks(req.getParameter("ccp" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setNoofclassinaweek(req.getParameter("noClass" + j).equals("") ? null : new BigDecimal(req.getParameter("noClass" + j)));
                    programSchemeAcadYearDetail.setNoofhours(req.getParameter("noHrs" + j).equals("") ? null : new BigDecimal(req.getParameter("noHrs" + j)));
                    programSchemeAcadYearDetail.setTotalclasses(req.getParameter("totalClass" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("totalClass" + j)));
                    programSchemeAcadYearDetail.setLtppassingmarks(req.getParameter("ccp" + j).equals("") ? new BigDecimal("0") : new BigDecimal(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setTotalmarks(req.getParameter("ccp" + j).equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("ccp" + j)));
                    programSchemeAcadYearDetail.setPassingmarks(req.getParameter("ccp" + j).equals("") ? Double.parseDouble("0") : Double.parseDouble(req.getParameter("ccp" + j)));
//                            programSchemeAcadYearDetail.setByltp(asobject1.containsKey("applicable") ? asobject1.get("applicable") == null ? null : new BigDecimal(asobject1.get("applicable").toString()) : null);
                    programSchemeAcadYearDetail.setDeactive(req.getParameter("active" + j) == null ? "Y" : "N");
                    programSchemeAcadYearDetail.setId(programSchemeAcadYearDetailId);
                    programSchemeAcadYearDetailsList.add(programSchemeAcadYearDetail);
//                    if (req.getParameter("noHrs" + j) != null && req.getParameter("noClass" + j) != null) {
//                        if (!req.getParameter("noHrs" + j).trim().equals("0") && !req.getParameter("noClass" + j).trim().equals("0")) {
                            set.add(programSchemeAcadYearDetail);
//                        }
//                    }

                    String subjectid = req.getParameter("subId") == null ? "" : req.getParameter("subId");
                    String subjectcomponentid = req.getParameter("subCompCode" + j).equals("") ? "" : req.getParameter("subCompCode" + j);
                    List subComList = daoFactory.getSubjectMasterDAO().checkSubjectComponent(instituteid, subjectid, subjectcomponentid);
                    if (subComList.size() > 0) {
                    } else {
                        detail = new SubjectComponentDetail();
                        detailid = new SubjectComponentDetailId();
                        detailid.setInstituteid(instituteid);
                        detailid.setSubjectcomponentid(subjectcomponentid);
                        detailid.setSubjectid(subjectid);
                        detail.setId(detailid);
                        detail.setDeactive(req.getParameter("active" + j) == null ? "Y" : "N");
                        detail.setLtppassingmarks(new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", "")));
                        detail.setNoofclassinaweek((req.getParameter("noClass" + j + "") != null && !req.getParameter("noClass" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("noClass" + j + "").toString().replace(" ", ""))) : null);
                        detail.setNoofhours((req.getParameter("noHrs" + j + "") != null && !req.getParameter("noHrs" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("noHrs" + j + "").toString().replace(" ", ""))) : null);
                        detail.setPassingmarks((req.getParameter("ccp" + j + "") != null && !req.getParameter("ccp" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", ""))) : null);
                        detail.setTotalccpmarks(new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", "")));
                        detail.setTotalclasses((req.getParameter("totalClass" + j + "") != null && !req.getParameter("totalClass" + j + "").toString().trim().isEmpty()) ? Short.valueOf(req.getParameter("totalClass" + j + "").toString().replace(" ", "")) : null);
                        detail.setTotalmarks((req.getParameter("ccp" + j + "") != null && !req.getParameter("ccp" + j + "").toString().trim().isEmpty()) ? (new BigDecimal(req.getParameter("ccp" + j + "").toString().replace(" ", ""))) : null);
                        daoFactory.getSubjectMasterDAO().add(detail);
                    }
                    programSchemeAcadyearWise.setProgramschemeacadyeardetails(set);
                }
            }
            daoFactory.getProgramSchemeAcadyearWiseDAO().saveOrUpdate(programSchemeAcadyearWise);
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;
    }

    public List deleteProgramScheme(HttpServletRequest request) {
        List err = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split("~@~");
                daoFactory.getProgramSchemeAcadyearWiseDAO().delete(daoFactory.getProgramSchemeAcadyearWiseDAO().findByPrimaryKey(new ProgramSchemeAcadyearWiseId(id[0], id[1])));
            }
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add("Error");
        }
        return err;
    }
}
