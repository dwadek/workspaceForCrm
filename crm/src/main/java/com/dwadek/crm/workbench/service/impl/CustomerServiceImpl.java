package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.workbench.dao.CustomerDao;
import com.dwadek.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {

        List<String> sList = customerDao.getCustomerByName1(name);
        return sList;
    }
}
