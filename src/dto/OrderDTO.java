package dto;

import entity.Order;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {
    private String orderId;
    private String orderDate;
    private String orderTime;
    private String customerId;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String orderDate, String orderTime, String customerId, List<OrderDetailDTO> orderDetail) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCustomerId(customerId);
        this.setOrderDetail(orderDetail);
    }
    public OrderDTO(Order orders) {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
