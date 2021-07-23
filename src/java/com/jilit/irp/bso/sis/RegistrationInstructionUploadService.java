/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.iservice.RegistrationInstructionUploadIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.RegistrationMaster;
import com.jilit.irp.persistence.dto.RegistrationInstruction;
import com.jilit.irp.persistence.dto.RegistrationInstructionId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.servlet.ServletOutputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author priyanka.tyagi
 */
@Service
public class RegistrationInstructionUploadService implements RegistrationInstructionUploadIService {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getRegistratioInstructionList(ModelMap model) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        try {
            List<RegistrationMaster> dto = new ArrayList<RegistrationMaster>();
            dto = (List<RegistrationMaster>) daoFactory.getRegistrationMasterDAO().findAll(instituteid);
            model.put("dto", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getgriddata(HttpServletRequest request) {
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        List list = new ArrayList();
        try {
            list = (List) daoFactory.getRegistrationInstructionUploadDAO().getRegistrationInstructionData(instituteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
