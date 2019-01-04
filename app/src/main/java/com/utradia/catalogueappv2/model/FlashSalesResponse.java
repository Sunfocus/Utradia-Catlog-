package com.utradia.catalogueappv2.model;

import java.util.List;

public class FlashSalesResponse {
    /**
     * success : 1
     * success_message : Flash sales.
     * flash_sale : {"id":"4","title":"Smartflash","start_datetime":"2018-06-05 10:40:07","end_datetime":"2018-06-15 17:40:27","created":"2018-06-02 10:37:22","status":"1","products":[{"id":"30","title":"Demo - 3","brandname":"Panasonic","model":"dsfsd","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"200","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"29","title":"Demo - 2","brandname":"Panasonic","model":"dsfsd","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"25","title":"Demo - 1","brandname":"Panasonic","model":"dsfsd","category_id":"14","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"2018-06-12 10:23:31","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"5","title":"Mac Mini","brandname":"APPLE","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527747290_download_%281%29_mac_.jpeg","discount":"25000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:14:50","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"3","title":"Leather Wallet","brandname":"Armani","model":"Latest","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:06:30","brand_id":"96","client_name":"Laundry Exchange","raters":0}]}
     * next_flash_sale : {"id":"6","title":"tttttt","start_datetime":"2018-06-15 18:00:00","end_datetime":"2018-06-15 22:00:00","created":"2018-06-13 12:38:23","status":"1","products":[{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"13","title":"Key Board","brandname":"HP","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760544_352614-das-keyboard-4.jpg","discount":"1500","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:55:44","brand_id":"72","client_name":"Laundry Exchange","raters":0}]}
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
    private FlashSaleBean flash_sale;
    private NextFlashSaleBean next_flash_sale;
    private String error_type;

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

    public FlashSaleBean getFlash_sale() {
        return flash_sale;
    }

    public void setFlash_sale(FlashSaleBean flash_sale) {
        this.flash_sale = flash_sale;
    }

    public NextFlashSaleBean getNext_flash_sale() {
        return next_flash_sale;
    }

    public void setNext_flash_sale(NextFlashSaleBean next_flash_sale) {
        this.next_flash_sale = next_flash_sale;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public static class FlashSaleBean {
        /**
         * id : 4
         * title : Smartflash
         * start_datetime : 2018-06-05 10:40:07
         * end_datetime : 2018-06-15 17:40:27
         * created : 2018-06-02 10:37:22
         * status : 1
         * products : [{"id":"30","title":"Demo - 3","brandname":"Panasonic","model":"dsfsd","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"200","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"29","title":"Demo - 2","brandname":"Panasonic","model":"dsfsd","category_id":"15","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"0000-00-00 00:00:00","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"25","title":"Demo - 1","brandname":"Panasonic","model":"dsfsd","category_id":"14","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg","discount":"140","discounted_price":"","rating":"0.0","client":"3","status":"1","created":"2018-06-12 10:23:31","brand_id":"82","client_name":"Laundry Exchange","raters":0},{"id":"5","title":"Mac Mini","brandname":"APPLE","model":"Test","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527747290_download_%281%29_mac_.jpeg","discount":"25000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:14:50","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"3","title":"Leather Wallet","brandname":"Armani","model":"Latest","category_id":"18","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527746790_download.jpeg","discount":"5000","discounted_price":"500","rating":"0.0","client":"3","status":"1","created":"2018-05-31 06:06:30","brand_id":"96","client_name":"Laundry Exchange","raters":0}]
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
             * id : 30
             * title : Demo - 3
             * brandname : Panasonic
             * model : dsfsd
             * category_id : 15
             * image : https://utradiapp.s3-us-west-2.amazonaws.com/1528873410_ready-stock-manufacturer-of-plain-tshirts-wholesale-500x500.jpg
             * discount : 200
             * discounted_price :
             * rating : 0.0
             * client : 3
             * status : 1
             * created : 0000-00-00 00:00:00
             * brand_id : 82
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

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            private String quantity;
            private String sold;
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

    public static class NextFlashSaleBean {
        /**
         * id : 6
         * title : tttttt
         * start_datetime : 2018-06-15 18:00:00
         * end_datetime : 2018-06-15 22:00:00
         * created : 2018-06-13 12:38:23
         * status : 1
         * products : [{"id":"15","title":"Mac Book","brandname":"APPLE","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg","discount":"75000","discounted_price":"1000","rating":"0.0","client":"3","status":"1","created":"2018-05-31 10:05:19","brand_id":"69","client_name":"Laundry Exchange","raters":0},{"id":"13","title":"Key Board","brandname":"HP","model":"Latest","category_id":"13","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1527760544_352614-das-keyboard-4.jpg","discount":"1500","discounted_price":"200","rating":"0.0","client":"3","status":"1","created":"2018-05-31 09:55:44","brand_id":"72","client_name":"Laundry Exchange","raters":0}]
         */

        private String id;
        private String title;
        private String start_datetime;
        private String end_datetime;
        private String created;
        private String status;

        private List<ProductsBeanX> products;

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

        public List<ProductsBeanX> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBeanX> products) {
            this.products = products;
        }

        public static class ProductsBeanX {
            /**
             * id : 15
             * title : Mac Book
             * brandname : APPLE
             * model : Latest
             * category_id : 13
             * image : https://utradiapp.s3-us-west-2.amazonaws.com/1527761119_download_%282%29mm.jpeg
             * discount : 75000
             * discounted_price : 1000
             * rating : 0.0
             * client : 3
             * status : 1
             * created : 2018-05-31 10:05:19
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
            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            private String quantity;
            private String sold;

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
