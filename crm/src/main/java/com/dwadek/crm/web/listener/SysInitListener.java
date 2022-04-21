package com.dwadek.crm.web.listener;

import com.dwadek.crm.settings.domain.DicValue;
import com.dwadek.crm.settings.service.DicService;
import com.dwadek.crm.settings.service.impl.DicServiceImpl;
import com.dwadek.crm.utils.ServiceFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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


    }
}
