package com.aurora.video.mapper;
import com.aurora.video.pojo.Videos;
import com.aurora.video.vo.VideoVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    @Update("update videos set coverpath = #{coverPath} where id = #{id}")
    void updateCoverPathByID(int id,String coverPath);

    @Select("SELECT v.*, u.nickname,u.faceimage FROM videos v LEFT JOIN users u ON u.id = v.userid\n" +
            "WHERE 1=1 AND v.status =1 \n" +
            "ORDER BY v.createtime DESC ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "userid", property = "userId"),
            @Result(column = "audioid", property = "audioId"),
            @Result(column = "videopath", property = "videoPath"),
            @Result(column = "videoseconds", property = "videoSeconds"),
            @Result(column = "videowidth", property = "videoWidth"),
            @Result(column = "videoheight", property = "videoHeight"),
            @Result(column = "coverpath", property = "coverPath"),
            @Result(column = "likecounts", property = "likeCounts"),
            @Result(column = "status", property = "status"),
            @Result(column = "createtime", property = "createTime"),
            @Result(column = "faceimage", property = "faceImage"),
            @Result(column = "nickname", property = "nickname"),
    })
    List<VideoVo> queryVideoList();
}