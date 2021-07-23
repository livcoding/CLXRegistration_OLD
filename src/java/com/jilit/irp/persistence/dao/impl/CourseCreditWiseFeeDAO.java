/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CourseCreditWiseFeeIDAO;
import com.jilit.irp.persistence.dto.CourseCreditWiseFee;
import com.jilit.irp.persistence.dao.HibernateDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import com.jilit.irp.persistence.dto.ScholarshipCriteriaId;
import com.jilit.irp.persistence.dto.ScholarshipCriteria;
import com.jilit.irp.persistence.dao.ScholarshipCriteriaIDAO;

/**
 *
 * @author Malkeet Singh
 */
public class CourseCreditWiseFeeDAO extends HibernateDAO implements CourseCreditWiseFeeIDAO {

    private static final Log log = LogFactory.getLog(CourseCreditWiseFeeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all CourseCreditWiseFee records via Hibernate from the database");
        return this.find("from CourseCreditWiseFee as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CourseCreditWiseFee.class, id);
    }

    public List getGridData(String instituteid, String stytypeid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select ccwf.id.instituteid,ccwf.id.stytypeid,sty.stytype,sty.stytypedesc ,ccwf.feeamount,ccwf.creditfrom,ccwf.creditto,ccwf.id.slno "
                + " from CourseCreditWiseFee ccwf,StyType sty "
                + " where ccwf.id.instituteid=:instituteid "
                + " and ccwf.id.stytypeid=:stytypeid "
                + " and sty.id.instituteid= ccwf.id.instituteid "
                + " and sty.id.stytypeid=ccwf.id.stytypeid");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("stytypeid", stytypeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List creditSRno(String instituteid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select ccwf.id.slno from CourseCreditWiseFee ccwf where ccwf.id.instituteid=:instituteid order by ccwf.id.slno desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List validData(String instituteid, String stytypeid, double creditfrom, double creditto, short slno, String status) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select 1 from CourseCreditWiseFee ccwf "
                + " where  ccwf.id.instituteid = :instituteid "
                + " and ccwf.id.stytypeid = :stytypeid");
        if (status.equals("update")) {
            sb.append(" and ccwf.id.slno != :slno");
        }
        sb.append(" and (:creditfrom between ccwf.creditfrom and ccwf.creditto "
                + " or :creditto between ccwf.creditfrom and ccwf.creditto "
                + " and ccwf.creditfrom between :creditfrom and :creditto "
                + " or ccwf.creditto between :creditfrom and :creditto) ");
        try {

            if (status.equals("update")) {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("instituteid", instituteid).
                        setParameter("stytypeid", stytypeid).
                        setParameter("creditfrom", creditfrom).
                        setParameter("slno", slno).
                        setParameter("creditto", creditto).list();
            } else {
                list = getHibernateSession().createQuery(sb.toString()).
                        setParameter("instituteid", instituteid).
                        setParameter("stytypeid", stytypeid).
                        setParameter("creditfrom", creditfrom).
                        setParameter("creditto", creditto).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public String getCreditWiseBackPaperFeeAmount(String instituteid, String stytypeid, double creditfrom, double creditto) {
        List list = null;
        String value = "";
        StringBuilder sb = new StringBuilder();
        sb.append("select ccwf.feeamount from CourseCreditWiseFee ccwf "
                + " where  ccwf.id.instituteid = :instituteid "
                + " and ccwf.id.stytypeid = :stytypeid and (:creditfrom between ccwf.creditfrom and ccwf.creditto "
                + " or :creditto between ccwf.creditfrom and ccwf.creditto "
                + " and ccwf.creditfrom between :creditfrom and :creditto "
                + " or ccwf.creditto between :creditfrom and :creditto) ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("stytypeid", stytypeid).
                    setParameter("creditfrom", creditfrom).
                    setParameter("creditto", creditto).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return value;
    }

}
