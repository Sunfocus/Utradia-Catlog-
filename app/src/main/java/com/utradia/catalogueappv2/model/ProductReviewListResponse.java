package com.utradia.catalogueappv2.model;

import java.util.List;

public class ProductReviewListResponse {



    private int success;
    private String success_message;
    private String error_message;
    private String total_ratings;
    private String one_star;
    private String two_star;
    private String three_star;
    private String four_star;
    private String five_star;
    private String error_type;
    private List<DataBean> data;
    private List<OfferDataBean> offer_data;

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

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(String total_ratings) {
        this.total_ratings = total_ratings;
    }

    public String getOne_star() {
        return one_star;
    }

    public void setOne_star(String one_star) {
        this.one_star = one_star;
    }

    public String getTwo_star() {
        return two_star;
    }

    public void setTwo_star(String two_star) {
        this.two_star = two_star;
    }

    public String getThree_star() {
        return three_star;
    }

    public void setThree_star(String three_star) {
        this.three_star = three_star;
    }

    public String getFour_star() {
        return four_star;
    }

    public void setFour_star(String four_star) {
        this.four_star = four_star;
    }

    public String getFive_star() {
        return five_star;
    }

    public void setFive_star(String five_star) {
        this.five_star = five_star;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<OfferDataBean> getOffer_data() {
        return offer_data;
    }

    public void setOffer_data(List<OfferDataBean> offer_data) {
        this.offer_data = offer_data;
    }

    public static class DataBean {

        private String id;
        private String user_id;
        private String product_id;
        private String rating;
        private String review;
        private String created;
        private String status;
        private String username;
        private String image;

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

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class OfferDataBean {
        /**
         * rating : 5.0
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1531227650_5.jpg
         */

        private String rating;
        private String image;

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
