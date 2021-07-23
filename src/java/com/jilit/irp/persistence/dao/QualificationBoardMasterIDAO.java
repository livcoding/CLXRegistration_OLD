/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;


import com.jilit.irp.persistence.dto.StudentQlyBoardMaster;
import java.util.List;
/**
 *
 * @author narendars.shekhawat
 */
public interface QualificationBoardMasterIDAO extends IDAO {

    public int checkIfChildExist(final String qualificationid);
    
    public List<String> doValidate(final StudentQlyBoardMaster master, final String mode);
    
//     public List getQualificationData(String data) ;
//     public List<String> doValidateBoardUniversity(final StudentQlyBoardMaster master);
}
