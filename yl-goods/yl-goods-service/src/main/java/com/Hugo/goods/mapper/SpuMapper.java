package com.Hugo.goods.mapper;

import com.Hugo.goods.pojo.Spu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface SpuMapper {
    List<Spu> querySpuList() throws DataAccessException;
}
