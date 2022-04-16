package com.dwadek.crm.settings.service;

import com.dwadek.crm.exception.LoginException;
import com.dwadek.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;

    List<User> getUserList();
}
