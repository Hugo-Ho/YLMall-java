package com.Hugo.goods.controller;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import com.Hugo.goods.pojo.Brand;
import com.Hugo.goods.pojo.Category;
import com.Hugo.goods.pojo.CreateBrandParam;
import com.Hugo.goods.service.BrandService;
import com.Hugo.pojo.PageParam;
import com.Hugo.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("brand")

@Api(value = "品牌")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>>queryBrandByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                                             @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                             @RequestParam(value = "key", required = false) String key)
    {
        PageResult<Brand> result = this.brandService.queryBrandByPageAndSort(page,rows,sortBy,desc,key);
        if(result==null||result.getList().size()==0)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void>createBrand(@RequestBody CreateBrandParam brandParam)
    {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandParam,brand);
        this.brandService.createBrand(brand,brandParam.getCategoryIds());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
