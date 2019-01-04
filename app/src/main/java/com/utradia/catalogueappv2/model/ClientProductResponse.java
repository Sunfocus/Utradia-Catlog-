package com.utradia.catalogueappv2.model;

import java.util.List;

public class ClientProductResponse {
    /**
     * success : 1
     * success_message : All Offers.
     * offers : [{"id":"33","title":"nhjhyj","brandname":"Nikon","model":"test me","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1529424293_sasas.jpg","discount":"199","discounted_price":"149","rating":"0.0","client":"3","status":"1","created":"2018-06-19 16:04:53","brand_id":"76","raters":0},{"id":"26","title":"qqqqqqq","brandname":"Hisense","model":"hjfgujhgjh","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528801159_item.jpg","discount":"100","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"2018-06-12 10:59:19","brand_id":"84","raters":0},{"id":"19","title":"Test","brandname":"PHILIPS","model":"rereee","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528447084_item.jpg","discount":"150","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"2018-06-08 08:38:04","brand_id":"70","raters":0},{"id":"16","title":"Power Bank","brandname":"PHILIPS","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528278976_ic_banner1.png","discount":"1500","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:09:42","brand_id":"70","raters":0},{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","raters":0},{"id":"14","title":"Hard Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760696_download_%282%29h.jpeg","discount":"5200","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:58:16","brand_id":"74","raters":0},{"id":"13","title":"Key Board","brandname":"HP","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760544_352614-das-keyboard-4.jpg","discount":"1500","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:55:44","brand_id":"72","raters":0},{"id":"12","title":"Pen Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760326_sony-usm32gr-b2-in-31301912-original-imaeksnuhb4ytpp8.jpeg","discount":"1000","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:52:06","brand_id":"74","raters":0}]
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
         * id : 33
         * title : nhjhyj
         * brandname : Nikon
         * model : test me
         * category_id : 13
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1529424293_sasas.jpg
         * discount : 199
         * discounted_price : 149
         * rating : 0.0
         * client : 3
         * status : 1
         * created : 2018-06-19 16:04:53
         * brand_id : 76
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

        public int getRaters() {
            return raters;
        }

        public void setRaters(int raters) {
            this.raters = raters;
        }
    }
}
