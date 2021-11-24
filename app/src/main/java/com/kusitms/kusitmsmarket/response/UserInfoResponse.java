package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private UserInfo userInfo;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "count=" + count +
                ", userInfo=" + userInfo +
                '}';
    }

    public class UserInfo {

        @SerializedName("username")
        private String username;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("phone")
        private String phone;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "userInfo{" +
                    "username='" + username + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}
