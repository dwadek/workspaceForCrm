package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Tran;
import com.dwadek.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    PaginationVO<Tran> pageList(Map<String, Object> map);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
