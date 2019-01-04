package com.utradia.catalogueappv2.model;

public class SettingsResponse {
    /**
     * success : 1
     * data : {"id":"1","user_id":"5","notifications":"1","order_and_logistics":"1","system_messages":"1","status":"1"}
     * success_message : User notification settings.
     * error_type :
     */

    private int success;
    private DataBean data;
    private String success_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private String error_message;
    private String error_type;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * id : 1
         * user_id : 5
         * notifications : 1
         * order_and_logistics : 1
         * system_messages : 1
         * status : 1
         */

        private String id;
        private String user_id;
        private String notifications;
        private String order_and_logistics;
        private String system_messages;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNotifications() {
            return notifications;
        }

        public void setNotifications(String notifications) {
            this.notifications = notifications;
        }

        public String getOrder_and_logistics() {
            return order_and_logistics;
        }

        public void setOrder_and_logistics(String order_and_logistics) {
            this.order_and_logistics = order_and_logistics;
        }

        public String getSystem_messages() {
            return system_messages;
        }

        public void setSystem_messages(String system_messages) {
            this.system_messages = system_messages;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
