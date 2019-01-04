package com.utradia.catalogueappv2.model;

import java.util.List;

public class WishListResponse {
    /**
     * success : 1
     * success_message : Favourite offers list
     * favourite_data : [{"id":"8","title":"iphone 7 Black Mat finish","region_id":"UpEas","cities":"Bolgatanga","client":"3","brandname":"69","model":"Apple Iphone 7","category_id":"13","end_date":"0000-00-00","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527753916_iphone7-black-select-2016.jpeg","image2":"https://utradiapp.s3-us-west-2.amazonaws.com/1528289208_2a35cd2a-8719-49f9-a369-8445cb53fb49_1.7ce0f921a93253b7e37f5ed875aa3914.jpeg","image3":"https://utradiapp.s3-us-west-2.amazonaws.com/1528289208_000000000300015107-detail-3-9f87d67d-8011-452a-a9cb-56a95674db0a0-Format-960.jpg","image4":"https://utradiapp.s3-us-west-2.amazonaws.com/1528289209_edeeb2f73d3912064b9559e917107cf1.png","discount":"50000","discounted_price":"1000","product_weight":"500g","description":"<p><strong>Lorem Ipsum<\/strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum<\/p>","created":"2018-05-31 08:05:16","status":"1","center_id":"1","show_on_app":"1","shop_enabel":"1","cod":"1","pay_online":"0","pickup_store":"0","pay_and_send":"0","shipment_charges":"","rating":"0.0","views":"57","quantity":"0","favourite_id":"6","favourite_status":"1"}]
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
    private List<FavouriteDataBean> favourite_data;

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

    public List<FavouriteDataBean> getFavourite_data() {
        return favourite_data;
    }

    public void setFavourite_data(List<FavouriteDataBean> favourite_data) {
        this.favourite_data = favourite_data;
    }

    public static class FavouriteDataBean {
        /**
         * id : 8
         * title : iphone 7 Black Mat finish
         * region_id : UpEas
         * cities : Bolgatanga
         * client : 3
         * brandname : 69
         * model : Apple Iphone 7
         * category_id : 13
         * end_date : 0000-00-00
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527753916_iphone7-black-select-2016.jpeg
         * image2 : https://utradiapp.s3-us-west-2.amazonaws.com/1528289208_2a35cd2a-8719-49f9-a369-8445cb53fb49_1.7ce0f921a93253b7e37f5ed875aa3914.jpeg
         * image3 : https://utradiapp.s3-us-west-2.amazonaws.com/1528289208_000000000300015107-detail-3-9f87d67d-8011-452a-a9cb-56a95674db0a0-Format-960.jpg
         * image4 : https://utradiapp.s3-us-west-2.amazonaws.com/1528289209_edeeb2f73d3912064b9559e917107cf1.png
         * discount : 50000
         * discounted_price : 1000
         * product_weight : 500g
         * description : <p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum</p>
         * created : 2018-05-31 08:05:16
         * status : 1
         * center_id : 1
         * show_on_app : 1
         * shop_enabel : 1
         * cod : 1
         * pay_online : 0
         * pickup_store : 0
         * pay_and_send : 0
         * shipment_charges :
         * rating : 0.0
         * views : 57
         * quantity : 0
         * favourite_id : 6
         * favourite_status : 1
         */

        private String id;
        private String title;
        private String brandname;
        private String model;
        private String category_id;
        private String end_date;
        private String image;
        private String discount;
        private String discounted_price;
        private String description;
        private String favourite_id;
        private String favourite_status;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFavourite_id() {
            return favourite_id;
        }

        public void setFavourite_id(String favourite_id) {
            this.favourite_id = favourite_id;
        }

        public String getFavourite_status() {
            return favourite_status;
        }

        public void setFavourite_status(String favourite_status) {
            this.favourite_status = favourite_status;
        }
    }
}
