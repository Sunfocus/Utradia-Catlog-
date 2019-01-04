package com.utradia.catalogueappv2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductsByCatResponse {


    /**
     * success : 1
     * success_message : All Offers and sub categories.
     * offers : [{"id":"437","title":"Samsung EVO Plus U1 Memory Card Class10","brandname":"SAMSUNG","model":"","category_id":"236","image":"https://utradiapp.s3-us-west-2.amazonaws.com/1540831254_Samsung-U3-Memory-Card-128GB-EVO-PLUS-Micro-sd-card-Class10-UHS-1-64GB-256GB-Speed.jpg_640x640.jpg","discount":"39","discounted_price":"","rating":"0.0","client":"4","status":"1","created":"2018-10-29 16:07:16","brand_id":"98","client_name":"Dropship Gh","raters":0,"favourite":0}]
     * shops : [{"id":"4","name":"Dropship Gh"}]
     * brands : [{"id":"98","name":"SAMSUNG"}]
     * error_type :
     */

    private int success;
    private String success_message;
    private String error_type;
    private String error_message;
    private List<OffersBean> offers;
    private List<ShopsBean> shops;
    private List<BrandsBean> brands;

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

    public List<ShopsBean> getShops() {
        return shops;
    }

    public void setShops(List<ShopsBean> shops) {
        this.shops = shops;
    }

    public List<BrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandsBean> brands) {
        this.brands = brands;
    }

    public static class OffersBean {
        /**
         * id : 437
         * title : Samsung EVO Plus U1 Memory Card Class10
         * brandname : SAMSUNG
         * model :
         * category_id : 236
         * image : https://utradiapp.s3-us-west-2.amazonaws.com/1540831254_Samsung-U3-Memory-Card-128GB-EVO-PLUS-Micro-sd-card-Class10-UHS-1-64GB-256GB-Speed.jpg_640x640.jpg
         * discount : 39
         * discounted_price :
         * rating : 0.0
         * client : 4
         * status : 1
         * created : 2018-10-29 16:07:16
         * brand_id : 98
         * client_name : Dropship Gh
         * raters : 0
         * favourite : 0
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
        private int favourite;

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

        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
            this.favourite = favourite;
        }
    }

    public static class ShopsBean implements Parcelable {
        /**
         * id : 4
         * name : Dropship Gh
         */

        private String id;
        private String name;
        private boolean shopStatus;
        private boolean header;


        protected ShopsBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            shopStatus = in.readByte() != 0;
            header = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeByte((byte) (shopStatus ? 1 : 0));
            dest.writeByte((byte) (header ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ShopsBean> CREATOR = new Creator<ShopsBean>() {
            @Override
            public ShopsBean createFromParcel(Parcel in) {
                return new ShopsBean(in);
            }

            @Override
            public ShopsBean[] newArray(int size) {
                return new ShopsBean[size];
            }
        };

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

        public boolean isShopStatus() {
            return shopStatus;
        }

        public void setShopStatus(boolean shopStatus) {
            this.shopStatus = shopStatus;
        }

        public boolean isHeader() {
            return header;
        }

        public void setHeader(boolean header) {
            this.header = header;
        }
    }

    public static class BrandsBean  implements Parcelable{
        /**
         * id : 98
         * name : SAMSUNG
         */

        private String id;
        private String name;
        private boolean brandStatus;
        private boolean header;


        protected BrandsBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            brandStatus = in.readByte() != 0;
            header = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeByte((byte) (brandStatus ? 1 : 0));
            dest.writeByte((byte) (header ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<BrandsBean> CREATOR = new Creator<BrandsBean>() {
            @Override
            public BrandsBean createFromParcel(Parcel in) {
                return new BrandsBean(in);
            }

            @Override
            public BrandsBean[] newArray(int size) {
                return new BrandsBean[size];
            }
        };

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

        public boolean isBrandStatus() {
            return brandStatus;
        }

        public void setBrandStatus(boolean brandStatus) {
            this.brandStatus = brandStatus;
        }

        public boolean isHeader() {
            return header;
        }

        public void setHeader(boolean header) {
            this.header = header;
        }
    }
}
