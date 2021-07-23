package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.OfferSubjectTaggingIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingDetail;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingDetailId;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingId;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.SubjectComponent;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author deepak.gupta
 */
@Service
public class OfferSubjectTaggingService implements OfferSubjectTaggingIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    public List getOfferSubjectTaggingListData(HttpServletRequest request) {
        List list = null;
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = request.getParameter("semesterCode");
        try {
        list = daoFactory.getOfferedODSubjectTaggingDAO().getOfferSubjectTaggingGridData(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addOfferSubjectTagging(ModelMap model) {
        List registrationList = null;
        List departmentList = null;
        List subjectTypeList = null;
        List<SubjectComponent> componentList = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            registrationList = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegCodeForOST(instituteid);
            departmentList = (List<Object[]>) daoFactory.getDepartmentMasterDAO().getDepartmentCode();
            componentList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
            subjectTypeList = (List<Object[]>) daoFactory.getSubjectTypeMasterDAO().getSubjectTypeCode(instituteid);
            model.addAttribute("componentList", componentList);
            model.addAttribute("deptList", departmentList);
            model.addAttribute("semester", registrationList);
            model.addAttribute("subTypeList", subjectTypeList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getOldSubjectCode(HttpServletRequest request) {
        List oldSubjectList = null;
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = request.getParameter("registrationid").split("~@~")[0];
            String subjectid = request.getParameter("subjectid");
            String department = request.getParameter("department");
            oldSubjectList = (List<Object[]>) daoFactory.getDepartmentSubjectTaggingDAO().getSubjectCodeUsingDepartmentBased(instituteid, department, subjectid, registrationid," ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return oldSubjectList;
    }

    @Override
    public List getSubjectComponent(HttpServletRequest request) {
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String subjectid = request.getParameter("subjectid").split("~@~")[0];
        List list = null;
        try {
            list = (List) daoFactory.getSubjectComponentDAO().getComponentIdOfSubject(instituteid, subjectid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getFacultyList(HttpServletRequest request) {
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String departmentid = request.getParameter("departmentid");
        String facultyFrom = request.getParameter("facultyFrom");
        List list = null;
        try {
            list = (List) daoFactory.getV_StaffDAO().getStaff(departmentid, instituteid, facultyFrom);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List addNewOfferSubjectTagging(HttpServletRequest request) {
        OfferedODSubjectTagging dto = null;
        OfferedODSubjectTaggingId id = null;
        OfferedODSubjectTaggingDetail dDto = null;
        OfferedODSubjectTaggingDetailId dId = null;
        List<OfferedODSubjectTaggingDetail> offeredODSubjectTaggingDetailList = new ArrayList<OfferedODSubjectTaggingDetail>();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        BusinessService businessService = new BusinessService(daoFactory);
        String registrationid = request.getParameter("semCode").split("~@~")[0];
        Set set = new HashSet();
        List list = null;
        try {
            dto = new OfferedODSubjectTagging();
            id = new OfferedODSubjectTaggingId();
            id.setInstituteid(instituteid);
            id.setRegistrationid(registrationid);
            String offersubjectid = businessService.generateId("OfferedODSubjectTaggingID", jirpession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
            id.setOffersubjectid(offersubjectid);
            dto.setId(id);
            dto.setOdsubjectid(request.getParameter("oldSubCode"));
            dto.setSubjecttypeid(request.getParameter("subType"));
            dto.setDepartmentid(request.getParameter("deptCode"));
            dto.setCurrentsubjectid(request.getParameter("currSubCode").split("~@~")[0]);
            dto.setAuditsubject(request.getParameter("auditsubject"));
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            dto.setCredits(Byte.decode(request.getParameter("credit")));
            list = daoFactory.getOfferedODSubjectTaggingDAO().doValidate(dto, "normal");
            if (list.size() > 0) {
                businessService.rollback();
                return list;
            }
            set = new HashSet();
            for (int i = 1; i <= 3; i++) {
                if (request.getParameter("chk" + i + "") != null) {
                    dDto = new OfferedODSubjectTaggingDetail();
                    dId = new OfferedODSubjectTaggingDetailId();
                    String pks[] = request.getParameter("ids" + i + "").split(":");
                    dId.setInstituteid(pks[0].toString());
                    dId.setOffersubjectid(offersubjectid);
                    dId.setSubjectcomponentid(pks[1].toString());
                    dId.setRegistrationid(registrationid);
                    dDto.setId(dId);
                    dDto.setTotalccpmarks(new BigDecimal(request.getParameter("total" + i + "")));
                    dDto.setLtppassingmarks(new BigDecimal(request.getParameter("passing" + i + "")));
                    dDto.setNoofclassinaweek(new BigDecimal(request.getParameter("noofclass" + i + "")));
                    dDto.setTotalclasses(new BigDecimal(request.getParameter("totalclass" + i + "")));
                    dDto.setNoofhours(new BigDecimal(request.getParameter("noofhours" + i + "")));
                    if (!request.getParameter("facultyid" + i + "").equals("")) {
                        dDto.setStaffid(request.getParameter("facultyid" + i + "").split("~@~")[0]);
                        dDto.setStafftype(request.getParameter("facultyid" + i + "").split("~@~")[1]);
                    }
                    dDto.setByltp(new BigDecimal(0));
                    if (request.getParameter("deactive" + i + "") != null) {
                        dDto.setDeactive("N");
                    } else {
                        dDto.setDeactive("Y");
                    }
                    offeredODSubjectTaggingDetailList.add(dDto);
                    set.add(dDto);
                }
            }
            dto.setOfferedodsubjecttaggingdetails(set);
            businessService.insertInIdGenerationControl(dto);
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        } finally {
            businessService.closeSession();
        }
        return list;
    }

    public List getAllSubjectComponent() {
        List<SubjectComponent> retList = new ArrayList<SubjectComponent>();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        try {
            retList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public ModelMap getAllOfferSubjectTaggingData(ModelMap mm, HttpServletRequest request) {
        List<RegistrationMaster> regList = null;
        List departmentList = null;
        List subjectList = null;
        List<SubjectComponent> componentList = null;
        List subjectTypeList = null;
        List detailData = null;
        try {
            OfferedODSubjectTagging dto = new OfferedODSubjectTagging();
            OfferedODSubjectTaggingId id = new OfferedODSubjectTaggingId();
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            regList = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            departmentList = (List<Object[]>) daoFactory.getDepartmentMasterDAO().getDepartmentCode();
            //subjectList = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSubjectCodeDeactive(instituteid);
            subjectTypeList = (List<Object[]>) daoFactory.getSubjectTypeMasterDAO().getSubjectTypeCode(instituteid);
            componentList = (List<SubjectComponent>) daoFactory.getSubjectComponentDAO().findAll(instituteid);
            String pk = request.getParameter("pk");
            String sr[] = pk.split("~@~");
            id.setInstituteid(sr[0]);
            id.setRegistrationid(sr[1]);
            id.setOffersubjectid(sr[2]);
            dto = (OfferedODSubjectTagging) daoFactory.getOfferedODSubjectTaggingDAO().findByPrimaryKey(id);
            detailData = (List) daoFactory.getOfferedODSubjectTaggingDetailDAO().getOfferSubjectTagginfDetaildata(sr[0], sr[1], sr[2]);
            List oldSubjectList = (List<Object[]>) daoFactory.getDepartmentSubjectTaggingDAO().getSubjectCodeUsingDepartmentBased(instituteid, dto.getDepartmentid(), " ", dto.getId().getRegistrationid(),dto.getCurrentsubjectid());
            List stafflist = (List) daoFactory.getV_StaffDAO().getStaff(dto.getDepartmentid(), instituteid, "SD");
            mm.addAttribute("data", dto);
            mm.addAttribute("detailDto", detailData);
            mm.addAttribute("componentList", componentList);
            mm.addAttribute("deptList", departmentList);
            mm.addAttribute("regList", regList);
            mm.addAttribute("subTypeList", subjectTypeList);
            mm.addAttribute("subList", oldSubjectList);
            mm.addAttribute("stafflist", stafflist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mm;
    }

    @Override
    public List updateOfferSubjectTagging(HttpServletRequest request) {
        OfferedODSubjectTagging dto = null;
        OfferedODSubjectTaggingId id = null;
        OfferedODSubjectTaggingDetail dDto = null;
        OfferedODSubjectTaggingDetailId dId = null;
        List<OfferedODSubjectTaggingDetail> offeredODSubjectTaggingDetailList = new ArrayList<OfferedODSubjectTaggingDetail>();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = request.getParameter("regId");
        String offersubjectid = request.getParameter("offerSubjectId");
        Set set = new HashSet();
        List list = null;
        try {
            dto = new OfferedODSubjectTagging();
            id = new OfferedODSubjectTaggingId();
            id.setInstituteid(instituteid);
            id.setRegistrationid(registrationid);
            id.setOffersubjectid(offersubjectid);
            dto.setId(id);
            dto.setOdsubjectid(request.getParameter("oldSubCode"));
            dto.setSubjecttypeid(request.getParameter("subType"));
            dto.setDepartmentid(request.getParameter("deptCode"));
            dto.setCurrentsubjectid(request.getParameter("currSubCode").split("~@~")[0]);
            dto.setAuditsubject(request.getParameter("auditsubject"));
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            dto.setCredits(Byte.decode(request.getParameter("credit")));
            list = daoFactory.getOfferedODSubjectTaggingDAO().doValidate(dto, "edit");
            if (list.size() > 0) {
                return list;
            }
            set = new HashSet();
            for (int i = 1; i <= 3; i++) {
                dDto = new OfferedODSubjectTaggingDetail();
                dId = new OfferedODSubjectTaggingDetailId();
                if (request.getParameter("chk" + i + "") != null) {
                    dId.setInstituteid(instituteid);
                    dId.setOffersubjectid(offersubjectid);
                    dId.setSubjectcomponentid(request.getParameter("chk" + i + "").toString());
                    dId.setRegistrationid(registrationid);
                    dDto.setId(dId);
                    dDto.setTotalccpmarks(new BigDecimal(request.getParameter("total" + i + "")));
                    dDto.setLtppassingmarks(new BigDecimal(request.getParameter("passing" + i + "")));
                    dDto.setNoofclassinaweek(new BigDecimal(request.getParameter("noofclass" + i + "")));
                    dDto.setTotalclasses(new BigDecimal(request.getParameter("totalclass" + i + "")));
                    dDto.setNoofhours(new BigDecimal(request.getParameter("noofhours" + i + "")));
                    dDto.setByltp(new BigDecimal(0));
                    if (!request.getParameter("facultyid" + i + "").equals("")) {
                        dDto.setStaffid(request.getParameter("facultyid" + i + "").split("~@~")[0]);
                        dDto.setStafftype(request.getParameter("facultyid" + i + "").split("~@~")[1]);
                    }
                    if (request.getParameter("deactive" + i) != null) {
                        dDto.setDeactive("N");
                    } else {
                        dDto.setDeactive("Y");
                    }
                    offeredODSubjectTaggingDetailList.add(dDto);
                    set.add(dDto);
                } else {
                    dId.setInstituteid(instituteid);
                    dId.setOffersubjectid(offersubjectid);
                    dId.setSubjectcomponentid(request.getParameter("subjectComId" + i));
                    dId.setRegistrationid(registrationid);
                    dDto = (OfferedODSubjectTaggingDetail) daoFactory.getOfferedODSubjectTaggingDetailDAO().findByPrimaryKey(dId);
                    if (dDto != null) {
                        daoFactory.getOfferedODSubjectTaggingDetailDAO().delete(dDto);
                    }
                }
            }
            dto.setOfferedodsubjecttaggingdetails(set);
            daoFactory.getOfferedODSubjectTaggingDAO().saveOrUpdate(dto);
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List deleteOfferSubjectTagging(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split("~@~");
                daoFactory.getOfferedODSubjectTaggingDAO().delete(daoFactory.getOfferedODSubjectTaggingDAO().findByPrimaryKey(new OfferedODSubjectTaggingId(id[0], id[1], id[2])));
            }
            list = new ArrayList<String>();
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public void getRegistrationList(Model model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
