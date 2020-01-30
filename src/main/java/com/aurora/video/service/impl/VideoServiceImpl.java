package com.aurora.video.service.impl;

import com.aurora.video.mapper.VideosMapper;
import com.aurora.video.pojo.Videos;
import com.aurora.video.service.VideoService;
import com.aurora.video.utils.PagedResult;
import com.aurora.video.vo.VideoVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public void updateCoverPath(int videoID, String coverPath) {
        videosMapper.updateCoverPathByID(videoID,coverPath);
    }

    @Override
    public PagedResult getAllVideos(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideoVo> list = videosMapper.queryVideoList();
        System.out.println("查询到的数据");
        list.stream().forEach(System.out::println);

        PageInfo<VideoVo> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
}
