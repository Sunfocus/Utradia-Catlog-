package com.utradia.catalogueappv2.model;

/**
 * response model for login api
 */
public class LoginResponse {


    /**
     * success : 1
     * user_data : {"id":"11073","first_name":"davinder17","last_name":"","email":"sfs.davinder17@gmail.com","password":"3c0309afb1ee387b362de75214eeac11","hash":"1c67df9e0a5cfefa030b853983324004","phone_number":"9783839278","image":"","fb_id":"","google_id":"","lat":"","lng":"","device_token":"dWmNnz7B0MM:APA91bErRr04Al7Avm1D3WAk8UKW8nE7Vi_w5ySovsZhzaGO5Y0zFqTMozrzxPAjNU3dDDpYvvW52EQCsSxHiv4Szcn_0jdDnBwCefAtNsyUxiZ8YYVnwLL9I1UZStaX2rdcZGuAkdEj","device_type":"android","catalogue_notification_status":"1","created":"2018-05-04 07:32:41","status":"0","user_id":"11073"}
     * success_message : User logged in.
     * error_type :
     */

    private int success;
    private UserDataBean user_data;
    private String success_message;

    public String getCart_count() {
        return cart_count;
    }

    public void setCart_count(String cart_count) {
        this.cart_count = cart_count;
    }

    private String cart_count;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private String error_message;
    private String error_type;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
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

    public static class UserDataBean {
        /**
         * id : 11073
         * first_name : davinder17
         * last_name :
         * email : sfs.davinder17@gmail.com
         * password : 3c0309afb1ee387b362de75214eeac11
         * hash : 1c67df9e0a5cfefa030b853983324004
         * phone_number : 9783839278
         * image :
         * fb_id :
         * google_id :
         * lat :
         * lng :
         * device_token : dWmNnz7B0MM:APA91bErRr04Al7Avm1D3WAk8UKW8nE7Vi_w5ySovsZhzaGO5Y0zFqTMozrzxPAjNU3dDDpYvvW52EQCsSxHiv4Szcn_0jdDnBwCefAtNsyUxiZ8YYVnwLL9I1UZStaX2rdcZGuAkdEj
         * device_type : android
         * catalogue_notification_status : 1
         * created : 2018-05-04 07:32:41
         * status : 0
         * user_id : 11073
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
        private String user_id;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
