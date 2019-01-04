package com.utradia.catalogueappv2.model;

import java.util.List;

public class ShopListResponse {
    /**
     * success : 1
     * success_message : All Offers.
     * shop_lists_data : [{"id":"2","name":"Utradia","brand_name":"Utradia Retails","logo":"https://utradiapp.s3-us-west-2.amazonaws.com/1527051258_12.png"}]
     * error_type :
     */

    private int success;
    private String success_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private String error_message;
    private String error_type;
    private List<ShopListsDataBean> shop_lists_data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public List<ShopListsDataBean> getShop_lists_data() {
        return shop_lists_data;
    }

    public void setShop_lists_data(List<ShopListsDataBean> shop_lists_data) {
        this.shop_lists_data = shop_lists_data;
    }

    public static class ShopListsDataBean {
        /**
         * id : 2
         * name : Utradia
         * brand_name : Utradia Retails
         * logo : https://utradiapp.s3-us-west-2.amazonaws.com/1527051258_12.png
         */

        private String id;
        private String name;
        private String brand_name;
        private String logo;
        private boolean header;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isHeader() {
            return header;
        }

        public void setHeader(boolean header) {
            this.header = header;
        }
    }
}
