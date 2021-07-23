/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.CommitteeFormationIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import com.jilit.irp.persistence.dto.CommitteeFormation;
import com.jilit.irp.persistence.dto.CommitteeFormationId;
import com.jilit.irp.persistence.dto.MeetingAgendaMaster;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class CommitteeFormationDAO extends HibernateDAO implements CommitteeFormationIDAO {

     private static final Log log = LogFactory.getLog(CommitteeFormationDAO.class);



    public List<String> doValidate(CommitteeFormation committeeFormation, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<?> findAll() {
        log.info("Retrieving all DesignationMaster records via Hibernate from the database");
        return this.find("from CommitteeFormation as tname");
    }


    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeFormation.class, id);
    }

//     public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((CommitteeFormation) record);
//    }
//
//
//     public int checkIfChildExist(final CommitteeFormationId committeeFormationId) {
//        HibernateCallback callback = new HibernateCallback() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException{
//                CommitteeFormation committeeform = (CommitteeFormation)session.get(CommitteeFormation.class, new CommitteeFormationId(committeeFormationId.getInstituteid(),committeeFormationId.getCommitteeid(),committeeFormationId.getFormationid()));
//                int i1 = ((Integer) session.createFilter(committeeform.getCommitteemembers(), "select count(*)").list().get(0)).intValue();
//            return i1;
//            }
//           };
//         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
//     }
//
//
//    public Collection<?>getCommitteeFormations(final String instituteId, final String committeeId){
//        System.err.print("instituteId "+instituteId +" committeeId "+committeeId);
//       final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//           public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(CommitteeFormation.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteId));
//                criteria.add(Restrictions.eq("master.id.committeeid", committeeId));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.committeeid").as("COMMITTEEID")).add(Projections.property("master.id.formationid").as("FORMATIONID")).add(Projections.property("master.remarks").as("REMARKS")).add(Projections.property("master.formationdate").as("FORMATIONDATE")).add(Projections.property("master.activetilldate").as("ACTIVETILLDATE"))
//                    );
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//
//      }
//
//
//            public Collection<?> committeeFormationIDExists(final String instituteId, final String committeeId,  final String formationid)
//            {
//            final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                Criteria criteria = session.createCriteria(CommitteeFormation.class, "master");
//                criteria.add(Restrictions.eq("master.id.instituteid", instituteId));
//                criteria.add(Restrictions.eq("master.id.committeeid", committeeId));
//                criteria.add(Restrictions.eq("master.id.formationid", formationid));
//                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.committeeid").as("COMMITTEEID")).add(Projections.property("master.id.formationid").as("formationid")).add(Projections.property("master.formationdate").as("FORMATIONDATE")).add(Projections.property("master.activetilldate").as("ACTIVETILLDATE")).add(Projections.property("master.remarks").as("REMARKS"))
//                      );
//                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//                return criteria.list();
//            }
//        });
//        return list;
//     }
}
