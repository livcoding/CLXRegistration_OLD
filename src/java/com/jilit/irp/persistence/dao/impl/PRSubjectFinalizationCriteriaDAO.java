package com.jilit.irp.persistence.dao.impl;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.PRSubjectFinalizationCriteriaIDAO;
import com.jilit.irp.persistence.dto.PRSubjectFinalizationCriteria;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 *
 * @author singh.jaswinder
 */
public class PRSubjectFinalizationCriteriaDAO extends HibernateDAO implements PRSubjectFinalizationCriteriaIDAO  {

private static final Log log = LogFactory.getLog(PRSubjectFinalizationCriteria.class);

    public Collection<?> findAll() {
        log.info("Retrieving all PRSubjectFinalizationCriteria records via Hibernate from the database");
        return this.find("from PRSubjectFinalizationCriteria as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(PRSubjectFinalizationCriteria.class, id);
    }
   
}
