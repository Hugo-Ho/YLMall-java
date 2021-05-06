package com.Hugo.goods.controller;

import com.Hugo.goods.pojo.VO.SpuVO;
import com.Hugo.goods.service.GoodsService;
import com.Hugo.pojo.PageResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("goods")
@Api("商品")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuVO>> querySpuByPageAndSort(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy", required = false) String sortBy,
                                                       @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                       @RequestParam(value = "key", required = false) String key)
    {
        PageResult<SpuVO>result = this.goodsService.querySpuByPageAndSort(page,rows,key);
        if(result==null||result.getList().size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
