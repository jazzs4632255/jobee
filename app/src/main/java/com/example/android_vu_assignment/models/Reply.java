package com.example.android_vu_assignment.models;

import java.io.Serializable;

public class Reply implements Serializable {
    private String reply;
    private long timestamp;

    public Reply() {
    }

    public Reply(String reply, long timestamp) {
        this.reply = reply;
        this.timestamp = timestamp;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
