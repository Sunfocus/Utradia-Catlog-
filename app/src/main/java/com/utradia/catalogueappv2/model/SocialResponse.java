package com.utradia.catalogueappv2.model;

/**
 * Created by DESKTOP on 12/19/2017.
 */

public class SocialResponse {
    /**
     * success : 1
     * success_message : User logged in Successfully
     * user_data : {"id":"81","name":"Sun Sol","email":"contact.sfs8@gmail.com","password":"","phone_number":"7963436969","carrierCode":"91","type":"Customer","image":"","banner_image":"","refer_code":"H2UG9Y27K8","invite_refer_code":"","business_name":"","business_website":"","postal_code":"","house_number":"","location":"","street":"","city":"0","state":"0","country":"2","lat":"","lng":"","device_token":"","device_type":"android","confirm_code":"168-440","bundles":"0","reward_points":"0","rating":"0.00","password_token":"","fb_id":"1945636389018334","gid":"","tid":"","hash":"","created":"2017-12-18 05:50:18","my_utc_timestamp":"1513576218","status":"1","is_phone_verified":"0","is_email_verified":"0","raters":"0","deactivate_reason":"","rating_avg":"0.0","country_name":"India","state_name":"","city_name":""}
     * error_type :
     */

    private int success;
    private String success_message;
    private String error_message;
    private String isSocial;

    public String getIsSocial() {
        return isSocial;
    }

    public void setIsSocial(String isSocial) {
        this.isSocial = isSocial;
    }
    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private UserDataBean user_data;
    private String error_type;

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

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public static class UserDataBean {
        /**
         * id : 81
         * name : Sun Sol
         * email : contact.sfs8@gmail.com
         * password :
         * phone_number : 7963436969
         * carrierCode : 91
         * type : Customer
         * image :
         * banner_image :
         * refer_code : H2UG9Y27K8
         * invite_refer_code :
         * business_name :
         * business_website :
         * postal_code :
         * house_number :
         * location :
         * street :
         * city : 0
         * state : 0
         * country : 2
         * lat :
         * lng :
         * device_token :
         * device_type : android
         * confirm_code : 168-440
         * bundles : 0
         * reward_points : 0
         * rating : 0.00
         * password_token :
         * fb_id : 1945636389018334
         * gid :
         * tid :
         * hash :
         * created : 2017-12-18 05:50:18
         * my_utc_timestamp : 1513576218
         * status : 1
         * is_phone_verified : 0
         * is_email_verified : 0
         * raters : 0
         * deactivate_reason :
         * rating_avg : 0.0
         * country_name : India
         * state_name :
         * city_name :
         */

        private String id;
        private String name;


        private String email;
        private String password;
        private String phone_number;
        private String carrierCode;
        private String type;
        private String image;
        private String banner_image;
        private String refer_code;
        private String invite_refer_code;
        private String business_name;
        private String business_website;
        private String postal_code;
        private String house_number;
        private String location;
        private String street;
        private String city;
        private String state;
        private String country;
        private String lat;
        private String lng;
        private String device_token;
        private String device_type;
        private String confirm_code;
        private String bundles;
        private String reward_points;
        private String rating;
        private String password_token;
        private String fb_id;
        private String gid;
        private String tid;
        private String hash;
        private String created;
        private String my_utc_timestamp;
        private String status;
        private String is_phone_verified;
        private String is_email_verified;
        private String raters;
        private String deactivate_reason;
        private String rating_avg;
        private String country_name;
        private String state_name;
        private String city_name;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCarrierCode() {
            return carrierCode;
        }

        public void setCarrierCode(String carrierCode) {
            this.carrierCode = carrierCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBanner_image() {
            return banner_image;
        }

        public void setBanner_image(String banner_image) {
            this.banner_image = banner_image;
        }

        public String getRefer_code() {
            return refer_code;
        }

        public void setRefer_code(String refer_code) {
            this.refer_code = refer_code;
        }

        public String getInvite_refer_code() {
            return invite_refer_code;
        }

        public void setInvite_refer_code(String invite_refer_code) {
            this.invite_refer_code = invite_refer_code;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getBusiness_website() {
            return business_website;
        }

        public void setBusiness_website(String business_website) {
            this.business_website = business_website;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public String getHouse_number() {
            return house_number;
        }

        public void setHouse_number(String house_number) {
            this.house_number = house_number;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getConfirm_code() {
            return confirm_code;
        }

        public void setConfirm_code(String confirm_code) {
            this.confirm_code = confirm_code;
        }

        public String getBundles() {
            return bundles;
        }

        public void setBundles(String bundles) {
            this.bundles = bundles;
        }

        public String getReward_points() {
            return reward_points;
        }

        public void setReward_points(String reward_points) {
            this.reward_points = reward_points;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPassword_token() {
            return password_token;
        }

        public void setPassword_token(String password_token) {
            this.password_token = password_token;
        }

        public String getFb_id() {
            return fb_id;
        }

        public void setFb_id(String fb_id) {
            this.fb_id = fb_id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getMy_utc_timestamp() {
            return my_utc_timestamp;
        }

        public void setMy_utc_timestamp(String my_utc_timestamp) {
            this.my_utc_timestamp = my_utc_timestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_phone_verified() {
            return is_phone_verified;
        }

        public void setIs_phone_verified(String is_phone_verified) {
            this.is_phone_verified = is_phone_verified;
        }

        public String getIs_email_verified() {
            return is_email_verified;
        }

        public void setIs_email_verified(String is_email_verified) {
            this.is_email_verified = is_email_verified;
        }

        public String getRaters() {
            return raters;
        }

        public void setRaters(String raters) {
            this.raters = raters;
        }

        public String getDeactivate_reason() {
            return deactivate_reason;
        }

        public void setDeactivate_reason(String deactivate_reason) {
            this.deactivate_reason = deactivate_reason;
        }

        public String getRating_avg() {
            return rating_avg;
        }

        public void setRating_avg(String rating_avg) {
            this.rating_avg = rating_avg;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
