/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StyTypeIDAO;
import com.jilit.irp.persistence.dto.StyType;
import com.jilit.irp.persistence.dto.StyTypeId;
import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.util.JIRPSession;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author v.kumar
 */
public class StyTypeDAO extends HibernateDAO implements StyTypeIDAO {

    @Autowired
    JIRPSession jirpsession;

    private static final Log log = LogFactory.getLog(StyTypeDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StyType records via Hibernate from the database");
        return this.find("from StyType as tname");
    }

    public Collection<?> findAll(String instituteid) {
        log.info("Retrieving all StyType records via Hibernate from the database");
        return this.find("from StyType as tname where tname.id.instituteid = ?  order by seqid desc  ", new Object[]{instituteid});
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StyType.class, id);
    }

    public Collection<?> getStyType(String stytype) {
        return getHibernateTemplate().find("from StyType where stytype = ? ", stytype);
    }

    public List getStyTypeGridData(String instituteid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id.instituteid, st.id.stytypeid,st.stytype,st.stytypedesc,st.seqid,coalesce(st.etod,'N') as etod,coalesce(st.deactive,'N') as deactive from StyType st where st.id.instituteid=:instituteid and coalesce(st.deactive, 'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStyTypeEditData(String instituteid, String stytypeid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id.instituteid, st.id.stytypeid,st.stytype,st.stytypedesc,st.seqid,coalesce(st.etod,'N') as etod,coalesce(st.deactive,'N') as deactive from StyType st where st.id.instituteid=:instituteid and st.id.stytypeid=:stytypeid and coalesce(st.deactive, 'N')='N' ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("stytypeid", stytypeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getStyTypeData(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select st.id.stytypeid,st.stytype,st.stytypedesc from StyType st where st.id.instituteid=:instituteid and coalesce(st.deactive, 'N')='N' ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
            session.close();
        }
        return list;
    }

    public int checkIfChildExist(final StyTypeId id) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StyType master = (StyType) session.get(StyType.class, id);
                int i1 = 0; //((Integer) session.createFilter(master.getStudenthosteldetails(), "select count(*)").list().get(0)).intValue();
                int i2 = Integer.parseInt(session.createFilter(master.getPrsubjectfinalizationcriterias(), "select count(*)").list().get(0).toString());
                int i3 = Integer.parseInt(session.createFilter(master.getStudentregistrations(), "select count(*)").list().get(0).toString());
//                int i4 = ((Integer) session.createFilter(master.getFeecolletioneventgroupses(), "select count(*)").list().get(0)).intValue();
//                int i5 = ((Integer) session.createFilter(master.getFeefinewaivers(), "select count(*)").list().get(0)).intValue();
                int i6 = Integer.parseInt(session.createFilter(master.getStudentregistration_infos(), "select count(*)").list().get(0).toString());
                int i7 = Integer.parseInt(session.createFilter(master.getStudentsubjectchoicemasters(), "select count(*)").list().get(0).toString());
//                int i8 =0;// ((Integer) session.createFilter(master.getTeachingloaddistributions(), "select count(*)").list().get(0)).intValue();
                int i9 = Integer.parseInt(session.createFilter(master.getFacultysubjecttaggings(), "select count(*)").list().get(0).toString());
                int i10 = Integer.parseInt(session.createFilter(master.getStudentpreviousattendences(), "select count(*)").list().get(0).toString());
//                int i11 = ((Integer) session.createFilter(master.getFeetransactions(), "select count(*)").list().get(0)).intValue();
//                int i12 = 0;//((Integer) session.createFilter(master.getFeetransactionDetails(), "select count(*)").list().get(0)).intValue();
//                int i13 = ((Integer) session.createFilter(master.getFeetransfers1(), "select count(*)").list().get(0)).intValue();
//                int i14 = ((Integer) session.createFilter(master.getFeetransfers2(), "select count(*)").list().get(0)).intValue();
//                int i15 = ((Integer) session.createFilter(master.getFeefineruleheadwises(), "select count(*)").list().get(0)).intValue();
//                int i16 = ((Integer) session.createFilter(master.getFeefinerulegenerals(), "select count(*)").list().get(0)).intValue();
//                int i17 = ((Integer) session.createFilter(master.getFeetransferheadfroms(), "select count(*)").list().get(0)).intValue();
//                int i18 = ((Integer) session.createFilter(master.getFeetransferheadtos(), "select count(*)").list().get(0)).intValue();
//                int i19 = ((Integer) session.createFilter(master.getFeestructures(), "select count(*)").list().get(0)).intValue();
//                int i20 = ((Integer) session.createFilter(master.getFeestructureindividuals(), "select count(*)").list().get(0)).intValue();
//                int i21 = ((Integer) session.createFilter(master.getDs_datesheetsubjectWises(), "select count(*)").list().get(0)).intValue();
//                int i22 = ((Integer) session.createFilter(master.getTemp_Studenthosteldetails(), "select count(*)").list().get(0)).intValue();
//                int i23 = ((Integer) session.createFilter(master.getStudentfeediscounts(), "select count(*)").list().get(0)).intValue();
//                int i24 = ((Integer) session.createFilter(master.getStudentspecialapprovals(), "select count(*)").list().get(0)).intValue();
//                int i25 = ((Integer) session.createFilter(master.getFeecollectionevent_grpdtls(), "select count(*)").list().get(0)).intValue();
//                int i26 = ((Integer) session.createFilter(master.getHoldstudentresults(), "select count(*)").list().get(0)).intValue();
//                int i27 = ((Integer) session.createFilter(master.getResultpublishonwebs(), "select count(*)").list().get(0)).intValue();
//                int i28 = ((Integer) session.createFilter(master.getDatesheetparameterses(), "select count(*)").list().get(0)).intValue();
//                int i29 = ((Integer) session.createFilter(master.getDatesheetsubjectwises(), "select count(*)").list().get(0)).intValue();
//                int i30 = ((Integer) session.createFilter(master.getDatesheetsubjectwisedetails(), "select count(*)").list().get(0)).intValue();
//                int i31 = ((Integer) session.createFilter(master.getDraft_datesheetdatas(), "select count(*)").list().get(0)).intValue();
//                int i32 = ((Integer) session.createFilter(master.getDs_datesheetsubjectwisedetails(), "select count(*)").list().get(0)).intValue();
//                int i33 = ((Integer) session.createFilter(master.getDs_draft_datesheetdatas(), "select count(*)").list().get(0)).intValue();
//                int i34 = ((Integer) session.createFilter(master.getDs_draft_datesheetparas(), "select count(*)").list().get(0)).intValue();
//                int i35 = ((Integer) session.createFilter(master.getStudentexamattendances(), "select count(*)").list().get(0)).intValue();
//                int i36 = ((Integer) session.createFilter(master.getSeatingplanallocations(), "select count(*)").list().get(0)).intValue();
//                int i37 = ((Integer) session.createFilter(master.getSeatingplansubjectses(), "select count(*)").list().get(0)).intValue();
//                int i38 = ((Integer) session.createFilter(master.getSeatingplanreallocations(), "select count(*)").list().get(0)).intValue();
//                int i39 = ((Integer) session.createFilter(master.getDs_stytypewisesubjects(), "select count(*)").list().get(0)).intValue();
//                int i40 = ((Integer) session.createFilter(master.getDsstytypewisesubjects(), "select count(*)").list().get(0)).intValue();
//                int i41 = ((Integer) session.createFilter(master.getDs_datesheetparameterses(), "select count(*)").list().get(0)).intValue();
//                int i42 = ((Integer) session.createFilter(master.getVfa_studenttransactions(), "select count(*)").list().get(0)).intValue();
//                int i43 = ((Integer) session.createFilter(master.getFeetransactiondetails(), "select count(*)").list().get(0)).intValue();
//                return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18 + i19 + i20 + i21 + i22 + i23 + i24 + i25 + i26 + i27 + i28 + i29 + i30 + i31 + i32 + i33 + i34 + i35 + i36 + i37 + i38 + i39 + i40 + i41 + i42 + i43;
                return i1 + i2 + i3 + i6 + i7 + i9 + i10;
            }
        };
        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
    }

    public List<String> doValidate(final StyType styType, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StyType.class);
                criteria.add(Restrictions.eq("id.instituteid", styType.getId().getInstituteid()));
                criteria.add(Restrictions.eq("stytype", styType.getStytype()).ignoreCase());
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("stytype", styType.getId().getStytypeid()));
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Semester Type Code already exist");
        }
        return errors;
    }

    public List<StyType> getStyType(final String instituteid, final String stytype) {
        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = null;
                criteria = session.createCriteria(StyType.class)
                        .add(Restrictions.eq("id.instituteid", instituteid))
                        .add(Restrictions.eq("stytype", stytype))
                        .add(Restrictions.eq("deactive", "N"));
                return criteria.list();
            }
        });
        return list;
    }

    public String getStytypeId(final String instituteid, final String stytype) {
        final String stytypeid = (String) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(StyType.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteid));
                criteria.add(Restrictions.eq("master.stytype", stytype));
                criteria.setProjection(Projections.property("master.id.stytypeid"));
                return criteria.list();
            }
        }).get(0).toString();
        return stytypeid;
    }

    public String getstytypeID(String instituteid, String stytype) {
        String stytypeId = "";
        String qryStr = "select id.stytypeid from StyType where stytype='" + stytype + "' and id.instituteid='" + instituteid + "'";
        List list = getHibernateTemplate().find(qryStr);
        if (list.size() > 0) {
            stytypeId = list.get(0).toString();
        }
        return stytypeId;
    }

}
