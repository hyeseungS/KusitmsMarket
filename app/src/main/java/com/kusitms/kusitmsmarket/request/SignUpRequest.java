package com.kusitms.kusitmsmarket.request;
/* {
"nickname": "string",
"password": "string",
"phone": "string",
"username": "string"
}*/
public class SignUpRequest {

    private String nickname;
    private String password;
    private String phone;
    private String username;

    public SignUpRequest(String nickname, String password, String phone, String username) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
