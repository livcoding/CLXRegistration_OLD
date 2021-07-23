package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.ElectiveMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
@Service
public class ElectiveMasterService implements ElectiveMasterIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void getElectiveMaster(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getElectiveMasterDAO().getElectiveMaster(instituteid);
        model.put("data", list);
    }

    public void editElectiveMaster(ModelMap mm, HttpServletRequest request) {
        List<Object[]> list = new ArrayList<Object[]>();
        ElectiveMaster master = new ElectiveMaster();
        ElectiveMasterId id = new ElectiveMasterId();
        String ids = request.getParameter("pk");
        String[] str = ids.split(":");
        String elec_id = str[0];
        String ins_id = str[1];
        list = (List<Object[]>) daoFactory.getElectiveMasterDAO().EditElectiveMaster(ins_id, elec_id);
        mm.addAttribute("edit", list);

    }

    /**
     * Description: Function has been used to save information.
     *
     * @param asobj
     * @return
     */
    public List saveElectiveMaster(HttpServletRequest req) {
        ElectiveMaster master = new ElectiveMaster();
        ElectiveMasterId id = new ElectiveMasterId();
        List err = new ArrayList<String>();
        BusinessService businessService = new BusinessService(daoFactory);
        try {
            String elec_code = req.getParameter("elec_code").trim();
            String elec_desc = req.getParameter("elec_desc");
            String credit = req.getParameter("credit");
            String deactive = req.getParameter("deactive");
            String uniq_id = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
            String ins_id = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String elec_id = businessService.generateId("ElectiveID", uniq_id, "I", false);
            id.setInstituteid(ins_id);
            id.setElectiveid(elec_id);
            master.setId(id);
            master.setElectivecode(elec_code);
            master.setElectivedesc(elec_desc);
            if (credit != null && !credit.equals("")) {
                Short cr = Short.parseShort(credit);
                master.setCredits(cr);
            } else {
                master.setCredits(null);
            }

            if (deactive != null) {
                master.setDeactive("N");
            } else {
                master.setDeactive("Y");
            }
            err = daoFactory.getElectiveMasterDAO().doValidate(master, "save");
            if (err.size() > 0) 
            {
                businessService.rollback();
                return err;
            }
            businessService.insertInIdGenerationControl(master);
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

    public List updateElectiveMaster(HttpServletRequest req) {
        ElectiveMaster master = new ElectiveMaster();
        ElectiveMasterId id = new ElectiveMasterId();
         BusinessService businessService = new BusinessService(daoFactory);
        List err = new ArrayList<String>();
        try {
            String elec_code = req.getParameter("elec_code").trim();
            String elec_desc = req.getParameter("elec_desc");
            String credit = req.getParameter("credit");
            String ins_id = req.getParameter("ins_id");
            String elec_id = req.getParameter("elec_id");
            String deactive = req.getParameter("deactive");
            id.setInstituteid(ins_id);
            id.setElectiveid(elec_id);
            master = (ElectiveMaster) daoFactory.getElectiveMasterDAO().findByPrimaryKey(id);
            if (master != null) {
                master.setElectivecode(elec_code);
                master.setElectivedesc(elec_desc);
                if (deactive != null) {
                    master.setDeactive("N");
                } else {
                    master.setDeactive("Y");
                }
                if (credit != null && !credit.equals("")) {
                    Short cr = Short.parseShort(credit);
                    master.setCredits(cr);
                } else {
                    master.setCredits(null);
                }
                 err = daoFactory.getElectiveMasterDAO().doValidate(master, "update");
            if (err.size() > 0) 
            {
                businessService.rollback();
                return err;
            }
                daoFactory.getElectiveMasterDAO().update(master);
            }
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
        }
        return err;
    }

    public String checkIfChildExist(HttpServletRequest request) {
        ElectiveMasterId id = null;
        String str = request.getParameter("pk");
        String[] str1 = str.split(":");
        String elec_id = str1[0];
        String ins_id = str1[1];
        String status = "";
        try {
            id = new ElectiveMasterId();
            id.setElectiveid(elec_id);
            id.setInstituteid(ins_id);
            int val = daoFactory.getElectiveMasterDAO().checkIfChildExist(id);
            if (val > 0) {
                status = "true";
            } else {
                status = "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public List deleteElectiveMaster(HttpServletRequest request) {
        ElectiveMasterId id = null;
        List retList = new ArrayList();
        ElectiveMaster master = null;
        String ins_pr_br = request.getParameter("ids");
        String[] arr = ins_pr_br.split(",");
        for (int i = 0; i < arr.length; i++) {
            String[] new_arr = arr[i].split(":");
            String elec_id = new_arr[0];
            String ins_id = new_arr[1];
            id = new ElectiveMasterId();
            master = new ElectiveMaster();
            id.setElectiveid(elec_id);
            id.setInstituteid(ins_id);

            master = (ElectiveMaster) daoFactory.getElectiveMasterDAO().findByPrimaryKey(id);
            if (master != null) {
                try {
                    daoFactory.getElectiveMasterDAO().delete(master);
                    retList.add("Success");
                } catch (Exception e) {
                    e.printStackTrace();
                    retList.add("Error");
                }
            }

        }
        return retList;
    }

}
