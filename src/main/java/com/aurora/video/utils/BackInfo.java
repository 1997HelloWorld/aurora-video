package com.aurora.video.utils;

import com.aurora.video.pojo.Users;
import lombok.Data;
import org.junit.validator.PublicClassValidator;
import org.springframework.stereotype.Component;

@Data
@Component
public class BackInfo {
    private String msg;
    private Object obj;
    private String backUrl;

    public void cleanInfo(){
        this.setMsg(null);
        this.setObj(null);
        this.setBackUrl(null);
    }

}
