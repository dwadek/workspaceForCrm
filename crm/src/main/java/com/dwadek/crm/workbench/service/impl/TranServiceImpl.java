package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.settings.dao.UserDao;
import com.dwadek.crm.settings.domain.User;
import com.dwadek.crm.utils.DateTimeUtil;
import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.dao.CustomerDao;
import com.dwadek.crm.workbench.dao.TranDao;
import com.dwadek.crm.workbench.dao.TranHistoryDao;
import com.dwadek.crm.workbench.domain.Clue;
import com.dwadek.crm.workbench.domain.Customer;
import com.dwadek.crm.workbench.domain.Tran;
import com.dwadek.crm.workbench.domain.TranHistory;
import com.dwadek.crm.workbench.service.CustomerService;
import com.dwadek.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public boolean save(Tran t, String customerName) {

        /*
            交易添加业务：
                在做添加之前，参数t里面就少了一项信息，就是客户的主键，customerId

                先处理客户相关的需求
                1.判断customerName，根据客户名称在客户表进行精确查询
                    如果有这个客户则取出这个客户的id，封装到t对象中
                    如果没有这个客户，则在客户表新建一条客户信息，然后将新建的客户的id取出，封装到t对象中

                2.经过以上操作后，t对象中的信息就全了，需要执行添加交易的操作

                3.添加交易完毕后，需要创建一条交易历史
         */

        boolean flag = true;

        Customer cus = customerDao.getCustomerByName(customerName);

        //如果cus为null,需要创建客户
        if(cus==null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            //添加客户
            int count1 = customerDao.save(cus);
            if(count1!=1){
                flag = false;
            }
        }

        //通过以上对客户的处理，不论是查询处理已有的客户，还是以前没有我们新增的客户，总之客户已经有了，客户的id就有了
        //将客户id封装的t对象中
        t.setCustomerId(cus.getId());

        //添加交易
        int count2 = tranDao.save(t);
        if(count2!=1){
            flag = false;
        }

        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        int count3 = tranHistoryDao.save(th);
        if(count3!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public PaginationVO<Tran> pageList(Map<String, Object> map) {

        //取得total
        int total = tranDao.getTotalByCondition(map);

        //取得dataList
        List<Tran> dataList = tranDao.getTranListByCondition(map);

        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<Tran> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;

    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);

        return thList;
    }

    @Override
    public Map<String, Object> getUserListandTran(String id) {
        List<User> uList = userDao.getUserList();
        Tran t = tranDao.getById(id);

        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("t",t);


        return map;
    }

    @Override
    public boolean changeStage(Tran t,String oldStage) {

        boolean flag = true;

        //改变交易阶段
        int count1 = tranDao.changeStage(t);
        if(count1!=1){
            flag = false;
        }

        //交易阶段改变后，生成一条交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(t.getEditTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        th.setStage(oldStage);

        //添加交易历史
        int count2 = tranHistoryDao.save(th);
        if(count2!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        //取得total
        int total = tranDao.getTotal();

        //取得dataList
        List<Map<String,Object>> dataList = tranDao.getCharts();

        //将total和dataList存到map中
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);

        //返回map
        return map;
    }
}
