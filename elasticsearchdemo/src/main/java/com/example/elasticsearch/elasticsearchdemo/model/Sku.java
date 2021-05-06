package com.example.elasticsearch.elasticsearchdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Document(indexName = "hello2",shards = 1, replicas = 0)
@Data
public class Sku {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String skuID;
    @Field(type = FieldType.Keyword)
    private String skuName;
    @Field(type = FieldType.Integer)
    private int skuType;
    @Field(type = FieldType.Text)
    private String skuSource;
    @Field(type = FieldType.Text)
    private String skuAgentSource;
    @Field(type = FieldType.Date)
    private Date createTime;
    @Field(type = FieldType.Keyword)
    private String productArea;
    @Field(type = FieldType.Keyword)
    private String brandName;
    @Field(type = FieldType.Keyword)
    private String isEnergySaving;
    @Field(type = FieldType.Keyword)
    private String capacity;
    @Field(type = FieldType.Keyword)
    private String firstCataName;
    @Field(type = FieldType.Keyword)
    private String secondCataName;
    @Field(type = FieldType.Keyword)
    private String thirdCataName;
}
