package com.jilit.irp.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Log_CLXAuditTrail_Master;
import com.jilit.irp.persistence.dto.Log_CLXAuditTrail_MasterId;
import com.jilit.irp.persistence.dto.Log_LoginLogInfo;
import com.jilit.irp.persistence.dto.Log_LoginLogInfoId;
import com.jilit.irp.util.JIRPSession;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ashutosh1.kumar
 */

public class BusinessLogger {

    @Autowired
    DAOFactory daoFactory;

    @Autowired
    JIRPSession jirpession;

    @Async
    @Transactional
    public void saveAuditLogs(String pModuleName, String pRightsName, String pTableOrObjectName, String pOperation, String pPKColName, String pPKValues, Object dto, String pLogStatusS_F, Date pActionDateTime, String pRemarks) throws UnknownHostException, JsonProcessingException {

        Log_CLXAuditTrail_Master logData;
        Log_CLXAuditTrail_MasterId logId;
        String memeberid = jirpession.getJsessionInfo().getMemberid();
        String memebercode = jirpession.getJsessionInfo().getMembercode();
        String memebername = jirpession.getJsessionInfo().getUsername();
        String memebertype = jirpession.getJsessionInfo().getUsertype();
        String ipaddress = jirpession.getJsessionInfo().getIpaddress();
        String macaddress = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(dto);
        logData = new Log_CLXAuditTrail_Master();
        logId = new Log_CLXAuditTrail_MasterId();
        try {
            String logid = getRandomNumber();
            logId.setLogentryid(logid);
            logId.setLogid(new BigDecimal("1"));
            logData.setId(logId);
            logData.setLogbymembercode(memebercode);
            logData.setLogbymemberid(memeberid);
            logData.setLogbymembername(memebername);
            logData.setLogbymembertype(memebertype);
            logData.setLogdate(new Date());
            logData.setLogstatus(pLogStatusS_F);
            logData.setMenuname(pRightsName);
            logData.setModulecode(pModuleName);
            logData.setNewvalue(json);
            logData.setActivity(pOperation);
            logData.setIpaddress(ipaddress);
            logData.setMacaddress(macaddress);
            logData.setOntable(pTableOrObjectName);
            logData.setPkcolumnname(pPKColName);
            logData.setMacaddress("jsonLogData.toString()");
            logData.setPkvalues(pPKValues);
            logData.setRemarks(pRemarks);
            //  Thread.sleep(2222);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        daoFactory.getLoginLogDAO().saveOrUpdate(logData);
    }

    @Async
    @Transactional
    public void saveAuditLogs(String pModuleName, String pRightsName, String[] pTableOrObjectName, String[] pOperation, Object[] dto, Date pActionDateTime, String pRemarks) throws UnknownHostException, JsonProcessingException {

        Log_CLXAuditTrail_Master logData;
        Log_CLXAuditTrail_MasterId logId;
        String memeberid = jirpession.getJsessionInfo().getMemberid();
        String memebercode = jirpession.getJsessionInfo().getMembercode();
        String memebername = jirpession.getJsessionInfo().getUsername();
        String memebertype = jirpession.getJsessionInfo().getUsertype();
        String ipaddress = jirpession.getJsessionInfo().getIpaddress();
        String macaddress = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String logid = getRandomNumber();
        for (int i =0; i < pTableOrObjectName.length; i++) {
            logData = new Log_CLXAuditTrail_Master();
            logId = new Log_CLXAuditTrail_MasterId();
            try {
                String json = mapper.writeValueAsString(dto[i]);
                logId.setLogentryid(logid);
                logId.setLogid(new BigDecimal(String.valueOf(i+1)));
                logData.setId(logId);
                logData.setLogbymembercode(memebercode);
                logData.setLogbymemberid(memeberid);
                logData.setLogbymembername(memebername);
                logData.setLogbymembertype(memebertype);
                logData.setLogdate(new Date());
                logData.setLogstatus("S");
                logData.setMenuname(pRightsName);
                logData.setModulecode(pModuleName);
                logData.setNewvalue(json);
                logData.setActivity(pOperation[i]);
                logData.setIpaddress(ipaddress);
                logData.setMacaddress(macaddress);
                logData.setOntable(pTableOrObjectName[i]);
                logData.setMacaddress("jsonLogData.toString()");
                logData.setRemarks(pRemarks);
                //  Thread.sleep(2222);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            daoFactory.getLoginLogDAO().saveOrUpdate(logData);
        }
    }

    public String getRandomNumber() {

        DateFormat df = new SimpleDateFormat("HH:mm:ss.SSSSSS");
        String processingId = "LOG" + (df.format(new Date())).replace(":", "").replace(".", "") + (int) (Math.random() * 973) + (int) (Math.random() * 591) + 137;
        processingId = processingId.substring(0, 20);
        return processingId;
    }   
}
