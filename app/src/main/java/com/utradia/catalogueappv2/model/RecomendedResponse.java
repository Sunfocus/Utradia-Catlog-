package com.utradia.catalogueappv2.model;

import java.util.List;

public class RecomendedResponse {

    /**
     * success : 1
     * success_message : All products.
     * offers : [{"id":"8","title":"iphone 7 Black Mat finish","brandname":"APPLE","model":"Apple Iphone 7","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527753916_iphone7-black-select-2016.jpeg","discount":"50000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 08:05:16","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"16","title":"Power Bank","brandname":"PHILIPS","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528278976_ic_banner1.png","discount":"1500","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:09:42","brand_id":"70","client_name":"Laundry Exchange","raters":0},{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"14","title":"Hard Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760696_download_%282%29h.jpeg","discount":"5200","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:58:16","brand_id":"74","client_name":"Laundry Exchange","raters":0},{"id":"30","title":"Demo - 3","brandname":"Panasonic","model":"dsfsd","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"200","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"4","title":"Charging cable","brandname":"NOKIA","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527747118_download_%281%29cable.jpeg","discount":"500","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:11:58","brand_id":"71","client_name":"Laundry Exchange","raters":0},{"id":"10","title":"Sony","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527759698_sony-xperia-x-ultra-mobile-phone-large-1.jpg","discount":"50000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:41:38","brand_id":"74","client_name":"Laundry Exchange","raters":0},{"id":"5","title":"Mac Mini","brandname":"APPLE","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527747290_download_%281%29_mac_.jpeg","discount":"25000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:14:50","brand_id":"69","client_name":"Laundry Exchange","raters":0}]
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
    private List<OffersBean> offers;

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

    public List<OffersBean> getOffers() {
        return offers;
    }

    public void setOffers(List<OffersBean> offers) {
        this.offers = offers;
    }

    public static class OffersBean {
        /**
         * id : 8
         * title : iphone 7 Black Mat finish
         * brandname : APPLE
         * model : Apple Iphone 7
         * category_id : 13
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527753916_iphone7-black-select-2016.jpeg
         * discount : 50000
         * discounted_price : 1000
         * rating : 0.0
         * client : 3
         * status : 1
         * created : 2018-05-31 08:05:16
         * brand_id : 69
         * client_name : Laundry Exchange
         * raters : 0
         */

        private String id;
        private String title;
        private String brandname;
        private String model;
        private String category_id;
        private String image;
        private String discount;
        private String discounted_price;
        private String rating;
        private String client;
        private String status;
        private String created;
        private String brand_id;
        private String client_name;
        private int raters;

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

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public int getRaters() {
            return raters;
        }

        public void setRaters(int raters) {
            this.raters = raters;
        }
    }
}
