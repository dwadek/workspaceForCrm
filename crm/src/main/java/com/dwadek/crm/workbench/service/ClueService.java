package com.dwadek.crm.workbench.service;

import com.dwadek.crm.workbench.domain.Clue;

public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);
}
