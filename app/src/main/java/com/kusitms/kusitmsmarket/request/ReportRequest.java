package com.kusitms.kusitmsmarket.request;
/*{
        "marketName": "string",
        "storeAddress": "string",
        "storeCategory": "string",
        "storeGiftcard": true,
        "storeLink":
        "storeName": "string",
        "storePhone": "string",
        "storeScore:
        "storeTime":
        "userName": "string"
}*/
public class ReportRequest {
    private String marketName;
    private String storeAddress;
    private String storeCategory; //
    private boolean storeGiftcard;
    private String storeLink;
    private String storeName;
    private String storePhone; //
    private String storeScore;
    private String storeTime;
    private String userName; //


    public ReportRequest() {
    }

    public ReportRequest(String marketName, String storeAddress, boolean storeGiftcard, String storeName) {
        this.marketName = marketName;
        this.storeAddress = storeAddress;
        this.storeGiftcard = storeGiftcard;
        this.storeName = storeName;

        this.storeLink = " ";
        this.storeScore = " ";
        this.storeTime = " ";
        this.userName = " ";
        this.storePhone = " ";
        this.storeCategory = " ";
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "marketName='" + marketName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeCategory='" + storeCategory + '\'' +
                ", storeGiftcard=" + storeGiftcard +
                ", storeName='" + storeName + '\'' +
                ", storePhone='" + storePhone + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public boolean isStoreGiftcard() {
        return storeGiftcard;
    }

    public void setStoreGiftcard(boolean storeGiftcard) {
        this.storeGiftcard = storeGiftcard;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }

    public String getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(String storeScore) {
        this.storeScore = storeScore;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }
}
