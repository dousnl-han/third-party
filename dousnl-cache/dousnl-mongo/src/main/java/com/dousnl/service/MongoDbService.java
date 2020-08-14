package com.dousnl.service;

import com.dousnl.domain.Book;
import com.dousnl.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/19 19:11
 */
@Service
public class MongoDbService {

    private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     * @param book
     * @return
     */
    public String saveObj(Book book) {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        Query queryNote = new Query(where("_id").is("5"));
        Update update = new Update();
        //update.inc("likeCount", 2);
        //update.inc("dislikeCount", 1);
        update.inc("likeNum", -1);
        mongoTemplate.updateFirst(queryNote, update, Book.class);
        //mongoTemplate.save(book);
        return "添加成功";
    }

    /**
     * 保存对象
     * @param order
     * @return
     */
    public String saveOrder(Order order) {
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        mongoTemplate.save(order);
        return "添加成功";
    }

    public List<Order> findOrder(String name) {
        Query query = new Query();
        if (!StringUtils.isEmpty(name)){
            Pattern pattern = Pattern.compile("^.*" + name + ".*$" , Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        }
        return mongoTemplate.find(query, Order.class);
    }

    /**
     * 查询所有
     * @return
     */
    public List<Book> findAll() {
        Query query = new Query();
        query.with(new Sort("id"));
        // 每页五个
        //Pageable pageable = new PageRequest(1, 10); // get 5 profiles on a page
        //query.with(pageable);
        return mongoTemplate.find(query, Book.class);
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    public Book getBookById(String id) {
        Query query = new Query(Criteria.where("resourceId").is(id).and("platforms").in(Arrays.asList(1,2,3)));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Book getBookByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 更新对象
     *
     * @param book
     * @return
     */
    public String updateBook(Book book) {
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish", book.getPublish()).set("info", book.getInfo()).set("updateTime",
                new Date());
        // updateFirst 更新查询返回结果集的第一条
        update.inc("likeCount", 2);
        mongoTemplate.updateFirst(query, update, Book.class);
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query,update,Book.class);
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query,update,Book.class);
        return "success";
    }

    /***
     * 删除对象
     * @param book
     * @return
     */
    public String deleteBook(Book book) {
        mongoTemplate.remove(book);
        return "success";
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public String deleteBookById(String id) {
        // findOne
        Book book = getBookById(id);
        // delete
        deleteBook(book);
        return "success";
    }

    /**
     * 模糊查询
     * @param search
     * @return
     */
    public List<Book> findByLikes(String search){
        Query query = new Query();
        Pattern pattern = Pattern.compile("^.*" + search + ".*$" , Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        query.with(new Sort("id"));
        query.limit(1);
        List<Book> lists = mongoTemplate.find(query, Book.class);

        //List<Book> lists = mongoTemplate.findAllAndRemove(query, Book.class);
        return lists;
    }

    public long count(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        long count = mongoTemplate.count(query, Book.class);
        return  mongoTemplate.count(query,Book.class);
    }
}
