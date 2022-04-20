package com.dwadek.crm.settings.service.impl;

import com.dwadek.crm.settings.dao.DicTypeDao;
import com.dwadek.crm.settings.dao.DicValueDao;
import com.dwadek.crm.settings.service.DicService;
import com.dwadek.crm.utils.SqlSessionUtil;

public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
}
