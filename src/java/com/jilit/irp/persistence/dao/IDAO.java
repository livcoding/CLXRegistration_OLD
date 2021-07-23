package com.jilit.irp.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;

public interface IDAO {

    public abstract Collection<?> findAll();
    public abstract Collection<?> find(final String queryString);
    public abstract Object findByPrimaryKey(final Serializable id);

    public abstract void update(final Object record);

    public abstract void add(final Object record);

    public abstract void delete(final Object record);

    public abstract void saveOrUpdate(final Object record);
    public Short getMaxSequenceId(final Object exampleObject) ;
    public Short getMaxSequenceIdInstWise(final Object exampleObject,String instituteid) ;         
    public Short getMaxSequenceIdInstPK(final Object exampleObject,String instituteid) ;
    public Session getHibernateSession();

}
