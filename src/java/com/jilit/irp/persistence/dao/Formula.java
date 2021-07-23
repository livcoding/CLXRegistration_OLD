/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao;

/**
 *
 * @author sumitk.singh
 */
import org.hibernate.criterion.Order;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;


public class Formula extends Order {
    private String sqlFormula;


    protected Formula(String sqlFormula) {
        super(sqlFormula, true);
        this.sqlFormula = sqlFormula;
    }

    @Override
    public String toString() {
        return sqlFormula;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return sqlFormula;
    }

    public static Order sqlFormula(String sqlFormula) {
        return new Formula(sqlFormula);
    }
}

