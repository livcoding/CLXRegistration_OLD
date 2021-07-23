package com.jilit.irp.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware{   

//    public ApplicationContext getApplicationContext() {
//        ApplicationContext context = null;
//        String[] paths = {"applicationContext-Services.xml", "applicationContext-Dao.xml"};
//        context = new ClassPathXmlApplicationContext(paths);
//        return context;
//    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Wiring the ApplicationContext into a static method
        AppContext.setApplicationContext(ctx);

    }

}