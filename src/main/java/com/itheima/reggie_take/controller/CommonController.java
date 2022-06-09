package com.itheima.reggie_take.controller;

import com.alibaba.fastjson.util.IOUtils;
import com.itheima.reggie_take.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @title: CommonController
 * @Author Junfang Yuan
 * @Date: 2022/6/8 13:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + type;
        File path = new File(basePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        file.transferTo(new File(basePath+filename));
        return R.success(filename);

    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(String name, HttpServletResponse response)  {
        FileInputStream is=null;
        ServletOutputStream os=null;
        try {
             is = new FileInputStream(basePath+name);
//            os = response.getOutputStream();
//            response.setContentType("image/jpeg");
//            byte[] bytes = new byte[1024];
//            int len=0;
//            while ((len=is.read(bytes))!=-1){
//                os.write(bytes,0,len);
//                os.flush();
//            }

            byte[] bytes = new byte[is.available()];
            is.read(bytes);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attachment;filename=1.jpg");
            return new ResponseEntity<byte[]>(bytes,httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
        return null;


    }
    @GetMapping("/dl")
    public ResponseEntity<byte[]> dl(String name){
        FileInputStream is = null;
        try {
            is = new FileInputStream(basePath+name);
            byte[] bytes=new byte[is.available()];
            is.read(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename=fyg.jpg");
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;


    }
}
