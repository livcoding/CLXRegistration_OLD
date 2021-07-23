/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.StudentQualificationIDAO;
import com.jilit.irp.persistence.dao.HibernateDAO;

import com.jilit.irp.persistence.dto.StudentQualification;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Shimona.Khandelwal
 */
public class StudentQualificationDAO extends HibernateDAO implements StudentQualificationIDAO {

    private static final Log log = LogFactory.getLog(StudentQualificationDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentQualification records via Hibernate from the database");
        return this.find("from StudentQualification as tname");
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentQualification.class, id);
    }

    public List getStudentQualification(String studentid) {
        List list = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select sqm.exampassed,sqm.yearofpassing,sqm.division,sqm.maxmarks,sqm.marksobtained,sqm.percentageofmarks,sqm.grade,sq.boardname from  StudentQualification  sqm,StudentMaster sm,StudentQlyBoardMaster sq"
                + " where  sqm.id.studentid =:studentid  and   sqm.id.studentid = sm.studentid and sqm.boardid = sq.boardid ");

        try {
            list = getHibernateSession().createQuery(sb.toString()).
                    setParameter("studentid", studentid).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sb = null;
        }
        return list;
    }

//    public String getQualification(String qualificationid) {
//        List list = null;
//        String qualificationcode = "";
//        String qryString = " select  qm.qualificationcode " +
//                " from QualificationMaster  qm " +
//                " where qm.qualificationid = '" + qualificationid + "' ";
//        try {
//            list = getHibernateTemplate().find(qryString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (list != null && !list.isEmpty() && list.size() == 1) {
//            qualificationcode = list.get(0).toString();
//        }
//        return qualificationcode;
//    }
//
//    public String getBoard(String boardid) {
//        List list = null;
//        String boardname = "";
//        String qryString = " select  sqm.boardname " +
//                " from StudentQlyBoardMaster  sqm " +
//                " where sqm.boardid = '" + boardid + "' ";
//        try {
//            list = getHibernateTemplate().find(qryString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (list != null && !list.isEmpty() && list.size() == 1) {
//            boardname = list.get(0).toString();
//        }
//        return boardname;
//    }
}
