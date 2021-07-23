/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.persistence.dao.Sct_IrpMlpIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.Sct_IrpMlp;
import com.jilit.irp.persistence.dto.Sct_IrpUsers;
import com.jilit.irp.util.OLTEncryption;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author sunny.singhal
 */
public class Sct_IrpMlpDAO extends HibernateDAO implements Sct_IrpMlpIDAO {

    private static final Log log = LogFactory.getLog(Sct_IrpMlpDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all Sct_IrpMlp records via Hibernate from the database");
        return this.find("from Sct_IrpMlp as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(Sct_IrpMlp.class, id);
    }

    public int checkIfChildExist(String data2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> doValidate(Sct_IrpMlp sct_IrpMlp, String mode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Sct_IrpMlp> loginUserData(final String username, final String encodedusertype, final String instituteid, final String usertype) {

        List<Sct_IrpMlp> list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpMlp.class);
                criteria.add(Restrictions.eq("data3", encodedusertype));
                criteria.add(Restrictions.eq("data4", username));
                return criteria.list();
            }
        });
        System.err.println("asdfasdf" + list.size() + " dddddddddd" + list);
        return list;
    }

    public boolean isValidPassword(final String encodedusertype, final String loginid, final String password, final String usertype) {
        int count = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Sct_IrpMlp.class);
                criteria.add(Restrictions.eq("data3", encodedusertype));
                criteria.add(Restrictions.eq("data4", loginid));
                if (usertype.equals("P")) {
                    criteria.add(Restrictions.eq("data7", password));
                } else {
                    criteria.add(Restrictions.eq("data6", password));
                }
                return criteria.list();
            }
        }).size();
        System.err.println("asdfasdf" + count);
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void updateAfterLogin(final Sct_IrpMlp sct_IrpMlp, final Sct_IrpUsers sct_IrpUsers,final List<Object> retList) {
        Session session = null;
        Transaction tx = null;

        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            tx = session.beginTransaction();

            if (sct_IrpUsers != null) {
                session.update(sct_IrpUsers);
            }
            if (sct_IrpMlp != null) {
                session.update(sct_IrpMlp);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList.add("error");
            retList.add("Error in tx update");
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public String insertData(final List<Sct_IrpUsers> irpUserses, final List<Sct_IrpMlp> irpMlps,final BusinessService businessService) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            //session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();
            session = businessService.getSession();
            tx = businessService.getTransaction();
            // System.err.println("*********** in transaction:irpUserses: " + irpUserses.size()+"irpMlps:"+irpMlps.size()+"userRoleses:"+userRoleses.size()+"securityInfos:"+securityInfos.size());

            for (int i = 0; i < irpUserses.size(); i++) {
                // System.err.println("************* value" + i);
                session.save((Sct_IrpUsers) irpUserses.get(i));
            }
            for (int j = 0; j < irpMlps.size(); j++) {
                //System.err.println("************* value" + j);
                session.save((Sct_IrpMlp) irpMlps.get(j));
            }
            businessService.insertOrUpdateInIdGen(session);
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            retList = "Success";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error";
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return retList;

    }

    public String insertData(final List<Sct_IrpUsers> irpUserses, final List<Sct_IrpMlp> irpMlps) {
        String retList = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateSession();   //getHibernateTemplate().getSessionFactory().openSession();

//            tx = session.getTransaction();
            tx = session.beginTransaction();
            // System.err.println("*********** in transaction:irpUserses: " + irpUserses.size()+"irpMlps:"+irpMlps.size()+"userRoleses:"+userRoleses.size()+"securityInfos:"+securityInfos.size());

            for (int i = 0; i < irpUserses.size(); i++) {
                // System.err.println("************* value" + i);
                session.save((Sct_IrpUsers) irpUserses.get(i));
            }
            for (int j = 0; j < irpMlps.size(); j++) {
                //System.err.println("************* value" + j);
                session.save((Sct_IrpMlp) irpMlps.get(j));
            }
//             businessService.insertOrUpdateInIdGen(session);
            if (!tx.wasCommitted()) {
                tx.commit();
            }
            retList = "Success";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            retList = "Error";
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return retList;

    }

    public ArrayList getDataForResetPassword(final String instid, final String usertype, final String memberid) {
        ArrayList l = (ArrayList) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qryString = "SELECT data6,data2 "
                        + " FROM sct_irpmlp where "
                        + " data1 = '" + instid + "' "
                        + " AND data5= '" + memberid + "' "
                        + " AND data3= '" + usertype + "' ";

                return session.createSQLQuery(qryString).list();
            }
        });
        // System.err.println("SSSSSSSSSSS"+list.size());
        return l;
    }

    public Long updateDataForParentResetPassword(final String PassWord, final String UserType, final String Id) {
        Long count = new Long(0);
        count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qryString = "update Sct_IrpMlp irp  "
                        + " set irp.Data7 ='" + PassWord + "'"
                        + "  where  irp.id.data2= '" + Id + "' "
                        + "  and irp.data3= '" + UserType + "' ";
                return (long) session.createQuery(qryString).executeUpdate();
            }
        });
        return count;
    }

    public Long updateDataForAllResetPassword(final String PassWord, final String UserType, final String Id) {
        Long count = new Long(0);
        count = (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qryString = "update Sct_IrpMlp sirp "
                        + " set sirp.data6 ='" + PassWord + "'"
                        + " where sirp.id.data2 = '" + Id + "' "
                        + " and sirp.data3 = '" + UserType + "' ";
                return (long) session.createQuery(qryString).executeUpdate();
            }
        });
        return count;
    }

    public String findUserid(String employeeid) {
//        String message = null;
        String userid = null;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data5 = '" + employeeid + "' ";
        Sct_IrpMlp irpMlp = null;
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        if (!l.isEmpty()) {
            irpMlp = (Sct_IrpMlp) l.get(0);
            userid = irpMlp.getData2();
            System.out.println("yyy" + irpMlp);
        }
        return userid;
    }

    public String getSaveInsert(String employeeid, String usertype, String str, String str1) {
        String message = null;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data5 = '" + employeeid + "' "
                + " and sct.data3 = '" + usertype + "'";
        Sct_IrpMlp irpMlp = null;
        System.err.println("Query :- " + qry);
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        System.err.println("dddddddd" + l.size());
        if (!l.isEmpty()) {
            irpMlp = (Sct_IrpMlp) l.get(0);
            irpMlp.setData11(str);
            getHibernateTemplate().update(irpMlp);
            if (str1.equals("Y")) {
                message = "Account Locked  Successfully";
            }
            if (str1.equals("N")) {
                message = "Account Unlocked Successfully";
            }
        } else {
            message = "Problem in Update";
        }
        return message;
    }

    @Override
    public String makePasswordExpired(String employeeid, String usertype) {
        String message = null;
        String str = "YUUA7OJGtu4=";  // Means Y and Fkj18Uswuqg=   means N
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data5 = '" + employeeid + "' "
                + " and sct.data3 = '" + usertype + "'";

        List list = getHibernateTemplate().find(qry);
        if (list != null && !list.isEmpty()) {
            Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) list.get(0);
            String data15 = sct_IrpMlp.getData15() == null ? "" : sct_IrpMlp.getData15().toString();
            if (data15.equals("YUUA7OJGtu4=")) {
                message = "Already Expired";
            } else {
                sct_IrpMlp.setData15(str);
                getHibernateTemplate().update(sct_IrpMlp);
                message = "Password Has Been Expired Successfully";
            }
        } else {
            message = "Login Hasnot been created for this Type";
        }
        return message;
    }

    @Override
    public String expireForGroupType(String usertype) {
        String message = null;
        String str = "YUUA7OJGtu4=";  // Means Y and Fkj18Uswuqg=   means N
        Boolean flag = true;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data3 = '" + usertype + "' ";
        List list = getHibernateTemplate().find(qry);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) list.get(i);
                String data15 = sct_IrpMlp.getData15() == null ? "" : sct_IrpMlp.getData15().toString();
                if (!data15.equals("YUUA7OJGtu4=")) {
                    flag = false;
                    sct_IrpMlp.setData15(str);
                    getHibernateTemplate().update(sct_IrpMlp);
                }
            }
            if (flag) {
                message = "Already All Types Has Been Expired ";
            } else {
                message = "All User Types Has Been Expired Successfully";
            }
        }
        return message;
    }

    @Override
    public String expireAllTypes() {
        String message = null;
        String str = "YUUA7OJGtu4="; // Means Y and Fkj18Uswuqg=   means N
        Boolean flag = true;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct ";
        List list = getHibernateTemplate().find(qry);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Sct_IrpMlp sct_IrpMlp = (Sct_IrpMlp) list.get(i);
                String data15 = sct_IrpMlp.getData15() == null ? "" : sct_IrpMlp.getData15().toString();
                if (!data15.equals("YUUA7OJGtu4=")) {
                    flag = false;
                    sct_IrpMlp.setData15(str);
                    getHibernateTemplate().update(sct_IrpMlp);
                }
            }
            if (flag) {
                message = "Already All Types Has Been Expired ";
            } else {
                message = "All User Types Has Been Expired Successfully ";
            }
        }
        return message;
    }

    public String findLoginID(String userid) {
        String loginid = null;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data2 = '" + userid + "' ";
        Sct_IrpMlp irpMlp = null;
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        if (!l.isEmpty()) {
            irpMlp = (Sct_IrpMlp) l.get(0);
            loginid = irpMlp.getData4();
            //System.out.println("yyy" + irpMlp);
        }
        return loginid;
    }

    /**
     * Description: Function has been used to get MemberID for getting messages and portal information.
     *
     * @param userid
     * @return
     */
    public String getMemberID(String userid) {
        String loginid = null;
        String qry = "select sct  "
                + " from Sct_IrpMlp sct "
                + " where sct.data2 = '" + userid + "' ";
        Sct_IrpMlp irpMlp = null;
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        if (!l.isEmpty()) {
            irpMlp = (Sct_IrpMlp) l.get(0);
            loginid = irpMlp.getData5() + ":" + irpMlp.getData3();
        }
        return loginid;
    }

    /**
     * Description: Function has been used to get Online Login information.
     *
     * @param email
     * @return
     */
    public List getMemberDetailAD(String loginid) {

        OLTEncryption oLTEncryption;
        String loginidD = "";
        try {
            oLTEncryption = new OLTEncryption();
            loginidD = oLTEncryption.encode(loginid);
        } catch (Exception e) {
        }
        String qry = "select sct.data6,sct.data3, sct.data1,sct.data2,adloginid,data4  from Sct_IrpMlp sct  where data4='" + loginidD + "' or sct.adloginid = '" + loginid + "' ";
        Sct_IrpMlp irpMlp = null;
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        if (!l.isEmpty()) {
            return l;
        }
        return null;
    }

    public List getMemberDetail(String email) {

        String qry = "select sct.data6,sct.data3, sct.data1,sct.data2  from Sct_IrpMlp sct  where sct.data4 = '" + email + "' ";
        Sct_IrpMlp irpMlp = null;
        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        if (!l.isEmpty()) {
            return l;
        }
        return null;
    }

    public List checkExistingStudentLogin(String instituteid, String studentid, String usertype) {
        String qry = " select 1 from Sct_IrpMlp sct where sct.data1 = '" + instituteid + "' and sct.data5 = '" + studentid + "' and sct.data3 = '" + usertype + "'";

        try {
            return getHibernateTemplate().find(qry);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public List getUserId(String memberid) {
        StringBuilder qryString = new StringBuilder();
        try {
            qryString.append("select new map(sct.data2 as userid) from Sct_IrpMlp sct where sct.data5 = '" + memberid + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHibernateTemplate().find(qryString.toString());
    }

    public List getDataFromSct_IrpMlp() {
        StringBuilder qryString = new StringBuilder();
        try {
            qryString.append("select sct.data2,sct.data3,sct.data5,sct.data13 from Sct_IrpMlp sct");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHibernateTemplate().find(qryString.toString());
    }

    public List getLoginDataList(String UserID) {
        StringBuilder qryString = new StringBuilder();
        try {
            qryString.append("select new map(sct) from Sct_IrpMlp sct where sct.data2 = '" + UserID + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHibernateTemplate().find(qryString.toString());
    }

    public Sct_IrpMlp getLoginDataObject(String UserID) {
        StringBuilder qryString = new StringBuilder();
        try {
            qryString.append("select sct from Sct_IrpMlp sct where sct.data4 = '" + UserID + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Sct_IrpMlp) getHibernateTemplate().find(qryString.toString()).get(0);
    }

    public ArrayList getDataForResetLoginId(final String instid, final String usertype, final String memberid) {
        ArrayList l = (ArrayList) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qryString = "SELECT data2,data4,data6 "
                        + " FROM sct_irpmlp where "
                        + " data1 = '" + instid + "' "
                        + " AND data5= '" + memberid + "' ";
                // " AND data3= '" + usertype + "' ";

                return session.createSQLQuery(qryString).list();
            }
        });
        // System.err.println("SSSSSSSSSSS"+list.size());
        return l;
    }

    public List getLoginEmpRecord(String memberid) {
        List list = null;
        Session session = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select  vs.employeename,vs.employeecode from V_Staff vs where vs.employeeid=:memberid and coalesce(vs.deactive,'N')='N'");
        try {
            session = (Session) getSession();
            list = session.createQuery(sb.toString()).
                    setParameter("memberid", memberid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sb = null;
        }
        return list;
    }

    public List<Sct_IrpMlp> getInstiteWiseStaff(String instituteid, String mexcludeMembertype) {
        String qry = "select sct from Sct_IrpMlp sct where DATA1='" + instituteid + "' and DATA3 <> '" + mexcludeMembertype + "'";

        ArrayList l = new ArrayList();
        l = (ArrayList) getHibernateTemplate().find(qry);
        return l;

    }

    public List getExistingLoginId(String membercode, String member_type) {
        StringBuilder qryString = new StringBuilder();
        try {
            qryString.append("select sct.data4,sct.data2 from Sct_IrpMlp sct where sct.data5='" + membercode + "' and sct.data3='" + member_type + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHibernateTemplate().find(qryString.toString());
    }

    @Override
    public List fetchInstituteData(String userId) {
        List list = null;
        StringBuilder query = new StringBuilder();
        query.append("");
        try {
            query.append("select  distinct  userole.id.rightsbasedid,im.institutecode,im.institutename from Sct_UserRolesCriteraBased userole,InstituteMaster im"
                    + " where userole.id.userid='" + userId + "' and userole.id.rightsbasedid=im.instituteid "
                    + " union"
                    + " select  distinct  urts.id.rightsbasedid,im.institutecode,im.institutename from Sct_UserRightsCriteraBased urts ,InstituteMaster im"
                    + " where urts.id.userid='" + userId + "'  and urts.id.rightsbasedid=im.instituteid");
            list = getHibernateTemplate().find(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query = null;
        }
        return list;
    }

}
