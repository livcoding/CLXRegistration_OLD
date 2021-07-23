package com.jilit.irp.persistence.dao;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;

import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface UtilIDAO extends IDAO {

    public HibernateTemplate getHibernateTemplate();

    public List<?> findSimpleData(final String queryName);

    public List<?> findSimpleData(final String queryName, final Object parmValue);

    /**
     * Execute a named query, binding a number of values to "?"
     *
     * @param queryName
     * @param parmValues
     * @return
     */
    public List<?> findSimpleData(final String queryName, final Object[] parmValues);

    /**
     * Execute a named query, binding a number of values to ":" named parameters in the query string.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List<?> findSimpleData(final String queryName,
            final Map<?, ?> parametersLinkedHashMap);

    /**
     * Convenience method that executes a named query without any parameters and returns a List of Strings.
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName);

    /**
     * Convenience method that executes a named query, binding one value to a "?" and returns a List of Strings.
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Object parmValue);

    /**
     * Convenience method that executes a named query, binding a number of values to "?" and returns a List of Strings.
     *
     * @param queryName
     * @param parmValues
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Object[] parmValues);

    /**
     * Convenience method that executes a named query, binding a number of values to ":" named parameters in the query string and returns a List of Strings.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List<String> findSimpleDataAsListOfStrings(final String queryName,
            final Map<?, ?> parametersLinkedHashMap);

    /**
     * Convenience method that executes a named query without any parameters. and returns a List of Objects.
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName);

    /**
     * Convenience method that executes a named query, binding one value to a "?" and returns a List of Objects.
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Object parmValue);

    /**
     * Convenience method that executes a named query, binding a number of values to "?" and returns a List of Objects.
     *
     * @param queryName
     * @param parmValues
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Object[] parmValues);

    /**
     * Convenience method that executes a named query, binding a number of values to ":" named parameters in the query string and returns a List of Objects.
     *
     * @param query name
     * @param parametersLinkedHashMap Name/value pair for each parameter
     */
    public List<Object> findSimpleDataAsListOfObjects(final String queryName,
            final Map<?, ?> parametersLinkedHashMap);

    public List findSimpleDataAsListOfValueBean(final String queryName, final Object parmValue);

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
    public void deleteAll(final Collection<?> objectsToDelete);

    /**
     * Sets the sql restriction property file.
     *
     * @param sqlRestrictionResource the sql restriction property file
     */
    public void setSqlRestrictionPropertyFile(
            final Resource sqlRestrictionResource);

    public int countObjects(final String queryName, final String[] parms,
            final Object exampleObject, final boolean deactivechk);

    public Collection<?> findByExample(final Object exampleObject,
            final int firstResult, final int maxResults);

    public int countObjects(final String queryName,
            final Map<String, String> params);

    /**
     * This method may not be robust in the sense if I had two variables, one a substring of the other - say ":docNumber" and ":docNumberType", then this method's "replace" may mangle the sql
     * String..depending on which variable came first in the iterative loop.
     */
    public void executeDDLUpdate(final String queryName,
            final Map<String, String> params);

    /**
     * Find by example.
     *
     * @param exampleObject the example object
     *
     * @return the collection
     */
    public Collection<?> findByExample(final Object exampleObject);

    public int countObjects(final Object exampleObject);

    /**
     * Convenience method to findByExample with sqlRestriction that has no parameters
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public Collection<?> findByExample(final String queryName,
            final Object exampleObject, final int firstResult,
            final int maxResults);

    /**
     * Convenience method to findByExample with sqlRestriction that has one parameter
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public Collection<?> findByExample(final String queryName, final String parm,
            final Object exampleObject, final int firstResult,
            final int maxResults);

    /**
     * @param queryName name of query, or more specifically, SQL Restriction to be loaded from the sqlRestrictions properties file
     * @param parms List of parameters to be used in positional parameters in the sql Restriction string
     * @param exampleObject Example business object with only fields set that should be used in search criteria for query-by-example operation
     * @param firstResult Lower limit of range of records to be returned for paging results. Starts at 0. If number is -1, then the range limit will not be included.
     * @param maxResults The maximum number of results to be returned starting at firstResult. Ignored if firstResult is -1.
     */
    public Collection<?> findByExample(final String queryName,
            final String[] parms, final Object exampleObject,
            final int firstResult, final int maxResults);

    public Collection findByExample(final String queryName,
            final String[] parms, final Object exampleObject, final boolean deactivechk);

    public Collection findByExample(final String queryName, final String parm,
            final Object exampleObject, final String[] excludeProperties, final int firstResult,
            final int maxResults);

    /**
     * Convenience method to count objects with sqlRestriction that has no parameters
     *
     * @param queryName
     * @param exampleObject
     * @param firstResult
     * @param maxResults
     * @return
     */
    public int countObjects(final String queryName, final Object exampleObject, final boolean deactivechk);

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
            final Object exampleObject, final boolean deactivechk);

    /**
     * This method returns the NEXTVAL from the Oracle sequence named by the input parameter.
     *
     * @param String sequenceName
     * @return Long
     */
    public Long sequenceNextValue(final String sequenceName);

    /**
     * This method returns the CURVAL from the Oracle sequence named by the input parameter. if this is called without a call to sequenceNextValue, ORACLE returns an error: "ORA-08002: sequence
     * <sequenceName>.CURRVAL is not yet defined in this session"
     *
     * @param String sequenceName
     * @return Long
     */
    public Long sequenceCurrentValue(final String sequenceName);

    /**
     * This method synchronizes Hibernate's in-memory data with that in the database for the current session. This is not a COMMIT. However this "flush" is done automatically by Hibernate when a
     * transaction commits. It is important to note that this method may need to be used in cases where the data is expected to be available in the database before a stored procedure is called.
     *
     * @param
     * @return
     */
    public void synchronizeWithDatabase();
    //  public Criterion getCriteria(String fname,Object fvalue,String logic,String type);

    public List findNamedQuery(final String queryName,
            final Map<String, String> params);

    public Date getDate();

    public String getProgramDuration(String instituteid, String academicyear, String programid, String branchid);

    public String getSqlRestrictionString(String name, String[] param);

    public byte[] getImageByteArray(File file) throws IOException;

    public String getNotificationMode(String instituteid, String parameterid, String notificationfor);

    public List getNamesOfRightsCouns();

    public List findNamesOfRights();

    public List getSCT_RoleMasterList();

    public List getDataforLogMantainanceReport(String startDate, String endDate, String insValue, String updValue, String delValue, String operation_by, String studentid, String radioValue1);

    public List getStudentDetails(String enrollmentno);

    public List getStaffDetails(String empcode);

    public void downloadexcel_LogReport(ServletOutputStream outputStream, String headername, String executequery, String sheetname, String institutename, List list);

    public List dateConverterList(List<Object[]> gridlist, List indexlist, String pattern);

    public List listAgg(List<Object[]> mainlist, List combineindexlist, String combinewith);
}
