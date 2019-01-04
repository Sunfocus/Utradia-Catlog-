package com.utradia.catalogueappv2.model;

/*
 * Created by DESKTOP on 11/10/2017.
 */

public class ProfileResponse {
    /**
     * success : 1
     * success_message :
     * userdata : {"id":"5","first_name":"Davinder","last_name":"Davinder","email":"sfs.davinder17@gmail.com","password":"3c0309afb1ee387b362de75214eeac11","hash":"2adee8815dd939548ee6b2772524b6f2","phone_number":"9780182889","image":"","fb_id":"","google_id":"","lat":"","lng":"","device_token":"dO_9nyVBeZU:APA91bELC9gT0FrXdDGwKkPHLRPAMYy4h5zfMBMu19XRnZYRHyP5BY1NjRDp36vjiyMUcBq82g0C6yM00hJOja3oQGkfCcs3zinTwoAOfpXOGsc7tpyAvIqEfvoz_RVjfffcPX2fHngm","device_type":"android","catalogue_notification_status":"1","created":"2018-05-17 05:28:53","status":"0","gender":"0","carrierCode":"","dob":""}
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
    private UserdataBean userdata;
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

    public UserdataBean getUserdata() {
        return userdata;
    }

    public void setUserdata(UserdataBean userdata) {
        this.userdata = userdata;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public static class UserdataBean {
        /**
         * id : 5
         * first_name : Davinder
         * last_name : Davinder
         * email : sfs.davinder17@gmail.com
         * password : 3c0309afb1ee387b362de75214eeac11
         * hash : 2adee8815dd939548ee6b2772524b6f2
         * phone_number : 9780182889
         * image :
         * fb_id :
         * google_id :
         * lat :
         * lng :
         * device_token : dO_9nyVBeZU:APA91bELC9gT0FrXdDGwKkPHLRPAMYy4h5zfMBMu19XRnZYRHyP5BY1NjRDp36vjiyMUcBq82g0C6yM00hJOja3oQGkfCcs3zinTwoAOfpXOGsc7tpyAvIqEfvoz_RVjfffcPX2fHngm
         * device_type : android
         * catalogue_notification_status : 1
         * created : 2018-05-17 05:28:53
         * status : 0
         * gender : 0
         * carrierCode :
         * dob :
         */

        private String id;
        private String first_name;
        private String last_name;
        private String email;
        private String password;
        private String hash;
        private String phone_number;
        private String image;
        private String fb_id;
        private String google_id;
        private String lat;
        private String lng;
        private String device_token;
        private String device_type;
        private String catalogue_notification_status;
        private String created;
        private String status;
        private String gender;
        private String carrierCode;
        private String dob;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
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

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFb_id() {
            return fb_id;
        }

        public void setFb_id(String fb_id) {
            this.fb_id = fb_id;
        }

        public String getGoogle_id() {
            return google_id;
        }

        public void setGoogle_id(String google_id) {
            this.google_id = google_id;
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

        public String getCatalogue_notification_status() {
            return catalogue_notification_status;
        }

        public void setCatalogue_notification_status(String catalogue_notification_status) {
            this.catalogue_notification_status = catalogue_notification_status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCarrierCode() {
            return carrierCode;
        }

        public void setCarrierCode(String carrierCode) {
            this.carrierCode = carrierCode;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }
    }
}
