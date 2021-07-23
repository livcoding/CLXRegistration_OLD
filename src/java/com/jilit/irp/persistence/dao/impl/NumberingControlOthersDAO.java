/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.NumberingControlOthersIDAO;
import com.jilit.irp.persistence.dto.NumberingControlOthers;
import com.jilit.irp.persistence.dto.NumberingControlOthersId;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class NumberingControlOthersDAO extends HibernateDAO implements NumberingControlOthersIDAO {

    private static final Log log = LogFactory.getLog(HostelTypeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all NumberingControlOthers records via Hibernate from the database");
        return this.find("from NumberingControlOthers as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(NumberingControlOthers.class, id);
    }

     public NumberingControlOthers getNumberingControlData(  final String pymd,   final String pGroupID,   Session session) {
        NumberingControlOthers nm = null;
        try {
            NumberingControlOthersId id = new NumberingControlOthersId();
            id.setGroupid(pGroupID);
            id.setYmd(pymd);
            nm = (NumberingControlOthers) session.get(NumberingControlOthers.class, id, LockMode.UPGRADE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nm;

    }
}
