package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;

import java.util.Map;

public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    PaginationVO<Clue> pageList(Map<String, Object> map);
}
