/*   extends IDAO
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.V_ProgramSubjectTaggingIDAO;
import com.jilit.irp.persistence.dto.V_ProgramSubjectTagging;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author akshya.gaur
 */
public class V_ProgramSubjectTaggingDAO extends HibernateDAO implements V_ProgramSubjectTaggingIDAO {

    private static final Log log = LogFactory.getLog(SubjectComponentDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentComponent records via Hibernate from the database");
        return this.find("from SubjectComponent as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(V_ProgramSubjectTagging.class, id);
    }

    public Collection<?> getDataForLTPPassingMarks(final String instituteid, final String registrationid, final String subjectid, final String facultyid, final String coordinatorid) {

        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(V_ProgramSubjectTagging.class, "master");
                return criteria.list();
            }
        });
        return list;
    }

    public Collection<?> getSubjectList(final String instituteid, final String registrationid, final String departmentid, final String academicyear, final String programid, final String sectionid, final String stynumber) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(V_ProgramSubjectTagging.class, "master").add(Restrictions.eq("master.instituteid", instituteid)).add(Restrictions.eq("master.registrationid", registrationid)).add(Restrictions.eq("master.departmentid", departmentid));
                //.add(Restrictions.eq("master.programid", programid))
                //.add(Restrictions.eq("master.sectionid", sectionid));
                if (!"ALL".equals(programid)) {
                    if (programid.contains(",")) {
                        criteria.add(Restrictions.in("master.programid", programid.split(",")));
                    } else {
                        criteria.add(Restrictions.eq("master.programid", programid.replace("'", "").trim()));
                    }
                }
                if (!"ALL".equals(sectionid)) {
                    criteria.add(Restrictions.eq("master.sectionid", sectionid));
                }
                if (!"ALL".equals(academicyear)) {
                    criteria.add(Restrictions.eq("master.academicyear", academicyear));
                }
                if (!"ALL".equals(stynumber)) {
                    criteria.add(Restrictions.eq("master.stynumber", new Short(stynumber)));
                }
                criteria.setProjection(Projections.projectionList().
                        add(Projections.property("master.subjectcode").as("subjectcode")).
                        add(Projections.property("master.subjectid").as("subjectid")).
                        add(Projections.property("master.subjectdesc").as("subjectdesc")).
                        add(Projections.property("master.instituteid").as("instituteid")).
                        add(Projections.property("master.basketid").as("basketid")).
                        add(Projections.property("master.basketcode").as("basketcode")).
                        add(Projections.property("master.basketdesc").as("basketdesc")).
                        add(Projections.property("master.subjectcomponentcode").as("subjectcomponentcode")).
                        add(Projections.property("master.subjectcomponentdesc").as("subjectcomponentdesc")).
                        add(Projections.property("master.noofhours").as("noofhours")).
                        add(Projections.property("master.noofclassinaweek").as("noofclassinaweek")).
                        add(Projections.property("master.subjectcomponentid").as("subjectcomponentid")));

                criteria.addOrder(Order.asc("master.subjectid"));
                criteria.addOrder(Order.asc("master.basketid"));

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
    }

    public List SubjectswithFilterpoints(final String instituteid, final String registrationid, final String academicYear, final String semester, final String Sectionid, final String ProgramCode, final String subjecttypeid) {

        String qryString = "select distinct subjectcode, subjectdesc,sum(calccpmarks),subjectid,basketid,subjectcomponentid ,subjectcomponentcode,subjectid"
                + " from V_ProgramSubjectTagging  "
                + " where instituteid = '" + instituteid + "' "
                + " and registrationid = '" + registrationid + "' "
                + " and academicyear = '" + academicYear + "' "
                + " and stynumber = '" + semester + "' "
                + " and sectionid = '" + Sectionid + "' "
                + " and programcode = '" + ProgramCode + "' "
                + " and subjecttypeid = '" + subjecttypeid + "' "
                + " group by subjectcode,subjectdesc,subjectid,basketid,subjectcomponentid,subjectcomponentcode,subjectid"
                + " order by subjectcode";
        return getHibernateTemplate().find(qryString);

    }

    public List SubjectswithFilterpointsForInstituteElective(final String instituteid, final String registrationid, final String academicYear, final String semester, final String Sectioncode, final String ProgramCode, final String subjecttypeid) {

        String qryString = "select distinct subjectcode, subjectdesc,calccpmarks ,subjectid,basketid,subjectcomponentid,subjectcomponentcode,subjectid,subjectareacode"
                + " from V_ProgramSubjectTagging  "
                + " where instituteid = '" + instituteid + "' "
                + " and registrationid = '" + registrationid + "' "
                + " and academicyear = '" + academicYear + "' "
                + " and stynumber = '" + semester + "' "
                + " and sectioncode = '" + Sectioncode + "' "
                + " and programcode = '" + ProgramCode + "' "
                + " and subjecttypeid = '" + subjecttypeid + "' "
                + " and coalesce(subjectrunning,'N') = 'Y'"
                + " order by basketid,subjectcode";
        return getHibernateTemplate().find(qryString);

    }

    public List getV_ProgramSubjectTaggingData(final String instituteid) {
        String qryString = "select distinct registrationcode,registrationdesc,registrationid"
                + " from V_ProgramSubjectTagging"
                + " where instituteid = '" + instituteid + "'"
                + " order by registrationcode desc ";
        return getHibernateTemplate().find(qryString);

    }

    public List checkIfRegCodeExists_V_ProgramSubjectTagging(final String instituteid, final String regcode) {
        String qryString = "select distinct registrationcode,registrationdesc,registrationid"
                + " from V_ProgramSubjectTagging"
                + " where instituteid = '" + instituteid + "' "
                + " and registrationcode = '" + regcode + "' ";
        return getHibernateTemplate().find(qryString);

    }

    public List getV_ProgramSubjectTypeData(final String instituteid, final String registrationid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct subjecttypeid, subjecttype, subjecttypedesc"
                + " from V_ProgramSubjectTagging"
                + " where registrationid = :registrationid "
                + " and instituteid = :instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("registrationid", registrationid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List checkIfSubjectTypeExists_V_ProgramSubjectTagging(final String instituteid, final String registrationid, final String subjecttype) {
        String qryString = "select distinct  subjecttype ,subjecttypedesc,subjecttypeid"
                + " from V_ProgramSubjectTagging"
                + " where registrationid ='" + registrationid + "'"
                + " and subjecttype = '" + subjecttype + "'"
                + " and instituteid = '" + instituteid + "' ";
        return getHibernateTemplate().find(qryString);
    }

    public List getSubjectTypeReport(final String registrationid, final String subjecttypeid, final String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select e.subjectcode, e.subjectdesc, (select count(*) from StudentSubjectChoiceMaster  b "
                + " where a.id.instituteid = b.id.instituteid  and e.id.subjectid = b.id.subjectid "
                + " and a.id.registrationid = b.id.registrationid and a.subjectid=b.id.subjectid "
                + " and b.subjectrunning = 'Y') as alloted, sum(a.noofstudents) as totalseats, d.subjecttypedesc "
                + " from ProgramSubjectTagging a, SubjectMaster e, SubjectTypeMaster d "
                + " where a.id.instituteid = e.id.instituteid  and e.id.subjectid = a.subjectid "
                + " and a.id.registrationid = :registrationid and d.id.subjecttypeid = :subjecttypeid "
                + " and a.id.instituteid= :instituteid and a.subjectrunning = 'Y' "
                + " and exists (select 1 from  BasketMaster c where a.id.instituteid = c.id.instituteid "
                + " and c.subjecttypeid = d.id.subjecttypeid  and a.basketid = c.id.basketid) "
                + " and d.subjecttypedesc not in ('C' ) "
                + " group by  e.subjectcode, e.subjectdesc, d.subjecttypedesc, a.id.instituteid, e.id.subjectid, a.id.registrationid, a.subjectid "
                + " order by  e.subjectcode, e.subjectdesc ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("registrationid", registrationid).
                    setParameter("subjecttypeid", subjecttypeid).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;

    }

}
