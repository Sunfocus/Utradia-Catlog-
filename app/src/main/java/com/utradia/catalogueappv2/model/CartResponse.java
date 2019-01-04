package com.utradia.catalogueappv2.model;

import java.util.List;

public class CartResponse {
    /**
     * success : 1
     * error_message : Product available on cart
     * product_data : [{"id":"31","title":"Demo - 1","brandname":"Panasonic","model":"dsfsd","category_id":"44","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","variant_group_type":"1","favourite":0,"category":"Promo ","prices":[{"id":"73","offer_id":"31","color":"1","size_category":"Footwear","size_type":"US","sizes":"8.5","price":"160","quantity":"5","used":"0","grp_id":"9","created":"2018-06-21 09:58:25","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"}],"cart_quantity":"1","cart_id":"8","raters":0}]
     */

    private int success;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;
    private String error_message;

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    private String success_message;
    private List<ProductDataBean> product_data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<ProductDataBean> getProduct_data() {
        return product_data;
    }

    public void setProduct_data(List<ProductDataBean> product_data) {
        this.product_data = product_data;
    }

    public static class ProductDataBean {
        /**
         * id : 31
         * title : Demo - 1
         * brandname : Panasonic
         * model : dsfsd
         * category_id : 44
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg
         * discount : 140
         * discounted_price : 100
         * rating : 0.0
         * client : 3
         * status : 1
         * created : 0000-00-00 00:00:00
         * variant_group_type : 1
         * favourite : 0
         * category : Promo
         * prices : [{"id":"73","offer_id":"31","color":"1","size_category":"Footwear","size_type":"US","sizes":"8.5","price":"160","quantity":"5","used":"0","grp_id":"9","created":"2018-06-21 09:58:25","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"}]
         * cart_quantity : 1
         * cart_id : 8
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
        private String variant_group_type;
        private int favourite;
        private String category;
        private String cart_quantity;

        public String getDefault_quantity() {
            return default_quantity;
        }

        public void setDefault_quantity(String default_quantity) {
            this.default_quantity = default_quantity;
        }

        private String default_quantity;
        private String cart_id;
        private int raters;
        private List<PricesBean> prices;

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

        public String getVariant_group_type() {
            return variant_group_type;
        }

        public void setVariant_group_type(String variant_group_type) {
            this.variant_group_type = variant_group_type;
        }

        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
            this.favourite = favourite;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCart_quantity() {
            return cart_quantity;
        }

        public void setCart_quantity(String cart_quantity) {
            this.cart_quantity = cart_quantity;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public int getRaters() {
            return raters;
        }

        public void setRaters(int raters) {
            this.raters = raters;
        }

        public List<PricesBean> getPrices() {
            return prices;
        }

        public void setPrices(List<PricesBean> prices) {
            this.prices = prices;
        }

        public static class PricesBean {
            /**
             * id : 73
             * offer_id : 31
             * color : 1
             * size_category : Footwear
             * size_type : US
             * sizes : 8.5
             * price : 160
             * quantity : 5
             * used : 0
             * grp_id : 9
             * created : 2018-06-21 09:58:25
             * status : 1
             * var_image : https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg
             * color_name : Red
             */

            private String id;
            private String offer_id;
            private String color;
            private String size_category;
            private String size_type;
            private String sizes;
            private String price;
            private String quantity;
            private String used;
            private String grp_id;
            private String created;
            private String status;
            private String var_image;
            private String color_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOffer_id() {
                return offer_id;
            }

            public void setOffer_id(String offer_id) {
                this.offer_id = offer_id;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getSize_category() {
                return size_category;
            }

            public void setSize_category(String size_category) {
                this.size_category = size_category;
            }

            public String getSize_type() {
                return size_type;
            }

            public void setSize_type(String size_type) {
                this.size_type = size_type;
            }

            public String getSizes() {
                return sizes;
            }

            public void setSizes(String sizes) {
                this.sizes = sizes;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getUsed() {
                return used;
            }

            public void setUsed(String used) {
                this.used = used;
            }

            public String getGrp_id() {
                return grp_id;
            }

            public void setGrp_id(String grp_id) {
                this.grp_id = grp_id;
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

            public String getVar_image() {
                return var_image;
            }

            public void setVar_image(String var_image) {
                this.var_image = var_image;
            }

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }
        }
    }
}
