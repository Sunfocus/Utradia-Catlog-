package com.utradia.catalogueappv2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponse {



    private int success;
    private String error_message;
    private OrderDataBean order_data;

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

    public OrderDataBean getOrder_data() {
        return order_data;
    }

    public void setOrder_data(OrderDataBean order_data) {
        this.order_data = order_data;
    }

    public static class OrderDataBean {

        private String id;
        private String order_id;
        private String user_id;
        private String cust_ref;
        private String payment_token;
        private String payment_common_id;
        private String price;
        private String created;
        private String status;
        private double shipment_charges;
        private String payment_mode;
        private String total_price;
        private String shipment_id;
        private String shipping_type;
        private String trace_number;
        private String delivery_status;
        private String order_status;
        private String payment_status;
        private String billing_address_id;
        private String promocode_id;
        private String payment_method;
        private String discount;
        private String discount_type;
        private String client;
        private ShipmentAddressBean shipment_address;
        private List<OrderProductsBean> order_products;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCust_ref() {
            return cust_ref;
        }

        public void setCust_ref(String cust_ref) {
            this.cust_ref = cust_ref;
        }

        public String getPayment_token() {
            return payment_token;
        }

        public void setPayment_token(String payment_token) {
            this.payment_token = payment_token;
        }

        public String getPayment_common_id() {
            return payment_common_id;
        }

        public void setPayment_common_id(String payment_common_id) {
            this.payment_common_id = payment_common_id;
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

        public double getShipment_charges() {
            return shipment_charges;
        }

        public void setShipment_charges(double shipment_charges) {
            this.shipment_charges = shipment_charges;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getShipment_id() {
            return shipment_id;
        }

        public void setShipment_id(String shipment_id) {
            this.shipment_id = shipment_id;
        }

        public String getShipping_type() {
            return shipping_type;
        }

        public void setShipping_type(String shipping_type) {
            this.shipping_type = shipping_type;
        }

        public String getTrace_number() {
            return trace_number;
        }

        public void setTrace_number(String trace_number) {
            this.trace_number = trace_number;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getBilling_address_id() {
            return billing_address_id;
        }

        public void setBilling_address_id(String billing_address_id) {
            this.billing_address_id = billing_address_id;
        }

        public String getPromocode_id() {
            return promocode_id;
        }

        public void setPromocode_id(String promocode_id) {
            this.promocode_id = promocode_id;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_type() {
            return discount_type;
        }

        public void setDiscount_type(String discount_type) {
            this.discount_type = discount_type;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public ShipmentAddressBean getShipment_address() {
            return shipment_address;
        }

        public void setShipment_address(ShipmentAddressBean shipment_address) {
            this.shipment_address = shipment_address;
        }

        public List<OrderProductsBean> getOrder_products() {
            return order_products;
        }

        public void setOrder_products(List<OrderProductsBean> order_products) {
            this.order_products = order_products;
        }

        public static class ShipmentAddressBean {
            /**
             * id : 6
             * user_id : 4
             * full_name : yaw meen
             * mobile_number : 0205628828
             * pincode : 1884
             * houseno : 77
             * locality : santa Maria
             * city : 73
             * state : 1
             * default : 1
             * type : shipping
             * status : 1
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
        }

        public static class OrderProductsBean {
            /**
             * id : 48
             * title : Women Celeb Ripped Bodycon High Waist Jean Denim Skirt
             * brandname : Other
             * model :
             * category_id : 29
             * image : https://utradiapp.s3-us-west-2.amazonaws.com/1531227650_5.jpg
             * discount : 54
             * discounted_price :
             * rating : 0.0
             * client : 4
             * status : 1
             * created : 2018-07-10 13:00:50
             * variant_group_type : 3
             * product_weight : 5
             * region_id : 4
             * cities : Prestea-Huni Valley,Sefwi-Wiawso,SefwiAkontombra,Shama,WasaAmenfi East ,Wasa Amenfi West,Wassa West
             * favourite : 0
             * category : Skirts
             * prices : [{"id":"3712","offer_id":"48","color":"","size_category":"Clothing","size_type":"INTL.","sizes":"S","price":"","quantity":"50","used":"0","grp_id":"109","created":"2018-07-25 11:02:23","status":"1","var_image":"","variant_title":"OS","color_name":""}]
             * cart_quantity : 1
             * cart_id : 2
             * shipment_charges : 50
             * shipment_type : Dropshipping14-28
             * order_item_rating : 0
             * raters : 0
             * quantity : 0
             */

            private String id;
            private String title;
            private String brandname;
            private String model;
            private String category_id;
            private String image;
            private String discount;
            private String discounted_price;
            private double rating;
            private String client;
            private String status;
            private String created;
            private String variant_group_type;
            private String product_weight;
            private String region_id;
            private String cities;
            private int favourite;
            private String category;
            private String cart_quantity;
            private String cart_id;
            private String shipment_charges;
            private String shipment_type;
            private String order_item_rating;
            private int raters;
            private int quantity;
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

            public double getRating() {
                return rating;
            }

            public void setRating(double rating) {
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

            public String getShipment_charges() {
                return shipment_charges;
            }

            public void setShipment_charges(String shipment_charges) {
                this.shipment_charges = shipment_charges;
            }

            public String getShipment_type() {
                return shipment_type;
            }

            public void setShipment_type(String shipment_type) {
                this.shipment_type = shipment_type;
            }

            public String getOrder_item_rating() {
                return order_item_rating;
            }

            public void setOrder_item_rating(String order_item_rating) {
                this.order_item_rating = order_item_rating;
            }

            public int getRaters() {
                return raters;
            }

            public void setRaters(int raters) {
                this.raters = raters;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public List<PricesBean> getPrices() {
                return prices;
            }

            public void setPrices(List<PricesBean> prices) {
                this.prices = prices;
            }

            public static class PricesBean {
                /**
                 * id : 3712
                 * offer_id : 48
                 * color :
                 * size_category : Clothing
                 * size_type : INTL.
                 * sizes : S
                 * price :
                 * quantity : 50
                 * used : 0
                 * grp_id : 109
                 * created : 2018-07-25 11:02:23
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
}
