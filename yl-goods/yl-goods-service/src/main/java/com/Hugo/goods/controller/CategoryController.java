package com.Hugo.goods.controller;

import com.Hugo.goods.pojo.Category;
import com.Hugo.goods.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
@Api("商品分类")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据parentId查询类目
     *
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        try {
            if (pid == null || pid.longValue() < 0) {
                //pid 为null或者小于等于0，响应400
                return ResponseEntity.badRequest().build();
            }
            //执行查询操作
            List<Category> categoryList = this.categoryService.queryCategoryListByParentId(pid);
            if (CollectionUtils.isEmpty(categoryList)) {
                //返回结果集为空，响应404
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categoryList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>>queryCategoryListByBrandId(@PathVariable("bid") Long bid)
    {
        List<Category> list = this.categoryService.queryCategoryListByBrandId(bid);
        if(list ==null ||list.size()<0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
