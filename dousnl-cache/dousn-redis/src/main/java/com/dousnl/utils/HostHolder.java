package com.dousnl.utils;

import com.dousnl.domain.User;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/14 13:23
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> local=new ThreadLocal<User>();

    public static User getUser(){
        User user = local.get();
        if (user==null) {return new User(31);}
        return local.get();
    }

    public static void setLocal(User u){
        local.set(u);
    }
}
