package com.aurora.video.service;

import com.aurora.video.pojo.Users;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    /*
     *   判断用户名是否存在
     * */
    public boolean queryUserNameIsExist(Users users);

    /*
     * 保存用户
     * */
    public void saveUser(Users users);

    public Users queryUserByName(String username);
}
