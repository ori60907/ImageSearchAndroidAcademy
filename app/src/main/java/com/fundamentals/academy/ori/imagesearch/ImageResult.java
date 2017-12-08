package com.fundamentals.academy.ori.imagesearch;

/**
 * Created by User on 08/12/2017.
 */

public class ImageResult {
    private String imageUrl;

    public ImageResult(String imageUrl) {
        super();
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
