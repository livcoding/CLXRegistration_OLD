/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.Ex_PatternMaster;
import com.jilit.irp.persistence.dto.Ex_PatternMasterId;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Malkeet Singh
 */
public interface Ex_PatternMasterIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

    public List doValidate(final Ex_PatternMaster master, String mode);

    public List getSeqId(String instituteid);
    /*
    public int checkIfChildExist(final Ex_PatternMasterId patternid);

    public List getAllPatternMaster(String instituteid);

    public List checkIfExamPatternExists(String instituteid, String pattrencode);

    public List getPatternCode(String instituteid, String patternid);

    public List getExamPattern_Attendance(final String instituteid, final String registrationid);
     */
}
