package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.UpgradeDegradeSemesterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.BranchMaster;
import com.jilit.irp.persistence.dto.BranchMasterId;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.ProgramMaster;
import com.jilit.irp.persistence.dto.ProgramMasterId;
import com.jilit.irp.persistence.dto.ProgramMaxSty;
import com.jilit.irp.persistence.dto.ProgramMaxStyId;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.UpdateStyNumber;
import com.jilit.irp.persistence.dto.UpdateStyNumberId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Service
public class UpgradeDegradeSemesterService implements UpgradeDegradeSemesterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getAcademicYearCombo(ModelMap model) {
        List acadList = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            acadList = (List<Object[]>) daoFactory.getStudentMasterDAO().getAcademicYearReg(instituteid);
            model.addAttribute("acadList", acadList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAllBranchCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String programid = request.getParameter("program");
            list = (List<Object[]>) daoFactory.getBranchMasterDAO().getBranchCode(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getSemesterForAcadProgramBranch(HttpServletRequest request) {
        Boolean flag = false;
        List retList = new ArrayList();
        Set distinctStySet = new HashSet();
        ProgramMaxSty programMaxSty = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String acadYear = request.getParameter("academicYear");
            String programid = request.getParameter("program");
            String branch = request.getParameter("branch");
            String branch_1[] = branch.split(",");
            String branchid = "";
            for (int i = 0; i < branch_1.length; i++) {
                branchid = branch_1[i];
                programMaxSty = (ProgramMaxSty) daoFactory.getProgramMaxStyDAO().findByPrimaryKey(new ProgramMaxStyId(instituteid, acadYear, programid, branchid));
                if (programMaxSty != null) {
                    distinctStySet.add(programMaxSty.getEndsty());
                } else {
                    flag = true;
                }
            }
            if (distinctStySet.isEmpty() || distinctStySet.size() < 1) {
              //  retList.add("fail");
                return retList;
            } else {
                if (programMaxSty != null) {
                    byte start = (byte) programMaxSty.getStartsty();
                    byte end = (byte) programMaxSty.getEndsty();
                    for (int i = start; i < end; i++) {
                        retList.add(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List getAllGridData(HttpServletRequest request) {
        Boolean flag = false;
        List retList = new ArrayList();
        List loaclList = null;
        Set distinctStySet = new HashSet();
        ProgramMaxSty programMaxSty = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String action = request.getParameter("action");
            String academicYear = request.getParameter("academicYear");
            String programid = request.getParameter("program");
            String currentSty = request.getParameter("currentSTY");
            int stynumber = Integer.parseInt(currentSty);
            String br = request.getParameter("branch");
            String br_1[] = br.split(",");
            String branchid = "";
            for (int i = 0; i < br_1.length; i++) {
                branchid = br_1[i];
                try {
                    programMaxSty = (ProgramMaxSty) daoFactory.getProgramMaxStyDAO().findByPrimaryKey(new ProgramMaxStyId(instituteid, academicYear, programid, branchid));
                    if (programMaxSty != null) {
                        distinctStySet.add(programMaxSty.getEndsty());
                    } else {
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (distinctStySet.isEmpty() || distinctStySet.size() < 1) {
                retList.add("fail");
                return retList;
            } else {
                List arrList = daoFactory.getStudentMasterDAO().getSemesterBranchData(instituteid, academicYear, programid, br_1, stynumber);
                for (int i = 0; i < arrList.size(); i++) {
                    loaclList = new ArrayList();
                    StudentMaster smobject = (StudentMaster) arrList.get(i);
                    ProgramMaster master = (ProgramMaster) daoFactory.getProgramMasterDAO().findByPrimaryKey(new ProgramMasterId(smobject.getInstituteid(), smobject.getProgramid()));
                    BranchMaster bmaster = (BranchMaster) daoFactory.getBranchMasterDAO().findByPrimaryKey(new BranchMasterId(smobject.getInstituteid(), smobject.getProgramid(), smobject.getBranchid()));
                    loaclList.add(smobject.getEnrollmentno());
                    loaclList.add(smobject.getName());
                    loaclList.add(smobject.getAcadyear());
                    loaclList.add(master.getProgramcode());
                    loaclList.add(bmaster.getBranchcode());
                    loaclList.add(smobject.getStynumber());
                    if ("up".equals(action)) {
                        if (smobject.getStynumber() == 8) {
                            loaclList.add(smobject.getStynumber());
                        } else {
                            loaclList.add(smobject.getStynumber() + 1);
                        }
                    } else {
                        if (smobject.getStynumber() == 1) {
                            loaclList.add(smobject.getStynumber());
                        } else {
                            loaclList.add(smobject.getStynumber() - 1);
                        }
                    }
                    loaclList.add(smobject.getStudentid());
                    loaclList.add(smobject.getRemarks());
                    if (action.equals("up")) {
                        String str = daoFactory.getStoredProcedureDAO().RegistrationPermited(instituteid, master.getProgramcode(), smobject.getStudentid(), smobject.getStynumber() + 1, "Y");
                        if ("Y".equalsIgnoreCase(str)) {
                            loaclList.add(true);
                        } else {
                            continue;
                        }
                    } else {
                        String str = daoFactory.getStoredProcedureDAO().RegistrationPermited(instituteid, master.getProgramcode(), smobject.getStudentid(), smobject.getStynumber() + 1, "Y");
                        if ("Y".equalsIgnoreCase(str)) {
                            loaclList.add(true);
                        } else {
                            continue;
                        }
//                        ----------------Last Code-----------
//                        if ("Y".equalsIgnoreCase(str)) {
//                            continue;
//                        } else {
//                            loaclList.add(false);
//                        }
//                        ---------------Last Code--------------
                    }
                    retList.add(loaclList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    @Override
    public List getSaveGridata(HttpServletRequest request) {
        UpdateStyNumber updateStyNumber = null;
        UpdateStyNumberId id = null;
        List localList = null;
        List updateStyNumberList = new ArrayList();
        List updateStudentMasterList = new ArrayList();
        List finalList = new ArrayList();
        List finalListnew = new ArrayList();
        Date date = new Date();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        InstituteMaster ims = (InstituteMaster) daoFactory.getInstituteMasterDAO().findByPrimaryKey(instituteid);
        String institutecode = ims.getInstitutecode();
        String updatesection = request.getParameter("upgradeOrDegrade") == null ? "N" : request.getParameter("upgradeOrDegrade").toString();
        String semesterUpdatedText = request.getParameter("upDegradeSty").trim();
        int count = Integer.parseInt(request.getParameter("checkedCount"));
        try {
            updateStyNumber = new UpdateStyNumber();
            id = new UpdateStyNumberId();
            for (int i = 0; i <= count; i++) {
                if (request.getParameter("chk" + i + "") != null) {
                    String pks[] = request.getParameter("chk" + i + "").split("~@~");
                    String studentid = pks[0].toString();
                    String stynumber = pks[1].toString();
                    id.setStudentid(studentid);
                    id.setInstitutecode(institutecode);
                    id.setPrestynumber(Byte.valueOf(stynumber));
                    id.setFlag('U');
                    id.setDocmode('A');
                    updateStyNumber.setId(id);
                    updateStyNumber.setEntrydate(date);
                    List l = (List) daoFactory.getUpdateStyNumberDAO().getStudentMaster(studentid);
                    StudentMaster master = new StudentMaster((StudentMaster) l.get(0), false);
                    master.setStynumber(Byte.valueOf(semesterUpdatedText));
                    if ("Y".equals(updatesection)) {
                        if (master.getNextsectionid() == null || master.getNextsubsectionid() == null) {
                            finalList = new ArrayList();
                            finalList.add("nullsection");
                            finalList.add("Section(next) / Subsection(next) is not entered. Can't update Section / Subsection with null value ! \n Record is not-saved.");
                            return finalList;
                        }
                        master.setSectionid(master.getNextsectionid());
                        master.setSubsectionid(master.getNextsubsectionid());
                    }
                    updateStyNumber.setUpdatestynumber(master.getStynumber());
                    updateStyNumberList.add(updateStyNumber);
                    updateStudentMasterList.add(master);
                }
            }
            daoFactory.getUpdateStyNumberDAO().saveData(updateStyNumberList, updateStudentMasterList);
            finalListnew.add("Success");
            finalList = new ArrayList();
            for (int i = 0; i < updateStudentMasterList.size(); i++) {
                localList = new ArrayList();
                StudentMaster smobject = (StudentMaster) updateStudentMasterList.get(i);
                ProgramMaster master = (ProgramMaster) daoFactory.getProgramMasterDAO().findByPrimaryKey(new ProgramMasterId(smobject.getInstituteid(), smobject.getProgramid()));
                BranchMaster bmaster = (BranchMaster) daoFactory.getBranchMasterDAO().findByPrimaryKey(new BranchMasterId(smobject.getInstituteid(), smobject.getProgramid(), smobject.getBranchid()));
                localList.add(smobject.getEnrollmentno());
                localList.add(smobject.getName());
                localList.add(smobject.getAcadyear());
                localList.add(master.getProgramcode());
                localList.add(bmaster.getBranchcode());
                localList.add(smobject.getStynumber());
                localList.add(smobject.getStynumber() + 1);
                finalList.add(localList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalListnew;
    }
}
