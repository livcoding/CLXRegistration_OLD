/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationParametersIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.Parameters;
import com.jilit.irp.persistence.dto.ParametersId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Malkeet Singh
 */
@Service
public class RegistrationParametersService implements RegistrationParametersIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    public List getRegistrationParametersGridData(HttpServletRequest req) {
        List gridData = null;
        try {
            String parameters = req.getParameter("parameters");
            String supplementryReg = "SUBREG1.1,SUBREG1.2,SUBREG3.3,SUBREG2.4,SUBREG4.1,SUBREG4.2,SUBREG4.5,SUBREG1.7,SUBREG4.3,SUBREG3.9,SUBREG3.8,SUBREG2.6,SUBREG2.7";
            String attendanceEntry = "K1.7,K1.6";
            String pgRegistration = "SUBREG5.2,SUBREG5.1";
            String marksView = "EX-1.1,EX-1.4,EX-1.2,EX-1.3";
            String courseReg = "SUBREG1.1,SUBREG1.2,SUBREG1.3,SUBREG1.4,SUBREG2.2,SUBREG1.6,SUBREG2.3,SUBREG1.5,K1.3,SUBREG2.1,K1.2,A1.2";
            String summerReg = "SUBREG1.1,SUBREG1.2,SUBREG3.2,SUBREG3.6,SUBREG3.4,SUBREG3.5,SUBREG4.4,SUBREG2.8,SUBREG4.6,SUBREG3.1";
            String otherParam = "SUBREG1.1,SUBREG1.2,SUBREG3.3,SUBREG2.4,SUBREG4.1,SUBREG4.2,SUBREG4.5,SUBREG1.7,SUBREG4.3,SUBREG3.9,SUBREG3.8,"
                    + "SUBREG2.6,SUBREG2.7,K1.7,K1.6,SUBREG5.2,SUBREG5.1,EX-1.1,EX-1.4,EX-1.2,EX-1.3,SUBREG1.1,SUBREG1.2,SUBREG1.3,SUBREG1.4,"
                    + "SUBREG2.2,SUBREG1.6,SUBREG2.3,SUBREG1.5,K1.3,SUBREG2.1,K1.2,A1.2,SUBREG1.1,SUBREG1.2,SUBREG3.2,SUBREG3.6,SUBREG3.4,SUBREG3.5,"
                    + "SUBREG4.4,SUBREG2.8,SUBREG4.6,SUBREG3.1";
            String status = "";
            List<String> parameterslist = new ArrayList<String>();
            if (parameters.equals("supplementry")) {
                String sReg[] = supplementryReg.split(",");
                for (int i = 0; i < sReg.length; i++) {
                    parameterslist.add(sReg[i]);
                }
            }
            if (parameters.equals("attendance")) {
                String attdEntry[] = attendanceEntry.split(",");
                for (int i = 0; i < attdEntry.length; i++) {
                    parameterslist.add(attdEntry[i]);
                }
            }
            if (parameters.equals("pgregistration")) {
                String pgReg[] = pgRegistration.split(",");
                for (int i = 0; i < pgReg.length; i++) {
                    parameterslist.add(pgReg[i]);
                }
            }
            if (parameters.equals("marksview")) {
                String mView[] = marksView.split(",");
                for (int i = 0; i < mView.length; i++) {
                    parameterslist.add(mView[i]);
                }
            }
            if (parameters.equals("course")) {
                String cReg[] = courseReg.split(",");
                for (int i = 0; i < cReg.length; i++) {
                    parameterslist.add(cReg[i]);
                }
            }
            if (parameters.equals("summerReg")) {
                String sumReg[] = summerReg.split(",");
                for (int i = 0; i < sumReg.length; i++) {
                    parameterslist.add(sumReg[i]);
                }
            }
            if (parameters.equals("other")) {
                String oParam[] = otherParam.split(",");
                for (int i = 0; i < oParam.length; i++) {
                    parameterslist.add(oParam[i]);
                }
                status = "other";
            }
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            gridData = (List) daoFactory.getRegistrationParametersDAO().getGridData(instituteid, parameterslist, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridData;
    }

    @Override
    public void getRegistrationParametersEditData(ModelMap mm, HttpServletRequest request) {
        Parameters dto = new Parameters();
        ParametersId id = new ParametersId();
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String pk = request.getParameter("pk");
            String ids[] = pk.split("~@~");
            id.setInstituteid(ids[0]);
            id.setModuleid(ids[1]);
            id.setParameterid(ids[2]);
            dto = (Parameters) daoFactory.getRegistrationParametersDAO().findByPrimaryKey(id);
            mm.addAttribute("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List updateRegistrationParameters(HttpServletRequest request) {
        Parameters dto = new Parameters();
        ParametersId id = new ParametersId();
        List err = new ArrayList<String>();
        try {
//            String instituteId = jirpsession.getJsessionInfo().getSelectedinstituteid();
            String instituteid = request.getParameter("instituteid");
            String moduleid = request.getParameter("moduleid");
            String parameterid = request.getParameter("parameterid");
            String parameter = request.getParameter("parameter");
            String parametervalue = request.getParameter("parametervalue");
            String datatype = request.getParameter("datatype");
            int slno = 0;
            id.setInstituteid(instituteid);
            id.setModuleid(moduleid);
            id.setParameterid(parameterid);
            dto.setId(id);
            dto.setParameter(parameter);
            dto.setParametervalue(parametervalue);
            dto.setDatatype(datatype);
            List list = daoFactory.getRegistrationParametersDAO().getSeqId(instituteid);
            if (list.size() == 0) {
                slno = 0;
            } else {
                slno = Integer.parseInt(list.get(0).toString());
            }
            short sl = (short) ++slno;
            dto.setSeqid(sl);
            daoFactory.getRegistrationParametersDAO().update(dto);
            err.add("Success");
        } catch (Exception e) {
            e.printStackTrace();
            err.add("Error");
            return err;
        }
        return err;
    }
}
