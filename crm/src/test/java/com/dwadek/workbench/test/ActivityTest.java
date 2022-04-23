package com.dwadek.workbench.test;

import com.dwadek.crm.utils.ServiceFactory;
import com.dwadek.crm.utils.UUIDUtil;
import com.dwadek.crm.workbench.domain.Activity;
import com.dwadek.crm.workbench.service.ActivityService;
import com.dwadek.crm.workbench.service.impl.ActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit:
 *      单元测试
 *      是未来实际项目开发中，用来代替主方法main的
 */

public class ActivityTest {

    @Test
    public void testSave(){

        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setName("洗脑会");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        Assert.assertEquals(false,flag);

    }

    @Test
    public void testUpdate(){

    }

    @Test
    public void testSelect(){

    }
}
