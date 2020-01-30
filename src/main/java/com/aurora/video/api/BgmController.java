package com.aurora.video.api;

import com.aurora.video.pojo.Bgm;
import com.aurora.video.service.BgmService;
import com.aurora.video.service.UserService;
import com.aurora.video.utils.BackInfo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(value = "背景音乐业务的接口", tags = {"背景音乐controller"})
@RestController
@RequestMapping("/bgm")
public class BgmController {
    @Autowired
    private Gson gson;
    @Autowired
    private BgmService bgmService;
    @Autowired
    private BackInfo info;

    @ApiOperation(value = "查询背景音乐", notes = "查询背景音乐list")
    @GetMapping(value = {"/list"}, produces = "text/plain;charset=UTF-8")
    public String queryBgmList(){
        info.cleanInfo();
        List<Bgm> bgms = bgmService.queryBgmList();
        info.setObj(bgms);
        return gson.toJson(info);
    }
}
