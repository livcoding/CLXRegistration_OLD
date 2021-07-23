package com.jilit.irp.Login;

/**
 *
 * @author ashutosh1.kumar
 */
import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.EmployeePhoto;
import com.jilit.irp.persistence.dto.Log_LoginLogInfo;
import com.jilit.irp.persistence.dto.Log_LoginLogInfoId;
import com.jilit.irp.persistence.dto.Notify_NotificationTo;
import com.jilit.irp.persistence.dto.Notify_NotificationToId;
import com.jilit.irp.persistence.dto.Sct_IrpMlp;
import com.jilit.irp.persistence.dto.Sct_IrpUserType;
import com.jilit.irp.persistence.dto.Sct_IrpUsers;
import com.jilit.irp.persistence.dto.Sct_KioskMarqueeText;
import com.jilit.irp.persistence.dto.Sct_unsuccesslogindetail;
import com.jilit.irp.persistence.dto.Sct_unsuccesslogindetailId;
import com.jilit.irp.util.JIRPDateUtil;
import com.jilit.irp.util.JIRPSession;
import com.jilit.irp.util.JIRPSessionInfo;
import com.jilit.irp.util.OLTEncryption;
import com.jilit.irp.util.OLTEncryption.EncryptionException;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONException;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*----------------- for AD Integration -********************/
import com.sun.jndi.ldap.LdapCtxFactory;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
//import org.acegisecurity.GrantedAuthority;

/**
 *
 * @author ashutosh1.Kumar
 */
@Service
public class LoginService implements LoginIservice, ServletContextAware {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;
    ServletContext context;
    private static final String NORMAL = "\u001b[0m";
    private static final String BOLD = "\u001b[1m";
    private static final String GREEN = "\u001b[32m";

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public List<Object> checkUserid_Password(String username, String password, String usertype, String instituteid, HttpServletRequest request) {
        List<Object> retList = new ArrayList<Object>();
        String lastlogin = "";
        String message = "";
        boolean validpassword = false;

        boolean flag = false;
        String newkey = "";
        String encodedusertype = "";
        BusinessService bservice = new BusinessService(context, daoFactory);
        String usernameFromToken = null;
        try {
            OLTEncryption oLTEncryption = new OLTEncryption();
            if (username.equals("access_token") && usertype != null && password.toString().length() > 10) {
                try {
                    usernameFromToken = getUserFromServer(password, "Barear", "fine", instituteid);
                    if (usernameFromToken != null && !usernameFromToken.equals("anonymus")) {
//                        System.out.print("Ashutosh" + usernameFromToken);
                        usernameFromToken = oLTEncryption.encode(usernameFromToken.replaceAll("\"", "").split(":")[1].trim());
                        // usernameFromToken = oLTEncryption.encode(usernameFromToken.trim());
                        Sct_IrpMlp sct_IrpMlp1 = new Sct_IrpMlp();
                        ///////////////////Mathod Added in DAO fro getting SCT_IRPMLP Object with the Help of Username////////////
                        sct_IrpMlp1 = (Sct_IrpMlp) daoFactory.getSct_IrpMlpDAO().getLoginDataObject(usernameFromToken);
                        //   OLTEncryption encPass = new OLTEncryption(sct_SecurityInfo.getLastkey());
                        username = oLTEncryption.decode(sct_IrpMlp1.getData4());
                        flag = true;
                        password = sct_IrpMlp1.getData6().trim();
                        usertype = oLTEncryption.decode(sct_IrpMlp1.getData3());
                        instituteid = oLTEncryption.decode(sct_IrpMlp1.getData1());

                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
            if (username != null && !username.equals("")) {
                username = oLTEncryption.encode(username);
//                System.out.println(oLTEncryption.decode(username));
            }
            /*            if (usertype != null && !usertype.equals("")) {
            encodedusertype = oLTEncryption.encode(usertype);
            }
             */
 /*--new-*/
            if (usertype != null && !usertype.equals("")) {
                if (usertype.equals("P")) {
                    encodedusertype = oLTEncryption.encode("S");
                } else {
                    encodedusertype = oLTEncryption.encode(usertype);
                }
            }
            /*--new-*/
            if (instituteid != null && !instituteid.equals("")) {
                instituteid = oLTEncryption.encode(instituteid);
            }
            List<Sct_IrpMlp> sct_IrpMlplist = daoFactory.getSct_IrpMlpDAO().loginUserData(username, encodedusertype, instituteid, usertype);
            if (sct_IrpMlplist != null && sct_IrpMlplist.size() > 0) {
                Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) sct_IrpMlplist.get(0);

                //........................................ Code For Set The JIRPSession .................................//  
                HttpSession session = request.getSession(true);
                session.setAttribute("userid", sct_IrpMlp.getData2());
                session.setAttribute("tokenLogin", "true");
                jirpsession.getJsessionInfo().setSession(session);
                jirpsession.getJsessionInfo().setIpaddress(request.getRemoteAddr());
                jirpsession.getJsessionInfo().setMemberid(oLTEncryption.decode(sct_IrpMlp.getData5()));
                jirpsession.getJsessionInfo().setUsertype(oLTEncryption.decode(sct_IrpMlp.getData3()));
                jirpsession.getJsessionInfo().setUserid(oLTEncryption.decode(sct_IrpMlp.getData2()));

                List<Object[]> loginemprecord = (List<Object[]>) daoFactory.getSct_IrpMlpDAO().getLoginEmpRecord(oLTEncryption.decode(sct_IrpMlp.getData5()));
                if (loginemprecord != null && loginemprecord.size() > 0) {
                    jirpsession.getJsessionInfo().setUsername(loginemprecord.get(0)[0].toString());
                    jirpsession.getJsessionInfo().setMembercode(loginemprecord.get(0)[1].toString());
                }

                // To Be changed when popup for Multiple Inst/Company will be adopted
//                jirpsessioninfo.setSelectedinstituteid("SOAUINSD1312A0000002");
//                jirpsessioninfo.setSelectedinstituteuniqueid("ITER");
//                jirpsessioninfo.setSelectedcompanyid("CMP20131200000000001");
                if (sct_IrpMlp.getData1() != null && sct_IrpMlp.getData1().equals("") && sct_IrpMlp.getData2() != null && sct_IrpMlp.getData2().equals("") && sct_IrpMlp.getData3() != null && sct_IrpMlp.getData3().equals("") && sct_IrpMlp.getData4() != null && sct_IrpMlp.getData4().equals("") && sct_IrpMlp.getData5() != null && sct_IrpMlp.getData5().equals("")) {
                    retList.add("Error!");
                    retList.add("Data not found");
                    return retList;
                }
                if (sct_IrpMlp.getData13() != null && sct_IrpMlp.getData13().equals(oLTEncryption.encode("Y"))) {// check deactive and check for Always valid or valid in given time period
                    retList.add("error");
                    retList.add("Your account has been Deactive/Not valid");
                    return retList;
                }
                if (isValidUser(sct_IrpMlp)) {
                    retList.add("error");
                    retList.add("Your account has been expired.");
                    return retList;
                }
                if (sct_IrpMlp.getData11() != null && sct_IrpMlp.getData11().equals(oLTEncryption.encode("Y"))) { // check for accoucnt lock
                    retList.add("error");
//                    System.out.println(sct_IrpMlp.getData4());
                    retList.add("Your account has been Locked");
                    return retList;
                }
                if (sct_IrpMlp.getData16() != null && sct_IrpMlp.getData16().equals(oLTEncryption.encode("Y"))) { // check for username and password expired
                    retList.add("error");
                    retList.add("Your account has been expired");
                    return retList;
                }
                //--------------------------------Code For Deactive User---------------------------------------//
                String memberid = oLTEncryption.decode(sct_IrpMlp.getData5());
                if (usertype.equals("S")) {
                    List<Object[]> list = (List<Object[]>) daoFactory.getStudentMasterDAO().getActiveDeactiveStudent(memberid);
                    if (list != null && list.size() > 0) {
                        if (list.get(0)[1] != null && list.get(0)[1].equals("D")) {
                            retList.add("error");
                            retList.add("Your account has been Deactive/Not valid");
                            return retList;
                        }
                    }
                }
                if (usertype.equals("E")) {
                    List<Object[]> list = (List<Object[]>) daoFactory.getStudentMasterDAO().getActiveDeactiveEmployee(memberid);
                    if (list != null && list.size() > 0) {
                        if (list.get(0)[1] != null && list.get(0)[1].equals("Y")) {
                            retList.add("error");
                            retList.add("Your account has been Deactive/Not valid");
                            return retList;
                        }
                    }
                }
                //--------------------------------------------------------------------------------------------------
                validpassword = true;//since Outh has been used for Authentication and token already provided
                //System.err.println("jass" + oLTEncryption.decode(sct_IrpMlp.getData2()));
                // sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey(oLTEncryption.decode(sct_IrpMlp.getData2()));
                //sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey("IITRUSER1007A0000001");
                //   validpassword = isValidPassword(sct_IrpMlp, newkey, password, usertype, sct_SecurityInfo);
//                System.err.println("validpassword" + validpassword);
                if (validpassword) {
                    //----------sumit
                    String loginrestrictedtime = bservice.getPropertyValue("SecurityLoginRestrictedTime", "numbercode.properties");
                    List<Object[]> statuslist = daoFactory.getSct_IrpUsersDAO().getLoginStatus(oLTEncryption.decode(sct_IrpMlp.getData2()));
                    Date d = new Date();
                    if (statuslist.get(0)[1] != null) {
                        d = (Date) statuslist.get(0)[1];
                    }
                    Date cdate = new Date();
                    int month = cdate.getMonth() + 1;
                    Integer diff = month - d.getMonth();
                    String status = "";
                    boolean notrestricted = true;
                    long lastvisiteddate = 0;
                    if (statuslist != null && statuslist.size() > 0) {
                        status = statuslist.get(0)[0] == null ? "" : statuslist.get(0)[0].toString();
                        lastvisiteddate = Long.parseLong(diff.toString());
                    }
                    if (lastvisiteddate < Integer.parseInt(loginrestrictedtime)) {
                        if (usertype.equals("A")) {
                            notrestricted = true;
                        } else {
                            notrestricted = false;
                        }
                    }
                    // modification on date 23-09-2017 to resolve os-ticket 019350
                    if (status.equals("") || status.equals("O") || status.equals("I") || notrestricted) {
                        if (retList.size() == 0) {

                            String noOfhitThisday = daoFactory.getLoginLogDAO().getSlno(oLTEncryption.decode(sct_IrpMlp.getData2()), new Date());
                            String maxnoOfhitAday;
                            String NoForGiveMaxAttemptAlert = bservice.getPropertyValue("NoForGiveMaxAttemptAlert", "numbercode.properties");
                            if (usertype.equals("S")) {
                                maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayStudent", "numbercode.properties");
                            } else {
                                maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayOther", "numbercode.properties");
                            }
                            if (Integer.parseInt(noOfhitThisday) <= Integer.parseInt(maxnoOfhitAday)) {
                                if (sct_IrpMlp.getData15() != null && sct_IrpMlp.getData15().equals(oLTEncryption.encode("Y"))) {// check password expire
                                    retList.add("success");
                                    retList.add("Your Password has been Expired please provide new password");
                                } else if (Integer.parseInt(maxnoOfhitAday) - Integer.parseInt(noOfhitThisday) <= Integer.parseInt(NoForGiveMaxAttemptAlert)) {
                                    if (Integer.parseInt(maxnoOfhitAday) == Integer.parseInt(noOfhitThisday)) {
                                        message = checkvalidationsuccess(sct_IrpMlp, usertype, "", password);
                                        retList.add("error");
                                        retList.add(message);
                                        return retList;
                                    } else {
                                        retList.add("success");
                                        retList.add("Only " + (Integer.parseInt(maxnoOfhitAday) - Integer.parseInt(noOfhitThisday)) + " Attempt Is Allow For Today");
                                    }
                                } else {
                                    retList.add("success");
                                    retList.add("Successfuly Login");
                                }
                            } else {
                                message = "";
                                retList.add("error");
                                retList.add("Max Trial Exceed For Today" + message);
                                return retList;
                            }

                            //retList.add(getBaseModules());
                            return retList;
                        }
                    } else {
                        message = "";
                        retList.add("error");
                        retList.add("User Allready Login!Please Login After.........." + message);
                        return retList;
                    }
                    //------sumit
                } else {
                    message = "";
                    retList.add("error");
                    retList.add("The username or password is incorrect." + message);
                    return retList;
                }
            } else {
                retList.add("error");
                retList.add("The username or password is incorrect.");
                return retList;
            }
        } catch (Exception e) {
            retList.add("error");
            retList.add("Unable to Login ");
            e.printStackTrace();
        } finally {
            bservice.closeSession();
        }
        return retList;
    }

    public void getRightsIdUserWise(String Sct_UserRolesCriteraBased, String qry_Urts, Model model, HttpServletRequest request) {
        List<Object[]> userRoleRightsList = new ArrayList<Object[]>();
        List<Object[]> userRightsList = new ArrayList<Object[]>();
        List finalList = new ArrayList();
        EmployeePhoto empPhoto = new EmployeePhoto();
        List<String> rightsIdList = new ArrayList<String>();
        List<String> parentIdList = new ArrayList<String>();
        List<String> rightsIdList1 = new ArrayList<String>();
        List<String> rightsIdList2 = new ArrayList<String>();
        Map menu = new HashMap();
        Map<String, Object[]> rightsMap = new HashMap<String, Object[]>();
        try {
            String instCodeId[] = request.getParameter("institute").split("@");
            model.addAttribute("institute", instCodeId[1]);//institute code
            jirpsession.getJsessionInfo().setSelectedinstituteid(instCodeId[0]); //institute id
            List<Object[]> list1 = daoFactory.getInstituteMasterDAO().getAllInstituteCode(instCodeId[0]);
            jirpsession.getJsessionInfo().setSelectedinstituteuniqueid(list1.get(0)[2].toString());
            jirpsession.getJsessionInfo().setSelectedclientid(list1.get(0)[3].toString());
            jirpsession.getJsessionInfo().setSelectedcompanyid(list1.get(0)[4].toString());

            userRoleRightsList = (List<Object[]>) daoFactory.getUtilDAO().findSimpleData(Sct_UserRolesCriteraBased, new String[]{jirpsession.getJsessionInfo().getUserid(), jirpsession.getJsessionInfo().getSelectedinstituteid().toString()});
            userRightsList = (List<Object[]>) daoFactory.getUtilDAO().findSimpleData(qry_Urts, new String[]{jirpsession.getJsessionInfo().getUserid(), jirpsession.getJsessionInfo().getSelectedinstituteid().toString()});
            Map m = new HashMap();
            for (Object[] obj : userRoleRightsList) {
                m.put(obj[0], obj[0]);
            }
            for (Object[] obj : userRightsList) {
                if (!m.containsKey(obj[0])) {
                    userRoleRightsList.add(obj);
                }
            }
            for (int i = 0; i < userRoleRightsList.size(); i++) {
                Object[] object = (Object[]) userRoleRightsList.get(i);
                if (object[4] == null) {
//                    System.out.println(" i is " + i + " " + object[1].toString());
                    parentIdList.add(object[1].toString());

                }
            }

            Map map = new LinkedHashMap();
            for (int i = 0; i < parentIdList.size(); i++) {
                List list = new ArrayList();
                for (int j = 0; j < userRoleRightsList.size(); j++) {
                    Object[] object = (Object[]) userRoleRightsList.get(j);
                    if (object[4] != null && !object[1].toString().equals(parentIdList.get(i).toString()) && object[4].toString().equals(parentIdList.get(i).toString())) {
                        list.add(object);
                    }

                }
                //  System.out.println("final" + finalList);
                map.put(parentIdList.get(i).toString(), list);
                //   model.addAttribute("rightsid" ,list);
            }

            String value = daoFactory.getRegistrationParametersDAO().getParametersValue(instCodeId[0], "ADDBTN1.1");
            if (value == null && value.equals("")) {
                value = "N";
            }
            Random r = new Random();
            String alphabet = "123ABCDEFGHIJKLMNOPQRSTUVWXYS456NNYYNNYY";
            StringBuilder prefix = new StringBuilder();
            StringBuilder sufix = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                prefix.append(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            for (int i = 0; i < 3; i++) {
                sufix.append(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            model.addAttribute("menu", parentIdList);
            model.addAttribute("rightsid", map);
            empPhoto = (EmployeePhoto) daoFactory.getEmployeePhotoDAO().getPhoto(jirpsession.getJsessionInfo().getMemberid());
            model.addAttribute("userName", jirpsession.getJsessionInfo().getUsername());
            if (empPhoto != null) {
                Blob b = empPhoto.getPhoto();
                if (b != null) {
                    byte[] bytes = b.getBytes(1, (int) b.length());
                    String photo = Base64.encode(bytes);
                    model.addAttribute("userPhoto", photo);
                }
            }
            model.addAttribute("cc", prefix + "" + value + "" + instCodeId[1] + "" + sufix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Sct_IrpUserType> getAllIRPUserTypeOrderby() {
        List sct_IrpUserTypeList = null;
        try {
            sct_IrpUserTypeList = null;//(List) daoFactory.getDbFilterHandlerDAO().filterRecords(JIRPDBUtil.getAllSct_IrpUserTypes(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sct_IrpUserTypeList;
    }

    public boolean isValidUser(Sct_IrpMlp sct_IrpMlp) throws EncryptionException, Exception {
        OLTEncryption oLTEncryption = new OLTEncryption();
        boolean ret = false;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            if (sct_IrpMlp.getData8() != null && sct_IrpMlp.getData8().equals(oLTEncryption.encode("N"))) { //check for always valid
                if (sct_IrpMlp.getData9() != null && sct_IrpMlp.getData10() != null) {
                    Date formDate = null;
                    Date toDate = null;
                    if (oLTEncryption.decode(sct_IrpMlp.getData9()).length() == 8) {
                        int fromyear = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData9()).substring(0, 4));
                        int frommonth = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData9()).substring(4, 6));
                        int fromdate = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData9()).substring(6, 8));
                        formDate = new Date();
                        formDate.setDate(fromdate);
                        formDate.setMonth(frommonth - 1);
                        formDate.setYear(fromyear - 1900);
                    }
                    if (oLTEncryption.decode(sct_IrpMlp.getData10()).length() == 8) {
                        int toyear = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData10()).substring(0, 4));
                        int tomonth = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData10()).substring(4, 6));
                        int todate = Integer.parseInt(oLTEncryption.decode(sct_IrpMlp.getData10()).substring(6, 8));
                        toDate = new Date();
                        toDate.setDate(todate);
                        toDate.setMonth(tomonth - 1);
                        toDate.setYear(toyear - 1900);
                    }
                    if (formDate != null && toDate != null) {
                        if (df.parse(df.format(new Date())).compareTo(df.parse(df.format(formDate))) > 0 && df.parse(df.format(toDate)).compareTo(df.parse(df.format(new Date()))) != -1) {
                            ret = false;
                        } else {
                            ret = true;
                        }
                    } else {
                        ret = false;
                    }
                } else {
                    ret = false;
                }
            } else {
                ret = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean isValidPassword(Sct_IrpMlp sct_IrpMlp, String newkey, String password, String usertype) throws EncryptionException, Exception {
//        newkey = sct_SecurityInfo.getNewkey();
        OLTEncryption oLTEncryptionWithKey = new OLTEncryption(newkey);
        password = oLTEncryptionWithKey.encode(password);
        return daoFactory.getSct_IrpMlpDAO().isValidPassword(sct_IrpMlp.getData3(), sct_IrpMlp.getData4(), password, usertype);
    }

//    public static FilterInfoData getOtherStaffMasterData(int firstResult, int maxResults, String searchTerm) {
//        List pknames = new ArrayList<String>();
//        pknames.add("osid");
//        List<FilterInfo> list = new ArrayList<FilterInfo>();
//        FilterInfoData filterInfoData = new FilterInfoData(new OtherStaffMaster(), list, null, firstResult, maxResults, pknames);
//        return filterInfoData;
//    }
    public List<Sct_KioskMarqueeText> getMessageBoxData(String qryname) {

        List<Sct_KioskMarqueeText> retList = new ArrayList<Sct_KioskMarqueeText>();
        try {
            List<Sct_KioskMarqueeText> list = (List<Sct_KioskMarqueeText>) daoFactory.getUtilDAO().findByExample(qryname, new String[]{}, new Sct_KioskMarqueeText(), false);
            for (int i = 0; i < list.size(); i++) {
                retList.add(new Sct_KioskMarqueeText(list.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public String checkvalidationsuccess(Sct_IrpMlp sct_IrpMlp, String usertype, String unsuccessreason, String pwd) throws EncryptionException, Exception {

        OLTEncryption encryption = new OLTEncryption();
        String retstr = "";
        BusinessService bservice = new BusinessService(context, daoFactory);
        try {
            String slno = daoFactory.getLoginLogDAO().getSlno(encryption.decode(sct_IrpMlp.getData2()), new Date());
            if (slno != null) {
                String maxnoOfhitAday;
                String NoForGiveMaxAttemptAlert = bservice.getPropertyValue("NoForGiveMaxAttemptAlert", "numbercode.properties");
                if (usertype.equals("S")) {
                    maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayStudent", "numbercode.properties");
                } else {
                    maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayOther", "numbercode.properties");
                }
                if ((Integer.parseInt(maxnoOfhitAday) <= Integer.parseInt(slno))) {
                    sct_IrpMlp.setData11(encryption.encode("Y"));
                    daoFactory.getSct_IrpMlpDAO().update(sct_IrpMlp);
                    retstr = "Your Login Account Has Been Locked!";
                }

            }
        } catch (Exception e) {
            bservice.rollback();
            e.printStackTrace();
        } finally {
            bservice.closeSession();
        }
        return retstr;
    }

    public String getmacAddress() {
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress thisIp = InetAddress.getByName(getIpAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(thisIp);
//            byte[] mac = network.getHardwareAddress();
//            for (int i = 0; i < mac.length; i++) {
//                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getIpAddress() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = sra.getRequest();
//             System.out.println("------------------->"+req.getHeader("x-forwarded-for"));
        return req.getRemoteAddr();
    }

    public String LoginChangePassword(String userid, String newpassword) throws EncryptionException {
        System.err.println("id:" + userid + "----" + newpassword);
        Map map = null;
        try {
            OLTEncryption encryption = new OLTEncryption();
//            Sct_SecurityInfo sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey(encryption.decode(userid));
//            if (sct_SecurityInfo != null) {
//                OLTEncryption oLTEncryption = new OLTEncryption(sct_SecurityInfo.getNewkey().toString());
            OLTEncryption oLTEncryption = new OLTEncryption();
            map = new HashMap();
            map.put("userid", "'" + userid + "'");
            map.put("newpassword", "'" + oLTEncryption.encode(newpassword) + "'");
            daoFactory.getUtilDAO().executeDDLUpdate("updateUserPassword", map);
//            } else {
//                return "Key";
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return "Success";
    }

    public List<String> LoginChangePassword1(String memebertype, String userid, String newpassword) throws EncryptionException, Exception {
        System.err.println("hello world" + userid + "NEW:" + newpassword);
        List list = new ArrayList();
        OLTEncryption oLTEncryption = new OLTEncryption();

        Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) daoFactory.getSct_IrpMlpDAO().findByPrimaryKey(oLTEncryption.encode(userid));
//        Sct_SecurityInfo sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey(userid);

//        OLTEncryption oLTEncryptionkey = new OLTEncryption(sct_SecurityInfo.getNewkey());
        OLTEncryption oLTEncryptionkey = new OLTEncryption();
        if (userid != null) {

            /*    sct_IrpMlp.setData6(oLTEncryptionkey.encode(newpassword));

            sct_IrpMlp.setData15(oLTEncryption.encode("N"));
            daoFactory.getSct_IrpMlpDAO().update(sct_IrpMlp);
            list.add("success");
            list.add("updated");
             */
            if (memebertype.equals("P")) {
                sct_IrpMlp.setData7(oLTEncryptionkey.encode(newpassword));
                sct_IrpMlp.setData15(oLTEncryption.encode("N"));
                daoFactory.getSct_IrpMlpDAO().update(sct_IrpMlp);
                list.add("success");
                list.add("updated");
            } else {

                sct_IrpMlp.setData6(oLTEncryptionkey.encode(newpassword));
                sct_IrpMlp.setData15(oLTEncryption.encode("N"));
                daoFactory.getSct_IrpMlpDAO().update(sct_IrpMlp);
                list.add("success");
                list.add("updated");

            }
        } else {
            list.add("error");
            list.add("invalid password");
        }
        return list;
    }

    boolean checkKeyValidity() {
        boolean mFlag = false;
        BusinessService bservice = new BusinessService(context, daoFactory);
        try {
            String cleintID = bservice.getPropertyValue("clientid", "campuslynx.properties");
            String keyvalidaFrom = bservice.getPropertyValue("svf", "campuslynx.properties");
            String keyvalidaUpto = bservice.getPropertyValue("svt", "campuslynx.properties");
            if (Double.parseDouble(JIRPDateUtil.convertDateFormat(new Date(), "yyyyMMdd")) <= Double.parseDouble(keyvalidaUpto)) {
                mFlag = true;
            } else {
                mFlag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bservice.closeSession();
        }
        return mFlag;
    }

    private int getNoOfDays(String ptodate) {
        int noofdays = 0;
        try {
            OLTEncryption oLTEncryption = new OLTEncryption();
            Date toDate = null;
            if (oLTEncryption.decode(ptodate).length() == 8) {
                int toyear = Integer.parseInt(oLTEncryption.decode(ptodate).substring(0, 4));
                int tomonth = Integer.parseInt(oLTEncryption.decode(ptodate).substring(4, 6));
                int todate = Integer.parseInt(oLTEncryption.decode(ptodate).substring(6, 8));
                toDate = new Date();
                toDate.setDate(todate);
                toDate.setMonth(tomonth - 1);
                toDate.setYear(toyear - 1900);
            }
            if (toDate != null) {
                if (new Date().compareTo(toDate) < 0) {
                    noofdays = (int) ((toDate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noofdays;
    }

    /**
     * Description: Function has been used to get current user messages and
     * portal messages.
     *
     * @param userid
     * @return
     */
    public ModelMap getUserMessage(String userid) {
        ModelMap finalobj = new ModelMap();
        List emaillist = new ArrayList();
        List portallist = new ArrayList();
        try {
            OLTEncryption oLTEncryption = new OLTEncryption();
            userid = oLTEncryption.encode(userid);
            String memberinfo = daoFactory.getSct_IrpMlpDAO().getMemberID(userid);
            String[] memberstr = memberinfo.split(":");
            String memberid = oLTEncryption.decode(memberstr[0].toString());
            String membertype = oLTEncryption.decode(memberstr[1].toString());
            List emailPortallist = (List) daoFactory.getNotify_NotificationAlertMasterDAO().getSMSEmailPortalData(memberid, membertype);
            if (emailPortallist != null && emailPortallist.size() > 0) {
                for (int i = 0; i < emailPortallist.size(); i++) {
                    Map m = (Map) emailPortallist.get(i);
                    Map obj = new ModelMap();
                    obj.put("notificationid", m.get("notificationid"));
                    obj.put("slno", m.get("slno"));
                    obj.put("notificationfrom", "Message Sent By: " + m.get("notificationfrom"));
                    obj.put("from", m.get("notificationfrom"));
                    obj.put("notificationvia", m.get("notificationvia"));
                    obj.put("notificationdatetime", convertDateFormat((Date) m.get("notificationdatetime")));
                    obj.put("notoficationsubject", m.get("notoficationsubject"));
                    obj.put("notificationtext", m.get("notificationtext"));
                    obj.put("readflag", m.get("readflag"));
                    obj.put("checked", false);
                    if ("N".equals(m.get("readflag")) || "".equals(m.get("readflag"))) {
                        obj.put("color", "blue");
                        obj.put("font", "bold");
                        obj.put("readunread", "It is unread message");
                    } else {
                        obj.put("color", "black");
                        obj.put("readunread", "It is read message");
                    }
                    obj.put("deleteflag", m.get("deleteflag"));
                    if ("E".equals(m.get("notificationvia"))) {
                        emaillist.add(obj);
                    } else if ("P".equals(m.get("notificationvia"))) {
                        portallist.add(obj);
                    }
                }
            }
            finalobj.put("emaillist", emaillist);
            finalobj.put("portallist", portallist);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalobj;
    }

    /**
     * Description: Functio to convert Date format.
     *
     * @param indate
     * @return
     * @throws flex.messaging.services.messaging.selector.ParseException
     */
    public static String convertDateFormat(Date indate) throws ParseException {
        if (indate == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            return format.format(indate);
        }
    }

    /**
     * Description: Function has been used to update information.
     *
     * @param notificationid
     */
    public void updateInfo(String notificationid, int slno) {
        Notify_NotificationToId id = new Notify_NotificationToId();
        id.setNotificationid(notificationid);
        id.setSlno(slno);
        Notify_NotificationTo dto = (Notify_NotificationTo) daoFactory.getNotify_NotificationToDAO().findByPrimaryKey(id);
        dto.setReadflag("Y");
        daoFactory.getNotify_NotificationToDAO().update(dto);

    }

    /**
     * Description: Function has been used to update information.
     *
     * @param notificationid
     */
    public List delSelectedMsg(List dellist) {
        Notify_NotificationToId id = new Notify_NotificationToId();
        ArrayList err = null;
        int j = 0;
        try {
            for (int i = 0; i < dellist.size(); i++) {
                j++;
                id.setNotificationid((((ModelMap) dellist.get(i)).get("notificationid").toString()));
                id.setSlno(Integer.parseInt((((ModelMap) dellist.get(i)).get("slno").toString())));
                Notify_NotificationTo dto = (Notify_NotificationTo) daoFactory.getNotify_NotificationToDAO().findByPrimaryKey(id);
                dto.setDeleteflag("Y");
                daoFactory.getNotify_NotificationToDAO().update(dto);
            }
            err = new ArrayList();
            err.add("Success");
            err.add(j);
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList();
            err.add("Unsuccess");
        }
        return err;
    }


    /*--new--*/
    public List getMemberTypeDetail() {
        List MemberTypeList = null;
        List retList = new ArrayList();
        MemberTypeList = null;// (List) daoFactory.getDbFilterHandlerDAO().filterRecords(JIRPDBUtil.getAllSct_IrpUserTypes(), false);
        for (int i = 0; i < MemberTypeList.size(); i++) {
            Sct_IrpUserType object = (Sct_IrpUserType) MemberTypeList.get(i);
            ModelMap aso = new ModelMap();
            aso.put("userdescription", object.getUserdescription());
            aso.put("usertype", object.getUsertype());
            retList.add(aso);

        }
        return retList;
    }

    /*--new--*/
//    public List<Object> validateMailId(final String username, final String password) {
//        List<Object> retList = new ArrayList<Object>();
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.auth", "true");
//        props.setProperty("mail.smtp.port", "587");
//        props.put("mail.smtp.starttls.enable", "true");
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
//            message.setSubject("Login in ERP");
//            message.setText("You are login last at " + new Date());
//            Transport.send(message);
//            OLTEncryption oLTEncryption = new OLTEncryption();
//            List<Object[]> data = (List<Object[]>) daoFactory.getSct_IrpMlpDAO().getMemberDetail(oLTEncryption.encode(username));
//            if (data != null) {
//                Sct_SecurityInfo sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey(oLTEncryption.decode(data.get(0)[3].toString()));
//                OLTEncryption oLTEncryptionkey = new OLTEncryption(sct_SecurityInfo.getNewkey());
//
//                return checkUserid_Password(username, oLTEncryptionkey.decode(data.get(0)[0].toString()), oLTEncryption.decode(data.get(0)[1].toString()), oLTEncryption.decode(data.get(0)[2].toString()));
//            } else {
//                retList.add("error");
//                retList.add("Your account has been Deactive/Not valid");
//                return retList;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            retList.add("error");
//            retList.add("Invalid username/paswword");
//        }
//        return retList;
//    }
    public List<Object> validateMailId(final String loginid, final String password) {

        List retList = new ArrayList();
        String domainName;
        String serverName;
        String username = "";
        //domainName = "jil";
        // serverName = "172.16.4.50";
        BusinessService bservice = new BusinessService(context, daoFactory);
        boolean mStatus = false;
        Hashtable props = new Hashtable();
        try {
            domainName = bservice.getPropertyValue("domainName", "campuslynx.properties");
            serverName = bservice.getPropertyValue("serverName", "campuslynx.properties");
            OLTEncryption oLTEncryption = new OLTEncryption();

            List<Object[]> data = (List<Object[]>) daoFactory.getSct_IrpMlpDAO().getMemberDetail(loginid);
            if (data != null) {
                username = data.get(0)[4].toString();
                System.out.println("Authenticating " + username + "@" + domainName + " through " + serverName + "." + domainName);
                String principalName = username + "@" + domainName;
                props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                props.put(Context.SECURITY_AUTHENTICATION, "simple");
                props.put(Context.SECURITY_PRINCIPAL, principalName);
                props.put(Context.SECURITY_CREDENTIALS, password);
                DirContext context;
                context = LdapCtxFactory.getLdapCtxInstance("ldap://" + serverName + '/', props);//+ "." + domainName
                System.out.println("Authentication succeeded!");
                mStatus = true;
                context.close();
                return checkADUserid_Password(oLTEncryption.decode(data.get(0)[5].toString()), oLTEncryption.decode(data.get(0)[1].toString()), oLTEncryption.decode(data.get(0)[2].toString()));
            } else {
                retList.add("error");
                retList.add("Your account has been Deactive/Not valid");
                return retList;
            }
        } catch (AuthenticationException a) {
            System.out.println("Authentication failed: " + a);
            mStatus = false;
            retList.add("error");
            retList.add("Authentication failed");
            return retList;
        } catch (NamingException e) {
            System.out.println("Failed to bind to LDAP / get account information: " + e);
            mStatus = false;
            retList.add("error");
            retList.add("Failed to bind to LDAP / get account information: ");
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List<Object> checkADUserid_Password(String username, String usertype, String instituteid) {
        List<Object> retList = new ArrayList<Object>();
        String lastlogin = "";
        String message = "";
        String encodedusertype = "";
//        Sct_SecurityInfo sct_SecurityInfo = null;
        BusinessService bservice = new BusinessService(context, daoFactory);
        try {
            OLTEncryption oLTEncryption = new OLTEncryption();
            if (username != null && !username.equals("")) {
                username = oLTEncryption.encode(username);
//                System.out.println(oLTEncryption.decode(username));
            }
            if (usertype != null && !usertype.equals("")) {
                encodedusertype = oLTEncryption.encode(usertype);
            }
            if (instituteid != null && !instituteid.equals("")) {
                instituteid = oLTEncryption.encode(instituteid);
            }
            List<Sct_IrpMlp> sct_IrpMlplist = daoFactory.getSct_IrpMlpDAO().loginUserData(username, encodedusertype, instituteid, usertype);
            if (sct_IrpMlplist != null && sct_IrpMlplist.size() > 0) {
                Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) sct_IrpMlplist.get(0);
                if (sct_IrpMlp.getData1() != null && sct_IrpMlp.getData1().equals("") && sct_IrpMlp.getData2() != null && sct_IrpMlp.getData2().equals("") && sct_IrpMlp.getData3() != null && sct_IrpMlp.getData3().equals("") && sct_IrpMlp.getData4() != null && sct_IrpMlp.getData4().equals("") && sct_IrpMlp.getData5() != null && sct_IrpMlp.getData5().equals("")) {
                    retList.add("Error!");
                    retList.add("Data not found");
                    return retList;
                }
                if (sct_IrpMlp.getData13() != null && sct_IrpMlp.getData13().equals(oLTEncryption.encode("Y"))) {// check deactive and check for Always valid or valid in given time period
                    retList.add("error");
                    retList.add("Your account has been Deactive/Not valid");
                    return retList;
                }
                if (isValidUser(sct_IrpMlp)) {
                    retList.add("error");
                    retList.add("Your account has been expired.");
                    return retList;
                }
                if (sct_IrpMlp.getData11() != null && sct_IrpMlp.getData11().equals(oLTEncryption.encode("Y"))) { // check for accoucnt lock
                    retList.add("error");
//                    System.out.println(sct_IrpMlp.getData4());
                    retList.add("Your account has been Locked");
                    return retList;
                }
                if (sct_IrpMlp.getData16() != null && sct_IrpMlp.getData16().equals(oLTEncryption.encode("Y"))) { // check for username and password expired
                    retList.add("error");
                    retList.add("Your account has been expired");
                    return retList;
                }
                //System.err.println("jass" + oLTEncryption.decode(sct_IrpMlp.getData2()));
                // sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey(oLTEncryption.decode(sct_IrpMlp.getData2()));
                //sct_SecurityInfo = (Sct_SecurityInfo) daoFactory.getSct_SecurityInfoDAO().findByPrimaryKey("IITRUSER1007A0000001");
                // validpassword = isValidPassword(sct_IrpMlp, newkey, password, usertype, sct_SecurityInfo);
                // System.err.println("validpassword" + validpassword);
                // (validpassword) {
                //----------sumit
                String loginrestrictedtime = bservice.getPropertyValue("SecurityLoginRestrictedTime", "numbercode.properties");
                List<Object[]> statuslist = daoFactory.getSct_IrpUsersDAO().getLoginStatus(oLTEncryption.decode(sct_IrpMlp.getData2()));
                Date d = new Date();
                if (statuslist.get(0)[1] != null) {
                    d = (Date) statuslist.get(0)[1];
                }
                Date cdate = new Date();
                int month = cdate.getMonth() + 1;
                Integer diff = month - d.getMonth();
                String status = "";
                boolean notrestricted = true;
                long lastvisiteddate = 0;
                if (statuslist != null && statuslist.size() > 0) {
                    status = statuslist.get(0)[0] == null ? "" : statuslist.get(0)[0].toString();
                    lastvisiteddate = Long.parseLong(diff.toString());
                }
                if (lastvisiteddate < Integer.parseInt(loginrestrictedtime)) {
                    if (usertype.equals("A")) {
                        notrestricted = true;
                    } else {
                        notrestricted = false;
                    }
                }
                // By Ashok for Testing
                if (status.equals("I")) {
                    status = "O";
                }

                if (status.equals("") || status.equals("O") || notrestricted) {
                    if (retList.size() == 0) {

                        String noOfhitThisday = daoFactory.getLoginLogDAO().getSlno(oLTEncryption.decode(sct_IrpMlp.getData2()), new Date());
                        String maxnoOfhitAday;
                        String NoForGiveMaxAttemptAlert = bservice.getPropertyValue("NoForGiveMaxAttemptAlert", "numbercode.properties");
                        if (usertype.equals("S")) {
                            maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayStudent", "numbercode.properties");
                        } else {
                            maxnoOfhitAday = bservice.getPropertyValue("MaxNoOfAttemptADayOther", "numbercode.properties");
                        }
                        if (Integer.parseInt(noOfhitThisday) <= Integer.parseInt(maxnoOfhitAday)) {
                            if (sct_IrpMlp.getData15() != null && sct_IrpMlp.getData15().equals(oLTEncryption.encode("Y"))) {// check password expire
                                retList.add("success");
                                retList.add("Your Password has been Expired please provide new password");
                            } else if (Integer.parseInt(maxnoOfhitAday) - Integer.parseInt(noOfhitThisday) <= Integer.parseInt(NoForGiveMaxAttemptAlert)) {
                                if (Integer.parseInt(maxnoOfhitAday) == Integer.parseInt(noOfhitThisday)) {
                                    // message = checkvalidationsuccess(sct_IrpMlp, usertype, "", password);
                                    retList.add("error");
                                    retList.add(message);
                                    return retList;
                                } else {
                                    retList.add("success");
                                    retList.add("Only " + (Integer.parseInt(maxnoOfhitAday) - Integer.parseInt(noOfhitThisday)) + " Attempt Is Allow For Today");
                                }
                            } else {
                                retList.add("success");
                                retList.add("Done");
                            }
                        } else {
                            message = "";
                            retList.add("error");
                            retList.add("Max Trial Exceed For Today" + message);
                            return retList;
                        }

                        //retList.add(getBaseModules());
                        return retList;
                    }
                } else {
                    message = "";
                    retList.add("error");
                    retList.add("User Allready Login!Please Login After.........." + message);
                    return retList;
                }
            } else {
                retList.add("error");
                retList.add("The username or password is incorrect.");
                return retList;
            }
        } catch (Exception e) {
            retList.add("error");
            retList.add("Unable to Login ");
            e.printStackTrace();
        } finally {
            bservice.closeSession();
        }
        return retList;
    }

    //////////////////////////////////////////////Method For For Getting Token From SSO OAuth Server/////////////////////////////////////////////
    private String getUserFromServer(String access_token, String token_type, String state, String resourceServer) throws IOException, UnirestException, JSONException {
        //HttpResponse<JsonNode> jsonResponse = Unirest.get(resourceServer+"/rest/me").header("accept", "application/json").header("Authorization","Bearer "+access_token).asJson();

        String output = null;
        try {

            URL url = new URL(resourceServer + "/rest/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
//                System.out.println(output);
                break;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        String value = output;
        value = value.substring(1, value.length() - 1);           //remove curly brackets
//        String keyValuePairs = value;            //split the string to creat key-value pairs
//        Map<String, String> map = new HashMap<String, String>();
//
//        String[] entry = keyValuePairs.split(":");                   //split the pairs to get key and value
//        map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
//        return entry[1].trim();

        String keyValuePairs = value;
        String userName = "";
        String[] entry = keyValuePairs.split(",");                   //split the pairs to get key and value
        for (int i = 0; i < entry.length; i++) {
            if (entry[i].contains("username")) {
                userName = entry[i].trim();
            }
        }
        return userName;
    }

    /**
     * Author : mohit kumar this method is used for fetching the record of
     * institute master and company master
     *
     * @param model
     */
    @Override
    public void getInstCompData(Model model) {
        try {
            List instData = new ArrayList();
            List compData = new ArrayList();
            String userId = jirpsession.getJsessionInfo().getUserid().toString();
            instData = daoFactory.getSct_IrpMlpDAO().fetchInstituteData(userId);
            model.addAttribute("instData", instData);
            boolean flag = false;
            if (instData.size() >= 1 && compData.size() >= 1) {
                flag = true;
            }
            model.addAttribute("flag", flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
