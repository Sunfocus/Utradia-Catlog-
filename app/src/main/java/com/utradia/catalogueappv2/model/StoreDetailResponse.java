package com.utradia.catalogueappv2.model;

import java.util.List;

public class StoreDetailResponse {



    private int success;
    private String success_message;
    private ClientDataBean client_data;
    private String error_type;
    private String error_message;
    private List<LocationsBean> locations;
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

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public ClientDataBean getClient_data() {
        return client_data;
    }

    public void setClient_data(ClientDataBean client_data) {
        this.client_data = client_data;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public List<LocationsBean> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsBean> locations) {
        this.locations = locations;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class ClientDataBean {

        private String id;
        private String name;
        private String username;
        private String email;
        private String phone;
        private String type;
        private String access;
        private String brand_name;
        private String merchant_key;
        private String merchant_email;
        private String about;
        private String shipping_policy;
        private String policies;
        private String created;
        private String modified;
        private String status;
        private String logo;
        private String image;
        private String order_data;
        private String gender;
        private int is_following;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getMerchant_key() {
            return merchant_key;
        }

        public void setMerchant_key(String merchant_key) {
            this.merchant_key = merchant_key;
        }

        public String getMerchant_email() {
            return merchant_email;
        }

        public void setMerchant_email(String merchant_email) {
            this.merchant_email = merchant_email;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getShipping_policy() {
            return shipping_policy;
        }

        public void setShipping_policy(String shipping_policy) {
            this.shipping_policy = shipping_policy;
        }

        public String getPolicies() {
            return policies;
        }

        public void setPolicies(String policies) {
            this.policies = policies;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOrder_data() {
            return order_data;
        }

        public void setOrder_data(String order_data) {
            this.order_data = order_data;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getIs_following() {
            return is_following;
        }

        public void setIs_following(int is_following) {
            this.is_following = is_following;
        }
    }

    public static class LocationsBean {

        private String id;
        private String mon_time;
        private String tue_time;
        private String wed_time;
        private String thu_time;
        private String fri_time;
        private String sat_time;
        private String sun_time;
        private String address;
        private String contact_number;
        private String lat;
        private String lng;
        private String city;
        private String state;
        private String country;
        private String website;
        private String created;
        private String modified;
        private String type;
        private String client_id;
        private String email;
        private String image;
        private String center;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMon_time() {
            return mon_time;
        }

        public void setMon_time(String mon_time) {
            this.mon_time = mon_time;
        }

        public String getTue_time() {
            return tue_time;
        }

        public void setTue_time(String tue_time) {
            this.tue_time = tue_time;
        }

        public String getWed_time() {
            return wed_time;
        }

        public void setWed_time(String wed_time) {
            this.wed_time = wed_time;
        }

        public String getThu_time() {
            return thu_time;
        }

        public void setThu_time(String thu_time) {
            this.thu_time = thu_time;
        }

        public String getFri_time() {
            return fri_time;
        }

        public void setFri_time(String fri_time) {
            this.fri_time = fri_time;
        }

        public String getSat_time() {
            return sat_time;
        }

        public void setSat_time(String sat_time) {
            this.sat_time = sat_time;
        }

        public String getSun_time() {
            return sun_time;
        }

        public void setSun_time(String sun_time) {
            this.sun_time = sun_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCenter() {
            return center;
        }

        public void setCenter(String center) {
            this.center = center;
        }
    }

    public static class CategoriesBean {


        private String id;
        private String name;
        private String have_child;
        private String image;
        private String parent_id;
        private int have_offers;
        private List<SubCategoriesBean> sub_categories;

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

        public String getHave_child() {
            return have_child;
        }

        public void setHave_child(String have_child) {
            this.have_child = have_child;
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

        public int getHave_offers() {
            return have_offers;
        }

        public void setHave_offers(int have_offers) {
            this.have_offers = have_offers;
        }

        public List<SubCategoriesBean> getSub_categories() {
            return sub_categories;
        }

        public void setSub_categories(List<SubCategoriesBean> sub_categories) {
            this.sub_categories = sub_categories;
        }

        public static class SubCategoriesBean {

            private String id;
            private String name;
            private String have_child;
            private String image;
            private String parent_id;
            private int have_offers;
            private List<SubCategoriesBean> sub_categories;

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

            public String getHave_child() {
                return have_child;
            }

            public void setHave_child(String have_child) {
                this.have_child = have_child;
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

            public int getHave_offers() {
                return have_offers;
            }

            public void setHave_offers(int have_offers) {
                this.have_offers = have_offers;
            }

            public List<SubCategoriesBean> getSub_categories() {
                return sub_categories;
            }

            public void setSub_categories(List<SubCategoriesBean> sub_categories) {
                this.sub_categories = sub_categories;
            }
        }
    }
}
