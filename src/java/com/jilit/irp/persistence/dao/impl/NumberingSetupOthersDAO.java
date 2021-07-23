/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;
import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.NumberingSetupOthersIDAO;
import com.jilit.irp.persistence.dto.NumberingSetupOthers;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author ashok.singh
 */
public class NumberingSetupOthersDAO extends HibernateDAO implements NumberingSetupOthersIDAO {

    private static final Log log = LogFactory.getLog(NumberingSetupOthersDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all NumberingSetupOthers records via Hibernate from the database");
        return this.find("from NumberingSetupOthers as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(NumberingSetupOthers.class, id);
    }

    public List getNumberingSetupGroupID(final String pCompanyOrInstituteID,final String pCompInstFlag, final String pPrefix)
    {
        List l = null;
        String qryString = "select a.id.groupid,a.ymd,a.totalwidth from	NumberingSetupOthers a where a.companyinstid='"+pCompanyOrInstituteID+"'" +
                " and a.companyinstitute = '"+pCompInstFlag+"' and	a.prefix='"+pPrefix+"'";
        try {
            l = getHibernateTemplate().find(qryString);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return l;
    }

    public List getAllNumberingSetupOthersData()
    {
        List list = null;
        String str = " select distinct nsom.prefix,nsom.numberingname from NumberingSetUpOthersMaster nsom " ;
        try {
            list = getHibernateTemplate().find(str);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getMaxGroupId()
    {
        String maxId = null;
        String str = " select max(nso.groupid) from NumberingSetupOthers nso " ;
        try {
            maxId = getHibernateTemplate().find(str).get(0).toString();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return maxId;
    }

//    public int checkIfChildExist(final String groupid){
//         HibernateCallback callback = new HibernateCallback() {
//
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                NumberingSetupOthers numberingsetupothers = (NumberingSetupOthers) session.get(NumberingSetupOthers.class, groupid);
//                int i1 = ((Integer) session.createFilter(numberingsetupothers.getNumberingcontrolotherses(), "select count(*)").list().get(0)).intValue();
//                return i1;
//            }
//        };
//        return ((Integer) getHibernateTemplate().execute(callback)).intValue();
//    }
}
