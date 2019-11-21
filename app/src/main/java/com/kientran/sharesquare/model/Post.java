package com.kientran.sharesquare.model;

public class Post {

    public final String id;
    public final String title;
    public final String image;
    public final String imageCropped;
    public final String imageMedium;
    public final String imageSmall;
    public final String createdAt;



    public Post(String id, String title, String image,String createdAt) {
        this.id = id;
        this.title = title;
        this.image = image;
        String imageExt = image.substring(image.lastIndexOf("."));
        image = image.replace(imageExt,"");

        this.imageCropped = image + "-cropped" + imageExt;
        this.imageMedium = image + "-medium" + imageExt;
        this.imageSmall = image + "-small" + imageExt;
        this.createdAt = createdAt;
    }



    @Override
    public String toString() {
        return title;
    }
}
