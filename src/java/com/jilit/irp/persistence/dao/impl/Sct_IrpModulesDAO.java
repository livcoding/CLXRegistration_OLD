/*
 * To change this template, choose ToolgetRightsModuleWises | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.Sct_IrpModulesIDAO;
import com.jilit.irp.persistence.dto.Sct_IrpModules;
import com.jilit.irp.persistence.dto.Sct_ModuleRightsMaster;
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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author subrata.lohar
 */
public class Sct_IrpModulesDAO extends HibernateDAO implements Sct_IrpModulesIDAO {

    private static final Log log = LogFactory.getLog(Sct_IrpModulesDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all SCT_IrpModules records via Hibernate from the database");
        return this.find("from Sct_IrpModules dd where coalesce(dd.counsellingmodule,'N')='N' ");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_IrpModules.class, id);
    }

    

    public int checkIfChildExist(final String moduleid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Sct_IrpModules irpModules = (Sct_IrpModules) session.get(Sct_IrpModules.class,  moduleid);
//                int i1 = ((Integer) session.createFilter(irpModules.getSct_modulerightstaggings(), "select count(*)").list().get(0)).intValue();
                int i2 = ((Integer) session.createFilter(irpModules.getParameterss(), "select count(*)").list().get(0)).intValue();
               int i3 = ((Integer) session.createCriteria(Sct_IrpModules.class)
                        //.add(Expression.eq("id.instituteid", irpModules.getId().getInstituteid()))
                        .add(Restrictions.eq("parentid", irpModules.getModuleid()))
                        // .add(Expression.ne("moduleid", irpModules.getModuleid()))
                         .list().size()).intValue();
                 System.err.println("VALUE OF i3:"+i3);
                return  i2 +i3;
               // return 0;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final Sct_IrpModules irpModules, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Sct_IrpModules.class);
                //criteria.add(Expression.eq("id.instituteid", irpModules.getId().getInstituteid()));
                criteria.add(Restrictions.eq("modulecode", irpModules.getModulecode()).ignoreCase());
                if (mode.equals("edit")) {
                    System.err.println("MODULEID:::::::::::::"+irpModules.getModulecode()+"::"+irpModules.getModuleid());
                    criteria.add(Restrictions.ne("moduleid", irpModules.getModuleid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Same Values Of Module Code exist");
        }
        return errors;
    }

//    public ASObject getModuleMenu(final List rightsids) {
//        final ASObject menuData = (ASObject) getHibernateTemplate().execute(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                final Criteria crit = session.createCriteria(Sct_IrpModules.class);
//                List<Sct_IrpModules> irpModules = null;
//                crit.add(Restrictions.in("moduleid", rightsids.toArray()));
//                crit.addOrder(Order.asc("seqid"));
//                irpModules = crit.list();
//                ASObject moduleItems = new ASObject(); //actual menu
//                List<Sct_IrpModules> listOfNotNullParent = new ArrayList<Sct_IrpModules>();
//                List<ASObject> modluetreelist = new ArrayList<ASObject>();
//                if (irpModules != null) {
//                    for (int i = 0; i < irpModules.size(); i++) {
//                        Sct_IrpModules sct_IrpModules = irpModules.get(i);
//                        if (sct_IrpModules.getSct_irpmodules() == null) {
//                            ASObject modlueasobject = new ASObject();
//                            modlueasobject.put("label", sct_IrpModules.getModulecode()+"  ["+sct_IrpModules.getModuledesc1()+"]");
//                            modlueasobject.put("id", sct_IrpModules.getModuleid());
//                            modlueasobject.put("seqid", sct_IrpModules.getSeqid());
//                            modlueasobject.put("deactive", sct_IrpModules.getDeactive());
//                            modlueasobject.put("counsellingmodule", sct_IrpModules.getCounsellingmodule());
//                            //List<Sct_IrpModules> rootChildList = (List) session.createFilter(sct_IrpModules.getSct_irpmoduleses(), "where moduleid in  (" + JIRPStringUtil.getQuerySubstring(rightsids) + ")").list();
//                            //Set<Sct_IrpModules> rootChildSet = new HashSet<Sct_IrpModules>(rootChildList);
//                            //if (rootChildList.size() > 0) {
//                              //  getChild(rootChildSet, listOfNotNullParent);
//                            //}
//                            modluetreelist.add(modlueasobject);
//                        }
//                    }
//                }
//                for (int i = 0; i < modluetreelist.size(); i++) {
//                    ASObject aSObject = modluetreelist.get(i);
//                    addsubchild(aSObject, aSObject.get("id").toString(), listOfNotNullParent);
//                }
//                //code ends
//                //System.err.println("aaa12" + modluetreelist);
//                //System.err.println("aaa12" + modluetreelist.size());
//                moduleItems.put("addldata", "RootNode");
//                moduleItems.put("children", modluetreelist);
//                moduleItems.put("label", "Program Type");
//                return moduleItems;
//            }
//        });
//        return menuData;
//    }

//    private boolean addsubchild(ASObject addinthis, String searchitem, List<Sct_IrpModules> searchinthis) {
//        boolean flag = false;
//
//        for (int i = 0; i < searchinthis.size(); i++) {
//            Sct_IrpModules dto = searchinthis.get(i);
//            if ((dto.getSct_irpmodules().getModuleid()).equals(searchitem)) {
//                ASObject modlueasobject = new ASObject();
//                modlueasobject.put("label", dto.getModulecode());
//                modlueasobject.put("id", dto.getModuleid());
//                modlueasobject.put("seqid", dto.getSeqid());
//                modlueasobject.put("deactive", dto.getDeactive());
//                addinthis.put("children", modlueasobject);
//                if (dto.getSct_irpmodules().getSct_irpmoduleses() != null) {
//                    addsubchild(modlueasobject, modlueasobject.get("id").toString(), searchinthis);
//                }
//            }
//        }
//        return flag;
//    }

    private void getChild(Set<Sct_IrpModules> rootChildSet, List<Sct_IrpModules> listOfNotNullParent) {
        for (Iterator<Sct_IrpModules> it = rootChildSet.iterator(); it.hasNext();) {
            Sct_IrpModules sct_IrpModules = it.next();
            listOfNotNullParent.add(sct_IrpModules);
            getChild(sct_IrpModules.getSct_irpmoduleses(), listOfNotNullParent);
        }
    }

    private void getChildMenu(Set<Sct_ModuleRightsMaster> rootChildSet, List<Sct_ModuleRightsMaster> listOfNotNullParent) {
        for (Iterator<Sct_ModuleRightsMaster> it = rootChildSet.iterator(); it.hasNext();) {
            
            Sct_ModuleRightsMaster sct_ModuleRightsMaster = it.next();
            //System.err.println("serrrrrrr:-"+sct_ModuleRightsMaster.getRightsid());
            listOfNotNullParent.add(sct_ModuleRightsMaster);
            getChildMenu(sct_ModuleRightsMaster.getSct_modulerightsmasters(), listOfNotNullParent);
        }
        //System.err.println("scrrr :- "+listOfNotNullParent);
    }
    public Collection<?> getModuleId(final String moduleName){
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria cr = session.createCriteria(Sct_IrpModules.class);
                cr.add(Restrictions.eq("modulecode", moduleName).ignoreCase());
                cr.setProjection(Projections.property("moduleid").as("moduleid"));
                return cr.list();
            }
        });
        return list;
    }

    
     public Collection<?> getModuleCode(){
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpModules.class,"master");   
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.moduleid").as("moduleid")).add(Projections.property("master.modulecode").as("modulecode")));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);                
                return criteria.list();
            }
        });
        return list;
    }

}
