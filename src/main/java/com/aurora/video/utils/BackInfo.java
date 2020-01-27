package com.aurora.video.utils;

import com.aurora.video.pojo.Users;
import lombok.Data;
import org.junit.validator.PublicClassValidator;
import org.springframework.stereotype.Component;

@Data
@Component
public class BackInfo {
    private String msg;
    private Users user;

    public void cleanInfo(){
        this.setMsg(null);
        this.setUser(null);
    }

}
