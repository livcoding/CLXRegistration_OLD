/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.util;

import java.io.Serializable;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ashutosh1.kumar
 */
@Component
//@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class JIRPSession implements Serializable {

    @Autowired
    private JIRPSessionInfo jsessionInfo;

    public static Object removeAttributeFromSession(HttpSession session, String key) {

        HashMap hashMap = (HashMap) session.getAttribute(JIRPConstants.JIRPSESSIONMAP);
        if (hashMap.containsKey(key)) {
            hashMap.remove(key);
            System.err.println("Successfully Removed " + key);
        } else {
            System.err.println(key + " does not exist.");
        }
        return hashMap.get(key);
    }

    public JIRPSessionInfo getJsessionInfo() {
        return jsessionInfo;
    }

    public void setJsessionInfo(JIRPSessionInfo jsessionInfo) {
        this.jsessionInfo = jsessionInfo;
    }

}
