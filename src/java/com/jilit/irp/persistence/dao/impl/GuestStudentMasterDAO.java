/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.GuestStudentMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.GuestStudentMaster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author priya.sharma
 */
public class GuestStudentMasterDAO extends HibernateDAO implements GuestStudentMasterIDAO {

    private static final Log log = LogFactory.getLog(GuestStudentMasterDAO.class);

    @Override
    public Collection<?> findAll() {
        return this.find("from GuestStudentMaster as gsm ");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(GuestStudentMaster.class, id);
    }

    public List studentListData(String instituteid) {
        List list = new ArrayList();
        StringBuilder str = new StringBuilder();
        str.append("select gs.gueststudentid,gs.enrollmentno,gs.name,gs.programcode,gs.branchcode,"
                + " gs.sectioncode,");
        str.append(" gs.subsectioncode,gs.dateofbirth,gs.gender,");
        str.append("(select coalesce(ga.smobileno,'-')as mn from GuestStudentAddress ga where  ga.gueststudentid=gs.gueststudentid) as sno,(select "
                + " coalesce(ga.pmobileno,'-')as pn from GuestStudentAddress ga where  ga.gueststudentid=gs.gueststudentid) as pno,(select "
                + " coalesce(ga.semailid,'-')as se from GuestStudentAddress ga where  ga.gueststudentid=gs.gueststudentid) as seid,(select "
                + " coalesce(ga.pemailid,'-')as pe from GuestStudentAddress ga where  ga.gueststudentid=gs.gueststudentid) as pid");
        str.append(" from GuestStudentMaster gs");
        str.append(" where gs.frominstitutename=:instId");
        try {
            list = getHibernateSession().createQuery(str.toString()).setParameter("instId", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List getGuestInstituteName() {
        List list = new ArrayList();
        StringBuilder str = new StringBuilder();
        str.append("select distinct gs.frominstitutename from GuestStudentMaster gs");
        try {
            list = getHibernateSession().createQuery(str.toString()).list();
        } catch (Exception e) {
        }
        return list;
    }
}
