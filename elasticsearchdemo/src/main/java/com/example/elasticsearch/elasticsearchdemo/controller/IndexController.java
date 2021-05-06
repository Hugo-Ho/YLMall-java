package com.example.elasticsearch.elasticsearchdemo.controller;

import com.example.elasticsearch.elasticsearchdemo.model.Sku;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("es")
@Api("index操作")
public class IndexController {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //新建索引
    @PostMapping("index/create")
    public ResponseEntity<Boolean> createIndex(String indexName) {
        Boolean result;
        //result = elasticsearchRestTemplate.indexOps(Sku.class).create();//需要在创建的类中添加注解：@Document(indexName = "hello2",shards = 1, replicas = 0) 由于es版本为7.9.3 故去掉type
        result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).create();
        return ResponseEntity.ok(result);
    }

    //配置映射
    @PutMapping("index/mapping")
    public ResponseEntity<Boolean> mappingField(String indexName,String classMappingJson)
    {
        //Boolean result = elasticsearchRestTemplate.indexOps(Sku.class).putMapping();// 配置映射，会根据类中的字段来自动完成映射
        Boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).putMapping(Document.parse(classMappingJson));// 配置映射，会根据类中的字段来自动完成映射
        return ResponseEntity.ok(result);
    }

    //删除索引
    @DeleteMapping("index/delete")
    public ResponseEntity<Boolean>deleteIndex(String indexName)
    {
        Boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
        return ResponseEntity.ok(result);
    }


}
