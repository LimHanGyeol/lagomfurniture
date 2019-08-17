package com.example.lagomfurniture.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "order_info")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "order_total_price")
    private String orderTotalPrice;

    @Column(name = "order_quantity")
    private String orderQuantity;

    @ManyToMany(mappedBy="orderInfos")
    private List<User> users = new ArrayList<User>();

    @ManyToMany(mappedBy="orderInfos")
    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

    @ManyToMany
    @JoinTable(name="order_info_product",
            joinColumns = @JoinColumn(name = "order_info_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<Product>();

    public OrderInfo(String orderDate, String orderTotalPrice, String orderQuantity) {
        this.orderDate = orderDate;
        this.orderTotalPrice = orderTotalPrice;
        this.orderQuantity = orderQuantity;
    }

    public OrderInfo(String orderDate, String orderTotalPrice, String orderQuantity, List<Product> products) {
        this.orderDate = orderDate;
        this.orderTotalPrice = orderTotalPrice;
        this.orderQuantity = orderQuantity;
        this.products = products;
    }
}
