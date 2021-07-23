package com.jilit.irp.bso.biz;

import com.jilit.irp.context.AppContext;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.CityMaster;
import com.jilit.irp.persistence.dto.CityMasterId;
import com.jilit.irp.persistence.dto.CountryMaster;
import com.jilit.irp.persistence.dto.IdGenerationControl;
import com.jilit.irp.persistence.dto.IdGenerationControlId;
import com.jilit.irp.persistence.dto.IdGenerationSetup;
import com.jilit.irp.persistence.dto.InstituteMaster;
import com.jilit.irp.persistence.dto.NumberingControlOthers;
import com.jilit.irp.persistence.dto.NumberingControlOthersId;
import com.jilit.irp.persistence.dto.StateMaster;
import com.jilit.irp.persistence.dto.StateMasterId;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.util.JIRPDBUtil;
import com.jilit.irp.util.JIRPDateUtil;
import com.jilit.irp.util.JIRPStringUtil;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
//

public class BusinessService {

    @Autowired
    DAOFactory daoFactory;
    private Session session = null;
    private Transaction tx = null;
    private ServletContext context;

    /// -----------------------------------------------------------
    /// Function to get various numbers in the desired format
    //-------------------------------------------------------------
    private List<HashMap> NumberDataGenDataList = null;

    public BusinessService() {
    }

    public BusinessService(ServletContext context, DAOFactory daoFactory) {
        this.context = context;
        this.daoFactory = daoFactory;
    }

    public BusinessService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public BusinessService(DAOFactory daoFactory, boolean createsession) {
        this.daoFactory = daoFactory;
        if (createsession) {
            if (session == null) {
                session = daoFactory.getCountryMasterDAO().getHibernateSession();
                tx = session.beginTransaction();

            }
        }
    }

    public void insertInIdGenerationControl(Object obj) {
        session.save(obj);
        insertOrUpdateInIdGen();
        tx.commit();
    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return tx;
    }

    public void rollback() {
        if (tx != null) {
            tx.rollback();
        }
    }

    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    public boolean checkIfSessionIsNull() {
        return session == null || !session.isOpen();
    }

    public boolean validateCompanyOrInstId(String I_or_C, String comp_or_inst_unqid) {
        List list = new ArrayList();
        if (I_or_C.equals("I")) {
            list = daoFactory.getInstituteMasterDAO().getInstituteUniqueIdBase(comp_or_inst_unqid);
        }
        if (!list.isEmpty() && list.size() > 0) {
            return true;
        }

        return false;
    }

    public void insertInIdGenerationControl(List objects) {
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            session.save(object);
        }
        insertOrUpdateInIdGen();
        tx.commit();
    }

    public void insertOrUpdateInIdGenerationControl(List objects, boolean flag) {
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            session.saveOrUpdate(object);
        }
        if (flag) {
            insertOrUpdateInIdGen();
        }
        tx.commit();
    }

    public void insertInIdGenerationControl(List insertObj, List deleteObj) {
        for (int i = 0; i < deleteObj.size(); i++) {
            Object object = deleteObj.get(i);
            session.delete(object);
        }
        for (int i = 0; i < insertObj.size(); i++) {
            Object object = insertObj.get(i);
            session.saveOrUpdate(object);
        }
        insertOrUpdateInIdGen();
        tx.commit();
    }

    public void insertupdateInIdGenerationControl(List insertObj, List deleteObj, boolean flag) {
        for (int i = 0; i < deleteObj.size(); i++) {
            Object object = deleteObj.get(i);
            session.delete(object);
        }
        for (int i = 0; i < insertObj.size(); i++) {
            Object object = insertObj.get(i);
            session.saveOrUpdate(object);
        }
        if (flag) {
            insertOrUpdateInIdGen();
        }
        tx.commit();
    }

    public void insertInIdGenerationControl_STRNew(List insertObj, List saveUpdateObjList, List deleteObj) {
        if (session == null) {
            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();
        }
        for (int i = 0; i < deleteObj.size(); i++) {
            Object object = deleteObj.get(i);
            session.delete(object);
        }
        if (insertObj != null && !(insertObj.isEmpty()) && insertObj.size() > 0) {
            for (int i = 0; i < insertObj.size(); i++) {
                Object object = insertObj.get(i);
                session.save(object);
            }
        }
        for (int i = 0; i < saveUpdateObjList.size(); i++) {
            Object object = saveUpdateObjList.get(i);
            //session.saveOrUpdate(object);
            session.merge(object);
        }
        if (idGenDataList != null && idGenDataList.size() > 0) {
            insertOrUpdateInIdGen();
        }
        tx.commit();
    }

    public void insertInIdGenerationControl(List insertObj, List deleteObj, List updateList, boolean flag) {
        if (session == null) {
            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();
        }
        for (int i = 0; i < deleteObj.size(); i++) {
            Object object = deleteObj.get(i);
            session.delete(object);
        }
        for (int i = 0; i < insertObj.size(); i++) {
            Object object = insertObj.get(i);
            session.save(object);
        }
        for (int i = 0; i < updateList.size(); i++) {
            Object object = updateList.get(i);
            session.saveOrUpdate(object);
        }
        if (flag) {
            //      if (session == null) {
            insertOrUpdateInOtherNumber();
            //      }
        }
        tx.commit();
    }

    public void insertOrUpdateInIdGenerationControl(Object obj, boolean flag) {
        session.saveOrUpdate(obj);
        if (flag) {
            insertOrUpdateInIdGen();
        }
        tx.commit();
    }

    public void insertOrUpdateInIdGenerationControl(Object obj) {
        //System.err.println("ZZZZZZ--"+obj+"/"+generatedid+"/"+idcode);
        session.saveOrUpdate(obj);

        insertOrUpdateInIdGen();

        tx.commit();
    }

    public void insertOrUpdateInIdGen() {
        IdGenerationControl idGenerationControl = null;
        if (idGenDataList != null) {
            for (int i = 0; i < idGenDataList.size(); i++) {
                HashMap hashMap = idGenDataList.get(i);
                //  BigDecimal lastrunningno = new BigDecimal(hashMap.get("lastrunningno").toString()) ;
                BigDecimal lastrunningno = new BigDecimal(hashMap.get("generatedid").toString().substring(9, 16));
                idGenerationControl = (IdGenerationControl) hashMap.get("dto");
                idGenerationControl.setLastid(hashMap.get("generatedid").toString());
                idGenerationControl.setLastrunningno(lastrunningno);
                try {
                    if ((Boolean) hashMap.get("insertOrUpdate")) {//update
                        session.save(idGenerationControl);

                    } else {//insert
                        session.update(idGenerationControl);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertOrUpdateInIdGen(Session session) {
        IdGenerationControl idGenerationControl = null;
        for (int i = 0; i < idGenDataList.size(); i++) {
            HashMap hashMap = idGenDataList.get(i);

            BigDecimal lastrunningno = new BigDecimal(hashMap.get("generatedid").toString().substring(9, 16));
            idGenerationControl = (IdGenerationControl) hashMap.get("dto");
            idGenerationControl.setLastid(hashMap.get("generatedid").toString());
            idGenerationControl.setLastrunningno(lastrunningno);
            try {
                if ((Boolean) hashMap.get("insertOrUpdate")) {//insert
                    session.save(idGenerationControl);

                } else {//update
                    session.update(idGenerationControl);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<HashMap> idGenDataList = null;

    public String generateId(String pCharacters) {

        if (pCharacters.equals("INST")) {
            List<InstituteMaster> list = (List<InstituteMaster>) daoFactory.getInstituteMasterDAO().find("from InstituteMaster where rownum=1 order by instituteid desc");
            return JIRPDBUtil.generateId((JIRPStringUtil.isNullorEmpty(list)) ? "" : list.get(0).getInstituteid(), pCharacters);

        }

        return "";

    }

    public String generateId(String idcode, String comp_or_inst_unqid, String I_or_C, boolean I_C_Check) {
        BigDecimal lastrunningno = new BigDecimal("0");
        if (comp_or_inst_unqid == null) {
            comp_or_inst_unqid = "JP";   //4 zeroes in case table is neither related to company nor to institute
        } else if (I_C_Check && !validateCompanyOrInstId(I_or_C, comp_or_inst_unqid)) {
            return "*";
        }
        String mth = (new SimpleDateFormat("MM")).format(System.currentTimeMillis());
        String year = (new SimpleDateFormat("yy")).format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(16);
        String generatedNumber = "0000001";
        //prefix of that table:
        //String tablePrefix="ACCG";

        String tablePrefix = ((IdGenerationSetup) daoFactory.getIdGenerationSetupDAO().findByPrimaryKey(idcode)).getPrefix();
        if (idGenDataList != null) {
            for (int i = 0; i < idGenDataList.size(); i++) {
                HashMap hashMap = idGenDataList.get(i);
                String lastGenId = hashMap.get("generatedid").toString();
                if (tablePrefix.equals(lastGenId.substring(2, 5))) {

                    BigDecimal dec = new BigDecimal(lastGenId.substring(9, 16));
                    dec = dec.add(new BigDecimal(1));
                    String newgennumber = StringUtils.leftPad(String.valueOf(dec), 7, '0');
                    String newGeneratedId = lastGenId.substring(0, 9) + newgennumber;
                    hashMap.put("generatedid", newGeneratedId);
                    return newGeneratedId;
                    //IdGenerationControl control = (IdGenerationControl) hashMap.get("dto");
                    //control.setLastrunningno(control.getLastrunningno().add(new BigDecimal(1)));
                }
            }
        }
        char alpha = 'A';
        List maxAlphaList = (List) daoFactory.getIdGenerationControlDAO().find("select max(id.alpha) from IdGenerationControl where id.icid = '" + comp_or_inst_unqid + "' and id.idcode = '" + idcode + "' and trim(id.year) = '" + year + "' and id.month = '" + mth + "'");
        //   try{
        if (session == null) {
            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();

        }
        boolean insert = true;
        IdGenerationControl idGenerationControl = null;
        if (maxAlphaList.get(0) != null) {
            alpha = maxAlphaList.get(0).toString().toCharArray()[0];
            //  System.err.println("" + comp_or_inst_unqid + "" + idcode + "" + year + "" + mth + "" + alpha);
            try {
                idGenerationControl = (IdGenerationControl) session.get(IdGenerationControl.class, new IdGenerationControlId(comp_or_inst_unqid, idcode, year, mth, alpha), LockMode.UPGRADE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //if (String.valueOf(idGenerationControl.getLastrunningno()).equals("9999999")) {
            //  alpha++;
            //} else {
            insert = false;
            generatedNumber = StringUtils.leftPad(String.valueOf(idGenerationControl.getLastrunningno().intValue() + 1), 7, '0');
            lastrunningno = new BigDecimal(idGenerationControl.getLastrunningno().intValue() + 1);
            // }
        } else {

            idGenerationControl = new IdGenerationControl();
            idGenerationControl.setId(new IdGenerationControlId(comp_or_inst_unqid, idcode, year, mth, alpha));
            lastrunningno = new BigDecimal("1");
        }
        if (idGenDataList == null) {
            idGenDataList = new ArrayList<HashMap>();
        }
        // sb.append(comp_or_inst_unqid + tablePrefix + year + mth + alpha + generatedNumber);
        sb.append(comp_or_inst_unqid + tablePrefix + year + mth + generatedNumber);
        idGenerationControl.setLastrunningno(BigDecimal.ONE);
        idGenerationControl.setLastid(sb.toString());
        HashMap map = new HashMap();
        map.put("insertOrUpdate", insert);
        map.put("dto", idGenerationControl);
        map.put("generatedid", sb.toString());
        map.put("lastrunningno", lastrunningno);
        idGenDataList.add(map);

        return sb.toString();
    }

    public void insertOrUpdateInOtherNumber(Object obj) {

        session.saveOrUpdate(obj);
        insertOrUpdateInOtherNumber();
        tx.commit();

    }

    public void insertOrUpdateInOtherNumber(List objects) {

        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            session.saveOrUpdate(object);
        }
        insertOrUpdateInOtherNumber();
        tx.commit();

    }

    public void insertOrUpdateInOtherNumber() {
        for (int i = 0; i < NumberDataGenDataList.size(); i++) {
            HashMap hashMap = NumberDataGenDataList.get(i);
            NumberingControlOthers nc1 = (NumberingControlOthers) hashMap.get("dto");
            if ((Boolean) hashMap.get("insert")) {//insert
                session.save(nc1);
            } else {//update
                session.update(nc1);

            }
        }
        if (idGenDataList != null) {
            IdGenerationControl idGenerationControl = null;
            for (int i = 0; i < idGenDataList.size(); i++) {
                HashMap hashMap = idGenDataList.get(i);

                BigDecimal lastrunningno = new BigDecimal(hashMap.get("generatedid").toString().substring(9, 16));
                idGenerationControl = (IdGenerationControl) hashMap.get("dto");
                idGenerationControl.setLastid(hashMap.get("generatedid").toString());
                idGenerationControl.setLastrunningno(lastrunningno);

                if ((Boolean) hashMap.get("insertOrUpdate")) {//update
                    session.save(idGenerationControl);

                } else {//insert
                    session.update(idGenerationControl);

                }
            }
        }
        idGenDataList = null;
    }

    public String getPropertyValue(String propertyname, String propertyfilename) {
        String propertyvalue = "";
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream(context.getRealPath("/WEB-INF/classes/" + propertyfilename)));
            //get the property value
            propertyvalue = prop.getProperty(propertyname);
            System.err.println("Property name is " + propertyname + " and value is " + propertyvalue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return propertyvalue;
    }

    public String generateNumber(final String pCompanyOrInstituteID, final String pCompInstFlag, final String pPrefix, final Date pTransDate) {

        if (daoFactory == null) {
            daoFactory = (DAOFactory) AppContext.getApplicationContext().getBean("DAOFactory");
        }

        String mVchNo = "";
        String totalwidth = "";
        String mYMD = "";
        long mLastRunningNo;
        String mGroupID = "";
        Date mLastVoucherDate = new Date();
        String returnValue = "";
        boolean insert = false;
        //-- First Get the Group ID ;
        try {

            List<Object[]> numberingGroupList = (List<Object[]>) daoFactory.getNumberingSetupOthersDAO().getNumberingSetupGroupID(pCompanyOrInstituteID, pCompInstFlag, pPrefix);
            if (numberingGroupList.size() == 0) {
                return returnValue = "#";
            } else {
                mGroupID = numberingGroupList.get(0)[0].toString();
                mYMD = numberingGroupList.get(0)[1].toString();
                totalwidth = numberingGroupList.get(0)[2].toString();
                if (mGroupID.equals("")) {
                    return returnValue = "^";
                }
            }

            String codelength = totalwidth;// Integer.parseInt(totalwidth);
            String mdd = JIRPDateUtil.convertDateFormat(pTransDate, "mm");
            String mth = JIRPDateUtil.convertDateFormat(pTransDate, "MM");
            String myy = JIRPDateUtil.convertDateFormat(pTransDate, "yy");
            String myyyy = JIRPDateUtil.convertDateFormat(pTransDate, "yyyy");
            String mYYYYMM = JIRPDateUtil.convertDateFormat(pTransDate, "yyyyMM");
            String mMMDD = JIRPDateUtil.convertDateFormat(pTransDate, "MMdd");
            String mYYYYMMDD = JIRPDateUtil.convertDateFormat(pTransDate, "yyyyMMdd");

            mVchNo = pPrefix;
            if (mYMD.equals("YYMM")) {
                mVchNo = mVchNo + myy + mdd;
            } else if (mYMD.equals("YY")) {
                mVchNo = mVchNo + myy;
            } else if (mYMD.equals("YYYY")) {
                mVchNo = mVchNo + myyyy;
            } else if (mYMD.equals("YYYYMM")) {
                mVchNo = mVchNo + mYYYYMM;
            } else if (mYMD.equals("MMDD")) {
                mVchNo = mVchNo + mMMDD;
            } else if (mYMD.equals("YYYYMMDD")) {
                mVchNo = mVchNo + mYYYYMMDD;
            }

//Open NC(mYMD, mGroupId);
            if (session == null) {
                session = daoFactory.getUtilDAO().getHibernateSession();
                tx = session.beginTransaction();
            }
            NumberingControlOthers nc = (NumberingControlOthers) daoFactory.getNumberingControlOthersDAO().getNumberingControlData(mYMD, mGroupID, session);
//Fetch NC Into NCRec;

            if (nc == null) {
                mLastRunningNo = 1;
            } else {
                mLastRunningNo = Integer.parseInt(nc.getLastrunningno() == null ? "0" : nc.getLastrunningno().toString()) + 1;

                try {
                    if (nc.getLastnumberdate() != null) {
                        mLastVoucherDate = JIRPDateUtil.convertToDateFormat(nc.getLastnumberdate().toString(), "DD-MM-YYYY");
                    } else //mLastVoucherDate =JIRPDateUtil.convertToDateFormat(pVchDate,"DD-MM-YYYY");
                    {
                        mLastVoucherDate = JIRPDateUtil.convertToDateFormat(JIRPDateUtil.convertDateFormat(pTransDate, "DD-MM-YYYY"), "DD-MM-YYYY");
                    }
                } catch (Exception e) {
                }
            }

            int vc_N = Integer.parseInt(totalwidth) - (mVchNo.trim().length());

            if (vc_N > 0) {
                String s = String.valueOf(mLastRunningNo);
                if (s.length() > vc_N) {
                    return returnValue = "*";
                }
                //String lpadString = LPad(mLastRunningNo, vc_N, "0");
                String lpadString = String.format("%" + vc_N + "s", mLastRunningNo).replace(' ', '0');
                mVchNo = mVchNo + "~N~";
                mVchNo = mVchNo.replace("~N~", lpadString);
            } else {
                return returnValue = "$";
            }

            if (nc == null) {
                insert = true;

                nc = new NumberingControlOthers();
                NumberingControlOthersId id = new NumberingControlOthersId();
                id.setGroupid(mGroupID);
                id.setYmd(mYMD);
                nc.setId(id);
                nc.setLastno(mVchNo);
                nc.setLastrunningno(new BigDecimal(mLastRunningNo));
                nc.setLastnumberdate(pTransDate);
            } else {
                insert = false;
                nc.setLastno(mVchNo);
                nc.setLastrunningno(new BigDecimal(mLastRunningNo));
                if (pTransDate.compareTo(mLastVoucherDate) > 0) {
                    nc.setLastnumberdate(pTransDate);
                } else {
                    nc.setLastnumberdate(mLastVoucherDate);
                }

            }

//------------------------------------------------------------------
            if (NumberDataGenDataList == null) {
                NumberDataGenDataList = new ArrayList<HashMap>();
            }
            //If Retrun Value has any error character then Show just Error
            if (returnValue.length() == 1) {
                mVchNo = returnValue;
            } else {
                HashMap map = new HashMap();
                map.put("insert", insert);
                map.put("dto", nc);
                //map.put("voucherno", sb.toString());
                NumberDataGenDataList.add(map);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//------------------------------------------------------------------
        return (mVchNo);
    }

    public String getSTYTypeId(String stytype) {

        List<StyType> list = (List<StyType>) daoFactory.getUtilDAO().findByExample("findBySTYType", stytype, new StyType(), -1, 1);
        if (JIRPStringUtil.isNullorEmpty(list)) {
            return null;
        }
        return list.get(0).getId().getStytypeid();
    }

    public String generateIds(String idcode, String comp_or_inst_unqid, String I_or_C, DAOFactory daoFactory) {

        if (comp_or_inst_unqid == null || comp_or_inst_unqid.equals("")) {
            comp_or_inst_unqid = "NIRU";   //4 zeroes in case table is neither related to company nor to institute
        } else if (!validateCompanyOrInstId(I_or_C, comp_or_inst_unqid)) {
            return "*";
        }
        BigDecimal lastrunningno = new BigDecimal("0");
        String mth = (new SimpleDateFormat("MM")).format(System.currentTimeMillis());
        String year = (new SimpleDateFormat("yy")).format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(16);
        String generatedNumber = "0000001";
        //prefix of that table:
        //String tablePrefix="ACCG";

        String tablePrefix = ((IdGenerationSetup) daoFactory.getIdGenerationSetupDAO().findByPrimaryKey(idcode)).getPrefix();
        if (idGenDataList != null) {
            for (int i = 0; i < idGenDataList.size(); i++) {
                HashMap hashMap = idGenDataList.get(i);
                String lastGenId = hashMap.get("generatedid").toString();
                if (tablePrefix.equals(lastGenId.substring(2, 5))) {

                    BigDecimal dec = new BigDecimal(lastGenId.substring(9, 16));
                    dec = dec.add(new BigDecimal(1));
                    String newgennumber = StringUtils.leftPad(String.valueOf(dec), 7, '0');
                    String newGeneratedId = lastGenId.substring(0, 9) + newgennumber;
                    hashMap.put("generatedid", newGeneratedId);
                    return newGeneratedId;
                    //IdGenerationControl control = (IdGenerationControl) hashMap.get("dto");
                    //control.setLastrunningno(control.getLastrunningno().add(new BigDecimal(1)));
                }
            }
        }
        char alpha = 'A';
        List maxAlphaList = (List) daoFactory.getIdGenerationControlDAO().find("select max(id.alpha) from IdGenerationControl where id.icid = '" + comp_or_inst_unqid + "' and id.idcode = '" + idcode + "' and id.year = '" + year + "' and id.month = '" + mth + "'");
        //   try{
        if (session == null) {
            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();

        }
        boolean insert = true;
        IdGenerationControl idGenerationControl = null;
        if (maxAlphaList.get(0) != null) {
            alpha = maxAlphaList.get(0).toString().toCharArray()[0];
            System.err.println("" + comp_or_inst_unqid + "" + idcode + "" + year + "" + mth + "" + alpha);
            try {
                idGenerationControl = (IdGenerationControl) session.get(IdGenerationControl.class, new IdGenerationControlId(comp_or_inst_unqid, idcode, year, mth, alpha), LockMode.UPGRADE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (idGenerationControl != null && String.valueOf(idGenerationControl.getLastrunningno()).equals("9999999")) {
                alpha++;
            } else {
                insert = false;
                generatedNumber = StringUtils.leftPad(String.valueOf(idGenerationControl.getLastrunningno().intValue() + 1), 7, '0');
                lastrunningno = new BigDecimal(idGenerationControl.getLastrunningno().intValue() + 1);
            }
        } else {

            idGenerationControl = new IdGenerationControl();
            // idGenerationControl.setId(new IdGenerationControlId(comp_or_inst_unqid, idcode, year + "  ", mth, alpha));
            idGenerationControl.setId(new IdGenerationControlId(comp_or_inst_unqid, idcode, year, mth, alpha));
            lastrunningno = BigDecimal.ONE;
        }

        if (idGenDataList == null) {
            idGenDataList = new ArrayList<HashMap>();
        }
        sb.append(comp_or_inst_unqid + tablePrefix + year + mth + generatedNumber);
        HashMap map = new HashMap();
        map.put("insertOrUpdate", insert);
        map.put("dto", idGenerationControl);
        map.put("dto", idGenerationControl);
        map.put("generatedid", sb.toString());
        map.put("lastrunningno", lastrunningno);
        idGenDataList.add(map);

        return sb.toString();
    }

    public void updateInIdGenerationControl() {
        insertOrUpdateInIdGen();
        tx.commit();
    }

    public void saveWithoutIDGeneration(List SaveObjects) {
        if (session == null) {
            session = daoFactory.getUtilDAO().getHibernateSession();
            tx = session.beginTransaction();

        }
        for (int i = 0; i < SaveObjects.size(); i++) {
            Object object = SaveObjects.get(i);
            session.save(object);
        }
        tx.commit();
    }
}
