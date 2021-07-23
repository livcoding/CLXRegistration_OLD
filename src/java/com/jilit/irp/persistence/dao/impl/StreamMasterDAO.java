/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StreamMasterIDAO;
import com.jilit.irp.persistence.dto.StreamMaster;
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author subrata.lohar
 */

public class StreamMasterDAO extends HibernateDAO implements StreamMasterIDAO {

    private static final Log log = LogFactory.getLog(StreamMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StreamMaster records via Hibernate from the database");
        return this.find("from StreamMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StreamMaster.class, id);
    }

    

    public void add(Object record) {
          ((StreamMaster) record).setSeqid(Short.decode(String.valueOf(getMaxSequenceId(record)+1)));
        getHibernateTemplate().save((StreamMaster) record);
    }

    


     public int checkIfChildExist(final String streamid) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException{
                StreamMaster stm = (StreamMaster)session.get(StreamMaster.class, streamid);
               int i1=0;// ( (Integer) session.createFilter( stm.getEmployeedetails(), "select count(*)" ).list().get(0) ).intValue();

            return i1;
            }
           };
         return ((Integer)getHibernateTemplate().execute(callback)).intValue();
     }



  public  List<String> doValidate(final StreamMaster streamMaster,final String mode) {
         List<String> errors = new ArrayList<String>();
         Integer count =new Integer(0);
         /* Check For Primary Key*/

        /*Unique Key Constraint on StreamID and StreamCode*/
         count =(Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(StreamMaster.class);
                 criteria.add(Restrictions.eq("streamcode", streamMaster.getStreamcode()).ignoreCase());
                 criteria.add(Restrictions.eq("instituteid", streamMaster.getInstituteid()).ignoreCase());
                  if(mode.equals("edit")){
                      criteria.add(Restrictions.ne("streamid", streamMaster.getStreamid()));   //Do not check for itself when updating record
                  }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

		if(count.intValue()>0){
            errors.add("Same Values Of Stream Code exist");
        }
        
        return errors;
    }

   public Collection<?> getstreamMasterData(String instId)
  {
      String str=" from StreamMaster where coalesce(deactive,'N')='N' and instituteid='"+instId+"'";
      return this.find(str);
  }
}

