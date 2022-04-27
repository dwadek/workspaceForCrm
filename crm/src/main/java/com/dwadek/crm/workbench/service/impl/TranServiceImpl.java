package com.dwadek.crm.workbench.service.impl;

import com.dwadek.crm.utils.DateTimeUtil;
import com.dwadek.crm.utils.SqlSessionUtil;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.workbench.dao.CustomerDao;
import com.dwadek.crm.workbench.dao.TranDao;
import com.dwadek.crm.workbench.dao.TranHistoryDao;
import com.dwadek.crm.workbench.domain.Customer;
import com.dwadek.crm.workbench.domain.Tran;
import com.dwadek.crm.workbench.domain.TranHistory;
import com.dwadek.crm.workbench.service.CustomerService;
import com.dwadek.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

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
}
