package com.dousnl.controller;

import com.alibaba.fastjson.JSON;
import com.dousnl.domain.EsIndexAndTypeParam;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/7/3 11:05
 */
@Slf4j
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/addindex")
    public String addIndex(@RequestBody EsIndexAndTypeParam param) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>(2);
        //System.out.println("before resize:"+jsonMap.size());
        jsonMap.put("user", "laimailai");
        jsonMap.put("postDate", new Date());
        //System.out.println("before2 resize:"+jsonMap.size());
        jsonMap.put("message", "trying out Elasticsearch");
        //System.out.println("after resize:"+jsonMap.size());
        long id = System.currentTimeMillis();
        IndexRequest indexRequest = new IndexRequest(param.getIndex(), param.getType(), String.valueOf(id))
                .source(jsonMap);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        RestStatus status = indexResponse.status();
        return status.toString();
    }

    @PostMapping("/getindex")
    public String getIndex(@RequestBody EsIndexAndTypeParam param) throws IOException {

        GetRequest getRequest=new GetRequest(param.getIndex(),param.getType(),param.getId());
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> source = response.getSource();
        return JSON.toJSONString(source);
    }

    @PostMapping("/updateindex")
    public String updateIndex(@RequestBody EsIndexAndTypeParam param) throws IOException {
        UpdateRequest  upadteRequest=new UpdateRequest(param.getIndex(),param.getType(),param.getId());
        Map<String, Object> jsonMap = new HashMap<>(4);
        jsonMap.put("user", "laimailai");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch update");
        upadteRequest.doc(jsonMap);
        UpdateResponse update = client.update(upadteRequest, RequestOptions.DEFAULT);
        GetResult getResult = update.getGetResult();
        return JSON.toJSONString(getResult);
    }

    @PostMapping("/findall")
    public String findIndex(@RequestBody EsIndexAndTypeParam param) throws IOException {
        SearchRequest searchRequest=new SearchRequest(param.getIndex());
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        List<String> list= Lists.newArrayList();
        for (SearchHit hit:hits1){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //log.info(">>>>>>sourceAsMap:{}",sourceAsMap);
            String sourceAsString = hit.getSourceAsString();
            //log.info(">>>>>>sourceAsString:{}",sourceAsString);
            list.add(sourceAsString);
        }
        log.info(list.toString());
        return list.toString();
    }

    @PostMapping("/findterm")
    public String findTerm(@RequestBody EsIndexAndTypeParam param) throws IOException {
        //根据多个条件搜索
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //TermQueryBuilder termQueryBuilder=new TermQueryBuilder("_id",param.getId());
        //boolQueryBuilder.should(termQueryBuilder);
        TermQueryBuilder termQueryBuilder=new TermQueryBuilder("user",param.getType());
        boolQueryBuilder.should(termQueryBuilder);

        SearchRequest searchRequest = new SearchRequest(param.getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit:hits){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info(">>>>>>sourceAsMap:{}",sourceAsMap);
        }
        return response.toString();
    }
}
