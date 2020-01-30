package com.aurora.video.service;

import com.aurora.video.pojo.Videos;
import org.springframework.stereotype.Component;

@Component
public interface VideoService {

    int firstInsertVideo(Videos videos);

    void updatePathByID(String path,int id);

}
