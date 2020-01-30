package com.aurora.video.mapper;


import com.aurora.video.pojo.Videos;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosMapper {
    int deleteByPrimaryKey(String id);

    @Insert("insert into videos(userid,videodesc,videopath,videoseconds," +
            "videowidth,videoheight,audioid,createtime,status) values (#{userId},#{videoDesc}," +
            "#{videoPath},#{videoSeconds},#{videoWidth},#{videoHeight},#{audioId},#{createTime}," +
            "#{status})")
    @SelectKey(statement = "select last_insert_id()", before = false, keyProperty = "id", resultType = Integer.class, keyColumn = "id")
    int firstInsertVideo(Videos record);

    int insertSelective(Videos record);

    Videos selectByPrimaryKey(String id);

    @Update("update videos set videopath = #{videoPath} where id = #{id} ")
    int updatePathByID(String videoPath, int id);

    int updateByPrimaryKey(Videos record);
}