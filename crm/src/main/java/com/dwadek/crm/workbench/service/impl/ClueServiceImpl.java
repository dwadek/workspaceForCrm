package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.workbench.dao.ClueActivityRelationDao;
import com.dwadek.crm.workbench.dao.ClueDao;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public Clue detail(String id) {

        Clue c = clueDao.detail(id);
        return c;
    }

    @Override
    public boolean unbund(String id) {

        boolean flag = true;
        int count = clueActivityRelationDao.unbund(id);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean save(Clue c) {

        boolean flag = true;
        int count = clueDao.save(c);
        if(count != 1){
            flag = false;
        }
        return flag;
    }
}
