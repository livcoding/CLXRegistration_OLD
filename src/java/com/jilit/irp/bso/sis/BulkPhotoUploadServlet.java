/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.bso.sis;

import com.jilit.irp.Report.RequestManager;
import com.jilit.irp.context.AppContext;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author Priyanka.tyagi
 */
public class BulkPhotoUploadServlet extends HttpServlet implements ServletContextAware {

    private ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ApplicationContext ctx = AppContext.getApplicationContext();
            String data = request.getParameter("data");
            RequestManager service = (RequestManager) ctx.getBean(request.getParameter("refor"));
            service.getRequestReponse(request, response, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
