package com.dwadek.crm.workbench.web.controller;

import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.settings.service.UserService;
import com.dwadek.crm.settings.service.impl.UserServiceImpl;
import com.dwadek.crm.utils.DateTimeUtil;
import com.dwadek.crm.utils.PrintJson;
import com.dwadek.crm.utils.ServiceFactory;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Tran;
import com.dwadek.crm.workbench.domain.TranHistory;
import com.dwadek.crm.workbench.service.ClueService;
import com.dwadek.crm.workbench.service.CustomerService;
import com.dwadek.crm.workbench.service.TranService;
import com.dwadek.crm.workbench.service.impl.ClueServiceImpl;
import com.dwadek.crm.workbench.service.impl.CustomerServiceImpl;
import com.dwadek.crm.workbench.service.impl.TranServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet({"/workbench/transaction/add.do", "/workbench/transaction/getCustomerName.do",
        "/workbench/transaction/save.do", "/workbench/transaction/detail.do",
        "/workbench/transaction/pageList.do", "/workbench/transaction/getHistoryListByTranId.do",
        "/workbench/transaction/getUserListandTran.do","/workbench/transaction/update.do",
        "/workbench/transaction/changeStage.do","/workbench/transaction/getCharts.do"})
public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)) {
            add(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        } else if ("/workbench/transaction/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/transaction/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/transaction/pageList.do".equals(path)) {
            pageList(request, response);
        } else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
            getHistoryListByTranId(request, response);
        }else if ("/workbench/transaction/getUserListandTran.do".equals(path)) {
            getUserListandTran(request, response);
        }else if ("/workbench/transaction/update.do".equals(path)) {
            update(request, response);
        }else if ("/workbench/transaction/changeStage.do".equals(path)) {
            changeStage(request, response);
        }else if ("/workbench/transaction/getCharts.do".equals(path)) {
            getCharts(request, response);
        }
    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("??????????????????????????????????????????");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Map<String,Object> map = ts.getCharts();
        PrintJson.printJsonObj(response,map);


    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????");

        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();

        String oldStage = request.getParameter("oldStage");


        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(editTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = ts.changeStage(t,oldStage);

        Map<String, String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        Map<String,Object> map = new HashMap<>();
        map.put("t",t);
        map.put("success",flag);

        PrintJson.printJsonObj(response,map);


    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????");

        String id = request.getParameter("id");
    }

    private void getUserListandTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Map<String,Object> map = ts.getUserListandTran(id);

        Tran t= (Tran) map.get("t");
        String stage = t.getStage();
        Map<String, String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);

        t.setPossibility(possibility);

        map.put("t",t);

        request.setAttribute("map",map);
        request.getRequestDispatcher("/workbench/transaction/edit.jsp").forward(request,response);
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("????????????id???????????????????????????");

        String tranId = request.getParameter("tranId");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);

        //???????????????????????????????????????
        Map<String, String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");


        //???????????????????????????
        for (TranHistory th : thList) {
            //????????????????????????????????????????????????
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }

        PrintJson.printJsonObj(response, thList);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("?????????????????????????????????????????????????????????+???????????????");

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String name = request.getParameter("name");
        String stage = request.getParameter("stage");
        String source = request.getParameter("source");
        String owner = request.getParameter("owner");
        String contactsName = request.getParameter("contactsName");
        String customerName = request.getParameter("customerName");
        String type = request.getParameter("type");

        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        //???????????????????????????
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("stage", stage);
        map.put("type", type);
        map.put("owner", owner);
        map.put("source", source);
        map.put("contactsName", contactsName);
        map.put("customerName", customerName);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        PaginationVO<Tran> vo = ts.pageList(map);

        PrintJson.printJsonObj(response, vo);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("????????????????????????");

        String id = request.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t = ts.detail(id);

        //???????????????
        /*
           ?????? t
           ??????????????????????????????????????? pMap

         */
        String stage = t.getStage();
        Map<String, String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);

        t.setPossibility(possibility);

        request.setAttribute("t", t);

        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("???????????????????????????");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");//????????????????????????????????????????????????id
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = ts.save(t, customerName);

        if (flag) {
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("??????????????????????????????????????????????????????????????????");

        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> sList = cs.getCustomerName(name);
        PrintJson.printJsonObj(response, sList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("???????????????????????????????????????");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList", uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
    }
}
