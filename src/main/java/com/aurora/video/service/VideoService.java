package com.aurora.video.service;

import com.aurora.video.pojo.Videos;
import com.aurora.video.utils.PagedResult;
import org.springframework.stereotype.Component;

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
    PagedResult getAllVideos(Integer page,Integer pageSize);

}
