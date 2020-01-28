package com.aurora.video.api;

import com.aurora.video.pojo.Users;
import com.aurora.video.service.impl.UserServiceImpl;
import com.aurora.video.utils.BackInfo;
import com.aurora.video.utils.MD5;
import com.aurora.video.utils.RedisOperator;
import com.aurora.video.vo.UsersVo;
import com.google.gson.Gson;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "这是用户注册和登录的接口", tags = {"用户登录注册的controller"})
public class RegisterLoginController extends BasicController{
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
            users.setFansCounts(0);
            users.setFollowCounts(0);
            users.setReceiveLikeCounts(0);
            userService.saveUser(users);
            info.setMsg("注册成功");

            //loginUser是database中的数据
            Users loginUser = userService.queryUserByName(users.getUsername());
            UsersVo usersVo = setUserRedisSessionToken(loginUser);
            loginUser.setPassword("");
            info.setObj(usersVo);

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
            info.setMsg("密码错误");
        }else{
            UsersVo usersVo = setUserRedisSessionToken(user);

            info.setMsg("登陆成功");
            user.setPassword("");
            info.setObj(usersVo);
        }
        return gson.toJson(info);
    }

    /**
     *
     * @param userID
     * @return
     */
    @ApiOperation(value = "用户注销" ,notes = "用户注销")
    @ApiImplicitParam(name = "userID" ,value = "用户ID" ,required = true,
                     dataType = "application/json" ,paramType = "query")
    @PostMapping("/logout")
    public String logout(@RequestBody String userID){
        info.cleanInfo();
        redisOperator.del(USER_REDIS_SESSION+":"+userID);
        info.setObj(USER_REDIS_SESSION+":"+userID);
        info.setMsg("200");
        return gson.toJson(info);
    }





    /**
     *
     * @param loginUser 当前尝试登陆 / 注册的用户
     * @return  一个带有token的vo
     */
    public UsersVo setUserRedisSessionToken(Users loginUser){
        System.out.println("看看有没有ID"+loginUser);
        //将用户信息存入redis
        String uniqueToken = UUID.randomUUID().toString();
        redisOperator.set(USER_REDIS_SESSION+":"+ loginUser.getId(),uniqueToken,1000*60*30);
        UsersVo usersVo = new UsersVo();
        //把loginuser拷贝到vo
        BeanUtils.copyProperties(loginUser,usersVo);
        System.out.println(usersVo);
        usersVo.setUserToken(uniqueToken);
        return usersVo;
    }

}
