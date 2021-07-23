/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author Malkeet Singh
 */
public interface RegistrationParametersIDAO extends IDAO {

    public List getGridData(String instituteid, List parameterslist, String status);

    public List getSeqId(String instituteid);

    public String getParametersValue(String instituteid, String parameterid);

    public String getFeeheadid(String instituteid, String feeheadCode);

    public List getParametersValue(List instituteid, String parameterid);

    public List getFeeheadids(List instituteid, List feeheadCode);

}
