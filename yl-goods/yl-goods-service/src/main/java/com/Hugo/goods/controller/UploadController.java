package com.Hugo.goods.controller;

import com.Hugo.goods.service.UploadService;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
@Api("上传")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public ResponseEntity<String>UploadImage(@RequestParam("file")MultipartFile file)
    {
        String url = this.uploadService.baseUpload(file);//普通上传
        String fastdfsurl = this.uploadService.fastdfsUpload(file);//fastdfs上传
        if(StringUtils.isEmptyOrWhitespaceOnly(url))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(url+fastdfsurl);
    }
}
