package com.utradia.catalogueappv2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutData {

    /**
     * success : 1
     * success_message : Product available on cart
     * product_data : [{"id":"48","title":"Women Celeb Ripped Bodycon High Waist Jean Denim Skirt","brandname":"Other","model":"","category_id":"29","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1531227650_5.jpg","discount":"54","discounted_price":"","rating":"5.0","client":"4","status":"1","created":"2018-07-10 13:00:50","variant_group_type":"3","product_weight":"5","region_id":"4","cities":"Prestea-Huni Valley,Sefwi-Wiawso,SefwiAkontombra,Shama,WasaAmenfi East ,Wasa Amenfi West,Wassa West","cod":"1","delivery_options":"Standard Delivery: 3-5 days","favourite":0,"category":"Skirts","prices":[{"id":"8586","offer_id":"48","color":"","size_category":"Clothing","size_type":"INTL.","sizes":"L","price":"","quantity":"50","used":"0","grp_id":"109","created":"2018-08-09 16:58:08","status":"1","var_image":"","variant_title":"OS","color_name":""}],"cart_quantity":"1","cart_id":"25","raters":1,"shipping_data":{"id":"53","shipment_location_id":"6","start_weight":"0","end_weight":"9","price":"12","created":"2018-07-17 12:45:33","status":"1","delivery_method":"Standard Delivery: 3-5 days"},"shipping_charges":"12","is_shipping_free":0}]
     * default_address : {"id":"20","user_id":"13","full_name":"Vicky Kumar","mobile_number":"85596999999","pincode":"","houseno":"77","locality":"kkjj","city":"354","state":"8","default":"1","type":"shipping","status":"1","city_name":"KPMG","state_name":"Greater Accra Region "}
     */

    private int success;
    private String success_message;
    private DefaultAddressBean default_address;
    private List<ProductDataBean> product_data;

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

    public DefaultAddressBean getDefault_address() {
        return default_address;
    }

    public void setDefault_address(DefaultAddressBean default_address) {
        this.default_address = default_address;
    }

    public List<ProductDataBean> getProduct_data() {
        return product_data;
    }

    public void setProduct_data(List<ProductDataBean> product_data) {
        this.product_data = product_data;
    }

    public static class DefaultAddressBean {
        /**
         * id : 20
         * user_id : 13
         * full_name : Vicky Kumar
         * mobile_number : 85596999999
         * pincode :
         * houseno : 77
         * locality : kkjj
         * city : 354
         * state : 8
         * default : 1
         * type : shipping
         * status : 1
         * city_name : KPMG
         * state_name : Greater Accra Region
         */

        private String id;
        private String user_id;
        private String full_name;
        private String mobile_number;
        private String pincode;
        private String houseno;
        private String locality;
        private String city;
        private String state;
        @SerializedName("default")
        private String defaultX;
        private String type;
        private String status;
        private String city_name;
        private String state_name;

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

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getHouseno() {
            return houseno;
        }

        public void setHouseno(String houseno) {
            this.houseno = houseno;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
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

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
    }

    public static class ProductDataBean {
        /**
         * id : 48
         * title : Women Celeb Ripped Bodycon High Waist Jean Denim Skirt
         * brandname : Other
         * model :
         * category_id : 29
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1531227650_5.jpg
         * discount : 54
         * discounted_price :
         * rating : 5.0
         * client : 4
         * status : 1
         * created : 2018-07-10 13:00:50
         * variant_group_type : 3
         * product_weight : 5
         * region_id : 4
         * cities : Prestea-Huni Valley,Sefwi-Wiawso,SefwiAkontombra,Shama,WasaAmenfi East ,Wasa Amenfi West,Wassa West
         * cod : 1
         * delivery_options : Standard Delivery: 3-5 days
         * favourite : 0
         * category : Skirts
         * prices : [{"id":"8586","offer_id":"48","color":"","size_category":"Clothing","size_type":"INTL.","sizes":"L","price":"","quantity":"50","used":"0","grp_id":"109","created":"2018-08-09 16:58:08","status":"1","var_image":"","variant_title":"OS","color_name":""}]
         * cart_quantity : 1
         * cart_id : 25
         * raters : 1
         * shipping_data : {"id":"53","shipment_location_id":"6","start_weight":"0","end_weight":"9","price":"12","created":"2018-07-17 12:45:33","status":"1","delivery_method":"Standard Delivery: 3-5 days"}
         * shipping_charges : 12
         * is_shipping_free : 0
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
        private String product_weight;
        private String region_id;
        private String cities;
        private String cod;
        private String delivery_options;
        private int favourite;
        private String category;
        private String cart_quantity;
        private String cart_id;
        private int raters;
        private ShippingDataBean shipping_data;
        private String shipping_charges;
        private int is_shipping_free;
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

        public String getProduct_weight() {
            return product_weight;
        }

        public void setProduct_weight(String product_weight) {
            this.product_weight = product_weight;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getCities() {
            return cities;
        }

        public void setCities(String cities) {
            this.cities = cities;
        }

        public String getCod() {
            return cod;
        }

        public void setCod(String cod) {
            this.cod = cod;
        }

        public String getDelivery_options() {
            return delivery_options;
        }

        public void setDelivery_options(String delivery_options) {
            this.delivery_options = delivery_options;
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

        public ShippingDataBean getShipping_data() {
            return shipping_data;
        }

        public void setShipping_data(ShippingDataBean shipping_data) {
            this.shipping_data = shipping_data;
        }

        public String getShipping_charges() {
            return shipping_charges;
        }

        public void setShipping_charges(String shipping_charges) {
            this.shipping_charges = shipping_charges;
        }

        public int getIs_shipping_free() {
            return is_shipping_free;
        }

        public void setIs_shipping_free(int is_shipping_free) {
            this.is_shipping_free = is_shipping_free;
        }

        public List<PricesBean> getPrices() {
            return prices;
        }

        public void setPrices(List<PricesBean> prices) {
            this.prices = prices;
        }

        public static class ShippingDataBean {
            /**
             * id : 53
             * shipment_location_id : 6
             * start_weight : 0
             * end_weight : 9
             * price : 12
             * created : 2018-07-17 12:45:33
             * status : 1
             * delivery_method : Standard Delivery: 3-5 days
             */

            private String id;
            private String shipment_location_id;
            private String start_weight;
            private String end_weight;
            private String price;
            private String created;
            private String status;
            private String delivery_method;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShipment_location_id() {
                return shipment_location_id;
            }

            public void setShipment_location_id(String shipment_location_id) {
                this.shipment_location_id = shipment_location_id;
            }

            public String getStart_weight() {
                return start_weight;
            }

            public void setStart_weight(String start_weight) {
                this.start_weight = start_weight;
            }

            public String getEnd_weight() {
                return end_weight;
            }

            public void setEnd_weight(String end_weight) {
                this.end_weight = end_weight;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getDelivery_method() {
                return delivery_method;
            }

            public void setDelivery_method(String delivery_method) {
                this.delivery_method = delivery_method;
            }
        }

        public static class PricesBean {
            /**
             * id : 8586
             * offer_id : 48
             * color :
             * size_category : Clothing
             * size_type : INTL.
             * sizes : L
             * price :
             * quantity : 50
             * used : 0
             * grp_id : 109
             * created : 2018-08-09 16:58:08
             * status : 1
             * var_image :
             * variant_title : OS
             * color_name :
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
            private String variant_title;
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

            public String getVariant_title() {
                return variant_title;
            }

            public void setVariant_title(String variant_title) {
                this.variant_title = variant_title;
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
