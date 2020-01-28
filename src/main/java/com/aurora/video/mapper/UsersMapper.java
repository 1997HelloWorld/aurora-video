package com.aurora.video.mapper;


import com.aurora.video.pojo.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper {

    @Insert("insert into users(username,password,faceimage,nickname,fanscounts," +
            "followcounts,receivelikecounts) " +
            "values(#{username},#{password},#{faceImage},#{nickname},#{fansCounts},#{followCounts}," +
            "#{receiveLikeCounts})")
    int insert(Users record);

    @Select("select * from users where id = #{id}")
    Users selectUserByID(@Param("id") Integer id);



    @Update("update users set username = #{username},faceimage = #{faceImage}," +
            "Password=#{password},nickname=#{nickname},fanscounts=#{fansCounts}," +
            "followcounts=#{followCounts},receivelikecounts=#{receiveLikeCounts} where id = #{id}")
    int updateByPrimaryKey(Users user);

    @Select("select * from users where username = #{username}")
    Users selectOne(@Param("username") String username);

}