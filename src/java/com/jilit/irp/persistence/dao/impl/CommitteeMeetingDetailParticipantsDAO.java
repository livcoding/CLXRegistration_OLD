/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

/**
 *
 * @author ashok.singh
 */
//import com.jilit.irp.data.FilterInfoData;

import com.jilit.irp.persistence.dao.CommitteeMeetingDetailParticipantsIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.CommitteeMeetingDetailId;
import com.jilit.irp.persistence.dto.CommitteeMeetingDetail;
import com.jilit.irp.persistence.dto.CommitteeMeetingParticipents;
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
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author Shimona.Khandelwal
 */
public class CommitteeMeetingDetailParticipantsDAO extends HibernateDAO implements CommitteeMeetingDetailParticipantsIDAO {

    private static final Log log = LogFactory.getLog(CommitteeMeetingDetailParticipantsDAO.class);

    public Collection<?> findAll() {

        log.info("Retrieving all CommitteeMeetingDetailParticipents records via Hibernate from the database");
        return this.find("from CommitteeMeetingDetailParticipents as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeMeetingParticipents.class, id);
    }



//    public void saveOrUpdate(Object record) {
//        getHibernateTemplate().saveOrUpdate((CommitteeMeetingParticipents) record);
//    }
//
//    public Collection<?> getCommitteeDetailWithChildTablesData(final int firstResult, final int maxResults, final FilterInfoData filterInfoData, final boolean returnNoOfRows) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                List<CommitteeMeetingDetail> list = null;
//                if (filterInfoData != null) {
//                    String qry = JIRPDBUtil.getQuery(filterInfoData, null, "");
//                    Query query = session.createQuery(qry);
//                    if (filterInfoData.getFirstResult() >= 0) {
//                        query.setFirstResult(filterInfoData.getFirstResult());
//                        query.setMaxResults(filterInfoData.getMaxResults());
//                    }
//                    list = query.list();
//                } else {
//                    final Criteria criteria = session.createCriteria(CommitteeMeetingDetail.class);
//
//                    if (firstResult >= 0) {
//                        criteria.setFirstResult(firstResult);
//                        criteria.setMaxResults(maxResults);
//                    }
//                    if (returnNoOfRows) {
//                        criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
//                    }
//                    list = criteria.list();
//                }
//
//                List<GridData> retList = new ArrayList<GridData>();
//                ASObject obj = new ASObject();
//                for (int i = 0; i < list.size(); i++) {
//                    CommitteeMeetingDetail master = list.get(i);
//                    obj = new ASObject();
//                    obj.put("committeecode", master.getCommitteemaster() != null ? master.getCommitteemaster().getCommitteecode() : "");
//                    obj.put("committeedesc", master.getCommitteemaster() != null ? master.getCommitteemaster().getCommitteedesc() : "");
//                    obj.put("agendadesc", master.getMeetingagendamaster() != null ? master.getMeetingagendamaster().getAgendadesc() : "");
//                    retList.add(new GridData(new CommitteeMeetingDetail(master, true), false, master.getIdStr(), obj));
//                }
//                return retList;
//            }
//        });
//        return list;
//    }
//
// public List getParticipentsMembers(String  committeeId, String  formationId, String  meetingId ,String  agendaId, String  instituteId, String staffid, String stafftype){
//           String qryString = "select cmp.remarks" +
//                              " from CommitteeMeetingParticepents  cmp " +
//                              " where cmp.id.instituteid='"+instituteId+"' and cmp.id.committeeid='"+committeeId+"' and cmp.id.formationid='"+formationId+"'" +
//                              " and cmp.id.meetingid='"+meetingId+"' and cmp.id.agendaid='"+agendaId+"' and id.staffid='"+staffid+"' and id.stafftype='"+stafftype+"'";
//            List list = getHibernateTemplate().find(qryString);
//            return list;
//    }
//
// public int deleteParticipentsMembers(String  committeeId, String  formationId, String  meetingId, String  instituteId, String staffid, String stafftype,String  agendaId){
//           String qryString = "delete from" +
//                              " CommitteeMeetingParticipents " +
//                              " where id.committeeid='"+committeeId+"' and id.formationid='"+formationId+"'" +
//                              " and id.meetingid='"+meetingId+"' and id.instituteid='"+instituteId+"'"+
//                              "and id.staffid='"+staffid+"' and id.stafftype='"+stafftype+"'"+
//                              "and id.agendaid='"+agendaId+"'";
//           System.err.println("AAAAAAAAAAAA-----"+qryString);
//           try{
//                List list = getHibernateTemplate().find(qryString);
//           }catch(Exception e){
//               e.printStackTrace();
//           }
//            return 0;
//    }

}