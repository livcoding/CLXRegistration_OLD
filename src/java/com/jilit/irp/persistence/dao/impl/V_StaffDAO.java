/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.V_StaffIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.CoordinatorNameStudentWise;
import com.jilit.irp.persistence.dto.DepartmentWiseHod;
import com.jilit.irp.persistence.dto.StudentMaster;
import com.jilit.irp.persistence.dto.V_Staff;
import com.jilit.irp.util.JIRPSession;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 *
 * @author sunny.singhal
 */
public class V_StaffDAO extends HibernateDAO implements V_StaffIDAO {

    Session session = null;
    private static final Log log = LogFactory.getLog(V_StaffDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all V_Staff records via Hibernate from the database");
        return this.find("from V_Staff as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(V_Staff.class, id);
    }

    public List getEmp_Type(String instituteid, String emp_type) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select v_staff0_.companyid, v_staff0_.employeeid,v_staff0_.employeecode,v_staff0_.salutationid,v_staff0_.employeename, v_staff0_.departmentid,v_staff0_.departmentcode,v_staff0_.department, "
                + " v_staff0_.designationid,v_staff0_.designationcode,v_staff0_.designation,v_staff0_.stafftype,v_staff0_.shortname,v_staff0_.employeetypeid,v_staff0_.employeetype,v_staff0_.emailid,v_staff0_.mobileno, "
                + " v_staff0_.gender,v_staff0_.deactive from V_Staff v_staff0_  where (v_staff0_.employeetypeid in (select employeety1_.employeetypeid  from EmployeeType employeety1_ where  coalesce(v_staff0_.deactive, 'N')='N'");
        if (!emp_type.equalsIgnoreCase("All")) {
            sb.append(" and employeety1_.employeetype= :emp_type ");
        }else{
            sb.append(" and :emp_type = :emp_type ");
        }
        sb.append(" ) )  and v_staff0_.placeofpostingid in (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N') ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("emp_type", emp_type).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    @Override
    public List getStaff(String depId, String instituteid, String facultyFrom) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select v.employeeid, v.employeecode,v.employeename,v.stafftype,v.department,v.designation from V_Staff v where v.employeetype='TEC' and");
        if (facultyFrom.equals("OD")) {
            sb.append(" v.departmentid<>:depId and ");
        } else if (facultyFrom.equals("SD")) {
            sb.append(" v.departmentid =:depId and ");
        } else {
            sb.append(" :depId =:depId and ");
        }
        if (!facultyFrom.equals("OI")) {
            sb.append("exists (select 1 from DepartmentBranchTagging dt ");
        } else {
            sb.append("not exists (select 1 from DepartmentBranchTagging dt ");
        }
        sb.append(" where dt.id.departmentid = v.departmentid and dt.id.instituteid = :instituteid");
        if (!facultyFrom.equals("OI")) {
            sb.append(") and exists (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N'))  ");
        } else {
            sb.append(" and exists (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N')) )");
        }
        sb.append(" order by v.employeecode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("depId", depId).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List gettingAllDepartmentWiseEmployeeCode(String departmentid, String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select vst.employeeid,vst.employeecode,vst.employeename,vst.stafftype,vst.designation from V_Staff vst where vst.departmentid=:departmentid "
                + " and vst.placeofpostingid in (select im.placeofpostingid from InstituteMaster im where im.instituteid=:instituteid  and coalesce(im.deactive,'N')='N')) "
                + " and ( vst.deactive is null  or vst.deactive='N') "
                + " group by vst.employeeid, vst.employeecode, vst.employeename, vst.stafftype, vst.designation "
                + " order by vst.employeename asc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("departmentid", departmentid).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
}
