package com.aurora.video.service.impl;

import com.aurora.video.mapper.SearchRecordsMapper;
import com.aurora.video.mapper.UsersLikeVideosMapper;
import com.aurora.video.mapper.UsersMapper;
import com.aurora.video.mapper.VideosMapper;
import com.aurora.video.pojo.UsersLikeVideos;
import com.aurora.video.pojo.Videos;
import com.aurora.video.service.VideoService;
import com.aurora.video.utils.PagedResult;
import com.aurora.video.vo.VideoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;
    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public int firstInsertVideo(Videos videos) {
        int video_last_Id = videosMapper.firstInsertVideo(videos);
        return video_last_Id;
    }

    @Override
    public void updatePathByID(String path, int id) {
        videosMapper.updatePathByID(path, id);
    }

    @Override
    public void updateCoverPath(int videoID, String coverPath) {
        videosMapper.updateCoverPathByID(videoID, coverPath);
    }

    @Override
    public PagedResult getAllVideos(String videoDesc, Integer isSaveRecord, Integer page, Integer pageSize) {

        if (isSaveRecord != null && !"".equals(isSaveRecord) && !"undefined".equals(videoDesc)) {
            searchRecordsMapper.insert(videoDesc);
        }

        if (StringUtils.isBlank(videoDesc) || "undefined".equals(videoDesc)) {
            System.out.println("描述为空");
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
        } else {
            System.out.println(videoDesc);
            System.out.println("描述不为空");
            PageHelper.startPage(page, pageSize);
            List<VideoVo> list = videosMapper.queryVideoListByDesc(videoDesc);
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

    /**
     * 获取热搜词
     *
     * @return
     */
    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWordsList();
    }

    /**
     * 点击喜欢按钮事件
     *
     * @param userID
     * @param videoID
     * @param videoCreaterID
     */
    @Override
    public void userLikeVideo(int userID, int videoID, int videoCreaterID) {
        //保存用户和视频的点赞关系表
        System.out.println("保存用户和视频的点赞关系表");
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setUserId(userID);
        usersLikeVideos.setVideoId(videoID);
        usersLikeVideosMapper.saveUserLikeVideo(usersLikeVideos);
        //视频喜欢数量累加
        System.out.println("视频喜欢数量累加");
        videosMapper.addVideoLikeCount(videoID);
        //用户受喜欢数量累加
        System.out.println("用户受喜欢数量累加");
        usersMapper.addReceiveLikeCount(userID);
    }

    @Override
    public void userUnLikeVideo(int userID, int videoID, int videoCreaterID) {
        //保存用户和视频的点赞关系表
        System.out.println("删除用户和视频的点赞关系表");
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setUserId(userID);
        usersLikeVideos.setVideoId(videoID);
        usersLikeVideosMapper.deleteUserLikeVideo(usersLikeVideos);
        //视频喜欢数量累加
        System.out.println("视频喜欢数量累减");
        videosMapper.reduceVideoLikeCount(videoCreaterID);
        //用户受喜欢数量累加
        System.out.println("用户受喜欢数量累减");
        usersMapper.reduceReceiveLikeCount(userID);
    }

    @Override
    public boolean isUserLikeVideo(Integer userID, Integer videoID) {

        if (userID == null || videoID == null) {
            return false;
        }

        UsersLikeVideos usersLikeVideos = usersLikeVideosMapper.queryExample(userID, videoID);
        if (usersLikeVideos != null) {
            return true;
        }

        return false;
    }

    /**
     * 获取我发布的视频列表
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedResult getMyVideoList(Integer userid, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);
        List<VideoVo> list = videosMapper.queryMyVideoList(userid);
        System.out.println("查询到的我的视频数据");
        list.stream().forEach(System.out::println);

        PageInfo<VideoVo> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;

    }

    /**
     * 获取我喜欢的视频列表
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedResult getMyLikeVideoList(Integer userid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Videos> list = videosMapper.queryMyLikeVideoList(userid);
        System.out.println("查询到的我喜欢的视频数据");
        list.stream().forEach(System.out::println);

        PageInfo<Videos> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;

    }
}
