package com.utradia.catalogueappv2.model;

import java.util.List;

public class CategoriesResponse {

    /**
     * success : 1
     * success_message : All categories.
     * sub_categories : [[{"id":"12","name":"LEDs","description":"Leds","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527681418_ic_electronics.png","parent_id":"3","have_child":"0","order_data":"8","created":"2018-05-30 11:56:58","status":"1"}],[],[],[],[],[],[]]
     * categories : [{"id":"3","name":"Electronics","description":"Testing","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669177_electronics.png","parent_id":"0","have_child":"1","order_data":"1","created":"2018-05-16 06:47:56","status":"1"},{"id":"4","name":"Fashion","description":"Fashion","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669188_fashion.png","parent_id":"0","have_child":"0","order_data":"2","created":"2018-05-30 06:12:36","status":"1"},{"id":"5","name":"Health & Beauty","description":"Health","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669197_health-%26-beauty.png","parent_id":"0","have_child":"0","order_data":"3","created":"2018-05-30 06:13:07","status":"1"},{"id":"6","name":"Appliances","description":"Appliances","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669217_appliance.png","parent_id":"0","have_child":"0","order_data":"4","created":"2018-05-30 06:13:26","status":"1"},{"id":"7","name":"Home & Furniture","description":"furniture","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669230_home.png","parent_id":"0","have_child":"0","order_data":"5","created":"2018-05-30 06:13:54","status":"1"},{"id":"8","name":"Baby & Kids","description":"kids","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669243_baby.png","parent_id":"0","have_child":"0","order_data":"6","created":"2018-05-30 06:14:21","status":"1"},{"id":"9","name":"Ghana Made","description":"Ghana","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669263_gh.png","parent_id":"0","have_child":"0","order_data":"7","created":"2018-05-30 06:14:46","status":"1"}]
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
    private List<List<SubCategoriesBean>> sub_categories;
    private List<CategoriesBean> categories;

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

    public List<List<SubCategoriesBean>> getSub_categories() {
        return sub_categories;
    }

    public void setSub_categories(List<List<SubCategoriesBean>> sub_categories) {
        this.sub_categories = sub_categories;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class SubCategoriesBean {
        /**
         * id : 12
         * name : LEDs
         * description : Leds
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527681418_ic_electronics.png
         * parent_id : 3
         * have_child : 0
         * order_data : 8
         * created : 2018-05-30 11:56:58
         * status : 1
         */

        private String id;
        private String name;
        private String description;
        private String image;
        private String parent_id;
        private String have_child;
        private String order_data;
        private String created;
        private String status;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getHave_child() {
            return have_child;
        }

        public void setHave_child(String have_child) {
            this.have_child = have_child;
        }

        public String getOrder_data() {
            return order_data;
        }

        public void setOrder_data(String order_data) {
            this.order_data = order_data;
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
    }

    public static class CategoriesBean {
        /**
         * id : 3
         * name : Electronics
         * description : Testing
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527669177_electronics.png
         * parent_id : 0
         * have_child : 1
         * order_data : 1
         * created : 2018-05-16 06:47:56
         * status : 1
         */

        private String id;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        private String name;
        private String description;
        private String image;
        private String parent_id;
        private String have_child;
        private String order_data;
        private String created;
        private String status;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getHave_child() {
            return have_child;
        }

        public void setHave_child(String have_child) {
            this.have_child = have_child;
        }

        public String getOrder_data() {
            return order_data;
        }

        public void setOrder_data(String order_data) {
            this.order_data = order_data;
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
    }
}
