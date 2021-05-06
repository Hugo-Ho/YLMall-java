package com.Hugo.goods.mapper;

import com.Hugo.goods.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface CategoryMapper{
    List<Category> queryCategoryListByParentId(Long pid) throws DataAccessException;

    List<Category>queryCategoryListByCategoryIds(@Param("categoryIds") List<Long> categoryIds)throws DataAccessException;
}
