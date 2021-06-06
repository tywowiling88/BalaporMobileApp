package umn.ac.id.balapor_kawanua.model;

public class Article {
    String postId, description, location,
            postImage, publisher, date,
            status;

    public Article(){
    }

    public Article(String postId, String description, String location, String postImage, String publisher, String date, String status) {
        this.postId = postId;
        this.description = description;
        this.location = location;
        this.postImage = postImage;
        this.publisher = publisher;
        this.date = date;
        this.status = status;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String process) {
        this.status = process;
    }

}
