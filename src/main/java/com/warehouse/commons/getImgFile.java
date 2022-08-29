package com.warehouse.commons;

import cn.hutool.core.date.DateUtil;
import com.warehouse.utils.AppFileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class getImgFile {
    @RequestMapping("/showImageByPath")
    public ResponseEntity showImageByPath(String path) {
        //从内存中读取文件
        return AppFileUtils.createResponseEntity(path);
    }

    /**
     * 文件上传
     *
     * @return
     */
    @PostMapping("/uploadFile")
    public DataResults uploadFile(@RequestParam("mf") MultipartFile file) {
        try {
            // 上传的原始的文件名字  324234.jpg
            String originalFilename = file.getOriginalFilename();
            //服务器生成一个新的文件名
            String newFileName = AppFileUtils.createNewFileName(originalFilename);
            //3.得到当前日期的字符串
            String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
            //4.构造文件夹
            File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
            //5.判断当前文件夹是否存在
            if (!dirFile.exists()) {
                //如果不存在则创建新文件夹
                dirFile.mkdirs();
            }
            File fileOuput = new File(dirFile, newFileName);
            //7.把mf里面的图片信息写入file
            file.transferTo(fileOuput);
            return DataResults.success(ResultCode.SUCCESS, dirName + "/" + newFileName);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return DataResults.success(ResultCode.FAIL);
    }


}
