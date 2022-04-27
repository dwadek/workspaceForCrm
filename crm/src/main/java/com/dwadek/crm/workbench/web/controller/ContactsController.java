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
import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.domain.ContactsWithCname;
import com.dwadek.crm.workbench.service.ClueService;
import com.dwadek.crm.workbench.service.ContactsService;
import com.dwadek.crm.workbench.service.impl.ClueServiceImpl;
import com.dwadek.crm.workbench.service.impl.ContactsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/workbench/contacts/pageList.do","/workbench/contacts/getUserList.do",
        "/workbench/contacts/save.do","/workbench/contacts/getUserListAndContacts.do"})
public class ContactsController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/workbench/contacts/pageList.do".equals(path)) {
            pageList(request, response);
        }else if("/workbench/contacts/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/contacts/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/contacts/getUserListAndContacts.do".equals(path)){
            getUserListAndContacts(request,response);
        }

    }

    private void getUserListAndContacts(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ContactsService cs = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        Map<String,Object> map = cs.getUserListAndContacts(id);
        PrintJson.printJsonObj(response,map);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行联系人添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String mphone = request.getParameter("mphone");
        String source = request.getParameter("source");
        String birth = request.getParameter("birth");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Contacts c = new Contacts();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setJob(job);
        c.setEmail(email);
        c.setMphone(mphone);
        c.setSource(source);
        c.setBirth(birth);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        ContactsService cs = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        boolean flag = cs.save(c);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到联系人列表的操作（结合条件查询+分页查询）");

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String name = request.getParameter("name");
        String source = request.getParameter("source");
        String owner = request.getParameter("owner");
        String birth = request.getParameter("birth");

        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("name", name);
        map.put("source", source);
        map.put("owner", owner);
        map.put("birth", birth);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        ContactsService cos = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());

        PaginationVO<ContactsWithCname> vo = cos.pageList(map);

        PrintJson.printJsonObj(response, vo);
    }
}
