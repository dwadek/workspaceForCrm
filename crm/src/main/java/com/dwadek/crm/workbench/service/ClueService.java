package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Tran;

import java.util.Map;

public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    PaginationVO<Clue> pageList(Map<String, Object> map);

    Map<String, Object> getUserListAndClue(String id);

    boolean update(Clue c);

    boolean delete(String[] ids);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);
}
