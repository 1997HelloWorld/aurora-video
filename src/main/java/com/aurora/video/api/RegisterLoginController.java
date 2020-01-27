package com.aurora.video.api;

import com.aurora.video.pojo.Users;
import com.aurora.video.service.impl.UserServiceImpl;
import com.aurora.video.utils.BackInfo;
import com.aurora.video.utils.MD5;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.Password;

@RestController
@Api(value = "这是用户注册和登录的接口", tags = {"用户登录注册的controller"})
public class RegisterLoginController {
    @Autowired()
    private BackInfo info;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private Gson gson;

    @ApiOperation(value = "用户注册", notes = "注册接口")
    @PostMapping(value = "/register", produces = "text/plain;charset=UTF-8")
    public String Register(@RequestBody Users users) {
        //为避免第二次请求仍有第一次请求残留info
        info.cleanInfo();
        if (StringUtils.isBlank(users.getUsername())) {
            info.setMsg("用户名不能为空");
            return gson.toJson(info);
        }
        if (StringUtils.isBlank(users.getPassword())) {
            info.setMsg("密码不能为空");
            return gson.toJson(info);
        }

        boolean flag = userService.queryUserNameIsExist(users);

        if (!flag) {
            users.setPassword(MD5.md5(users.getPassword()));
            users.setNickname(users.getUsername());
            userService.saveUser(users);
            info.setMsg("注册成功");
            Users loginUser = userService.queryUserByName(users.getUsername());
            loginUser.setPassword("");
            info.setUser(loginUser);
        } else {
            info.setMsg("用户名已存在");
        }
        return gson.toJson(info);

    }

    @PostMapping(value = {"/login"}, produces = "text/plain;charset=UTF-8")
    @ApiOperation(value = "用户登录", notes = "登录接口")
    public String login(@RequestBody Users loginUsers) {
        System.out.println("接受登陆对象"+loginUsers);
        //为避免第二次请求仍有第一次请求残留info
        info.cleanInfo();
        if (StringUtils.isBlank(loginUsers.getUsername())) {
            info.setMsg("用户名不能为空");
            return gson.toJson(info);
        }
        if (StringUtils.isBlank(loginUsers.getPassword())) {
            info.setMsg("密码不能为空");
            return gson.toJson(info);
        }
        Users user = userService.queryUserByName(loginUsers.getUsername());
        if (user == null) {
            info.setMsg("抱歉,该用户不存在");
            return gson.toJson(info);
        }
        String password = MD5.md5(loginUsers.getPassword());
        if (!user.getPassword().equals(password)) {
            System.out.println("密码错误:"+user.getPassword()+"========"+loginUsers.getPassword());
            info.setMsg("密码错误");
        }else{
            info.setMsg("登陆成功");
            info.setUser(user);
        }
        return gson.toJson(info);


    }

}
