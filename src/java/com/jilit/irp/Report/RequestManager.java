/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ashutosh1.kumar
 */
public abstract class RequestManager {

    public abstract void getRequestReponse(HttpServletRequest request, HttpServletResponse response, String enrollmentno);
}
