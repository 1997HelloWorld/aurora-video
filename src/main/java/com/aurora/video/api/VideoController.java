package com.aurora.video.api;

import com.aurora.video.pojo.Bgm;
import com.aurora.video.pojo.Videos;
import com.aurora.video.service.BgmService;
import com.aurora.video.service.VideoService;
import com.aurora.video.utils.BackInfo;
import com.aurora.video.utils.FFMpeg;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/video")
@Api(value = "视频业务的接口", tags = {"视频controller"})
public class VideoController extends BasicController {
    @Autowired
    private Gson gson;
    @Autowired
    private BackInfo info;
    @Autowired
    private VideoService videoService;
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "用户上传视频", notes = "用户上传视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID", value = "用户ID", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmID", value = "背景音乐ID", required = false,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "视频时长", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upload", headers = {"content-type=multipart/form-data"}, produces = "text/plain;charset=UTF-8")
    public String uploadFace(String userID, String bgmID, float videoSeconds,
                             int videoWidth, int videoHeight, String desc,
                             @ApiParam(value = "短视频", required = true)
                                     MultipartFile file) throws Exception {

        info.cleanInfo();

        if (StringUtils.isBlank(userID)) {
            info.setMsg("请登陆后重试");
            return gson.toJson(info);
        }

        //文件保存的命名空间
        String filePath = "E:/video_dev";
        //公共BGM保存路径
        String fileMusic = "E:/video_dev";
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userID + "/video";
//        文件最终保存位置
        String finalVideoPath = null;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String filename = null;
        Videos videos = new Videos();
        try {
            if (file != null) {

                filename = file.getOriginalFilename();
                System.out.println("filename:" + filename);
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径（到硬盘）
                    finalVideoPath = filePath + uploadPathDB + "/" + filename;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + filename);
                    File outFile = new File(finalVideoPath);
                    //创建父目录
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);

                    inputStream = file.getInputStream();

                    IOUtils.copy(inputStream, fileOutputStream);

                    Integer uid = Integer.valueOf(userID);


                    //输入信息
                    videos.setUserId(uid);
                    videos.setAudioId(bgmID);
                    videos.setVideoDesc(desc);
                    videos.setVideoPath(uploadPathDB);
                    videos.setVideoSeconds(videoSeconds);
                    videos.setVideoHeight(videoHeight);
                    videos.setVideoWidth(videoWidth);
                    videos.setStatus(1);
                    videos.setCreateTime(new Date());

                    //插入到数据库
                    videoService.firstInsertVideo(videos);

                    System.out.println("============" + videos.getId());


                }
            } else {
                info.setMsg("文件上传出错");
                return gson.toJson(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }

        if (StringUtils.isNotBlank(bgmID)) {
            Bgm bgm = bgmService.queryById(bgmID);
            String mp3Path = fileMusic + bgm.getSrc();
//
//            //文件保存的命名空间
//            String filePath = "E:/video_dev";
//            //公共BGM保存路径
//            String fileMusic = "E:/video_dev";
//            //保存到数据库中的相对路径
//            String uploadPathDB = "/" + userID + "/video";
            // finalVideoPath = filePath + uploadPathDB + "/" +filename;
            FFMpeg ffMpeg = new FFMpeg("D:/ffmpeg/bin/ffmpeg.exe");
            String tempVideoPath = filePath + "/" + userID + "/video" + "/copy_" + filename;
            //存放图片的uuid
            String picUUID = UUID.randomUUID().toString()+".mp4";

            ffMpeg.videoSilencing(finalVideoPath,
                    tempVideoPath);
            ffMpeg.videoMerge(tempVideoPath, String.valueOf(videoSeconds),
                    mp3Path, filePath + "/" + userID + "/video" + "/final_" + picUUID);
            //更新合成视频的路径到数据库
            videoService.updatePathByID(filePath + "/" + userID + "/video" + "/final_" + picUUID,
                    videos.getId());



        } else {
            System.out.println("该视频为原视频，不合成音频");
        }
        info.setObj(videos);
        return gson.toJson(info);
    }

}
