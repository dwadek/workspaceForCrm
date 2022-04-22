package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.settings.dao.UserDao;
import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.dao.ClueActivityRelationDao;
import com.dwadek.crm.workbench.dao.ClueDao;
import com.dwadek.crm.workbench.domain.Activity;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.ClueActivityRelation;
import com.dwadek.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


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
    public PaginationVO<Clue> pageList(Map<String, Object> map) {

        //取得total
        int total = clueDao.getTotalByCondition(map);

        //取得dataList
        List<Clue> dataList = clueDao.getClueListByCondition(map);

        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<Clue> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }

    @Override
    public boolean update(Clue c) {

        boolean flag = true;
        int count = clueDao.update(c);
        if(count != 1){
            flag = false;
        }
        return flag;

    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;
        int count3 = clueDao.delete(ids);
        if(count3 != ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        boolean flag = true;
        ClueActivityRelation car = new ClueActivityRelation();

        for(String aid:aids){

            //取得每一aid和cid做关联
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);

            //添加关联关系表中的记录
            int count = clueActivityRelationDao.bund(car);
            if(count!=1){
                flag=false;
            }
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndClue(String id) {

        //取uList
        List<User> uList = userDao.getUserList();

        //去a
        Clue c = clueDao.getById(id);

        //将uList打包到map中
        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("c", c);

        //返回map就可以了
        return map;
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
