package com.aurora.video.vo;

import lombok.Data;

import java.util.Date;
@Data
public class VideoVo {
    private Integer id;

    private Integer userId;

    private String audioId;

    private String videoDesc;

    private String videoPath;

    private Float videoSeconds;

    private Integer videoWidth;

    private Integer videoHeight;

    private String coverPath;

    private Long likeCounts;

    private Integer status;

    private Date createTime;

    private String faceImage;

    private String nickname;
}
