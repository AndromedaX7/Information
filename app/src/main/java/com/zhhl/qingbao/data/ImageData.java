package com.zhhl.qingbao.data;

/**
 * Created by miao on 2018/12/19.
 */
public class ImageData {
    private String imageType;

    public ImageData(int imageType, String imageBase64) {
        this.imageType = String.valueOf(imageType);
        this.imageBase64 = imageBase64;
    }

    public String getImageType() {

        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    private String imageBase64;

}
