package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.workbench.dao.TranDao;
import com.dwadek.crm.workbench.dao.TranHistoryDao;
import com.dwadek.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


}
