package com.utradia.catalogueappv2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderListResponse {


    /**
     * success : 1
     * error_message : Avaliable orders
     * data : {"all_orders":[{"id":"3","order_id":"3","total_price":"79.0","payment_mode":"COD","created":"2018-07-21 12:51:30","order_status":"PENDING","item_total":"1"},{"id":"2","order_id":"2","total_price":"200.0","payment_mode":"COD","created":"2018-07-20 14:02:03","order_status":"PENDING","item_total":"2"}],"pending_orders":[{"id":"3","order_id":"3","total_price":"79.0","payment_mode":"COD","created":"2018-07-21 12:51:30","order_status":"PENDING","item_total":"1"},{"id":"2","order_id":"2","total_price":"200.0","payment_mode":"COD","created":"2018-07-20 14:02:03","order_status":"PENDING","item_total":"2"}],"shipped_orders":[],"processing_orders":[],"cancelled_orders":[],"completed_orders":[]}
     */

    private int success;
    private String error_message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<OrdersBean> all_orders;
        private List<OrdersBean> pending_orders;
        private List<OrdersBean> shipped_orders;
        private List<OrdersBean> processing_orders;
        private List<OrdersBean> cancelled_orders;
        private List<OrdersBean> completed_orders;

        public List<OrdersBean> getAll_orders() {
            return all_orders;
        }

        public void setAll_orders(List<OrdersBean> all_orders) {
            this.all_orders = all_orders;
        }

        public List<OrdersBean> getPending_orders() {
            return pending_orders;
        }

        public void setPending_orders(List<OrdersBean> pending_orders) {
            this.pending_orders = pending_orders;
        }

        public List<OrdersBean> getShipped_orders() {
            return shipped_orders;
        }

        public void setShipped_orders(List<OrdersBean> shipped_orders) {
            this.shipped_orders = shipped_orders;
        }

        public List<OrdersBean> getProcessing_orders() {
            return processing_orders;
        }

        public void setProcessing_orders(List<OrdersBean> processing_orders) {
            this.processing_orders = processing_orders;
        }

        public List<OrdersBean> getCancelled_orders() {
            return cancelled_orders;
        }

        public void setCancelled_orders(List<OrdersBean> cancelled_orders) {
            this.cancelled_orders = cancelled_orders;
        }

        public List<OrdersBean> getCompleted_orders() {
            return completed_orders;
        }

        public void setCompleted_orders(List<OrdersBean> completed_orders) {
            this.completed_orders = completed_orders;
        }

        public static class OrdersBean implements Parcelable{
            /**
             * id : 3
             * order_id : 3
             * total_price : 79.0
             * payment_mode : COD
             * created : 2018-07-21 12:51:30
             * order_status : PENDING
             * item_total : 1
             */

            private String id;
            private String order_id;
            private double total_price;
            private String payment_mode;
            private String orderId;
            private String created;
            private String order_status;
            private String item_total;

            protected OrdersBean(Parcel in) {
                id = in.readString();
                order_id = in.readString();
                total_price = in.readDouble();
                payment_mode = in.readString();
                orderId = in.readString();
                created = in.readString();
                order_status = in.readString();
                item_total = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(order_id);
                dest.writeDouble(total_price);
                dest.writeString(payment_mode);
                dest.writeString(orderId);
                dest.writeString(created);
                dest.writeString(order_status);
                dest.writeString(item_total);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<OrdersBean> CREATOR = new Creator<OrdersBean>() {
                @Override
                public OrdersBean createFromParcel(Parcel in) {
                    return new OrdersBean(in);
                }

                @Override
                public OrdersBean[] newArray(int size) {
                    return new OrdersBean[size];
                }
            };

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

            public double getTotal_price() {
                return total_price;
            }

            public void setTotal_price(double total_price) {
                this.total_price = total_price;
            }

            public String getPayment_mode() {
                return payment_mode;
            }

            public void setPayment_mode(String payment_mode) {
                this.payment_mode = payment_mode;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getItem_total() {
                return item_total;
            }

            public void setItem_total(String item_total) {
                this.item_total = item_total;
            }


            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }
        }


    }
}
