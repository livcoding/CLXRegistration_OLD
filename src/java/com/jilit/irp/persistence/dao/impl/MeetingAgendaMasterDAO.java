 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.MeetingAgendaMasterIDAO;
import com.jilit.irp.persistence.dto.MeetingAgendaMaster;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.MeetingAgendaMasterId;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author campus.trainee
 */

    public class MeetingAgendaMasterDAO extends HibernateDAO implements MeetingAgendaMasterIDAO {

    private static final Log log = LogFactory.getLog(MeetingAgendaMaster.class);
    public Collection<?> findAll() {
        log.info("Retrieving all MeetingAgendaMaster  records via Hibernate from the database");
        return this.find("from MeetingAgendaMaster as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(MeetingAgendaMaster.class, id);
    }

    public int checkIfChildExist(final String MeetingAgendaMasterId ) {
       HibernateCallback callback = new HibernateCallback() {
             public Object doInHibernate(Session session) throws HibernateException, SQLException{
               MeetingAgendaMaster meetingAgendaMasterr= (MeetingAgendaMaster)session.get(MeetingAgendaMaster.class, MeetingAgendaMasterId);
               return 0;
            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();

  }
    public List doValidate(MeetingAgendaMaster meetingAgendaMaster, String string) {
//      System.out.println("hi i m in do validate methods");
        List<String> errors = new ArrayList<String>();
        Integer count =new Integer(0);
//         System.err.println("---SSSSSUUUUUUUUUUUU---");
//         count =(Integer) getHibernateTemplate().executeFind(new HibernateCallback(){
//
//            public Object doInHibernate(Session session) {
//                Criteria criteria = session.createCriteria(MeetingAgendaMaster.class);
//                 criteria.add(Expression.eq("committeecode", committeeMaster.getCommitteecode()).ignoreCase());
//                  criteria.add(Expression.eq("id.instituteid", committeeMaster.getId().getInstituteid()).ignoreCase());
//                  if(mode.equals("edit")){
//                      criteria.add(Expression.ne("id.committeeid", committeeMaster.getId().getCommitteeid())); //Do not check for itself when updating record
//                  }
//                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                return criteria.list();
//            }
//        }).get(0);
//
       if(count.intValue()>0){
           errors.add("Same Values Of Committee Code exist");
       }
       return errors;
    }

    public Collection<?>getAgenda(final String instituteId, final String committeeId){
       final List list = getHibernateTemplate().executeFind(new HibernateCallback() {

           public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(MeetingAgendaMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteId));
                criteria.add(Restrictions.eq("master.id.committeeid", committeeId));
                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.committeeid").as("COMMITTEEID")).add(Projections.property("master.id.agendaid").as("AGENDAID")).add(Projections.property("master.agendadesc").as("AGENDADESC")).add(Projections.property("master.agendastatus").as("AGENDASTATUS"))
                    );
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }

        });
        return list;

      }


public Collection<?> agendaExits(final String instituteId, final String committeeId,  final String agendadesc)
{
      final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(MeetingAgendaMaster.class, "master");
                criteria.add(Restrictions.eq("master.id.instituteid", instituteId));
                criteria.add(Restrictions.eq("master.id.committeeid", committeeId));
                criteria.add(Restrictions.eq("master.agendadesc", agendadesc));


                criteria.setProjection(Projections.projectionList().add(Projections.property("master.id.committeeid").as("COMMITTEEID")).add(Projections.property("master.id.agendaid").as("agendadid")).add(Projections.property("master.agendadesc").as("AGENDADESC")));
          

                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                return criteria.list();
            }
        });
        return list;
}


}
