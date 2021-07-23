package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.ElectiveMasterIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.ElectiveMaster;
import com.jilit.irp.persistence.dto.ElectiveMasterId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author suman.saurabh
 */
public class ElectiveMasterDAO extends HibernateDAO implements ElectiveMasterIDAO {

    private static final Log log = LogFactory.getLog(ElectiveMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all ElectiveMaster records via Hibernate from the database");
        return this.find("from ElectiveMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(ElectiveMaster.class, id);
    }

    public List doValidate(final ElectiveMaster master, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);

        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(ElectiveMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", master.getId().getInstituteid()));
                criteria.add(Restrictions.ne("id.electiveid", master.getId().getElectiveid()).ignoreCase());
                criteria.add(Restrictions.eq("electivecode", master.getElectivecode()).ignoreCase());
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Elective Code Already Exist!");
        }
        return errors;

    }

    public int checkIfChildExist(final ElectiveMasterId id) {

        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ElectiveMaster electivemaster = (ElectiveMaster) session.get(ElectiveMaster.class, id);
                int i1 = Integer.parseInt(session.createFilter(electivemaster.getProgramschemes(), "select count(*)").list().get(0).toString());
                int i2 = Integer.parseInt(session.createFilter(electivemaster.getProgramschemeacacmicyearwises(), "select count(*)").list().get(0).toString());
                return i1 + i2;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List getElectiveMaster(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select em.id.electiveid,em.id.instituteid,ins.institutecode,em.electivecode,em.electivedesc,em.deactive,em.credits "
                + " from ElectiveMaster em,InstituteMaster ins where em.id.instituteid = :instituteid and ins.id.instituteid = em.id.instituteid order by  em.id.electiveid desc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List EditElectiveMaster(String instituteid, String elective_id) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select em.id.electiveid,em.id.instituteid,ins.institutecode,em.electivecode,em.electivedesc,em.deactive,em.credits from ElectiveMaster em,InstituteMaster ins "
                + " where em.id.instituteid = :instituteid and ins.id.instituteid = em.id.instituteid and em.id.electiveid = :elective_id ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("elective_id", elective_id).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
