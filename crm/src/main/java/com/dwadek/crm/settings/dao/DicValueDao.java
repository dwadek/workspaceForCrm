package com.dwadek.crm.settings.dao;

import com.dwadek.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
