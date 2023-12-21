package com.springboot.chatapp.springboot.controller;

import com.springboot.chatapp.springboot.entity.UserInfo;
import com.springboot.chatapp.springboot.exception.RESTException;
import com.springboot.chatapp.springboot.model.UserInfoReponse;
import com.springboot.chatapp.springboot.service.UserInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(
            UserInfoService userInfoService
    ) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/save")
    public UserInfo saveUserInfo(
            @RequestBody UserInfo userInfo
    ) {
        return userInfoService.saveUserInfo(userInfo);
    }

    @GetMapping("/get/all")
    public List<UserInfoReponse> getAllUserInfo(
            @RequestParam("id") int id,
            @RequestParam("name") String name
    ) throws RESTException {
        return userInfoService.getAllUserInfo(id, name);
    }

    @PostMapping("/logout")
    public UserInfo logoutUser(
            @RequestParam("id") int id
    ) throws RESTException {
        return userInfoService.logoutUser(id);
    }

    @PatchMapping("/update")
    public UserInfo updateUserInfo(
            @RequestParam("id") int id,
            @RequestBody Map<String, Object> updateUserInfo
    ) throws RESTException {
        return userInfoService.updateName(id, updateUserInfo);
    }

    @GetMapping("/get")
    public UserInfo getUserInfo(
            @RequestParam("id") int id
    ) throws RESTException {
        return userInfoService.getUserInfo(id);
    }

    @DeleteMapping("/delete")
    public String deleteUserInfo(
            @RequestParam("id") int id
    ) throws RESTException {
        return userInfoService.deleteUserInfo(id);
    }

    @PostMapping("/upload/profile")
    public UserInfo uploadProfile(
            @RequestParam("id") int id,
            @RequestParam("profileImage") MultipartFile multipartFile
            ) throws RESTException {
        return userInfoService.uploadProfile(id, multipartFile);
    }
}
