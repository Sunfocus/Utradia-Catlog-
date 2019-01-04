package com.utradia.catalogueappv2.model;

public class AddProductReviewResponse {


    /**
     * success : 1
     * success_message : Product rated successfully.
     * error_type :
     */

    private int success;
    private String success_message;
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

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }
}
