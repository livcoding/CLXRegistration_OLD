package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.RegistrationInstructionUploadIDAO;
import com.jilit.irp.persistence.dto.RegistrationInstruction;
import com.jilit.irp.persistence.dto.RegistrationInstructionId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author priyanka.tyagi
 */
public class RegistrationInstructionUploadDAO extends HibernateDAO implements RegistrationInstructionUploadIDAO {

    private static final Log log = LogFactory.getLog(RegistrationInstructionUploadDAO.class);

    @Override
    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(RegistrationInstruction.class, id);
    }

    public List getRegistrationInstructionData(String instituteid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select rm.registrationcode, rm.registrationdesc, coalesce( ri.coursereginstruction, ''), coalesce(ri.electiveinstruction,'') ,coalesce(ri.gipinstruction,''), coalesce( ri.supplinstruction,'') from RegistrationInstruction ri , RegistrationMaster rm "
                + " where rm.id.instituteid = ri.id.instituteid and rm.id.registrationid = ri.id.registrationid "
                + " and ri.id.instituteid = :instituteid ");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).setParameter("instituteid", instituteid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

}
