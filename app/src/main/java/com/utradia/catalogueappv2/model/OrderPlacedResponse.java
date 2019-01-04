package com.utradia.catalogueappv2.model;

public class OrderPlacedResponse {
    /**
     * success : 1
     * success_message : Order placed successfully
     * insert_id : 4
     * orderId : 113BC7CD-4733-47EE-8DED-801838F7A23F
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
    private String insert_id;
    private String orderId;
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

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }
}
