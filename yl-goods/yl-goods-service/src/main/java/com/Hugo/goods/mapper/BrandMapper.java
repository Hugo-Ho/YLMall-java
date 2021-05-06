package com.Hugo.goods.mapper;

import com.Hugo.goods.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> queryBrandList() throws DataAccessException;

    void insertBrand(Brand brand);

}
