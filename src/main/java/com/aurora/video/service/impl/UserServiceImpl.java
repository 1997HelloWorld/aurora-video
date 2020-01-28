package com.aurora.video.service.impl;

import com.aurora.video.mapper.UsersMapper;
import com.aurora.video.pojo.Users;
import com.aurora.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;


    @Override
    public boolean queryUserNameIsExist(Users users) {
        String username = users.getUsername();
        Users user = usersMapper.selectOne(username);

                return user == null ? false:true;
    }

    @Override
    public void saveUser(Users users) {
        usersMapper.insert(users);
    }

    @Override
    public Users queryUserByName(String username) {
        Users user = usersMapper.selectOne(username);
        return user;
    }

    /**
     * 通过userID查询users
     * @param id
     * @return
     */
    @Override
    public Users queryUserByUserID(Integer id) {

        Users usersFromDB = usersMapper.selectUserByID(id);
        return usersFromDB;
    }

    @Override
    public String updateUserInfo(Users users) {
        int i = usersMapper.updateByPrimaryKey(users);
        if(i==1){
            return "200";
        }
        return "修改失败";
    }
}
