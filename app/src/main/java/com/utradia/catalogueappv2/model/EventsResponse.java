package com.utradia.catalogueappv2.model;

import java.util.List;

public class EventsResponse {
    /**
     * success : 1
     * success_message : All Events.
     * events : [[{"id":"1","title":"Night","client":"3","category_id":"59","end_date":"2018-05-31","image":"1527068940_bar.png","start_time":"10:00 AM","end_time":"10:30 AM","price":"44","description":"wddwdwd","created":"2018-05-23 09:49:00","status":"1","center_id":"1","show_on_app":"0","going_status":"N/A","total_going":0,"client_name":"Laundry Exchange"}],[{"id":"1","title":"Night","client":"3","category_id":"59","end_date":"2018-05-31","image":"1527068940_bar.png","start_time":"10:00 AM","end_time":"10:30 AM","price":"44","description":"wddwdwd","created":"2018-05-23 09:49:00","status":"1","center_id":"1","show_on_app":"0","going_status":"N/A","total_going":0,"client_name":"Laundry Exchange"}],[{"id":"1","title":"Night","client":"3","category_id":"59","end_date":"2018-05-31","image":"1527068940_bar.png","start_time":"10:00 AM","end_time":"10:30 AM","price":"44","description":"wddwdwd","created":"2018-05-23 09:49:00","status":"1","center_id":"1","show_on_app":"0","going_status":"N/A","total_going":0,"client_name":"Laundry Exchange"}],[{"id":"2","title":"Day","client":"3","category_id":"109","end_date":"2018-05-29","image":"1527069018_bar.png","start_time":"9:00 AM","end_time":"9:30 AM","price":"222","description":"2edddededededededwedwededwededdedewdedewdd","created":"2018-05-23 09:50:18","status":"1","center_id":"1","show_on_app":"0","going_status":"Not going","total_going":0,"client_name":"Laundry Exchange"}]]
     * categories : ["Music ","Celebrations","Parties","Religious"]
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
    private List<List<EventsBean>> events;
    private List<String> categories;

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

    public List<List<EventsBean>> getEvents() {
        return events;
    }

    public void setEvents(List<List<EventsBean>> events) {
        this.events = events;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public static class EventsBean {
        /**
         * id : 1
         * title : Night
         * client : 3
         * category_id : 59
         * end_date : 2018-05-31
         * image : 1527068940_bar.png
         * start_time : 10:00 AM
         * end_time : 10:30 AM
         * price : 44
         * description : wddwdwd
         * created : 2018-05-23 09:49:00
         * status : 1
         * center_id : 1
         * show_on_app : 0
         * going_status : N/A
         * total_going : 0
         * client_name : Laundry Exchange
         */

        private String id;
        private String title;
        private String client;
        private String category_id;
        private String end_date;
        private String image;
        private String start_time;
        private String end_time;
        private String price;
        private String description;
        private String created;
        private String status;
        private String center_id;
        private String show_on_app;
        private String going_status;
        private int total_going;
        private String client_name;

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

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getCenter_id() {
            return center_id;
        }

        public void setCenter_id(String center_id) {
            this.center_id = center_id;
        }

        public String getShow_on_app() {
            return show_on_app;
        }

        public void setShow_on_app(String show_on_app) {
            this.show_on_app = show_on_app;
        }

        public String getGoing_status() {
            return going_status;
        }

        public void setGoing_status(String going_status) {
            this.going_status = going_status;
        }

        public int getTotal_going() {
            return total_going;
        }

        public void setTotal_going(int total_going) {
            this.total_going = total_going;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }
    }
}
