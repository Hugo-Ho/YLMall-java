package com.Hugo.goods.service.impl;

import com.Hugo.goods.mapper.BrandMapper;
import com.Hugo.goods.mapper.SpuMapper;
import com.Hugo.goods.pojo.Spu;
import com.Hugo.goods.pojo.VO.SpuVO;
import com.Hugo.goods.service.CategoryService;
import com.Hugo.goods.service.GoodsService;
import com.Hugo.pojo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    public PageResult<SpuVO> querySpuByPageAndSort(Integer page, Integer rows, String key)
    {
        //分页查询spu，只允许查100条
        PageHelper.startPage(page,Math.min(rows,100));
        List<Spu> list = this.spuMapper.querySpuList();
        PageInfo<Spu> pageInfo =new PageInfo(list);
        List<SpuVO> result = pageInfo.getList().stream().map(spu->{
            //把spu变成spuvo
            SpuVO spuVO = new SpuVO();
            //属性拷贝
            BeanUtils.copyProperties(spu,spuVO);
           return spuVO;
        }).collect(Collectors.toList());
        return new PageResult<>(pageInfo.getTotal(),result);
    }
}
