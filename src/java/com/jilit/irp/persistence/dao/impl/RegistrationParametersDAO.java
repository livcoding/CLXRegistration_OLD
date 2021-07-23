/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.RegistrationParametersIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Parameters;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Malkeet Singh
 */
public class RegistrationParametersDAO extends HibernateDAO implements RegistrationParametersIDAO {

    private static final Log log = LogFactory.getLog(RegistrationParametersDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Parameters records via Hibernate from the database");
        return this.find("from Parameters as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Parameters.class, id);
    }

    public List getGridData(String instituteid, List parameterslist, String status) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select p.id.instituteid,p.id.moduleid,p.id.parameterid,p.parameter,p.parametervalue,p.seqid,p.datatype"
                + " from Parameters p where p.id.moduleid in ('MOD08000002','MOD08000018','MOD08000025','MOD08000033') "
                + " and p.id.instituteid=:instituteid ");
        if (status.equals("other")) {
            sb.append(" and p.id.parameterid not in (:parameterslist) order by p.seqid desc");
        } else {
            sb.append(" and p.id.parameterid in (:parameterslist) order by p.seqid desc");
        }
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameterList("parameterslist", parameterslist).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSeqId(String instituteid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select p.seqid from Parameters p where p.id.instituteid=:instituteid order by p.seqid desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getParametersValue(String instituteid, String parameterid) {
        StringBuilder sb = new StringBuilder();
        String value = "";
        List list = null;
        sb.append(" select p.parametervalue from Parameters p where p.id.instituteid=:instituteid and p.id.moduleid='MOD08000002' and p.id.parameterid=:parameterid");
        try {
            list = getHibernateSession().createQuery(sb.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("parameterid", parameterid).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public String getFeeheadid(String instituteid, String feeheadCode) {
        StringBuilder sb = new StringBuilder();
        String value = "";
        sb.append(" select fh.id.feeheadid from FeeHeads fh where feehead=:feeheadCode and fh.id.instituteid=:instituteid");
        try {
            List list = getHibernateSession().createQuery(sb.toString())
                    .setParameter("instituteid", instituteid)
                    .setParameter("feeheadCode", feeheadCode).list();
            if (list.size() > 0) {
                value = list.get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public List getFeeheadids(List instituteid, List feeheadCode) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select fh.id.feeheadid from FeeHeads fh where feehead in(:feeheadCode) and fh.id.instituteid in(:instituteid)");
        try {
            list = getHibernateSession().createQuery(sb.toString())
                    .setParameterList("instituteid", instituteid)
                    .setParameterList("feeheadCode", feeheadCode).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
   }
    public List getParametersValue(List instituteid, String parameterid) {
        StringBuilder sb = new StringBuilder();
        List list = null;
        sb.append(" select p.parametervalue from Parameters p where p.id.instituteid in(:instituteid) and p.id.moduleid='MOD08000002' and p.id.parameterid=:parameterid");
        try {
            list = getHibernateSession().createQuery(sb.toString())
                    .setParameterList("instituteid", instituteid)
                    .setParameter("parameterid", parameterid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
