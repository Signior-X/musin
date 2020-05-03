package com.example.musin.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("vid_url")
    @Expose
    private String videoUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getVideoUrl(){
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl){
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", image='" + image + '\'' +
                ", error='" + error + '\'' +
                ", length=" + length +
                ", rating=" + rating +
                '}';
    }
}