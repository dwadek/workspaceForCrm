package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Contacts;

import java.util.Map;

public interface ContactsService {

    PaginationVO<Contacts> pageList(Map<String, Object> map);
}
