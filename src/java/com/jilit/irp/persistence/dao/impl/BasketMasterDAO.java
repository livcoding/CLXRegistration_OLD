/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.BasketMasterIDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dto.BasketMaster;
import java.io.Serializable;
import org.hibernate.criterion.Restrictions;

/*
 * @author v.kumar
 */
public class BasketMasterDAO extends HibernateDAO implements BasketMasterIDAO {

    private static final Log log = LogFactory.getLog(BasketMasterDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all BasketMaster records via Hibernate from the database");
        return this.find("from BasketMaster as tname order by seqid asc");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(BasketMaster.class, id);
    }

    public int checkIfChildExist(final String instituteid, final String basketid) {
        List list = null;
        String qryString = " select pst.basketid from ProgramSubjectTagging pst"
                + " where pst.id.instituteid='" + instituteid + "'"
                + " and pst.basketid='" + basketid + "'";
        try {
            list = getHibernateTemplate().find(qryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.size();
    }

    public List<String> doValidate(final BasketMaster basketMaster, final String mode) {
        List<String> errors = new ArrayList<String>();
        Integer count = new Integer(0);
        //Unique Key Constraint
        count = (Integer) getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(BasketMaster.class);
                criteria.add(Restrictions.eq("id.instituteid", basketMaster.getId().getInstituteid()));
                criteria.add(Restrictions.eq("basketcode", basketMaster.getBasketcode()).ignoreCase());
                criteria.add(Restrictions.eq("programid", basketMaster.getProgramid()).ignoreCase());
                criteria.add(Restrictions.eq("sectionid", basketMaster.getSectionid()).ignoreCase());
                criteria.add(Restrictions.eq("stynumber", basketMaster.getStynumber()));
                if (mode.equals("edit")) {
                    criteria.add(Restrictions.ne("id.basketid", basketMaster.getId().getBasketid()));//Do not check for itself when updating record
                }
                criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
                return criteria.list();
            }
        }).get(0);

        if (count.intValue() > 0) {
            errors.add("Duplicate Basket Code Found! ");
        }
        return errors;
    }

    public List getBasketCodeForCopy(String instituteid, String programid, String sectionid, byte stynumber, String reqfor) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.basketid, a.basketcode, a.basketdesc from BasketMaster a"
                + " where a.id.instituteid= :instituteid "
                + " and a.programid= :programid "
                + " and a.sectionid= :sectionid "
                + " and a.stynumber= :stynumber "
                + " and coalesce(a.deactive, 'N')='N' ");
        if (reqfor.equals("copyfrom")) {
            sb.append(" and exists ("
                    + " select ps.basketid from ProgramSchemeAcadyearWise ps "
                    + " where ps.id.instituteid=a.id.instituteid"
                    + " and ps.basketid=a.id.basketid)");
        }
        sb.append(" group by a.id.basketid, a.basketcode, a.basketdesc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBasketCode(String instituteid, String programid, String sectionid, byte stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.basketid, a.basketcode, a.basketdesc from BasketMaster a"
                + " where a.id.instituteid= :instituteid "
                + " and a.programid= :programid "
                + " and a.sectionid= :sectionid "
                + " and a.stynumber= :stynumber "
                + " and coalesce(a.deactive, 'N')='N' "
                + " group by a.id.basketid, a.basketcode, a.basketdesc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBasketCodeExistTeachingScheme(String instituteid, String programid, String sectionid, byte stynumber) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.basketid, a.basketcode, a.basketdesc from BasketMaster a"
                + " where a.id.instituteid= :instituteid "
                + " and a.programid= :programid "
                + " and a.sectionid= :sectionid "
                + " and a.stynumber= :stynumber "
                + " and coalesce(a.deactive, 'N')='N' "
                + " and exists ("
                + " select psaw.basketid from ProgramSchemeAcadyearWise psaw where psaw.id.instituteid=a.id.instituteid and psaw.basketid=a.id.basketid "
                + " and psaw.sectionid=a.sectionid and psaw.stynumber=a.stynumber and psaw.programid=a.programid  )"
                + " group by a.id.basketid, a.basketcode, a.basketdesc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBasketCodeData(String instituteid, String programid, String sectionid, byte stynumber, String subType) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.id.basketid, a.basketcode, a.basketdesc from BasketMaster a"
                + " where a.id.instituteid= :instituteid "
                + " and a.programid= :programid "
                + " and a.sectionid= :sectionid "
                + " and a.stynumber= :stynumber "
                + " and a.subjecttypeid= :subjecttypeid "
                + " and coalesce(a.deactive, 'N')='N' "
                + " group by a.id.basketid, a.basketcode, a.basketdesc");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("stynumber", stynumber).
                    setParameter("subjecttypeid", subType).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public Short getSeqId(String instituteid, String programid, String sectionid, Byte stynumber, String subjecttypeid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select max(bs.seqid) from BasketMaster bs where bs.id.instituteid=:instituteid and  bs.programid=:programid "
                + " and bs.sectionid=:sectionid and  bs.stynumber=:stynumber and  bs.subjecttypeid=:subjecttypeid and coalesce(bs.deactive, 'N')='N'");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).
                    setParameter("subjecttypeid", subjecttypeid).
                    setParameter("stynumber", stynumber).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        final Short seqid = list.get(0) == null ? new Short("0") : Short.decode(String.valueOf(list.get(0)));
        return seqid;
        //   return list;
    }

    public List getBasketCode(String instituteid, String programid, String sectionid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select bs.id.basketid,bs.basketcode,bs.basketdesc,bs.programid,bs.stynumber,bs.subjecttypeid,bs.minsubject,bs.maxsubject,coalesce(bs.deactive,'N') as deactive,bs.preferencetype,"
                + " bs.optional,bs.totalsubject,pr.programcode,sec.sectioncode from BasketMaster bs,ProgramMaster pr,SectionMaster sec where bs.programid =:programid and bs.sectionid =:sectionid and bs.id.instituteid=:instituteid"
                + " and bs.programid=pr.id.programid "
                + " and bs.sectionid=sec.id.sectionid "
                + " and bs.id.instituteid= pr.id.instituteid "
                + " and bs.id.instituteid =:instituteid "
                + " and bs.programid=:programid "
                + " and bs.sectionid=:sectionid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("programid", programid).
                    setParameter("sectionid", sectionid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBasketCode_edit(String instituteid, String basketid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select bs.id.basketid,bs.basketcode,bs.basketdesc,bs.programid,bs.stynumber,bs.subjecttypeid,bs.minsubject,bs.maxsubject,bs.deactive,bs.preferencetype,"
                + " bs.optional,bs.totalsubject,pr.programcode,sec.sectioncode,bs.seqid,bs.sectionid from BasketMaster bs,ProgramMaster pr,SectionMaster sec where "
                + " bs.id.instituteid=:instituteid and bs.id.basketid=:basketid"
                + " and bs.programid=pr.id.programid "
                + " and bs.sectionid=sec.id.sectionid "
                + " and bs.id.instituteid= pr.id.instituteid ");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("basketid", basketid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List checkTeachingScheme(String instituteid, String basketid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" select psayw.id.instituteid, psayw.basketid from ProgramSchemeAcadyearWise psayw where "
                + " psayw.id.instituteid=:instituteid and psayw.basketid=:basketid");
        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instituteid).
                    setParameter("basketid", basketid).list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

    public List getBasketCode(String instituteid, String subjecttypeid) {
        List list = null;
        String qryString = " select A.id.basketid,A.basketcode, A.basketdesc from BasketMaster A"
                + " where A.id.instituteid=:instituteid and A.subjecttypeid=:subjecttypeid ";
        try {
            list = getHibernateSession().createQuery(qryString)
                    .setParameter("instituteid", instituteid).
                    setParameter("subjecttypeid", subjecttypeid).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getAllBasketForReport(String instId, String regId, String progId, String acdyr, String secId) {
        List rtnList = new ArrayList();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select distinct a.basketcode "
                    + " from BasketMaster a, ProgramSubjectTagging b "
                    + " where  "
                    + " a.id.instituteid=:instituteid "
                    + " and a.id.instituteid=b.id.instituteid "
                    + " and a.id.basketid=b.basketid and a.programid=b.programid ");
            if (!progId.equalsIgnoreCase("All")) {
                sb.append(" and a.programid= :progId");
            }
            if (!secId.equalsIgnoreCase("All")) {
                sb.append(" and a.sectionid=:secId'");
            }
            if (!acdyr.equalsIgnoreCase("All")) {
                sb.append(" and b.academicyear=:acdyr");
            }
            sb.append(" and :acdyr=:acdyr and :secId=:secId and :progId=:progId ");
            sb.append(" and a.sectionid=b.sectionid "
                    + " and a.stynumber=b.stynumber "
                    + " and b.id.registrationid=:regId  "
                    + " and a.basketcode <> 'A'"
                    + " and coalesce(BACKPAPERTYPE,'N') <>'Y'"
                    + "  order by a.basketcode");
            rtnList = getHibernateSession().createQuery(sb.toString()).
                    setParameter("instituteid", instId).
                    setParameter("progId", progId).
                    setParameter("acdyr", acdyr).
                    setParameter("secId", secId).
                    setParameter("regId", regId).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnList;
    }
}
