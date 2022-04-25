package com.dwadek.crm.workbench.web.controller;

import com.dwadek.crm.utils.PrintJson;
import com.dwadek.crm.utils.ServiceFactory;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.service.ContactsService;
import com.dwadek.crm.workbench.service.impl.ContactsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/workbench/contacts/pageList.do"})
public class ContactsController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/workbench/contacts/pageList.do".equals(path)) {
            pageList(request, response);
        }

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

        PaginationVO<Contacts> vo = cos.pageList(map);

        PrintJson.printJsonObj(response, vo);
    }
}
