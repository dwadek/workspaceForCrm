package com.dwadek.crm.workbench.service;

import com.dwadek.crm.vo.PaginationVO;
import com.dwadek.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}


