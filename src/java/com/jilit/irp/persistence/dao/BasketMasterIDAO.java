/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.BasketMaster;
import java.util.List;

//* @author V.Kumar/Mohit1.kumar
public interface BasketMasterIDAO extends IDAO {

    public int checkIfChildExist(final String instituteid, final String basketid);

    public List getBasketCodeForCopy(String instituteid, String programid, String sectionid, byte stynumber,String reqfor);

    public List getBasketCode(String instituteid, String programid, String sectionid, byte stynumber);

    public List getBasketCodeExistTeachingScheme(String instituteid, String programid, String sectionid, byte stynumber);

    public List getBasketCodeData(String instituteid, String programid, String sectionid, byte stynumber, String subType);

    public List getBasketCode(String instituteid, String programid, String sectionid);

    public List getBasketCode_edit(String instituteid, String basketid);

    public List checkTeachingScheme(String instituteid, String basketid);

    public List getAllBasketForReport(String instId, String regId, String progId, String acdyr, String secId);

    public List getBasketCode(String instituteid, String subjecttypeid);

    public Short getSeqId(String instituteid, String programid, String sectionid, Byte stynumber, String Subjecttypeid);

    public List<String> doValidate(final BasketMaster basketmaster, final String mode);

}
