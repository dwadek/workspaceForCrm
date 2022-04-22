package com.dwadek.crm.workbench.web.controller;

import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.settings.service.UserService;
import com.dwadek.crm.settings.service.impl.UserServiceImpl;
import com.dwadek.crm.utils.DateTimeUtil;
import com.dwadek.crm.utils.PrintJson;
import com.dwadek.crm.utils.ServiceFactory;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Activity;
import com.dwadek.crm.workbench.domain.ActivityRemark;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.service.ActivityService;
import com.dwadek.crm.workbench.service.ClueService;
import com.dwadek.crm.workbench.service.impl.ActivityServiceImpl;
import com.dwadek.crm.workbench.service.impl.ClueServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/workbench/clue/getUserList.do", "/workbench/clue/save.do",
        "/workbench/clue/detail.do", "/workbench/clue/getActivityListByClueId.do",
        "/workbench/clue/unbund.do", "/workbench/clue/pageList.do",
        "/workbench/clue/getUserListAndClue.do","/workbench/clue/update.do",
        "/workbench/clue/delete.do"})
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/clue/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/clue/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/clue/getActivityListByClueId.do".equals(path)) {
            getActivityListByClueId(request, response);
        } else if ("/workbench/clue/unbund.do".equals(path)) {
            unbund(request, response);
        } else if ("/workbench/clue/pageList.do".equals(path)) {
            pageList(request, response);
        } else if ("/workbench/clue/getUserListAndClue.do".equals(path)) {
            getUserListAndClue(request, response);
        }else if ("/workbench/clue/update.do".equals(path)) {
            update(request, response);
        }else if ("/workbench/clue/delete.do".equals(path)) {
            delete(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索的删除操作");

        String[] ids = request.getParameterValues("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.delete(ids);

        PrintJson.printJsonFlag(response, flag);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索的更新操作");

        String id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setEditBy(editBy);
        c.setEditTime(editTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.update(c);
        PrintJson.printJsonFlag(response, flag);


    }

    private void getUserListAndClue(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表和根据id获取线索信息");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Map<String,Object> map = cs.getUserListAndClue(id);
        PrintJson.printJsonObj(response,map);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到线索信息列表的操作（结合条件查询+分页查询）");

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String mphone = request.getParameter("mphone");
        String source = request.getParameter("source");
        String owner = request.getParameter("owner");
        String state = request.getParameter("state");

        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("mphone", mphone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("state", state);
        map.put("owner", owner);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        PaginationVO<Clue> vo = cs.pageList(map);

        PrintJson.printJsonObj(response, vo);

    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("解除关联操作");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response, flag);


    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据线索id查询关联的市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(clueId);
        PrintJson.printJsonObj(response, aList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到线索详细页");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c = cs.detail(id);

        request.setAttribute("c", c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.save(c);
        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }


}
