package com.utradia.catalogueappv2.model;

import java.util.List;

public class RegionsResponse {
    /**
     * success : 1
     * success_message : All Regions.
     * regions : [{"id":"1","region_name":"Volta Region","status":"1"},{"id":"2","region_name":"Upper West Region","status":"1"},{"id":"3","region_name":"Upper East Region ","status":"1"},{"id":"4","region_name":"Western Region ","status":"1"},{"id":"5","region_name":"Northern Region ","status":"1"},{"id":"6","region_name":"Eastern Region ","status":"1"},{"id":"7","region_name":"Central Region ","status":"1"},{"id":"8","region_name":"Greater Accra Region ","status":"1"},{"id":"9","region_name":"Brong-Ahafo Region ","status":"1"},{"id":"10","region_name":"Ashanti Region","status":"1"}]
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
         * id : 1
         * region_name : Volta Region
         * status : 1
         */

        private String id;
        private String region_name;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
