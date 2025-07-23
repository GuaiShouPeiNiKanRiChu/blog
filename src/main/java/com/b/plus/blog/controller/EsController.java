package com.b.plus.blog.controller;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * @description: es的controller类
 * @author: biyunfei3@jd.com
 * @date: 2025-07-23 20:48
 **/
@RestController
public class EsController {

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("/createIndex")
    public Boolean createIndex(String indexName) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 设置分片和副本
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 1)
        );

        // 设置mappings
        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"name\": { \"type\": \"keyword\" },\n" +
                        "    \"age\": { \"type\": \"integer\" },\n" +
                        "    \"description\": { \"type\": \"text\" }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/addDoc")
    public Boolean addDoc() throws IOException {
        IndexRequest request = new IndexRequest().index("index_operation");
        // 指定ID，也可以不指定随机生成
        request.id("21");
        // 这里用Map，也可以创建Java对象操作
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 18);
        map.put("description", "一个学习ES的学生");
        // map转换json字符串
        request.source(JSON.toJSONString(map), XContentType.JSON);
        // 请求es
        client.index(request, RequestOptions.DEFAULT);
        return true;
    }

    @RequestMapping("/getDoc")
    public GetResponse getDoc() throws IOException {
        GetRequest request = new GetRequest()
                .index("index_operation")
                .id("21");
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        return getResponse;
    }

}
