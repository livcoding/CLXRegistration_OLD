/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.Sct_RoleMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.Sct_ModuleRightsMaster;
import com.jilit.irp.persistence.dto.Sct_RoleMaster;
import com.jilit.irp.persistence.dto.Sct_RoleRightsTagging;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 *
 * @author akshya.gaur
 */
public class Sct_RoleMasterDAO  extends HibernateDAO implements Sct_RoleMasterIDAO{

    private static final Log log = LogFactory.getLog(Sct_RoleMaster.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_RoleMaster records via Hibernate from the database");
        return this.find("from Sct_RoleMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_RoleMaster.class, id);
    }

    public Collection<?> checkIfRoleNameExists(final String roleName) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_RoleMaster.class);
                if (roleName!=null) {
                    criteria.add(Restrictions.eq("rolename", roleName));
                }
                return criteria.list();
            }
        });
        return list;
    }

    public int checkIfChildExist(final String roleid) {
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Sct_RoleMaster dto = (Sct_RoleMaster) session.get(Sct_RoleMaster.class, roleid);
//                int i1 = Integer.parseInt(session.createFilter(dto.getSct_userroleses(), "select count(*)").list().get(0).toString());               
                int i2 =Integer.parseInt(session.createFilter(dto.getSct_rolerightstaggings(), "select count(*)").list().get(0).toString());
                return i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final Sct_RoleMaster dto, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(Sct_RoleMaster.class);
                criteria.add(Restrictions.eq("rolename", dto.getRolename()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("roleid", dto.getRoleid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Role Name ");
        }


        return errors;
    }

    public List getCounsellingRoleList(String roleid)
    {
        List rtnlist=null;
        String str=" select distinct rrt.id.rightsid from Sct_ModuleRightsTagging mrt, Sct_RoleRightsTagging rrt" +
                " where mrt.id.rightsid=rrt.id.rightsid" +
                " and mrt.id.moduleid in (  select irpm.moduleid from Sct_IrpModules irpm where  " +
                " irpm.moduleid=mrt.id.moduleid " +
              //  " and coalesce(irpm.counsellingmodule,'N')='Y' " +
                " and coalesce(irpm.deactive,'N')='N')" +
                " and rrt.id.roleid='"+roleid+"'" +
                " and coalesce(rrt.deactive,'N')='N'   " +
                " and coalesce(mrt.deactive,'N')='N' ";
        try{
        rtnlist=getHibernateTemplate().find(str);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return rtnlist;
    }
    
    public Collection<?> getAssignedRoles() {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_ModuleRightsMaster.class, "master");
                        criteria.createAlias("master.sct_modulerightstagging", "mrt").setFetchMode("sct_modulerightstagging", FetchMode.JOIN);
                        criteria.createAlias("master.sct_rolerightstaggings", "rrt").setFetchMode("sct_rolerightstaggings", FetchMode.JOIN);
                        criteria.createAlias("mrt.sct_irpmodules", "irm").setFetchMode("sct_irpmodules", FetchMode.JOIN);
                        criteria.createAlias("rrt.sct_rolemaster", "rm").setFetchMode("sct_rolemaster", FetchMode.JOIN);
                        criteria.add(Restrictions.eqProperty("master.rightsid", "mrt.id.rightsid"));
                        criteria.add(Restrictions.eqProperty("master.rightsid", "rrt.id.rightsid"));
                        criteria.add(Restrictions.eqProperty("mrt.id.moduleid", "irm.moduleid"));

                criteria.add(Restrictions.eq("master.deactive", "N"));
                criteria.add(Restrictions.eq("irm.deactive", "N"));
                criteria.add(Restrictions.isNotNull("master.url"));                                          
                criteria.add(Restrictions.isNotNull("master.parentrightsid"));                                          
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.rightsid").as("rightsid")).add(Projections.property("master.rightsinfo").as("rightsinfo")).add(Projections.property("irm.modulecode").as("modulecode")).add(Projections.property("rrt.id.roleid").as("roleid")).add(Projections.property("rm.roledescription").as("roledescription")));
                //criteria.add(Projections.groupProperty("master.rightsid"));
                criteria.addOrder(Order.asc("irm.modulecode"));

               criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();


            }
        });
        return list;

    }  
    
    public List getRoleCode() {
        String qry = "select new map(a.roleid as roleid, a.rolename as rolename, a.roledescription as roledescription) " +
                " from Sct_RoleMaster a where coalesce(a.deactive, 'N')='N' ";
        try {
            return getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
     public List getModuleCode() {
        String qry = "select new map(a.modulecode as modulecode, a.moduleid as moduleid ) " +
                " from Sct_IrpModules a where coalesce(a.deactive, 'N')='N' order by seqid";
        try {
            return getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
     public List getAssignedRole(String roleid) {
        StringBuilder qry = new StringBuilder();
        qry.append(" select distinct a.rightsid, a.rightsinfo from Sct_ModuleRightsMaster a,Sct_RoleRightsTagging b where ");
        qry.append(" a.rightsid = b.id.rightsid");
        qry.append(" and b.id.roleid='" + roleid + "' ");
        qry.append(" and (a.url is not null or b.id.roleid='" + roleid + "') ");
        qry.append(" and coalesce(a.deactive, 'N')='N'  group by a.rightsid, a.rightsinfo order by a.rightsinfo");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List getRemainingRoles(String roleid, String moduleid) {
        StringBuilder qry = new StringBuilder();
        qry.append(" select  a.rightsid, a.rightsinfo from Sct_ModuleRightsMaster a,Sct_ModuleRightsTagging sct_mod where ");
        qry.append(" a.rightsid not in( select b.id.rightsid from Sct_RoleRightsTagging b where b.id.roleid='" + roleid + "') ");
        qry.append(" and (a.url is not null or sct_mod.id.moduleid='" + moduleid + "')");
        qry.append(" and a.rightsid in (select b.id.rightsid from Sct_ModuleRightsTagging b where b.id.moduleid='" + moduleid + "')");
        qry.append(" and coalesce(a.deactive, 'N')='N' group by a.rightsid, a.rightsinfo order by a.rightsinfo ");
        try {
            return getHibernateTemplate().find(qry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Object findByPrimaryKey1(Serializable id) {
        return getHibernateTemplate().get(Sct_RoleRightsTagging.class, id);
    }
    public boolean saveObjlist(List objList) {
        Session session = null;
        Transaction tx = null;
        boolean flag = false;
        try {
            if (session == null) {
                session = getHibernateSession();
                tx = session.beginTransaction();
            }
            for (int i = 0; i < objList.size(); i++) {
                session.saveOrUpdate(objList.get(i));
            }
            tx.commit();
            if (tx.wasCommitted()) {
                flag = true;
            }
        } catch (Exception e) {
            tx.rollback();
            flag = false;
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.clear();
                session.close();
            }
        }
        return flag;
    }
   
 
}
