package com.dwadek.crm.workbench.dao;

import com.dwadek.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);

    int getTotalByCondition(Map<String, Object> map);

    List<Clue> getClueListByCondition(Map<String, Object> map);

    Clue getById(String id);

    int update(Clue c);

    int delete(String[] ids);
}
