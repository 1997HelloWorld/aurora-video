package com.aurora.video.service.impl;

import com.aurora.video.mapper.BgmMapper;
import com.aurora.video.pojo.Bgm;
import com.aurora.video.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        List<Bgm> bgms = bgmMapper.selectAll();
        return bgms;
    }

    @Override
    public Bgm queryById(String bgmID) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(bgmID);
        return bgm;
    }
}
