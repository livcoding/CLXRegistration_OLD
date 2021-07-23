/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.Sct_ModuleRightsMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Sct_ModuleRightsMaster;
import com.jilit.irp.util.JIRPDBUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Projections;
/**
 *
 * @author sunny.singhal
 */
public class Sct_ModuleRightsMasterDAO extends HibernateDAO implements Sct_ModuleRightsMasterIDAO {

    private static final Log log = LogFactory.getLog(Sct_ModuleRightsMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SCT_ModuleRightsMaster records via Hibernate from the database");
        // return this.find("from SCT_ModuleRightsMaster as tname orderby seqid asc");
        return this.find("from Sct_ModuleRightsMaster as tname order by parentrightsid,seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_ModuleRightsMaster.class, id);
    }

    public int checkIfChildExist(final String rightsid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Sct_ModuleRightsMaster moduleRightsMaster = (Sct_ModuleRightsMaster) session.get(Sct_ModuleRightsMaster.class, rightsid);
                int i1 =0;// ((Integer) session.createFilter(moduleRightsMaster.getSct_eventlocations(), "select count(*)").list().get(0)).intValue();
                int i2 =0;// ((Integer) session.createFilter(moduleRightsMaster.getSct_eventlocationexcludes(), "select count(*)").list().get(0)).intValue();
                int i3 = ((Integer) session.createFilter(moduleRightsMaster.getSct_rolerightstaggings(), "select count(*)").list().get(0)).intValue();
//                int i4 = ((Integer) session.createFilter(moduleRightsMaster.getSct_urtses(), "select count(*)").list().get(0)).intValue();
//                int i5 = ((Integer) session.createFilter(moduleRightsMaster.getSct_userlocations(), "select count(*)").list().get(0)).intValue();
                return i1 + i2 + i3 ;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final Sct_ModuleRightsMaster moduleRightsMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Sct_ModuleRightsMaster.class);
                criteria.add(Restrictions.eq("rightsinfo", moduleRightsMaster.getRightsname()).ignoreCase());
                if (mode.equals("edit")) {
                    System.err.println("RIGHTSID:" + moduleRightsMaster.getRightsid());
                    System.err.println("RIGHTSINFO:" + moduleRightsMaster.getRightsname());
                    criteria.add(Restrictions.ne("rightsid", moduleRightsMaster.getRightsid()));
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Same Values Of Rights Info exist");
        }
        return errors;
    }

    private void getChildMenu(Set<Sct_ModuleRightsMaster> rootChildSet, List<Sct_ModuleRightsMaster> listOfNotNullParent) {
        for (Iterator<Sct_ModuleRightsMaster> it = rootChildSet.iterator(); it.hasNext();) {
            try {
                Sct_ModuleRightsMaster sct_ModuleRightsMaster = it.next();
                listOfNotNullParent.add(sct_ModuleRightsMaster);
                getChildMenu(sct_ModuleRightsMaster.getSct_modulerightsmasters(), listOfNotNullParent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List getParentRightsID() {
        List list=null;
        try{
              String qryString = "select r.rightsid as rightsid ,r.rightsinfo as rightsinfo from Sct_ModuleRightsMaster r  where r.parentrightsid is null";
            list = getHibernateTemplate().find(qryString);
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public Collection<?> getRightsModuleWise(final Collection moduleidslist) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = session.createCriteria(Sct_ModuleRightsMaster.class, "master");
                criteria.createAlias("master.sct_modulerightstagging", "smrt");
                criteria.setFetchMode("smrt", FetchMode.JOIN);
                //criteria.createAlias("smrt.sct_irpmodules", "sim");
                //criteria.setFetchMode("sct_irpmodules", FetchMode.JOIN);
                criteria.add(JIRPDBUtil.getCriteriaForString("master.deactive", "N", "nvl"));
                criteria.add(JIRPDBUtil.getCriteriaForString("smrt.deactive", "N", "nvl"));
                //criteria.add(JIRPDBUtil.getCriteriaForString("sim.deactive", "N", "nvl"));
                criteria.add(Restrictions.in("smrt.id.moduleid", moduleidslist));                
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.rightsinfo").as("rightsinfo")).add(Projections.property("master.rightsid").as("rightsid")).add(Projections.property("master.menulable").as("menulable")));

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                System.err.println(criteria.list().size());
                return criteria.list();
            }
        });
        System.err.println("modulerightsmasterdata" + list.size());
        return list;
    }
}
