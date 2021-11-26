package com.kusitms.kusitmsmarket.model;

public class ReviewItem {
    String name;
    int resourceId;
    double score;
    String content;

    public ReviewItem(int resourceId, String name, double score, String content) {
        this.name = name;
        this.resourceId = resourceId;
        this.score = score;
        this.content = content;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) { this.score = score; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }
}
