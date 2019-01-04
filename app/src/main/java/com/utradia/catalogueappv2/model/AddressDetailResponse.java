package com.utradia.catalogueappv2.model;

import com.google.gson.annotations.SerializedName;

public class AddressDetailResponse {
    /**
     * success : 1
     * success_message : Avaliable address
     * address : {"id":"4","user_id":"5","full_name":"gguug fhfjc","mobile_number":"86868686686","pincode":"fggg","houseno":"77","locality":"fuvu","city":"126","state":"6","default":"1","type":"shipping","status":"1","city_name":"Birim North","state_name":"Eastern Region "}
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
    private AddressBean address;

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

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * id : 4
         * user_id : 5
         * full_name : gguug fhfjc
         * mobile_number : 86868686686
         * pincode : fggg
         * houseno : 77
         * locality : fuvu
         * city : 126
         * state : 6
         * default : 1
         * type : shipping
         * status : 1
         * city_name : Birim North
         * state_name : Eastern Region
         */

        private String id;
        private String user_id;
        private String full_name;
        private String mobile_number;
        private String pincode;
        private String houseno;
        private String locality;
        private String city;
        private String state;
        @SerializedName("default")
        private String defaultX;
        private String type;
        private String status;
        private String city_name;
        private String state_name;

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
    }
}
