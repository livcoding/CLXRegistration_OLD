/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentAddressIDAO;
import com.jilit.irp.persistence.dto.StudentAdddress;
import com.jilit.irp.persistence.dto.StudentMaster;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;

/**
 *
 * @author campus.trainee
 */
public class StudentAddressDAO extends HibernateDAO implements StudentAddressIDAO {

    private static final Log log = LogFactory.getLog(StudentAddressDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentAdddress records via Hibernate from the database");
        return this.find("from StudentAdddress as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentAdddress.class, id);
    }

    public List getStudentStatus(String instituteid) {
        String qryString = "select distinct st.activestatus from  StudentMaster st where  st.instituteid='" + instituteid + "' ";
        return getHibernateTemplate().find(qryString);

    }

    public List getStudentData(String instituteid, String prg_id, String br_id, String sty, String acad_year, String status) {
        String qryString = "select distinct st.studentid,st.name from  StudentMaster st where  st.instituteid='" + instituteid + "' and  st.stynumber in (" + sty + ") and "
                + "st.acadyear='" + acad_year + "' and  st.programid in (" + prg_id + ") and st.branchid in (" + br_id + ") and st.activestatus in ('" + status + "') ";
        return getHibernateTemplate().find(qryString);

    }

    public List getSemester(String branchid, String acad_year) {
        String qryString = "Select  prg.id.instituteid,prg.id.programid,prg.id.branchid,prg.id.academicyear,prg.startsty,prg.endsty  from ProgramMaxSty prg where prg.id.academicyear='" + acad_year + "'  and  prg.id.branchid = '" + branchid + "' ";
        return getHibernateTemplate().find(qryString);

    }

    public Collection<?> getStudentContactInformation(final String studentid) {

        List list = getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                List l = null;

                Criteria criteria = session.createCriteria(StudentMaster.class, "master")
                        .createAlias("master.studentphones", "sp", Criteria.LEFT_JOIN)
                        .setFetchMode("studentphones", FetchMode.DEFAULT)
                        .createAlias("master.studentadddresses", "sa", Criteria.LEFT_JOIN)
                        .setFetchMode("studentadddresses", FetchMode.DEFAULT);

                criteria.add(Expression.eq("master.studentid", studentid));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("sa.studentid").as("studentid"))
                        .add(Projections.property("sa.ccityname").as("ccityname"))
                        .add(Projections.property("sa.cpostoffice").as("cpostoffice"))
                        .add(Projections.property("sa.crailstation").as("crailstation"))
                        .add(Projections.property("sa.cpolicestation").as("cpolicestation"))
                        .add(Projections.property("sa.cpin").as("cpin"))
                        .add(Projections.property("sa.ppostoffice").as("ppostoffice"))
                        .add(Projections.property("sa.prailstation").as("prailstation"))
                        .add(Projections.property("sa.ppolicestation").as("ppolicestation"))
                        .add(Projections.property("sp.scellno").as("scellno"))
                        .add(Projections.property("sp.pcellno").as("pcellno"))
                        .add(Projections.property("sp.sstdcode").as("sstdcode"))
                        .add(Projections.property("sp.stelephoneno").as("stelephoneno"))
                        .add(Projections.property("sp.pstdcode").as("pstdcode"))
                        .add(Projections.property("sp.ptelephoneno").as("ptelephoneno"))
                        .add(Projections.property("sp.semailid").as("semailid"))
                        .add(Projections.property("sp.pemailid").as("pemailid"))
                        .add(Projections.property("sa.caddress1").as("caddress1"))
                        .add(Projections.property("sa.caddress2").as("caddress2"))
                        .add(Projections.property("sa.caddress3").as("caddress3"))
                        .add(Projections.property("sa.paddress1").as("paddress1"))
                        .add(Projections.property("sa.paddress2").as("paddress2"))
                        .add(Projections.property("sa.paddress3").as("paddress3"))
                        .add(Projections.property("sa.cdistrict").as("cdistrict"))
                        .add(Projections.property("sa.pdistrict").as("pdistrict"))
                        .add(Projections.property("sa.pcityname").as("pcityname"))
                        .add(Projections.property("sa.ppin").as("ppin"))
                        .add(Projections.property("sa.cstatename").as("cstatename"))
                        .add(Projections.property("sa.pstatename").as("pstatename")));
                //criteria.addOrder(Order.asc("master.departmentcode"));
                criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                l = criteria.list();

                return l;
            }
        });
        if (list != null) {
            System.err.println("Check Size Of ::" + ":: " + list.size());
        }
        return list;
    }

}
