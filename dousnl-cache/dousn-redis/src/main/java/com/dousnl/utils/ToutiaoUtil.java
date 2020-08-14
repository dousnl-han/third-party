package com.dousnl.utils;

import com.alibaba.fastjson.JSON;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/14 13:41
 */
public class ToutiaoUtil {
    public static String getJSONString(int i, String s) {
        try {
            String jsonString = JSON.toJSONString(s);
            return jsonString;
        } catch (Exception e) {
            return JSON.toJSONString(i);
        }
    }
}
