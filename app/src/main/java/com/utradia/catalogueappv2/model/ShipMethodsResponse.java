package com.utradia.catalogueappv2.model;

import java.util.List;

public class ShipMethodsResponse {
    /**
     * success : 1
     * success_message : All Prices.
     * shipping_price : [{"id":"49","shipment_location_id":"4","start_weight":"1","end_weight":"100","price":"50","created":"2018-07-17 12:45:20","status":"1","delivery_method":"Standard Delivery: 3-5 days"},{"id":"55","shipment_location_id":"7","start_weight":"1","end_weight":"100","price":"20","created":"2018-07-18 07:19:20","status":"1","delivery_method":"Express Delivery: 1-2 days"}]
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
    private List<ShippingPriceBean> shipping_price;

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

    public List<ShippingPriceBean> getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(List<ShippingPriceBean> shipping_price) {
        this.shipping_price = shipping_price;
    }

    public static class ShippingPriceBean {
        /**
         * id : 49
         * shipment_location_id : 4
         * start_weight : 1
         * end_weight : 100
         * price : 50
         * created : 2018-07-17 12:45:20
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
}
