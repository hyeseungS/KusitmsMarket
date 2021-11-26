package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("count")
    public int count = 0;
    @SerializedName("data")
    public List<ReviewData> data = null;

    @SerializedName("count")
    public int getCount() {
        return count;
    }

    @SerializedName("count")
    public void setCount(int count) {
        this.count = count;
    }

    @SerializedName("data")
    public List<ReviewData> getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(List<ReviewData> data) {
        this.data = data;
    }
    public class ReviewData {

        @SerializedName("reviewUserName")
        private String reviewUserName;
        @SerializedName("reviewMemo")
        private String reviewMemo;
        @SerializedName("reviewScore")
        private double reviewScore;

        public ReviewData(String reviewUserName, String reviewMemo, double reviewScore) {
            this.reviewUserName = reviewUserName;
            this.reviewMemo = reviewMemo;
            this.reviewScore = reviewScore;
        }

        public String getReviewUserName() {
            return reviewUserName;
        }

        public void setReviewUserName(String reviewUserName) {
            this.reviewUserName = reviewUserName;
        }

        public String getReviewMemo() {
            return reviewMemo;
        }

        public void setReviewMemo(String reviewMemo) {
            this.reviewMemo = reviewMemo;
        }

        public double getReviewScore() {
            return reviewScore;
        }

        public void setReviewScore(double reviewScore) {
            this.reviewScore = reviewScore;
        }

    }
}
