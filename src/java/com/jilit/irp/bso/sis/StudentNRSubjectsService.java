package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.StudentNRSubjectsIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.util.JIRPSession;
import com.jilit.irp.persistence.dto.StudentNRSubjects;
import com.jilit.irp.persistence.dto.StudentNRSubjectsId;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet.singh
 */
@Service
public class StudentNRSubjectsService implements StudentNRSubjectsIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public List getStudetnInfo(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String enrollmentno = request.getParameter("enrollmentno").trim();
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
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String studentid = request.getParameter("studentid");
        List list = null;
        try {
            list = (List) daoFactory.getStudentNRSubjectsDAO().getStudentNRSubjects(instituteid, studentid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void getSemesterCode(ModelMap model, String studentid) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list1 = null;
        List list2 = null;
        try {
            list1 = (List) daoFactory.getStudentNRSubjectsDAO().getPreviousRegCode(instituteid, studentid);
            list2 = (List) daoFactory.getSubjectTypeMasterDAO().getSubjectType(instituteid);
            model.put("semCode", list1);
            model.put("subjectType", list2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List getSubjectCode(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String studentid = request.getParameter("studentid");
        String subjecttype = request.getParameter("subjecttype");
        String registrationid = request.getParameter("registrationid");
        List list = null;
        try {
            list = (List) daoFactory.getStudentNRSubjectsDAO().getPreviousSubject(instituteid, studentid, subjecttype, registrationid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List saveNRSubjects(HttpServletRequest request) {
        StudentNRSubjects dto = null;
        StudentNRSubjectsId id = null;
        List list = null;
        try {
            dto = new StudentNRSubjects();
            id = new StudentNRSubjectsId();
            list = new ArrayList<String>();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setAcademicyear(request.getParameter("academicyear"));
            id.setProgramid(request.getParameter("programid"));
            id.setSectionid(request.getParameter("sectionid"));
            String s = request.getParameter("subjectcode");
            String[] subid = s.split("~@~");
            id.setSubjectid(subid[0]);
            id.setStudentid(request.getParameter("stuid"));
            id.setStynumber(Byte.decode(request.getParameter("stynumber")));
            dto.setId(id);
            dto.setBasketid(request.getParameter("basketid"));
            dto.setNrregisteredid(request.getParameter("nrsemcode"));
            dto.setRemarks(request.getParameter("reason"));
            dto.setRegistered("N");
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            list = daoFactory.getStudentNRSubjectsDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getStudentNRSubjectsDAO().add(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }

        return list;
    }

    @Override
    public List deleteNRSubjects(HttpServletRequest request) {
        StudentNRSubjects dto = null;
        StudentNRSubjectsId id = null;
        List list = null;
        try {
            dto = new StudentNRSubjects();
            id = new StudentNRSubjectsId();
            list = new ArrayList<String>();
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (int i = 0; i < pks.length; i++) {
                id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
                String[] idss = pks[i].toString().split("~@~");
                id.setAcademicyear(idss[1]);
                id.setProgramid(idss[2]);
                id.setSectionid(idss[3]);
                id.setSubjectid(idss[6]);
                id.setStudentid(idss[4]);
                id.setStynumber(Byte.decode(idss[5]));
                dto = (StudentNRSubjects) daoFactory.getStudentNRSubjectsDAO().findByPrimaryKey(id);
                if (dto != null) {
                    daoFactory.getStudentNRSubjectsDAO().delete(dto);
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

//    @Override
//    public ModelMap getSemesterTypeMasterEdit(HttpServletRequest request, ModelMap mm) {
//        StyType dto = null;
//        StyTypeId id = null;
//        try {
//            dto = new StyType();
//            id = new StyTypeId();
//            String pk = request.getParameter("pk");
//            String ids[] = pk.split(":");
//            mm.addAttribute("institute_id", ids[0]);
//            mm.addAttribute("sem_type_id", ids[1]);
//            id.setInstituteid(ids[0]);
//            id.setStytypeid(ids[1]);
//            dto = (StyType) daoFactory.getStyTypeDAO().findByPrimaryKey(id);
//            mm.addAttribute("data", dto);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mm;
//    }
//
//
//    @Override
//    public List getSemesterTypeMasterUpdate(HttpServletRequest request) {
//
//        StyType dto = null;
//        StyTypeId id = null;
//        List list = null;
//        StyType styTypeClone = null;
//        try {
//            dto = new StyType();
//            id = new StyTypeId();
//            styTypeClone = new StyType();
//            id.setInstituteid(request.getParameter("institute_id"));
//            id.setStytypeid(request.getParameter("sem_type_id"));
//            dto.setId(id);
//            dto.setStytype(request.getParameter("sty_type_code"));
//            dto.setStytypedesc(request.getParameter("subject_Type_Desc"));
//            if (!request.getParameter("Seq_Id").equals("")) {
//                dto.setSeqid(Short.parseShort(request.getParameter("Seq_Id")));
//            } else {
//                dto.setSeqid(null);
//            }
//            if (request.getParameter("ETOD") != null) {
//                dto.setEtod(request.getParameter("ETOD"));
//            } else {
//                dto.setEtod("N");
//               
//            }
//              
//            if (request.getParameter("deactive") != null) {
//                dto.setDeactive(request.getParameter("deactive"));
//            } else {
//                dto.setDeactive("Y");
//            }
//            list = new ArrayList<String>();
//            styTypeClone = (StyType) daoFactory.getStyTypeDAO().findByPrimaryKey(id);
//            if (!(dto.getStytype().equals(styTypeClone.getStytype()))) {
//                list = daoFactory.getStyTypeDAO().doValidate(dto, "edit");
//            }
//            if (list != null && !list.isEmpty()) {
//                return list;
//            }
//            daoFactory.getStyTypeDAO().update(dto);
//            list.add("Success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            list = new ArrayList<String>();
//            list.add("Error");
//        }
//        return list;
//    }
//
//    @Override
//    public List getSemesterTypeMasterDelete(HttpServletRequest request) {
//        List list = null;
//        int i;
//        try {
//            String ids = request.getParameter("ids");
//            String pks[] = ids.split(",");
//            for (i = 0; i < pks.length; i++) {
//                String[] id = pks[i].toString().split(":");
//                daoFactory.getStyTypeDAO().delete(daoFactory.getStyTypeDAO().findByPrimaryKey(new StyTypeId(id[0], id[1])));
//            }
//            list = new ArrayList<String>();
//            list.add("Success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            list = new ArrayList<String>();
//            list.add("Error");
//        }
//        return list;
//    }
//
//    @Override
//    public String checkIfChildExist(HttpServletRequest request) {
//        String pk = request.getParameter("pk");
//        String ids[] = pk.split(":");
//        StyTypeId id = new StyTypeId();
//        id.setInstituteid(ids[0]);
//        id.setStytypeid(ids[1]);
//        String status = (daoFactory.getStyTypeDAO().checkIfChildExist(id) > 0) ? "true" : "false";
//        return status;
//    }
}
