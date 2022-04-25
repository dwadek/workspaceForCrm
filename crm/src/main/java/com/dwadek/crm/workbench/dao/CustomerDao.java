package com.dwadek.crm.workbench.dao;

import com.dwadek.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerByName1(String name);
}
