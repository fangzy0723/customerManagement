package cn.com.example.customermanagement.domain;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片数据的实体类
 * Created by fangzy on 2018/1/26 21:13
 */
public class ImageCode {

    private BufferedImage bufferedImage;

    private String code;

    private LocalDateTime expireTime;

    public ImageCode(BufferedImage bufferedImage, String code,int second) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(second);
    }

    public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime expireTime) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpireTime(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
