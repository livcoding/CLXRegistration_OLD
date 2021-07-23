package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ankit.kumar
 */
public interface PhdSubjectFinalizationIService {

    public List getPendingForApprovalData(HttpServletRequest request);

    public List getApprovedData(HttpServletRequest request);

    public List getCanclledData(HttpServletRequest request);

    public List getSubjectReportData(HttpServletRequest request);

    public List savePendingForApprove_Approve(HttpServletRequest request);

    public List saveCancelledData(HttpServletRequest request);
}
