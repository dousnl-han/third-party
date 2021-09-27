package com.dousnl.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Description: TODO
 * Company    : 上海黄豆网络科技有限公司
 *
 * @author : hanliangliang
 * Date       : 2021/9/27
 * Modify     : 修改日期          修改人员        修改说明          JIRA编号
 * v1.0.0       2021/9/27       hanliangliang     新增              1001
 ********************************************************************/
@Data
public class User {

    private String name;
    private Integer age;
    private String address;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
