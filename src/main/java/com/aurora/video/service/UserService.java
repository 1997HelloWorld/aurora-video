package com.aurora.video.service;

import com.aurora.video.pojo.Users;
import org.springframework.stereotype.Component;
@Component
public interface UserService {
    /**
     * 判断用户名是否存在
     * @param users
     * @return boolean
     */
    public boolean queryUserNameIsExist(Users users);

    /**
     * 保存用户
     * @param users
     */
    public void saveUser(Users users);

    /**
     * 通过名称 / id 查询，该用户是否存在
     * @param username
     * @return users
     */
    public Users queryUserByName(String username);

    /**
     *
     * @param id
     * @return
     */
    public Users queryUserByUserID(Integer id);

    /**
     * 用户修改信息
     * @param users
     */
    public String updateUserInfo(Users users);
}
