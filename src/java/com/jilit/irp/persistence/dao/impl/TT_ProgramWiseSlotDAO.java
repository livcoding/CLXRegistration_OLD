/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.TT_ProgramWiseSlotIDAO;
import com.jilit.irp.persistence.dto.TT_ProgramWiseSlot;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Collection;
import org.hibernate.Session;
import java.util.List;
import com.jilit.irp.persistence.dao.HibernateDAO;
import org.hibernate.Transaction;

/**
 *
 * @author ashok.singh
 */
public class TT_ProgramWiseSlotDAO extends HibernateDAO implements TT_ProgramWiseSlotIDAO{
     private static final Log log = LogFactory.getLog(TT_ProgramWiseSlot.class);

    public Collection<?> findAll() {
        log.info("Retrieving all TT_ProgramWiseSlot records via Hibernate from the database");
        return this.find("from TT_ProgramWiseSlot as tname order by seqno asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(TT_ProgramWiseSlot.class, id);
    }

//    public boolean saveObjList(List objList){
//        Session session = null;
//        Transaction tx = null;
//        boolean flag = false;
//        try {
//            if(session == null){
//                session = getHibernateSession();
//                tx = session.beginTransaction();
//            }
//            for (int i = 0; i < objList.size(); i++) {
//                session.save(objList.get(i));
//            }
//            tx.commit();
//            if(tx.wasCommitted()){
//                flag = true;
//            }
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//            flag = false;
//        }finally{
//            if(session != null){
//                session.clear();
//                session.close();
//            }
//        }
//        return flag;
//    }

}
