package com.aurora.video.service.impl;

import com.aurora.video.mapper.VideosMapper;
import com.aurora.video.pojo.Videos;
import com.aurora.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;

    @Override
    public int firstInsertVideo(Videos videos) {
        int video_last_Id = videosMapper.firstInsertVideo(videos);
        return video_last_Id;
    }

    @Override
    public void updatePathByID(String path, int id) {
        videosMapper.updatePathByID(path,id);
    }
}
