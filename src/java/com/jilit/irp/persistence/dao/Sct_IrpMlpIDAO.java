/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dto.Sct_IrpMlp;
import com.jilit.irp.persistence.dto.Sct_IrpUsers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author sunny.singhal
 */
public interface Sct_IrpMlpIDAO extends IDAO {

    public int checkIfChildExist(final String data2);

    public String findUserid(String employeeid);

    public List<String> doValidate(final Sct_IrpMlp sct_IrpMlp, final String mode);

    public List<Sct_IrpMlp> loginUserData(final String username, final String encodedusertype, final String instituteid, final String usertype);

    public boolean isValidPassword(final String encodedusertype, final String loginid, final String password, final String usertype);

    public void updateAfterLogin(final Sct_IrpMlp sct_IrpMlp, final Sct_IrpUsers sct_IrpUsers, final List<Object> retList);

    public String insertData(final List<Sct_IrpUsers> irpUserses, final List<Sct_IrpMlp> irpMlps, final BusinessService businessService);

    public String insertData(final List<Sct_IrpUsers> irpUserses, final List<Sct_IrpMlp> irpMlps);

    public ArrayList getDataForResetPassword(final String instid, final String usertype, final String memberid);

    //public ArrayList updateDataForAllResetPassword(final String PassWord ,final String UserType,final String Id);
    // public ArrayList updateDataForParentResetPassword(final String PassWord ,final String UserType,final String Id);
    public Long updateDataForAllResetPassword(final String PassWord, final String UserType, final String Id);

    public Long updateDataForParentResetPassword(final String PassWord, final String UserType, final String Id);

    public String getSaveInsert(final String employeeid, final String usertype, final String Str, final String Str1);

    public String makePasswordExpired(String employeeid, String usertype);

    public String expireForGroupType(String usertype);

    public String expireAllTypes();

    public String findLoginID(String userid);

    public String getMemberID(String userid);

    public List getMemberDetail(String email);

    public List checkExistingStudentLogin(String instituteid, String studentid, String usertype);

    public List getUserId(String memberid);

    public List getMemberDetailAD(String loginid);

    public List getDataFromSct_IrpMlp();

    public List getLoginDataList(String UserID);

    public Sct_IrpMlp getLoginDataObject(String UserID);

    public ArrayList getDataForResetLoginId(final String instid, final String usertype, final String memberid);

    public List getLoginEmpRecord(String memberid);

    public List<Sct_IrpMlp> getInstiteWiseStaff(String instituteid, String mexcludeMembertype);

    public List getExistingLoginId(String membercode, String member_type);

    public List fetchInstituteData(String userId);

//    public List fetchCompData(String userId);
}
