package com.dwadek.crm.workbench.dao;

import com.dwadek.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);
}
