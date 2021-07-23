package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankur.goyal
 */
public interface UpgradeDegradeSemesterIService {

    void getAcademicYearCombo(ModelMap model);

    List getAllBranchCode(HttpServletRequest request);

    List getSemesterForAcadProgramBranch(HttpServletRequest request);

    List getAllGridData(HttpServletRequest request);

    List getSaveGridata(HttpServletRequest request);

}
