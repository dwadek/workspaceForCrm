package com.dwadek.crm.settings.web.controller;

import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.settings.service.UserService;
import com.dwadek.crm.settings.service.impl.UserServiceImpl;
import com.dwadek.crm.utils.MD5Util;
import com.dwadek.crm.utils.PrintJson;
import com.dwadek.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/settings/user/login.do")
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if("/settings/user/xxx.do".equals(path)){

        }


    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码的明文转换为密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收ip地址
        String ip = request.getRemoteAddr();
        System.out.println(ip);

        //为了业务层开发，统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {

            User user = us.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            //如果程序执行到此处，说明业务层没有为controller抛出任何异常
            //表示登陆成功
            //{"success":true}
            PrintJson.printJsonFlag(response,true);

        }catch (Exception e){
            e.printStackTrace();

            //一旦程序执行了catch块的信息，说明业务层为我们验证登录失败，为controller抛出了异常
            //{"success":false,"msg":?}
            String msg = e.getMessage();
            /*
                我们现在作为controller，需要为ajax请求提供多项信息

                可以有两种手段来处理：
                    1.将多项信息打包成为map，将map解析为json串
                    2.创建一个VO
                        private boolean success;
                        private String msg;

                 如果对于展现的信息将来还会大量的使用，我们创建一个vo类，使用方便
                 如果对于展现的信息指挥在这个需求中使用，我们使用map就可以了

             */
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }




    }


}
