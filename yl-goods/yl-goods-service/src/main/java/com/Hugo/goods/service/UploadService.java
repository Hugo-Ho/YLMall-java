package com.Hugo.goods.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String baseUpload(MultipartFile file);
    String fastdfsUpload(MultipartFile file);
}
