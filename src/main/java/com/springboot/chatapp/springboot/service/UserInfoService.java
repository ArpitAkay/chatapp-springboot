package com.springboot.chatapp.springboot.service;

import com.springboot.chatapp.springboot.entity.UserInfo;
import com.springboot.chatapp.springboot.exception.RESTException;
import com.springboot.chatapp.springboot.model.UserInfoReponse;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    UserInfo saveUserInfo(
            UserInfo userInfo
    );

    List<UserInfoReponse> getAllUserInfo(
            int id,
            String name
    ) throws RESTException;

    UserInfo logoutUser(
            int id
    ) throws RESTException;

    UserInfo updateName(
            int id,
            Map<String, Object> updateUserInfo
    ) throws RESTException;

    UserInfo getUserInfo(
            int id
    ) throws RESTException;

    String deleteUserInfo(
            int id
    ) throws RESTException;
}
