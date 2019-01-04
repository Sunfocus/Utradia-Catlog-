package com.utradia.catalogueappv2.model;

public class EventDetailResponse {
    /**
     * success : 1
     * success_message : Event details.
     * event_details : {"id":"2","title":"Day","client":"3","category_id":"109","end_date":"2018-05-29","image":"http://35.163.109.148/assets/uploads/events/https://utradiapp.s3-us-west-2.amazonaws.com/1527155485_ic_banner.png","start_time":"9:00 AM","end_time":"9:30 AM","price":"222","description":"2edddededed","created":"2018-05-23 09:50:18","status":"1","center_id":"1","show_on_app":"0","category":"Religious","going_status":"Not going","total_going":0,"user_data":{"id":"3","name":"Laundry Exchange","username":"","email":"sony@utradia.com","password":"e3ceb5881a0a1fdaad01296d7554868d","phone":"918973479444","type":"shop","access":"shop_business","brand_name":"Sony India pvt ltd","merchant_key":"r342r4r","merchant_email":"sony@utradia.com","about":"<p>erfwfwefefff<\/p>","shipping_policy":"","policies":"<p>fefefefefe<\/p>","created":"0000-00-00 00:00:00","modified":"2018-05-23 07:27:31","status":"1","logo":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_image_ic_toolbar_back.png","order_data":"2","gender":"0"},"location_data":{"id":"1","mon_time":"12:30 AM - 1:00 AM","tue_time":"12:30 AM - 1:00 AM","wed_time":"1:30 AM - 2:00 AM","thu_time":"12:30 AM - 1:00 AM","fri_time":"6:00 AM - 6:30 AM","sat_time":"Closed","sun_time":"Closed","address":"3b2, Mohali Bypass, Phase 3B-2, Sector 60, Sahibzada Ajit Singh Nagar, Punjab, India","contact_number":"65465654654","lat":"30.7061107","lng":"76.7170519","city":"Mohali","state":"Punjab","country":"India","website":"www.utradia.com","created":"2018-05-23 09:47:25","modified":"0","type":"","client_id":"3","email":"shop@utradia.com","image":"1527068845_ic_about_grey.png","center":"Mohali"}}
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
    private EventDetailsBean event_details;
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

    public EventDetailsBean getEvent_details() {
        return event_details;
    }

    public void setEvent_details(EventDetailsBean event_details) {
        this.event_details = event_details;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public static class EventDetailsBean {
        /**
         * id : 2
         * title : Day
         * client : 3
         * category_id : 109
         * end_date : 2018-05-29
         * image : http://35.163.109.148/assets/uploads/events/https://utradiapp.s3-us-west-2.amazonaws.com/1527155485_ic_banner.png
         * start_time : 9:00 AM
         * end_time : 9:30 AM
         * price : 222
         * description : 2edddededed
         * created : 2018-05-23 09:50:18
         * status : 1
         * center_id : 1
         * show_on_app : 0
         * category : Religious
         * going_status : Not going
         * total_going : 0
         * user_data : {"id":"3","name":"Laundry Exchange","username":"","email":"sony@utradia.com","password":"e3ceb5881a0a1fdaad01296d7554868d","phone":"918973479444","type":"shop","access":"shop_business","brand_name":"Sony India pvt ltd","merchant_key":"r342r4r","merchant_email":"sony@utradia.com","about":"<p>erfwfwefefff<\/p>","shipping_policy":"","policies":"<p>fefefefefe<\/p>","created":"0000-00-00 00:00:00","modified":"2018-05-23 07:27:31","status":"1","logo":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_image_ic_toolbar_back.png","order_data":"2","gender":"0"}
         * location_data : {"id":"1","mon_time":"12:30 AM - 1:00 AM","tue_time":"12:30 AM - 1:00 AM","wed_time":"1:30 AM - 2:00 AM","thu_time":"12:30 AM - 1:00 AM","fri_time":"6:00 AM - 6:30 AM","sat_time":"Closed","sun_time":"Closed","address":"3b2, Mohali Bypass, Phase 3B-2, Sector 60, Sahibzada Ajit Singh Nagar, Punjab, India","contact_number":"65465654654","lat":"30.7061107","lng":"76.7170519","city":"Mohali","state":"Punjab","country":"India","website":"www.utradia.com","created":"2018-05-23 09:47:25","modified":"0","type":"","client_id":"3","email":"shop@utradia.com","image":"1527068845_ic_about_grey.png","center":"Mohali"}
         */

        private String id;
        private String title;
        private String client;
        private String category_id;
        private String end_date;
        private String image;
        private String start_time;
        private String end_time;
        private String price;
        private String description;
        private String created;
        private String status;
        private String center_id;
        private String show_on_app;
        private String category;
        private String going_status;
        private int total_going;
        private UserDataBean user_data;
        private LocationDataBean location_data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getCenter_id() {
            return center_id;
        }

        public void setCenter_id(String center_id) {
            this.center_id = center_id;
        }

        public String getShow_on_app() {
            return show_on_app;
        }

        public void setShow_on_app(String show_on_app) {
            this.show_on_app = show_on_app;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getGoing_status() {
            return going_status;
        }

        public void setGoing_status(String going_status) {
            this.going_status = going_status;
        }

        public int getTotal_going() {
            return total_going;
        }

        public void setTotal_going(int total_going) {
            this.total_going = total_going;
        }

        public UserDataBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserDataBean user_data) {
            this.user_data = user_data;
        }

        public LocationDataBean getLocation_data() {
            return location_data;
        }

        public void setLocation_data(LocationDataBean location_data) {
            this.location_data = location_data;
        }

        public static class UserDataBean {
            /**
             * id : 3
             * name : Laundry Exchange
             * username :
             * email : sony@utradia.com
             * password : e3ceb5881a0a1fdaad01296d7554868d
             * phone : 918973479444
             * type : shop
             * access : shop_business
             * brand_name : Sony India pvt ltd
             * merchant_key : r342r4r
             * merchant_email : sony@utradia.com
             * about : <p>erfwfwefefff</p>
             * shipping_policy :
             * policies : <p>fefefefefe</p>
             * created : 0000-00-00 00:00:00
             * modified : 2018-05-23 07:27:31
             * status : 1
             * logo : https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png
             * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_image_ic_toolbar_back.png
             * order_data : 2
             * gender : 0
             */

            private String id;
            private String name;
            private String username;
            private String email;
            private String password;
            private String phone;
            private String type;
            private String access;
            private String brand_name;
            private String merchant_key;
            private String merchant_email;
            private String about;
            private String shipping_policy;
            private String policies;
            private String created;
            private String modified;
            private String status;
            private String logo;
            private String image;
            private String order_data;
            private String gender;

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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAccess() {
                return access;
            }

            public void setAccess(String access) {
                this.access = access;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getMerchant_key() {
                return merchant_key;
            }

            public void setMerchant_key(String merchant_key) {
                this.merchant_key = merchant_key;
            }

            public String getMerchant_email() {
                return merchant_email;
            }

            public void setMerchant_email(String merchant_email) {
                this.merchant_email = merchant_email;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getShipping_policy() {
                return shipping_policy;
            }

            public void setShipping_policy(String shipping_policy) {
                this.shipping_policy = shipping_policy;
            }

            public String getPolicies() {
                return policies;
            }

            public void setPolicies(String policies) {
                this.policies = policies;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getOrder_data() {
                return order_data;
            }

            public void setOrder_data(String order_data) {
                this.order_data = order_data;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }

        public static class LocationDataBean {
            /**
             * id : 1
             * mon_time : 12:30 AM - 1:00 AM
             * tue_time : 12:30 AM - 1:00 AM
             * wed_time : 1:30 AM - 2:00 AM
             * thu_time : 12:30 AM - 1:00 AM
             * fri_time : 6:00 AM - 6:30 AM
             * sat_time : Closed
             * sun_time : Closed
             * address : 3b2, Mohali Bypass, Phase 3B-2, Sector 60, Sahibzada Ajit Singh Nagar, Punjab, India
             * contact_number : 65465654654
             * lat : 30.7061107
             * lng : 76.7170519
             * city : Mohali
             * state : Punjab
             * country : India
             * website : www.utradia.com
             * created : 2018-05-23 09:47:25
             * modified : 0
             * type :
             * client_id : 3
             * email : shop@utradia.com
             * image : 1527068845_ic_about_grey.png
             * center : Mohali
             */

            private String id;
            private String mon_time;
            private String tue_time;
            private String wed_time;
            private String thu_time;
            private String fri_time;
            private String sat_time;
            private String sun_time;
            private String address;
            private String contact_number;
            private String lat;
            private String lng;
            private String city;
            private String state;
            private String country;
            private String website;
            private String created;
            private String modified;
            private String type;
            private String client_id;
            private String email;
            private String image;
            private String center;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMon_time() {
                return mon_time;
            }

            public void setMon_time(String mon_time) {
                this.mon_time = mon_time;
            }

            public String getTue_time() {
                return tue_time;
            }

            public void setTue_time(String tue_time) {
                this.tue_time = tue_time;
            }

            public String getWed_time() {
                return wed_time;
            }

            public void setWed_time(String wed_time) {
                this.wed_time = wed_time;
            }

            public String getThu_time() {
                return thu_time;
            }

            public void setThu_time(String thu_time) {
                this.thu_time = thu_time;
            }

            public String getFri_time() {
                return fri_time;
            }

            public void setFri_time(String fri_time) {
                this.fri_time = fri_time;
            }

            public String getSat_time() {
                return sat_time;
            }

            public void setSat_time(String sat_time) {
                this.sat_time = sat_time;
            }

            public String getSun_time() {
                return sun_time;
            }

            public void setSun_time(String sun_time) {
                this.sun_time = sun_time;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContact_number() {
                return contact_number;
            }

            public void setContact_number(String contact_number) {
                this.contact_number = contact_number;
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

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getClient_id() {
                return client_id;
            }

            public void setClient_id(String client_id) {
                this.client_id = client_id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCenter() {
                return center;
            }

            public void setCenter(String center) {
                this.center = center;
            }
        }
    }
}
