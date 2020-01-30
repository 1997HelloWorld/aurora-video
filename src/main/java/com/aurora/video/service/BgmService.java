package com.aurora.video.service;

import com.aurora.video.pojo.Bgm;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface BgmService {
    List<Bgm> queryBgmList();

    Bgm queryById(String bgmID);
}
