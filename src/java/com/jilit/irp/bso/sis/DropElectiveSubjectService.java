package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.DropElectiveSubjectIservice;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

/**
 *
 * @author ashutosh1.kumar
 */
@Service
public class DropElectiveSubjectService implements DropElectiveSubjectIservice {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getFormData(Model model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List list = (List) daoFactory.getDropElectiveSubjectDao().getRegistrationCodeForDropElective(instituteid);
            model.addAttribute("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getSubjectType(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = request.getParameter("regId");
        List list = null;
        try {
            list = daoFactory.getDropElectiveSubjectDao().getAllSubjectType(instituteid, registrationid);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSubjectCode(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = request.getParameter("regId");
        String subjecttypeid = request.getParameter("subjectType");
        List list = null;
        try {
            list = daoFactory.getDropElectiveSubjectDao().getSubjectToBeDroped(instituteid, registrationid, subjecttypeid);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getGridData(HttpServletRequest request) {
        List data = null;
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String registrationid = request.getParameter("regId");
        String subjectid = request.getParameter("subjectCode");
        try {
            data = daoFactory.getDropElectiveSubjectDao().getDataInDataGrid(instituteid, registrationid, subjectid);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    @Override
    public List getAllProcessForDropping(HttpServletRequest request) {
        String registrationid = request.getParameter("regCode");
        String subjectid = request.getParameter("subjectCode");
        String subjecttypeid = request.getParameter("subjectType");
        String radiovalue = request.getParameter("dropElectiveBased");
        List list = new ArrayList();
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            if (radiovalue.equals("cgpa")) {
                List<Object[]> stuList = daoFactory.getDropElectiveSubjectDao().getAllStudentRecord(instituteid, registrationid, subjectid, subjecttypeid);
                search:
                for (int i = 0; i < stuList.size(); i++) {
                    String basketid = stuList.get(i)[5] == null ? "" : stuList.get(i)[5].toString();
                    String studentid = stuList.get(i)[4] == null ? "" : stuList.get(i)[4].toString();
                    byte stynumber = Byte.valueOf(stuList.get(i)[6] == null ? "" : stuList.get(i)[6].toString());
                    List<Object[]> subjectList = daoFactory.getDropElectiveSubjectDao().getAllSubjectRecord(instituteid, registrationid, studentid, basketid, subjectid);
                    for (int k = 0; k < subjectList.size(); k++) {
                        String subjectid1 = subjectList.get(k)[0] == null ? "" : subjectList.get(k)[0].toString();
                        List totalCount = daoFactory.getDropElectiveSubjectDao().getTotalNoOfStudent(instituteid, registrationid, basketid, subjectid);
                        int ttl = 0;
                        if (totalCount.size() > 0) {
                            ttl = Integer.parseInt(totalCount.get(0) == null ? "0" : totalCount.get(0).toString());
                        }

                        List totalAssignCount = daoFactory.getDropElectiveSubjectDao().getTotalAssignedCount(instituteid, registrationid, subjectid);
                        int ttlAssign = 0;
                        if (totalAssignCount.size() > 0) {
                            ttlAssign = Integer.parseInt(totalAssignCount.get(0) == null ? "0" : totalAssignCount.get(0).toString());
                        }
                        if (ttlAssign < ttl) {
                            StudentSubjectChoiceMasterId studentSubjectChoiceMasterId = new StudentSubjectChoiceMasterId();
                            studentSubjectChoiceMasterId.setInstituteid(instituteid);
                            studentSubjectChoiceMasterId.setRegistrationid(registrationid);
                            studentSubjectChoiceMasterId.setStudentid(studentid);
                            studentSubjectChoiceMasterId.setSubjectid(subjectid1);
                            studentSubjectChoiceMasterId.setStynumber(stynumber);
                            StudentSubjectChoiceMaster studentSubjectChoiceMaster = (StudentSubjectChoiceMaster) daoFactory.getStudentSubjectChoiceMasterDAO().findByPrimaryKey(studentSubjectChoiceMasterId);
                            studentSubjectChoiceMaster.setSubjectrunning("Y");
                            daoFactory.getStudentSubjectChoiceMasterDAO().update(studentSubjectChoiceMaster);
                            list.add("Success");
                            break search;
                        }
                        list.add("Error");
                    }
                }
                if (list.size() == 0) {
                    list.add("NoStudentFound");
                }
            } else {
                int count = request.getParameter("countval").split(",").length;
                List acdyrlist = new ArrayList();
                List prlist = new ArrayList();
                List brlist = new ArrayList();
                List stynolist = new ArrayList();
                List stytypelist = new ArrayList();
                for (int j = 0; j < count; j++) {
                    if (request.getParameter("chk" + j) != null) {
                        String[] ids = request.getParameter("chk" + j).split("~@~");
                        acdyrlist.add(ids[0]);
                        prlist.add(ids[1]);
                        brlist.add(ids[2]);
                        stynolist.add(new Byte(ids[3]));
                        stytypelist.add(ids[4]);
                    }
                }
                List<StudentSubjectChoiceMaster> stuList = daoFactory.getDropElectiveSubjectDao().getAllStudentRecordBatchWise(instituteid, registrationid, subjectid, acdyrlist, prlist, brlist, stynolist, stytypelist);
                for (int i = 0; i < stuList.size(); i++) {
                    StudentSubjectChoiceMaster studentSubjectChoiceMaster = (StudentSubjectChoiceMaster) stuList.get(i);
                    studentSubjectChoiceMaster.setSubjectrunning("Y");
                    daoFactory.getStudentSubjectChoiceMasterDAO().update(studentSubjectChoiceMaster);
                    list.add("Success");
                }
            }
        } catch (Exception ex) {
            list.add("Error");
            ex.printStackTrace();
        }
        return list;
    }

}
