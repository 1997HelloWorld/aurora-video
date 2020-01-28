package com.aurora.video.api;

import com.aurora.video.pojo.Users;
import com.aurora.video.service.UserService;
import com.aurora.video.utils.BackInfo;
import com.aurora.video.vo.UsersVo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/user")
@Api(value = "用户相关业务的接口", tags = {"用户业务controller"})
public class UserController extends BasicController {
    @Autowired
    private Gson gson;
    @Autowired
    private UserService userService;
    @Autowired
    private BackInfo info;

    @ApiOperation(value = "用户上传头像", notes = "用户上传头像接口")
    @RequestMapping(value = "/uploadFace",produces = "text/plain;charset=UTF-8")
    public String uploadFace(String userID,
                             @RequestParam("file") MultipartFile[] files) throws Exception {

        Integer uid = Integer.valueOf(userID);
        if(userID==null){
            info.setMsg("请登陆后重试");
            return gson.toJson(info);
        }

        //文件保存的命名空间
        String filePath = "E:/video_dev";
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userID + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (files != null && files.length > 0) {

                String filename = files[0].getOriginalFilename();
                System.out.println("filename:"+filename);
                if (StringUtils.isNotBlank(filename)) {
                    //文件上传的最终保存路径（到硬盘）
                    String finalFacePath = filePath + uploadPathDB + "/" + filename;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + filename);
                    File outFile = new File(finalFacePath);
                    //创建父目录
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);

                    inputStream = files[0].getInputStream();

                    IOUtils.copy(inputStream, fileOutputStream);


                    Users usersFromDB = userService.queryUserByUserID(uid);

                    usersFromDB.setFaceImage(uploadPathDB);
                    //返回一个修改的结果,200说明正常
                    String s = userService.updateUserInfo(usersFromDB);
                    if("200".equals(s)){
                        info.setMsg("200");
                        info.setBackUrl(uploadPathDB);
                    }else {
                        info.setMsg(s);
                    }

                }
            }else {
                info.setMsg("文件上传出错");
                return gson.toJson(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }


        return gson.toJson(info);
    }

    @ApiOperation(value = "查询用户信息", notes = "粉丝数，关注数。。。")
    @RequestMapping(value = "/query",produces = "text/plain;charset=UTF-8")
    public String query(@RequestBody String userID){

        info.cleanInfo();

        if(StringUtils.isBlank(userID)){
            info.setMsg("请登录");
            return gson.toJson(info);
        }

        Integer uid = Integer.valueOf(userID);
        Users users = userService.queryUserByUserID(uid);
        users.setPassword("");
        System.out.println("用户信息"+users);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users,usersVo);
        info.setObj(usersVo);
        return gson.toJson(info);
    }
}
