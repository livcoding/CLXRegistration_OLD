/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import com.jilit.irp.persistence.dto.SummerRegistrationSetup;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupId;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupDet;
import com.jilit.irp.persistence.dto.SummerRegistrationSetupDetId;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.jilit.irp.iservice.SummerRegistrationSetupIService;

/**
 *
 * @author Malkeet Singh
 */
@Service
public class SummerRegistrationSetupService implements SummerRegistrationSetupIService {

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

    public List getSummerRegistrationSetupGridData(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programId");
            list = (List<Object[]>) daoFactory.getSummerRegistrationSetupDAO().getGridData(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStyNumber(HttpServletRequest req) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = req.getParameter("programId");
            List<String> programlist = new ArrayList<String>();
            String plist[] = programid.split(",");
            for (int i = 0; i < plist.length; i++) {
                programlist.add(plist[i]);
            }
            list = (List<Object[]>) daoFactory.getSummerRegistrationSetupDAO().getStyNumber(instituteid, programlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List saveSummerRegistrationSetup(HttpServletRequest req) {
        List list = new ArrayList();
        SummerRegistrationSetup dto = new SummerRegistrationSetup();
        SummerRegistrationSetupId id = new SummerRegistrationSetupId();
        SummerRegistrationSetupDet ddto = new SummerRegistrationSetupDet();
        SummerRegistrationSetupDetId did = new SummerRegistrationSetupDetId();
        int save = 0;
        try {
            //Ids Part..
            String instiuteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programCode[] = req.getParameterValues("programId");
            String styNumber[] = req.getParameterValues("stynumber");
            //summer regsitration setup
            double maxCredit = Double.parseDouble(req.getParameter("maxCredit"));
            int maxSubject = Integer.parseInt(req.getParameter("maxSubject"));
            int maxSubjectProject = Integer.parseInt(req.getParameter("maxSubjectProject"));
            int maxSubjectIndusCase = Integer.parseInt(req.getParameter("maxSubjectIndusCase"));

            for (int i = 0; i < programCode.length; i++) {
                for (int j = 0; j < styNumber.length; j++) {
                    id.setInstituteid(instiuteid);
                    id.setProgramid(programCode[i]);
                    id.setStynumber(Byte.parseByte(styNumber[j]));
                    dto = (SummerRegistrationSetup) daoFactory.getSummerRegistrationSetupDAO().findByPrimaryKey(id);
                    if (dto != null) {
                    } else {
                        dto = new SummerRegistrationSetup();
                        dto.setId(id);
                        dto.setMaxcredit(maxCredit);
                        dto.setMaxsubjects(maxSubject);
                        dto.setMaxprojectsubjects(maxSubjectProject);
                        dto.setInduscasemaxsubj(maxSubjectIndusCase);
                        daoFactory.getSummerRegistrationSetupDAO().add(dto);
                        for (int k = 1; k <= 2; k++) {
                            if (req.getParameter("chk" + k + "") != null) {
                                String majororminor = req.getParameter("majorOrMinor" + k + "");
                                String lpcriteriatype = req.getParameter("lpCriteriaType" + k + "");
                                double maxcredit = Double.parseDouble(req.getParameter("maxCredit" + k + "").equals("") ? "0" : req.getParameter("maxCredit" + k + ""));
                                int maxlsubjectscredit = Integer.parseInt(req.getParameter("maxLsubjectCredit" + k + "").equals("") ? "0" : req.getParameter("maxLsubjectCredit" + k + ""));
                                int maxpsubjectscredit = Integer.parseInt(req.getParameter("maxPsubjectCredit" + k + "").equals("") ? "0" : req.getParameter("maxPsubjectCredit" + k + ""));
                                int maxltheorysubjects = Integer.parseInt(req.getParameter("maxLtheorySubject" + k + "").equals("") ? "0" : req.getParameter("maxLtheorySubject" + k + ""));
                                int maxplabsubjects = Integer.parseInt(req.getParameter("maxPlabSubject" + k + "").equals("") ? "0" : req.getParameter("maxPlabSubject" + k + ""));
                                did.setInstituteid(instiuteid);
                                did.setProgramid(programCode[i]);
                                did.setStynumber(Byte.parseByte(styNumber[j]));
                                did.setMajororminor(majororminor);
                                ddto.setId(did);
                                ddto.setLpcriteriatype(lpcriteriatype);
                                ddto.setMaxcredit(maxcredit);
                                ddto.setMaxlsubjectscredit(maxlsubjectscredit);
                                ddto.setMaxpsubjectscredit(maxpsubjectscredit);
                                ddto.setMaxltheorysubjects(maxltheorysubjects);
                                ddto.setMaxplabsubjects(maxplabsubjects);
                                daoFactory.getSummerRegistrationSetupDetDAO().add(ddto);
                                save = 1;
                            }
                        }
                    }
                }
            }
            if (save == 1) {
                list.add("Success");
            } else {
                list.add("Record is already saved for this Program Code and STY Number");
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    @Override
    public void getSummerRegistrationSetupEdit(ModelMap model, HttpServletRequest req) {
        try {
            String pk[] = req.getParameter("pk").split(":");
            String instituteId = pk[0];
            String programid = pk[1];
            String stynumber = pk[2];
            model.put("srsData", daoFactory.getSummerRegistrationSetupDAO().getSummerRegistrationSetupEdit(instituteId, programid, Byte.parseByte(stynumber)));
            model.put("srsdData", daoFactory.getSummerRegistrationSetupDetDAO().getSummerRegistrationSetupDetEdit(instituteId, programid, Byte.parseByte(stynumber)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List updateSummerRegistrationSetup(HttpServletRequest req) {
        List list = new ArrayList();
        SummerRegistrationSetup dto = new SummerRegistrationSetup();
        SummerRegistrationSetupId id = new SummerRegistrationSetupId();
        SummerRegistrationSetupDet ddto = new SummerRegistrationSetupDet();
        SummerRegistrationSetupDetId did = new SummerRegistrationSetupDetId();
        try {
            //Ids Part..
            String instiuteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programCode[] = req.getParameterValues("programId");
            String styNumber[] = req.getParameterValues("stynumber");
            //summer regsitration setup
            double maxCredit = Double.parseDouble(req.getParameter("maxCredit"));
            int maxSubject = Integer.parseInt(req.getParameter("maxSubject"));
            int maxSubjectProject = Integer.parseInt(req.getParameter("maxSubjectProject"));
            int maxSubjectIndusCase = Integer.parseInt(req.getParameter("maxSubjectIndusCase"));

            for (int i = 0; i < programCode.length; i++) {
                for (int j = 0; j < styNumber.length; j++) {
                    id.setInstituteid(instiuteid);
                    id.setProgramid(programCode[i]);
                    id.setStynumber(Byte.parseByte(styNumber[j]));
                    dto.setId(id);
                    dto.setMaxcredit(maxCredit);
                    dto.setMaxsubjects(maxSubject);
                    dto.setMaxprojectsubjects(maxSubjectProject);
                    dto.setInduscasemaxsubj(maxSubjectIndusCase);
                    daoFactory.getSummerRegistrationSetupDAO().update(dto);
                    for (int k = 1; k <= 2; k++) {
                        if (req.getParameter("chk" + k + "") != null) {
                            String majororminor = req.getParameter("majorOrMinor" + k + "");
                            String lpcriteriatype = req.getParameter("lpCriteriaType" + k + "");
                            double maxcredit = Double.parseDouble(req.getParameter("maxCredit" + k + "").equals("") ? "0" : req.getParameter("maxCredit" + k + ""));
                            int maxlsubjectscredit = Integer.parseInt(req.getParameter("maxLsubjectCredit" + k + "").equals("") ? "0" : req.getParameter("maxLsubjectCredit" + k + ""));
                            int maxpsubjectscredit = Integer.parseInt(req.getParameter("maxPsubjectCredit" + k + "").equals("") ? "0" : req.getParameter("maxPsubjectCredit" + k + ""));
                            int maxltheorysubjects = Integer.parseInt(req.getParameter("maxLtheorySubject" + k + "").equals("") ? "0" : req.getParameter("maxLtheorySubject" + k + ""));
                            int maxplabsubjects = Integer.parseInt(req.getParameter("maxPlabSubject" + k + "").equals("") ? "0" : req.getParameter("maxPlabSubject" + k + ""));
                            did.setInstituteid(instiuteid);
                            did.setProgramid(programCode[i]);
                            did.setStynumber(Byte.parseByte(styNumber[j]));
                            did.setMajororminor(majororminor);
                            ddto.setId(did);
                            ddto.setLpcriteriatype(lpcriteriatype);
                            ddto.setMaxcredit(maxcredit);
                            ddto.setMaxlsubjectscredit(maxlsubjectscredit);
                            ddto.setMaxpsubjectscredit(maxpsubjectscredit);
                            ddto.setMaxltheorysubjects(maxltheorysubjects);
                            ddto.setMaxplabsubjects(maxplabsubjects);
                            daoFactory.getSummerRegistrationSetupDetDAO().saveOrUpdate(ddto);
                        } else {
                            String majororminor = req.getParameter("majorOrMinor" + k + "");
                            did.setInstituteid(instiuteid);
                            did.setProgramid(programCode[i]);
                            did.setStynumber(Byte.parseByte(styNumber[j]));
                            did.setMajororminor(majororminor);
                            ddto.setId(did);
                            ddto = (SummerRegistrationSetupDet) daoFactory.getSummerRegistrationSetupDetDAO().findByPrimaryKey(did);
                            if (ddto != null) {
                                daoFactory.getSummerRegistrationSetupDetDAO().delete(ddto);
                            }
                        }
                    }
                }
            }
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList();
            list.add("Error");
        }
        return list;
    }

    public List deleteSummerRegistrationSetup(HttpServletRequest request) {
        List err = null;
        List<Object[]> list = new ArrayList();
        SummerRegistrationSetup dto = new SummerRegistrationSetup();
        SummerRegistrationSetupId id = new SummerRegistrationSetupId();
        SummerRegistrationSetupDet ddto = new SummerRegistrationSetupDet();
        SummerRegistrationSetupDetId did = new SummerRegistrationSetupDetId();
        int i;
        try {
//            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] pkid = pks[i].toString().split("~@~");
                String instituteId = pkid[0];
                String programid = pkid[1];
                String stynumber = pkid[2];

                //Delete Child Record first from SummerRegsitrationSetupDetail
                did.setInstituteid(instituteId);
                did.setProgramid(programid);
                did.setStynumber(Byte.parseByte(stynumber));
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        did.setMajororminor("M");
                    } else {
                        did.setMajororminor("N");
                    }
                    ddto = (SummerRegistrationSetupDet) daoFactory.getSummerRegistrationSetupDetDAO().findByPrimaryKey(did);
                    if (ddto != null) {
                        daoFactory.getSummerRegistrationSetupDetDAO().delete(ddto);
                    }
                }

                //Delete from SummerRegsitrationSetup
                id.setInstituteid(instituteId);
                id.setProgramid(programid);
                id.setStynumber(Byte.parseByte(stynumber));
                dto = (SummerRegistrationSetup) daoFactory.getSummerRegistrationSetupDAO().findByPrimaryKey(id);
                daoFactory.getfSTwiseCoordinatorDAO().delete(dto);

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
