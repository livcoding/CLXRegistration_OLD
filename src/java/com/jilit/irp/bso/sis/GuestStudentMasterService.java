package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.GuestStudentMasterIService;
import com.jilit.irp.log.BusinessLogger;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dao.LogMantainFlag;
import com.jilit.irp.persistence.dto.Academicyear;
import com.jilit.irp.persistence.dto.GuestStudentAddress;
import com.jilit.irp.persistence.dto.GuestStudentMaster;
import com.jilit.irp.persistence.dto.GuestStudentPhoto;
import com.jilit.irp.persistence.dto.GuestStudentPhotoFetch;
import com.jilit.irp.persistence.dto.InstituteMaster;
//import com.jilit.irp.persistence.dto.InstituteMaster;

import com.jilit.irp.util.JIRPSession;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author priya.sharma
 */
@Service
public class GuestStudentMasterService implements GuestStudentMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Autowired
    BusinessLogger businessLogger;

//    public List getbranchForFilteration(HttpServletRequest req) {
//        List rtnlist = new ArrayList();
//        try {
//            String program[] = req.getParameter("programId").split("~@~");
//            String programId = program[0];
//            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
//            List<Object[]> ccList = daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programId);
//            if (ccList != null && ccList.size() > 0) {
//                rtnlist.add(ccList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rtnlist;
//    }
//    public List getSemester(HttpServletRequest req) {
//        List semester = new ArrayList();
//        try {
//            String acadYear = req.getParameter("academicYear");
//            String branch[] = req.getParameter("branch").split("~@~");
//            String branchId = branch[0];
//            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
//            semester = daoFactory.getProgramMinMaxLimitDAO().getSemester(branchId, acadYear, instituteid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return semester;
//    }
//    public void getInstituteName(ModelMap model) {
//        List<InstituteMaster> list = null;
//        try {
//            list = (List<InstituteMaster>) daoFactory.getInstituteMasterDAO().findAll();
//            model.addAttribute("institute", list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void getAcademicYears(ModelMap model) {
        List list = null;
        try {
            String instId = jirpession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getAcademicYearDAO().getAcademicYear(instId);
            model.addAttribute("academic", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getAllStudentMaster(HttpServletRequest req) {
        List retList = new ArrayList();
        String instituteid = "";
        try {
            instituteid = req.getParameter("fromInstituteId");

            retList = daoFactory.getGuestStudentMasterDAO().studentListData(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public void personalInfoSave(HttpServletRequest req, ModelMap m) {
        BusinessService businessService = null;

        try {
            GuestStudentMaster master = new GuestStudentMaster();
            businessService = new BusinessService(daoFactory, true);
            String guestStudentId = "";
            guestStudentId = businessService.generateId("GuestStudentID", jirpession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false);
            m.put("guestStudentId", guestStudentId);
            master.setInstituteid(jirpession.getJsessionInfo().getSelectedinstituteid());
            master.setGuestStudentid(guestStudentId);
            master.setAcadyear(req.getParameter("academicYear"));
            master.setFrominstitutename(req.getParameter("frominstititename"));
            master.setEnrollmentno(req.getParameter("enrollmentNo"));
            master.setName(req.getParameter("name"));
            String dob = req.getParameter("dob");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            master.setDateofbirth(date);
            master.setFathersname(req.getParameter("fathersName"));
            master.setMothersname(req.getParameter("mothersName"));
            master.setNationality(req.getParameter("nationality"));
            master.setBloodgroup(req.getParameter("bloodGroup"));
            master.setProgramcode(req.getParameter("program"));
            master.setBranchcode(req.getParameter("branch"));
            master.setSubsectioncode(req.getParameter("subSect"));
            master.setSectioncode(req.getParameter("sectCode"));

            master.setReferencedetail(req.getParameter("referenceDetail"));
            master.setGender(req.getParameter("gender"));

            if (!req.getParameter("sem").equals("") && req.getParameter("sem") != null) {
                master.setStynumber(Byte.parseByte(req.getParameter("sem")));
            } else {
                master.setStynumber(null);
            }
            businessService.insertInIdGenerationControl(master);
            m.put("result", "Success");
            if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "SAVE", "", "", master, "S", new Date(), "Value After Save");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
            businessService.rollback();
        } finally {
            businessService.closeSession();
        }
    }

    @Override
    public void addressDetailSave(HttpServletRequest req, ModelMap m) {
        BusinessService businessService = null;
        try {
            String guestStudentId = "";
            guestStudentId = req.getParameter("student_pk2");
            GuestStudentAddress address = new GuestStudentAddress();
            address.setStudentid(guestStudentId);
            address.setCaddress1(req.getParameter("address1"));
            address.setCaddress2(req.getParameter("address2"));
            address.setCaddress3(req.getParameter("address3"));
            address.setCcityname(req.getParameter("city"));
            address.setCstatename(req.getParameter("state"));
            address.setCcountryname(req.getParameter("country"));
            address.setCpin(Integer.parseInt(req.getParameter("pin")));
            address.setPaddress1(req.getParameter("paddress1"));
            address.setPaddress2(req.getParameter("paddress2"));
            address.setPaddress3(req.getParameter("pAddress3"));
            address.setPcityname(req.getParameter("city"));
            address.setPstatename(req.getParameter("pState"));
            address.setPcountryname(req.getParameter("pCountry"));
            address.setPpin(Integer.parseInt(req.getParameter("pPIN")));
            address.setPcountryname(req.getParameter("pCountry"));
            address.setPemailid(req.getParameter("parentEmailId"));
            address.setPmobileno(req.getParameter("parentMobileNo"));
            address.setSemailid(req.getParameter("studentEmailId"));
            address.setSmobileno(req.getParameter("studentMobileNo"));
            daoFactory.getStudentAddressDAO().saveOrUpdate(address);
            GuestStudentMaster master = new GuestStudentMaster();
            master = (GuestStudentMaster) daoFactory.getGuestStudentMasterDAO().findByPrimaryKey(guestStudentId);
            String fromDate = req.getParameter("fromdate");
            Date validFrom = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
            master.setValidfrom(validFrom);
            String uptoDate = req.getParameter("todate");
            Date validUpto = new SimpleDateFormat("dd/MM/yyyy").parse(uptoDate);
            master.setValidupto(validUpto);
            master.setParentloginid(req.getParameter("parentLoginId"));
            master.setStudentloginid(req.getParameter("studentLoginId"));
            daoFactory.getGuestStudentMasterDAO().saveOrUpdate(master);
            m.put("result", "Success");
            if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "SAVE", "", "", address, "S", new Date(), "Value After Save");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
        }
    }

    @Override
    public void studentPhotoSignatureSave(HttpServletRequest req, ModelMap m, CommonsMultipartFile[] attachment, CommonsMultipartFile[] sign) {
        GuestStudentPhoto studentphoto = new GuestStudentPhoto();
        try {
            String guestStudentId = "";
            guestStudentId = req.getParameter("student_pk3");
            studentphoto.setGueststudentid(guestStudentId);
            if (attachment != null && attachment.length > 0) {
                for (CommonsMultipartFile aFile : attachment) {
                    if (!aFile.getOriginalFilename().equals("")) {
                        if (aFile.getContentType().equalsIgnoreCase("image/jpg") || aFile.getContentType().equalsIgnoreCase("image/jpeg") || aFile.getContentType().equalsIgnoreCase("image/png")) {
                            byte[] studentphotoimg = aFile.getBytes();
                            studentphoto.setSphoto(studentphotoimg);
                        }
                    }
                }
            }
            if (sign != null && sign.length > 0) {
                for (CommonsMultipartFile aSign : sign) {
                    if (!aSign.getOriginalFilename().equals("")) {
                        if (aSign.getContentType().equalsIgnoreCase("image/jpg") || aSign.getContentType().equalsIgnoreCase("image/jpeg") || aSign.getContentType().equalsIgnoreCase("image/png")) {
                            byte[] studentSign = aSign.getBytes();
                            studentphoto.setSsignature(studentSign);
                        }
                    }
                }
            }
            daoFactory.getStudentPhotoDAO().saveOrUpdate(studentphoto);
            m.put("result", "Success");
            if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "SAVE", "", "", studentphoto, "S", new Date(), "Value After Save");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
        }
    }

    public void personalInfoUpdate(HttpServletRequest req, ModelMap m) {
        BusinessService businessService = null;
        try {
            GuestStudentMaster master = new GuestStudentMaster();
            String guestStudentId = "";
            guestStudentId = req.getParameter("student_pk1");
            m.put("guestStudentId", guestStudentId);
            master = (GuestStudentMaster) daoFactory.getGuestStudentMasterDAO().findByPrimaryKey(guestStudentId);
            if (master != null) {
                master.setInstituteid(jirpession.getJsessionInfo().getSelectedinstituteid());
                master.setGuestStudentid(guestStudentId);
                master.setAcadyear(req.getParameter("acadYear"));
                master.setFrominstitutename(req.getParameter("frominstititename"));
                master.setEnrollmentno(req.getParameter("enrollmentNo"));
                master.setName(req.getParameter("name"));
                String dob = req.getParameter("dob");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
                master.setDateofbirth(date);
                master.setFathersname(req.getParameter("fathersName"));
                master.setMothersname(req.getParameter("mothersName"));
                master.setProgramcode(req.getParameter("program"));
                master.setBranchcode(req.getParameter("branch"));
                master.setNationality(req.getParameter("nationality"));
                master.setBloodgroup(req.getParameter("bloodgroup"));
                master.setSubsectioncode(req.getParameter("subSect"));
                master.setSectioncode(req.getParameter("sectCode"));
                // master.setStynumber(Byte.parseByte(req.getParameter("sem")));

                if (!req.getParameter("sem").equals("")) {
                    master.setStynumber(Byte.parseByte(req.getParameter("sem")));
                } else {
                    master.setStynumber(null);
                }
                master.setReferencedetail(req.getParameter("referenceDetail"));
                master.setGender(req.getParameter("gender"));
                daoFactory.getStudentMasterDAO().update(master);
                m.put("result", "Success");

                if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                    businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "UPDATE", "", "", master, "S", new Date(), "Value After Save");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
        }
    }

//    public void addressDetailUpdate(HttpServletRequest req, ModelMap m) {
//        BusinessService businessService = null;
//       try {
//            String guestStudentId = "";
//            guestStudentId = req.getParameter("student_pk2");
//            GuestStudentAddress address = new GuestStudentAddress();            //address = (GuestStudentAdddress) daoFactory.getStudentAddressDAO().findByPrimaryKey(studentId);            
//            address.setGueststudentid(guestStudentId);
//            address.setCaddress1(req.getParameter("address1"));
//            address.setCaddress2(req.getParameter("address2"));
//            address.setCaddress3(req.getParameter("address3"));
//            address.setCcityname(req.getParameter("city"));
//            address.setCstatename(req.getParameter("state"));
//            address.setCcountryname(req.getParameter("country"));
//            address.setCpin(Integer.parseInt(req.getParameter("pin")));
//            address.setPaddress1(req.getParameter("paddress1"));
//            address.setPaddress2(req.getParameter("paddress2"));
//            address.setPaddress3(req.getParameter("pAddress3"));
//            address.setPcityname(req.getParameter("city"));
//            address.setPstatename(req.getParameter("pState"));
//            address.setPcountryname(req.getParameter("pCountry"));
//            address.setPpin(Integer.parseInt(req.getParameter("pPIN")));
//            address.setPcountryname(req.getParameter("pCountry"));
//            address.setPemailid(req.getParameter("parentEmailId"));
//            address.setPmobileno(req.getParameter("parentMobileNo"));
//            address.setSemailid(req.getParameter("studentEmailId"));
//            address.setSmobileno(req.getParameter("studentMobileNo"));
////            address.setPstatename(req.getParameter("pState"));
//            daoFactory.getStudentAddressDAO().saveOrUpdate(address);
//            GuestStudentMaster master = new GuestStudentMaster();
//            String fromDate = req.getParameter("validFromId");
//            Date validFrom = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
//            master.setValidfrom(validFrom);
//            String uptoDate = req.getParameter("validUptoId");
//            Date validUpto = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
//            master.setValidfrom(validUpto);
//            master.setParentloginid(req.getParameter("parentLoginId"));
//            master.setStudentloginid(req.getParameter("studentLoginId"));
//            m.put("result", "Success");
//            if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
//                businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "UPDATE", "", "", address, "S", new Date(), "Value After Save");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            m.put("result", "Error");
//        }
//    }
    public void addressDetailUpdate(HttpServletRequest req, ModelMap m) {
        try {
            String guestStudentId = "";
            guestStudentId = req.getParameter("student_pk2");
            GuestStudentAddress address = new GuestStudentAddress();
            address = (GuestStudentAddress) daoFactory.getGuestStudentAddressDAO().findByPrimaryKey(guestStudentId);
            if (address != null) {
                address.setGueststudentid(guestStudentId);
                address.setCaddress1(req.getParameter("address1"));
                address.setCaddress2(req.getParameter("address2"));
                address.setCaddress3(req.getParameter("address3"));
                address.setCcityname(req.getParameter("city"));
                address.setCstatename(req.getParameter("state"));
                address.setCcountryname(req.getParameter("country"));
                address.setCpin(Integer.parseInt(req.getParameter("pin")));
                address.setPaddress1(req.getParameter("paddress1"));
                address.setPaddress2(req.getParameter("paddress2"));
                address.setPaddress3(req.getParameter("pAddress3"));
                address.setPcityname(req.getParameter("city"));
                address.setPstatename(req.getParameter("pState"));
                address.setPcountryname(req.getParameter("pCountry"));
                address.setPpin(Integer.parseInt(req.getParameter("pPIN")));
                address.setPemailid(req.getParameter("parentEmailId"));
                address.setPmobileno(req.getParameter("parentMobileNo"));
                address.setSemailid(req.getParameter("studentEmailId"));
                address.setSmobileno(req.getParameter("studentMobileNo"));
//            address.setPstatename(req.getParameter("pState"));         
                GuestStudentMaster master = new GuestStudentMaster();
                master = (GuestStudentMaster) daoFactory.getGuestStudentMasterDAO().findByPrimaryKey(guestStudentId);
                String fromDate = req.getParameter("validfrom");
                Date validFrom = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
                master.setValidfrom(validFrom);
                String uptoDate = req.getParameter("validupto");
                Date validUpto = new SimpleDateFormat("dd/MM/yyyy").parse(uptoDate);
                master.setValidupto(validUpto);
                master.setParentloginid(req.getParameter("parentLoginId"));
                master.setStudentloginid(req.getParameter("studentLoginId"));
                daoFactory.getGuestStudentAddressDAO().update(address);
                daoFactory.getGuestStudentMasterDAO().saveOrUpdate(master);
                m.put("result", "Success");
                if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                    businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "UPDATE", "", "", address, "S", new Date(), "Value After Update");
                }
            } else {
                address = new GuestStudentAddress();
                address.setGueststudentid(guestStudentId);
                address.setCaddress1(req.getParameter("address1"));
                address.setCaddress2(req.getParameter("address2"));
                address.setCaddress3(req.getParameter("address3"));
                address.setCcityname(req.getParameter("city"));
                address.setCstatename(req.getParameter("state"));
                address.setCcountryname(req.getParameter("country"));
                address.setCpin(Integer.parseInt(req.getParameter("pin")));
                address.setPaddress1(req.getParameter("paddress1"));
                address.setPaddress2(req.getParameter("paddress2"));
                address.setPaddress3(req.getParameter("pAddress3"));
                address.setPcityname(req.getParameter("city"));
                address.setPstatename(req.getParameter("pState"));
                address.setPcountryname(req.getParameter("pCountry"));
                address.setPpin(Integer.parseInt(req.getParameter("pPIN")));
                address.setPemailid(req.getParameter("parentEmailId"));
                address.setPmobileno(req.getParameter("parentMobileNo"));
                address.setSemailid(req.getParameter("studentEmailId"));
                address.setSmobileno(req.getParameter("studentMobileNo"));
//            address.setPstatename(req.getParameter("pState"));   
                daoFactory.getGuestStudentAddressDAO().saveOrUpdate(address);
                GuestStudentMaster master = new GuestStudentMaster();
                master = (GuestStudentMaster) daoFactory.getGuestStudentMasterDAO().findByPrimaryKey(guestStudentId);
                String fromDate = req.getParameter("validFromId");
                Date validFrom = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);
                master.setValidfrom(validFrom);
                String uptoDate = req.getParameter("validUptoId");
                Date validUpto = new SimpleDateFormat("dd/MM/yyyy").parse(uptoDate);
                master.setValidfrom(validUpto);
                master.setParentloginid(req.getParameter("parentLoginId"));
                master.setStudentloginid(req.getParameter("studentLoginId"));
                daoFactory.getGuestStudentMasterDAO().saveOrUpdate(master);
                m.put("result", "Success");
                if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                    businessLogger.saveAuditLogs("CLXRegistration", "Guest Student Master", "GuestStudentMasterService", "UPDATE", "", "", address, "S", new Date(), "Value After Update");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
        }
    }

    public void editStudentMaster(ModelMap model, HttpServletRequest req) {
        try {
            String instituteId = jirpession.getJsessionInfo().getSelectedinstituteid();
            String data = req.getParameter("pk");
            GuestStudentMaster master = new GuestStudentMaster();
            master = (GuestStudentMaster) daoFactory.getGuestStudentMasterDAO().findByPrimaryKey(data);
            model.put("personalInfo", master);
            GuestStudentAddress address = new GuestStudentAddress();
            address = (GuestStudentAddress) daoFactory.getGuestStudentAddressDAO().findByPrimaryKey(data);
            model.put("addressInfo", address);
            List list = daoFactory.getStudentPhotoDAO().getGuestStudentPhotoSign(data);
            if (list != null && list.size() > 0) {
                Blob b = (Blob) ((GuestStudentPhotoFetch) list.get(0)).getSphoto();
                if (b != null) {
                    byte[] bytes = b.getBytes(1, (int) b.length());
                    String photo = Base64.encode(bytes);
                    model.put("photo", photo);
                }
                Blob s = (Blob) ((GuestStudentPhotoFetch) list.get(0)).getSsignature();
                if (s != null) {
                    byte[] signBytes = s.getBytes(1, (int) s.length());
                    String sign = Base64.encode(signBytes);
                    model.put("sign", sign);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void studentPhotoSignatureUpdate(HttpServletRequest request, ModelMap m, CommonsMultipartFile[] attachment, CommonsMultipartFile[] sign) {
        GuestStudentPhoto studentphoto = new GuestStudentPhoto();
        try {
            String studentId = "";
            studentId = request.getParameter("student_pk3");
            studentphoto = (GuestStudentPhoto) daoFactory.getStudentPhotoDAO().findByPrimaryKey(studentId);
            if (studentphoto != null) {
                studentphoto.setGueststudentid(studentId);
                if (attachment != null && attachment.length > 0) {
                    for (CommonsMultipartFile aFile : attachment) {
                        if (!aFile.getOriginalFilename().equals("")) {
                            if (aFile.getContentType().equalsIgnoreCase("image/jpg") || aFile.getContentType().equalsIgnoreCase("image/jpeg") || aFile.getContentType().equalsIgnoreCase("image/png")) {
                                byte[] studentphotoimg = aFile.getBytes();
                                studentphoto.setSphoto(studentphotoimg);
                            }
                        }
                    }
                }
                if (sign != null && sign.length > 0) {
                    for (CommonsMultipartFile aSign : sign) {
                        if (!aSign.getOriginalFilename().equals("")) {
                            if (aSign.getContentType().equalsIgnoreCase("image/jpg") || aSign.getContentType().equalsIgnoreCase("image/jpeg") || aSign.getContentType().equalsIgnoreCase("image/png")) {
                                byte[] studentSign = aSign.getBytes();
                                studentphoto.setSsignature(studentSign);
                            }
                        }
                    }
                }
                daoFactory.getStudentPhotoDAO().update(studentphoto);
            } else {
                studentphoto = new GuestStudentPhoto();
                studentphoto.setGueststudentid(studentId);
                if (attachment != null && attachment.length > 0) {
                    for (CommonsMultipartFile aFile : attachment) {
                        if (!aFile.getOriginalFilename().equals("")) {
                            if (aFile.getContentType().equalsIgnoreCase("image/jpg") || aFile.getContentType().equalsIgnoreCase("image/jpeg") || aFile.getContentType().equalsIgnoreCase("image/png")) {
                                byte[] studentphotoimg = aFile.getBytes();
                                studentphoto.setSphoto(studentphotoimg);
                            }
                        }
                    }
                }
                if (sign != null && sign.length > 0) {
                    for (CommonsMultipartFile aSign : sign) {
                        if (!aSign.getOriginalFilename().equals("")) {
                            if (aSign.getContentType().equalsIgnoreCase("image/jpg") || aSign.getContentType().equalsIgnoreCase("image/jpeg") || aSign.getContentType().equalsIgnoreCase("image/png")) {
                                byte[] studentSign = aSign.getBytes();
                                studentphoto.setSsignature(studentSign);
                            }
                        }
                    }
                }
                daoFactory.getStudentPhotoDAO().saveOrUpdate(studentphoto);
            }
            m.put("result", "Success");
            if (LogMantainFlag.GuestStudentMasterService.equals("Y")) {
                businessLogger.saveAuditLogs("CLXAdmin", "Student Master", "StudentMaster", "UPDATE", "", "", studentphoto, "S", new Date(), "Value After Update");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.put("result", "Error");
        }
    }

    @Override
    public void getGuestInstituteName(ModelMap model) {
        List<GuestStudentMaster> list = null;
        try {
            list = (List<GuestStudentMaster>) daoFactory.getGuestStudentMasterDAO().getGuestInstituteName();
            model.addAttribute("institute", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
