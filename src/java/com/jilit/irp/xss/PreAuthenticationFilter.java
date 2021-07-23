/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.xss;

import com.jilit.irp.util.ErrorResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ashutosh1.kumar
 */
public class PreAuthenticationFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         this.filterConfig=filterConfig; 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean authorized = true;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpServletRequest);
        String loginURL = httpServletRequest.getContextPath() + "/";
        String loginURL1 = httpServletRequest.getContextPath() + "/token.jsp";
        String loginURL2 = httpServletRequest.getContextPath() + "/resources/js/pace.js";
        String loginURL3 = httpServletRequest.getContextPath() + "/authTokenValidate";
        if (!httpServletRequest.getRequestURI().equals(loginURL)) {
            if (!httpServletRequest.getRequestURI().equals(loginURL1)) {
                if (!httpServletRequest.getRequestURI().equals(loginURL2)) {
                    if (!httpServletRequest.getRequestURI().equals(loginURL3)) {
                        if (httpServletRequest.getMethod().toLowerCase().equals("post") || httpServletRequest.getMethod().toLowerCase().equals("get")) {
                            HttpSession session = httpServletRequest.getSession(false);
                            if (session != null) {
                                if (session.getAttribute("tokenLogin") == "true") {
                                    wrapper.setAttribute("user", session.getAttribute("userid"));

                                } else {
                                    authorized = false;
//                                    ErrorResponse errorResponse = new ErrorResponse();
//                                    errorResponse.setCode(401);
//                                    errorResponse.setMessage("Unauthorized Access");
//
//                                    byte[] responseToSend = restResponseBytes(errorResponse);
//                                    ((HttpServletResponse) httpServletResponse).setHeader("Content-Type", "application/json");
//                                    ((HttpServletResponse) httpServletResponse).setStatus(401);
//                                    httpServletResponse.getOutputStream().write(responseToSend);
//                                    return;
                                }
                            } else {
                                authorized = false;
//                                ErrorResponse errorResponse = new ErrorResponse();
//                                errorResponse.setCode(401);
//                                errorResponse.setMessage("Unauthorized Access");
//
//                                byte[] responseToSend = restResponseBytes(errorResponse);
//                                ((HttpServletResponse) httpServletResponse).setHeader("Content-Type", "application/json");
//                                ((HttpServletResponse) httpServletResponse).setStatus(401);
//                                httpServletResponse.getOutputStream().write(responseToSend);
//                                return;
                            }
                        }
                    }
                }
            }
        }
        if (authorized) {
            chain.doFilter(wrapper, httpServletResponse);
            return;
        } else if (filterConfig != null) {
            String login_page = filterConfig.getInitParameter("login_page");
            if (login_page != null && !"".equals(login_page)) {
                filterConfig.getServletContext().getRequestDispatcher(login_page).
                        forward(request, response);
                return;
            }
        }
    }

    @Override
    public void destroy() {
    }

    private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }

}