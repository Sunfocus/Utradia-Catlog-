package com.utradia.catalogueappv2.model;

import java.util.List;

public class ProductDetailResponse {
    /**
     * success : 1
     * success_message : Offer details.
     * offer_details : {"id":"25","title":"Demo - 1","region_id":"Wes","cities":"Ahanta West ,Aowin/Suaman","client":"3","brandname":"Panasonic","model":"dsfsd","category_id":"18","end_date":"0000-00-00","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","image2":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","image3":"","image4":"","discount":"140","discounted_price":"","product_weight":"43","description":"<p>sdefdsf dfgdgdfg<\/p>","created":"2018-06-12 10:23:31","status":"1","center_id":"1","show_on_app":"0","shop_enabel":"1","cod":"1","pay_online":"1","pickup_store":"0","pay_and_send":"1","shipment_charges":"","rating":"0.0","views":"8","quantity":"0","variant_group_type":"1","favourite":0,"can_receive_payment":1,"policies":"<p>fefefefefe<\/p>","category":"Trousers","prices":[{"id":"46","offer_id":"29","color":"Blue","size_category":"Footwear","size_type":"","sizes":"","price":"190","quantity":"0","used":"0","grp_id":"4","created":"2018-06-13 07:05:04","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873504_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg"}],"sizes":[{"id":"43","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8","price":"150","quantity":"5","used":"0","grp_id":"8","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"44","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8.5","price":"160","quantity":"6","used":"0","grp_id":"9","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"45","offer_id":"25","color":"2","size_category":"2","size_type":"US","sizes":"8","price":"180","quantity":"3","used":"0","grp_id":"10","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","color_name":"Blue"}],"colors":[{"id":"43","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8","price":"150","quantity":"5","used":"0","grp_id":"8","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"45","offer_id":"25","color":"2","size_category":"2","size_type":"US","sizes":"8","price":"180","quantity":"3","used":"0","grp_id":"10","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","color_name":"Blue"}],"user_data":{"id":"3","name":"Laundry Exchange","brand_name":"Sony India pvt ltd","logo":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png","email":"sony@utradia.com","phone":"918973479444","shipping_policy":"","about":"<p>erfwfwefefff<\/p>","status":"1"},"raters":0}
     * similar_offers : [{"id":"3","title":"Leather Wallet","brandname":"Armani","model":"Latest","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:06:30","brand_id":"96","client_name":"Laundry Exchange","raters":0},{"id":"25","title":"Demo - 1","brandname":"Panasonic","model":"dsfsd","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"2018-06-12 10:23:31","brand_id":"82","client_name":"Laundry Exchange","raters":0}]
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
    private OfferDetailsBean offer_details;

    public SelectedVariantBean getSelectedVariant() {
        return SelectedVariant;
    }

    public void setSelectedVariant(SelectedVariantBean selectedVariant) {
        SelectedVariant = selectedVariant;
    }

    private SelectedVariantBean SelectedVariant;
    private String error_type;
    private List<SimilarOffersBean> similar_offers;

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

    public OfferDetailsBean getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(OfferDetailsBean offer_details) {
        this.offer_details = offer_details;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public List<SimilarOffersBean> getSimilar_offers() {
        return similar_offers;
    }

    public void setSimilar_offers(List<SimilarOffersBean> similar_offers) {
        this.similar_offers = similar_offers;
    }

    public static class SelectedVariantBean{
        private String color="";
        private String variant_id="";
        private String price="";

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVariant_id() {
            return variant_id;
        }

        public void setVariant_id(String variant_id) {
            this.variant_id = variant_id;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        private String size="";

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        private String qty="";
        private String color_id="";

        public String getColor_id() {
            return color_id;
        }

        public void setColor_id(String color_id) {
            this.color_id = color_id;
        }
    }
    public static class OfferDetailsBean {
        /**
         * id : 25
         * title : Demo - 1
         * region_id : Wes
         * cities : Ahanta West ,Aowin/Suaman
         * client : 3
         * brandname : Panasonic
         * model : dsfsd
         * category_id : 18
         * end_date : 0000-00-00
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg
         * image2 : https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg
         * image3 :
         * image4 :
         * discount : 140
         * discounted_price :
         * product_weight : 43
         * description : <p>sdefdsf dfgdgdfg</p>
         * created : 2018-06-12 10:23:31
         * status : 1
         * center_id : 1
         * show_on_app : 0
         * shop_enabel : 1
         * cod : 1
         * pay_online : 1
         * pickup_store : 0
         * pay_and_send : 1
         * shipment_charges :
         * rating : 0.0
         * views : 8
         * quantity : 0
         * variant_group_type : 1
         * favourite : 0
         * can_receive_payment : 1
         * policies : <p>fefefefefe</p>
         * category : Trousers
         * prices : [{"id":"46","offer_id":"29","color":"Blue","size_category":"Footwear","size_type":"","sizes":"","price":"190","quantity":"0","used":"0","grp_id":"4","created":"2018-06-13 07:05:04","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873504_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg"}]
         * sizes : [{"id":"43","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8","price":"150","quantity":"5","used":"0","grp_id":"8","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"44","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8.5","price":"160","quantity":"6","used":"0","grp_id":"9","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"45","offer_id":"25","color":"2","size_category":"2","size_type":"US","sizes":"8","price":"180","quantity":"3","used":"0","grp_id":"10","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","color_name":"Blue"}]
         * colors : [{"id":"43","offer_id":"25","color":"1","size_category":"2","size_type":"US","sizes":"8","price":"150","quantity":"5","used":"0","grp_id":"8","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_5054065447503.jpg","color_name":"Red"},{"id":"45","offer_id":"25","color":"2","size_category":"2","size_type":"US","sizes":"8","price":"180","quantity":"3","used":"0","grp_id":"10","created":"2018-06-13 07:04:02","status":"1","var_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","color_name":"Blue"}]
         * user_data : {"id":"3","name":"Laundry Exchange","brand_name":"Sony India pvt ltd","logo":"https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png","email":"sony@utradia.com","phone":"918973479444","shipping_policy":"","about":"<p>erfwfwefefff<\/p>","status":"1"}
         * raters : 0
         */

        private String id;
        private String title;
        private String region_id;
        private String cities;
        private String client;
        private String brandname;
        private String model;
        private String category_id;
        private String end_date;
        private String image;
        private String image2;
        private String image3;
        private String image4;
        private String discount;
        private String discounted_price;
        private String product_weight;
        private String description;
        private String created;
        private String status;
        private String center_id;
        private String show_on_app;
        private String shop_enabel;
        private String cod;
        private String pay_online;
        private String pickup_store;
        private String pay_and_send;
        private String shipment_charges;
        private String rating;
        private String views;
        private String default_quantity;

        public String getDefault_quantity() {
            return default_quantity;
        }

        public void setDefault_quantity(String default_quantity) {
            this.default_quantity = default_quantity;
        }

        private String variant_group_type;
        private int favourite;
        private int can_receive_payment;
        private String policies;
        private String category;
        private UserDataBean user_data;
        private int raters;
        private List<PricesBean> prices;
        private List<SizesBean> sizes;
        private List<ColorsBean> colors;

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

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
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

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getImage4() {
            return image4;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
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

        public String getProduct_weight() {
            return product_weight;
        }

        public void setProduct_weight(String product_weight) {
            this.product_weight = product_weight;
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

        public String getShop_enabel() {
            return shop_enabel;
        }

        public void setShop_enabel(String shop_enabel) {
            this.shop_enabel = shop_enabel;
        }

        public String getCod() {
            return cod;
        }

        public void setCod(String cod) {
            this.cod = cod;
        }

        public String getPay_online() {
            return pay_online;
        }

        public void setPay_online(String pay_online) {
            this.pay_online = pay_online;
        }

        public String getPickup_store() {
            return pickup_store;
        }

        public void setPickup_store(String pickup_store) {
            this.pickup_store = pickup_store;
        }

        public String getPay_and_send() {
            return pay_and_send;
        }

        public void setPay_and_send(String pay_and_send) {
            this.pay_and_send = pay_and_send;
        }

        public String getShipment_charges() {
            return shipment_charges;
        }

        public void setShipment_charges(String shipment_charges) {
            this.shipment_charges = shipment_charges;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
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

        public int getCan_receive_payment() {
            return can_receive_payment;
        }

        public void setCan_receive_payment(int can_receive_payment) {
            this.can_receive_payment = can_receive_payment;
        }

        public String getPolicies() {
            return policies;
        }

        public void setPolicies(String policies) {
            this.policies = policies;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public UserDataBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserDataBean user_data) {
            this.user_data = user_data;
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

        public List<SizesBean> getSizes() {
            return sizes;
        }

        public void setSizes(List<SizesBean> sizes) {
            this.sizes = sizes;
        }

        public List<ColorsBean> getColors() {
            return colors;
        }

        public void setColors(List<ColorsBean> colors) {
            this.colors = colors;
        }

        public static class UserDataBean {
            /**
             * id : 3
             * name : Laundry Exchange
             * brand_name : Sony India pvt ltd
             * logo : https://utradiapp.s3-us-west-2.amazonaws.com/1527060272_ic_instagram.png
             * email : sony@utradia.com
             * phone : 918973479444
             * shipping_policy :
             * about : <p>erfwfwefefff</p>
             * status : 1
             */

            private String id;
            private String name;
            private String brand_name;
            private String logo;
            private String email;
            private String phone;
            private String shipping_policy;
            private String about;
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

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getShipping_policy() {
                return shipping_policy;
            }

            public void setShipping_policy(String shipping_policy) {
                this.shipping_policy = shipping_policy;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class PricesBean {
            /**
             * id : 46
             * offer_id : 29
             * color : Blue
             * size_category : Footwear
             * size_type :
             * sizes :
             * price : 190
             * quantity : 0
             * used : 0
             * grp_id : 4
             * created : 2018-06-13 07:05:04
             * status : 1
             * var_image : https://utradiapp.s3-us-west-2.amazonaws.com/1528873504_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg
             */

            private String id;
            private String offer_id;
            private String color;

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            private String color_name;
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
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

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
        }

        public static class SizesBean {
            /**
             * id : 43
             * offer_id : 25
             * color : 1
             * size_category : 2
             * size_type : US
             * sizes : 8
             * price : 150
             * quantity : 5
             * used : 0
             * grp_id : 8
             * created : 2018-06-13 07:04:02
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
            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private boolean isSelected;
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

        public static class ColorsBean {
            /**
             * id : 43
             * offer_id : 25
             * color : 1
             * size_category : 2
             * size_type : US
             * sizes : 8
             * price : 150
             * quantity : 5
             * used : 0
             * grp_id : 8
             * created : 2018-06-13 07:04:02
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

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private boolean isSelected;

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

    public static class SimilarOffersBean {
        /**
         * id : 3
         * title : Leather Wallet
         * brandname : Armani
         * model : Latest
         * category_id : 18
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg
         * discount : 5000
         * discounted_price : 500
         * rating : 0.0
         * client : 3
         * status : 1
         * created : 2018-05-31 06:06:30
         * brand_id : 96
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
