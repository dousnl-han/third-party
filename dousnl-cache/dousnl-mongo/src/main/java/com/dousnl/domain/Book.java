package com.dousnl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/19 19:10
 */
@Data
@Document(collection="book")
public class Book {

    @Id
    private String id;
    private Integer price;
    private String name;
    private String info;
    private String publish;
    private Date createTime;
    private Date updateTime;

}
