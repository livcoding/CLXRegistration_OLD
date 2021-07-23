package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.SemesterTypeMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
@Service
public class SemesterTypeMasterService implements SemesterTypeMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getSemesterTypeMasterList(Model model) {
        List semesterList = null;
        try {
            semesterList = new ArrayList<StyType>();
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            semesterList =  daoFactory.getStyTypeDAO().getStyTypeGridData(instituteid);
            model.addAttribute("semesterList", semesterList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModelMap getSemesterTypeMasterEdit(HttpServletRequest request, ModelMap mm) {
        StyType dto = null;
        StyTypeId id = null;
        try {
            dto = new StyType();
            id = new StyTypeId();
            String pk = request.getParameter("pk");
            String ids[] = pk.split(":");
            mm.addAttribute("institute_id", ids[0]);
            mm.addAttribute("sem_type_id", ids[1]);
            id.setInstituteid(ids[0]);
            id.setStytypeid(ids[1]);
            List semesterList =  daoFactory.getStyTypeDAO().getStyTypeEditData(ids[0],ids[1]);
            mm.addAttribute("data", semesterList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mm;
    }

    @Override
    public List getSemesterTypeMasterSave(HttpServletRequest request) {
        StyType dto = null;
        StyTypeId id = null;
        BusinessService businessService = null;
        List list = null;
        try {
            dto = new StyType();
            id = new StyTypeId();
            list = new ArrayList<String>();
            businessService = new BusinessService(daoFactory,true);
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setStytypeid(businessService.generateId("STYTypeID", jirpsession.getJsessionInfo().getSelectedinstituteuniqueid(), "I", false));
            dto.setId(id);
            dto.setStytype(request.getParameter("sty_type_code"));
            dto.setStytypedesc(request.getParameter("sty_Type_Desc"));
         //   dto.setSeqid(Short.decode(Integer.toString(daoFactory.getStyTypeDAO().getMaxSeqId(dto) + 1)));
            dto.setSeqid(Short.decode(Integer.toString(daoFactory.getStyTypeDAO().getMaxSequenceIdInstPK(dto, jirpsession.getJsessionInfo().getSelectedinstituteid())  + 1)));
            
            if (request.getParameter("ETOD") != null) {
                dto.setEtod(request.getParameter("ETOD"));
            } else {
                dto.setEtod("N");
            }
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            list = daoFactory.getStyTypeDAO().doValidate(dto, "save");
            if (list != null && !list.isEmpty()) {
                businessService.rollback();
                return list;
            }
            businessService.insertOrUpdateInIdGenerationControl(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            businessService.rollback();
            list = new ArrayList<String>();
            list.add("Error");
        }finally{
            businessService.closeSession();
        }
        return list;
    }

    @Override
    public List getSemesterTypeMasterUpdate(HttpServletRequest request) {

        StyType dto = null;
        StyTypeId id = null;
        List list = null;
        StyType styTypeClone = null;
        try {
            dto = new StyType();
            id = new StyTypeId();
            styTypeClone = new StyType();
            id.setInstituteid(request.getParameter("institute_id"));
            id.setStytypeid(request.getParameter("sem_type_id"));
            dto.setId(id);
            dto.setStytype(request.getParameter("sty_type_code"));
            dto.setStytypedesc(request.getParameter("subject_Type_Desc"));
            if (!request.getParameter("Seq_Id").equals("")) {
                dto.setSeqid(Short.parseShort(request.getParameter("Seq_Id")));
            } else {
                dto.setSeqid(null);
            }
            if (request.getParameter("ETOD") != null) {
                dto.setEtod(request.getParameter("ETOD"));
            } else {
                dto.setEtod("N");
               
            }
              
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            list = new ArrayList<String>();
            styTypeClone = (StyType) daoFactory.getStyTypeDAO().findByPrimaryKey(id);
            if (!(dto.getStytype().equals(styTypeClone.getStytype()))) {
                list = daoFactory.getStyTypeDAO().doValidate(dto, "edit");
            }
            if (list != null && !list.isEmpty()) {
                return list;
            }
            daoFactory.getStyTypeDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

    @Override
    public List getSemesterTypeMasterDelete(HttpServletRequest request) {
        List list = null;
        int i;
        try {
            String ids = request.getParameter("ids");
            String pks[] = ids.split(",");
            for (i = 0; i < pks.length; i++) {
                String[] id = pks[i].toString().split(":");
                daoFactory.getStyTypeDAO().delete(daoFactory.getStyTypeDAO().findByPrimaryKey(new StyTypeId(id[0], id[1])));
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
    public String checkIfChildExist(HttpServletRequest request) {
        String pk = request.getParameter("pk");
        String ids[] = pk.split(":");
        StyTypeId id = new StyTypeId();
        id.setInstituteid(ids[0]);
        id.setStytypeid(ids[1]);
        String status = (daoFactory.getStyTypeDAO().checkIfChildExist(id) > 0) ? "true" : "false";
        return status;
    }
}
