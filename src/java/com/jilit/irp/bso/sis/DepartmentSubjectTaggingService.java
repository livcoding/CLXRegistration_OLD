/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.DepartmentSubjectTaggingIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.DepartmentSubjectTagging;
import com.jilit.irp.persistence.dto.DepartmentSubjectTaggingId;
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
public class DepartmentSubjectTaggingService implements DepartmentSubjectTaggingIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getDepartmentSubjectList(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();

        List list = (List) daoFactory.getDepartmentSubjectTaggingDAO().getDepartmentSubjectTaggingData(instituteid);
        model.put("data", list);
    }

    @Override
    public void getAllDepartmentCode(ModelMap model) {
        List list = (List) daoFactory.getDepartmentMasterDAO().getAllDepartmentCode();
        model.put("depart_code", list);
    }

    @Override
    public void getReqSubjectForBranchChange(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = (List) daoFactory.getSubjectMasterDAO().getSubjectForDST(instituteid);
        model.put("subject_code", list);
    }

    public List saveDepertmentSubject(HttpServletRequest req) {
        DepartmentSubjectTagging dbt = new DepartmentSubjectTagging();
        List err = null;
        String sub_id = req.getParameter("sub_code");
        String dept_id = req.getParameter("dept_code");
        String sub_area_id = req.getParameter("sub_area");
        try {
            DepartmentSubjectTaggingId id = new DepartmentSubjectTaggingId();
            err = new ArrayList<String>();
            id.setInstituteid(jirpsession.getJsessionInfo().getSelectedinstituteid());
            id.setSubjectid(sub_id);
            id.setDepartmentid(dept_id);
            dbt.setId(id);
            if (sub_area_id != null && sub_area_id != "") {
                dbt.setSubjectareaid(sub_area_id);
            } else {
                dbt.setSubjectareaid("");
            }
            err = daoFactory.getDepartmentSubjectTaggingDAO().doValidate(dbt, "normal");
            if (err.size() > 0) {
                
                //System.err.println("err - " + err);
                return err;
            }
            daoFactory.getDepartmentSubjectTaggingDAO().saveOrUpdate(dbt);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
            return err;
        }
        return err;
    }

    public List deleteDepartmentSubject(HttpServletRequest req) {
        List err = null;
        int i;
        String ins_sub_dep = req.getParameter("ids");
        String[] arr = ins_sub_dep.split(",");
        try {
            for (i = 0; i < arr.length; i++) {
                String new_arr[] = arr[i].toString().split("~@~");
                String ins_id = new_arr[0];
                String sub_id = new_arr[1];
                String dep_id = new_arr[2];
                daoFactory.getDepartmentSubjectTaggingDAO().delete(daoFactory.getDepartmentSubjectTaggingDAO().findByPrimaryKey(new DepartmentSubjectTaggingId(ins_id, sub_id, dep_id)));
            }
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;
    }

    @Override
    public List getDepartmentwiseSubjectList(HttpServletRequest req, ModelMap map) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String dept_id = req.getParameter("dept_code");
        List list = (List) daoFactory.getDepartmentSubjectTaggingDAO().getDepartmentWiseSubjectTaggingData(instituteid, dept_id);
        return list;
    }
}
