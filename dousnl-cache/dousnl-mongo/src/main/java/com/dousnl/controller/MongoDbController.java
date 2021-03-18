package com.dousnl.controller;

import com.dousnl.domain.Book;
import com.dousnl.domain.Order;
import com.dousnl.domain.entity.AdvertImageDTO;
import com.dousnl.mapper.AdvertImageDTOMapper;
import com.dousnl.service.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/19 19:13
 */
@RestController
public class MongoDbController {

    @Autowired
    private MongoDbService mongoDbService;
    @Autowired
    private AdvertImageDTOMapper advertImageDTOMapper;
    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/mongo/save")
    public String saveObj(@RequestBody Book book) {
        return mongoDbService.saveObj(book);
    }

    @PostMapping("/mongo/saveImage")
    public String saveImage(String group) {
        Example example=new Example(AdvertImageDTO.class);
        example.createCriteria().andEqualTo("delFlag",1)
                .andEqualTo("groupCode","intellect");
        List<AdvertImageDTO> advertImageDTOS = advertImageDTOMapper.selectByExample(example);
        mongoTemplate.insertAll(advertImageDTOS);
        return "success";
    }

    @PostMapping(value = "/mongo/getImageIds")
    public List<AdvertImageDTO> getImageIds(@RequestBody List<Integer> ids) {
        long start=System.currentTimeMillis();
        Query query = new Query(Criteria.where("_id").in(ids));
        List<AdvertImageDTO> imageDTOS = mongoTemplate.find(query, AdvertImageDTO.class);
        System.out.println((System.currentTimeMillis()-start)+"ms");
        return imageDTOS;
    }
    @PostMapping(value = "/mongo/sqlImageIds")
    public List<AdvertImageDTO> sqlImageIds(@RequestBody List<Integer> ids) {
        long start=System.currentTimeMillis();
        Example example=new Example(AdvertImageDTO.class);
        example.createCriteria().andIn("id",ids);
        List<AdvertImageDTO> advertImageDTOS = advertImageDTOMapper.selectByExample(example);
        System.out.println((System.currentTimeMillis()-start)+"ms");
        return advertImageDTOS;
    }


    @PostMapping("/mongo/save/order")
    public String saveObj(@RequestBody Order order) {
        return mongoDbService.saveOrder(order);
    }

    @PostMapping("/mongo/find/order")
    public List<Order> saveObj(@RequestParam(required = false) String name) {
        return mongoDbService.findOrder(name);
    }

    @GetMapping("/mongo/findAll")
    public List<Book> findAll() {
        return mongoDbService.findAll();
    }

    @GetMapping("/mongo/count")
    public long count(@RequestParam(required = false)String name) {
        return mongoDbService.count(name);
    }

    @GetMapping("/mongo/findOne")
    public Book findOne(@RequestParam String id) {
        return mongoDbService.getBookById(id);
    }

    @GetMapping("/mongo/findOneByName")
    public Book findOneByName(@RequestParam String name) {
        return mongoDbService.getBookByName(name);
    }

    @PostMapping("/mongo/update")
    public String update(@RequestBody Book book) {
        return mongoDbService.updateBook(book);
    }

    @PostMapping("/mongo/delOne")
    public String delOne(@RequestBody Book book) {
        return mongoDbService.deleteBook(book);
    }

    @GetMapping("/mongo/delById")
    public String delById(@RequestParam String id) {
        return mongoDbService.deleteBookById(id);
    }

    @GetMapping("/mongo/findlikes")
    public List<Book> findByLikes(@RequestParam String search) {
        return mongoDbService.findByLikes(search);
    }
}
