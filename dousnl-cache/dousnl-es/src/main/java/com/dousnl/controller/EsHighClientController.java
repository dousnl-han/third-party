package com.dousnl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dousnl.annontion.EsIndex;
import com.dousnl.annontion.EsType;
import com.dousnl.domain.Book;
import com.dousnl.domain.EsIndexAndTypeParam;
import com.dousnl.util.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/9 15:05
 */
@Slf4j
@RestController
@RequestMapping("/eshigh")
public class EsHighClientController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/index/create")
    public boolean addIndex(@RequestBody EsIndexAndTypeParam param) throws IOException {

        CreateIndexRequest request=new CreateIndexRequest("create_index");
        request.settings(Settings.builder()
                .put("index.number_of_shards",5)
                .put("index.number_of_replicas",1));
        request.mapping("tcc",
                "  {\n" +
                        "    \"tcc\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"name\": {\n" +
                        "          \"type\": \"keyword\"\n" +
                        "        },\n" +
                        "        \"age\": {\n" +
                        "          \"type\": \"long\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }",
                XContentType.JSON);
        request.timeout("1m");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        return acknowledged;
    }

    @DeleteMapping("/index/delete")
    public boolean deleteIndex(String index) throws Exception {

        DeleteIndexRequest request=new DeleteIndexRequest(index);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = delete.isAcknowledged();
        //如果没成功，抛出异常
        if (!delete.isAcknowledged()) {
            throw new Exception(String.format("delete index %s error", index));
        }
        System.out.println(String.format("delete index %s success", index));
        return acknowledged;
    }

    @PostMapping("/type/add")
    public int addType(@RequestBody Book book) throws Exception {
        Class<? extends Book> aClass = book.getClass();
        EsIndex esIndex = aClass.getDeclaredAnnotation(EsIndex.class);
        EsType esType = aClass.getDeclaredAnnotation(EsType.class);

        String globalId = getGlobalId();

        IndexRequest request=new IndexRequest(esIndex.value(),esType.value());
        request.id(globalId);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(book));

        System.out.println("before:"+jsonObject);
        jsonObject.remove("_id");
        System.out.println("after:"+jsonObject);

        request.source(jsonObject);
        request.timeout("1m");
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        RestStatus restStatus = response.status();

        //如果没成功，抛出异常
        if (201!=restStatus.getStatus() && 200!=restStatus.getStatus()) {
            throw new Exception(String.format("add type %s error", esType.value()));
        }
        System.out.println(String.format("add type %s success", esType.value()));
        return restStatus.getStatus();
    }

    private String getGlobalId() throws Exception {

        // 获取URL
        // 通过url获取File的绝对路径
        File file = new File("E:\\my-workspace\\third-party\\dousnl-cache\\dousnl-es\\src\\main\\resources\\id.properties");
        if (file.exists()) {
            List<String> strings = MyFileUtils.readFileContent(file.getPath());
            if (!CollectionUtils.isEmpty(strings)) {
                Integer id = Integer.parseInt(strings.get(0));
                id++;
                MyFileUtils.fileLinesWrite(file.getPath(), id.toString(), false);
                return id.toString();
            }
        }
        throw new Exception("can not get global id");
    }


}
