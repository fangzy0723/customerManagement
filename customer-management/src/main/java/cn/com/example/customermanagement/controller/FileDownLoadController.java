package cn.com.example.customermanagement.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件下载
 * Created by fangzy on 2018/01/25 10:42
 */
@RestController
public class FileDownLoadController {

    /**
     * 测试文件下载示例
     * @return
     * @throws Exception
     */
    @GetMapping("/download")
    public ResponseEntity download() throws Exception{
        //要下载的文件（路径+文件名）
        String file_path = "D:\\uploadfile\\test.xlsx";
        //要下载的文件资源
        FileSystemResource fileSystemResource = new FileSystemResource(file_path);

        HttpHeaders httpHeaders = new HttpHeaders();
        //下载的文件名称
        String down_load_file_name = "test.xlsx";
        //设置下载文件的默认名称
        httpHeaders.add("Content-Disposition","attachment;filename="+down_load_file_name);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(fileSystemResource.contentLength())
                //application/x-download或者application/octet-stream都可以下载
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }
}
