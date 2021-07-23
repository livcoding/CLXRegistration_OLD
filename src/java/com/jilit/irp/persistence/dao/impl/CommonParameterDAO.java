/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CommonParameterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Parameters;
import com.jilit.irp.persistence.dto.Sct_IrpModules;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author ankit.kumar
 */
public class CommonParameterDAO extends HibernateDAO implements CommonParameterIDAO {

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Parameters.class, id);
    }

    public List getAllSct_IRPModule() {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpModules.class, "master");
                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),
                        Restrictions.eq("master.deactive", "N")));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.moduleid").as("moduleid")).add(Projections.property("master.modulecode").as("modulecode")).add(Projections.property("master.moduledesc1").as("moduledesc1")));
                criteria.addOrder(Order.asc("master.seqid"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    /**
     *
     * @param modulecode
     * @return
     */
    public List checkModuleCodeExist(final String modulecode) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpModules.class, "master");
                criteria.add(Restrictions.eq("master.modulecode", modulecode).ignoreCase());
                criteria.add(Restrictions.or(Restrictions.isNull("master.deactive"),
                        Restrictions.eq("master.deactive", "N")));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.moduleid").as("moduleid")).add(Projections.property("master.modulecode").as("modulecode")).add(Projections.property("master.moduledesc1").as("moduledesc1")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List geParametersData(final String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Criteria criteria = null;
                criteria = session.createCriteria(Parameters.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.moduleid"))
                        .add(Projections.property("master.id.parameterid"))
                        .add(Projections.property("master.id.instituteid"))
                        .add(Projections.property("master.parametervalue"))
                        .add(Projections.property("master.seqid"))
                        .add(Projections.property("master.deactive"))
                        .add(Projections.property("master.parameter")));
                Order.asc("master.id.moduleid");
                Order.asc("master.id.parameterid");
                return criteria.list();
            }
        });
        return list;
    }

    public List geParametersDataModulewise(final String instituteid,
            final String moduleid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Criteria criteria = null;
                criteria = session.createCriteria(Parameters.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.id.moduleid", moduleid));
                criteria.setProjection(Projections.projectionList().add((Projections.groupProperty("master.id.moduleid")))
                        .add((Projections.groupProperty("master.id.parameterid")))
                        .add((Projections.groupProperty("master.id.instituteid")))
                        .add((Projections.groupProperty("master.parametervalue")))
                        .add((Projections.groupProperty("master.seqid")))
                        .add((Projections.groupProperty("master.deactive")))
                        .add((Projections.groupProperty("master.parameter")))
                        .add((Projections.groupProperty("master.datatype"))));
                criteria.addOrder(Order.asc("master.id.parameterid"));
                return criteria.list();
            }
        });
        return list;
    }

    public List geModuleData(final String instituteid) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Criteria criteria = null;
                criteria = session.createCriteria(Parameters.class, "master");
                criteria.createAlias("master.sct_irpmodules", "irp");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.instituteid"))
                        .add(Projections.property("master.id.moduleid"))
                        .add(Projections.property("master.id.parameterid"))
                        .add(Projections.property("master.parametervalue"))
                        .add(Projections.property("irp.modulecode")));
                Order.asc("master.id.moduleid");
                return criteria.list();
            }
        });
        return list;
    }

    public String getParameterValue(final String instituteid, final String moduleid, final String parameterid) {
        String qry = " select p.parametervalue from Parameters p "
                + " where p.id.instituteid = '" + instituteid + "' "
                + " and p.id.moduleid = '" + moduleid + "' "
                + " and p.id.parameterid = '" + parameterid + "' "
                + " and coalesce(p.deactive,'N')='N' ";
        try {
            return getHibernateTemplate().find(qry.toString()).get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getValidFileName(String filename) {
        filename = filename.replaceAll(",", "_");
        filename = filename.replaceAll("-", "_");
        filename = filename.replaceAll("&", "_and_");
        filename = filename.replaceAll("%", "_");
        return filename;
    }

}
