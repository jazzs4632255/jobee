package com.example.android_vu_assignment.models;

import java.io.Serializable;

public class Post implements Serializable {
    private String postReference;
    private String type;
    private String title;
    private String description;
    private String postedBy;
    private long timestamp;

    public Post() {
    }

    public Post(String type, String title, String description, String postedBy, long timestamp) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPostReference() {
        return postReference;
    }

    public void setPostReference(String postReference) {
        this.postReference = postReference;
    }
}
