package com.dwadek.crm.workbench.service;

import com.dwadek.crm.workbench.domain.Tran;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);
}
