package com.example.elasticsearch.elasticsearchdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.elasticsearch.elasticsearchdemo.model.Sku;
import com.example.elasticsearch.elasticsearchdemo.repository.Goodsrepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.management.Agent;

import java.util.*;

@RestController
@RequestMapping("es")
@Api("es查询操作")
public class DocumentController {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private Goodsrepository goodsrepository;

    @PostMapping("document/createOneDocumentByJsonClass")
    @ApiOperation(value = "添加一个document，支持批量插入，同步创建json类的mapping")
    public ResponseEntity<Void> createOneDocumentByJsonClass(String indexName, String jsonString) {
        elasticsearchRestTemplate.save(JSON.parseObject(jsonString), IndexCoordinates.of(indexName));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("document/createOneDocumentByClass")
    @ApiOperation(value = "添加一个document，同步创建指定类的mapping，使用类中指定的index")
    public ResponseEntity<Void> createOneDocumentByClass(Sku sku) {
        //使用继承仓储类
        goodsrepository.save(sku);
        //使用原生template
        elasticsearchRestTemplate.save(sku);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("document/createManyDocumentByClass")
    @ApiOperation(value = "批量添加document")
    public ResponseEntity<Void> createManyDocumentByClass(String indexName, List<Sku> skus) {
        goodsrepository.saveAll(skus);
        List<IndexQuery> indexQueryList = new ArrayList<>();
        for (Sku sku : skus) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(sku);
            indexQueryList.add(indexQuery);
        }
        elasticsearchRestTemplate.bulkIndex(indexQueryList, BulkOptions.defaultOptions(), IndexCoordinates.of(indexName));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("document/searchOneById")
    @ApiOperation(value = "查询index的指定ID的document")
    public ResponseEntity<Object> searchOneById(String id) {
        Optional<Sku> result = goodsrepository.findById(Long.parseLong(id));
        elasticsearchRestTemplate.get(id, Sku.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("document/searchAllByIndex")
    @ApiOperation(value = "基于repository的查询index的所有document")
    public ResponseEntity<Object> searchAllByIndex() {
        return ResponseEntity.ok(goodsrepository.findAll());
    }

    @GetMapping("document/matchquery")
    @ApiOperation(value = "基于repository的匹配查询")
    public ResponseEntity<Object> queryMatch(String key, String value) {
        // 构建查询条件 NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery(key, value));
        // 执行搜索，获取结果
        Page<Sku> skus = this.goodsrepository.search(queryBuilder.build());
        return ResponseEntity.ok(skus);
    }

    @GetMapping("document/pagequery")
    @ApiOperation(value = "基于repository的分页查询")
    public ResponseEntity<Object> queryPage(String key, String value, int page, int size) {
        // 构建查询条件 NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery(key, value));
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 执行搜索，获取结果
        Page<Sku> skus = this.goodsrepository.search(queryBuilder.build());
        return ResponseEntity.ok(skus);
    }

    @GetMapping("document/sortquery")
    @ApiOperation(value = "基于repository的排序查询")
    public ResponseEntity<Object> querySort(String key, String value, String sortKey, SortOrder sort) {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery(key, value));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort(sortKey).order(sort));
        // 执行搜索，获取结果
        Page<Sku> skus = this.goodsrepository.search(queryBuilder.build());
        return ResponseEntity.ok(skus);
    }

    @GetMapping("document/aggquery")
    @ApiOperation(value = "基于repository的聚合为桶查询，桶就是分组")
    public ResponseEntity<Object> queryAgg(String aggKey) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms(aggKey + "s").field(aggKey));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Sku> aggPage = (AggregatedPage<Sku>) this.goodsrepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation(aggKey + "s");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        Map<String, Long> result = new HashMap<>();
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
            result.put(bucket.getKeyAsString(), bucket.getDocCount());
        }
        ;
        return ResponseEntity.ok(result);
    }

    @GetMapping("document/subaggquery")
    @ApiOperation(value = "基于repository的嵌套聚合查询，求平均值")
    public ResponseEntity<Object> querySubAgg(String aggKey, String subKey) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms(aggKey + "s").field(aggKey)
                        .subAggregation(AggregationBuilders.avg(subKey + "Avg").field(subKey)) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Sku> aggPage = (AggregatedPage<Sku>) this.goodsrepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation(aggKey + "s");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        Map<String, String> result = new HashMap<>();
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            result.put(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台", "平均售价：" + avg.getValue());
        }
        return ResponseEntity.ok(result);
    }
}
