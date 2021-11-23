package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*{
        "username": "string",
        "phone": "string",
        "nickname": "string",
        "deviceToken": null,
        "authorities": [
        {
        "authorityName": "ROLE_USER"
        }
        ],
        "userClick": 0
}*/
public class SignUpResponse {

    @SerializedName("username")
    private String username;

    @SerializedName("phone")
    private String phone;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("deviceToken")
    private String deviceToken;

    @SerializedName("authorities")
    private List<Authority> authorities;

    @SerializedName("userClick")
    private int userClick;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    public int getUserClick() {
        return userClick;
    }

    public void setUserClick(int userClick) {
        this.userClick = userClick;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public class Authority {
        @SerializedName("authorityName")
        private String authorityName;

        public Authority(String authorityName) {
            this.authorityName = authorityName;
        }

        public String getAuthorityName() {
            return authorityName;
        }

        public void setAuthorityName(String authorityName) {
            this.authorityName = authorityName;
        }

        @Override
        public String toString() {
            return "Authority{" +
                    "authorityName='" + authorityName + '\'' +
                    '}';
        }
    }
}
