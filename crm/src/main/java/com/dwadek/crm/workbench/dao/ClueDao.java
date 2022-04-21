package com.dwadek.crm.workbench.dao;

import com.dwadek.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);
}
