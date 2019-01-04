package com.utradia.catalogueappv2.model;

import java.util.List;

public class HomeResponse {


    /**
     * success : 1
     * categories : [{"id":"3","name":"Electronics","description":"Testing","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669177_electronics.png","parent_id":"0","have_child":"1","order_data":"1","created":"2018-05-16 06:47:56","status":"1"},{"id":"4","name":"Fashion","description":"Fashion","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669188_fashion.png","parent_id":"0","have_child":"1","order_data":"2","created":"2018-05-30 06:12:36","status":"1"},{"id":"5","name":"Health & Beauty","description":"Health","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669197_health-%26-beauty.png","parent_id":"0","have_child":"0","order_data":"3","created":"2018-05-30 06:13:07","status":"1"},{"id":"6","name":"Appliances","description":"Appliances","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669217_appliance.png","parent_id":"0","have_child":"0","order_data":"4","created":"2018-05-30 06:13:26","status":"1"},{"id":"7","name":"Home & Furniture","description":"furniture","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669230_home.png","parent_id":"0","have_child":"0","order_data":"5","created":"2018-05-30 06:13:54","status":"1"},{"id":"8","name":"Baby & Kids","description":"kids","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669243_baby.png","parent_id":"0","have_child":"0","order_data":"6","created":"2018-05-30 06:14:21","status":"1"},{"id":"9","name":"Ghana Made","description":"Ghana","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527669263_gh.png","parent_id":"0","have_child":"0","order_data":"7","created":"2018-05-30 06:14:46","status":"1"}]
     * banners : [{"id":"1","title":"Test","banner_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527662274_ic_banner1.png","created":"2018-05-15 09:57:57","status":"1"},{"id":"2","title":"New Product","banner_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527662297_event.jpg","created":"2018-05-25 06:57:41","status":"1"},{"id":"3","title":"Banner 1","banner_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527692159_banner1.jpg","created":"2018-05-30 14:55:59","status":"1"},{"id":"4","title":"Banner 2","banner_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527692207_banner2.jpg","created":"2018-05-30 14:56:47","status":"1"},{"id":"5","title":"sdsa","banner_image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527762036_52-2.jpg","created":"2018-05-31 10:20:36","status":"1"}]
     * recommended_items : [{"id":"16","title":"Power Bank","brandname":"PHILIPS","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761382_51uaaL5NWvL._SL1200_600x.jpg","discount":"1500","discounted_price":"100","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:09:42","brand_id":"70","client_name":"Laundry Exchange","raters":0},{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"2","title":"Mobile","brandname":"NOKIA","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746180_111323-v1-nokia-6-mobile-phone-large-1.jpg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 05:56:20","brand_id":"71","client_name":"Laundry Exchange","raters":0},{"id":"12","title":"Pen Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760326_sony-usm32gr-b2-in-31301912-original-imaeksnuhb4ytpp8.jpeg","discount":"1000","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:52:06","brand_id":"74","client_name":"Laundry Exchange","raters":0},{"id":"5","title     ":"Mac Mini","brandname":"APPLE","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527747290_download_%281%29_mac_.jpeg","discount":"25000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:14:50","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"14","title":"Hard Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760696_download_%282%29h.jpeg","discount":"5200","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:58:16","brand_id":"74","client_name":"Laundry Exchange","raters":0}]
     * most_popular : [{"id":"1","title":"Deal Zone","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527591908_1.png","created":"2018-05-29 10:56:00","status":"1"},{"id":"2","title":"Clearance Sales","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527591926_2.png","created":"2018-05-29 10:56:52","status":"1"},{"id":"3","title":"New Arrivals","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527591948_3.png","created":"2018-05-29 11:05:48","status":"1"},{"id":"4","title":"Promo Sales","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527591963_4.png","created":"2018-05-29 11:06:03","status":"1"}]
     * flash_sale : [{"id":"2","title":"Grand Sale","start_datetime":"2018-06-01 13:00:00","end_datetime":"2018-06-01 16:00:00","created":"2018-06-01 06:40:50","status":"1","products":[{"id":"11","title":"Smart watch","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527759906_download_%281%29.jpeg","discount":"35000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:45:06","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"12","title":"Pen Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760326_sony-usm32gr-b2-in-31301912-original-imaeksnuhb4ytpp8.jpeg","discount":"1000","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:52:06","brand_id":"74","client_name":"Laundry Exchange","raters":0},{"id":"3","title":"Leather Wallet","brandname":"Armani","model":"Latest","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:06:30","brand_id":"96","client_name":"Laundry Exchange","raters":0}]}]
     * success_message : Data
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
    private List<CategoriesBean> categories;
    private List<BannersBean> banners;
    private List<RecommendedItemsBean> recommended_items;
    private List<MostPopularBean> most_popular;
    private List<FlashSaleBean> flash_sale;

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

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<RecommendedItemsBean> getRecommended_items() {
        return recommended_items;
    }

    public void setRecommended_items(List<RecommendedItemsBean> recommended_items) {
        this.recommended_items = recommended_items;
    }

    public List<MostPopularBean> getMost_popular() {
        return most_popular;
    }

    public void setMost_popular(List<MostPopularBean> most_popular) {
        this.most_popular = most_popular;
    }

    public List<FlashSaleBean> getFlash_sale() {
        return flash_sale;
    }

    public void setFlash_sale(List<FlashSaleBean> flash_sale) {
        this.flash_sale = flash_sale;
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

    public static class BannersBean {
        /**
         * id : 1
         * title : Test
         * banner_image : https://utradiapp.s3-us-west-2.amazonaws.com/1527662274_ic_banner1.png
         * created : 2018-05-15 09:57:57
         * status : 1
         */

        private String id;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        private String category_id;
        private String title;
        private String banner_image;
        private String created;
        private String status;

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

        public String getBanner_image() {
            return banner_image;
        }

        public void setBanner_image(String banner_image) {
            this.banner_image = banner_image;
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

    public static class RecommendedItemsBean {
        /**
         * id : 16
         * title : Power Bank
         * brandname : PHILIPS
         * model : Latest
         * category_id : 13
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527761382_51uaaL5NWvL._SL1200_600x.jpg
         * discount : 1500
         * discounted_price : 100
         * rating : 0.0
         * client : 3
         * status : 1
         * created : 2018-05-31 10:09:42
         * brand_id : 70
         * client_name : Laundry Exchange
         * raters : 0
         * title      : Mac Mini
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class MostPopularBean {
        /**
         * id : 1
         * title : Deal Zone
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527591908_1.png
         * created : 2018-05-29 10:56:00
         * status : 1
         */

        private String id;
        private String category_id;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        private String title;
        private String image;
        private String created;
        private String status;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

    public static class FlashSaleBean {
        /**
         * id : 2
         * title : Grand Sale
         * start_datetime : 2018-06-01 13:00:00
         * end_datetime : 2018-06-01 16:00:00
         * created : 2018-06-01 06:40:50
         * status : 1
         * products : [{"id":"11","title":"Smart watch","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527759906_download_%281%29.jpeg","discount":"35000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:45:06","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"12","title":"Pen Drive","brandname":"SONY","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760326_sony-usm32gr-b2-in-31301912-original-imaeksnuhb4ytpp8.jpeg","discount":"1000","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:52:06","brand_id":"74","client_name":"Laundry Exchange","raters":0},{"id":"3","title":"Leather Wallet","brandname":"Armani","model":"Latest","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:06:30","brand_id":"96","client_name":"Laundry Exchange","raters":0}]
         */

        private String id;
        private String title;
        private String start_datetime;
        private String end_datetime;
        private String created;
        private String status;
        private List<ProductsBean> products;

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

        public String getStart_datetime() {
            return start_datetime;
        }

        public void setStart_datetime(String start_datetime) {
            this.start_datetime = start_datetime;
        }

        public String getEnd_datetime() {
            return end_datetime;
        }

        public void setEnd_datetime(String end_datetime) {
            this.end_datetime = end_datetime;
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

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * id : 11
             * title : Smart watch
             * brandname : APPLE
             * model : Latest
             * category_id : 13
             * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527759906_download_%281%29.jpeg
             * discount : 35000
             * discounted_price : 500
             * rating : 0.0
             * client : 3
             * status : 1
             * created : 2018-05-31 09:45:06
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
}
