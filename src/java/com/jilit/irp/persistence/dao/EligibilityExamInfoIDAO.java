/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;
import com.jilit.irp.persistence.dto.EligibilityExamInfo;
import com.jilit.irp.persistence.dto.EligibilityExamInfoId;
import com.jilit.irp.persistence.dto.EligibilityExamInfoDetail;
import com.jilit.irp.persistence.dto.EligibilityExamInfoDetailId;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author deepak
 */
public interface EligibilityExamInfoIDAO extends IDAO {
    
    public Collection<?> findAllInstituteWise(String instituteid);

    public List<Object[]> getGridData(final String instituteid);

    public List doValidate(final EligibilityExamInfo master, final String mode);

    public int checkIfChildExist(final EligibilityExamInfoId id);

    public Object findByPrimaryKeyDetail(Serializable id);

    public List<Object[]> getAllEligibilityExamInfoDetailgridData(final String eeexamid);

    public List doValidate1(final EligibilityExamInfoDetail master, final String mode);

    public int checkIfChildExistdetail(final EligibilityExamInfoDetailId id);

}
