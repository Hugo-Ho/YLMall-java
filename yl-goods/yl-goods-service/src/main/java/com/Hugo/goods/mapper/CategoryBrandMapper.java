package com.Hugo.goods.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {

    void insertCategoryBrand(@Param("categoryIds") List<Long> categoryIds, @Param("brandId") Long brandId) throws DataAccessException;

    List<Long>queryCategoryIdsByBarandId(Long brandId) throws DataAccessException;
}
