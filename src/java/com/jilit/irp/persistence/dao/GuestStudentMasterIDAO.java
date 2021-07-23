/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author priya.sharma
 */
public interface GuestStudentMasterIDAO extends IDAO {

    public List studentListData(String instituteid);
    
    public List getGuestInstituteName();
}
