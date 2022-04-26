package com.dwadek.crm.web.listener;

import com.dwadek.crm.settings.domain.DicValue;
import com.dwadek.crm.settings.service.DicService;
import com.dwadek.crm.settings.service.impl.DicServiceImpl;
import com.dwadek.crm.utils.ServiceFactory;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@WebListener
public class SysInitListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = event.getServletContext();

        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
        /*
            应该管业务层要
                7个list
                可以打包陈给一个map
                业务层应该这样保存数据的：
                    map.put("appellationList",dvList1);
                    map.put("clueStateList",dvList2);
                    map.put("stageList",dvList3);
                    ...

         */
        Map<String, List<DicValue>> map = ds.getAll();
        //将map解析为上下文对象中保存的键值对
        Set<String> set = map.keySet();
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");

        //----------------------------------

        //数据字处理完毕之后，处理Stage2Possibility.properties文件
        /*
            处理Stage2Possibility.properties文件步骤
                解析该文件，将属性文件中的键值对关系处理成java中键值对关系（map）

                Map<String(阶段stage),String(可能性possibility)> pMap = ......
                pMap.put("01资质审查",10);

                pMap保存值之后，放在服务器缓存中
                application.setAttribute("pMap",pMap)
         */

        //解析properties文件

        Map<String,String> pMap = new HashMap<>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while(e.hasMoreElements()){
            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);
            pMap.put(key,value);
        }

        //将pMap保存到服务器缓存中
        application.setAttribute("pMap",pMap);


    }



}
