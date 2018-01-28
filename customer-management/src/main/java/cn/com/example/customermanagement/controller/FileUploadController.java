package cn.com.example.customermanagement.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/**
 * 文件上传
 * Created by fangzy on 2017/8/26 21:42
 */
@RestController
public class FileUploadController {

    /**
     * 单文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadfile")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            try {
                //指定路径加文件名就会将文件保存到指定的路径下，
                String file_upload_path = "D:\\uploadfile\\"+file.getOriginalFilename();
                //没有指定就保存到项目所有的目录下
                //String file_upload_path = file.getOriginalFilename();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file_upload_path)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    /**
     * 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
     * @param request
     * @return
     */
    @PostMapping(value = "/upload/batch")
    public  String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                //指定路径加文件名就会将文件保存到指定的路径下，
                String file_upload_path = "D:\\uploadfile\\"+file.getOriginalFilename();
                //没有指定就保存到项目所有的目录下
                //String file_upload_path = file.getOriginalFilename();
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file_upload_path)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
