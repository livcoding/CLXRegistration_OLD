/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dao.V_ProgrmSecBranchSemIDAO;
import com.jilit.irp.persistence.dto.V_ProgrmSecBranchSem;
//
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author subrata.lohar
 */
public class V_ProgrmSecBranchSemDAO extends HibernateDAO implements V_ProgrmSecBranchSemIDAO {

    private static final Log log = LogFactory.getLog(V_ProgrmSecBranchSem.class);

    public Collection<?> findAll() {
        log.info("Retrieving all V_ProgrmSecBranchSem records via Hibernate from the database");
        return this.find("from V_ProgrmSecBranchSem as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(V_ProgrmSecBranchSem.class, id);
    }
    public ArrayList getSectionSubsectionDetail(final String instid, final String acadmicyear, final String programid, final Byte stynumber, final String orderby) {
        List l = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String qry = "SELECT   academicyear,programid,branchid , " +
                        " branchcode,stynumber, " +
                        " COUNT (sectionid) sections, SUM (abc) subsections , sectionmaxstudent, subsectionmaxstudent, studcount" +
                        " FROM (SELECT academicyear, programid,  branchid, branchcode, " +
                        " stynumber,sectionid, sectionmaxstudent, subsectionmaxstudent," +
                        " COUNT (DISTINCT subsectionid) abc ,";
                        if(orderby.equalsIgnoreCase("customize")){
                qry=qry+ "(select count(*) from StudentMaster s where s.instituteid=vpsbs.instituteid and  s.programid=vpsbs.programid and  s.branchid=vpsbs.branchid and  s.stynumber=vpsbs.stynumber and coalesce(s.activestatus,'A')='A' and s.academicyear ='" + acadmicyear + "' ) studcount";
                        }else{
                qry=qry+ "(select count(*) from StudentMaster s where s.instituteid=vpsbs.instituteid and  s.programid=vpsbs.programid and  s.branchid=vpsbs.branchid and  s.stynumber=vpsbs.stynumber and coalesce(s.activestatus,'A')='A' and s.academicyear ='" + acadmicyear + "' ) studcount";            
                        }
                qry=qry+ " FROM v_progrmsecbranchsem vpsbs " +
                        " WHERE vpsbs.instituteid = '" + instid + "' " +
                        " AND vpsbs.academicyear = '" + acadmicyear + "'" +
                        " AND vpsbs.programid = '" + programid + "'" +
                        " AND vpsbs.stynumber = '" + stynumber + "'" +
                        " GROUP BY  academicyear,programid,branchid, branchcode,stynumber, sectionid,sectionmaxstudent, subsectionmaxstudent,vpsbs.instituteid)" +
                        " GROUP BY academicyear,programid,branchid, branchcode,stynumber,sectionmaxstudent, subsectionmaxstudent, studcount";
                return session.createSQLQuery(qry).list();
            }
        });
        // System.err.println("SSSSSSSSSSS"+list.size());
        return (ArrayList) l;
    }

    public List getSectionSubSectionDataBranchWiseQuery(String instituteid, String programid, String acadYear, Byte stynumber, String branchid){
        {
            List l = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String qryString = " SELECT distinct branchid, branchcode, sectionid ,sectioncode, subsectionid,subsectioncode," +
                            " academicyear,programid,stynumber, sectionmaxstudent, subsectionmaxstudent " +
                            " FROM v_progrmsecbranchsem vpsbs " +
                            " WHERE vpsbs.instituteid = '" + instituteid + "' " +
                            " AND vpsbs.academicyear = '" + acadYear + "'" +
                            " AND vpsbs.programid = '" + programid + "'" +
                            " AND vpsbs.stynumber = '" + stynumber + "'" +
                            " AND vpsbs.branchid = '" + branchid + "'" +
                            " ORDER BY branchid, branchcode, sectioncode";
                    return session.createSQLQuery(qryString).list();
                }
            });
            return l;
        }
    }
}