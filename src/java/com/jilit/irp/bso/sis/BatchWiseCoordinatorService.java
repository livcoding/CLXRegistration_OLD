/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.BatchWiseCoordinatorIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.FSTwiseCoordinator;
import com.jilit.irp.persistence.dto.FSTwiseCoordinatorId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class BatchWiseCoordinatorService implements BatchWiseCoordinatorIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getSemesterCode(ModelMap model) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List<Object[]>) daoFactory.getRegistrationMasterDAO().getRegistrationCodeForAcademicDataReset(instituteid);
            model.put("semCode", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getSubjectCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = request.getParameter("regId");
            list = (List<Object[]>) daoFactory.getSubjectMasterDAO().getSubjectCodeUsingFacultySubjectTagging(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getGridData(HttpServletRequest req) {
        List gridData = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String subjectid = req.getParameter("subjectid");
            String registartionid = req.getParameter("regId");
            gridData = (List) daoFactory.getfSTwiseCoordinatorDAO().getGridData(instituteid, subjectid,registartionid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridData;
    }

    public void getSubjectComponentCode(ModelMap model) {
        List subjectComp = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            subjectComp = (List) daoFactory.getSubjectComponentDAO().getSubjectComponentCode(instituteid);
            model.put("subjectComp", subjectComp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCoordinatorType(ModelMap model) {
        List coordinatorType = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            coordinatorType = (List) daoFactory.getfSTwiseCoordinatorDAO().getCoordinatorType(instituteid);
            model.put("coordinatorType", coordinatorType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getStaffCode(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String regId = request.getParameter("regId");
            String subjectId = request.getParameter("subjectId");
            String staffType = request.getParameter("staffType");
            if (staffType.equalsIgnoreCase("TTBased")) {
                list = (List<Object[]>) daoFactory.getfSTwiseCoordinatorDAO().getStaffCode1(instituteid, regId, subjectId);
            } else {
                list = (List<Object[]>) daoFactory.getfSTwiseCoordinatorDAO().getStaffCode2(instituteid, staffType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAddGridData(HttpServletRequest request) {
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registrationid = request.getParameter("registrationid");
            String subjectid = request.getParameter("subjectid");
            String subjectcomponentid = request.getParameter("subjectcomponentid");
            list = (List<Object[]>) daoFactory.getfSTwiseCoordinatorDAO().getAddGridData(instituteid, registrationid, subjectid, subjectcomponentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List saveBatchWiseCoordinator(HttpServletRequest request) {
        FSTwiseCoordinator dto = new FSTwiseCoordinator();
        FSTwiseCoordinatorId id = new FSTwiseCoordinatorId();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List err = new ArrayList<String>();
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String registraionId = request.getParameter("semesterCode");
            String subjectId = request.getParameter("subjectCode");
            String st = request.getParameter("staffCode");
            String staff[] = st.split("~@~");
            String coordinatorType[] = request.getParameterValues("coordinatorType");
            int totalCount = Integer.parseInt(request.getParameter("totalCount"));

            for (int i = 0; i < totalCount; i++) {
                String fstid = request.getParameter("chk" + i + "");
                for (int j = 0; j < coordinatorType.length; j++) {
                    if (fstid != null) {
                        id.setInstituteid(instituteId);
                        id.setStaffid(staff[0]);
                        id.setStafftype(staff[1]);
                        id.setCoordinatortypeid(coordinatorType[j]);
                        id.setFstid(fstid);
                        dto = (FSTwiseCoordinator) daoFactory.getfSTwiseCoordinatorDAO().findByPrimaryKey(id);
                        if (dto == null) {
                            dto = new FSTwiseCoordinator();
                            dto.setId(id);
                        }
                        dto.setSubjectid(subjectId);
                        if (!request.getParameter("fromDate").equals("") && request.getParameter("fromDate") != null) {
                            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fromDate"));
                            dto.setFromdate(fromDate);
                        }
                        if (!request.getParameter("toDate").equals("") && request.getParameter("toDate") != null) {
                            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("toDate"));
                            dto.setTodate(toDate);
                        }
                        dto.setDeactive("N");
                        daoFactory.getfSTwiseCoordinatorDAO().saveOrUpdate(dto);
                    }
                }
            }
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }

    public List deleteBatchWiseCoordinator(HttpServletRequest request) {
        List err = null;
        FSTwiseCoordinator dto = new FSTwiseCoordinator();
        FSTwiseCoordinatorId id = new FSTwiseCoordinatorId();
        int i;
        try {
            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] pkid = pks[i].toString().split("~@~");
                id.setInstituteid(pkid[0]);
                id.setCoordinatortypeid(pkid[1]);
                id.setFstid(pkid[2]);
                id.setStaffid(pkid[3]);
                id.setStafftype(pkid[4]);
                dto = (FSTwiseCoordinator) daoFactory.getfSTwiseCoordinatorDAO().findByPrimaryKey(id);
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
