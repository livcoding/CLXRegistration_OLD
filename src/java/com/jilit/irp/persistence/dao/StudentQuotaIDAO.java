/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentQuota;
import com.jilit.irp.persistence.dto.StudentQuotaId;

/**
 *
 * @author ashok.singh
 */
import java.util.Collection;
import java.util.List;

public interface StudentQuotaIDAO extends IDAO {

    public List getAllQuota(String instituteid);
//
//    public List checkIfQuotaExist(String instituteid, String quota);
//
    public List doValidate(final StudentQuota dto, final String mode);

    public int checkIfChildExist(final StudentQuotaId id);
//
//    public List getAllQuotaData(String instituteid);
//
//    public List getStudentQuotaReportData(String instituteid, String quotaid, String ordertype, String sortedtype);
//
//    public String getQuotaId(final String instituteid, final String quotacode);
//    public List getAllQuota1(String instituteid);
//    public List checkIfQuotaCodeExist(String instituteid, String quotacode);
}
