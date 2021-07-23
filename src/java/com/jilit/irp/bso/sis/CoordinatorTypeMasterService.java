/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.CoordinatorTypeMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.CoordinatorType;
import com.jilit.irp.persistence.dto.CoordinatorTypeId;
import com.jilit.irp.util.JIRPSession;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class CoordinatorTypeMasterService implements CoordinatorTypeMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Override
    public Model getCoordinatorTypeMasterList(Model model) {
        List<CoordinatorType> coordinatorTypeList = new ArrayList<CoordinatorType>();
        try {
            String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
            coordinatorTypeList = (List<CoordinatorType>) daoFactory.getCoordinatorTypeDAO().getGridData(instituteid);
            model.addAttribute("coordinatorTypeList", coordinatorTypeList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

    @Override
    public List addCoordinatorTypeMaster(HttpServletRequest request) {
        CoordinatorType dto = null;
        CoordinatorTypeId id = null;
        List list = new ArrayList<String>();
        String instituteid = jirpession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<Object[]> list1 = (List<Object[]>) daoFactory.getCoordinatorTypeDAO().getLastCTypeId(instituteid);

            dto = new CoordinatorType();
            id = new CoordinatorTypeId();
            id.setInstituteid(instituteid);
            Object[] obj1 = list1.get(0);
            if (obj1[0] != null) {
                for (int i = 0; i < list1.size(); i++) {
                    Object[] obj = list1.get(i);
                    int maxCTId = Integer.parseInt(obj[0].toString().substring(19, 20)) + 1;
                    id.setCoordinatortypeid(obj[0].toString().substring(0, 19) + "" + maxCTId + "");
                    dto.setSeqid(Short.decode((Integer.parseInt(obj[1].toString()) + 1) + ""));
                }
            } else {
                String ctid = instituteid.substring(0, 10);
                id.setCoordinatortypeid(ctid + "CTID000001");
                dto.setSeqid(Short.decode("1"));
            }
            dto.setId(id);
            dto.setCoordinatortype(request.getParameter("coordType"));
            dto.setCoordinatortypecode(request.getParameter("coord_code"));
            dto.setCoordinatortypedesc(request.getParameter("coorTypeDesc"));
            dto.setGroupflag(request.getParameter("CoordTypeBased"));
            dto.setStudentlimit(Integer.parseInt(request.getParameter("stuLimit")));
            //  dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectComponentDAO().getMaxSeqId(dto) + 1)));
            dto.setDeactive(request.getParameter("deactive"));
            list = daoFactory.getCoordinatorTypeDAO().doValidate(dto, "save");
            if (list.size() > 0) {
                return list;
            }
            daoFactory.getCoordinatorTypeDAO().add(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list.add("Error");
        } finally {
        }
        return list;
    }

    @Override
    public ModelMap getAllCoordinatorTypeMasterData(ModelMap mm, HttpServletRequest request) {
        try {
            CoordinatorType dto = new CoordinatorType();
            CoordinatorTypeId id = new CoordinatorTypeId();
            String pk = request.getParameter("pk");
            String sr[] = pk.split("::");
            mm.addAttribute("instituteid", sr[0]);
            mm.addAttribute("coordinatortypeid", sr[1]);
            id.setInstituteid(sr[0]);
            id.setCoordinatortypeid(sr[1]);
            dto = (CoordinatorType) daoFactory.getCoordinatorTypeDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mm;
    }

    @Override
    public List updateCoordinatorTypeMaster(HttpServletRequest request) {
        CoordinatorType dto = new CoordinatorType();
        CoordinatorTypeId id = new CoordinatorTypeId();
        List list = null;
        try {
            id.setInstituteid(request.getParameter("instituteId"));
            id.setCoordinatortypeid(request.getParameter("coordinatorTypeId"));
            dto.setId(id);
            dto.setCoordinatortype(request.getParameter("coorType"));
            dto.setCoordinatortypecode(request.getParameter("coorTypeCode"));
            dto.setCoordinatortypedesc(request.getParameter("coorTypeDesc"));
            dto.setGroupflag(request.getParameter("CoordTypeBased"));
            dto.setStudentlimit(Integer.parseInt(request.getParameter("stuLimit")));
            dto.setSeqid(Short.decode(Integer.toString(daoFactory.getCoordinatorTypeDAO().getMaxSequenceIdInstPK(dto, jirpession.getJsessionInfo().getSelectedinstituteid()) + 1)));
            //  dto.setSeqid(Short.decode(Integer.toString(daoFactory.getSubjectComponentDAO().getMaxSeqId(dto) + 1)));
            dto.setDeactive(request.getParameter("deactive"));
//            if (!request.getParameter("seqId").equals("")) {
//                dto.setSeqid(Short.decode(request.getParameter("seqId")));
//            } else {
//                dto.setSeqid(null);
//            }
            if (request.getParameter("deactive") != null) {
                dto.setDeactive(request.getParameter("deactive"));
            } else {
                dto.setDeactive("Y");
            }
            list = new ArrayList<String>();
            daoFactory.getCoordinatorTypeDAO().update(dto);
            list.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
            list.add("Error");
        }
        return list;
    }

}
