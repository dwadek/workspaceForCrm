package com.dwadek.crm.workbench.dao;

import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.domain.ContactsWithCname;

import java.util.List;
import java.util.Map;

public interface ContactsDao {

    int save(Contacts con);

    int getTotalByCondition(Map<String, Object> map);

    List<ContactsWithCname> getContactsListByCondition(Map<String, Object> map);

    Contacts getById(String id);
}
