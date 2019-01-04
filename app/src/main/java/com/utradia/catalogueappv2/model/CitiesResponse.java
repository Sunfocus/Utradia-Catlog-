package com.utradia.catalogueappv2.model;

import java.util.List;

public class CitiesResponse {
    /**
     * success : 1
     * success_message : All Regions.
     * regions : [{"id":"274","city_name":"Sekondi - Takoradi","region_id":"4","status":"1"},{"id":"275","city_name":"Ahanta West ","region_id":"4","status":"1"},{"id":"276","city_name":"Aowin/Suaman","region_id":"4","status":"1"},{"id":"277","city_name":"Bia ","region_id":"4","status":"1"},{"id":"278","city_name":"Bibiani/Anhwiaso/Bekwai","region_id":"4","status":"1"},{"id":"279","city_name":"Ellembelle","region_id":"4","status":"1"},{"id":"280","city_name":"Jomoro","region_id":"4","status":"1"},{"id":"281","city_name":"Juabeso","region_id":"4","status":"1"},{"id":"282","city_name":"Mpohor/Wassa East District","region_id":"4","status":"1"},{"id":"283","city_name":"Nzema East ","region_id":"4","status":"1"},{"id":"284","city_name":"Prestea-Huni Valley","region_id":"4","status":"1"},{"id":"285","city_name":"Sefwi-Wiawso","region_id":"4","status":"1"},{"id":"286","city_name":"SefwiAkontombra","region_id":"4","status":"1"},{"id":"287","city_name":"Shama","region_id":"4","status":"1"},{"id":"288","city_name":"WasaAmenfi East ","region_id":"4","status":"1"},{"id":"289","city_name":"Wasa Amenfi West","region_id":"4","status":"1"},{"id":"290","city_name":"Wassa West","region_id":"4","status":"1"}]
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
    private List<RegionsBean> regions;

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

    public List<RegionsBean> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionsBean> regions) {
        this.regions = regions;
    }

    public static class RegionsBean {
        /**
         * id : 274
         * city_name : Sekondi - Takoradi
         * region_id : 4
         * status : 1
         */

        private String id;
        private String city_name;
        private String region_id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
