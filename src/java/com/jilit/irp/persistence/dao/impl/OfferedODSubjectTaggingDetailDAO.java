
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.OfferedODSubjectTaggingIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.OfferedODSubjectTaggingDetailIDAO;
import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingDetail;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author deepak.gupta
 */
public class OfferedODSubjectTaggingDetailDAO extends HibernateDAO implements OfferedODSubjectTaggingDetailIDAO {

    private static final Log log = LogFactory.getLog(OfferedODSubjectTaggingDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all OfferedODSubjectTaggingDetail records via Hibernate from the database");
        return this.find("from OfferedODSubjectTaggingDetail as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(OfferedODSubjectTaggingDetail.class, id);
    }

    public Collection<?> findByPrimaryKeys(Serializable id) {
        return this.find("from OfferedODSubjectTaggingDetail as tname");
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((OfferedODSubjectTaggingDetail) record);
    }

    @Override
    public List getOfferSubjectTagginfDetaildata(String instituteid, String registrationid, String offersubjectid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sc.id.subjectcomponentid, sc.subjectcomponentcode, "
                + " od.ltppassingmarks, od.totalccpmarks, "
                + " od.noofhours, od.noofclassinaweek,od.totalclasses, od.deactive,od.staffid,od.stafftype "
                + " from OfferedODSubjectTaggingDetail od, SubjectComponent sc where "
                + " od.id.instituteid=sc.id.instituteid and od.id.subjectcomponentid=sc.id.subjectcomponentid "
                + " and od.id.instituteid= :instituteid "
                + " and od.id.registrationid= :registrationid "
                + " and od.id.offersubjectid= :offersubjectid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).
                    setParameter("offersubjectid", offersubjectid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

}
