package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.dao.ContactsDao;
import com.dwadek.crm.workbench.dao.CustomerDao;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.domain.ContactsWithCname;
import com.dwadek.crm.workbench.service.ContactsService;

import java.util.List;
import java.util.Map;

public class ContactsServiceImpl implements ContactsService {

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

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
}
