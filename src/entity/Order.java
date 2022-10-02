package entity;

import dto.OrderDetailDTO;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private String orderDate;
    private String orderTime;
    private String customerId;
    private ArrayList<OrderDetailDTO> items;

    public Order() {
    }

    public Order(String orderId, String orderDate, String orderTime, String customerId, ArrayList<OrderDetailDTO> items) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCustomerId(customerId);
        this.setItems(items);
    }

    public Order(String orderId, String orderDate, String orderTime, String customerId) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCustomerId(customerId);
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

    public ArrayList<OrderDetailDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetailDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                '}';
    }
}
