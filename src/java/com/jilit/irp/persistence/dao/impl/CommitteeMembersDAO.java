/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.CommitteeMembersIDAO;
import com.jilit.irp.persistence.dto.CommitteeMembers;
import com.jilit.irp.persistence.dto.CommitteeMembersId;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.sql.SQLException;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author ashok.singh
 */
public class CommitteeMembersDAO extends HibernateDAO implements CommitteeMembersIDAO
{

     private static final Log log = LogFactory.getLog(CommitteeFormationDAO.class);

     public List<String> doValidate(CommitteeMembers committeeMembers, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<?> findAll() {
        log.info("Retrieving all CommitteeMembers records via Hibernate from the database");
        return this.find("from CommitteeMembers as tname");
    }


    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(CommitteeMembers.class, id);
    }

     public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((CommitteeMembers) record);
    }


     public int checkIfChildExist(final CommitteeMembersId committeeMembersId) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException{
                CommitteeMembers committeemem = (CommitteeMembers)session.get(CommitteeMembers.class, new CommitteeMembersId(committeeMembersId.getInstituteid(),committeeMembersId.getCommitteeid(),committeeMembersId.getFormationid(),committeeMembersId.getStafftype(),committeeMembersId.getStaffid()));
                int i1 = ((Integer) session.createFilter(committeemem.getCommitteemeetingparticipentses(), "select count(*)").list().get(0)).intValue();
                System.out.print("Hello Ashok"+i1);
            return i1;
            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
     }
}
