/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

/**
 *
 * @author ashok.singh
 */
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
//import com.jilit.irp.data.FilterInfoData;

import com.jilit.irp.persistence.dao.CommitteeMeetingDetailIDAO;
import com.jilit.irp.persistence.dao.CommitteeMeetingDetailParticipantsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CommitteeMeetingDetailId;
import com.jilit.irp.persistence.dto.CommitteeMeetingDetail;
import com.jilit.irp.util.JIRPDBUtil;
import com.jilit.irp.util.JIRPSession;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Shimona.Khandelwal
 */
public class CommitteeMeetingDetailDAO extends HibernateDAO implements CommitteeMeetingDetailIDAO {
private static final Log log = LogFactory.getLog(CommitteeMeetingDetailDAO.class);
public Collection<?> findAll()
{

    log.info("Retrieving all CommitteeMeetingDetail records via Hibernate from the database");
    return this.find("from CommitteeMeetingDetail as tname");
 }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeMeetingDetail.class, id);
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((CommitteeMeetingDetail) record);
    }

  }