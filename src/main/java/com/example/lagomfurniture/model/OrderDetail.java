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
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_detail_name")
    private String orderDetailName;

    @Column(name = "order_detail_address1")
    private String orderDetailAddress1;

    @Column(name = "order_detail_address2")
    private String orderDetailAddress2;

    @Column(name = "order_detail_phone_num")
    private String orderDetailPhoneNum;

    public OrderDetail(){
    }

    @ManyToMany(mappedBy="orderdetails")
    private List<User> users = new ArrayList<User>();

    @ManyToMany
    @JoinTable(name="order_detail_product",
            joinColumns = @JoinColumn(name = "order_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<Product>();


    @ManyToMany
    @JoinTable(name="order_detail_info",
            joinColumns = @JoinColumn(name = "order_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

    //결제 시 회원 정보 입력
    public OrderDetail(String orderDetailName, String orderDetailAddress1, String orderDetailAddress2, String orderDetailPhoneNum) {
        this.orderDetailName = orderDetailName;
        this.orderDetailAddress1 = orderDetailAddress1;
        this.orderDetailAddress2 = orderDetailAddress2;
        this.orderDetailPhoneNum = orderDetailPhoneNum;
    }
}
