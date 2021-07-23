/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author ankit.kumar
 */
public interface CommonParameterIDAO extends IDAO {

    public List checkModuleCodeExist(final String modulecode);

    public List geParametersData(final String instituteid);

    public List geModuleData(final String instituteid);

    public List geParametersDataModulewise(final String instituteid,final String moduleid);

    public String getParameterValue(final String instituteid, final String moduleid, final String parameterid);

    public String getValidFileName(String filename);

}
