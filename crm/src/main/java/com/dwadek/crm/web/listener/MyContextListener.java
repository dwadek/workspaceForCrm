package com.dwadek.crm.web.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.DriverManager;

/**
 * 解决web应用程序注册了JDBC驱动程序
 * 手动配置一个监听器
 */
public class MyContextListener implements ServletContextListener {
    /**
     * 实现其中的初始化函数，当有事件发生时即触发
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("webService start");
    }

    /**
     *
     * 实现其中的销毁函数
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("webService stop");
        try {
            while (DriverManager.getDrivers().hasMoreElements()){
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            System.out.println("jdbc Driver close");
            // 由于原文博客内容.shutdown();方法过期,我查看原类此方法继承了checkedShutdown(); 如果不改会启动警告
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("clean thread success");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
