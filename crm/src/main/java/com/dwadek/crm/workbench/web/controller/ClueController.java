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
import com.dwadek.crm.workbench.domain.Tran;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/workbench/clue/getUserList.do", "/workbench/clue/save.do",
        "/workbench/clue/detail.do", "/workbench/clue/getActivityListByClueId.do",
        "/workbench/clue/unbund.do", "/workbench/clue/pageList.do",
        "/workbench/clue/getUserListAndClue.do","/workbench/clue/update.do",
        "/workbench/clue/delete.do","/workbench/clue/getActivityListByNameAndNotByClueId.do",
        "/workbench/clue/bund.do","/workbench/clue/getActivityListByName.do",
        "/workbench/clue/convert.do"})
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
        }else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {
            getActivityListByNameAndNotByClueId(request, response);
        }else if ("/workbench/clue/bund.do".equals(path)) {
            bund(request, response);
        }else if ("/workbench/clue/getActivityListByName.do".equals(path)) {
            getActivityListByName(request, response);
        }else if ("/workbench/clue/convert.do".equals(path)) {
            convert(request, response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("???????????????????????????");

        String clueId = request.getParameter("clueId");

        //???????????????????????????????????????
        String flag = request.getParameter("flag");

        Tran t = null;

        String createBy = ((User) request.getSession().getAttribute("user")).getName();


        //????????????????????????
        if("a".equals(flag)){

            t = new Tran();

            //??????????????????????????????
            String money  = request.getParameter("money");
            String name  = request.getParameter("name");
            String exceptedDate  = request.getParameter("exceptedDate");
            String stage  = request.getParameter("stage");
            String activityId  = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
            t.setActivityId(activityId);
            t.setStage(stage);
            t.setExpectedDate(exceptedDate);
        }

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        /*
            ???????????????????????????

            1.?????????????????????clueId???????????????clueId??????????????????????????????????????????
            2.????????????t?????????????????????????????????????????????????????????????????????????????????????????????t??????????????????null???
         */

        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????????????????????????????");

        String aname = request.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("?????????????????????????????????");

        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bund(cid,aids);
        PrintJson.printJsonFlag(response,flag);


    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????????????????????????????+??????????????????????????????????????????");

        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,aList);



    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????");

        String[] ids = request.getParameterValues("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.delete(ids);

        PrintJson.printJsonFlag(response, flag);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("???????????????????????????");

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
        System.out.println("?????????????????????????????????id??????????????????");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Map<String,Object> map = cs.getUserListAndClue(id);
        PrintJson.printJsonObj(response,map);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("?????????????????????????????????????????????????????????+???????????????");

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
        //???????????????????????????
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
        System.out.println("??????????????????");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response, flag);


    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("????????????id?????????????????????????????????");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(clueId);
        PrintJson.printJsonObj(response, aList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("????????????????????????");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c = cs.detail(id);

        request.setAttribute("c", c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("????????????????????????");

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
        System.out.println("????????????????????????");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }


}
