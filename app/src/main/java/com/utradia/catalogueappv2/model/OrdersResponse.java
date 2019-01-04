package com.utradia.catalogueappv2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResponse {


    private int success;
    private String error_message;

    public String getSuccess_message() {
        return success_message;
    }

    public void setSuccess_message(String success_message) {
        this.success_message = success_message;
    }

    private String success_message;
    private List<OrderDataBean> order_data;

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

    public List<OrderDataBean> getOrder_data() {
        return order_data;
    }

    public void setOrder_data(List<OrderDataBean> order_data) {
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
        private String shipment_charges;
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
        @SerializedName("billing     _address_id")
        private String _$Billing_address_id285; // FIXME check this code

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

        public String getShipment_charges() {
            return shipment_charges;
        }

        public void setShipment_charges(String shipment_charges) {
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

        public String get_$Billing_address_id285() {
            return _$Billing_address_id285;
        }

        public void set_$Billing_address_id285(String _$Billing_address_id285) {
            this._$Billing_address_id285 = _$Billing_address_id285;
        }
    }
}
