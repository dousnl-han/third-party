package com.dousnl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/19 19:10
 */
@Data
@Document(collection="order")
public class Order {

    @Id
    private String id;
    private String orderNo;
    private String name;
    private BigDecimal price;
    private String info;
    private Date createTime;
    private Date updateTime;

}
