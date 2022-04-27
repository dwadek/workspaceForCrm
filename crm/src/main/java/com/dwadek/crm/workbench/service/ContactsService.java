package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Contacts;
import com.dwadek.crm.workbench.domain.ContactsWithCname;

import java.util.Map;

public interface ContactsService {

    PaginationVO<ContactsWithCname> pageList(Map<String, Object> map);

    boolean save(Contacts c);

    Map<String, Object> getUserListAndContacts(String id);
}
