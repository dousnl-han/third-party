package com.dousnl.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/9 17:00
 */
@Data
public class BaseIndex {

    @JSONField(name="_id")
    private String id;
}
