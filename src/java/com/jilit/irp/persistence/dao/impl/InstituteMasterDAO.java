/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.InstituteMasterIDAO;
import com.jilit.irp.persistence.dto.InstituteMaster;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author subrata.lohar
 */
public class InstituteMasterDAO extends HibernateDAO implements InstituteMasterIDAO {

    private static final Log log = LogFactory.getLog(InstituteMasterDAO.class);

    @Override
    public Collection<?> findAll() {
        log.info("Retrieving all InstituteMaster records via Hibernate from the database");
        return this.find("from InstituteMaster as tname");
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(InstituteMaster.class, id);
    }

    public List getInstituteUniqueIdBase(String uniqueid) {
        String qrystring = "select im.institutecode,im.institutename,im.uniqueid from InstituteMaster im"
                + " where im.uniqueid='" + uniqueid + "' ";

        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    @Override
    public List getAllInstituteCode(String instituteid) {
        String qrystring = "select im.institutecode,im.institutename,im.uniqueid,im.clientid,im.placeofpostingid from InstituteMaster im "
                + " where  im.id.instituteid='" + instituteid + "' ";
        List list = getHibernateTemplate().find(qrystring);
        return list;
    }

    @Override
    public List getInstituteCodeForAddDrop(String userid, String rightsid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct im.instituteid,im.institutecode,im.institutename from InstituteMaster im"
                + " where exists(select  ur.id.rightsbasedid from Sct_UserRolesCriteraBased ur where coalesce(ur.deactive,'N')='N'"
                + " and ur.id.rightsbasedid=im.instituteid"
                + " and ur.id.userid=:userid"
                + " and exists(select rr.id.roleid from Sct_RoleRightsTagging rr where rr.id.rightsid=:rightsid and rr.id.roleid=ur.id.roleid)) "
                + " or exists(select us.id.rightsbasedid from Sct_UserRightsCriteraBased us where  "
                + " us.id.rightsbasedid=im.instituteid and us.id.rightsid=:rightsid and us.id.userid=:userid) and coalesce(im.deactive,'N')='N'"
                + "order by im.institutecode");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("userid", userid).
                    setParameter("rightsid", rightsid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }
}
