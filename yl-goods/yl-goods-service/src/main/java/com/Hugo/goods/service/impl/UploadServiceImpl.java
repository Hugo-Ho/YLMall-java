package com.Hugo.goods.service.impl;

import com.Hugo.goods.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {
    //支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Autowired
    FastFileStorageClient storageClient;

    public String baseUpload(MultipartFile file) {
        try {
            //1 校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                return null;
            }
            //2 校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                return null;
            }
            //3 保存图片
            File dir = new File("G:\\upload");//生成保存目录
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(new File(dir, file.getOriginalFilename()));
            //拼接图片地址
            String url = "http://localhost:8081/upload/" + file.getOriginalFilename();
            return url;
        } catch (Exception ex) {
            return null;
        }
    }

    public String fastdfsUpload(MultipartFile file)
    {
        try {
            //1 校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                return null;
            }
            //2 校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                return null;
            }
            //3 将图片上传到fasdfs
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);//获取文件后缀名
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(),file.getSize(),extension,null);
            return "http://localhost:8081/upload/" +storePath.getFullPath();
        } catch (Exception ex) {
            return null;
        }
    }

}
