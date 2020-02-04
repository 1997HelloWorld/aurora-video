package com.aurora.video.vo;

import com.aurora.video.pojo.Users;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PublisherVo {
    private Users publisher;
    private boolean userLikeVideo;

}
