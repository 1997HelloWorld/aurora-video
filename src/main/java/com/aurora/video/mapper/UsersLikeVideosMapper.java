package com.aurora.video.mapper;


import com.aurora.video.pojo.UsersLikeVideos;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersLikeVideosMapper {
    @Insert("insert into users_like_videos (user_id,video_id) values(#{userId},#{videoId})")
    void saveUserLikeVideo(UsersLikeVideos usersLikeVideos);
    @Delete("delete from users_like_videos where user_id = #{userId} and video_id = #{videoId}")
    void deleteUserLikeVideo(UsersLikeVideos usersLikeVideos);
    @Select("select * from users_like_videos where user_id = #{userId} and video_id = #{videoId}" )
    UsersLikeVideos queryExample(Integer userId,Integer videoId);

}