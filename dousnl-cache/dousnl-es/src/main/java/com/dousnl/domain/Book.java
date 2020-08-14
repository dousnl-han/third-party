package com.dousnl.domain;

import com.dousnl.annontion.EsIndex;
import com.dousnl.annontion.EsType;
import lombok.Data;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/9 15:40
 */
@Data
@EsIndex("book_index")
@EsType("book")
public class Book extends BaseIndex{
    private String name;
    private String desc;
    private double price;
    private Integer count;
}
