package com.utradia.catalogueappv2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse  {
    /**
     * success : 1
     * success_message : Avaliable addresses
     * address : [{"id":"1","user_id":"5","full_name":"Davinder kumar","mobile_number":"9780182889","pincode":"12","houseno":"77","locality":"C127 mohali","city":"127","state":"6","default":"0","type":"shipping","status":"1"}]
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
    private List<AddressBean> address;

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

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * id : 1
         * user_id : 5
         * full_name : Davinder kumar
         * mobile_number : 9780182889
         * pincode : 12
         * houseno : 77
         * locality : C127 mohali
         * city : 127
         * state : 6
         * default : 0
         * type : shipping
         * status : 1
         */

        private String id;
        private String user_id;
        private String full_name;
        private String mobile_number;
        private String pincode;
        private String city_name;
        private String state_name;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        private String houseno;
        private String locality;
        private String city;
        private String state;
        @SerializedName("default")
        private String defaultX;
        private String type;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getHouseno() {
            return houseno;
        }

        public void setHouseno(String houseno) {
            this.houseno = houseno;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
