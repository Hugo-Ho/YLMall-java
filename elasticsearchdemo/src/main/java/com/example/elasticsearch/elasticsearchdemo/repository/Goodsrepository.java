package com.example.elasticsearch.elasticsearchdemo.repository;

import com.example.elasticsearch.elasticsearchdemo.model.Sku;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface Goodsrepository extends ElasticsearchRepository<Sku,Long> {
}
