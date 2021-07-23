package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.UtilIDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

import java.util.Map;
import java.util.Set;
import java.lang.String;
import com.jilit.irp.context.AppContext;
import com.jilit.irp.persistence.dao.DAOFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

public class UtilDAO extends HibernateDAO implements UtilIDAO {

    DAOFactory daoFactory;

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    private ServletContext context;

    public DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = (DAOFactory) AppContext.getApplicationContext().getBean("DAOFactory");
        }
        return daoFactory;
    }

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    private UtilDAO() {
    }

    public static UtilDAO getInstance() {

        UtilDAO _instance = new UtilDAO();

        _instance.setSqlRestrictionPropertyFile(new ClassPathResource("sqlRestrictions.properties"));

        return _instance;
    }

    /**
     * Execute a named query without any parameters.
     */
    public List findSimpleData(final String queryName) {

        return getHibernateTemplate().findByNamedQuery(queryName);
    }

    /**
     * Execute a named query, binding one value to a "?"
     */
    public List findSimpleData(final String queryName, final Object parmValue) {

        return getHibernateTemplate().findByNamedQuery(queryName, parmValue);
    }

    public List findSimpleDataAsListOfValueBean(final String queryName, final Object parmValue) {

        return getHibernateTemplate().findByNamedQueryAndValueBean(queryName, parmValue);
    }

    /**
     * Execute a named query, binding a number of values to "?"
     *
     * @param queryName
     * @param parmValues
     * @return
     */
    public List findSimpleData(final String queryName, final Object[] parmValues) {
        return getHibernateTemplate().findByNamedQuery(queryName, parmValues);
    }

    /**
     * Execute a named query, binding a number of values to ":" named parameters in the query string.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List findSimpleData(final String queryName,
            final Map parametersLinkedHashMap) {

        final Set entrySet = parametersLinkedHashMap.entrySet();
        final int parmCount = entrySet.size();

        // Build an array of parameter names and an array of parameter values
        // from the given parametersLinkedHashMap
        final String[] parmNames = new String[parmCount];
        final Object[] parmValues = new Object[parmCount];

        final Iterator entrySetIterator = entrySet.iterator();
        int i = 0;

        while (entrySetIterator.hasNext()) {
            final Entry entry = (Entry) entrySetIterator.next();
            parmNames[i] = (String) entry.getKey();
            parmValues[i] = entry.getValue();
            i++;
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, parmNames, parmValues);
    }

    /**
     * Convenience method that executes a named query without any parameters and returns a List of Strings.
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName) {

        return this.findSimpleData(queryName, queryName);
    }

    /**
     * Convenience method that executes a named query, binding one value to a "?" and returns a List of Strings.
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Object parmValue) {

        return this.findSimpleData(queryName, parmValue);
    }

    /**
     * Convenience method that executes a named query, binding a number of values to "?" and returns a List of Strings.
     *
     * @param queryName
     * @param parmValues
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Object[] parmValues) {

        return this.findSimpleData(queryName, parmValues);
    }

    /**
     * Convenience method that executes a named query, binding a number of values to ":" named parameters in the query string and returns a List of Strings.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Map parametersLinkedHashMap) {

        return this.findSimpleData(queryName, parametersLinkedHashMap);
    }

    /**
     * Convenience method that executes a named query without any parameters. and returns a List of Objects.
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName) {

        return this.findSimpleData(queryName);
    }

    /**
     * Convenience method that executes a named query, binding one value to a "?" and returns a List of Objects.
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Object parmValue) {

        return this.findSimpleData(queryName, parmValue);
    }

    /**
     * Convenience method that executes a named query, binding a number of values to "?" and returns a List of Objects.
     *
     * @param queryName
     * @param parmValues
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Object[] parmValues) {

        return this.findSimpleData(queryName, parmValues);
    }

    /**
     * Convenience method that executes a named query, binding a number of values to ":" named parameters in the query string and returns a List of Objects.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Map parametersLinkedHashMap) {

        return this.findSimpleData(queryName, parametersLinkedHashMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see mil.af.elsg.cmos.db.IUtilDAOs#deleteAll(java.util.Collection)
     */
 /*
     * (non-Javadoc)
     *
     * @see mil.af.elsg.cmos.db.IUtilDAOs#deleteAll(java.util.Collection)
     */
    public void deleteAll(final Collection objectsToDelete) {

        getHibernateTemplate().deleteAll(objectsToDelete);
    }
    /**
     * The sql restrictions.
     */
    private Map sqlRestrictions = null;

    /**
     * Sets the sql restriction property file.
     *
     * @param sqlRestrictionResource the sql restriction property file
     */
    public void setSqlRestrictionPropertyFile(final Resource sqlRestrictionResource) {

        if (!sqlRestrictionResource.exists()) {
            try {
                System.out.println("Cannot find sqlRestriction property file " + sqlRestrictionResource.getFile().getAbsolutePath());
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        InputStream inputStream = null;
        try {
            inputStream = sqlRestrictionResource.getInputStream();
        } catch (final IOException e1) {
            e1.printStackTrace();
        }

        if (inputStream != null) {
            try {
                sqlRestrictions = new Properties();
                ((Properties) sqlRestrictions).load(inputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.sqlRestrictions == null) {
            System.out.println("*** could not load property file");
        } else {
            System.out.println("*** done");
        }

    }

    private Criteria buildCriteria(final Session session,
            final Object exampleObjectSource, final String[] excludeProperties) {

        // exclude date properties from criteria
        Example example = Example.create(exampleObjectSource).ignoreCase().enableLike(MatchMode.EXACT);
        for (String exclude : excludeProperties) {
            example = example.excludeProperty(exclude);
        }

        final Criteria criteria = session.createCriteria(
                exampleObjectSource.getClass()).add(example);

        //	final StringBuffer sqlRestriction = new StringBuffer(64);
        return criteria;
    }

    private Criteria buildCriteria(final Session session,
            final Object exampleObjectSource) {

        // exclude date properties from criteria
        final Criteria criteria = session.createCriteria(
                exampleObjectSource.getClass()).add(
                Example.create(exampleObjectSource).ignoreCase().enableLike(MatchMode.EXACT));

        //	final StringBuffer sqlRestriction = new StringBuffer(64);
        return criteria;
    }

    public int countObjects(final String queryName, final String[] parms,
            final Object exampleObject, final boolean deactivechk) {
        final String sqlRestriction = this.getSqlRestriction(queryName, parms);
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {
                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);
                if (sqlRestriction != null) {
                    criteria.add(Restrictions.sqlRestriction(sqlRestriction));
                }
                if (deactivechk) {
                    criteria.add(Restrictions.sqlRestriction(getSqlRestriction("deactivechk", new String[]{})));
                }
                criteria.setProjection(Projections.projectionList().add(
                        Projections.rowCount()));
                return criteria.list();
            }
        });
        final Integer count = (Integer) list.get(0);
        return count.intValue();
    }
////public void test1() {
////    Session session =null;
////    Transaction tx = null;
////try {
////session =getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
////
////tx = session.beginTransaction();
////Ingroup group = (Ingroup) session.get(Ingroup.class, "0000INGP0910A0000006",LockMode.UPGRADE);
////group.setDescription("hello11");
////session.update(group);
////
////tx.commit();
////}
//// catch (Exception e) {
////     if (tx!=null) tx.rollback();
////     e.printStackTrace();
//// }
//// finally {
////     session.close();
//// }
//
//		//getHibernateTemplate()."create table jc2.c#allocationdetail as select * from jc2.c#allocationdetail").executeUpdate();
//
//
//
//	}

//public void test() {
//    Session session =  Session sessnull;
//    Transaction tx = null;
//try {
//session =getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//
//tx = session.beginTransaction();
////Ingroup group = (Ingroup) session.get(Ingroup.class, "0000INGP0910A0000006",LockMode.UPGRADE);
//	final Criteria criteria = session.createCriteria(IdGenerationControl.class);
//    IdGenerationControlId id =((IdGenerationControl)criteria.list().get(3)).getId();
//    IdGenerationControlId id2 = new IdGenerationControlId("0000","InGroupID","09  ","10",'A');
//IdGenerationControl control = (IdGenerationControl) session.get(IdGenerationControl.class, new IdGenerationControlId("0000","InGroupID","09  ","10",'A'));
//    System.err.println(""+control);
//
//tx.commit();
//}
// catch (Exception e) {
//     if (tx!=null) tx.rollback();
//     e.printStackTrace();
// }
// finally {
//     session.close();
// }
//
//		//getHibernateTemplate()."create table jc2.c#allocationdetail as select * from jc2.c#allocationdetail").executeUpdate();
//
//
//
//	}
//public void test() {
//    Session session =null;
//    Transaction tx = null;
//try {
//session =getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
//
//tx = session.beginTransaction();
//String query = "create table c#allocationdetail as select * from c#allocationdetail";
//
//session.createSQLQuery(query).executeUpdate();
//tx.commit();
//}
// catch (Exception e) {
//     if (tx!=null) tx.rollback();
//     e.printStackTrace();
// }
// finally {
//     session.close();
// }
//
//		//getHibernateTemplate()."create table jc2.c#allocationdetail as select * from jc2.c#allocationdetail").executeUpdate();
//
//
//
//	}
    public Collection findByExample(final Object exampleObject,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);

                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);

                return criteria.list();
            }
        });
    }

    public int countObjects(final String queryName, final Map<String, String> params) {
        Integer count = new Integer(0);
        count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Query query = session.getNamedQuery(queryName);
                for (final String paramName : params.keySet()) {
                    query.setString(paramName, params.get(paramName));
                }
                return query.list().size();
            }
        });

        return count.intValue();

    }

    public List findNamedQuery(final String queryName,
            final Map<String, String> params) {

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Query query = session.getNamedQuery(queryName);

                String hql = query.getQueryString();

                for (final String paramName : params.keySet()) {
                    hql = org.apache.commons.lang.StringUtils.replace(hql, ":" + paramName, params.get(paramName));
                }

                return session.createQuery(hql).list();

            }
        });
    }

    /**
     * This method may not be robust in the sense if I had two variables, one a substring of the other - say ":docNumber" and ":docNumberType", then this method's "replace" may mangle the sql
     * String..depending on which variable came first in the iterative loop.
     */
    public void executeDDLUpdate(final String queryName,
            final Map<String, String> params) {

        int i = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                Query query = session.getNamedQuery(queryName);
                String hql = query.getQueryString();

                for (final String paramName : params.keySet()) {
                    hql = org.apache.commons.lang.StringUtils.replace(hql, ":" + paramName, params.get(paramName));
                }

                query = session.createQuery(hql);
                return query.executeUpdate();
            }
        });
        System.err.println("In executeDDLUpdate" + i + " records updated");
    }

    /**
     * Find by example.
     *
     * @param exampleObject the example object
     *
     * @return the collection
     */
    public Collection findByExample(final Object exampleObject) {

        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);
                return criteria.list();
            }
        });
    }

    public int countObjects(final Object exampleObject) {

        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);
                criteria.setProjection(Projections.projectionList().add(
                        Projections.rowCount()));
                return criteria.list();
            }
        });
        final Integer count = (Integer) list.get(0);
        return count.intValue();
    }

    /**
     * Convenience method to findByExample with sqlRestriction that has no parameters
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public Collection findByExample(final String queryName,
            final Object exampleObject, final int firstResult,
            final int maxResults) {

        return this.findByExample(queryName, new String[0], exampleObject,
                firstResult, maxResults);
    }

    /**
     * Convenience method to findByExample with sqlRestriction that has one parameter
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public Collection findByExample(final String queryName, final String parm,
            final Object exampleObject, final int firstResult,
            final int maxResults) {

        return this.findByExample(queryName, new String[]{parm},
                exampleObject, firstResult, maxResults);
    }

    public Collection findByExample(final String queryName, final String parm,
            final Object exampleObject, final String[] excludeProperties, final int firstResult,
            final int maxResults) {

        return this.findByExample(queryName, new String[]{parm},
                exampleObject, excludeProperties, firstResult, maxResults);
    }

    /**
     * @param queryName name of query, or more specifically, SQL Restriction to be loaded from the sqlRestrictions properties file
     * @param parms List of parameters to be used in positional parameters in the sql Restriction string
     * @param exampleObject Example business object with only fields set that should be used in search criteria for query-by-example operation
     * @param firstResult Lower limit of range of records to be returned for paging results. Starts at 0. If number is -1, then the range limit will not be included.
     * @param maxResults The maximum number of results to be returned starting at firstResult. Ignored if firstResult is -1.
     */
    public Collection findByExample(final String queryName,
            final String[] parms, final Object exampleObject,
            final int firstResult, final int maxResults) {

        final String sqlRestriction = this.getSqlRestriction(queryName, parms);

        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);
                if (sqlRestriction != null) {
                    criteria.add(Restrictions.sqlRestriction(sqlRestriction));

                }
                if (firstResult >= 0) {
                    criteria.setFirstResult(firstResult);
                    criteria.setMaxResults(maxResults);
                }

                return criteria.list();
            }
        });
    }

    public Collection findByExample(final String queryName,
            final String[] parms, final Object exampleObject, final String[] excludeProperties,
            final int firstResult, final int maxResults) {

        final String sqlRestriction = this.getSqlRestriction(queryName, parms);

        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject, excludeProperties);
                if (sqlRestriction != null) {
                    criteria.add(Restrictions.sqlRestriction(sqlRestriction));

                }
                if (firstResult >= 0) {
                    criteria.setFirstResult(firstResult);
                    criteria.setMaxResults(maxResults);
                }

                return criteria.list();
            }
        });
    }

    public Collection findByExample(final String queryName,
            final String[] parms, final Object exampleObject, final boolean deactivechk) {
        final String sqlRestriction = this.getSqlRestriction(queryName, parms);

        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final Criteria criteria = UtilDAO.this.buildCriteria(session, exampleObject);
                if (sqlRestriction != null) {
                    criteria.add(Restrictions.sqlRestriction(sqlRestriction));

                }
                if (deactivechk) {
                    criteria.add(Restrictions.sqlRestriction(getSqlRestriction("deactivechk", new String[]{})));
                }
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                return criteria.list();
            }
        });
    }

    /**
     * Convenience method to count objects with sqlRestriction that has no parameters
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public int countObjects(final String queryName, final Object exampleObject, final boolean deactivechk) {

        return this.countObjects(queryName, new String[0], exampleObject, deactivechk);
    }

    /**
     * Convenience method to count objects with sqlRestriction that has one parameter
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public int countObjects(final String queryName, final String parm,
            final Object exampleObject, final boolean deactivechk) {

        return this.countObjects(queryName, new String[]{parm},
                exampleObject, deactivechk);
    }

    /**
     * This method returns the NEXTVAL from the Oracle sequence named by the input parameter.
     *
     * @param String sequenceName
     * @return Long
     */
    public Long sequenceNextValue(final String sequenceName) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final String sqlQuery = "select " + sequenceName + ".NEXTVAL as seqnum from dual";
                return session.createSQLQuery(sqlQuery).addScalar("seqnum",
                        Hibernate.LONG).uniqueResult();
            }
        });
    }

    /**
     * This method returns the CURVAL from the Oracle sequence named by the input parameter. if this is called without a call to sequenceNextValue, ORACLE returns an error: "ORA-08002: sequence
     * <sequenceName>.CURRVAL is not yet defined in this session"
     *
     * @param String sequenceName
     * @return Long
     */
    public Long sequenceCurrentValue(final String sequenceName) {

        return (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session)
                    throws HibernateException, SQLException {

                final String sqlQuery = "select " + sequenceName + ".CURRVAL as seqnum from dual";
                return session.createSQLQuery(sqlQuery).addScalar("seqnum",
                        Hibernate.LONG).uniqueResult();
            }
        });
    }

    /**
     * This method synchronizes Hibernate's in-memory data with that in the database for the current session. This is not a COMMIT. However this "flush" is done automatically by Hibernate when a
     * transaction commits. It is important to note that this method may need to be used in cases where the data is expected to be available in the database before a stored procedure is called.
     *
     * @param
     * @return
     */
    public void synchronizeWithDatabase() {

        getHibernateTemplate().flush();
    }

    /**
     * Get the SQL Restriction string for the given queryName and also replace positional parameters in the sql with values from the given array of string parameters.
     *
     * @param sqlRestrictionName
     * @param parms
     * @return
     * @throws IllegalStateException If properties file not found or if the queryName is not found in the property file.
     */
    private String getSqlRestriction(final String sqlRestrictionName,
            final String[] parms) throws IllegalStateException {

        /**
         * Tmp string declaired to avoid Questionable assignments
         */
        String tempParam = "";
        if (this.sqlRestrictions == null) {
            throw new IllegalStateException(
                    "sqlRestrictions property file not found.");
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(sqlRestrictionName)) {
            throw new IllegalStateException("sqlRestrictionName is null.");
        }
        String queryString = ((Properties) sqlRestrictions).getProperty(sqlRestrictionName);
        if (queryString == null) {
            throw new IllegalStateException(
                    "No query string for sqlRestrictionName of: " + sqlRestrictionName);
        }

        // Verify that number of parameters received match the
        // number required number of parameters
        int parmCount = 0;
        int nextPlaceholderIndex = queryString.indexOf('?');
        while (nextPlaceholderIndex != -1) {
            parmCount++;
            nextPlaceholderIndex = queryString.indexOf('?',
                    nextPlaceholderIndex + 1);
        }
        if (parms.length != parmCount) {
            throw new IllegalStateException("sqlRestriction named " + sqlRestrictionName + " requires " + parmCount + " parameters. But, " + parms.length + " were provided.");
        }
        for (int i = 0; i < parms.length; i++) {
            if (parms[i] == null) {
                throw new IllegalStateException(
                        "sqlRestriction parameter at index " + i + " is null for sqlRestriction named:" + sqlRestrictionName);
            }
        }

        // Replace question marks with parameters
        // The lop was modified to avoid audit rule - Questionable assignments
        for (int i = 0; i < parms.length; i++) {
            //tempParam = OracleUtils.escapeQuotes(parms[i]);
            tempParam = parms[i];
            // Escape $ so that $'s don't cause regular expression error in
            // replaceFirst call
            tempParam = tempParam.replace("$", "\\$");
            queryString = queryString.replaceFirst("\\?", tempParam);
        }
        return queryString;
    }

    public Date getDate() {
        Session session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
        SQLQuery query = null;
        try {
            query = session.createSQLQuery("select sysdate as mydate from dual");
            query.addScalar("mydate", Hibernate.TIMESTAMP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (Date) query.uniqueResult();
    }

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProgramDuration(String instituteid, String academicyear, String programid, String branchid) {
        String retString = "";
        List<Object[]> list = null;
        String stypattern = "";
        int styduration = 0;
        String QueryString = "select pmsty.stypattern,pmsty.startsty,pmsty.endsty from ProgramMaxSty pmsty "
                + " where pmsty.id.instituteid = '" + instituteid + "'"
                + " and pmsty.id.academicyear = '" + academicyear + "'"
                + " and pmsty.id.programid = '" + programid + "'"
                + " and pmsty.id.branchid = '" + branchid + "'"
                + " and coalesce(pmsty.deactive,'N') = 'N'";
        try {
            list = (List<Object[]>) getHibernateTemplate().find(QueryString);
            if (list != null && list.size() > 0) {
                stypattern = list.get(0)[0].toString().trim();

                styduration = (Integer.parseInt(list.get(0)[2].toString().trim()) - Integer.parseInt(list.get(0)[1].toString().trim())) + 1;
                if (stypattern.equalsIgnoreCase("S")) {
                    retString = String.valueOf(styduration / 2);
                } else if (stypattern.equalsIgnoreCase("T")) {
                    retString = String.valueOf(styduration / 3);
                } else if (stypattern.equalsIgnoreCase("Y")) {
                    retString = String.valueOf(styduration);
                }
            }

        } catch (NumberFormatException ne) {
            System.err.println("Numberformatexeption with startsty and endsty in utildao.getProgramDuration() method while proccessing");
            ne.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    public String getSqlRestrictionString(String name, String[] param) {
        return this.getSqlRestriction(name, param);
    }

    public byte[] getImageByteArray(File file) throws IOException {

        byte[] imageByte = null;
        BufferedImage originalImage = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        baos.flush();
        imageByte = baos.toByteArray();
        baos.close();
        return imageByte;
    }

    public List getDataforLogMantainanceReport(String startDate, String endDate, String insValue, String updValue, String delValue, String operation_by, String studentid, String radioValue1) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select activity,ontable,oncolumn,oldvalue,newvalue,remarks,logdate,ipaddress, logbymembername, logbymembertype, logstatus "
                + " from LOG_CLXAUDITTRAIL#MASTER LGD");
        if (insValue.equals("1") && updValue.equals("0") && delValue.equals("0")) {
            sb.append(" where  LGD.ACTIVITY='" + "SAVE" + "'");
        } else if (insValue.equals("0") && updValue.equals("1") && delValue.equals("0")) {
            sb.append(" where  LGD.ACTIVITY='" + "UPDATE" + "'");
        } else if (insValue.equals("0") && updValue.equals("0") && delValue.equals("1")) {
            sb.append(" where  LGD.ACTIVITY='" + "DELETE" + "'");
        } else if (insValue.equals("1") && updValue.equals("1") && delValue.equals("0")) {
            sb.append(" where  LGD.ACTIVITY in ('" + "SAVE" + "','" + "UPDATE" + "')");
        } else if (insValue.equals("1") && updValue.equals("0") && delValue.equals("1")) {
            sb.append(" where  LGD.ACTIVITY in ('" + "SAVE" + "','" + "DELETE" + "')");
        } else if (insValue.equals("0") && updValue.equals("1") && delValue.equals("1")) {
            sb.append(" where  LGD.ACTIVITY in ('" + "DELETE" + "','" + "UPDATE" + "')");
        } else if (insValue.equals("1") && updValue.equals("1") && delValue.equals("1")) {
            sb.append(" where  LGD.ACTIVITY in ('" + "SAVE" + "','" + "UPDATE" + "','" + "DELETE" + "')");
        }
        if (!operation_by.equals("1")) {
            if (operation_by.equals("2")) {
                sb.append(" and  LGD.logbymemberid='" + studentid + "'");
            } else if (operation_by.equals("3")) {
                sb.append(" and  LGD.logbymemberid='" + studentid + "'");
            } else if (operation_by.equals("4")) {
                sb.append(" and  LGD.logbymemberid='" + studentid + "'");
            }
        }
        if (radioValue1.equals("0")) {
            sb.append(" and  LGD.logstatus='" + "S" + "'");
        } else if (radioValue1.equals("1")) {
            sb.append(" and  LGD.logstatus='" + "U" + "'");
        } else if (radioValue1.equals("2")) {
            sb.append(" and  LGD.logstatus in ('" + "S" + "','" + "U" + "')");
        }

        try {
            session = getHibernateSession();
            list = session.createSQLQuery(sb.toString()).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
            session.close();
        }
        return list;
    }

    /*@private String instituteid, private String parameterid,private String notificationfor
     *
     *  getNotificationMode()
     *  SMS/ E Mail and Portal Message sending feauture
    000 = SMS No, Email No and  Portal Message No
    100 = SMS Yes, Email No and  Portal Message No
    110 = SMS Yes, Email Yes and  Portal Message No
    111 = SMS Yes, Email Yes and  Portal Message Yes
     *
     *INSTITUTEID, PARAMETERID, PARAMETERDESC, FLAGFORSTUDENTS, FLAGFORPARENTS, FLAGFORTEACHERS, FLAGFORADVISORS, FLAGFORHOD, FLAGFORDEAM, FLAGFORREGISTRAR, FLAGFORVC_CHAIRPERSON, FLAGFORIRPADMIN, FLAGFOROTHERS, DEACTIVE
     *instituteid, parameterid, parameterdesc, flagforstudents, flagforparents, flagforteachers, flagforadvisors, flagforhod, flagfordeam, flagforregistrar, flagforvc_chairperson, flagforirpadmin, flagforothers, deactive
     */
    public String getNotificationMode(String instituteid, String parameterid, String notificationfor) {
        String retString = "";
        List<Object[]> list = null;

        String QueryString = "select a.flagforstudents, a.flagforparents, "
                + "a.flagforteachers, a.flagforadvisors, a.flagforhod, a.flagfordeam, a.flagforregistrar, a.flagforvcChairperson, a.flagforirpadmin, a.flagforothers "
                + " from Notify_Parameters a "
                + " where a.id.instituteid = '" + instituteid + "'"
                + " and a.id.parameterid = '" + parameterid + "'"
                + " and coalesce(a.deactive,'N') = 'N'";
        try {
            list = (List<Object[]>) getHibernateTemplate().find(QueryString);

            if (list != null && list.size() > 0) {
                if (notificationfor.equalsIgnoreCase("S"))//flagforstudents
                {
                    retString = list.get(0)[0].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("P"))//flagforparents
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("T"))// flagforteachers
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("A"))// flagforadvisors
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("H"))//flagforhod
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("D"))//flagfordeam
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("R"))//flagforregistrar
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("V"))//flagforvc_chairperson,
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("A"))//flagforirpadmin
                {
                    retString = list.get(0)[1].toString().trim();
                } else if (notificationfor.equalsIgnoreCase("O"))//flagforothers
                {
                    retString = list.get(0)[1].toString().trim();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retString;
    }

    public List getNamesOfRightsCouns() {
        List list = null;
        String strqry = " select mrm from Sct_ModuleRightsMaster mrm "
                + " where exists (select mrm.rightsid from Sct_IrpModules irpm, Sct_ModuleRightsTagging mrt where mrm.rightsid=mrt.id.rightsid "
                + " and irpm.moduleid=mrt.id.moduleid "
                + " and coalesce(irpm.counsellingmodule,'N')='Y' "
                + " and coalesce(irpm.deactive,'N')='N') "
                + " and coalesce(mrm.deactive,'N')='N' ";
        try {
            list = getHibernateTemplate().find(strqry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List findNamesOfRights() {
        List list = null;
        String qry = "select a from Sct_ModuleRightsMaster a "
                + "where a.rightsid in (select ss.id.rightsid from Sct_ModuleRightsTagging ss "
                + "where ss.id.moduleid not in (select dd.moduleid from Sct_IrpModules dd where coalesce(dd.counsellingmodule,'N')='Y')) ";
        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSCT_RoleMasterList() {
        List list = null;
        String qry = "select a from Sct_RoleMaster a "
                + "where coalesce(a.deactive,'N')='N' ";
        try {
            list = getHibernateTemplate().find(qry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void downloadexcel_LogReport(ServletOutputStream outputStream, String headername, String executequery, String sheetname, String institutename, List list) {
        getDaoFactory();
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet(sheetname);
        HSSFCellStyle style = hwb.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        sheet.createFreezePane(0, 1);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        HSSFFont font = hwb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setBorderRight((short) 1);
        int countrow = 0;
        HSSFCell cell = null;
        //----------------------------------------------------------------------------------------------------------------------------------------------
        String[] header4 = {institutename};
        sheet.addMergedRegion(new org.apache.poi.hssf.util.Region(countrow, (short) 0, countrow, (short) (10)));
        HSSFRow header4_row = sheet.createRow((short) countrow++);
        HSSFRow row4 = null;
        int a4 = 0;
        cell = header4_row.createCell((short) a4);
        cell.setCellStyle(style);
        cell.setCellValue(new HSSFRichTextString(header4[a4]));
        sheet.autoSizeColumn((short) a4);
        //-------------------------------------------------------------------------------------------------------------------------------------------------
        String[] header = headername.split("@");
        HSSFRow header_row = sheet.createRow((short) countrow);
        HSSFRow row = null;
        try {
            for (int i = 0; i < header.length; i++) {
                cell = header_row.createCell((short) i);
                cell.setCellStyle(style);
                cell.setCellValue(new HSSFRichTextString(header[i]));
                sheet.autoSizeColumn((short) i);
            }
            countrow++;
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Object apdata[] = (Object[]) list.get(i);
                    row = sheet.createRow((short) countrow);
                    //row.createCell((short) 0).setCellValue(index);//S.No.
                    for (int k = 0; k < apdata.length; k++) {
                        row.createCell((short) (k)).setCellValue(new HSSFRichTextString(apdata[k] != null ? apdata[k].toString() : ""));
                        if (i <= 20) {
                            sheet.autoSizeColumn((short) ((short) k));//
                        }
                    }
                    countrow++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            hwb.write(outputStream);
        } catch (Exception e) {
            System.out.println("Error Occurred In : " + e);
        } finally {
            hwb = null;
            sheet = null;
            font = null;
            cell = null;
            style = null;
            countrow = 0;
            row = null;

        }
    }

    public List getStudentDetails(String enrollmentno) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select sm.studentid,sm.name from StudentMaster  sm where sm.enrollmentno= :enrollmentno ");
        try {
            session = (Session) getHibernateSession();
            list = session.createQuery(sb.toString()).
                    setParameter("enrollmentno", enrollmentno).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List getStaffDetails(String empcode) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select vs.employeeid,vs.employeename from V_Staff vs where vs.employeecode= :empcode ");
        try {
            session = (Session) getHibernateSession();
            list = session.createQuery(sb.toString()).
                    setParameter("empcode", empcode).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public static String dateConverter(Date date, String pattern) {
        if (pattern.equals("") || pattern.equals(null)) {
            pattern = "MM/dd/yyyy HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public List dateConverterList(List<Object[]> gridlist, List indexlist, String pattern) {
        List finallist = new ArrayList();
        if (indexlist.isEmpty()) {
            return gridlist;
        }
        for (int i = 0; i < gridlist.size(); i++) {
            Object[] obj = gridlist.get(i);
            for (int j = 0; j < indexlist.size(); j++) {
                if (obj[(Integer.parseInt(indexlist.get(j).toString()))] != null) {
                    obj[Integer.parseInt(indexlist.get(j).toString())] = dateConverter((Date) obj[(Integer.parseInt(indexlist.get(j).toString()))], pattern);
                } else {
                    obj[Integer.parseInt(indexlist.get(j).toString())] = "--";
                }
            }
            finallist.add(obj);
        }
        return finallist;
    }

    public List listAgg(List<Object[]> mainlist, List combineindexlist, String combinewith) {
        List<Object[]> rtnlist = new ArrayList<Object[]>();
        try {
            if (mainlist != null) {
                Map map = new HashMap();
                for (int i = 0; i < mainlist.size(); i++) {
                    Object[] obj = mainlist.get(i);
                    Object[] mainobj = new Object[obj.length];
                    String content = "";
                    String key = "";
                    for (int j = 0; j < obj.length; j++) {
                        if (!combineindexlist.contains(j)) {
                            content += obj[j];
                            key += obj[j];
                        }
                    }
                    if (map.containsKey(key)) {
                        for (int k = 0; k < rtnlist.size(); k++) {
                            Object[] obj1 = rtnlist.get(k);
                            String content1 = "";
                            for (int l = 0; l < obj1.length; l++) {
                                if (!combineindexlist.contains(l)) {
                                    content1 += obj1[l];
                                }
                            }
                            if (content1.equals(content)) {
                                for (int m = 0; m < obj.length; m++) {
                                    if (combineindexlist.contains(m)) {
                                        mainobj[m] = obj[m].toString() + combinewith + obj1[m].toString();
                                    } else {
                                        mainobj[m] = obj[m].toString();
                                    }
                                }
                                rtnlist.remove(k);
                            }
                        }
                        rtnlist.add(mainobj);
                    } else {
                        map.put(content, content);
                        rtnlist.add(obj);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rtnlist;
    }
}
