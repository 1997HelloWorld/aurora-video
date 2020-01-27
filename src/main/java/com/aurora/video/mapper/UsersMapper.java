package com.aurora.video.mapper;


import com.aurora.video.pojo.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    @Insert("insert into users(username,password,face_image,nickname,fans_counts," +
            "follow_counts,receive_like_counts) " +
            "values(#{username},#{password},#{faceImage},#{nickname},#{fansCounts},#{followCounts}," +
            "#{receiveLikeCounts})")
    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    @Select("select * from users where username = #{username}")
    Users selectOne(@Param("username") String username);

}