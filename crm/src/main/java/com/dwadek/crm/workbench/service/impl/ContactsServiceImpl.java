package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.settings.dao.UserDao;
import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.dao.ContactsDao;
import com.dwadek.crm.workbench.dao.CustomerDao;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.domain.ContactsWithCname;
import com.dwadek.crm.workbench.service.ContactsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsServiceImpl implements ContactsService {

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public PaginationVO<ContactsWithCname> pageList(Map<String, Object> map) {

        //取得total
        int total = contactsDao.getTotalByCondition(map);

        //取得dataList
        List<ContactsWithCname> dataList = contactsDao.getContactsListByCondition(map);


        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<ContactsWithCname> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;

    }

    @Override
    public boolean save(Contacts c) {

        boolean flag = true;
        int count = contactsDao.save(c);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndContacts(String id) {

        List<User> uList = userDao.getUserList();
        Contacts con = contactsDao.getById(id);

        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("con",con);

        return map;
    }
}
