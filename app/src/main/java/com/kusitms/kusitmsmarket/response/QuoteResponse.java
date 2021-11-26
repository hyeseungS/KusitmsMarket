package com.kusitms.kusitmsmarket.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuoteResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<Quote> quotes = new ArrayList<>();

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class Quote {

        @SerializedName("price")
        private String price;

        @SerializedName("date")
        private String unit;

        @SerializedName("m_name")
        private String m_name;

        @SerializedName("a_name")
        private String a_name;

        public Quote(String price, String unit, String m_name, String a_name) {
            this.price = price;
            this.unit = unit;
            this.m_name = m_name;
            this.a_name = a_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String date) {
            this.unit = date;
        }

        public String getM_name() {
            return m_name;
        }

        public void setM_name(String m_name) {
            this.m_name = m_name;
        }

        public String getA_name() {
            return a_name;
        }

        public void setA_name(String a_name) {
            this.a_name = a_name;
        }
    }

}
