package com.aurora.video.service;

import com.aurora.video.pojo.Videos;
import com.aurora.video.utils.PagedResult;
import com.aurora.video.vo.VideoVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoService {
    //插入视频信息
    int firstInsertVideo(Videos videos);
    //更新视频路径
    void updatePathByID(String path,int id);
    //更新视频封面
    void updateCoverPath(int videoID,String coverPath);

    /**
     *
     * @param page  第几页
     * @param pageSize   页面数据量
     * @return
     */
    PagedResult getAllVideos(String videoDesc, Integer isSaveRecord,Integer page,Integer pageSize);


    List<String> getHotWords();


    void userLikeVideo(int userID,int videoID,int videoCreaterID);
    void userUnLikeVideo(int userID,int videoID,int videoCreaterID);


    public boolean isUserLikeVideo(Integer userID,Integer videoID);

    public PagedResult getMyVideoList(Integer userid,Integer page ,Integer pageSize);

    public PagedResult getMyLikeVideoList(Integer userid,Integer page ,Integer pageSize);

}
